package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.support.annotation.NonNull;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import com.waze.FriendsBarFragment;
import com.waze.LayoutManager;
import com.waze.map.CanvasFont;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

class MaterialProgressDrawable extends Drawable implements Animatable {
    private static final int ANIMATION_DURATION = 1332;
    private static final int ARROW_HEIGHT = 5;
    private static final int ARROW_HEIGHT_LARGE = 6;
    private static final float ARROW_OFFSET_ANGLE = 5.0f;
    private static final int ARROW_WIDTH = 10;
    private static final int ARROW_WIDTH_LARGE = 12;
    private static final float CENTER_RADIUS = 8.75f;
    private static final float CENTER_RADIUS_LARGE = 12.5f;
    private static final int CIRCLE_DIAMETER = 40;
    private static final int CIRCLE_DIAMETER_LARGE = 56;
    private static final float COLOR_START_DELAY_OFFSET = 0.75f;
    static final int DEFAULT = 1;
    private static final float END_TRIM_START_DELAY_OFFSET = 0.5f;
    private static final float FULL_ROTATION = 1080.0f;
    static final int LARGE = 0;
    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    private static final Interpolator MATERIAL_INTERPOLATOR = new FastOutSlowInInterpolator();
    private static final float MAX_PROGRESS_ARC = 0.8f;
    private static final float NUM_POINTS = 5.0f;
    private static final float START_TRIM_DURATION_OFFSET = 0.5f;
    private static final float STROKE_WIDTH = 2.5f;
    private static final float STROKE_WIDTH_LARGE = 3.0f;
    private final int[] COLORS = new int[]{-16777216};
    private Animation mAnimation;
    private final ArrayList<Animation> mAnimators = new ArrayList();
    private final Callback mCallback = new C01473();
    boolean mFinishing;
    private double mHeight;
    private View mParent;
    private Resources mResources;
    private final Ring mRing;
    private float mRotation;
    private float mRotationCount;
    private double mWidth;

    class C01473 implements Callback {
        C01473() throws  {
        }

        public void invalidateDrawable(Drawable d) throws  {
            MaterialProgressDrawable.this.invalidateSelf();
        }

        public void scheduleDrawable(Drawable d, Runnable $r2, long $l0) throws  {
            MaterialProgressDrawable.this.scheduleSelf($r2, $l0);
        }

        public void unscheduleDrawable(Drawable d, Runnable $r2) throws  {
            MaterialProgressDrawable.this.unscheduleSelf($r2);
        }
    }

    @Retention(RetentionPolicy.CLASS)
    public @interface ProgressDrawableSize {
    }

    private static class Ring {
        private int mAlpha;
        private Path mArrow;
        private int mArrowHeight;
        private final Paint mArrowPaint = new Paint();
        private float mArrowScale;
        private int mArrowWidth;
        private int mBackgroundColor;
        private final Callback mCallback;
        private final Paint mCirclePaint = new Paint(1);
        private int mColorIndex;
        private int[] mColors;
        private int mCurrentColor;
        private float mEndTrim = 0.0f;
        private final Paint mPaint = new Paint();
        private double mRingCenterRadius;
        private float mRotation = 0.0f;
        private boolean mShowArrow;
        private float mStartTrim = 0.0f;
        private float mStartingEndTrim;
        private float mStartingRotation;
        private float mStartingStartTrim;
        private float mStrokeInset = 2.5f;
        private float mStrokeWidth = 5.0f;
        private final RectF mTempBounds = new RectF();

        public Ring(Callback $r1) throws  {
            this.mCallback = $r1;
            this.mPaint.setStrokeCap(Cap.SQUARE);
            this.mPaint.setAntiAlias(true);
            this.mPaint.setStyle(Style.STROKE);
            this.mArrowPaint.setStyle(Style.FILL);
            this.mArrowPaint.setAntiAlias(true);
        }

        public void setBackgroundColor(int $i0) throws  {
            this.mBackgroundColor = $i0;
        }

        public void setArrowDimensions(float $f0, float $f1) throws  {
            this.mArrowWidth = (int) $f0;
            this.mArrowHeight = (int) $f1;
        }

        public void draw(Canvas $r1, Rect $r2) throws  {
            RectF $r3 = this.mTempBounds;
            $r3.set($r2);
            $r3.inset(this.mStrokeInset, this.mStrokeInset);
            float $f0 = (this.mStartTrim + this.mRotation) * 360.0f;
            float $f1 = ((this.mEndTrim + this.mRotation) * 360.0f) - $f0;
            this.mPaint.setColor(this.mCurrentColor);
            $r1.drawArc($r3, $f0, $f1, false, this.mPaint);
            drawTriangle($r1, $f0, $f1, $r2);
            if (this.mAlpha < 255) {
                this.mCirclePaint.setColor(this.mBackgroundColor);
                this.mCirclePaint.setAlpha(255 - this.mAlpha);
                $r1.drawCircle($r2.exactCenterX(), $r2.exactCenterY(), (float) ($r2.width() / 2), this.mCirclePaint);
            }
        }

