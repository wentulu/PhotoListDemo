package cn.stu.cusview.ruiz.myapplication.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class FooterBeheavior extends CoordinatorLayout.Behavior {

    private static final String TAG = "FooterBeheavior";
    public FooterBeheavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {

        float translationY = dependency.getY();
        float y = Math.abs(translationY);
        Log.e(TAG,"translationY="+y);
        child.setTranslationY(y);
        return true;
    }
}
