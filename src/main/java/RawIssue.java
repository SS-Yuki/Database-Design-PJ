import java.util.List;

public class RawIssue {
    private String uuid;
    private String commit;
    List<Location> locations;

    public static String generateRawIssueUUID(RawIssue rawIssue) {
        return "issue";
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public RawIssue(String commit) {
        this.commit = commit;
    }
}
