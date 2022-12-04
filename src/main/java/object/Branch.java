package object;

public class Branch {
    private int branchId;

    private int repositoryId;

    private String name;

    public Branch(int repositoryId, String name) {
        this.repositoryId = repositoryId;
        this.name = name;
    }

    public Branch() {
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

    public void setRepositoryId(int repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
