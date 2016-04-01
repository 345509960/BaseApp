package com.yuanyuan.baseapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.Toast;

public class BaseActivity extends FragmentActivity {
    //上下文对象
    protected Context context;
    private double leastTime=0;
    protected void onCreate(Bundle savedInstanceState) {
        this.context=this;
        this.initBeforeOnCreate(true);
        super.onCreate(savedInstanceState);
    }
    //在OnCreate之前做的操作
    protected void initBeforeOnCreate(boolean isShowHeadScreen) {
        if(isShowHeadScreen){
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
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
