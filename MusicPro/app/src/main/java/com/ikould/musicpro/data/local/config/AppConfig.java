package com.ikould.musicpro.data.local.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.ikould.frame.utils.FileUtils;
import com.ikould.frame.utils.SerialUtil;
import com.ikould.musicpro.CoreApplication;
import com.ikould.musicpro.data.bean.PluginBean;
import com.ikould.musicpro.utils.Constants;

import java.io.File;

/**
 * App相关配置信息
 * <p>
 * Created by liudong on 2016/8/2.
 */
public class AppConfig {
    /**
     * 基础地址
     */
    public static String BASE_URL;
    /**
     * 下载地址
     */
    public static String DOWNLOAD;
    /**
     * 图片下载地址
     */
    public static String DOWNLOAD_IMG;
    /**
     * 文件下载地址
     */
    public static String DOWNLOAD_FILE;
    /**
     * 缓存地址
     */
    public static String CACHE;
    /**
     * 冲突地址
     */
    public static String CLASH;
    /**
     * Log地址
     */
    public static String LOG;

    static {
        BASE_URL = FileUtils.getRootPath().getAbsolutePath() + File.separator + "MusicPro";
        DOWNLOAD = BASE_URL + File.separator + "Download";
        DOWNLOAD_IMG = DOWNLOAD + File.separator + "Images";
        DOWNLOAD_FILE = DOWNLOAD + File.separator + "Files";
        CACHE = BASE_URL + File.separator + "Cache";
        CLASH = BASE_URL + File.separator + "Clash";
        LOG = BASE_URL + File.separator + "Log";
        //初始化文件
        FileUtils.initDirctory(BASE_URL);
        FileUtils.initDirctory(DOWNLOAD);
        FileUtils.initDirctory(DOWNLOAD_IMG);
        FileUtils.initDirctory(DOWNLOAD_FILE);
        FileUtils.initDirctory(CACHE);
        FileUtils.initDirctory(CLASH);
        FileUtils.initDirctory(LOG);
    }

    private SharedPreferences sharedPre;
    private static AppConfig instance;

    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (AppConfig.class) {
                instance = new AppConfig();
            }
        }
        return instance;
    }

    private AppConfig() {
        sharedPre = CoreApplication.getInstance().getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
    }

    /**
     * 设置是否全屏
     */
    public boolean isFullScreen() {
        return sharedPre.getBoolean(Constants.FULL_SCREEN, false);
    }

    public void setFullScreen(boolean isFull) {
        SharedPreferences.Editor editor = sharedPre.edit();
        editor.putBoolean(Constants.FULL_SCREEN, isFull);
        editor.commit();
    }

    /**
     * 设置网络插件数据实体类
     */
    public void setPluginBean(PluginBean pluginBean) {
        SerialUtil.saveObject(pluginBean, Constants.PLUGIN_BEAN, sharedPre);
    }

    public PluginBean getPluginBean() {
        return (PluginBean) SerialUtil.readObject(Constants.PLUGIN_BEAN, sharedPre);
    }

}
