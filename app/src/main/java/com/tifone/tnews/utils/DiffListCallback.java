package com.tifone.tnews.utils;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class DiffListCallback extends DiffUtil.Callback {

    private List<?> mOldList;
    private List<?> mNewList;
    private DiffListCallback(List<?> oldList, List<?> newList) {
        mOldList = oldList;
        mNewList = newList;
    }
    public static void create(List<?> oldList, List<?> newList, RecyclerView.Adapter targetAdapter) {
        DiffListCallback diffListCallback = new DiffListCallback(oldList, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffListCallback);
        // dispatch to adapter
        diffResult.dispatchUpdatesTo(targetAdapter);
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldList.get(oldItemPosition).equals(mNewList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldList.get(oldItemPosition).hashCode() == mNewList.get(newItemPosition).hashCode();
    }
}
