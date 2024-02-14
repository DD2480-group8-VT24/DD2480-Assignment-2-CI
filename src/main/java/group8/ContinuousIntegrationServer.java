package group8;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.*;
import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jgit.api.Git;
import org.json.JSONObject;

/**
 *  Responsible for handling the CI request from GitHub, by compiling and testing the target repo
 */
public class ContinuousIntegrationServer extends AbstractHandler {

    /**
     * Parses the json request of GitHub webhook, clones the repo to temporary directory. runs compilation and testing
     * on the target repo. Then sends the results using a commit status
     * @param target the uri
     * @param baseRequest base request
     * @param request the HTTP request
     * @param response the HTTP response
     */
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

        JsonRequest jsonRequest = JsonRequest.readJsonFromRequest(request);

        Git git = GitCommands.cloneRepo(tempDir, jsonRequest.getRepoCloneUrl());
        if (git == null) {
            response.getWriter().println("Failed to clone repo");
        }

        GitCommands.checkoutBranch(git, jsonRequest.getBranchName());
        GitCommands.checkoutCommit(git, jsonRequest.getCommitId());

        boolean compiles = Compiler.compileProject(tempDir);
        System.out.println("compiles: " + compiles);
        boolean passTests = runUnitTests.runAllTests(tempDir);
        System.out.println("passTests: " + passTests);

        JSONObject result = StatusNotification.createStatusMessage(compiles, passTests);

        try {
            StatusNotification.statusNotification(jsonRequest.getRepoName(), jsonRequest.getOwnerName(), jsonRequest.getCommitId(), result);
        }
        catch (InterruptedException | IOException e) {
            System.err.println("could not send commit status: " + e);
            response.getWriter().println("couldn't send commit status");
        }

        response.getWriter().println("CI job done");
        System.out.println("CI job done");
    }

    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }
}
