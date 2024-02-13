package group8;

import org.junit.Test;
import static org.junit.Assert.*;

public class CompilerTest {

    @Test
    public void testCompileProjectSuccess() {
        String successfulRepoPath = "path\\successful";
        
        boolean result = Compiler.compileProject(successfulRepoPath);
        
        assertTrue("Expected compilation to succeed for a project that compiles successfully", result);
    }

    @Test
    public void testCompileProjectFailure() {
        String failingRepoPath = "path\\failing";
        
        boolean result = Compiler.compileProject(failingRepoPath);
        
        assertFalse("Expected compilation to fail for a project that does not compile successfully", result);
    }
}