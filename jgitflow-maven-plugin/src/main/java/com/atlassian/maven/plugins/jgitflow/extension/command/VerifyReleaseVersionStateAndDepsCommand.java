package com.atlassian.maven.plugins.jgitflow.extension.command;

/*-
 * #%L
 * JGitFlow :: Maven Plugin
 * %%
 * Copyright (C) 2017 Atlassian Pty, LTD, Ultreia.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.List;

import com.atlassian.jgitflow.core.BranchType;
import com.atlassian.jgitflow.core.GitFlowConfiguration;
import com.atlassian.jgitflow.core.command.JGitFlowCommand;
import com.atlassian.jgitflow.core.exception.JGitFlowExtensionException;
import com.atlassian.jgitflow.core.extension.ExtensionCommand;
import com.atlassian.jgitflow.core.extension.ExtensionFailStrategy;
import com.atlassian.maven.plugins.jgitflow.ReleaseContext;
import com.atlassian.maven.plugins.jgitflow.VersionState;
import com.atlassian.maven.plugins.jgitflow.exception.UnresolvedSnapshotsException;
import com.atlassian.maven.plugins.jgitflow.helper.BranchHelper;
import com.atlassian.maven.plugins.jgitflow.helper.ProjectHelper;
import com.atlassian.maven.plugins.jgitflow.provider.ContextProvider;
import com.atlassian.maven.plugins.jgitflow.provider.ProjectCacheKey;

import com.google.common.base.Joiner;

import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.eclipse.jgit.api.Git;

@Component(role = VerifyReleaseVersionStateAndDepsCommand.class)
public class VerifyReleaseVersionStateAndDepsCommand implements ExtensionCommand
{
    private static final String ls = System.getProperty("line.separator");

    @Requirement
    private ContextProvider contextProvider;

    @Requirement
    private ProjectHelper projectHelper;

    @Requirement
    private BranchHelper branchHelper;


    @Override
    public void execute(GitFlowConfiguration configuration, Git git, JGitFlowCommand gitFlowCommand) throws JGitFlowExtensionException
    {
        try
        {
            BranchType branchType = branchHelper.getCurrentBranchType();
            ProjectCacheKey cacheKey = null;

            switch (branchType)
            {
                case RELEASE:
                    cacheKey = ProjectCacheKey.RELEASE_BRANCH;
                    break;

                case HOTFIX:
                    cacheKey = ProjectCacheKey.HOTFIX_BRANCH;
                    break;

                case DEVELOP:
                    cacheKey = ProjectCacheKey.DEVELOP_BRANCH;
                    break;

                case MASTER:
                    cacheKey = ProjectCacheKey.MASTER_BRANCH;
                    break;

                case FEATURE:
                    cacheKey = ProjectCacheKey.FEATURE_BRANCH;
                    break;

                default:
                    throw new JGitFlowExtensionException("Unsupported branch type '" + branchType.name() + "' while running " + this.getClass().getSimpleName() + " command");
            }

            ReleaseContext ctx = contextProvider.getContext();
            List<MavenProject> branchProjects = branchHelper.getProjectsForCurrentBranch();

            projectHelper.checkPomForVersionState(VersionState.RELEASE, branchProjects);

            if (!ctx.isAllowSnapshots())
            {
                List<String> snapshots = projectHelper.checkForNonReactorSnapshots(cacheKey, branchProjects);
                if (!snapshots.isEmpty())
                {
                    String details = Joiner.on(ls).join(snapshots);
                    throw new UnresolvedSnapshotsException("Cannot finish a " + branchType.name().toLowerCase() + " due to snapshot dependencies:" + ls + details);
                }
            }
        }
        catch (Exception e)
        {
            throw new JGitFlowExtensionException("Error verifying version state in poms: " + e.getMessage(), e);
        }
    }

    @Override
    public ExtensionFailStrategy failStrategy()
    {
        return ExtensionFailStrategy.ERROR;
    }
}
