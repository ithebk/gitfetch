package com.ithebk.gitfetcho.storage;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RepoDb extends RealmObject {

    @PrimaryKey
    private String id;

    private String organisation;
    private String repoName;
    private Date last_modified;

    private RealmList<IssuesDb> issuesDbRealmList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public Date getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(Date last_modified) {
        this.last_modified = last_modified;
    }

    public RealmList<IssuesDb> getIssuesDbRealmList() {
        return issuesDbRealmList;
    }

    public void setIssuesDbRealmList(RealmList<IssuesDb> issuesDbRealmList) {
        this.issuesDbRealmList = issuesDbRealmList;
    }
}
