package com.database.utils;

import com.database.object.Branch;
import com.database.object.Commit;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.util.*;

public final class GitUtil {

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

    public static Git getGit(String pathName) throws Exception {
        Git git;
        git = Git.open(new File(pathName));
        return git;
    }

    public static void gitCheckout(String pathName, String des) throws Exception {
        Git git = getGit(pathName);
        git.checkout().setName(des).call();
    }

    public static void getAllBranch(Git git, int repositoryId) throws GitAPIException {
        List<Ref> refs = git.branchList().call();

        ArrayList<Branch> list = new ArrayList<Branch>();

        refs.forEach(ref -> {
            Branch branch = new Branch(0, ref.getName().substring(GitBranchType.LOCAL.prefix.length()), repositoryId);
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
            Commit commitForImport = new Commit(0,
                    commitFromPj.getName(),
                    commitFromPj.getAuthorIdent().getWhen(),
                    commitFromPj.getAuthorIdent().getName(),
                    branchId);

            list.add(commitForImport);

            System.out.println(commitForImport.getBranchId());
            System.out.println(commitForImport.getCommitHash());
            System.out.println(commitForImport.getCommitTime());
            System.out.println(commitForImport.getCommiter());
            System.out.println(commitFromPj.getShortMessage());
        }

    }

}
