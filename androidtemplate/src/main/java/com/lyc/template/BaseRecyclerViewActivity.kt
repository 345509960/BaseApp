package com.lyc.template

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.lyc.love.baselib.ui.AbstractActivity

import com.lyc.love.baselib.view.recyclerview.RecyclerAdapter
import kotlinx.android.synthetic.main.componet_recyclerview.*

/**
 * Time :2018/11/6
 * Des:
 *
 * @author liangyc
 */
abstract class BaseRecyclerViewActivity<T> : AbstractActivity() {

    var mTRecyclerAdapter: RecyclerAdapter<T>? = null

    var mList: ArrayList<T>? = null

    var mItemDecorations: ArrayList<RecyclerView.ItemDecoration>? = null


    override fun getLayoutId(): Int {
        return R.layout.componet_recyclerview
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }


    private fun initData() {
        mList = initList()
        mItemDecorations = initItemDecoration()
        mTRecyclerAdapter = initAdapter()
        rv.layoutManager = initLayoutManager()

        if (mItemDecorations != null && mItemDecorations?.size!! > 0) {
            for (index in 0..mItemDecorations?.size!! - 1) {
                rv.addItemDecoration(mItemDecorations!![index])
            }
        }

        rv.adapter = mTRecyclerAdapter

        mTRecyclerAdapter?.setOnItemClickListener { viewHolder, position ->
            onItemClickListener(viewHolder, position)
        }

    }

    open fun onItemClickListener(viewHolder: RecyclerView.ViewHolder, position: Int) {

    }

    open fun initItemDecoration(): ArrayList<RecyclerView.ItemDecoration>? {
        return null
    }

    abstract fun initLayoutManager(): RecyclerView.LayoutManager

    abstract fun initAdapter(): RecyclerAdapter<T>

    abstract fun initList(): ArrayList<T>


}
