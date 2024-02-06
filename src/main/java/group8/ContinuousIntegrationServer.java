package group8;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONObject;

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

        response.getWriter().println("CI job done");
    }

    public void statusUpdate(String URL){
        String pat = "";
        try {
            pat = Files.readString(Paths.get("githubPAT"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONObject jObject = new JSONObject();

        jObject.put("state", "success");
        jObject.put("target_url", "https://example.com/build/status");
        jObject.put("description", "The build succeeded!");
        jObject.put("context", "continuous-integration/lab2");

        String[] command = {"curl", "-L", "-X", "POST", "-H", "Accept: application/vnd.github+json", "-H", "Authorization: Bearer " + pat, "-H", "X-GitHub-Api-Version: 2022-11-28",  URL, "-d", jObject.toString()};

        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            process.waitFor();
            process.destroy();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
    {
        ContinuousIntegrationServer test = new ContinuousIntegrationServer();
        test.statusUpdate("https://api.github.com/repos/DD2480-group8-VT24/DD2480-Assignment-2-CI/statuses/599348833adb3b968dc537edda4e7db906b2ae18");

        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }
}
