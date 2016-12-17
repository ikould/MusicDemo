package widget;

import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ikould.musicpro.utils.LogUtil;
import com.ikould.musicpro.utils.ScreenUtil;

/**
 * MainActivity的主View的动画处理
 * Created by liudong on 2016/8/3.
 */
public class MainView {
    /* View */
    private FrameLayout mMainView;
    private LinearLayout mFragmentMain;
    private LinearLayout mFragmentMy;
    private FrameLayout mFragmentMyMain;

    /* 相关参数 */
    private int mMyCurrentWidth;
    private double mSlideRate = 1 / 10;//跟屏幕宽度的最小滑动比例
    private double mScaleLeftRate = 0.9;//缩放比例
    private double mScaleRightRate = 0.7;//缩放比例
    private float mSlideMin;//滑动最小距离
    private float downX;
    private float currentX;
    private boolean isLeftOpen;

    public MainView(FrameLayout mMainView, LinearLayout mFragmentMain, LinearLayout mFragmentMy, FrameLayout mFragmentMyMain) {
        this.mMainView = mMainView;
        this.mFragmentMain = mFragmentMain;
        this.mFragmentMy = mFragmentMy;
        this.mFragmentMyMain = mFragmentMyMain;
        initConfig();
        //初始化mMainView的事件处理
        initViewState();
        initMainTouch();
    }

    /**
     * 初始化相关参数
     */
    private void initConfig() {
        mMyCurrentWidth = ScreenUtil.screenWidth * 2 / 3;
        mSlideMin = (float) (ScreenUtil.screenWidth * mSlideRate);
        mFragmentMy.setLayoutParams(new FrameLayout.LayoutParams(mMyCurrentWidth, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    /**
     * 初始化View的状态
     */
    private void initViewState() {
        //初始化状态
        setViewState(isLeftOpen, 0);
    }

    /**
     * 初始化监听事件
     */
    private void initMainTouch() {
        mMainView.setOnTouchListener((v, event) -> {
            currentX = event.getX();
            float distance = currentX - downX;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (Math.abs(distance) < mMyCurrentWidth)
                        setViewState(isLeftOpen, distance);
                    break;
                case MotionEvent.ACTION_UP:
                    if ((Math.abs(distance) < mSlideMin && isLeftOpen)  //排除最小滑动
                            || (distance > mSlideMin && !isLeftOpen)) {
                        isLeftOpen = true;
                        setViewState(true, 0);
                    } else if ((Math.abs(distance) < mSlideMin && !isLeftOpen)
                            || (distance < -mSlideMin && isLeftOpen)) {
                        isLeftOpen = false;
                        setViewState(false, 0);
                    }
                    break;
            }
            return true;
        });
    }

    /**
     * 设置View的状态
     */
    private void setViewState(boolean isOpen, float distance) {
        if (!isOpen && distance >= 0) { //关闭 --> 打开
            mFragmentMy.setTranslationX(-mMyCurrentWidth + distance);
            mFragmentMain.setTranslationX(distance);
            float scale1 = (float) (1 - ((1 - mScaleLeftRate) * (mMyCurrentWidth - distance) / mMyCurrentWidth));
            float scale2 = (float) (mScaleRightRate + ((1 - mScaleRightRate) * (mMyCurrentWidth - distance) / mMyCurrentWidth));
            mFragmentMyMain.setScaleX(scale1);
            mFragmentMyMain.setScaleY(scale1);
            mFragmentMain.setScaleX(scale2);
            mFragmentMain.setScaleY(scale2);
            LogUtil.d(-mMyCurrentWidth + distance + "," + (float) (distance * 1.1) + "," + scale1 + "," + scale2);
        } else if (isOpen && distance <= 0) { //打开 --> 关闭
            mFragmentMy.setTranslationX(distance);
            mFragmentMain.setTranslationX(mMyCurrentWidth + distance);
            float scale1 = (float) (mScaleLeftRate + ((1 - mScaleLeftRate) * (mMyCurrentWidth + distance) / mMyCurrentWidth));
            float scale2 = (float) (1 - ((1 - mScaleRightRate) * (mMyCurrentWidth + distance) / mMyCurrentWidth));
            mFragmentMyMain.setScaleX(scale1);
            mFragmentMyMain.setScaleY(scale1);
            mFragmentMain.setScaleX(scale2);
            mFragmentMain.setScaleY(scale2);
        }
    }
}
