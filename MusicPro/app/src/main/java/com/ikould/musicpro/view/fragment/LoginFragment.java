package com.ikould.musicpro.view.fragment;

import android.os.Bundle;

import com.ikould.frame.fragment.BaseFragment;
import com.ikould.musicpro.R;

/**
 * 登录及注册界面
 * <p>
 * Created by liudong on 2016/8/2.
 */
public class LoginFragment extends BaseFragment {
    private static LoginFragment instance;

    public static LoginFragment newInstance() {
        if (instance == null) {
            synchronized (LoginFragment.class) {
                instance = new LoginFragment();
            }
        }
        return instance;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    public void onBaseFragmentCreate(Bundle savedInstanceState) {
    }
}
