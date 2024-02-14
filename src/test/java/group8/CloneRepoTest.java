package group8;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

public class CloneRepoTest {
    
    //---------------- Clone Repo -------------
    
    @Test
    public void cloneRepoWorks() throws GitAPIException, IOException {
        File tempDir = new File("repo");
        if (tempDir.exists()) {
            FileUtils.deleteDirectory(tempDir);
        }
        Git git = GitCommands.cloneRepo(tempDir, "https://github.com/DD2480-group8-VT24/DD2480-Assignment-2-CI.git");
    }

    @Test
    public void checkoutBranchWorks() throws GitAPIException, IOException {
        File tempDir = new File("repo");
        if (tempDir.exists()) {
            FileUtils.deleteDirectory(tempDir);
        }
        Git git = GitCommands.cloneRepo(tempDir, "https://github.com/DD2480-group8-VT24/DD2480-Assignment-2-CI.git");

        GitCommands.checkoutBranch(git, "Testing");

        ProcessBuilder pb = new ProcessBuilder("ls");
        pb.directory(tempDir);
        Process process = pb.start();

        String result = new String(process.getInputStream().readAllBytes());

        assertTrue(result.substring(0, result.length()-1).equals("README.md"));
    }

    @Test
    public void checkoutCommitWorks() throws GitAPIException, IOException {
        File tempDir = new File("repo");
        if (tempDir.exists()) {
            FileUtils.deleteDirectory(tempDir);
        }
        Git git = GitCommands.cloneRepo(tempDir, "https://github.com/DD2480-group8-VT24/DD2480-Assignment-2-CI.git");

        GitCommands.checkoutBranch(git, "Testing");
        GitCommands.checkoutCommit(git, "0b802b7ced98b6f963f7b468386e5af425f05255");

        ObjectId id = git.getRepository().resolve(Constants.HEAD);

        System.out.println(id.getName());

        assertTrue(id.getName().equals("0b802b7ced98b6f963f7b468386e5af425f05255"));
    }
}

