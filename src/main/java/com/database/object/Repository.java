package com.database.object;

public class Repository {

    private int repositoryId;
    private String repositoryName;
    private String baseDir;

    public Repository() {
    }

    public Repository(String repositoryName, String baseDir) {
        this.repositoryName = repositoryName;
        this.baseDir = baseDir;
    }

    public Repository(int repositoryId, String repositoryName, String baseDir) {
        this.repositoryId = repositoryId;
        this.repositoryName = repositoryName;
        this.baseDir = baseDir;
    }

    public int getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(int repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "repositoryId=" + repositoryId +
                ", repositoryName='" + repositoryName + '\'' +
                ", baseDir='" + baseDir + '\'' +
                '}';
    }
}
