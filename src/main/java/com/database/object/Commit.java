package com.database.object;

import java.util.Date;

public class Commit {
    private int commitId;

    private String commitHash;
    private Date commitTime;
    private String commiter;

    private int branchId;

    public Commit() {
    }

    public Commit(String commitHash, Date commitTime, String commiter, int branchId) {
        this.commitHash = commitHash;
        this.commitTime = commitTime;
        this.commiter = commiter;
        this.branchId = branchId;
    }

    public Commit(int commitId, String commitHash, Date commitTime, String commiter, int branchId) {
        this.commitId = commitId;
        this.commitHash = commitHash;
        this.commitTime = commitTime;
        this.commiter = commiter;
        this.branchId = branchId;
    }

    public int getCommitId() {
        return commitId;
    }

    public void setCommitId(int commitId) {
        this.commitId = commitId;
    }

    public String getCommitHash() {
        return commitHash;
    }

    public void setCommitHash(String commitHash) {
        this.commitHash = commitHash;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public String getCommiter() {
        return commiter;
    }

    public void setCommiter(String commiter) {
        this.commiter = commiter;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    @Override
    public String toString() {
        return "Commit{" +
                "commitId=" + commitId +
                ", commitHash='" + commitHash + '\'' +
                ", commitTime=" + commitTime +
                ", commiter='" + commiter + '\'' +
                ", branchId=" + branchId +
                '}';
    }
}
