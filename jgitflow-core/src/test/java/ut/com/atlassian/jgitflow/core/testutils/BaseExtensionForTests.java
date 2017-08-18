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

import java.util.HashMap;
import java.util.Map;

import com.atlassian.jgitflow.core.extension.ExtensionCommand;
import com.atlassian.jgitflow.core.extension.ExtensionFailStrategy;

import com.google.common.collect.Lists;

public abstract class BaseExtensionForTests<T>
{
    public static final String BEFORE_FETCH = "beforeFetch";
    public static final String AFTER_FETCH = "afterFetch";
    public static final String BEFORE = "before";
    public static final String AFTER = "after";
    public static final String BEFORE_CREATE_BRANCH = "beforeCreateBranch";
    public static final String AFTER_CREATE_BRANCH = "afterCreateBranch";
    public static final String AFTER_PUSH = "afterPush";
    public static final String BEFORE_REBASE = "beforeRebase";
    public static final String AFTER_REBASE = "afterRebase";
    public static final String BEFORE_DEVELOP_CHECKOUT = "beforeDevelopCheckout";
    public static final String AFTER_DEVELOP_CHECKOUT = "afterDevelopCheckout";
    public static final String BEFORE_DEVELOP_MERGE = "beforeDevelopMerge";
    public static final String AFTER_DEVELOP_MERGE = "afterDevelopMerge";
    public static final String BEFORE_MASTER_CHECKOUT = "beforeMasterCheckout";
    public static final String AFTER_MASTER_CHECKOUT = "afterMasterCheckout";
    public static final String BEFORE_MASTER_MERGE = "beforeMasterMerge";
    public static final String AFTER_MASTER_MERGE = "afterMasterMerge";
    public static final String BEFORE_RELEASE_CHECKOUT = "beforeReleaseCheckout";
    public static final String AFTER_RELEASE_CHECKOUT = "afterReleaseCheckout";
    public static final String BEFORE_RELEASE_MERGE = "beforeReleaseMerge";
    public static final String AFTER_RELEASE_MERGE = "afterReleaseMerge";
    public static final String AFTER_TOPIC_CHECKOUT = "afterTopicCheckout";
    public static final String BEFORE_TAG = "beforeTag";
    public static final String AFTER_TAG = "afterTag";

    private final Map<String, WasCalledExtension> methodMap;

    protected BaseExtensionForTests()
    {
        this.methodMap = new HashMap<String, WasCalledExtension>();
    }

    public T withException(String methodName, ExtensionFailStrategy failStrategy)
    {
        createExtensionWithException(methodName, failStrategy);
        return (T) this;
    }

    public Iterable<ExtensionCommand> beforeFetch()
    {
        return Lists.<ExtensionCommand>newArrayList(createExtension(BEFORE_FETCH));
    }

    public Iterable<ExtensionCommand> afterFetch()
    {
        return Lists.<ExtensionCommand>newArrayList(createExtension(AFTER_FETCH));
    }

    public Iterable<ExtensionCommand> before()
    {
        return Lists.<ExtensionCommand>newArrayList(createExtension(BEFORE));
    }

    public Iterable<ExtensionCommand> after()
    {
        return Lists.<ExtensionCommand>newArrayList(createExtension(AFTER));
    }

    public boolean wasCalled(String methodName)
    {
        if (methodMap.containsKey(methodName))
        {
            return methodMap.get(methodName).wasCalled();
        }

        return false;
    }

    protected void createExtensionWithException(String methodName, ExtensionFailStrategy failStrategy)
    {
        WasCalledExtension extension = new WasCalledExtension(true);
        extension.setFailStrategy(failStrategy);

        methodMap.put(methodName, extension);
    }

    protected ExtensionCommand createExtension(String methodName)
    {
        if (methodMap.containsKey(methodName))
        {
            return methodMap.get(methodName);
        }

        WasCalledExtension extension = new WasCalledExtension();
        methodMap.put(methodName, extension);

        return extension;
    }
}
