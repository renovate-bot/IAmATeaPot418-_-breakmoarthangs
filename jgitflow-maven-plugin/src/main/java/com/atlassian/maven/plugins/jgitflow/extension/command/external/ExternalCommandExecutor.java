package com.atlassian.maven.plugins.jgitflow.extension.command.external;

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

import com.atlassian.jgitflow.core.GitFlowConfiguration;
import com.atlassian.jgitflow.core.JGitFlow;
import com.atlassian.jgitflow.core.JGitFlowInfo;
import com.atlassian.jgitflow.core.command.JGitFlowCommand;
import com.atlassian.jgitflow.core.exception.JGitFlowExtensionException;
import com.atlassian.jgitflow.core.extension.ExtensionCommand;
import com.atlassian.jgitflow.core.extension.ExtensionFailStrategy;
import com.atlassian.maven.jgitflow.api.MavenJGitFlowExtension;
import com.atlassian.maven.plugins.jgitflow.extension.ExternalInitializingExtension;
import com.atlassian.maven.plugins.jgitflow.provider.JGitFlowProvider;

import org.codehaus.plexus.component.annotations.Requirement;
import org.eclipse.jgit.api.Git;

public abstract class ExternalCommandExecutor implements ExtensionCommand, ExternalInitializingExtension, ExternalCommand
{
    @Requirement
    private JGitFlowProvider jGitFlowProvider;

    private MavenJGitFlowExtension externalExtension;

    @Override
    public void execute(GitFlowConfiguration configuration, Git git, JGitFlowCommand gitFlowCommand) throws JGitFlowExtensionException
    {
        if (null == externalExtension)
        {
            return;
        }

        try
        {
            JGitFlow flow = jGitFlowProvider.gitFlow();

            execute(externalExtension, getNewVersion(), getOldVersion(), new JGitFlowInfo(flow.git(), configuration));
        }
        catch (Exception e)
        {
            throw new JGitFlowExtensionException("Error running external extension for branch change", e);
        }
    }

    @Override
    public void init(MavenJGitFlowExtension externalExtension)
    {
        this.externalExtension = externalExtension;
    }


    @Override
    public ExtensionFailStrategy failStrategy()
    {
        return ExtensionFailStrategy.ERROR;
    }
}
