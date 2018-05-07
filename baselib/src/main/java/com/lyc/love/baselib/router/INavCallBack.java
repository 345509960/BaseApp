package com.lyc.love.baselib.router;

import com.alibaba.android.arouter.facade.Postcard;


/**
    @author lyc
    create date: 2018/5/7 0007
    des: copy from Code Arouter
**/
public interface INavCallBack {
    /**
     * Callback when find the destination.
     *
     * @param postcard meta
     */
    void onFound(Postcard postcard);

    /**
     * Callback after lose your way.
     *
     * @param postcard meta
     */
    void onLost(Postcard postcard);

    /**
     * Callback after navigation.
     *
     * @param postcard meta
     */
    void onArrival(Postcard postcard);

    /**
     * Callback on interrupt.
     *
     * @param postcard meta
     */
    void onInterrupt(Postcard postcard);
}
