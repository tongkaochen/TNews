package com.tifone.tnews.home;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tifone.tnews.R;
import com.tifone.tnews.adapter.ViewPagerFragmentAdapter;
import com.tifone.tnews.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tongkao.chen on 2018/5/3.
 */

public class HomeFragment extends BaseFragment {

    private TabLayout mTabLayout;
    private ImageView mAddItemIv;
    private ViewPager mViewPager;
    List<BaseHomeFragment> mFragments;

    public static HomeFragment newInstance() {
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT_ID, HomeFragment.class.getName());
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int inflaterViewId() {
        return R.layout.home_fragment_layout;
    }

    @Override
    protected void initView(View root) {
        mTabLayout = root.findViewById(R.id.tab_layout);
        mAddItemIv = root.findViewById(R.id.add_item_iv);
        mViewPager = root.findViewById(R.id.view_pager);

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initViewData() {
        mFragments = new ArrayList<>();
        initFragments();
        ViewPagerFragmentAdapter adapter = new ViewPagerFragmentAdapter(getFragmentManager(), mFragments);
        mViewPager.setAdapter(adapter);
    }

    @Override
    protected void fetchData() {

    }

    @Override
    public void forceRefresh() {
        mFragments.get(mTabLayout.getSelectedTabPosition()).forceRefresh();
    }

    private void initFragments() {
        /*mFragments.add(HomeListFragment.newInstance("头条"));
        mFragments.add(HomeListFragment.newInstance("新闻"));
        mFragments.add(HomeListFragment.newInstance("视频"));
        mFragments.add(HomeListFragment.newInstance("搞笑"));
        mFragments.add(HomeListFragment.newInstance("科技"));
        mFragments.add(HomeListFragment.newInstance("娱乐"));
        mFragments.add(HomeListFragment.newInstance("动漫"));
        mFragments.add(HomeListFragment.newInstance("影视"));
        mFragments.add(HomeListFragment.newInstance("直播"));*/
        Resources res = getActivity().getResources();
        String[] channelTitle = res.getStringArray(R.array.channel_info);
        String[] channelId = res.getStringArray(R.array.channel_info_id);
        for (int i = 0; i < channelId.length; i++) {
            mFragments.add(HomeListFragment.newInstance(channelTitle[i], channelId[i]));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
