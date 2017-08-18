package com.atlassian.maven.plugins.jgitflow.helper;

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

import com.atlassian.jgitflow.core.exception.JGitFlowException;
import com.atlassian.maven.plugins.jgitflow.exception.ReactorReloadException;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.release.exec.MavenExecutorException;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * @since version
 */
public interface MavenExecutionHelper
{
    void execute(MavenProject rootProject, MavenSession session) throws MavenExecutorException;

    void execute(MavenProject rootProject, MavenSession session, String goals) throws MavenExecutorException;

    MavenSession reloadReactor(MavenProject rootProject, MavenSession oldSession) throws ReactorReloadException;

    MavenSession getSessionForBranch(String branchName, MavenProject rootProject, MavenSession oldSession) throws JGitFlowException, IOException, GitAPIException, ReactorReloadException;
}
