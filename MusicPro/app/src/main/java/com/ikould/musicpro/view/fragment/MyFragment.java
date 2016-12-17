package com.ikould.musicpro.view.fragment;

import android.os.Bundle;

import com.ikould.frame.fragment.BaseFragment;
import com.ikould.musicpro.R;

/**
 * 用户界面
 * <p>
 * Created by liudong on 2016/8/2.
 */
public class MyFragment extends BaseFragment {
    private static MyFragment instance;

    public static MyFragment newInstance() {
        if (instance == null) {
            synchronized (MyFragment.class) {
                instance = new MyFragment();
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
