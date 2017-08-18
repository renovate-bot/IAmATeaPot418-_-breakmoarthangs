package com.atlassian.maven.plugins.jgitflow.manager;

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
import com.atlassian.jgitflow.core.exception.JGitFlowException;
import com.atlassian.maven.plugins.jgitflow.ReleaseContext;
import com.atlassian.maven.plugins.jgitflow.VersionType;
import com.atlassian.maven.plugins.jgitflow.exception.MavenJGitFlowException;
import com.atlassian.maven.plugins.jgitflow.extension.ReleaseStartPluginExtension;
import com.atlassian.maven.plugins.jgitflow.helper.MavenExecutionHelper;
import com.atlassian.maven.plugins.jgitflow.helper.PomUpdater;
import com.atlassian.maven.plugins.jgitflow.helper.ProjectHelper;
import com.atlassian.maven.plugins.jgitflow.provider.BranchLabelProvider;
import com.atlassian.maven.plugins.jgitflow.provider.ProjectCacheKey;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Requirement;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractProductionBranchManager extends AbstractFlowReleaseManager
{
    private final BranchType branchType;

    @Requirement
    protected MavenExecutionHelper mavenExecutionHelper;

    @Requirement
    protected ProjectHelper projectHelper;

    @Requirement
    protected BranchLabelProvider labelProvider;

    @Requirement
    protected PomUpdater pomUpdater;

    @Requirement
    protected ReleaseStartPluginExtension extension;

    public AbstractProductionBranchManager(BranchType branchType)
    {
        this.branchType = branchType;
    }

    public String getStartLabelAndRunPreflight(ReleaseContext ctx, List<MavenProject> reactorProjects, MavenSession session) throws JGitFlowException, MavenJGitFlowException
    {
        runPreflight(ctx, reactorProjects, session);

        JGitFlow flow = jGitFlowProvider.gitFlow();

        String branchName = null;
        VersionType versionType = null;
        ProjectCacheKey cacheKey = null;

        switch (branchType)
        {
            case RELEASE:
                branchName = flow.getDevelopBranchName();
                versionType = VersionType.RELEASE;
                cacheKey = ProjectCacheKey.RELEASE_START_LABEL;
                break;

            case HOTFIX:
                branchName = flow.getMasterBranchName();
                versionType = VersionType.HOTFIX;
                cacheKey = ProjectCacheKey.HOTFIX_LABEL;
                break;
        }

        checkNotNull(branchName);
        checkNotNull(versionType);
        checkNotNull(cacheKey);

        List<MavenProject> branchProjects = checkoutAndGetProjects.run(branchName).getProjects();

        verifyInitialVersionState.run(branchType, branchProjects);

        return labelProvider.getNextVersionLabel(versionType, cacheKey, branchProjects);

    }

    public String getFinishLabelAndRunPreflight(ReleaseContext ctx, List<MavenProject> reactorProjects, MavenSession session) throws JGitFlowException, MavenJGitFlowException
    {
        runPreflight(ctx, reactorProjects, session);

        return labelProvider.getCurrentProductionVersionLabel(branchType);

    }
}
