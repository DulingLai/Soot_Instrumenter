package com.waze.view.button;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.waze.LayoutManager;
import com.waze.R;
import com.waze.map.CanvasFont;

public class PillButton extends FrameLayout {
    public static final float PRESSED_SCALE_DOWN = 0.95f;
    public static final int UPDATE_MILLIS = 20;
    private int mBgColor = -1;
    private StateListDrawable mBgDrawable;
    private int mBmpHeight = -1;
    private int mBmpWidth = -1;
    private float mDensity;
    private boolean mDrawTimer = false;
    private int mLastHeight = -1;
    private int mLastWidth = -1;
    private boolean mManualtrack = false;
    private int mModifyPadding = 0;
    private float mPathLength;
    private boolean mPerformTapOnEnd = true;
    private int mShadowColor = 1996488704;
    private int mShadowSize = -1;
    private int mTimeout;
    private int mTimerDistance = -1;
    private Path mTimerPath;
    private long mTimerStart;
    private int mTimerStroke = -1;
    private int mTimerTimeColor = -13408513;
    private Paint mTimerTimePaint;
    private int mTimerTrackColor = -39373;
    private Paint mTimerTrackPaint;
    private Runnable mUpdateTimer;
    private boolean mbPressed = false;

    class C29831 implements Runnable {
        C29831() {
        }

        public void run() {
            int diff = (int) (System.currentTimeMillis() - PillButton.this.mTimerStart);
            if (diff < 0 || diff >= PillButton.this.mTimeout) {
                PillButton.this.mUpdateTimer = null;
                PillButton.this.mTimerTimePaint.setPathEffect(null);
                PillButton.this.invalidate();
                if (PillButton.this.mPerformTapOnEnd) {
                    PillButton.this.performClick();
                    return;
                }
                return;
            }
            float[] fArr = new float[]{PillButton.this.mPathLength, PillButton.this.mPathLength};
            PillButton.this.mTimerTimePaint.setPathEffect(new DashPathEffect(fArr, PillButton.this.mPathLength * (1.0f - (((float) diff) / ((float) PillButton.this.mTimeout)))));
            PillButton.this.invalidate();
        }
    }

    public PillButton(Context context) {
        super(context);
        init(context);
    }

