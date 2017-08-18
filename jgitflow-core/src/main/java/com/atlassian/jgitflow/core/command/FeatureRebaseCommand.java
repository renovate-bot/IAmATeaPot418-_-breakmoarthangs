package com.atlassian.jgitflow.core.command;

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
import com.atlassian.jgitflow.core.JGitFlowConstants;
import com.atlassian.jgitflow.core.exception.*;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * Performs a rebase of the feature branch
 * <p></p>
 * Examples ({@code flow} is a {@link com.atlassian.jgitflow.core.JGitFlow} instance):
 * <p></p>
 * Rebase a feature:
 * <p></p>
 * <pre>
 * flow.featureRebase(&quot;feature&quot;).call();
 * </pre>
 */
public class FeatureRebaseCommand extends AbstractGitFlowCommand<FeatureRebaseCommand, Void>
{
    private static final String SHORT_NAME = "feature-rebase";

    /**
     * Create a new feature rebase command instance.
     * <p></p>
     * This command is usually run as part of a release finish by calling {@link FeatureFinishCommand#setRebase(boolean)}
     *
     * @param git      The git instance to use
     * @param gfConfig The GitFlowConfiguration to use
     */
    public FeatureRebaseCommand(String branchName, Git git, GitFlowConfiguration gfConfig)
    {
        super(branchName, git, gfConfig);
    }

    /**
     * @return nothing
     * @throws com.atlassian.jgitflow.core.exception.NotInitializedException
     * @throws com.atlassian.jgitflow.core.exception.JGitFlowGitAPIException
     * @throws com.atlassian.jgitflow.core.exception.DirtyWorkingTreeException
     * @throws com.atlassian.jgitflow.core.exception.JGitFlowIOException
     * @throws com.atlassian.jgitflow.core.exception.LocalBranchMissingException
     */
    @Override
    public Void call() throws NotInitializedException, JGitFlowGitAPIException, DirtyWorkingTreeException, JGitFlowIOException, LocalBranchMissingException
    {
        String prefixedBranchName = gfConfig.getPrefixValue(JGitFlowConstants.PREFIXES.FEATURE.configKey()) + getBranchName();
        enforcer().requireGitFlowInitialized();
        enforcer().requireCleanWorkingTree(isAllowUntracked());
        enforcer().requireLocalBranchExists(prefixedBranchName);

        try
        {
            git.checkout().setName(prefixedBranchName).call();
            git.rebase().setUpstream(gfConfig.getDevelop()).call();
        }
        catch (GitAPIException e)
        {
            throw new JGitFlowGitAPIException(e);
        }

        return null;
    }

    @Override
    protected String getCommandName()
    {
        return SHORT_NAME;
    }
}
