package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.text.TextUtilsCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.appcompat.C0192R;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.KeyEvent.DispatcherState;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import dalvik.annotation.Signature;
import java.lang.reflect.Method;
import java.util.Locale;

public class ListPopupWindow {
    private static final boolean DEBUG = false;
    private static final int EXPAND_LIST_TIMEOUT = 250;
    public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
    public static final int INPUT_METHOD_NEEDED = 1;
    public static final int INPUT_METHOD_NOT_NEEDED = 2;
    public static final int MATCH_PARENT = -1;
    public static final int POSITION_PROMPT_ABOVE = 0;
    public static final int POSITION_PROMPT_BELOW = 1;
    private static final String TAG = "ListPopupWindow";
    public static final int WRAP_CONTENT = -2;
    private static Method sClipToWindowEnabledMethod = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", new Class[]{Boolean.TYPE});
    private static Method sGetMaxAvailableHeightMethod = PopupWindow.class.getDeclaredMethod("getMaxAvailableHeight", new Class[]{View.class, Integer.TYPE, Boolean.TYPE});
    private ListAdapter mAdapter;
    private Context mContext;
    private boolean mDropDownAlwaysVisible;
    private View mDropDownAnchorView;
    private int mDropDownGravity;
    private int mDropDownHeight;
    private int mDropDownHorizontalOffset;
    private DropDownListView mDropDownList;
    private Drawable mDropDownListHighlight;
    private int mDropDownVerticalOffset;
    private boolean mDropDownVerticalOffsetSet;
    private int mDropDownWidth;
    private int mDropDownWindowLayoutType;
    private boolean mForceIgnoreOutsideTouch;
    private final Handler mHandler;
    private final ListSelectorHider mHideSelector;
    private OnItemClickListener mItemClickListener;
    private OnItemSelectedListener mItemSelectedListener;
    private int mLayoutDirection;
    int mListItemExpandMaximum;
    private boolean mModal;
    private DataSetObserver mObserver;
    private PopupWindow mPopup;
    private int mPromptPosition;
    private View mPromptView;
    private final ResizePopupRunnable mResizePopupRunnable;
    private final PopupScrollListener mScrollListener;
    private Runnable mShowDropDownRunnable;
    private Rect mTempRect;
    private final PopupTouchInterceptor mTouchInterceptor;

    public static abstract class ForwardingListener implements OnTouchListener {
        private int mActivePointerId;
        private Runnable mDisallowIntercept;
        private boolean mForwarding;
        private final int mLongPressTimeout;
        private final float mScaledTouchSlop;
        private final View mSrc;
        private final int mTapTimeout;
        private final int[] mTmpLocation = new int[2];
        private Runnable mTriggerLongPress;
        private boolean mWasLongPress;

        private class DisallowIntercept implements Runnable {
            private DisallowIntercept() throws  {
            }

            public void run() throws  {
                ForwardingListener.this.mSrc.getParent().requestDisallowInterceptTouchEvent(true);
            }
        }

        private class TriggerLongPress implements Runnable {
            private TriggerLongPress() throws  {
            }

            public void run() throws  {
                ForwardingListener.this.onLongPress();
            }
        }

        public abstract ListPopupWindow getPopup() throws ;

        public ForwardingListener(View $r1) throws  {
            this.mSrc = $r1;
            this.mScaledTouchSlop = (float) ViewConfiguration.get($r1.getContext()).getScaledTouchSlop();
            this.mTapTimeout = ViewConfiguration.getTapTimeout();
            this.mLongPressTimeout = (this.mTapTimeout + ViewConfiguration.getLongPressTimeout()) / 2;
        }

        public boolean onTouch(View v, MotionEvent $r2) throws  {
            boolean $z1;
            boolean $z0 = this.mForwarding;
            if ($z0) {
                $z1 = this.mWasLongPress ? onTouchForwarded($r2) : onTouchForwarded($r2) || !onForwardingStopped();
            } else {
                $z1 = onTouchObserved($r2) && onForwardingStarted();
                if ($z1) {
                    long $l0 = SystemClock.uptimeMillis();
                    $r2 = MotionEvent.obtain($l0, $l0, 3, 0.0f, 0.0f, 0);
                    View view = this.mSrc;
                    v = view;
                    view.onTouchEvent($r2);
                    $r2.recycle();
                }
            }
            this.mForwarding = $z1;
            if ($z1 || $z0) {
                return true;
            }
            return false;
        }

        protected boolean onForwardingStarted() throws  {
            ListPopupWindow $r1 = getPopup();
            if (!($r1 == null || $r1.isShowing())) {
                $r1.show();
            }
            return true;
        }

        protected boolean onForwardingStopped() throws  {
            ListPopupWindow $r1 = getPopup();
            if ($r1 != null && $r1.isShowing()) {
                $r1.dismiss();
            }
            return true;
        }

        private boolean onTouchObserved(MotionEvent $r1) throws  {
            View $r2 = this.mSrc;
            if (!$r2.isEnabled()) {
                return false;
            }
            switch (MotionEventCompat.getActionMasked($r1)) {
                case 0:
                    this.mActivePointerId = $r1.getPointerId(0);
                    this.mWasLongPress = false;
                    if (this.mDisallowIntercept == null) {
                        this.mDisallowIntercept = new DisallowIntercept();
                    }
                    $r2.postDelayed(this.mDisallowIntercept, (long) this.mTapTimeout);
                    if (this.mTriggerLongPress == null) {
                        this.mTriggerLongPress = new TriggerLongPress();
                    }
                    $r2.postDelayed(this.mTriggerLongPress, (long) this.mLongPressTimeout);
                    return false;
                case 1:
                case 3:
                    clearCallbacks();
                    return false;
                case 2:
                    int $i0 = $r1.findPointerIndex(this.mActivePointerId);
                    if ($i0 < 0) {
                        return false;
                    }
                    if (pointInView($r2, $r1.getX($i0), $r1.getY($i0), this.mScaledTouchSlop)) {
                        return false;
                    }
                    clearCallbacks();
                    $r2.getParent().requestDisallowInterceptTouchEvent(true);
                    return true;
                default:
                    return false;
            }
        }

        private void clearCallbacks() throws  {
            if (this.mTriggerLongPress != null) {
                this.mSrc.removeCallbacks(this.mTriggerLongPress);
            }
            if (this.mDisallowIntercept != null) {
                this.mSrc.removeCallbacks(this.mDisallowIntercept);
            }
        }

