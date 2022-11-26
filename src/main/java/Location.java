public class Location {
    private int startLine;
    private int endLine;
    private String filePath;
    private String repoPath;

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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getRepoPath() {
        return repoPath;
    }

    public void setRepoPath(String repoPath) {
        this.repoPath = repoPath;
    }

    public Location(int startLine, int endLine, String filePath, String repoPath) {
        this.startLine = startLine;
        this.endLine = endLine;
        this.filePath = filePath;
        this.repoPath = repoPath;
    }
}
