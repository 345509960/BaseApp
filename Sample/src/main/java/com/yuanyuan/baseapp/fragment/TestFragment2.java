package com.yuanyuan.baseapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanyuan.baseapp.R;


/**
 * Created by Administrator on 2016/3/24.
 */
public class TestFragment2 extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment, container,false);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
