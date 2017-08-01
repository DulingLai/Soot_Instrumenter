package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.C0192R;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;

public class ButtonBarLayout extends LinearLayout {
    private boolean mAllowStacking;
    private int mLastWidthSize = -1;

    public ButtonBarLayout(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        TypedArray $r4 = $r1.obtainStyledAttributes($r2, C0192R.styleable.ButtonBarLayout);
        this.mAllowStacking = $r4.getBoolean(C0192R.styleable.ButtonBarLayout_allowStacking, false);
        $r4.recycle();
    }

    public void setAllowStacking(boolean $z0) throws  {
        if (this.mAllowStacking != $z0) {
            this.mAllowStacking = $z0;
            if (!this.mAllowStacking && getOrientation() == 1) {
                setStacked(false);
            }
            requestLayout();
        }
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        int $i3;
        boolean $z0 = false;
        int $i2 = MeasureSpec.getSize($i0);
        if (this.mAllowStacking) {
            if ($i2 > this.mLastWidthSize && isStacked()) {
                setStacked(false);
            }
            this.mLastWidthSize = $i2;
        }
        boolean $z1 = false;
        if (isStacked() || MeasureSpec.getMode($i0) != 1073741824) {
            $i3 = $i0;
        } else {
            $i3 = MeasureSpec.makeMeasureSpec($i2, Integer.MIN_VALUE);
            $z1 = true;
        }
        super.onMeasure($i3, $i1);
        if (this.mAllowStacking && !isStacked()) {
            if (VERSION.SDK_INT < 11) {
                $i3 = 0;
                for (int $i4 = 0; $i4 < getChildCount(); $i4++) {
                    $i3 += getChildAt($i4).getMeasuredWidth();
                }
                if ((getPaddingLeft() + $i3) + getPaddingRight() > $i2) {
                    $z0 = true;
                }
            } else if ((ViewCompat.getMeasuredWidthAndState(this) & -16777216) == ViewCompat.MEASURED_STATE_TOO_SMALL) {
                $z0 = true;
            }
            if ($z0) {
                setStacked(true);
                $z1 = true;
            }
        }
        if ($z1) {
            super.onMeasure($i0, $i1);
        }
    }

    private void setStacked(boolean $z0) throws  {
        byte $b0;
        setOrientation($z0 ? (byte) 1 : (byte) 0);
        if ($z0) {
            $b0 = (byte) 5;
        } else {
            $b0 = (byte) 80;
        }
        setGravity($b0);
        View $r1 = findViewById(C0192R.id.spacer);
        if ($r1 != null) {
            if ($z0) {
                $b0 = (byte) 8;
            } else {
                $b0 = (byte) 4;
            }
            $r1.setVisibility($b0);
        }
        for (int $i1 = getChildCount() - 2; $i1 >= 0; $i1--) {
            bringChildToFront(getChildAt($i1));
        }
    }

    private boolean isStacked() throws  {
        return getOrientation() == 1;
    }
}
