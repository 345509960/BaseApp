package com.lyc.love.baselib.detectingsystem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;


/**
 * Created by gaoyang on 2018/06/19.
 */
public class BiometricPromptUtils {


    public Context mActiviy;

    public BiometricPromptUtils(Context activity) {
        mActiviy = activity;
    }


    public boolean checkBiometricPromp() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return getFingerprintManager(mActiviy) != null && getFingerprintManager(mActiviy).isHardwareDetected();
        } else {
            final FingerprintManager manager = mActiviy.getSystemService(FingerprintManager.class);
            return manager != null && manager.isHardwareDetected();
        }
    }


    @SuppressLint("NewApi")
    public static FingerprintManager getFingerprintManager(Context context) {
        FingerprintManager fingerprintManager = null;
        try {
            fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        } catch (Throwable e) {
            LogUtils.d("have not class FingerprintManager");
        }
        return fingerprintManager;
    }

}
