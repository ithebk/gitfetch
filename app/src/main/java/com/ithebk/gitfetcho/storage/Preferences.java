package com.ithebk.gitfetcho.storage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.ithebk.gitfetcho.utils.Constants;

public class Preferences {
    private static Preferences instance;
    private SharedPreferences.Editor editor;
    private String organisationName;
    private String repoName;
    private long lastUpdated;


    private Preferences() {

    }
    public static Preferences getInstance() {
        if (instance == null)
            instance = new Preferences();
        return instance;
    }

    public void init(Context ctx) {
        SharedPreferences pref = ctx.getSharedPreferences(Constants.PreferencesConstants.PREF_NAME, Activity.MODE_PRIVATE);
        editor = pref.edit();

        organisationName = pref.getString(Constants.PreferencesConstants.PREF_KEY_ORGANISATION_NAME, null);
        repoName         = pref.getString(Constants.PreferencesConstants.PREF_KEY_REPO_NAME, null);
        lastUpdated      = pref.getLong(Constants.PreferencesConstants.PREF_KEY_LAST_UPDATED, 0);

    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
        editor.putString(Constants.PreferencesConstants.PREF_KEY_ORGANISATION_NAME, organisationName);
        editor.commit();
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
        editor.putString(Constants.PreferencesConstants.PREF_KEY_REPO_NAME, repoName);
        editor.commit();
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
        editor.putLong(Constants.PreferencesConstants.PREF_KEY_LAST_UPDATED, lastUpdated);
        editor.commit();
    }
}
