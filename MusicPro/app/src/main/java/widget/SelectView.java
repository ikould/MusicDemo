package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.ikould.musicpro.utils.LogUtil;

/**
 * 可伸缩选择器
 * <p>子View限定为CheckBox
 * Created by liudong on 2016/8/4.
 */
public class SelectView extends ViewGroup {
    private int mNum; //数量
    private double mScaleRate = 1.5;//伸缩比例
    private int mIndex; //当前显示的下标
    private int mWidth; //宽度
    private int mHeight;//高度
    private int mCellRange;//每一格距离

    public SelectView(Context context) {
        super(context);
    }

    public SelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        setWillNotDraw(false);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LogUtil.d("onLayout");
        mNum = getChildCount();
        mWidth = getWidth();
        mHeight = getHeight();
        LogUtil.d(mNum);
        mCellRange = (int) (mWidth / (mNum + mScaleRate - 1));
       /* for (int i = 0; i < mNum; i++) {
            LogUtil.d("i:" + i);
            CheckBox view = (CheckBox) getChildAt(i);
            if (mIndex == i) {
                view.getLayoutParams().width = mWidth - (mNum - 1) * mCellRange;
                view.setChecked(true);
            } else {
                view.getLayoutParams().width = mCellRange;
                view.setChecked(false);
            }
            view.getLayoutParams().height = mHeight;
        }*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.d("onDraw");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        LogUtil.d("draw");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.d("onMeasure");
    }

    /**
     * 指示器设置
     *
     * @param index 选中的下标
     */
    public void setIndicator(int index) {
        mIndex = index;
    }

    /**
     * 获取宽度
     *
     * @return
     */
    public int getSelectWidth() {
        return mWidth;
    }

    /**
     * 获取高度
     *
     * @return
     */

    public int getSelectHeight() {
        return mHeight;
    }
}
