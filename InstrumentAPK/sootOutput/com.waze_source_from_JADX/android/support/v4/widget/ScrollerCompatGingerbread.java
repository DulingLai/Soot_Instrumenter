package android.support.v4.widget;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.OverScroller;

class ScrollerCompatGingerbread {
    ScrollerCompatGingerbread() throws  {
    }

    public static Object createScroller(Context $r0, Interpolator $r1) throws  {
        return $r1 != null ? new OverScroller($r0, $r1) : new OverScroller($r0);
    }

    public static boolean isFinished(Object $r0) throws  {
        return ((OverScroller) $r0).isFinished();
    }

    public static int getCurrX(Object $r0) throws  {
        return ((OverScroller) $r0).getCurrX();
    }

    public static int getCurrY(Object $r0) throws  {
        return ((OverScroller) $r0).getCurrY();
    }

    public static boolean computeScrollOffset(Object $r0) throws  {
        return ((OverScroller) $r0).computeScrollOffset();
    }

    public static void startScroll(Object $r0, int $i0, int $i1, int $i2, int $i3) throws  {
        ((OverScroller) $r0).startScroll($i0, $i1, $i2, $i3);
    }

    public static void startScroll(Object $r0, int $i0, int $i1, int $i2, int $i3, int $i4) throws  {
        ((OverScroller) $r0).startScroll($i0, $i1, $i2, $i3, $i4);
    }

    public static void fling(Object $r0, int $i0, int $i1, int $i2, int $i3, int $i4, int $i5, int $i6, int $i7) throws  {
        ((OverScroller) $r0).fling($i0, $i1, $i2, $i3, $i4, $i5, $i6, $i7);
    }

    public static void fling(Object $r0, int $i0, int $i1, int $i2, int $i3, int $i4, int $i5, int $i6, int $i7, int $i8, int $i9) throws  {
        ((OverScroller) $r0).fling($i0, $i1, $i2, $i3, $i4, $i5, $i6, $i7, $i8, $i9);
    }

    public static void abortAnimation(Object $r0) throws  {
        ((OverScroller) $r0).abortAnimation();
    }

    public static void notifyHorizontalEdgeReached(Object $r0, int $i0, int $i1, int $i2) throws  {
        ((OverScroller) $r0).notifyHorizontalEdgeReached($i0, $i1, $i2);
    }

    public static void notifyVerticalEdgeReached(Object $r0, int $i0, int $i1, int $i2) throws  {
        ((OverScroller) $r0).notifyVerticalEdgeReached($i0, $i1, $i2);
    }

    public static boolean isOverScrolled(Object $r0) throws  {
        return ((OverScroller) $r0).isOverScrolled();
    }

    public static int getFinalX(Object $r0) throws  {
        return ((OverScroller) $r0).getFinalX();
    }

    public static int getFinalY(Object $r0) throws  {
        return ((OverScroller) $r0).getFinalY();
    }

    public static boolean springBack(Object $r0, int $i0, int $i1, int $i2, int $i3, int $i4, int $i5) throws  {
        return ((OverScroller) $r0).springBack($i0, $i1, $i2, $i3, $i4, $i5);
    }
}
