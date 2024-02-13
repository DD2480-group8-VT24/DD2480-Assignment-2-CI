package group8;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * This class will run specific test cases or classes by using the function runTests.
 * @author: Melissa Mazura
*/

public class runUnitTests {
    /**
    * This function will return a boolean whether the test passes or not. It throws an excpetion when it cannot find the test name.
    * @param: testcase name
    * @return: boolean if the test failed or succeeded
    * @author: Melissa Mazura
    */

    public static boolean runTests(String testCase) {
        ProcessBuilder pb = new ProcessBuilder("mvn", testCase, "test");
        File resultsFile = new File("test_results.txt");

        try {
            pb.redirectErrorStream(true);
            pb.redirectOutput(resultsFile);
            // start the process
            Process process = pb.start();
            boolean is_exited = process.waitFor(5, TimeUnit.MINUTES);
            if (is_exited) {
                int exitCode = process.exitValue();
                if (exitCode == 0) {
                    return true;
                }
            }
            else {
                process.destroy();
                System.err.println("Test execution timed out");
                return false;
            }
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } 
        return false;
    }

}
