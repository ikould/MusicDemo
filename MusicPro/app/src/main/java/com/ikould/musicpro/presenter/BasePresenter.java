package com.ikould.musicpro.presenter;

import com.ikould.musicpro.data.local.config.AppConfig;

/**
 * Presenter的基类
 * <p>
 * Created by liudong on 2016/8/2.
 */
public class BasePresenter implements IBasePresenter {
    @Override
    public boolean isFullScreen() {
        return AppConfig.getInstance().isFullScreen();
    }

    @Override
    public void setFullScreen(boolean isFull) {
        AppConfig.getInstance().setFullScreen(isFull);
    }
}
