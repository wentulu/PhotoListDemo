package cn.stu.cusview.ruiz.myapplication.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.stu.cusview.ruiz.myapplication.R;


public class ItemDivederDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "ItemDivederDecoration";

    public static final int ORIENTATION_HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static final int ORIENTATION_VERTICAL = LinearLayoutManager.VERTICAL;

    @IntDef({ORIENTATION_HORIZONTAL, ORIENTATION_VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OrientationCheck {

    }

    int[] attrs = new int[]{
            android.R.attr.listDivider
    };

    private int orientation;
    private Drawable mDividerDrawable;

    public ItemDivederDecoration(Context context, @OrientationCheck int orientation) {
        TypedArray ta = context.obtainStyledAttributes(attrs);
        mDividerDrawable = context.getResources().getDrawable(R.drawable.divider);
                //ta.getDrawable(0);
        ta.recycle();
        setOrientation(orientation);
    }


    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (orientation==ORIENTATION_HORIZONTAL){
            outRect.set(0,0,mDividerDrawable.getIntrinsicWidth(),0);
        }else {
            outRect.set(0,0,0,mDividerDrawable.getIntrinsicHeight());
        }
    }


    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (orientation == ORIENTATION_HORIZONTAL) {
            drawHorizontal(c, parent);
        } else {
            drawVertical(c, parent);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int paddingLeft = parent.getPaddingLeft();
        int paddingRight = parent.getWidth()-parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int top = childView.getBottom()+ layoutParams.bottomMargin;
            int bottom = top+mDividerDrawable.getIntrinsicHeight();
            mDividerDrawable.setBounds(paddingLeft,top,paddingRight,bottom);
            mDividerDrawable.draw(c);
        }

    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getRight()+layoutParams.rightMargin;//getRight不能使用getWidth代替
            int right = left+mDividerDrawable.getIntrinsicWidth();
            mDividerDrawable.setBounds(left,top,right,bottom);
            mDividerDrawable.draw(c);
        }
    }


}
