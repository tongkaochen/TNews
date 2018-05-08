package com.tifone.tnews.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by tongkao.chen on 2018/5/7.
 */

public class NetworkUtils {

    public static boolean isNetworkConnected(Context context) {
        if (null != context) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            return null != info && info.isAvailable();
        }
        return false;
    }
}
