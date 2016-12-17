package com.ikould.musicpro.data.remote;

import android.graphics.Bitmap;

import com.ikould.frame.net.HttpsHelper;
import com.ikould.musicpro.data.bean.PluginBean;
import com.ikould.musicpro.data.local.config.AppConfig;

import java.io.File;

/**
 * 网络数据下载
 * <p>
 * Created by liudong on 2016/8/2.
 */
public class DownNet {

    public static void getStringByUrl(String url, HttpsHelper.HttpCallBack<String> callBack) {
        HttpsHelper.getInstance().get(url, callBack);
    }

    public static void getBitmapByUrl(String url, HttpsHelper.HttpCallBack<Bitmap> callBack) {
        HttpsHelper.getInstance().getBitmap(url, callBack);
    }

    public static void downApk(PluginBean.DataBean dataBean) {
        String savePath = AppConfig.DOWNLOAD_FILE + File.separator + dataBean.getFile();
        String downPath = AppConfig.BASE_URL + File.separator + dataBean.getFile();
        HttpsHelper.getInstance().downLoadFile(savePath, downPath, dataBean.getApk());
    }
}
