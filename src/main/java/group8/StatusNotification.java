package group8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.jetty.util.ajax.JSON;
import org.json.JSONObject;

/**
 * Sends github commit status updates to specified repo
 */
public class StatusNotification {

    /**
     * creates a status message json object based whether the commit compiles and passes 
     * the test suit
     * 
     * @param compiles
     * @param passTests
     * @return
     */
    public static JSONObject createStatusMessage(boolean compiles, boolean passTests){
        JSONObject message = new JSONObject();

        if (compiles) {
            if (passTests) 
            {
                message.put("state", "success");
                message.put("description", "The build succeeded!");
            } 
            else 
            {
                message.put("state", "failure");
                message.put("description", "The build compiles, but does not pass all tests");
            }
        } 
        else 
        {
            message.put("state", "failure");
            message.put("description", "The build fails to compile");
        }

        message.put("target_url", JSONObject.NULL);
        message.put("context", "continuous-integration/lab2");

        return message;
    }

    /**
     * Sends a commit status update to the specified repo and commit sha with the specified message
     * @param repo
     * @param owner
     * @param sha the full 41 character SHA
     * @param compiles
     * @param passTests
     * @return
     * @throws InterruptedException
     */
    public static boolean statusNotification(String repo, String owner, String sha, JSONObject message) throws InterruptedException{
        
        String pat = "";
        
        try {
            pat = Files.readString(Paths.get("githubPAT"));
        } catch (IOException e) {
            
            System.err.println("Can't read in PAT");
            e.printStackTrace();
            return false;
        }

        String URL = "https://api.github.com/repos/" + owner + "/" + repo + "/statuses/" + sha;

        String[] command = {"curl", "-L", "-X", "POST", "-H", "Accept: application/vnd.github+json", "-H", "Authorization: Bearer " + pat, "-H", "X-GitHub-Api-Version: 2022-11-28",  URL, "-d", message.toString()};

        Process process;

        try {
            process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            
            System.err.println("Error when running curl");
            e.printStackTrace();
            return false;
        }

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error when reading response");
            e.printStackTrace();
        }

        process.waitFor();
        process.destroy();

        return true;
    }
}
