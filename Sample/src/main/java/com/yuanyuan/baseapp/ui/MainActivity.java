package com.yuanyuan.baseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lyc.love.baselib.ui.AbstractActivity;


public class MainActivity extends AbstractActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBarLogo(R.mipmap.ic_launcher);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_right,menu);

        MenuItem serachTtem=menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) serachTtem.getActionView();
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(MainActivity.this, "onClose", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_SHORT).show();

            }
        });

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
