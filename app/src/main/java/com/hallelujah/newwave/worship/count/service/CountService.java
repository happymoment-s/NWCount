package com.hallelujah.newwave.worship.count.service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.hallelujah.newwave.worship.count.R;
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
        initCount();
    }

    private void initCount() {
        realm = Realm.getDefaultInstance();
        final RealmQuery<Count> realmCount = realm.where(Count.class);
        if (realmCount.count() == 0) {
            realm.beginTransaction();
            count = realm.createObject(Count.class);
            realm.commitTransaction();
        } else {
            count = realmCount.findFirst();
        }
        Log.d(TAG, "initCount(); " + count);
    }

    public void addCount(@NonNull Count.Type type) {
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

    public void removeCount(@NonNull Count.Type type) {
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

    public void resetCount(@NonNull Count.Type type) {
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

    public String getCountString(@NonNull Context context, @NonNull Count.Type type) {
        int countNumber = 0;
        switch (type) {
            case MAN:
                countNumber = count.getManCount();
                return countNumber == 0 ? context.getString(R.string.btn_man)
                        : context.getString(R.string.btn_man) + System.lineSeparator() + countNumber;
            case WOMAN:
                countNumber = count.getWomanCount();
                return countNumber == 0 ? context.getString(R.string.btn_woman)
                        : context.getString(R.string.btn_woman) + System.lineSeparator() + countNumber;
            default:
                break;
        }
        return String.valueOf(countNumber);
    }
}