package com.atlassian.maven.plugins.jgitflow.helper;

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

import java.util.List;
import java.util.Map;

import com.atlassian.maven.plugins.jgitflow.VersionType;
import com.atlassian.maven.plugins.jgitflow.exception.MavenJGitFlowException;
import com.atlassian.maven.plugins.jgitflow.provider.ProjectCacheKey;

import org.apache.maven.project.MavenProject;

public interface PomUpdater
{

    void removeSnapshotFromPomVersions(ProjectCacheKey cacheKey, String versionSuffix, List<MavenProject> reactorProjects) throws MavenJGitFlowException;

    void removeSnapshotFromPomVersionsKeepSuffix(ProjectCacheKey cacheKey, String versionSuffix, List<MavenProject> reactorProjects) throws MavenJGitFlowException;

    void addSnapshotToPomVersions(ProjectCacheKey cacheKey, VersionType versionType, String versionSuffix, List<MavenProject> reactorProjects) throws MavenJGitFlowException;

    void copyPomVersionsFromProject(List<MavenProject> projectsToCopy, List<MavenProject> projectsToUpdate) throws MavenJGitFlowException;

    void copyPomVersionsFromMap(Map<String, String> versionsToCopy, List<MavenProject> projectsToUpdate) throws MavenJGitFlowException;

    void updatePomsWithNextDevelopmentVersion(ProjectCacheKey cacheKey, List<MavenProject> reactorProjects) throws MavenJGitFlowException;

    void addFeatureVersionToSnapshotVersions(ProjectCacheKey cacheKey, String featureVersion, List<MavenProject> reactorProjects) throws MavenJGitFlowException;

    void removeFeatureVersionFromSnapshotVersions(ProjectCacheKey cacheKey, String featureVersion, List<MavenProject> reactorProjects) throws MavenJGitFlowException;

    void removeSnapshotFromFeatureVersions(ProjectCacheKey cacheKey, final String featureVersion, List<MavenProject> reactorProjects) throws MavenJGitFlowException;
}
