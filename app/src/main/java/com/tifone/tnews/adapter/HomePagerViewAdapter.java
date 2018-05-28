package com.tifone.tnews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tifone.mfrv.pullload.adapter.CommonViewHolder;
import com.tifone.mfrv.pullload.adapter.MultiItemsAdapter;
import com.tifone.tnews.R;
import com.tifone.tnews.bean.news.MultiNewsArticleDataBean;
import com.tifone.tnews.image.ImageLoader;
import com.tifone.tnews.utils.TimeUtils;


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
        logger("======== position = "+ position);
        MultiNewsArticleDataBean bean = getItem(position);
        if (bean != null && !bean.isHas_image() && !bean.isHas_video()) {
            // text content
            return VIEW_TYPE_ONLY_TEXT;
        } else if (bean != null && bean.isHas_image()) {
            // with image content
            return VIEW_TYPE_WITH_IMAGE;
        } else if (bean != null && bean.isHas_video()) {
            // with video content
            return VIEW_TYPE_WITH_VIDEO;
        }
        return VIEW_TYPE_ONLY_TEXT;
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

    private void setupCommonItems(@NonNull CommonViewHolder commonViewHolder,
                                  @NonNull final MultiNewsArticleDataBean bean) {
        logger("setupCommonItems");
        CardView cardView = commonViewHolder.getView(R.id.home_item_card_view);
        commonViewHolder.setOnClickListener(R.id.home_item_card_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Item " + bean.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
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
    }

    private void logger(String msg) {
        Log.e("tifone", msg);
    }

}
