package com.yuanyuan.baseapp.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Aron on 2016/3/31.
 */
public class GsonUtils<T>{
    public  Gson mGson;
    //初始化Gson

    public GsonUtils() {
        this.mGson = new Gson();
    }

    public Gson getmGson() {
        return mGson;
    }

    //对象转字符串
    public  String  Entity2Json(T t){
        return mGson.toJson(t);
    }
    //对象集合转字符串
    public  String Entity2Jsons(List<T> t){
        return mGson.toJson(t);
    }
    //字符串转对象
    public  T Json2Entity(String s,Class c){
        return (T) mGson.fromJson(s,c);
    }
    //字符串转对象集合
    public  List<T> Json2Entitys(String s){
        return mGson.fromJson(s,new TypeToken<List<T>>(){}.getType());
    }

    /**
     * 取出List再解析
     */
    //字符串转对象集合
    public  List<T> Json2EntitysByL(String s){
        JSONObject jsonStr=null;
        try {
            jsonStr=new JSONObject(s);
            return mGson.fromJson(jsonStr.get("list").toString(),new TypeToken<List<T>>(){}.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
       return null;
    }
}
