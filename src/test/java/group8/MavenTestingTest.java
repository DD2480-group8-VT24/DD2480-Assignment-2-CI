package group8;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

/*
 * This class will run the unit tests for the Testing part with maven of the code. 
 */
public class MavenTestingTest {

    @Test
    public void checkTestTrue() {
        assertTrue(runUnitTests.runTests("-Dtest=DummyTest#testIsTrue"));
    }
    @Test
    public void checkTestFalse() throws IOException {

        File tempDir = new File("maven_failed_to_test");
        if (tempDir.exists()) {
            FileUtils.deleteDirectory(tempDir);
        }

        try {
            Git git = GitCommands.cloneRepo(tempDir, "https://github.com/DD2480-group8-VT24/DD2480-Assignment-2-CI.git");
            GitCommands.checkoutBranch(git, "maven_failing_tests_intentionally");

            boolean result = runUnitTests.runAllTests(tempDir);
            assertFalse(result);

            FileUtils.deleteDirectory(tempDir);
        } catch(GitAPIException | IOException e) {
            System.err.println("error: " + e);
        }
    }

}
