package com.yuanyuan.baseapp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lyc.love.baselib.ui.AbstractActivity;

public class TwoActivity extends AbstractActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_two;
    }
}
