package com.tifone.tnews.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tifone.tnews.base.BaseFragment;
import com.tifone.tnews.home.IHomeContrast.IHomeView;
import com.tifone.tnews.home.IHomeContrast.IHomePresenter;


/**
 * Created by tongkao.chen on 2018/5/3.
 */

public abstract class BaseHomeFragment extends BaseFragment implements IHomeView {
    protected IHomePresenter mPresenter;

    public abstract String fragmentTitle();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(mPresenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadData();
    }
}
