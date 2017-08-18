package com.atlassian.maven.jgitflow.api.impl;

/*-
 * #%L
 * Maven JGitFlow API
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

import com.atlassian.jgitflow.core.JGitFlowInfo;
import com.atlassian.maven.jgitflow.api.MavenReleaseFinishExtension;
import com.atlassian.maven.jgitflow.api.exception.MavenJGitFlowExtensionException;

public class NoopMavenReleaseFinishExtension implements MavenReleaseFinishExtension
{
    @Override
    public void onMasterBranchVersionChange(String newVersion, String oldVersion, JGitFlowInfo flow) throws MavenJGitFlowExtensionException
    {

    }

    @Override
    public void onTopicBranchVersionChange(String newVersion, String oldVersion, JGitFlowInfo flow) throws MavenJGitFlowExtensionException
    {

    }
}
