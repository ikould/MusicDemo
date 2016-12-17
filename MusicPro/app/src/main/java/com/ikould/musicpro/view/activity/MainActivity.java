package com.ikould.musicpro.view.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ikould.frame.activity.BaseActivity;
import com.ikould.frame.annotation.InjectView;
import com.ikould.frame.widget.StatusButton;
import com.ikould.musicpro.R;
import com.ikould.musicpro.presenter.BasePresenter;
import com.ikould.musicpro.presenter.IBasePresenter;
import com.ikould.musicpro.presenter.activity_presenter.MainPresenter;
import com.ikould.musicpro.presenter.activity_presenter.impl.IMainPresenter;
import com.ikould.musicpro.utils.LogUtil;
import com.ikould.musicpro.utils.ScreenUtil;
import com.ikould.musicpro.view.fragment.ContactFragment;
import com.ikould.musicpro.view.fragment.ExtraFragment;
import com.ikould.musicpro.view.fragment.MusicFragment;
import com.ikould.musicpro.view.fragment.MyFragment;

import widget.MainView;

/**
 * 主界面
 * <p>
 * Created by liudong on 2016/8/2.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, ExtraFragment.MyListener {
    @InjectView(R.id.fl_main)
    private FrameLayout mMainView;
    @InjectView(R.id.ll_fragment_my)
    private LinearLayout mFragmentMy;
    @InjectView(R.id.ll_fragment_my_main)
    private FrameLayout mFragmentMyMain;
    @InjectView(R.id.ll_fragment_main)
    private LinearLayout mFragmentMain;
    //home
    @InjectView(R.id.home_main)
    private FrameLayout mHomMain;
    @InjectView(R.id.home1)
    private FrameLayout mHome1;
    @InjectView(R.id.home2)
    private FrameLayout mHome2;
    @InjectView(R.id.home3)
    private FrameLayout mHome3;

    @InjectView(R.id.sv_main_table)
    private LinearLayout mMainTable;
    @InjectView(R.id.home_extra)
    private StatusButton mExtra;
    @InjectView(R.id.home_music)
    private StatusButton mMusic;
    @InjectView(R.id.home_constact)
    private StatusButton mConstact;
    //主View
    private IMainPresenter mainPresenter;
    private IBasePresenter basePresenter;
    //菜单选项
    private StatusButton[] checkBoxes;
    private FrameLayout[] frameLayouts;
    //相关参数
    private int mNum;
    private int mWidth;
    private int mHeight;
    private int mCellRange;
    private double mScaleRate;//伸缩比例
    private int currentIndex;

    @Override
    public void onBaseCreate(Bundle savedInstanceState) {
        initConfig();
        initView();
        initFragment();
        initListener();
    }

    /**
     * 初始化View相关参数
     */
    private void initView() {
        new MainView(mMainView, mFragmentMain, mFragmentMy, mFragmentMyMain);
        checkBoxes = new StatusButton[]{mExtra, mMusic, mConstact};
        frameLayouts = new FrameLayout[]{mHome1, mHome2, mHome3};
    }

    /**
     * 初始化相关配置
     */
    private void initConfig() {
        mainPresenter = new MainPresenter();
        basePresenter = new BasePresenter();
        initScreen();
        setLayoutId(R.layout.activity_main);
        mNum = mMainTable.getChildCount();
        mWidth = ScreenUtil.screenWidth;
        mHeight = 60;
        mScaleRate = 1.5;
        mCellRange = (int) (mWidth / (mNum + mScaleRate - 1));
    }

    /**
     * 添加监听事件
     */
    private void initListener() {
        mExtra.setOnClickListener(this);
        mMusic.setOnClickListener(this);
        mConstact.setOnClickListener(this);
    }

    /**
     * 初始化Indicator
     *
     * @param index
     */
    private void initIndicator(int index) {
        currentIndex = index;
        for (int i = 0; i < mNum; i++) {
            if (index == i) {
                checkBoxes[i].setLayoutParams(new LinearLayout.LayoutParams(mWidth - (mNum - 1) * mCellRange, mHeight));
                frameLayouts[i].setTranslationX(0);
                checkBoxes[i].setChecked(true);
            } else {
                checkBoxes[i].setLayoutParams(new LinearLayout.LayoutParams(mCellRange, mHeight));
                frameLayouts[i].setTranslationX(mWidth);
                checkBoxes[i].setChecked(false);
            }
        }
    }

    /**
     * 指示器设置
     *
     * @param index 选中的下标
     */
    public void setIndicator(int index) {
        if (index != currentIndex) {
            setTableAnim(index);
            setFragmentAnim(index);
            currentIndex = index;
        }
    }

    /**
     * Fragment切换动画
     *
     * @param index 下标
     */
    private void setFragmentAnim(int index) {
        if (index > currentIndex) {//右切左
            doPositionChangedAnim(mWidth, 0, 500, index);
            doPositionChangedAnim(0, -mWidth, 500, currentIndex);
        } else { //左切右
            doPositionChangedAnim(-mWidth, 0, 500, index);
            doPositionChangedAnim(0, mWidth, 500, currentIndex);
        }
    }

    /**
     * 位置变化动画
     *
     * @param startP   开始位置
     * @param endP     结束位置
     * @param duration 时间
     * @param index    下标
     */
    private void doPositionChangedAnim(int startP, int endP, int duration, int index) {
        ValueAnimator animator = ValueAnimator.ofInt(startP, endP);
        animator.setDuration(duration);
        animator.setTarget(frameLayouts[index]);
        animator.start();
        animator.addUpdateListener(animation -> {
            int degree = (int) animation.getAnimatedValue();
            frameLayouts[index].setTranslationX(degree);
        });
    }

    /**
     * Table切换动画
     *
     * @param index 下标
     */
    private void setTableAnim(int index) {
        doWidthChangedAnim(mCellRange, mWidth - (mNum - 1) * mCellRange, mHeight, 500, index);
        doWidthChangedAnim(mWidth - (mNum - 1) * mCellRange, mCellRange, mHeight, 500, currentIndex);
        checkBoxes[index].setChecked(true);
        checkBoxes[currentIndex].setChecked(false);
        LogUtil.d(index + "," + mNum + "," + mWidth + "," + mHeight + "," + mCellRange + "," + (mWidth - (mNum - 1) * mCellRange));
    }

    /**
     * 宽度变化动画
     *
     * @param fromX    开始宽度
     * @param toX      结束宽度
     * @param height   高度
     * @param duration 时间
     * @param index    下标
     */
    private void doWidthChangedAnim(int fromX, int toX, int height, int duration, int index) {
        ValueAnimator animator = ValueAnimator.ofInt(fromX, toX);
        animator.setDuration(duration);
        animator.setTarget(checkBoxes[index]);
        animator.start();
        animator.addUpdateListener(animation -> {
            int degree = (int) animation.getAnimatedValue();
            checkBoxes[index].setLayoutParams(new LinearLayout.LayoutParams(degree, height));
        });
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        replaceFragment(R.id.ll_fragment_my_main, MyFragment.newInstance(), null);
        replaceFragment(R.id.home1, ExtraFragment.newInstance(), null);
        replaceFragment(R.id.home2, MusicFragment.newInstance(), null);
        replaceFragment(R.id.home3, ContactFragment.newInstance(), null);
        //开始显示MusicFragment
        initIndicator(1);
    }

    /**
     * 检测屏幕宽高
     */
    private void initScreen() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        ScreenUtil.getIntsance(metric);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_extra:
                setIndicator(0);
                break;
            case R.id.home_music:
                setIndicator(1);
                break;
            case R.id.home_constact:
                setIndicator(2);
                break;
        }
    }

    @Override
    public boolean isFullScreen() {
        return basePresenter.isFullScreen();
    }

    @Override
    public void onListener(String msgStr) {
        Log.d("Fragment_Activity", msgStr);
    }
}
