package android.support.v4.view.accessibility;

import android.os.Build.VERSION;
import android.os.Parcelable;
import android.view.View;
import dalvik.annotation.Signature;
import java.util.Collections;
import java.util.List;

public class AccessibilityRecordCompat {
    private static final AccessibilityRecordImpl IMPL;
    private final Object mRecord;

    interface AccessibilityRecordImpl {
        int getAddedCount(Object obj) throws ;

        CharSequence getBeforeText(Object obj) throws ;

        CharSequence getClassName(Object obj) throws ;

        CharSequence getContentDescription(Object obj) throws ;

        int getCurrentItemIndex(Object obj) throws ;

        int getFromIndex(Object obj) throws ;

        int getItemCount(Object obj) throws ;

        int getMaxScrollX(Object obj) throws ;

        int getMaxScrollY(Object obj) throws ;

        Parcelable getParcelableData(Object obj) throws ;

        int getRemovedCount(Object obj) throws ;

        int getScrollX(Object obj) throws ;

        int getScrollY(Object obj) throws ;

        AccessibilityNodeInfoCompat getSource(Object obj) throws ;

        List<CharSequence> getText(@Signature({"(", "Ljava/lang/Object;", ")", "Ljava/util/List", "<", "Ljava/lang/CharSequence;", ">;"}) Object obj) throws ;

        int getToIndex(Object obj) throws ;

        int getWindowId(Object obj) throws ;

        boolean isChecked(Object obj) throws ;

        boolean isEnabled(Object obj) throws ;

        boolean isFullScreen(Object obj) throws ;

        boolean isPassword(Object obj) throws ;

        boolean isScrollable(Object obj) throws ;

        Object obtain() throws ;

        Object obtain(Object obj) throws ;

        void recycle(Object obj) throws ;

        void setAddedCount(Object obj, int i) throws ;

        void setBeforeText(Object obj, CharSequence charSequence) throws ;

        void setChecked(Object obj, boolean z) throws ;

        void setClassName(Object obj, CharSequence charSequence) throws ;

        void setContentDescription(Object obj, CharSequence charSequence) throws ;

        void setCurrentItemIndex(Object obj, int i) throws ;

        void setEnabled(Object obj, boolean z) throws ;

        void setFromIndex(Object obj, int i) throws ;

        void setFullScreen(Object obj, boolean z) throws ;

        void setItemCount(Object obj, int i) throws ;

        void setMaxScrollX(Object obj, int i) throws ;

        void setMaxScrollY(Object obj, int i) throws ;

        void setParcelableData(Object obj, Parcelable parcelable) throws ;

        void setPassword(Object obj, boolean z) throws ;

        void setRemovedCount(Object obj, int i) throws ;

        void setScrollX(Object obj, int i) throws ;

        void setScrollY(Object obj, int i) throws ;

        void setScrollable(Object obj, boolean z) throws ;

        void setSource(Object obj, View view) throws ;

        void setSource(Object obj, View view, int i) throws ;

        void setToIndex(Object obj, int i) throws ;
    }

    static class AccessibilityRecordStubImpl implements AccessibilityRecordImpl {
        public int getAddedCount(Object record) throws  {
            return 0;
        }

        public CharSequence getBeforeText(Object record) throws  {
            return null;
        }

        public CharSequence getClassName(Object record) throws  {
            return null;
        }

        public CharSequence getContentDescription(Object record) throws  {
            return null;
        }

        public int getCurrentItemIndex(Object record) throws  {
            return 0;
        }

        public int getFromIndex(Object record) throws  {
            return 0;
        }

        public int getItemCount(Object record) throws  {
            return 0;
        }

        public int getMaxScrollX(Object record) throws  {
            return 0;
        }

        public int getMaxScrollY(Object record) throws  {
            return 0;
        }

        public Parcelable getParcelableData(Object record) throws  {
            return null;
        }

        public int getRemovedCount(Object record) throws  {
            return 0;
        }

        public int getScrollX(Object record) throws  {
            return 0;
        }

        public int getScrollY(Object record) throws  {
            return 0;
        }

