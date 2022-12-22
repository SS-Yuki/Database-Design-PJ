package com.database.object;

public class IssueLocation {
    private int issueInstanceId;
    private int locationOrder;

    private int startLine;
    private int endLine;

    public IssueLocation() {
    }

    public IssueLocation(int issueInstanceId, int locationOrder, int startLine, int endLine) {
        this.issueInstanceId = issueInstanceId;
        this.locationOrder = locationOrder;
        this.startLine = startLine;
        this.endLine = endLine;
    }

    public int getIssueInstanceId() {
        return issueInstanceId;
    }

    public void setIssueInstanceId(int issueInstanceId) {
        this.issueInstanceId = issueInstanceId;
    }

    public int getLocationOrder() {
        return locationOrder;
    }

    public void setLocationOrder(int locationOrder) {
        this.locationOrder = locationOrder;
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
                ", locationOrder=" + locationOrder +
                ", startLine=" + startLine +
                ", endLine=" + endLine +
                '}';
    }
}
