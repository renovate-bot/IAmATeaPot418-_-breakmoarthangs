package com.atlassian.maven.plugins.jgitflow;

/*-
 * #%L
 * JGitFlow :: Maven Plugin
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

import java.io.IOException;

import org.codehaus.plexus.components.interactivity.OutputHandler;

/**
 * @since version
 */
public class TestOutputHandler implements OutputHandler
{
    private final StringBuilder sb;

    public TestOutputHandler()
    {
        this.sb = new StringBuilder();
    }

    @Override
    public void write(String line) throws IOException
    {
        sb.append(line);
    }

    @Override
    public void writeLine(String line) throws IOException
    {
        sb.append(line).append("\n");
    }

    public String getValue()
    {
        return sb.toString();
    }

    public void clear()
    {
        sb.setLength(0);
    }
}