        public AccessibilityNodeInfoCompat getSource(Object record) throws  {
            return null;
        }

        public int getToIndex(Object record) throws  {
            return 0;
        }

        public int getWindowId(Object record) throws  {
            return 0;
        }

        public boolean isChecked(Object record) throws  {
            return false;
        }

        public boolean isEnabled(Object record) throws  {
            return false;
        }

        public boolean isFullScreen(Object record) throws  {
            return false;
        }

        public boolean isPassword(Object record) throws  {
            return false;
        }

        public boolean isScrollable(Object record) throws  {
            return false;
        }

        public Object obtain() throws  {
            return null;
        }

        public Object obtain(Object record) throws  {
            return null;
        }

        AccessibilityRecordStubImpl() throws  {
        }

        public List<CharSequence> getText(@Signature({"(", "Ljava/lang/Object;", ")", "Ljava/util/List", "<", "Ljava/lang/CharSequence;", ">;"}) Object record) throws  {
            return Collections.emptyList();
        }

        public void recycle(Object record) throws  {
        }

        public void setAddedCount(Object record, int addedCount) throws  {
        }

        public void setBeforeText(Object record, CharSequence beforeText) throws  {
        }

        public void setChecked(Object record, boolean isChecked) throws  {
        }

        public void setClassName(Object record, CharSequence className) throws  {
        }

        public void setContentDescription(Object record, CharSequence contentDescription) throws  {
        }

        public void setCurrentItemIndex(Object record, int currentItemIndex) throws  {
        }

        public void setEnabled(Object record, boolean isEnabled) throws  {
        }

        public void setFromIndex(Object record, int fromIndex) throws  {
        }

        public void setFullScreen(Object record, boolean isFullScreen) throws  {
        }

        public void setItemCount(Object record, int itemCount) throws  {
        }

        public void setMaxScrollX(Object record, int maxScrollX) throws  {
        }

        public void setMaxScrollY(Object record, int maxScrollY) throws  {
        }

        public void setParcelableData(Object record, Parcelable parcelableData) throws  {
        }

        public void setPassword(Object record, boolean isPassword) throws  {
        }

        public void setRemovedCount(Object record, int removedCount) throws  {
        }

        public void setScrollX(Object record, int scrollX) throws  {
        }

        public void setScrollY(Object record, int scrollY) throws  {
        }

        public void setScrollable(Object record, boolean scrollable) throws  {
        }

        public void setSource(Object record, View source) throws  {
        }

        public void setSource(Object record, View root, int virtualDescendantId) throws  {
        }

        public void setToIndex(Object record, int toIndex) throws  {
        }
    }

    static class AccessibilityRecordIcsImpl extends AccessibilityRecordStubImpl {
        AccessibilityRecordIcsImpl() throws  {
        }

        public Object obtain() throws  {
            return AccessibilityRecordCompatIcs.obtain();
        }

        public Object obtain(Object $r1) throws  {
            return AccessibilityRecordCompatIcs.obtain($r1);
        }

        public int getAddedCount(Object $r1) throws  {
            return AccessibilityRecordCompatIcs.getAddedCount($r1);
        }

        public CharSequence getBeforeText(Object $r1) throws  {
            return AccessibilityRecordCompatIcs.getBeforeText($r1);
        }

        public CharSequence getClassName(Object $r1) throws  {
            return AccessibilityRecordCompatIcs.getClassName($r1);
        }

        public CharSequence getContentDescription(Object $r1) throws  {
            return AccessibilityRecordCompatIcs.getContentDescription($r1);
        }

        public int getCurrentItemIndex(Object $r1) throws  {
            return AccessibilityRecordCompatIcs.getCurrentItemIndex($r1);
        }

        public int getFromIndex(Object $r1) throws  {
            return AccessibilityRecordCompatIcs.getFromIndex($r1);
        }

        public int getItemCount(Object $r1) throws  {
            return AccessibilityRecordCompatIcs.getItemCount($r1);
        }

        public Parcelable getParcelableData(Object $r1) throws  {
            return AccessibilityRecordCompatIcs.getParcelableData($r1);
        }

