package com.yuanyuan.baseapp.ui

import android.Manifest
import android.Manifest.permission.WRITE_SECURE_SETTINGS
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.umeng.analytics.AnalyticsConfig.getLocation
import com.yuanyuan.baseapp.R
import com.yuanyuan.baseapp.constant.SampleConstant
import kotlinx.android.synthetic.main.activity_detecting_system.*
import android.provider.Settings.Secure
import android.provider.Settings.Secure.LOCATION_MODE
import android.telephony.TelephonyManager
import android.widget.Toast
import com.lyc.love.baselib.detectingsystem.*
import java.lang.reflect.Method


@Route(path = SampleConstant.ROUTER_DETECTING_SYSTYEM)
class DetectingSystemActivity : AppCompatActivity() {
    private val flag = true//true为屏蔽，false、为不屏蔽

    private var mHeadsetReceiver: HeadsetReceiver? = null
    private var mKeyReceiver: KeyReceiver? = null
    //home键,+，-，锁屏键
    private var mKey = arrayOf(0, 0, 0, 0)

    private val BAIDU_READ_PHONE_STATE = 100//定位权限请求
    private val PRIVATE_CODE = 1315//开启GPS权限
    private val REQUEST_CODE_CALL_PERMISSION = 1318

    val LOCATIONGPS = arrayOf(WRITE_SECURE_SETTINGS)
    //Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE,
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detecting_system)

        //耳机拔插接收器
        mHeadsetReceiver = HeadsetReceiver()
        mHeadsetReceiver?.addMonitor(object : HeadsetMonitor {
            override fun headsetState(state: Boolean) {
                if (state) {
                    tv_headset.setText("true")
                }
            }

        })
        registerReceiver(mHeadsetReceiver, IntentFilter(Intent.ACTION_HEADSET_PLUG))
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        intentFilter.addAction(Intent.ACTION_SCREEN_ON)
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF)
        //按键检测
        mKeyReceiver = KeyReceiver()
        mKeyReceiver?.addMonitor(object : KeyMonitor {
            override fun keyState(state: Int) {
                if (state == KeyReceiver.HOME) {
                    mKey[0] = 1
                    checkKey()
                } else if (state == KeyReceiver.SCREEN) {
                    mKey[3] = 1
                    checkKey()
                }
            }

        })
        registerReceiver(mKeyReceiver, intentFilter)

        btn_gps.setOnClickListener {
            showGPSContacts()
        }

        btn_phone.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CODE_CALL_PERMISSION)
            } else {
                startCallorDialPhone()
            }
        }


        tv_zhiwen.text=BiometricPromptUtils(this@DetectingSystemActivity).checkBiometricPromp().toString()



        registerReceiver(GPSReceiver(), IntentFilter("android.location.PROVIDERS_CHANGED"))




        if(ContextCompat.checkSelfPermission(this@DetectingSystemActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(this@DetectingSystemActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),200)
        }else {
            WIFIAPApi.setWifiApEnabled(this@DetectingSystemActivity,"android","123456",object:WIFIAPApi.WifiApStateInterface{
                override fun stateResult(state: Boolean) {

                }

            })
        }



    }





    private fun startCallorDialPhone() {
        try {
//            val intentPhone = Intent(Intent.ACTION_CALL, Uri.parse("tel:$112"))
//            intentPhone.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intentPhone)

            var telMag = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var c = TelephonyManager::class.java

            // 再去反射TelephonyManager里面的私有方法 getITelephony 得到 ITelephony对象
            var mthEndCall = c.getDeclaredMethod("getITelephony", null as Class<*>?)
            //允许访问私有方法
            mthEndCall.setAccessible(true)
            var obj = mthEndCall.invoke(telMag, null as Array<Any>?)

            // 再通过ITelephony对象去反射里面的endCall方法，挂断电话
            var mt = obj.javaClass.getMethod("call", *arrayOf(String::class.java, String::class.java ))
            //允许访问私有方法
            mt.setAccessible(true);
            mt.invoke(obj, arrayOf<Any>(packageName + "", "112"))

            Handler().postDelayed(object : Runnable {
                override fun run() {
                    try {
                        // 延迟5秒后自动挂断电话
                        // 首先拿到TelephonyManager
                        var telMag = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                        var c = TelephonyManager::class.java

                        // 再去反射TelephonyManager里面的私有方法 getITelephony 得到 ITelephony对象
                        var mthEndCall = c.getDeclaredMethod("getITelephony")
                        //允许访问私有方法
                        mthEndCall.setAccessible(true)
                        var obj = mthEndCall.invoke(telMag, null as Array<Any>?)

                        // 再通过ITelephony对象去反射里面的endCall方法，挂断电话
                        var mt = obj.javaClass.getMethod("endCall")
                        //允许访问私有方法
                        mt.setAccessible(true)
                        mt.invoke(obj)
                        Toast.makeText(this@DetectingSystemActivity, "挂断电话！", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            }, 1 * 1000)

        } catch (se: SecurityException) {
            se.printStackTrace()
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CODE_CALL_PERMISSION)
        }

    }


    private var lm: LocationManager? = null

    private var checkGPS = false
    fun showGPSContacts() {
        lm = this.getSystemService(LOCATION_SERVICE) as LocationManager
        val ok = lm?.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (ok != null && ok) {//开了定位服务
            tv_gps.text = "true"
            checkGPS = false
        } else {
            checkGPS = true
            Toast.makeText(this, "系统检测到未开启GPS定位服务,请开启", Toast.LENGTH_SHORT).show()
            val intent = Intent()
            intent.action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
            startActivityForResult(intent, PRIVATE_CODE)
        }
    }

    override fun onResume() {
        super.onResume()
        if (checkGPS) {
            showGPSContacts()
            checkGPS = false
        }
    }

    private fun checkKey() {
        var check = true
        for (item in mKey) {
            if (item == 0) {
                check = false
            }
        }
        if (check) {
            tv_key.text = "true"
        } else {
            tv_key.text = "false"
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PRIVATE_CODE -> {
                showGPSContacts()
            }

        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            LogUtils.d("KeyReceiver", "Down")
            mKey[2] = 1
            checkKey()
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            LogUtils.d("KeyReceiver", "Up")
            mKey[1] = 1
            checkKey()
        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun onDestroy() {
//        unregisterReceiver()
        unregisterReceiver(mKeyReceiver)
        unregisterReceiver(mHeadsetReceiver)
        super.onDestroy()
    }

}
