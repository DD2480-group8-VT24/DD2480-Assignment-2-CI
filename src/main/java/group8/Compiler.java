package group8;

import java.io.IOException;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class Compiler {

    public static boolean compileProject(File tempDir) {
        ProcessBuilder processBuilder = new ProcessBuilder("mvn", "compile");
        processBuilder.directory(tempDir);
        File resultsFile = new File("compile_results.txt");
        try {
            processBuilder.redirectErrorStream(true);
            processBuilder.redirectOutput(resultsFile);

            Process process = processBuilder.start();
            boolean is_exited = process.waitFor(5, TimeUnit.MINUTES);
            if (is_exited) {
                return runUnitTests.findSuccessBuild(resultsFile);
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