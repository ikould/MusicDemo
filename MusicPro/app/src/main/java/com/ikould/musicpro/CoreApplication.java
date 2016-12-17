package com.ikould.musicpro;

import android.app.Application;
import android.content.Context;
import android.os.RemoteException;

import com.morgoo.droidplugin.PluginHelper;
import com.morgoo.droidplugin.pm.PluginManager;
import com.morgoo.helper.compat.PackageManagerCompat;

/**
 * 全场唯一
 * <p>
 * Created by liudong on 2016/8/2.
 */
public class CoreApplication extends Application {
    private static CoreApplication instance;

    public static CoreApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //这里必须在super.onCreate方法之后，顺序不能变
        PluginHelper.getInstance().applicationOnCreate(getBaseContext());
        try {
            //打开插件
            PluginManager.getInstance().installPackage("", PackageManagerCompat.INSTALL_REPLACE_EXISTING);
            //关闭插件
            PluginManager.getInstance().deletePackage("", PackageManagerCompat.INSTALL_REPLACE_EXISTING);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void attachBaseContext(Context base) {
        PluginHelper.getInstance().applicationAttachBaseContext(base);
        super.attachBaseContext(base);
    }
}
