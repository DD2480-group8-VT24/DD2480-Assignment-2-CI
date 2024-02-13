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

        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        // 2nd compile the code
        JsonRequest newRequest = JsonRequest.readJsonFromRequest(request);
        cloneRepo(newRequest.getRepoName());

        response.getWriter().println("CI job done");
    }

    public Git cloneRepo(File tempDir, String repoName) throws GitAPIException{
        return Git.cloneRepository()
            .setURI(repoName)
            .setDirectory(tempDir)
            .call();
    }

    public void checkoutBranch(Git git, String branchName) throws GitAPIException, IOException{
        git.checkout().setName("origin/" + branchName).call();

        ProcessBuilder pb = new ProcessBuilder("cd", "repo", ";", "git", "rev-parse", "--abbrev-ref", "HEAD");
        Process process = pb.start();

        String result = new String(process.getInputStream().readAllBytes());

        System.out.println(result);
    }

    public void checkoutCommit(Git git, String commitId) throws GitAPIException, IOException {
        git.checkout().setName(commitId).call();
        ObjectId id = git.getRepository().resolve(Constants.HEAD);

        System.out.println(id.getName());

        assert(id.getName().equals(commitId));
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
