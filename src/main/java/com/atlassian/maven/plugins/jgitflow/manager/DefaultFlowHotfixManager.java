package com.atlassian.maven.plugins.jgitflow.manager;

import java.util.List;

import com.atlassian.maven.plugins.jgitflow.ReleaseContext;
import com.atlassian.maven.plugins.jgitflow.exception.JGitFlowReleaseException;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;

/**
 * @since version
 */
public class DefaultFlowHotfixManager extends AbstractFlowReleaseManager
{
    @Override
    public void start(ReleaseContext ctx, List<MavenProject> reactorProjects) throws JGitFlowReleaseException
    {
        startHotfix(ctx,reactorProjects);
    }

    @Override
    public void finish(ReleaseContext ctx, List<MavenProject> reactorProjects, MavenSession session) throws JGitFlowReleaseException
    {
        finishHotfix(ctx, reactorProjects, session);
    }
}