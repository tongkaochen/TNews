package com.tifone.tnews.home;

import android.util.Log;

import com.google.gson.Gson;
import com.tifone.tnews.api.IMobileNewsApi;
import com.tifone.tnews.bean.news.MultiNewsArticleBean;
import com.tifone.tnews.bean.news.MultiNewsArticleDataBean;
import com.tifone.tnews.home.IHomeContrast.*;
import com.tifone.tnews.model.NetworkModel;
import com.tifone.tnews.utils.RetrofitFactory;
import com.tifone.tnews.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tongkao.chen on 2018/5/4.
 */

public class HomeListPresenter implements IHomePresenter {
    private IHomeView mView;
    private NetworkModel mModel;
    private Random random;
    private String mCategory;
    private String mTime;
    private List<MultiNewsArticleDataBean> mDataList;
    private Gson mGson;


    public static HomeListPresenter newInstance(IHomeView view) {
        return new HomeListPresenter(view);
    }

    private HomeListPresenter(IHomeView view) {
        mView = view;
        //mModel = NetworkModel.getInstance();
        random = new Random();
        mTime = TimeUtils.getCurrentTimeStamp();
        mDataList = new ArrayList<>();
        mGson = new Gson();
    }

    @Override
    public void loadData(String... category) {
        /*mModel.loadData(new IModel.OnDataLoadedCallback<HomeTestBean>() {
            @Override
            public void onDataLoadedComplete(HomeTestBean data) {
                mView.showData(data);
            }

            @Override
            public void onDataLoadedFail(String reason) {

            }
        });*/

        if (null == mCategory) {
            mCategory = category[0];
        }

        getRandom()
                .subscribeOn(Schedulers.io())
                .map(new Function<MultiNewsArticleBean, List<MultiNewsArticleDataBean>>() {

                    @Override
                    public List<MultiNewsArticleDataBean> apply(MultiNewsArticleBean multiNewsArticleBean) throws Exception {
                        // 得到新闻的data数据，其包含有新闻的标题、关键字分类信息，同时包含新闻详情的入口
                        List<MultiNewsArticleDataBean> newsDataList = new ArrayList<>();
                        List<MultiNewsArticleBean.DataBean> originalDataBeans = multiNewsArticleBean.getData();
                        for (MultiNewsArticleBean.DataBean dataBean : originalDataBeans) {
                            // 解析Data的json内容
                            newsDataList.add(mGson.fromJson(dataBean.getContent(), MultiNewsArticleDataBean.class));
                        }
                        return newsDataList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MultiNewsArticleDataBean>>() {
                    @Override
                    public void accept(List<MultiNewsArticleDataBean> multiNewsArticleDataBeans) throws Exception {
                        for (MultiNewsArticleDataBean bean : multiNewsArticleDataBeans) {
                            Log.e("tifone", "标题： " + bean.getTitle());
                            Log.e("tifone", "摘要： " + bean.getAbstractX());
                        }
                        doSetAdapter(multiNewsArticleDataBeans);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("tifone", "Get data error: ", throwable);
                    }
                });
    }

    @Override
    public void doSetAdapter(List<MultiNewsArticleDataBean> list) {
        // 数据list已变更，更新ui
        mDataList.addAll(list);
        mView.onSetAdapter(mDataList);
    }

    private Observable<MultiNewsArticleBean> getRandom() {
        int i = random.nextInt(10);
        if (i % 2 == 0 ) {
            return RetrofitFactory.getRetrofit().create(IMobileNewsApi.class).getNewsArticle(mCategory, mTime);
        }
        return RetrofitFactory.getRetrofit().create(IMobileNewsApi.class).getNewsArticle2(mCategory, mTime);
    }
}
