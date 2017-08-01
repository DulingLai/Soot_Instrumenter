package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.VelocityTracker;

public final class VelocityTrackerCompat {
    static final VelocityTrackerVersionImpl IMPL;

    interface VelocityTrackerVersionImpl {
        float getXVelocity(VelocityTracker velocityTracker, int i) throws ;

        float getYVelocity(VelocityTracker velocityTracker, int i) throws ;
    }

    static class BaseVelocityTrackerVersionImpl implements VelocityTrackerVersionImpl {
        BaseVelocityTrackerVersionImpl() throws  {
        }

        public float getXVelocity(VelocityTracker $r1, int pointerId) throws  {
            return $r1.getXVelocity();
        }

        public float getYVelocity(VelocityTracker $r1, int pointerId) throws  {
            return $r1.getYVelocity();
        }
    }

    static class HoneycombVelocityTrackerVersionImpl implements VelocityTrackerVersionImpl {
        HoneycombVelocityTrackerVersionImpl() throws  {
        }

        public float getXVelocity(VelocityTracker $r1, int $i0) throws  {
            return VelocityTrackerCompatHoneycomb.getXVelocity($r1, $i0);
        }

        public float getYVelocity(VelocityTracker $r1, int $i0) throws  {
            return VelocityTrackerCompatHoneycomb.getYVelocity($r1, $i0);
        }
    }

    static {
        if (VERSION.SDK_INT >= 11) {
            IMPL = new HoneycombVelocityTrackerVersionImpl();
        } else {
            IMPL = new BaseVelocityTrackerVersionImpl();
        }
    }

    public static float getXVelocity(VelocityTracker $r0, int $i0) throws  {
        return IMPL.getXVelocity($r0, $i0);
    }

    public static float getYVelocity(VelocityTracker $r0, int $i0) throws  {
        return IMPL.getYVelocity($r0, $i0);
    }

    private VelocityTrackerCompat() throws  {
    }
}
