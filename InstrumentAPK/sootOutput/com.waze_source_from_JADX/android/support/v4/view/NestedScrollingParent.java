package android.support.v4.view;

import android.view.View;

public interface NestedScrollingParent {
    int getNestedScrollAxes() throws ;

    boolean onNestedFling(View view, float f, float f2, boolean z) throws ;

    boolean onNestedPreFling(View view, float f, float f2) throws ;

    void onNestedPreScroll(View view, int i, int i2, int[] iArr) throws ;

    void onNestedScroll(View view, int i, int i2, int i3, int i4) throws ;

    void onNestedScrollAccepted(View view, View view2, int i) throws ;

    boolean onStartNestedScroll(View view, View view2, int i) throws ;

    void onStopNestedScroll(View view) throws ;
}
