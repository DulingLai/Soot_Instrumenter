package android.support.v4.view;

import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

class AccessibilityDelegateCompatIcs {

    public interface AccessibilityDelegateBridge {
        boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) throws ;

        void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) throws ;

        void onInitializeAccessibilityNodeInfo(View view, Object obj) throws ;

        void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) throws ;

        boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) throws ;

        void sendAccessibilityEvent(View view, int i) throws ;

        void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) throws ;
    }

    AccessibilityDelegateCompatIcs() throws  {
    }

    public static Object newAccessibilityDelegateDefaultImpl() throws  {
        return new AccessibilityDelegate();
    }

    public static Object newAccessibilityDelegateBridge(final AccessibilityDelegateBridge $r0) throws  {
        return new AccessibilityDelegate() {
            public boolean dispatchPopulateAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
                return $r0.dispatchPopulateAccessibilityEvent($r1, $r2);
            }

            public void onInitializeAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
                $r0.onInitializeAccessibilityEvent($r1, $r2);
            }

            public void onInitializeAccessibilityNodeInfo(View $r1, AccessibilityNodeInfo $r2) throws  {
                $r0.onInitializeAccessibilityNodeInfo($r1, $r2);
            }

            public void onPopulateAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
                $r0.onPopulateAccessibilityEvent($r1, $r2);
            }

            public boolean onRequestSendAccessibilityEvent(ViewGroup $r1, View $r2, AccessibilityEvent $r3) throws  {
                return $r0.onRequestSendAccessibilityEvent($r1, $r2, $r3);
            }

            public void sendAccessibilityEvent(View $r1, int $i0) throws  {
                $r0.sendAccessibilityEvent($r1, $i0);
            }

            public void sendAccessibilityEventUnchecked(View $r1, AccessibilityEvent $r2) throws  {
                $r0.sendAccessibilityEventUnchecked($r1, $r2);
            }
        };
    }

    public static boolean dispatchPopulateAccessibilityEvent(Object $r2, View $r0, AccessibilityEvent $r1) throws  {
        return ((AccessibilityDelegate) $r2).dispatchPopulateAccessibilityEvent($r0, $r1);
    }

    public static void onInitializeAccessibilityEvent(Object $r2, View $r0, AccessibilityEvent $r1) throws  {
        ((AccessibilityDelegate) $r2).onInitializeAccessibilityEvent($r0, $r1);
    }

    public static void onInitializeAccessibilityNodeInfo(Object $r1, View $r0, Object $r2) throws  {
        ((AccessibilityDelegate) $r1).onInitializeAccessibilityNodeInfo($r0, (AccessibilityNodeInfo) $r2);
    }

    public static void onPopulateAccessibilityEvent(Object $r2, View $r0, AccessibilityEvent $r1) throws  {
        ((AccessibilityDelegate) $r2).onPopulateAccessibilityEvent($r0, $r1);
    }

    public static boolean onRequestSendAccessibilityEvent(Object $r3, ViewGroup $r0, View $r1, AccessibilityEvent $r2) throws  {
        return ((AccessibilityDelegate) $r3).onRequestSendAccessibilityEvent($r0, $r1, $r2);
    }

    public static void sendAccessibilityEvent(Object $r1, View $r0, int $i0) throws  {
        ((AccessibilityDelegate) $r1).sendAccessibilityEvent($r0, $i0);
    }

    public static void sendAccessibilityEventUnchecked(Object $r2, View $r0, AccessibilityEvent $r1) throws  {
        ((AccessibilityDelegate) $r2).sendAccessibilityEventUnchecked($r0, $r1);
    }
}
