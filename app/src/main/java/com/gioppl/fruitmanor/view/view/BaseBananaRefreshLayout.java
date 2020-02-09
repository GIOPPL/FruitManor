package com.gioppl.fruitmanor.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.gioppl.fruitmanor.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public abstract class BaseBananaRefreshLayout extends SwipeRefreshLayout {
    private int mTouchSlop;
    private int mYDown;
    private int mLastY;
    private float mPrevX;
    protected boolean isAutoLoad = true;
    protected View footView;

    private boolean isLoading;
    private boolean isLoadEnable;
    private OnLoadListener mOnLoadListener;

    public BaseBananaRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public BaseBananaRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        footView = LayoutInflater.from(context).inflate(R.layout.view_list_footer, null);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = MotionEvent.obtain(ev).getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float eventX=ev.getX();
                float xDiff=Math.abs(eventX-mPrevX);
                if (xDiff>mTouchSlop)
                    return false;
        }
        return super.onInterceptTouchEvent(ev);

    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
        setLoadEnable(true);
    }

    public interface OnLoadListener {
        void onLoad();
    }
    /**
     * 当手指上滑，离开屏幕时加载更多
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mYDown = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = (int) event.getRawY();
                loadData();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }
    /**
     * 是否是上拉操作
     *
     * @return
     */
    protected boolean isPullUp() {
        return mYDown > mLastY;
    }
    /**
     * 加载更多数据
     */
    protected void loadData() {
        if (isReachBottom() && !isLoading && isPullUp() && isLoadEnable) {
            setLoading(true);
            if (mOnLoadListener != null) {
                mOnLoadListener.onLoad();
            }
        }
    }
    /**
     * @param loading
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            showLoadView(true);
        } else {
            showLoadView(false);
            mYDown = 0;
            mLastY = 0;
        }
    }
    /**
     * 是否滚动到了底部
     *
     * @return
     */
    protected abstract boolean isReachBottom();
    /**
     * 控制加载更多view的显示可隐藏
     *
     * @param isShow
     */
    protected abstract void showLoadView(boolean isShow);
    /**
     * 设置自动加载更多，默认开启
     *
     * @param isAuto
     */
    public void setAutoLoad(boolean isAuto) {
        isAutoLoad = isAuto;
    }

    /**
     * 设置是否可以上拉加载更多
     *
     * @param enable
     */
    public void setLoadEnable(boolean enable) {
        this.isLoadEnable = enable;
    }
    /**
     * 设置加载更多监听器，同时默认开启加载更多开关
     *
     * @param loadListener
     */

}
