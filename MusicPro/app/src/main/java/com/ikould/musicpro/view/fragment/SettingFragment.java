package com.ikould.musicpro.view.fragment;

import android.os.Bundle;

import com.ikould.frame.fragment.BaseFragment;
import com.ikould.musicpro.R;

/**
 * 设置界面
 * <p>
 * Created by liudong on 2016/8/2.
 */
public class SettingFragment extends BaseFragment {
    private static SettingFragment instance;

    public static SettingFragment newInstance() {
        if (instance == null) {
            synchronized (SettingFragment.class) {
                instance = new SettingFragment();
            }
        }
        return instance;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void onBaseFragmentCreate(Bundle savedInstanceState) {
    }
}
