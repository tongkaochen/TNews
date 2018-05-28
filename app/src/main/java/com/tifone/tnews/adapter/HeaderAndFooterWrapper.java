package com.tifone.tnews.adapter;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tongkao.chen on 2018/5/15.
 */

public class HeaderAndFooterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 10000;
    private static final int VIEW_TYPE_FOOTER = 10001;

    private RecyclerView.Adapter mInnerAdapter;
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();
    public HeaderAndFooterWrapper(RecyclerView.Adapter innerAdapter) {
        mInnerAdapter = innerAdapter;
    }
    public void addHeaderView(View view) {
        mHeaderViews.put(VIEW_TYPE_HEADER + mHeaderViews.size(), view);
    }

    public void addFooterView(View view) {
        mFooterViews.put(VIEW_TYPE_FOOTER + mFooterViews.size(), view);
    }
    public int getHeaderCount() {
        return mHeaderViews.size();
    }
    public int getFooterCount() {
        return mFooterViews.size();
    }
    private boolean isHeaderPos(int position) {
        return position < getHeaderCount();
    }
    private boolean isFooterPos(int position) {
        return position >= getContentItemsCount() + getHeaderCount();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            return new HeaderViewHolder(mHeaderViews.get(viewType));
        } else if (mFooterViews.get(viewType) != null) {
            return new FooterViewHolder(mFooterViews.get(viewType));
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isHeaderPos(position) || isFooterPos(position)) {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - getHeaderCount());
    }

    public int getContentItemsCount() {
        return mInnerAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("tifone", "position = " + position +", getContentItemsCount = " + getContentItemsCount());
        if (isHeaderPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterPos(position)) {
            return mFooterViews.keyAt(position - getHeaderCount() - getContentItemsCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeaderCount());
    }

    @Override
    public int getItemCount() {
        return getContentItemsCount() + getHeaderCount() + getFooterCount();
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
