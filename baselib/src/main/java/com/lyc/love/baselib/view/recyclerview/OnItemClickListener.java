package com.lyc.love.baselib.view.recyclerview;

import android.support.v7.widget.RecyclerView;

/**
 * Created by liangyc
 * Time :2018/3/23
 * Des:
 */

public interface OnItemClickListener {
    void onItemClick(RecyclerView.ViewHolder viewHolder, int position);
}
