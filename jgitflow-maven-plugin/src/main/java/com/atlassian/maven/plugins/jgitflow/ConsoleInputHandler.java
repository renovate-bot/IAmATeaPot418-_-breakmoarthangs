package com.atlassian.maven.plugins.jgitflow;

/*-
 * #%L
 * Maven JGitFlow Plugin
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

import java.io.*;

import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.components.interactivity.AbstractInputHandler;
import org.codehaus.plexus.components.interactivity.InputHandler;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Disposable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

import jline.ConsoleReader;
import jline.UnixTerminal;

/**
 * @since version
 */
@Component(role = InputHandler.class, hint = "console")
public class ConsoleInputHandler extends AbstractInputHandler implements Initializable, Disposable
{
    private final Console console = System.console();
    private ConsoleReader jline;

    public ConsoleInputHandler()
    {
        if (null == console)
        {
            try
            {
                this.jline = new ConsoleReader();
            }
            catch (IOException e)
            {
                this.jline = null;
            }
        }
    }

    public void setCygwinTerminal()
    {
        try
        {
            this.jline = new ConsoleReader(new FileInputStream(FileDescriptor.in)
                    , new PrintWriter(
                    new OutputStreamWriter(System.out,
                            System.getProperty("jline.WindowsTerminal.output.encoding", System.getProperty("file.encoding")))
            )
                    , null
                    , new UnixTerminal());
        }
        catch (IOException e)
        {
            this.jline = null;
        }
    }

    @Override
    public void dispose()
    {
        if (noConsole())
        {
            return;
        }

        if (null != console)
        {
            try
            {
                console.reader().close();
            }
            catch (IOException e)
            {
                getLogger().error("Error closing input stream must be ignored", e);
            }
        }
    }

    @Override
    public void initialize() throws InitializationException
    {
        //do nothing
    }

    @Override
    public String readLine() throws IOException
    {
        if (null != console)
        {
            return console.readLine();
        }

        if (null != jline)
        {
            return jline.readLine();
        }

        return "";
    }

    @Override
    public String readPassword() throws IOException
    {
        if (null != console)
        {
            return new String(console.readPassword());
        }

        if (null != jline)
        {
            return jline.readLine(new Character('*'));
        }

        return "";
    }

    private boolean noConsole()
    {
        return (null == console && null == jline);
    }
}
