package object;

import common.IssueCaseSeverity;
import common.IssueCaseType;


public class IssueCase {
    private int issueCaseId;

//    private IssueCaseSeverity severity;
    private IssueCaseType type;

    private int appearCommitId;
    private int solveCommitId;

    public IssueCase(IssueCaseType type, int appearCommitId) {
        this.type = type;
        this.appearCommitId = appearCommitId;
    }

    public IssueCase() {
    }

    public int getIssueCaseId() {
        return issueCaseId;
    }

    public void setIssueCaseId(int issueCaseId) {
        this.issueCaseId = issueCaseId;
    }

//    public IssueCaseSeverity getSeverity() {
//        return severity;
//    }
//
//    public void setSeverity(IssueCaseSeverity severity) {
//        this.severity = severity;
//    }

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
}
