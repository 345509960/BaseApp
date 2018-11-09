package com.yuanyuan.baseapp.ui

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.lyc.love.baselib.view.recyclerview.RecyclerAdapter
import com.lyc.love.baselib.view.recyclerview.SuctionTopDecoration
import com.lyc.template.BaseRecyclerViewActivity
import com.yuanyuan.baseapp.R
import com.yuanyuan.baseapp.adapter.TestAdapter
import com.yuanyuan.baseapp.entity.TestEntity

@Route(path = "/rv/test")
class TestRvActivity : BaseRecyclerViewActivity<TestEntity>() {

    override fun initLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this@TestRvActivity)
    }

    override fun initAdapter(): RecyclerAdapter<TestEntity> {
        return TestAdapter(this@TestRvActivity, mList)
    }

    override fun initList(): ArrayList<TestEntity> {
        var tags = ArrayList<TestEntity>()
        tags.add(TestEntity("123","123456","1"))
        tags.add(TestEntity("123","123456","1"))
        tags.add(TestEntity("123","123456","1"))
        tags.add(TestEntity("123","123456","2"))
        tags.add(TestEntity("123","123456","2"))
        tags.add(TestEntity("123","123456","3"))
        tags.add(TestEntity("123","123456","4"))
        tags.add(TestEntity("123","123456","1"))
        tags.add(TestEntity("123","123456","1"))
        tags.add(TestEntity("123","123456","1"))
        tags.add(TestEntity("123","123456","2"))
        tags.add(TestEntity("123","123456","2"))
        tags.add(TestEntity("123","123456","3"))
        tags.add(TestEntity("123","123456","4"))
        tags.add(TestEntity("123","123456","1"))
        tags.add(TestEntity("123","123456","1"))
        tags.add(TestEntity("123","123456","1"))
        tags.add(TestEntity("123","123456","2"))
        tags.add(TestEntity("123","123456","2"))
        tags.add(TestEntity("123","123456","3"))
        tags.add(TestEntity("123","123456","4"))
        tags.add(TestEntity("123","123456","1"))
        tags.add(TestEntity("123","123456","1"))
        tags.add(TestEntity("123","123456","1"))
        tags.add(TestEntity("123","123456","2"))
        tags.add(TestEntity("123","123456","2"))
        tags.add(TestEntity("123","123456","3"))
        tags.add(TestEntity("123","123456","4"))
        tags.add(TestEntity("123","123456","1"))
        tags.add(TestEntity("123","123456","1"))
        tags.add(TestEntity("123","123456","1"))
        tags.add(TestEntity("123","123456","2"))
        tags.add(TestEntity("123","123456","2"))
        tags.add(TestEntity("123","123456","3"))
        tags.add(TestEntity("123","123456","4"))
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
        itemDecorations?.add(dividerItemDecoration)
        return itemDecorations
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_right, menu)
        return true
    }
}
