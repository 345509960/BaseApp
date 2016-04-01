package com.yuanyuan.baseapp.biz;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/28.
 */
public abstract class BaseBiz {


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
