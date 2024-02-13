package group8;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

/**
 * This class will run specific test cases or classes by using the function runTests.
 * @author: Melissa Mazura
*/

public class runUnitTests {
    /**
    * This function will return a boolean whether the test passes or not. It throws an excpetion when it cannot find the test name.
    * @param: testcase name. If this field is an emptry string, the function will run all test cases.
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
                return findSuccessBuild(resultsFile);
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

    /**
    * This function will return a boolean whether the test passes or not. It throws an excpetion when it cannot find the test name.
    * This will run all test cases.
    * @return: boolean if the test failed or succeeded
    * @author: Melissa Mazura
    */

    public static boolean runAllTests() {
        ProcessBuilder pb = new ProcessBuilder("mvn", "test");
        File resultsFile = new File("test_results.txt");
        try {
            pb.redirectErrorStream(true);
            pb.redirectOutput(resultsFile);
            // start the process
            Process process = pb.start();
            boolean is_exited = process.waitFor(5, TimeUnit.MINUTES);
            if (is_exited) {
                return findSuccessBuild(resultsFile);
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
    /**
    * This function will go through the results of the tests and return whether all tests passed.
    * @param: file of the tests
    * @return: boolean if we find BUILD SUCCESS in said file to ensure the test passed
    * @author: Melissa Mazura
    */

    public static boolean findSuccessBuild(File resultsFile) {
        try {
            Scanner sc = new Scanner(resultsFile);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.indexOf("BUILD SUCCESS") != -1) {
                    return true;
                }
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
    }
    return false;
    }
}
