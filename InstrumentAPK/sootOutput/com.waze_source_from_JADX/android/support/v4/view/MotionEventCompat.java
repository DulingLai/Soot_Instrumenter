package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.MotionEvent;

public final class MotionEventCompat {
    public static final int ACTION_HOVER_ENTER = 9;
    public static final int ACTION_HOVER_EXIT = 10;
    public static final int ACTION_HOVER_MOVE = 7;
    public static final int ACTION_MASK = 255;
    public static final int ACTION_POINTER_DOWN = 5;
    public static final int ACTION_POINTER_INDEX_MASK = 65280;
    public static final int ACTION_POINTER_INDEX_SHIFT = 8;
    public static final int ACTION_POINTER_UP = 6;
    public static final int ACTION_SCROLL = 8;
    public static final int AXIS_BRAKE = 23;
    public static final int AXIS_DISTANCE = 24;
    public static final int AXIS_GAS = 22;
    public static final int AXIS_GENERIC_1 = 32;
    public static final int AXIS_GENERIC_10 = 41;
    public static final int AXIS_GENERIC_11 = 42;
    public static final int AXIS_GENERIC_12 = 43;
    public static final int AXIS_GENERIC_13 = 44;
    public static final int AXIS_GENERIC_14 = 45;
    public static final int AXIS_GENERIC_15 = 46;
    public static final int AXIS_GENERIC_16 = 47;
    public static final int AXIS_GENERIC_2 = 33;
    public static final int AXIS_GENERIC_3 = 34;
    public static final int AXIS_GENERIC_4 = 35;
    public static final int AXIS_GENERIC_5 = 36;
    public static final int AXIS_GENERIC_6 = 37;
    public static final int AXIS_GENERIC_7 = 38;
    public static final int AXIS_GENERIC_8 = 39;
    public static final int AXIS_GENERIC_9 = 40;
    public static final int AXIS_HAT_X = 15;
    public static final int AXIS_HAT_Y = 16;
    public static final int AXIS_HSCROLL = 10;
    public static final int AXIS_LTRIGGER = 17;
    public static final int AXIS_ORIENTATION = 8;
    public static final int AXIS_PRESSURE = 2;
    public static final int AXIS_RTRIGGER = 18;
    public static final int AXIS_RUDDER = 20;
    public static final int AXIS_RX = 12;
    public static final int AXIS_RY = 13;
    public static final int AXIS_RZ = 14;
    public static final int AXIS_SIZE = 3;
    public static final int AXIS_THROTTLE = 19;
    public static final int AXIS_TILT = 25;
    public static final int AXIS_TOOL_MAJOR = 6;
    public static final int AXIS_TOOL_MINOR = 7;
    public static final int AXIS_TOUCH_MAJOR = 4;
    public static final int AXIS_TOUCH_MINOR = 5;
    public static final int AXIS_VSCROLL = 9;
    public static final int AXIS_WHEEL = 21;
    public static final int AXIS_X = 0;
    public static final int AXIS_Y = 1;
    public static final int AXIS_Z = 11;
    static final MotionEventVersionImpl IMPL;

    interface MotionEventVersionImpl {
        int findPointerIndex(MotionEvent motionEvent, int i) throws ;

        float getAxisValue(MotionEvent motionEvent, int i) throws ;

        float getAxisValue(MotionEvent motionEvent, int i, int i2) throws ;

        int getPointerCount(MotionEvent motionEvent) throws ;

        int getPointerId(MotionEvent motionEvent, int i) throws ;

        int getSource(MotionEvent motionEvent) throws ;

        float getX(MotionEvent motionEvent, int i) throws ;

        float getY(MotionEvent motionEvent, int i) throws ;
    }

    static class BaseMotionEventVersionImpl implements MotionEventVersionImpl {
        public float getAxisValue(MotionEvent event, int axis) throws  {
            return 0.0f;
        }

        public float getAxisValue(MotionEvent event, int axis, int pointerIndex) throws  {
            return 0.0f;
        }

        public int getPointerCount(MotionEvent event) throws  {
            return 1;
        }

        public int getSource(MotionEvent event) throws  {
            return 0;
        }

        BaseMotionEventVersionImpl() throws  {
        }

        public int findPointerIndex(MotionEvent event, int $i0) throws  {
            return $i0 == 0 ? 0 : -1;
        }

