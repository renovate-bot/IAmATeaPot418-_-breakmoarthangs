package ut.com.atlassian.maven.plugins.jgitflow;

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

import com.atlassian.maven.plugins.jgitflow.util.NamingUtil;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @since version
 */
public class NamingUtilTest
{
    @Test
    public void lowerCamel() throws Exception
    {
        String expected = "some-feature";
        String test = "someFeature";

        assertEquals(expected, NamingUtil.camelCaseOrSpaceToDashed(test));

    }

    @Test
    public void upperCamel() throws Exception
    {
        String expected = "some-feature";
        String test = "SomeFeature";

        assertEquals(expected, NamingUtil.camelCaseOrSpaceToDashed(test));

    }

    @Test
    public void upperSpaced() throws Exception
    {
        String expected = "some-feature";
        String test = "Some Feature";

        assertEquals(expected, NamingUtil.camelCaseOrSpaceToDashed(test));

    }

    @Test
    public void lowerSpaced() throws Exception
    {
        String expected = "some-feature";
        String test = "some Feature";

        assertEquals(expected, NamingUtil.camelCaseOrSpaceToDashed(test));

    }

    @Test
    public void acronymCamel() throws Exception
    {
        String expected = "some-feature";
        String test = "SOMEFeature";

        assertEquals(expected, NamingUtil.camelCaseOrSpaceToDashed(test));

    }

    @Test
    public void issueCamel() throws Exception
    {
        String expected = "acdev-1286-some-feature";
        String test = "ACDEV-1286-some-feature";

        assertEquals(expected, NamingUtil.camelCaseOrSpaceToDashed(test));

    }

    @Test
    public void oneEOLNix() throws Exception
    {
        String expected = "bbb";
        String test = "aaa\nbbb";

        assertEquals(expected, NamingUtil.afterLastNewline(test));

    }

    @Test
    public void twoEOLNix() throws Exception
    {
        String expected = "bbb";
        String test = "aaa\n\nbbb";

        assertEquals(expected, NamingUtil.afterLastNewline(test));

    }

    @Test
    public void scatteredEOLNix() throws Exception
    {
        String expected = "bbb";
        String test = "aaa\naaaa\nbbb";

        assertEquals(expected, NamingUtil.afterLastNewline(test));

    }

    @Test
    public void oneEOLWin() throws Exception
    {
        String expected = "bbb";
        String test = "aaa\n\rbbb";

        assertEquals(expected, NamingUtil.afterLastNewline(test));

    }

    @Test
    public void twoEOLWin() throws Exception
    {
        String expected = "bbb";
        String test = "aaa\n\r\n\rbbb";

        assertEquals(expected, NamingUtil.afterLastNewline(test));

    }

    @Test
    public void scatteredEOLWin() throws Exception
    {
        String expected = "bbb";
        String test = "aaa\n\raaaa\n\rbbb";

        assertEquals(expected, NamingUtil.afterLastNewline(test));

    }

    @Test
    public void oneEOLMac() throws Exception
    {
        String expected = "bbb";
        String test = "aaa\rbbb";

        assertEquals(expected, NamingUtil.afterLastNewline(test));

    }

    @Test
    public void twoEOLMac() throws Exception
    {
        String expected = "bbb";
        String test = "aaa\r\rbbb";

        assertEquals(expected, NamingUtil.afterLastNewline(test));

    }

    @Test
    public void scatteredEOLMac() throws Exception
    {
        String expected = "bbb";
        String test = "aaa\raaaa\rbbb";

        assertEquals(expected, NamingUtil.afterLastNewline(test));

    }
}
