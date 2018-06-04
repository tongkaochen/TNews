package com.tifone.tnews.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtils {
    companion object STATIC {
        fun isNetworkConnected(context: Context): Boolean {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = manager.activeNetworkInfo
            return null != info && info.isAvailable
        }
    }
}