package com.tifone.tnews.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tifone.tnews.home.IHomeContrast;

/**
 * Created by tongkao.chen on 2018/5/4.
 */

/**
 * 所有Fragment的父类
 */
public abstract class BaseFragment extends Fragment {
    protected static final String FRAGMENT_ID = "fragment_id";

    protected abstract int inflaterViewId();
    protected abstract void initView(View root);
    protected abstract void initViewData();
    protected abstract void fetchData();
    public abstract void forceRefresh();

    public static Bundle setupBundle(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT_ID, name);
        return bundle;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(inflaterViewId(), container, false);
        initView(view);
        initViewData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchData();
    }
}