        public int getRemovedCount(Object $r1) throws  {
            return AccessibilityRecordCompatIcs.getRemovedCount($r1);
        }

        public int getScrollX(Object $r1) throws  {
            return AccessibilityRecordCompatIcs.getScrollX($r1);
        }

        public int getScrollY(Object $r1) throws  {
            return AccessibilityRecordCompatIcs.getScrollY($r1);
        }

        public AccessibilityNodeInfoCompat getSource(Object $r1) throws  {
            return AccessibilityNodeInfoCompat.wrapNonNullInstance(AccessibilityRecordCompatIcs.getSource($r1));
        }

        public List<CharSequence> getText(@Signature({"(", "Ljava/lang/Object;", ")", "Ljava/util/List", "<", "Ljava/lang/CharSequence;", ">;"}) Object $r1) throws  {
            return AccessibilityRecordCompatIcs.getText($r1);
        }

        public int getToIndex(Object $r1) throws  {
            return AccessibilityRecordCompatIcs.getToIndex($r1);
        }

        public int getWindowId(Object $r1) throws  {
            return AccessibilityRecordCompatIcs.getWindowId($r1);
        }

        public boolean isChecked(Object $r1) throws  {
            return AccessibilityRecordCompatIcs.isChecked($r1);
        }

        public boolean isEnabled(Object $r1) throws  {
            return AccessibilityRecordCompatIcs.isEnabled($r1);
        }

        public boolean isFullScreen(Object $r1) throws  {
            return AccessibilityRecordCompatIcs.isFullScreen($r1);
        }

        public boolean isPassword(Object $r1) throws  {
            return AccessibilityRecordCompatIcs.isPassword($r1);
        }

        public boolean isScrollable(Object $r1) throws  {
            return AccessibilityRecordCompatIcs.isScrollable($r1);
        }

        public void recycle(Object $r1) throws  {
            AccessibilityRecordCompatIcs.recycle($r1);
        }

        public void setAddedCount(Object $r1, int $i0) throws  {
            AccessibilityRecordCompatIcs.setAddedCount($r1, $i0);
        }

        public void setBeforeText(Object $r1, CharSequence $r2) throws  {
            AccessibilityRecordCompatIcs.setBeforeText($r1, $r2);
        }

        public void setChecked(Object $r1, boolean $z0) throws  {
            AccessibilityRecordCompatIcs.setChecked($r1, $z0);
        }

        public void setClassName(Object $r1, CharSequence $r2) throws  {
            AccessibilityRecordCompatIcs.setClassName($r1, $r2);
        }

        public void setContentDescription(Object $r1, CharSequence $r2) throws  {
            AccessibilityRecordCompatIcs.setContentDescription($r1, $r2);
        }

        public void setCurrentItemIndex(Object $r1, int $i0) throws  {
            AccessibilityRecordCompatIcs.setCurrentItemIndex($r1, $i0);
        }

        public void setEnabled(Object $r1, boolean $z0) throws  {
            AccessibilityRecordCompatIcs.setEnabled($r1, $z0);
        }

        public void setFromIndex(Object $r1, int $i0) throws  {
            AccessibilityRecordCompatIcs.setFromIndex($r1, $i0);
        }

        public void setFullScreen(Object $r1, boolean $z0) throws  {
            AccessibilityRecordCompatIcs.setFullScreen($r1, $z0);
        }

        public void setItemCount(Object $r1, int $i0) throws  {
            AccessibilityRecordCompatIcs.setItemCount($r1, $i0);
        }

        public void setParcelableData(Object $r1, Parcelable $r2) throws  {
            AccessibilityRecordCompatIcs.setParcelableData($r1, $r2);
        }

        public void setPassword(Object $r1, boolean $z0) throws  {
            AccessibilityRecordCompatIcs.setPassword($r1, $z0);
        }

        public void setRemovedCount(Object $r1, int $i0) throws  {
            AccessibilityRecordCompatIcs.setRemovedCount($r1, $i0);
        }

