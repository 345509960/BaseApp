package com.yuanyuan.baseapp.fragment;

import android.view.View;
import android.widget.TextView;

import com.yuanyuan.baseapp.base.BaseFragmentCommon;

/**
 * Created by Administrator on 2016/4/6.
 */
public class ShopCarFragment extends BaseFragmentCommon {

    @Override
    public View initView() {
        TextView textView=new TextView(mContext);
        textView.setText("shopcar");
        return textView;
    }
}
