package com.hallelujah.newwave.worship.count.application;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class MyApplication extends Application {
    public ApplicationComponent appComponent = DaggerApplicationComponent.create();
    private static Resources resources;

    @Override
    public void onCreate() {
        initRealm();
        resources = getResources();
        super.onCreate();
    }

    private void initRealm() {
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                //.deleteRealmIfMigrationNeeded() // for test
                .schemaVersion(1)
                .migration(new RealmMigration() {
                    @Override
                    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
                        RealmSchema schema = realm.getSchema();
                        Log.d("REALM", "oldVersion: " + oldVersion +  ", newVersion: " + newVersion);
                        if (oldVersion == 0) {
                            schema.create("Sector")
                                    .addField("currentSector", int.class)
                                    .addField("maxSector", int.class);
                        }
                    }
                })
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public static Resources getAppResources() {
        return resources;
    }
}
