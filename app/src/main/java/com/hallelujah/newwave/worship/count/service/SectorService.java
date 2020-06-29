package com.hallelujah.newwave.worship.count.service;

import android.util.Log;

import com.hallelujah.newwave.worship.count.R;
import com.hallelujah.newwave.worship.count.application.MyApplication;
import com.hallelujah.newwave.worship.count.model.Sector;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmQuery;

public class SectorService {
    private static final String TAG = "SectorService";

    private Sector sector;
    private Realm realm;

    @Inject
    public SectorService() {
        initSector();
    }

    private void initSector() {
        realm = Realm.getDefaultInstance();
        final RealmQuery<Sector> realmCount = realm.where(Sector.class);
        if (realmCount.count() == 0) {
            realm.beginTransaction();
            sector = realm.createObject(Sector.class);
            realm.commitTransaction();
        } else {
            sector = realmCount.findFirst();
        }
        Log.d(TAG, "initSector(); " + sector);
    }

    public List<String> getSectorList() {
        List<String> sectorList = new ArrayList<>();
        for (int i = 1; i <= sector.getMaxSector(); i++) {
            String sectorString = MyApplication.getAppResources().getString(R.string.sector);
            sectorList.add("" + i + sectorString);
        }
        return sectorList;
    }

    public void setCurrentSector(int currentSector) {
        realm.beginTransaction();
        sector.setCurrentSector(currentSector);
        realm.commitTransaction();
    }

    public int getCurrentSector() {
        return sector.getCurrentSector();
    }
}
