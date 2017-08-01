package com.waze.view.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class HeightAnimation extends Animation {
    private int mFromHeight;
    private HeightChangedListener mListener;
    private int mToHeight;
    private View mView;

    public interface HeightChangedListener {
        void onHeightChanged(int i);
    }

    public HeightAnimation(View view, int fromHeight, int toHeight) {
        this.mView = view;
        this.mFromHeight = fromHeight;
        this.mToHeight = toHeight;
    }

    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int height = this.mFromHeight + ((int) (((float) (this.mToHeight - this.mFromHeight)) * interpolatedTime));
        this.mView.getLayoutParams().height = height;
        this.mView.setLayoutParams(this.mView.getLayoutParams());
        if (this.mListener != null) {
            this.mListener.onHeightChanged(height);
        }
    }

    public void setHeightChangedListener(HeightChangedListener listener) {
        this.mListener = listener;
    }

    public boolean willChangeBounds() {
        return true;
    }
}
