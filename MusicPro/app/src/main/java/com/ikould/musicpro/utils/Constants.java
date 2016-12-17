package com.ikould.musicpro.utils;

import java.io.File;

/**
 * 常量类
 * <p>
 * Created by liudong on 2016/8/2.
 */
public class Constants {

    public final static String SHARED_PREF = "musicpro_preferences";

    /**
     * SharedPreferences--> KEY
     */
    public final static String FULL_SCREEN = "full_screen";
    public final static String PLUGIN_BEAN = "plugin_bean";

    /**
     * 服务器下载基地址
     */
    public final static String BASE_DOWN_URL = "http://192.168.1.160:8080/plugin";

    /**
     * 服务器更新文件地址
     */
    public final static String UPDATE_FILE_URL = BASE_DOWN_URL + File.separator + "pulgin_des.txt";

}
