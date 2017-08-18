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
import java.util.ArrayList;
import java.util.List;

import org.codehaus.plexus.components.interactivity.InputHandler;

/**
 * @since version
 */
public class TestInputHandler extends ConsoleInputHandler implements InputHandler
{
    private final StringBuilder sb;

    public TestInputHandler()
    {
        this.sb = new StringBuilder();
    }

    @Override
    public String readLine() throws IOException
    {
        String val = sb.toString();
        sb.setLength(0);

        return val;
    }

    @Override
    public String readPassword() throws IOException
    {
        return readLine();
    }

    @Override
    public List readMultipleLines() throws IOException
    {
        return new ArrayList();
    }

    public void setResponse(String response)
    {
        sb.setLength(0);
        sb.append(response);
    }
}
