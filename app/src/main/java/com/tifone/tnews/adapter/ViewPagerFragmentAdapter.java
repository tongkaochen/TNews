package com.tifone.tnews.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tifone.tnews.home.BaseHomeFragment;

import java.util.List;

/**
 * Created by tongkao.chen on 2018/5/4.
 */

public class ViewPagerFragmentAdapter extends FragmentStatePagerAdapter {
    private List<BaseHomeFragment> mDataSet;
    public ViewPagerFragmentAdapter(FragmentManager fm, List<BaseHomeFragment> dataSet) {
        super(fm);
        mDataSet = dataSet;
    }

    @Override
    public Fragment getItem(int position) {
        return mDataSet.get(position);
    }

    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mDataSet.get(position).fragmentTitle();
    }
}
