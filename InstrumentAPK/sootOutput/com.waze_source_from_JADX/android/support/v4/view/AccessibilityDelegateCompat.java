package android.support.v4.view;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompatIcs.AccessibilityDelegateBridge;
import android.support.v4.view.AccessibilityDelegateCompatJellyBean.AccessibilityDelegateBridgeJellyBean;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

public class AccessibilityDelegateCompat {
    private static final Object DEFAULT_DELEGATE = IMPL.newAccessiblityDelegateDefaultImpl();
    private static final AccessibilityDelegateImpl IMPL;
    final Object mBridge = IMPL.newAccessiblityDelegateBridge(this);

    interface AccessibilityDelegateImpl {
        boolean dispatchPopulateAccessibilityEvent(Object obj, View view, AccessibilityEvent accessibilityEvent) throws ;

        AccessibilityNodeProviderCompat getAccessibilityNodeProvider(Object obj, View view) throws ;

        Object newAccessiblityDelegateBridge(AccessibilityDelegateCompat accessibilityDelegateCompat) throws ;

        Object newAccessiblityDelegateDefaultImpl() throws ;

        void onInitializeAccessibilityEvent(Object obj, View view, AccessibilityEvent accessibilityEvent) throws ;

        void onInitializeAccessibilityNodeInfo(Object obj, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) throws ;

        void onPopulateAccessibilityEvent(Object obj, View view, AccessibilityEvent accessibilityEvent) throws ;

        boolean onRequestSendAccessibilityEvent(Object obj, ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) throws ;

        boolean performAccessibilityAction(Object obj, View view, int i, Bundle bundle) throws ;

        void sendAccessibilityEvent(Object obj, View view, int i) throws ;

        void sendAccessibilityEventUnchecked(Object obj, View view, AccessibilityEvent accessibilityEvent) throws ;
    }

    static class AccessibilityDelegateStubImpl implements AccessibilityDelegateImpl {
        public boolean dispatchPopulateAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) throws  {
            return false;
        }

        public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(Object delegate, View host) throws  {
            return null;
        }

        public Object newAccessiblityDelegateBridge(AccessibilityDelegateCompat listener) throws  {
            return null;
        }

        public Object newAccessiblityDelegateDefaultImpl() throws  {
            return null;
        }

        public boolean onRequestSendAccessibilityEvent(Object delegate, ViewGroup host, View child, AccessibilityEvent event) throws  {
            return true;
        }

        public boolean performAccessibilityAction(Object delegate, View host, int action, Bundle args) throws  {
            return false;
        }

        AccessibilityDelegateStubImpl() throws  {
        }

