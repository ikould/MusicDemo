package com.ikould.musicpro.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * 屏幕宽高检测及单位转换工具
 * <p>
 * Created by liudong on 2016/8/3.
 */
public class ScreenUtil {
    //屏幕宽度
    public static int screenWidth;
    //屏幕高度
    public static int screenHeight;
    //屏幕密度
    public static int screenDpi;
    private static ScreenUtil instance;

    public static ScreenUtil getIntsance(DisplayMetrics metric) {
        if (instance == null) {
            synchronized (ScreenUtil.class) {
                instance = new ScreenUtil(metric);
            }
        }
        return instance;
    }

    private ScreenUtil(DisplayMetrics metric) {
        screenWidth = metric.widthPixels;
        screenHeight = metric.heightPixels;
        screenDpi = metric.densityDpi;
    }

    /**
     * 是否是横屏
     *
     * @return
     */
    public static boolean isHorizontal() {
        return screenWidth > screenHeight;
    }

    /**
     * 单位换算
     *
     * @param resources
     * @param inParam
     * @return
     */
    public static int px2dip(Resources resources, float inParam) {
        float f = resources.getDisplayMetrics().density;
        return (int) (inParam / f + 0.5F);
    }

    public static int dip2px(Resources resources, float inParam) {
        float f = resources.getDisplayMetrics().density;
        return (int) (inParam * f + 0.5F);
    }

    public static int px2sp(Resources resources, float inParam) {
        float f = resources.getDisplayMetrics().scaledDensity;
        return (int) (inParam / f + 0.5F);
    }

    public static int sp2px(Resources resources, float inParam) {
        float f = resources.getDisplayMetrics().scaledDensity;
        return (int) (inParam * f + 0.5F);
    }

}
