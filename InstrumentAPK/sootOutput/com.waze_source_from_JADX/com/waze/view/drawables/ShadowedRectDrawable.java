package com.waze.view.drawables;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class ShadowedRectDrawable extends Drawable {
    private static final float DEFAULT_TOP_DARKEN_RATIO = 0.9f;
    private Paint mBottomPaint;
    private Paint mTopPaint;

    public ShadowedRectDrawable(int color) {
        this(color, DEFAULT_TOP_DARKEN_RATIO);
    }

    public ShadowedRectDrawable(int color, float darkenRatio) {
        this.mTopPaint = new Paint();
        this.mBottomPaint = new Paint();
        int darkRgb = (int) (255.0f * darkenRatio);
        this.mTopPaint.setARGB(255, darkRgb, darkRgb, darkRgb);
        this.mBottomPaint.setColor(-1);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setScale(((float) Color.red(color)) / 255.0f, ((float) Color.green(color)) / 255.0f, ((float) Color.blue(color)) / 255.0f, 1.0f);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        this.mTopPaint.setColorFilter(filter);
        this.mBottomPaint.setColorFilter(filter);
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        canvas.drawRect(0.0f, 0.0f, (float) bounds.width(), (float) (bounds.height() / 2), this.mTopPaint);
        canvas.drawRect(0.0f, (float) (bounds.height() / 2), (float) bounds.width(), (float) bounds.height(), this.mBottomPaint);
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public int getOpacity() {
        return 0;
    }
}
