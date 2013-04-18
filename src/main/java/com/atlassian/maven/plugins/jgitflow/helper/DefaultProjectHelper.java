package com.atlassian.maven.plugins.jgitflow.helper;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atlassian.maven.plugins.jgitflow.PrettyPrompter;
import com.atlassian.maven.plugins.jgitflow.ReleaseContext;
import com.atlassian.maven.plugins.jgitflow.exception.JGitFlowReleaseException;

import com.google.common.collect.ImmutableMap;

import org.apache.maven.artifact.ArtifactUtils;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.release.util.ReleaseUtil;
import org.apache.maven.shared.release.version.HotfixVersionInfo;
import org.apache.maven.shared.release.versions.DefaultVersionInfo;
import org.apache.maven.shared.release.versions.VersionParseException;
import org.codehaus.plexus.components.interactivity.PrompterException;
import org.codehaus.plexus.util.StringUtils;

/**
 * @since version
 */
public class DefaultProjectHelper implements ProjectHelper
{
    private PrettyPrompter prompter;
    private Map<String,String> originalVersions;
    private Map<String,String> releaseVersions;
    private Map<String,String> developmentVersions;
    private Map<String,String> hotfixVersions;
    
    @Override
    public String getReleaseVersion(ReleaseContext ctx, MavenProject rootProject) throws JGitFlowReleaseException
    {
        String defaultVersion = rootProject.getVersion();
        
        if (StringUtils.isNotBlank(ctx.getDefaultReleaseVersion()))
        {
            defaultVersion = ctx.getDefaultReleaseVersion();
        }

        String suggestedVersion = null;
        String releaseVersion = defaultVersion;
        
        while(null == releaseVersion || ArtifactUtils.isSnapshot(releaseVersion))
        {
            DefaultVersionInfo info = null;
            try
            {
               info = new DefaultVersionInfo(rootProject.getVersion());
            }
            catch (VersionParseException e)
            {
                if (ctx.isInteractive())
                {
                    try
                    {
                        info = new DefaultVersionInfo("1.0");
                    }
                    catch (VersionParseException e1)
                    {
                        throw new JGitFlowReleaseException("error parsing 1.0 version!!!", e1);
                    }
                }
                else
                {
                    throw new JGitFlowReleaseException("error parsing release version: " + e.getMessage(),e);
                }
            }
            
            suggestedVersion = info.getReleaseVersionString();

            if(ctx.isInteractive())
            {
                String message = MessageFormat.format("What is the release version for \"{0}\"? ({1})",rootProject.getName(), ArtifactUtils.versionlessKey(rootProject.getGroupId(), rootProject.getArtifactId()));
                try
                {
                    releaseVersion = prompter.promptNotBlank(message,suggestedVersion);
                }
                catch (PrompterException e)
                {
                    throw new JGitFlowReleaseException("Error reading version from command line " + e.getMessage(),e);
                }
            }
            else
            {
                releaseVersion = suggestedVersion;
            }
            
        }
        
        return releaseVersion;
    }

    @Override
    public String getHotfixVersion(ReleaseContext ctx, MavenProject rootProject, String lastRelease) throws JGitFlowReleaseException
    {
        String defaultVersion = rootProject.getVersion();

        HotfixVersionInfo hotfixInfo = null;
        if (StringUtils.isNotBlank(lastRelease) && !ArtifactUtils.isSnapshot(lastRelease))
        {
            try
            {
                hotfixInfo = new HotfixVersionInfo(lastRelease);
                defaultVersion = hotfixInfo.getHotfixVersionString();
            }
            catch (VersionParseException e)
            {
                //just ignore
            }
        }
        else
        {
            try
            {
                hotfixInfo = new HotfixVersionInfo(rootProject.getVersion());
                defaultVersion = hotfixInfo.getDecrementedHotfixVersionString();
            }
            catch (VersionParseException e)
            {
                //ignore
            }
        }

        String suggestedVersion = defaultVersion;
        String hotfixVersion = null;

        while(null == suggestedVersion || ArtifactUtils.isSnapshot(suggestedVersion))
        {
            HotfixVersionInfo info = null;
            try
            {
                info = new HotfixVersionInfo(rootProject.getVersion());
            }
            catch (VersionParseException e)
            {
                if (ctx.isInteractive())
                {
                    try
                    {
                        info = new HotfixVersionInfo("2.0");
                    }
                    catch (VersionParseException e1)
                    {
                        throw new JGitFlowReleaseException("error parsing 2.0 version!!!", e1);
                    }
                }
                else
                {
                    throw new JGitFlowReleaseException("error parsing release version: " + e.getMessage(),e);
                }
            }

            suggestedVersion = info.getDecrementedHotfixVersionString();
        }

        while(null == hotfixVersion || ArtifactUtils.isSnapshot(hotfixVersion))
        {
            if(ctx.isInteractive())
            {
                String message = MessageFormat.format("What is the hotfix version for \"{0}\"? ({1})",rootProject.getName(), ArtifactUtils.versionlessKey(rootProject.getGroupId(), rootProject.getArtifactId()));
                try
                {
                    hotfixVersion = prompter.promptNotBlank(message,suggestedVersion);
                }
                catch (PrompterException e)
                {
                    throw new JGitFlowReleaseException("Error reading version from command line " + e.getMessage(),e);
                }
            }
            else
            {
                hotfixVersion = suggestedVersion;
            }

        }

        return hotfixVersion;
    }
    
