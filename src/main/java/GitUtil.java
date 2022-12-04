import object.Branch;
import object.Commit;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.util.*;

public final class GitUtil {

    private static String baseDir = "C:\\GitCloneRepository";

    public enum GitBranchType {
        LOCAL("refs/heads/"),
        REMOTE("refs/remotes/origin/");

        private String prefix;
        public String getPrefix() {
            return prefix;
        }
        GitBranchType(String prefix) {
            this.prefix = prefix;
        }
    }

    private GitUtil() {}

//    public static Git getGit(String userName, String pwd, String gitUrl, String pjName, String branch) throws Exception {
    public static Git getGit(String pjName) throws Exception {
        Git git;

//        if (new File(baseDir + File.separator + pjName).exists()) {
            git = Git.open(new File(baseDir + File.separator + pjName));

//            git.fetch()
//                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(userName, pwd))
//                .setCheckFetchedObjects(true)
//                .call();
//        }
//        else {
//            git = Git.cloneRepository()
//                    .setURI(gitUrl)
//                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(userName, pwd))
//                    .setCloneAllBranches(true)
//                    .setDirectory(new File(baseDir + File.separator + pjName))
//                    .call();
//        }

//        git.checkout()
//                .setCreateBranch(false)
//                .setName(branch)
//                .call();

        return git;
    }

    public static void getAllBranch(Git git, int repositoryId) throws GitAPIException {
        List<Ref> refs = git.branchList().call();

        ArrayList<Branch> list = new ArrayList<Branch>();

        refs.forEach(ref -> {
            Branch branch = new Branch(repositoryId, ref.getName().substring(GitBranchType.LOCAL.prefix.length()));
            System.out.println(ref.getName().substring(GitBranchType.LOCAL.prefix.length()));
        });
    }

    public static void getAllCommit(Git git, int branchId, String branchName) throws GitAPIException {

        git.checkout()
            .setCreateBranch(false)
            .setName(branchName)
            .call();

        Iterator<RevCommit> logIterator = git.log().call().iterator();

        ArrayList<Commit> list = new ArrayList<Commit>();
        while (logIterator.hasNext()) {
            RevCommit commitFromPj = logIterator.next();
            Commit commitForImport = new Commit(branchId,
                    commitFromPj.getName(),
                    commitFromPj.getAuthorIdent().getWhen(),
                    commitFromPj.getAuthorIdent().getName());

            list.add(commitForImport);

            System.out.println(commitForImport.getBranchId());
            System.out.println(commitForImport.getHash());
            System.out.println(commitForImport.getCommitTime());
            System.out.println(commitForImport.getCommiter());
            System.out.println(commitFromPj.getShortMessage());
        }

    }

}
