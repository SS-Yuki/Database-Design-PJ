package com.database.utils;

import cn.edu.fudan.issue.entity.dbo.Location;
import cn.edu.fudan.issue.entity.dbo.RawIssue;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

    private static Location getLocation(int startLine, int endLine) {
        Location location = new Location();
        location.setStartLine(startLine);
        location.setEndLine(endLine);

        return location;
    }

    public List<Location> getLocations(JSONObject issue, String repoPath) throws Exception {
        int startLine = 0;
        int endLine = 0;
        String sonarPath;
        String[] sonarComponents;
        String filePath = null;
        List<Location> locations = new ArrayList<>();
        JSONArray flows = issue.getJSONArray("flows");
        if (flows.size() == 0) {
            //第一种针对issue中的textRange存储location
            JSONObject textRange = issue.getJSONObject("textRange");
            if (textRange != null) {
                startLine = textRange.getIntValue("startLine");
                endLine = textRange.getIntValue("endLine");
            } else {
                // 无 location 行号信息的 issue 过滤掉
                return new ArrayList<>();
            }

            sonarPath = issue.getString("component");
            if (sonarPath != null) {
                sonarComponents = sonarPath.split(":");
                if (sonarComponents.length >= 2) {
                    filePath = sonarComponents[sonarComponents.length - 1];
                }
            }

            Location mainLocation = getLocation(startLine, endLine);
            locations.add(mainLocation);
        } else {
            //第二种针对issue中的flows中的所有location存储
            for (int i = 0; i < flows.size(); i++) {
                JSONObject flow = flows.getJSONObject(i);
                JSONArray flowLocations = flow.getJSONArray("locations");
                //一个flows里面有多个locations， locations是一个数组，目前看sonar的结果每个locations都是一个location，但是不排除有多个。
                for (int j = 0; j < flowLocations.size(); j++) {
                    JSONObject flowLocation = flowLocations.getJSONObject(j);
                    String flowComponent = flowLocation.getString("component");
                    JSONObject flowTextRange = flowLocation.getJSONObject("textRange");
                    if (flowTextRange == null || flowComponent == null) {
                        continue;
                    }
                    int flowStartLine = flowTextRange.getIntValue("startLine");
                    int flowEndLine = flowTextRange.getIntValue("endLine");
                    String flowFilePath = null;

                    String[] flowComponents = flowComponent.split(":");
                    if (flowComponents.length >= 2) {
                        flowFilePath = flowComponents[flowComponents.length - 1];
                    }

                    Location location = getLocation(flowStartLine, flowEndLine);
                    locations.add(location);
                }
            }
        }

        return locations;
    }
}
