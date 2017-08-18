package ut.com.atlassian.jgitflow.core.testutils;

/*-
 * #%L
 * JGit-Flow core library
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
import com.atlassian.jgitflow.core.command.JGitFlowCommand;
import com.atlassian.jgitflow.core.exception.JGitFlowExtensionException;
import com.atlassian.jgitflow.core.extension.ExtensionCommand;
import com.atlassian.jgitflow.core.extension.ExtensionFailStrategy;

import org.eclipse.jgit.api.Git;

public class WasCalledExtension implements ExtensionCommand
{
    private boolean methodCalled;
    private boolean withException;
    private ExtensionFailStrategy failStrategy;

    public WasCalledExtension()
    {
        this(false);
    }

    public WasCalledExtension(boolean withException)
    {
        this.methodCalled = false;
        this.withException = withException;
        this.failStrategy = ExtensionFailStrategy.WARN;
    }

    @Override
    public void execute(GitFlowConfiguration configuration, Git git, JGitFlowCommand gitFlowCommand) throws JGitFlowExtensionException
    {
        this.methodCalled = true;
        if (withException)
        {
            throw new JGitFlowExtensionException("Exception!!!");
        }
    }

    @Override
    public ExtensionFailStrategy failStrategy()
    {
        return failStrategy;
    }

    public void setFailStrategy(ExtensionFailStrategy failStrategy)
    {
        this.failStrategy = failStrategy;
    }

    public boolean wasCalled()
    {
        return methodCalled;
    }
}
