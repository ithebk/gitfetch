package com.ithebk.gitfetcho.repo;

import android.os.Bundle;

import com.ithebk.gitfetcho.utils.Constants;
import com.ithebk.gitfetcho.utils.Utils;

class RepoPresenter {
    private RepoView repoView;
    RepoPresenter(RepoView repoView) {
        this.repoView = repoView;
    }
    void updateRepo(String orgName, String repoName) {
        if(Utils.isEmpty(orgName) || Utils.isEmpty(repoName)) {
            repoView.showAlert("Please enter all the fields");
            return;
        }
        repoView.onUpdateRepo(orgName, repoName);

    }

    void init(Bundle arguments) {
        if(arguments!=null) {
            String orgName = arguments.getString(Constants.ORGANISATION_NAME);
            String repoName = arguments.getString(Constants.REPO_NAME);
            repoView.init(orgName, repoName);

        }
    }

    public interface RepoView {
        void init(String orgName, String repoName);
        void onUpdateRepo(String orgName, String repoName);
        void showAlert(String alert);
    }
}
