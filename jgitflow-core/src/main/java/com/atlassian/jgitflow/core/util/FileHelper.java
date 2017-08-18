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

import java.io.*;

/**
 * A helper utility to make dealing with file operations easier
 */
public class FileHelper
{
    /**
     * Reads the first line of a file
     *
     * @param f the file to read
     * @return the first line of the file or an empty string
     */
    public static String readFirstLine(File f)
    {
        String line = "";
        BufferedReader br = null;
        try
        {
            if (null != f && f.exists() && f.canRead())
            {
                br = new BufferedReader(new FileReader(f));
                line = br.readLine();
            }
        }
        catch (FileNotFoundException e)
        {
            //ignore
        }
        catch (IOException e)
        {
            //ignore
        }
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    //ignore
                }
            }
        }

        return line;
    }

    /**
     * Creates any non-existent parent directories for the given file
     *
     * @param f the file to create parent directories for
     * @throws java.io.IOException
     */
    public static void createParentDirs(File f) throws IOException
    {
        File parent = f.getCanonicalFile().getParentFile();
        if (null != parent)
        {
            parent.mkdirs();
        }
    }

    /**
     * Writes a string to a file replacing the entire contents
     *
     * @param s The string to write
     * @param f The file to write to
     * @throws java.io.IOException
     */
    public static void writeStringToFile(String s, File f) throws IOException
    {
        BufferedWriter bw = null;
        try
        {
            bw = new BufferedWriter(new FileWriter(f));
            bw.write(s);
        }
        finally
        {
            if (null != bw)
            {
                bw.close();
            }
        }
    }

}
