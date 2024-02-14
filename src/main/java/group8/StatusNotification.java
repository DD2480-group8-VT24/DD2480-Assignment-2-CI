package group8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

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
     * @author Jonatan Tuvstedt
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
     * @param sha the full 40 character SHA
     * @param message JSON object of form {"state": , "description": , "target_url": , "context":}
     * @author Jonatan Tuvstedt
     * @return
     * @throws InterruptedException
     * @throws IOException 
     */
    public static String statusNotification(String repo, String owner, String sha, JSONObject message) throws InterruptedException, IOException{
        
        String pat = "";
        try {
            pat = Files.readString(Paths.get("githubPAT"));
        } catch (Exception e) {
            pat = Files.readString(Paths.get("../githubPAT"));
        }

        String URL = "https://api.github.com/repos/" + owner + "/" + repo + "/statuses/" + sha;

        String[] command = {"curl", "-L", "-X", "POST", "-H", "Accept: application/vnd.github+json", "-H", "Authorization: Bearer " + pat, "-H", "X-GitHub-Api-Version: 2022-11-28",  URL, "-d", message.toString()};

        Process process;
        process = Runtime.getRuntime().exec(command);


        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String responseString = in.lines().collect(Collectors.joining("\n"));
        JSONObject response = new JSONObject(responseString);

        process.waitFor();
        process.destroy();

        try {
            return response.getString("state");
        } catch (Exception e) {
            return "Response state not found.\n Full response message:" + responseString;
        }
    }
}
