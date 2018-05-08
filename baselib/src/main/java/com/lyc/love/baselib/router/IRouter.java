package com.lyc.love.baselib.router;

import android.app.Application;

public interface IRouter {

    void init(Application application);

    /**
     * 使用路由构造器跳转
     */
    void navigation(RouterBuilder routerBuilder);

}
