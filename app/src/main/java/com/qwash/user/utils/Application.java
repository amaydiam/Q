package com.qwash.user.utils;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.google.android.gms.analytics.Tracker;
import com.orm.SugarApp;

/**
 * Created  on 8/2/16.
 */
public class Application extends SugarApp {
    private Tracker mTracker;

    /**
     * Gets the default {@link Tracker} for this {@link android.app.Application}.
     *
     * @return tracker
     */
//    synchronized public Tracker getDefaultTracker() {
//        if (mTracker == null) {
//            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
//            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
//            mTracker = analytics.newTracker(R.xml.global_tracker);
//        }
//        return mTracker;
//    }

    // Here you will enable Multidex
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(getBaseContext());
    }
}
