package android.support.v4.view;

import android.view.MotionEvent;

class MotionEventCompatEclair {
    MotionEventCompatEclair() throws  {
    }

    public static int findPointerIndex(MotionEvent $r0, int $i0) throws  {
        return $r0.findPointerIndex($i0);
    }

    public static int getPointerId(MotionEvent $r0, int $i0) throws  {
        return $r0.getPointerId($i0);
    }

    public static float getX(MotionEvent $r0, int $i0) throws  {
        return $r0.getX($i0);
    }

    public static float getY(MotionEvent $r0, int $i0) throws  {
        return $r0.getY($i0);
    }

    public static int getPointerCount(MotionEvent $r0) throws  {
        return $r0.getPointerCount();
    }
}
