package android.support.v4.view;

import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewParent;

class ViewCompatHC {
    ViewCompatHC() throws  {
    }

    static long getFrameTime() throws  {
        return ValueAnimator.getFrameDelay();
    }

    public static float getAlpha(View $r0) throws  {
        return $r0.getAlpha();
    }

    public static void setLayerType(View $r0, int $i0, Paint $r1) throws  {
        $r0.setLayerType($i0, $r1);
    }

    public static int getLayerType(View $r0) throws  {
        return $r0.getLayerType();
    }

    public static int resolveSizeAndState(int $i0, int $i1, int $i2) throws  {
        return View.resolveSizeAndState($i0, $i1, $i2);
    }

    public static int getMeasuredWidthAndState(View $r0) throws  {
        return $r0.getMeasuredWidthAndState();
    }

    public static int getMeasuredHeightAndState(View $r0) throws  {
        return $r0.getMeasuredHeightAndState();
    }

    public static int getMeasuredState(View $r0) throws  {
        return $r0.getMeasuredState();
    }

    public static float getTranslationX(View $r0) throws  {
        return $r0.getTranslationX();
    }

    public static float getTranslationY(View $r0) throws  {
        return $r0.getTranslationY();
    }

    public static float getX(View $r0) throws  {
        return $r0.getX();
    }

    public static float getY(View $r0) throws  {
        return $r0.getY();
    }

    public static float getRotation(View $r0) throws  {
        return $r0.getRotation();
    }

    public static float getRotationX(View $r0) throws  {
        return $r0.getRotationX();
    }

    public static float getRotationY(View $r0) throws  {
        return $r0.getRotationY();
    }

    public static float getScaleX(View $r0) throws  {
        return $r0.getScaleX();
    }

    public static float getScaleY(View $r0) throws  {
        return $r0.getScaleY();
    }

    public static void setTranslationX(View $r0, float $f0) throws  {
        $r0.setTranslationX($f0);
    }

    public static void setTranslationY(View $r0, float $f0) throws  {
        $r0.setTranslationY($f0);
    }

    public static void setAlpha(View $r0, float $f0) throws  {
        $r0.setAlpha($f0);
    }

    public static void setX(View $r0, float $f0) throws  {
        $r0.setX($f0);
    }

    public static void setY(View $r0, float $f0) throws  {
        $r0.setY($f0);
    }

    public static void setRotation(View $r0, float $f0) throws  {
        $r0.setRotation($f0);
    }

    public static void setRotationX(View $r0, float $f0) throws  {
        $r0.setRotationX($f0);
    }

    public static void setRotationY(View $r0, float $f0) throws  {
        $r0.setRotationY($f0);
    }

    public static void setScaleX(View $r0, float $f0) throws  {
        $r0.setScaleX($f0);
    }

    public static void setScaleY(View $r0, float $f0) throws  {
        $r0.setScaleY($f0);
    }

    public static void setPivotX(View $r0, float $f0) throws  {
        $r0.setPivotX($f0);
    }

    public static void setPivotY(View $r0, float $f0) throws  {
        $r0.setPivotY($f0);
    }

    public static float getPivotX(View $r0) throws  {
        return $r0.getPivotX();
    }

    public static float getPivotY(View $r0) throws  {
        return $r0.getPivotY();
    }

    public static void jumpDrawablesToCurrentState(View $r0) throws  {
        $r0.jumpDrawablesToCurrentState();
    }

    public static void setSaveFromParentEnabled(View $r0, boolean $z0) throws  {
        $r0.setSaveFromParentEnabled($z0);
    }

    public static void setActivated(View $r0, boolean $z0) throws  {
        $r0.setActivated($z0);
    }

    public static int combineMeasuredStates(int $i0, int $i1) throws  {
        return View.combineMeasuredStates($i0, $i1);
    }

    static void offsetTopAndBottom(View $r0, int $i0) throws  {
        $r0.offsetTopAndBottom($i0);
        tickleInvalidationFlag($r0);
        ViewParent $r1 = $r0.getParent();
        if ($r1 instanceof View) {
            tickleInvalidationFlag((View) $r1);
        }
    }

    static void offsetLeftAndRight(View $r0, int $i0) throws  {
        $r0.offsetLeftAndRight($i0);
        tickleInvalidationFlag($r0);
        ViewParent $r1 = $r0.getParent();
        if ($r1 instanceof View) {
            tickleInvalidationFlag((View) $r1);
        }
    }

    private static void tickleInvalidationFlag(View $r0) throws  {
        float $f0 = $r0.getTranslationY();
        $r0.setTranslationY(1.0f + $f0);
        $r0.setTranslationY($f0);
    }
}
