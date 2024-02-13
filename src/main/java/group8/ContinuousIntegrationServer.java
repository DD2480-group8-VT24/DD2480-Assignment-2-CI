package group8;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.*;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

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

    public boolean cloneRepo(String repoName){
        File tempDir = new File("./src/repo");
        try{
            Git git = Git.cloneRepository()
                .setURI(repoName)
                .setDirectory(tempDir)
                .call();
        }
        catch(GitAPIException e){
            System.err.println("Failed to clone repository");
        }

        return false;
    }

    public void checkout(String id){

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
