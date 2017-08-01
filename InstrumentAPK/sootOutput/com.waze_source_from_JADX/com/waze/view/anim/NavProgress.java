package com.waze.view.anim;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Region.Op;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.waze.LayoutManager;
import com.waze.map.CanvasFont;

public class NavProgress extends View {
    private static final int CIRCLE_COLOR = -5383962;
    private int mAnimCycle = 1000;
    private int mBgColor = -1;
    private Paint mBgPaint;
    private Paint mClearPaint;
    private int mCornerX;
    private int mCornerY;
    private float mDensity;
    private Paint mDotPaint;
    private int mHeight;
    private long mRevealTime = 0;
    private long mStartTime = 0;
    private int mWidth;
    private Interpolator x1Int = new AccelerateDecelerateInterpolator();
    private Interpolator x2Int = new DecelerateInterpolator(1.0f);
    private Interpolator x3Int = new AccelerateInterpolator(1.0f);
    private Interpolator y1Int = new CycleInterpolator(1.0f);
    private Interpolator y2Int = new CycleInterpolator(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);

    public NavProgress(Context context) {
        super(context);
        init(context);
    }

    public NavProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NavProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(21)
    public NavProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.mDensity = context.getResources().getDisplayMetrics().density;
        this.mDotPaint = new Paint(1);
        this.mDotPaint.setStyle(Style.FILL_AND_STROKE);
        this.mDotPaint.setColor(CIRCLE_COLOR);
        this.mClearPaint = new Paint(1);
        this.mClearPaint.setStyle(Style.FILL_AND_STROKE);
        this.mClearPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        this.mBgPaint = new Paint(1);
        this.mBgPaint.setStyle(Style.FILL_AND_STROKE);
        this.mBgPaint.setColor(this.mBgColor);
    }

    public void setBgColor(int color) {
        this.mBgColor = color;
        this.mBgPaint.setColor(color);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = (w * 5) / 10;
        this.mHeight = (h * 5) / 10;
        this.mCornerX = (w / 2) - (this.mWidth / 2);
        this.mCornerY = (h / 2) - (this.mHeight / 2);
    }

    public void startReveal() {
        if (this.mRevealTime == 0) {
            this.mRevealTime = System.currentTimeMillis();
        }
    }

    protected void onDraw(Canvas canvas) {
        float input;
        super.onDraw(canvas);
        long curTime = System.currentTimeMillis();
        if (this.mStartTime == 0) {
            this.mStartTime = curTime;
            input = 0.0f;
        } else {
            input = ((float) ((int) ((curTime - this.mStartTime) % ((long) this.mAnimCycle)))) / ((float) this.mAnimCycle);
        }
        if (this.mRevealTime == 0) {
            canvas.drawColor(this.mBgColor);
            drawCircles(canvas, input, this.mDensity * 7.0f, 0.0f);
            input -= 0.015f;
            if (input < 0.0f) {
                input += 1.0f;
            }
            drawCircles(canvas, input, this.mDensity * 3.0f, this.mDensity * 3.0f);
            return;
        }
        drawHoles(canvas, input, curTime);
    }

    private void drawCircles(Canvas canvas, float input, float radius, float dy) {
        int x2 = (this.mWidth / 2) - ((int) (((float) (this.mWidth / 2)) * this.x2Int.getInterpolation(input)));
        int y2 = (this.mHeight / 2) - ((int) (((float) (this.mHeight / 4)) * this.y2Int.getInterpolation(input)));
        int x3 = this.mWidth - ((int) (((float) (this.mWidth / 2)) * this.x3Int.getInterpolation(input)));
        int y3 = this.mHeight - y2;
        canvas.drawCircle((float) (this.mCornerX + ((int) (((float) this.mWidth) * this.x1Int.getInterpolation(input)))), ((float) (this.mCornerY + (((int) ((((float) this.mHeight) * this.y1Int.getInterpolation(input)) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN)) + (this.mHeight / 2)))) + dy, radius, this.mDotPaint);
        canvas.drawCircle((float) (this.mCornerX + x2), ((float) (this.mCornerY + y2)) + dy, radius, this.mDotPaint);
        canvas.drawCircle((float) (this.mCornerX + x3), ((float) (this.mCornerY + y3)) + dy, radius, this.mDotPaint);
        postInvalidateDelayed(20);
    }

    private void drawHoles(Canvas canvas, float input, long curTime) {
        int x1 = (int) (((float) this.mWidth) * this.x1Int.getInterpolation(input));
        int y1 = ((int) ((((float) this.mHeight) * this.y1Int.getInterpolation(input)) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN)) + (this.mHeight / 2);
        int x2 = (this.mWidth / 2) - ((int) (((float) (this.mWidth / 2)) * this.x2Int.getInterpolation(input)));
        int y2 = (this.mHeight / 2) - ((int) (((float) (this.mHeight / 4)) * this.y2Int.getInterpolation(input)));
        int x3 = this.mWidth - ((int) (((float) (this.mWidth / 2)) * this.x3Int.getInterpolation(input)));
        int y3 = this.mHeight - y2;
        float radius = (this.mDensity * ((float) Math.pow(2.0d, (double) (CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR + (((float) (curTime - this.mRevealTime)) / 200.0f))))) * 5.0f;
        Path clipPath = new Path();
        clipPath.addCircle((float) (this.mCornerX + x1), (float) (this.mCornerY + y1), radius, Direction.CW);
        clipPath.addCircle((float) (this.mCornerX + x2), (float) (this.mCornerY + y2), radius, Direction.CW);
        clipPath.addCircle((float) (this.mCornerX + x3), (float) (this.mCornerY + y3), radius, Direction.CW);
        canvas.clipPath(clipPath, Op.DIFFERENCE);
        canvas.drawColor(this.mBgColor);
        radius += this.mDensity * 7.0f;
        canvas.drawCircle((float) (this.mCornerX + x1), (float) (this.mCornerY + y1), radius, this.mDotPaint);
        canvas.drawCircle((float) (this.mCornerX + x2), (float) (this.mCornerY + y2), radius, this.mDotPaint);
        canvas.drawCircle((float) (this.mCornerX + x3), (float) (this.mCornerY + y3), radius, this.mDotPaint);
        if (radius < ((float) this.mWidth)) {
            postInvalidateDelayed(20);
        }
    }

    public boolean isRevealing() {
        return this.mRevealTime != 0;
    }

    public void reset() {
        this.mRevealTime = 0;
        this.mStartTime = 0;
        invalidate();
    }
}
