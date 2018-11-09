package com.lyc.love.baselib.router;

import android.app.Application;

import com.alibaba.android.arouter.facade.callback.NavigationCallback;

public interface IRouter {

    void init(Application application);

    /**
     * 使用路由构造器跳转
     */
    void navigation(RouterBuilder routerBuilder);

    void navigation(RouterBuilder routerBuilder, NavigationCallback callback);
}
