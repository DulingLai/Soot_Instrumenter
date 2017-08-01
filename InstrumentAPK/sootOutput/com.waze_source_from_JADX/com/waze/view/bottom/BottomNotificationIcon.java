package com.waze.view.bottom;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.LayoutManager;

public class BottomNotificationIcon extends View {
    private static final int ICON_ANIM_DELAY_PERCENT = 40;
    private static final int ICON_ANIM_DURATION_PERCENT = 60;
    private static final int INNER_HEX_ANIM_DELAY_PERCENT = 0;
    private static final int INNER_HEX_ANIM_DURATION_PERCENT = 100;
    private static final int OUTER_HEX_ANIM_DURATION_PERCENT = 100;
    public static final int RADAR_DURATION_SEC = 2000;
    boolean mAlphaAnimateIcon = false;
    private IAnimationListener mAnimationListener = null;
    MyWorkerThread mAnimatorThread;
    private Bitmap mBackBmp = null;
    private RectF mBackRect = new RectF();
    private ValueAnimator mBackSizeValueAnimator;
    private Paint mBitmapPaint;
    Path mClipPath = new Path();
    boolean mDrawRadar = false;
    private Bitmap mFrontBmp = null;
    private RectF mFrontRect = new RectF();
    private ValueAnimator mFrontSizeValueAnimator;
    private ValueAnimator mHexOffsetValueAnimator;
    float mIconAlpha = 1.0f;
    private Bitmap mIconBmp = null;
    private ValueAnimator mIconOffsetValueAnimator;
    private RectF mIconRect = new RectF();
    boolean mIsOpen = false;
    private Bitmap mMyBmp = null;
    private Canvas mMyCanvas = null;
    private ObjectAnimator mObjectAnimator;
    private Paint mOnTopPaint;
    private Bitmap mRadarBmp = null;
    Matrix mRadarMatrix = new Matrix();
    private ObjectAnimator mRadarRotator;
    private Bitmap mShadowBmp = null;
    private RectF mShadowRect = new RectF();
    boolean mShouldDrawRadar = false;
    private float mTopMargin;

    class C29751 implements AnimatorListener {
        C29751() {
        }

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationRepeat(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
            if (BottomNotificationIcon.this.mAnimationListener != null) {
                BottomNotificationIcon.this.mAnimationListener.onAnimationDone();
            }
            BottomNotificationIcon.this.mDrawRadar = BottomNotificationIcon.this.mShouldDrawRadar;
            BottomNotificationIcon.this.postInvalidate();
        }

        public void onAnimationCancel(Animator animation) {
        }
    }

    class C29762 implements Runnable {
        C29762() {
        }

        public void run() {
            BottomNotificationIcon.this.mObjectAnimator.start();
        }
    }

    class C29773 implements Runnable {
        C29773() {
        }

        public void run() {
            BottomNotificationIcon.this.mRadarRotator.start();
        }
    }

    class C29794 implements AnimatorListener {

        class C29781 implements Runnable {
            C29781() {
            }

            public void run() {
                BottomNotificationIcon.this.setVisibility(8);
            }
        }

        C29794() {
        }

        public void onAnimationStart(Animator animation) {
            if (BottomNotificationIcon.this.mRadarRotator != null) {
                BottomNotificationIcon.this.mRadarRotator.cancel();
            }
            BottomNotificationIcon.this.mDrawRadar = false;
        }

        public void onAnimationRepeat(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
            if (BottomNotificationIcon.this.mAnimationListener != null) {
                BottomNotificationIcon.this.mAnimationListener.onAnimationDone();
            }
            if (BottomNotificationIcon.this.mRadarRotator != null) {
                BottomNotificationIcon.this.mRadarRotator.end();
            }
            BottomNotificationIcon.this.post(new C29781());
        }

        public void onAnimationCancel(Animator animation) {
        }
    }

    public interface IAnimationListener {
        void onAnimationDone();
    }

    private static class MyWorkerThread extends HandlerThread {
        private Handler mWorkerHandler;

        public MyWorkerThread(String name) {
            super(name);
        }

        public void postTask(Runnable task) {
            if (this.mWorkerHandler != null) {
                this.mWorkerHandler.post(task);
            }
        }

        public void prepareHandler() {
            this.mWorkerHandler = new Handler(getLooper());
        }

        public boolean quit() {
            this.mWorkerHandler = null;
            return super.quit();
        }
    }

    public BottomNotificationIcon(Context context) {
        super(context);
        init(context);
    }

