package android.support.v4.view.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityManager;
import dalvik.annotation.Signature;
import java.util.Collections;
import java.util.List;

public final class AccessibilityManagerCompat {
    private static final AccessibilityManagerVersionImpl IMPL;

    interface AccessibilityManagerVersionImpl {
        boolean addAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListenerCompat accessibilityStateChangeListenerCompat) throws ;

        List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(@Signature({"(", "Landroid/view/accessibility/AccessibilityManager;", "I)", "Ljava/util/List", "<", "Landroid/accessibilityservice/AccessibilityServiceInfo;", ">;"}) AccessibilityManager accessibilityManager, @Signature({"(", "Landroid/view/accessibility/AccessibilityManager;", "I)", "Ljava/util/List", "<", "Landroid/accessibilityservice/AccessibilityServiceInfo;", ">;"}) int i) throws ;

        List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(@Signature({"(", "Landroid/view/accessibility/AccessibilityManager;", ")", "Ljava/util/List", "<", "Landroid/accessibilityservice/AccessibilityServiceInfo;", ">;"}) AccessibilityManager accessibilityManager) throws ;

        boolean isTouchExplorationEnabled(AccessibilityManager accessibilityManager) throws ;

        Object newAccessiblityStateChangeListener(AccessibilityStateChangeListenerCompat accessibilityStateChangeListenerCompat) throws ;

        boolean removeAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListenerCompat accessibilityStateChangeListenerCompat) throws ;
    }

    static class AccessibilityManagerStubImpl implements AccessibilityManagerVersionImpl {
        public boolean addAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListenerCompat listener) throws  {
            return false;
        }

        public boolean isTouchExplorationEnabled(AccessibilityManager manager) throws  {
            return false;
        }

        public Object newAccessiblityStateChangeListener(AccessibilityStateChangeListenerCompat listener) throws  {
            return null;
        }

        public boolean removeAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListenerCompat listener) throws  {
            return false;
        }

        AccessibilityManagerStubImpl() throws  {
        }

        public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(@Signature({"(", "Landroid/view/accessibility/AccessibilityManager;", "I)", "Ljava/util/List", "<", "Landroid/accessibilityservice/AccessibilityServiceInfo;", ">;"}) AccessibilityManager manager, @Signature({"(", "Landroid/view/accessibility/AccessibilityManager;", "I)", "Ljava/util/List", "<", "Landroid/accessibilityservice/AccessibilityServiceInfo;", ">;"}) int feedbackTypeFlags) throws  {
            return Collections.emptyList();
        }

        public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(@Signature({"(", "Landroid/view/accessibility/AccessibilityManager;", ")", "Ljava/util/List", "<", "Landroid/accessibilityservice/AccessibilityServiceInfo;", ">;"}) AccessibilityManager manager) throws  {
            return Collections.emptyList();
        }
    }

    static class AccessibilityManagerIcsImpl extends AccessibilityManagerStubImpl {
        AccessibilityManagerIcsImpl() throws  {
        }

        public Object newAccessiblityStateChangeListener(final AccessibilityStateChangeListenerCompat $r1) throws  {
            return AccessibilityManagerCompatIcs.newAccessibilityStateChangeListener(new AccessibilityStateChangeListenerBridge() {
                public void onAccessibilityStateChanged(boolean $z0) throws  {
                    $r1.onAccessibilityStateChanged($z0);
                }
            });
        }

        public boolean addAccessibilityStateChangeListener(AccessibilityManager $r1, AccessibilityStateChangeListenerCompat $r2) throws  {
            return AccessibilityManagerCompatIcs.addAccessibilityStateChangeListener($r1, $r2.mListener);
        }

        public boolean removeAccessibilityStateChangeListener(AccessibilityManager $r1, AccessibilityStateChangeListenerCompat $r2) throws  {
            return AccessibilityManagerCompatIcs.removeAccessibilityStateChangeListener($r1, $r2.mListener);
        }

        public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(@Signature({"(", "Landroid/view/accessibility/AccessibilityManager;", "I)", "Ljava/util/List", "<", "Landroid/accessibilityservice/AccessibilityServiceInfo;", ">;"}) AccessibilityManager $r1, @Signature({"(", "Landroid/view/accessibility/AccessibilityManager;", "I)", "Ljava/util/List", "<", "Landroid/accessibilityservice/AccessibilityServiceInfo;", ">;"}) int $i0) throws  {
            return AccessibilityManagerCompatIcs.getEnabledAccessibilityServiceList($r1, $i0);
        }

        public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(@Signature({"(", "Landroid/view/accessibility/AccessibilityManager;", ")", "Ljava/util/List", "<", "Landroid/accessibilityservice/AccessibilityServiceInfo;", ">;"}) AccessibilityManager $r1) throws  {
            return AccessibilityManagerCompatIcs.getInstalledAccessibilityServiceList($r1);
        }

        public boolean isTouchExplorationEnabled(AccessibilityManager $r1) throws  {
            return AccessibilityManagerCompatIcs.isTouchExplorationEnabled($r1);
        }
    }

    public static abstract class AccessibilityStateChangeListenerCompat {
        final Object mListener = AccessibilityManagerCompat.IMPL.newAccessiblityStateChangeListener(this);

        public abstract void onAccessibilityStateChanged(boolean z) throws ;
    }

    static {
        if (VERSION.SDK_INT >= 14) {
            IMPL = new AccessibilityManagerIcsImpl();
        } else {
            IMPL = new AccessibilityManagerStubImpl();
        }
    }

    public static boolean addAccessibilityStateChangeListener(AccessibilityManager $r0, AccessibilityStateChangeListenerCompat $r1) throws  {
        return IMPL.addAccessibilityStateChangeListener($r0, $r1);
    }

    public static boolean removeAccessibilityStateChangeListener(AccessibilityManager $r0, AccessibilityStateChangeListenerCompat $r1) throws  {
        return IMPL.removeAccessibilityStateChangeListener($r0, $r1);
    }

    public static List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(@Signature({"(", "Landroid/view/accessibility/AccessibilityManager;", ")", "Ljava/util/List", "<", "Landroid/accessibilityservice/AccessibilityServiceInfo;", ">;"}) AccessibilityManager $r0) throws  {
        return IMPL.getInstalledAccessibilityServiceList($r0);
    }

    public static List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(@Signature({"(", "Landroid/view/accessibility/AccessibilityManager;", "I)", "Ljava/util/List", "<", "Landroid/accessibilityservice/AccessibilityServiceInfo;", ">;"}) AccessibilityManager $r0, @Signature({"(", "Landroid/view/accessibility/AccessibilityManager;", "I)", "Ljava/util/List", "<", "Landroid/accessibilityservice/AccessibilityServiceInfo;", ">;"}) int $i0) throws  {
        return IMPL.getEnabledAccessibilityServiceList($r0, $i0);
    }

    public static boolean isTouchExplorationEnabled(AccessibilityManager $r0) throws  {
        return IMPL.isTouchExplorationEnabled($r0);
    }

    private AccessibilityManagerCompat() throws  {
    }
}