    @Override
    public String getDevelopmentVersion(ReleaseContext ctx, MavenProject rootProject) throws JGitFlowReleaseException
    {
        String defaultVersion = rootProject.getVersion();

        if (StringUtils.isNotBlank(ctx.getDefaultDevelopmentVersion()))
        {
            defaultVersion = ctx.getDefaultDevelopmentVersion();
        }

        String suggestedVersion = null;
        String developmentVersion = defaultVersion;

        while(null == developmentVersion || !ArtifactUtils.isSnapshot(developmentVersion))
        {
            DefaultVersionInfo info = null;
            try
            {
                info = new DefaultVersionInfo(rootProject.getVersion());
            }
            catch (VersionParseException e)
            {
                if (ctx.isInteractive())
                {
                    try
                    {
                        info = new DefaultVersionInfo("1.0");
                    }
                    catch (VersionParseException e1)
                    {
                        throw new JGitFlowReleaseException("error parsing 1.0 version!!!", e1);
                    }
                }
                else
                {
                    throw new JGitFlowReleaseException("error parsing development version: " + e.getMessage(),e);
                }
            }

            suggestedVersion = info.getNextVersion().getSnapshotVersionString();

            if(ctx.isInteractive())
            {
                String message = MessageFormat.format("What is the development version for \"{0}\"? ({1})",rootProject.getName(), ArtifactUtils.versionlessKey(rootProject.getGroupId(), rootProject.getArtifactId()));
                try
                {
                    developmentVersion = prompter.promptNotBlank(message,suggestedVersion);
                }
                catch (PrompterException e)
                {
                    throw new JGitFlowReleaseException("Error reading version from command line " + e.getMessage(),e);
                }
            }
            else
            {
                developmentVersion = suggestedVersion;
            }

        }

        return developmentVersion;
    }

    @Override
    public Map<String, String> getOriginalVersions(List<MavenProject> reactorProjects)
    {
        if(null == originalVersions)
        {
            this.originalVersions = new HashMap<String, String>();
            
            for(MavenProject project : reactorProjects)
            {
                originalVersions.put(ArtifactUtils.versionlessKey(project.getGroupId(),project.getArtifactId()),project.getVersion());
            }
        }
        
        return ImmutableMap.copyOf(originalVersions);
    }

    @Override
    public Map<String, String> getReleaseVersions(List<MavenProject> reactorProjects, ReleaseContext ctx) throws JGitFlowReleaseException
    {
        if(null == releaseVersions)
        {
            this.releaseVersions = new HashMap<String, String>();

            MavenProject rootProject = ReleaseUtil.getRootProject(reactorProjects);
            
            if(ctx.isAutoVersionSubmodules() && ArtifactUtils.isSnapshot(rootProject.getVersion()))
            {
                String rootProjectId = ArtifactUtils.versionlessKey(rootProject.getGroupId(),rootProject.getArtifactId());
                String rootReleaseVersion = getReleaseVersion(ctx,rootProject);
                
                releaseVersions.put(rootProjectId,rootReleaseVersion);
                
                for(MavenProject subProject : reactorProjects)
                {
                    String subProjectId = ArtifactUtils.versionlessKey(subProject.getGroupId(),subProject.getArtifactId());
                    releaseVersions.put(subProjectId,rootReleaseVersion);
                }
            }
            else
            {
                for (MavenProject project : reactorProjects)
                {
                    String projectId = ArtifactUtils.versionlessKey(project.getGroupId(),project.getArtifactId());
                    String releaseVersion = getReleaseVersion(ctx,project);
                    releaseVersions.put(projectId,releaseVersion);
                }
            }
        }
        
        return ImmutableMap.copyOf(releaseVersions);
        
    }

    @Override
    public Map<String, String> getHotfixVersions(List<MavenProject> reactorProjects, ReleaseContext ctx, Map<String,String> lastReleaseVersions) throws JGitFlowReleaseException
    {
        if(null == hotfixVersions)
        {
            this.hotfixVersions = new HashMap<String, String>();

            MavenProject rootProject = ReleaseUtil.getRootProject(reactorProjects);
            
            if(ctx.isAutoVersionSubmodules())
            {
                String rootProjectId = ArtifactUtils.versionlessKey(rootProject.getGroupId(),rootProject.getArtifactId());

                String lastRootRelease = "";

                if(null != lastReleaseVersions)
                {
                    lastRootRelease = lastReleaseVersions.get(rootProjectId);
                }
                
                String rootHotfixVersion = getHotfixVersion(ctx,rootProject,lastRootRelease);

                hotfixVersions.put(rootProjectId,rootHotfixVersion);

                for(MavenProject subProject : reactorProjects)
                {
                    String subProjectId = ArtifactUtils.versionlessKey(subProject.getGroupId(),subProject.getArtifactId());
                    hotfixVersions.put(subProjectId,rootHotfixVersion);
                }
            }
            else
            {
                for (MavenProject project : reactorProjects)
                {
                    String projectId = ArtifactUtils.versionlessKey(project.getGroupId(),project.getArtifactId());
                    String lastRelease = "";

                    if(null != lastReleaseVersions)
                    {
                        lastRelease = lastReleaseVersions.get(projectId);
                    }

                    String hotfixVersion = getHotfixVersion(ctx, project, lastRelease);
                    hotfixVersions.put(projectId,hotfixVersion);
                }
            }
        }

        return ImmutableMap.copyOf(hotfixVersions);
    }

    @Override
    public Map<String, String> getDevelopmentVersions(List<MavenProject> reactorProjects, ReleaseContext ctx) throws JGitFlowReleaseException
    {
        if(null == developmentVersions)
        {
            this.developmentVersions = new HashMap<String, String>();

                for (MavenProject project : reactorProjects)
                {
                    String projectId = ArtifactUtils.versionlessKey(project.getGroupId(),project.getArtifactId());
                    String developmentVersion = getDevelopmentVersion(ctx, project);
                    developmentVersions.put(projectId,developmentVersion);
                }
        }

        return ImmutableMap.copyOf(developmentVersions);

    }
}