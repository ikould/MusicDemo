package com.ikould.musicpro.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Window工具类
 * <p>
 * Created by liudong on 2016/5/26.
 */
public class WindowUtil {
    private Map<String, View> viewMap;
    private WindowManager mWindowManager;
    private Context mContext;
    private WindowManager.LayoutParams params;

    private static WindowUtil instance;

    public static WindowUtil getIntsance() {
        if (instance == null) {
            synchronized (WindowUtil.class) {
                instance = new WindowUtil();
            }
        }
        return instance;
    }

    private WindowUtil() {
        initParams();
    }

    /**
     * 初始化LayoutParams
     */
    private void initParams() {
        params = new WindowManager.LayoutParams();
        // 类型
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        // 设置flag
        int flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;//窗口占满整个屏幕，忽略周围的装饰边框（例如状态栏）。此窗口需考虑到装饰边框的内容。
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        params.flags = flags;
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;
        params.gravity = Gravity.CENTER;
    }

    /**
     * 显示弹出框
     *
     * @param context 上下文，可以为Application
     */
    public void showPopupWindow(Context context, View view, String tag) {
        // 获取应用的Context
        mContext = context;
        if (viewMap == null) {
            viewMap = new HashMap<>();
        }
        if (viewMap.get(tag) == null) {
            viewMap.put(tag, view);
        }
        // 获取WindowManager
        mWindowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.addView(viewMap.get(tag), params);
    }

    /**
     * 隐藏弹出框
     */
    public void hidePopupWindow(String tag) {
        if (viewMap.get(tag) != null) {
            mWindowManager.removeView(viewMap.get(tag));
            viewMap.remove(tag);
        }
    }
}
