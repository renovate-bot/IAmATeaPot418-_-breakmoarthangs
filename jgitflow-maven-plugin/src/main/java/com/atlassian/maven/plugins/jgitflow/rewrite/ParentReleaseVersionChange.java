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

import com.google.common.base.Joiner;

import org.apache.maven.artifact.ArtifactUtils;
import org.apache.maven.project.MavenProject;
import org.jdom2.Element;
import org.jdom2.Namespace;

import static com.atlassian.maven.plugins.jgitflow.rewrite.ProjectChangeUtils.getNamespaceOrNull;

/**
 * @since version
 */
public class ParentReleaseVersionChange implements ProjectChange
{
    private final Map<String, String> originalVersions;
    private final Map<String, String> releaseVersions;
    private final boolean consistentProjectVersions;
    private final List<String> workLog;

    private ParentReleaseVersionChange(Map<String, String> originalVersions, Map<String, String> releaseVersions, boolean consistentProjectVersions)
    {
        this.originalVersions = originalVersions;
        this.releaseVersions = releaseVersions;
        this.consistentProjectVersions = consistentProjectVersions;
        this.workLog = new ArrayList<String>();
    }

    public static ParentReleaseVersionChange parentReleaseVersionChange(Map<String, String> originalVersions, Map<String, String> releaseVersions, boolean consistentProjectVersions)
    {
        return new ParentReleaseVersionChange(originalVersions, releaseVersions, consistentProjectVersions);
    }

    @Override
    public boolean applyChange(MavenProject project, Element root, String eol) throws ProjectRewriteException
    {
        boolean modified = false;

        if (project.hasParent())
        {
            Namespace ns = getNamespaceOrNull(root);
            Element parentVersionElement = root.getChild("parent", ns).getChild("version", ns);
            MavenProject parent = project.getParent();
            String parentId = ArtifactUtils.versionlessKey(parent.getGroupId(), parent.getArtifactId());
            // Don't attempt to update parents that aren't even in the project
            if (originalVersions.get(parentId) != null)
            {
                String parentVersion = releaseVersions.get(parentId);
                if (null == parentVersion && consistentProjectVersions && releaseVersions.size() > 0)
                {
                    // Use any release version, as the project's versions are consistent/global
                    parentVersion = releaseVersions.values().iterator().next();
                }

                if (null == parentVersion)
                {
                    if (parent.getVersion().equals(originalVersions.get(parentId)))
                    {
                        throw new ProjectRewriteException("Release version for parent " + parent.getName() + " was not found");
                    }
                }
                else
                {
                    workLog.add("setting parent version to '" + parentVersion + "'");
                    parentVersionElement.setText(parentVersion);
                    modified = true;
                }
            }
        }

        return modified;
    }

    @Override
    public String toString()
    {
        if (workLog.isEmpty())
        {
            return "[Update Parent Release Version]";
        }
        else
        {
            return "[Update Parent Release Version]\n - " + Joiner.on("\n - ").join(workLog);
        }
    }
}
