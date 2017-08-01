package android.support.v4.view.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener;
import dalvik.annotation.Signature;
import java.util.List;

class AccessibilityManagerCompatIcs {

    interface AccessibilityStateChangeListenerBridge {
        void onAccessibilityStateChanged(boolean z) throws ;
    }

    AccessibilityManagerCompatIcs() throws  {
    }

    public static Object newAccessibilityStateChangeListener(final AccessibilityStateChangeListenerBridge $r0) throws  {
        return new AccessibilityStateChangeListener() {
            public void onAccessibilityStateChanged(boolean $z0) throws  {
                $r0.onAccessibilityStateChanged($z0);
            }
        };
    }

    public static boolean addAccessibilityStateChangeListener(AccessibilityManager $r0, Object $r1) throws  {
        return $r0.addAccessibilityStateChangeListener((AccessibilityStateChangeListener) $r1);
    }

    public static boolean removeAccessibilityStateChangeListener(AccessibilityManager $r0, Object $r1) throws  {
        return $r0.removeAccessibilityStateChangeListener((AccessibilityStateChangeListener) $r1);
    }

    public static List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(@Signature({"(", "Landroid/view/accessibility/AccessibilityManager;", "I)", "Ljava/util/List", "<", "Landroid/accessibilityservice/AccessibilityServiceInfo;", ">;"}) AccessibilityManager $r0, @Signature({"(", "Landroid/view/accessibility/AccessibilityManager;", "I)", "Ljava/util/List", "<", "Landroid/accessibilityservice/AccessibilityServiceInfo;", ">;"}) int $i0) throws  {
        return $r0.getEnabledAccessibilityServiceList($i0);
    }

    public static List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(@Signature({"(", "Landroid/view/accessibility/AccessibilityManager;", ")", "Ljava/util/List", "<", "Landroid/accessibilityservice/AccessibilityServiceInfo;", ">;"}) AccessibilityManager $r0) throws  {
        return $r0.getInstalledAccessibilityServiceList();
    }

    public static boolean isTouchExplorationEnabled(AccessibilityManager $r0) throws  {
        return $r0.isTouchExplorationEnabled();
    }
}
