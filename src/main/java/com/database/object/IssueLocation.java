package com.database.object;

public class IssueLocation {
    private int issueInstanceId;
    private int order;

    private int startLine;
    private int endLine;

    public int getIssueInstanceId() {
        return issueInstanceId;
    }

    public void setIssueInstanceId(int issueInstanceId) {
        this.issueInstanceId = issueInstanceId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }
}
