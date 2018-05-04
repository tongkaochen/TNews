package com.tifone.tnews.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tifone.tnews.R;
import com.tifone.tnews.bean.home.HomeTestBean;

import java.util.List;

/**
 * Created by tongkao.chen on 2018/5/4.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    private List<HomeTestBean> mDataSet;

    public HomeRecyclerViewAdapter(List<HomeTestBean> dataSet) {
        mDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.cardView.getContext(), "Item " + position + " clicked", Toast.LENGTH_SHORT).show();
            }
        });
        holder.titleTv.setText(mDataSet.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView contentIv;
        private TextView titleTv;

        private ViewHolder(View root) {
            super(root);
            cardView = root.findViewById(R.id.home_item_card_view);
            contentIv = root.findViewById(R.id.content_iv);
            titleTv = root.findViewById(R.id.content_title);
        }
    }

    public void adapterDataSetInserted(HomeTestBean bean) {
        if (null == bean) {
            return;
        }
        mDataSet.add(bean);
        notifyItemInserted(mDataSet.size());
    }
}
