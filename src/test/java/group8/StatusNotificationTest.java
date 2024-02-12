package group8;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.json.JSONObject;
import org.junit.Test;

public class StatusNotificationTest {
    
    
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
}
