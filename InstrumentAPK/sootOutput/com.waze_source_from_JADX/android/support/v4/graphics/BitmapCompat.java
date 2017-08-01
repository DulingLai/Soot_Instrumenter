package android.support.v4.graphics;

import android.graphics.Bitmap;
import android.os.Build.VERSION;

public final class BitmapCompat {
    static final BitmapImpl IMPL;

    interface BitmapImpl {
        int getAllocationByteCount(Bitmap bitmap) throws ;

        boolean hasMipMap(Bitmap bitmap) throws ;

        void setHasMipMap(Bitmap bitmap, boolean z) throws ;
    }

    static class BaseBitmapImpl implements BitmapImpl {
        public boolean hasMipMap(Bitmap bitmap) throws  {
            return false;
        }

        BaseBitmapImpl() throws  {
        }

        public void setHasMipMap(Bitmap bitmap, boolean hasMipMap) throws  {
        }

        public int getAllocationByteCount(Bitmap $r1) throws  {
            return $r1.getRowBytes() * $r1.getHeight();
        }
    }

    static class HcMr1BitmapCompatImpl extends BaseBitmapImpl {
        HcMr1BitmapCompatImpl() throws  {
        }

        public int getAllocationByteCount(Bitmap $r1) throws  {
            return BitmapCompatHoneycombMr1.getAllocationByteCount($r1);
        }
    }

    static class JbMr2BitmapCompatImpl extends HcMr1BitmapCompatImpl {
        JbMr2BitmapCompatImpl() throws  {
        }

        public boolean hasMipMap(Bitmap $r1) throws  {
            return BitmapCompatJellybeanMR2.hasMipMap($r1);
        }

        public void setHasMipMap(Bitmap $r1, boolean $z0) throws  {
            BitmapCompatJellybeanMR2.setHasMipMap($r1, $z0);
        }
    }

    static class KitKatBitmapCompatImpl extends JbMr2BitmapCompatImpl {
        KitKatBitmapCompatImpl() throws  {
        }

        public int getAllocationByteCount(Bitmap $r1) throws  {
            return BitmapCompatKitKat.getAllocationByteCount($r1);
        }
    }

    static {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 19) {
            IMPL = new KitKatBitmapCompatImpl();
        } else if ($i0 >= 18) {
            IMPL = new JbMr2BitmapCompatImpl();
        } else if ($i0 >= 12) {
            IMPL = new HcMr1BitmapCompatImpl();
        } else {
            IMPL = new BaseBitmapImpl();
        }
    }

    public static boolean hasMipMap(Bitmap $r0) throws  {
        return IMPL.hasMipMap($r0);
    }

    public static void setHasMipMap(Bitmap $r0, boolean $z0) throws  {
        IMPL.setHasMipMap($r0, $z0);
    }

    public static int getAllocationByteCount(Bitmap $r0) throws  {
        return IMPL.getAllocationByteCount($r0);
    }

    private BitmapCompat() throws  {
    }
}