        private void drawTriangle(Canvas $r1, float $f0, float $f1, Rect $r2) throws  {
            if (this.mShowArrow) {
                if (this.mArrow == null) {
                    this.mArrow = new Path();
                    this.mArrow.setFillType(FillType.EVEN_ODD);
                } else {
                    this.mArrow.reset();
                }
                float $f2 = ((float) (((int) this.mStrokeInset) / 2)) * this.mArrowScale;
                float $f3 = (float) ((this.mRingCenterRadius * Math.cos(0.0d)) + ((double) $r2.exactCenterX()));
                float $f4 = (float) ((this.mRingCenterRadius * Math.sin(0.0d)) + ((double) $r2.exactCenterY()));
                this.mArrow.moveTo(0.0f, 0.0f);
                this.mArrow.lineTo(((float) this.mArrowWidth) * this.mArrowScale, 0.0f);
                this.mArrow.lineTo((((float) this.mArrowWidth) * this.mArrowScale) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, ((float) this.mArrowHeight) * this.mArrowScale);
                this.mArrow.offset($f3 - $f2, $f4);
                this.mArrow.close();
                this.mArrowPaint.setColor(this.mCurrentColor);
                $r1.rotate(($f0 + $f1) - 5.0f, $r2.exactCenterX(), $r2.exactCenterY());
                $r1.drawPath(this.mArrow, this.mArrowPaint);
            }
        }

        public void setColors(@NonNull int[] $r1) throws  {
            this.mColors = $r1;
            setColorIndex(0);
        }

        public void setColor(int $i0) throws  {
            this.mCurrentColor = $i0;
        }

        public void setColorIndex(int $i0) throws  {
            this.mColorIndex = $i0;
            this.mCurrentColor = this.mColors[this.mColorIndex];
        }

        public int getNextColor() throws  {
            return this.mColors[getNextColorIndex()];
        }

        private int getNextColorIndex() throws  {
            return (this.mColorIndex + 1) % this.mColors.length;
        }

        public void goToNextColor() throws  {
            setColorIndex(getNextColorIndex());
        }

        public void setColorFilter(ColorFilter $r1) throws  {
            this.mPaint.setColorFilter($r1);
            invalidateSelf();
        }

        public void setAlpha(int $i0) throws  {
            this.mAlpha = $i0;
        }

        public int getAlpha() throws  {
            return this.mAlpha;
        }

        public void setStrokeWidth(float $f0) throws  {
            this.mStrokeWidth = $f0;
            this.mPaint.setStrokeWidth($f0);
            invalidateSelf();
        }

        public float getStrokeWidth() throws  {
            return this.mStrokeWidth;
        }

        public void setStartTrim(float $f0) throws  {
            this.mStartTrim = $f0;
            invalidateSelf();
        }

        public float getStartTrim() throws  {
            return this.mStartTrim;
        }

        public float getStartingStartTrim() throws  {
            return this.mStartingStartTrim;
        }

        public float getStartingEndTrim() throws  {
            return this.mStartingEndTrim;
        }

        public int getStartingColor() throws  {
            return this.mColors[this.mColorIndex];
        }

        public void setEndTrim(float $f0) throws  {
            this.mEndTrim = $f0;
            invalidateSelf();
        }

        public float getEndTrim() throws  {
            return this.mEndTrim;
        }

        public void setRotation(float $f0) throws  {
            this.mRotation = $f0;
            invalidateSelf();
        }

        public float getRotation() throws  {
            return this.mRotation;
        }

        public void setInsets(int $i0, int $i1) throws  {
            float $f0 = (float) Math.min($i0, $i1);
            if (this.mRingCenterRadius <= 0.0d || $f0 < 0.0f) {
                $f0 = (float) Math.ceil((double) (this.mStrokeWidth / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN));
            } else {
                $f0 = (float) (((double) ($f0 / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN)) - this.mRingCenterRadius);
            }
            this.mStrokeInset = $f0;
        }

        public float getInsets() throws  {
            return this.mStrokeInset;
        }

        public void setCenterRadius(double $d0) throws  {
            this.mRingCenterRadius = $d0;
        }

        public double getCenterRadius() throws  {
            return this.mRingCenterRadius;
        }

        public void setShowArrow(boolean $z0) throws  {
            if (this.mShowArrow != $z0) {
                this.mShowArrow = $z0;
                invalidateSelf();
            }
        }

