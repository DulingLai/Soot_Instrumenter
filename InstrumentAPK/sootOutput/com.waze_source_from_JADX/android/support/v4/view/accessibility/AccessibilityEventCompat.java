package android.support.v4.view.accessibility;

import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityEvent;

public final class AccessibilityEventCompat {
    public static final int CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION = 4;
    public static final int CONTENT_CHANGE_TYPE_SUBTREE = 1;
    public static final int CONTENT_CHANGE_TYPE_TEXT = 2;
    public static final int CONTENT_CHANGE_TYPE_UNDEFINED = 0;
    private static final AccessibilityEventVersionImpl IMPL;
    public static final int TYPES_ALL_MASK = -1;
    public static final int TYPE_ANNOUNCEMENT = 16384;
    public static final int TYPE_GESTURE_DETECTION_END = 524288;
    public static final int TYPE_GESTURE_DETECTION_START = 262144;
    public static final int TYPE_TOUCH_EXPLORATION_GESTURE_END = 1024;
    public static final int TYPE_TOUCH_EXPLORATION_GESTURE_START = 512;
    public static final int TYPE_TOUCH_INTERACTION_END = 2097152;
    public static final int TYPE_TOUCH_INTERACTION_START = 1048576;
    public static final int TYPE_VIEW_ACCESSIBILITY_FOCUSED = 32768;
    public static final int TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED = 65536;
    public static final int TYPE_VIEW_HOVER_ENTER = 128;
    public static final int TYPE_VIEW_HOVER_EXIT = 256;
    public static final int TYPE_VIEW_SCROLLED = 4096;
    public static final int TYPE_VIEW_TEXT_SELECTION_CHANGED = 8192;
    public static final int TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY = 131072;
    public static final int TYPE_WINDOW_CONTENT_CHANGED = 2048;

    interface AccessibilityEventVersionImpl {
        void appendRecord(AccessibilityEvent accessibilityEvent, Object obj) throws ;

        int getContentChangeTypes(AccessibilityEvent accessibilityEvent) throws ;

        Object getRecord(AccessibilityEvent accessibilityEvent, int i) throws ;

        int getRecordCount(AccessibilityEvent accessibilityEvent) throws ;

        void setContentChangeTypes(AccessibilityEvent accessibilityEvent, int i) throws ;
    }

    static class AccessibilityEventStubImpl implements AccessibilityEventVersionImpl {
        public int getContentChangeTypes(AccessibilityEvent event) throws  {
            return 0;
        }

        public Object getRecord(AccessibilityEvent event, int index) throws  {
            return null;
        }

        public int getRecordCount(AccessibilityEvent event) throws  {
            return 0;
        }

        AccessibilityEventStubImpl() throws  {
        }

        public void appendRecord(AccessibilityEvent event, Object record) throws  {
        }

        public void setContentChangeTypes(AccessibilityEvent event, int types) throws  {
        }
    }

    static class AccessibilityEventIcsImpl extends AccessibilityEventStubImpl {
        AccessibilityEventIcsImpl() throws  {
        }

        public void appendRecord(AccessibilityEvent $r1, Object $r2) throws  {
            AccessibilityEventCompatIcs.appendRecord($r1, $r2);
        }

        public Object getRecord(AccessibilityEvent $r1, int $i0) throws  {
            return AccessibilityEventCompatIcs.getRecord($r1, $i0);
        }

        public int getRecordCount(AccessibilityEvent $r1) throws  {
            return AccessibilityEventCompatIcs.getRecordCount($r1);
        }
    }

    static class AccessibilityEventKitKatImpl extends AccessibilityEventIcsImpl {
        AccessibilityEventKitKatImpl() throws  {
        }

        public void setContentChangeTypes(AccessibilityEvent $r1, int $i0) throws  {
            AccessibilityEventCompatKitKat.setContentChangeTypes($r1, $i0);
        }

        public int getContentChangeTypes(AccessibilityEvent $r1) throws  {
            return AccessibilityEventCompatKitKat.getContentChangeTypes($r1);
        }
    }

    static {
        if (VERSION.SDK_INT >= 19) {
            IMPL = new AccessibilityEventKitKatImpl();
        } else if (VERSION.SDK_INT >= 14) {
            IMPL = new AccessibilityEventIcsImpl();
        } else {
            IMPL = new AccessibilityEventStubImpl();
        }
    }

    private AccessibilityEventCompat() throws  {
    }

    public static int getRecordCount(AccessibilityEvent $r0) throws  {
        return IMPL.getRecordCount($r0);
    }

    public static void appendRecord(AccessibilityEvent $r0, AccessibilityRecordCompat $r1) throws  {
        IMPL.appendRecord($r0, $r1.getImpl());
    }

    public static AccessibilityRecordCompat getRecord(AccessibilityEvent $r0, int $i0) throws  {
        return new AccessibilityRecordCompat(IMPL.getRecord($r0, $i0));
    }

    public static AccessibilityRecordCompat asRecord(AccessibilityEvent $r0) throws  {
        return new AccessibilityRecordCompat($r0);
    }

    public static void setContentChangeTypes(AccessibilityEvent $r0, int $i0) throws  {
        IMPL.setContentChangeTypes($r0, $i0);
    }

    public static int getContentChangeTypes(AccessibilityEvent $r0) throws  {
        return IMPL.getContentChangeTypes($r0);
    }
}
