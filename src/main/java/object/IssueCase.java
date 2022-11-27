package object;

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
    private int solveCommitId;

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
}
