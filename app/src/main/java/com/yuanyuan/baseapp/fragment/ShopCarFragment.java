package com.yuanyuan.baseapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanyuan.baseapp.base.BaseFragment;

/**
 * Created by Administrator on 2016/4/6.
 */
public class ShopCarFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView=new TextView(context);
        textView.setText("shopcar");
        return textView;
    }
}
