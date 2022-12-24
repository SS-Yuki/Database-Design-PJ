package com.database.sonar.impl;

import cn.edu.fudan.issue.core.process.RawIssueMatcher;
import cn.edu.fudan.issue.entity.dbo.Location;
import cn.edu.fudan.issue.entity.dbo.RawIssue;
import cn.edu.fudan.issue.util.AnalyzerUtil;
import cn.edu.fudan.issue.util.AstParserUtil;
import com.database.common.IssueCaseStatus;
import com.database.common.IssueCaseType;
import com.database.common.IssueInstanceStatus;
import com.database.object.*;
import com.database.service.*;
import com.database.service.impl.*;
import com.database.sonar.SonarService;
import com.database.utils.EnumUtil;
import com.database.utils.GitUtil;
import com.database.utils.IssueUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SonarServiceImpl implements SonarService {

    private RepositoryService repositoryService = new RepositoryServiceImpl();
    private BranchService branchService = new BranchServiceImpl();
    private CommitService commitService = new CommitServiceImpl();
    private IssueCaseService caseService = new IssueCaseServiceImpl();
    private IssueInstanceService instanceService = new IssueInstanceServiceImpl();
    private IssueLocationService locationService = new IssueLocationServiceImpl();


    @Override
    public void showInstInfoByCommit(int commit) {
        List<IssueInstance> instances = instanceService.getInstByCommit(commit);
        showInstInfo(instances);
    }

    @Override
    public void showLatestInstInfo(int repositoryId, String branchName) {
        // 显示指定分支最新版本的缺陷信息
        // 1、查找分支id
        int branchId = branchService.getIdByNameAndRepoId(repositoryId, branchName);
        // 若返回id为0代表该分支不存在，return
        if (branchId == 0) {
            System.out.println("分支不存在！");
            return;
        }
        // 2、获取该分支的最新版本id
        int commitId = commitService.getLatestByBranchId(branchId);
        // 3、获取特定版本下的信息
        showInstInfoByCommit(commitId);
    }

    @Override
    public void showCaseInfoByCommit(int commit) {
        // 指定版本引入的
        List<IssueCase> appear = caseService.getCaseByAppearCommitId(commit);
        // 指定版本解决的
        List<IssueCase> solve = caseService.getCaseBySolveCommitId(commit);
        showAppearAndSolveInfo(appear, solve);
    }

    @Override
    public void showCaseInfoByTime(Date begin, Date end) {
        // 指定时间段内引入的
        List<IssueCase> appear = caseService.getCaseByAppearTime(begin, end);
        // 指定时间段内解决的
        List<IssueCase> solve = caseService.getCaseBySolveTime(begin, end);
        showAppearAndSolveInfo(appear, solve);
    }

    @Override
    public void showCaseInfoByCommiter(String commiter) {
        // 查找该commiter引入的
        List<IssueCase> appear = caseService.getCaseByAppearCommiter(commiter);
        // 查找该commiter解决的
        List<IssueCase> solve = caseService.getCaseBySolveCommiter(commiter);
        showAppearAndSolveInfo(appear, solve);
    }

    @Override
    public void showCaseInfoByDuration() {
        // 获取存续时长超过一定时间的issue
        long duration = 30 * 24 * 3600 * 1000;
        Map<IssueCase, Long> cases = caseService.getCaseByDurationTime(duration);
        List<IssueCase> caseList = new ArrayList<>();
        cases.forEach((issueCase, aLong) -> {
            caseList.add(issueCase);
        });
        Map<IssueCase, Commit> appearHashMap = new HashMap<>();
        Map<IssueCase, Commit> solveHashMap = new HashMap<>();
        getAppearAndSolveHashByCase(caseList, appearHashMap, solveHashMap);
        System.out.println("存续时间过长的缺陷：");
        caseList.forEach(issueCase -> {
            int day = (int) (cases.get(issueCase) / (24 * 3600 * 1000));
            System.out.printf("抽象缺陷id：%d，缺陷类型：%s，引入版本：%s，解决版本：%s，存续时间：约%d天",
                    issueCase.getIssueCaseId(), issueCase.getIssueCaseType(), appearHashMap.get(issueCase).getCommitHash(), solveHashMap.get(issueCase).getCommitHash(), day);
        });
    }

    @Override
    public boolean importRepository(String baseDir, String pjName) {
        String pathName = baseDir + File.separator + pjName;                // 获取仓库路径
        // 1、确定本地项目（即确定repo）
        Git git = null;
        try {
            git = Git.open(new File(pathName));
            // git.checkout().setCreateBranch(false).setName("main").call();   // 切换到主分支
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 2、完成repo，branch，commit的入库
        Repository repository = new Repository(0, baseDir, pjName);
        int repositoryId = repositoryService.insert(repository);
        // 获取所有的分支列表
        try {
            List<Ref> refs = git.branchList().call();
            // 遍历所有的branch
            for (Ref ref : refs) {
                // 获取该分支的基本信息
                String refName = ref.getName();
                if (refName.startsWith("refs/heads/")) {
                    String branchName = ref.getName().substring(GitUtil.GitBranchType.LOCAL.getPrefix().length());

                    Branch branch = new Branch(0, branchName, repositoryId);
                    // 将该分支信息入库
                    int branchId = branchService.insert(branch);
                    // 切换到该分支
                    git.checkout().setCreateBranch(false).setName(branchName).call();
                    // 从日志中获得commit信息
                    List<RevCommit> commits = new ArrayList<>();
                    for (RevCommit commit : git.log().call()) {
                        commits.add(commit);
                    }
                    System.out.println("分支" + branchName + "存在commit个数为" + commits.size());
                    // 遍历commit，进行入库与扫描（注！这里只完成扫描，不完成扫描信息的入库）

                    int firstCommitId = importCommitAndScanner(commits.get(commits.size()-1), branchId, repositoryId, pathName);

                    List<RawIssue> preRawIssues = IssueUtil.getSonarResult(repositoryId, branchId, firstCommitId);
                    List<IssueInstance> preIssueInstances = new ArrayList<>();

                    // 通过得到的 raw issue，封装case和instance
                    for (RawIssue preRawIssue : preRawIssues) {
                        // 封装成case并入库
                        IssueCase issueCase = new IssueCase(0, IssueCaseStatus.UNSOLVED, EnumUtil.IssueCaseTypeString2Enum(preRawIssue.getType()), firstCommitId, 0);
                        int caseId = caseService.insert(issueCase);

                        // 封装成instance并入库
                        IssueInstance preIssueInstance = importInstanceAndLocation(firstCommitId, caseId, IssueInstanceStatus.APPEAR, preRawIssue);
                        preIssueInstances.add(preIssueInstance);
                    }


                    for (int i = commits.size() - 2; i >= 0; i--) {
                        RevCommit commit = commits.get(i);
                        // new一个新的commit并将其入库
                        int commitId = importCommitAndScanner(commit, branchId, repositoryId, pathName);

                        GitUtil.gitCheckout(pathName, commit.getName());

                        // 获取该commit下的raw issue
                        List<RawIssue> curRawIssues = IssueUtil.getSonarResult(repositoryId, branchId, commitId);
                        List<IssueInstance> curIssueInstances = new ArrayList<>();

                        // 进行与pre的匹配，若匹配上则不新建case
                        AnalyzerUtil.addExtraAttributeInRawIssues(preRawIssues, pathName);
                        AnalyzerUtil.addExtraAttributeInRawIssues(curRawIssues, pathName);

//                        preRawIssues.forEach(iss-> System.out.println(iss.getLocations()));
//                        curRawIssues.forEach(iss-> System.out.println(iss.getLocations()));
                        RawIssueMatcher.match(preRawIssues, curRawIssues, addAllMethodsAndFields(pathName));

                        // 遍历并入库所有的instance，部分case
                        for (RawIssue curRawIssue : curRawIssues) {
                            // 没有匹配到前面的instance
                            if (curRawIssue.getMappedRawIssue() == null) {
                                // 封装case并入库
                                IssueCase issueCase = new IssueCase(0, IssueCaseStatus.UNSOLVED, EnumUtil.IssueCaseTypeString2Enum(curRawIssue.getType()), commitId, 0);
                                int caseId = caseService.insert(issueCase);
                                IssueInstance curIssueInstance = importInstanceAndLocation(commitId, caseId, IssueInstanceStatus.APPEAR, curRawIssue);
                                curIssueInstances.add(curIssueInstance);
                            }
                            else {
                                // 封装instance，并匹配到case
                                RawIssue mappedRawIssue = curRawIssue.getMappedRawIssue();
                                IssueInstance mappedIssueInstance = preIssueInstances.get(preRawIssues.indexOf(mappedRawIssue));
                                IssueInstance curIssueInstance = importInstanceAndLocation(commitId, mappedIssueInstance.getIssueCaseId(), IssueInstanceStatus.EVOLVE, curRawIssue);
                                curIssueInstances.add(curIssueInstance);
                            }
                        }

                        // 可能存在当前版本不存在，上个版本存在的问题，是在该版本被解决
                        for (int j = 0; j < preRawIssues.size(); j++) {
                            if (preRawIssues.get(j).getMappedRawIssue() == null) {
                                RawIssue preRawIssue = preRawIssues.get(j);
                                // 该问题已经被解决
                                IssueInstance preIssueInstance = preIssueInstances.get(j);
                                int caseId = preIssueInstance.getIssueCaseId();
                                IssueCase issueCase = caseService.getCaseById(caseId);
                                issueCase.setSolveCommitId(commitId);
                                issueCase.setIssueCaseStatus(IssueCaseStatus.SOLVED);
                                caseService.update(issueCase);

                                IssueInstance curIssueInstance = importInstanceAndLocation(commitId, caseId, IssueInstanceStatus.DISAPPEAR, preRawIssue);
                                curIssueInstances.add(curIssueInstance);
                            }
                        }

                        // issue已经入库完成，更新循环控制变量
                        for (RawIssue curRawIssue : curRawIssues) {
                            curRawIssue.resetMappedInfo();
                        }
                        preIssueInstances = curIssueInstances;
                        preRawIssues = curRawIssues;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 4、仓库，分支，版本的元信息处理完成，下面完成issue的入库（包括instance、case和location）
//        try {
//            importIssue(1, pathName);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return true;
    }

    private int importCommitAndScanner(RevCommit commit, int branchId, int repositoryId, String pathName) throws Exception {
        Commit commitInsert = new Commit(0, commit.getName(), commit.getAuthorIdent().getWhen(), commit.getAuthorIdent().getName(), branchId);
        int commitId = commitService.insert(commitInsert);

        // 3、对当前入库的commit代码进行扫描
        String commitHash = commitInsert.getCommitHash();
        // 切换扫描的版本commit
        GitUtil.gitCheckout(pathName, commitHash);
        IssueUtil.scannerCommit(pathName, repositoryId, branchId, commitId);
        return commitId;
    }

    public void importIssue(int repositoryId, String pathName) throws Exception {
        // 前面已经将仓库的元信息入库，该函数将issue case、instance与location入库
        // 确定仓库分支的数目
        int[] branchIdList = branchService.getIdByRepoId(repositoryId);
        Git git = Git.open(new File(pathName));
        // 遍历分支，逐分支逐commit入库issue
        for (int branchId:branchIdList) {
            if (branchId == 1) git.checkout().setName("main").call();
            else git.checkout().setName("test").call();
            // 获取该分支下的所有commit id（这里是由我们维护的id）
            int[] commitIdList = commitService.getIdByBranchId(branchId);
            // 先读出该分支下第一个commit的issue，入库instance和case
            try {
                git.checkout().setName(commitService.getHashById(commitIdList[0])).call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<RawIssue> preRawIssues = IssueUtil.getSonarResult(repositoryId, branchId, commitIdList[0]);
            List<IssueInstance> preIssueInstances = new ArrayList<>();

            // 通过得到的 raw issue，封装case和instance
            for (RawIssue preRawIssue : preRawIssues) {
                // 封装成case并入库
                IssueCase issueCase = new IssueCase(0, IssueCaseStatus.UNSOLVED, EnumUtil.IssueCaseTypeString2Enum(preRawIssue.getType()), commitIdList[0], 0);
                int caseId = caseService.insert(issueCase);

                // 封装成instance并入库
                IssueInstance preIssueInstance = importInstanceAndLocation(commitIdList[0], caseId, IssueInstanceStatus.APPEAR, preRawIssue);
                preIssueInstances.add(preIssueInstance);
            }

            // 开始处理该分支下后续的版本号
            for (int i = 1; i < commitIdList.length; i++) {
                int commitId = commitIdList[i];
                try {
                    git.checkout().setName(commitService.getHashById(commitId)).call();
                    System.out.println(commitId + "--" + commitService.getHashById(commitId));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 获取该commit下的raw issue
                List<RawIssue> curRawIssues = IssueUtil.getSonarResult(repositoryId, branchId, commitId);
                List<IssueInstance> curIssueInstances = new ArrayList<>();

                // 进行与pre的匹配，若匹配上则不新建case
                AnalyzerUtil.addExtraAttributeInRawIssues(preRawIssues, pathName);
                AnalyzerUtil.addExtraAttributeInRawIssues(curRawIssues, pathName);
                System.out.println(preRawIssues);
                System.out.println(curRawIssues);

                RawIssueMatcher.match(preRawIssues, curRawIssues, addAllMethodsAndFields(pathName));

                // 遍历并入库所有的instance，部分case
                for (RawIssue curRawIssue : curRawIssues) {
                    // 没有匹配到前面的instance
                    if (curRawIssue.getMappedRawIssue() == null) {
                        // 封装case并入库
                        IssueCase issueCase = new IssueCase(0, IssueCaseStatus.UNSOLVED, EnumUtil.IssueCaseTypeString2Enum(curRawIssue.getType()), commitId, 0);
                        int caseId = caseService.insert(issueCase);
                        IssueInstance curIssueInstance = importInstanceAndLocation(commitId, caseId, IssueInstanceStatus.APPEAR, curRawIssue);
                        curIssueInstances.add(curIssueInstance);
                    }
                    else {
                        // 封装instance，并匹配到case
                        RawIssue mappedRawIssue = curRawIssue.getMappedRawIssue();
                        IssueInstance mappedIssueInstance = preIssueInstances.get(preRawIssues.indexOf(mappedRawIssue));
                        IssueInstance curIssueInstance = importInstanceAndLocation(commitId, mappedIssueInstance.getIssueCaseId(), IssueInstanceStatus.EVOLVE, curRawIssue);
                        curIssueInstances.add(curIssueInstance);
                    }
                }

                // 可能存在当前版本不存在，上个版本存在的问题，是在该版本被解决
                for (int j = 0; j < preRawIssues.size(); j++) {
                    if (preRawIssues.get(j).getMappedRawIssue() == null) {
                        RawIssue preRawIssue = preRawIssues.get(j);
                        // 该问题已经被解决
                        IssueInstance preIssueInstance = preIssueInstances.get(j);
                        int caseId = preIssueInstance.getIssueCaseId();
                        IssueCase issueCase = caseService.getCaseById(caseId);
                        issueCase.setSolveCommitId(commitId);
                        issueCase.setIssueCaseStatus(IssueCaseStatus.SOLVED);
                        caseService.update(issueCase);

                        IssueInstance curIssueInstance = importInstanceAndLocation(commitId, caseId, IssueInstanceStatus.DISAPPEAR, preRawIssue);
                        curIssueInstances.add(curIssueInstance);
                    }
                }

                // issue已经入库完成，更新循环控制变量
                for (RawIssue curRawIssue : curRawIssues) {
                    curRawIssue.resetMappedInfo();
                }
                preIssueInstances = curIssueInstances;
                preRawIssues = curRawIssues;
            }
        }
    }

    //插入instance和location
    private IssueInstance importInstanceAndLocation(int commitId, int caseId, IssueInstanceStatus status, RawIssue issue) {
        // 封装instance并入库
        IssueInstance issueInstance = new IssueInstance(0, commitId, caseId, status, issue.getFileName());
        int instanceId = instanceService.insert(issueInstance);
        issueInstance.setIssueInstanceId(instanceId);

        // 封装location并入库
        for (int i = 0; i < issue.getLocations().size(); i++) {
            Location rawLocation = issue.getLocations().get(i);
            IssueLocation issueLocation = new IssueLocation(instanceId, i, rawLocation.getStartLine(), rawLocation.getEndLine());
            locationService.insert(issueLocation);
        }

        return issueInstance;
    }

    //递归遍历工程文件夹
    private Set<String> addAllMethodsAndFields(String pathName) throws IOException {
        File file = new File(pathName);
        File[] files = file.listFiles();
        Set<String> res = new HashSet<>();

        for(File file1 : files){
            if(file1.isDirectory()){
                Set<String> out = addAllMethodsAndFields(file1.getAbsolutePath());
                res.addAll(out);
            }
            else {
                String name = file1.getName();
                if (name.endsWith(".java")) {
                    res.addAll(AstParserUtil.getMethodsAndFieldsInFile(file1.getAbsolutePath()));
                }
            }
        }
        return res;
    }

    private Map<IssueCaseType, List<IssueInstance>> classifyByType(List<IssueInstance> instances) {
        Map<IssueCaseType, List<IssueInstance>> map = new HashMap<>();
        map.put(IssueCaseType.BUG, new ArrayList<>());
        map.put(IssueCaseType.CODE_SMELL, new ArrayList<>());
        map.put(IssueCaseType.VULNERABILITY, new ArrayList<>());
        instances.forEach(issueInstance -> {
            IssueCaseType type = instanceService.getTypeById(issueInstance.getIssueInstanceId());
            map.get(type).add(issueInstance);
        });
        return map;
    }

    private List<Map.Entry<IssueInstance, Long>> sortByTime(Map<IssueInstance, Long> map) {
        // 按照time进行排序
        List<Map.Entry<IssueInstance, Long>> result = new ArrayList<>(map.entrySet());
        Collections.sort(result, (a, b) -> {
            long diff = a.getValue() - b.getValue();
            if (diff > 0) return 1;
            else if (diff == 0) return 0;
            else return -1;
        });
        return result;
    }

    private void getAppearAndSolveHashByCase(List<IssueCase> cases, Map<IssueCase, Commit> appearMap, Map<IssueCase, Commit> solveMap) {
        cases.forEach(issueCase -> {
            Commit appearHash = caseService.getAppearCommitById(issueCase.getIssueCaseId());
            Commit solveHash = caseService.getSolveCommitById(issueCase.getIssueCaseId());
            appearMap.put(issueCase, appearHash);
            solveMap.put(issueCase, solveHash);
        });
    }

    private void showAppearAndSolveInfo(List<IssueCase> appear, List<IssueCase> solve) {
        // 引入的
        Map<IssueCase, Commit> a_appearHashMap = new HashMap<>();
        Map<IssueCase, Commit> a_solveHashMap = new HashMap<>();
        getAppearAndSolveHashByCase(appear, a_appearHashMap, a_solveHashMap);
        System.out.println("引入的缺陷情况：");
        appear.forEach(issueCase -> {
            Commit appearCommit = a_appearHashMap.get(issueCase);
            Commit solveCommit = a_solveHashMap.get(issueCase);
            long time = 0;
            if (solveCommit == null) time = (new Date().getTime() - appearCommit.getCommitTime().getTime());
            else time = solveCommit.getCommitTime().getTime() - appearCommit.getCommitTime().getTime();
            int day = (int)(time / (24 * 3600 * 1000)) + 1;
            if (solveCommit == null) {
                System.out.printf("抽象缺陷id：%d，缺陷类型：%s，引入版本：%s，未解决，存续时间：约%d天\n",
                        issueCase.getIssueCaseId(), issueCase.getIssueCaseType(), appearCommit.getCommitHash(), day);
            }
            else {
                System.out.printf("抽象缺陷id：%d，缺陷类型：%s，引入版本：%s，解决版本：%s，存续时间：约%d天\n",
                        issueCase.getIssueCaseId(), issueCase.getIssueCaseType(), appearCommit.getCommitHash(), solveCommit.getCommitHash(), day);
            }

        });
        System.out.printf("共引入%d个新的缺陷\n", appear.size());

        // 解决的
        Map<IssueCase, Commit> s_appearHashMap = new HashMap<>();
        Map<IssueCase, Commit> s_solveHashMap = new HashMap<>();
        getAppearAndSolveHashByCase(solve, s_appearHashMap, s_solveHashMap);
        System.out.println("解决的缺陷情况：");
        solve.forEach(issueCase -> {
            Commit appearCommit = s_appearHashMap.get(issueCase);
            Commit solveCommit = s_solveHashMap.get(issueCase);
            long time = 0;
            time = solveCommit.getCommitTime().getTime() - appearCommit.getCommitTime().getTime();
            int day = (int)(time / (24 * 3600 * 1000)) + 1;
            System.out.printf("抽象缺陷id：%d，缺陷类型：%s，引入版本：%s，解决版本：%s，存续时间：约%d天\n",
                    issueCase.getIssueCaseId(), issueCase.getIssueCaseType(), appearCommit.getCommitHash(), solveCommit.getCommitHash(), day);
        });
        System.out.printf("共解决%d个缺陷\n", solve.size());
    }

    private void showInstInfo(List<IssueInstance> instances) {
        // 1、获取缺陷的详细信息，包括
        Map<IssueInstance, List<IssueLocation>> locationMap = new HashMap<>();
        Map<IssueInstance, Date> appearTimeMap = new HashMap<>();
        Map<IssueInstance, Date> solveTimeMap = new HashMap<>();
        Map<IssueInstance, Long> durationTimeMap = new HashMap<>();
        Map<IssueInstance, IssueCaseType> typeMap = new HashMap<>();
        instances.forEach(issueInstance -> {
            int caseId = issueInstance.getIssueCaseId();
            // 查找对应的location
            List<IssueLocation> locations = locationService.getLocationByInstId(issueInstance.getIssueInstanceId());
            locationMap.put(issueInstance, locations);
            // 查找对应的appear time
            Date appearTime = instanceService.getAppearTimeById(issueInstance.getIssueInstanceId());
            appearTimeMap.put(issueInstance, appearTime);
            // 查找对应的solve time
            Date solveTime = instanceService.getSolveTimeById(issueInstance.getIssueInstanceId());
            solveTimeMap.put(issueInstance, solveTime);
            // 获取对应的duration time
            long time = (solveTime == null) ? (new Date().getTime() - appearTime.getTime()) : (solveTime.getTime() - appearTime.getTime());
            durationTimeMap.put(issueInstance, time);
            // 获取对应的type
            IssueCaseType type = instanceService.getTypeById(issueInstance.getIssueInstanceId());
            typeMap.put(issueInstance, type);
        });

        // 2、显示缺陷的详细信息
        System.out.printf("该版本中共有静态缺陷%d个\n", instances.size());
        instances.forEach(issueInstance -> {
            int day = (int)(durationTimeMap.get(issueInstance) / (24 * 3600 * 1000));
            System.out.printf("缺陷实例id: %d，缺陷类型：%s，引入时间：%s，解决时间：%s，存续时间：约%d天，缺陷所在文件：%s，缺陷位置：%s\n",
                    issueInstance.getIssueInstanceId(), typeMap.get(issueInstance), appearTimeMap.get(issueInstance), solveTimeMap.get(issueInstance), day, issueInstance.getFileName(), locationMap.get(issueInstance));
        });

        // 3、按类型分类
        Map<IssueCaseType, List<IssueInstance>> classifyInstances = classifyByType(instances);
        System.out.println("按类型分类（只显示缺陷实例id）：");
        classifyInstances.forEach((issueCaseType, instances1) -> {
            System.out.printf("缺陷类型：%s, 总计: %d\n", issueCaseType, instances1.size());
            System.out.printf("缺陷id：[");
            instances1.forEach(issueInstance -> {
                System.out.printf("%d, ", issueInstance.getIssueInstanceId());
            });
            System.out.printf("]\n");
        });

        // 4、按照存续时长排序
        List<Map.Entry<IssueInstance, Long>> sortInstances = sortByTime(durationTimeMap);
        System.out.println("按缺陷存在时间排序（只显示缺陷实例id）：");
        sortInstances.forEach(issueInstanceLongEntry -> {
            IssueInstance key = issueInstanceLongEntry.getKey();
            Long value = issueInstanceLongEntry.getValue();
            int day = (int)(value / (24 * 3600 * 1000));
            System.out.printf("缺陷实例id：%d，存续时间：约%d天\n", key.getIssueInstanceId(), day);
        });
    }
}

