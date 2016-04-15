package com.yuanyuan.baseapp.fragment;

import android.support.v4.util.SparseArrayCompat;

import com.yuanyuan.baseapp.base.BaseFragment;
import com.yuanyuan.baseapp.base.BaseFragmentCommon;

/**
 * 工厂模式获取Fragment on 2016/4/6.
 */
public class FragmentFactory {
    public static final int	FRAGMENT_HOME = 0;
    public static final int	FRAGMENT_CLASS= 1;
    public static final int	FRAGMENT_SHOPCARD= 2;
    public static final int	FRAGMENT_MINE= 3;
    //緩存Fragment數組
    public static SparseArrayCompat<BaseFragmentCommon> cachesFragment=new SparseArrayCompat<>();
    //获取Fragment的工厂
    public static BaseFragmentCommon getFragment(int position){
        BaseFragmentCommon fragment=null;
        BaseFragmentCommon tmpFragment=cachesFragment.get(position);
        if(tmpFragment!=null){
            fragment=tmpFragment;
            return fragment;
        }
        switch (position){
            case FRAGMENT_HOME:
                fragment=new HomeFragment();
                break;
            case FRAGMENT_CLASS:
                fragment=new ClassFragment();
                break;
            case FRAGMENT_SHOPCARD:
                fragment=new ShopCarFragment();
                break;
            case FRAGMENT_MINE:
                fragment=new MineFragment();
                break;
            default:
                break;
        }
        // 保存对应的fragment
        cachesFragment.put(position,fragment);
        return fragment;
    }
}