    public BottomNotificationIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottomNotificationIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mBitmapPaint = new Paint(1);
        this.mOnTopPaint = new Paint();
        this.mOnTopPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_ATOP));
        this.mOnTopPaint.setAntiAlias(true);
        this.mTopMargin = getResources().getDisplayMetrics().density * 10.0f;
        this.mRadarBmp = BitmapFactory.decodeResource(getResources(), C1283R.drawable.bottom_notification_radar);
        if (isInEditMode()) {
            setImageResources(C1283R.drawable.bottom_message_reports_back, C1283R.drawable.bottom_message_reports_shadow, C1283R.drawable.bottom_message_reports_front);
            setOpen();
        }
        this.mAnimatorThread = new MyWorkerThread("BottomNotificationIcon Animator");
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAnimatorThread.start();
        this.mAnimatorThread.prepareHandler();
    }

    protected void onDetachedFromWindow() {
        this.mAnimatorThread.quit();
        super.onDetachedFromWindow();
    }

    public void setImageResources(int idBack, int idShadow, int idIcon) {
        this.mBackBmp = BitmapFactory.decodeResource(getResources(), C1283R.drawable.points_banner_white_polygon);
        this.mFrontBmp = BitmapFactory.decodeResource(getResources(), idBack);
        this.mShadowBmp = BitmapFactory.decodeResource(getResources(), idShadow);
        this.mIconBmp = BitmapFactory.decodeResource(getResources(), idIcon);
        this.mShouldDrawRadar = true;
        this.mAlphaAnimateIcon = false;
        this.mBackRect.set(0.0f, 0.0f, 0.0f, 0.0f);
        this.mFrontRect.set(0.0f, 0.0f, 0.0f, 0.0f);
        this.mShadowRect.set(0.0f, 0.0f, 0.0f, 0.0f);
        this.mIconRect.set(0.0f, 0.0f, 0.0f, 0.0f);
        requestLayout();
    }

    public void setImageResourcesAndPoints(Context ctx, int idBack, int points) {
        this.mBackBmp = BitmapFactory.decodeResource(getResources(), C1283R.drawable.points_banner_white_polygon);
        this.mFrontBmp = BitmapFactory.decodeResource(getResources(), idBack);
        this.mShadowBmp = null;
        this.mShouldDrawRadar = false;
        this.mAlphaAnimateIcon = true;
        if (VERSION.SDK_INT < 17) {
            this.mIconBmp = Bitmap.createBitmap(this.mFrontBmp.getWidth(), this.mFrontBmp.getHeight(), this.mFrontBmp.getConfig());
            this.mIconBmp.setDensity(ctx.getResources().getDisplayMetrics().densityDpi);
        } else {
            this.mIconBmp = Bitmap.createBitmap(ctx.getResources().getDisplayMetrics(), this.mFrontBmp.getWidth(), this.mFrontBmp.getHeight(), this.mFrontBmp.getConfig());
        }
        View layout = LayoutInflater.from(ctx).inflate(C1283R.layout.bottom_bar_notification_points_container, null);
        ((TextView) layout.findViewById(C1283R.id.mainBottomBarNotificationPoints)).setText(String.valueOf(points));
        layout.measure(MeasureSpec.makeMeasureSpec(this.mFrontBmp.getWidth(), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(this.mFrontBmp.getHeight(), Integer.MIN_VALUE));
        layout.layout(0, 0, this.mFrontBmp.getWidth(), this.mFrontBmp.getHeight());
        layout.draw(new Canvas(this.mIconBmp));
        this.mBackRect.set(0.0f, 0.0f, 0.0f, 0.0f);
        this.mFrontRect.set(0.0f, 0.0f, 0.0f, 0.0f);
        this.mShadowRect.set(0.0f, 0.0f, 0.0f, 0.0f);
        this.mIconRect.set(0.0f, 0.0f, 0.0f, 0.0f);
        requestLayout();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mBackBmp != null) {
            setMeasuredDimension(this.mBackBmp.getWidth(), this.mBackBmp.getHeight() * 2);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            if (VERSION.SDK_INT < 17) {
                this.mMyBmp = Bitmap.createBitmap(w, h, this.mBackBmp.getConfig());
                this.mMyBmp.setDensity(getContext().getResources().getDisplayMetrics().densityDpi);
            } else {
                this.mMyBmp = Bitmap.createBitmap(getContext().getResources().getDisplayMetrics(), w, h, this.mBackBmp.getConfig());
            }
            this.mMyCanvas = new Canvas(this.mMyBmp);
            if (this.mBackBmp != null) {
                drawToCanvas(this.mMyCanvas);
            }
        }
    }

    public void setOpenAnimationStep(float f) {
        float offSetVal = 0.0f;
        if (this.mHexOffsetValueAnimator.isRunning()) {
            offSetVal = 1.0f - ((Float) this.mHexOffsetValueAnimator.getAnimatedValue()).floatValue();
        }
        if (this.mBackSizeValueAnimator.isRunning()) {
            setBitmapRect(this.mBackRect, this.mBackBmp, 1.0f - ((Float) this.mBackSizeValueAnimator.getAnimatedValue()).floatValue(), offSetVal);
        } else if (this.mBackSizeValueAnimator.getAnimatedFraction() == 1.0f) {
            setBitmapRect(this.mBackRect, this.mBackBmp, 0.0f, 0.0f);
        }
        if (this.mFrontSizeValueAnimator.isRunning()) {
            float val = ((Float) this.mFrontSizeValueAnimator.getAnimatedValue()).floatValue();
            this.mIconAlpha = val;
            setBitmapRect(this.mFrontRect, this.mFrontBmp, 1.0f - val, offSetVal);
            if (this.mShadowBmp != null) {
                setBitmapRect(this.mShadowRect, this.mShadowBmp, 1.0f - val, offSetVal);
            }
        } else if (this.mFrontSizeValueAnimator.getAnimatedFraction() == 1.0f) {
            this.mIconAlpha = 1.0f;
            setBitmapRect(this.mFrontRect, this.mFrontBmp, 0.0f, 0.0f);
            if (this.mShadowBmp != null) {
                setBitmapRect(this.mShadowRect, this.mShadowBmp, 0.0f, 0.0f);
            }
        }
        if (this.mIconOffsetValueAnimator.isRunning()) {
            setBitmapRect(this.mIconRect, this.mIconBmp, 0.0f, 1.0f - ((Float) this.mIconOffsetValueAnimator.getAnimatedValue()).floatValue());
        } else if (this.mIconOffsetValueAnimator.getAnimatedFraction() == 1.0f) {
            setBitmapRect(this.mIconRect, this.mIconBmp, 0.0f, 0.0f);
        }
        postInvalidate();
    }

    private void setBitmapRect(RectF rect, Bitmap bmp, float sizeVal, float offsetVal) {
        float height = (float) bmp.getHeight();
        float width = (float) bmp.getWidth();
        float yOffset = offsetVal * height;
        float scaleOffsetX = (sizeVal * width) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
        float scaleOffsetY = (sizeVal * height) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
        rect.top = (yOffset + scaleOffsetY) + this.mTopMargin;
        rect.left = scaleOffsetX;
        rect.right = width - scaleOffsetX;
        rect.bottom = ((height + yOffset) - scaleOffsetY) + this.mTopMargin;
    }

    public void setRotateRadarStep(float f) {
        this.mRadarMatrix.reset();
        this.mRadarMatrix.setRotate(360.0f * (f % 1.0f), (float) (this.mRadarBmp.getWidth() / 2), (float) (this.mRadarBmp.getHeight() / 2));
        this.mRadarMatrix.postTranslate(0.0f, this.mTopMargin);
        postInvalidate();
    }

    public void startOpenAnimation(int offsetMs, int durationMs, IAnimationListener l) {
        setVisibility(0);
        this.mIsOpen = true;
        this.mAnimationListener = l;
        this.mDrawRadar = false;
        this.mHexOffsetValueAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.mHexOffsetValueAnimator.setInterpolator(new OvershootInterpolator(1.0f));
        this.mHexOffsetValueAnimator.setDuration((long) ((durationMs * 100) / 100));
        this.mHexOffsetValueAnimator.setStartDelay((long) (((durationMs * 0) / 100) + offsetMs));
        this.mHexOffsetValueAnimator.start();
        this.mBackSizeValueAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.mBackSizeValueAnimator.setInterpolator(new OvershootInterpolator(1.65f));
        this.mBackSizeValueAnimator.setDuration((long) ((durationMs * 100) / 100));
        this.mBackSizeValueAnimator.setStartDelay((long) offsetMs);
        this.mBackSizeValueAnimator.start();
        this.mFrontSizeValueAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.mFrontSizeValueAnimator.setInterpolator(new DecelerateInterpolator(1.0f));
        this.mFrontSizeValueAnimator.setDuration((long) ((durationMs * 100) / 100));
        this.mFrontSizeValueAnimator.setStartDelay((long) (((durationMs * 0) / 100) + offsetMs));
        this.mFrontSizeValueAnimator.start();
        this.mIconOffsetValueAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.mIconOffsetValueAnimator.setInterpolator(new OvershootInterpolator(1.0f));
        this.mIconOffsetValueAnimator.setDuration((long) ((durationMs * 60) / 100));
        this.mIconOffsetValueAnimator.setStartDelay((long) (((durationMs * 40) / 100) + offsetMs));
        this.mIconOffsetValueAnimator.start();
        this.mObjectAnimator = ObjectAnimator.ofFloat(this, "openAnimationStep", new float[]{0.0f, 1.0f});
        this.mObjectAnimator.setInterpolator(new LinearInterpolator());
        this.mObjectAnimator.setDuration((long) (((double) durationMs) * 1.1d));
        this.mObjectAnimator.setStartDelay((long) offsetMs);
        this.mObjectAnimator.addListener(new C29751());
        this.mAnimatorThread.postTask(new C29762());
        if (this.mShouldDrawRadar) {
            this.mRadarRotator = ObjectAnimator.ofFloat(this, "rotateRadarStep", new float[]{0.0f, 2000.0f});
            this.mRadarRotator.setInterpolator(new LinearInterpolator());
            this.mRadarRotator.setDuration(2000000);
            this.mRadarRotator.setStartDelay((long) (durationMs + offsetMs));
            this.mAnimatorThread.postTask(new C29773());
        }
    }

    public void setCloseAnimationStep(float f) {
        float offSetVal = 1.0f;
        if (this.mHexOffsetValueAnimator.isRunning()) {
            offSetVal = ((Float) this.mHexOffsetValueAnimator.getAnimatedValue()).floatValue();
        }
        offSetVal /= 4.0f;
        if (this.mBackSizeValueAnimator.isRunning()) {
            setBitmapRect(this.mBackRect, this.mBackBmp, ((Float) this.mBackSizeValueAnimator.getAnimatedValue()).floatValue(), offSetVal);
        } else if (this.mBackSizeValueAnimator.getAnimatedFraction() == 1.0f) {
            setBitmapRect(this.mBackRect, this.mBackBmp, 1.0f, offSetVal);
        }
        if (this.mFrontSizeValueAnimator.isRunning()) {
            float val = ((Float) this.mFrontSizeValueAnimator.getAnimatedValue()).floatValue();
            setBitmapRect(this.mFrontRect, this.mFrontBmp, val, offSetVal);
            setBitmapRect(this.mIconRect, this.mIconBmp, val, offSetVal);
            if (this.mShadowBmp != null) {
                setBitmapRect(this.mShadowRect, this.mShadowBmp, val, offSetVal);
            }
        } else if (this.mFrontSizeValueAnimator.getAnimatedFraction() == 1.0f) {
            setBitmapRect(this.mFrontRect, this.mFrontBmp, 1.0f, offSetVal);
            setBitmapRect(this.mIconRect, this.mIconBmp, 1.0f, offSetVal);
            if (this.mShadowBmp != null) {
                setBitmapRect(this.mShadowRect, this.mShadowBmp, 1.0f, offSetVal);
            }
        }
        if (this.mIconOffsetValueAnimator.isRunning()) {
            this.mIconAlpha = 1.0f - ((Float) this.mIconOffsetValueAnimator.getAnimatedValue()).floatValue();
        } else if (this.mIconOffsetValueAnimator.getAnimatedFraction() == 1.0f) {
            this.mIconAlpha = 0.0f;
        }
        postInvalidate();
    }

    public void startCloseAnimation(int offsetMs, int durationMs, IAnimationListener l) {
        if (this.mIsOpen) {
            this.mIsOpen = false;
            this.mAnimationListener = l;
            if (this.mHexOffsetValueAnimator != null) {
                this.mHexOffsetValueAnimator.cancel();
            }
            if (this.mBackSizeValueAnimator != null) {
                this.mBackSizeValueAnimator.cancel();
            }
            if (this.mFrontSizeValueAnimator != null) {
                this.mFrontSizeValueAnimator.cancel();
            }
            if (this.mIconOffsetValueAnimator != null) {
                this.mIconOffsetValueAnimator.cancel();
            }
            if (this.mObjectAnimator != null) {
                this.mObjectAnimator.cancel();
            }
            if (this.mRadarRotator != null) {
                this.mRadarRotator.cancel();
            }
            this.mDrawRadar = false;
            this.mHexOffsetValueAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            this.mHexOffsetValueAnimator.setInterpolator(new AccelerateInterpolator(LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN));
            this.mHexOffsetValueAnimator.setDuration((long) ((durationMs * 100) / 100));
            this.mHexOffsetValueAnimator.setStartDelay((long) (((durationMs * 0) / 100) + offsetMs));
            this.mHexOffsetValueAnimator.start();
            this.mBackSizeValueAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            this.mBackSizeValueAnimator.setInterpolator(new AnticipateInterpolator(1.65f));
            this.mBackSizeValueAnimator.setDuration((long) ((durationMs * 100) / 100));
            this.mBackSizeValueAnimator.setStartDelay((long) (((durationMs * 0) / 100) + offsetMs));
            this.mBackSizeValueAnimator.start();
            this.mFrontSizeValueAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            this.mFrontSizeValueAnimator.setInterpolator(new AccelerateInterpolator(1.0f));
            this.mFrontSizeValueAnimator.setDuration((long) ((durationMs * 100) / 100));
            this.mFrontSizeValueAnimator.setStartDelay((long) (((durationMs * 0) / 100) + offsetMs));
            this.mFrontSizeValueAnimator.start();
            this.mIconOffsetValueAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            this.mIconOffsetValueAnimator.setInterpolator(new AccelerateInterpolator());
            this.mIconOffsetValueAnimator.setDuration((long) ((durationMs * 60) / 100));
            this.mIconOffsetValueAnimator.setStartDelay((long) (((durationMs * 0) / 100) + offsetMs));
            this.mIconOffsetValueAnimator.start();
            final ObjectAnimator a = ObjectAnimator.ofFloat(this, "closeAnimationStep", new float[]{0.0f, 1.0f});
            a.setInterpolator(new LinearInterpolator());
            a.setDuration((long) durationMs);
            a.setStartDelay((long) offsetMs);
            a.addListener(new C29794());
            this.mAnimatorThread.postTask(new Runnable() {
                public void run() {
                    a.start();
                }
            });
        }
    }

    public void invalidate() {
        if (this.mMyBmp != null) {
            this.mMyCanvas.drawColor(0, Mode.CLEAR);
            drawToCanvas(this.mMyCanvas);
            super.invalidate();
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(this.mMyBmp, 0.0f, 0.0f, this.mBitmapPaint);
    }

    private void drawToCanvas(Canvas canvas) {
        canvas.drawBitmap(this.mBackBmp, null, this.mBackRect, this.mBitmapPaint);
        canvas.drawBitmap(this.mFrontBmp, null, this.mFrontRect, this.mBitmapPaint);
        if (this.mDrawRadar) {
            canvas.drawBitmap(this.mRadarBmp, this.mRadarMatrix, this.mOnTopPaint);
        }
        if (this.mShadowBmp != null) {
            canvas.drawBitmap(this.mShadowBmp, null, this.mShadowRect, this.mBitmapPaint);
        }
        this.mClipPath.reset();
        this.mClipPath.addRect(this.mFrontRect, Direction.CW);
        canvas.save();
        canvas.clipPath(this.mClipPath);
        if (this.mAlphaAnimateIcon) {
            this.mOnTopPaint.setAlpha((int) (255.0f * this.mIconAlpha));
        }
        canvas.drawBitmap(this.mIconBmp, null, this.mIconRect, this.mOnTopPaint);
        if (this.mAlphaAnimateIcon) {
            this.mOnTopPaint.setAlpha(255);
        }
        canvas.restore();
    }

    public void setClosed() {
        this.mBackRect.set(0.0f, 0.0f, 0.0f, 0.0f);
        this.mFrontRect.set(0.0f, 0.0f, 0.0f, 0.0f);
        this.mShadowRect.set(0.0f, 0.0f, 0.0f, 0.0f);
        this.mIconRect.set(0.0f, 0.0f, 0.0f, 0.0f);
        this.mDrawRadar = false;
        setVisibility(8);
        invalidate();
    }

    public void setOpen() {
        this.mBackRect.set(0.0f, this.mTopMargin, (float) this.mBackBmp.getWidth(), ((float) this.mBackBmp.getHeight()) + this.mTopMargin);
        this.mFrontRect.set(0.0f, this.mTopMargin, (float) this.mFrontBmp.getWidth(), ((float) this.mFrontBmp.getHeight()) + this.mTopMargin);
        if (this.mShadowBmp != null) {
            this.mShadowRect.set(0.0f, this.mTopMargin, (float) this.mShadowBmp.getWidth(), ((float) this.mShadowBmp.getHeight()) + this.mTopMargin);
        }
        this.mIconRect.set(0.0f, this.mTopMargin, (float) this.mIconBmp.getWidth(), ((float) this.mIconBmp.getHeight()) + this.mTopMargin);
        setVisibility(0);
        invalidate();
    }
}
