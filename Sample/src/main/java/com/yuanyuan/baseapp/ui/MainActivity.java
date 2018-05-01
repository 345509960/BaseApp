package com.yuanyuan.baseapp.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lyc.love.baselib.ui.AbstractActivity;
import com.yuanyuan.baseapp.R;


public class MainActivity extends AbstractActivity {


    @Override
    protected void initView(View view) {
//        setSupportActionBarLogo(null);
        setSupportActionBarLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setDisplayShowTitleEnabled(true);
        setDisplayHomeAsUpEnabled(true);

//        setStatusBarColor(R.color.black);

        getToolbar().setNavigationContentDescription("xxx");

        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击了导航按钮", Toast.LENGTH_SHORT).show();
            }
        });

        //修改状态栏颜色
//        setStatusBarColor(android.R.color.holo_blue_light);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    public void start(View view)
    {
        Intent i = new Intent(this, TwoActivity.class);
        startActivity(i);
    }

    public void startCustom(View view)
    {
        Intent i = new Intent(this, CustomToolBarActivity.class);
        startActivity(i);
    }

    public void startActionBar(View view)
    {
        Intent i = new Intent(this, ActionBarActivity.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_right,menu);

        MenuItem serachTtem=menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) serachTtem.getActionView();

        SearchManager searchManager= (SearchManager) getSystemService(Context.SEARCH_SERVICE);
       if (searchManager!=null){
           searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
       }

        //定义一个监听器
        MenuItem.OnActionExpandListener expandListener = new MenuItem.OnActionExpandListener()
        {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item)
            {
                Toast.makeText(MainActivity.this, "expand", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item)
            {
                Toast.makeText(MainActivity.this, "collapse", Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        //设置监听器
        serachTtem.setOnActionExpandListener(expandListener);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_search:
                Toast.makeText(MainActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_setting:
                Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_show:
                Toast.makeText(MainActivity.this, "显示", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_hide:
                Toast.makeText(MainActivity.this, "隐藏", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
