import cn.edu.fudan.issue.core.process.RawIssueMatcher;
import cn.edu.fudan.issue.entity.dbo.RawIssue;
import cn.edu.fudan.issue.util.AstParserUtil;
import com.alibaba.fastjson.JSONObject;
import object.*;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test {

    public static void main(String[] args) throws Exception {
        String baseDir = "";
        String pjName = "";

//        1.确定本地项目（即确定repo）
        Git git = Git.open(new File(baseDir + File.separator + pjName));

//        2.利用jgit完成repo, branch, commit 的入库

        //repo
        Repository repository = new Repository(pjName);
        int repositoryId = insertRepo(repository);

        List<Ref> refs = git.branchList().call();
        for (Ref ref : refs) {
            //branch
            String branchName = ref.getName().substring(GitUtil.GitBranchType.LOCAL.getPrefix().length());
            Branch branch = new Branch(repositoryId, branchName);
            int branchId = insertBranch(branch);

            git.checkout()
                    .setCreateBranch(false)
                    .setName(branchName)
                    .call();
            for (RevCommit commitFromPj : git.log().call()) {
                //commit
                Commit commitForImport = new Commit(branchId,
                        commitFromPj.getName(),
                        commitFromPj.getAuthorIdent().getWhen(),
                        commitFromPj.getAuthorIdent().getName());
                insertCommit(commitForImport);
            }
        }

//        3.对不同的commit进行自动化扫描
        scannerCommit();
//        4.从最早的commit开始进行case和instance的入库
        insertIssue();
    }

    private static void insertIssue() throws Exception {
        int repositoryId = 0;
        int branchNum = getBranchNum();
        for (int branchId = 0; branchId < branchNum; branchId++) {
            List<Commit> commitList = getCommitByBranchId();

            for (Commit commit : commitList) {
                int commitId = commit.getCommitId();

                List<RawIssue> resultRawIssues = IssueUtil.getSonarResult(repositoryId, branchId, commitId);
//                resultRawIssues.forEach(iss->System.out.println(iss.getFileName()));

                Commit last_commit = getLastCommit();
                List<IssueInstance> last_instances = getInstanceByCommit(last_commit);

                List<RawIssue> lastRawIssues = IssueUtil.IssueInstancesToRawIssues(last_instances);

                String filePath = "";

                RawIssueMatcher.match(lastRawIssues, resultRawIssues, AstParserUtil.getMethodsAndFieldsInFile(filePath));

                for (int i = 0; i < resultRawIssues.size(); i++) {
                    if (resultRawIssues.get(i).getMappedRawIssue() == null) {
                        IssueInstance issueInstance = new IssueInstance();
                        IssueCase issueCase = new IssueCase();
                    } else {
                        IssueInstance issueInstance = new IssueInstance();
                    }
                }

            }

        }
    }

    private static void scannerCommit() {
    }

    private static List<Commit> getCommitByBranchId() {
        return new ArrayList<>();
    }

    private static int getBranchNum() {
        return 0;
    }

    private static void insertCommit(Commit commitForImport) {
    }

    private static int insertBranch(Branch branch) {
        return 0;
    }

    private static int insertRepo(Repository repository) {
        return 0;
    }

    public static Commit getLastCommit() {
        return new Commit();
    }

    private static List<IssueInstance> getInstanceByCommit(Commit commit) {
        return new ArrayList<>();
    }
}

//    public static void main(String[] args) throws Exception {
//        Git git = GitUtil.getGit("scannerDemo");
//        GitUtil.getAllCommit(git, 1, "main");
//        GitUtil.getAllBranch(git, 1);
//
//    }





