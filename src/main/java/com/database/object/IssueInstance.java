package com.database.object;

import com.database.common.IssueInstanceStatus;

public class IssueInstance {
    private int issueInstanceId;

    private int commitId;

    private int issueCaseId;

    private IssueInstanceStatus issueInstanceStatus;

    private String fileName;

    public IssueInstance() {
    }

    public IssueInstance(int issueInstanceId, int commitId, int issueCaseId, IssueInstanceStatus issueInstanceStatus, String fileName) {
        this.issueInstanceId = issueInstanceId;
        this.commitId = commitId;
        this.issueCaseId = issueCaseId;
        this.issueInstanceStatus = issueInstanceStatus;
        this.fileName = fileName;
    }

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

    public IssueInstanceStatus getIssueInstanceStatus() {
        return issueInstanceStatus;
    }

    public void setIssueInstanceStatus(IssueInstanceStatus issueInstanceStatus) {
        this.issueInstanceStatus = issueInstanceStatus;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "IssueInstance{" +
                "issueInstanceId=" + issueInstanceId +
                ", commitId=" + commitId +
                ", issueCaseId=" + issueCaseId +
                ", issueInstanceStatus=" + issueInstanceStatus +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
