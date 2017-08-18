package com.atlassian.maven.plugins.jgitflow.rewrite;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.atlassian.maven.plugins.jgitflow.exception.ProjectRewriteException;

import com.atlassian.maven.plugins.jgitflow.util.NamingUtil;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import org.apache.maven.artifact.ArtifactUtils;
import org.apache.maven.project.MavenProject;
import org.jdom2.Content;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.Text;

import static com.atlassian.maven.plugins.jgitflow.rewrite.ProjectChangeUtils.getNamespaceOrNull;

/**
 * @since version
 */
public class ProjectReleaseVersionChange implements ProjectChange
{
    private final Map<String, String> releaseVersions;

    private final boolean consistentProjectVersions;
    private final List<String> workLog;

    private ProjectReleaseVersionChange(Map<String, String> releaseVersions, boolean consistentProjectVersions)
    {
        this.releaseVersions = releaseVersions;
        this.consistentProjectVersions = consistentProjectVersions;
        this.workLog = new ArrayList<String>();
    }

    public static ProjectReleaseVersionChange projectReleaseVersionChange(Map<String, String> releaseVersions, boolean consistentProjectVersions)
    {
        return new ProjectReleaseVersionChange(releaseVersions, consistentProjectVersions);
    }

    @Override
    public boolean applyChange(MavenProject project, Element root, String eol) throws ProjectRewriteException
    {
        boolean modified = false;

        Namespace ns = getNamespaceOrNull(root);

        Element versionElement = root.getChild("version", ns);
        String projectId = ArtifactUtils.versionlessKey(project.getGroupId(), project.getArtifactId());
        String releaseVersion = releaseVersions.get(projectId);

        if (Strings.isNullOrEmpty(releaseVersion))
        {
            if (consistentProjectVersions && releaseVersions.size() > 0)
            {
                // Use any release version, as the project's versions are consistent/global
                releaseVersion = releaseVersions.values().iterator().next();
            }
            else
            {
                throw new ProjectRewriteException("Release version for " + project.getName() + " was not found");
            }
        }

        if (null == versionElement)
        {
            String parentVersion = null;
            if (project.hasParent())
            {
                MavenProject parent = project.getParent();
                String parentId = ArtifactUtils.versionlessKey(parent.getGroupId(), parent.getArtifactId());

                parentVersion = releaseVersions.get(parentId);
                if (Strings.isNullOrEmpty(parentVersion) && consistentProjectVersions && releaseVersions.size() > 0)
                {
                    // Use any version for the parent, as the project's versions are consistent/global
                    parentVersion = releaseVersions.values().iterator().next();
                }
            }

            if (!releaseVersion.equals(parentVersion))
            {
                Element artifactId = root.getChild("artifactId", ns);
                versionElement = new Element("version", ns);
                Text indent = new Text("");
                
                workLog.add("setting version to '" + releaseVersion + "'");
                versionElement.setText(releaseVersion);
                
                int index = root.indexOf(artifactId);
                List<Content> cList = root.getContent();
                
                //get the previous sibling if it exists
                if(index > 0 && index < cList.size()) {
                    Content prevSibling = cList.get(index - 1);
                    if(Text.class.isInstance(prevSibling))
                    {
                        String siblingText = ((Text)prevSibling).getText();

                        if(siblingText.matches("^\\s*$"))
                        {
                            indent = new Text("");
                            indent.setText(eol + NamingUtil.afterLastNewline(siblingText));
                        }
                    }
                }
                
                root.addContent(index + 1, indent);
                root.addContent(index + 2, versionElement);
                modified = true;
            }
        }
        else
        {
            workLog.add("updating version '" + versionElement.getTextTrim() + " to '" + releaseVersion + "'");
            versionElement.setText(releaseVersion);
            modified = true;
        }

        return modified;
    }

    @Override
    public String toString()
    {
        if (workLog.isEmpty())
        {
            return "[Update Project Release Version]";
        }
        else
        {
            return "[Update Project Release Version]\n - " + Joiner.on("\n - ").join(workLog);
        }
    }
}
