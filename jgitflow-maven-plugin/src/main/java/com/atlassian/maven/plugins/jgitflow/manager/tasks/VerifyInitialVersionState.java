package com.atlassian.maven.plugins.jgitflow.manager.tasks;

/*-
 * #%L
 * Maven JGitFlow Plugin
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
import com.atlassian.jgitflow.core.JGitFlow;
import com.atlassian.maven.plugins.jgitflow.ReleaseContext;
import com.atlassian.maven.plugins.jgitflow.VersionState;
import com.atlassian.maven.plugins.jgitflow.exception.MavenJGitFlowException;
import com.atlassian.maven.plugins.jgitflow.exception.UnresolvedSnapshotsException;
import com.atlassian.maven.plugins.jgitflow.helper.ProjectHelper;
import com.atlassian.maven.plugins.jgitflow.provider.ContextProvider;
import com.atlassian.maven.plugins.jgitflow.provider.JGitFlowProvider;
import com.atlassian.maven.plugins.jgitflow.provider.ProjectCacheKey;

import com.google.common.base.Joiner;

import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;

import static com.google.common.base.Preconditions.checkNotNull;

@Component(role = VerifyInitialVersionState.class)
public class VerifyInitialVersionState
{
    public static final String ls = System.getProperty("line.separator");

    @Requirement
    private JGitFlowProvider jGitFlowProvider;

    @Requirement
    private ProjectHelper projectHelper;

    @Requirement
    private ContextProvider contextProvider;

    public void run(final BranchType branchType, List<MavenProject> branchProjects) throws MavenJGitFlowException
    {
        try
        {
            JGitFlow flow = jGitFlowProvider.gitFlow();
            ReleaseContext ctx = contextProvider.getContext();

            VersionState initialVersionState = null;
            ProjectCacheKey cacheKey = null;

            switch (branchType)
            {
                case RELEASE:
                    initialVersionState = VersionState.SNAPSHOT;
                    cacheKey = ProjectCacheKey.DEVELOP_BRANCH;
                    break;

                case HOTFIX:
                    initialVersionState = VersionState.RELEASE;
                    cacheKey = ProjectCacheKey.MASTER_BRANCH;
                    break;

                case FEATURE:
                    initialVersionState = VersionState.SNAPSHOT;
                    cacheKey = ProjectCacheKey.DEVELOP_BRANCH;
                    break;
            }

            checkNotNull(initialVersionState);
            checkNotNull(cacheKey);

            projectHelper.checkPomForVersionState(initialVersionState, branchProjects);

            if (!ctx.isAllowSnapshots())
            {
                List<String> snapshots = projectHelper.checkForNonReactorSnapshots(cacheKey, branchProjects);
                if (!snapshots.isEmpty())
                {
                    String details = Joiner.on(ls).join(snapshots);
                    throw new UnresolvedSnapshotsException("Cannot start a " + branchType.name().toLowerCase() + " due to snapshot dependencies:" + ls + details);
                }
            }
        }
        catch (Exception e)
        {
            throw new MavenJGitFlowException("Error verifying initial version state in poms: " + e.getMessage(), e);
        }
    }

}
