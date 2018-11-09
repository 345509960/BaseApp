package com.lyc.template

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.lyc.love.baselib.router.Router
import com.lyc.love.baselib.router.RouterBuilder

class LaucherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_back_feed_back_temp)
    }


    override fun onResume() {
        super.onResume()
        laucher()
    }

    private fun laucher() {

        Router.getInstance().navigation(RouterBuilder().withContext(this@LaucherActivity).withUrl("/main/home"), object : NavigationCallback {
            override fun onLost(postcard: Postcard?) {

            }

            override fun onFound(postcard: Postcard?) {
            }

            override fun onInterrupt(postcard: Postcard?) {
            }

            override fun onArrival(postcard: Postcard?) {
                finish()
            }

        })

    }
}
