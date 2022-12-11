package sonar;

import cn.edu.fudan.issue.entity.dbo.RawIssue;
import object.*;

import java.util.*;


public class Test {

    public void main(String[] args) throws Exception {
        String baseDir = "";
        String pjName = "";
        importData(baseDir, pjName);
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















}







