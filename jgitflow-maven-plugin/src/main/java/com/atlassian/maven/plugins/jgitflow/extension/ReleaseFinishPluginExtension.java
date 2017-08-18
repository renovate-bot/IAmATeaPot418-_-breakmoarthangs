package com.atlassian.maven.plugins.jgitflow.extension;

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

import com.atlassian.jgitflow.core.extension.ReleaseFinishExtension;
import com.atlassian.maven.jgitflow.api.MavenJGitFlowExtension;
import com.atlassian.maven.plugins.jgitflow.extension.command.UpdateDevelopPomsWithMasterVersion;
import com.atlassian.maven.plugins.jgitflow.extension.command.UpdateDevelopWithPreviousVersionsCommand;

import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;

@Component(role = ReleaseFinishPluginExtension.class)
public class ReleaseFinishPluginExtension extends ProductionBranchMergingPluginExtension implements ReleaseFinishExtension
{
    @Requirement
    private UpdateDevelopPomsWithMasterVersion updateDevelopPomsWithMasterVersion;

    @Requirement
    private UpdateDevelopWithPreviousVersionsCommand updateDevelopWithPreviousVersionsCommand;

    @Override
    public void init(MavenJGitFlowExtension externalExtension)
    {
        super.init(externalExtension);

        addBeforeDevelopMergeCommands(updateDevelopPomsWithMasterVersion);
        addAfterDevelopMergeCommands(updateDevelopWithPreviousVersionsCommand);
    }
}
