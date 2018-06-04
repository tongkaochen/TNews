package com.tifone.tnews.api

import com.tifone.tnews.bean.channel.HomeChannelBean
import io.reactivex.Observable
import retrofit2.http.GET

interface IChannelInfoApi {
    // 频道信息api
    // https://is.snssdk.com/article/category/get_subscribed/v1/?
    @GET("https://is.snssdk.com/article/category/get_subscribed/v1/?")
    fun getChannelInfo(): Observable<HomeChannelBean>
}