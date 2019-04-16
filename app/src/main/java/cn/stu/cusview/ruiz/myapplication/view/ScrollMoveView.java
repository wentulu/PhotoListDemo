package cn.stu.cusview.ruiz.myapplication.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class ScrollMoveView extends View {

    private static final String TAG = "ScrollMoveView";

    private float xLast, yLast;

    public ScrollMoveView(Context context) {
        this(context,null);
    }

    public ScrollMoveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollMoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
                ((ViewGroup) getParent()).scrollBy(-offsetX,-offsetY);
                Log.e(TAG, "l:t " + getLeft() + ":" + getTop());

                break;

            case MotionEvent.ACTION_UP:

                break;

        }


        return true;
    }


}
