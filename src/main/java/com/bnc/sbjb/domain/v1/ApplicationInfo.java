package com.bnc.sbjb.domain.v1;

public class ApplicationInfo {

    private final String commit;
    private final String timestamp;
    private final String version;
    private final String branch;
    private final String timestampUtc;

    public ApplicationInfo(String commit, String timestamp, String version, String branch,
        String timestampUtc) {
        this.commit = commit;
        this.timestamp = timestamp;
        this.version = version;
        this.branch = branch;
        this.timestampUtc = timestampUtc;
    }

    public String getCommit() {
        return commit;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getVersion() {
        return version;
    }

    public String getBranch() {
        return branch;
    }

    public String getTimestampUtc() {
        return timestampUtc;
    }
}
