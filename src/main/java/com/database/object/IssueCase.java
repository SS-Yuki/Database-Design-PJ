package com.database.object;

import java.util.Date;

enum IssueCaseSeverity {
    BLOCKER, MINOR, CRITICAL, INFO, MAJOR
}

enum IssueCaseType {
    BUG, VULNERABILITY, CODE_SMELL
}

enum IssueCaseStatus {
    SOLVED, UNSOLVED
}

public class IssueCase {
    private int issueCaseId;

    private IssueCaseSeverity severity;
    private IssueCaseType type;

    private int appearCommitId;
    private Date appearTime;
    private int solveCommitId;
    private Date solveTime;

    public IssueCase(IssueCaseSeverity severity, IssueCaseType type, int appearCommitId) {
        this.severity = severity;
        this.type = type;
        this.appearCommitId = appearCommitId;
    }

    public IssueCase(int issueCaseId, IssueCaseSeverity severity, IssueCaseType type, int appearCommitId, int solveCommitId) {
        this.issueCaseId = issueCaseId;
        this.severity = severity;
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

    public IssueCaseSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(IssueCaseSeverity severity) {
        this.severity = severity;
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

    public Date getAppearTime() {
        return appearTime;
    }

    public void setAppearTime(Date appearTime) {
        this.appearTime = appearTime;
    }

    public Date getSolveTime() {
        return solveTime;
    }

    public void setSolveTime(Date solveTime) {
        this.solveTime = solveTime;
    }

    @Override
    public String toString() {
        return "IssueCase{" +
                "issueCaseId=" + issueCaseId +
                ", severity=" + severity +
                ", type=" + type +
                ", appearCommitId=" + appearCommitId +
                ", solveCommitId=" + solveCommitId +
                '}';
    }
}
