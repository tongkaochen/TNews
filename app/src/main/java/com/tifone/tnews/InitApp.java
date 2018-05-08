package com.tifone.tnews;

import android.app.Application;
import android.content.Context;

/**
 * Created by tongkao.chen on 2018/5/7.
 */

public class InitApp extends Application {

    public static Context appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }
}
