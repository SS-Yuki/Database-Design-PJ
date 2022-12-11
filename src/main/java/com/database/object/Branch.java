package com.database.object;

public class Branch {

    private int branchId;

    private String branchName;

    private int repositoryId;


    public Branch() {
    }

    public Branch(String branchName, int repositoryId) {
        this.branchName = branchName;
        this.repositoryId = repositoryId;
    }

    public Branch(int branchId, String branchName, int repositoryId) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.repositoryId = repositoryId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getRepositoryId() {
        return repositoryId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void setRepositoryId(int repositoryId) {
        this.repositoryId = repositoryId;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "branchId=" + branchId +
                ", branchName='" + branchName + '\'' +
                ", repositoryId=" + repositoryId +
                '}';
    }
}
