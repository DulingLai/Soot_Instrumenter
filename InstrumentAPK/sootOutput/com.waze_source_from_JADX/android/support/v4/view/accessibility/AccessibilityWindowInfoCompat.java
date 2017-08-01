package android.support.v4.view.accessibility;

import android.graphics.Rect;
import android.os.Build.VERSION;

public class AccessibilityWindowInfoCompat {
    private static final AccessibilityWindowInfoImpl IMPL;
    public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
    public static final int TYPE_APPLICATION = 1;
    public static final int TYPE_INPUT_METHOD = 2;
    public static final int TYPE_SYSTEM = 3;
    private static final int UNDEFINED = -1;
    private Object mInfo;

    private interface AccessibilityWindowInfoImpl {
        void getBoundsInScreen(Object obj, Rect rect) throws ;

        Object getChild(Object obj, int i) throws ;

        int getChildCount(Object obj) throws ;

        int getId(Object obj) throws ;

        int getLayer(Object obj) throws ;

        Object getParent(Object obj) throws ;

        Object getRoot(Object obj) throws ;

        int getType(Object obj) throws ;

        boolean isAccessibilityFocused(Object obj) throws ;

        boolean isActive(Object obj) throws ;

        boolean isFocused(Object obj) throws ;

        Object obtain() throws ;

        Object obtain(Object obj) throws ;

        void recycle(Object obj) throws ;
    }

    private static class AccessibilityWindowInfoStubImpl implements AccessibilityWindowInfoImpl {
        public Object getChild(Object info, int index) throws  {
            return null;
        }

        public int getChildCount(Object info) throws  {
            return 0;
        }

        public int getId(Object info) throws  {
            return -1;
        }

        public int getLayer(Object info) throws  {
            return -1;
        }

        public Object getParent(Object info) throws  {
            return null;
        }

        public Object getRoot(Object info) throws  {
            return null;
        }

        public int getType(Object info) throws  {
            return -1;
        }

        public boolean isAccessibilityFocused(Object info) throws  {
            return true;
        }

        public boolean isActive(Object info) throws  {
            return true;
        }

        public boolean isFocused(Object info) throws  {
            return true;
        }

        public Object obtain() throws  {
            return null;
        }

        public Object obtain(Object info) throws  {
            return null;
        }

        private AccessibilityWindowInfoStubImpl() throws  {
        }

        public void getBoundsInScreen(Object info, Rect outBounds) throws  {
        }

        public void recycle(Object info) throws  {
        }
    }

    private static class AccessibilityWindowInfoApi21Impl extends AccessibilityWindowInfoStubImpl {
        private AccessibilityWindowInfoApi21Impl() throws  {
            super();
        }

        public Object obtain() throws  {
            return AccessibilityWindowInfoCompatApi21.obtain();
        }

        public Object obtain(Object $r1) throws  {
            return AccessibilityWindowInfoCompatApi21.obtain($r1);
        }

        public int getType(Object $r1) throws  {
            return AccessibilityWindowInfoCompatApi21.getType($r1);
        }

        public int getLayer(Object $r1) throws  {
            return AccessibilityWindowInfoCompatApi21.getLayer($r1);
        }

        public Object getRoot(Object $r1) throws  {
            return AccessibilityWindowInfoCompatApi21.getRoot($r1);
        }

        public Object getParent(Object $r1) throws  {
            return AccessibilityWindowInfoCompatApi21.getParent($r1);
        }

        public int getId(Object $r1) throws  {
            return AccessibilityWindowInfoCompatApi21.getId($r1);
        }

        public void getBoundsInScreen(Object $r1, Rect $r2) throws  {
            AccessibilityWindowInfoCompatApi21.getBoundsInScreen($r1, $r2);
        }

        public boolean isActive(Object $r1) throws  {
            return AccessibilityWindowInfoCompatApi21.isActive($r1);
        }

        public boolean isFocused(Object $r1) throws  {
            return AccessibilityWindowInfoCompatApi21.isFocused($r1);
        }

        public boolean isAccessibilityFocused(Object $r1) throws  {
            return AccessibilityWindowInfoCompatApi21.isAccessibilityFocused($r1);
        }

        public int getChildCount(Object $r1) throws  {
            return AccessibilityWindowInfoCompatApi21.getChildCount($r1);
        }

