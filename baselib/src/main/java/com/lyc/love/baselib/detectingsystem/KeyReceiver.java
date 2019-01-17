package com.lyc.love.baselib.detectingsystem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;

public class KeyReceiver extends BroadcastReceiver {

    public static int HOME = 0X123;
    public static int SCREEN = 0X124;
    private ArrayList<KeyMonitor> monitors = new ArrayList<>();

    public void addMonitor(KeyMonitor monitor) {
        monitors.add(monitor);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equalsIgnoreCase(action)) {
            LogUtils.d("KeyReceiver", "Home键");
            updateState(HOME);
        }
        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            LogUtils.d("KeyReceiver", "开屏");
            updateState(SCREEN);
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            LogUtils.d("KeyReceiver", "锁屏");
            updateState(SCREEN);
        } else {// 解锁
            //TODO
            LogUtils.d("KeyReceiver", "解锁");
        }
    }


    private void updateState(int state) {
        for (KeyMonitor monitor : monitors) {
            monitor.keyState(state);
        }
    }
}
