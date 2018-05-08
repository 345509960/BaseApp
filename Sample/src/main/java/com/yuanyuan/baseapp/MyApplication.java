package com.yuanyuan.baseapp;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.lyc.love.baselib.BaseApplication;

public class MyApplication extends BaseApplication{


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
