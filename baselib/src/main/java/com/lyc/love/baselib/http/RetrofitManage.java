package com.lyc.love.baselib.http;

import com.lyc.love.baselib.http.fastJsonConver.FastJsonConverterFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitManage {

    public static Retrofit getInstance(){
        return new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/movie/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();
    }
}
