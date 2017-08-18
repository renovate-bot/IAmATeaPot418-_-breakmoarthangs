package com.atlassian.jgitflow.core.extension.impl;

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

import java.util.Arrays;
import java.util.List;

import com.atlassian.jgitflow.core.extension.ExtensionCommand;
import com.atlassian.jgitflow.core.extension.FeatureFinishExtension;

import com.google.common.collect.Iterables;

import static com.google.common.collect.Lists.newArrayList;

public class EmptyFeatureFinishExtension extends EmptyDevelopMergingExtension implements FeatureFinishExtension
{
    private final List<ExtensionCommand> beforeRebase;
    private final List<ExtensionCommand> afterRebase;

    public EmptyFeatureFinishExtension()
    {
        this.beforeRebase = newArrayList();
        this.afterRebase = newArrayList();
    }

    public void addBeforeRebaseCommands(ExtensionCommand... commands)
    {
        beforeRebase.addAll(Arrays.asList(commands));
    }

    public void addAfterRebaseCommands(ExtensionCommand... commands)
    {
        afterRebase.addAll(Arrays.asList(commands));
    }

    @Override
    public Iterable<ExtensionCommand> beforeRebase()
    {
        return Iterables.unmodifiableIterable(beforeRebase);
    }

    @Override
    public Iterable<ExtensionCommand> afterRebase()
    {
        return Iterables.unmodifiableIterable(afterRebase);
    }

}
