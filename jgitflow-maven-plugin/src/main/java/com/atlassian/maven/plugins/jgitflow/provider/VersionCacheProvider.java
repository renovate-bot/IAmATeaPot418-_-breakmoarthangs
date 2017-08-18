package com.atlassian.maven.plugins.jgitflow.provider;

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

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.atlassian.jgitflow.core.exception.JGitFlowException;
import com.atlassian.maven.plugins.jgitflow.exception.ReactorReloadException;
import com.atlassian.maven.plugins.jgitflow.helper.BranchHelper;

import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.eclipse.jgit.api.errors.GitAPIException;

@Component(role = VersionCacheProvider.class)
public class VersionCacheProvider
{
    private static final VersionCacheProvider INSTANCE = new VersionCacheProvider();
    private Map<String, String> cache;

    @Requirement
    BranchHelper branchHelper;

    @Requirement
    VersionProvider versionProvider;

    public Map<String, String> cacheCurrentBranchVersions() throws GitAPIException, JGitFlowException, ReactorReloadException, IOException
    {
        List<MavenProject> projects = branchHelper.getProjectsForCurrentBranch();
        INSTANCE.cache = versionProvider.getOriginalVersions(projects);

        return INSTANCE.cache;
    }

    public Map<String, String> getCachedVersions()
    {
        return INSTANCE.cache;
    }
}
