package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.LayoutInflater;

public final class LayoutInflaterCompat {
    static final LayoutInflaterCompatImpl IMPL;

    interface LayoutInflaterCompatImpl {
        LayoutInflaterFactory getFactory(LayoutInflater layoutInflater) throws ;

        void setFactory(LayoutInflater layoutInflater, LayoutInflaterFactory layoutInflaterFactory) throws ;
    }

    static class LayoutInflaterCompatImplBase implements LayoutInflaterCompatImpl {
        LayoutInflaterCompatImplBase() throws  {
        }

        public void setFactory(LayoutInflater $r1, LayoutInflaterFactory $r2) throws  {
            LayoutInflaterCompatBase.setFactory($r1, $r2);
        }

        public LayoutInflaterFactory getFactory(LayoutInflater $r1) throws  {
            return LayoutInflaterCompatBase.getFactory($r1);
        }
    }

    static class LayoutInflaterCompatImplV11 extends LayoutInflaterCompatImplBase {
        LayoutInflaterCompatImplV11() throws  {
        }

        public void setFactory(LayoutInflater $r1, LayoutInflaterFactory $r2) throws  {
            LayoutInflaterCompatHC.setFactory($r1, $r2);
        }
    }

    static class LayoutInflaterCompatImplV21 extends LayoutInflaterCompatImplV11 {
        LayoutInflaterCompatImplV21() throws  {
        }

        public void setFactory(LayoutInflater $r1, LayoutInflaterFactory $r2) throws  {
            LayoutInflaterCompatLollipop.setFactory($r1, $r2);
        }
    }

    static {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 21) {
            IMPL = new LayoutInflaterCompatImplV21();
        } else if ($i0 >= 11) {
            IMPL = new LayoutInflaterCompatImplV11();
        } else {
            IMPL = new LayoutInflaterCompatImplBase();
        }
    }

    private LayoutInflaterCompat() throws  {
    }

    public static void setFactory(LayoutInflater $r0, LayoutInflaterFactory $r1) throws  {
        IMPL.setFactory($r0, $r1);
    }

    public static LayoutInflaterFactory getFactory(LayoutInflater $r0) throws  {
        return IMPL.getFactory($r0);
    }
}
