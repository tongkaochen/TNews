package com.tifone.tnews.adapter;

import android.content.Context;
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
import com.tifone.tnews.bean.news.MultiNewsArticleDataBean;
import com.tifone.tnews.utils.TimeUtils;

import java.util.List;

/**
 * Created by tongkao.chen on 2018/5/4.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    private List<MultiNewsArticleDataBean> mDataSet;

    public HomeRecyclerViewAdapter(List<MultiNewsArticleDataBean> dataSet) {
        mDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_with_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Context context = holder.cardView.getContext();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Item " + position + " clicked", Toast.LENGTH_SHORT).show();
            }
        });
        MultiNewsArticleDataBean bean = mDataSet.get(position);
        holder.newsTitleTv.setText(bean.getTitle());
        holder.newsSummaryTv.setText(bean.getAbstractX());
        //
        String source = bean.getSource();
        String comments = bean.getComment_count() + context.getResources().getString(R.string.comment_title);
        String time = TimeUtils.getTimeStampAgo(bean.getBehot_time());
        holder.newsHeaderTv.setText(context.getResources().getString(R.string.news_header_title, source, comments, time));
        // 设置图片
        if (bean.isHas_image()) {
            String imageUrl = bean.getMiddle_image().getUrl_list().get(0).getUrl();
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView userAvatarIv;
        private TextView newsHeaderTv;
        private TextView newsTitleTv;
        private TextView newsSummaryTv;
        private ImageView moreDotsIv;
        private ImageView newsImageIv;

        private ViewHolder(View root) {
            super(root);
            cardView = root.findViewById(R.id.home_item_card_view);
            userAvatarIv = root.findViewById(R.id.user_avatar_iv);
            newsHeaderTv = root.findViewById(R.id.news_header_tv);
            newsTitleTv = root.findViewById(R.id.news_title);
            newsSummaryTv = root.findViewById(R.id.news_summary);
            moreDotsIv = root.findViewById(R.id.more_dots);
            newsImageIv = root.findViewById(R.id.news_image);
        }
    }

    public void adapterDataSetInserted(List<MultiNewsArticleDataBean> beans) {
        if (null == beans) {
            return;
        }
        for (MultiNewsArticleDataBean bean : beans) {
            mDataSet.add(bean);
            notifyItemInserted(mDataSet.size());
        }
    }
}
