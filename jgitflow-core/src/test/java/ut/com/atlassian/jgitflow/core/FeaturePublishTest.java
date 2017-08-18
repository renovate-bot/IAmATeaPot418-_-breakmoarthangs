package ut.com.atlassian.jgitflow.core;

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

import com.atlassian.jgitflow.core.JGitFlow;
import com.atlassian.jgitflow.core.JGitFlowInitCommand;
import com.atlassian.jgitflow.core.exception.DirtyWorkingTreeException;
import com.atlassian.jgitflow.core.exception.LocalBranchMissingException;
import com.atlassian.jgitflow.core.exception.RemoteBranchExistsException;
import com.atlassian.jgitflow.core.util.GitHelper;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

import ut.com.atlassian.jgitflow.core.testutils.RepoUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @since version
 */
public class FeaturePublishTest extends BaseGitFlowTest
{
    @Test
    public void simplePublish() throws Exception
    {
        Git git = null;
        Git remoteGit = null;
        remoteGit = RepoUtil.createRepositoryWithMasterAndDevelop(newDir());

        git = Git.cloneRepository().setDirectory(newDir()).setURI("file://" + remoteGit.getRepository().getWorkTree().getPath()).call();

        JGitFlowInitCommand initCommand = new JGitFlowInitCommand();
        JGitFlow flow = initCommand.setDirectory(git.getRepository().getWorkTree()).call();

        flow.featureStart("my-feature").call();

        //create a new commit
        File junkFile = new File(git.getRepository().getWorkTree(), "junk.txt");
        FileUtils.writeStringToFile(junkFile, "I am junk");
        git.add().addFilepattern(junkFile.getName()).call();
        RevCommit commit = git.commit().setMessage("committing junk file").call();

        flow.featurePublish("my-feature").call();

        assertTrue(GitHelper.isMergedInto(remoteGit, commit, flow.getFeatureBranchPrefix() + "my-feature"));
        remoteGit.checkout().setName(flow.getFeatureBranchPrefix() + "my-feature").call();

        File remoteJunk = new File(remoteGit.getRepository().getWorkTree(), junkFile.getName());
        assertTrue(remoteJunk.exists());

        assertEquals(flow.getFeatureBranchPrefix() + "my-feature", git.getRepository().getBranch());

    }

    @Test(expected = DirtyWorkingTreeException.class)
    public void publishFeatureWithUnStagedFile() throws Exception
    {
        Git git = null;
        Git remoteGit = null;
        remoteGit = RepoUtil.createRepositoryWithMasterAndDevelop(newDir());

        git = Git.cloneRepository().setDirectory(newDir()).setURI("file://" + remoteGit.getRepository().getWorkTree().getPath()).call();

        JGitFlowInitCommand initCommand = new JGitFlowInitCommand();
        JGitFlow flow = initCommand.setDirectory(git.getRepository().getWorkTree()).call();

        flow.featureStart("my-feature").call();

        //create a new file
        File junkFile = new File(git.getRepository().getWorkTree(), "junk.txt");
        FileUtils.writeStringToFile(junkFile, "I am junk");

        flow.featurePublish("my-feature").call();
    }

    @Test(expected = DirtyWorkingTreeException.class)
    public void publishFeatureUnCommittedFile() throws Exception
    {
        Git git = null;
        Git remoteGit = null;
        remoteGit = RepoUtil.createRepositoryWithMasterAndDevelop(newDir());

        git = Git.cloneRepository().setDirectory(newDir()).setURI("file://" + remoteGit.getRepository().getWorkTree().getPath()).call();

        JGitFlowInitCommand initCommand = new JGitFlowInitCommand();
        JGitFlow flow = initCommand.setDirectory(git.getRepository().getWorkTree()).call();

        flow.featureStart("my-feature").call();

        //create a new commit
        File junkFile = new File(git.getRepository().getWorkTree(), "junk.txt");
        FileUtils.writeStringToFile(junkFile, "I am junk");
        git.add().addFilepattern(junkFile.getName()).call();

        flow.featurePublish("my-feature").call();
    }

    @Test(expected = LocalBranchMissingException.class)
    public void publishFeatureWithMissingLocalBranch() throws Exception
    {
        Git git = null;
        Git remoteGit = null;
        remoteGit = RepoUtil.createRepositoryWithMasterAndDevelop(newDir());

        git = Git.cloneRepository().setDirectory(newDir()).setURI("file://" + remoteGit.getRepository().getWorkTree().getPath()).call();

        JGitFlowInitCommand initCommand = new JGitFlowInitCommand();
        JGitFlow flow = initCommand.setDirectory(git.getRepository().getWorkTree()).call();

        //create a new commit
        File junkFile = new File(git.getRepository().getWorkTree(), "junk.txt");
        FileUtils.writeStringToFile(junkFile, "I am junk");
        git.add().addFilepattern(junkFile.getName()).call();
        git.commit().setMessage("committing junk file").call();

        flow.featurePublish("my-feature").call();
    }

    @Test(expected = RemoteBranchExistsException.class)
    public void publishFeatureWithExistingRemoteBranch() throws Exception
    {
        Git git = null;
        Git remoteGit = null;
        remoteGit = RepoUtil.createRepositoryWithMasterAndDevelop(newDir());

        git = Git.cloneRepository().setDirectory(newDir()).setURI("file://" + remoteGit.getRepository().getWorkTree().getPath()).call();

        JGitFlowInitCommand initCommand = new JGitFlowInitCommand();
        JGitFlow flow = initCommand.setDirectory(git.getRepository().getWorkTree()).call();

        flow.featureStart("my-feature").call();

        //create a new commit
        File junkFile = new File(git.getRepository().getWorkTree(), "junk.txt");
        FileUtils.writeStringToFile(junkFile, "I am junk");
        flow.git().add().addFilepattern(junkFile.getName()).call();
        flow.git().commit().setMessage("committing junk file").call();

        //manually add the feature branch to remote
        remoteGit.branchCreate().setName(flow.getFeatureBranchPrefix() + "my-feature").call();

        flow.featurePublish("my-feature").call();
    }
}
