package com.yuanyuan.baseapp.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 跟网络相关的工具类  on 2016/3/24.
 */
public class NetUtils {
    private NetUtils()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context)
    {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity)
        {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected())
            {
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity)
    {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }


    //网络访问数据开始
    /**
     * OkHttpUtils封装方式
     * @param url
     */
    public void okHttpConnectionGetMethod(String url, HashMap<String,String> map, StringCallback callback){
        GetBuilder builder= OkHttpUtils.get().url(url);
        Iterator it=map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,String> entry= (Map.Entry<String, String>) it.next();
            builder.addParams(entry.getKey(),entry.getValue());
        }
        builder.build().execute(callback);
    }
    /**
     * OkHttpUtils Post封装方式
     * @param url
     */
    public void okHttpConnectionPostMethod(String url, HashMap<String,String> map, StringCallback callback){
        PostFormBuilder builder=OkHttpUtils
                .post()
                .url(url);
        Iterator it=map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,String> entry= (Map.Entry<String, String>) it.next();
            builder.addParams(entry.getKey(),entry.getValue());
        }
        builder.build().execute(callback);
    }
    //网络访问数据结束

}
