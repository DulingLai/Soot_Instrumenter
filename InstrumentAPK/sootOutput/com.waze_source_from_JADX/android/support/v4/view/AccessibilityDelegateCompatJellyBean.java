package android.support.v4.view;

import android.os.Bundle;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;

class AccessibilityDelegateCompatJellyBean {

    public interface AccessibilityDelegateBridgeJellyBean {
        boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) throws ;

        Object getAccessibilityNodeProvider(View view) throws ;

        void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) throws ;

        void onInitializeAccessibilityNodeInfo(View view, Object obj) throws ;

        void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) throws ;

        boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) throws ;

        boolean performAccessibilityAction(View view, int i, Bundle bundle) throws ;

        void sendAccessibilityEvent(View view, int i) throws ;

        void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) throws ;
    }

    AccessibilityDelegateCompatJellyBean() throws  {
    }

    public static Object newAccessibilityDelegateBridge(final AccessibilityDelegateBridgeJellyBean $r0) throws  {
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

            public AccessibilityNodeProvider getAccessibilityNodeProvider(View $r1) throws  {
                return (AccessibilityNodeProvider) $r0.getAccessibilityNodeProvider($r1);
            }

            public boolean performAccessibilityAction(View $r1, int $i0, Bundle $r2) throws  {
                return $r0.performAccessibilityAction($r1, $i0, $r2);
            }
        };
    }

    public static Object getAccessibilityNodeProvider(Object $r2, View $r0) throws  {
        return ((AccessibilityDelegate) $r2).getAccessibilityNodeProvider($r0);
    }

    public static boolean performAccessibilityAction(Object $r2, View $r0, int $i0, Bundle $r1) throws  {
        return ((AccessibilityDelegate) $r2).performAccessibilityAction($r0, $i0, $r1);
    }
}
