package cn.stu.cusview.ruiz.myapplication.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;


public class BottomBeheavior extends CoordinatorLayout.Behavior<View> {

    private static final String TAG = "BottomBeheavior";

    private int directionChange;

    public BottomBeheavior(Context context, AttributeSet attributes) {
        super(context, attributes);
    }


    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }


    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.e(TAG, "本次滑动" + dy);
        if (dy > 0 && directionChange < 0 || dy < 0 && directionChange > 0) {
            child.animate().cancel();
            directionChange = 0;
            Log.e(TAG, "滑动清零" + dy);
        }
        directionChange += dy;
        Log.e(TAG, "总滑动距离" + directionChange + "view高度" + child.getHeight() + "View show" + (child.getVisibility() == View.VISIBLE ? "Visible" : "Gone"));
        if (directionChange < 0 ) {
            show(child);
            Log.e(TAG, "展示" + dy);
        } else if (directionChange > child.getHeight() ) {
            hide(child);
            Log.e(TAG, "隐藏" + dy);
        }
//        if (dy>0){//向上滑动
//
//        }else {//向下滑动
//
//        }


    }

    private void hide(final View view) {
        Log.e(TAG, "动画");
        if (view.getTranslationY()>0){
            return;
        }
        ViewPropertyAnimator animator = view.animate().translationY(view.getHeight())
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(200)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        Log.e(TAG,"隐藏动画开始");
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.e(TAG,"隐藏动画结束");
//                        view.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        Log.e(TAG,"隐藏动画取消");
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
        animator.start();
    }


    private void show(final View view) {
        if (view.getTranslationY()==0){
            return;
        }
        ViewPropertyAnimator animator = view.animate().translationY(0)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(200)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.e(TAG,"显示动画结束");
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
        animator.start();
    }

}
