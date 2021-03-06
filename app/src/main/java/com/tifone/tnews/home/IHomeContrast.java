package com.tifone.tnews.home;

import com.tifone.tnews.base.IPresenter;
import com.tifone.tnews.base.IView;
import com.tifone.tnews.bean.home.HomeTestBean;
import com.tifone.tnews.bean.news.MultiNewsArticleDataBean;

import java.util.List;

/**
 * Created by tongkao.chen on 2018/5/3.
 */

public interface IHomeContrast {
    interface IHomeView extends IView<IHomePresenter> {
        //void showData(List<HomeTestBean> targets);
        void showData(HomeTestBean bean);
        void showEmpty();
        void onSetAdapter(List<MultiNewsArticleDataBean> list);
        boolean isLoadLatest();
    }
    interface IHomePresenter extends IPresenter {
        void loadData(String... category);
        void doSetAdapter(List<MultiNewsArticleDataBean> list);
    }
}

