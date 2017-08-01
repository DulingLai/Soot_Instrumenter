package android.support.v4.view;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.Gravity;

public final class GravityCompat {
    public static final int END = 8388613;
    static final GravityCompatImpl IMPL;
    public static final int RELATIVE_HORIZONTAL_GRAVITY_MASK = 8388615;
    public static final int RELATIVE_LAYOUT_DIRECTION = 8388608;
    public static final int START = 8388611;

    interface GravityCompatImpl {
        void apply(int i, int i2, int i3, Rect rect, int i4, int i5, Rect rect2, int i6) throws ;

        void apply(int i, int i2, int i3, Rect rect, Rect rect2, int i4) throws ;

        void applyDisplay(int i, Rect rect, Rect rect2, int i2) throws ;

        int getAbsoluteGravity(int i, int i2) throws ;
    }

    static class GravityCompatImplBase implements GravityCompatImpl {
        public int getAbsoluteGravity(int $i0, int layoutDirection) throws  {
            return -8388609 & $i0;
        }

        GravityCompatImplBase() throws  {
        }

        public void apply(int $i0, int $i1, int $i2, Rect $r1, Rect $r2, int layoutDirection) throws  {
            Gravity.apply($i0, $i1, $i2, $r1, $r2);
        }

        public void apply(int $i0, int $i1, int $i2, Rect $r1, int $i3, int $i4, Rect $r2, int layoutDirection) throws  {
            Gravity.apply($i0, $i1, $i2, $r1, $i3, $i4, $r2);
        }

        public void applyDisplay(int $i0, Rect $r1, Rect $r2, int layoutDirection) throws  {
            Gravity.applyDisplay($i0, $r1, $r2);
        }
    }

    static class GravityCompatImplJellybeanMr1 implements GravityCompatImpl {
        GravityCompatImplJellybeanMr1() throws  {
        }

        public int getAbsoluteGravity(int $i0, int $i1) throws  {
            return GravityCompatJellybeanMr1.getAbsoluteGravity($i0, $i1);
        }

        public void apply(int $i0, int $i1, int $i2, Rect $r1, Rect $r2, int $i3) throws  {
            GravityCompatJellybeanMr1.apply($i0, $i1, $i2, $r1, $r2, $i3);
        }

        public void apply(int $i0, int $i1, int $i2, Rect $r1, int $i3, int $i4, Rect $r2, int $i5) throws  {
            GravityCompatJellybeanMr1.apply($i0, $i1, $i2, $r1, $i3, $i4, $r2, $i5);
        }

        public void applyDisplay(int $i0, Rect $r1, Rect $r2, int $i1) throws  {
            GravityCompatJellybeanMr1.applyDisplay($i0, $r1, $r2, $i1);
        }
    }

    static {
        if (VERSION.SDK_INT >= 17) {
            IMPL = new GravityCompatImplJellybeanMr1();
        } else {
            IMPL = new GravityCompatImplBase();
        }
    }

    public static void apply(int $i0, int $i1, int $i2, Rect $r0, Rect $r1, int $i3) throws  {
        IMPL.apply($i0, $i1, $i2, $r0, $r1, $i3);
    }

    public static void apply(int $i0, int $i1, int $i2, Rect $r0, int $i3, int $i4, Rect $r1, int $i5) throws  {
        IMPL.apply($i0, $i1, $i2, $r0, $i3, $i4, $r1, $i5);
    }

    public static void applyDisplay(int $i0, Rect $r0, Rect $r1, int $i1) throws  {
        IMPL.applyDisplay($i0, $r0, $r1, $i1);
    }

    public static int getAbsoluteGravity(int $i0, int $i1) throws  {
        return IMPL.getAbsoluteGravity($i0, $i1);
    }

    private GravityCompat() throws  {
    }
}
