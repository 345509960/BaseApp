package com.yuanyuan.baseapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanyuan.baseapp.base.BaseFragment;
import com.yuanyuan.baseapp.base.BaseFragmentCommon;

/**
 * Created by Administrator on 2016/4/6.
 */
public class HomeFragment extends BaseFragmentCommon {


    @Override
    public View initView() {
        TextView textView=new TextView(mContext);
        textView.setText("Home");
        return textView;
    }
}
