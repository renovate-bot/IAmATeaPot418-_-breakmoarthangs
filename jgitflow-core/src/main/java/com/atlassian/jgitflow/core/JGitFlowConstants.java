package com.atlassian.jgitflow.core;

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

import org.eclipse.jgit.lib.Constants;

/**
 * Constants used by JGitFlow
 */
public final class JGitFlowConstants
{
    public static final String SECTION = "gitflow";
    public static final String PREFIX_SUB = "prefix";
    public static final String DEVELOP_KEY = "develop";
    public static final String GITFLOW_DIR = ".gitflow";
    public static final String MERGE_BASE = "MERGE_BASE";
    public static final String R_REMOTE_ORIGIN = Constants.R_REMOTES + Constants.DEFAULT_REMOTE_NAME + "/";

    public static enum PREFIXES
    {
        FEATURE, RELEASE, HOTFIX, SUPPORT, VERSIONTAG;

        public String configKey()
        {
            return name().toLowerCase();
        }
    }

}
