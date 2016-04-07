package com.yuanyuan.baseapp.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yuanyuan.baseapp.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aron on 2016/3/31.
 */
public abstract class LBaseAdapter extends BaseAdapter {
    public Context context;
    public List<T> datasource;
    public LayoutInflater inflater;
    public ImageLoader imageLoader;
    public LBaseAdapter(Context context) {
       this(context,null);
    }

    public LBaseAdapter(Context context,List<T> datasource) {
        this(context,datasource,null);
    }
    public  LBaseAdapter(Context context, List<T> datasource, ImageLoader imageLoader) {
        super();
        this.context=context;
        this.datasource= (datasource!=null?datasource:new ArrayList<T>());
        this.imageLoader = imageLoader;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    //获取数据源的数量
    @Override
    public int getCount() {
        return datasource.size();
    }
    //获取当前Position对象
    @Override
    public Object getItem(int position) {
        return datasource.get(position);
    }
    //获取当前Position对象ID
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItemView(position, convertView, parent);
    }
    public abstract View getItemView(int position ,View convertView,ViewGroup parent);

    //设置index的对象
    public void set(int index, T elem) {
        datasource.set(index, elem);
        notifyDataSetChanged();
    }
    //删除该对象
    public void remove(T elem) {
        datasource.remove(elem);
        notifyDataSetChanged();
    }
    //删除该index的对象
    public void remove(int index) {
        datasource.remove(index);
        notifyDataSetChanged();
    }
    //替换
    public void replaceAll(List<T> elem) {
        datasource.clear();
        datasource.addAll(elem);
        notifyDataSetChanged();
    }
    //判断是否包含elem的元素
    public boolean contains(T elem) {
        return datasource.contains(elem);
    }
    /** Clear data list */
    public void clear() {
        datasource.clear();
        notifyDataSetChanged();
    }
}
