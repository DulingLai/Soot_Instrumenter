package com.waze.view.drawables;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.map.CanvasFont;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.EasingInterpolators;

public class ReportButtonDrawable extends AnimatingDrawable {
    private static final float BITMAP_ANIM_DELAY_PERCENT = 0.2f;
    private static final float BITMAP_ANIM_DURATION_PERCENT = 0.8f;
    private static final Interpolator BITMAP_CLOSE_INTERPOLATOR = new AnticipateInterpolator(0.95f);
    private static final Interpolator BITMAP_INTERPOLATOR = new DecelerateInterpolator(INNER_CIRCLE_ANIM_DURATION_PERCENT);
    private static final int CIRCLE_COLOR = -1;
    private static final int CIRCLE_SIZE_DP = 86;
    private static final int CIRCLE_STROKE_DP = 5;
    private static final float INNER_CIRCLE_ANIM_DELAY_PERCENT = 0.1f;
    private static final float INNER_CIRCLE_ANIM_DURATION_PERCENT = 0.85f;
    private static final float OUTER_CIRCLE_ANIM_DURATION_PERCENT = 0.95f;
    public static final float OVERSHOOT_FACTOR_INNER = 1.04f;
    public static final float OVERSHOOT_FACTOR_OUTER = 1.08f;
    private static final int SHADOW_OFFSET_DP = 5;
    private static final Interpolator STANDARD_CLOSE_INTERPOLATOR = new AccelerateInterpolator();
    private static final Interpolator STANDARD_INTERPOLATOR = new DecelerateInterpolator(1.2f);
    private static Bitmap mShadowBmp = null;
    private int mBgColor = -16777216;
    private Paint mBitmapPaint;
    private float mCircleSize = -1.0f;
    private float mCircleStroke = -1.0f;
    private Path mClipPath;
    private Context mContext;
    private float mCurImageOffset;
    private float mCurInnerCircleRadius;
    private float mCurOuterCircleRadius;
    private boolean mDrawShadow = true;
    private float mFinalInnerCircleRadius;
    private float mFinalOuterCircleRadius;
    private Bitmap mImageBmp = null;
    private Paint mInnerCirclePaint;
    private boolean mIsClosing;
    private Paint mOuterCirclePaint;
    private RectF mShadowBmpRect = new RectF();
    private float mShadowOffset = -1.0f;
    private Paint mShadowPaint;

    public ReportButtonDrawable(Context context) {
        this.mContext = context;
        this.mOuterCirclePaint = new Paint(1);
        this.mOuterCirclePaint.setStyle(Style.STROKE);
        this.mOuterCirclePaint.setColor(-1);
        this.mInnerCirclePaint = new Paint(1);
        this.mInnerCirclePaint.setStyle(Style.FILL_AND_STROKE);
        this.mBitmapPaint = new Paint(1);
        this.mShadowPaint = new Paint(1);
        this.mShadowPaint.setAlpha(0);
        this.mClipPath = new Path();
        if (mShadowBmp == null) {
            mShadowBmp = BitmapFactory.decodeResource(this.mContext.getResources(), C1283R.drawable.report_btn_shadow);
        }
    }

    public void setBackgroundColor(int backgroundColor) {
        this.mBgColor = backgroundColor;
        this.mInnerCirclePaint.setColor(this.mBgColor);
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.mImageBmp = imageBitmap;
    }

    public void setDrawShadow(boolean drawShadow) {
        this.mDrawShadow = drawShadow;
    }

    public void setCircleSize(float circleSize) {
        this.mCircleSize = circleSize;
        if (this.mCircleSize < 0.0f) {
            this.mCircleSize = (float) PixelMeasure.dp(86);
        }
    }

    public void setCircleStroke(float circleStroke) {
        this.mCircleStroke = circleStroke;
        if (this.mCircleStroke < 0.0f) {
            this.mCircleStroke = (float) PixelMeasure.dp(5);
        }
    }

    public void setShadowOffset(float shadowOffset) {
        this.mShadowOffset = shadowOffset;
        if (this.mShadowOffset < 0.0f) {
            this.mShadowOffset = (float) PixelMeasure.dp(5);
        }
    }

    public void setIsClosing(boolean isClosing) {
        this.mIsClosing = isClosing;
    }

    public void animateButton(long duration) {
        if (this.mImageBmp != null) {
            setupFinalValues();
            animate(0, 10000, duration * 2, null);
        }
    }

    private void setupFinalValues() {
        this.mFinalOuterCircleRadius = (this.mCircleSize / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) - (this.mCircleStroke / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN);
        this.mFinalInnerCircleRadius = this.mFinalOuterCircleRadius - (this.mCircleStroke / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN);
        float shadowRadius = this.mFinalOuterCircleRadius * 1.3f;
        Rect bounds = getBounds();
        this.mShadowBmpRect.left = ((float) bounds.centerX()) - shadowRadius;
        this.mShadowBmpRect.top = (((float) bounds.centerY()) - shadowRadius) + this.mShadowOffset;
        this.mShadowBmpRect.right = ((float) bounds.centerX()) + shadowRadius;
        this.mShadowBmpRect.bottom = (((float) bounds.centerY()) + shadowRadius) + this.mShadowOffset;
    }

