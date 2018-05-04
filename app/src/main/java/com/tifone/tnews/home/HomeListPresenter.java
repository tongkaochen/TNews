package com.tifone.tnews.home;

import com.tifone.tnews.base.IModel;
import com.tifone.tnews.bean.home.HomeTestBean;
import com.tifone.tnews.home.IHomeContrast.*;
import com.tifone.tnews.model.NetworkModel;

/**
 * Created by tongkao.chen on 2018/5/4.
 */

public class HomeListPresenter implements IHomePresenter {
    private IHomeView mView;
    private NetworkModel mModel;

    public static HomeListPresenter newInstance(IHomeView view) {
        return new HomeListPresenter(view);
    }

    private HomeListPresenter(IHomeView view) {
        mView = view;
        mModel = NetworkModel.getInstance();
    }

    @Override
    public void loadData() {
        mModel.loadData(new IModel.OnDataLoadedCallback<HomeTestBean>() {
            @Override
            public void onDataLoadedComplete(HomeTestBean data) {
                mView.showData(data);
            }

            @Override
            public void onDataLoadedFail(String reason) {

            }
        });
    }
}
