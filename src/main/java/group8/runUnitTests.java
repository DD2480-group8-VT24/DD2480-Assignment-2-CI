package group8;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class runUnitTests {
    public static boolean runUnitTests() {
        ProcessBuilder pb = new ProcessBuilder("mvn", "test");
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
