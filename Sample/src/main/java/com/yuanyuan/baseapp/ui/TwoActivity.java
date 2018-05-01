package com.yuanyuan.baseapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lyc.love.baselib.ui.AbstractActivity;
import com.yuanyuan.baseapp.R;

public class TwoActivity extends AbstractActivity {


    @Override
    protected void initView(View view) {
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
