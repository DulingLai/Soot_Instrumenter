package android.support.v4.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.waze.FriendsBarFragment;
import com.waze.LayoutManager;

final class SwipeProgressBar {
    private static final int ANIMATION_DURATION_MS = 2000;
    private static final int COLOR1 = -1291845632;
    private static final int COLOR2 = Integer.MIN_VALUE;
    private static final int COLOR3 = 1291845632;
    private static final int COLOR4 = 436207616;
    private static final int FINISH_ANIMATION_DURATION_MS = 1000;
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private Rect mBounds = new Rect();
    private final RectF mClipRect = new RectF();
    private int mColor1;
    private int mColor2;
    private int mColor3;
    private int mColor4;
    private long mFinishTime;
    private final Paint mPaint = new Paint();
    private View mParent;
    private boolean mRunning;
    private long mStartTime;
    private float mTriggerPercentage;

    public SwipeProgressBar(View $r1) throws  {
        this.mParent = $r1;
        this.mColor1 = COLOR1;
        this.mColor2 = Integer.MIN_VALUE;
        this.mColor3 = COLOR3;
        this.mColor4 = COLOR4;
    }

    void setColorScheme(int $i0, int $i1, int $i2, int $i3) throws  {
        this.mColor1 = $i0;
        this.mColor2 = $i1;
        this.mColor3 = $i2;
        this.mColor4 = $i3;
    }

    void setTriggerPercentage(float $f0) throws  {
        this.mTriggerPercentage = $f0;
        this.mStartTime = 0;
        ViewCompat.postInvalidateOnAnimation(this.mParent, this.mBounds.left, this.mBounds.top, this.mBounds.right, this.mBounds.bottom);
    }

    void start() throws  {
        if (!this.mRunning) {
            this.mTriggerPercentage = 0.0f;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mRunning = true;
            this.mParent.postInvalidate();
        }
    }

    void stop() throws  {
        if (this.mRunning) {
            this.mTriggerPercentage = 0.0f;
            this.mFinishTime = AnimationUtils.currentAnimationTimeMillis();
            this.mRunning = false;
            this.mParent.postInvalidate();
        }
    }

    boolean isRunning() throws  {
        return this.mRunning || this.mFinishTime > 0;
    }

    void draw(Canvas $r1) throws  {
        int $i5 = this.mBounds.width();
        int $i6 = this.mBounds.height();
        int $i0 = $i5 / 2;
        int $i1 = $i6 / 2;
        boolean $z0 = false;
        int $i7 = $r1.save();
        int $i8 = $i7;
        $r1.clipRect(this.mBounds);
        float $f2;
        if (this.mRunning || this.mFinishTime > 0) {
            float f;
            long $l3 = AnimationUtils.currentAnimationTimeMillis();
            long j = ($l3 - this.mStartTime) / 2000;
            $f2 = ((float) (($l3 - this.mStartTime) % 2000)) / 20.0f;
            if (!this.mRunning) {
                if ($l3 - this.mFinishTime >= 1000) {
                    this.mFinishTime = 0;
                    return;
                }
                float $f1 = ($l3 - this.mFinishTime) % 1000;
                $l3 = $f1;
                float $f0 = ((float) ($i5 / 2)) * INTERPOLATOR.getInterpolation((((float) $f1) / 10.0f) / 100.0f);
                $f1 = (float) $i0;
                this.mClipRect.set($f1 - $f0, 0.0f, ((float) $i0) + $f0, (float) $i6);
                $r1.saveLayerAlpha(this.mClipRect, 0, 0);
                $z0 = true;
            }
            if (j == 0) {
                $r1.drawColor(this.mColor1);
            } else if ($f2 >= 0.0f && $f2 < 25.0f) {
                $r1.drawColor(this.mColor4);
            } else if ($f2 >= 25.0f && $f2 < LayoutManager.WAZE_LAYOUT_EDIT_HEIGHT) {
                $r1.drawColor(this.mColor1);
            } else if ($f2 < LayoutManager.WAZE_LAYOUT_EDIT_HEIGHT || $f2 >= 75.0f) {
                $r1.drawColor(this.mColor3);
            } else {
                $r1.drawColor(this.mColor2);
            }
            if ($f2 >= 0.0f && $f2 <= 25.0f) {
                f = ((25.0f + $f2) * LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) / 100.0f;
                drawCircle($r1, (float) $i0, (float) $i1, this.mColor1, f);
            }
            if ($f2 >= 0.0f && $f2 <= LayoutManager.WAZE_LAYOUT_EDIT_HEIGHT) {
                f = (LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN * $f2) / 100.0f;
                drawCircle($r1, (float) $i0, (float) $i1, this.mColor2, f);
            }
            if ($f2 >= 25.0f && $f2 <= 75.0f) {
                f = (($f2 - 25.0f) * LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) / 100.0f;
                drawCircle($r1, (float) $i0, (float) $i1, this.mColor3, f);
            }
            if ($f2 >= LayoutManager.WAZE_LAYOUT_EDIT_HEIGHT && $f2 <= 100.0f) {
                f = (($f2 - LayoutManager.WAZE_LAYOUT_EDIT_HEIGHT) * LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) / 100.0f;
                drawCircle($r1, (float) $i0, (float) $i1, this.mColor4, f);
            }
            if ($f2 >= 75.0f && $f2 <= 100.0f) {
                $f2 = (($f2 - 75.0f) * LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) / 100.0f;
                drawCircle($r1, (float) $i0, (float) $i1, this.mColor1, $f2);
            }
            if (this.mTriggerPercentage > 0.0f && $z0) {
                $r1.restoreToCount($i7);
                $i8 = $r1.save();
                $r1.clipRect(this.mBounds);
                drawTrigger($r1, $i0, $i1);
            }
            ViewCompat.postInvalidateOnAnimation(this.mParent, this.mBounds.left, this.mBounds.top, this.mBounds.right, this.mBounds.bottom);
        } else if (this.mTriggerPercentage > 0.0f) {
            double $d0 = this.mTriggerPercentage;
            $f2 = $d0;
            if (((double) $d0) <= FriendsBarFragment.END_LOCATION_POSITION) {
                drawTrigger($r1, $i0, $i1);
            }
        }
        $r1.restoreToCount($i8);
    }

    private void drawTrigger(Canvas $r1, int $i0, int $i1) throws  {
        this.mPaint.setColor(this.mColor1);
        $r1.drawCircle((float) $i0, (float) $i1, ((float) $i0) * this.mTriggerPercentage, this.mPaint);
    }

    private void drawCircle(Canvas $r1, float $f0, float $f1, int $i0, float $f2) throws  {
        this.mPaint.setColor($i0);
        $r1.save();
        $r1.translate($f0, $f1);
        $f1 = INTERPOLATOR.getInterpolation($f2);
        $r1.scale($f1, $f1);
        $r1.drawCircle(0.0f, 0.0f, $f0, this.mPaint);
        $r1.restore();
    }

    void setBounds(int $i0, int $i1, int $i2, int $i3) throws  {
        this.mBounds.left = $i0;
        this.mBounds.top = $i1;
        this.mBounds.right = $i2;
        this.mBounds.bottom = $i3;
    }
}
