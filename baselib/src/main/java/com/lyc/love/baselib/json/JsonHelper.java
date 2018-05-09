package com.lyc.love.baselib.json;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;


/**
    @author lyc
    create date: 2018/5/9 0009
    des: Json工具类
**/
public class JsonHelper {
    /**
     * 把json string 转化成类对象
     *
     * @param str
     * @param t
     * @return
     */
    public static <T> T parseObject(String str, Class<T> t) {
        try {
            if (str != null && !"".equals(str.trim())) {
                T res = JSONArray.parseObject(str.trim(), t);
                return res;
            }
        } catch (Exception e) {
            Log.e("数据转换出错", "exception:" + e.getMessage());
        }
        return null;
    }

    /**
     * 把json string 转化成类对象
     *
     * @param str
     * @param t
     * @return
     */
    public static <T> List<T> parseArray(String str, Class<T> t) {
        try {
            if (str != null && !"".equals(str.trim())) {
                List<T> res = JSONArray.parseArray(str.trim(), t);
                return res;
            }
        } catch (Exception e) {
            Log.e("数据转换出错", "exception:" + e.getMessage());
        }
        return null;
    }

    /**
     * 把类对象转化成json string
     *
     * @param t
     * @return
     */
    public static <T> String toJson(T t) {
        try {
            return JSONObject.toJSONString(t);
        } catch (Exception e) {
            Log.e("数据转换出错", "exception:" + e.getMessage());
        }
        return "";
    }
}