        public int getPointerId(MotionEvent event, int $i0) throws  {
            if ($i0 == 0) {
                return 0;
            }
            throw new IndexOutOfBoundsException("Pre-Eclair does not support multiple pointers");
        }

        public float getX(MotionEvent $r1, int $i0) throws  {
            if ($i0 == 0) {
                return $r1.getX();
            }
            throw new IndexOutOfBoundsException("Pre-Eclair does not support multiple pointers");
        }

        public float getY(MotionEvent $r1, int $i0) throws  {
            if ($i0 == 0) {
                return $r1.getY();
            }
            throw new IndexOutOfBoundsException("Pre-Eclair does not support multiple pointers");
        }
    }

    static class EclairMotionEventVersionImpl extends BaseMotionEventVersionImpl {
        EclairMotionEventVersionImpl() throws  {
        }

        public int findPointerIndex(MotionEvent $r1, int $i0) throws  {
            return MotionEventCompatEclair.findPointerIndex($r1, $i0);
        }

        public int getPointerId(MotionEvent $r1, int $i0) throws  {
            return MotionEventCompatEclair.getPointerId($r1, $i0);
        }

        public float getX(MotionEvent $r1, int $i0) throws  {
            return MotionEventCompatEclair.getX($r1, $i0);
        }

        public float getY(MotionEvent $r1, int $i0) throws  {
            return MotionEventCompatEclair.getY($r1, $i0);
        }

        public int getPointerCount(MotionEvent $r1) throws  {
            return MotionEventCompatEclair.getPointerCount($r1);
        }
    }

    static class GingerbreadMotionEventVersionImpl extends EclairMotionEventVersionImpl {
        GingerbreadMotionEventVersionImpl() throws  {
        }

        public int getSource(MotionEvent $r1) throws  {
            return MotionEventCompatGingerbread.getSource($r1);
        }
    }

    static class HoneycombMr1MotionEventVersionImpl extends GingerbreadMotionEventVersionImpl {
        HoneycombMr1MotionEventVersionImpl() throws  {
        }

        public float getAxisValue(MotionEvent $r1, int $i0) throws  {
            return MotionEventCompatHoneycombMr1.getAxisValue($r1, $i0);
        }

        public float getAxisValue(MotionEvent $r1, int $i0, int $i1) throws  {
            return MotionEventCompatHoneycombMr1.getAxisValue($r1, $i0, $i1);
        }
    }

    static {
        if (VERSION.SDK_INT >= 12) {
            IMPL = new HoneycombMr1MotionEventVersionImpl();
        } else if (VERSION.SDK_INT >= 9) {
            IMPL = new GingerbreadMotionEventVersionImpl();
        } else if (VERSION.SDK_INT >= 5) {
            IMPL = new EclairMotionEventVersionImpl();
        } else {
            IMPL = new BaseMotionEventVersionImpl();
        }
    }

    public static int getActionMasked(MotionEvent $r0) throws  {
        return $r0.getAction() & 255;
    }

    public static int getActionIndex(MotionEvent $r0) throws  {
        return ($r0.getAction() & ACTION_POINTER_INDEX_MASK) >> 8;
    }

    public static int findPointerIndex(MotionEvent $r0, int $i0) throws  {
        return IMPL.findPointerIndex($r0, $i0);
    }

    public static int getPointerId(MotionEvent $r0, int $i0) throws  {
        return IMPL.getPointerId($r0, $i0);
    }

    public static float getX(MotionEvent $r0, int $i0) throws  {
        return IMPL.getX($r0, $i0);
    }

    public static float getY(MotionEvent $r0, int $i0) throws  {
        return IMPL.getY($r0, $i0);
    }

    public static int getPointerCount(MotionEvent $r0) throws  {
        return IMPL.getPointerCount($r0);
    }

    public static int getSource(MotionEvent $r0) throws  {
        return IMPL.getSource($r0);
    }

    public static float getAxisValue(MotionEvent $r0, int $i0) throws  {
        return IMPL.getAxisValue($r0, $i0);
    }

    public static float getAxisValue(MotionEvent $r0, int $i0, int $i1) throws  {
        return IMPL.getAxisValue($r0, $i0, $i1);
    }

    private MotionEventCompat() throws  {
    }
}
