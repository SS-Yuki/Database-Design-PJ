package sonar;

import utils.GitUtil;
import cn.edu.fudan.issue.core.process.RawIssueMatcher;
import cn.edu.fudan.issue.entity.dbo.Location;
import cn.edu.fudan.issue.entity.dbo.RawIssue;
import cn.edu.fudan.issue.util.AnalyzerUtil;
import cn.edu.fudan.issue.util.AstParserUtil;
import common.IssueInstanceStatus;
import object.*;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static utils.EnumUtil.RawIssueType2IssueCaseType;

public class DataImport {
    String baseDir;
    String pjName;

    public DataImport(String baseDir, String pjName) {
        this.baseDir = baseDir;
        this.pjName = pjName;
    }

    public void importData() throws Exception {
        String pathName = baseDir + File.separator + pjName;

        //        1.确定本地项目（即确定repo）
        Git git = Git.open(new File(pathName));
        git.checkout()
                .setCreateBranch(false)
                .setName("main")
                .call();

//        2.利用jgit完成repo, branch, commit 的入库

        //repo
        Repository repository = new Repository(pjName, baseDir);
        int repositoryId = insertRepo(repository);

        List<Ref> refs = git.branchList().call();
        for (Ref ref : refs) {
            //branch
//            System.out.println(ref.getName());
            String branchName = ref.getName().substring(GitUtil.GitBranchType.LOCAL.getPrefix().length());
            Branch branch = new Branch(repositoryId, branchName);
            int branchId = insertBranch(branch);

            git.checkout()
                    .setCreateBranch(false)
                    .setName(branchName)
                    .call();

            List<RevCommit> commitFromPjs = new ArrayList<>();
            for (RevCommit commitFromPj : git.log().call()) {
                commitFromPjs.add(commitFromPj);
            }

            for (int i = commitFromPjs.size()-1; i > 0; i--) {
                RevCommit commitFromPj = commitFromPjs.get(i);
                System.out.println(commitFromPj.getName());
                System.out.println(commitFromPj.getAuthorIdent().getWhen());
                System.out.println(commitFromPj.getShortMessage());
                //commit
                Commit commitForImport = new Commit(branchId,
                        commitFromPj.getName(),
                        commitFromPj.getAuthorIdent().getWhen(),
                        commitFromPj.getAuthorIdent().getName());
                int commitId = insertCommit(commitForImport);


//                3.对不同的commit进行自动化扫描
                String commitHash = commitForImport.getHash();
                git.checkout()
                        .setCreateBranch(false)
                        .setName(commitHash)
                        .call();
                Sonar sonar = new Sonar();
                sonar.scannerCommit(commitHash, repositoryId, branchId, commitId);
            }
        }


//        4.从最早的commit开始进行case和instance的入库
        importIssue(repositoryId, pathName);
    }

