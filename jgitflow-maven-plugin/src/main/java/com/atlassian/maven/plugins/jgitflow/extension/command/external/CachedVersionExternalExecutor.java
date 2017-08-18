package com.atlassian.maven.plugins.jgitflow.extension.command.external;

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

import com.atlassian.maven.jgitflow.api.exception.MavenJGitFlowExtensionException;
import com.atlassian.maven.plugins.jgitflow.helper.BranchHelper;
import com.atlassian.maven.plugins.jgitflow.provider.ReactorProjectsProvider;
import com.atlassian.maven.plugins.jgitflow.provider.VersionCacheProvider;
import com.atlassian.maven.plugins.jgitflow.provider.VersionProvider;

import org.apache.maven.artifact.ArtifactUtils;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.release.util.ReleaseUtil;
import org.codehaus.plexus.component.annotations.Requirement;

public abstract class CachedVersionExternalExecutor extends ExternalCommandExecutor
{
    @Requirement
    protected VersionCacheProvider versionCacheProvider;

    @Requirement
    protected VersionProvider versionProvider;

    @Requirement
    protected BranchHelper branchHelper;

    @Requirement
    protected ReactorProjectsProvider reactorProjectsProvider;

    @Override
    public String getOldVersion() throws MavenJGitFlowExtensionException
    {
        MavenProject rootProject = ReleaseUtil.getRootProject(reactorProjectsProvider.getReactorProjects());

        return versionCacheProvider.getCachedVersions().get(ArtifactUtils.versionlessKey(rootProject.getGroupId(), rootProject.getArtifactId()));
    }

    @Override
    public String getNewVersion() throws MavenJGitFlowExtensionException
    {
        try
        {
            return versionProvider.getRootVersion(branchHelper.getProjectsForCurrentBranch());
        }
        catch (Exception e)
        {
            throw new MavenJGitFlowExtensionException("Error calculating new version", e);
        }
    }
}
