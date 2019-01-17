package com.lyc.love.baselib.detectingsystem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;

public class HeadsetReceiver extends BroadcastReceiver {

    private ArrayList<HeadsetMonitor> monitors = new ArrayList<>();

    public void addMonitor(HeadsetMonitor monitor){
        monitors.add(monitor);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_HEADSET_PLUG.equalsIgnoreCase(intent.getAction())) {
            if (intent.hasExtra("state")) {
                int state = intent.getIntExtra("state", 0);
                if (state == 0) {
                    //Headset is not plugged    
                    LogUtils.d("HeadsetReceiver", "耳机未插入");
                    updateState(false);
                } else if (state == 1)//Headphones into
                {
                    updateState(true);
                    LogUtils.d("HeadsetReceiver", "耳机已插入");
                }
            }
        }

    }

    private void updateState(boolean state) {
        for (HeadsetMonitor monitor : monitors) {
            monitor.headsetState(state);
        }
    }
}
