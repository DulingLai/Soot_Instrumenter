package com.waze.view.anim;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;

public final class DisplayNextView implements AnimationListener {
    RelativeLayout firstLayout;
    private boolean mCurrentView;
    RelativeLayout secondLayout;

    public DisplayNextView(boolean currentView, RelativeLayout firstLayout, RelativeLayout secondLayout) {
        this.mCurrentView = currentView;
        this.firstLayout = firstLayout;
        this.secondLayout = secondLayout;
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
        this.firstLayout.post(new SwapViews(this.mCurrentView, this.firstLayout, this.secondLayout));
    }

    public void onAnimationRepeat(Animation animation) {
    }
}
