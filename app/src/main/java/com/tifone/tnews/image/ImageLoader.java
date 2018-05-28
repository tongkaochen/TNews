package com.tifone.tnews.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by tongkao.chen on 2018/5/15.
 */
@GlideModule
public final class ImageLoader extends AppGlideModule{

    public static void loadImageToView(Context activity, String srcUrl, ImageView intoView, int errDrawable) {
        GlideApp.with(activity)
                .load(srcUrl)
                .error(GlideApp.with(activity).load(srcUrl))
                .into(intoView);
    }

}
