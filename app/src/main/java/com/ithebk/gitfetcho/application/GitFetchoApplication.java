package com.ithebk.gitfetcho.application;


import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class GitFetchoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        //Drop db on column changes
        // Migration need to write to save column
        RealmConfiguration config= new RealmConfiguration.Builder()
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

    }
}
