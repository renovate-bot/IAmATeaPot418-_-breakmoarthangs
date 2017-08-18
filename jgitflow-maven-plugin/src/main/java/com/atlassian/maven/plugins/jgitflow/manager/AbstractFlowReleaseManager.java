package com.atlassian.maven.plugins.jgitflow.manager;

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

import com.atlassian.jgitflow.core.JGitFlow;
import com.atlassian.jgitflow.core.exception.JGitFlowException;
import com.atlassian.maven.plugins.jgitflow.ReleaseContext;
import com.atlassian.maven.plugins.jgitflow.exception.MavenJGitFlowException;
import com.atlassian.maven.plugins.jgitflow.helper.JGitFlowSetupHelper;
import com.atlassian.maven.plugins.jgitflow.manager.tasks.CheckoutAndGetProjects;
import com.atlassian.maven.plugins.jgitflow.manager.tasks.VerifyInitialVersionState;
import com.atlassian.maven.plugins.jgitflow.provider.ContextProvider;
import com.atlassian.maven.plugins.jgitflow.provider.JGitFlowProvider;
import com.atlassian.maven.plugins.jgitflow.provider.MavenSessionProvider;
import com.atlassian.maven.plugins.jgitflow.provider.ReactorProjectsProvider;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.AbstractLogEnabled;

/**
 * @since version
 */
public abstract class AbstractFlowReleaseManager extends AbstractLogEnabled implements FlowReleaseManager
{
    @Requirement
    protected ContextProvider contextProvider;

    @Requirement
    protected MavenSessionProvider sessionProvider;

    @Requirement
    protected ReactorProjectsProvider projectsProvider;

    @Requirement
    protected JGitFlowProvider jGitFlowProvider;

    @Requirement
    protected JGitFlowSetupHelper setupHelper;

    @Requirement
    protected CheckoutAndGetProjects checkoutAndGetProjects;

    @Requirement
    protected VerifyInitialVersionState verifyInitialVersionState;

    @Override
    public void deploy(ReleaseContext ctx, List<MavenProject> reactorProjects, MavenSession session, String buildNumber, String goals) throws MavenJGitFlowException
    {
        //do nothing. override if you need to
    }

    protected void setupProviders(ReleaseContext ctx, MavenSession session, List<MavenProject> projects)
    {
        contextProvider.setContext(ctx);
        sessionProvider.setSession(session);
        projectsProvider.setReactorProjects(projects);
    }

    public void runPreflight(ReleaseContext ctx, List<MavenProject> reactorProjects, MavenSession session) throws JGitFlowException, MavenJGitFlowException
    {
        setupProviders(ctx, session, reactorProjects);

        setupHelper.setupCredentialProviders();

        JGitFlow flow = jGitFlowProvider.gitFlow();

        setupHelper.runCommonSetup();

    }

    @Override
    public JGitFlow flow() throws JGitFlowException {
        return jGitFlowProvider.gitFlow();
    }
}
