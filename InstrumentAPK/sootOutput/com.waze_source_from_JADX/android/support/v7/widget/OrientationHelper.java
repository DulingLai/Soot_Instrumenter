package android.support.v7.widget;

import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.view.View;

public abstract class OrientationHelper {
    public static final int HORIZONTAL = 0;
    private static final int INVALID_SIZE = Integer.MIN_VALUE;
    public static final int VERTICAL = 1;
    private int mLastTotalSpace;
    protected final LayoutManager mLayoutManager;

    public abstract int getDecoratedEnd(View view) throws ;

    public abstract int getDecoratedMeasurement(View view) throws ;

    public abstract int getDecoratedMeasurementInOther(View view) throws ;

    public abstract int getDecoratedStart(View view) throws ;

    public abstract int getEnd() throws ;

    public abstract int getEndAfterPadding() throws ;

    public abstract int getEndPadding() throws ;

    public abstract int getMode() throws ;

    public abstract int getModeInOther() throws ;

    public abstract int getStartAfterPadding() throws ;

    public abstract int getTotalSpace() throws ;

    public abstract void offsetChild(View view, int i) throws ;

    public abstract void offsetChildren(int i) throws ;

    private OrientationHelper(LayoutManager $r1) throws  {
        this.mLastTotalSpace = Integer.MIN_VALUE;
        this.mLayoutManager = $r1;
    }

    public void onLayoutComplete() throws  {
        this.mLastTotalSpace = getTotalSpace();
    }

    public int getTotalSpaceChange() throws  {
        return Integer.MIN_VALUE == this.mLastTotalSpace ? 0 : getTotalSpace() - this.mLastTotalSpace;
    }

    public static OrientationHelper createOrientationHelper(LayoutManager $r0, int $i0) throws  {
        switch ($i0) {
            case 0:
                return createHorizontalHelper($r0);
            case 1:
                return createVerticalHelper($r0);
            default:
                throw new IllegalArgumentException("invalid orientation");
        }
    }

    public static OrientationHelper createHorizontalHelper(LayoutManager $r0) throws  {
        return new OrientationHelper($r0) {
            public int getEndAfterPadding() throws  {
                return this.mLayoutManager.getWidth() - this.mLayoutManager.getPaddingRight();
            }

            public int getEnd() throws  {
                return this.mLayoutManager.getWidth();
            }

            public void offsetChildren(int $i0) throws  {
                this.mLayoutManager.offsetChildrenHorizontal($i0);
            }

            public int getStartAfterPadding() throws  {
                return this.mLayoutManager.getPaddingLeft();
            }

            public int getDecoratedMeasurement(View $r1) throws  {
                LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
                return (this.mLayoutManager.getDecoratedMeasuredWidth($r1) + $r3.leftMargin) + $r3.rightMargin;
            }

            public int getDecoratedMeasurementInOther(View $r1) throws  {
                LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
                return (this.mLayoutManager.getDecoratedMeasuredHeight($r1) + $r3.topMargin) + $r3.bottomMargin;
            }

            public int getDecoratedEnd(View $r1) throws  {
                return this.mLayoutManager.getDecoratedRight($r1) + ((LayoutParams) $r1.getLayoutParams()).rightMargin;
            }

            public int getDecoratedStart(View $r1) throws  {
                return this.mLayoutManager.getDecoratedLeft($r1) - ((LayoutParams) $r1.getLayoutParams()).leftMargin;
            }

            public int getTotalSpace() throws  {
                return (this.mLayoutManager.getWidth() - this.mLayoutManager.getPaddingLeft()) - this.mLayoutManager.getPaddingRight();
            }

            public void offsetChild(View $r1, int $i0) throws  {
                $r1.offsetLeftAndRight($i0);
            }

            public int getEndPadding() throws  {
                return this.mLayoutManager.getPaddingRight();
            }

            public int getMode() throws  {
                return this.mLayoutManager.getWidthMode();
            }

            public int getModeInOther() throws  {
                return this.mLayoutManager.getHeightMode();
            }
        };
    }

    public static OrientationHelper createVerticalHelper(LayoutManager $r0) throws  {
        return new OrientationHelper($r0) {
            public int getEndAfterPadding() throws  {
                return this.mLayoutManager.getHeight() - this.mLayoutManager.getPaddingBottom();
            }

            public int getEnd() throws  {
                return this.mLayoutManager.getHeight();
            }

            public void offsetChildren(int $i0) throws  {
                this.mLayoutManager.offsetChildrenVertical($i0);
            }

            public int getStartAfterPadding() throws  {
                return this.mLayoutManager.getPaddingTop();
            }

            public int getDecoratedMeasurement(View $r1) throws  {
                LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
                return (this.mLayoutManager.getDecoratedMeasuredHeight($r1) + $r3.topMargin) + $r3.bottomMargin;
            }

            public int getDecoratedMeasurementInOther(View $r1) throws  {
                LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
                return (this.mLayoutManager.getDecoratedMeasuredWidth($r1) + $r3.leftMargin) + $r3.rightMargin;
            }

            public int getDecoratedEnd(View $r1) throws  {
                return this.mLayoutManager.getDecoratedBottom($r1) + ((LayoutParams) $r1.getLayoutParams()).bottomMargin;
            }

            public int getDecoratedStart(View $r1) throws  {
                return this.mLayoutManager.getDecoratedTop($r1) - ((LayoutParams) $r1.getLayoutParams()).topMargin;
            }

            public int getTotalSpace() throws  {
                return (this.mLayoutManager.getHeight() - this.mLayoutManager.getPaddingTop()) - this.mLayoutManager.getPaddingBottom();
            }

            public void offsetChild(View $r1, int $i0) throws  {
                $r1.offsetTopAndBottom($i0);
            }

            public int getEndPadding() throws  {
                return this.mLayoutManager.getPaddingBottom();
            }

            public int getMode() throws  {
                return this.mLayoutManager.getHeightMode();
            }

            public int getModeInOther() throws  {
                return this.mLayoutManager.getWidthMode();
            }
        };
    }
}
