package com.waze.view.anim;

import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import com.waze.LayoutManager;

public final class SwapViews implements Runnable {
    RelativeLayout firstLayout;
    private boolean mIsFirstView;
    RelativeLayout secondLayout;

    public SwapViews(boolean isFirstView, RelativeLayout firstLayout, RelativeLayout secondLayout) {
        this.mIsFirstView = isFirstView;
        this.firstLayout = firstLayout;
        this.secondLayout = secondLayout;
    }

    public void run() {
        Flip3dAnimation rotation;
        float centerX = ((float) this.firstLayout.getWidth()) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
        float centerY = ((float) this.secondLayout.getHeight()) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
        if (this.mIsFirstView) {
            this.firstLayout.setVisibility(8);
            this.secondLayout.setVisibility(0);
            this.secondLayout.requestFocus();
            rotation = new Flip3dAnimation(-90.0f, 0.0f, centerX, centerY);
        } else {
            this.secondLayout.setVisibility(8);
            this.firstLayout.setVisibility(0);
            this.firstLayout.requestFocus();
            rotation = new Flip3dAnimation(90.0f, 0.0f, centerX, centerY);
        }
        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new DecelerateInterpolator());
        if (this.mIsFirstView) {
            this.secondLayout.startAnimation(rotation);
        } else {
            this.firstLayout.startAnimation(rotation);
        }
    }
}
