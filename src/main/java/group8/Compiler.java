package group8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Compiler {

    public static void main(String[] args) {
        String repoPath = "C:\\Users\\mertd\\OneDrive\\Dokument\\GitHub\\DD2480-Assignment-2-CI";
        boolean compilationResult = compileProject(repoPath);
        System.out.println("Compilation successful: " + compilationResult);
    }

    public static boolean compileProject(String repoPath) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        // Correctly set up the command to compile a Maven project
        processBuilder.command("cmd", "/c", "mvn", "-f", repoPath, "clean", "install");

        StringBuilder output = new StringBuilder();
        StringBuilder errors = new StringBuilder();

        try {
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
                System.out.println(line);
            }
            while ((line = errorReader.readLine()) != null) {
                errors.append(line).append(System.lineSeparator());
                System.err.println(line);
            }

            // Use Pattern and Matcher for pattern matching if needed
            Pattern pattern = Pattern.compile("BUILD SUCCESS", Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(output.toString());
            boolean buildSuccess = matcher.find();

            // Wait for the process to exit
            int exitVal = process.waitFor();
            return exitVal == 0 && buildSuccess; // Returns true if compilation was successful and "BUILD SUCCESS" is
                                                 // found
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return false; // Returns false if an exception occurs
        }
    }
}