package group8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

public class StatusNotification {

    public static boolean statusNotification(String repo, String owner, String sha, boolean compiles, boolean passTests) throws InterruptedException{
        
        String pat = "";
        
        try {
            pat = Files.readString(Paths.get("githubPAT"));
        } catch (IOException e) {
            
            System.err.println("Can't read in PAT");
            e.printStackTrace();
            return false;
        }

        JSONObject jObject = new JSONObject();

        if (compiles) {
            if (passTests) 
            {
                jObject.put("state", "success");
                jObject.put("description", "The build succeeded!");
            } 
            else 
            {
                jObject.put("state", "failure");
                jObject.put("description", "The build compiles, but does not pass all tests");
            }
        } 
        else 
        {
            jObject.put("state", "failure");
            jObject.put("description", "The build fails to compile");
        }

        jObject.put("target_url", JSONObject.NULL);
        jObject.put("context", "continuous-integration/lab2");

        String URL = "https://api.github.com/repos/" + owner + "/" + repo + "/statuses/" + sha;

        String[] command = {"curl", "-L", "-X", "POST", "-H", "Accept: application/vnd.github+json", "-H", "Authorization: Bearer " + pat, "-H", "X-GitHub-Api-Version: 2022-11-28",  URL, "-d", jObject.toString()};

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
