package com.tifone.tnews.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tifone.tnews.R;
import com.tifone.tnews.adapter.HomeRecyclerViewAdapter;
import com.tifone.tnews.bean.home.HomeTestBean;
import com.tifone.tnews.bean.news.MultiNewsArticleDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tongkao.chen on 2018/5/4.
 */

public class HomeListFragment extends BaseHomeFragment {

    private RecyclerView mRecyclerView;
    private HomeRecyclerViewAdapter mAdapter;

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
    }

    @Override
    protected void initViewData() {
        mAdapter = new HomeRecyclerViewAdapter(new ArrayList<MultiNewsArticleDataBean>());
        mRecyclerView.setAdapter(mAdapter);
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
        mAdapter.adapterDataSetInserted(list);
    }
}
