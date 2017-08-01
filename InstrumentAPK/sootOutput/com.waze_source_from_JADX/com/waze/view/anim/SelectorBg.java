package com.waze.view.anim;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class SelectorBg extends View {
    private static final int TIMER_TICK = 25;
    private ObjectAnimator _animator;
    private int _bgColor = -16777216;
    private Paint _bgPaint;
    private Path _bgPath;
    private int _duration = 200;
    private int _lineColor = -6184026;
    private int _offset;
    private int _strokeWidth = 3;
    private Paint _topLinePaint;
    private Path _topLinePath;
    private boolean shouldAnimateSelected = false;

    public SelectorBg(Context context, AttributeSet attrs) {
        super(context, attrs);
        this._strokeWidth = (int) Math.ceil((double) context.getResources().getDisplayMetrics().density);
    }

    public void setLineColor(int color) {
        this._lineColor = color;
    }

    public void setBgColor(int color) {
        this._bgColor = color;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this._offset = 0;
        int alpha = 0;
        if (isInEditMode()) {
            this._offset = h / 10;
            alpha = 128;
        }
        this._bgPath = new Path();
        drawBgPath(this._bgPath, w, h, (float) this._offset);
        this._topLinePath = new Path();
        drawTopLinePath(this._topLinePath, w, h, (float) this._offset);
        this._bgPaint = new Paint();
        this._bgPaint.setStyle(Style.FILL);
        this._bgPaint.setColor(this._bgColor);
        this._bgPaint.setAlpha(alpha);
        this._bgPaint.setAntiAlias(true);
        this._topLinePaint = new Paint();
        this._topLinePaint.setStyle(Style.STROKE);
        this._topLinePaint.setStrokeWidth((float) this._strokeWidth);
        this._topLinePaint.setColor(this._lineColor);
        this._topLinePaint.setAntiAlias(true);
        if (this.shouldAnimateSelected) {
            animateSelected();
            this.shouldAnimateSelected = false;
        }
    }

    private void drawTopLinePath(Path p, int w, int h, float offsetPixels) {
        p.reset();
        p.moveTo(-1.0f, (float) (this._strokeWidth / 2));
        if (offsetPixels > 0.0f) {
            p.lineTo(((float) (w / 2)) - offsetPixels, (float) (this._strokeWidth / 2));
            p.lineTo((float) (w / 2), offsetPixels);
            p.lineTo(((float) (w / 2)) + offsetPixels, (float) (this._strokeWidth / 2));
        }
        p.lineTo((float) w, (float) (this._strokeWidth / 2));
    }

    private void drawBgPath(Path p, int w, int h, float offsetPixels) {
        drawTopLinePath(p, w, h, offsetPixels);
        p.lineTo((float) w, (float) h);
        p.lineTo(0.0f, (float) h);
        p.lineTo(0.0f, (float) this._strokeWidth);
    }

    public void setDuration(int ms) {
        this._duration = ms;
    }

    public void setOffset(int value) {
        this._offset = value;
        int w = getWidth();
        int h = getHeight();
        drawBgPath(this._bgPath, w, h, (float) this._offset);
        drawTopLinePath(this._topLinePath, w, h, (float) this._offset);
        this._bgPaint.setAlpha(((this._offset * 10) * 128) / h);
        invalidate();
    }

    public void animateUnselected() {
        if (this._animator != null) {
            this._animator.cancel();
        }
        this._animator = ObjectAnimator.ofInt(this, "offset", new int[]{getHeight() / 10, 0});
        this._animator.setDuration((long) this._duration);
        this._animator.setInterpolator(new AccelerateDecelerateInterpolator());
        this._animator.start();
    }

    public void animateSelected() {
        if (this._bgPath == null) {
            this.shouldAnimateSelected = true;
            return;
        }
        if (this._animator != null) {
            this._animator.cancel();
        }
        this._animator = ObjectAnimator.ofInt(this, "offset", new int[]{0, getHeight() / 10});
        this._animator.setDuration((long) this._duration);
        this._animator.setInterpolator(new AccelerateDecelerateInterpolator());
        this._animator.start();
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawPath(this._bgPath, this._bgPaint);
        canvas.drawPath(this._topLinePath, this._topLinePaint);
    }
}
