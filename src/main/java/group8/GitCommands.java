package group8;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.Git;

import java.io.File;
import java.io.IOException;

public class GitCommands {

    /**
     * Clones the requested repo into the folder specified in tempDir and returns a
     * git object referencing it
     *
     * @param tempDir
     * @param repoName
     * @return
     * @throws GitAPIException
     * @author Marcus Odin
     * @author Jonatan Tuvstedt
     */
    public static Git cloneRepo(File tempDir, String repoName) throws GitAPIException {
        return org.eclipse.jgit.api.Git.cloneRepository()
                .setURI(repoName)
                .setDirectory(tempDir)
                .call();
    }

    /**
     * Switches to the requested repo
     *
     * @param git
     * @param branchName
     * @throws GitAPIException
     * @throws IOException
     * @author Marcus Odin
     * @author Jonatan Tuvstedt
     */
    public static void checkoutBranch(org.eclipse.jgit.api.Git git, String branchName) throws GitAPIException{
        git.checkout().setName("origin/" + branchName).call();
    }

    /**
     * Checks out the requested git commit
     *
     * @param git
     * @param commitId
     * @throws GitAPIException
     * @throws IOException
     * @author Marcus Odin
     * @author Jonatan Tuvstedt
     */
    public static void checkoutCommit(org.eclipse.jgit.api.Git git, String commitId) throws GitAPIException, IOException {
        git.checkout().setName(commitId).call();
    }



}
