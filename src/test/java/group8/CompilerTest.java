package group8;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class CompilerTest {

    @Test
    public void testCompileProjectSuccess() {
        File tempDir = new File("maven_fail_to_compile");

        try {
            Git git = GitCommands.cloneRepo(tempDir, "https://github.com/DD2480-group8-VT24/DD2480-Assignment-2-CI.git");
            GitCommands.checkoutBranch(git, "maven_succeed_to_compile_intentionally");

            boolean result = Compiler.compileProject(tempDir);
            assertTrue("Expected compilation to succeed for a project that compiles successfully", result);

            FileUtils.deleteDirectory(tempDir);
        } catch(GitAPIException | IOException e) {
            System.err.println("error: " + e);
        }
    }

    @Test
    public void testCompileProjectFailure() {

        File tempDir = new File("maven_fail_to_compile");
        try {
            Git git = GitCommands.cloneRepo(tempDir, "https://github.com/DD2480-group8-VT24/DD2480-Assignment-2-CI.git");
            GitCommands.checkoutBranch(git, "maven_fail_to_compile_intentionally");

            boolean result = Compiler.compileProject(tempDir);
            assertFalse("Expected compilation to fail for a project that does not compile successfully", result);


            FileUtils.deleteDirectory(tempDir);
        } catch(GitAPIException | IOException e) {
            System.err.println("error: " + e);
        }
    }
}