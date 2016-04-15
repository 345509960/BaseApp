package com.yuanyuan.baseapp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.yuanyuan.baseapp.utils.T;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends FragmentActivity {
    /**
     * 保存所有进入的代码
     */
    public static List<Activity> activityList = new ArrayList<Activity>();
    //Toast对象
    private Toast toast;
    //上下文对象
    protected Context context;
    private double leastTime=0;
    protected void onCreate(Bundle savedInstanceState) {
        this.context=this;
        this.initBeforeOnCreate(true);
        super.onCreate(savedInstanceState);
        this.initView();
        this.initData();
        this.initListener();
    }
    //在OnCreate之前做的操作
    public void initBeforeOnCreate(boolean isShowHeadScreen) {

    }

    //初始化布局
    public abstract void initView();
    //初始化数据
    public void initData(){

    }
    //初始化监听器
    public void initListener(){

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        leastTime=System.currentTimeMillis();
        if (System.currentTimeMillis()-leastTime>2){
            Toast.makeText(context,"退出",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"退出",Toast.LENGTH_SHORT).show();
        }
    }

    public View getView(View v, int resId){
        return v.findViewById(resId);
    }




    /**
     * 回到主页
     */
    public void goHome()
    {
        for (int i = activityList.size() - 1; i >= 1; i--)
        {
            Activity activity = activityList.get(i);
            activity.finish();
        }
    }
    //提示Toast
    public void toast(String text){
        if (toast != null)
        {
            toast.setText("添加购物车成功");
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        } else
        {
            toast = Toast.makeText(context, "添加购物车成功", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityList.remove(this);
    }
}
