package object;

import cn.edu.fudan.issue.entity.dbo.Location;

import java.util.List;

enum IssueInstanceStatus {
    APPEAR, DISAPPEAR
}

public class IssueInstance {
    private int issueInstanceId;

    private int commitId;

    private int issueCaseId;

    private IssueInstanceStatus status;

    private String fileName;

    List<Location> locations;

    public int getIssueInstanceId() {
        return issueInstanceId;
    }

    public void setIssueInstanceId(int issueInstanceId) {
        this.issueInstanceId = issueInstanceId;
    }

    public int getCommitId() {
        return commitId;
    }

    public void setCommitId(int commitId) {
        this.commitId = commitId;
    }

    public int getIssueCaseId() {
        return issueCaseId;
    }

    public void setIssueCaseId(int issueCaseId) {
        this.issueCaseId = issueCaseId;
    }

    public IssueInstanceStatus getStatus() {
        return status;
    }

    public void setStatus(IssueInstanceStatus status) {
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
