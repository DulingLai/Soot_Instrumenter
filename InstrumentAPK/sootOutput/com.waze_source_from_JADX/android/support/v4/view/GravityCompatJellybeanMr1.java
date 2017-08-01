package android.support.v4.view;

import android.graphics.Rect;
import android.view.Gravity;

class GravityCompatJellybeanMr1 {
    GravityCompatJellybeanMr1() throws  {
    }

    public static int getAbsoluteGravity(int $i0, int $i1) throws  {
        return Gravity.getAbsoluteGravity($i0, $i1);
    }

    public static void apply(int $i0, int $i1, int $i2, Rect $r0, Rect $r1, int $i3) throws  {
        Gravity.apply($i0, $i1, $i2, $r0, $r1, $i3);
    }

    public static void apply(int $i0, int $i1, int $i2, Rect $r0, int $i3, int $i4, Rect $r1, int $i5) throws  {
        Gravity.apply($i0, $i1, $i2, $r0, $i3, $i4, $r1, $i5);
    }

    public static void applyDisplay(int $i0, Rect $r0, Rect $r1, int $i1) throws  {
        Gravity.applyDisplay($i0, $r0, $r1, $i1);
    }
}
