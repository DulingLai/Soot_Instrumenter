package android.support.v4.view.accessibility;

import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;

class AccessibilityNodeInfoCompatKitKat {

    static class CollectionInfo {
        CollectionInfo() throws  {
        }

        static int getColumnCount(Object $r0) throws  {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionInfo) $r0).getColumnCount();
        }

        static int getRowCount(Object $r0) throws  {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionInfo) $r0).getRowCount();
        }

        static boolean isHierarchical(Object $r0) throws  {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionInfo) $r0).isHierarchical();
        }
    }

    static class CollectionItemInfo {
        CollectionItemInfo() throws  {
        }

        static int getColumnIndex(Object $r0) throws  {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) $r0).getColumnIndex();
        }

        static int getColumnSpan(Object $r0) throws  {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) $r0).getColumnSpan();
        }

        static int getRowIndex(Object $r0) throws  {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) $r0).getRowIndex();
        }

        static int getRowSpan(Object $r0) throws  {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) $r0).getRowSpan();
        }

        static boolean isHeading(Object $r0) throws  {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) $r0).isHeading();
        }
    }

    static class RangeInfo {
        RangeInfo() throws  {
        }

        static float getCurrent(Object $r0) throws  {
            return ((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) $r0).getCurrent();
        }

        static float getMax(Object $r0) throws  {
            return ((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) $r0).getMax();
        }

        static float getMin(Object $r0) throws  {
            return ((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) $r0).getMin();
        }

        static int getType(Object $r0) throws  {
            return ((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) $r0).getType();
        }
    }

    AccessibilityNodeInfoCompatKitKat() throws  {
    }

    static int getLiveRegion(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).getLiveRegion();
    }

    static void setLiveRegion(Object $r0, int $i0) throws  {
        ((AccessibilityNodeInfo) $r0).setLiveRegion($i0);
    }

    static Object getCollectionInfo(Object $r1) throws  {
        return ((AccessibilityNodeInfo) $r1).getCollectionInfo();
    }

    static Object getCollectionItemInfo(Object $r1) throws  {
        return ((AccessibilityNodeInfo) $r1).getCollectionItemInfo();
    }

    public static void setCollectionInfo(Object $r0, Object $r1) throws  {
        ((AccessibilityNodeInfo) $r0).setCollectionInfo((android.view.accessibility.AccessibilityNodeInfo.CollectionInfo) $r1);
    }

    public static void setCollectionItemInfo(Object $r0, Object $r1) throws  {
        ((AccessibilityNodeInfo) $r0).setCollectionItemInfo((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) $r1);
    }

    static Object getRangeInfo(Object $r1) throws  {
        return ((AccessibilityNodeInfo) $r1).getRangeInfo();
    }

    public static void setRangeInfo(Object $r0, Object $r1) throws  {
        ((AccessibilityNodeInfo) $r0).setRangeInfo((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) $r1);
    }

    public static Object obtainCollectionInfo(int $i0, int $i1, boolean $z0, int selectionMode) throws  {
        return android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.obtain($i0, $i1, $z0);
    }

    public static Object obtainCollectionItemInfo(int $i0, int $i1, int $i2, int $i3, boolean $z0) throws  {
        return android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.obtain($i0, $i1, $i2, $i3, $z0);
    }

    public static void setContentInvalid(Object $r0, boolean $z0) throws  {
        ((AccessibilityNodeInfo) $r0).setContentInvalid($z0);
    }

    public static boolean isContentInvalid(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).isContentInvalid();
    }

    public static boolean canOpenPopup(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).canOpenPopup();
    }

    public static void setCanOpenPopup(Object $r0, boolean $z0) throws  {
        ((AccessibilityNodeInfo) $r0).setCanOpenPopup($z0);
    }

    public static Bundle getExtras(Object $r1) throws  {
        return ((AccessibilityNodeInfo) $r1).getExtras();
    }

    public static int getInputType(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).getInputType();
    }

    public static void setInputType(Object $r0, int $i0) throws  {
        ((AccessibilityNodeInfo) $r0).setInputType($i0);
    }

    public static boolean isDismissable(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).isDismissable();
    }

    public static void setDismissable(Object $r0, boolean $z0) throws  {
        ((AccessibilityNodeInfo) $r0).setDismissable($z0);
    }

    public static boolean isMultiLine(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).isMultiLine();
    }

    public static void setMultiLine(Object $r0, boolean $z0) throws  {
        ((AccessibilityNodeInfo) $r0).setMultiLine($z0);
    }
}