        public void onInitializeAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) throws  {
        }

        public void onInitializeAccessibilityNodeInfo(Object delegate, View host, AccessibilityNodeInfoCompat info) throws  {
        }

        public void onPopulateAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) throws  {
        }

        public void sendAccessibilityEvent(Object delegate, View host, int eventType) throws  {
        }

        public void sendAccessibilityEventUnchecked(Object delegate, View host, AccessibilityEvent event) throws  {
        }
    }

    static class AccessibilityDelegateIcsImpl extends AccessibilityDelegateStubImpl {
        AccessibilityDelegateIcsImpl() throws  {
        }

        public Object newAccessiblityDelegateDefaultImpl() throws  {
            return AccessibilityDelegateCompatIcs.newAccessibilityDelegateDefaultImpl();
        }

        public Object newAccessiblityDelegateBridge(final AccessibilityDelegateCompat $r1) throws  {
            return AccessibilityDelegateCompatIcs.newAccessibilityDelegateBridge(new AccessibilityDelegateBridge() {
                public boolean dispatchPopulateAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
                    return $r1.dispatchPopulateAccessibilityEvent($r1, $r2);
                }

                public void onInitializeAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
                    $r1.onInitializeAccessibilityEvent($r1, $r2);
                }

                public void onInitializeAccessibilityNodeInfo(View $r1, Object $r2) throws  {
                    $r1.onInitializeAccessibilityNodeInfo($r1, new AccessibilityNodeInfoCompat($r2));
                }

                public void onPopulateAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
                    $r1.onPopulateAccessibilityEvent($r1, $r2);
                }

                public boolean onRequestSendAccessibilityEvent(ViewGroup $r1, View $r2, AccessibilityEvent $r3) throws  {
                    return $r1.onRequestSendAccessibilityEvent($r1, $r2, $r3);
                }

                public void sendAccessibilityEvent(View $r1, int $i0) throws  {
                    $r1.sendAccessibilityEvent($r1, $i0);
                }

                public void sendAccessibilityEventUnchecked(View $r1, AccessibilityEvent $r2) throws  {
                    $r1.sendAccessibilityEventUnchecked($r1, $r2);
                }
            });
        }

        public boolean dispatchPopulateAccessibilityEvent(Object $r1, View $r2, AccessibilityEvent $r3) throws  {
            return AccessibilityDelegateCompatIcs.dispatchPopulateAccessibilityEvent($r1, $r2, $r3);
        }

        public void onInitializeAccessibilityEvent(Object $r1, View $r2, AccessibilityEvent $r3) throws  {
            AccessibilityDelegateCompatIcs.onInitializeAccessibilityEvent($r1, $r2, $r3);
        }

        public void onInitializeAccessibilityNodeInfo(Object $r1, View $r2, AccessibilityNodeInfoCompat $r3) throws  {
            AccessibilityDelegateCompatIcs.onInitializeAccessibilityNodeInfo($r1, $r2, $r3.getInfo());
        }

        public void onPopulateAccessibilityEvent(Object $r1, View $r2, AccessibilityEvent $r3) throws  {
            AccessibilityDelegateCompatIcs.onPopulateAccessibilityEvent($r1, $r2, $r3);
        }

        public boolean onRequestSendAccessibilityEvent(Object $r1, ViewGroup $r2, View $r3, AccessibilityEvent $r4) throws  {
            return AccessibilityDelegateCompatIcs.onRequestSendAccessibilityEvent($r1, $r2, $r3, $r4);
        }

        public void sendAccessibilityEvent(Object $r1, View $r2, int $i0) throws  {
            AccessibilityDelegateCompatIcs.sendAccessibilityEvent($r1, $r2, $i0);
        }

        public void sendAccessibilityEventUnchecked(Object $r1, View $r2, AccessibilityEvent $r3) throws  {
            AccessibilityDelegateCompatIcs.sendAccessibilityEventUnchecked($r1, $r2, $r3);
        }
    }

    static class AccessibilityDelegateJellyBeanImpl extends AccessibilityDelegateIcsImpl {
        AccessibilityDelegateJellyBeanImpl() throws  {
        }

        public Object newAccessiblityDelegateBridge(final AccessibilityDelegateCompat $r1) throws  {
            return AccessibilityDelegateCompatJellyBean.newAccessibilityDelegateBridge(new AccessibilityDelegateBridgeJellyBean() {
                public boolean dispatchPopulateAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
                    return $r1.dispatchPopulateAccessibilityEvent($r1, $r2);
                }

                public void onInitializeAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
                    $r1.onInitializeAccessibilityEvent($r1, $r2);
                }

                public void onInitializeAccessibilityNodeInfo(View $r1, Object $r2) throws  {
                    $r1.onInitializeAccessibilityNodeInfo($r1, new AccessibilityNodeInfoCompat($r2));
                }

                public void onPopulateAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
                    $r1.onPopulateAccessibilityEvent($r1, $r2);
                }

                public boolean onRequestSendAccessibilityEvent(ViewGroup $r1, View $r2, AccessibilityEvent $r3) throws  {
                    return $r1.onRequestSendAccessibilityEvent($r1, $r2, $r3);
                }

                public void sendAccessibilityEvent(View $r1, int $i0) throws  {
                    $r1.sendAccessibilityEvent($r1, $i0);
                }

                public void sendAccessibilityEventUnchecked(View $r1, AccessibilityEvent $r2) throws  {
                    $r1.sendAccessibilityEventUnchecked($r1, $r2);
                }

                public Object getAccessibilityNodeProvider(View $r1) throws  {
                    AccessibilityNodeProviderCompat $r3 = $r1.getAccessibilityNodeProvider($r1);
                    return $r3 != null ? $r3.getProvider() : null;
                }

                public boolean performAccessibilityAction(View $r1, int $i0, Bundle $r2) throws  {
                    return $r1.performAccessibilityAction($r1, $i0, $r2);
                }
            });
        }

        public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(Object $r1, View $r2) throws  {
            $r1 = AccessibilityDelegateCompatJellyBean.getAccessibilityNodeProvider($r1, $r2);
            if ($r1 != null) {
                return new AccessibilityNodeProviderCompat($r1);
            }
            return null;
        }

        public boolean performAccessibilityAction(Object $r1, View $r2, int $i0, Bundle $r3) throws  {
            return AccessibilityDelegateCompatJellyBean.performAccessibilityAction($r1, $r2, $i0, $r3);
        }
    }

    static {
        if (VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityDelegateJellyBeanImpl();
        } else if (VERSION.SDK_INT >= 14) {
            IMPL = new AccessibilityDelegateIcsImpl();
        } else {
            IMPL = new AccessibilityDelegateStubImpl();
        }
    }

    Object getBridge() throws  {
        return this.mBridge;
    }

    public void sendAccessibilityEvent(View $r1, int $i0) throws  {
        IMPL.sendAccessibilityEvent(DEFAULT_DELEGATE, $r1, $i0);
    }

    public void sendAccessibilityEventUnchecked(View $r1, AccessibilityEvent $r2) throws  {
        IMPL.sendAccessibilityEventUnchecked(DEFAULT_DELEGATE, $r1, $r2);
    }

    public boolean dispatchPopulateAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
        return IMPL.dispatchPopulateAccessibilityEvent(DEFAULT_DELEGATE, $r1, $r2);
    }

    public void onPopulateAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
        IMPL.onPopulateAccessibilityEvent(DEFAULT_DELEGATE, $r1, $r2);
    }

    public void onInitializeAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
        IMPL.onInitializeAccessibilityEvent(DEFAULT_DELEGATE, $r1, $r2);
    }

    public void onInitializeAccessibilityNodeInfo(View $r1, AccessibilityNodeInfoCompat $r2) throws  {
        IMPL.onInitializeAccessibilityNodeInfo(DEFAULT_DELEGATE, $r1, $r2);
    }

    public boolean onRequestSendAccessibilityEvent(ViewGroup $r1, View $r2, AccessibilityEvent $r3) throws  {
        return IMPL.onRequestSendAccessibilityEvent(DEFAULT_DELEGATE, $r1, $r2, $r3);
    }

    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View $r1) throws  {
        return IMPL.getAccessibilityNodeProvider(DEFAULT_DELEGATE, $r1);
    }

    public boolean performAccessibilityAction(View $r1, int $i0, Bundle $r2) throws  {
        return IMPL.performAccessibilityAction(DEFAULT_DELEGATE, $r1, $i0, $r2);
    }
}
