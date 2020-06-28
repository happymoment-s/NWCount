package com.hallelujah.newwave.worship.count.application;

import android.app.Application;

public class MyApplication extends Application {
    public ApplicationComponent appComponent = DaggerApplicationComponent.create();
}
