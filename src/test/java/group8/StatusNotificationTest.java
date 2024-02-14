package group8;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.junit.Test;

public class StatusNotificationTest {
    
    //---------------- Create Status Message -------------
    
    @Test
    public void createStatusMessageCorrectForPass() {
        JSONObject result = StatusNotification.createStatusMessage(true, true);
        
        assertEquals(result.getString("state"), "success");
        assertEquals(result.getString("description"), "The build succeeded!");

        assertEquals(result.get("target_url"), JSONObject.NULL);
        assertEquals(result.getString("context"), "continuous-integration/lab2");
    }

    @Test
    public void createStatusMessageCorrectForCompilesButNotPassTestSuit() {
        JSONObject result = StatusNotification.createStatusMessage(true, false);
        
        assertEquals(result.getString("state"), "failure");
        assertEquals(result.getString("description"), "The build compiles, but does not pass all tests");
        
        assertEquals(result.get("target_url"), JSONObject.NULL);
        assertEquals(result.getString("context"), "continuous-integration/lab2");
    }

    @Test
    public void createStatusMessageCorrectForFailsToCompile() {
        JSONObject result = StatusNotification.createStatusMessage(false, false);
        
        assertEquals(result.getString("state"), "failure");
        assertEquals(result.getString("description"), "The build fails to compile");
        
        assertEquals(result.get("target_url"), JSONObject.NULL);
        assertEquals(result.getString("context"), "continuous-integration/lab2");
    }

    //---------------- Status Notification -------------

    @Test
    public void githubPATExists(){
        try {
            String pat = "";
            pat = Files.readString(Paths.get("githubPAT"));
            assertFalse(pat.equals(""));
        } catch (IOException e) {
            try {
                String pat = "";
                pat = Files.readString(Paths.get("../githubPAT"));
                assertFalse(pat.equals(""));
            } catch (Exception d) {
                fail();
            }
        }
    }

    @Test
    public void statusNotificationCorrectForSuccessfulCommit(){
        try {
            String response = StatusNotification.statusNotification("DD2480-Assignment-2-CI", "DD2480-group8-VT24", "599348833adb3b968dc537edda4e7db906b2ae18", StatusNotification.createStatusMessage(true, true));
            assertTrue(response.equals("success"));
        } 
        catch (InterruptedException | IOException e) {
            fail();
        }
    }

    @Test
    public void statusNotificationCorrectForFailedCommit(){
        try {
            String response = StatusNotification.statusNotification("DD2480-Assignment-2-CI", "DD2480-group8-VT24", "599348833adb3b968dc537edda4e7db906b2ae18", StatusNotification.createStatusMessage(true, false));
            assertTrue(response.equals("failure"));
        } 
        catch (InterruptedException | IOException e) {
            fail();
        }
    }

    @Test
    public void statusNotificationFailsForIncorrectLocation(){
        try {
            String response = StatusNotification.statusNotification("DD2480-Assignment-2-CI", "DD2480-group8-VT24", "NotARealSHA", StatusNotification.createStatusMessage(true, false));
            assertTrue(response.substring(0,25).equals("Response state not found."));
        } 
        catch (InterruptedException | IOException e) {
            fail();
        }
    }
}