        public void setArrowScale(float $f0) throws  {
            if ($f0 != this.mArrowScale) {
                this.mArrowScale = $f0;
                invalidateSelf();
            }
        }

        public float getStartingRotation() throws  {
            return this.mStartingRotation;
        }

        public void storeOriginals() throws  {
            this.mStartingStartTrim = this.mStartTrim;
            this.mStartingEndTrim = this.mEndTrim;
            this.mStartingRotation = this.mRotation;
        }

        public void resetOriginals() throws  {
            this.mStartingStartTrim = 0.0f;
            this.mStartingEndTrim = 0.0f;
            this.mStartingRotation = 0.0f;
            setStartTrim(0.0f);
            setEndTrim(0.0f);
            setRotation(0.0f);
        }

        private void invalidateSelf() throws  {
            this.mCallback.invalidateDrawable(null);
        }
    }

    public int getOpacity() throws  {
        return -3;
    }

    public MaterialProgressDrawable(Context $r1, View $r2) throws  {
        this.mParent = $r2;
        this.mResources = $r1.getResources();
        this.mRing = new Ring(this.mCallback);
        this.mRing.setColors(this.COLORS);
        updateSizes(1);
        setupAnimators();
    }

    private void setSizeParameters(double $d0, double $d1, double $d2, double $d3, float $f0, float $f1) throws  {
        Ring $r1 = this.mRing;
        float $f2 = this.mResources.getDisplayMetrics().density;
        this.mWidth = ((double) $f2) * $d0;
        this.mHeight = ((double) $f2) * $d1;
        $r1.setStrokeWidth(((float) $d3) * $f2);
        $r1.setCenterRadius(((double) $f2) * $d2);
        $r1.setColorIndex(0);
        $r1.setArrowDimensions($f0 * $f2, $f1 * $f2);
        $r1.setInsets((int) this.mWidth, (int) this.mHeight);
    }

    public void updateSizes(@ProgressDrawableSize int $i0) throws  {
        if ($i0 == 0) {
            setSizeParameters(56.0d, 56.0d, 12.5d, 3.0d, 12.0f, 6.0f);
        } else {
            setSizeParameters(40.0d, 40.0d, 8.75d, 2.5d, 10.0f, 5.0f);
        }
    }

    public void showArrow(boolean $z0) throws  {
        this.mRing.setShowArrow($z0);
    }

    public void setArrowScale(float $f0) throws  {
        this.mRing.setArrowScale($f0);
    }

    public void setStartEndTrim(float $f0, float $f1) throws  {
        this.mRing.setStartTrim($f0);
        this.mRing.setEndTrim($f1);
    }

    public void setProgressRotation(float $f0) throws  {
        this.mRing.setRotation($f0);
    }

    public void setBackgroundColor(int $i0) throws  {
        this.mRing.setBackgroundColor($i0);
    }

    public void setColorSchemeColors(int... $r1) throws  {
        this.mRing.setColors($r1);
        this.mRing.setColorIndex(0);
    }

    public int getIntrinsicHeight() throws  {
        return (int) this.mHeight;
    }

    public int getIntrinsicWidth() throws  {
        return (int) this.mWidth;
    }

    public void draw(Canvas $r1) throws  {
        Rect $r2 = getBounds();
        int $i0 = $r1.save();
        $r1.rotate(this.mRotation, $r2.exactCenterX(), $r2.exactCenterY());
        this.mRing.draw($r1, $r2);
        $r1.restoreToCount($i0);
    }

    public void setAlpha(int $i0) throws  {
        this.mRing.setAlpha($i0);
    }

    public int getAlpha() throws  {
        return this.mRing.getAlpha();
    }

    public void setColorFilter(ColorFilter $r1) throws  {
        this.mRing.setColorFilter($r1);
    }

    void setRotation(float $f0) throws  {
        this.mRotation = $f0;
        invalidateSelf();
    }

    private float getRotation() throws  {
        return this.mRotation;
    }

