package com.waze.view.anim;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import com.waze.map.CanvasFont;
import com.waze.utils.PixelMeasure;

public class ReportMenuBg extends View {
    static final long ANIM_INTERVAL_MS = 5;
    static final long REVEAL_DURATION_MS = 500;
    private Paint mBottomPaint;
    private int mColor0 = -14010046;
    private int mColor1 = -11184550;
    private float mDensity;
    private int mHeight;
    private final Interpolator mInterpolator = new AccelerateInterpolator(0.9f);
    private int mOriginX = 0;
    private int mOriginY = 1;
    private long mRevealDuration = REVEAL_DURATION_MS;
    private long mRevealTime;
    private boolean mRevealed = false;
    private Paint mTopPaint;
    private int mWidth;

    public ReportMenuBg(Context context) {
        super(context);
        init(context);
    }

    public ReportMenuBg(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ReportMenuBg(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(21)
    public ReportMenuBg(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.mDensity = context.getResources().getDisplayMetrics().density;
        this.mTopPaint = new Paint(1);
        this.mTopPaint.setStyle(Style.FILL);
        this.mTopPaint.setColor(-1);
        PixelMeasure.setResourceIfUnset(getResources());
        this.mBottomPaint = new Paint(1);
        this.mBottomPaint.setStyle(Style.STROKE);
        this.mBottomPaint.setStrokeWidth((float) PixelMeasure.dp(30));
        this.mBottomPaint.setColor(-1426063361);
        this.mBottomPaint.setMaskFilter(new BlurMaskFilter(8.0f, Blur.NORMAL));
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        this.mTopPaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, (float) h, this.mColor0, this.mColor1, TileMode.MIRROR));
        this.mBottomPaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, (float) h, this.mColor0, this.mColor1, TileMode.MIRROR));
    }

    public void setRevealDuration(long revealDuration) {
        this.mRevealDuration = revealDuration;
    }

    public void startReveal() {
        this.mRevealed = false;
        this.mRevealTime = System.currentTimeMillis();
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode()) {
            canvas.drawCircle((float) (this.mWidth / 2), (float) (this.mHeight / 2), (float) (this.mHeight / 2), this.mTopPaint);
        }
        if (this.mRevealed) {
            canvas.drawPaint(this.mTopPaint);
        }
        if (this.mRevealTime != 0) {
            long diff = System.currentTimeMillis() - this.mRevealTime;
            if (diff < this.mRevealDuration) {
                float i = this.mInterpolator.getInterpolation(((float) diff) / ((float) this.mRevealDuration));
                float r = i * ((float) (this.mHeight + this.mWidth));
                this.mBottomPaint.setColor(Color.argb((int) (255.0f * i), 255, 255, 255));
                canvas.drawCircle((float) this.mOriginX, (float) this.mOriginY, r, this.mBottomPaint);
                this.mTopPaint.setColor(Color.argb(i > CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR ? 255 : ((int) (255.0f * i)) + 128, 255, 255, 255));
                canvas.drawCircle((float) this.mOriginX, (float) this.mOriginY, r, this.mTopPaint);
                postInvalidateDelayed(ANIM_INTERVAL_MS);
                return;
            }
            this.mTopPaint.setColor(Color.argb(255, 255, 255, 255));
            canvas.drawPaint(this.mTopPaint);
            this.mRevealed = true;
            this.mRevealTime = 0;
        }
    }

    public void reset() {
        this.mRevealTime = 0;
        this.mRevealed = false;
        invalidate();
    }

    public void setRevealed() {
        this.mRevealTime = 0;
        this.mRevealed = true;
        invalidate();
    }

    public void setOrigin(int x, int y) {
        this.mOriginX = x;
        this.mOriginY = y;
    }
}
