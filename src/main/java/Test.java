import cn.edu.fudan.issue.core.process.RawIssueMatcher;
import cn.edu.fudan.issue.entity.dbo.Location;
import cn.edu.fudan.issue.entity.dbo.RawIssue;
import cn.edu.fudan.issue.util.AnalyzerUtil;
import cn.edu.fudan.issue.util.AstParserUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import object.*;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;



public class Test {

    public static void main(String[] args) throws Exception {
        String baseDir = "";
        String pjName = "";
        importData(baseDir, pjName);
    }


    private static void importData(String baseDir, String pjName) throws Exception {
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
                String cdStr = "cmd /c cd " + pathName;
                String componentKeys = "repositoryId" + repositoryId + "_" + "branchId" + branchId + "_" + "commitId" + commitId;
                String scannerStr = "sonar-scanner -D sonar.projectKey=" + componentKeys;
//                Runtime.getRuntime().exec(cdStr);
//                Cmd.run(cdStr);
                System.out.println(componentKeys);
                System.out.println(Cmd.run(cdStr + " && " + scannerStr));
//                Process process = Runtime.getRuntime().exec(cdStr + " && " + scannerStr);
//                Process process = Runtime.getRuntime().exec(cdStr + " && dir");
//                process.waitFor( 10, TimeUnit.SECONDS);
                System.out.println(componentKeys);
            }
        }


