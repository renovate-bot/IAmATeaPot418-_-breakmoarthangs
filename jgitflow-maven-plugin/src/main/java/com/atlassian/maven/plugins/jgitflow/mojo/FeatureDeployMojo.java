package com.atlassian.maven.plugins.jgitflow.mojo;

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

import com.atlassian.maven.plugins.jgitflow.ReleaseContext;
import com.atlassian.maven.plugins.jgitflow.exception.MavenJGitFlowException;
import com.atlassian.maven.plugins.jgitflow.manager.FlowReleaseManager;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @since version
 */
@Mojo(name = "feature-deploy", aggregator = true)
public class FeatureDeployMojo extends AbstractJGitFlowMojo
{

    /**
     * Default name of the feature. This option is primarily useful when starting the goal in non-interactive mode.
     */
    @Parameter(property = "featureName", defaultValue = "")
    private String featureName = "";

    /**
     * The space-separated list of goals to run when doing a maven deploy
     */
    @Parameter(property = "goals", defaultValue = "")
    private String goals = "";

    /**
     * The build number to tack on to the deployed version.
     */
    @Parameter(property = "buildNumber", defaultValue = "")
    private String buildNumber = "";

    @Component(hint = "feature")
    FlowReleaseManager releaseManager;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException
    {
        ReleaseContext ctx = new ReleaseContext(getBasedir());
        ctx.setInteractive(getSettings().isInteractiveMode())
           .setNoDeploy(false)
           .setEnableFeatureVersions(true)
           .setAlwaysUpdateOrigin(alwaysUpdateOrigin)
           .setPullMaster(pullMaster)
           .setPullDevelop(pullDevelop)
           .setDefaultOriginUrl(defaultOriginUrl)
           .setAllowRemote(isRemoteAllowed())
           .setEnableSshAgent(enableSshAgent)
           .setUsername(username)
           .setPassword(password)
                .setEol(eol)
           .setUseReleaseProfile(false)
           .setFlowInitContext(getFlowInitContext().getJGitFlowContext());

        try
        {
            releaseManager.deploy(ctx, getReactorProjects(), session, buildNumber, goals);
        }
        catch (MavenJGitFlowException e)
        {
            throw new MojoExecutionException("Error finishing feature: " + e.getMessage(), e);
        }
    }
}
