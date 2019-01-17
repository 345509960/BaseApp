package com.yuanyuan.baseapp.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.yuanyuan.baseapp.R
import com.mob.wrappers.ShareSDKWrapper.share
import cn.sharesdk.onekeyshare.OnekeyShare
import android.R.attr.dialogTitle
import android.content.Intent


class ShareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
    }


    public fun share(view: View) {
        val oks = OnekeyShare()
        //关闭sso授权
        oks.disableSSOWhenAuthorize()

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("分享")
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn")
        // text是分享文本，所有平台都需要这个字段
        oks.text = "我是分享文本"
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg")//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn")
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我是测试评论文本")
        // 启动分享GUI
        oks.show(this)
    }


    fun local_share(view: View) {
        var share_intent = Intent()
        share_intent.action = Intent.ACTION_SEND//设置分享行为
        share_intent.type = "text/plain"//设置分享内容的类型
        share_intent.putExtra(Intent.EXTRA_SUBJECT, "内容标题")//添加分享内容标题
        share_intent.putExtra(Intent.EXTRA_TEXT, "内容")//添加分享内容
        //创建分享的Dialog
        share_intent = Intent.createChooser(share_intent, "标题")
        startActivity(share_intent)
    }
}
