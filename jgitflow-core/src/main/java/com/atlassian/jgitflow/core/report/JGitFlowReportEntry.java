package com.atlassian.jgitflow.core.report;

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
 * @since version
 */
public class JGitFlowReportEntry
{
    private final String id;
    private final String entry;
    private final boolean debug;
    private final boolean error;

    public JGitFlowReportEntry(String id, String entry, boolean debug, boolean error)
    {
        this.id = id;
        this.entry = entry;
        this.debug = debug;
        this.error = error;
    }

    public String getId()
    {
        return id;
    }

    public String getEntry()
    {
        return entry;
    }

    public boolean isDebug()
    {
        return debug;
    }

    public boolean isError()
    {
        return error;
    }

    @Override
    public String toString()
    {
        return id + ": " + getEntry();
    }
}
