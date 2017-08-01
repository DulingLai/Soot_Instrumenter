package net.simonvt.numberpicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.ActivityChooserView.ActivityChooserViewAdapter;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.LayoutManager;
import com.waze.map.CanvasFont;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@SuppressLint({"NewApi"})
public class NumberPicker extends LinearLayout {
    private static final int DEFAULT_LAYOUT_RESOURCE_ID = 0;
    private static final long DEFAULT_LONG_PRESS_UPDATE_INTERVAL = 300;
    private static final char[] DIGIT_CHARACTERS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩', '۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹'};
    private static final int SELECTOR_ADJUSTMENT_DURATION_MILLIS = 800;
    private static final int SELECTOR_MAX_FLING_VELOCITY_ADJUSTMENT = 8;
    private static final int SELECTOR_MIDDLE_ITEM_INDEX = 1;
    private static final int SELECTOR_WHEEL_ITEM_COUNT = 3;
    private static final int SIZE_UNSPECIFIED = -1;
    private static final int SNAP_SCROLL_DURATION = 300;
    private static final float TOP_AND_BOTTOM_FADING_EDGE_STRENGTH = 0.9f;
    private static final int UNSCALED_DEFAULT_SELECTION_DIVIDERS_DISTANCE = 48;
    private static final int UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT = 2;
    private static final TwoDigitFormatter sTwoDigitFormatter = new TwoDigitFormatter();
    private SupportAccessibilityNodeProvider mAccessibilityNodeProvider;
    private final Scroller mAdjustScroller;
    private BeginSoftInputOnLongPressCommand mBeginSoftInputOnLongPressCommand;
    private int mBottomSelectionDividerBottom;
    private ChangeCurrentByOneFromLongPressCommand mChangeCurrentByOneFromLongPressCommand;
    private final boolean mComputeMaxWidth;
    private int mCurrentScrollOffset;
    private final ImageButton mDecrementButton;
    private boolean mDecrementVirtualButtonPressed;
    private String[] mDisplayedValues;
    private final Scroller mFlingScroller;
    private Formatter mFormatter;
    private final boolean mHasSelectorWheel;
    private final ImageButton mIncrementButton;
    private boolean mIncrementVirtualButtonPressed;
    private boolean mIngonreMoveEvents;
    private int mInitialScrollOffset;
    private final EditText mInputText;
    private long mLastDownEventTime;
    private float mLastDownEventY;
    private float mLastDownOrMoveEventY;
    private int mLastHandledDownDpadKeyCode;
    private int mLastHoveredChildVirtualViewId;
    private long mLongPressUpdateInterval;
    private final int mMaxHeight;
    private int mMaxValue;
    private int mMaxWidth;
    private int mMaximumFlingVelocity;
    private final int mMinHeight;
    private int mMinValue;
    private final int mMinWidth;
    private int mMinimumFlingVelocity;
    private OnScrollListener mOnScrollListener;
    private OnValueChangeListener mOnValueChangeListener;
    private final PressedStateHelper mPressedStateHelper;
    private int mPreviousScrollerY;
    private int mScrollState;
    private Drawable mSelectionDivider;
    private final int mSelectionDividerHeight;
    private final int mSelectionDividersDistance;
    private int mSelectorElementHeight;
    private final SparseArray<String> mSelectorIndexToStringCache;
    private final int[] mSelectorIndices;
    private int mSelectorTextGapHeight;
    private Paint mSelectorWheelPaint;
    private SetSelectionCommand mSetSelectionCommand;
    private boolean mShowSoftInputOnTap;
    private final int mSolidColor;
    private final int mTextSize;
    private int mTopSelectionDividerTop;
    private int mTouchSlop;
    private int mValue;
    private VelocityTracker mVelocityTracker;
    private Drawable mVirtualButtonPressedDrawable;
    private boolean mWrapSelectorWheel;

    class C34411 implements OnClickListener {
        C34411() {
        }

        public void onClick(View v) {
            NumberPicker.this.hideSoftInput();
            NumberPicker.this.mInputText.clearFocus();
            if (v.getId() == C3444R.id.np__increment) {
                NumberPicker.this.changeValueByOne(true);
            } else {
                NumberPicker.this.changeValueByOne(false);
            }
        }
    }

    class C34422 implements OnLongClickListener {
        C34422() {
        }

        public boolean onLongClick(View v) {
            NumberPicker.this.hideSoftInput();
            NumberPicker.this.mInputText.clearFocus();
            if (v.getId() == C3444R.id.np__increment) {
                NumberPicker.this.postChangeCurrentByOneFromLongPress(true, 0);
            } else {
                NumberPicker.this.postChangeCurrentByOneFromLongPress(false, 0);
            }
            return true;
        }
    }

