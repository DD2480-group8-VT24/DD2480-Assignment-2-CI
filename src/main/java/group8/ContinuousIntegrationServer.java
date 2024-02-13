package group8;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;

public class ContinuousIntegrationServer extends AbstractHandler
{
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        System.out.println(target);

        File tempDir = new File("repo");
        if (tempDir.exists()) {
            FileUtils.deleteDirectory(tempDir);
        }

        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        // 2nd compile the code
        JsonRequest jsonRequest = JsonRequest.readJsonFromRequest(request);

        Git git = GitCommands.cloneRepo(tempDir, jsonRequest.getRepoCloneUrl());
        if (git == null) {
            response.getWriter().println("Failed to clone repo");
        }

        GitCommands.checkoutBranch(git, jsonRequest.getBranchName());
        GitCommands.checkoutCommit(git, jsonRequest.getCommitId());

        //String workingDirectory = System.getProperty("user.dir");

        response.getWriter().println("CI job done");
    }



    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }
}
