package object;

public class Repository {
    private int repositoryId;

    private String name;
    private String baseDir;

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }
//    private String url;

    public Repository(String name, String baseDir) {
        this.name = name;
        this.baseDir = baseDir;
    }
    public Repository() {
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

//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
}
