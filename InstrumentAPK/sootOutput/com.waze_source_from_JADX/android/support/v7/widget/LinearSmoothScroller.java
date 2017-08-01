package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.SmoothScroller;
import android.support.v7.widget.RecyclerView.SmoothScroller.Action;
import android.support.v7.widget.RecyclerView.State;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

public abstract class LinearSmoothScroller extends SmoothScroller {
    private static final boolean DEBUG = false;
    private static final float MILLISECONDS_PER_INCH = 25.0f;
    public static final int SNAP_TO_ANY = 0;
    public static final int SNAP_TO_END = 1;
    public static final int SNAP_TO_START = -1;
    private static final String TAG = "LinearSmoothScroller";
    private static final float TARGET_SEEK_EXTRA_SCROLL_RATIO = 1.2f;
    private static final int TARGET_SEEK_SCROLL_DISTANCE_PX = 10000;
    private final float MILLISECONDS_PER_PX;
    protected final DecelerateInterpolator mDecelerateInterpolator = new DecelerateInterpolator();
    protected int mInterimTargetDx = 0;
    protected int mInterimTargetDy = 0;
    protected final LinearInterpolator mLinearInterpolator = new LinearInterpolator();
    protected PointF mTargetVector;

    protected float calculateSpeedPerPixel(DisplayMetrics $r1) throws  {
        return MILLISECONDS_PER_INCH / ((float) $r1.densityDpi);
    }

    public abstract PointF computeScrollVectorForPosition(int i) throws ;

    public LinearSmoothScroller(Context $r1) throws  {
        this.MILLISECONDS_PER_PX = calculateSpeedPerPixel($r1.getResources().getDisplayMetrics());
    }

    protected void onStart() throws  {
    }

    protected void onTargetFound(View $r1, State state, Action $r3) throws  {
        int $i1 = calculateDxToMakeVisible($r1, getHorizontalSnapPreference());
        int $i2 = calculateDyToMakeVisible($r1, getVerticalSnapPreference());
        int $i0 = calculateTimeForDeceleration((int) Math.sqrt((double) (($i1 * $i1) + ($i2 * $i2))));
        if ($i0 > 0) {
            $r3.update(-$i1, -$i2, $i0, this.mDecelerateInterpolator);
        }
    }

    protected void onSeekTargetStep(int $i0, int $i1, State state, Action $r2) throws  {
        if (getChildCount() == 0) {
            stop();
            return;
        }
        this.mInterimTargetDx = clampApplyScroll(this.mInterimTargetDx, $i0);
        this.mInterimTargetDy = clampApplyScroll(this.mInterimTargetDy, $i1);
        if (this.mInterimTargetDx == 0 && this.mInterimTargetDy == 0) {
            updateActionForInterimTarget($r2);
        }
    }

    protected void onStop() throws  {
        this.mInterimTargetDy = 0;
        this.mInterimTargetDx = 0;
        this.mTargetVector = null;
    }

    protected int calculateTimeForDeceleration(int $i0) throws  {
        return (int) Math.ceil(((double) calculateTimeForScrolling($i0)) / 0.3356d);
    }

    protected int calculateTimeForScrolling(int $i0) throws  {
        return (int) Math.ceil((double) (((float) Math.abs($i0)) * this.MILLISECONDS_PER_PX));
    }

    protected int getHorizontalSnapPreference() throws  {
        if (this.mTargetVector == null || this.mTargetVector.x == 0.0f) {
            return 0;
        }
        return this.mTargetVector.x > 0.0f ? 1 : -1;
    }

    protected int getVerticalSnapPreference() throws  {
        if (this.mTargetVector == null || this.mTargetVector.y == 0.0f) {
            return 0;
        }
        return this.mTargetVector.y > 0.0f ? 1 : -1;
    }

    protected void updateActionForInterimTarget(Action $r1) throws  {
        PointF $r2 = computeScrollVectorForPosition(getTargetPosition());
        if ($r2 == null || ($r2.x == 0.0f && $r2.y == 0.0f)) {
            Log.e(TAG, "To support smooth scrolling, you should override \nLayoutManager#computeScrollVectorForPosition.\nFalling back to instant scroll");
            $r1.jumpTo(getTargetPosition());
            stop();
            return;
        }
        normalize($r2);
        this.mTargetVector = $r2;
        this.mInterimTargetDx = (int) ($r2.x * 10000.0f);
        this.mInterimTargetDy = (int) ($r2.y * 10000.0f);
        $r1.update((int) (((float) this.mInterimTargetDx) * TARGET_SEEK_EXTRA_SCROLL_RATIO), (int) (((float) this.mInterimTargetDy) * TARGET_SEEK_EXTRA_SCROLL_RATIO), (int) (((float) calculateTimeForScrolling(10000)) * TARGET_SEEK_EXTRA_SCROLL_RATIO), this.mLinearInterpolator);
    }

    private int clampApplyScroll(int $i1, int $i0) throws  {
        $i0 = $i1 - $i0;
        return $i1 * $i0 <= 0 ? 0 : $i0;
    }

    public int calculateDtToFit(int $i0, int $i1, int $i2, int $i3, int $i4) throws  {
        switch ($i4) {
            case -1:
                return $i2 - $i0;
            case 0:
                $i0 = $i2 - $i0;
                if ($i0 > 0) {
                    return $i0;
                }
                $i0 = $i3 - $i1;
                return $i0 < 0 ? $i0 : 0;
            case 1:
                return $i3 - $i1;
            default:
                throw new IllegalArgumentException("snap preference should be one of the constants defined in SmoothScroller, starting with SNAP_");
        }
    }

    public int calculateDyToMakeVisible(View $r1, int $i0) throws  {
        LayoutManager $r2 = getLayoutManager();
        if ($r2 == null || !$r2.canScrollVertically()) {
            return 0;
        }
        LayoutParams $r4 = (LayoutParams) $r1.getLayoutParams();
        return calculateDtToFit($r2.getDecoratedTop($r1) - $r4.topMargin, $r2.getDecoratedBottom($r1) + $r4.bottomMargin, $r2.getPaddingTop(), $r2.getHeight() - $r2.getPaddingBottom(), $i0);
    }

    public int calculateDxToMakeVisible(View $r1, int $i0) throws  {
        LayoutManager $r2 = getLayoutManager();
        if ($r2 == null || !$r2.canScrollHorizontally()) {
            return 0;
        }
        LayoutParams $r4 = (LayoutParams) $r1.getLayoutParams();
        return calculateDtToFit($r2.getDecoratedLeft($r1) - $r4.leftMargin, $r2.getDecoratedRight($r1) + $r4.rightMargin, $r2.getPaddingLeft(), $r2.getWidth() - $r2.getPaddingRight(), $i0);
    }
}
