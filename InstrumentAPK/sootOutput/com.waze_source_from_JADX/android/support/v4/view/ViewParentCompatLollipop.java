package android.support.v4.view;

import android.util.Log;
import android.view.View;
import android.view.ViewParent;

class ViewParentCompatLollipop {
    private static final String TAG = "ViewParentCompat";

    ViewParentCompatLollipop() throws  {
    }

    public static boolean onStartNestedScroll(ViewParent $r0, View $r1, View $r2, int $i0) throws  {
        try {
            return $r0.onStartNestedScroll($r1, $r2, $i0);
        } catch (AbstractMethodError $r3) {
            Log.e(TAG, "ViewParent " + $r0 + " does not implement interface " + "method onStartNestedScroll", $r3);
            return false;
        }
    }

    public static void onNestedScrollAccepted(ViewParent $r0, View $r1, View $r2, int $i0) throws  {
        try {
            $r0.onNestedScrollAccepted($r1, $r2, $i0);
        } catch (AbstractMethodError $r3) {
            Log.e(TAG, "ViewParent " + $r0 + " does not implement interface " + "method onNestedScrollAccepted", $r3);
        }
    }

    public static void onStopNestedScroll(ViewParent $r0, View $r1) throws  {
        try {
            $r0.onStopNestedScroll($r1);
        } catch (AbstractMethodError $r2) {
            Log.e(TAG, "ViewParent " + $r0 + " does not implement interface " + "method onStopNestedScroll", $r2);
        }
    }

    public static void onNestedScroll(ViewParent $r0, View $r1, int $i0, int $i1, int $i2, int $i3) throws  {
        try {
            $r0.onNestedScroll($r1, $i0, $i1, $i2, $i3);
        } catch (AbstractMethodError $r2) {
            Log.e(TAG, "ViewParent " + $r0 + " does not implement interface " + "method onNestedScroll", $r2);
        }
    }

    public static void onNestedPreScroll(ViewParent $r0, View $r1, int $i0, int $i1, int[] $r2) throws  {
        try {
            $r0.onNestedPreScroll($r1, $i0, $i1, $r2);
        } catch (AbstractMethodError $r3) {
            Log.e(TAG, "ViewParent " + $r0 + " does not implement interface " + "method onNestedPreScroll", $r3);
        }
    }

    public static boolean onNestedFling(ViewParent $r0, View $r1, float $f0, float $f1, boolean $z0) throws  {
        try {
            return $r0.onNestedFling($r1, $f0, $f1, $z0);
        } catch (AbstractMethodError $r2) {
            Log.e(TAG, "ViewParent " + $r0 + " does not implement interface " + "method onNestedFling", $r2);
            return false;
        }
    }

    public static boolean onNestedPreFling(ViewParent $r0, View $r1, float $f0, float $f1) throws  {
        try {
            return $r0.onNestedPreFling($r1, $f0, $f1);
        } catch (AbstractMethodError $r2) {
            Log.e(TAG, "ViewParent " + $r0 + " does not implement interface " + "method onNestedPreFling", $r2);
            return false;
        }
    }
}
