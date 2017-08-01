package com.waze.view.anim;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.view.animation.Interpolator;
import com.waze.LayoutManager;
import com.waze.map.CanvasFont;

public class MaterialDesignImageAnimationHelper {

    private static class AlphaSatColorMatrixEvaluator implements TypeEvaluator {
        private static final int MAX_BLACKER = 50;
        private ColorMatrix colorMatrix = new ColorMatrix();
        float[] elements = new float[20];

        public ColorMatrix getColorMatrix() {
            return this.colorMatrix;
        }

        public Object evaluate(float fraction, Object startValue, Object endValue) {
            float phase = fraction * 3.0f;
            this.elements[18] = Math.min(phase, LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
            float blackening = (float) Math.round((1.0f - (Math.min(phase, CanvasFont.OUTLINE_STROKE_WIDTH) / CanvasFont.OUTLINE_STROKE_WIDTH)) * LayoutManager.WAZE_LAYOUT_EDIT_HEIGHT);
            float[] fArr = this.elements;
            float[] fArr2 = this.elements;
            float f = -blackening;
            this.elements[14] = f;
            fArr2[9] = f;
            fArr[4] = f;
            float invSat = 1.0f - Math.max(0.0f, fraction);
            float R = 0.213f * invSat;
            float G = 0.715f * invSat;
            float B = 0.072f * invSat;
            this.elements[0] = R + fraction;
            this.elements[1] = G;
            this.elements[2] = B;
            this.elements[5] = R;
            this.elements[6] = G + fraction;
            this.elements[7] = B;
            this.elements[10] = R;
            this.elements[11] = G;
            this.elements[12] = B + fraction;
            this.colorMatrix.set(this.elements);
            return this.colorMatrix;
        }
    }

    public static void animateImageEntrance(Drawable drawable) {
        animateImageEntrance(drawable, 300, ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
    }

    public static void animateImageEntrance(Drawable drawable, long duration) {
        animateImageEntrance(drawable, duration, ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
    }

    public static void animateImageEntrance(Drawable drawable, Interpolator interpolator) {
        animateImageEntrance(drawable, 300, interpolator);
    }

    public static void animateImageEntrance(final Drawable drawable, long duration, Interpolator interpolator) {
        AlphaSatColorMatrixEvaluator evaluator = new AlphaSatColorMatrixEvaluator();
        final ColorMatrixColorFilter filter = new ColorMatrixColorFilter(evaluator.getColorMatrix());
        drawable.setColorFilter(filter);
        ObjectAnimator animator = ObjectAnimator.ofObject(filter, "colorMatrix", evaluator, new Object[]{evaluator.getColorMatrix()});
        animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                drawable.setColorFilter(filter);
            }
        });
        animator.setDuration(duration);
        animator.setInterpolator(interpolator);
        animator.start();
    }
}