//        4.从最早的commit开始进行case和instance的入库
        insertIssue(repositoryId, pathName);
    }







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





    //IssueInstances -> RawIssues
    public static List<RawIssue> IssueInstancesToRawIssues(List<IssueInstance> issueInstances) {
        List<RawIssue> rawIssues = new ArrayList<RawIssue>();
        for (int i = 0; i < issueInstances.size(); i++) {
            RawIssue rawIssue = new RawIssue();

            rawIssue.setType("");
            rawIssue.setFileName(issueInstances.get(i).getFileName());
            rawIssue.setDetail("");
            rawIssue.setLocations(issueInstances.get(i).getLocations());
            rawIssue.setCommitId(getCommitHashByCommitId(issueInstances.get(i).getCommitId()));

            rawIssues.add(rawIssue);
        }
        return rawIssues;
    }



    private static void insertIssue(int repositoryId, String pathName) throws Exception {
        int branchNum = getBranchNumByRepoId();
        for (int branchId = 0; branchId < branchNum; branchId++) {
            int [] commitIdList = getCommitIdByBranchId();

            for (int i = 0; i < commitIdList.length; i++) {

                int commitId = commitIdList[i];
                List<RawIssue> resultRawIssues = getSonarResult(repositoryId, branchId, commitId);
                AnalyzerUtil.addExtraAttributeInRawIssues(resultRawIssues, pathName);
//                resultRawIssues.forEach(iss->System.out.println(iss.getFileName()));

                if (i == 0) {
                    for (RawIssue resultRawIssue : resultRawIssues) {
                        //新建issueCase
                        IssueCase issueCase = new IssueCase();
                        issueCase.setAppearCommitId(commitId);
                        issueCase.setType(RawIssueType2IssueCaseType(resultRawIssue.getType()));

                        int issueCaseId = insertIssueCase(issueCase);

                        //新建issueInstance
                        IssueInstance issueInstance = new IssueInstance();
                        issueInstance.setCommitId(commitId);
                        issueInstance.setFileName(resultRawIssue.getFileName());
                        issueInstance.setLocations(resultRawIssue.getLocations());
                        issueInstance.setStatus(IssueInstanceStatus.APPEAR);
                        issueInstance.setIssueCaseId(issueCaseId);

                        int issueInstanceId = insertIssueInstance(issueInstance);


                        //新建Location
                        for (int j = 0; j < resultRawIssue.getLocations().size(); j++) {
                            Location rawLocation = resultRawIssue.getLocations().get(j);
                            IssueLocation issueLocation = new IssueLocation();

                            issueLocation.setIssueInstanceId(issueInstanceId);
                            issueLocation.setOrder(j);
                            issueLocation.setStartLine(rawLocation.getStartLine());
                            issueLocation.setEndLine(rawLocation.getEndLine());

                            insertIssueLocation(issueLocation);
                        }
                    }
                    continue;
                }

                int lastCommitId = commitIdList[i-1];
                List<IssueInstance> lastInstances = getInstanceByCommitId(lastCommitId);
                List<RawIssue> lastRawIssues = IssueInstancesToRawIssues(lastInstances);
                AnalyzerUtil.addExtraAttributeInRawIssues(lastRawIssues, pathName);

                Set<String> methodsAndFields = new HashSet();
                addAllMethodsAndFields(methodsAndFields, pathName);

                RawIssueMatcher.match(lastRawIssues, resultRawIssues, methodsAndFields);

                for (RawIssue resultRawIssue : resultRawIssues) {
                    if (resultRawIssue.getMappedRawIssue() == null) {
                        //新建issueCase
                        IssueCase issueCase = new IssueCase();
                        issueCase.setAppearCommitId(commitId);
                        issueCase.setType(RawIssueType2IssueCaseType(resultRawIssue.getType()));

                        int issueCaseId = insertIssueCase(issueCase);

                        //新建issueInstance
                        IssueInstance issueInstance = new IssueInstance();
                        issueInstance.setCommitId(commitId);
                        issueInstance.setFileName(resultRawIssue.getFileName());
                        issueInstance.setLocations(resultRawIssue.getLocations());
                        issueInstance.setStatus(IssueInstanceStatus.APPEAR);
                        issueInstance.setIssueCaseId(issueCaseId);

                        int issueInstanceId = insertIssueInstance(issueInstance);


                        //新建Location
                        for (int j = 0; j < resultRawIssue.getLocations().size(); j++) {
                            Location rawLocation = resultRawIssue.getLocations().get(j);
                            IssueLocation issueLocation = new IssueLocation();

                            issueLocation.setIssueInstanceId(issueInstanceId);
                            issueLocation.setOrder(j);
                            issueLocation.setStartLine(rawLocation.getStartLine());
                            issueLocation.setEndLine(rawLocation.getEndLine());

                            insertIssueLocation(issueLocation);
                        }
                    } else {
                        //新建issueInstance到相应的issueCase
                        IssueInstance issueInstance = new IssueInstance();
                        issueInstance.setCommitId(commitId);
                        issueInstance.setFileName(resultRawIssue.getFileName());
                        issueInstance.setLocations(resultRawIssue.getLocations());
                        issueInstance.setStatus(IssueInstanceStatus.APPEAR);
                        issueInstance.setIssueCaseId(getIssueCaseIdbyMatch());

                        int issueInstanceId = insertIssueInstance(issueInstance);


                        //新建Location
                        for (int j = 0; j < resultRawIssue.getLocations().size(); j++) {
                            Location rawLocation = resultRawIssue.getLocations().get(j);
                            IssueLocation issueLocation = new IssueLocation();

                            issueLocation.setIssueInstanceId(issueInstanceId);
                            issueLocation.setOrder(j);
                            issueLocation.setStartLine(rawLocation.getStartLine());
                            issueLocation.setEndLine(rawLocation.getEndLine());

                            insertIssueLocation(issueLocation);
                        }
                    }
                }

                for (int j = 0; j < lastRawIssues.size(); j++) {
                    if (lastRawIssues.get(j).getMappedRawIssue() == null) {
                        //已解决: 更新相应的issueCase
                        int issueCaseId = lastInstances.get(j).getIssueCaseId();
                        IssueCase issueCase = getIssueCasebyIssueCaseId(issueCaseId);
                        issueCase.setSolveCommitId(lastInstances.get(j).getCommitId());
                        updateIssueCase(issueCase);
                    }
                }

            }

        }
    }



    //根据Id 从云端获取所有的Json, 并解析成RawIssue
    public static List<RawIssue> getSonarResult(int repositoryId, int branchId, int commitId) throws Exception {
        //获取issue数量
        List<RawIssue> resultRawIssues = new ArrayList<RawIssue>();
        JSONObject sonarResult = getSonarIssueResults("repositoryId" + repositoryId + "_" + "branchId" + branchId + "_" + "commitId" + commitId);
        JSONArray sonarRawIssues = sonarResult.getJSONArray("issues");
        for (int j = 0; j < sonarRawIssues.size(); j++) {
            JSONObject sonarIssue = sonarRawIssues.getJSONObject(j);

            String component = sonarIssue.getString("component");
            if (!component.endsWith(".java")) {
                continue;
            }

            List<Location> locations = getLocations(sonarIssue);
            if (locations.isEmpty()) {
                continue;
            }

            RawIssue rawIssue = new RawIssue();

            rawIssue.setType(sonarIssue.getString("type"));
            rawIssue.setFileName(getFileName(sonarIssue));
            rawIssue.setDetail(sonarIssue.getString("message"));
            rawIssue.setLocations(locations);
            rawIssue.setCommitId(getCommitHashByCommitId(commitId));

            String rawIssueUuid = RawIssue.generateRawIssueUUID(rawIssue);
            rawIssue.setUuid(rawIssueUuid);

            resultRawIssues.add(rawIssue);
        }
        return resultRawIssues;
    }




    //根据Id 从云端获取Json数据
    private static final String SEARCH_API = "http://127.0.0.1:9000/api/issues/search";
    private static final String AUTHORIZATION = "Basic YWRtaW46MTIzNDU2Nzg=";
    public static JSONObject getSonarIssueResults(String componentKeys) throws IOException {

        String url_s = SEARCH_API + "?" + "componentKeys=" + componentKeys;
        URL url = new URL(url_s);

        URLConnection connection = url.openConnection();

        connection.setConnectTimeout(5000);
        connection.setReadTimeout(15000);

        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setRequestProperty("authorization", AUTHORIZATION);

        connection.connect();

        //读返回值
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = in.readLine()) != null) {
            result.append(line);
        }
        in.close();

        return JSONObject.parseObject(result.toString());
    }


    //解析Json数据中的location信息
    public static List<Location> getLocations(JSONObject issue) throws Exception {
        int startLine = 0;
        int endLine = 0;
        List<Location> locations = new ArrayList<>();
        JSONArray flows = issue.getJSONArray("flows");
        if (flows.size() == 0) {
            JSONObject textRange = issue.getJSONObject("textRange");
            if (textRange != null) {
                startLine = textRange.getIntValue("startLine");
                endLine = textRange.getIntValue("endLine");
            } else {
                return new ArrayList<>();
            }

            Location mainLocation = getLocation(startLine, endLine);
            locations.add(mainLocation);
        }
        else {
            for (int i = 0; i < flows.size(); i++) {
                JSONObject flow = flows.getJSONObject(i);
                JSONArray flowLocations = flow.getJSONArray("locations");

                for (int j = 0; j < flowLocations.size(); j++) {
                    JSONObject flowLocation = flowLocations.getJSONObject(j);
//                    String flowComponent = flowLocation.getString("component");
                    JSONObject flowTextRange = flowLocation.getJSONObject("textRange");
                    if (flowTextRange == null) {
                        continue;
                    }

                    int flowStartLine = flowTextRange.getIntValue("startLine");
                    int flowEndLine = flowTextRange.getIntValue("endLine");

                    Location location = getLocation(flowStartLine, flowEndLine);
                    locations.add(location);
                }
            }
        }

        return locations;
    }

    private static Location getLocation(int startLine, int endLine) {
        Location location = new Location();
        location.setStartLine(startLine);
        location.setEndLine(endLine);

        return location;
    }

    //解析Json数据中的FileName
    public static String getFileName(JSONObject issue) {
        String sonarPath;
        String[] sonarComponents;
        String filePath = null;

        sonarPath = issue.getString("component");
        if (sonarPath != null) {
            sonarComponents = sonarPath.split(":");
            if (sonarComponents.length >= 2) {
                filePath = sonarComponents[sonarComponents.length - 1];
            }
        }

        return filePath;
    }





    //递归遍历工程文件夹
    public static void addAllMethodsAndFields(Set<String> methodsAndFields, String pathName) throws IOException {
        File file = new File(pathName);
        File[] files = file.listFiles();

        for(File file1 : files){
            if(file1.isDirectory()){
                addAllMethodsAndFields(methodsAndFields, file1.getAbsolutePath());
            }
            else {
                methodsAndFields.addAll(AstParserUtil.getMethodsAndFieldsInFile(file1.getAbsolutePath()));
            }
        }
    }

    //RawIssueType -> IssueCaseType
    private static IssueCaseType RawIssueType2IssueCaseType(String type) {
        IssueCaseType issueCaseType = IssueCaseType.BUG;
        switch(type){
            case "BUG" :
                issueCaseType = IssueCaseType.BUG;
                break;
            case "VULNERABILITY" :
                issueCaseType = IssueCaseType.VULNERABILITY;
                break;
            case "CODE_SMELL" :
                issueCaseType = IssueCaseType.CODE_SMELL;
                break;
            default :
                break;
        }
        return issueCaseType;
    }



}







