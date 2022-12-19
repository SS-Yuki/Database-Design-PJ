package com.database.object;

import com.database.common.IssueCaseType;


public class IssueCase {
    private int issueCaseId;

    private IssueCaseType type;

    private int appearCommitId;
    private int solveCommitId;

    public IssueCase(IssueCaseType type, int appearCommitId) {
        this.type = type;
        this.appearCommitId = appearCommitId;
    }

    public IssueCase(int issueCaseId, IssueCaseType type, int appearCommitId, int solveCommitId) {
        this.issueCaseId = issueCaseId;
        this.type = type;
        this.appearCommitId = appearCommitId;
        this.solveCommitId = solveCommitId;
    }

    public int getIssueCaseId() {
        return issueCaseId;
    }

    public void setIssueCaseId(int issueCaseId) {
        this.issueCaseId = issueCaseId;
    }

    public IssueCaseType getType() {
        return type;
    }

    public void setType(IssueCaseType type) {
        this.type = type;
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
                ", type=" + type +
                ", appearCommitId=" + appearCommitId +
                ", solveCommitId=" + solveCommitId +
                '}';
    }
}
