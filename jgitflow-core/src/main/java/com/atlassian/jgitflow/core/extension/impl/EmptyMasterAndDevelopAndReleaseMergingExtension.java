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

import com.atlassian.jgitflow.core.extension.ExtensionCommand;
import com.atlassian.jgitflow.core.extension.ReleaseMergingExtension;

import com.google.common.collect.Iterables;

import static com.google.common.collect.Lists.newArrayList;

public class EmptyMasterAndDevelopAndReleaseMergingExtension extends EmptyMasterAndDevelopMergingExtension implements ReleaseMergingExtension
{

    private final List<ExtensionCommand> beforeReleaseCheckout;
    private final List<ExtensionCommand> afterReleaseCheckout;
    private final List<ExtensionCommand> beforeReleaseMerge;
    private final List<ExtensionCommand> afterReleaseMerge;


    protected EmptyMasterAndDevelopAndReleaseMergingExtension()
    {
        this.beforeReleaseCheckout = newArrayList();
        this.afterReleaseCheckout = newArrayList();
        this.beforeReleaseMerge = newArrayList();
        this.afterReleaseMerge = newArrayList();
    }

    public void addBeforeReleaseCheckoutCommands(ExtensionCommand... commands)
    {
        beforeReleaseCheckout.addAll(Arrays.asList(commands));
    }

    public void addAfterReleaseCheckoutCommands(ExtensionCommand... commands)
    {
        afterReleaseCheckout.addAll(Arrays.asList(commands));
    }

    public void addBeforeReleaseMergeCommands(ExtensionCommand... commands)
    {
        beforeReleaseMerge.addAll(Arrays.asList(commands));
    }

    public void addAfterReleaseMergeCommands(ExtensionCommand... commands)
    {
        afterReleaseMerge.addAll(Arrays.asList(commands));
    }

    @Override
    public Iterable<ExtensionCommand> beforeReleaseCheckout()
    {
        return Iterables.unmodifiableIterable(beforeReleaseCheckout);
    }

    @Override
    public Iterable<ExtensionCommand> afterReleaseCheckout()
    {
        return Iterables.unmodifiableIterable(afterReleaseCheckout);
    }

    @Override
    public Iterable<ExtensionCommand> beforeReleaseMerge()
    {
        return Iterables.unmodifiableIterable(beforeReleaseMerge);
    }

    @Override
    public Iterable<ExtensionCommand> afterReleaseMerge()
    {
        return Iterables.unmodifiableIterable(afterReleaseMerge);
    }
}
