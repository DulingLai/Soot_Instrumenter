package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

public final class ViewParentCompat {
    static final ViewParentCompatImpl IMPL;

    interface ViewParentCompatImpl {
        void notifySubtreeAccessibilityStateChanged(ViewParent viewParent, View view, View view2, int i) throws ;

        boolean onNestedFling(ViewParent viewParent, View view, float f, float f2, boolean z) throws ;

        boolean onNestedPreFling(ViewParent viewParent, View view, float f, float f2) throws ;

        void onNestedPreScroll(ViewParent viewParent, View view, int i, int i2, int[] iArr) throws ;

        void onNestedScroll(ViewParent viewParent, View view, int i, int i2, int i3, int i4) throws ;

        void onNestedScrollAccepted(ViewParent viewParent, View view, View view2, int i) throws ;

        boolean onStartNestedScroll(ViewParent viewParent, View view, View view2, int i) throws ;

        void onStopNestedScroll(ViewParent viewParent, View view) throws ;

        boolean requestSendAccessibilityEvent(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent) throws ;
    }

    static class ViewParentCompatStubImpl implements ViewParentCompatImpl {
        ViewParentCompatStubImpl() throws  {
        }

        public boolean requestSendAccessibilityEvent(ViewParent parent, View $r2, AccessibilityEvent $r3) throws  {
            if ($r2 == null) {
                return false;
            }
            ((AccessibilityManager) $r2.getContext().getSystemService("accessibility")).sendAccessibilityEvent($r3);
            return true;
        }

        public boolean onStartNestedScroll(ViewParent $r3, View $r1, View $r2, int $i0) throws  {
            if ($r3 instanceof NestedScrollingParent) {
                return ((NestedScrollingParent) $r3).onStartNestedScroll($r1, $r2, $i0);
            }
            return false;
        }

        public void onNestedScrollAccepted(ViewParent $r3, View $r1, View $r2, int $i0) throws  {
            if ($r3 instanceof NestedScrollingParent) {
                ((NestedScrollingParent) $r3).onNestedScrollAccepted($r1, $r2, $i0);
            }
        }

        public void onStopNestedScroll(ViewParent $r2, View $r1) throws  {
            if ($r2 instanceof NestedScrollingParent) {
                ((NestedScrollingParent) $r2).onStopNestedScroll($r1);
            }
        }

        public void onNestedScroll(ViewParent $r1, View $r2, int $i0, int $i1, int $i2, int $i3) throws  {
            if ($r1 instanceof NestedScrollingParent) {
                ((NestedScrollingParent) $r1).onNestedScroll($r2, $i0, $i1, $i2, $i3);
            }
        }

        public void onNestedPreScroll(ViewParent $r3, View $r1, int $i0, int $i1, int[] $r2) throws  {
            if ($r3 instanceof NestedScrollingParent) {
                ((NestedScrollingParent) $r3).onNestedPreScroll($r1, $i0, $i1, $r2);
            }
        }

        public boolean onNestedFling(ViewParent $r2, View $r1, float $f0, float $f1, boolean $z0) throws  {
            if ($r2 instanceof NestedScrollingParent) {
                return ((NestedScrollingParent) $r2).onNestedFling($r1, $f0, $f1, $z0);
            }
            return false;
        }

        public boolean onNestedPreFling(ViewParent $r2, View $r1, float $f0, float $f1) throws  {
            if ($r2 instanceof NestedScrollingParent) {
                return ((NestedScrollingParent) $r2).onNestedPreFling($r1, $f0, $f1);
            }
            return false;
        }

        public void notifySubtreeAccessibilityStateChanged(ViewParent parent, View child, View source, int changeType) throws  {
        }
    }

    static class ViewParentCompatICSImpl extends ViewParentCompatStubImpl {
        ViewParentCompatICSImpl() throws  {
        }

        public boolean requestSendAccessibilityEvent(ViewParent $r1, View $r2, AccessibilityEvent $r3) throws  {
            return ViewParentCompatICS.requestSendAccessibilityEvent($r1, $r2, $r3);
        }
    }

    static class ViewParentCompatKitKatImpl extends ViewParentCompatICSImpl {
        ViewParentCompatKitKatImpl() throws  {
        }

