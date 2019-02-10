package com.ithebk.gitfetcho.main;


import com.ithebk.gitfetcho.models.IssueDataModel;
import com.ithebk.gitfetcho.models.IssuesResponse;
import com.ithebk.gitfetcho.network.GitFetchoApiUtil;
import com.ithebk.gitfetcho.storage.IssuesDb;
import com.ithebk.gitfetcho.storage.Preferences;
import com.ithebk.gitfetcho.storage.RepoDb;
import com.ithebk.gitfetcho.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {

    private MainView mainView;
    private Preferences pref;
    private Realm realm;
    private String issueType;
    private List<IssuesResponse> issuesResponses;

    MainPresenter(MainView mainView, Preferences preferences, Realm realm) {
        this.mainView = mainView;
        this.pref = preferences;
        this.realm = realm;
        this.issueType = "open";
        this.issuesResponses = new ArrayList<>();
    }

    public String getIssueType() {
        return issueType;
    }


    void init() {
        this.issuesResponses.clear();
        String orgName = pref.getOrganisationName();
        String repoName = pref.getRepoName();
        if (Utils.isEmpty(orgName) || Utils.isEmpty(repoName)) {
            mainView.showRepoSelectorView();
        } else {
            mainView.init(orgName, repoName);
            RepoDb repoDb =
                    realm.where(RepoDb.class)
                            .equalTo("id", orgName + repoName)
                            .findFirst();
            System.out.println("repoDb" + repoDb);
            if (repoDb == null || Utils.secondDifference(repoDb.getLast_modified(), new Date()) / 60 > 30) {
                mainView.loading(true);
                callApi(orgName, repoName, "open");
            } else {
                System.out.println("Calling API Not Required");
                mainView.showAlert("Last updated at " + repoDb.getLast_modified().toString().split("GMT")[0]);
                mainView.showAdapter(getAdapterModels(orgName + repoName));
            }

        }

    }

    private List<IssueDataModel> getAdapterModels(String id) {
        RepoDb repoDb =
                realm.where(RepoDb.class)
                        .equalTo("id", id)
                        .findFirst();
        List<IssueDataModel> issueDataModels = new ArrayList<>();
        if (repoDb != null) {
            for (IssuesDb issuesDb : repoDb.getIssuesDbRealmList()) {
                if (issuesDb.getState().equalsIgnoreCase(issueType)) {
                    issueDataModels.add(new IssueDataModel(issuesDb.getPrNumber(), issuesDb.getTitle(), issuesDb.getUser(), issuesDb.getPatchUrl()));

                }
            }
        }
        return issueDataModels;
    }


    private void callApi(String orgName, String repoName, String state) {
        System.out.println("Calling API:" + state);
        GitFetchoApiUtil.getGitFetchoApi().getRepoIssues(orgName, repoName, state).enqueue(new Callback<List<IssuesResponse>>() {
            @Override
            public void onResponse(Call<List<IssuesResponse>> call, Response<List<IssuesResponse>> response) {
                System.out.println(response.toString());
                System.out.println("Success");
                if (response.code() == 200 && response.body() != null) {
                    issuesResponses.addAll(response.body());
                    System.out.println("Issues Size:" + issuesResponses.size());
                    if (state.equalsIgnoreCase("open")) {
                        //Delete DB Upon Creations
                        deleteDb(orgName + repoName);
                        callApi(orgName, repoName, "closed");
                    } else {

                        insertData(orgName, repoName, issuesResponses);
                        mainView.loading(false);
                        mainView.showAdapter(getAdapterModels(orgName + repoName));
                    }

                } else {
                    mainView.loading(false);
                    mainView.showAdapter(getAdapterModels(orgName + repoName));
                    mainView.showAlert(response.message());
                    mainView.showRepoSelectorView(orgName, repoName);

                }
            }

            @Override
            public void onFailure(Call<List<IssuesResponse>> call, Throwable t) {
                System.out.println(t.toString());
                System.out.println("Error");
                mainView.loading(false);
                mainView.showAdapter(getAdapterModels(orgName + repoName));
                mainView.showAlert("Connection Error");
            }
        });
    }

    private void deleteDb(String id) {
        RepoDb repoDb = realm.where(RepoDb.class)
                .equalTo("id", id)
                .findFirst();
        if (repoDb != null) {
            realm.beginTransaction();
            repoDb.getIssuesDbRealmList().deleteAllFromRealm();
            repoDb.deleteFromRealm();
            realm.commitTransaction();
        }
    }

    private void insertData(String orgName, String repoName, List<IssuesResponse> body) {
        realm.beginTransaction();
        RepoDb repoDb = new RepoDb();
        repoDb.setId(orgName + repoName);
        repoDb.setOrganisation(orgName);
        repoDb.setLast_modified(new Date());
        System.out.println("InsertCalled");
        RealmList<IssuesDb> issuesDbRealmList = new RealmList<>();
        for (IssuesResponse issuesResponse : body) {
            IssuesDb issuesDb = new IssuesDb();
            issuesDb.setUser(issuesResponse.getUser().getLogin());
            if (issuesResponse.getPull_request() != null) {
                issuesDb.setPatchUrl(issuesResponse.getPull_request().getPatch_url());
            }
            issuesDb.setPrNumber(issuesResponse.getNumber());
            issuesDb.setRepoId(issuesResponse.getId());
            issuesDb.setState(issuesResponse.getState());
            issuesDb.setTitle(issuesResponse.getTitle());
            issuesDbRealmList.add(issuesDb);
        }
        repoDb.setIssuesDbRealmList(issuesDbRealmList);
        realm.copyToRealmOrUpdate(repoDb);
        realm.commitTransaction();
    }

    void editRepo() {
        mainView.showRepoSelectorView(pref.getOrganisationName(), pref.getRepoName());
    }

    void updateRepoName(String orgName, String repoName) {
        pref.setOrganisationName(orgName);
        pref.setRepoName(repoName);
        init();
    }

    void updateSelectedIssueType(String issueType) {
        this.issueType = issueType;
        init();
    }

    public interface MainView {
        void init(String organisationName, String repoName);

        void showAdapter(List<IssueDataModel> issueDataModels);

        void showRepoSelectorView(String organisationName, String repoName);

        void showRepoSelectorView();

        void loading(boolean isLoading);

        void showAlert(String alert);
    }
}
