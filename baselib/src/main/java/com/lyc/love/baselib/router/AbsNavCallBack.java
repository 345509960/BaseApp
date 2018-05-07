package com.lyc.love.baselib.router;

import com.alibaba.android.arouter.facade.Postcard;

public abstract class AbsNavCallBack implements INavCallBack{

    @Override
    public void onFound(Postcard postcard) {

    }

    @Override
    public void onLost(Postcard postcard) {

    }

    @Override
    public abstract void onArrival(Postcard postcard);

    @Override
    public void onInterrupt(Postcard postcard) {

    }
}
