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

import com.atlassian.jgitflow.core.extension.impl.EmptyMasterAndDevelopAndReleaseMergingExtension;
import com.atlassian.maven.jgitflow.api.MavenJGitFlowExtension;
import com.atlassian.maven.plugins.jgitflow.extension.command.*;
import com.atlassian.maven.plugins.jgitflow.extension.command.external.FinishProductionExternalExecutor;

import org.codehaus.plexus.component.annotations.Requirement;

public abstract class ProductionBranchMergingPluginExtension extends EmptyMasterAndDevelopAndReleaseMergingExtension implements ExternalInitializingExtension
{
    @Requirement
    private UpdatePomsWithNonSnapshotCommand updatePomsWithNonSnapshotCommand;

    @Requirement
    private VerifyReleaseVersionStateAndDepsCommand verifyReleaseVersionStateAndDepsCommand;

    @Requirement
    private MavenBuildCommand mavenBuildCommand;

    @Requirement
    private TagMessageUpdateCommand tagMessageUpdateCommand;

    @Requirement
    private FinishProductionExternalExecutor productionExecutor;

    @Requirement
    private CacheVersionsCommand cacheVersionsCommand;

    @Override
    public void init(MavenJGitFlowExtension externalExtension)
    {
        productionExecutor.init(externalExtension);

        addAfterTopicCheckoutCommands(
                cacheVersionsCommand,
                updatePomsWithNonSnapshotCommand,
                verifyReleaseVersionStateAndDepsCommand,
                productionExecutor,
                mavenBuildCommand
        );

        addAfterMasterCheckoutCommands(cacheVersionsCommand);
        addAfterMasterMergeCommands(productionExecutor);
        addBeforeTagCommands(tagMessageUpdateCommand);
    }
}
