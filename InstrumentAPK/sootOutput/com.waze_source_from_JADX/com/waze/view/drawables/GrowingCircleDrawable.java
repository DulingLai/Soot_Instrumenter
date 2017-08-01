package com.waze.view.drawables;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.view.animation.Interpolator;
import com.waze.AppService;

public class GrowingCircleDrawable extends AnimatingDrawable {
    private int mOriginX;
    private int mOriginY;
    private Paint mPaint = new Paint();

    public GrowingCircleDrawable() {
        this.mPaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, (float) AppService.getAppResources().getDisplayMetrics().heightPixels, -14010046, -11184550, TileMode.CLAMP));
    }

    public void draw(Canvas canvas) {
        float ratio = ((float) getLevel()) / 10000.0f;
        Rect bounds = getBounds();
        float maxRadius = (float) Math.max(bounds.width(), bounds.height());
        if (ratio < 1.0f) {
            canvas.drawCircle((float) this.mOriginX, (float) this.mOriginY, ratio * maxRadius, this.mPaint);
        } else {
            canvas.drawRect(bounds, this.mPaint);
        }
    }

    public void animate() {
        animate(200, null);
    }

    public void animate(long duration, Interpolator interpolator) {
        animate(0, 10000, duration, interpolator);
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public int getOpacity() {
        return 0;
    }

    public void setOrigin(int x, int y) {
        this.mOriginX = x;
        this.mOriginY = y;
    }
}
