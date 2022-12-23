package com.database.object;

public class Repository {

    private int repositoryId;
    private String baseDir;
    private String repositoryName;


    public Repository() {
    }

    public Repository(int repositoryId, String baseDir, String repositoryName) {
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
