package com.waze.view.drawables;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.EasingInterpolators;

public class AnimatingCircleDrawable extends AnimatingDrawable {
    private Paint mBorderPaint;
    private Paint mFillPaint;

    public AnimatingCircleDrawable() {
        this(0);
    }

    public AnimatingCircleDrawable(int color) {
        this.mBorderPaint = new Paint();
        this.mFillPaint = new Paint();
        this.mBorderPaint.setStyle(Style.STROKE);
        this.mFillPaint.setStyle(Style.FILL);
        this.mBorderPaint.setStrokeWidth((float) PixelMeasure.dp(1));
        this.mBorderPaint.setAntiAlias(true);
        setColor(color);
    }

    public void setColor(int color) {
        this.mBorderPaint.setColor(color);
        this.mFillPaint.setColor(color);
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        int centerX = bounds.width() / 2;
        int centerY = bounds.height() / 2;
        int radius = Math.min(centerX, centerY) - PixelMeasure.dp(2);
        canvas.drawCircle((float) centerX, (float) centerY, (float) radius, this.mBorderPaint);
        canvas.drawCircle((float) centerX, (float) centerY, ((float) radius) * (((float) getLevel()) / 10000.0f), this.mFillPaint);
    }

    public void showCircle() {
        animate(0, 10000, 400, EasingInterpolators.BOUNCE_OUT);
    }

    public void hideCircle() {
        animate(10000, 0, 300, EasingInterpolators.BOUNCE_OUT);
    }

    public void setAlpha(int alpha) {
        this.mBorderPaint.setAlpha(alpha);
        this.mFillPaint.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mBorderPaint.setColorFilter(colorFilter);
        this.mFillPaint.setColorFilter(colorFilter);
    }

    public int getOpacity() {
        return 0;
    }
}