        public Object getChild(Object $r1, int $i0) throws  {
            return AccessibilityWindowInfoCompatApi21.getChild($r1, $i0);
        }

        public void recycle(Object $r1) throws  {
            AccessibilityWindowInfoCompatApi21.recycle($r1);
        }
    }

    private static String typeToString(int $i0) throws  {
        switch ($i0) {
            case 1:
                return "TYPE_APPLICATION";
            case 2:
                return "TYPE_INPUT_METHOD";
            case 3:
                return "TYPE_SYSTEM";
            case 4:
                return "TYPE_ACCESSIBILITY_OVERLAY";
            default:
                return "<UNKNOWN>";
        }
    }

    static {
        if (VERSION.SDK_INT >= 21) {
            IMPL = new AccessibilityWindowInfoApi21Impl();
        } else {
            IMPL = new AccessibilityWindowInfoStubImpl();
        }
    }

    static AccessibilityWindowInfoCompat wrapNonNullInstance(Object $r0) throws  {
        if ($r0 != null) {
            return new AccessibilityWindowInfoCompat($r0);
        }
        return null;
    }

    private AccessibilityWindowInfoCompat(Object $r1) throws  {
        this.mInfo = $r1;
    }

    public int getType() throws  {
        return IMPL.getType(this.mInfo);
    }

    public int getLayer() throws  {
        return IMPL.getLayer(this.mInfo);
    }

    public AccessibilityNodeInfoCompat getRoot() throws  {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance(IMPL.getRoot(this.mInfo));
    }

    public AccessibilityWindowInfoCompat getParent() throws  {
        return wrapNonNullInstance(IMPL.getParent(this.mInfo));
    }

    public int getId() throws  {
        return IMPL.getId(this.mInfo);
    }

    public void getBoundsInScreen(Rect $r1) throws  {
        IMPL.getBoundsInScreen(this.mInfo, $r1);
    }

    public boolean isActive() throws  {
        return IMPL.isActive(this.mInfo);
    }

    public boolean isFocused() throws  {
        return IMPL.isFocused(this.mInfo);
    }

    public boolean isAccessibilityFocused() throws  {
        return IMPL.isAccessibilityFocused(this.mInfo);
    }

    public int getChildCount() throws  {
        return IMPL.getChildCount(this.mInfo);
    }

    public AccessibilityWindowInfoCompat getChild(int $i0) throws  {
        return wrapNonNullInstance(IMPL.getChild(this.mInfo, $i0));
    }

    public static AccessibilityWindowInfoCompat obtain() throws  {
        return wrapNonNullInstance(IMPL.obtain());
    }

    public static AccessibilityWindowInfoCompat obtain(AccessibilityWindowInfoCompat $r0) throws  {
        return wrapNonNullInstance(IMPL.obtain($r0.mInfo));
    }

    public void recycle() throws  {
        IMPL.recycle(this.mInfo);
    }

    public int hashCode() throws  {
        return this.mInfo == null ? 0 : this.mInfo.hashCode();
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null) {
            return false;
        }
        if (getClass() != $r1.getClass()) {
            return false;
        }
        AccessibilityWindowInfoCompat $r4 = (AccessibilityWindowInfoCompat) $r1;
        if (this.mInfo != null) {
            return this.mInfo.equals($r4.mInfo);
        } else {
            if ($r4.mInfo != null) {
                return false;
            }
            return true;
        }
    }

    public String toString() throws  {
        boolean $z1;
        boolean $z0 = true;
        StringBuilder $r2 = new StringBuilder();
        Rect $r1 = new Rect();
        getBoundsInScreen($r1);
        $r2.append("AccessibilityWindowInfo[");
        $r2.append("id=").append(getId());
        $r2.append(", type=").append(typeToString(getType()));
        $r2.append(", layer=").append(getLayer());
        $r2.append(", bounds=").append($r1);
        $r2.append(", focused=").append(isFocused());
        $r2.append(", active=").append(isActive());
        StringBuilder $r3 = $r2.append(", hasParent=");
        if (getParent() != null) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        $r3.append($z1);
        $r3 = $r2.append(", hasChildren=");
        if (getChildCount() <= 0) {
            $z0 = false;
        }
        $r3.append($z0);
        $r2.append(']');
        return $r2.toString();
    }
}
