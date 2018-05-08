package com.tifone.tnews.utils;

import com.tifone.tnews.BuildConfig;
import com.tifone.tnews.InitApp;
import com.tifone.tnews.api.INewsApi;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tongkao.chen on 2018/5/7.
 */

public class RetrofitFactory {
    private volatile static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitFactory.class) {
                if (retrofit == null) {
                    File httpCacheDirectory = new File(InitApp.appContext.getCacheDir(), "HttpCache");
                    int maxSize = 10 * 1024 * 1024; // 10 MiB
                    Cache cache = new Cache(httpCacheDirectory, maxSize);

                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .cache(cache)
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .addInterceptor(cacheInterceptor)
                            .retryOnConnectionFailure(true);
                    if (BuildConfig.DEBUG) {
                        // 打开Http log
                        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        builder.addInterceptor(loggingInterceptor);
                    }
                    retrofit = new Retrofit.Builder()
                            .baseUrl(INewsApi.HOST)
                            .client(builder.build())
                            // 设置Gson转换器
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();

                }
            }
        }
        return retrofit;
    }

    // 缓存机制
    private static Interceptor cacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtils.isNetworkConnected(InitApp.appContext)) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isNetworkConnected(InitApp.appContext)) {
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                // 无网络
                int maxStale = 60 * 60 * 24 * 7; // 一周
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };
}
