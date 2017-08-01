package android.support.v4.view.accessibility;

import android.os.Build.VERSION;
import android.os.Bundle;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

public class AccessibilityNodeProviderCompat {
    private static final AccessibilityNodeProviderImpl IMPL;
    private final Object mProvider;

    interface AccessibilityNodeProviderImpl {
        Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) throws ;
    }

    static class AccessibilityNodeProviderStubImpl implements AccessibilityNodeProviderImpl {
        public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat compat) throws  {
            return null;
        }

        AccessibilityNodeProviderStubImpl() throws  {
        }
    }

    static class AccessibilityNodeProviderJellyBeanImpl extends AccessibilityNodeProviderStubImpl {
        AccessibilityNodeProviderJellyBeanImpl() throws  {
        }

        public Object newAccessibilityNodeProviderBridge(final AccessibilityNodeProviderCompat $r1) throws  {
            return AccessibilityNodeProviderCompatJellyBean.newAccessibilityNodeProviderBridge(new AccessibilityNodeInfoBridge() {
                public boolean performAction(int $i0, int $i1, Bundle $r1) throws  {
                    return $r1.performAction($i0, $i1, $r1);
                }

                public List<Object> findAccessibilityNodeInfosByText(@Signature({"(", "Ljava/lang/String;", "I)", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) String $r1, @Signature({"(", "Ljava/lang/String;", "I)", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) int $i0) throws  {
                    List $r4 = $r1.findAccessibilityNodeInfosByText($r1, $i0);
                    ArrayList $r2 = new ArrayList();
                    $i0 = $r4.size();
                    for (int $i1 = 0; $i1 < $i0; $i1++) {
                        $r2.add(((AccessibilityNodeInfoCompat) $r4.get($i1)).getInfo());
                    }
                    return $r2;
                }

                public Object createAccessibilityNodeInfo(int $i0) throws  {
                    AccessibilityNodeInfoCompat $r2 = $r1.createAccessibilityNodeInfo($i0);
                    return $r2 == null ? null : $r2.getInfo();
                }
            });
        }
    }

    static class AccessibilityNodeProviderKitKatImpl extends AccessibilityNodeProviderStubImpl {
        AccessibilityNodeProviderKitKatImpl() throws  {
        }

        public Object newAccessibilityNodeProviderBridge(final AccessibilityNodeProviderCompat $r1) throws  {
            return AccessibilityNodeProviderCompatKitKat.newAccessibilityNodeProviderBridge(new AccessibilityNodeInfoBridge() {
                public boolean performAction(int $i0, int $i1, Bundle $r1) throws  {
                    return $r1.performAction($i0, $i1, $r1);
                }

                public List<Object> findAccessibilityNodeInfosByText(@Signature({"(", "Ljava/lang/String;", "I)", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) String $r1, @Signature({"(", "Ljava/lang/String;", "I)", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) int $i0) throws  {
                    List $r4 = $r1.findAccessibilityNodeInfosByText($r1, $i0);
                    ArrayList $r2 = new ArrayList();
                    $i0 = $r4.size();
                    for (int $i1 = 0; $i1 < $i0; $i1++) {
                        $r2.add(((AccessibilityNodeInfoCompat) $r4.get($i1)).getInfo());
                    }
                    return $r2;
                }

                public Object createAccessibilityNodeInfo(int $i0) throws  {
                    AccessibilityNodeInfoCompat $r2 = $r1.createAccessibilityNodeInfo($i0);
                    return $r2 == null ? null : $r2.getInfo();
                }

                public Object findFocus(int $i0) throws  {
                    AccessibilityNodeInfoCompat $r2 = $r1.findFocus($i0);
                    return $r2 == null ? null : $r2.getInfo();
                }
            });
        }
    }

    public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int virtualViewId) throws  {
        return null;
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(@Signature({"(", "Ljava/lang/String;", "I)", "Ljava/util/List", "<", "Landroid/support/v4/view/accessibility/AccessibilityNodeInfoCompat;", ">;"}) String text, @Signature({"(", "Ljava/lang/String;", "I)", "Ljava/util/List", "<", "Landroid/support/v4/view/accessibility/AccessibilityNodeInfoCompat;", ">;"}) int virtualViewId) throws  {
        return null;
    }

    public AccessibilityNodeInfoCompat findFocus(int focus) throws  {
        return null;
    }

    public boolean performAction(int virtualViewId, int action, Bundle arguments) throws  {
        return false;
    }

    static {
        if (VERSION.SDK_INT >= 19) {
            IMPL = new AccessibilityNodeProviderKitKatImpl();
        } else if (VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityNodeProviderJellyBeanImpl();
        } else {
            IMPL = new AccessibilityNodeProviderStubImpl();
        }
    }

    public AccessibilityNodeProviderCompat() throws  {
        this.mProvider = IMPL.newAccessibilityNodeProviderBridge(this);
    }

    public AccessibilityNodeProviderCompat(Object $r1) throws  {
        this.mProvider = $r1;
    }

    public Object getProvider() throws  {
        return this.mProvider;
    }
}
