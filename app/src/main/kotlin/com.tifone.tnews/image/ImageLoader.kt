package com.tifone.tnews.image

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class ImageLoader : AppGlideModule() {
    companion object STATIC {
        fun loadImageToView(activity: Context, srcUrl: String, intoView: ImageView, errDrawable: Int) {
            GlideApp.with(activity)
                    .load(srcUrl)
                    .error(GlideApp.with(activity).load(srcUrl))
                    .into(intoView)
        }
    }

}