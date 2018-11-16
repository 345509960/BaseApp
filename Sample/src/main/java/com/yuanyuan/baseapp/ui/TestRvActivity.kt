package com.yuanyuan.baseapp.ui

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.lyc.love.baselib.view.recyclerview.drag.DragItemTouchHelper
import com.lyc.love.baselib.view.recyclerview.drag.OnRecyclerItemClickListener
import com.lyc.love.baselib.view.recyclerview.RecyclerAdapter
import com.lyc.love.baselib.view.recyclerview.SuctionTopDecoration
import com.lyc.template.BaseRecyclerViewActivity
import com.yuanyuan.baseapp.R
import com.yuanyuan.baseapp.adapter.TestAdapter
import com.yuanyuan.baseapp.entity.TestEntity

@Route(path = "/rv/test")
class TestRvActivity : BaseRecyclerViewActivity<TestEntity>() {



    override fun realRv(rv: RecyclerView?) {
        super.realRv(rv)
        val itemTouchHelper = ItemTouchHelper(DragItemTouchHelper(mTRecyclerAdapter))
        itemTouchHelper.attachToRecyclerView(rv)

//        rv?.addOnItemTouchListener(object : OnRecyclerItemClickListener(rv) {
//            override fun onItemClick(viewHolder: RecyclerView.ViewHolder?) {
//                ToastUtils.showLong("${viewHolder?.getAdapterPosition()}")
//            }
//
//            override fun onLongClick(viewHolder: RecyclerView.ViewHolder?) {
//                ToastUtils.showLong("${viewHolder?.getAdapterPosition()}")
//                //当 item 被长按且不是第一个时，开始拖曳这个 item（这里只是一个特殊需求）
//                if (viewHolder?.getLayoutPosition() != 0) {
//                    itemTouchHelper.startDrag(viewHolder)
//                }
//            }
//
//        })
    }

    override fun initLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this@TestRvActivity)
    }

    override fun initAdapter(): RecyclerAdapter<TestEntity> {
        return TestAdapter(this@TestRvActivity, mList)
    }

    override fun initList(): ArrayList<TestEntity> {
        var tags = ArrayList<TestEntity>()
        tags.add(TestEntity("123", "123456", "1"))
        tags.add(TestEntity("123", "123456", "1"))
        tags.add(TestEntity("123", "123456", "1"))
        tags.add(TestEntity("123", "123456", "2"))
        tags.add(TestEntity("123", "123456", "2"))
        tags.add(TestEntity("123", "123456", "3"))
        tags.add(TestEntity("123", "123456", "4"))
        tags.add(TestEntity("123", "123456", "1"))
        tags.add(TestEntity("123", "123456", "1"))
        tags.add(TestEntity("123", "123456", "1"))
        tags.add(TestEntity("123", "123456", "2"))
        tags.add(TestEntity("123", "123456", "2"))
        tags.add(TestEntity("123", "123456", "3"))
        tags.add(TestEntity("123", "123456", "4"))
        tags.add(TestEntity("123", "123456", "1"))
        tags.add(TestEntity("123", "123456", "1"))
        tags.add(TestEntity("123", "123456", "1"))
        tags.add(TestEntity("123", "123456", "2"))
        tags.add(TestEntity("123", "123456", "2"))
        tags.add(TestEntity("123", "123456", "3"))
        tags.add(TestEntity("123", "123456", "4"))
        tags.add(TestEntity("123", "123456", "1"))
        tags.add(TestEntity("123", "123456", "1"))
        tags.add(TestEntity("123", "123456", "1"))
        tags.add(TestEntity("123", "123456", "2"))
        tags.add(TestEntity("123", "123456", "2"))
        tags.add(TestEntity("123", "123456", "3"))
        tags.add(TestEntity("123", "123456", "4"))
        tags.add(TestEntity("123", "123456", "1"))
        tags.add(TestEntity("123", "123456", "1"))
        tags.add(TestEntity("123", "123456", "1"))
        tags.add(TestEntity("123", "123456", "2"))
        tags.add(TestEntity("123", "123456", "2"))
        tags.add(TestEntity("123", "123456", "3"))
        tags.add(TestEntity("123", "123456", "4"))
        return tags
    }

    override fun initView(view: View?) {
        super.initView(view)
        setToolbarTitle("封装Rv操作")
    }


    override fun onItemClickListener(viewHolder: RecyclerView.ViewHolder, position: Int) {
        super.onItemClickListener(viewHolder, position)
        Toast.makeText(this@TestRvActivity, "点击了${position}", Toast.LENGTH_SHORT).show()
    }

    override fun initItemDecoration(): ArrayList<RecyclerView.ItemDecoration>? {
        val itemDecorations = ArrayList<RecyclerView.ItemDecoration>()
        val dividerItemDecoration = SuctionTopDecoration<TestEntity>(mList, this@TestRvActivity)
        itemDecorations.add(dividerItemDecoration)
        return itemDecorations
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_right, menu)
        return true
    }


}
