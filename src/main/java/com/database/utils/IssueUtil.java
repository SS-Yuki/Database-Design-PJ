package com.database.utils;

import cn.edu.fudan.issue.entity.dbo.Location;
import cn.edu.fudan.issue.entity.dbo.RawIssue;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.database.service.CommitService;
import com.database.service.impl.CommitServiceImpl;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

public class IssueUtil {

    private static final String SEARCH_API = "http://127.0.0.1:9000/api/issues/search";
    private static String AUTHORIZATION = "Basic ";
    static List <RawIssue> resultRawIssues = new ArrayList<RawIssue>();

    static {
        InputStream is = ClassLoader.getSystemResourceAsStream("sonar.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
            String auth = properties.getProperty("sonar.username") + ":" + properties.getProperty("sonar.password");
            AUTHORIZATION += Base64.getEncoder().encodeToString(auth.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //扫描单个commit
    public static void scannerCommit(String pathName, int repositoryId, int branchId, int commitId) throws IOException {
        String cdStr = "cmd /c cd " + pathName;
        String componentKeys = "repositoryId" + repositoryId + "_" + "branchId" + branchId + "_" + "commitId" + commitId;
        String scannerStr = "sonar-scanner -D sonar.projectKey=" + componentKeys;
        CmdUtil.run(cdStr + " && " + scannerStr);
        System.out.println(componentKeys + "扫描完成");
    }

    //根据Id 从云端获取所有的Json, 并解析成RawIssue
    public static List<RawIssue> getSonarResult(int repositoryId, int branchId, int commitId) throws Exception {
        List<RawIssue> resultRawIssues = new ArrayList<RawIssue>();
        String componentKeys = "repositoryId" + repositoryId + "_" + "branchId" + branchId + "_" + "commitId" + commitId;
        JSONObject sonarIssueResult = getSonarIssueResults(componentKeys, 1);
        //获取issue数量
        int pageSize = 100;
        int issueTotal = sonarIssueResult.getIntValue("total");
        System.out.println("total:" + issueTotal);
        int pages = issueTotal % pageSize > 0 ? issueTotal / pageSize + 1 : issueTotal / pageSize;
        for (int i = 1; i <= pages; i++) {
            JSONObject sonarResult = getSonarIssueResults(componentKeys, i);
            JSONArray sonarRawIssues = sonarResult.getJSONArray("issues");
            System.out.println("page" + i + ":" + sonarRawIssues.size());
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
                CommitService commitService = new CommitServiceImpl();

                rawIssue.setType(sonarIssue.getString("type"));
                rawIssue.setFileName(getFileName(sonarIssue));
                rawIssue.setDetail(sonarIssue.getString("message"));
                rawIssue.setLocations(locations);
                rawIssue.setCommitId(commitService.getHashById(commitId));

                rawIssue.setUuid(String.format("%d_%d_%d_%d", repositoryId, branchId, commitId, pageSize*i+j));

                resultRawIssues.add(rawIssue);
            }
        }
        System.out.println(componentKeys + "得到原始缺陷共计" + resultRawIssues.size() + "个");

        return resultRawIssues;
    }

    private static JSONObject getSonarIssueResults(String componentKeys, int page) throws IOException {


        String url_s = SEARCH_API + "?" + "componentKeys=" + componentKeys + "&p=" + page + "&additionalFields=_all&s=FILE_LINE&resolved=false";
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

    private static Location getLocation(int startLine, int endLine) {
        Location location = new Location();
        location.setStartLine(startLine);
        location.setEndLine(endLine);

        return location;
    }

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

                    String flowComponent = flowLocation.getString("component");
                    JSONObject flowTextRange = flowLocation.getJSONObject("textRange");
                    if (flowTextRange == null || flowComponent == null) {
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

    //解析Json数据中的FileName
    private static String getFileName(JSONObject issue) {
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

}