        private void onLongPress() throws  {
            clearCallbacks();
            View $r1 = this.mSrc;
            if ($r1.isEnabled() && !$r1.isLongClickable() && onForwardingStarted()) {
                $r1.getParent().requestDisallowInterceptTouchEvent(true);
                long $l0 = SystemClock.uptimeMillis();
                MotionEvent $r3 = MotionEvent.obtain($l0, $l0, 3, 0.0f, 0.0f, 0);
                $r1.onTouchEvent($r3);
                $r3.recycle();
                this.mForwarding = true;
                this.mWasLongPress = true;
            }
        }

        private boolean onTouchForwarded(MotionEvent $r1) throws  {
            boolean $z0 = true;
            View $r2 = this.mSrc;
            ListPopupWindow $r3 = getPopup();
            if ($r3 == null) {
                return false;
            }
            if (!$r3.isShowing()) {
                return false;
            }
            DropDownListView $r4 = $r3.mDropDownList;
            if ($r4 == null) {
                return false;
            }
            if (!$r4.isShown()) {
                return false;
            }
            MotionEvent $r5 = MotionEvent.obtainNoHistory($r1);
            toGlobalMotionEvent($r2, $r5);
            toLocalMotionEvent($r4, $r5);
            boolean $z1 = $r4.onForwardedEvent($r5, this.mActivePointerId);
            $r5.recycle();
            int $i0 = MotionEventCompat.getActionMasked($r1);
            boolean $z2 = ($i0 == 1 || $i0 == 3) ? false : true;
            if (!($z1 && $z2)) {
                $z0 = false;
            }
            return $z0;
        }

        private static boolean pointInView(View $r0, float $f0, float $f1, float $f2) throws  {
            return $f0 >= (-$f2) && $f1 >= (-$f2) && $f0 < ((float) ($r0.getRight() - $r0.getLeft())) + $f2 && $f1 < ((float) ($r0.getBottom() - $r0.getTop())) + $f2;
        }

        private boolean toLocalMotionEvent(View $r1, MotionEvent $r2) throws  {
            int[] $r3 = this.mTmpLocation;
            $r1.getLocationOnScreen($r3);
            $r2.offsetLocation((float) (-$r3[0]), (float) (-$r3[1]));
            return true;
        }

        private boolean toGlobalMotionEvent(View $r1, MotionEvent $r2) throws  {
            int[] $r3 = this.mTmpLocation;
            $r1.getLocationOnScreen($r3);
            $r2.offsetLocation((float) $r3[0], (float) $r3[1]);
            return true;
        }
    }

    class C02452 implements Runnable {
        C02452() throws  {
        }

        public void run() throws  {
            View $r2 = ListPopupWindow.this.getAnchorView();
            if ($r2 != null && $r2.getWindowToken() != null) {
                ListPopupWindow.this.show();
            }
        }
    }

    class C02463 implements OnItemSelectedListener {
        C02463() throws  {
        }

        public void onItemSelected(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View view, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i0, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long id) throws  {
            if ($i0 != -1) {
                DropDownListView $r3 = ListPopupWindow.this.mDropDownList;
                if ($r3 != null) {
                    $r3.mListSelectionHidden = false;
                }
            }
        }

        public void onNothingSelected(@Signature({"(", "Landroid/widget/AdapterView", "<*>;)V"}) AdapterView<?> adapterView) throws  {
        }
    }

    private static class DropDownListView extends ListViewCompat {
        private ViewPropertyAnimatorCompat mClickAnimation;
        private boolean mDrawsInPressedState;
        private boolean mHijackFocus;
        private boolean mListSelectionHidden;
        private ListViewAutoScrollHelper mScrollHelper;

        public DropDownListView(Context $r1, boolean $z0) throws  {
            super($r1, null, C0192R.attr.dropDownListViewStyle);
            this.mHijackFocus = $z0;
            setCacheColorHint(0);
        }

        public boolean onForwardedEvent(MotionEvent $r1, int $i0) throws  {
            boolean $z0 = true;
            boolean $z1 = false;
            int $i2 = MotionEventCompat.getActionMasked($r1);
            switch ($i2) {
                case 1:
                    $z0 = false;
                    break;
                case 2:
                    break;
                case 3:
                    $z0 = false;
                    break;
                default:
                    break;
            }
            int $i1 = $r1.findPointerIndex($i0);
            if ($i1 < 0) {
                $z0 = false;
            } else {
                $i0 = (int) $r1.getX($i1);
                $i1 = (int) $r1.getY($i1);
                int $i3 = pointToPosition($i0, $i1);
                if ($i3 == -1) {
                    $z1 = true;
                } else {
                    View $r3 = getChildAt($i3 - getFirstVisiblePosition());
                    setPressedItem($r3, $i3, (float) $i0, (float) $i1);
                    $z0 = true;
                    if ($i2 == 1) {
                        clickPressedItem($r3, $i3);
                    }
                }
            }
            if (!$z0 || $z1) {
                clearPressedItem();
            }
            if ($z0) {
                if (this.mScrollHelper == null) {
                    this.mScrollHelper = new ListViewAutoScrollHelper(this);
                }
                this.mScrollHelper.setEnabled(true);
                this.mScrollHelper.onTouch(this, $r1);
                return $z0;
            } else if (this.mScrollHelper == null) {
                return $z0;
            } else {
                this.mScrollHelper.setEnabled(false);
                return $z0;
            }
        }

        private void clickPressedItem(View $r1, int $i0) throws  {
            performItemClick($r1, $i0, getItemIdAtPosition($i0));
        }

        private void clearPressedItem() throws  {
            this.mDrawsInPressedState = false;
            setPressed(false);
            drawableStateChanged();
            View $r1 = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
            if ($r1 != null) {
                $r1.setPressed(false);
            }
            if (this.mClickAnimation != null) {
                this.mClickAnimation.cancel();
                this.mClickAnimation = null;
            }
        }

        private void setPressedItem(View $r1, int $i0, float $f0, float $f1) throws  {
            this.mDrawsInPressedState = true;
            if (VERSION.SDK_INT >= 21) {
                drawableHotspotChanged($f0, $f1);
            }
            if (!isPressed()) {
                setPressed(true);
            }
            layoutChildren();
            if (this.mMotionPosition != -1) {
                View $r2 = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
                if (!($r2 == null || $r2 == $r1 || !$r2.isPressed())) {
                    $r2.setPressed(false);
                }
            }
            this.mMotionPosition = $i0;
            float $f2 = $f0 - ((float) $r1.getLeft());
            float $f3 = $f1 - ((float) $r1.getTop());
            if (VERSION.SDK_INT >= 21) {
                $r1.drawableHotspotChanged($f2, $f3);
            }
            if (!$r1.isPressed()) {
                $r1.setPressed(true);
            }
            positionSelectorLikeTouchCompat($i0, $r1, $f0, $f1);
            setSelectorEnabled(false);
            refreshDrawableState();
        }

