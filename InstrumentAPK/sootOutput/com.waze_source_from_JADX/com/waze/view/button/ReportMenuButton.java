package com.waze.view.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import com.waze.R;
import com.waze.utils.ImageUtils;
import com.waze.view.drawables.ReportButtonDrawable;

public class ReportMenuButton extends View {
    private Runnable mAnimationRunnable;
    private int mBgColor = -16777216;
    private boolean mCancelAlphaChanges;
    private float mCircleSize = -1.0f;
    private float mCircleStroke = -1.0f;
    private boolean mDrawShadow = true;
    private Bitmap mImageBmp = null;
    private int mImageRes = 0;
    private float mImageSize = -1.0f;
    private ReportButtonDrawable mReportButtonDrawable;
    private float mShadowOffset = -1.0f;

    public interface IAnimationListener {
        void onAnimationDone();
    }

    class C29841 implements Runnable {
        C29841() {
        }

        public void run() {
            ReportMenuButton.this.mReportButtonDrawable.setLevel(10000);
            ReportMenuButton.this.mReportButtonDrawable.setIsClosing(false);
            ReportMenuButton.this.invalidate();
        }
    }

    public ReportMenuButton(Context context) {
        super(context);
        init(context);
    }

    public ReportMenuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.ReportMenuButton);
        this.mBgColor = attrArray.getColor(1, -16777216);
        this.mImageRes = attrArray.getResourceId(0, 0);
        this.mCircleSize = attrArray.getDimension(2, -1.0f);
        this.mCircleStroke = attrArray.getDimension(3, -1.0f);
        this.mShadowOffset = attrArray.getDimension(4, -1.0f);
        this.mDrawShadow = attrArray.getBoolean(5, true);
        this.mImageSize = attrArray.getDimension(6, -1.0f);
        attrArray.recycle();
        init(context);
    }

    public ReportMenuButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.ReportMenuButton);
        this.mBgColor = attrArray.getColor(1, -16777216);
        this.mImageRes = attrArray.getResourceId(0, 0);
        this.mCircleSize = attrArray.getDimension(2, -1.0f);
        this.mCircleStroke = attrArray.getDimension(3, -1.0f);
        this.mShadowOffset = attrArray.getDimension(4, -1.0f);
        this.mDrawShadow = attrArray.getBoolean(5, true);
        this.mImageSize = attrArray.getDimension(6, -1.0f);
        attrArray.recycle();
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
            this.mReportButtonDrawable = new ReportButtonDrawable(context);
            this.mReportButtonDrawable.setBackgroundColor(this.mBgColor);
            this.mReportButtonDrawable.setCircleSize(this.mCircleSize);
            this.mReportButtonDrawable.setCircleStroke(this.mCircleStroke);
            this.mReportButtonDrawable.setShadowOffset(this.mShadowOffset);
            this.mReportButtonDrawable.setDrawShadow(this.mDrawShadow);
            if (VERSION.SDK_INT >= 16) {
                setBackground(this.mReportButtonDrawable);
            } else {
                setBackgroundDrawable(this.mReportButtonDrawable);
            }
            if (this.mImageRes != 0) {
                this.mImageBmp = BitmapFactory.decodeResource(getResources(), this.mImageRes);
                if (this.mImageSize > 0.0f) {
                    this.mImageBmp = Bitmap.createScaledBitmap(this.mImageBmp, (int) this.mImageSize, (int) this.mImageSize, false);
                }
                this.mReportButtonDrawable.setImageBitmap(this.mImageBmp);
            }
        }
    }

    public void setImageResource(int resId) {
        this.mImageRes = resId;
        if (this.mImageRes != 0) {
            this.mImageBmp = BitmapFactory.decodeResource(getResources(), this.mImageRes);
            if (this.mImageSize > 0.0f) {
                Bitmap b = this.mImageBmp;
                Matrix m = new Matrix();
                m.setRectToRect(new RectF(0.0f, 0.0f, (float) b.getWidth(), (float) b.getHeight()), new RectF(0.0f, 0.0f, this.mImageSize, this.mImageSize), ScaleToFit.CENTER);
                this.mImageBmp = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
            }
            this.mReportButtonDrawable.setImageBitmap(this.mImageBmp);
            invalidate();
            return;
        }
        this.mImageBmp = null;
    }

    public void skipAnimation() {
        post(new C29841());
    }

    public void setImageDrawable(Drawable d) {
        this.mImageRes = 0;
        if (d != null) {
            this.mImageBmp = ImageUtils.drawableToBitmap(d);
            if (this.mImageSize > 0.0f) {
                this.mImageBmp = Bitmap.createScaledBitmap(this.mImageBmp, (int) this.mImageSize, (int) this.mImageSize, false);
            }
            this.mReportButtonDrawable.setImageBitmap(this.mImageBmp);
            invalidate();
            return;
        }
        this.mImageBmp = null;
    }

    public void setBackgroundColor(int color) {
        this.mBgColor = color;
        this.mReportButtonDrawable.setBackgroundColor(this.mBgColor);
    }

    public void startAnimation(Animation animation) {
        super.startAnimation(animation);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void startOpenAnimation(int offsetMs, final int durationMs, final IAnimationListener l) {
        setVisibility(0);
        if (this.mAnimationRunnable != null) {
            removeCallbacks(this.mAnimationRunnable);
        }
        this.mReportButtonDrawable.cancelAnimation();
        this.mReportButtonDrawable.setIsClosing(false);
        this.mAnimationRunnable = new Runnable() {

            class C29851 implements Runnable {
                C29851() {
                }

                public void run() {
                    if (l != null) {
                        l.onAnimationDone();
                    }
                }
            }

            public void run() {
                ReportMenuButton.this.mReportButtonDrawable.animateButton((long) durationMs);
                ReportMenuButton.this.postDelayed(new C29851(), (long) (durationMs * 2));
            }
        };
        postDelayed(this.mAnimationRunnable, (long) offsetMs);
        invalidate();
    }

    public void setCancelAlphaChanges(boolean cancelAlphaChanges) {
        this.mCancelAlphaChanges = cancelAlphaChanges;
    }

    public void setAlpha(float alpha) {
        if (!this.mCancelAlphaChanges) {
            super.setAlpha(alpha);
        }
    }

    public void startCloseAnimation(int offsetMs, final int durationMs, final IAnimationListener l) {
        if (this.mAnimationRunnable != null) {
            removeCallbacks(this.mAnimationRunnable);
        }
        this.mReportButtonDrawable.cancelAnimation();
        this.mReportButtonDrawable.setIsClosing(true);
        this.mReportButtonDrawable.setLevel(0);
        this.mAnimationRunnable = new Runnable() {

            class C29871 implements Runnable {
                C29871() {
                }

                public void run() {
                    ReportMenuButton.this.setVisibility(4);
                    if (l != null) {
                        l.onAnimationDone();
                    }
                }
            }

            public void run() {
                ReportMenuButton.this.mReportButtonDrawable.animateButton((long) (durationMs / 2));
                ReportMenuButton.this.postDelayed(new C29871(), (long) durationMs);
            }
        };
        postDelayed(this.mAnimationRunnable, (long) offsetMs);
    }

    public int getBackgroundColor() {
        return this.mBgColor;
    }

    public int getImageResId() {
        return this.mImageRes;
    }

    public void setClosed() {
        this.mReportButtonDrawable.setIsClosing(false);
        this.mReportButtonDrawable.setLevel(10000);
        setVisibility(4);
        invalidate();
    }

    public void setOpen() {
        this.mReportButtonDrawable.setIsClosing(false);
        this.mReportButtonDrawable.setLevel(10000);
        setVisibility(0);
        invalidate();
    }
}
