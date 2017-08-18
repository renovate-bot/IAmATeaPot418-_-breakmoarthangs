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

import com.atlassian.jgitflow.core.JGitFlow;
import com.atlassian.maven.plugins.jgitflow.ReleaseContext;
import com.atlassian.maven.plugins.jgitflow.exception.MavenJGitFlowException;
import com.atlassian.maven.plugins.jgitflow.helper.BranchHelper;
import com.atlassian.maven.plugins.jgitflow.helper.MavenExecutionHelper;
import com.atlassian.maven.plugins.jgitflow.provider.ContextProvider;
import com.atlassian.maven.plugins.jgitflow.provider.JGitFlowProvider;
import com.atlassian.maven.plugins.jgitflow.provider.MavenSessionProvider;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.release.util.ReleaseUtil;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;

@Component(role = GetProjectsForBranch.class)
public class GetProjectsForBranch
{
    @Requirement
    private MavenExecutionHelper mavenExecutionHelper;

    @Requirement
    private JGitFlowProvider jGitFlowProvider;

    @Requirement
    private MavenSessionProvider sessionProvider;

    @Requirement
    private ContextProvider contextProvider;

    @Requirement
    private BranchHelper branchHelper;

    public List<MavenProject> run(String branchName, List<MavenProject> originalProjects) throws MavenJGitFlowException
    {
        try
        {
            JGitFlow flow = jGitFlowProvider.gitFlow();
            ReleaseContext ctx = contextProvider.getContext();

            String originalBranchName = branchHelper.getCurrentBranchName();

            flow.git().checkout().setName(branchName).call();

            //reload the reactor projects for develop
            MavenSession branchSession = mavenExecutionHelper.getSessionForBranch(branchName, ReleaseUtil.getRootProject(originalProjects), sessionProvider.getSession());

            flow.git().checkout().setName(originalBranchName).call();

            return branchSession.getSortedProjects();
        }
        catch (Exception e)
        {
            throw new MavenJGitFlowException("Error checking out branch and loading projects", e);
        }
    }
}
