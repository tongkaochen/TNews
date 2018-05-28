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

import java.util.List;

/**
 * Created by tongkao.chen on 2018/5/4.
 */

public class HomeListFragment extends BaseHomeFragment implements PullLoadRecyclerView.OnRefreshListener, PullLoadRecyclerView.OnLoadListener {

    private PullLoadRecyclerView mRecyclerView;
    private HomePagerViewAdapter mAdapter;
    private boolean isRefreshDataInserted;
    private boolean isLoadDataInserted;

    public static HomeListFragment newInstance(String fragmentTitle) {
        Bundle bundle = setupBundle(fragmentTitle);
        HomeListFragment fragment = new HomeListFragment();
        fragment.setArguments(bundle);
        return fragment;
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
    }

    @Override
    protected void initViewData() {
    }

    @Override
    protected void fetchData() {
        mPresenter.loadData("news_hot");
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
        if (!isRefreshDataInserted) {
            mRecyclerView.notifyRefreshCompleted();
            isRefreshDataInserted = true;
        }
        if (!isLoadDataInserted) {
            mRecyclerView.notifyLoadComplete();
            isLoadDataInserted = true;
        }
        mAdapter.addDataSet(list);
        Log.e("tifone", "onSetAdapter: " + list.size() + " itemcount = " + mAdapter.getItemCount());
    }

    @Override
    public void onRefreshStarted() {
        fetchData();
        isRefreshDataInserted = false;
    }

    @Override
    public void onLoadStarted() {
        fetchData();
        isLoadDataInserted = false;
    }
}
