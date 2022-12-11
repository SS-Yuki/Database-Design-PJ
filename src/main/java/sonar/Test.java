package sonar;

import Utils.GitUtil;
import cn.edu.fudan.issue.core.process.RawIssueMatcher;
import cn.edu.fudan.issue.entity.dbo.Location;
import cn.edu.fudan.issue.entity.dbo.RawIssue;
import cn.edu.fudan.issue.util.AnalyzerUtil;
import cn.edu.fudan.issue.util.AstParserUtil;
import common.IssueCaseType;
import common.IssueInstanceStatus;
import object.*;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import sonar.Sonar;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static Utils.EnumUtil.RawIssueType2IssueCaseType;


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







