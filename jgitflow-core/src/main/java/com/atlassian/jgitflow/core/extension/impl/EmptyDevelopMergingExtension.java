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

import com.atlassian.jgitflow.core.extension.DevelopMergingExtension;
import com.atlassian.jgitflow.core.extension.ExtensionCommand;

import com.google.common.collect.Iterables;

import static com.google.common.collect.Lists.newArrayList;

public abstract class EmptyDevelopMergingExtension extends EmptyJGitFlowExtension implements DevelopMergingExtension
{
    private final List<ExtensionCommand> beforeDevelopCheckout;
    private final List<ExtensionCommand> afterDevelopCheckout;
    private final List<ExtensionCommand> beforeDevelopMerge;
    private final List<ExtensionCommand> afterDevelopMerge;
    private final List<ExtensionCommand> afterTopicCheckout;
    private final List<ExtensionCommand> beforeTag;
    private final List<ExtensionCommand> afterTag;

    protected EmptyDevelopMergingExtension()
    {
        this.beforeDevelopCheckout = newArrayList();
        this.afterDevelopCheckout = newArrayList();
        this.beforeDevelopMerge = newArrayList();
        this.afterDevelopMerge = newArrayList();
        this.afterTopicCheckout = newArrayList();
        this.beforeTag = newArrayList();
        this.afterTag = newArrayList();
    }

    public void addBeforeDevelopCheckoutCommands(ExtensionCommand... commands)
    {
        beforeDevelopCheckout.addAll(Arrays.asList(commands));
    }

    public void addAfterDevelopCheckoutCommands(ExtensionCommand... commands)
    {
        afterDevelopCheckout.addAll(Arrays.asList(commands));
    }

    public void addBeforeDevelopMergeCommands(ExtensionCommand... commands)
    {
        beforeDevelopMerge.addAll(Arrays.asList(commands));
    }

    public void addAfterDevelopMergeCommands(ExtensionCommand... commands)
    {
        afterDevelopMerge.addAll(Arrays.asList(commands));
    }

    public void addAfterTopicCheckoutCommands(ExtensionCommand... commands)
    {
        afterTopicCheckout.addAll(Arrays.asList(commands));
    }

    public void addBeforeTagCommands(ExtensionCommand... commands)
    {
        beforeTag.addAll(Arrays.asList(commands));
    }

    public void addAfterTagCommands(ExtensionCommand... commands)
    {
        afterTag.addAll(Arrays.asList(commands));
    }

    @Override
    public Iterable<ExtensionCommand> beforeDevelopCheckout()
    {
        return Iterables.unmodifiableIterable(beforeDevelopCheckout);
    }

    @Override
    public Iterable<ExtensionCommand> afterDevelopCheckout()
    {
        return Iterables.unmodifiableIterable(afterDevelopCheckout);
    }

    @Override
    public Iterable<ExtensionCommand> beforeDevelopMerge()
    {
        return Iterables.unmodifiableIterable(beforeDevelopMerge);
    }

    @Override
    public Iterable<ExtensionCommand> afterDevelopMerge()
    {
        return Iterables.unmodifiableIterable(afterDevelopMerge);
    }

    @Override
    public Iterable<ExtensionCommand> beforeTag()
    {
        return Iterables.unmodifiableIterable(beforeTag);
    }

    @Override
    public Iterable<ExtensionCommand> afterTag()
    {
        return Iterables.unmodifiableIterable(afterTag);
    }

    @Override
    public Iterable<ExtensionCommand> afterTopicCheckout()
    {
        return Iterables.unmodifiableIterable(afterTopicCheckout);
    }
}
