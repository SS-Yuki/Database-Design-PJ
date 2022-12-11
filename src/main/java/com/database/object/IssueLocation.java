package com.database.object;

public class IssueLocation {
    private int issueInstanceId;
    private int order;

    private int startLine;
    private int endLine;

    public IssueLocation() {
    }

    public IssueLocation(int issueInstanceId, int order, int startLine, int endLine) {
        this.issueInstanceId = issueInstanceId;
        this.order = order;
        this.startLine = startLine;
        this.endLine = endLine;
    }

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

    @Override
    public String toString() {
        return "IssueLocation{" +
                "issueInstanceId=" + issueInstanceId +
                ", order=" + order +
                ", startLine=" + startLine +
                ", endLine=" + endLine +
                '}';
    }
}