    public void draw(Canvas canvas) {
        float animationRatio = ((float) getLevel()) / 10000.0f;
        if (this.mFinalOuterCircleRadius == 0.0f) {
            setupFinalValues();
        }
        if (this.mIsClosing) {
            setClosingPhaseValues(animationRatio);
        } else if (animationRatio <= CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) {
            setFirstPhaseValues(animationRatio / CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        } else {
            setSecondPhaseValues((animationRatio - CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) / CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        }
        drawInternal(canvas);
    }

    private void drawInternal(Canvas canvas) {
        Rect bounds = getBounds();
        if (this.mDrawShadow && this.mCurOuterCircleRadius > 0.0f) {
            canvas.drawBitmap(mShadowBmp, null, this.mShadowBmpRect, this.mShadowPaint);
        }
        float realOuterCircleRadius = (this.mCurOuterCircleRadius + this.mCurInnerCircleRadius) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
        if (this.mCurInnerCircleRadius > 0.0f) {
            canvas.drawCircle((float) bounds.centerX(), (float) bounds.centerY(), this.mCurInnerCircleRadius, this.mInnerCirclePaint);
            if (this.mImageBmp != null) {
                int bitmapLeft = bounds.centerX() - (this.mImageBmp.getWidth() / 2);
                int bitmapTop = bounds.centerY() - (this.mImageBmp.getHeight() / 2);
                this.mClipPath.reset();
                this.mClipPath.addCircle((float) bounds.centerX(), (float) bounds.centerY(), realOuterCircleRadius, Direction.CW);
                canvas.save();
                canvas.clipPath(this.mClipPath);
                canvas.drawBitmap(this.mImageBmp, (float) bitmapLeft, ((float) bitmapTop) + this.mCurImageOffset, this.mBitmapPaint);
                canvas.restore();
            }
        }
        if (this.mCurOuterCircleRadius > 0.0f) {
            this.mOuterCirclePaint.setStrokeWidth((this.mCurOuterCircleRadius - this.mCurInnerCircleRadius) * LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN);
            canvas.drawCircle((float) bounds.centerX(), (float) bounds.centerY(), realOuterCircleRadius, this.mOuterCirclePaint);
        }
    }

    private void setFirstPhaseValues(float ratio) {
        this.mShadowPaint.setAlpha((int) (255.0f * ratio));
        float innerRatio = ratioWithDelayAndDuration(ratio, INNER_CIRCLE_ANIM_DELAY_PERCENT, INNER_CIRCLE_ANIM_DURATION_PERCENT);
        float outerRatio = ratioWithDelayAndDuration(ratio, 0.0f, 0.95f);
        float bitmapRatio = ratioWithDelayAndDuration(ratio, BITMAP_ANIM_DELAY_PERCENT, 0.8f);
        this.mCurInnerCircleRadius = (STANDARD_INTERPOLATOR.getInterpolation(innerRatio) * this.mFinalInnerCircleRadius) * OVERSHOOT_FACTOR_INNER;
        this.mCurOuterCircleRadius = (STANDARD_INTERPOLATOR.getInterpolation(outerRatio) * this.mFinalOuterCircleRadius) * OVERSHOOT_FACTOR_OUTER;
        this.mCurImageOffset = ((float) this.mImageBmp.getHeight()) - ((BITMAP_INTERPOLATOR.getInterpolation(bitmapRatio) * ((float) this.mImageBmp.getHeight())) * LayoutManager.REPORT_SCALE_ON_PRESS);
    }

    private void setSecondPhaseValues(float ratio) {
        this.mCurInnerCircleRadius = this.mFinalInnerCircleRadius + ((EasingInterpolators.EASE_IN_EASE_OUT.getInterpolation(1.0f - ratio) * this.mFinalInnerCircleRadius) * 0.03999996f);
        this.mCurOuterCircleRadius = this.mFinalOuterCircleRadius + ((EasingInterpolators.EASE_IN_EASE_OUT.getInterpolation(1.0f - ratio) * this.mFinalOuterCircleRadius) * 0.08000004f);
        this.mCurImageOffset = (EasingInterpolators.EASE_IN_EASE_OUT.getInterpolation(1.0f - ratio) * ((float) (-this.mImageBmp.getHeight()))) * 0.05f;
    }

    private void setClosingPhaseValues(float ratio) {
        this.mShadowPaint.setAlpha((int) (255.0f * limit(1.0f - (LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN * ratio))));
        float innerRatio = ratioWithDelayAndDuration(ratio, 0.049999975f, INNER_CIRCLE_ANIM_DURATION_PERCENT);
        float outerRatio = ratioWithDelayAndDuration(ratio, 0.050000012f, 0.95f);
        float bitmapRatio = ratioWithDelayAndDuration(ratio, 0.0f, 0.8f);
        this.mCurInnerCircleRadius = STANDARD_CLOSE_INTERPOLATOR.getInterpolation(1.0f - innerRatio) * this.mFinalInnerCircleRadius;
        this.mCurOuterCircleRadius = STANDARD_CLOSE_INTERPOLATOR.getInterpolation(1.0f - outerRatio) * this.mFinalOuterCircleRadius;
        this.mCurImageOffset = BITMAP_CLOSE_INTERPOLATOR.getInterpolation(bitmapRatio) * ((float) this.mImageBmp.getHeight());
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public int getOpacity() {
        return 0;
    }

    private static float ratioWithDelayAndDuration(float ratio, float delay, float duration) {
        if (duration - delay > 0.0f) {
            return limit((ratio - delay) / (duration - delay));
        }
        Log.w("ReportButtonDrawable", "Request to generate ratio with invalid duration - delay = " + (duration - delay));
        return 0.0f;
    }

    private static float limit(float value) {
        return Math.min(Math.max(value, 0.0f), 1.0f);
    }
}
