package group8;

import java.io.IOException;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * This class compiles the code using maven. This result will then be stored in the 
 * compile_results.txt file. After saving the result, it will look throught the file 
 * to find BUILD SUCCESS to ensure that the project actually compiles. 
 * @author Mert Demirsü
 */
public class Compiler {

    /**
     * The function CompileProject will return a boolean whether the code compiles. For this to work, it will call maven with
     * the Process Builder, run compile and save the results to compile_results.txt. It then calls the findSuccessBuild function
     * from runUnitTests.java to find the String BUILD SUCCESS to indicate a successful build.
     * @param temporary directory where the code will run
     * @return a boolean indicating whether the project successfully compiles
     * @author Mert Demirsü
     */
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
