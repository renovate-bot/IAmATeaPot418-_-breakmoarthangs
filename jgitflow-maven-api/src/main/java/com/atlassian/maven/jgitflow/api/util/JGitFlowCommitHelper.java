package com.atlassian.maven.jgitflow.api.util;

/*-
 * #%L
 * JGitFlow :: Maven API
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

import com.atlassian.jgitflow.core.JGitFlowInfo;
import com.atlassian.maven.jgitflow.api.exception.MavenJGitFlowExtensionException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * A helper class to make committing all changes from extensions easy peasy
 */
public class JGitFlowCommitHelper
{
    /**
     * Commits ALL changes to the current branch
     *
     * @param flow          the JGitFlow instance to use
     * @param commitMessage The message for the commit
     * @throws com.atlassian.maven.jgitflow.api.exception.MavenJGitFlowExtensionException
     */
    public static void commitAllChanges(JGitFlowInfo flow, String commitMessage) throws MavenJGitFlowExtensionException
    {
        try
        {
            Git git = flow.git();

            Status status = git.status().call();
            if (!status.isClean())
            {
                git.add().addFilepattern(".").call();
                git.commit().setMessage(commitMessage).call();
            }
        }
        catch (GitAPIException e)
        {
            throw new MavenJGitFlowExtensionException("error committing changes: " + e.getMessage(), e);
        }
    }
}
