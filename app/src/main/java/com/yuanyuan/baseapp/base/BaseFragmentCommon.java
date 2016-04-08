package com.yuanyuan.baseapp.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public abstract class BaseFragmentCommon extends Fragment {

	//共有的属性
	/**
	 *定义在BaseFragment中，用来获取上下文
	 */
	public Context mContext;
	/**
	 * 定义在BaseFragment中，用来“膨胀”视图
	 */
	public LayoutInflater mInflater;
	/**
	 * 定义在BaseFragment中，在子Fragment的onCreateView方法中，可以引用子Fragment的视图
	 */
	public View mView;

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		init();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext=getActivity();
		mInflater=inflater;
		mView=initView();
		return mView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initData();
		initListener();
		super.onActivityCreated(savedInstanceState);
	}



	/**
	 * @des 初始化view,而且是必须实现,但是不知道具体实现,定义成抽象方法
	 * @return
	 */
	public abstract View initView();

	
	public void init() {
		// TODO

	}
	
	public void initData() {
		// TODO

	}

	public void initListener() {
		// TODO

	}

	//共有的方法

	/**
	 * 跳转页面
	 */
	public void jump(Activity context, Class clz, boolean isFinish) {
		Intent intent = new Intent(context, clz);
		startActivity(intent);
		if (isFinish) {
			context.finish();
		}
	}

	public void toast(String text) {
		Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
	}



	public void jump(Activity context, Intent intent, boolean isFinish) {
		startActivity(intent);
		if (isFinish) {
			context.finish();
		}
	}

	/**
	 * 关闭该Activity
	 */
	public void Setfinish(){
		if(mContext instanceof Activity){
			((Activity)mContext).finish();
		}

	}
}
