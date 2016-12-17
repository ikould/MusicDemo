package com.ikould.musicpro.view.activity;

import android.os.Bundle;

import com.ikould.frame.activity.BaseActivity;
import com.ikould.musicpro.R;
import com.ikould.musicpro.presenter.BasePresenter;
import com.ikould.musicpro.presenter.IBasePresenter;
import com.ikould.musicpro.presenter.activity_presenter.MainPresenter;
import com.ikould.musicpro.presenter.activity_presenter.impl.IMainPresenter;

/**
 * 欢迎界面
 * <p>
 * Created by liudong on 2016/8/2.
 */
public class WelActivity extends BaseActivity {
    private IMainPresenter mMmainPresenter;
    private IBasePresenter mBasePresenter;

    @Override
    public void onBaseCreate(Bundle savedInstanceState) {
        mMmainPresenter = new MainPresenter();
        mBasePresenter = new BasePresenter();
        setLayoutId(R.layout.activity_wel);
    }

    @Override
    public boolean isFullScreen() {
        return mBasePresenter.isFullScreen();
    }
}
