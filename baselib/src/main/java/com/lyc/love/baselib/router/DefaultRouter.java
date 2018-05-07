package com.lyc.love.baselib.router;

import android.text.TextUtils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

public class DefaultRouter implements IRouter {

    @Override
    public void navigation(RouterBuilder routerBuilder) {
       if (routerBuilder==null){
           return ;
       }
       Postcard postcard;
       if (!TextUtils.isEmpty(routerBuilder.mUrl)){
           postcard=ARouter.getInstance().build(routerBuilder.mUrl);
           postcard.with(routerBuilder.mBundle);
       }
    }
}
