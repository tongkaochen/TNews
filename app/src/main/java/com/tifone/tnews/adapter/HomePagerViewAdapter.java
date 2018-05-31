package com.tifone.tnews.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.tifone.mfrv.pullload.adapter.CommonViewHolder;
import com.tifone.mfrv.pullload.adapter.MultiItemsAdapter;
import com.tifone.tnews.R;
import com.tifone.tnews.bean.news.MultiNewsArticleDataBean;
import com.tifone.tnews.image.ImageLoader;
import com.tifone.tnews.utils.TimeUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;


public class HomePagerViewAdapter extends MultiItemsAdapter<MultiNewsArticleDataBean> {
    private static final int BASE_VIEW_TYPE = 1000;
    private static final int VIEW_TYPE_ONLY_TEXT = BASE_VIEW_TYPE + 2;
    private static final int VIEW_TYPE_WITH_IMAGE = BASE_VIEW_TYPE + 3;
    private static final int VIEW_TYPE_WITH_VIDEO = BASE_VIEW_TYPE + 4;
    private Context mContext;

    public HomePagerViewAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public int setupItemViewType(int position) {
        MultiNewsArticleDataBean bean = getItem(position);
        if (bean == null) {
            return VIEW_TYPE_ONLY_TEXT;
        }
        if (!hasImage(bean) && !bean.isHas_video()) {
            // text content
            return VIEW_TYPE_ONLY_TEXT;
        } else if (hasImage(bean) && !bean.isHas_video()) {
            // with image content
            return VIEW_TYPE_WITH_IMAGE;
        } else if (bean.isHas_video()) {
            // with video content
            return VIEW_TYPE_WITH_VIDEO;
        }
        return VIEW_TYPE_ONLY_TEXT;
    }

    private boolean hasImage(@NonNull MultiNewsArticleDataBean bean) {
        if (bean.getLarge_image_list() != null && bean.getLarge_image_list().size() > 0) {
            return true;
        }
        if (bean.getMiddle_image() != null && !TextUtils.isEmpty(bean.getMiddle_image().getUrl())) {
            return true;
        }
        return false;
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType) {
            case VIEW_TYPE_ONLY_TEXT:
                return R.layout.news_item_only_text;
            case VIEW_TYPE_WITH_IMAGE:
                return R.layout.news_item_with_image;
            case VIEW_TYPE_WITH_VIDEO:
                return R.layout.news_item_with_video;
        }
        return R.layout.news_item_only_text;
    }

    @Override
    public void convertContent(CommonViewHolder commonViewHolder, MultiNewsArticleDataBean multiNewsArticleDataBean) {
        int viewType = commonViewHolder.viewType;
        switch (viewType) {
            case VIEW_TYPE_ONLY_TEXT:
                setupOnlyTextItem(commonViewHolder, multiNewsArticleDataBean);
                break;
            case VIEW_TYPE_WITH_IMAGE:
                setupWithImageItem(commonViewHolder, multiNewsArticleDataBean);
                break;
            case VIEW_TYPE_WITH_VIDEO:
                setupWithVideoItem(commonViewHolder, multiNewsArticleDataBean);
                break;
        }

    }

    private void setupWithVideoItem(CommonViewHolder commonViewHolder,
                                    MultiNewsArticleDataBean bean) {
        logger("setupWithVideoItem");
        setupCommonItems(commonViewHolder, bean);
        if (bean.getVideo_detail_info() != null) {
            String url = bean.getVideo_detail_info().getDetail_video_large_image().getUrl();
            //commonViewHolder.setImageResource(R.id.news_video_image, url);
            ImageView videoImageView = commonViewHolder.getView(R.id.news_video_image);
            ImageLoader.loadImageToView(mContext, url, videoImageView, R.color.image_bg_color);
            int duration = bean.getVideo_duration();
            int minute = duration / 60;
            int second = duration % 60;
            Log.e("tifone", "duration = " + duration);
            commonViewHolder.setText(R.id.news_video_duration, formatTwo(minute) + ":" + formatTwo(second));
        }
    }

    private String formatTwo(int input) {
        if (0 <= input && input < 10) {
            return "0" + String.valueOf(input);
        }
        return String.valueOf(input);
    }

    private void setupWithImageItem(CommonViewHolder commonViewHolder,
                                    MultiNewsArticleDataBean bean) {
        logger("setupWithImageItem");
        setupCommonItems(commonViewHolder, bean);
        // 设置图片
        if (bean.getMiddle_image() == null) {
            return;
        }
        String imageUrl = bean.getMiddle_image().getUrl_list().get(0).getUrl();
        ImageView imageView = commonViewHolder.getView(R.id.news_image);
        ImageLoader.loadImageToView(mContext, imageUrl, imageView,
                R.drawable.pic_default_news_image);
    }

    private void setupOnlyTextItem(@NonNull CommonViewHolder commonViewHolder,
                                   @NonNull final MultiNewsArticleDataBean bean) {
        logger("setupOnlyTextItem");
        setupCommonItems(commonViewHolder, bean);
        // 设置新闻的描述
        commonViewHolder.setText(R.id.news_summary, bean.getAbstractX());
    }

    @SuppressLint("CheckResult")
    private void setupCommonItems(@NonNull CommonViewHolder commonViewHolder,
                                  @NonNull final MultiNewsArticleDataBean bean) {
        logger("setupCommonItems");
        CardView cardView = commonViewHolder.getView(R.id.home_item_card_view);

        ImageView userAvatarIv = commonViewHolder.getView(R.id.user_avatar_iv);
        // 设置头像信息
        if (bean.getMedia_info() != null) {
            String avatarUrl = bean.getMedia_info().getAvatar_url();
            ImageLoader.loadImageToView(mContext, avatarUrl, userAvatarIv,
                    R.drawable.ic_default_user_avatar);
        }
        // 设置标题
        commonViewHolder.setText(R.id.news_title, bean.getTitle());
        // 设置新闻的描述
        commonViewHolder.setText(R.id.news_summary, bean.getAbstractX());
        String source = bean.getSource();
        String comments = bean.getComment_count() + mContext.getResources()
                .getString(R.string.comment_title);
        String time = TimeUtils.getTimeStampAgo(bean.getBehot_time());
        // 设置头部信息
        commonViewHolder.setText(R.id.news_header_tv, mContext.getResources()
                .getString(R.string.news_header_title, source, comments, time));

        ImageView moreDotsIv = commonViewHolder.getView(R.id.more_dots);
        RxView.clicks(cardView)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        // click
                        Toast.makeText(mContext, "Item " + bean.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void logger(String msg) {
        Log.e("tifone", msg);
    }

}
