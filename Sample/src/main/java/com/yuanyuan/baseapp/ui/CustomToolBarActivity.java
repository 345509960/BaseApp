package com.yuanyuan.baseapp.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyc.love.baselib.ui.AbstractActivity;
import com.yuanyuan.baseapp.R;

public class CustomToolBarActivity extends AbstractActivity {


    @Override
    protected void initView(View view) {
        RelativeLayout toolBar= (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.toolbar_custom,getToolbar(),false);
        TextView textView=toolBar.findViewById(R.id.tv_title);
        textView.setText("我的自定制的标题栏");
//        setSupportActionBarBackgroundDrawable(R.color.main_color);
        setCustomToolBar(toolBar);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_tool_bar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_right, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        Log.d("toolbar","onDestroy");
        super.onDestroy();
    }
}
