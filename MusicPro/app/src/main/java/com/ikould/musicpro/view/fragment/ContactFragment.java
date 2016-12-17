package com.ikould.musicpro.view.fragment;

import android.os.Bundle;

import com.ikould.frame.fragment.BaseFragment;
import com.ikould.musicpro.R;

/**
 * 通讯录界面
 * <p>
 * Created by liudong on 2016/8/2.
 */
public class ContactFragment extends BaseFragment {
    private static ContactFragment instance;

    public static ContactFragment newInstance() {
        if (instance == null) {
            synchronized (ContactFragment.class) {
                instance = new ContactFragment();
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
