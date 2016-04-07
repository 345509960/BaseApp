package com.yuanyuan.baseapp.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yuanyuan.baseapp.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/31.
 */
public abstract class LRVBaseAdapter extends RecyclerView.Adapter {
    public Context context;
    public List<T> datasource;
    public LayoutInflater inflater;
    public ImageLoader imageLoader;
    public LRVBaseAdapter(Context context) {
        this(context,null);
    }

    public LRVBaseAdapter(Context context,List<T> datasource) {
        this(context,datasource,null);
    }
    public  LRVBaseAdapter(Context context, List<T> datasource, ImageLoader imageLoader) {
        super();
        this.context=context;
        this.datasource= (datasource!=null?datasource:new ArrayList<T>());
        this.imageLoader = imageLoader;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract  void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return datasource.size();
    }

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
