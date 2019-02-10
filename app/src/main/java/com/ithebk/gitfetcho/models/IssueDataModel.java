package com.ithebk.gitfetcho.models;

public class IssueDataModel {
    private long prNumber;
    private String title;
    private String user;
    private String pathLink;

    public IssueDataModel(long prNumber, String title, String user, String pathLink) {
        this.prNumber = prNumber;
        this.title = title;
        this.user = user;
        this.pathLink = pathLink;
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

    public String getPathLink() {
        return pathLink;
    }

    public void setPathLink(String pathLink) {
        this.pathLink = pathLink;
    }
}
