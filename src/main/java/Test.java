import cn.edu.fudan.issue.core.process.RawIssueMatcher;
import cn.edu.fudan.issue.entity.dbo.RawIssue;
import cn.edu.fudan.issue.util.AstParserUtil;
import com.alibaba.fastjson.JSONObject;
import object.*;
import org.eclipse.jgit.api.Git;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static Repository getRepository() {
        return new Repository();
    }

    public static Branch getBranch() {
        return new Branch();
    }

    public static Commit getCommit() {
        return new Commit();
    }

    public static Commit getLastCommit() {
        return new Commit();
    }

    private static List<IssueInstance> getInstanceByCommit(Commit commit) {
        return new ArrayList<>();
    }

//    public static void main(String[] args) throws Exception {
//        /* 假设已经导入Repo, Branch, Commit */
//        Repository repo = getRepository();
//        Branch branch = getBranch();
//        Commit commit = getCommit();
//
//        int repositoryId = repo.getRepositoryId();
//        int branchId = branch.getBranchId();
//        int commitId = commit.getCommitId();
//
//        List<RawIssue> resultRawIssues =  IssueUtil.getSonarResult(repositoryId, branchId, commitId);
//        resultRawIssues.forEach(iss->System.out.println(iss.getFileName()));
//
//        /* 如何得到上一个commit */
//        Commit last_commit = getLastCommit();
//        List<IssueInstance> last_instances = getInstanceByCommit(last_commit);
//
//        List<RawIssue> lastRawIssues = IssueUtil.IssueInstancesToRawIssues(last_instances);
//
//        String filePath = "";
//
//        RawIssueMatcher.match(lastRawIssues, resultRawIssues, AstParserUtil.getMethodsAndFieldsInFile(filePath));
//
//        /*  处理新产生的RawIssue */
//        for (int i = 0; i < resultRawIssues.size(); i++) {
//            if (resultRawIssues.get(i).getMappedRawIssue() == null) {
//                IssueInstance issueInstance = new IssueInstance();
//                IssueCase issueCase = new IssueCase();
//            }
//            else {
//                IssueInstance issueInstance = new IssueInstance();
//            }
//        }
//
//    }

    public static void main(String[] args) throws Exception {
        Git git = GitUtil.getGit("scannerDemo");
        GitUtil.getAllCommit(git, 1, "main");
        GitUtil.getAllBranch(git, 1);

    }


}
