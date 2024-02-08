package group8;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class JsonRequestTest {

    @Test
    public void readPredefinedJsonTextDataTest1() throws Exception {
        String path = "/testFiles/github-webhook-dummy-payload-test1.txt";
        String json = IOUtils.toString(this.getClass().getResourceAsStream(path), "UTF-8");

        JsonRequest jsonRequest = JsonRequest.readJsonFromString(json);

        assertEquals(jsonRequest.getBranchName(), "atheer_testing");
        assertEquals(jsonRequest.getCommitId(), "8ca43edceb5af89158e6aac2fb636075a2b03bec");

        assertEquals(jsonRequest.getRepoCloneUrl(), "https://github.com/DD2480-group8-VT24/DD2480-Assignment-2-CI.git");
        assertEquals(jsonRequest.getRepoName(), "DD2480-Assignment-2-CI");

        assertEquals(jsonRequest.getOwnerName(), "DD2480-group8-VT24");
    }


    @Test
    public void readPredefinedJsonTextDataTest2() throws Exception {
        String path = "/testFiles/github-webhook-dummy-payload-test2.txt";
        String json = IOUtils.toString(this.getClass().getResourceAsStream(path), "UTF-8");

        JsonRequest jsonRequest = JsonRequest.readJsonFromString(json);

        assertEquals(jsonRequest.getBranchName(), "Testing");
        assertEquals(jsonRequest.getCommitId(), "599348833adb3b968dc537edda4e7db906b2ae18");

        assertEquals(jsonRequest.getRepoCloneUrl(), "https://github.com/DD2480-group8-VT24/DD2480-Assignment-2-CI.git");
        assertEquals(jsonRequest.getRepoName(), "DD2480-Assignment-2-CI");

        assertEquals(jsonRequest.getOwnerName(), "DD2480-group8-VT24");
    }

}
