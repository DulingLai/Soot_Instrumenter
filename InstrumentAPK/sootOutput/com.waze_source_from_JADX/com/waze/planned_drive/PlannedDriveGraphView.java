package com.waze.planned_drive;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;
import com.waze.C1283R;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.EasingInterpolators;

public class PlannedDriveGraphView extends View {
    public static final long ANIMATION_DURATION = 200;
    public static final int BAR_ANIMATION_DELAY = 10;
    private static final int MAX_BAR_COLOR = -33155;
    private static final int MIN_BAR_COLOR = -10823;
    public static boolean haltAllGraphAnimations;
    private long[] mAnimationStartTimes;
    private RectF mDrawRect;
    private boolean mIsAnimating;
    private Paint mPaint;
    private float[] mValues;

    public PlannedDriveGraphView(Context context) {
        this(context, null);
    }

    public PlannedDriveGraphView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlannedDriveGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        this.mPaint = new Paint();
        this.mPaint.setShader(new LinearGradient(0.0f, 0.0f, (float) PixelMeasure.dimension(C1283R.dimen.planDriveGraphWidth), 0.0f, MAX_BAR_COLOR, MIN_BAR_COLOR, TileMode.CLAMP));
        this.mDrawRect = new RectF();
    }

    public float[] getValues() {
        return this.mValues;
    }

    public void setValues(float[] values) {
        this.mValues = values;
        if (this.mValues != null) {
            this.mAnimationStartTimes = new long[this.mValues.length];
        } else {
            this.mAnimationStartTimes = null;
        }
        postInvalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mValues == null || getMeasuredHeight() <= 0 || getMeasuredWidth() <= 0) {
            postInvalidate();
            return;
        }
        float barHeight = ((float) getMeasuredHeight()) / ((float) this.mValues.length);
        float currentTop = 0.0f;
        for (int i = 0; i < this.mValues.length; i++) {
            drawBar(i, currentTop, currentTop + barHeight, canvas);
            currentTop += barHeight;
        }
        if (!this.mIsAnimating) {
            return;
        }
        if (isAllAnimationsCompleted()) {
            this.mIsAnimating = false;
        } else {
            postInvalidate();
        }
    }

    private boolean isAllAnimationsCompleted() {
        if (this.mAnimationStartTimes == null) {
            return false;
        }
        for (long startTime : this.mAnimationStartTimes) {
            if (200 + startTime > System.currentTimeMillis()) {
                return false;
            }
        }
        return true;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int prevWidth = getMeasuredWidth();
        int prevHeight = getMeasuredHeight();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (prevWidth != getMeasuredWidth() || prevHeight != getMeasuredHeight()) {
            postInvalidate();
        }
    }

    public boolean isActuallyAnimating() {
        return this.mValues != null;
    }

    public void startAnimating(boolean fromTop) {
        long nextAnimationStartTime = System.currentTimeMillis();
        for (int i = 0; i < this.mAnimationStartTimes.length; i++) {
            this.mAnimationStartTimes[fromTop ? i : (this.mAnimationStartTimes.length - i) - 1] = nextAnimationStartTime;
            nextAnimationStartTime += 10;
        }
        this.mIsAnimating = true;
        postInvalidate();
    }

    private void drawBar(int index, float top, float bottom, Canvas canvas) {
        float rawValue = this.mValues[index];
        float animationRatio = this.mIsAnimating ? Math.max(Math.min(((float) (System.currentTimeMillis() - this.mAnimationStartTimes[index])) / 200.0f, 1.0f), 0.0f) : 1.0f;
        float barHeight = bottom - top;
        float cornerRadius = barHeight * 0.4f;
        this.mDrawRect.set(((float) getMeasuredWidth()) - (((((float) getMeasuredWidth()) * 0.2f) * EasingInterpolators.BOUNCE_OUT.getInterpolation(animationRatio)) + ((((float) getMeasuredWidth()) * 0.75f) * (rawValue * EasingInterpolators.BOUNCE_OUT.getInterpolation(animationRatio)))), top + (0.1f * barHeight), (float) getMeasuredWidth(), bottom - (0.1f * barHeight));
        canvas.drawRoundRect(this.mDrawRect, cornerRadius, cornerRadius, this.mPaint);
    }
}
