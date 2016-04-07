package com.yuanyuan.baseapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;


/**
 * User: Geek_Soledad(msdx.android@qq.com)
 * Date: 2014-08-27
 * Time: 09:01
 * FIXME
 */
public class BaseFragment extends Fragment {
    //上下文资源
    protected Context context;
    //view对象
    protected View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

}
