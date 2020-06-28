package com.hallelujah.newwave.worship.count.application;

import com.hallelujah.newwave.worship.count.activity.MainActivity;

import dagger.Component;

@Component
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
}
