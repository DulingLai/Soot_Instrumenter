package com.waze.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import com.waze.view.anim.MaterialDesignImageAnimationHelper;
import com.waze.view.anim.ViewPropertyAnimatorHelper;

public class VolleyManager$ViewSwappingRequestListener implements VolleyManager$ImageRequestListener {
    private View mAppearingView;
    private View mDisappearingView;
    private VolleyManager$ImageRequestListener mListener;

    public VolleyManager$ViewSwappingRequestListener(View appearingView, View disappearingView, VolleyManager$ImageRequestListener listener) {
        this.mAppearingView = appearingView;
        this.mDisappearingView = disappearingView;
        this.mListener = listener;
    }

    public void onImageLoadComplete(Bitmap bitmap, Object token, long duration) {
        if (bitmap != null) {
            if (this.mAppearingView != null && (this.mAppearingView instanceof ImageView)) {
                Drawable drawable = createDrawableForBitmap(bitmap);
                ((ImageView) this.mAppearingView).setImageDrawable(drawable);
                this.mAppearingView.setVisibility(0);
                if (duration > 200) {
                    MaterialDesignImageAnimationHelper.animateImageEntrance(drawable, 1500);
                }
            }
            if (this.mDisappearingView != null) {
                if (duration > 200) {
                    ViewPropertyAnimatorHelper.initAnimation(this.mDisappearingView).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(this.mDisappearingView));
                } else {
                    this.mDisappearingView.setVisibility(8);
                }
            }
            if (this.mListener != null) {
                this.mListener.onImageLoadComplete(bitmap, token, duration);
            }
        } else if (this.mListener != null) {
            this.mListener.onImageLoadFailed(token, duration);
        }
    }

    protected Drawable createDrawableForBitmap(Bitmap bitmap) {
        return new BitmapDrawable(this.mAppearingView.getResources(), bitmap);
    }

    public void onImageLoadFailed(Object token, long duration) {
        if (this.mListener != null) {
            this.mListener.onImageLoadFailed(token, duration);
        }
    }
}
