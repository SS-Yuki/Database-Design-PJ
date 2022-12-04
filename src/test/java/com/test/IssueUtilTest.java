package com.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.database.utils.IssueUtil;
import org.junit.Test;

public class IssueUtilTest {

    @Test
    public void testGetSonarIssueResults() throws Exception{
        String key = "PlayMusic";
        JSONObject issueResults = IssueUtil.getSonarIssueResults(key);
        JSONArray issues = issueResults.getJSONArray("issues");
        int total = issueResults.getIntValue("total");
        System.out.println("total = " + total);
        System.out.println("issues.size() = " + issues.size());
        for (int i = 0; i < issues.size(); i++) {
            System.out.println(issues.get(i));
        }
    }

}