        protected boolean touchModeDrawsInPressedStateCompat() throws  {
            return this.mDrawsInPressedState || super.touchModeDrawsInPressedStateCompat();
        }

        public boolean isInTouchMode() throws  {
            return (this.mHijackFocus && this.mListSelectionHidden) || super.isInTouchMode();
        }

        public boolean hasWindowFocus() throws  {
            return this.mHijackFocus || super.hasWindowFocus();
        }

        public boolean isFocused() throws  {
            return this.mHijackFocus || super.isFocused();
        }

        public boolean hasFocus() throws  {
            return this.mHijackFocus || super.hasFocus();
        }
    }

    private class ListSelectorHider implements Runnable {
        private ListSelectorHider() throws  {
        }

        public void run() throws  {
            ListPopupWindow.this.clearListSelection();
        }
    }

    private class PopupDataSetObserver extends DataSetObserver {
        private PopupDataSetObserver() throws  {
        }

        public void onChanged() throws  {
            if (ListPopupWindow.this.isShowing()) {
                ListPopupWindow.this.show();
            }
        }

        public void onInvalidated() throws  {
            ListPopupWindow.this.dismiss();
        }
    }

    private class PopupScrollListener implements OnScrollListener {
        private PopupScrollListener() throws  {
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) throws  {
        }

        public void onScrollStateChanged(AbsListView view, int $i0) throws  {
            if ($i0 == 1 && !ListPopupWindow.this.isInputMethodNotNeeded() && ListPopupWindow.this.mPopup.getContentView() != null) {
                ListPopupWindow.this.mHandler.removeCallbacks(ListPopupWindow.this.mResizePopupRunnable);
                ListPopupWindow.this.mResizePopupRunnable.run();
            }
        }
    }

    private class PopupTouchInterceptor implements OnTouchListener {
        private PopupTouchInterceptor() throws  {
        }

        public boolean onTouch(View v, MotionEvent $r2) throws  {
            int $i2 = $r2.getAction();
            int $i0 = (int) $r2.getX();
            int $i1 = (int) $r2.getY();
            if ($i2 == 0 && ListPopupWindow.this.mPopup != null && ListPopupWindow.this.mPopup.isShowing() && $i0 >= 0 && $i0 < ListPopupWindow.this.mPopup.getWidth() && $i1 >= 0 && $i1 < ListPopupWindow.this.mPopup.getHeight()) {
                ListPopupWindow.this.mHandler.postDelayed(ListPopupWindow.this.mResizePopupRunnable, 250);
            } else if ($i2 == 1) {
                ListPopupWindow.this.mHandler.removeCallbacks(ListPopupWindow.this.mResizePopupRunnable);
            }
            return false;
        }
    }

    private class ResizePopupRunnable implements Runnable {
        private ResizePopupRunnable() throws  {
        }

        public void run() throws  {
            if (ListPopupWindow.this.mDropDownList != null && ViewCompat.isAttachedToWindow(ListPopupWindow.this.mDropDownList) && ListPopupWindow.this.mDropDownList.getCount() > ListPopupWindow.this.mDropDownList.getChildCount() && ListPopupWindow.this.mDropDownList.getChildCount() <= ListPopupWindow.this.mListItemExpandMaximum) {
                ListPopupWindow.this.mPopup.setInputMethodMode(2);
                ListPopupWindow.this.show();
            }
        }
    }

