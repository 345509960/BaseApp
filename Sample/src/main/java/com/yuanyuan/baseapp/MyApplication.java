package com.yuanyuan.baseapp;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.lyc.love.baselib.BaseApplication;
import com.mob.MobSDK;

public class MyApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);


    }
}
