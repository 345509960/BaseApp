package com.lyc.love.baselib.router;

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
    public void navigation(RouterBuilder routerBuilder) {
        mProxy.navigation(routerBuilder);
    }
}
