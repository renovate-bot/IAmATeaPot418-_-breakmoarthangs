package com.atlassian.jgitflow.core.exception;

/*-
 * #%L
 * JGitFlow :: Core
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

/**
 * Exception thrown when a local branch is behind a remote branch
 */
public class BranchOutOfDateException extends JGitFlowException
{
    public BranchOutOfDateException()
    {
    }

    public BranchOutOfDateException(String message)
    {
        super(message);
    }

    public BranchOutOfDateException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public BranchOutOfDateException(Throwable cause)
    {
        super(cause);
    }
}
