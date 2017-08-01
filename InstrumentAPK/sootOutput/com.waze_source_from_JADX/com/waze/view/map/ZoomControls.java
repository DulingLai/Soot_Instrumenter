package com.waze.view.map;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import com.waze.C1283R;
import com.waze.map.CanvasFont;
import com.waze.navigate.NavigateNativeManager;
import com.waze.utils.PixelMeasure;

public class ZoomControls extends FrameLayout {
    private static final int ANIMATION_STEP = 20;
    private static final int ANIMATION_TIME = 300;
    private static final int DEAD_TOUCH_DISTANCE_DP = 15;
    private static final int GROW_SIZE_DP = 30;
    private static final int HOLD_STEP = 20;
    private static final int HOLD_TIME_MS = ViewConfiguration.getTapTimeout();
    private static final int MAX_TOUCH_DISTANCE_DP = 50;
    private static final int RETURN_DELAY = 1000;
    private static final int SYMBOL_MAX_MOVE_DP = 5;
    private static final float SYMBOL_MAX_SCALE = 0.1f;
    private LayoutInflater inflater;
    private float mDistance;
    private Interpolator mGrowInterpolator = new AccelerateDecelerateInterpolator();
    int mHeight;
    private Runnable mHoldRunnable = new C30162();
    boolean mIsDelayed;
    private boolean mIsDragging;
    boolean mIsTouched;
    View mMinus;
    View mPlus;
    Runnable mResizeRunnable = new C30151();
    private Runnable mReturnRunnable = new C30173();
    View mSpace;
    private long mTouchTime = 0;

    class C30151 implements Runnable {
        C30151() {
        }

        public void run() {
            LayoutParams p = (LayoutParams) ZoomControls.this.mSpace.getLayoutParams();
            int targetHeight = PixelMeasure.dp(30);
            float curStep;
            if (ZoomControls.this.mIsTouched || ZoomControls.this.mIsDelayed) {
                if (p.height < targetHeight) {
                    curStep = ((float) (System.currentTimeMillis() - ZoomControls.this.mTouchTime)) / 300.0f;
                    if (curStep > 1.0f) {
                        curStep = 1.0f;
                    }
                    p.height = Math.round(((float) targetHeight) * ZoomControls.this.mGrowInterpolator.getInterpolation(curStep));
                } else {
                    return;
                }
            } else if (p.height > 0) {
                curStep = ((float) (System.currentTimeMillis() - ZoomControls.this.mTouchTime)) / 300.0f;
                if (curStep > 1.0f) {
                    curStep = 1.0f;
                }
                p.height = Math.round(((float) targetHeight) * (1.0f - ZoomControls.this.mGrowInterpolator.getInterpolation(curStep)));
            } else {
                return;
            }
            ZoomControls.this.mSpace.setLayoutParams(p);
            ZoomControls.this.postDelayed(this, 20);
        }
    }

    class C30162 implements Runnable {
        C30162() {
        }

        public void run() {
            if (ZoomControls.this.mIsTouched) {
                int deadDistance = PixelMeasure.dp(15);
                float rate = 0.0f;
                if (ZoomControls.this.mDistance < ((float) (-deadDistance))) {
                    rate = ZoomControls.this.mDistance + ((float) deadDistance);
                }
                if (ZoomControls.this.mDistance > ((float) deadDistance)) {
                    rate = ZoomControls.this.mDistance - ((float) deadDistance);
                }
                NavigateNativeManager.instance().zoomHold(rate / 6.0f);
                ZoomControls.this.mIsDragging = true;
                ZoomControls.this.postDelayed(this, 20);
            }
        }
    }

    class C30173 implements Runnable {
        C30173() {
        }

        public void run() {
            ZoomControls.this.mIsDelayed = false;
            ZoomControls.this.removeCallbacks(ZoomControls.this.mResizeRunnable);
            ZoomControls.this.postDelayed(ZoomControls.this.mResizeRunnable, 20);
        }
    }

    public ZoomControls(Context context) {
        super(context);
        init(context);
    }

    public ZoomControls(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZoomControls(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(21)
    public ZoomControls(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.inflater.inflate(C1283R.layout.zoom_controls, this);
        this.mSpace = findViewById(C1283R.id.zoomControlSpace);
        this.mPlus = findViewById(C1283R.id.zoomControlIn);
        this.mMinus = findViewById(C1283R.id.zoomControlOut);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mHeight = h;
    }

    public boolean onTouchEvent(MotionEvent event) {
        NavigateNativeManager nnm = NavigateNativeManager.instance();
        long curTime = System.currentTimeMillis();
        int action = event.getAction();
        if (action == 0) {
            this.mIsTouched = true;
            if (curTime - this.mTouchTime > ((long) HOLD_TIME_MS)) {
                this.mTouchTime = curTime;
                LayoutParams p = (LayoutParams) this.mSpace.getLayoutParams();
                int targetHeight = PixelMeasure.dp(30);
                if (p.height > 0) {
                    this.mTouchTime -= (long) ((p.height * 300) / targetHeight);
                }
            } else {
                this.mTouchTime = curTime;
            }
            removeCallbacks(this.mResizeRunnable);
            removeCallbacks(this.mHoldRunnable);
            removeCallbacks(this.mReturnRunnable);
            postDelayed(this.mResizeRunnable, 20);
            postDelayed(this.mHoldRunnable, (long) HOLD_TIME_MS);
        }
        if (action == 0 || action == 2) {
            this.mDistance = ((float) (this.mHeight / 2)) - event.getY();
            int maxTouchDistance = PixelMeasure.dp(50);
            int maxMove = PixelMeasure.dp(5);
            if (this.mDistance > ((float) maxTouchDistance)) {
                this.mDistance = (float) maxTouchDistance;
            }
            if (this.mDistance < ((float) (-maxTouchDistance))) {
                this.mDistance = (float) (-maxTouchDistance);
            }
            float move = (((float) maxMove) * this.mDistance) / ((float) maxTouchDistance);
            Animation translateAnimation = new TranslateAnimation(0.0f, 0.0f, -move, -move);
            float scale = 1.0f + (SYMBOL_MAX_SCALE * Math.abs(this.mDistance / ((float) maxTouchDistance)));
            ScaleAnimation sa = new ScaleAnimation(scale, scale, scale, scale, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            AnimationSet as = new AnimationSet(true);
            as.addAnimation(translateAnimation);
            as.addAnimation(sa);
            as.setFillAfter(true);
            if (this.mDistance > 0.0f) {
                this.mPlus.startAnimation(as);
                this.mMinus.clearAnimation();
            } else {
                this.mMinus.startAnimation(as);
                this.mPlus.clearAnimation();
            }
        }
        if (action == 1) {
            this.mIsTouched = false;
            this.mIsDelayed = true;
            this.mPlus.clearAnimation();
            this.mMinus.clearAnimation();
            if (curTime - this.mTouchTime < ((long) HOLD_TIME_MS)) {
                int deadDistance = PixelMeasure.dp(15);
                if (this.mDistance < ((float) (-deadDistance))) {
                    nnm.zoomOutTap();
                }
                if (this.mDistance > ((float) deadDistance)) {
                    nnm.zoomInTap();
                }
            }
            this.mTouchTime = curTime;
            this.mTouchTime += 1000;
            removeCallbacks(this.mHoldRunnable);
            removeCallbacks(this.mReturnRunnable);
            if (this.mIsDragging) {
                nnm.zoomHold(0.0f);
                this.mIsDragging = false;
            }
            postDelayed(this.mReturnRunnable, 1000);
        }
        return true;
    }
}
