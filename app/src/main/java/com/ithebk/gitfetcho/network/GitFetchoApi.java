package com.ithebk.gitfetcho.network;

import com.ithebk.gitfetcho.models.IssuesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitFetchoApi {

    @GET("/repos/{ORG_NAME}/{REPO_NAME}/issues")
    Call <List<IssuesResponse>> getRepoIssues(@Path("ORG_NAME") String orgName,
                                             @Path("REPO_NAME") String repoName,
                                             @Query("state") String state);
}