    private int buildDropDown() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:50:0x01d2
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r41 = this;
        r6 = 0;
        r0 = r41;
        r7 = r0.mDropDownList;
        if (r7 != 0) goto L_0x020a;
    L_0x0007:
        r0 = r41;
        r8 = r0.mContext;
        r9 = new android.support.v7.widget.ListPopupWindow$2;
        r0 = r41;
        r9.<init>();
        r0 = r41;
        r0.mShowDropDownRunnable = r9;
        r7 = new android.support.v7.widget.ListPopupWindow$DropDownListView;
        r0 = r41;
        r10 = r0.mModal;
        if (r10 != 0) goto L_0x01d6;
    L_0x001e:
        r10 = 1;
    L_0x001f:
        r7.<init>(r8, r10);
        r0 = r41;
        r0.mDropDownList = r7;
        r0 = r41;
        r11 = r0.mDropDownListHighlight;
        if (r11 == 0) goto L_0x0037;
    L_0x002c:
        r0 = r41;
        r7 = r0.mDropDownList;
        r0 = r41;
        r11 = r0.mDropDownListHighlight;
        r7.setSelector(r11);
    L_0x0037:
        r0 = r41;
        r7 = r0.mDropDownList;
        r0 = r41;
        r12 = r0.mAdapter;
        r7.setAdapter(r12);
        r0 = r41;
        r7 = r0.mDropDownList;
        r0 = r41;
        r13 = r0.mItemClickListener;
        r7.setOnItemClickListener(r13);
        r0 = r41;
        r7 = r0.mDropDownList;
        r14 = 1;
        r7.setFocusable(r14);
        r0 = r41;
        r7 = r0.mDropDownList;
        r14 = 1;
        r7.setFocusableInTouchMode(r14);
        r0 = r41;
        r7 = r0.mDropDownList;
        r15 = new android.support.v7.widget.ListPopupWindow$3;
        r0 = r41;
        r15.<init>();
        r7.setOnItemSelectedListener(r15);
        r0 = r41;
        r7 = r0.mDropDownList;
        r0 = r41;
        r0 = r0.mScrollListener;
        r16 = r0;
        r7.setOnScrollListener(r0);
        r0 = r41;
        r0 = r0.mItemSelectedListener;
        r17 = r0;
        if (r17 == 0) goto L_0x008d;
    L_0x0080:
        r0 = r41;
        r7 = r0.mDropDownList;
        r0 = r41;
        r0 = r0.mItemSelectedListener;
        r17 = r0;
        r7.setOnItemSelectedListener(r0);
    L_0x008d:
        r0 = r41;
        r0 = r0.mDropDownList;
        r18 = r0;
        r0 = r41;
        r0 = r0.mPromptView;
        r19 = r0;
        if (r19 == 0) goto L_0x0128;
    L_0x009b:
        r20 = new android.widget.LinearLayout;
        r0 = r20;
        r0.<init>(r8);
        r14 = 1;
        r0 = r20;
        r0.setOrientation(r14);
        r21 = new android.widget.LinearLayout$LayoutParams;
        r14 = -1;
        r22 = 0;
        r23 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = r21;
        r1 = r22;
        r2 = r23;
        r0.<init>(r14, r1, r2);
        r0 = r41;
        r6 = r0.mPromptPosition;
        switch(r6) {
            case 0: goto L_0x01f1;
            case 1: goto L_0x01d8;
            default: goto L_0x00c0;
        };
    L_0x00c0:
        goto L_0x00c1;
    L_0x00c1:
        r24 = new java.lang.StringBuilder;
        r0 = r24;
        r0.<init>();
        r25 = "Invalid hint position ";
        r0 = r24;
        r1 = r25;
        r24 = r0.append(r1);
        r0 = r41;
        r6 = r0.mPromptPosition;
        r0 = r24;
        r24 = r0.append(r6);
        r0 = r24;
        r26 = r0.toString();
        r25 = "ListPopupWindow";
        r0 = r25;
        r1 = r26;
        android.util.Log.e(r0, r1);
    L_0x00eb:
        r0 = r41;
        r6 = r0.mDropDownWidth;
        if (r6 < 0) goto L_0x0206;
    L_0x00f1:
        r6 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r0 = r41;
        r0 = r0.mDropDownWidth;
        r27 = r0;
    L_0x00fa:
        r0 = r27;
        r6 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r6);
        r14 = 0;
        r0 = r19;
        r0.measure(r6, r14);
        r0 = r19;
        r28 = r0.getLayoutParams();
        r29 = r28;
        r29 = (android.widget.LinearLayout.LayoutParams) r29;
        r21 = r29;
        r0 = r19;
        r6 = r0.getMeasuredHeight();
        r0 = r21;
        r0 = r0.topMargin;
        r27 = r0;
        r6 = r6 + r0;
        r0 = r21;
        r0 = r0.bottomMargin;
        r27 = r0;
        r6 = r6 + r0;
        r18 = r20;
    L_0x0128:
        r0 = r41;
        r0 = r0.mPopup;
        r30 = r0;
        r1 = r18;
        r0.setContentView(r1);
    L_0x0133:
        r27 = 0;
        r0 = r41;
        r0 = r0.mPopup;
        r30 = r0;
        r11 = r0.getBackground();
        if (r11 == 0) goto L_0x0243;
    L_0x0141:
        r0 = r41;
        r0 = r0.mTempRect;
        r31 = r0;
        r11.getPadding(r0);
        r0 = r41;
        r0 = r0.mTempRect;
        r31 = r0;
        r0 = r0.top;
        r27 = r0;
        r0 = r41;
        r0 = r0.mTempRect;
        r31 = r0;
        r0 = r0.bottom;
        r32 = r0;
        goto L_0x0162;
    L_0x015f:
        goto L_0x00eb;
    L_0x0162:
        r0 = r27;
        r1 = r32;
        r0 = r0 + r1;
        r27 = r0;
        goto L_0x016d;
    L_0x016a:
        goto L_0x00eb;
    L_0x016d:
        r0 = r41;
        r10 = r0.mDropDownVerticalOffsetSet;
        if (r10 != 0) goto L_0x0188;
    L_0x0173:
        goto L_0x0177;
    L_0x0174:
        goto L_0x00fa;
    L_0x0177:
        r0 = r41;
        r0 = r0.mTempRect;
        r31 = r0;
        r0 = r0.top;
        r32 = r0;
        r0 = -r0;
        r32 = r0;
        r1 = r41;
        r1.mDropDownVerticalOffset = r0;
    L_0x0188:
        r0 = r41;
        r0 = r0.mPopup;
        r30 = r0;
        r32 = r0.getInputMethodMode();
        r14 = 2;
        r0 = r32;
        if (r0 != r14) goto L_0x024d;
    L_0x0197:
        r10 = 1;
    L_0x0198:
        r0 = r41;
        r19 = r0.getAnchorView();
        goto L_0x01a2;
    L_0x019f:
        goto L_0x015f;
    L_0x01a2:
        r0 = r41;
        r0 = r0.mDropDownVerticalOffset;
        r32 = r0;
        goto L_0x01ac;
    L_0x01a9:
        goto L_0x016a;
    L_0x01ac:
        r0 = r41;
        r1 = r19;
        r2 = r32;
        r32 = r0.getMaxAvailableHeight(r1, r2, r10);
        goto L_0x01ba;
    L_0x01b7:
        goto L_0x0174;
    L_0x01ba:
        goto L_0x01be;
    L_0x01bb:
        goto L_0x0133;
    L_0x01be:
        r0 = r41;
        r10 = r0.mDropDownAlwaysVisible;
        if (r10 != 0) goto L_0x01cf;
    L_0x01c4:
        r0 = r41;
        r0 = r0.mDropDownHeight;
        r33 = r0;
        r14 = -1;
        r0 = r33;
        if (r0 != r14) goto L_0x024f;
    L_0x01cf:
        r6 = r32 + r27;
        return r6;
        goto L_0x01d6;
    L_0x01d3:
        goto L_0x001f;
    L_0x01d6:
        r10 = 0;
        goto L_0x01d3;
    L_0x01d8:
        r0 = r20;
        r1 = r18;
        r2 = r21;
        r0.addView(r1, r2);
        goto L_0x01e5;
    L_0x01e2:
        goto L_0x0188;
    L_0x01e5:
        r0 = r20;
        r1 = r19;
        r0.addView(r1);
        goto L_0x019f;
        goto L_0x01f1;
    L_0x01ee:
        goto L_0x0198;
    L_0x01f1:
        r0 = r20;
        r1 = r19;
        r0.addView(r1);
        goto L_0x01fc;
    L_0x01f9:
        goto L_0x01bb;
    L_0x01fc:
        r0 = r20;
        r1 = r18;
        r2 = r21;
        r0.addView(r1, r2);
        goto L_0x01a9;
    L_0x0206:
        r6 = 0;
        r27 = 0;
        goto L_0x01b7;
    L_0x020a:
        r0 = r41;
        r0 = r0.mPopup;
        r30 = r0;
        r19 = r0.getContentView();
        r34 = r19;
        r34 = (android.view.ViewGroup) r34;
        r18 = r34;
        r0 = r41;
        r0 = r0.mPromptView;
        r19 = r0;
        if (r19 == 0) goto L_0x0133;
    L_0x0222:
        r0 = r19;
        r28 = r0.getLayoutParams();
        r35 = r28;
        r35 = (android.widget.LinearLayout.LayoutParams) r35;
        r21 = r35;
        r0 = r19;
        r6 = r0.getMeasuredHeight();
        r0 = r21;
        r0 = r0.topMargin;
        r27 = r0;
        r6 = r6 + r0;
        r0 = r21;
        r0 = r0.bottomMargin;
        r27 = r0;
        r6 = r6 + r0;
        goto L_0x01f9;
    L_0x0243:
        r0 = r41;
        r0 = r0.mTempRect;
        r31 = r0;
        r0.setEmpty();
        goto L_0x01e2;
    L_0x024d:
        r10 = 0;
        goto L_0x01ee;
    L_0x024f:
        r0 = r41;
        r0 = r0.mDropDownWidth;
        r33 = r0;
        switch(r33) {
            case -2: goto L_0x028c;
            case -1: goto L_0x02d0;
            default: goto L_0x0258;
        };
    L_0x0258:
        goto L_0x0259;
    L_0x0259:
        r0 = r41;
        r0 = r0.mDropDownWidth;
        r33 = r0;
        r14 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r33;
        r33 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r14);
    L_0x0268:
        r0 = r41;
        r7 = r0.mDropDownList;
        r0 = r32;
        r0 = r0 - r6;
        r32 = r0;
        r14 = 0;
        r22 = -1;
        r36 = -1;
        r0 = r7;
        r1 = r33;
        r2 = r14;
        r3 = r22;
        r4 = r32;
        r5 = r36;
        r32 = r0.measureHeightOfChildrenCompat(r1, r2, r3, r4, r5);
        if (r32 <= 0) goto L_0x0289;
    L_0x0286:
        r0 = r27;
        r6 = r6 + r0;
    L_0x0289:
        r6 = r32 + r6;
        return r6;
    L_0x028c:
        r0 = r41;
        r8 = r0.mContext;
        r37 = r8.getResources();
        r0 = r37;
        r38 = r0.getDisplayMetrics();
        r0 = r38;
        r0 = r0.widthPixels;
        r33 = r0;
        r0 = r41;
        r0 = r0.mTempRect;
        r31 = r0;
        r0 = r0.left;
        r39 = r0;
        r0 = r41;
        r0 = r0.mTempRect;
        r31 = r0;
        r0 = r0.right;
        r40 = r0;
        goto L_0x02b8;
    L_0x02b5:
        goto L_0x0268;
    L_0x02b8:
        r0 = r39;
        r1 = r40;
        r0 = r0 + r1;
        r39 = r0;
        r0 = r33;
        r1 = r39;
        r0 = r0 - r1;
        r33 = r0;
        r14 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r0 = r33;
        r33 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r14);
        goto L_0x0268;
    L_0x02d0:
        r0 = r41;
        r8 = r0.mContext;
        r37 = r8.getResources();
        r0 = r37;
        r38 = r0.getDisplayMetrics();
        r0 = r38;
        r0 = r0.widthPixels;
        r33 = r0;
        r0 = r41;
        r0 = r0.mTempRect;
        r31 = r0;
        r0 = r0.left;
        r39 = r0;
        r0 = r41;
        r0 = r0.mTempRect;
        r31 = r0;
        r0 = r0.right;
        r40 = r0;
        r0 = r39;
        r1 = r40;
        r0 = r0 + r1;
        r39 = r0;
        r0 = r33;
        r1 = r39;
        r0 = r0 - r1;
        r33 = r0;
        r14 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r33;
        r33 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r14);
        goto L_0x02b5;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ListPopupWindow.buildDropDown():int");
    }

    private static boolean isConfirmKey(int $i0) throws  {
        return $i0 == 66 || $i0 == 23;
    }

    public void show() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:57:0x0148 in {4, 11, 12, 17, 20, 23, 26, 27, 28, 29, 32, 33, 34, 37, 38, 39, 40, 43, 46, 50, 52, 54, 56, 58, 62, 64, 65, 69, 79} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r23 = this;
        r6 = 1;
        r7 = 0;
        r8 = -1;
        r0 = r23;
        r9 = r0.buildDropDown();
        r0 = r23;
        r10 = r0.isInputMethodNotNeeded();
        r0 = r23;
        r11 = r0.mPopup;
        r0 = r23;
        r12 = r0.mDropDownWindowLayoutType;
        android.support.v4.widget.PopupWindowCompat.setWindowLayoutType(r11, r12);
        r0 = r23;
        r11 = r0.mPopup;
        r13 = r11.isShowing();
        if (r13 == 0) goto L_0x00d7;
    L_0x0024:
        r0 = r23;
        r12 = r0.mDropDownWidth;
        r14 = -1;
        if (r12 != r14) goto L_0x008a;
    L_0x002b:
        r12 = -1;
    L_0x002c:
        r0 = r23;
        r15 = r0.mDropDownHeight;
        r14 = -1;
        if (r15 != r14) goto L_0x00c6;
    L_0x0033:
        if (r10 == 0) goto L_0x00a3;
    L_0x0035:
        if (r10 == 0) goto L_0x00a8;
    L_0x0037:
        r0 = r23;
        r11 = r0.mPopup;
        r0 = r23;
        r15 = r0.mDropDownWidth;
        r14 = -1;
        if (r15 != r14) goto L_0x00a5;
    L_0x0042:
        r16 = -1;
    L_0x0044:
        r0 = r16;
        r11.setWidth(r0);
        r0 = r23;
        r11 = r0.mPopup;
        r14 = 0;
        r11.setHeight(r14);
    L_0x0051:
        r0 = r23;
        r11 = r0.mPopup;
        r0 = r23;
        r6 = r0.mForceIgnoreOutsideTouch;
        if (r6 != 0) goto L_0x0062;
    L_0x005b:
        r0 = r23;
        r6 = r0.mDropDownAlwaysVisible;
        if (r6 != 0) goto L_0x0062;
    L_0x0061:
        r7 = 1;
    L_0x0062:
        r11.setOutsideTouchable(r7);
        r0 = r23;
        r11 = r0.mPopup;
        r0 = r23;
        r17 = r0.getAnchorView();
        r0 = r23;
        r15 = r0.mDropDownHorizontalOffset;
        r0 = r23;
        r0 = r0.mDropDownVerticalOffset;
        r18 = r0;
        if (r12 >= 0) goto L_0x00d4;
    L_0x007b:
        r12 = -1;
    L_0x007c:
        if (r9 >= 0) goto L_0x00d5;
    L_0x007e:
        r0 = r11;
        r1 = r17;
        r2 = r15;
        r3 = r18;
        r4 = r12;
        r5 = r8;
        r0.update(r1, r2, r3, r4, r5);
        return;
    L_0x008a:
        r0 = r23;
        r12 = r0.mDropDownWidth;
        r14 = -2;
        if (r12 != r14) goto L_0x009e;
    L_0x0091:
        r0 = r23;
        r17 = r0.getAnchorView();
        r0 = r17;
        r12 = r0.getWidth();
        goto L_0x002c;
    L_0x009e:
        r0 = r23;
        r12 = r0.mDropDownWidth;
        goto L_0x002c;
    L_0x00a3:
        r9 = -1;
        goto L_0x0035;
    L_0x00a5:
        r16 = 0;
        goto L_0x0044;
    L_0x00a8:
        r0 = r23;
        r11 = r0.mPopup;
        r0 = r23;
        r15 = r0.mDropDownWidth;
        r14 = -1;
        if (r15 != r14) goto L_0x00c3;
    L_0x00b3:
        r16 = -1;
    L_0x00b5:
        r0 = r16;
        r11.setWidth(r0);
        r0 = r23;
        r11 = r0.mPopup;
        r14 = -1;
        r11.setHeight(r14);
        goto L_0x0051;
    L_0x00c3:
        r16 = 0;
        goto L_0x00b5;
    L_0x00c6:
        r0 = r23;
        r15 = r0.mDropDownHeight;
        r14 = -2;
        if (r15 != r14) goto L_0x00ce;
    L_0x00cd:
        goto L_0x0051;
    L_0x00ce:
        r0 = r23;
        r9 = r0.mDropDownHeight;
        goto L_0x0051;
    L_0x00d4:
        goto L_0x007c;
    L_0x00d5:
        r8 = r9;
        goto L_0x007e;
    L_0x00d7:
        r0 = r23;
        r8 = r0.mDropDownWidth;
        r14 = -1;
        if (r8 != r14) goto L_0x0187;
    L_0x00de:
        r8 = -1;
    L_0x00df:
        r0 = r23;
        r12 = r0.mDropDownHeight;
        r14 = -1;
        if (r12 != r14) goto L_0x01a0;
    L_0x00e6:
        r9 = -1;
    L_0x00e7:
        r0 = r23;
        r11 = r0.mPopup;
        r11.setWidth(r8);
        r0 = r23;
        r11 = r0.mPopup;
        r11.setHeight(r9);
        r14 = 1;
        r0 = r23;
        r0.setPopupClipToScreenEnabled(r14);
        r0 = r23;
        r11 = r0.mPopup;
        r0 = r23;
        r7 = r0.mForceIgnoreOutsideTouch;
        if (r7 != 0) goto L_0x01ad;
    L_0x0105:
        r0 = r23;
        r7 = r0.mDropDownAlwaysVisible;
        if (r7 != 0) goto L_0x01ad;
    L_0x010b:
        r11.setOutsideTouchable(r6);
        r0 = r23;
        r11 = r0.mPopup;
        r0 = r23;
        r0 = r0.mTouchInterceptor;
        r19 = r0;
        r11.setTouchInterceptor(r0);
        r0 = r23;
        r11 = r0.mPopup;
        r0 = r23;
        r17 = r0.getAnchorView();
        r0 = r23;
        r8 = r0.mDropDownHorizontalOffset;
        r0 = r23;
        r9 = r0.mDropDownVerticalOffset;
        r0 = r23;
        r12 = r0.mDropDownGravity;
        goto L_0x0135;
    L_0x0132:
        goto L_0x00df;
    L_0x0135:
        r0 = r17;
        android.support.v4.widget.PopupWindowCompat.showAsDropDown(r11, r0, r8, r9, r12);
        goto L_0x013e;
    L_0x013b:
        goto L_0x00df;
    L_0x013e:
        r0 = r23;
        r0 = r0.mDropDownList;
        r20 = r0;
        goto L_0x014c;
    L_0x0145:
        goto L_0x00e7;
        goto L_0x014c;
    L_0x0149:
        goto L_0x00e7;
    L_0x014c:
        r14 = -1;
        r0 = r20;
        r0.setSelection(r14);
        r0 = r23;
        r6 = r0.mModal;
        if (r6 == 0) goto L_0x0168;
    L_0x0158:
        r0 = r23;
        r0 = r0.mDropDownList;
        r20 = r0;
        goto L_0x0162;
    L_0x015f:
        goto L_0x010b;
    L_0x0162:
        r6 = r0.isInTouchMode();
        if (r6 == 0) goto L_0x016d;
    L_0x0168:
        r0 = r23;
        r0.clearListSelection();
    L_0x016d:
        r0 = r23;
        r6 = r0.mModal;
        if (r6 != 0) goto L_0x01af;
    L_0x0173:
        r0 = r23;
        r0 = r0.mHandler;
        r21 = r0;
        r0 = r23;
        r0 = r0.mHideSelector;
        r22 = r0;
        r0 = r21;
        r1 = r22;
        r0.post(r1);
        return;
    L_0x0187:
        r0 = r23;
        r8 = r0.mDropDownWidth;
        r14 = -2;
        if (r8 != r14) goto L_0x019b;
    L_0x018e:
        r0 = r23;
        r17 = r0.getAnchorView();
        r0 = r17;
        r8 = r0.getWidth();
        goto L_0x0132;
    L_0x019b:
        r0 = r23;
        r8 = r0.mDropDownWidth;
        goto L_0x013b;
    L_0x01a0:
        r0 = r23;
        r12 = r0.mDropDownHeight;
        r14 = -2;
        if (r12 != r14) goto L_0x01a8;
    L_0x01a7:
        goto L_0x0145;
    L_0x01a8:
        r0 = r23;
        r9 = r0.mDropDownHeight;
        goto L_0x0149;
    L_0x01ad:
        r6 = 0;
        goto L_0x015f;
    L_0x01af:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ListPopupWindow.show():void");
    }

    static {
        try {
        } catch (NoSuchMethodException e) {
            Log.i(TAG, "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
        }
        try {
        } catch (NoSuchMethodException e2) {
            Log.i(TAG, "Could not find method getMaxAvailableHeight(View, int, boolean) on PopupWindow. Oh well.");
        }
    }

    public ListPopupWindow(Context $r1) throws  {
        this($r1, null, C0192R.attr.listPopupWindowStyle);
    }

    public ListPopupWindow(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, C0192R.attr.listPopupWindowStyle);
    }

    public ListPopupWindow(Context $r1, AttributeSet $r2, int $i0) throws  {
        this($r1, $r2, $i0, 0);
    }

    public ListPopupWindow(Context $r1, AttributeSet $r2, int $i0, int $i1) throws  {
        ListPopupWindow listPopupWindow = this;
        this.mDropDownHeight = -2;
        this.mDropDownWidth = -2;
        this.mDropDownWindowLayoutType = 1002;
        this.mDropDownGravity = 0;
        this.mDropDownAlwaysVisible = false;
        this.mForceIgnoreOutsideTouch = false;
        this.mListItemExpandMaximum = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.mPromptPosition = 0;
        listPopupWindow = this;
        this.mResizePopupRunnable = new ResizePopupRunnable();
        listPopupWindow = this;
        this.mTouchInterceptor = new PopupTouchInterceptor();
        listPopupWindow = this;
        this.mScrollListener = new PopupScrollListener();
        listPopupWindow = this;
        this.mHideSelector = new ListSelectorHider();
        this.mTempRect = new Rect();
        this.mContext = $r1;
        this.mHandler = new Handler($r1.getMainLooper());
        TypedArray $r12 = $r1.obtainStyledAttributes($r2, C0192R.styleable.ListPopupWindow, $i0, $i1);
        this.mDropDownHorizontalOffset = $r12.getDimensionPixelOffset(C0192R.styleable.ListPopupWindow_android_dropDownHorizontalOffset, 0);
        this.mDropDownVerticalOffset = $r12.getDimensionPixelOffset(C0192R.styleable.ListPopupWindow_android_dropDownVerticalOffset, 0);
        if (this.mDropDownVerticalOffset != 0) {
            this.mDropDownVerticalOffsetSet = true;
        }
        $r12.recycle();
        this.mPopup = new AppCompatPopupWindow($r1, $r2, $i0);
        this.mPopup.setInputMethodMode(1);
        Context $r13 = this.mContext;
        Locale $r3 = $r13.getResources().getConfiguration().locale;
        this.mLayoutDirection = TextUtilsCompat.getLayoutDirectionFromLocale($r3);
    }

    public void setAdapter(ListAdapter $r1) throws  {
        if (this.mObserver == null) {
            this.mObserver = new PopupDataSetObserver();
        } else if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver);
        }
        this.mAdapter = $r1;
        if (this.mAdapter != null) {
            $r1.registerDataSetObserver(this.mObserver);
        }
        if (this.mDropDownList != null) {
            this.mDropDownList.setAdapter(this.mAdapter);
        }
    }

    public void setPromptPosition(int $i0) throws  {
        this.mPromptPosition = $i0;
    }

    public int getPromptPosition() throws  {
        return this.mPromptPosition;
    }

    public void setModal(boolean $z0) throws  {
        this.mModal = $z0;
        this.mPopup.setFocusable($z0);
    }

    public boolean isModal() throws  {
        return this.mModal;
    }

    public void setForceIgnoreOutsideTouch(boolean $z0) throws  {
        this.mForceIgnoreOutsideTouch = $z0;
    }

    public void setDropDownAlwaysVisible(boolean $z0) throws  {
        this.mDropDownAlwaysVisible = $z0;
    }

    public boolean isDropDownAlwaysVisible() throws  {
        return this.mDropDownAlwaysVisible;
    }

    public void setSoftInputMode(int $i0) throws  {
        this.mPopup.setSoftInputMode($i0);
    }

    public int getSoftInputMode() throws  {
        return this.mPopup.getSoftInputMode();
    }

    public void setListSelector(Drawable $r1) throws  {
        this.mDropDownListHighlight = $r1;
    }

    public Drawable getBackground() throws  {
        return this.mPopup.getBackground();
    }

    public void setBackgroundDrawable(Drawable $r1) throws  {
        this.mPopup.setBackgroundDrawable($r1);
    }

    public void setAnimationStyle(int $i0) throws  {
        this.mPopup.setAnimationStyle($i0);
    }

    public int getAnimationStyle() throws  {
        return this.mPopup.getAnimationStyle();
    }

    public View getAnchorView() throws  {
        return this.mDropDownAnchorView;
    }

    public void setAnchorView(View $r1) throws  {
        this.mDropDownAnchorView = $r1;
    }

    public int getHorizontalOffset() throws  {
        return this.mDropDownHorizontalOffset;
    }

    public void setHorizontalOffset(int $i0) throws  {
        this.mDropDownHorizontalOffset = $i0;
    }

    public int getVerticalOffset() throws  {
        return !this.mDropDownVerticalOffsetSet ? 0 : this.mDropDownVerticalOffset;
    }

    public void setVerticalOffset(int $i0) throws  {
        this.mDropDownVerticalOffset = $i0;
        this.mDropDownVerticalOffsetSet = true;
    }

    public void setDropDownGravity(int $i0) throws  {
        this.mDropDownGravity = $i0;
    }

    public int getWidth() throws  {
        return this.mDropDownWidth;
    }

    public void setWidth(int $i0) throws  {
        this.mDropDownWidth = $i0;
    }

    public void setContentWidth(int $i0) throws  {
        Drawable $r1 = this.mPopup.getBackground();
        if ($r1 != null) {
            $r1.getPadding(this.mTempRect);
            this.mDropDownWidth = (this.mTempRect.left + this.mTempRect.right) + $i0;
            return;
        }
        setWidth($i0);
    }

    public int getHeight() throws  {
        return this.mDropDownHeight;
    }

    public void setHeight(int $i0) throws  {
        this.mDropDownHeight = $i0;
    }

    public void setWindowLayoutType(int $i0) throws  {
        this.mDropDownWindowLayoutType = $i0;
    }

    public void setOnItemClickListener(OnItemClickListener $r1) throws  {
        this.mItemClickListener = $r1;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener $r1) throws  {
        this.mItemSelectedListener = $r1;
    }

    public void setPromptView(View $r1) throws  {
        boolean $z0 = isShowing();
        if ($z0) {
            removePromptView();
        }
        this.mPromptView = $r1;
        if ($z0) {
            show();
        }
    }

    public void postShow() throws  {
        this.mHandler.post(this.mShowDropDownRunnable);
    }

    public void dismiss() throws  {
        this.mPopup.dismiss();
        removePromptView();
        this.mPopup.setContentView(null);
        this.mDropDownList = null;
        this.mHandler.removeCallbacks(this.mResizePopupRunnable);
    }

    public void setOnDismissListener(OnDismissListener $r1) throws  {
        this.mPopup.setOnDismissListener($r1);
    }

    private void removePromptView() throws  {
        if (this.mPromptView != null) {
            ViewParent $r1 = this.mPromptView.getParent();
            if ($r1 instanceof ViewGroup) {
                ((ViewGroup) $r1).removeView(this.mPromptView);
            }
        }
    }

    public void setInputMethodMode(int $i0) throws  {
        this.mPopup.setInputMethodMode($i0);
    }

    public int getInputMethodMode() throws  {
        return this.mPopup.getInputMethodMode();
    }

    public void setSelection(int $i0) throws  {
        DropDownListView $r1 = this.mDropDownList;
        if (isShowing() && $r1 != null) {
            $r1.mListSelectionHidden = false;
            $r1.setSelection($i0);
            if (VERSION.SDK_INT >= 11 && $r1.getChoiceMode() != 0) {
                $r1.setItemChecked($i0, true);
            }
        }
    }

    public void clearListSelection() throws  {
        DropDownListView $r1 = this.mDropDownList;
        if ($r1 != null) {
            $r1.mListSelectionHidden = true;
            $r1.requestLayout();
        }
    }

    public boolean isShowing() throws  {
        return this.mPopup.isShowing();
    }

    public boolean isInputMethodNotNeeded() throws  {
        return this.mPopup.getInputMethodMode() == 2;
    }

    public boolean performItemClick(int $i0) throws  {
        if (!isShowing()) {
            return false;
        }
        if (this.mItemClickListener != null) {
            AdapterView $r1 = this.mDropDownList;
            View $r3 = $r1.getChildAt($i0 - $r1.getFirstVisiblePosition());
            ListAdapter $r4 = $r1.getAdapter();
            this.mItemClickListener.onItemClick($r1, $r3, $i0, $r4.getItemId($i0));
        }
        return true;
    }

    public Object getSelectedItem() throws  {
        return !isShowing() ? null : this.mDropDownList.getSelectedItem();
    }

    public int getSelectedItemPosition() throws  {
        return !isShowing() ? -1 : this.mDropDownList.getSelectedItemPosition();
    }

    public long getSelectedItemId() throws  {
        return !isShowing() ? Long.MIN_VALUE : this.mDropDownList.getSelectedItemId();
    }

    public View getSelectedView() throws  {
        return !isShowing() ? null : this.mDropDownList.getSelectedView();
    }

    public ListView getListView() throws  {
        return this.mDropDownList;
    }

    void setListItemExpandMax(int $i0) throws  {
        this.mListItemExpandMaximum = $i0;
    }

    public boolean onKeyDown(int $i0, KeyEvent $r1) throws  {
        if (isShowing() && $i0 != 62 && (this.mDropDownList.getSelectedItemPosition() >= 0 || !isConfirmKey($i0))) {
            int $i1 = this.mDropDownList.getSelectedItemPosition();
            boolean $z0 = !this.mPopup.isAboveAnchor();
            ListAdapter $r2 = this.mAdapter;
            int $i2 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            int $i3 = Integer.MIN_VALUE;
            if ($r2 != null) {
                boolean $z1 = $r2.areAllItemsEnabled();
                if ($z1) {
                    $i2 = 0;
                } else {
                    $i2 = this.mDropDownList.lookForSelectablePosition(0, true);
                }
                $i3 = $z1 ? $r2.getCount() - 1 : this.mDropDownList.lookForSelectablePosition($r2.getCount() - 1, false);
            }
            if (!($z0 && $i0 == 19 && $i1 <= $i2) && ($z0 || $i0 != 20 || $i1 < $i3)) {
                this.mDropDownList.mListSelectionHidden = false;
                if (this.mDropDownList.onKeyDown($i0, $r1)) {
                    this.mPopup.setInputMethodMode(2);
                    this.mDropDownList.requestFocusFromTouch();
                    show();
                    switch ($i0) {
                        case 19:
                        case 20:
                        case 23:
                        case 66:
                            break;
                        default:
                            break;
                    }
                } else if ($z0 && $i0 == 20) {
                    if ($i1 == $i3) {
                        return true;
                    }
                } else if (!$z0 && $i0 == 19 && $i1 == $i2) {
                    return true;
                }
            }
            clearListSelection();
            this.mPopup.setInputMethodMode(1);
            show();
            return true;
        }
        return false;
    }

    public boolean onKeyUp(int $i0, KeyEvent $r1) throws  {
        if (!isShowing() || this.mDropDownList.getSelectedItemPosition() < 0) {
            return false;
        }
        boolean $z0 = this.mDropDownList.onKeyUp($i0, $r1);
        if (!$z0 || !isConfirmKey($i0)) {
            return $z0;
        }
        dismiss();
        return $z0;
    }

    public boolean onKeyPreIme(int $i0, KeyEvent $r1) throws  {
        if ($i0 == 4 && isShowing()) {
            View $r2 = this.mDropDownAnchorView;
            DispatcherState $r3;
            if ($r1.getAction() == 0 && $r1.getRepeatCount() == 0) {
                $r3 = $r2.getKeyDispatcherState();
                if ($r3 == null) {
                    return true;
                }
                $r3.startTracking($r1, this);
                return true;
            } else if ($r1.getAction() == 1) {
                $r3 = $r2.getKeyDispatcherState();
                if ($r3 != null) {
                    $r3.handleUpEvent($r1);
                }
                if ($r1.isTracking() && !$r1.isCanceled()) {
                    dismiss();
                    return true;
                }
            }
        }
        return false;
    }

    public OnTouchListener createDragToOpenListener(View $r1) throws  {
        return new ForwardingListener($r1) {
            public ListPopupWindow getPopup() throws  {
                return ListPopupWindow.this;
            }
        };
    }

    private void setPopupClipToScreenEnabled(boolean $z0) throws  {
        if (sClipToWindowEnabledMethod != null) {
            Method $r3 = sClipToWindowEnabledMethod;
            try {
                $r3.invoke(this.mPopup, new Object[]{Boolean.valueOf($z0)});
            } catch (Exception e) {
                Log.i(TAG, "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
        }
    }

    private int getMaxAvailableHeight(View $r1, int $i0, boolean $z0) throws  {
        if (sGetMaxAvailableHeightMethod != null) {
            Method $r3 = sGetMaxAvailableHeightMethod;
            try {
                $i0 = ((Integer) $r3.invoke(this.mPopup, new Object[]{$r1, Integer.valueOf($i0), Boolean.valueOf($z0)})).intValue();
                return $i0;
            } catch (Exception e) {
                Log.i(TAG, "Could not call getMaxAvailableHeightMethod(View, int, boolean) on PopupWindow. Using the public version.");
            }
        }
        return this.mPopup.getMaxAvailableHeight($r1, $i0);
    }
}