    public PillButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributes(context, attrs);
        init(context);
    }

    public PillButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context, attrs);
        init(context);
    }

    @TargetApi(21)
    public PillButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttributes(context, attrs);
        init(context);
    }

    void getAttributes(Context context, AttributeSet attrs) {
        TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.PillButton);
        this.mDrawTimer = attrArray.getBoolean(0, this.mDrawTimer);
        this.mBgColor = attrArray.getColor(1, this.mBgColor);
        this.mShadowSize = attrArray.getDimensionPixelSize(6, this.mShadowSize);
        this.mShadowColor = attrArray.getColor(7, this.mShadowColor);
        this.mTimerTrackColor = attrArray.getColor(2, this.mTimerTrackColor);
        this.mTimerTimeColor = attrArray.getColor(3, this.mTimerTimeColor);
        this.mTimerDistance = attrArray.getDimensionPixelSize(5, this.mTimerDistance);
        this.mTimerStroke = attrArray.getDimensionPixelSize(4, this.mTimerStroke);
        this.mModifyPadding = attrArray.getDimensionPixelSize(8, this.mModifyPadding);
        attrArray.recycle();
    }

    public void setDrawTimer() {
        this.mDrawTimer = true;
        setWillNotDraw(false);
        padAndPaint(getMeasuredWidth(), getMeasuredHeight());
        init(getContext());
    }

    public void setPerformTapOnEnd(boolean performTapOnEnd) {
        this.mPerformTapOnEnd = performTapOnEnd;
    }

    void init(Context context) {
        this.mDensity = context.getResources().getDisplayMetrics().density;
        if (this.mShadowSize < 0) {
            this.mShadowSize = (int) (this.mDensity * 5.0f);
        }
        if (this.mTimerDistance < 0) {
            this.mTimerDistance = (int) (this.mDensity * 3.0f);
        }
        if (this.mTimerStroke < 0) {
            this.mTimerStroke = (int) (this.mDensity * 4.0f);
        }
        if (this.mDrawTimer) {
            setWillNotDraw(false);
            this.mTimerTrackPaint = new Paint(1);
            this.mTimerTrackPaint.setStyle(Style.STROKE);
            this.mTimerTrackPaint.setColor(this.mTimerTrackColor);
            this.mTimerTrackPaint.setStrokeWidth((float) this.mTimerStroke);
            this.mTimerTimePaint = new Paint(1);
            this.mTimerTimePaint.setStyle(Style.STROKE);
            this.mTimerTimePaint.setColor(0);
            this.mTimerTimePaint.setStrokeWidth(0.0f);
        }
    }

    private void padAndPaint(int w, int h) {
        int padding = ((int) (0.2071d * ((double) h))) + this.mModifyPadding;
        int wp = this.mShadowSize + padding;
        int hp = this.mShadowSize + padding;
        if (this.mDrawTimer) {
            wp += this.mTimerDistance;
            hp += this.mTimerDistance;
        }
        setPadding(wp, hp, wp, hp);
        if (w > h) {
            this.mBmpWidth = w;
            this.mBmpHeight = h;
            createBg(w, h);
            return;
        }
        this.mBmpWidth = w;
        this.mBmpHeight = w;
        createBg(w, w);
    }

    private void createBg(int width, int height) {
        if (this.mBgDrawable != null) {
            setBackgroundDrawable(null);
            this.mBgDrawable = null;
        }
        int curShadowColor = this.mShadowColor;
        int curShadowSize = this.mShadowSize;
        if (!isEnabled()) {
            curShadowColor = Color.argb(Color.alpha(this.mShadowColor) / 2, Color.red(this.mShadowColor), Color.green(this.mShadowColor), Color.blue(this.mShadowColor));
            curShadowSize = this.mShadowSize / 2;
        }
        Paint paint = new Paint(1);
        paint.setStyle(Style.FILL_AND_STROKE);
        paint.setColor(this.mBgColor);
        paint.setShadowLayer(((float) curShadowSize) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, 0.0f, ((float) curShadowSize) / 4.0f, curShadowColor);
        float top = (float) this.mShadowSize;
        float bottom = (float) (height - this.mShadowSize);
        float left = (float) this.mShadowSize;
        float right = (float) (width - this.mShadowSize);
        Bitmap bitmap = makeBitmap(width, height, top, bottom, left, right, paint);
        if (this.mDrawTimer && bitmap != null) {
            float in = (float) this.mTimerDistance;
            setLayerType(1, null);
            this.mTimerPath = makePillPath(left + in, top + in, right - in, bottom - in);
            if (isInEditMode()) {
                Canvas c1 = new Canvas(bitmap);
                c1.drawPath(this.mTimerPath, this.mTimerTrackPaint);
                this.mTimerTimePaint.setPathEffect(new DashPathEffect(new float[]{10.0f, 100.0f}, 5.0f));
                c1.drawPath(this.mTimerPath, this.mTimerTimePaint);
            }
            if (this.mTimerStart > 0 || this.mManualtrack) {
                drawTimerPath();
            }
        }
        int pressSize = (int) (1.0f * this.mDensity);
        paint.setShadowLayer(((float) curShadowSize) / 4.0f, 0.0f, ((float) curShadowSize) / 8.0f, curShadowColor);
        this.mBgDrawable = new StateListDrawable();
        this.mBgDrawable.addState(StateSet.WILD_CARD, new BitmapDrawable(getResources(), bitmap));
        setBackgroundDrawable(this.mBgDrawable);
        if (!isEnabled()) {
            setDisabledDrawables(this);
        }
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            setChildColorMatrix(this, null, 255);
        } else {
            setDisabledDrawables(this);
        }
        if (this.mBmpWidth > 0 && this.mBmpHeight > 0) {
            createBg(this.mBmpWidth, this.mBmpHeight);
        }
    }

    private void setChildColorMatrix(View child, ColorMatrixColorFilter f, int alpha) {
        Drawable d;
        if (child instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) child;
            int num = vg.getChildCount();
            for (int i = 0; i < num; i++) {
                setChildColorMatrix(vg.getChildAt(i), f, alpha);
            }
        }
        if (child instanceof ImageView) {
            d = ((ImageView) child).getDrawable();
            if (d != null) {
                d.setColorFilter(f);
                d.setAlpha(alpha);
            }
        }
        d = child.getBackground();
        if (d != null) {
            d.setColorFilter(f);
            if (child != this) {
                d.setAlpha(alpha);
            }
        }
    }

    public void onViewAdded(View child) {
        super.onViewAdded(child);
        if (!isEnabled()) {
            setDisabledDrawables(child);
        }
    }

    private void setDisabledDrawables(View child) {
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0.0f);
        setChildColorMatrix(child, new ColorMatrixColorFilter(cm), 178);
    }

    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        if (!isEnabled()) {
            setChildColorMatrix(child, null, 255);
        }
    }

    private Bitmap makeBitmap(int width, int height, float top, float bottom, float left, float right, Paint paint) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        Bitmap bitmap;
        if (VERSION.SDK_INT < 17) {
            bitmap = Bitmap.createBitmap(width, height, Config.ARGB_4444);
            bitmap.setDensity(getContext().getResources().getDisplayMetrics().densityDpi);
        } else {
            bitmap = Bitmap.createBitmap(getContext().getResources().getDisplayMetrics(), width, height, Config.ARGB_4444);
        }
        new Canvas(bitmap).drawPath(makePillPath(left, top, right, bottom), paint);
        return bitmap;
    }

    private void drawTimerPath() {
        this.mPathLength = new PathMeasure(this.mTimerPath, false).getLength();
        this.mTimerTimePaint.setColor(this.mTimerTimeColor);
        this.mTimerTimePaint.setStrokeWidth((float) this.mTimerStroke);
        if (!this.mManualtrack && this.mUpdateTimer == null) {
            this.mUpdateTimer = new C29831();
            this.mUpdateTimer.run();
            postDelayed(this.mUpdateTimer, 20);
        }
    }

    public void updatePath(float percentage) {
        if (percentage >= 0.0f) {
            this.mManualtrack = true;
            this.mPathLength = new PathMeasure(this.mTimerPath, false).getLength();
            this.mTimerTimePaint.setColor(this.mTimerTimeColor);
            this.mTimerTimePaint.setStrokeWidth((float) this.mTimerStroke);
            float[] fArr = new float[]{this.mPathLength, this.mPathLength};
            this.mTimerTimePaint.setPathEffect(new DashPathEffect(fArr, this.mPathLength * (1.0f - (percentage / 100.0f))));
            invalidate();
            return;
        }
        this.mManualtrack = false;
    }

    private Path makePillPath(float left, float top, float right, float bottom) {
        Path p = new Path();
        float r = bottom - top;
        float right1 = right - (r / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN);
        float left1 = left + (r / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN);
        p.moveTo((right + left) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, top);
        p.lineTo(right1, top);
        RectF oval = new RectF(right - r, top, right, bottom);
        p.arcTo(oval, 270.0f, 180.0f);
        p.lineTo(left1, bottom);
        oval.set(left, top, left + r, bottom);
        p.arcTo(oval, 90.0f, 180.0f);
        p.lineTo(right1, top);
        return p;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int iw = (w - getPaddingRight()) - getPaddingLeft();
        int ih = (h - getPaddingBottom()) - getPaddingTop();
        if (this.mLastWidth != iw || this.mLastHeight != ih) {
            this.mLastWidth = iw;
            this.mLastHeight = ih;
            padAndPaint(w, h);
            postInvalidate();
            requestLayout();
        }
    }

    public PillButton setTimeoutMilliSec(int timeoutMilliSec) {
        this.mTimeout = timeoutMilliSec;
        return this;
    }

    public void startTimer() {
        if (this.mDrawTimer) {
            this.mTimerStart = System.currentTimeMillis();
            if (this.mTimerPath != null) {
                drawTimerPath();
            }
        }
    }

    public void stopTimer() {
        this.mTimerStart = 0;
        invalidate();
    }

    public boolean didTimeOut() {
        if (this.mTimerStart != 0 && System.currentTimeMillis() > ((long) this.mTimeout) + this.mTimerStart) {
            return true;
        }
        return false;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mTimerStart > 0 || this.mManualtrack) {
            canvas.drawPath(this.mTimerPath, this.mTimerTrackPaint);
            canvas.drawPath(this.mTimerPath, this.mTimerTimePaint);
            if (this.mUpdateTimer != null) {
                removeCallbacks(this.mUpdateTimer);
                postDelayed(this.mUpdateTimer, 20);
            }
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        boolean pressed = false;
        for (int s : getDrawableState()) {
            if (s == 16842919) {
                pressed = true;
                break;
            }
        }
        ScaleAnimation sa;
        if (pressed) {
            this.mbPressed = true;
            sa = new ScaleAnimation(1.0f, PRESSED_SCALE_DOWN, 1.0f, PRESSED_SCALE_DOWN, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            sa.setDuration(100);
            sa.setInterpolator(new DecelerateInterpolator());
            sa.setFillAfter(true);
            startAnimation(sa);
        } else if (this.mbPressed) {
            this.mbPressed = false;
            sa = new ScaleAnimation(PRESSED_SCALE_DOWN, 1.0f, PRESSED_SCALE_DOWN, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            sa.setDuration(100);
            sa.setInterpolator(new DecelerateInterpolator());
            startAnimation(sa);
        }
    }

    public void setColor(int colorId) {
        this.mBgColor = getResources().getColor(colorId);
        createBg(this.mBmpWidth, this.mBmpHeight);
        invalidate();
    }
}
