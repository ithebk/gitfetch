package com.ithebk.gitfetcho.storage;

import io.realm.RealmObject;

public class IssuesDb extends RealmObject {
    private String repoId;
    private String state;
    private long prNumber;
    private String title;
    private String user;
    private String patchUrl;

    public String getRepoId() {
        return repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getPrNumber() {
        return prNumber;
    }

    public void setPrNumber(long prNumber) {
        this.prNumber = prNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPatchUrl() {
        return patchUrl;
    }

    public void setPatchUrl(String patchUrl) {
        this.patchUrl = patchUrl;
    }
}