    public boolean isRunning() throws  {
        ArrayList $r1 = this.mAnimators;
        int $i0 = $r1.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            Animation $r3 = (Animation) $r1.get($i1);
            if ($r3.hasStarted() && !$r3.hasEnded()) {
                return true;
            }
        }
        return false;
    }

    public void start() throws  {
        this.mAnimation.reset();
        this.mRing.storeOriginals();
        if (this.mRing.getEndTrim() != this.mRing.getStartTrim()) {
            this.mFinishing = true;
            this.mAnimation.setDuration(666);
            this.mParent.startAnimation(this.mAnimation);
            return;
        }
        this.mRing.setColorIndex(0);
        this.mRing.resetOriginals();
        this.mAnimation.setDuration(1332);
        this.mParent.startAnimation(this.mAnimation);
    }

    public void stop() throws  {
        this.mParent.clearAnimation();
        setRotation(0.0f);
        this.mRing.setShowArrow(false);
        this.mRing.setColorIndex(0);
        this.mRing.resetOriginals();
    }

    private float getMinProgressArc(Ring $r1) throws  {
        return (float) Math.toRadians(((double) $r1.getStrokeWidth()) / (6.283185307179586d * $r1.getCenterRadius()));
    }

    private int evaluateColorChange(float $f0, int $i0, int $i1) throws  {
        $i0 = Integer.valueOf($i0).intValue();
        int $i5 = ($i0 >> 24) & 255;
        int $i7 = ($i0 >> 16) & 255;
        int $i6 = ($i0 >> 8) & 255;
        $i0 &= 255;
        $i1 = Integer.valueOf($i1).intValue();
        return ((((((int) (((float) ((($i1 >> 24) & 255) - $i5)) * $f0)) + $i5) << 24) | ((((int) (((float) ((($i1 >> 16) & 255) - $i7)) * $f0)) + $i7) << 16)) | ((((int) (((float) ((($i1 >> 8) & 255) - $i6)) * $f0)) + $i6) << 8)) | (((int) (((float) (($i1 & 255) - $i0)) * $f0)) + $i0);
    }

    private void updateRingColor(float $f0, Ring $r1) throws  {
        if ($f0 > COLOR_START_DELAY_OFFSET) {
            $r1.setColor(evaluateColorChange(($f0 - COLOR_START_DELAY_OFFSET) / 0.25f, $r1.getStartingColor(), $r1.getNextColor()));
        }
    }

    private void applyFinishTranslation(float $f0, Ring $r1) throws  {
        updateRingColor($f0, $r1);
        float $f2 = (float) (Math.floor((double) ($r1.getStartingRotation() / 0.8f)) + FriendsBarFragment.END_LOCATION_POSITION);
        $r1.setStartTrim($r1.getStartingStartTrim() + ((($r1.getStartingEndTrim() - getMinProgressArc($r1)) - $r1.getStartingStartTrim()) * $f0));
        $r1.setEndTrim($r1.getStartingEndTrim());
        $r1.setRotation($r1.getStartingRotation() + (($f2 - $r1.getStartingRotation()) * $f0));
    }

    private void setupAnimators() throws  {
        final Ring $r2 = this.mRing;
        C01451 $r1 = new Animation() {
            public void applyTransformation(float $f0, Transformation t) throws  {
                if (MaterialProgressDrawable.this.mFinishing) {
                    MaterialProgressDrawable.this.applyFinishTranslation($f0, $r2);
                    return;
                }
                float $f2 = MaterialProgressDrawable.this.getMinProgressArc($r2);
                float $f1 = $r2.getStartingEndTrim();
                float $f4 = $r2.getStartingStartTrim();
                float $f3 = $r2.getStartingRotation();
                MaterialProgressDrawable.this.updateRingColor($f0, $r2);
                if ($f0 <= CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) {
                    $r2.setStartTrim($f4 + ((0.8f - $f2) * MaterialProgressDrawable.MATERIAL_INTERPOLATOR.getInterpolation($f0 / CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR)));
                }
                if ($f0 > CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) {
                    $r2.setEndTrim($f1 + (MaterialProgressDrawable.MATERIAL_INTERPOLATOR.getInterpolation(($f0 - CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) / CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) * (0.8f - $f2)));
                }
                $r2.setRotation($f3 + (0.25f * $f0));
                MaterialProgressDrawable.this.setRotation((216.0f * $f0) + (MaterialProgressDrawable.FULL_ROTATION * (MaterialProgressDrawable.this.mRotationCount / 5.0f)));
            }
        };
        $r1.setRepeatCount(-1);
        $r1.setRepeatMode(1);
        $r1.setInterpolator(LINEAR_INTERPOLATOR);
        $r1.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) throws  {
                MaterialProgressDrawable.this.mRotationCount = 0.0f;
            }

            public void onAnimationEnd(Animation animation) throws  {
            }

            public void onAnimationRepeat(Animation $r1) throws  {
                $r2.storeOriginals();
                $r2.goToNextColor();
                $r2.setStartTrim($r2.getEndTrim());
                if (MaterialProgressDrawable.this.mFinishing) {
                    MaterialProgressDrawable.this.mFinishing = false;
                    $r1.setDuration(1332);
                    $r2.setShowArrow(false);
                    return;
                }
                MaterialProgressDrawable.this.mRotationCount = (MaterialProgressDrawable.this.mRotationCount + 1.0f) % 5.0f;
            }
        });
        this.mAnimation = $r1;
    }
}
