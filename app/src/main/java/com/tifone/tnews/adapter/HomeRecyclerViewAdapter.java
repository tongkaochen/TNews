package com.tifone.tnews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tifone.tnews.R;
import com.tifone.tnews.bean.news.MultiNewsArticleDataBean;
import com.tifone.tnews.image.ImageLoader;
import com.tifone.tnews.utils.TimeUtils;

import java.util.List;

/**
 * Created by tongkao.chen on 2018/5/4.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int BASE_VIEW_TYPE = 1000;
    private static final int VIEW_TYPE_HEADER = BASE_VIEW_TYPE + 1;
    private static final int VIEW_TYPE_ONLY_TEXT = BASE_VIEW_TYPE + 2;
    private static final int VIEW_TYPE_WITH_IMAGE = BASE_VIEW_TYPE + 3;
    private static final int VIEW_TYPE_WITH_VIDEO = BASE_VIEW_TYPE + 4;
    private static final int VIEW_TYPE_FOOTER = BASE_VIEW_TYPE + 5;

    private List<MultiNewsArticleDataBean> mDataSet;

    public HomeRecyclerViewAdapter(List<MultiNewsArticleDataBean> dataSet) {
        mDataSet = dataSet;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("tifone", "onCreateViewHolder = " + viewType);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.news_item_only_text, parent, false);
        switch (viewType) {
            case VIEW_TYPE_ONLY_TEXT:
                view = inflater.inflate(R.layout.news_item_only_text, parent, false);
                return new ViewHolderOnlyText(view);
            case VIEW_TYPE_WITH_IMAGE:
                view = inflater.inflate(R.layout.news_item_with_image, parent, false);
                return new ViewHolderWithImage(view);
            case VIEW_TYPE_WITH_VIDEO:
                view = inflater.inflate(R.layout.news_item_with_video, parent, false);
                return new ViewHolderWithVideo(view);
        }
        return new ViewHolderOnlyText(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        Log.e("tifone", "onBindViewHolder = " + position);
        if (holder instanceof ViewHolderOnlyText) {
            resolveView((ViewHolderOnlyText) holder, position);
        } else if (holder instanceof ViewHolderWithImage) {
            resolveView((ViewHolderWithImage) holder, position);
        } else if (holder instanceof ViewHolderWithVideo) {
            resolveView((ViewHolderWithVideo) holder, position);
        }

    }
    private void resolveView(ViewHolderOnlyText holder, final int position) {
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
        if (bean.getMedia_info() != null) {
            String avatarUrl = bean.getMedia_info().getAvatar_url();
            ImageLoader.loadImageToView(context, avatarUrl, holder.userAvatarIv, R.drawable.ic_default_user_avatar);
        }
    }
    private void resolveView(ViewHolderWithImage holder, final int position) {

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
            ImageLoader.loadImageToView(context, imageUrl, holder.newsImageIv, R.drawable.pic_default_news_image);
        }
        if (bean.getMedia_info() != null) {
            String avatarUrl = bean.getMedia_info().getAvatar_url();
            ImageLoader.loadImageToView(context, avatarUrl, holder.userAvatarIv, R.drawable.ic_default_user_avatar);
        }
    }
    private void resolveView(ViewHolderWithVideo holder, final int position) {
        final Context context = holder.cardView.getContext();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Item " + position + " clicked", Toast.LENGTH_SHORT).show();
            }
        });
        MultiNewsArticleDataBean bean = mDataSet.get(position);
        holder.newsTitleTv.setText(bean.getTitle());
        //
        String source = bean.getSource();
        String comments = bean.getComment_count() + context.getResources().getString(R.string.comment_title);
        String time = TimeUtils.getTimeStampAgo(bean.getBehot_time());
        holder.newsHeaderTv.setText(context.getResources().getString(R.string.news_header_title, source, comments, time));

        if (bean.getMedia_info() != null) {
            String avatarUrl = bean.getMedia_info().getAvatar_url();
            ImageLoader.loadImageToView(context, avatarUrl, holder.userAvatarIv, R.drawable.ic_default_user_avatar);
        }
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        MultiNewsArticleDataBean bean = mDataSet.get(position);
        if (bean != null && !bean.isHas_image() && !bean.isHas_video()) {
            // text content
            return VIEW_TYPE_ONLY_TEXT;
        } else if(bean != null && bean.isHas_image()) {
            // with image content
            return VIEW_TYPE_WITH_IMAGE;
        }  else if(bean != null && bean.isHas_video()) {
            // with video content
            return VIEW_TYPE_WITH_VIDEO;
        }

        return VIEW_TYPE_ONLY_TEXT;
    }

    class ViewHolderWithImage extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView userAvatarIv;
        private TextView newsHeaderTv;
        private TextView newsTitleTv;
        private TextView newsSummaryTv;
        private ImageView moreDotsIv;
        private ImageView newsImageIv;

        private ViewHolderWithImage(View root) {
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
    class ViewHolderWithVideo extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView userAvatarIv;
        private TextView newsHeaderTv;
        private TextView newsTitleTv;
        private ImageView moreDotsIv;

        private ViewHolderWithVideo(View root) {
            super(root);
            cardView = root.findViewById(R.id.home_item_card_view);
            userAvatarIv = root.findViewById(R.id.user_avatar_iv);
            newsHeaderTv = root.findViewById(R.id.news_header_tv);
            newsTitleTv = root.findViewById(R.id.news_title);
            moreDotsIv = root.findViewById(R.id.more_dots);
        }
    }
    class ViewHolderOnlyText extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView userAvatarIv;
        private TextView newsHeaderTv;
        private TextView newsTitleTv;
        private TextView newsSummaryTv;
        private ImageView moreDotsIv;

        private ViewHolderOnlyText(View root) {
            super(root);
            cardView = root.findViewById(R.id.home_item_card_view);
            userAvatarIv = root.findViewById(R.id.user_avatar_iv);
            newsHeaderTv = root.findViewById(R.id.news_header_tv);
            newsTitleTv = root.findViewById(R.id.news_title);
            newsSummaryTv = root.findViewById(R.id.news_summary);
            moreDotsIv = root.findViewById(R.id.more_dots);
        }
    }

    public void adapterDataSetInserted(List<MultiNewsArticleDataBean> beans) {
        if (null == beans) {
            return;
        }
        for (MultiNewsArticleDataBean bean : beans) {
            Log.e("tifone", "bean = " + bean.getTitle());
            mDataSet.add(bean);
            notifyItemInserted(getItemCount());
        }
    }
}
