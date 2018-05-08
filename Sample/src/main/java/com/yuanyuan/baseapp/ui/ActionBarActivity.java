package com.yuanyuan.baseapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanyuan.baseapp.R;
import com.yuanyuan.baseapp.constant.SampleConstant;

@Route(path = SampleConstant.ROUTER_ACTIONBAR)
public class ActionBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);


        Toolbar mToolbar =findViewById(com.lyc.love.baselib.R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar()!=null){
            //默认为null
            getSupportActionBar().setLogo(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
////            case android.R.id.home:
////                finish();
////                break;
////        }
        return super.onOptionsItemSelected(item);
    }


}
