package android.support.v4.view;

import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;

class ViewCompatJB {
    ViewCompatJB() throws  {
    }

    public static boolean hasTransientState(View $r0) throws  {
        return $r0.hasTransientState();
    }

    public static void setHasTransientState(View $r0, boolean $z0) throws  {
        $r0.setHasTransientState($z0);
    }

    public static void postInvalidateOnAnimation(View $r0) throws  {
        $r0.postInvalidateOnAnimation();
    }

    public static void postInvalidateOnAnimation(View $r0, int $i0, int $i1, int $i2, int $i3) throws  {
        $r0.postInvalidate($i0, $i1, $i2, $i3);
    }

    public static void postOnAnimation(View $r0, Runnable $r1) throws  {
        $r0.postOnAnimation($r1);
    }

    public static void postOnAnimationDelayed(View $r0, Runnable $r1, long $l0) throws  {
        $r0.postOnAnimationDelayed($r1, $l0);
    }

    public static int getImportantForAccessibility(View $r0) throws  {
        return $r0.getImportantForAccessibility();
    }

    public static void setImportantForAccessibility(View $r0, int $i0) throws  {
        $r0.setImportantForAccessibility($i0);
    }

    public static boolean performAccessibilityAction(View $r0, int $i0, Bundle $r1) throws  {
        return $r0.performAccessibilityAction($i0, $r1);
    }

    public static Object getAccessibilityNodeProvider(View $r0) throws  {
        return $r0.getAccessibilityNodeProvider();
    }

    public static ViewParent getParentForAccessibility(View $r0) throws  {
        return $r0.getParentForAccessibility();
    }

    public static int getMinimumWidth(View $r0) throws  {
        return $r0.getMinimumWidth();
    }

    public static int getMinimumHeight(View $r0) throws  {
        return $r0.getMinimumHeight();
    }

    public static void requestApplyInsets(View $r0) throws  {
        $r0.requestFitSystemWindows();
    }

    public static boolean getFitsSystemWindows(View $r0) throws  {
        return $r0.getFitsSystemWindows();
    }

    public static boolean hasOverlappingRendering(View $r0) throws  {
        return $r0.hasOverlappingRendering();
    }
}
