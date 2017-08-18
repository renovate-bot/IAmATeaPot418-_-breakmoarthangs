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

import com.atlassian.jgitflow.core.BranchType;
import com.atlassian.jgitflow.core.JGitFlowInfo;
import com.atlassian.maven.jgitflow.api.MavenJGitFlowExtension;
import com.atlassian.maven.jgitflow.api.StartProductionBranchExtension;
import com.atlassian.maven.jgitflow.api.exception.MavenJGitFlowExtensionException;

import org.codehaus.plexus.component.annotations.Component;

@Component(role = StartProductionExternalExecutor.class)
public class StartProductionExternalExecutor extends CachedVersionExternalExecutor
{
    @Override
    public void execute(MavenJGitFlowExtension extension, String newVersion, String oldVersion, JGitFlowInfo flow) throws MavenJGitFlowExtensionException
    {
        if (null == extension || !StartProductionBranchExtension.class.isAssignableFrom(extension.getClass()))
        {
            return;
        }

        StartProductionBranchExtension startExtension = (StartProductionBranchExtension) extension;
        try
        {
            BranchType type = branchHelper.getCurrentBranchType();

            switch (type)
            {
                case HOTFIX:
                    startExtension.onTopicBranchVersionChange(newVersion, oldVersion, flow);
                    break;
                case RELEASE:
                    startExtension.onTopicBranchVersionChange(newVersion, oldVersion, flow);
                    break;
            }
        }
        catch (Exception e)
        {
            throw new MavenJGitFlowExtensionException("Error running external extension", e);
        }
    }
}
