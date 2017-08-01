package android.support.v4.view;

import android.view.View;
import android.view.ViewGroup;

public class NestedScrollingParentHelper {
    private int mNestedScrollAxes;
    private final ViewGroup mViewGroup;

    public NestedScrollingParentHelper(ViewGroup $r1) throws  {
        this.mViewGroup = $r1;
    }

    public void onNestedScrollAccepted(View child, View target, int $i0) throws  {
        this.mNestedScrollAxes = $i0;
    }

    public int getNestedScrollAxes() throws  {
        return this.mNestedScrollAxes;
    }

    public void onStopNestedScroll(View target) throws  {
        this.mNestedScrollAxes = 0;
    }
}
