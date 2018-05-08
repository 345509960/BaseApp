package com.lyc.love.baselib.router;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lyc.love.baselib.BuildConfig;

public class Router implements IRouter {

    private Router(IRouter proxy){
        mProxy=proxy;
    }

    private  static volatile Router sInstance;

    public static Router getInstance() {
        if (sInstance==null){
            synchronized (Router.class){
                if (sInstance==null){
                    sInstance=new Router(new DefaultRouter());
                }
            }
        }
        return sInstance;
    }

    private IRouter mProxy;

    @Override
    public void init(Application application) {
       mProxy.init(application);
    }

    @Override
    public void navigation(RouterBuilder routerBuilder) {
        mProxy.navigation(routerBuilder);
    }
}
