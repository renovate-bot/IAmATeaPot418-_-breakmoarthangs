package com.atlassian.jgitflow.core.util;

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

/**
 * A helper class to perform state checks
 */
public class Preconditions
{
    /**
     * Tests an expression to make sure it's true. If not, throws an {IllegalStateException}
     *
     * @param exp The expression to test
     */
    public static void checkState(boolean exp)
    {
        if (!exp)
        {
            throw new IllegalStateException();
        }
    }

    /**
     * Tests an object to ensure it's not null. If it is, throws an {IllegalStateException}
     *
     * @param obj The object to test
     */
    public static void checkNotNull(Object obj)
    {
        if (null == obj)
        {
            throw new IllegalStateException();
        }
    }
}
