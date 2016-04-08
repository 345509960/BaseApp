package com.yuanyuan.baseapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.Toast;

public abstract class BaseActivity extends FragmentActivity {
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
}
