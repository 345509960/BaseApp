package com.yuanyuan.baseapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lyc.love.baselib.ui.AbstractActivity;
import com.yuanyuan.baseapp.R;
import com.yuanyuan.baseapp.constant.SampleConstant;

@Route(path = SampleConstant.ROUTER_TWO)
public class TwoActivity extends AbstractActivity {


    @Override
    public void initView(View view) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_two;
    }


    public void startActionBar1(View view) {
        startActivity(new Intent(this,ActionBarActivity.class));
    }
}
