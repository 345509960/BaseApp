package com.yuanyuan.baseapp.biz;

import com.yuanyuan.baseapp.utils.MD5Utils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;

/**
 * Created by Aron on 2016/3/31.
 */
public class HomeFragmentAdsEntityBiz extends BaseBiz{

    public void getTopAllAbs(StringCallback callback){
        HashMap<String ,String > map=new HashMap<String, String>();
        map.put("Code", MD5Utils.md5("7haowang"));
        map.put("name","index_app1");
        okHttpConnectionGetMethod("http://c2.wxw33.com/newapi/getAdList.php", map,callback);
    }

    public void getBottomAbs(StringCallback callback){
        HashMap<String ,String > map=new HashMap<String, String>();
        map.put("Code", MD5Utils.md5("7haowang"));
        map.put("name", "index_app3");
        okHttpConnectionGetMethod("http://c2.wxw33.com/newapi/get_indexAd.php",map,callback);
    }
}
