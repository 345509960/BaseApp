package com.lyc.love.baselib;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.lyc.love.baselib.router.Router;


public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initBaseConfig();
    }

    private void initBaseConfig() {
        Utils.init(this);
        Router.getInstance().init(this);
    }

}
