package com.tifone.tnews.api

import com.tifone.tnews.bean.news.MultiNewsArticleBean
import com.tifone.tnews.bean.news.MultiNewsArticleDataBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface IMobileNewsApi {

    @GET("http://is.snssdk.com/api/news/feed/v62/?iid=5034850950&device_id=6096495334&refer=1&count=20&aid=13")
    fun getNewsArticle(
            @Query("category") category: String?,
            @Query("max_behot_time") max_behot_time: String
    ): Observable<MultiNewsArticleBean>

    @GET("http://lf.snssdk.com/api/news/feed/v62/?iid=12507202490&device_id=37487219424&refer=1&count=20&aid=13")
    fun getNewsArticle2(
            @Query("category") category: String?,
            @Query("max_behot_time") max_behot_time: String
    ): Observable<MultiNewsArticleBean>
}