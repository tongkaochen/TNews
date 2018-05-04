package com.tifone.tnews.base;

import com.tifone.tnews.bean.home.HomeTestBean;

import java.util.List;

/**
 * Created by tongkao.chen on 2018/5/3.
 */

public interface IModel {
     interface OnDataLoadedCallback<T> {
          void onDataLoadedComplete(T data);
          void onDataLoadedFail(String reason);
     }
     void loadData(OnDataLoadedCallback<HomeTestBean> callback);
}
