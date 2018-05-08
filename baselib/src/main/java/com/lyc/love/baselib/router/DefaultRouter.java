package com.lyc.love.baselib.router;

import android.app.Application;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lyc.love.baselib.BuildConfig;

public class DefaultRouter implements IRouter {

    @Override
    public void init(Application application) {
        if (BuildConfig.DEBUG){
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(application);
    }

    @Override
    public void navigation(RouterBuilder routerBuilder) {
       if (routerBuilder==null){
           return ;
       }
       Postcard postcard=null;
       if (!TextUtils.isEmpty(routerBuilder.mUrl)){
           postcard=ARouter.getInstance().build(routerBuilder.mUrl);
           postcard.with(routerBuilder.mBundle);
           if (routerBuilder.mContext!=null){
               postcard.navigation(routerBuilder.mContext);
           }else {
               postcard.navigation();
           }
       }

    }
}
