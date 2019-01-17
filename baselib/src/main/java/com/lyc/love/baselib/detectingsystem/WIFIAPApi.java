package com.lyc.love.baselib.detectingsystem;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.lang.reflect.Method;

import static android.support.constraint.Constraints.TAG;

public class WIFIAPApi {


    /* 开启/关闭热点 */
    public static void setWifiApEnabled(final Context context, String ssid, String password, WifiApStateInterface wifiApStateInterface) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // 因为wifi和热点不能同时打开，所以打开热点的时候需要关闭wifi
        boolean wifiEnable = wifiManager.isWifiEnabled();
        if (wifiEnable) {
            wifiManager.setWifiEnabled(false);
        }

        if (Build.VERSION.SDK_INT >= 26) {
            setWifiApEnabledForAndroidO(context, new WifiManager.LocalOnlyHotspotCallback() {

                @Override
                public void onStarted(WifiManager.LocalOnlyHotspotReservation reservation) {
                    super.onStarted(reservation);
                    Log.d(TAG, "Wifi Hotspot is on now");
                    if (wifiApStateInterface != null) {
                        wifiApStateInterface.stateResult(true);
                    }
                    if (wifiEnable) {
                        wifiManager.setWifiEnabled(true);
                    }

                }

                @Override
                public void onStopped() {
                    super.onStopped();
                    Log.d(TAG, "onStopped: ");
                    if (wifiApStateInterface != null) {
                        wifiApStateInterface.stateResult(false);
                    }
                    if (wifiEnable) {
                        wifiManager.setWifiEnabled(true);
                    }
                }

                @Override
                public void onFailed(int reason) {
                    super.onFailed(reason);
                    Log.d(TAG, "onFailed: ");
                    if (wifiApStateInterface != null) {
                        wifiApStateInterface.stateResult(false);
                    }
                    if (wifiEnable) {
                        wifiManager.setWifiEnabled(true);
                    }
                }
            });
            return;
        }

        WifiConfiguration ap = null;

        try {
            // 热点的配置类
            WifiConfiguration apConfig = new WifiConfiguration();
            // 配置热点的名称(可以在名字后面加点随机数什么的)
            apConfig.SSID = ssid;
            apConfig.preSharedKey = password;
            apConfig.allowedKeyManagement.set(4);//设置加密类型，这里4是wpa加密

            Method method = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
            if (wifiApStateInterface != null) {
                boolean state = (Boolean) method.invoke(wifiManager, apConfig, true);
                if (wifiEnable) {
                    wifiManager.setWifiEnabled(true);
                }
                wifiApStateInterface.stateResult(state);
            }

            // 返回热点打开状态

        } catch (Exception e) {
            e.printStackTrace();
            if (wifiEnable) {
                wifiManager.setWifiEnabled(true);
            }
            if (wifiApStateInterface != null) {
                wifiApStateInterface.stateResult(false);
            }
        }

    }


    /**
     * 8.0 开启热点方法
     * 注意：这个方法开启的热点名称和密码是手机系统里面默认的那个
     *
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void setWifiApEnabledForAndroidO(Context context, WifiManager.LocalOnlyHotspotCallback callback) {

        WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //cancelLocalOnlyHotspotRequest 是关闭热点
        // 打开热点
        manager.startLocalOnlyHotspot(callback, new Handler());

    }

    public interface WifiApStateInterface {
        void stateResult(boolean state);
    }

}
