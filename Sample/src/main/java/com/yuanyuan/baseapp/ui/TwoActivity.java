package com.yuanyuan.baseapp.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lyc.love.baselib.ui.AbstractActivity;
import com.yuanyuan.baseapp.R;
import com.yuanyuan.baseapp.constant.SampleConstant;

import java.io.PrintStream;
import java.lang.reflect.Method;

@Route(path = SampleConstant.ROUTER_TWO)
public class TwoActivity extends AbstractActivity {


    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_COARSE_LOCATION}, 200);
        } else {
            showPhoneState();
        }


    }

    private void showPhoneState() {
        TelephonyManager mr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        StringBuffer stringBuilder = new StringBuffer();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        stringBuilder.append("设备编号：" + mr.getDeviceId() + "\n\n");

        stringBuilder.append("软件版本：" + (mr.getDeviceSoftwareVersion()!= null?mr.getDeviceSoftwareVersion():"未知")+"\n\n");

        stringBuilder.append("运营商代号：" + mr.getNetworkOperator()+"\n\n");

        stringBuilder.append("运营商名称：" + mr.getNetworkOperatorName()+"\n\n");

        stringBuilder.append("网络类型：" + mr.getPhoneType()+"\n\n");

        stringBuilder.append("设备当前位置：" + (mr.getCellLocation() != null ? mr.getCellLocation().toString() : "未知位置")+"\n\n");

        stringBuilder.append("SIM卡的国别：" + mr.getSimCountryIso()+"\n\n");

        stringBuilder.append("SIM卡序列号：" + mr.getSimSerialNumber()+"\n\n");

        stringBuilder.append("SIM卡状态：" + mr.getSimState()+"\n\n");


        TextView resulttextview= (TextView) findViewById(R.id.tv_value);
        resulttextview.setText(stringBuilder.toString());

    }

    @Override
        public void initView(View view) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        @Override
        protected int getLayoutId() {
            return R.layout.activity_two;
        }


        public void startActionBar1(View view) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE}, 200);
            } else {
                startCallorDialPhone();
            }


        }


        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

            if (requestCode == 200) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        showPhoneState();
                        startCallorDialPhone();
                    }
                }
            }

            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }




    private void startCallorDialPhone() {

        try {
            // 首先拿到TelephonyManager
            TelephonyManager telMag = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            Class<TelephonyManager> c = TelephonyManager.class;

            // 再去反射TelephonyManager里面的私有方法 getITelephony 得到 ITelephony对象
            Method mthEndCall = c.getDeclaredMethod("getITelephony", (Class[]) null);
            //允许访问私有方法
            mthEndCall.setAccessible(true);
            final Object obj = mthEndCall.invoke(telMag, (Object[]) null);

            // 再通过ITelephony对象去反射里面的call方法，并传入包名和需要拨打的电话号码
            Method mt = obj.getClass().getMethod("call", new Class[]{String.class, String.class});
            //允许访问私有方法
            mt.setAccessible(true);
            mt.invoke(obj, new Object[]{getPackageName() + "", "114"});

            Toast.makeText(TwoActivity.this, "拨打电话！", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    try {
                        // 延迟5秒后自动挂断电话
                        // 再通过ITelephony对象去反射里面的endCall方法，挂断电话
                        Method mt = obj.getClass().getMethod("endCall");
                        //允许访问私有方法
                        mt.setAccessible(true);
                        mt.invoke(obj);
                        Toast.makeText(TwoActivity.this, "挂断电话！", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 5 * 1000);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
