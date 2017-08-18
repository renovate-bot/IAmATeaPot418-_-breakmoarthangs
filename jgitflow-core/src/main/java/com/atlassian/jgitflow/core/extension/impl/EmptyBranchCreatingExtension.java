package com.atlassian.jgitflow.core.extension.impl;

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

import java.util.Arrays;
import java.util.List;

import com.atlassian.jgitflow.core.extension.BranchCreatingExtension;
import com.atlassian.jgitflow.core.extension.ExtensionCommand;

import com.google.common.collect.Iterables;

import static com.google.common.collect.Lists.newArrayList;

public abstract class EmptyBranchCreatingExtension extends EmptyJGitFlowExtension implements BranchCreatingExtension
{
    private final List<ExtensionCommand> beforeCreateBranch;
    private final List<ExtensionCommand> afterCreateBranch;

    protected EmptyBranchCreatingExtension()
    {
        this.beforeCreateBranch = newArrayList();
        this.afterCreateBranch = newArrayList();
    }

    public void addBeforeCreateBranchCommands(ExtensionCommand... commands)
    {
        beforeCreateBranch.addAll(Arrays.asList(commands));
    }

    public void addAfterCreateBranchCommands(ExtensionCommand... commands)
    {
        afterCreateBranch.addAll(Arrays.asList(commands));
    }

    @Override
    public Iterable<ExtensionCommand> beforeCreateBranch()
    {
        return Iterables.unmodifiableIterable(beforeCreateBranch);
    }

    @Override
    public Iterable<ExtensionCommand> afterCreateBranch()
    {
        return Iterables.unmodifiableIterable(afterCreateBranch);
    }
}
