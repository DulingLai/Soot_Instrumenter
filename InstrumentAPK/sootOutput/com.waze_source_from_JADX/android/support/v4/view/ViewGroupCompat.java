package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

public final class ViewGroupCompat {
    static final ViewGroupCompatImpl IMPL;
    public static final int LAYOUT_MODE_CLIP_BOUNDS = 0;
    public static final int LAYOUT_MODE_OPTICAL_BOUNDS = 1;

    interface ViewGroupCompatImpl {
        int getLayoutMode(ViewGroup viewGroup) throws ;

        int getNestedScrollAxes(ViewGroup viewGroup) throws ;

        boolean isTransitionGroup(ViewGroup viewGroup) throws ;

        boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) throws ;

        void setLayoutMode(ViewGroup viewGroup, int i) throws ;

        void setMotionEventSplittingEnabled(ViewGroup viewGroup, boolean z) throws ;

        void setTransitionGroup(ViewGroup viewGroup, boolean z) throws ;
    }

    static class ViewGroupCompatStubImpl implements ViewGroupCompatImpl {
        public int getLayoutMode(ViewGroup group) throws  {
            return 0;
        }

        public boolean isTransitionGroup(ViewGroup group) throws  {
            return false;
        }

        public boolean onRequestSendAccessibilityEvent(ViewGroup group, View child, AccessibilityEvent event) throws  {
            return true;
        }

        ViewGroupCompatStubImpl() throws  {
        }

        public void setMotionEventSplittingEnabled(ViewGroup group, boolean split) throws  {
        }

        public void setLayoutMode(ViewGroup group, int mode) throws  {
        }

        public void setTransitionGroup(ViewGroup group, boolean isTransitionGroup) throws  {
        }

        public int getNestedScrollAxes(ViewGroup $r1) throws  {
            if ($r1 instanceof NestedScrollingParent) {
                return ((NestedScrollingParent) $r1).getNestedScrollAxes();
            }
            return 0;
        }
    }

    static class ViewGroupCompatHCImpl extends ViewGroupCompatStubImpl {
        ViewGroupCompatHCImpl() throws  {
        }

        public void setMotionEventSplittingEnabled(ViewGroup $r1, boolean $z0) throws  {
            ViewGroupCompatHC.setMotionEventSplittingEnabled($r1, $z0);
        }
    }

    static class ViewGroupCompatIcsImpl extends ViewGroupCompatHCImpl {
        ViewGroupCompatIcsImpl() throws  {
        }

        public boolean onRequestSendAccessibilityEvent(ViewGroup $r1, View $r2, AccessibilityEvent $r3) throws  {
            return ViewGroupCompatIcs.onRequestSendAccessibilityEvent($r1, $r2, $r3);
        }
    }

    static class ViewGroupCompatJellybeanMR2Impl extends ViewGroupCompatIcsImpl {
        ViewGroupCompatJellybeanMR2Impl() throws  {
        }

        public int getLayoutMode(ViewGroup $r1) throws  {
            return ViewGroupCompatJellybeanMR2.getLayoutMode($r1);
        }

        public void setLayoutMode(ViewGroup $r1, int $i0) throws  {
            ViewGroupCompatJellybeanMR2.setLayoutMode($r1, $i0);
        }
    }

    static class ViewGroupCompatLollipopImpl extends ViewGroupCompatJellybeanMR2Impl {
        ViewGroupCompatLollipopImpl() throws  {
        }

        public void setTransitionGroup(ViewGroup $r1, boolean $z0) throws  {
            ViewGroupCompatLollipop.setTransitionGroup($r1, $z0);
        }

        public boolean isTransitionGroup(ViewGroup $r1) throws  {
            return ViewGroupCompatLollipop.isTransitionGroup($r1);
        }

        public int getNestedScrollAxes(ViewGroup $r1) throws  {
            return ViewGroupCompatLollipop.getNestedScrollAxes($r1);
        }
    }

    static {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 21) {
            IMPL = new ViewGroupCompatLollipopImpl();
        } else if ($i0 >= 18) {
            IMPL = new ViewGroupCompatJellybeanMR2Impl();
        } else if ($i0 >= 14) {
            IMPL = new ViewGroupCompatIcsImpl();
        } else if ($i0 >= 11) {
            IMPL = new ViewGroupCompatHCImpl();
        } else {
            IMPL = new ViewGroupCompatStubImpl();
        }
    }

    private ViewGroupCompat() throws  {
    }

    public static boolean onRequestSendAccessibilityEvent(ViewGroup $r0, View $r1, AccessibilityEvent $r2) throws  {
        return IMPL.onRequestSendAccessibilityEvent($r0, $r1, $r2);
    }

    public static void setMotionEventSplittingEnabled(ViewGroup $r0, boolean $z0) throws  {
        IMPL.setMotionEventSplittingEnabled($r0, $z0);
    }

    public static int getLayoutMode(ViewGroup $r0) throws  {
        return IMPL.getLayoutMode($r0);
    }

    public static void setLayoutMode(ViewGroup $r0, int $i0) throws  {
        IMPL.setLayoutMode($r0, $i0);
    }

    public static void setTransitionGroup(ViewGroup $r0, boolean $z0) throws  {
        IMPL.setTransitionGroup($r0, $z0);
    }

    public static boolean isTransitionGroup(ViewGroup $r0) throws  {
        return IMPL.isTransitionGroup($r0);
    }

    public static int getNestedScrollAxes(ViewGroup $r0) throws  {
        return IMPL.getNestedScrollAxes($r0);
    }
}
