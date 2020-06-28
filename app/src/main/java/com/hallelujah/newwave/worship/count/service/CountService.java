package com.hallelujah.newwave.worship.count.service;

import android.util.Log;

import com.hallelujah.newwave.worship.count.model.Count;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmQuery;

public class CountService {
    private static final String TAG = "CountService";

    private Count count;
    private Realm realm;

    @Inject
    public CountService() {
        realm = Realm.getDefaultInstance();
        final RealmQuery<Count> realmCount = realm.where(Count.class);
        if (realmCount.count() == 0) {
            createCount();
        } else {
            count = realmCount.findFirst();
        }
    }

    public void createCount() {
        realm.beginTransaction();
        count = realm.createObject(Count.class);
        realm.commitTransaction();
        Log.d(TAG, "createCount(); " + count.toString());
    }

    public void addCount(Count.Type type) {
        realm.beginTransaction();
        switch (type) {
            case MAN:
                count.addManCount();
                break;
            case WOMAN:
                count.addWomanCount();
                break;
            default:
                break;
        }
        realm.commitTransaction();
        Log.d(TAG, "addCount(); " + count.toString());
    }

    public void removeCount(Count.Type type) {
        realm.beginTransaction();
        switch (type) {
            case MAN:
                count.removeManCount();
                break;
            case WOMAN:
                count.removeWomanCount();
                break;
            default:
                break;
        }
        realm.commitTransaction();
        Log.d(TAG, "removeCount(); " + count.toString());
    }

    public Count get() {
        Log.d(TAG, "get(); " + count.toString());
        return this.count;
    }

    public void resetCount(Count.Type type) {
        realm.beginTransaction();
        switch (type) {
            case MAN:
                count.setManCount(0);
                break;
            case WOMAN:
                count.setWomanCount(0);
                break;
            default:
                break;
        }
        realm.commitTransaction();
        Log.d(TAG, "resetCount(); " + count.toString());
    }
}