        public void notifySubtreeAccessibilityStateChanged(ViewParent $r1, View $r2, View $r3, int $i0) throws  {
            ViewParentCompatKitKat.notifySubtreeAccessibilityStateChanged($r1, $r2, $r3, $i0);
        }
    }

    static class ViewParentCompatLollipopImpl extends ViewParentCompatKitKatImpl {
        ViewParentCompatLollipopImpl() throws  {
        }

        public boolean onStartNestedScroll(ViewParent $r1, View $r2, View $r3, int $i0) throws  {
            return ViewParentCompatLollipop.onStartNestedScroll($r1, $r2, $r3, $i0);
        }

        public void onNestedScrollAccepted(ViewParent $r1, View $r2, View $r3, int $i0) throws  {
            ViewParentCompatLollipop.onNestedScrollAccepted($r1, $r2, $r3, $i0);
        }

        public void onStopNestedScroll(ViewParent $r1, View $r2) throws  {
            ViewParentCompatLollipop.onStopNestedScroll($r1, $r2);
        }

        public void onNestedScroll(ViewParent $r1, View $r2, int $i0, int $i1, int $i2, int $i3) throws  {
            ViewParentCompatLollipop.onNestedScroll($r1, $r2, $i0, $i1, $i2, $i3);
        }

        public void onNestedPreScroll(ViewParent $r1, View $r2, int $i0, int $i1, int[] $r3) throws  {
            ViewParentCompatLollipop.onNestedPreScroll($r1, $r2, $i0, $i1, $r3);
        }

        public boolean onNestedFling(ViewParent $r1, View $r2, float $f0, float $f1, boolean $z0) throws  {
            return ViewParentCompatLollipop.onNestedFling($r1, $r2, $f0, $f1, $z0);
        }

        public boolean onNestedPreFling(ViewParent $r1, View $r2, float $f0, float $f1) throws  {
            return ViewParentCompatLollipop.onNestedPreFling($r1, $r2, $f0, $f1);
        }
    }

    static {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 21) {
            IMPL = new ViewParentCompatLollipopImpl();
        } else if ($i0 >= 19) {
            IMPL = new ViewParentCompatKitKatImpl();
        } else if ($i0 >= 14) {
            IMPL = new ViewParentCompatICSImpl();
        } else {
            IMPL = new ViewParentCompatStubImpl();
        }
    }

    private ViewParentCompat() throws  {
    }

    public static boolean requestSendAccessibilityEvent(ViewParent $r0, View $r1, AccessibilityEvent $r2) throws  {
        return IMPL.requestSendAccessibilityEvent($r0, $r1, $r2);
    }

    public static boolean onStartNestedScroll(ViewParent $r0, View $r1, View $r2, int $i0) throws  {
        return IMPL.onStartNestedScroll($r0, $r1, $r2, $i0);
    }

    public static void onNestedScrollAccepted(ViewParent $r0, View $r1, View $r2, int $i0) throws  {
        IMPL.onNestedScrollAccepted($r0, $r1, $r2, $i0);
    }

    public static void onStopNestedScroll(ViewParent $r0, View $r1) throws  {
        IMPL.onStopNestedScroll($r0, $r1);
    }

    public static void onNestedScroll(ViewParent $r0, View $r1, int $i0, int $i1, int $i2, int $i3) throws  {
        IMPL.onNestedScroll($r0, $r1, $i0, $i1, $i2, $i3);
    }

    public static void onNestedPreScroll(ViewParent $r0, View $r1, int $i0, int $i1, int[] $r2) throws  {
        IMPL.onNestedPreScroll($r0, $r1, $i0, $i1, $r2);
    }

    public static boolean onNestedFling(ViewParent $r0, View $r1, float $f0, float $f1, boolean $z0) throws  {
        return IMPL.onNestedFling($r0, $r1, $f0, $f1, $z0);
    }

    public static boolean onNestedPreFling(ViewParent $r0, View $r1, float $f0, float $f1) throws  {
        return IMPL.onNestedPreFling($r0, $r1, $f0, $f1);
    }

    public static void notifySubtreeAccessibilityStateChanged(ViewParent $r0, View $r1, View $r2, int $i0) throws  {
        IMPL.notifySubtreeAccessibilityStateChanged($r0, $r1, $r2, $i0);
    }
}
