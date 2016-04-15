package com.yuanyuan.baseapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;


/**
 * User: Geek_Soledad(msdx.android@qq.com)
 * Date: 2014-08-27
 * Time: 09:01
 * FIXME
 */
public abstract class BaseFragment extends Fragment {
    //上下文资源
    private Context context;
    //view对象
    private View view;

    //加载的页面
    public LoadingPager mLoadingPager;
    //获取加载的页面
    public LoadingPager getLoadingPager(){
        return mLoadingPager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mLoadingPager!=null){
            mLoadingPager=new LoadingPager(context) {
                @Override
                public LoadedResult initData() {
                    return BaseFragment.this.initData();
                }

                @Override
                public View initSuccessView() {
                    return BaseFragment.this.initSuccessView();
                }
            };
        }else{
            ((ViewGroup)mLoadingPager.getParent()).removeView(mLoadingPager);
        }
        return mLoadingPager;
    }

    /**
     //页面显示分析
     //Fragment共性-->页面共性-->视图的展示
     /**
     任何应用其实就只有4种页面类型
     ① 加载页面
     ② 错误页面
     ③ 空页面
     ④ 成功页面

     ①②③三种页面一个应用基本是固定的
     每一个fragment/activity对应的页面④就不一样
     进入应用的时候显示①,②③④需要加载数据之后才知道显示哪个
     */

    // 数据加载的流程
    /**
     ① 触发加载  	进入页面开始加载/点击某一个按钮的时候加载
     ② 异步加载数据  -->显示加载视图
     ③ 处理加载结果
     ① 成功-->显示成功视图
     ② 失败
     ① 数据为空-->显示空视图
     ② 数据加载失败-->显示加载失败的视图
     */
    /**
     * @des 真正加载数据,但是BaseFragment不知道具体实现,必须实现,定义成为抽象方法,让子类具体实现
     * @des 它是LoadingPager同名方法
     * @call loadData()方法被调用的时候
     */
    public abstract LoadingPager.LoadedResult initData();

    /**
     * @des 返回成功视图,但是不知道具体实现,所以定义成抽象方法
     * @des 它是LoadingPager同名方法
     * @call 正在加载数据完成之后,并且数据加载成功,我们必须告知具体的成功视图
     */
    public abstract View initSuccessView();

    public LoadingPager.LoadedResult checkStace(Object obj){
        if (obj==null){
           return LoadingPager.LoadedResult.EMPTY;
        }
        if(obj instanceof List){
            if(((List)obj).size()==0){
                return LoadingPager.LoadedResult.EMPTY;
            }
        }
        if (obj instanceof Map){
            if(((Map)obj).size()==0){
                return LoadingPager.LoadedResult.EMPTY;
            }
        }
        return LoadingPager.LoadedResult.SUCCESS;
    }
}