    class C34433 implements OnFocusChangeListener {
        C34433() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                NumberPicker.this.mInputText.selectAll();
                return;
            }
            NumberPicker.this.mInputText.setSelection(0, 0);
            NumberPicker.this.validateInputTextView(v);
        }
    }

    class AccessibilityNodeProviderImpl extends AccessibilityNodeProvider {
        private static final int UNDEFINED = Integer.MIN_VALUE;
        private static final int VIRTUAL_VIEW_ID_DECREMENT = 3;
        private static final int VIRTUAL_VIEW_ID_INCREMENT = 1;
        private static final int VIRTUAL_VIEW_ID_INPUT = 2;
        private int mAccessibilityFocusedView = Integer.MIN_VALUE;
        private final int[] mTempArray = new int[2];
        private final Rect mTempRect = new Rect();

        AccessibilityNodeProviderImpl() {
        }

        public AccessibilityNodeInfo createAccessibilityNodeInfo(int virtualViewId) {
            switch (virtualViewId) {
                case -1:
                    return createAccessibilityNodeInfoForNumberPicker(NumberPicker.this.getScrollX(), NumberPicker.this.getScrollY(), NumberPicker.this.getScrollX() + (NumberPicker.this.getRight() - NumberPicker.this.getLeft()), NumberPicker.this.getScrollY() + (NumberPicker.this.getBottom() - NumberPicker.this.getTop()));
                case 1:
                    return createAccessibilityNodeInfoForVirtualButton(1, getVirtualIncrementButtonText(), NumberPicker.this.getScrollX(), NumberPicker.this.mBottomSelectionDividerBottom - NumberPicker.this.mSelectionDividerHeight, (NumberPicker.this.getRight() - NumberPicker.this.getLeft()) + NumberPicker.this.getScrollX(), (NumberPicker.this.getBottom() - NumberPicker.this.getTop()) + NumberPicker.this.getScrollY());
                case 2:
                    return createAccessibiltyNodeInfoForInputText();
                case 3:
                    return createAccessibilityNodeInfoForVirtualButton(3, getVirtualDecrementButtonText(), NumberPicker.this.getScrollX(), NumberPicker.this.getScrollY(), (NumberPicker.this.getRight() - NumberPicker.this.getLeft()) + NumberPicker.this.getScrollX(), NumberPicker.this.mSelectionDividerHeight + NumberPicker.this.mTopSelectionDividerTop);
                default:
                    return super.createAccessibilityNodeInfo(virtualViewId);
            }
        }

        public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String searched, int virtualViewId) {
            if (TextUtils.isEmpty(searched)) {
                return Collections.emptyList();
            }
            String searchedLowerCase = searched.toLowerCase();
            List<AccessibilityNodeInfo> result = new ArrayList();
            switch (virtualViewId) {
                case -1:
                    findAccessibilityNodeInfosByTextInChild(searchedLowerCase, 3, result);
                    findAccessibilityNodeInfosByTextInChild(searchedLowerCase, 2, result);
                    findAccessibilityNodeInfosByTextInChild(searchedLowerCase, 1, result);
                    return result;
                case 1:
                case 2:
                case 3:
                    findAccessibilityNodeInfosByTextInChild(searchedLowerCase, virtualViewId, result);
                    return result;
                default:
                    return super.findAccessibilityNodeInfosByText(searched, virtualViewId);
            }
        }

        public boolean performAction(int virtualViewId, int action, Bundle arguments) {
            boolean increment = false;
            switch (virtualViewId) {
                case -1:
                    switch (action) {
                        case 64:
                            if (this.mAccessibilityFocusedView == virtualViewId) {
                                return false;
                            }
                            this.mAccessibilityFocusedView = virtualViewId;
                            NumberPicker.this.performAccessibilityAction(64, null);
                            return true;
                        case 128:
                            if (this.mAccessibilityFocusedView != virtualViewId) {
                                return false;
                            }
                            this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                            NumberPicker.this.performAccessibilityAction(128, null);
                            return true;
                        case 4096:
                            if (!NumberPicker.this.isEnabled()) {
                                return false;
                            }
                            if (!NumberPicker.this.getWrapSelectorWheel() && NumberPicker.this.getValue() >= NumberPicker.this.getMaxValue()) {
                                return false;
                            }
                            NumberPicker.this.changeValueByOne(true);
                            return true;
                        case 8192:
                            if (!NumberPicker.this.isEnabled()) {
                                return false;
                            }
                            if (!NumberPicker.this.getWrapSelectorWheel() && NumberPicker.this.getValue() <= NumberPicker.this.getMinValue()) {
                                return false;
                            }
                            NumberPicker.this.changeValueByOne(false);
                            return true;
                        default:
                            break;
                    }
                case 1:
                    switch (action) {
                        case 16:
                            if (!NumberPicker.this.isEnabled()) {
                                return false;
                            }
                            NumberPicker.this.changeValueByOne(true);
                            sendAccessibilityEventForVirtualView(virtualViewId, 1);
                            return true;
                        case 64:
                            if (this.mAccessibilityFocusedView == virtualViewId) {
                                return false;
                            }
                            this.mAccessibilityFocusedView = virtualViewId;
                            sendAccessibilityEventForVirtualView(virtualViewId, 32768);
                            NumberPicker.this.invalidate(0, NumberPicker.this.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
                            return true;
                        case 128:
                            if (this.mAccessibilityFocusedView != virtualViewId) {
                                return false;
                            }
                            this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                            sendAccessibilityEventForVirtualView(virtualViewId, 65536);
                            NumberPicker.this.invalidate(0, NumberPicker.this.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
                            return true;
                        default:
                            return false;
                    }
                case 2:
                    switch (action) {
                        case 1:
                            if (!NumberPicker.this.isEnabled() || NumberPicker.this.getInputText().isFocused()) {
                                return false;
                            }
                            return NumberPicker.this.getInputText().requestFocus();
                        case 2:
                            if (!NumberPicker.this.isEnabled() || !NumberPicker.this.getInputText().isFocused()) {
                                return false;
                            }
                            NumberPicker.this.getInputText().clearFocus();
                            return true;
                        case 16:
                            if (!NumberPicker.this.isEnabled()) {
                                return false;
                            }
                            NumberPicker.this.showSoftInput();
                            return true;
                        case 64:
                            if (this.mAccessibilityFocusedView == virtualViewId) {
                                return false;
                            }
                            this.mAccessibilityFocusedView = virtualViewId;
                            sendAccessibilityEventForVirtualView(virtualViewId, 32768);
                            NumberPicker.this.getInputText().invalidate();
                            return true;
                        case 128:
                            if (this.mAccessibilityFocusedView != virtualViewId) {
                                return false;
                            }
                            this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                            sendAccessibilityEventForVirtualView(virtualViewId, 65536);
                            NumberPicker.this.getInputText().invalidate();
                            return true;
                        default:
                            return NumberPicker.this.getInputText().performAccessibilityAction(action, arguments);
                    }
                case 3:
                    switch (action) {
                        case 16:
                            if (!NumberPicker.this.isEnabled()) {
                                return false;
                            }
                            if (virtualViewId == 1) {
                                increment = true;
                            }
                            NumberPicker.this.changeValueByOne(increment);
                            sendAccessibilityEventForVirtualView(virtualViewId, 1);
                            return true;
                        case 64:
                            if (this.mAccessibilityFocusedView == virtualViewId) {
                                return false;
                            }
                            this.mAccessibilityFocusedView = virtualViewId;
                            sendAccessibilityEventForVirtualView(virtualViewId, 32768);
                            NumberPicker.this.invalidate(0, 0, NumberPicker.this.getRight(), NumberPicker.this.mTopSelectionDividerTop);
                            return true;
                        case 128:
                            if (this.mAccessibilityFocusedView != virtualViewId) {
                                return false;
                            }
                            this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                            sendAccessibilityEventForVirtualView(virtualViewId, 65536);
                            NumberPicker.this.invalidate(0, 0, NumberPicker.this.getRight(), NumberPicker.this.mTopSelectionDividerTop);
                            return true;
                        default:
                            return false;
                    }
            }
            return super.performAction(virtualViewId, action, arguments);
        }

        public void sendAccessibilityEventForVirtualView(int virtualViewId, int eventType) {
            switch (virtualViewId) {
                case 1:
                    if (hasVirtualIncrementButton()) {
                        sendAccessibilityEventForVirtualButton(virtualViewId, eventType, getVirtualIncrementButtonText());
                        return;
                    }
                    return;
                case 2:
                    sendAccessibilityEventForVirtualText(eventType);
                    return;
                case 3:
                    if (hasVirtualDecrementButton()) {
                        sendAccessibilityEventForVirtualButton(virtualViewId, eventType, getVirtualDecrementButtonText());
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        private void sendAccessibilityEventForVirtualText(int eventType) {
            if (((AccessibilityManager) NumberPicker.this.getContext().getSystemService("accessibility")).isEnabled()) {
                AccessibilityEvent event = AccessibilityEvent.obtain(eventType);
                NumberPicker.this.getInputText().onInitializeAccessibilityEvent(event);
                NumberPicker.this.getInputText().onPopulateAccessibilityEvent(event);
                event.setSource(NumberPicker.this, 2);
                NumberPicker.this.requestSendAccessibilityEvent(NumberPicker.this, event);
            }
        }

        private void sendAccessibilityEventForVirtualButton(int virtualViewId, int eventType, String text) {
            if (((AccessibilityManager) NumberPicker.this.getContext().getSystemService("accessibility")).isEnabled()) {
                AccessibilityEvent event = AccessibilityEvent.obtain(eventType);
                event.setClassName(Button.class.getName());
                event.setPackageName(NumberPicker.this.getContext().getPackageName());
                event.getText().add(text);
                event.setEnabled(NumberPicker.this.isEnabled());
                event.setSource(NumberPicker.this, virtualViewId);
                NumberPicker.this.requestSendAccessibilityEvent(NumberPicker.this, event);
            }
        }

        private void findAccessibilityNodeInfosByTextInChild(String searchedLowerCase, int virtualViewId, List<AccessibilityNodeInfo> outResult) {
            String text;
            switch (virtualViewId) {
                case 1:
                    text = getVirtualIncrementButtonText();
                    if (!TextUtils.isEmpty(text) && text.toString().toLowerCase().contains(searchedLowerCase)) {
                        outResult.add(createAccessibilityNodeInfo(1));
                        return;
                    }
                    return;
                case 2:
                    CharSequence text2 = NumberPicker.this.getInputText().getText();
                    if (TextUtils.isEmpty(text2) || !text2.toString().toLowerCase().contains(searchedLowerCase)) {
                        CharSequence contentDesc = NumberPicker.this.getInputText().getText();
                        if (!TextUtils.isEmpty(contentDesc) && contentDesc.toString().toLowerCase().contains(searchedLowerCase)) {
                            outResult.add(createAccessibilityNodeInfo(2));
                            return;
                        }
                        return;
                    }
                    outResult.add(createAccessibilityNodeInfo(2));
                    return;
                case 3:
                    text = getVirtualDecrementButtonText();
                    if (!TextUtils.isEmpty(text) && text.toString().toLowerCase().contains(searchedLowerCase)) {
                        outResult.add(createAccessibilityNodeInfo(3));
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        private AccessibilityNodeInfo createAccessibiltyNodeInfoForInputText() {
            AccessibilityNodeInfo info = NumberPicker.this.getInputText().createAccessibilityNodeInfo();
            info.setSource(NumberPicker.this, 2);
            if (this.mAccessibilityFocusedView != 2) {
                info.addAction(64);
            }
            if (this.mAccessibilityFocusedView == 2) {
                info.addAction(128);
            }
            return info;
        }

        private AccessibilityNodeInfo createAccessibilityNodeInfoForVirtualButton(int virtualViewId, String text, int left, int top, int right, int bottom) {
            AccessibilityNodeInfo info = AccessibilityNodeInfo.obtain();
            info.setClassName(Button.class.getName());
            info.setPackageName(NumberPicker.this.getContext().getPackageName());
            info.setSource(NumberPicker.this, virtualViewId);
            info.setParent(NumberPicker.this);
            info.setText(text);
            info.setClickable(true);
            info.setLongClickable(true);
            info.setEnabled(NumberPicker.this.isEnabled());
            Rect boundsInParent = this.mTempRect;
            boundsInParent.set(left, top, right, bottom);
            info.setBoundsInParent(boundsInParent);
            Rect boundsInScreen = boundsInParent;
            int[] locationOnScreen = this.mTempArray;
            NumberPicker.this.getLocationOnScreen(locationOnScreen);
            boundsInScreen.offset(locationOnScreen[0], locationOnScreen[1]);
            info.setBoundsInScreen(boundsInScreen);
            if (this.mAccessibilityFocusedView != virtualViewId) {
                info.addAction(64);
            }
            if (this.mAccessibilityFocusedView == virtualViewId) {
                info.addAction(128);
            }
            if (NumberPicker.this.isEnabled()) {
                info.addAction(16);
            }
            return info;
        }

        private AccessibilityNodeInfo createAccessibilityNodeInfoForNumberPicker(int left, int top, int right, int bottom) {
            AccessibilityNodeInfo info = AccessibilityNodeInfo.obtain();
            info.setClassName(NumberPicker.class.getName());
            info.setPackageName(NumberPicker.this.getContext().getPackageName());
            info.setSource(NumberPicker.this);
            if (hasVirtualDecrementButton()) {
                info.addChild(NumberPicker.this, 3);
            }
            info.addChild(NumberPicker.this, 2);
            if (hasVirtualIncrementButton()) {
                info.addChild(NumberPicker.this, 1);
            }
            info.setParent((View) NumberPicker.this.getParentForAccessibility());
            info.setEnabled(NumberPicker.this.isEnabled());
            info.setScrollable(true);
            if (this.mAccessibilityFocusedView != -1) {
                info.addAction(64);
            }
            if (this.mAccessibilityFocusedView == -1) {
                info.addAction(128);
            }
            if (NumberPicker.this.isEnabled()) {
                if (NumberPicker.this.getWrapSelectorWheel() || NumberPicker.this.getValue() < NumberPicker.this.getMaxValue()) {
                    info.addAction(4096);
                }
                if (NumberPicker.this.getWrapSelectorWheel() || NumberPicker.this.getValue() > NumberPicker.this.getMinValue()) {
                    info.addAction(8192);
                }
            }
            return info;
        }

        private boolean hasVirtualDecrementButton() {
            return NumberPicker.this.getWrapSelectorWheel() || NumberPicker.this.getValue() > NumberPicker.this.getMinValue();
        }

        private boolean hasVirtualIncrementButton() {
            return NumberPicker.this.getWrapSelectorWheel() || NumberPicker.this.getValue() < NumberPicker.this.getMaxValue();
        }

        private String getVirtualDecrementButtonText() {
            int value = NumberPicker.this.mValue - 1;
            if (NumberPicker.this.mWrapSelectorWheel) {
                value = NumberPicker.this.getWrappedSelectorIndex(value);
            }
            if (value < NumberPicker.this.mMinValue) {
                return null;
            }
            if (NumberPicker.this.mDisplayedValues == null) {
                return NumberPicker.this.formatNumber(value);
            }
            return NumberPicker.this.mDisplayedValues[value - NumberPicker.this.mMinValue];
        }

        private String getVirtualIncrementButtonText() {
            int value = NumberPicker.this.mValue + 1;
            if (NumberPicker.this.mWrapSelectorWheel) {
                value = NumberPicker.this.getWrappedSelectorIndex(value);
            }
            if (value > NumberPicker.this.mMaxValue) {
                return null;
            }
            if (NumberPicker.this.mDisplayedValues == null) {
                return NumberPicker.this.formatNumber(value);
            }
            return NumberPicker.this.mDisplayedValues[value - NumberPicker.this.mMinValue];
        }
    }

    class BeginSoftInputOnLongPressCommand implements Runnable {
        BeginSoftInputOnLongPressCommand() {
        }

        public void run() {
            NumberPicker.this.showSoftInput();
            NumberPicker.this.mIngonreMoveEvents = true;
        }
    }

    class ChangeCurrentByOneFromLongPressCommand implements Runnable {
        private boolean mIncrement;

        ChangeCurrentByOneFromLongPressCommand() {
        }

        private void setStep(boolean increment) {
            this.mIncrement = increment;
        }

        public void run() {
            NumberPicker.this.changeValueByOne(this.mIncrement);
            NumberPicker.this.postDelayed(this, NumberPicker.this.mLongPressUpdateInterval);
        }
    }

    public static class CustomEditText extends EditText {
        public CustomEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public void onEditorAction(int actionCode) {
            super.onEditorAction(actionCode);
            if (actionCode == 6) {
                clearFocus();
            }
        }

        public boolean onKeyPreIme(int keyCode, KeyEvent event) {
            if (event.getKeyCode() == 4) {
                clearFocus();
            }
            return super.onKeyPreIme(keyCode, event);
        }
    }

    public interface Formatter {
        String format(int i);
    }

    class InputTextFilter extends NumberKeyListener {
        InputTextFilter() {
        }

        public int getInputType() {
            return 1;
        }

        protected char[] getAcceptedChars() {
            return NumberPicker.DIGIT_CHARACTERS;
        }

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            int i = 0;
            CharSequence filtered;
            String result;
            if (NumberPicker.this.mDisplayedValues == null) {
                filtered = super.filter(source, start, end, dest, dstart, dend);
                if (filtered == null) {
                    filtered = source.subSequence(start, end);
                }
                result = String.valueOf(dest.subSequence(0, dstart)) + filtered + dest.subSequence(dend, dest.length());
                if ("".equals(result)) {
                    return result;
                }
                return NumberPicker.this.getSelectedPos(result) > NumberPicker.this.mMaxValue ? "" : filtered;
            } else {
                filtered = String.valueOf(source.subSequence(start, end));
                if (TextUtils.isEmpty(filtered)) {
                    return "";
                }
                result = String.valueOf(dest.subSequence(0, dstart)) + filtered + dest.subSequence(dend, dest.length());
                String str = String.valueOf(result).toLowerCase();
                String[] access$1000 = NumberPicker.this.mDisplayedValues;
                int length = access$1000.length;
                while (i < length) {
                    String val = access$1000[i];
                    if (val.toLowerCase().startsWith(str)) {
                        NumberPicker.this.postSetSelectionCommand(result.length(), val.length());
                        return val.subSequence(dstart, val.length());
                    }
                    i++;
                }
                return "";
            }
        }
    }

    public interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        void onScrollStateChange(NumberPicker numberPicker, int i);
    }

    public interface OnValueChangeListener {
        void onValueChange(NumberPicker numberPicker, int i, int i2);
    }

    class PressedStateHelper implements Runnable {
        public static final int BUTTON_DECREMENT = 2;
        public static final int BUTTON_INCREMENT = 1;
        private final int MODE_PRESS = 1;
        private final int MODE_TAPPED = 2;
        private int mManagedButton;
        private int mMode;

        PressedStateHelper() {
        }

        public void cancel() {
            this.mMode = 0;
            this.mManagedButton = 0;
            NumberPicker.this.removeCallbacks(this);
            if (NumberPicker.this.mIncrementVirtualButtonPressed) {
                NumberPicker.this.mIncrementVirtualButtonPressed = false;
                NumberPicker.this.invalidate(0, NumberPicker.this.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
            }
            NumberPicker.this.mDecrementVirtualButtonPressed = false;
            if (NumberPicker.this.mDecrementVirtualButtonPressed) {
                NumberPicker.this.invalidate(0, 0, NumberPicker.this.getRight(), NumberPicker.this.mTopSelectionDividerTop);
            }
        }

        public void buttonPressDelayed(int button) {
            cancel();
            this.mMode = 1;
            this.mManagedButton = button;
            NumberPicker.this.postDelayed(this, (long) ViewConfiguration.getTapTimeout());
        }

        public void buttonTapped(int button) {
            cancel();
            this.mMode = 2;
            this.mManagedButton = button;
            NumberPicker.this.post(this);
        }

        public void run() {
            switch (this.mMode) {
                case 1:
                    switch (this.mManagedButton) {
                        case 1:
                            NumberPicker.this.mIncrementVirtualButtonPressed = true;
                            NumberPicker.this.invalidate(0, NumberPicker.this.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
                            return;
                        case 2:
                            NumberPicker.this.mDecrementVirtualButtonPressed = true;
                            NumberPicker.this.invalidate(0, 0, NumberPicker.this.getRight(), NumberPicker.this.mTopSelectionDividerTop);
                            return;
                        default:
                            return;
                    }
                case 2:
                    switch (this.mManagedButton) {
                        case 1:
                            if (!NumberPicker.this.mIncrementVirtualButtonPressed) {
                                NumberPicker.this.postDelayed(this, (long) ViewConfiguration.getPressedStateDuration());
                            }
                            NumberPicker.this.mIncrementVirtualButtonPressed = NumberPicker.this.mIncrementVirtualButtonPressed ^ 1;
                            NumberPicker.this.invalidate(0, NumberPicker.this.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
                            return;
                        case 2:
                            if (!NumberPicker.this.mDecrementVirtualButtonPressed) {
                                NumberPicker.this.postDelayed(this, (long) ViewConfiguration.getPressedStateDuration());
                            }
                            NumberPicker.this.mDecrementVirtualButtonPressed = NumberPicker.this.mDecrementVirtualButtonPressed ^ 1;
                            NumberPicker.this.invalidate(0, 0, NumberPicker.this.getRight(), NumberPicker.this.mTopSelectionDividerTop);
                            return;
                        default:
                            return;
                    }
                default:
                    return;
            }
        }
    }

    class SetSelectionCommand implements Runnable {
        private int mSelectionEnd;
        private int mSelectionStart;

        SetSelectionCommand() {
        }

        public void run() {
            NumberPicker.this.getInputText().setSelection(this.mSelectionStart, this.mSelectionEnd);
        }
    }

    class SupportAccessibilityNodeProvider {
        AccessibilityNodeProviderImpl mProvider;

        private SupportAccessibilityNodeProvider() {
            if (VERSION.SDK_INT >= 16) {
                this.mProvider = new AccessibilityNodeProviderImpl();
            }
        }

        public boolean performAction(int virtualViewId, int action, Bundle arguments) {
            if (this.mProvider == null || VERSION.SDK_INT < 16) {
                return false;
            }
            return this.mProvider.performAction(virtualViewId, action, arguments);
        }

        public void sendAccessibilityEventForVirtualView(int virtualViewId, int eventType) {
            if (this.mProvider != null) {
                this.mProvider.sendAccessibilityEventForVirtualView(virtualViewId, eventType);
            }
        }
    }

    private static class TwoDigitFormatter implements Formatter {
        final Object[] mArgs = new Object[1];
        final StringBuilder mBuilder = new StringBuilder();
        java.util.Formatter mFmt;
        char mZeroDigit;

        TwoDigitFormatter() {
            init(Locale.getDefault());
        }

        private void init(Locale locale) {
            this.mFmt = createFormatter(locale);
            this.mZeroDigit = getZeroDigit(locale);
        }

        public String format(int value) {
            Locale currentLocale = Locale.getDefault();
            if (this.mZeroDigit != getZeroDigit(currentLocale)) {
                init(currentLocale);
            }
            this.mArgs[0] = Integer.valueOf(value);
            this.mBuilder.delete(0, this.mBuilder.length());
            this.mFmt.format("%02d", this.mArgs);
            return this.mFmt.toString();
        }

        private static char getZeroDigit(Locale locale) {
            return new DecimalFormatSymbols(locale).getZeroDigit();
        }

        private java.util.Formatter createFormatter(Locale locale) {
            return new java.util.Formatter(this.mBuilder, locale);
        }
    }

    public static final Formatter getTwoDigitFormatter() {
        return sTwoDigitFormatter;
    }

    public NumberPicker(Context context) {
        this(context, null);
    }

    public NumberPicker(Context context, AttributeSet attrs) {
        this(context, attrs, C3444R.attr.numberPickerStyle);
    }

    public NumberPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        this.mLongPressUpdateInterval = 300;
        this.mSelectorIndexToStringCache = new SparseArray();
        this.mSelectorIndices = new int[3];
        this.mInitialScrollOffset = Integer.MIN_VALUE;
        this.mScrollState = 0;
        this.mLastHandledDownDpadKeyCode = -1;
        TypedArray attributesArray = context.obtainStyledAttributes(attrs, C3444R.styleable.NumberPicker, defStyle, 0);
        int layoutResId = attributesArray.getResourceId(C3444R.styleable.NumberPicker_internalLayout, C3444R.layout.number_picker_with_selector_wheel);
        this.mHasSelectorWheel = layoutResId != 0;
        this.mSolidColor = attributesArray.getColor(C3444R.styleable.NumberPicker_solidColor, 0);
        setSelectionDivider(attributesArray.getDrawable(C3444R.styleable.NumberPicker_selectionDivider));
        this.mSelectionDividerHeight = attributesArray.getDimensionPixelSize(C3444R.styleable.NumberPicker_selectionDividerHeight, (int) TypedValue.applyDimension(1, LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, getResources().getDisplayMetrics()));
        this.mSelectionDividersDistance = attributesArray.getDimensionPixelSize(C3444R.styleable.NumberPicker_selectionDividersDistance, (int) TypedValue.applyDimension(1, 48.0f, getResources().getDisplayMetrics()));
        this.mMinHeight = attributesArray.getDimensionPixelSize(C3444R.styleable.NumberPicker_internalMinHeight, -1);
        this.mMaxHeight = attributesArray.getDimensionPixelSize(C3444R.styleable.NumberPicker_internalMaxHeight, -1);
        if (this.mMinHeight == -1 || this.mMaxHeight == -1 || this.mMinHeight <= this.mMaxHeight) {
            this.mMinWidth = attributesArray.getDimensionPixelSize(C3444R.styleable.NumberPicker_internalMinWidth, -1);
            this.mMaxWidth = attributesArray.getDimensionPixelSize(C3444R.styleable.NumberPicker_internalMaxWidth, -1);
            if (this.mMinWidth == -1 || this.mMaxWidth == -1 || this.mMinWidth <= this.mMaxWidth) {
                this.mComputeMaxWidth = this.mMaxWidth == -1;
                this.mVirtualButtonPressedDrawable = attributesArray.getDrawable(C3444R.styleable.NumberPicker_virtualButtonPressedDrawable);
                attributesArray.recycle();
                this.mPressedStateHelper = new PressedStateHelper();
                setWillNotDraw(!this.mHasSelectorWheel);
                ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(layoutResId, this, true);
                OnClickListener onClickListener = new C34411();
                OnLongClickListener onLongClickListener = new C34422();
                if (this.mHasSelectorWheel) {
                    this.mIncrementButton = null;
                } else {
                    this.mIncrementButton = (ImageButton) findViewById(C3444R.id.np__increment);
                    this.mIncrementButton.setOnClickListener(onClickListener);
                    this.mIncrementButton.setOnLongClickListener(onLongClickListener);
                }
                if (this.mHasSelectorWheel) {
                    this.mDecrementButton = null;
                } else {
                    this.mDecrementButton = (ImageButton) findViewById(C3444R.id.np__decrement);
                    this.mDecrementButton.setOnClickListener(onClickListener);
                    this.mDecrementButton.setOnLongClickListener(onLongClickListener);
                }
                this.mInputText = (EditText) findViewById(C3444R.id.np__numberpicker_input);
                this.mInputText.setOnFocusChangeListener(new C34433());
                this.mInputText.setFilters(new InputFilter[]{new InputTextFilter()});
                this.mInputText.setRawInputType(2);
                this.mInputText.setImeOptions(6);
                ViewConfiguration configuration = ViewConfiguration.get(context);
                this.mTouchSlop = configuration.getScaledTouchSlop();
                this.mMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();
                this.mMaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity() / 8;
                this.mTextSize = (int) this.mInputText.getTextSize();
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setTextAlign(Align.CENTER);
                paint.setTextSize((float) this.mTextSize);
                paint.setTypeface(this.mInputText.getTypeface());
                paint.setColor(-16777216);
                this.mSelectorWheelPaint = paint;
                this.mFlingScroller = new Scroller(getContext(), null, true);
                this.mAdjustScroller = new Scroller(getContext(), new DecelerateInterpolator(CanvasFont.OUTLINE_STROKE_WIDTH));
                updateInputTextView();
                if (VERSION.SDK_INT >= 16 && getImportantForAccessibility() == 0) {
                    setImportantForAccessibility(1);
                    return;
                }
                return;
            }
            throw new IllegalArgumentException("minWidth > maxWidth");
        }
        throw new IllegalArgumentException("minHeight > maxHeight");
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (this.mHasSelectorWheel) {
            int msrdWdth = getMeasuredWidth();
            int msrdHght = getMeasuredHeight();
            int inptTxtMsrdWdth = this.mInputText.getMeasuredWidth();
            int inptTxtMsrdHght = this.mInputText.getMeasuredHeight();
            int inptTxtLeft = (msrdWdth - inptTxtMsrdWdth) / 2;
            int inptTxtTop = (msrdHght - inptTxtMsrdHght) / 2;
            this.mInputText.layout(inptTxtLeft, inptTxtTop, inptTxtLeft + inptTxtMsrdWdth, inptTxtTop + inptTxtMsrdHght);
            if (changed) {
                initializeSelectorWheel();
                initializeFadingEdges();
                this.mTopSelectionDividerTop = ((getHeight() - this.mSelectionDividersDistance) / 2) - this.mSelectionDividerHeight;
                this.mBottomSelectionDividerBottom = (this.mTopSelectionDividerTop + (this.mSelectionDividerHeight * 2)) + this.mSelectionDividersDistance;
                return;
            }
            return;
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mHasSelectorWheel) {
            super.onMeasure(makeMeasureSpec(widthMeasureSpec, this.mMaxWidth), makeMeasureSpec(heightMeasureSpec, this.mMaxHeight));
            setMeasuredDimension(resolveSizeAndStateRespectingMinSize(this.mMinWidth, getMeasuredWidth(), widthMeasureSpec), resolveSizeAndStateRespectingMinSize(this.mMinHeight, getMeasuredHeight(), heightMeasureSpec));
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private boolean moveToFinalScrollerPosition(Scroller scroller) {
        scroller.forceFinished(true);
        int amountToScroll = scroller.getFinalY() - scroller.getCurrY();
        int overshootAdjustment = this.mInitialScrollOffset - ((this.mCurrentScrollOffset + amountToScroll) % this.mSelectorElementHeight);
        if (overshootAdjustment == 0) {
            return false;
        }
        if (Math.abs(overshootAdjustment) > this.mSelectorElementHeight / 2) {
            if (overshootAdjustment > 0) {
                overshootAdjustment -= this.mSelectorElementHeight;
            } else {
                overshootAdjustment += this.mSelectorElementHeight;
            }
        }
        scrollBy(0, amountToScroll + overshootAdjustment);
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!this.mHasSelectorWheel || !isEnabled()) {
            return false;
        }
        switch (event.getAction() & 255) {
            case 0:
                removeAllCallbacks();
                this.mInputText.setVisibility(4);
                float y = event.getY();
                this.mLastDownEventY = y;
                this.mLastDownOrMoveEventY = y;
                this.mLastDownEventTime = event.getEventTime();
                this.mIngonreMoveEvents = false;
                this.mShowSoftInputOnTap = false;
                if (this.mLastDownEventY < ((float) this.mTopSelectionDividerTop)) {
                    if (this.mScrollState == 0) {
                        this.mPressedStateHelper.buttonPressDelayed(2);
                    }
                } else if (this.mLastDownEventY > ((float) this.mBottomSelectionDividerBottom) && this.mScrollState == 0) {
                    this.mPressedStateHelper.buttonPressDelayed(1);
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                if (!this.mFlingScroller.isFinished()) {
                    this.mFlingScroller.forceFinished(true);
                    this.mAdjustScroller.forceFinished(true);
                    onScrollStateChange(0);
                    return true;
                } else if (!this.mAdjustScroller.isFinished()) {
                    this.mFlingScroller.forceFinished(true);
                    this.mAdjustScroller.forceFinished(true);
                    return true;
                } else if (this.mLastDownEventY < ((float) this.mTopSelectionDividerTop)) {
                    hideSoftInput();
                    postChangeCurrentByOneFromLongPress(false, (long) ViewConfiguration.getLongPressTimeout());
                    return true;
                } else if (this.mLastDownEventY > ((float) this.mBottomSelectionDividerBottom)) {
                    hideSoftInput();
                    postChangeCurrentByOneFromLongPress(true, (long) ViewConfiguration.getLongPressTimeout());
                    return true;
                } else {
                    this.mShowSoftInputOnTap = true;
                    postBeginSoftInputOnLongPressCommand();
                    return true;
                }
            default:
                return false;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled() || !this.mHasSelectorWheel) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(event);
        switch (event.getAction() & 255) {
            case 1:
                removeBeginSoftInputCommand();
                removeChangeCurrentByOneFromLongPress();
                this.mPressedStateHelper.cancel();
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumFlingVelocity);
                int initialVelocity = (int) velocityTracker.getYVelocity();
                if (Math.abs(initialVelocity) > this.mMinimumFlingVelocity) {
                    fling(initialVelocity);
                    onScrollStateChange(2);
                } else {
                    int eventY = (int) event.getY();
                    long deltaTime = event.getEventTime() - this.mLastDownEventTime;
                    long tapTimeout = (long) ViewConfiguration.getTapTimeout();
                    if (((int) Math.abs(((float) eventY) - this.mLastDownEventY)) > this.mTouchSlop) {
                        ensureScrollWheelAdjusted();
                    } else if (this.mShowSoftInputOnTap) {
                        this.mShowSoftInputOnTap = false;
                        showSoftInput();
                    } else {
                        int selectorIndexOffset = (eventY / this.mSelectorElementHeight) - 1;
                        if (selectorIndexOffset > 0) {
                            changeValueByOne(true);
                            this.mPressedStateHelper.buttonTapped(1);
                        } else if (selectorIndexOffset < 0) {
                            changeValueByOne(false);
                            this.mPressedStateHelper.buttonTapped(2);
                        }
                    }
                    onScrollStateChange(0);
                }
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
                break;
            case 2:
                if (!this.mIngonreMoveEvents) {
                    float currentMoveY = event.getY();
                    if (this.mScrollState == 1) {
                        scrollBy(0, (int) (currentMoveY - this.mLastDownOrMoveEventY));
                        invalidate();
                    } else if (((int) Math.abs(currentMoveY - this.mLastDownEventY)) > this.mTouchSlop) {
                        removeAllCallbacks();
                        onScrollStateChange(1);
                    }
                    this.mLastDownOrMoveEventY = currentMoveY;
                    break;
                }
                break;
        }
        return true;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction() & 255) {
            case 1:
            case 3:
                removeAllCallbacks();
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean dispatchKeyEvent(android.view.KeyEvent r6) {
        /*
        r5 = this;
        r4 = 20;
        r2 = 1;
        r0 = r6.getKeyCode();
        switch(r0) {
            case 19: goto L_0x0013;
            case 20: goto L_0x0013;
            case 23: goto L_0x000f;
            case 66: goto L_0x000f;
            default: goto L_0x000a;
        };
    L_0x000a:
        r2 = super.dispatchKeyEvent(r6);
    L_0x000e:
        return r2;
    L_0x000f:
        r5.removeAllCallbacks();
        goto L_0x000a;
    L_0x0013:
        r1 = r5.mHasSelectorWheel;
        if (r1 == 0) goto L_0x000a;
    L_0x0017:
        r1 = r6.getAction();
        switch(r1) {
            case 0: goto L_0x001f;
            case 1: goto L_0x0053;
            default: goto L_0x001e;
        };
    L_0x001e:
        goto L_0x000a;
    L_0x001f:
        r1 = r5.mWrapSelectorWheel;
        if (r1 != 0) goto L_0x0025;
    L_0x0023:
        if (r0 != r4) goto L_0x0046;
    L_0x0025:
        r1 = r5.getValue();
        r3 = r5.getMaxValue();
        if (r1 >= r3) goto L_0x000a;
    L_0x002f:
        r5.requestFocus();
        r5.mLastHandledDownDpadKeyCode = r0;
        r5.removeAllCallbacks();
        r1 = r5.mFlingScroller;
        r1 = r1.isFinished();
        if (r1 == 0) goto L_0x000e;
    L_0x003f:
        if (r0 != r4) goto L_0x0051;
    L_0x0041:
        r1 = r2;
    L_0x0042:
        r5.changeValueByOne(r1);
        goto L_0x000e;
    L_0x0046:
        r1 = r5.getValue();
        r3 = r5.getMinValue();
        if (r1 <= r3) goto L_0x000a;
    L_0x0050:
        goto L_0x002f;
    L_0x0051:
        r1 = 0;
        goto L_0x0042;
    L_0x0053:
        r1 = r5.mLastHandledDownDpadKeyCode;
        if (r1 != r0) goto L_0x000a;
    L_0x0057:
        r1 = -1;
        r5.mLastHandledDownDpadKeyCode = r1;
        goto L_0x000e;
        */
        throw new UnsupportedOperationException("Method not decompiled: net.simonvt.numberpicker.NumberPicker.dispatchKeyEvent(android.view.KeyEvent):boolean");
    }

    public boolean dispatchTrackballEvent(MotionEvent event) {
        switch (event.getAction() & 255) {
            case 1:
            case 3:
                removeAllCallbacks();
                break;
        }
        return super.dispatchTrackballEvent(event);
    }

    protected boolean dispatchHoverEvent(MotionEvent event) {
        if (!this.mHasSelectorWheel) {
            return super.dispatchHoverEvent(event);
        }
        if (((AccessibilityManager) getContext().getSystemService("accessibility")).isEnabled()) {
            int hoveredVirtualViewId;
            int eventY = (int) event.getY();
            if (eventY < this.mTopSelectionDividerTop) {
                hoveredVirtualViewId = 3;
            } else if (eventY > this.mBottomSelectionDividerBottom) {
                hoveredVirtualViewId = 1;
            } else {
                hoveredVirtualViewId = 2;
            }
            int action = event.getAction() & 255;
            SupportAccessibilityNodeProvider provider = getSupportAccessibilityNodeProvider();
            switch (action) {
                case 7:
                    if (!(this.mLastHoveredChildVirtualViewId == hoveredVirtualViewId || this.mLastHoveredChildVirtualViewId == -1)) {
                        provider.sendAccessibilityEventForVirtualView(this.mLastHoveredChildVirtualViewId, 256);
                        provider.sendAccessibilityEventForVirtualView(hoveredVirtualViewId, 128);
                        this.mLastHoveredChildVirtualViewId = hoveredVirtualViewId;
                        provider.performAction(hoveredVirtualViewId, 64, null);
                        break;
                    }
                case 9:
                    provider.sendAccessibilityEventForVirtualView(hoveredVirtualViewId, 128);
                    this.mLastHoveredChildVirtualViewId = hoveredVirtualViewId;
                    provider.performAction(hoveredVirtualViewId, 64, null);
                    break;
                case 10:
                    provider.sendAccessibilityEventForVirtualView(hoveredVirtualViewId, 256);
                    this.mLastHoveredChildVirtualViewId = -1;
                    break;
            }
        }
        return false;
    }

    public void computeScroll() {
        Scroller scroller = this.mFlingScroller;
        if (scroller.isFinished()) {
            scroller = this.mAdjustScroller;
            if (scroller.isFinished()) {
                return;
            }
        }
        scroller.computeScrollOffset();
        int currentScrollerY = scroller.getCurrY();
        if (this.mPreviousScrollerY == 0) {
            this.mPreviousScrollerY = scroller.getStartY();
        }
        scrollBy(0, currentScrollerY - this.mPreviousScrollerY);
        this.mPreviousScrollerY = currentScrollerY;
        if (scroller.isFinished()) {
            onScrollerFinished(scroller);
        } else {
            invalidate();
        }
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!this.mHasSelectorWheel) {
            this.mIncrementButton.setEnabled(enabled);
        }
        if (!this.mHasSelectorWheel) {
            this.mDecrementButton.setEnabled(enabled);
        }
        this.mInputText.setEnabled(enabled);
    }

    public void scrollBy(int x, int y) {
        int[] selectorIndices = this.mSelectorIndices;
        if (!this.mWrapSelectorWheel && y > 0 && selectorIndices[1] <= this.mMinValue) {
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
        } else if (this.mWrapSelectorWheel || y >= 0 || selectorIndices[1] < this.mMaxValue) {
            this.mCurrentScrollOffset += y;
            while (this.mCurrentScrollOffset - this.mInitialScrollOffset > this.mSelectorTextGapHeight) {
                this.mCurrentScrollOffset -= this.mSelectorElementHeight;
                decrementSelectorIndices(selectorIndices);
                setValueInternal(selectorIndices[1], true);
                if (!this.mWrapSelectorWheel && selectorIndices[1] <= this.mMinValue) {
                    this.mCurrentScrollOffset = this.mInitialScrollOffset;
                }
            }
            while (this.mCurrentScrollOffset - this.mInitialScrollOffset < (-this.mSelectorTextGapHeight)) {
                this.mCurrentScrollOffset += this.mSelectorElementHeight;
                incrementSelectorIndices(selectorIndices);
                setValueInternal(selectorIndices[1], true);
                if (!this.mWrapSelectorWheel && selectorIndices[1] >= this.mMaxValue) {
                    this.mCurrentScrollOffset = this.mInitialScrollOffset;
                }
            }
        } else {
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
        }
    }

    public int getSolidColor() {
        return this.mSolidColor;
    }

    public void setOnValueChangedListener(OnValueChangeListener onValueChangedListener) {
        this.mOnValueChangeListener = onValueChangedListener;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public void setFormatter(Formatter formatter) {
        if (formatter != this.mFormatter) {
            this.mFormatter = formatter;
            initializeSelectorWheelIndices();
            updateInputTextView();
        }
    }

    public void setValue(int value) {
        setValueInternal(value, false);
    }

    private void showSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
        if (inputMethodManager != null) {
            if (this.mHasSelectorWheel) {
                this.mInputText.setVisibility(0);
            }
            this.mInputText.requestFocus();
            inputMethodManager.showSoftInput(this.mInputText, 0);
        }
    }

    private void hideSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
        if (inputMethodManager != null && inputMethodManager.isActive(this.mInputText)) {
            inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            if (this.mHasSelectorWheel) {
                this.mInputText.setVisibility(4);
            }
        }
    }

    private void tryComputeMaxWidth() {
        if (this.mComputeMaxWidth) {
            int maxTextWidth = 0;
            int i;
            if (this.mDisplayedValues == null) {
                float maxDigitWidth = 0.0f;
                for (i = 0; i <= 9; i++) {
                    float digitWidth = this.mSelectorWheelPaint.measureText(formatNumberWithLocale(i));
                    if (digitWidth > maxDigitWidth) {
                        maxDigitWidth = digitWidth;
                    }
                }
                int numberOfDigits = 0;
                for (int current = this.mMaxValue; current > 0; current /= 10) {
                    numberOfDigits++;
                }
                maxTextWidth = (int) (((float) numberOfDigits) * maxDigitWidth);
            } else {
                for (String measureText : this.mDisplayedValues) {
                    float textWidth = this.mSelectorWheelPaint.measureText(measureText);
                    if (textWidth > ((float) maxTextWidth)) {
                        maxTextWidth = (int) textWidth;
                    }
                }
            }
            maxTextWidth += this.mInputText.getPaddingLeft() + this.mInputText.getPaddingRight();
            if (this.mMaxWidth != maxTextWidth) {
                if (maxTextWidth > this.mMinWidth) {
                    this.mMaxWidth = maxTextWidth;
                } else {
                    this.mMaxWidth = this.mMinWidth;
                }
                invalidate();
            }
        }
    }

    public boolean getWrapSelectorWheel() {
        return this.mWrapSelectorWheel;
    }

    public void setWrapSelectorWheel(boolean wrapSelectorWheel) {
        boolean wrappingAllowed = this.mMaxValue - this.mMinValue >= this.mSelectorIndices.length;
        if ((!wrapSelectorWheel || wrappingAllowed) && wrapSelectorWheel != this.mWrapSelectorWheel) {
            this.mWrapSelectorWheel = wrapSelectorWheel;
        }
    }

    public void setOnLongPressUpdateInterval(long intervalMillis) {
        this.mLongPressUpdateInterval = intervalMillis;
    }

    public int getValue() {
        return this.mValue;
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public void setMinValue(int minValue) {
        if (this.mMinValue != minValue) {
            if (minValue < 0) {
                throw new IllegalArgumentException("minValue must be >= 0");
            }
            this.mMinValue = minValue;
            if (this.mMinValue > this.mValue) {
                this.mValue = this.mMinValue;
            }
            setWrapSelectorWheel(this.mMaxValue - this.mMinValue > this.mSelectorIndices.length);
            initializeSelectorWheelIndices();
            updateInputTextView();
            tryComputeMaxWidth();
            invalidate();
        }
    }

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public void setMaxValue(int maxValue) {
        if (this.mMaxValue != maxValue) {
            if (maxValue < 0) {
                throw new IllegalArgumentException("maxValue must be >= 0");
            }
            this.mMaxValue = maxValue;
            if (this.mMaxValue < this.mValue) {
                this.mValue = this.mMaxValue;
            }
            setWrapSelectorWheel(this.mMaxValue - this.mMinValue > this.mSelectorIndices.length);
            initializeSelectorWheelIndices();
            updateInputTextView();
            tryComputeMaxWidth();
            invalidate();
        }
    }

    public String[] getDisplayedValues() {
        return this.mDisplayedValues;
    }

    public void setDisplayedValues(String[] displayedValues) {
        if (this.mDisplayedValues != displayedValues) {
            this.mDisplayedValues = displayedValues;
            if (this.mDisplayedValues != null) {
                this.mInputText.setRawInputType(524289);
            } else {
                this.mInputText.setRawInputType(2);
            }
            updateInputTextView();
            initializeSelectorWheelIndices();
            tryComputeMaxWidth();
        }
    }

    protected float getTopFadingEdgeStrength() {
        return TOP_AND_BOTTOM_FADING_EDGE_STRENGTH;
    }

    protected float getBottomFadingEdgeStrength() {
        return TOP_AND_BOTTOM_FADING_EDGE_STRENGTH;
    }

    protected void onDetachedFromWindow() {
        removeAllCallbacks();
    }

    protected void onDraw(Canvas canvas) {
        if (this.mHasSelectorWheel) {
            float x = (float) ((getRight() - getLeft()) / 2);
            float y = (float) this.mCurrentScrollOffset;
            if (this.mVirtualButtonPressedDrawable != null && this.mScrollState == 0) {
                if (this.mDecrementVirtualButtonPressed) {
                    this.mVirtualButtonPressedDrawable.setState(PRESSED_ENABLED_STATE_SET);
                    this.mVirtualButtonPressedDrawable.setBounds(0, 0, getRight(), this.mTopSelectionDividerTop);
                    this.mVirtualButtonPressedDrawable.draw(canvas);
                }
                if (this.mIncrementVirtualButtonPressed) {
                    this.mVirtualButtonPressedDrawable.setState(PRESSED_ENABLED_STATE_SET);
                    this.mVirtualButtonPressedDrawable.setBounds(0, this.mBottomSelectionDividerBottom, getRight(), getBottom());
                    this.mVirtualButtonPressedDrawable.draw(canvas);
                }
            }
            int[] selectorIndices = this.mSelectorIndices;
            for (int i = 0; i < selectorIndices.length; i++) {
                String scrollSelectorValue = (String) this.mSelectorIndexToStringCache.get(selectorIndices[i]);
                if (i != 1 || this.mInputText.getVisibility() != 0) {
                    canvas.drawText(scrollSelectorValue, x, y, this.mSelectorWheelPaint);
                }
                y += (float) this.mSelectorElementHeight;
            }
            if (this.mSelectionDivider != null) {
                int topOfTopDivider = this.mTopSelectionDividerTop;
                this.mSelectionDivider.setBounds(0, topOfTopDivider, getRight(), topOfTopDivider + this.mSelectionDividerHeight);
                this.mSelectionDivider.draw(canvas);
                int bottomOfBottomDivider = this.mBottomSelectionDividerBottom;
                this.mSelectionDivider.setBounds(0, bottomOfBottomDivider - this.mSelectionDividerHeight, getRight(), bottomOfBottomDivider);
                this.mSelectionDivider.draw(canvas);
                return;
            }
            return;
        }
        super.onDraw(canvas);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(NumberPicker.class.getName());
        event.setScrollable(true);
        event.setScrollY((this.mMinValue + this.mValue) * this.mSelectorElementHeight);
        if (VERSION.SDK_INT >= 15) {
            event.setMaxScrollY((this.mMaxValue - this.mMinValue) * this.mSelectorElementHeight);
        }
    }

    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        if (!this.mHasSelectorWheel && VERSION.SDK_INT >= 16) {
            return super.getAccessibilityNodeProvider();
        }
        if (this.mAccessibilityNodeProvider == null) {
            this.mAccessibilityNodeProvider = new SupportAccessibilityNodeProvider();
        }
        return this.mAccessibilityNodeProvider.mProvider;
    }

    private int makeMeasureSpec(int measureSpec, int maxSize) {
        if (maxSize == -1) {
            return measureSpec;
        }
        int size = MeasureSpec.getSize(measureSpec);
        int mode = MeasureSpec.getMode(measureSpec);
        switch (mode) {
            case Integer.MIN_VALUE:
                return MeasureSpec.makeMeasureSpec(Math.min(size, maxSize), 1073741824);
            case 0:
                return MeasureSpec.makeMeasureSpec(maxSize, 1073741824);
            case 1073741824:
                return measureSpec;
            default:
                throw new IllegalArgumentException("Unknown measure mode: " + mode);
        }
    }

    private int resolveSizeAndStateRespectingMinSize(int minSize, int measuredSize, int measureSpec) {
        if (minSize != -1) {
            return resolveSizeAndState(Math.max(minSize, measuredSize), measureSpec, 0);
        }
        return measuredSize;
    }

    public static int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case Integer.MIN_VALUE:
                if (specSize >= size) {
                    result = size;
                    break;
                }
                result = specSize | ViewCompat.MEASURED_STATE_TOO_SMALL;
                break;
            case 0:
                result = size;
                break;
            case 1073741824:
                result = specSize;
                break;
        }
        return (-16777216 & childMeasuredState) | result;
    }

    private void initializeSelectorWheelIndices() {
        this.mSelectorIndexToStringCache.clear();
        int[] selectorIndices = this.mSelectorIndices;
        int current = getValue();
        for (int i = 0; i < this.mSelectorIndices.length; i++) {
            int selectorIndex = current + (i - 1);
            if (this.mWrapSelectorWheel) {
                selectorIndex = getWrappedSelectorIndex(selectorIndex);
            }
            selectorIndices[i] = selectorIndex;
            ensureCachedScrollSelectorValue(selectorIndices[i]);
        }
    }

    private void setValueInternal(int current, boolean notifyChange) {
        if (this.mValue != current) {
            if (this.mWrapSelectorWheel) {
                current = getWrappedSelectorIndex(current);
            } else {
                current = Math.min(Math.max(current, this.mMinValue), this.mMaxValue);
            }
            int previous = this.mValue;
            this.mValue = current;
            updateInputTextView();
            if (notifyChange) {
                notifyChange(previous, current);
            }
            initializeSelectorWheelIndices();
            invalidate();
        }
    }

    private void changeValueByOne(boolean increment) {
        if (this.mHasSelectorWheel) {
            this.mInputText.setVisibility(4);
            if (!moveToFinalScrollerPosition(this.mFlingScroller)) {
                moveToFinalScrollerPosition(this.mAdjustScroller);
            }
            this.mPreviousScrollerY = 0;
            if (increment) {
                this.mFlingScroller.startScroll(0, 0, 0, -this.mSelectorElementHeight, 300);
            } else {
                this.mFlingScroller.startScroll(0, 0, 0, this.mSelectorElementHeight, 300);
            }
            invalidate();
        } else if (increment) {
            setValueInternal(this.mValue + 1, true);
        } else {
            setValueInternal(this.mValue - 1, true);
        }
    }

    private void initializeSelectorWheel() {
        initializeSelectorWheelIndices();
        int[] selectorIndices = this.mSelectorIndices;
        this.mSelectorTextGapHeight = (int) ((((float) ((getBottom() - getTop()) - (selectorIndices.length * this.mTextSize))) / ((float) selectorIndices.length)) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        this.mSelectorElementHeight = this.mTextSize + this.mSelectorTextGapHeight;
        this.mInitialScrollOffset = (this.mInputText.getBaseline() + this.mInputText.getTop()) - (this.mSelectorElementHeight * 1);
        this.mCurrentScrollOffset = this.mInitialScrollOffset;
        updateInputTextView();
    }

    private void initializeFadingEdges() {
        setVerticalFadingEdgeEnabled(true);
        setFadingEdgeLength(((getBottom() - getTop()) - this.mTextSize) / 2);
    }

    private void onScrollerFinished(Scroller scroller) {
        if (scroller == this.mFlingScroller) {
            if (!ensureScrollWheelAdjusted()) {
                updateInputTextView();
            }
            onScrollStateChange(0);
        } else if (this.mScrollState != 1) {
            updateInputTextView();
        }
    }

    private void onScrollStateChange(int scrollState) {
        if (this.mScrollState != scrollState) {
            this.mScrollState = scrollState;
            if (this.mOnScrollListener != null) {
                this.mOnScrollListener.onScrollStateChange(this, scrollState);
            }
        }
    }

    private void fling(int velocityY) {
        this.mPreviousScrollerY = 0;
        if (velocityY > 0) {
            this.mFlingScroller.fling(0, 0, 0, velocityY, 0, 0, 0, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        } else {
            this.mFlingScroller.fling(0, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, 0, velocityY, 0, 0, 0, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        }
        invalidate();
    }

    private int getWrappedSelectorIndex(int selectorIndex) {
        if (selectorIndex > this.mMaxValue) {
            return (this.mMinValue + ((selectorIndex - this.mMaxValue) % (this.mMaxValue - this.mMinValue))) - 1;
        }
        if (selectorIndex < this.mMinValue) {
            return (this.mMaxValue - ((this.mMinValue - selectorIndex) % (this.mMaxValue - this.mMinValue))) + 1;
        }
        return selectorIndex;
    }

    private void incrementSelectorIndices(int[] selectorIndices) {
        for (int i = 0; i < selectorIndices.length - 1; i++) {
            selectorIndices[i] = selectorIndices[i + 1];
        }
        int nextScrollSelectorIndex = selectorIndices[selectorIndices.length - 2] + 1;
        if (this.mWrapSelectorWheel && nextScrollSelectorIndex > this.mMaxValue) {
            nextScrollSelectorIndex = this.mMinValue;
        }
        selectorIndices[selectorIndices.length - 1] = nextScrollSelectorIndex;
        ensureCachedScrollSelectorValue(nextScrollSelectorIndex);
    }

    private void decrementSelectorIndices(int[] selectorIndices) {
        for (int i = selectorIndices.length - 1; i > 0; i--) {
            selectorIndices[i] = selectorIndices[i - 1];
        }
        int nextScrollSelectorIndex = selectorIndices[1] - 1;
        if (this.mWrapSelectorWheel && nextScrollSelectorIndex < this.mMinValue) {
            nextScrollSelectorIndex = this.mMaxValue;
        }
        selectorIndices[0] = nextScrollSelectorIndex;
        ensureCachedScrollSelectorValue(nextScrollSelectorIndex);
    }

    private void ensureCachedScrollSelectorValue(int selectorIndex) {
        SparseArray<String> cache = this.mSelectorIndexToStringCache;
        if (((String) cache.get(selectorIndex)) == null) {
            String scrollSelectorValue;
            if (selectorIndex < this.mMinValue || selectorIndex > this.mMaxValue) {
                scrollSelectorValue = "";
            } else if (this.mDisplayedValues != null) {
                scrollSelectorValue = this.mDisplayedValues[selectorIndex - this.mMinValue];
            } else {
                scrollSelectorValue = formatNumber(selectorIndex);
            }
            cache.put(selectorIndex, scrollSelectorValue);
        }
    }

    private String formatNumber(int value) {
        return this.mFormatter != null ? this.mFormatter.format(value) : formatNumberWithLocale(value);
    }

    private void validateInputTextView(View v) {
        String str = String.valueOf(((TextView) v).getText());
        if (TextUtils.isEmpty(str)) {
            updateInputTextView();
        } else {
            setValueInternal(getSelectedPos(str.toString()), true);
        }
    }

    private boolean updateInputTextView() {
        String text = this.mDisplayedValues == null ? formatNumber(this.mValue) : this.mDisplayedValues[this.mValue - this.mMinValue];
        if (TextUtils.isEmpty(text) || text.equals(this.mInputText.getText().toString())) {
            return false;
        }
        this.mInputText.setText(text);
        return true;
    }

    private void notifyChange(int previous, int current) {
        if (this.mOnValueChangeListener != null) {
            this.mOnValueChangeListener.onValueChange(this, previous, this.mValue);
        }
    }

    private void postChangeCurrentByOneFromLongPress(boolean increment, long delayMillis) {
        if (this.mChangeCurrentByOneFromLongPressCommand == null) {
            this.mChangeCurrentByOneFromLongPressCommand = new ChangeCurrentByOneFromLongPressCommand();
        } else {
            removeCallbacks(this.mChangeCurrentByOneFromLongPressCommand);
        }
        this.mChangeCurrentByOneFromLongPressCommand.setStep(increment);
        postDelayed(this.mChangeCurrentByOneFromLongPressCommand, delayMillis);
    }

    private void removeChangeCurrentByOneFromLongPress() {
        if (this.mChangeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(this.mChangeCurrentByOneFromLongPressCommand);
        }
    }

    private void postBeginSoftInputOnLongPressCommand() {
        if (this.mBeginSoftInputOnLongPressCommand == null) {
            this.mBeginSoftInputOnLongPressCommand = new BeginSoftInputOnLongPressCommand();
        } else {
            removeCallbacks(this.mBeginSoftInputOnLongPressCommand);
        }
        postDelayed(this.mBeginSoftInputOnLongPressCommand, (long) ViewConfiguration.getLongPressTimeout());
    }

    private void removeBeginSoftInputCommand() {
        if (this.mBeginSoftInputOnLongPressCommand != null) {
            removeCallbacks(this.mBeginSoftInputOnLongPressCommand);
        }
    }

    private void removeAllCallbacks() {
        if (this.mChangeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(this.mChangeCurrentByOneFromLongPressCommand);
        }
        if (this.mSetSelectionCommand != null) {
            removeCallbacks(this.mSetSelectionCommand);
        }
        if (this.mBeginSoftInputOnLongPressCommand != null) {
            removeCallbacks(this.mBeginSoftInputOnLongPressCommand);
        }
        this.mPressedStateHelper.cancel();
    }

    private int getSelectedPos(String value) {
        if (this.mDisplayedValues == null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return this.mMinValue;
            }
        }
        for (int i = 0; i < this.mDisplayedValues.length; i++) {
            value = value.toLowerCase();
            if (this.mDisplayedValues[i].toLowerCase().startsWith(value)) {
                return this.mMinValue + i;
            }
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e2) {
            return this.mMinValue;
        }
    }

    private void postSetSelectionCommand(int selectionStart, int selectionEnd) {
        if (this.mSetSelectionCommand == null) {
            this.mSetSelectionCommand = new SetSelectionCommand();
        } else {
            removeCallbacks(this.mSetSelectionCommand);
        }
        this.mSetSelectionCommand.mSelectionStart = selectionStart;
        this.mSetSelectionCommand.mSelectionEnd = selectionEnd;
        post(this.mSetSelectionCommand);
    }

    private boolean ensureScrollWheelAdjusted() {
        int deltaY = this.mInitialScrollOffset - this.mCurrentScrollOffset;
        if (deltaY == 0) {
            return false;
        }
        this.mPreviousScrollerY = 0;
        if (Math.abs(deltaY) > this.mSelectorElementHeight / 2) {
            deltaY += deltaY > 0 ? -this.mSelectorElementHeight : this.mSelectorElementHeight;
        }
        this.mAdjustScroller.startScroll(0, 0, 0, deltaY, 800);
        invalidate();
        return true;
    }

    private SupportAccessibilityNodeProvider getSupportAccessibilityNodeProvider() {
        return new SupportAccessibilityNodeProvider();
    }

    private static String formatNumberWithLocale(int value) {
        return String.format(Locale.getDefault(), "%d", new Object[]{Integer.valueOf(value)});
    }

    public Drawable getSelectionDivider() {
        return this.mSelectionDivider;
    }

    public void setSelectionDivider(Drawable mSelectionDivider) {
        this.mSelectionDivider = mSelectionDivider;
    }

    public Paint getSelectorWheelPaint() {
        return this.mSelectorWheelPaint;
    }

    public void setSelectorWheelPaint(Paint mSelectorWheelPaint) {
        this.mSelectorWheelPaint = mSelectorWheelPaint;
    }

    public EditText getInputText() {
        return this.mInputText;
    }

    public Drawable getVirtualButtonPressedDrawable() {
        return this.mVirtualButtonPressedDrawable;
    }

    public void setVirtualButtonPressedDrawable(Drawable mVirtualButtonPressedDrawable) {
        this.mVirtualButtonPressedDrawable = mVirtualButtonPressedDrawable;
    }
}
