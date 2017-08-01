package android.support.v4.view;

import android.view.View;
import android.view.ViewParent;

public class NestedScrollingChildHelper {
    private boolean mIsNestedScrollingEnabled;
    private ViewParent mNestedScrollingParent;
    private int[] mTempNestedScrollConsumed;
    private final View mView;

    public NestedScrollingChildHelper(View $r1) throws  {
        this.mView = $r1;
    }

    public void setNestedScrollingEnabled(boolean $z0) throws  {
        if (this.mIsNestedScrollingEnabled) {
            ViewCompat.stopNestedScroll(this.mView);
        }
        this.mIsNestedScrollingEnabled = $z0;
    }

    public boolean isNestedScrollingEnabled() throws  {
        return this.mIsNestedScrollingEnabled;
    }

    public boolean hasNestedScrollingParent() throws  {
        return this.mNestedScrollingParent != null;
    }

    public boolean startNestedScroll(int $i0) throws  {
        if (hasNestedScrollingParent()) {
            return true;
        }
        if (isNestedScrollingEnabled()) {
            View $r1 = this.mView;
            for (ViewParent $r2 = this.mView.getParent(); $r2 != null; $r2 = $r2.getParent()) {
                if (ViewParentCompat.onStartNestedScroll($r2, $r1, this.mView, $i0)) {
                    this.mNestedScrollingParent = $r2;
                    ViewParentCompat.onNestedScrollAccepted($r2, $r1, this.mView, $i0);
                    return true;
                }
                if ($r2 instanceof View) {
                    $r1 = (View) $r2;
                }
            }
        }
        return false;
    }

    public void stopNestedScroll() throws  {
        if (this.mNestedScrollingParent != null) {
            ViewParentCompat.onStopNestedScroll(this.mNestedScrollingParent, this.mView);
            this.mNestedScrollingParent = null;
        }
    }

    public boolean dispatchNestedScroll(int $i0, int $i1, int $i2, int $i3, int[] $r1) throws  {
        if (isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            if ($i0 != 0 || $i1 != 0 || $i2 != 0 || $i3 != 0) {
                int $i4 = 0;
                int $i5 = 0;
                if ($r1 != null) {
                    this.mView.getLocationInWindow($r1);
                    $i4 = $r1[0];
                    $i5 = $r1[1];
                }
                ViewParentCompat.onNestedScroll(this.mNestedScrollingParent, this.mView, $i0, $i1, $i2, $i3);
                if ($r1 != null) {
                    this.mView.getLocationInWindow($r1);
                    $r1[0] = $r1[0] - $i4;
                    $r1[1] = $r1[1] - $i5;
                }
                return true;
            } else if ($r1 != null) {
                $r1[0] = 0;
                $r1[1] = 0;
            }
        }
        return false;
    }

    public boolean dispatchNestedPreScroll(int $i0, int $i1, int[] $r3, int[] $r1) throws  {
        if (!isNestedScrollingEnabled()) {
            return false;
        }
        if (this.mNestedScrollingParent == null) {
            return false;
        }
        if ($i0 != 0 || $i1 != 0) {
            int $i2 = 0;
            int $i3 = 0;
            if ($r1 != null) {
                this.mView.getLocationInWindow($r1);
                $i2 = $r1[0];
                $i3 = $r1[1];
            }
            if ($r3 == null) {
                if (this.mTempNestedScrollConsumed == null) {
                    this.mTempNestedScrollConsumed = new int[2];
                }
                $r3 = this.mTempNestedScrollConsumed;
            }
            $r3[0] = 0;
            $r3[1] = 0;
            ViewParentCompat.onNestedPreScroll(this.mNestedScrollingParent, this.mView, $i0, $i1, $r3);
            if ($r1 != null) {
                this.mView.getLocationInWindow($r1);
                $r1[0] = $r1[0] - $i2;
                $r1[1] = $r1[1] - $i3;
            }
            if ($r3[0] == 0 && $r3[1] == 0) {
                return false;
            }
            return true;
        } else if ($r1 == null) {
            return false;
        } else {
            $r1[0] = 0;
            $r1[1] = 0;
            return false;
        }
    }

    public boolean dispatchNestedFling(float $f0, float $f1, boolean $z0) throws  {
        if (!isNestedScrollingEnabled() || this.mNestedScrollingParent == null) {
            return false;
        }
        return ViewParentCompat.onNestedFling(this.mNestedScrollingParent, this.mView, $f0, $f1, $z0);
    }

    public boolean dispatchNestedPreFling(float $f0, float $f1) throws  {
        if (!isNestedScrollingEnabled() || this.mNestedScrollingParent == null) {
            return false;
        }
        return ViewParentCompat.onNestedPreFling(this.mNestedScrollingParent, this.mView, $f0, $f1);
    }

    public void onDetachedFromWindow() throws  {
        ViewCompat.stopNestedScroll(this.mView);
    }

    public void onStopNestedScroll(View child) throws  {
        ViewCompat.stopNestedScroll(this.mView);
    }
}
