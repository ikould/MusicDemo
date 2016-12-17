package com.ikould.musicpro.view.fragment;

import android.os.Bundle;

import com.ikould.frame.fragment.BaseFragment;
import com.ikould.musicpro.R;

/**
 * 音乐播放界面
 * <p>
 * Created by liudong on 2016/8/2.
 */
public class MusicFragment extends BaseFragment {

    private static MusicFragment instance;

    public static MusicFragment newInstance() {
        if (instance == null) {
            synchronized (MusicFragment.class) {
                instance = new MusicFragment();
            }
        }
        return instance;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_music;
    }

    @Override
    public void onBaseFragmentCreate(Bundle savedInstanceState) {
    }
}
