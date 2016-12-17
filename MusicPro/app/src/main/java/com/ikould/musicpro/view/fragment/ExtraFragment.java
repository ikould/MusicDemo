package com.ikould.musicpro.view.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ikould.frame.annotation.InjectView;
import com.ikould.frame.fragment.BaseFragment;
import com.ikould.musicpro.R;
import com.ikould.musicpro.presenter.adapter.ExtraAdapter;
import com.ikould.musicpro.presenter.fragment_presenter.ExtraPresenter;
import com.ikould.musicpro.view.fragment.impl.IExtraFragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 音乐播放界面
 * <p>
 * Created by liudong on 2016/8/2.
 */
public class ExtraFragment extends BaseFragment implements IExtraFragment {
    @InjectView(R.id.rv_extra_main)
    private RecyclerView mExtraMain;

    private static ExtraFragment instance;

    private ExtraAdapter extraAdapter;
    private MyListener listener;

    private ExtraPresenter extraPresenter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (MyListener) context;
    }

    public interface MyListener {
        void onListener(String msgStr);
    }

    public static ExtraFragment newInstance() {
        if (instance == null) {
            synchronized (ExtraFragment.class) {
                instance = new ExtraFragment();
            }
        }
        return instance;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_extra;
    }

    @Override
    public void onBaseFragmentCreate(Bundle savedInstanceState) {
        extraPresenter = new ExtraPresenter(getActivity(), this);
        initView();
        ((MyListener) getActivity()).onListener("ExtraFragment创建了");
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mExtraMain.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mExtraMain.setAdapter(extraAdapter);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 1000);
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                return null;
            }
        };
    }

    /**
     * 成功返回数据
     *
     * @param extraAdapter
     */
    @Override
    public void setExtraAdapter(ExtraAdapter extraAdapter) {
        this.extraAdapter = extraAdapter;
    }
}
