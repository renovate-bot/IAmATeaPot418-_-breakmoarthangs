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

import com.atlassian.jgitflow.core.extension.ExtensionCommand;

public class MergeProcessExtensionWrapper
{
    private final Iterable<ExtensionCommand> beforeCheckout;
    private final Iterable<ExtensionCommand> afterCheckout;
    private final Iterable<ExtensionCommand> beforeMerge;
    private final Iterable<ExtensionCommand> afterMerge;

    public MergeProcessExtensionWrapper(Iterable<ExtensionCommand> beforeCheckout, Iterable<ExtensionCommand> afterCheckout, Iterable<ExtensionCommand> beforeMerge, Iterable<ExtensionCommand> afterMerge)
    {
        this.beforeCheckout = beforeCheckout;
        this.afterCheckout = afterCheckout;
        this.beforeMerge = beforeMerge;
        this.afterMerge = afterMerge;
    }

    public Iterable<ExtensionCommand> beforeCheckout() {return this.beforeCheckout;}

    public Iterable<ExtensionCommand> afterCheckout() {return this.afterCheckout;}

    public Iterable<ExtensionCommand> beforeMerge() {return this.beforeMerge;}

    public Iterable<ExtensionCommand> afterMerge() {return this.afterMerge;}
}
