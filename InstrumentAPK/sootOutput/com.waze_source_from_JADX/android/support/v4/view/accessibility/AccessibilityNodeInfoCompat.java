package android.support.v4.view.accessibility;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccessibilityNodeInfoCompat {
    public static final int ACTION_ACCESSIBILITY_FOCUS = 64;
    public static final String ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN";
    public static final String ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING";
    public static final String ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT";
    public static final String ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT";
    public static final String ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT";
    public static final String ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE = "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE";
    public static final int ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128;
    public static final int ACTION_CLEAR_FOCUS = 2;
    public static final int ACTION_CLEAR_SELECTION = 8;
    public static final int ACTION_CLICK = 16;
    public static final int ACTION_COLLAPSE = 524288;
    public static final int ACTION_COPY = 16384;
    public static final int ACTION_CUT = 65536;
    public static final int ACTION_DISMISS = 1048576;
    public static final int ACTION_EXPAND = 262144;
    public static final int ACTION_FOCUS = 1;
    public static final int ACTION_LONG_CLICK = 32;
    public static final int ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256;
    public static final int ACTION_NEXT_HTML_ELEMENT = 1024;
    public static final int ACTION_PASTE = 32768;
    public static final int ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512;
    public static final int ACTION_PREVIOUS_HTML_ELEMENT = 2048;
    public static final int ACTION_SCROLL_BACKWARD = 8192;
    public static final int ACTION_SCROLL_FORWARD = 4096;
    public static final int ACTION_SELECT = 4;
    public static final int ACTION_SET_SELECTION = 131072;
    public static final int ACTION_SET_TEXT = 2097152;
    public static final int FOCUS_ACCESSIBILITY = 2;
    public static final int FOCUS_INPUT = 1;
    private static final AccessibilityNodeInfoImpl IMPL;
    public static final int MOVEMENT_GRANULARITY_CHARACTER = 1;
    public static final int MOVEMENT_GRANULARITY_LINE = 4;
    public static final int MOVEMENT_GRANULARITY_PAGE = 16;
    public static final int MOVEMENT_GRANULARITY_PARAGRAPH = 8;
    public static final int MOVEMENT_GRANULARITY_WORD = 2;
    private final Object mInfo;

    public static class AccessibilityActionCompat {
        public static final AccessibilityActionCompat ACTION_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(64, null);
        public static final AccessibilityActionCompat ACTION_CLEAR_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(128, null);
        public static final AccessibilityActionCompat ACTION_CLEAR_FOCUS = new AccessibilityActionCompat(2, null);
        public static final AccessibilityActionCompat ACTION_CLEAR_SELECTION = new AccessibilityActionCompat(8, null);
        public static final AccessibilityActionCompat ACTION_CLICK = new AccessibilityActionCompat(16, null);
        public static final AccessibilityActionCompat ACTION_COLLAPSE = new AccessibilityActionCompat(524288, null);
        public static final AccessibilityActionCompat ACTION_COPY = new AccessibilityActionCompat(16384, null);
        public static final AccessibilityActionCompat ACTION_CUT = new AccessibilityActionCompat(65536, null);
        public static final AccessibilityActionCompat ACTION_DISMISS = new AccessibilityActionCompat(1048576, null);
        public static final AccessibilityActionCompat ACTION_EXPAND = new AccessibilityActionCompat(262144, null);
        public static final AccessibilityActionCompat ACTION_FOCUS = new AccessibilityActionCompat(1, null);
        public static final AccessibilityActionCompat ACTION_LONG_CLICK = new AccessibilityActionCompat(32, null);
        public static final AccessibilityActionCompat ACTION_NEXT_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(256, null);
        public static final AccessibilityActionCompat ACTION_NEXT_HTML_ELEMENT = new AccessibilityActionCompat(1024, null);
        public static final AccessibilityActionCompat ACTION_PASTE = new AccessibilityActionCompat(32768, null);
        public static final AccessibilityActionCompat ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(512, null);
        public static final AccessibilityActionCompat ACTION_PREVIOUS_HTML_ELEMENT = new AccessibilityActionCompat(2048, null);
        public static final AccessibilityActionCompat ACTION_SCROLL_BACKWARD = new AccessibilityActionCompat(8192, null);
        public static final AccessibilityActionCompat ACTION_SCROLL_FORWARD = new AccessibilityActionCompat(4096, null);
        public static final AccessibilityActionCompat ACTION_SELECT = new AccessibilityActionCompat(4, null);
        public static final AccessibilityActionCompat ACTION_SET_SELECTION = new AccessibilityActionCompat(131072, null);
        public static final AccessibilityActionCompat ACTION_SET_TEXT = new AccessibilityActionCompat(2097152, null);
        private final Object mAction;

        public AccessibilityActionCompat(int $i0, CharSequence $r1) throws  {
            this(AccessibilityNodeInfoCompat.IMPL.newAccessibilityAction($i0, $r1));
        }

        private AccessibilityActionCompat(Object $r1) throws  {
            this.mAction = $r1;
        }

        public int getId() throws  {
            return AccessibilityNodeInfoCompat.IMPL.getAccessibilityActionId(this.mAction);
        }

        public CharSequence getLabel() throws  {
            return AccessibilityNodeInfoCompat.IMPL.getAccessibilityActionLabel(this.mAction);
        }
    }

    interface AccessibilityNodeInfoImpl {
        void addAction(Object obj, int i) throws ;

        void addAction(Object obj, Object obj2) throws ;

        void addChild(Object obj, View view) throws ;

        void addChild(Object obj, View view, int i) throws ;

        boolean canOpenPopup(Object obj) throws ;

        List<Object> findAccessibilityNodeInfosByText(@Signature({"(", "Ljava/lang/Object;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) Object obj, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) String str) throws ;

        List<Object> findAccessibilityNodeInfosByViewId(@Signature({"(", "Ljava/lang/Object;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) Object obj, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) String str) throws ;

        Object findFocus(Object obj, int i) throws ;

        Object focusSearch(Object obj, int i) throws ;

        int getAccessibilityActionId(Object obj) throws ;

        CharSequence getAccessibilityActionLabel(Object obj) throws ;

        List<Object> getActionList(@Signature({"(", "Ljava/lang/Object;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) Object obj) throws ;

        int getActions(Object obj) throws ;

        void getBoundsInParent(Object obj, Rect rect) throws ;

        void getBoundsInScreen(Object obj, Rect rect) throws ;

        Object getChild(Object obj, int i) throws ;

        int getChildCount(Object obj) throws ;

        CharSequence getClassName(Object obj) throws ;

        Object getCollectionInfo(Object obj) throws ;

        int getCollectionInfoColumnCount(Object obj) throws ;

        int getCollectionInfoRowCount(Object obj) throws ;

        int getCollectionItemColumnIndex(Object obj) throws ;

        int getCollectionItemColumnSpan(Object obj) throws ;

        Object getCollectionItemInfo(Object obj) throws ;

        int getCollectionItemRowIndex(Object obj) throws ;

        int getCollectionItemRowSpan(Object obj) throws ;

        CharSequence getContentDescription(Object obj) throws ;

        CharSequence getError(Object obj) throws ;

        Bundle getExtras(Object obj) throws ;

        int getInputType(Object obj) throws ;

        Object getLabelFor(Object obj) throws ;

        Object getLabeledBy(Object obj) throws ;

        int getLiveRegion(Object obj) throws ;

        int getMaxTextLength(Object obj) throws ;

        int getMovementGranularities(Object obj) throws ;

        CharSequence getPackageName(Object obj) throws ;

        Object getParent(Object obj) throws ;

        Object getRangeInfo(Object obj) throws ;

        CharSequence getText(Object obj) throws ;

        int getTextSelectionEnd(Object obj) throws ;

        int getTextSelectionStart(Object obj) throws ;

        Object getTraversalAfter(Object obj) throws ;

        Object getTraversalBefore(Object obj) throws ;

        String getViewIdResourceName(Object obj) throws ;

        Object getWindow(Object obj) throws ;

        int getWindowId(Object obj) throws ;

        boolean isAccessibilityFocused(Object obj) throws ;

        boolean isCheckable(Object obj) throws ;

        boolean isChecked(Object obj) throws ;

        boolean isClickable(Object obj) throws ;

        boolean isCollectionInfoHierarchical(Object obj) throws ;

        boolean isCollectionItemHeading(Object obj) throws ;

        boolean isCollectionItemSelected(Object obj) throws ;

        boolean isContentInvalid(Object obj) throws ;

        boolean isDismissable(Object obj) throws ;

        boolean isEditable(Object obj) throws ;

        boolean isEnabled(Object obj) throws ;

        boolean isFocusable(Object obj) throws ;

        boolean isFocused(Object obj) throws ;

        boolean isLongClickable(Object obj) throws ;

        boolean isMultiLine(Object obj) throws ;

        boolean isPassword(Object obj) throws ;

        boolean isScrollable(Object obj) throws ;

        boolean isSelected(Object obj) throws ;

        boolean isVisibleToUser(Object obj) throws ;

        Object newAccessibilityAction(int i, CharSequence charSequence) throws ;

        Object obtain() throws ;

        Object obtain(View view) throws ;

        Object obtain(View view, int i) throws ;

        Object obtain(Object obj) throws ;

        Object obtainCollectionInfo(int i, int i2, boolean z, int i3) throws ;

        Object obtainCollectionItemInfo(int i, int i2, int i3, int i4, boolean z, boolean z2) throws ;

        boolean performAction(Object obj, int i) throws ;

        boolean performAction(Object obj, int i, Bundle bundle) throws ;

        void recycle(Object obj) throws ;

        boolean refresh(Object obj) throws ;

        boolean removeAction(Object obj, Object obj2) throws ;

        boolean removeChild(Object obj, View view) throws ;

        boolean removeChild(Object obj, View view, int i) throws ;

        void setAccessibilityFocused(Object obj, boolean z) throws ;

        void setBoundsInParent(Object obj, Rect rect) throws ;

        void setBoundsInScreen(Object obj, Rect rect) throws ;

        void setCanOpenPopup(Object obj, boolean z) throws ;

        void setCheckable(Object obj, boolean z) throws ;

        void setChecked(Object obj, boolean z) throws ;

        void setClassName(Object obj, CharSequence charSequence) throws ;

        void setClickable(Object obj, boolean z) throws ;

        void setCollectionInfo(Object obj, Object obj2) throws ;

        void setCollectionItemInfo(Object obj, Object obj2) throws ;

        void setContentDescription(Object obj, CharSequence charSequence) throws ;

        void setContentInvalid(Object obj, boolean z) throws ;

        void setDismissable(Object obj, boolean z) throws ;

        void setEditable(Object obj, boolean z) throws ;

        void setEnabled(Object obj, boolean z) throws ;

        void setError(Object obj, CharSequence charSequence) throws ;

        void setFocusable(Object obj, boolean z) throws ;

        void setFocused(Object obj, boolean z) throws ;

        void setInputType(Object obj, int i) throws ;

        void setLabelFor(Object obj, View view) throws ;

        void setLabelFor(Object obj, View view, int i) throws ;

        void setLabeledBy(Object obj, View view) throws ;

        void setLabeledBy(Object obj, View view, int i) throws ;

        void setLiveRegion(Object obj, int i) throws ;

        void setLongClickable(Object obj, boolean z) throws ;

        void setMaxTextLength(Object obj, int i) throws ;

        void setMovementGranularities(Object obj, int i) throws ;

        void setMultiLine(Object obj, boolean z) throws ;

        void setPackageName(Object obj, CharSequence charSequence) throws ;

        void setParent(Object obj, View view) throws ;

        void setParent(Object obj, View view, int i) throws ;

        void setPassword(Object obj, boolean z) throws ;

        void setRangeInfo(Object obj, Object obj2) throws ;

        void setScrollable(Object obj, boolean z) throws ;

        void setSelected(Object obj, boolean z) throws ;

        void setSource(Object obj, View view) throws ;

        void setSource(Object obj, View view, int i) throws ;

        void setText(Object obj, CharSequence charSequence) throws ;

        void setTextSelection(Object obj, int i, int i2) throws ;

        void setTraversalAfter(Object obj, View view) throws ;

        void setTraversalAfter(Object obj, View view, int i) throws ;

        void setTraversalBefore(Object obj, View view) throws ;

        void setTraversalBefore(Object obj, View view, int i) throws ;

        void setViewIdResourceName(Object obj, String str) throws ;

        void setVisibleToUser(Object obj, boolean z) throws ;
    }

    static class AccessibilityNodeInfoStubImpl implements AccessibilityNodeInfoImpl {
        public boolean canOpenPopup(Object info) throws  {
            return false;
        }

        public Object findFocus(Object info, int focus) throws  {
            return null;
        }

        public Object focusSearch(Object info, int direction) throws  {
            return null;
        }

        public int getAccessibilityActionId(Object action) throws  {
            return 0;
        }

        public CharSequence getAccessibilityActionLabel(Object action) throws  {
            return null;
        }

        public List<Object> getActionList(@Signature({"(", "Ljava/lang/Object;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) Object info) throws  {
            return null;
        }

        public int getActions(Object info) throws  {
            return 0;
        }

        public Object getChild(Object info, int index) throws  {
            return null;
        }

        public int getChildCount(Object info) throws  {
            return 0;
        }

        public CharSequence getClassName(Object info) throws  {
            return null;
        }

        public Object getCollectionInfo(Object info) throws  {
            return null;
        }

        public int getCollectionInfoColumnCount(Object info) throws  {
            return 0;
        }

        public int getCollectionInfoRowCount(Object info) throws  {
            return 0;
        }

        public int getCollectionItemColumnIndex(Object info) throws  {
            return 0;
        }

        public int getCollectionItemColumnSpan(Object info) throws  {
            return 0;
        }

        public Object getCollectionItemInfo(Object info) throws  {
            return null;
        }

        public int getCollectionItemRowIndex(Object info) throws  {
            return 0;
        }

        public int getCollectionItemRowSpan(Object info) throws  {
            return 0;
        }

        public CharSequence getContentDescription(Object info) throws  {
            return null;
        }

        public CharSequence getError(Object info) throws  {
            return null;
        }

        public int getInputType(Object info) throws  {
            return 0;
        }

        public Object getLabelFor(Object info) throws  {
            return null;
        }

        public Object getLabeledBy(Object info) throws  {
            return null;
        }

        public int getLiveRegion(Object info) throws  {
            return 0;
        }

        public int getMaxTextLength(Object info) throws  {
            return -1;
        }

        public int getMovementGranularities(Object info) throws  {
            return 0;
        }

        public CharSequence getPackageName(Object info) throws  {
            return null;
        }

        public Object getParent(Object info) throws  {
            return null;
        }

        public Object getRangeInfo(Object info) throws  {
            return null;
        }

        public CharSequence getText(Object info) throws  {
            return null;
        }

        public int getTextSelectionEnd(Object info) throws  {
            return -1;
        }

        public int getTextSelectionStart(Object info) throws  {
            return -1;
        }

        public Object getTraversalAfter(Object info) throws  {
            return null;
        }

        public Object getTraversalBefore(Object info) throws  {
            return null;
        }

        public String getViewIdResourceName(Object info) throws  {
            return null;
        }

        public Object getWindow(Object info) throws  {
            return null;
        }

        public int getWindowId(Object info) throws  {
            return 0;
        }

        public boolean isAccessibilityFocused(Object info) throws  {
            return false;
        }

        public boolean isCheckable(Object info) throws  {
            return false;
        }

        public boolean isChecked(Object info) throws  {
            return false;
        }

        public boolean isClickable(Object info) throws  {
            return false;
        }

        public boolean isCollectionInfoHierarchical(Object info) throws  {
            return false;
        }

        public boolean isCollectionItemHeading(Object info) throws  {
            return false;
        }

        public boolean isCollectionItemSelected(Object info) throws  {
            return false;
        }

        public boolean isContentInvalid(Object info) throws  {
            return false;
        }

        public boolean isDismissable(Object info) throws  {
            return false;
        }

        public boolean isEditable(Object info) throws  {
            return false;
        }

        public boolean isEnabled(Object info) throws  {
            return false;
        }

        public boolean isFocusable(Object info) throws  {
            return false;
        }

        public boolean isFocused(Object info) throws  {
            return false;
        }

        public boolean isLongClickable(Object info) throws  {
            return false;
        }

        public boolean isMultiLine(Object info) throws  {
            return false;
        }

        public boolean isPassword(Object info) throws  {
            return false;
        }

        public boolean isScrollable(Object info) throws  {
            return false;
        }

        public boolean isSelected(Object info) throws  {
            return false;
        }

        public boolean isVisibleToUser(Object info) throws  {
            return false;
        }

        public Object newAccessibilityAction(int actionId, CharSequence label) throws  {
            return null;
        }

        public Object obtain() throws  {
            return null;
        }

        public Object obtain(View source) throws  {
            return null;
        }

        public Object obtain(View root, int virtualDescendantId) throws  {
            return null;
        }

        public Object obtain(Object info) throws  {
            return null;
        }

        public Object obtainCollectionInfo(int rowCount, int columnCount, boolean hierarchical, int selectionMode) throws  {
            return null;
        }

        public Object obtainCollectionItemInfo(int rowIndex, int rowSpan, int columnIndex, int columnSpan, boolean heading, boolean selected) throws  {
            return null;
        }

        public boolean performAction(Object info, int action) throws  {
            return false;
        }

        public boolean performAction(Object info, int action, Bundle arguments) throws  {
            return false;
        }

        public boolean refresh(Object info) throws  {
            return false;
        }

        public boolean removeAction(Object info, Object action) throws  {
            return false;
        }

        public boolean removeChild(Object info, View child) throws  {
            return false;
        }

        public boolean removeChild(Object info, View root, int virtualDescendantId) throws  {
            return false;
        }

        AccessibilityNodeInfoStubImpl() throws  {
        }

        public void addAction(Object info, int action) throws  {
        }

        public void addAction(Object info, Object action) throws  {
        }

        public void addChild(Object info, View child) throws  {
        }

        public void addChild(Object info, View child, int virtualDescendantId) throws  {
        }

        public List<Object> findAccessibilityNodeInfosByText(@Signature({"(", "Ljava/lang/Object;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) Object info, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) String text) throws  {
            return Collections.emptyList();
        }

        public void getBoundsInParent(Object info, Rect outBounds) throws  {
        }

        public void getBoundsInScreen(Object info, Rect outBounds) throws  {
        }

        public void setMovementGranularities(Object info, int granularities) throws  {
        }

        public void setBoundsInParent(Object info, Rect bounds) throws  {
        }

        public void setBoundsInScreen(Object info, Rect bounds) throws  {
        }

        public void setCheckable(Object info, boolean checkable) throws  {
        }

        public void setChecked(Object info, boolean checked) throws  {
        }

        public void setClassName(Object info, CharSequence className) throws  {
        }

        public void setClickable(Object info, boolean clickable) throws  {
        }

        public void setContentDescription(Object info, CharSequence contentDescription) throws  {
        }

        public void setEnabled(Object info, boolean enabled) throws  {
        }

        public void setFocusable(Object info, boolean focusable) throws  {
        }

        public void setFocused(Object info, boolean focused) throws  {
        }

        public void setVisibleToUser(Object info, boolean visibleToUser) throws  {
        }

        public void setAccessibilityFocused(Object info, boolean focused) throws  {
        }

        public void setLongClickable(Object info, boolean longClickable) throws  {
        }

        public void setPackageName(Object info, CharSequence packageName) throws  {
        }

        public void setParent(Object info, View parent) throws  {
        }

        public void setPassword(Object info, boolean password) throws  {
        }

        public void setScrollable(Object info, boolean scrollable) throws  {
        }

        public void setSelected(Object info, boolean selected) throws  {
        }

        public void setSource(Object info, View source) throws  {
        }

        public void setSource(Object info, View root, int virtualDescendantId) throws  {
        }

        public void setText(Object info, CharSequence text) throws  {
        }

        public void recycle(Object info) throws  {
        }

        public void setParent(Object info, View root, int virtualDescendantId) throws  {
        }

        public void setViewIdResourceName(Object info, String viewId) throws  {
        }

        public void setLiveRegion(Object info, int mode) throws  {
        }

        public void setCollectionInfo(Object info, Object collectionInfo) throws  {
        }

        public void setCollectionItemInfo(Object info, Object collectionItemInfo) throws  {
        }

        public void setRangeInfo(Object info, Object rangeInfo) throws  {
        }

        public void setTraversalBefore(Object info, View view) throws  {
        }

        public void setTraversalBefore(Object info, View root, int virtualDescendantId) throws  {
        }

        public void setTraversalAfter(Object info, View view) throws  {
        }

        public void setTraversalAfter(Object info, View root, int virtualDescendantId) throws  {
        }

        public void setContentInvalid(Object info, boolean contentInvalid) throws  {
        }

        public void setError(Object info, CharSequence error) throws  {
        }

        public void setLabelFor(Object info, View labeled) throws  {
        }

        public void setLabelFor(Object info, View root, int virtualDescendantId) throws  {
        }

        public void setLabeledBy(Object info, View labeled) throws  {
        }

        public void setLabeledBy(Object info, View root, int virtualDescendantId) throws  {
        }

        public void setCanOpenPopup(Object info, boolean opensPopup) throws  {
        }

        public List<Object> findAccessibilityNodeInfosByViewId(@Signature({"(", "Ljava/lang/Object;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) Object info, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) String viewId) throws  {
            return Collections.emptyList();
        }

        public Bundle getExtras(Object info) throws  {
            return new Bundle();
        }

        public void setInputType(Object info, int inputType) throws  {
        }

        public void setMaxTextLength(Object info, int max) throws  {
        }

        public void setTextSelection(Object info, int start, int end) throws  {
        }

        public void setDismissable(Object info, boolean dismissable) throws  {
        }

        public void setEditable(Object info, boolean editable) throws  {
        }

        public void setMultiLine(Object info, boolean multiLine) throws  {
        }
    }

    static class AccessibilityNodeInfoIcsImpl extends AccessibilityNodeInfoStubImpl {
        AccessibilityNodeInfoIcsImpl() throws  {
        }

        public Object obtain() throws  {
            return AccessibilityNodeInfoCompatIcs.obtain();
        }

        public Object obtain(View $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.obtain($r1);
        }

        public Object obtain(Object $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.obtain($r1);
        }

        public void addAction(Object $r1, int $i0) throws  {
            AccessibilityNodeInfoCompatIcs.addAction($r1, $i0);
        }

        public void addChild(Object $r1, View $r2) throws  {
            AccessibilityNodeInfoCompatIcs.addChild($r1, $r2);
        }

        public List<Object> findAccessibilityNodeInfosByText(@Signature({"(", "Ljava/lang/Object;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) Object $r1, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) String $r2) throws  {
            return AccessibilityNodeInfoCompatIcs.findAccessibilityNodeInfosByText($r1, $r2);
        }

        public int getActions(Object $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.getActions($r1);
        }

        public void getBoundsInParent(Object $r1, Rect $r2) throws  {
            AccessibilityNodeInfoCompatIcs.getBoundsInParent($r1, $r2);
        }

        public void getBoundsInScreen(Object $r1, Rect $r2) throws  {
            AccessibilityNodeInfoCompatIcs.getBoundsInScreen($r1, $r2);
        }

        public Object getChild(Object $r1, int $i0) throws  {
            return AccessibilityNodeInfoCompatIcs.getChild($r1, $i0);
        }

        public int getChildCount(Object $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.getChildCount($r1);
        }

        public CharSequence getClassName(Object $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.getClassName($r1);
        }

        public CharSequence getContentDescription(Object $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.getContentDescription($r1);
        }

        public CharSequence getPackageName(Object $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.getPackageName($r1);
        }

        public Object getParent(Object $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.getParent($r1);
        }

        public CharSequence getText(Object $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.getText($r1);
        }

        public int getWindowId(Object $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.getWindowId($r1);
        }

        public boolean isCheckable(Object $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.isCheckable($r1);
        }

        public boolean isChecked(Object $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.isChecked($r1);
        }

        public boolean isClickable(Object $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.isClickable($r1);
        }

        public boolean isEnabled(Object $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.isEnabled($r1);
        }

        public boolean isFocusable(Object $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.isFocusable($r1);
        }

        public boolean isFocused(Object $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.isFocused($r1);
        }

        public boolean isLongClickable(Object $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.isLongClickable($r1);
        }

        public boolean isPassword(Object $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.isPassword($r1);
        }

        public boolean isScrollable(Object $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.isScrollable($r1);
        }

        public boolean isSelected(Object $r1) throws  {
            return AccessibilityNodeInfoCompatIcs.isSelected($r1);
        }

        public boolean performAction(Object $r1, int $i0) throws  {
            return AccessibilityNodeInfoCompatIcs.performAction($r1, $i0);
        }

        public void setBoundsInParent(Object $r1, Rect $r2) throws  {
            AccessibilityNodeInfoCompatIcs.setBoundsInParent($r1, $r2);
        }

        public void setBoundsInScreen(Object $r1, Rect $r2) throws  {
            AccessibilityNodeInfoCompatIcs.setBoundsInScreen($r1, $r2);
        }

        public void setCheckable(Object $r1, boolean $z0) throws  {
            AccessibilityNodeInfoCompatIcs.setCheckable($r1, $z0);
        }

        public void setChecked(Object $r1, boolean $z0) throws  {
            AccessibilityNodeInfoCompatIcs.setChecked($r1, $z0);
        }

        public void setClassName(Object $r1, CharSequence $r2) throws  {
            AccessibilityNodeInfoCompatIcs.setClassName($r1, $r2);
        }

        public void setClickable(Object $r1, boolean $z0) throws  {
            AccessibilityNodeInfoCompatIcs.setClickable($r1, $z0);
        }

        public void setContentDescription(Object $r1, CharSequence $r2) throws  {
            AccessibilityNodeInfoCompatIcs.setContentDescription($r1, $r2);
        }

        public void setEnabled(Object $r1, boolean $z0) throws  {
            AccessibilityNodeInfoCompatIcs.setEnabled($r1, $z0);
        }

        public void setFocusable(Object $r1, boolean $z0) throws  {
            AccessibilityNodeInfoCompatIcs.setFocusable($r1, $z0);
        }

        public void setFocused(Object $r1, boolean $z0) throws  {
            AccessibilityNodeInfoCompatIcs.setFocused($r1, $z0);
        }

        public void setLongClickable(Object $r1, boolean $z0) throws  {
            AccessibilityNodeInfoCompatIcs.setLongClickable($r1, $z0);
        }

        public void setPackageName(Object $r1, CharSequence $r2) throws  {
            AccessibilityNodeInfoCompatIcs.setPackageName($r1, $r2);
        }

        public void setParent(Object $r1, View $r2) throws  {
            AccessibilityNodeInfoCompatIcs.setParent($r1, $r2);
        }

        public void setPassword(Object $r1, boolean $z0) throws  {
            AccessibilityNodeInfoCompatIcs.setPassword($r1, $z0);
        }

        public void setScrollable(Object $r1, boolean $z0) throws  {
            AccessibilityNodeInfoCompatIcs.setScrollable($r1, $z0);
        }

        public void setSelected(Object $r1, boolean $z0) throws  {
            AccessibilityNodeInfoCompatIcs.setSelected($r1, $z0);
        }

        public void setSource(Object $r1, View $r2) throws  {
            AccessibilityNodeInfoCompatIcs.setSource($r1, $r2);
        }

        public void setText(Object $r1, CharSequence $r2) throws  {
            AccessibilityNodeInfoCompatIcs.setText($r1, $r2);
        }

        public void recycle(Object $r1) throws  {
            AccessibilityNodeInfoCompatIcs.recycle($r1);
        }
    }

    static class AccessibilityNodeInfoJellybeanImpl extends AccessibilityNodeInfoIcsImpl {
        AccessibilityNodeInfoJellybeanImpl() throws  {
        }

        public Object obtain(View $r1, int $i0) throws  {
            return AccessibilityNodeInfoCompatJellyBean.obtain($r1, $i0);
        }

        public Object findFocus(Object $r1, int $i0) throws  {
            return AccessibilityNodeInfoCompatJellyBean.findFocus($r1, $i0);
        }

        public Object focusSearch(Object $r1, int $i0) throws  {
            return AccessibilityNodeInfoCompatJellyBean.focusSearch($r1, $i0);
        }

        public void addChild(Object $r1, View $r2, int $i0) throws  {
            AccessibilityNodeInfoCompatJellyBean.addChild($r1, $r2, $i0);
        }

        public void setSource(Object $r1, View $r2, int $i0) throws  {
            AccessibilityNodeInfoCompatJellyBean.setSource($r1, $r2, $i0);
        }

        public boolean isVisibleToUser(Object $r1) throws  {
            return AccessibilityNodeInfoCompatJellyBean.isVisibleToUser($r1);
        }

        public void setVisibleToUser(Object $r1, boolean $z0) throws  {
            AccessibilityNodeInfoCompatJellyBean.setVisibleToUser($r1, $z0);
        }

        public boolean isAccessibilityFocused(Object $r1) throws  {
            return AccessibilityNodeInfoCompatJellyBean.isAccessibilityFocused($r1);
        }

        public void setAccessibilityFocused(Object $r1, boolean $z0) throws  {
            AccessibilityNodeInfoCompatJellyBean.setAccesibilityFocused($r1, $z0);
        }

        public boolean performAction(Object $r1, int $i0, Bundle $r2) throws  {
            return AccessibilityNodeInfoCompatJellyBean.performAction($r1, $i0, $r2);
        }

        public void setMovementGranularities(Object $r1, int $i0) throws  {
            AccessibilityNodeInfoCompatJellyBean.setMovementGranularities($r1, $i0);
        }

        public int getMovementGranularities(Object $r1) throws  {
            return AccessibilityNodeInfoCompatJellyBean.getMovementGranularities($r1);
        }

        public void setParent(Object $r1, View $r2, int $i0) throws  {
            AccessibilityNodeInfoCompatJellyBean.setParent($r1, $r2, $i0);
        }
    }

    static class AccessibilityNodeInfoJellybeanMr1Impl extends AccessibilityNodeInfoJellybeanImpl {
        AccessibilityNodeInfoJellybeanMr1Impl() throws  {
        }

        public void setLabelFor(Object $r1, View $r2) throws  {
            AccessibilityNodeInfoCompatJellybeanMr1.setLabelFor($r1, $r2);
        }

        public void setLabelFor(Object $r1, View $r2, int $i0) throws  {
            AccessibilityNodeInfoCompatJellybeanMr1.setLabelFor($r1, $r2, $i0);
        }

        public Object getLabelFor(Object $r1) throws  {
            return AccessibilityNodeInfoCompatJellybeanMr1.getLabelFor($r1);
        }

        public void setLabeledBy(Object $r1, View $r2) throws  {
            AccessibilityNodeInfoCompatJellybeanMr1.setLabeledBy($r1, $r2);
        }

        public void setLabeledBy(Object $r1, View $r2, int $i0) throws  {
            AccessibilityNodeInfoCompatJellybeanMr1.setLabeledBy($r1, $r2, $i0);
        }

        public Object getLabeledBy(Object $r1) throws  {
            return AccessibilityNodeInfoCompatJellybeanMr1.getLabeledBy($r1);
        }
    }

    static class AccessibilityNodeInfoJellybeanMr2Impl extends AccessibilityNodeInfoJellybeanMr1Impl {
        AccessibilityNodeInfoJellybeanMr2Impl() throws  {
        }

        public String getViewIdResourceName(Object $r1) throws  {
            return AccessibilityNodeInfoCompatJellybeanMr2.getViewIdResourceName($r1);
        }

        public void setViewIdResourceName(Object $r1, String $r2) throws  {
            AccessibilityNodeInfoCompatJellybeanMr2.setViewIdResourceName($r1, $r2);
        }

        public List<Object> findAccessibilityNodeInfosByViewId(@Signature({"(", "Ljava/lang/Object;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) Object $r1, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) String $r2) throws  {
            return AccessibilityNodeInfoCompatJellybeanMr2.findAccessibilityNodeInfosByViewId($r1, $r2);
        }

        public void setTextSelection(Object $r1, int $i0, int $i1) throws  {
            AccessibilityNodeInfoCompatJellybeanMr2.setTextSelection($r1, $i0, $i1);
        }

        public int getTextSelectionStart(Object $r1) throws  {
            return AccessibilityNodeInfoCompatJellybeanMr2.getTextSelectionStart($r1);
        }

        public int getTextSelectionEnd(Object $r1) throws  {
            return AccessibilityNodeInfoCompatJellybeanMr2.getTextSelectionEnd($r1);
        }

        public boolean isEditable(Object $r1) throws  {
            return AccessibilityNodeInfoCompatJellybeanMr2.isEditable($r1);
        }

        public void setEditable(Object $r1, boolean $z0) throws  {
            AccessibilityNodeInfoCompatJellybeanMr2.setEditable($r1, $z0);
        }

        public boolean refresh(Object $r1) throws  {
            return AccessibilityNodeInfoCompatJellybeanMr2.refresh($r1);
        }
    }

    static class AccessibilityNodeInfoKitKatImpl extends AccessibilityNodeInfoJellybeanMr2Impl {
        AccessibilityNodeInfoKitKatImpl() throws  {
        }

        public int getLiveRegion(Object $r1) throws  {
            return AccessibilityNodeInfoCompatKitKat.getLiveRegion($r1);
        }

        public void setLiveRegion(Object $r1, int $i0) throws  {
            AccessibilityNodeInfoCompatKitKat.setLiveRegion($r1, $i0);
        }

        public Object getCollectionInfo(Object $r1) throws  {
            return AccessibilityNodeInfoCompatKitKat.getCollectionInfo($r1);
        }

        public void setCollectionInfo(Object $r1, Object $r2) throws  {
            AccessibilityNodeInfoCompatKitKat.setCollectionInfo($r1, $r2);
        }

        public Object obtainCollectionInfo(int $i0, int $i1, boolean $z0, int $i2) throws  {
            return AccessibilityNodeInfoCompatKitKat.obtainCollectionInfo($i0, $i1, $z0, $i2);
        }

        public Object obtainCollectionItemInfo(int $i0, int $i1, int $i2, int $i3, boolean $z0, boolean selected) throws  {
            return AccessibilityNodeInfoCompatKitKat.obtainCollectionItemInfo($i0, $i1, $i2, $i3, $z0);
        }

        public int getCollectionInfoColumnCount(Object $r1) throws  {
            return CollectionInfo.getColumnCount($r1);
        }

        public int getCollectionInfoRowCount(Object $r1) throws  {
            return CollectionInfo.getRowCount($r1);
        }

        public boolean isCollectionInfoHierarchical(Object $r1) throws  {
            return CollectionInfo.isHierarchical($r1);
        }

        public Object getCollectionItemInfo(Object $r1) throws  {
            return AccessibilityNodeInfoCompatKitKat.getCollectionItemInfo($r1);
        }

        public Object getRangeInfo(Object $r1) throws  {
            return AccessibilityNodeInfoCompatKitKat.getRangeInfo($r1);
        }

        public void setRangeInfo(Object $r1, Object $r2) throws  {
            AccessibilityNodeInfoCompatKitKat.setRangeInfo($r1, $r2);
        }

        public int getCollectionItemColumnIndex(Object $r1) throws  {
            return CollectionItemInfo.getColumnIndex($r1);
        }

        public int getCollectionItemColumnSpan(Object $r1) throws  {
            return CollectionItemInfo.getColumnSpan($r1);
        }

        public int getCollectionItemRowIndex(Object $r1) throws  {
            return CollectionItemInfo.getRowIndex($r1);
        }

        public int getCollectionItemRowSpan(Object $r1) throws  {
            return CollectionItemInfo.getRowSpan($r1);
        }

        public boolean isCollectionItemHeading(Object $r1) throws  {
            return CollectionItemInfo.isHeading($r1);
        }

        public void setCollectionItemInfo(Object $r1, Object $r2) throws  {
            AccessibilityNodeInfoCompatKitKat.setCollectionItemInfo($r1, $r2);
        }

        public void setContentInvalid(Object $r1, boolean $z0) throws  {
            AccessibilityNodeInfoCompatKitKat.setContentInvalid($r1, $z0);
        }

        public boolean isContentInvalid(Object $r1) throws  {
            return AccessibilityNodeInfoCompatKitKat.isContentInvalid($r1);
        }

        public boolean canOpenPopup(Object $r1) throws  {
            return AccessibilityNodeInfoCompatKitKat.canOpenPopup($r1);
        }

        public void setCanOpenPopup(Object $r1, boolean $z0) throws  {
            AccessibilityNodeInfoCompatKitKat.setCanOpenPopup($r1, $z0);
        }

        public Bundle getExtras(Object $r1) throws  {
            return AccessibilityNodeInfoCompatKitKat.getExtras($r1);
        }

        public int getInputType(Object $r1) throws  {
            return AccessibilityNodeInfoCompatKitKat.getInputType($r1);
        }

        public void setInputType(Object $r1, int $i0) throws  {
            AccessibilityNodeInfoCompatKitKat.setInputType($r1, $i0);
        }

        public boolean isDismissable(Object $r1) throws  {
            return AccessibilityNodeInfoCompatKitKat.isDismissable($r1);
        }

        public void setDismissable(Object $r1, boolean $z0) throws  {
            AccessibilityNodeInfoCompatKitKat.setDismissable($r1, $z0);
        }

        public boolean isMultiLine(Object $r1) throws  {
            return AccessibilityNodeInfoCompatKitKat.isMultiLine($r1);
        }

        public void setMultiLine(Object $r1, boolean $z0) throws  {
            AccessibilityNodeInfoCompatKitKat.setMultiLine($r1, $z0);
        }
    }

    static class AccessibilityNodeInfoApi21Impl extends AccessibilityNodeInfoKitKatImpl {
        AccessibilityNodeInfoApi21Impl() throws  {
        }

        public Object newAccessibilityAction(int $i0, CharSequence $r1) throws  {
            return AccessibilityNodeInfoCompatApi21.newAccessibilityAction($i0, $r1);
        }

        public List<Object> getActionList(@Signature({"(", "Ljava/lang/Object;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) Object $r1) throws  {
            return AccessibilityNodeInfoCompatApi21.getActionList($r1);
        }

        public Object obtainCollectionInfo(int $i0, int $i1, boolean $z0, int $i2) throws  {
            return AccessibilityNodeInfoCompatApi21.obtainCollectionInfo($i0, $i1, $z0, $i2);
        }

        public void addAction(Object $r1, Object $r2) throws  {
            AccessibilityNodeInfoCompatApi21.addAction($r1, $r2);
        }

        public boolean removeAction(Object $r1, Object $r2) throws  {
            return AccessibilityNodeInfoCompatApi21.removeAction($r1, $r2);
        }

        public int getAccessibilityActionId(Object $r1) throws  {
            return AccessibilityNodeInfoCompatApi21.getAccessibilityActionId($r1);
        }

        public CharSequence getAccessibilityActionLabel(Object $r1) throws  {
            return AccessibilityNodeInfoCompatApi21.getAccessibilityActionLabel($r1);
        }

        public Object obtainCollectionItemInfo(int $i0, int $i1, int $i2, int $i3, boolean $z0, boolean $z1) throws  {
            return AccessibilityNodeInfoCompatApi21.obtainCollectionItemInfo($i0, $i1, $i2, $i3, $z0, $z1);
        }

        public boolean isCollectionItemSelected(Object $r1) throws  {
            return CollectionItemInfo.isSelected($r1);
        }

        public CharSequence getError(Object $r1) throws  {
            return AccessibilityNodeInfoCompatApi21.getError($r1);
        }

        public void setError(Object $r1, CharSequence $r2) throws  {
            AccessibilityNodeInfoCompatApi21.setError($r1, $r2);
        }

        public void setMaxTextLength(Object $r1, int $i0) throws  {
            AccessibilityNodeInfoCompatApi21.setMaxTextLength($r1, $i0);
        }

        public int getMaxTextLength(Object $r1) throws  {
            return AccessibilityNodeInfoCompatApi21.getMaxTextLength($r1);
        }

        public Object getWindow(Object $r1) throws  {
            return AccessibilityNodeInfoCompatApi21.getWindow($r1);
        }

        public boolean removeChild(Object $r1, View $r2) throws  {
            return AccessibilityNodeInfoCompatApi21.removeChild($r1, $r2);
        }

        public boolean removeChild(Object $r1, View $r2, int $i0) throws  {
            return AccessibilityNodeInfoCompatApi21.removeChild($r1, $r2, $i0);
        }
    }

    static class AccessibilityNodeInfoApi22Impl extends AccessibilityNodeInfoApi21Impl {
        AccessibilityNodeInfoApi22Impl() throws  {
        }

        public Object getTraversalBefore(Object $r1) throws  {
            return AccessibilityNodeInfoCompatApi22.getTraversalBefore($r1);
        }

        public void setTraversalBefore(Object $r1, View $r2) throws  {
            AccessibilityNodeInfoCompatApi22.setTraversalBefore($r1, $r2);
        }

        public void setTraversalBefore(Object $r1, View $r2, int $i0) throws  {
            AccessibilityNodeInfoCompatApi22.setTraversalBefore($r1, $r2, $i0);
        }

        public Object getTraversalAfter(Object $r1) throws  {
            return AccessibilityNodeInfoCompatApi22.getTraversalAfter($r1);
        }

        public void setTraversalAfter(Object $r1, View $r2) throws  {
            AccessibilityNodeInfoCompatApi22.setTraversalAfter($r1, $r2);
        }

        public void setTraversalAfter(Object $r1, View $r2, int $i0) throws  {
            AccessibilityNodeInfoCompatApi22.setTraversalAfter($r1, $r2, $i0);
        }
    }

    public static class CollectionInfoCompat {
        public static final int SELECTION_MODE_MULTIPLE = 2;
        public static final int SELECTION_MODE_NONE = 0;
        public static final int SELECTION_MODE_SINGLE = 1;
        final Object mInfo;

        public static CollectionInfoCompat obtain(int $i0, int $i1, boolean $z0, int $i2) throws  {
            return new CollectionInfoCompat(AccessibilityNodeInfoCompat.IMPL.obtainCollectionInfo($i0, $i1, $z0, $i2));
        }

        private CollectionInfoCompat(Object $r1) throws  {
            this.mInfo = $r1;
        }

        public int getColumnCount() throws  {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionInfoColumnCount(this.mInfo);
        }

        public int getRowCount() throws  {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionInfoRowCount(this.mInfo);
        }

        public boolean isHierarchical() throws  {
            return AccessibilityNodeInfoCompat.IMPL.isCollectionInfoHierarchical(this.mInfo);
        }
    }

    public static class CollectionItemInfoCompat {
        private final Object mInfo;

        public static CollectionItemInfoCompat obtain(int $i0, int $i1, int $i2, int $i3, boolean $z0, boolean $z1) throws  {
            return new CollectionItemInfoCompat(AccessibilityNodeInfoCompat.IMPL.obtainCollectionItemInfo($i0, $i1, $i2, $i3, $z0, $z1));
        }

        private CollectionItemInfoCompat(Object $r1) throws  {
            this.mInfo = $r1;
        }

        public int getColumnIndex() throws  {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionItemColumnIndex(this.mInfo);
        }

        public int getColumnSpan() throws  {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionItemColumnSpan(this.mInfo);
        }

        public int getRowIndex() throws  {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionItemRowIndex(this.mInfo);
        }

        public int getRowSpan() throws  {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionItemRowSpan(this.mInfo);
        }

        public boolean isHeading() throws  {
            return AccessibilityNodeInfoCompat.IMPL.isCollectionItemHeading(this.mInfo);
        }

        public boolean isSelected() throws  {
            return AccessibilityNodeInfoCompat.IMPL.isCollectionItemSelected(this.mInfo);
        }
    }

    public static class RangeInfoCompat {
        public static final int RANGE_TYPE_FLOAT = 1;
        public static final int RANGE_TYPE_INT = 0;
        public static final int RANGE_TYPE_PERCENT = 2;
        private final Object mInfo;

        private RangeInfoCompat(Object $r1) throws  {
            this.mInfo = $r1;
        }

        public float getCurrent() throws  {
            return RangeInfo.getCurrent(this.mInfo);
        }

        public float getMax() throws  {
            return RangeInfo.getMax(this.mInfo);
        }

        public float getMin() throws  {
            return RangeInfo.getMin(this.mInfo);
        }

        public int getType() throws  {
            return RangeInfo.getType(this.mInfo);
        }
    }

    private static String getActionSymbolicName(int $i0) throws  {
        switch ($i0) {
            case 1:
                return "ACTION_FOCUS";
            case 2:
                return "ACTION_CLEAR_FOCUS";
            case 4:
                return "ACTION_SELECT";
            case 8:
                return "ACTION_CLEAR_SELECTION";
            case 16:
                return "ACTION_CLICK";
            case 32:
                return "ACTION_LONG_CLICK";
            case 64:
                return "ACTION_ACCESSIBILITY_FOCUS";
            case 128:
                return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
            case 256:
                return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
            case 512:
                return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
            case 1024:
                return "ACTION_NEXT_HTML_ELEMENT";
            case 2048:
                return "ACTION_PREVIOUS_HTML_ELEMENT";
            case 4096:
                return "ACTION_SCROLL_FORWARD";
            case 8192:
                return "ACTION_SCROLL_BACKWARD";
            case 16384:
                return "ACTION_COPY";
            case 32768:
                return "ACTION_PASTE";
            case 65536:
                return "ACTION_CUT";
            case 131072:
                return "ACTION_SET_SELECTION";
            default:
                return "ACTION_UNKNOWN";
        }
    }

    public java.lang.String toString() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.toString():java.lang.String
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-int
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 7 more
*/
        /*
        // Can't load method instructions.
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.toString():java.lang.String");
    }

    static {
        if (VERSION.SDK_INT >= 22) {
            IMPL = new AccessibilityNodeInfoApi22Impl();
        } else if (VERSION.SDK_INT >= 21) {
            IMPL = new AccessibilityNodeInfoApi21Impl();
        } else if (VERSION.SDK_INT >= 19) {
            IMPL = new AccessibilityNodeInfoKitKatImpl();
        } else if (VERSION.SDK_INT >= 18) {
            IMPL = new AccessibilityNodeInfoJellybeanMr2Impl();
        } else if (VERSION.SDK_INT >= 17) {
            IMPL = new AccessibilityNodeInfoJellybeanMr1Impl();
        } else if (VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityNodeInfoJellybeanImpl();
        } else if (VERSION.SDK_INT >= 14) {
            IMPL = new AccessibilityNodeInfoIcsImpl();
        } else {
            IMPL = new AccessibilityNodeInfoStubImpl();
        }
    }

    static AccessibilityNodeInfoCompat wrapNonNullInstance(Object $r0) throws  {
        if ($r0 != null) {
            return new AccessibilityNodeInfoCompat($r0);
        }
        return null;
    }

    public AccessibilityNodeInfoCompat(Object $r1) throws  {
        this.mInfo = $r1;
    }

    public Object getInfo() throws  {
        return this.mInfo;
    }

    public static AccessibilityNodeInfoCompat obtain(View $r0) throws  {
        return wrapNonNullInstance(IMPL.obtain($r0));
    }

    public static AccessibilityNodeInfoCompat obtain(View $r0, int $i0) throws  {
        return wrapNonNullInstance(IMPL.obtain($r0, $i0));
    }

    public static AccessibilityNodeInfoCompat obtain() throws  {
        return wrapNonNullInstance(IMPL.obtain());
    }

    public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat $r0) throws  {
        return wrapNonNullInstance(IMPL.obtain($r0.mInfo));
    }

    public void setSource(View $r1) throws  {
        IMPL.setSource(this.mInfo, $r1);
    }

    public void setSource(View $r1, int $i0) throws  {
        IMPL.setSource(this.mInfo, $r1, $i0);
    }

    public AccessibilityNodeInfoCompat findFocus(int $i0) throws  {
        return wrapNonNullInstance(IMPL.findFocus(this.mInfo, $i0));
    }

    public AccessibilityNodeInfoCompat focusSearch(int $i0) throws  {
        return wrapNonNullInstance(IMPL.focusSearch(this.mInfo, $i0));
    }

    public int getWindowId() throws  {
        return IMPL.getWindowId(this.mInfo);
    }

    public int getChildCount() throws  {
        return IMPL.getChildCount(this.mInfo);
    }

    public AccessibilityNodeInfoCompat getChild(int $i0) throws  {
        return wrapNonNullInstance(IMPL.getChild(this.mInfo, $i0));
    }

    public void addChild(View $r1) throws  {
        IMPL.addChild(this.mInfo, $r1);
    }

    public void addChild(View $r1, int $i0) throws  {
        IMPL.addChild(this.mInfo, $r1, $i0);
    }

    public boolean removeChild(View $r1) throws  {
        return IMPL.removeChild(this.mInfo, $r1);
    }

    public boolean removeChild(View $r1, int $i0) throws  {
        return IMPL.removeChild(this.mInfo, $r1, $i0);
    }

    public int getActions() throws  {
        return IMPL.getActions(this.mInfo);
    }

    public void addAction(int $i0) throws  {
        IMPL.addAction(this.mInfo, $i0);
    }

    public void addAction(AccessibilityActionCompat $r1) throws  {
        IMPL.addAction(this.mInfo, $r1.mAction);
    }

    public boolean removeAction(AccessibilityActionCompat $r1) throws  {
        return IMPL.removeAction(this.mInfo, $r1.mAction);
    }

    public boolean performAction(int $i0) throws  {
        return IMPL.performAction(this.mInfo, $i0);
    }

    public boolean performAction(int $i0, Bundle $r1) throws  {
        return IMPL.performAction(this.mInfo, $i0, $r1);
    }

    public void setMovementGranularities(int $i0) throws  {
        IMPL.setMovementGranularities(this.mInfo, $i0);
    }

    public int getMovementGranularities() throws  {
        return IMPL.getMovementGranularities(this.mInfo);
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(@Signature({"(", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Landroid/support/v4/view/accessibility/AccessibilityNodeInfoCompat;", ">;"}) String $r1) throws  {
        ArrayList $r2 = new ArrayList();
        List $r5 = IMPL.findAccessibilityNodeInfosByText(this.mInfo, $r1);
        int $i0 = $r5.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r2.add(new AccessibilityNodeInfoCompat($r5.get($i1)));
        }
        return $r2;
    }

    public AccessibilityNodeInfoCompat getParent() throws  {
        return wrapNonNullInstance(IMPL.getParent(this.mInfo));
    }

    public void setParent(View $r1) throws  {
        IMPL.setParent(this.mInfo, $r1);
    }

    public void setParent(View $r1, int $i0) throws  {
        IMPL.setParent(this.mInfo, $r1, $i0);
    }

    public void getBoundsInParent(Rect $r1) throws  {
        IMPL.getBoundsInParent(this.mInfo, $r1);
    }

    public void setBoundsInParent(Rect $r1) throws  {
        IMPL.setBoundsInParent(this.mInfo, $r1);
    }

    public void getBoundsInScreen(Rect $r1) throws  {
        IMPL.getBoundsInScreen(this.mInfo, $r1);
    }

    public void setBoundsInScreen(Rect $r1) throws  {
        IMPL.setBoundsInScreen(this.mInfo, $r1);
    }

    public boolean isCheckable() throws  {
        return IMPL.isCheckable(this.mInfo);
    }

    public void setCheckable(boolean $z0) throws  {
        IMPL.setCheckable(this.mInfo, $z0);
    }

    public boolean isChecked() throws  {
        return IMPL.isChecked(this.mInfo);
    }

    public void setChecked(boolean $z0) throws  {
        IMPL.setChecked(this.mInfo, $z0);
    }

    public boolean isFocusable() throws  {
        return IMPL.isFocusable(this.mInfo);
    }

    public void setFocusable(boolean $z0) throws  {
        IMPL.setFocusable(this.mInfo, $z0);
    }

    public boolean isFocused() throws  {
        return IMPL.isFocused(this.mInfo);
    }

    public void setFocused(boolean $z0) throws  {
        IMPL.setFocused(this.mInfo, $z0);
    }

    public boolean isVisibleToUser() throws  {
        return IMPL.isVisibleToUser(this.mInfo);
    }

    public void setVisibleToUser(boolean $z0) throws  {
        IMPL.setVisibleToUser(this.mInfo, $z0);
    }

    public boolean isAccessibilityFocused() throws  {
        return IMPL.isAccessibilityFocused(this.mInfo);
    }

    public void setAccessibilityFocused(boolean $z0) throws  {
        IMPL.setAccessibilityFocused(this.mInfo, $z0);
    }

    public boolean isSelected() throws  {
        return IMPL.isSelected(this.mInfo);
    }

    public void setSelected(boolean $z0) throws  {
        IMPL.setSelected(this.mInfo, $z0);
    }

    public boolean isClickable() throws  {
        return IMPL.isClickable(this.mInfo);
    }

    public void setClickable(boolean $z0) throws  {
        IMPL.setClickable(this.mInfo, $z0);
    }

    public boolean isLongClickable() throws  {
        return IMPL.isLongClickable(this.mInfo);
    }

    public void setLongClickable(boolean $z0) throws  {
        IMPL.setLongClickable(this.mInfo, $z0);
    }

    public boolean isEnabled() throws  {
        return IMPL.isEnabled(this.mInfo);
    }

    public void setEnabled(boolean $z0) throws  {
        IMPL.setEnabled(this.mInfo, $z0);
    }

    public boolean isPassword() throws  {
        return IMPL.isPassword(this.mInfo);
    }

    public void setPassword(boolean $z0) throws  {
        IMPL.setPassword(this.mInfo, $z0);
    }

    public boolean isScrollable() throws  {
        return IMPL.isScrollable(this.mInfo);
    }

    public void setScrollable(boolean $z0) throws  {
        IMPL.setScrollable(this.mInfo, $z0);
    }

    public CharSequence getPackageName() throws  {
        return IMPL.getPackageName(this.mInfo);
    }

    public void setPackageName(CharSequence $r1) throws  {
        IMPL.setPackageName(this.mInfo, $r1);
    }

    public CharSequence getClassName() throws  {
        return IMPL.getClassName(this.mInfo);
    }

    public void setClassName(CharSequence $r1) throws  {
        IMPL.setClassName(this.mInfo, $r1);
    }

    public CharSequence getText() throws  {
        return IMPL.getText(this.mInfo);
    }

    public void setText(CharSequence $r1) throws  {
        IMPL.setText(this.mInfo, $r1);
    }

    public CharSequence getContentDescription() throws  {
        return IMPL.getContentDescription(this.mInfo);
    }

    public void setContentDescription(CharSequence $r1) throws  {
        IMPL.setContentDescription(this.mInfo, $r1);
    }

    public void recycle() throws  {
        IMPL.recycle(this.mInfo);
    }

    public void setViewIdResourceName(String $r1) throws  {
        IMPL.setViewIdResourceName(this.mInfo, $r1);
    }

    public String getViewIdResourceName() throws  {
        return IMPL.getViewIdResourceName(this.mInfo);
    }

    public int getLiveRegion() throws  {
        return IMPL.getLiveRegion(this.mInfo);
    }

    public void setLiveRegion(int $i0) throws  {
        IMPL.setLiveRegion(this.mInfo, $i0);
    }

    public CollectionInfoCompat getCollectionInfo() throws  {
        Object $r1 = IMPL.getCollectionInfo(this.mInfo);
        return $r1 == null ? null : new CollectionInfoCompat($r1);
    }

    public void setCollectionInfo(Object $r3) throws  {
        IMPL.setCollectionInfo(this.mInfo, ((CollectionInfoCompat) $r3).mInfo);
    }

    public void setCollectionItemInfo(Object $r3) throws  {
        IMPL.setCollectionItemInfo(this.mInfo, ((CollectionItemInfoCompat) $r3).mInfo);
    }

    public CollectionItemInfoCompat getCollectionItemInfo() throws  {
        Object $r1 = IMPL.getCollectionItemInfo(this.mInfo);
        return $r1 == null ? null : new CollectionItemInfoCompat($r1);
    }

    public RangeInfoCompat getRangeInfo() throws  {
        Object $r1 = IMPL.getRangeInfo(this.mInfo);
        return $r1 == null ? null : new RangeInfoCompat($r1);
    }

    public void setRangeInfo(RangeInfoCompat $r1) throws  {
        IMPL.setRangeInfo(this.mInfo, $r1.mInfo);
    }

    public List<AccessibilityActionCompat> getActionList() throws  {
        List $r3 = IMPL.getActionList(this.mInfo);
        if ($r3 == null) {
            return Collections.emptyList();
        }
        ArrayList $r4 = new ArrayList();
        int $i0 = $r3.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r4.add(new AccessibilityActionCompat($r3.get($i1)));
        }
        return $r4;
    }

    public void setContentInvalid(boolean $z0) throws  {
        IMPL.setContentInvalid(this.mInfo, $z0);
    }

    public boolean isContentInvalid() throws  {
        return IMPL.isContentInvalid(this.mInfo);
    }

    public void setError(CharSequence $r1) throws  {
        IMPL.setError(this.mInfo, $r1);
    }

    public CharSequence getError() throws  {
        return IMPL.getError(this.mInfo);
    }

    public void setLabelFor(View $r1) throws  {
        IMPL.setLabelFor(this.mInfo, $r1);
    }

    public void setLabelFor(View $r1, int $i0) throws  {
        IMPL.setLabelFor(this.mInfo, $r1, $i0);
    }

    public AccessibilityNodeInfoCompat getLabelFor() throws  {
        return wrapNonNullInstance(IMPL.getLabelFor(this.mInfo));
    }

    public void setLabeledBy(View $r1) throws  {
        IMPL.setLabeledBy(this.mInfo, $r1);
    }

    public void setLabeledBy(View $r1, int $i0) throws  {
        IMPL.setLabeledBy(this.mInfo, $r1, $i0);
    }

    public AccessibilityNodeInfoCompat getLabeledBy() throws  {
        return wrapNonNullInstance(IMPL.getLabeledBy(this.mInfo));
    }

    public boolean canOpenPopup() throws  {
        return IMPL.canOpenPopup(this.mInfo);
    }

    public void setCanOpenPopup(boolean $z0) throws  {
        IMPL.setCanOpenPopup(this.mInfo, $z0);
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByViewId(@Signature({"(", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Landroid/support/v4/view/accessibility/AccessibilityNodeInfoCompat;", ">;"}) String $r1) throws  {
        List<Object> $r4 = IMPL.findAccessibilityNodeInfosByViewId(this.mInfo, $r1);
        if ($r4 == null) {
            return Collections.emptyList();
        }
        ArrayList $r5 = new ArrayList();
        for (Object $r2 : $r4) {
            $r5.add(new AccessibilityNodeInfoCompat($r2));
        }
        return $r5;
    }

    public Bundle getExtras() throws  {
        return IMPL.getExtras(this.mInfo);
    }

    public int getInputType() throws  {
        return IMPL.getInputType(this.mInfo);
    }

    public void setInputType(int $i0) throws  {
        IMPL.setInputType(this.mInfo, $i0);
    }

    public void setMaxTextLength(int $i0) throws  {
        IMPL.setMaxTextLength(this.mInfo, $i0);
    }

    public int getMaxTextLength() throws  {
        return IMPL.getMaxTextLength(this.mInfo);
    }

    public void setTextSelection(int $i0, int $i1) throws  {
        IMPL.setTextSelection(this.mInfo, $i0, $i1);
    }

    public int getTextSelectionStart() throws  {
        return IMPL.getTextSelectionStart(this.mInfo);
    }

    public int getTextSelectionEnd() throws  {
        return IMPL.getTextSelectionEnd(this.mInfo);
    }

    public AccessibilityNodeInfoCompat getTraversalBefore() throws  {
        return wrapNonNullInstance(IMPL.getTraversalBefore(this.mInfo));
    }

    public void setTraversalBefore(View $r1) throws  {
        IMPL.setTraversalBefore(this.mInfo, $r1);
    }

    public void setTraversalBefore(View $r1, int $i0) throws  {
        IMPL.setTraversalBefore(this.mInfo, $r1, $i0);
    }

    public AccessibilityNodeInfoCompat getTraversalAfter() throws  {
        return wrapNonNullInstance(IMPL.getTraversalAfter(this.mInfo));
    }

    public void setTraversalAfter(View $r1) throws  {
        IMPL.setTraversalAfter(this.mInfo, $r1);
    }

    public void setTraversalAfter(View $r1, int $i0) throws  {
        IMPL.setTraversalAfter(this.mInfo, $r1, $i0);
    }

    public AccessibilityWindowInfoCompat getWindow() throws  {
        return AccessibilityWindowInfoCompat.wrapNonNullInstance(IMPL.getWindow(this.mInfo));
    }

    public boolean isDismissable() throws  {
        return IMPL.isDismissable(this.mInfo);
    }

    public void setDismissable(boolean $z0) throws  {
        IMPL.setDismissable(this.mInfo, $z0);
    }

    public boolean isEditable() throws  {
        return IMPL.isEditable(this.mInfo);
    }

    public void setEditable(boolean $z0) throws  {
        IMPL.setEditable(this.mInfo, $z0);
    }

    public boolean isMultiLine() throws  {
        return IMPL.isMultiLine(this.mInfo);
    }

    public void setMultiLine(boolean $z0) throws  {
        IMPL.setMultiLine(this.mInfo, $z0);
    }

    public boolean refresh() throws  {
        return IMPL.refresh(this.mInfo);
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
        AccessibilityNodeInfoCompat $r4 = (AccessibilityNodeInfoCompat) $r1;
        if (this.mInfo != null) {
            return this.mInfo.equals($r4.mInfo);
        } else {
            if ($r4.mInfo != null) {
                return false;
            }
            return true;
        }
    }
}
