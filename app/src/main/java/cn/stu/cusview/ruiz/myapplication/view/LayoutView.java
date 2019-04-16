package cn.stu.cusview.ruiz.myapplication.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class LayoutView extends ViewGroup {

    private static final String TAG = "LayoutView";

    private VelocityTracker mVelocityTracker;
    private Scroller mScroller;

    public LayoutView(Context context) {
        super(context);
        init();
    }


    public LayoutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mVelocityTracker = VelocityTracker.obtain();
        mScroller = new Scroller(this.getContext());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = getMode(widthMeasureSpec);
        int widthSize = getSize(widthMeasureSpec);
        int heightMode = getMode(heightMeasureSpec);
        int heightSize = getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            if (getChildCount() == 0) {
                setMeasuredDimension(0, 0);
            } else {
                View childView = getChildAt(0);
                setMeasuredDimension(childView.getMeasuredWidth() * getChildCount(), childView.getMeasuredHeight());
            }
        } else if (widthMode == MeasureSpec.AT_MOST) {
            if (getChildCount() == 0) {
                setMeasuredDimension(0, heightSize);
            } else {
                View childView = getChildAt(0);
                setMeasuredDimension(childView.getMeasuredWidth() * getChildCount(), heightSize);
            }
        } else if (heightMode == MeasureSpec.AT_MOST) {
            if (getChildCount() == 0) {
                setMeasuredDimension(widthSize, 0);
            } else {
                View childView = getChildAt(0);
                setMeasuredDimension(widthSize, childView.getMeasuredHeight());
            }
        } else {

        }
    }


    private int getMode(int measureSpec) {
        return MeasureSpec.getMode(measureSpec);
    }

    private int getSize(int measureSpec) {
        return MeasureSpec.getSize(measureSpec);
    }


    private int interceptLastX, interceptLastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        boolean intercepted = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - interceptLastX;
                int deltaY = y - interceptLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;

        }
        lastX = x;
        lastY = y;
        interceptLastX = x;
        interceptLastY = y;
        return intercepted;
    }


    private int lastX, lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mVelocityTracker.addMovement(event);

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastX;
                if ((getScrollX() <= 0 && deltaX > 0) || (getScrollX() >= getChildAt(0).getMeasuredWidth() * (getChildCount() - 1) && deltaX <= 0)) {

                } else {
                    if (getScrollX() - deltaX < 0) {
                        scrollTo(0, 0);
                    } else if ((getScrollX() - deltaX) > (getChildAt(0).getMeasuredWidth() * (getChildCount() - 1))) {
                        scrollTo(getChildAt(0).getMeasuredWidth() * (getChildCount() - 1), 0);
                    } else {
                        scrollBy(-deltaX, 0);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                int childWidth = getChildAt(0).getMeasuredWidth();
                int offset = getScrollX() % childWidth;
                int childInsex = getScrollX() / childWidth;
                mVelocityTracker.computeCurrentVelocity(300);
                int xVelocity = (int) mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) > 50) {
                    Log.e(TAG, "Velocity faster than , velocity=" + xVelocity);
                    if (xVelocity > 0) {

                        Log.e(TAG, "left translation");
                        if (childInsex < 0) {

                        } else {
//                            scrollTo(childWidth * childInsex, 0);
                            smoothScrollTo(childWidth * childInsex, 0);
                        }

                    } else {
                        Log.e(TAG, "right translation");
                        childInsex++;
                        if (childInsex >= getChildCount()) {

                        } else {
//                            scrollTo(childWidth * childInsex, 0);
                            smoothScrollTo(childInsex*childWidth,0);
                        }
                    }
                } else {
                    Log.e(TAG, "Velocity slower than 50");
                    if (offset > childWidth / 2) {
                        childInsex++;
//                        scrollTo(childWidth * childInsex, 0);
                        smoothScrollTo(childInsex*childWidth,0);
                    } else {
//                        scrollTo(childWidth * childInsex, 0);
                        smoothScrollTo(childInsex*childWidth,0);
                    }
                }
                mVelocityTracker.clear();
                break;

        }

        lastY = y;
        lastX = x;
        return true;
    }



    private void smoothScrollTo(int x,int y){
        mScroller.startScroll(getScrollX(),getScrollY(),x-getScrollX(),y-getScrollY());
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0;
        int childCount = getChildCount();
        View child;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int width = child.getMeasuredWidth();
                Log.e(TAG, "Child Height" + child.getHeight());
                child.layout(left, 0, left + width, child.getMeasuredHeight());
                left += width;
            }
        }
    }

    Thread mThread;

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }
}
