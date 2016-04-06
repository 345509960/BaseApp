package com.yuanyuan.baseapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.yuanyuan.baseapp.R;
import com.yuanyuan.baseapp.domain.BaseActivity;
import com.yuanyuan.baseapp.fragment.FragmentFactory;
import com.yuanyuan.baseapp.view.CustomViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements CustomViewPager.OnPageChangeListener,RadioGroup.OnCheckedChangeListener {
    //View开始//
    @Bind(R.id.choose_city)
    TextView mChooseCity;
    @Bind(R.id.ll_top_layout)
    LinearLayout mLlTopLayout;
    @Bind(R.id.content_tv)
    TextView mContentTv;
    @Bind(R.id.search_right_ib)
    ImageButton mSearchRightIb;
    @Bind(R.id.header_search_ll)
    LinearLayout mHeaderSearchLl;
    @Bind(R.id.header_iv_back)
    ImageView mHeaderIvBack;
    @Bind(R.id.header_layout_leftview_container)
    LinearLayout mHeaderLayoutLeftviewContainer;
    @Bind(R.id.header_layout_middle_title)
    TextView mHeaderLayoutMiddleTitle;
    @Bind(R.id.header_layout_middleview_container)
    LinearLayout mHeaderLayoutMiddleviewContainer;
    @Bind(R.id.good_details_iv_share)
    ImageView mGoodDetailsIvShare;
    @Bind(R.id.good_detils_iv_goodcar)
    ImageView mGoodDetilsIvGoodcar;
    @Bind(R.id.iv_contact_tips)
    TextView mIvContactTips;
    @Bind(R.id.good_detils_rl_goodcar)
    RelativeLayout mGoodDetilsRlGoodcar;
    @Bind(R.id.header_layout_rightview_container)
    LinearLayout mHeaderLayoutRightviewContainer;
    @Bind(R.id.good_deatils_iv_all)
    TextView mGoodDeatilsIvAll;
    @Bind(R.id.main_ll_title)
    RelativeLayout mMainLlTitle;
    @Bind(R.id.customViewPager)
    CustomViewPager mCustomViewPager;
    @Bind(R.id.main_buttom_include)
    RadioGroup mRgBottomLayout;
    @Bind(R.id.header_main1)
    LinearLayout mllMainHeader1;
    @Bind(R.id.header_main2)
    RelativeLayout mllMainHeader2;
    //View结束//


    //数据对象开始//

        //内部适配器
    private InnerPageAdapter adapter;
    //数据对象结束//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initValues();
        initListener();
    }

    private void initView() {
        mllMainHeader2.setVisibility(View.GONE);
    }

    private void initListener() {
        mRgBottomLayout.setOnCheckedChangeListener(this);
    }
    protected void initValues() {
        adapter = new InnerPageAdapter(getSupportFragmentManager());
        mCustomViewPager.setAdapter(adapter);
    }
    /**
     * 从其他Activity跳转过来后
     * 指定到相应的Fragment
     */
    public CustomViewPager getViewPager() {
        return mCustomViewPager;
    }
    /**
     * 内部页面适配器
     */
    //FragmentStatePagerAdapter是一个不会缓存的Fragment适配器 如果需要大量的Fragment可以这样设置
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
            switch (position){
                case 0:

                    mRgBottomLayout.check(R.id.rb_home);
                    break;
                case 1:
                    mRgBottomLayout.check(R.id.rb_nearby);
                    break;
                case 2:
                    mRgBottomLayout.check(R.id.rb_featured);
                    break;
                case 3:
                    mRgBottomLayout.check(R.id.rb_min);
                    break;
            }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

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
