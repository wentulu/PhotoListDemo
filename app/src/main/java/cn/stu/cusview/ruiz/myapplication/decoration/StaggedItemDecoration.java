package cn.stu.cusview.ruiz.myapplication.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


import cn.stu.cusview.ruiz.myapplication.R;

public class StaggedItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "StaggedItemDecoration";

    private Context mContext;
    private int mOrientation;

    private Drawable mDrawable,mDrawable1;

    @IntDef({StaggeredGridLayoutManager.VERTICAL, StaggeredGridLayoutManager.HORIZONTAL})
    @Retention(RetentionPolicy.SOURCE)
    @interface Orientation {
    }

    public StaggedItemDecoration(Context context, @Orientation int orientation) {
        mContext = context;
        mOrientation = orientation;
        mDrawable = context.getResources().getDrawable(R.drawable.divider);

        mDrawable1 = context.getResources().getDrawable(R.drawable.divider);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int space = mDrawable.getIntrinsicHeight();
        if (mOrientation == StaggeredGridLayoutManager.VERTICAL) {
            outRect.set(space, space, space, space);
        } else {
            outRect.set(space, space, space, space);
        }
        Log.e(TAG,"L R T B="+view.getLeft()+" "+view.getRight()+" "+view.getTop()+" "+view.getBottom());
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == StaggeredGridLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawHorizontal(@NonNull Canvas c, @NonNull RecyclerView parent) {

    }

    private void drawVertical(@NonNull Canvas c, @NonNull RecyclerView parent) {
        int space = mDrawable.getIntrinsicHeight();
        int childrens = parent.getChildCount();
        for (int i = 0; i < childrens; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop();
            int bottom = child.getBottom();
            int left = child.getLeft();
            int right = child.getRight();
            if (left<200){
                mDrawable.setBounds(right+space,top-space,right+space+space,bottom);
                mDrawable.draw(c);
                mDrawable1.setBounds(left-space,bottom,right+space,bottom+space);
                mDrawable1.draw(c);
            }else {
                mDrawable.setBounds(left-space,top-space,left,bottom);
                mDrawable.draw(c);
                mDrawable.setBounds(left-space,bottom,right+space,bottom+space);
                mDrawable.draw(c);
            }

        }
    }
}
