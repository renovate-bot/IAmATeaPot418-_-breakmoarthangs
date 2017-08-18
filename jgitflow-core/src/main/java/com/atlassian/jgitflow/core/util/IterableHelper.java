package com.atlassian.jgitflow.core.util;

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

import java.util.ArrayList;
import java.util.List;

/**
 * A helper class for common Iterable operations
 */
public class IterableHelper
{
    /**
     * Returns a {List} of the contents for the given {Iterable}
     *
     * @param iterable The Iterable
     * @param <T>      The type of objects contained in the {Iterable}
     * @return A {List} of the iterable's objects
     */
    public static <T> List<T> asList(Iterable<T> iterable)
    {
        List<T> list = new ArrayList<T>();

        for (T item : iterable)
        {
            list.add(item);
        }

        return list;
    }
}
