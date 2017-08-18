package com.atlassian.maven.plugins.jgitflow.util;

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

import org.apache.commons.lang.StringUtils;

/**
 * @since version
 */
public class NamingUtil
{
    public static String camelCaseOrSpaceToDashed(String s)
    {
//        String dashed = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN,s);
//        String trimmed = StringUtils.replace(StringUtils.replace(StringUtils.replace(dashed, " -", "-"), "- ", "-"), " ", "-");
//
//        return trimmed.toLowerCase();

        String trimmed = s.replaceAll("[\\s]", "");

        String dashed = trimmed.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                "-"
        );

        dashed = dashed.replaceAll("--", "-");

        return dashed.toLowerCase();
    }

    public static String unprefixedBranchName(String prefix, String branchName)
    {
        return StringUtils.substringAfter(branchName, prefix);
    }
    
    public static String afterLastNewline(String str)
    {
        String[] lines = str.split("\\r\\n|\\r|\\n");
        return lines[lines.length - 1];
    }
}
