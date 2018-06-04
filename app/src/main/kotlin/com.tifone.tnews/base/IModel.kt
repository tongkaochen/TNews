package com.tifone.tnews.base

import com.tifone.tnews.bean.home.HomeTestBean

interface IModel {
    interface OnDataLoadedCallback<T> {
        fun onDataLoadedComplete(data: T)
        fun onDataLoadedFail(reason: String)
    }

    fun loadData(callback: OnDataLoadedCallback<HomeTestBean>)
}