        public void setScrollX(Object $r1, int $i0) throws  {
            AccessibilityRecordCompatIcs.setScrollX($r1, $i0);
        }

        public void setScrollY(Object $r1, int $i0) throws  {
            AccessibilityRecordCompatIcs.setScrollY($r1, $i0);
        }

        public void setScrollable(Object $r1, boolean $z0) throws  {
            AccessibilityRecordCompatIcs.setScrollable($r1, $z0);
        }

        public void setSource(Object $r1, View $r2) throws  {
            AccessibilityRecordCompatIcs.setSource($r1, $r2);
        }

        public void setToIndex(Object $r1, int $i0) throws  {
            AccessibilityRecordCompatIcs.setToIndex($r1, $i0);
        }
    }

    static class AccessibilityRecordIcsMr1Impl extends AccessibilityRecordIcsImpl {
        AccessibilityRecordIcsMr1Impl() throws  {
        }

        public int getMaxScrollX(Object $r1) throws  {
            return AccessibilityRecordCompatIcsMr1.getMaxScrollX($r1);
        }

        public int getMaxScrollY(Object $r1) throws  {
            return AccessibilityRecordCompatIcsMr1.getMaxScrollY($r1);
        }

        public void setMaxScrollX(Object $r1, int $i0) throws  {
            AccessibilityRecordCompatIcsMr1.setMaxScrollX($r1, $i0);
        }

        public void setMaxScrollY(Object $r1, int $i0) throws  {
            AccessibilityRecordCompatIcsMr1.setMaxScrollY($r1, $i0);
        }
    }

    static class AccessibilityRecordJellyBeanImpl extends AccessibilityRecordIcsMr1Impl {
        AccessibilityRecordJellyBeanImpl() throws  {
        }

        public void setSource(Object $r1, View $r2, int $i0) throws  {
            AccessibilityRecordCompatJellyBean.setSource($r1, $r2, $i0);
        }
    }

    static {
        if (VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityRecordJellyBeanImpl();
        } else if (VERSION.SDK_INT >= 15) {
            IMPL = new AccessibilityRecordIcsMr1Impl();
        } else if (VERSION.SDK_INT >= 14) {
            IMPL = new AccessibilityRecordIcsImpl();
        } else {
            IMPL = new AccessibilityRecordStubImpl();
        }
    }

    public AccessibilityRecordCompat(Object $r1) throws  {
        this.mRecord = $r1;
    }

    public Object getImpl() throws  {
        return this.mRecord;
    }

    public static AccessibilityRecordCompat obtain(AccessibilityRecordCompat $r0) throws  {
        return new AccessibilityRecordCompat(IMPL.obtain($r0.mRecord));
    }

    public static AccessibilityRecordCompat obtain() throws  {
        return new AccessibilityRecordCompat(IMPL.obtain());
    }

    public void setSource(View $r1) throws  {
        IMPL.setSource(this.mRecord, $r1);
    }

    public void setSource(View $r1, int $i0) throws  {
        IMPL.setSource(this.mRecord, $r1, $i0);
    }

    public AccessibilityNodeInfoCompat getSource() throws  {
        return IMPL.getSource(this.mRecord);
    }

    public int getWindowId() throws  {
        return IMPL.getWindowId(this.mRecord);
    }

    public boolean isChecked() throws  {
        return IMPL.isChecked(this.mRecord);
    }

    public void setChecked(boolean $z0) throws  {
        IMPL.setChecked(this.mRecord, $z0);
    }

    public boolean isEnabled() throws  {
        return IMPL.isEnabled(this.mRecord);
    }

    public void setEnabled(boolean $z0) throws  {
        IMPL.setEnabled(this.mRecord, $z0);
    }

    public boolean isPassword() throws  {
        return IMPL.isPassword(this.mRecord);
    }

    public void setPassword(boolean $z0) throws  {
        IMPL.setPassword(this.mRecord, $z0);
    }

    public boolean isFullScreen() throws  {
        return IMPL.isFullScreen(this.mRecord);
    }

    public void setFullScreen(boolean $z0) throws  {
        IMPL.setFullScreen(this.mRecord, $z0);
    }

