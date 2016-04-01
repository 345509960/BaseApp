package com.yuanyuan.baseapp.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/3/31.
 */
public class MD5Utils {
    private static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static String md5(String string) {
        Object encodeBytes = null;

        byte[] encodeBytes1;
        try {
            encodeBytes1 = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException var3) {
            throw new RuntimeException(var3);
        }

        return toHexString(encodeBytes1);
    }
    public static String toHexString(byte[] bytes) {
        if(bytes == null) {
            return "";
        } else {
            StringBuilder hex = new StringBuilder(bytes.length * 2);
            byte[] var5 = bytes;
            int var4 = bytes.length;

            for(int var3 = 0; var3 < var4; ++var3) {
                byte b = var5[var3];
                hex.append(hexDigits[b >> 4 & 15]);
                hex.append(hexDigits[b & 15]);
            }

            return hex.toString();
        }
    }
}
