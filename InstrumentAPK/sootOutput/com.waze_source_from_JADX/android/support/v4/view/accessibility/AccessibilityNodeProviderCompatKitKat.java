package android.support.v4.view.accessibility;

import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import dalvik.annotation.Signature;
import java.util.List;

class AccessibilityNodeProviderCompatKitKat {

    interface AccessibilityNodeInfoBridge {
        Object createAccessibilityNodeInfo(int i) throws ;

        List<Object> findAccessibilityNodeInfosByText(@Signature({"(", "Ljava/lang/String;", "I)", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) String str, @Signature({"(", "Ljava/lang/String;", "I)", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) int i) throws ;

        Object findFocus(int i) throws ;

        boolean performAction(int i, int i2, Bundle bundle) throws ;
    }

    AccessibilityNodeProviderCompatKitKat() throws  {
    }

    public static Object newAccessibilityNodeProviderBridge(final AccessibilityNodeInfoBridge $r0) throws  {
        return new AccessibilityNodeProvider() {
            public AccessibilityNodeInfo createAccessibilityNodeInfo(int $i0) throws  {
                return (AccessibilityNodeInfo) $r0.createAccessibilityNodeInfo($i0);
            }

            public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(@Signature({"(", "Ljava/lang/String;", "I)", "Ljava/util/List", "<", "Landroid/view/accessibility/AccessibilityNodeInfo;", ">;"}) String $r1, @Signature({"(", "Ljava/lang/String;", "I)", "Ljava/util/List", "<", "Landroid/view/accessibility/AccessibilityNodeInfo;", ">;"}) int $i0) throws  {
                return $r0.findAccessibilityNodeInfosByText($r1, $i0);
            }

            public boolean performAction(int $i0, int $i1, Bundle $r1) throws  {
                return $r0.performAction($i0, $i1, $r1);
            }

            public AccessibilityNodeInfo findFocus(int $i0) throws  {
                return (AccessibilityNodeInfo) $r0.findFocus($i0);
            }
        };
    }
}