    public boolean isScrollable() throws  {
        return IMPL.isScrollable(this.mRecord);
    }

    public void setScrollable(boolean $z0) throws  {
        IMPL.setScrollable(this.mRecord, $z0);
    }

    public int getItemCount() throws  {
        return IMPL.getItemCount(this.mRecord);
    }

    public void setItemCount(int $i0) throws  {
        IMPL.setItemCount(this.mRecord, $i0);
    }

    public int getCurrentItemIndex() throws  {
        return IMPL.getCurrentItemIndex(this.mRecord);
    }

    public void setCurrentItemIndex(int $i0) throws  {
        IMPL.setCurrentItemIndex(this.mRecord, $i0);
    }

    public int getFromIndex() throws  {
        return IMPL.getFromIndex(this.mRecord);
    }

    public void setFromIndex(int $i0) throws  {
        IMPL.setFromIndex(this.mRecord, $i0);
    }

    public int getToIndex() throws  {
        return IMPL.getToIndex(this.mRecord);
    }

    public void setToIndex(int $i0) throws  {
        IMPL.setToIndex(this.mRecord, $i0);
    }

    public int getScrollX() throws  {
        return IMPL.getScrollX(this.mRecord);
    }

    public void setScrollX(int $i0) throws  {
        IMPL.setScrollX(this.mRecord, $i0);
    }

    public int getScrollY() throws  {
        return IMPL.getScrollY(this.mRecord);
    }

    public void setScrollY(int $i0) throws  {
        IMPL.setScrollY(this.mRecord, $i0);
    }

    public int getMaxScrollX() throws  {
        return IMPL.getMaxScrollX(this.mRecord);
    }

    public void setMaxScrollX(int $i0) throws  {
        IMPL.setMaxScrollX(this.mRecord, $i0);
    }

    public int getMaxScrollY() throws  {
        return IMPL.getMaxScrollY(this.mRecord);
    }

    public void setMaxScrollY(int $i0) throws  {
        IMPL.setMaxScrollY(this.mRecord, $i0);
    }

    public int getAddedCount() throws  {
        return IMPL.getAddedCount(this.mRecord);
    }

    public void setAddedCount(int $i0) throws  {
        IMPL.setAddedCount(this.mRecord, $i0);
    }

    public int getRemovedCount() throws  {
        return IMPL.getRemovedCount(this.mRecord);
    }

    public void setRemovedCount(int $i0) throws  {
        IMPL.setRemovedCount(this.mRecord, $i0);
    }

    public CharSequence getClassName() throws  {
        return IMPL.getClassName(this.mRecord);
    }

    public void setClassName(CharSequence $r1) throws  {
        IMPL.setClassName(this.mRecord, $r1);
    }

    public List<CharSequence> getText() throws  {
        return IMPL.getText(this.mRecord);
    }

    public CharSequence getBeforeText() throws  {
        return IMPL.getBeforeText(this.mRecord);
    }

    public void setBeforeText(CharSequence $r1) throws  {
        IMPL.setBeforeText(this.mRecord, $r1);
    }

    public CharSequence getContentDescription() throws  {
        return IMPL.getContentDescription(this.mRecord);
    }

    public void setContentDescription(CharSequence $r1) throws  {
        IMPL.setContentDescription(this.mRecord, $r1);
    }

    public Parcelable getParcelableData() throws  {
        return IMPL.getParcelableData(this.mRecord);
    }

    public void setParcelableData(Parcelable $r1) throws  {
        IMPL.setParcelableData(this.mRecord, $r1);
    }

    public void recycle() throws  {
        IMPL.recycle(this.mRecord);
    }

    public int hashCode() throws  {
        return this.mRecord == null ? 0 : this.mRecord.hashCode();
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
        AccessibilityRecordCompat $r4 = (AccessibilityRecordCompat) $r1;
        if (this.mRecord != null) {
            return this.mRecord.equals($r4.mRecord);
        } else {
            if ($r4.mRecord != null) {
                return false;
            }
            return true;
        }
    }
}
