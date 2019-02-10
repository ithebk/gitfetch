package com.ithebk.gitfetcho.models;

public class IssuesResponse {
    private long number;
    private String id;
    private String title;
    private String state;
    private User user;
    private PullRequest pull_request;

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PullRequest getPull_request() {
        return pull_request;
    }

    public void setPull_request(PullRequest pull_request) {
        this.pull_request = pull_request;
    }

    public class User {
        private String login;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }
    }

    public class PullRequest {
        private String patch_url;

        public String getPatch_url() {
            return patch_url;
        }

        public void setPatch_url(String patch_url) {
            this.patch_url = patch_url;
        }
    }
}
