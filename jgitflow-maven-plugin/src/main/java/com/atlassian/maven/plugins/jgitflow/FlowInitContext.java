package com.atlassian.maven.plugins.jgitflow;

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

import com.atlassian.jgitflow.core.InitContext;

/**
 * @since version
 */
public class FlowInitContext
{
    private String masterBranchName;
    private String developBranchName;
    private String featureBranchPrefix;
    private String releaseBranchPrefix;
    private String hotfixBranchPrefix;
    private String versionTagPrefix;

    public FlowInitContext()
    {
        this.masterBranchName = "master";
        this.developBranchName = "develop";
        this.featureBranchPrefix = "feature/";
        this.releaseBranchPrefix = "release/";
        this.hotfixBranchPrefix = "hotfix/";
        this.versionTagPrefix = "";
    }

    public String getMasterBranchName()
    {
        return masterBranchName;
    }

    public void setMasterBranchName(String masterBranchName)
    {
        this.masterBranchName = masterBranchName;
    }

    public String getDevelopBranchName()
    {
        return developBranchName;
    }

    public void setDevelopBranchName(String developBranchName)
    {
        this.developBranchName = developBranchName;
    }

    public String getFeatureBranchPrefix()
    {
        return featureBranchPrefix;
    }

    public void setFeatureBranchPrefix(String featureBranchPrefix)
    {
        this.featureBranchPrefix = featureBranchPrefix;
    }

    public String getReleaseBranchPrefix()
    {
        return releaseBranchPrefix;
    }

    public void setReleaseBranchPrefix(String releaseBranchPrefix)
    {
        this.releaseBranchPrefix = releaseBranchPrefix;
    }

    public String getHotfixBranchPrefix()
    {
        return hotfixBranchPrefix;
    }

    public void setHotfixBranchPrefix(String hotfixBranchPrefix)
    {
        this.hotfixBranchPrefix = hotfixBranchPrefix;
    }

    public String getVersionTagPrefix()
    {
        return versionTagPrefix;
    }

    public void setVersionTagPrefix(String versionTagPrefix)
    {
        this.versionTagPrefix = versionTagPrefix;
    }

    public InitContext getJGitFlowContext()
    {
        InitContext ctx = new InitContext();
        ctx.setMaster(masterBranchName)
           .setDevelop(developBranchName)
           .setFeature(featureBranchPrefix)
           .setRelease(releaseBranchPrefix)
           .setHotfix(hotfixBranchPrefix)
           .setVersiontag(versionTagPrefix);

        return ctx;
    }
}
