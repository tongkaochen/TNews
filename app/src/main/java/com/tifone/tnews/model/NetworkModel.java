package com.tifone.tnews.model;

import android.annotation.SuppressLint;

import com.tifone.tnews.base.IModel;
import com.tifone.tnews.bean.home.HomeTestBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tongkao.chen on 2018/5/3.
 */

public class NetworkModel implements IModel {

    static class Singleton {
        public static NetworkModel INSTANCE = new NetworkModel();
    }

    private NetworkModel() {

    }

    public static NetworkModel getInstance() {
        return Singleton.INSTANCE;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadData(final OnDataLoadedCallback<HomeTestBean> callback) {

        Observable.fromIterable(madeTestData())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<HomeTestBean>() {
                    @Override
                    public void accept(HomeTestBean bean) throws Exception {
                        if (null != bean) {
                            callback.onDataLoadedComplete(bean);
                        } else {
                            callback.onDataLoadedFail("data is null");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callback.onDataLoadedFail("Error: " + throwable);
                    }
                });
    }

    private List<HomeTestBean> madeTestData() {
        List<HomeTestBean> list = new ArrayList<>();
        list.add(new HomeTestBean(" Aaaaa"));
        list.add(new HomeTestBean(" Bbbbb"));
        list.add(new HomeTestBean(" Ccccc"));
        list.add(new HomeTestBean(" Ddddd"));
        list.add(new HomeTestBean(" Eeeee"));
        list.add(new HomeTestBean(" Fffff"));
        list.add(new HomeTestBean(" Ggggg"));
        list.add(new HomeTestBean(" Hhhhh"));
        return list;
    }
}
