package com.database.object;

import com.database.common.IssueCaseStatus;
import com.database.common.IssueCaseType;

public class IssueCase {
    private int issueCaseId;

    private IssueCaseStatus issueCaseStatus;
    private IssueCaseType issueCaseType;

    private int appearCommitId;
    private int solveCommitId;

    public IssueCase() {
    }

    public IssueCase(int issueCaseId, IssueCaseStatus issueCaseStatus, IssueCaseType issueCaseType, int appearCommitId, int solveCommitId) {
        this.issueCaseId = issueCaseId;
        this.issueCaseStatus = issueCaseStatus;
        this.issueCaseType = issueCaseType;
        this.appearCommitId = appearCommitId;
        this.solveCommitId = solveCommitId;
    }

    public int getIssueCaseId() {
        return issueCaseId;
    }

    public void setIssueCaseId(int issueCaseId) {
        this.issueCaseId = issueCaseId;
    }

    public IssueCaseStatus getIssueCaseStatus() {
        return issueCaseStatus;
    }

    public void setIssueCaseStatus(IssueCaseStatus issueCaseStatus) {
        this.issueCaseStatus = issueCaseStatus;
    }

    public IssueCaseType getIssueCaseType() {
        return issueCaseType;
    }

    public void setIssueCaseType(IssueCaseType issueCaseType) {
        this.issueCaseType = issueCaseType;
    }

    public int getAppearCommitId() {
        return appearCommitId;
    }

    public void setAppearCommitId(int appearCommitId) {
        this.appearCommitId = appearCommitId;
    }

    public int getSolveCommitId() {
        return solveCommitId;
    }

    public void setSolveCommitId(int solveCommitId) {
        this.solveCommitId = solveCommitId;
    }

    @Override
    public String toString() {
        return "IssueCase{" +
                "issueCaseId=" + issueCaseId +
                ", issueCaseStatus=" + issueCaseStatus +
                ", issueCaseType=" + issueCaseType +
                ", appearCommitId=" + appearCommitId +
                ", solveCommitId=" + solveCommitId +
                '}';
    }
}
