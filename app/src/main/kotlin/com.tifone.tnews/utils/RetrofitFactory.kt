package com.tifone.tnews.utils

import android.util.Log
import com.tifone.tnews.BuildConfig
import com.tifone.tnews.InitApp
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitFactory {
    @Volatile
    private var retrofit: Retrofit? = null

    fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            synchronized(RetrofitFactory::class.java) {
                if (retrofit == null) {
                    val httpCacheDirectory = File(InitApp.appContext!!.cacheDir, "HttpCache")
                    val maxSize = 10 * 1024 * 1024 // 10 MiB
                    val cache = Cache(httpCacheDirectory, maxSize.toLong())

                    val builder = OkHttpClient.Builder()
                            .cache(cache)
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .addInterceptor(cacheInterceptor)
                            .retryOnConnectionFailure(true)
                    if (BuildConfig.DEBUG) {
                        // 打开Http log
                        val loggingInterceptor = HttpLoggingInterceptor()
                        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                        builder.addInterceptor(loggingInterceptor)
                    }
                    Log.e("tifone", "retrofit init")
                    retrofit = Retrofit.Builder()
                            .baseUrl(ConstantUtils.HOST)
                            .client(builder.build())
                            // 设置Gson转换器
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build()

                }
            }
        }
        return retrofit!!
    }

    // 缓存机制
    private val cacheInterceptor = Interceptor { chain ->
        var request = chain.request()
        if (!NetworkUtils.isNetworkConnected(InitApp.appContext)) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
        }
        val originalResponse = chain.proceed(request)
        if (NetworkUtils.isNetworkConnected(InitApp.appContext)) {
            val cacheControl = request.cacheControl().toString()
            originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build()
        } else {
            // 无网络
            val maxStale = 60 * 60 * 24 * 7 // 一周
            originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
        }
    }
}