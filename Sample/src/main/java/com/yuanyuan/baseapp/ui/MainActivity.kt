package com.yuanyuan.baseapp.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.lyc.love.baselib.router.Router
import com.lyc.love.baselib.router.RouterBuilder
import com.lyc.love.baselib.ui.AbstractActivity
import com.yuanyuan.baseapp.R
import com.yuanyuan.baseapp.constant.SampleConstant


class MainActivity : AbstractActivity() {


    override fun initView(view: View) {
        //        setSupportActionBarLogo(null);
        setSupportActionBarLogo(R.mipmap.ic_launcher)
        supportActionBar!!.setDisplayUseLogoEnabled(true)
        setDisplayShowTitleEnabled(true)
        setDisplayHomeAsUpEnabled(true)

        //        setStatusBarColor(R.color.black);

        toolbar.navigationContentDescription = "xxx"

        toolbar.setNavigationOnClickListener { Toast.makeText(this@MainActivity, "点击了导航按钮", Toast.LENGTH_SHORT).show() }

        //修改状态栏颜色
        //        setStatusBarColor(android.R.color.holo_blue_light);
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


    fun start(view: View) {
        Router.getInstance().navigation(RouterBuilder()
                .withContext(this@MainActivity)
                .withUrl(SampleConstant.ROUTER_TWO)
                .build())
    }

    fun startCustom(view: View) {
        Router.getInstance().navigation(RouterBuilder()
                .withContext(this)
                .withUrl(SampleConstant.ROUTER_CUSTOMTOOL)
                .build())
    }

    fun startActionBar(view: View) {
        Router.getInstance().navigation(RouterBuilder()
                .withContext(this)
                .withUrl(SampleConstant.ROUTER_ACTIONBAR)
                .build())
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_right, menu)

        val serachTtem = menu.findItem(R.id.action_search)
        val searchView = serachTtem.actionView as SearchView

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        //定义一个监听器
        val expandListener = object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                Toast.makeText(this@MainActivity, "expand", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                Toast.makeText(this@MainActivity, "collapse", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        //设置监听器
        serachTtem.setOnActionExpandListener(expandListener)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> Toast.makeText(this@MainActivity, "搜索", Toast.LENGTH_SHORT).show()
            R.id.action_setting -> Toast.makeText(this@MainActivity, "设置", Toast.LENGTH_SHORT).show()
            R.id.action_show -> Toast.makeText(this@MainActivity, "显示", Toast.LENGTH_SHORT).show()
            R.id.action_hide -> Toast.makeText(this@MainActivity, "隐藏", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }
}
