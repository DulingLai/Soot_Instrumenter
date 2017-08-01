package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.ViewGroup.MarginLayoutParams;

public final class MarginLayoutParamsCompat {
    static final MarginLayoutParamsCompatImpl IMPL;

    interface MarginLayoutParamsCompatImpl {
        int getLayoutDirection(MarginLayoutParams marginLayoutParams) throws ;

        int getMarginEnd(MarginLayoutParams marginLayoutParams) throws ;

        int getMarginStart(MarginLayoutParams marginLayoutParams) throws ;

        boolean isMarginRelative(MarginLayoutParams marginLayoutParams) throws ;

        void resolveLayoutDirection(MarginLayoutParams marginLayoutParams, int i) throws ;

        void setLayoutDirection(MarginLayoutParams marginLayoutParams, int i) throws ;

        void setMarginEnd(MarginLayoutParams marginLayoutParams, int i) throws ;

        void setMarginStart(MarginLayoutParams marginLayoutParams, int i) throws ;
    }

    static class MarginLayoutParamsCompatImplBase implements MarginLayoutParamsCompatImpl {
        public int getLayoutDirection(MarginLayoutParams lp) throws  {
            return 0;
        }

        public boolean isMarginRelative(MarginLayoutParams lp) throws  {
            return false;
        }

        MarginLayoutParamsCompatImplBase() throws  {
        }

        public int getMarginStart(MarginLayoutParams $r1) throws  {
            return $r1.leftMargin;
        }

        public int getMarginEnd(MarginLayoutParams $r1) throws  {
            return $r1.rightMargin;
        }

        public void setMarginStart(MarginLayoutParams $r1, int $i0) throws  {
            $r1.leftMargin = $i0;
        }

        public void setMarginEnd(MarginLayoutParams $r1, int $i0) throws  {
            $r1.rightMargin = $i0;
        }

        public void setLayoutDirection(MarginLayoutParams lp, int layoutDirection) throws  {
        }

        public void resolveLayoutDirection(MarginLayoutParams lp, int layoutDirection) throws  {
        }
    }

    static class MarginLayoutParamsCompatImplJbMr1 implements MarginLayoutParamsCompatImpl {
        MarginLayoutParamsCompatImplJbMr1() throws  {
        }

        public int getMarginStart(MarginLayoutParams $r1) throws  {
            return MarginLayoutParamsCompatJellybeanMr1.getMarginStart($r1);
        }

        public int getMarginEnd(MarginLayoutParams $r1) throws  {
            return MarginLayoutParamsCompatJellybeanMr1.getMarginEnd($r1);
        }

        public void setMarginStart(MarginLayoutParams $r1, int $i0) throws  {
            MarginLayoutParamsCompatJellybeanMr1.setMarginStart($r1, $i0);
        }

        public void setMarginEnd(MarginLayoutParams $r1, int $i0) throws  {
            MarginLayoutParamsCompatJellybeanMr1.setMarginEnd($r1, $i0);
        }

        public boolean isMarginRelative(MarginLayoutParams $r1) throws  {
            return MarginLayoutParamsCompatJellybeanMr1.isMarginRelative($r1);
        }

        public int getLayoutDirection(MarginLayoutParams $r1) throws  {
            return MarginLayoutParamsCompatJellybeanMr1.getLayoutDirection($r1);
        }

        public void setLayoutDirection(MarginLayoutParams $r1, int $i0) throws  {
            MarginLayoutParamsCompatJellybeanMr1.setLayoutDirection($r1, $i0);
        }

        public void resolveLayoutDirection(MarginLayoutParams $r1, int $i0) throws  {
            MarginLayoutParamsCompatJellybeanMr1.resolveLayoutDirection($r1, $i0);
        }
    }

    static {
        if (VERSION.SDK_INT >= 17) {
            IMPL = new MarginLayoutParamsCompatImplJbMr1();
        } else {
            IMPL = new MarginLayoutParamsCompatImplBase();
        }
    }

    public static int getMarginStart(MarginLayoutParams $r0) throws  {
        return IMPL.getMarginStart($r0);
    }

    public static int getMarginEnd(MarginLayoutParams $r0) throws  {
        return IMPL.getMarginEnd($r0);
    }

    public static void setMarginStart(MarginLayoutParams $r0, int $i0) throws  {
        IMPL.setMarginStart($r0, $i0);
    }

    public static void setMarginEnd(MarginLayoutParams $r0, int $i0) throws  {
        IMPL.setMarginEnd($r0, $i0);
    }

    public static boolean isMarginRelative(MarginLayoutParams $r0) throws  {
        return IMPL.isMarginRelative($r0);
    }

    public static int getLayoutDirection(MarginLayoutParams $r0) throws  {
        int $i0 = IMPL.getLayoutDirection($r0);
        return ($i0 == 0 || $i0 == 1) ? $i0 : 0;
    }

    public static void setLayoutDirection(MarginLayoutParams $r0, int $i0) throws  {
        IMPL.setLayoutDirection($r0, $i0);
    }

    public static void resolveLayoutDirection(MarginLayoutParams $r0, int $i0) throws  {
        IMPL.resolveLayoutDirection($r0, $i0);
    }

    private MarginLayoutParamsCompat() throws  {
    }
}
