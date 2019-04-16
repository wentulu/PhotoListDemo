package cn.stu.cusview.ruiz.myapplication.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class MovieView extends View {
    private static final String TAG = "MovieView";

    private float xLast, yLast;

    public MovieView(Context context) {
        super(context);
    }

    public MovieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MovieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
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
                int topNew = getTop();
                int leftNew = getLeft();
                ViewGroup parent = ((ViewGroup) getParent());
                if (offsetY < 0 && topNew <= 0) {
                    Log.e(TAG, "new Y");
                    topNew = 0;
                    offsetY = 0;
                } else if (getBottom() >= parent.getBottom() && offsetY > 0) {
                    offsetY = 0;
                } else {
                    topNew += offsetY;
                    if (topNew<0){
                        topNew=0;
                    }else if (getBottom()+offsetY>parent.getHeight()){
                        topNew = parent.getHeight()-getHeight();
                    }
                }
                if (getLeft() <= 0 && offsetX < 0) {
                    leftNew = 0;
                    offsetX = 0;
                } else if (getRight() >= parent.getRight() && offsetX > 0) {
                    offsetX = 0;
                } else {
                    leftNew += offsetX;
                    if (getLeft()<0){
                        leftNew =0 ;
                    }else if (getRight()+offsetX>parent.getWidth()){
                        leftNew = parent.getWidth()-getWidth();
                    }
                }

//                offsetLeftAndRight(offsetX);
//                offsetTopAndBottom(offsetY);

                layout(leftNew, topNew, leftNew + getWidth(), topNew + getHeight());
                Log.e(TAG, "l:t " + getLeft() + ":" + getTop());

                break;

            case MotionEvent.ACTION_UP:

                break;

        }


        return true;
    }
}
