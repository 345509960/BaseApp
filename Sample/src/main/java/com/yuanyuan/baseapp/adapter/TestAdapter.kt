package com.yuanyuan.baseapp.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.lyc.love.baselib.view.recyclerview.RecyclerAdapter
import com.lyc.love.baselib.view.recyclerview.RecyclerHolder
import com.yuanyuan.baseapp.R
import com.yuanyuan.baseapp.entity.TestEntity

/**
 * Created by liangyc
 * Time :2018/11/7
 * Des:
 */
class TestAdapter(context: Context, list: ArrayList<TestEntity>?) : RecyclerAdapter<TestEntity>(context, list) {


    override fun getContentView(parent: ViewGroup?, viewType: Int): View {
        return createViewById(parent, R.layout.listview_test)
    }

    override fun onInitView(holder: RecyclerHolder?, `object`: TestEntity?, position: Int) {
        holder?.setText(R.id.tv_title, `object`?.name)
    }
}