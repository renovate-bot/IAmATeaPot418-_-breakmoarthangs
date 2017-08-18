package com.atlassian.maven.plugins.jgitflow;

/*-
 * #%L
 * jgitflow-it-support
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

import java.io.File;

import com.atlassian.jgitflow.core.JGitFlowInfo;
import com.atlassian.maven.jgitflow.api.exception.MavenJGitFlowExtensionException;
import com.atlassian.maven.jgitflow.api.impl.NoopMavenReleaseFinishExtension;
import com.atlassian.maven.jgitflow.api.util.JGitFlowCommitHelper;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;

public class ITFinishExtension extends NoopMavenReleaseFinishExtension
{
    public static final String EXTENSION_RESULT = "ext-result.txt";

    @Override
    public void onMasterBranchVersionChange(String newVersion, String oldVersion, JGitFlowInfo flow) throws MavenJGitFlowExtensionException
    {
        try
        {
            Git git = flow.git();

            //get the README.md file
            File extFile = new File(flow.getProjectRoot(), EXTENSION_RESULT);

            if (!extFile.exists())
            {
                FileUtils.touch(extFile);
            }

            FileUtils.writeStringToFile(extFile, oldVersion + ":" + newVersion);

            //now commit the change
            JGitFlowCommitHelper.commitAllChanges(flow, "updating version in extension file");

        }
        catch (Exception e)
        {
            throw new MavenJGitFlowExtensionException("Error updating " + EXTENSION_RESULT + " file!", e);
        }
    }

}
