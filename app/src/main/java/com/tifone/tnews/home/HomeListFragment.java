package com.tifone.tnews.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.tifone.mfrv.pullload.PullLoadRecyclerView;
import com.tifone.tnews.R;
import com.tifone.tnews.adapter.HomePagerViewAdapter;
import com.tifone.tnews.bean.home.HomeTestBean;
import com.tifone.tnews.bean.news.MultiNewsArticleDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tongkao.chen on 2018/5/4.
 */

public class HomeListFragment extends BaseHomeFragment implements PullLoadRecyclerView.OnRefreshListener, PullLoadRecyclerView.OnLoadListener {

    private static final String CATEGORY_ID = "category_id";
    private PullLoadRecyclerView mRecyclerView;
    private HomePagerViewAdapter mAdapter;
    private boolean isRefreshDataStarted;
    private boolean isLoadDataStarted;
    private String mCategoryId;

    public static HomeListFragment newInstance(String fragmentTitle, String categoryId) {
        Bundle bundle = setupBundle(fragmentTitle);
        bundle.putString(CATEGORY_ID, categoryId);
        HomeListFragment fragment = new HomeListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    public HomeListFragment() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCategoryId = bundle.getString(CATEGORY_ID, "news_hot");
        }
    }

    @Override
    public void setPresenter(IHomeContrast.IHomePresenter presenter) {
        if (null == presenter) {
            mPresenter = HomeListPresenter.newInstance(this);
        }
    }

    @Override
    protected int inflaterViewId() {
        return R.layout.home_list_frament_layout;
    }

    @Override
    protected void initView(View root) {
        mRecyclerView = root.findViewById(R.id.list_item_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mAdapter = new HomeRecyclerViewAdapter(new ArrayList<MultiNewsArticleDataBean>());
        mAdapter = new HomePagerViewAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnLoadListener(this);
        mRecyclerView.setOnRefreshListener(this);
        mRecyclerView.setLoadMoreBehavior(PullLoadRecyclerView.LOAD_BEHAVIOR_STYLE_NORMAL);
    }

    @Override
    protected void initViewData() {
    }

    @Override
    protected void fetchData() {
        mPresenter.loadData(mCategoryId);
    }

    @Override
    public void forceRefresh() {
        mRecyclerView.scrollToPosition(0);
        mRecyclerView.notifyDoRefresh();
    }

    @Override
    public String fragmentTitle() {
        return getArguments().getString(FRAGMENT_ID);
    }

    @Override
    public void showData(HomeTestBean targets) {
        //mAdapter.adapterDataSetInserted(targets);
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void onSetAdapter(List<MultiNewsArticleDataBean> list) {
        if (isRefreshDataStarted) {
            mRecyclerView.notifyRefreshCompleted();
            isRefreshDataStarted = false;
        }
        if (isLoadDataStarted) {
            mRecyclerView.notifyLoadComplete();
            isLoadDataStarted = false;
        }
        mAdapter.setDataSet(list);
    }

    @Override
    public boolean isLoadLatest() {
        return !isLoadDataStarted;
    }

    @Override
    public void onRefreshStarted() {
        isRefreshDataStarted = true;
        fetchData();
    }

    @Override
    public void onLoadStarted() {
        isLoadDataStarted = true;
        fetchData();
    }
}
