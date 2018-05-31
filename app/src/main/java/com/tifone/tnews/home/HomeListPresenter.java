package com.tifone.tnews.home;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.tifone.tnews.api.IMobileNewsApi;
import com.tifone.tnews.bean.news.MultiNewsArticleBean;
import com.tifone.tnews.bean.news.MultiNewsArticleDataBean;
import com.tifone.tnews.home.IHomeContrast.IHomePresenter;
import com.tifone.tnews.home.IHomeContrast.IHomeView;
import com.tifone.tnews.model.NetworkModel;
import com.tifone.tnews.utils.RetrofitFactory;
import com.tifone.tnews.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
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
    private List<String> mHistoryItemsId;


    public static HomeListPresenter newInstance(IHomeView view) {
        return new HomeListPresenter(view);
    }

    private HomeListPresenter(IHomeView view) {
        mView = view;
        //mModel = NetworkModel.getInstance();
        random = new Random();
        mTime = TimeUtils.getCurrentTimeStamp();
        mDataList = new ArrayList<>();
        mHistoryItemsId = new ArrayList<>();
        mGson = new Gson();
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadData(String... category) {

        if (null == mCategory) {
            mCategory = category[0];
        }
        if (mView.isLoadLatest()) {
            mTime = TimeUtils.getCurrentTimeStamp();
        } else {
            mTime = String.valueOf(mDataList.get(mDataList.size() - 1).getBehot_time());
        }
        getRandom()
                .subscribeOn(Schedulers.io())
                .switchMap(new Function<MultiNewsArticleBean, Observable<MultiNewsArticleDataBean>>() {

                    @Override
                    public Observable<MultiNewsArticleDataBean> apply(MultiNewsArticleBean multiNewsArticleBean) throws Exception {
                        // 得到新闻的data数据，其包含有新闻的标题、关键字分类信息，同时包含新闻详情的入口
                        List<MultiNewsArticleDataBean> newsDataList = new ArrayList<>();
                        List<MultiNewsArticleBean.DataBean> originalDataBeans = multiNewsArticleBean.getData();
                        for (MultiNewsArticleBean.DataBean dataBean : originalDataBeans) {
                            // 解析Data的json内容
                            MultiNewsArticleDataBean bean = mGson.fromJson(dataBean.getContent(), MultiNewsArticleDataBean.class);
                            if (bean == null || TextUtils.isEmpty(bean.getTitle())) {
                                continue;
                            }
                            newsDataList.add(bean);
                        }
                        return Observable.fromIterable(newsDataList);
                    }
                })
                .filter(new Predicate<MultiNewsArticleDataBean>() {
                    @Override
                    public boolean test(MultiNewsArticleDataBean bean) throws Exception {
                        // 移除已有的item
                        StringBuilder key = new StringBuilder("");
                        key.append(bean.getGroup_id()).
                                append(bean.getItem_id()).
                                append(bean.getItem_version());
                        if (mHistoryItemsId.contains(key.toString())){
                            return false;
                        }
                        mHistoryItemsId.add(key.toString());
                        return true;
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MultiNewsArticleDataBean>>() {
                    @Override
                    public void accept(List<MultiNewsArticleDataBean> multiNewsArticleDataBeans) throws Exception {
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
        if (mView.isLoadLatest()) {
            //removeRepeatDataAndAdd(list);
            // 根据时间排序
            Comparator<MultiNewsArticleDataBean> comparator = new Comparator<MultiNewsArticleDataBean>() {
                @Override
                public int compare(MultiNewsArticleDataBean bean1, MultiNewsArticleDataBean bean2) {
                    return bean2.getBehot_time() - bean1.getBehot_time();
                }
            };
            Collections.sort(mDataList, comparator);
        }
        mView.onSetAdapter(mDataList);
    }

    private void removeRepeatDataAndAdd(List<MultiNewsArticleDataBean> list) {

        Set<MultiNewsArticleDataBean> set = new HashSet<>();
        set.addAll(mDataList);
        set.addAll(list);
        List<MultiNewsArticleDataBean> newList = new ArrayList<>();
        newList.addAll(set);
        mDataList.clear();
        mDataList = newList;
    }

    private Observable<MultiNewsArticleBean> getRandom() {
        int i = random.nextInt(10);
        if (i % 2 == 0) {
            return RetrofitFactory.getRetrofit().create(IMobileNewsApi.class).getNewsArticle(mCategory, mTime);
        }
        return RetrofitFactory.getRetrofit().create(IMobileNewsApi.class).getNewsArticle2(mCategory, mTime);
    }
}
