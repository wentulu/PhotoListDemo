package cn.stu.cusview.ruiz.myapplication.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class ScrollerView extends View {

    private static final String TAG = "ScrollerView";

    private Scroller mScroller;

    private float xLast, yLast;

    public ScrollerView(Context context) {
        this(context,null);
    }

    public ScrollerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                xLast = x;
                yLast = y;
                Log.e(TAG, "start " + x + ":" + y);
                Log.e(TAG, "raw x:y " + event.getRawX() + ":" + event.getRawY());
                break;

            case MotionEvent.ACTION_MOVE:
                int offsetX = (int) (x - xLast);
                int offsetY = (int) (y - yLast);
                Log.e(TAG, "offset X*Y=" + offsetX + "*" + offsetY);
                smoothScrollTo(-offsetX,-offsetY);

                break;

            case MotionEvent.ACTION_UP:

                break;

        }


        return true;
    }


    public void smoothScrollTo(int destX, int destY){
        if (mScroller.isFinished()) {
            int scrollX = ((ViewGroup) getParent()).getScrollX();
            int scrollY = ((ViewGroup) getParent()).getScrollY();
            Log.e(TAG,"Scroll X*Y = "+scrollX+":"+scrollY);
            int deltaX = scrollX + destX;
            int deltaY = scrollY + destY;
            mScroller.startScroll(scrollX, scrollY, deltaX, deltaY, 300);
            invalidate();
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            ((ViewGroup)getParent()).scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }
}
