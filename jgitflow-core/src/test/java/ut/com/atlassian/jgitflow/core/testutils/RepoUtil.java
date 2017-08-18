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

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * @since version
 */
public class RepoUtil
{
    public static Git createEmptyRepository(File dir) throws GitAPIException
    {
        Git git = Git.init().setDirectory(dir).setBare(true).call();
        git.commit().setMessage("initial commit").call();

        return git;
    }

    public static Git createRepositoryWithMaster(File dir) throws GitAPIException
    {
        Git git = Git.init().setDirectory(dir).call();
        git.commit().setMessage("initial commit").call();

        return git;
    }

    public static Git createRepositoryWithMasterAndDevelop(File dir) throws GitAPIException
    {
        Git git = Git.init().setDirectory(dir).call();
        git.commit().setMessage("initial commit").call();
        git.branchCreate().setName("develop").call();
        git.commit().setMessage("added develop branch").call();

        return git;
    }

    public static Git createRepositoryWithBranches(File dir, String... branches) throws GitAPIException
    {
        Git git = Git.init().setDirectory(dir).call();
        git.commit().setMessage("initial commit").call();

        for (String branch : branches)
        {
            git.branchCreate().setName(branch).call();
            git.commit().setMessage("added branch " + branch).call();
        }

        return git;
    }
}
