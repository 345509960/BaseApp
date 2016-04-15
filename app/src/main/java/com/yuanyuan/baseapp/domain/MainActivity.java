package com.yuanyuan.baseapp.domain;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.yuanyuan.baseapp.R;
import com.yuanyuan.baseapp.base.BaseActivity;
import com.yuanyuan.baseapp.fragment.FragmentFactory;
import com.yuanyuan.baseapp.view.CustomViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    //View开始//

    @Bind(R.id.customViewPager)
    CustomViewPager mCustomViewPager;
    @Bind(R.id.rg_bottom_layout)
    RadioGroup mRgBottomLayout;
    LinearLayout mllMainHeader1;
    @Bind(R.id.root)
    View root;
    //View结束//

    //数据对象开始//

    //内部适配器
    private InnerPageAdapter adapter;
    //数据对象结束//
    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mllMainHeader1= (LinearLayout) getView(root,R.id.header_main1);
    }

    public void initData() {
        adapter = new InnerPageAdapter(getSupportFragmentManager());
        mCustomViewPager.setAdapter(adapter);
    }

    public void initListener() {
        mRgBottomLayout.setOnCheckedChangeListener(this);
    }

    /**
     //     * 内部页面适配器
     //     */
//    //FragmentStatePagerAdapter是一个不会缓存的Fragment适配器 如果需要大量的Fragment可以这样设置
    private class InnerPageAdapter extends FragmentPagerAdapter {

        public InnerPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //从工厂里面拿出Fragment返回
            return FragmentFactory.getFragment(position);
        }
        //固定设置大小 如果需要变化重新设置
        @Override
        public int getCount() {
            return 4;
        }
    }

    //监听器开启//

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                //第一个参数是设置当前的View  第二个参数设置当前view的动画取消
                mCustomViewPager.setCurrentItem(0,false);
                break;
            case R.id.rb_nearby:
                mCustomViewPager.setCurrentItem(1,false);
                break;
            case R.id.rb_featured:
                mCustomViewPager.setCurrentItem(2,false);
                break;
            case R.id.rb_min:
                mCustomViewPager.setCurrentItem(3,false);
                break;
        }
    }

    //监听器结束//
}
