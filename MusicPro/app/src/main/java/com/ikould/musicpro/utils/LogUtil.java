package com.ikould.musicpro.utils;

import android.util.Log;

/**
 * Created by liudong on 2016/8/3.
 */
public class LogUtil {
    public static final String TAG = "liudong";

    public static void d(int in) {
        Log.d(TAG, in + "");
    }

    public static void d(String str) {
        Log.d(TAG, str);
    }
}