    private void importIssue(int repositoryId, String pathName) throws Exception {
        int branchNum = getBranchNumByRepoId();
        Sonar sonar = new Sonar();
        for (int branchId = 0; branchId < branchNum; branchId++) {
            int [] commitIdList = getCommitIdByBranchId();

            List <RawIssue> preRawIssues = sonar.getSonarResult(repositoryId, branchId, commitIdList[0]);
            AnalyzerUtil.addExtraAttributeInRawIssues(preRawIssues, pathName);
            List <IssueInstance> preIssueInstance = new ArrayList<>();

            for (RawIssue preRawIssue : preRawIssues) {
                //新建issueCase
                IssueCase issueCase = new IssueCase(RawIssueType2IssueCaseType(preRawIssue.getType()), commitIdList[0]);
                int issueCaseId = insertIssueCase(issueCase);

                //新建issueInstance
                IssueInstance issueInstance = new IssueInstance(commitIdList[0], issueCaseId, IssueInstanceStatus.APPEAR, preRawIssue.getFileName());
                int issueInstanceId = insertIssueInstance(issueInstance);
                issueInstance.setIssueInstanceId(issueInstanceId);
                preIssueInstance.add(issueInstance);

                //新建Location
                for (int j = 0; j < preRawIssue.getLocations().size(); j++) {
                    Location rawLocation = preRawIssue.getLocations().get(j);
                    IssueLocation issueLocation = new IssueLocation(issueInstanceId, j, rawLocation.getStartLine(), rawLocation.getEndLine());
                    insertIssueLocation(issueLocation);
                }
            }

            for (int i = 1; i < commitIdList.length; i++) {
                int commitId = commitIdList[i];

                List<RawIssue> curRawIssues = sonar.getSonarResult(repositoryId, branchId, commitIdList[0]);
                AnalyzerUtil.addExtraAttributeInRawIssues(curRawIssues, pathName);
                List <IssueInstance> curIssueInstance = new ArrayList<>();
//                curRawIssues.forEach(iss->System.out.println(iss.getFileName()));


                RawIssueMatcher.match(preRawIssues, curRawIssues, addAllMethodsAndFields(pathName));

                for (RawIssue curRawIssue : curRawIssues) {
                    if (curRawIssue.getMappedRawIssue() == null) {
                        //新建issueCase
                        IssueCase issueCase = new IssueCase(RawIssueType2IssueCaseType(curRawIssue.getType()), commitId);
                        int issueCaseId = insertIssueCase(issueCase);

                        //新建issueInstance
                        IssueInstance issueInstance = new IssueInstance(commitId, issueCaseId, IssueInstanceStatus.APPEAR, curRawIssue.getFileName());
                        int issueInstanceId = insertIssueInstance(issueInstance);
                        issueInstance.setIssueInstanceId(issueInstanceId);
                        curIssueInstance.add(issueInstance);

                        //新建Location
                        for (int j = 0; j < curRawIssue.getLocations().size(); j++) {
                            Location rawLocation = curRawIssue.getLocations().get(j);
                            IssueLocation issueLocation = new IssueLocation(issueInstanceId, j, rawLocation.getStartLine(), rawLocation.getEndLine());
                            insertIssueLocation(issueLocation);
                        }
                    } else {
                        //新建issueInstance到相应的issueCase
                        IssueInstance issueInstance = new IssueInstance(commitId, getIssueCaseIdbyMatch(), IssueInstanceStatus.APPEAR, curRawIssue.getFileName());
                        int issueInstanceId = insertIssueInstance(issueInstance);
                        issueInstance.setIssueInstanceId(issueInstanceId);
                        curIssueInstance.add(issueInstance);

                        //新建Location
                        for (int j = 0; j < curRawIssue.getLocations().size(); j++) {
                            Location rawLocation = curRawIssue.getLocations().get(j);
                            IssueLocation issueLocation = new IssueLocation(issueInstanceId, j, rawLocation.getStartLine(), rawLocation.getEndLine());
                            insertIssueLocation(issueLocation);
                        }
                    }
                }

                for (int j = 0; j < preRawIssues.size(); j++) {
                    if (preRawIssues.get(j).getMappedRawIssue() == null) {
                        //已解决: 更新相应的issueCase
                        int issueCaseId = preIssueInstance.get(j).getIssueCaseId();
                        IssueCase issueCase = getIssueCasebyIssueCaseId(issueCaseId);
                        issueCase.setSolveCommitId(preIssueInstance.get(j).getCommitId());
                        updateIssueCase(issueCase);
                    }
                }

                for (RawIssue curRawIssue : curRawIssues) {
                    curRawIssue.resetMappedInfo();
                }

                preIssueInstance = curIssueInstance;
                preRawIssues = curRawIssues;

            }

        }
    }

    //递归遍历工程文件夹
    public Set<String> addAllMethodsAndFields(String pathName) throws IOException {
        File file = new File(pathName);
        File[] files = file.listFiles();
        Set<String> res = new HashSet<>();

        for(File file1 : files){
            if(file1.isDirectory()){
                Set<String> out = addAllMethodsAndFields(file1.getAbsolutePath());
                res.addAll(out);
            }
            else {
                res = AstParserUtil.getMethodsAndFieldsInFile(file1.getAbsolutePath());
            }
        }

        return res;
    }





    //CRUD
    //插入仓库
    private static int insertRepo(Repository repository) {
        /*
        （1）执行sql进行插入
        （2）返回repoId
         */


        return 0;
    }



    //插入分支
    private static int insertBranch(Branch branch) {
        /*
        （1）执行sql进行插入
        （2）返回branchId
         */



        return 0;
    }


    //插入commit
    private static int insertCommit(Commit commitForImport) {
        /*
        （1）执行sql进行插入
        （2）返回commitId
         */



        return 0;
    }



    //插入issueInstance
    private static int insertIssueInstance(IssueInstance issueInstance) {
        /*
        （1）执行sql进行插入
        （2）返回issueInstanceId
         */


        return 0;
    }



    //插入issueCase
    private static int insertIssueCase(IssueCase issueCase) {
        /*
        （1）执行sql进行插入
        （2）返回issueCaseId
         */


        return 0;
    }


    //插入Location
    private static void insertIssueLocation(IssueLocation issueLocation) {


    }






    //根据repoId 查找 branch 的 个数
    private static int getBranchNumByRepoId() {
        /*
        （1）执行sql
        （2）返回数目
         */


        return 0;
    }



    //根据branchId 返回 所有 的 commitId
    private static int[] getCommitIdByBranchId() {


        return new int[]{1, 2, 3};
    }


    //根据commitId 返回 所有 的 IssueInstance
    private static List<IssueInstance> getInstanceByCommitId(int lastCommitId) {

        return new List<IssueInstance>();
    }

    //根据commitId 返回 所有 的 commitHash
    private static String getCommitHashByCommitId(int commitId) {
        return "";
    }


    //根据IssueCaseId 返回 IssueCase
    private static IssueCase getIssueCasebyIssueCaseId(int issueCaseId) {
        return new IssueCase();
    }


    //更新IssueCase
    private static void updateIssueCase(IssueCase issueCase) {
    }


    //通过match 获得 IssueCaseId
    private static int getIssueCaseIdbyMatch() {
        return 0;
    }



}
