package com.waze.view.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class WidthAnimation extends Animation {
    private int mFromWidth;
    private WidthChangedListener mListener;
    private int mToWidth;
    private View mView;

    public interface WidthChangedListener {
        void onWidthChanged(int i);
    }

    public WidthAnimation(View view, int fromWidth, int toWidth) {
        this.mView = view;
        this.mFromWidth = fromWidth;
        this.mToWidth = toWidth;
    }

    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int width = this.mFromWidth + ((int) (((float) (this.mToWidth - this.mFromWidth)) * interpolatedTime));
        this.mView.getLayoutParams().width = width;
        this.mView.setLayoutParams(this.mView.getLayoutParams());
        if (this.mListener != null) {
            this.mListener.onWidthChanged(width);
        }
    }

    public void setWidthChangedListener(WidthChangedListener listener) {
        this.mListener = listener;
    }

    public boolean willChangeBounds() {
        return true;
    }
}
