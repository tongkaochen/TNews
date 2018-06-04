package com.tifone.tnews.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import com.tifone.mfrv.pullload.adapter.CommonViewHolder
import com.tifone.mfrv.pullload.adapter.MultiItemsAdapter
import com.tifone.tnews.R
import com.tifone.tnews.bean.news.MultiNewsArticleDataBean
import com.tifone.tnews.image.ImageLoader
import com.tifone.tnews.utils.TimeUtils
import org.jetbrains.annotations.NotNull
import java.util.concurrent.TimeUnit

class HomePagerViewAdapter(context: Context) :
        MultiItemsAdapter<MultiNewsArticleDataBean>(context) {
    private var mContext: Context = context

    companion object Constant{
        private val BASE_VIEW_TYPE: Int = 1000
        private val VIEW_TYPE_ONLY_TEXT: Int = BASE_VIEW_TYPE + 2
        private val VIEW_TYPE_WITH_IMAGE: Int = BASE_VIEW_TYPE + 3
        private val VIEW_TYPE_WITH_VIDEO: Int = BASE_VIEW_TYPE + 4
    }

    override fun getLayoutId(viewType: Int): Int {
        return when(viewType) {
            VIEW_TYPE_ONLY_TEXT -> R.layout.news_item_only_text
            VIEW_TYPE_WITH_IMAGE -> R.layout.news_item_with_image
            VIEW_TYPE_WITH_VIDEO -> R.layout.news_item_with_video
            else -> R.layout.news_item_only_text
        }
    }

    override fun convertContent(viewHolder: CommonViewHolder, bean: MultiNewsArticleDataBean) {
        val viewType: Int = viewHolder.viewType
        when(viewType) {
            VIEW_TYPE_ONLY_TEXT -> setupOnlyTextItem(viewHolder, bean)
            VIEW_TYPE_WITH_IMAGE -> setupWithImageItem(viewHolder, bean)
            VIEW_TYPE_WITH_VIDEO -> setupWithVideoItem(viewHolder, bean)
            else -> setupOnlyTextItem(viewHolder, bean)
        }
    }
    private fun setupWithVideoItem(viewHolder: CommonViewHolder, bean: MultiNewsArticleDataBean) {
        setupCommonItems(viewHolder, bean)
        if (bean.video_detail_info != null) {
            val url: String = bean.video_detail_info.detail_video_large_image.url
            val imageView: ImageView = viewHolder.getView(R.id.news_video_image)
            ImageLoader.loadImageToView(mContext, url, imageView, R.color.image_bg_color)
            val duration: Int = bean.video_duration
            val minute: Int = duration / 60
            val second: Int = duration % 60
            Log.e("tifone", "duration = $duration")
            viewHolder.setText(R.id.news_video_duration, formatTwo(minute) + ":" + formatTwo(second))
        }
    }
    private fun setupWithImageItem(viewHolder: CommonViewHolder, bean: MultiNewsArticleDataBean) {
        setupCommonItems(viewHolder, bean)
        if (bean.middle_image == null) {
            return
        }
        val imageUrl: String = bean.middle_image.url
        val imageView: ImageView = viewHolder.getView(R.id.news_image)
        ImageLoader.loadImageToView(mContext, imageUrl, imageView,
                R.drawable.pic_default_news_image)
    }
    private fun setupOnlyTextItem(viewHolder: CommonViewHolder, bean: MultiNewsArticleDataBean) {
        setupCommonItems(viewHolder, bean)
        viewHolder.setText(R.id.news_summary, bean.abstractX)
    }
    private fun setupCommonItems(viewHolder: CommonViewHolder, bean: MultiNewsArticleDataBean) {
        val cardView: CardView = viewHolder.getView(R.id.home_item_card_view)
        val userAvatarIv: ImageView = viewHolder.getView(R.id.user_avatar_iv)
        if (bean.media_info != null) {
            val avatarUrl: String =bean.media_info.avatar_url
            ImageLoader.loadImageToView(mContext, avatarUrl, userAvatarIv, R.drawable.ic_default_user_avatar)
        }
        viewHolder.setText(R.id.news_title, bean.title)
        viewHolder.setText(R.id.news_summary, bean.abstractX)
        val source: String = bean.source
        val commentsCount = bean.comment_count.toString() + mContext.resources.getString(R.string.comment_title)
        val time = TimeUtils.getTimeStampAgo(bean.behot_time.toLong())
        viewHolder.setText(R.id.news_header_tv,
                mContext.resources.getString(R.string.news_header_title, source, commentsCount, time))
        val moreDotIv: ImageView = viewHolder.getView(R.id.more_dots)
        RxView.clicks(cardView)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe({
                    Toast.makeText(mContext, "Item " + bean.title, Toast.LENGTH_SHORT).show()
                })
    }
    private fun formatTwo(input: Int): String {
        if (input in 0..9) {
            return "0$input"
        }
        return input.toString()
    }

    override fun setupItemViewType(position: Int): Int {
        val bean: MultiNewsArticleDataBean = getItem(position)
        if (hasImage(bean) && !bean.isHas_video) {
            return VIEW_TYPE_WITH_IMAGE
        }
        if (bean.isHas_video) {
            return VIEW_TYPE_WITH_VIDEO
        }
        return VIEW_TYPE_ONLY_TEXT
    }
    private fun hasImage(@NotNull bean: MultiNewsArticleDataBean): Boolean {
        if (bean.large_image_list != null && bean.large_image_list.size > 0) {
            return true
        }
        if (bean.middle_image != null && !TextUtils.isEmpty(bean.middle_image.url)) {
            return true
        }
        return false
    }

}