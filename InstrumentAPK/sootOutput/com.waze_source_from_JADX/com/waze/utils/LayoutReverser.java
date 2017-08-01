package com.waze.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class LayoutReverser {
    private boolean mDoGravity = true;
    private boolean mDoGroups = true;
    private boolean mDoLinearLayout = true;
    private boolean mDoMargins = true;
    private boolean mDoPadding = true;
    private boolean mDoRelativeLayout = false;

    public void reverseGroup(ViewGroup vg) {
        if (vg instanceof RelativeLayout) {
            if (this.mDoRelativeLayout) {
                reverseChildren(vg);
            }
        } else if (!(vg instanceof LinearLayout)) {
            reverseChildren(vg);
        } else if (this.mDoLinearLayout) {
            reverseChildren(vg);
            for (int i = vg.getChildCount() - 2; i >= 0; i--) {
                View child = vg.getChildAt(i);
                vg.removeViewAt(i);
                vg.addView(child, child.getWidth(), child.getHeight());
            }
        }
    }

    private void reverseChildren(ViewGroup vg) {
        int count = vg.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = vg.getChildAt(i);
            if (this.mDoGroups && (child instanceof ViewGroup)) {
                reverseGroup((ViewGroup) child);
            } else {
                reverseView(child);
            }
        }
    }

    public void reverseView(View v) {
        LayoutParams lllp;
        FrameLayout.LayoutParams fllp;
        ViewGroup.LayoutParams vglp = v.getLayoutParams();
        if (this.mDoGravity) {
            if (vglp instanceof LayoutParams) {
                lllp = (LayoutParams) vglp;
                if ((lllp.gravity & 3) != 0) {
                    lllp.gravity &= -4;
                    lllp.gravity |= 5;
                }
                if ((lllp.gravity & 5) != 0) {
                    lllp.gravity &= -6;
                    lllp.gravity |= 3;
                }
            }
            if (vglp instanceof FrameLayout.LayoutParams) {
                fllp = (FrameLayout.LayoutParams) vglp;
                if ((fllp.gravity & 3) != 0) {
                    fllp.gravity &= -4;
                    fllp.gravity |= 5;
                }
                if ((fllp.gravity & 5) != 0) {
                    fllp.gravity &= -6;
                    fllp.gravity |= 3;
                }
            }
        }
        if (this.mDoPadding) {
            v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingLeft(), v.getPaddingBottom());
        }
        if (this.mDoMargins) {
            int left;
            if (vglp instanceof LayoutParams) {
                lllp = (LayoutParams) vglp;
                left = lllp.leftMargin;
                lllp.leftMargin = lllp.rightMargin;
                lllp.rightMargin = left;
            }
            if (vglp instanceof FrameLayout.LayoutParams) {
                fllp = (FrameLayout.LayoutParams) vglp;
                left = fllp.leftMargin;
                fllp.leftMargin = fllp.rightMargin;
                fllp.rightMargin = left;
            }
            if (vglp instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams rllp = (RelativeLayout.LayoutParams) vglp;
                left = rllp.leftMargin;
                rllp.leftMargin = rllp.rightMargin;
                rllp.rightMargin = left;
            }
        }
    }

    public void setDoGroups(boolean doGroups) {
        this.mDoGroups = doGroups;
    }

    public void setDoGravity(boolean doGravity) {
        this.mDoGravity = doGravity;
    }

    public void setDoPadding(boolean doPadding) {
        this.mDoPadding = doPadding;
    }

    public void setDoMargins(boolean doMargins) {
        this.mDoMargins = doMargins;
    }

    public void setDoLinearLayout(boolean doLinearLayout) {
        this.mDoLinearLayout = doLinearLayout;
    }

    public void setDoRelativeLayout(boolean doRelativeLayout) {
        this.mDoRelativeLayout = doRelativeLayout;
    }
}
