package android.support.v4.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import com.waze.LayoutManager;
import com.waze.map.CanvasFont;
import dalvik.annotation.Signature;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewPager extends ViewGroup {
    private static final int CLOSE_ENOUGH = 2;
    private static final Comparator<ItemInfo> COMPARATOR = new C01211();
    private static final boolean DEBUG = false;
    private static final int DEFAULT_GUTTER_SIZE = 16;
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int DRAW_ORDER_DEFAULT = 0;
    private static final int DRAW_ORDER_FORWARD = 1;
    private static final int DRAW_ORDER_REVERSE = 2;
    private static final int INVALID_POINTER = -1;
    private static final int[] LAYOUT_ATTRS = new int[]{16842931};
    private static final int MAX_SETTLE_DURATION = 600;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    private static final int MIN_FLING_VELOCITY = 400;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "ViewPager";
    private static final boolean USE_CACHE = false;
    private static final Interpolator sInterpolator = new C01222();
    private static final ViewPositionComparator sPositionComparator = new ViewPositionComparator();
    private int mActivePointerId = -1;
    private PagerAdapter mAdapter;
    private OnAdapterChangeListener mAdapterChangeListener;
    private int mBottomPageBounds;
    private boolean mCalledSuper;
    private int mChildHeightMeasureSpec;
    private int mChildWidthMeasureSpec;
    private int mCloseEnough;
    private int mCurItem;
    private int mDecorChildCount;
    private int mDefaultGutterSize;
    private int mDrawingOrder;
    private ArrayList<View> mDrawingOrderedChildren;
    private final Runnable mEndScrollRunnable = new C01233();
    private int mExpectedAdapterCount;
    private long mFakeDragBeginTime;
    private boolean mFakeDragging;
    private boolean mFirstLayout = true;
    private float mFirstOffset = -3.4028235E38f;
    private int mFlingDistance;
    private int mGutterSize;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private OnPageChangeListener mInternalPageChangeListener;
    private boolean mIsBeingDragged;
    private boolean mIsScrollStarted;
    private boolean mIsUnableToDrag;
    private final ArrayList<ItemInfo> mItems = new ArrayList();
    private float mLastMotionX;
    private float mLastMotionY;
    private float mLastOffset = AutoScrollHelper.NO_MAX;
    private EdgeEffectCompat mLeftEdge;
    private Drawable mMarginDrawable;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private boolean mNeedCalculatePageOffsets = false;
    private PagerObserver mObserver;
    private int mOffscreenPageLimit = 1;
    private OnPageChangeListener mOnPageChangeListener;
    private List<OnPageChangeListener> mOnPageChangeListeners;
    private int mPageMargin;
    private PageTransformer mPageTransformer;
    private boolean mPopulatePending;
    private Parcelable mRestoredAdapterState = null;
    private ClassLoader mRestoredClassLoader = null;
    private int mRestoredCurItem = -1;
    private EdgeEffectCompat mRightEdge;
    private int mScrollState = 0;
    private Scroller mScroller;
    private boolean mScrollingCacheEnabled;
    private Method mSetChildrenDrawingOrderEnabled;
    private final ItemInfo mTempItem = new ItemInfo();
    private final Rect mTempRect = new Rect();
    private int mTopPageBounds;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    interface Decor {
    }

    interface OnAdapterChangeListener {
        void onAdapterChanged(PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) throws ;
    }

    public interface OnPageChangeListener {
        void onPageScrollStateChanged(int i) throws ;

        void onPageScrolled(int i, float f, int i2) throws ;

        void onPageSelected(int i) throws ;
    }

    static class C01211 implements Comparator<ItemInfo> {
        C01211() throws  {
        }

        public int compare(ItemInfo $r1, ItemInfo $r2) throws  {
            return $r1.position - $r2.position;
        }
    }

    static class C01222 implements Interpolator {
        C01222() throws  {
        }

        public float getInterpolation(float $f0) throws  {
            $f0 -= 1.0f;
            return (((($f0 * $f0) * $f0) * $f0) * $f0) + 1.0f;
        }
    }

    class C01233 implements Runnable {
        C01233() throws  {
        }

        public void run() throws  {
            ViewPager.this.setScrollState(0);
            ViewPager.this.populate();
        }
    }

    class C01244 implements OnApplyWindowInsetsListener {
        private final Rect mTempRect = new Rect();

        C01244() throws  {
        }

        public WindowInsetsCompat onApplyWindowInsets(View $r1, WindowInsetsCompat $r2) throws  {
            $r2 = ViewCompat.onApplyWindowInsets($r1, $r2);
            if ($r2.isConsumed()) {
                return $r2;
            }
            Rect $r3 = this.mTempRect;
            $r3.left = $r2.getSystemWindowInsetLeft();
            $r3.top = $r2.getSystemWindowInsetTop();
            $r3.right = $r2.getSystemWindowInsetRight();
            $r3.bottom = $r2.getSystemWindowInsetBottom();
            int $i1 = ViewPager.this.getChildCount();
            for (int $i0 = 0; $i0 < $i1; $i0++) {
                WindowInsetsCompat $r5 = ViewCompat.dispatchApplyWindowInsets(ViewPager.this.getChildAt($i0), $r2);
                $r3.left = Math.min($r5.getSystemWindowInsetLeft(), $r3.left);
                $r3.top = Math.min($r5.getSystemWindowInsetTop(), $r3.top);
                $r3.right = Math.min($r5.getSystemWindowInsetRight(), $r3.right);
                $r3.bottom = Math.min($r5.getSystemWindowInsetBottom(), $r3.bottom);
            }
            return $r2.replaceSystemWindowInsets($r3.left, $r3.top, $r3.right, $r3.bottom);
        }
    }

    static class ItemInfo {
        Object object;
        float offset;
        int position;
        boolean scrolling;
        float widthFactor;

        ItemInfo() throws  {
        }
    }

    public static class LayoutParams extends android.view.ViewGroup.LayoutParams {
        int childIndex;
        public int gravity;
        public boolean isDecor;
        boolean needsMeasure;
        int position;
        float widthFactor = 0.0f;

        public LayoutParams() throws  {
            super(-1, -1);
        }

        public LayoutParams(Context $r1, AttributeSet $r2) throws  {
            super($r1, $r2);
            TypedArray $r4 = $r1.obtainStyledAttributes($r2, ViewPager.LAYOUT_ATTRS);
            this.gravity = $r4.getInteger(0, 48);
            $r4.recycle();
        }
    }

    class MyAccessibilityDelegate extends AccessibilityDelegateCompat {
        MyAccessibilityDelegate() throws  {
        }

        public void onInitializeAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
            super.onInitializeAccessibilityEvent($r1, $r2);
            $r2.setClassName(ViewPager.class.getName());
            AccessibilityRecordCompat $r5 = AccessibilityEventCompat.asRecord($r2);
            $r5.setScrollable(canScroll());
            if ($r2.getEventType() == 4096 && ViewPager.this.mAdapter != null) {
                $r5.setItemCount(ViewPager.this.mAdapter.getCount());
                $r5.setFromIndex(ViewPager.this.mCurItem);
                $r5.setToIndex(ViewPager.this.mCurItem);
            }
        }

        public void onInitializeAccessibilityNodeInfo(View $r1, AccessibilityNodeInfoCompat $r2) throws  {
            super.onInitializeAccessibilityNodeInfo($r1, $r2);
            $r2.setClassName(ViewPager.class.getName());
            $r2.setScrollable(canScroll());
            if (ViewPager.this.canScrollHorizontally(1)) {
                $r2.addAction(4096);
            }
            if (ViewPager.this.canScrollHorizontally(-1)) {
                $r2.addAction(8192);
            }
        }

        public boolean performAccessibilityAction(View $r1, int $i0, Bundle $r2) throws  {
            if (super.performAccessibilityAction($r1, $i0, $r2)) {
                return true;
            }
            switch ($i0) {
                case 4096:
                    if (!ViewPager.this.canScrollHorizontally(1)) {
                        return false;
                    }
                    ViewPager.this.setCurrentItem(ViewPager.this.mCurItem + 1);
                    return true;
                case 8192:
                    if (!ViewPager.this.canScrollHorizontally(-1)) {
                        return false;
                    }
                    ViewPager.this.setCurrentItem(ViewPager.this.mCurItem - 1);
                    return true;
                default:
                    return false;
            }
        }

        private boolean canScroll() throws  {
            return ViewPager.this.mAdapter != null && ViewPager.this.mAdapter.getCount() > 1;
        }
    }

    public interface PageTransformer {
        void transformPage(View view, float f) throws ;
    }

    private class PagerObserver extends DataSetObserver {
        private PagerObserver() throws  {
        }

        public void onChanged() throws  {
            ViewPager.this.dataSetChanged();
        }

        public void onInvalidated() throws  {
            ViewPager.this.dataSetChanged();
        }
    }

    public static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new C01251());
        Parcelable adapterState;
        ClassLoader loader;
        int position;

        static class C01251 implements ParcelableCompatCreatorCallbacks<SavedState> {
            C01251() throws  {
            }

            public SavedState createFromParcel(Parcel $r1, ClassLoader $r2) throws  {
                return new SavedState($r1, $r2);
            }

            public SavedState[] newArray(int $i0) throws  {
                return new SavedState[$i0];
            }
        }

        public SavedState(Parcelable $r1) throws  {
            super($r1);
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            super.writeToParcel($r1, $i0);
            $r1.writeInt(this.position);
            $r1.writeParcelable(this.adapterState, $i0);
        }

        public String toString() throws  {
            return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.position + "}";
        }

        SavedState(Parcel $r1, ClassLoader $r2) throws  {
            super($r1);
            if ($r2 == null) {
                $r2 = getClass().getClassLoader();
            }
            this.position = $r1.readInt();
            this.adapterState = $r1.readParcelable($r2);
            this.loader = $r2;
        }
    }

    public static class SimpleOnPageChangeListener implements OnPageChangeListener {
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) throws  {
        }

        public void onPageSelected(int position) throws  {
        }

        public void onPageScrollStateChanged(int state) throws  {
        }
    }

    static class ViewPositionComparator implements Comparator<View> {
        ViewPositionComparator() throws  {
        }

        public int compare(View $r1, View $r2) throws  {
            LayoutParams $r4 = (LayoutParams) $r1.getLayoutParams();
            LayoutParams $r5 = (LayoutParams) $r2.getLayoutParams();
            if ($r4.isDecor != $r5.isDecor) {
                return $r4.isDecor ? 1 : -1;
            } else {
                return $r4.position - $r5.position;
            }
        }
    }

    public boolean arrowScroll(int r18) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:32:0x00a2
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
        r17 = this;
        r0 = r17;
        r2 = r0.findFocus();
        r3 = r2;
        r0 = r17;
        if (r2 != r0) goto L_0x0055;
    L_0x000b:
        r3 = 0;
    L_0x000c:
        r4 = 0;
        r5 = android.view.FocusFinder.getInstance();
        r0 = r17;
        r1 = r18;
        r2 = r5.findNextFocus(r0, r3, r1);
        if (r2 == 0) goto L_0x0109;
    L_0x001b:
        if (r2 == r3) goto L_0x0109;
    L_0x001d:
        r6 = 17;
        r0 = r18;
        if (r0 != r6) goto L_0x00db;
    L_0x0023:
        r0 = r17;
        r7 = r0.mTempRect;
        r0 = r17;
        r7 = r0.getChildRectInPagerCoordinates(r7, r2);
        r8 = r7.left;
        r0 = r17;
        r7 = r0.mTempRect;
        r0 = r17;
        r7 = r0.getChildRectInPagerCoordinates(r7, r3);
        r9 = r7.left;
        if (r3 == 0) goto L_0x00d6;
    L_0x003d:
        if (r8 < r9) goto L_0x00d6;
    L_0x003f:
        r0 = r17;
        r4 = r0.pageLeft();
    L_0x0045:
        if (r4 == 0) goto L_0x012d;
    L_0x0047:
        r0 = r18;
        r18 = android.view.SoundEffectConstants.getContantForFocusDirection(r0);
        r0 = r17;
        r1 = r18;
        r0.playSoundEffect(r1);
        return r4;
    L_0x0055:
        if (r2 == 0) goto L_0x000c;
    L_0x0057:
        r4 = 0;
        r10 = r2.getParent();
    L_0x005c:
        r11 = r10 instanceof android.view.ViewGroup;
        if (r11 == 0) goto L_0x0069;
    L_0x0060:
        goto L_0x0064;
    L_0x0061:
        goto L_0x000c;
    L_0x0064:
        r0 = r17;
        if (r10 != r0) goto L_0x00a7;
    L_0x0068:
        r4 = 1;
    L_0x0069:
        if (r4 != 0) goto L_0x000c;
    L_0x006b:
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r13 = r2.getClass();
        r14 = r13.getSimpleName();
        r12.append(r14);
        r10 = r2.getParent();
    L_0x007f:
        r4 = r10 instanceof android.view.ViewGroup;
        if (r4 == 0) goto L_0x00b0;
    L_0x0083:
        goto L_0x0087;
    L_0x0084:
        goto L_0x0045;
    L_0x0087:
        r16 = " => ";
        r0 = r16;
        r15 = r12.append(r0);
        r13 = r10.getClass();
        r14 = r13.getSimpleName();
        r15.append(r14);
        r10 = r10.getParent();
        goto L_0x00a6;
    L_0x009f:
        goto L_0x0045;
        goto L_0x00a6;
    L_0x00a3:
        goto L_0x0045;
    L_0x00a6:
        goto L_0x007f;
    L_0x00a7:
        r10 = r10.getParent();
        goto L_0x005c;
        goto L_0x00b0;
    L_0x00ad:
        goto L_0x0045;
    L_0x00b0:
        r15 = new java.lang.StringBuilder;
        r15.<init>();
        goto L_0x00b9;
    L_0x00b6:
        goto L_0x0045;
    L_0x00b9:
        r16 = "arrowScroll tried to find focus based on non-child current focused view ";
        r0 = r16;
        r15 = r15.append(r0);
        r14 = r12.toString();
        r12 = r15.append(r14);
        r14 = r12.toString();
        r16 = "ViewPager";
        r0 = r16;
        android.util.Log.e(r0, r14);
        r3 = 0;
        goto L_0x0061;
    L_0x00d6:
        r4 = r2.requestFocus();
        goto L_0x0084;
    L_0x00db:
        r6 = 66;
        r0 = r18;
        if (r0 != r6) goto L_0x0045;
    L_0x00e1:
        r0 = r17;
        r7 = r0.mTempRect;
        r0 = r17;
        r7 = r0.getChildRectInPagerCoordinates(r7, r2);
        r8 = r7.left;
        r0 = r17;
        r7 = r0.mTempRect;
        r0 = r17;
        r7 = r0.getChildRectInPagerCoordinates(r7, r3);
        r9 = r7.left;
        if (r3 == 0) goto L_0x0104;
    L_0x00fb:
        if (r8 > r9) goto L_0x0104;
    L_0x00fd:
        r0 = r17;
        r4 = r0.pageRight();
        goto L_0x009f;
    L_0x0104:
        r4 = r2.requestFocus();
        goto L_0x00a3;
    L_0x0109:
        r6 = 17;
        r0 = r18;
        if (r0 == r6) goto L_0x0114;
    L_0x010f:
        r6 = 1;
        r0 = r18;
        if (r0 != r6) goto L_0x011b;
    L_0x0114:
        r0 = r17;
        r4 = r0.pageLeft();
        goto L_0x00ad;
    L_0x011b:
        r6 = 66;
        r0 = r18;
        if (r0 == r6) goto L_0x0126;
    L_0x0121:
        r6 = 2;
        r0 = r18;
        if (r0 != r6) goto L_0x0045;
    L_0x0126:
        r0 = r17;
        r4 = r0.pageRight();
        goto L_0x00b6;
    L_0x012d:
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.arrowScroll(int):boolean");
    }

    public boolean onInterceptTouchEvent(android.view.MotionEvent r22) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:61:0x01c3 in {5, 10, 14, 16, 19, 21, 29, 33, 40, 41, 47, 50, 56, 59, 62} preds:[]
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
        r21 = this;
        r0 = r22;
        r6 = r0.getAction();
        r6 = r6 & 255;
        r7 = 3;
        if (r6 == r7) goto L_0x000e;
    L_0x000b:
        r7 = 1;
        if (r6 != r7) goto L_0x0015;
    L_0x000e:
        r0 = r21;
        r0.resetTouch();
        r7 = 0;
        return r7;
    L_0x0015:
        if (r6 == 0) goto L_0x0027;
    L_0x0017:
        r0 = r21;
        r8 = r0.mIsBeingDragged;
        if (r8 == 0) goto L_0x001f;
    L_0x001d:
        r7 = 1;
        return r7;
    L_0x001f:
        r0 = r21;
        r8 = r0.mIsUnableToDrag;
        if (r8 == 0) goto L_0x0027;
    L_0x0025:
        r7 = 0;
        return r7;
    L_0x0027:
        switch(r6) {
            case 0: goto L_0x0126;
            case 2: goto L_0x0047;
            case 6: goto L_0x01c7;
            default: goto L_0x002a;
        };
    L_0x002a:
        goto L_0x002b;
    L_0x002b:
        r0 = r21;
        r9 = r0.mVelocityTracker;
        if (r9 != 0) goto L_0x0039;
    L_0x0031:
        r9 = android.view.VelocityTracker.obtain();
        r0 = r21;
        r0.mVelocityTracker = r9;
    L_0x0039:
        r0 = r21;
        r9 = r0.mVelocityTracker;
        r0 = r22;
        r9.addMovement(r0);
        r0 = r21;
        r8 = r0.mIsBeingDragged;
        return r8;
    L_0x0047:
        r0 = r21;
        r6 = r0.mActivePointerId;
        r7 = -1;
        if (r6 == r7) goto L_0x002b;
    L_0x004e:
        r0 = r22;
        r6 = android.support.v4.view.MotionEventCompat.findPointerIndex(r0, r6);
        r0 = r22;
        r10 = android.support.v4.view.MotionEventCompat.getX(r0, r6);
        r0 = r21;
        r11 = r0.mLastMotionX;
        r12 = r10 - r11;
        r13 = java.lang.Math.abs(r12);
        r0 = r22;
        r11 = android.support.v4.view.MotionEventCompat.getY(r0, r6);
        r0 = r21;
        r14 = r0.mInitialMotionY;
        r14 = r11 - r14;
        r14 = java.lang.Math.abs(r14);
        r16 = 0;
        r15 = (r12 > r16 ? 1 : (r12 == r16 ? 0 : -1));
        if (r15 == 0) goto L_0x00b5;
    L_0x007a:
        r0 = r21;
        r0 = r0.mLastMotionX;
        r17 = r0;
        r0 = r21;
        r1 = r17;
        r8 = r0.isGutterDrag(r1, r12);
        if (r8 != 0) goto L_0x00b5;
    L_0x008a:
        r6 = (int) r12;
        r0 = (int) r10;
        r18 = r0;
        r0 = (int) r11;
        r19 = r0;
        goto L_0x0095;
    L_0x0092:
        goto L_0x002b;
    L_0x0095:
        r7 = 0;
        r0 = r21;
        r1 = r21;
        r2 = r7;
        r3 = r6;
        r4 = r18;
        r5 = r19;
        r8 = r0.canScroll(r1, r2, r3, r4, r5);
        if (r8 == 0) goto L_0x00b5;
    L_0x00a6:
        r0 = r21;
        r0.mLastMotionX = r10;
        r0 = r21;
        r0.mLastMotionY = r11;
        r7 = 1;
        r0 = r21;
        r0.mIsUnableToDrag = r7;
        r7 = 0;
        return r7;
    L_0x00b5:
        r0 = r21;
        r6 = r0.mTouchSlop;
        r0 = (float) r6;
        r17 = r0;
        r15 = (r13 > r17 ? 1 : (r13 == r17 ? 0 : -1));
        if (r15 <= 0) goto L_0x0117;
    L_0x00c0:
        r16 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r13 = r16 * r13;
        r15 = (r13 > r14 ? 1 : (r13 == r14 ? 0 : -1));
        if (r15 <= 0) goto L_0x0117;
    L_0x00c9:
        r7 = 1;
        r0 = r21;
        r0.mIsBeingDragged = r7;
        r7 = 1;
        r0 = r21;
        r0.requestParentDisallowInterceptTouchEvent(r7);
        r7 = 1;
        r0 = r21;
        r0.setScrollState(r7);
        r16 = 0;
        r15 = (r12 > r16 ? 1 : (r12 == r16 ? 0 : -1));
        if (r15 <= 0) goto L_0x010c;
    L_0x00e0:
        r0 = r21;
        r12 = r0.mInitialMotionX;
        r0 = r21;
        r6 = r0.mTouchSlop;
        r13 = (float) r6;
        r12 = r12 + r13;
    L_0x00ea:
        r0 = r21;
        r0.mLastMotionX = r12;
        r0 = r21;
        r0.mLastMotionY = r11;
        r7 = 1;
        r0 = r21;
        r0.setScrollingCacheEnabled(r7);
    L_0x00f8:
        r0 = r21;
        r8 = r0.mIsBeingDragged;
        if (r8 == 0) goto L_0x002b;
    L_0x00fe:
        r0 = r21;
        r8 = r0.performDrag(r10);
        if (r8 == 0) goto L_0x002b;
    L_0x0106:
        r0 = r21;
        android.support.v4.view.ViewCompat.postInvalidateOnAnimation(r0);
        goto L_0x0092;
    L_0x010c:
        r0 = r21;
        r12 = r0.mInitialMotionX;
        r0 = r21;
        r6 = r0.mTouchSlop;
        r13 = (float) r6;
        r12 = r12 - r13;
        goto L_0x00ea;
    L_0x0117:
        r0 = r21;
        r6 = r0.mTouchSlop;
        r11 = (float) r6;
        r15 = (r14 > r11 ? 1 : (r14 == r11 ? 0 : -1));
        if (r15 <= 0) goto L_0x00f8;
    L_0x0120:
        r7 = 1;
        r0 = r21;
        r0.mIsUnableToDrag = r7;
        goto L_0x00f8;
    L_0x0126:
        r0 = r22;
        r10 = r0.getX();
        r0 = r21;
        r0.mInitialMotionX = r10;
        r0 = r21;
        r0.mLastMotionX = r10;
        r0 = r22;
        r10 = r0.getY();
        r0 = r21;
        r0.mInitialMotionY = r10;
        r0 = r21;
        r0.mLastMotionY = r10;
        r7 = 0;
        r0 = r22;
        r6 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r7);
        r0 = r21;
        r0.mActivePointerId = r6;
        r7 = 0;
        r0 = r21;
        r0.mIsUnableToDrag = r7;
        r7 = 1;
        r0 = r21;
        r0.mIsScrollStarted = r7;
        r0 = r21;
        r0 = r0.mScroller;
        r20 = r0;
        r0.computeScrollOffset();
        r0 = r21;
        r6 = r0.mScrollState;
        r7 = 2;
        if (r6 != r7) goto L_0x01b3;
    L_0x0167:
        r0 = r21;
        r0 = r0.mScroller;
        r20 = r0;
        r6 = r0.getFinalX();
        r0 = r21;
        r0 = r0.mScroller;
        r20 = r0;
        r18 = r0.getCurrX();
        r0 = r18;
        r6 = r6 - r0;
        r6 = java.lang.Math.abs(r6);
        r0 = r21;
        r0 = r0.mCloseEnough;
        r18 = r0;
        if (r6 <= r0) goto L_0x01b3;
    L_0x018a:
        r0 = r21;
        r0 = r0.mScroller;
        r20 = r0;
        r0.abortAnimation();
        r7 = 0;
        r0 = r21;
        r0.mPopulatePending = r7;
        r0 = r21;
        r0.populate();
        r7 = 1;
        r0 = r21;
        r0.mIsBeingDragged = r7;
        r7 = 1;
        r0 = r21;
        r0.requestParentDisallowInterceptTouchEvent(r7);
        goto L_0x01ac;
    L_0x01a9:
        goto L_0x002b;
    L_0x01ac:
        r7 = 1;
        r0 = r21;
        r0.setScrollState(r7);
        goto L_0x01a9;
    L_0x01b3:
        r7 = 0;
        r0 = r21;
        r0.completeScroll(r7);
        goto L_0x01bd;
    L_0x01ba:
        goto L_0x002b;
    L_0x01bd:
        r7 = 0;
        r0 = r21;
        r0.mIsBeingDragged = r7;
        goto L_0x01ba;
        goto L_0x01c7;
    L_0x01c4:
        goto L_0x002b;
    L_0x01c7:
        r0 = r21;
        r1 = r22;
        r0.onSecondaryPointerUp(r1);
        goto L_0x01c4;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    void populate(int r46) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:77:0x0230
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
        r45 = this;
        r3 = 0;
        r0 = r45;
        r4 = r0.mCurItem;
        r0 = r46;
        if (r4 == r0) goto L_0x0019;
    L_0x0009:
        r0 = r45;
        r4 = r0.mCurItem;
        r0 = r45;
        r3 = r0.infoForPosition(r4);
        r0 = r46;
        r1 = r45;
        r1.mCurItem = r0;
    L_0x0019:
        r0 = r45;
        r5 = r0.mAdapter;
        if (r5 != 0) goto L_0x0025;
    L_0x001f:
        r0 = r45;
        r0.sortChildDrawingOrder();
        return;
    L_0x0025:
        r0 = r45;
        r6 = r0.mPopulatePending;
        if (r6 == 0) goto L_0x0031;
    L_0x002b:
        r0 = r45;
        r0.sortChildDrawingOrder();
        return;
    L_0x0031:
        r0 = r45;
        r7 = r0.getWindowToken();
        if (r7 == 0) goto L_0x0473;
    L_0x0039:
        r0 = r45;
        r5 = r0.mAdapter;
        r0 = r45;
        r5.startUpdate(r0);
        r0 = r45;
        r4 = r0.mOffscreenPageLimit;
        r0 = r45;
        r0 = r0.mCurItem;
        r46 = r0;
        r0 = r0 - r4;
        r46 = r0;
        r9 = 0;
        r0 = r46;
        r8 = java.lang.Math.max(r9, r0);
        r0 = r45;
        r5 = r0.mAdapter;
        r46 = r5.getCount();
        r10 = r46 + -1;
        r0 = r45;
        r11 = r0.mCurItem;
        r4 = r11 + r4;
        r4 = java.lang.Math.min(r10, r4);
        r0 = r45;
        r10 = r0.mExpectedAdapterCount;
        r0 = r46;
        if (r0 == r10) goto L_0x00f1;
    L_0x0072:
        r0 = r45;	 Catch:{ NotFoundException -> 0x00e5 }
        r12 = r0.getResources();	 Catch:{ NotFoundException -> 0x00e5 }
        r0 = r45;	 Catch:{ NotFoundException -> 0x00e5 }
        r4 = r0.getId();	 Catch:{ NotFoundException -> 0x00e5 }
        r13 = r12.getResourceName(r4);	 Catch:{ NotFoundException -> 0x00e5 }
    L_0x0082:
        r14 = new java.lang.IllegalStateException;
        r15 = new java.lang.StringBuilder;
        r15.<init>();
        r16 = "The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: ";
        r0 = r16;
        r15 = r15.append(r0);
        r0 = r45;
        r4 = r0.mExpectedAdapterCount;
        r15 = r15.append(r4);
        r16 = ", found: ";
        r0 = r16;
        r15 = r15.append(r0);
        r0 = r46;
        r15 = r15.append(r0);
        r16 = " Pager id: ";
        r0 = r16;
        r15 = r15.append(r0);
        r15 = r15.append(r13);
        r16 = " Pager class: ";
        r0 = r16;
        r15 = r15.append(r0);
        r0 = r45;
        r17 = r0.getClass();
        r0 = r17;
        r15 = r15.append(r0);
        r16 = " Problematic adapter: ";
        r0 = r16;
        r15 = r15.append(r0);
        r0 = r45;
        r5 = r0.mAdapter;
        r17 = r5.getClass();
        r0 = r17;
        r15 = r15.append(r0);
        r13 = r15.toString();
        r14.<init>(r13);
        throw r14;
    L_0x00e5:
        r18 = move-exception;
        r0 = r45;
        r4 = r0.getId();
        r13 = java.lang.Integer.toHexString(r4);
        goto L_0x0082;
    L_0x00f1:
        r19 = 0;
        r10 = 0;
    L_0x00f4:
        r0 = r45;
        r0 = r0.mItems;
        r20 = r0;
        r11 = r0.size();
        if (r10 >= r11) goto L_0x012a;
    L_0x0100:
        r0 = r45;
        r0 = r0.mItems;
        r20 = r0;
        r21 = r0.get(r10);
        r23 = r21;
        r23 = (android.support.v4.view.ViewPager.ItemInfo) r23;
        r22 = r23;
        r0 = r22;
        r11 = r0.position;
        r0 = r45;
        r0 = r0.mCurItem;
        r24 = r0;
        if (r11 < r0) goto L_0x0234;
    L_0x011c:
        r0 = r22;
        r11 = r0.position;
        r0 = r45;
        r0 = r0.mCurItem;
        r24 = r0;
        if (r11 != r0) goto L_0x012a;
    L_0x0128:
        r19 = r22;
    L_0x012a:
        if (r19 != 0) goto L_0x0138;
    L_0x012c:
        if (r46 <= 0) goto L_0x0138;
    L_0x012e:
        r0 = r45;
        r11 = r0.mCurItem;
        r0 = r45;
        r19 = r0.addNewItem(r11, r10);
    L_0x0138:
        if (r19 == 0) goto L_0x01ba;
    L_0x013a:
        r25 = 0;
        r24 = r10 + -1;
        if (r24 < 0) goto L_0x0237;
    L_0x0140:
        r0 = r45;
        r0 = r0.mItems;
        r20 = r0;
        r1 = r24;
        r21 = r0.get(r1);
        r26 = r21;
        r26 = (android.support.v4.view.ViewPager.ItemInfo) r26;
        r22 = r26;
    L_0x0152:
        r0 = r45;
        r11 = r0.getClientWidth();
        if (r11 > 0) goto L_0x023a;
    L_0x015a:
        r27 = 0;
    L_0x015c:
        r0 = r45;
        r0 = r0.mCurItem;
        r28 = r0;
        r28 = r28 + -1;
    L_0x0164:
        if (r28 < 0) goto L_0x0170;
    L_0x0166:
        r29 = (r25 > r27 ? 1 : (r25 == r27 ? 0 : -1));
        if (r29 < 0) goto L_0x02b8;
    L_0x016a:
        r0 = r28;
        if (r0 >= r8) goto L_0x02b8;
    L_0x016e:
        if (r22 != 0) goto L_0x0266;
    L_0x0170:
        r0 = r19;
        r0 = r0.widthFactor;
        r25 = r0;
        r8 = r10 + 1;
        r30 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r29 = (r25 > r30 ? 1 : (r25 == r30 ? 0 : -1));
        if (r29 >= 0) goto L_0x01b3;
    L_0x017f:
        r0 = r45;
        r0 = r0.mItems;
        r20 = r0;
        r24 = r0.size();
        r0 = r24;
        if (r8 >= r0) goto L_0x0324;
    L_0x018d:
        r0 = r45;
        r0 = r0.mItems;
        r20 = r0;
        r21 = r0.get(r8);
        r31 = r21;
        r31 = (android.support.v4.view.ViewPager.ItemInfo) r31;
        r22 = r31;
    L_0x019d:
        if (r11 > 0) goto L_0x0327;
    L_0x019f:
        r27 = 0;
    L_0x01a1:
        r0 = r45;
        r11 = r0.mCurItem;
        r11 = r11 + 1;
    L_0x01a7:
        r0 = r46;
        if (r11 >= r0) goto L_0x01b3;
    L_0x01ab:
        r29 = (r25 > r27 ? 1 : (r25 == r27 ? 0 : -1));
        if (r29 < 0) goto L_0x039b;
    L_0x01af:
        if (r11 <= r4) goto L_0x039b;
    L_0x01b1:
        if (r22 != 0) goto L_0x034b;
    L_0x01b3:
        r0 = r45;
        r1 = r19;
        r0.calculatePageOffsets(r1, r10, r3);
    L_0x01ba:
        r0 = r45;
        r5 = r0.mAdapter;
        r0 = r45;
        r0 = r0.mCurItem;
        r46 = r0;
        if (r19 == 0) goto L_0x0411;
    L_0x01c6:
        goto L_0x01ca;
    L_0x01c7:
        goto L_0x0152;
    L_0x01ca:
        r0 = r19;
        r0 = r0.object;
        r21 = r0;
    L_0x01d0:
        r0 = r45;
        r1 = r46;
        r2 = r21;
        r5.setPrimaryItem(r0, r1, r2);
        r0 = r45;
        r5 = r0.mAdapter;
        r0 = r45;
        r5.finishUpdate(r0);
        r0 = r45;
        r46 = r0.getChildCount();
        r4 = 0;
    L_0x01e9:
        r0 = r46;
        if (r4 >= r0) goto L_0x0414;
    L_0x01ed:
        r0 = r45;
        r32 = r0.getChildAt(r4);
        r0 = r32;
        r33 = r0.getLayoutParams();
        r35 = r33;
        r35 = (android.support.v4.view.ViewPager.LayoutParams) r35;
        r34 = r35;
        r0 = r34;
        r0.childIndex = r4;
        r0 = r34;
        r6 = r0.isDecor;
        if (r6 != 0) goto L_0x022d;
    L_0x0209:
        r0 = r34;
        r0 = r0.widthFactor;
        r25 = r0;
        r30 = 0;
        r29 = (r25 > r30 ? 1 : (r25 == r30 ? 0 : -1));
        if (r29 != 0) goto L_0x022d;
    L_0x0215:
        r0 = r45;
        r1 = r32;
        r3 = r0.infoForChild(r1);
        if (r3 == 0) goto L_0x022d;
    L_0x021f:
        r0 = r3.widthFactor;
        r25 = r0;
        r1 = r34;
        r1.widthFactor = r0;
        r10 = r3.position;
        r0 = r34;
        r0.position = r10;
    L_0x022d:
        r4 = r4 + 1;
        goto L_0x01e9;
        goto L_0x0234;
    L_0x0231:
        goto L_0x00f4;
    L_0x0234:
        r10 = r10 + 1;
        goto L_0x0231;
    L_0x0237:
        r22 = 0;
        goto L_0x01c7;
    L_0x023a:
        r0 = r19;
        r0 = r0.widthFactor;
        r27 = r0;
        r30 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r27 = r30 - r27;
        r0 = r45;
        r28 = r0.getPaddingLeft();
        r0 = r28;
        r0 = (float) r0;
        r36 = r0;
        r0 = (float) r11;
        r37 = r0;
        r0 = r36;
        r1 = r37;
        r0 = r0 / r1;
        r36 = r0;
        goto L_0x025e;
    L_0x025b:
        goto L_0x015c;
    L_0x025e:
        r0 = r27;
        r1 = r36;
        r0 = r0 + r1;
        r27 = r0;
        goto L_0x025b;
    L_0x0266:
        r0 = r22;
        r0 = r0.position;
        r38 = r0;
        r0 = r28;
        r1 = r38;
        if (r0 != r1) goto L_0x02b2;
    L_0x0272:
        r0 = r22;
        r6 = r0.scrolling;
        if (r6 != 0) goto L_0x02b2;
    L_0x0278:
        r0 = r45;
        r0 = r0.mItems;
        r20 = r0;
        r1 = r24;
        r0.remove(r1);
        r0 = r45;
        r5 = r0.mAdapter;
        r0 = r22;
        r0 = r0.object;
        r21 = r0;
        r0 = r45;
        r1 = r28;
        r2 = r21;
        r5.destroyItem(r0, r1, r2);
        r24 = r24 + -1;
        r10 = r10 + -1;
        if (r24 < 0) goto L_0x02b5;
    L_0x029c:
        r0 = r45;
        r0 = r0.mItems;
        r20 = r0;
        r1 = r24;
        r21 = r0.get(r1);
        r39 = r21;
        r39 = (android.support.v4.view.ViewPager.ItemInfo) r39;
        r22 = r39;
        goto L_0x02b2;
    L_0x02af:
        goto L_0x0164;
    L_0x02b2:
        r28 = r28 + -1;
        goto L_0x02af;
    L_0x02b5:
        r22 = 0;
        goto L_0x02b2;
    L_0x02b8:
        if (r22 == 0) goto L_0x02ed;
    L_0x02ba:
        r0 = r22;
        r0 = r0.position;
        r38 = r0;
        r0 = r28;
        r1 = r38;
        if (r0 != r1) goto L_0x02ed;
    L_0x02c6:
        r0 = r22;
        r0 = r0.widthFactor;
        r36 = r0;
        r0 = r25;
        r1 = r36;
        r0 = r0 + r1;
        r25 = r0;
        r24 = r24 + -1;
        if (r24 < 0) goto L_0x02ea;
    L_0x02d7:
        r0 = r45;
        r0 = r0.mItems;
        r20 = r0;
        r1 = r24;
        r21 = r0.get(r1);
        r40 = r21;
        r40 = (android.support.v4.view.ViewPager.ItemInfo) r40;
        r22 = r40;
    L_0x02e9:
        goto L_0x02b2;
    L_0x02ea:
        r22 = 0;
        goto L_0x02e9;
    L_0x02ed:
        r38 = r24 + 1;
        r0 = r45;
        r1 = r28;
        r2 = r38;
        r22 = r0.addNewItem(r1, r2);
        r0 = r22;
        r0 = r0.widthFactor;
        r36 = r0;
        r0 = r25;
        r1 = r36;
        r0 = r0 + r1;
        r25 = r0;
        r10 = r10 + 1;
        if (r24 < 0) goto L_0x031d;
    L_0x030a:
        r0 = r45;
        r0 = r0.mItems;
        r20 = r0;
        r1 = r24;
        r21 = r0.get(r1);
        r41 = r21;
        r41 = (android.support.v4.view.ViewPager.ItemInfo) r41;
        r22 = r41;
    L_0x031c:
        goto L_0x02b2;
    L_0x031d:
        r22 = 0;
        goto L_0x031c;
        goto L_0x0324;
    L_0x0321:
        goto L_0x019d;
    L_0x0324:
        r22 = 0;
        goto L_0x0321;
    L_0x0327:
        r0 = r45;
        r24 = r0.getPaddingRight();
        r0 = r24;
        r0 = (float) r0;
        r27 = r0;
        r0 = (float) r11;
        r36 = r0;
        r0 = r27;
        r1 = r36;
        r0 = r0 / r1;
        r27 = r0;
        goto L_0x0340;
    L_0x033d:
        goto L_0x01a1;
    L_0x0340:
        r30 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r27;
        r1 = r30;
        r0 = r0 + r1;
        r27 = r0;
        goto L_0x033d;
    L_0x034b:
        r0 = r22;
        r0 = r0.position;
        r24 = r0;
        if (r11 != r0) goto L_0x0395;
    L_0x0353:
        r0 = r22;
        r6 = r0.scrolling;
        if (r6 != 0) goto L_0x0395;
    L_0x0359:
        r0 = r45;
        r0 = r0.mItems;
        r20 = r0;
        r0.remove(r8);
        r0 = r45;
        r5 = r0.mAdapter;
        r0 = r22;
        r0 = r0.object;
        r21 = r0;
        r0 = r45;
        r1 = r21;
        r5.destroyItem(r0, r11, r1);
        r0 = r45;
        r0 = r0.mItems;
        r20 = r0;
        r24 = r0.size();
        r0 = r24;
        if (r8 >= r0) goto L_0x0398;
    L_0x0381:
        r0 = r45;
        r0 = r0.mItems;
        r20 = r0;
        r21 = r0.get(r8);
        r42 = r21;
        r42 = (android.support.v4.view.ViewPager.ItemInfo) r42;
        r22 = r42;
        goto L_0x0395;
    L_0x0392:
        goto L_0x01a7;
    L_0x0395:
        r11 = r11 + 1;
        goto L_0x0392;
    L_0x0398:
        r22 = 0;
        goto L_0x0395;
    L_0x039b:
        if (r22 == 0) goto L_0x03d6;
    L_0x039d:
        r0 = r22;
        r0 = r0.position;
        r24 = r0;
        if (r11 != r0) goto L_0x03d6;
    L_0x03a5:
        r0 = r22;
        r0 = r0.widthFactor;
        r36 = r0;
        r0 = r25;
        r1 = r36;
        r0 = r0 + r1;
        r25 = r0;
        r8 = r8 + 1;
        r0 = r45;
        r0 = r0.mItems;
        r20 = r0;
        r24 = r0.size();
        r0 = r24;
        if (r8 >= r0) goto L_0x03d3;
    L_0x03c2:
        r0 = r45;
        r0 = r0.mItems;
        r20 = r0;
        r21 = r0.get(r8);
        r43 = r21;
        r43 = (android.support.v4.view.ViewPager.ItemInfo) r43;
        r22 = r43;
    L_0x03d2:
        goto L_0x0395;
    L_0x03d3:
        r22 = 0;
        goto L_0x03d2;
    L_0x03d6:
        r0 = r45;
        r22 = r0.addNewItem(r11, r8);
        r8 = r8 + 1;
        r0 = r22;
        r0 = r0.widthFactor;
        r36 = r0;
        r0 = r25;
        r1 = r36;
        r0 = r0 + r1;
        r25 = r0;
        r0 = r45;
        r0 = r0.mItems;
        r20 = r0;
        r24 = r0.size();
        r0 = r24;
        if (r8 >= r0) goto L_0x040a;
    L_0x03f9:
        r0 = r45;
        r0 = r0.mItems;
        r20 = r0;
        r21 = r0.get(r8);
        r44 = r21;
        r44 = (android.support.v4.view.ViewPager.ItemInfo) r44;
        r22 = r44;
    L_0x0409:
        goto L_0x0395;
    L_0x040a:
        r22 = 0;
        goto L_0x0409;
        goto L_0x0411;
    L_0x040e:
        goto L_0x01d0;
    L_0x0411:
        r21 = 0;
        goto L_0x040e;
    L_0x0414:
        r0 = r45;
        r0.sortChildDrawingOrder();
        r0 = r45;
        r6 = r0.hasFocus();
        if (r6 == 0) goto L_0x0474;
    L_0x0421:
        r0 = r45;
        r32 = r0.findFocus();
        if (r32 == 0) goto L_0x0471;
    L_0x0429:
        r0 = r45;
        r1 = r32;
        r3 = r0.infoForAnyChild(r1);
    L_0x0431:
        if (r3 == 0) goto L_0x043f;
    L_0x0433:
        r0 = r3.position;
        r46 = r0;
        r0 = r45;
        r4 = r0.mCurItem;
        r0 = r46;
        if (r0 == r4) goto L_0x0475;
    L_0x043f:
        r46 = 0;
    L_0x0441:
        r0 = r45;
        r4 = r0.getChildCount();
        r0 = r46;
        if (r0 >= r4) goto L_0x0476;
    L_0x044b:
        r0 = r45;
        r1 = r46;
        r32 = r0.getChildAt(r1);
        r0 = r45;
        r1 = r32;
        r3 = r0.infoForChild(r1);
        if (r3 == 0) goto L_0x046e;
    L_0x045d:
        r4 = r3.position;
        r0 = r45;
        r10 = r0.mCurItem;
        if (r4 != r10) goto L_0x046e;
    L_0x0465:
        r9 = 2;
        r0 = r32;
        r6 = r0.requestFocus(r9);
        if (r6 != 0) goto L_0x0477;
    L_0x046e:
        r46 = r46 + 1;
        goto L_0x0441;
    L_0x0471:
        r3 = 0;
        goto L_0x0431;
    L_0x0473:
        return;
    L_0x0474:
        return;
    L_0x0475:
        return;
    L_0x0476:
        return;
    L_0x0477:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.populate(int):void");
    }

    public ViewPager(Context $r1) throws  {
        super($r1);
        initViewPager();
    }

    public ViewPager(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        initViewPager();
    }

    void initViewPager() throws  {
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        Context $r2 = getContext();
        this.mScroller = new Scroller($r2, sInterpolator);
        ViewConfiguration $r4 = ViewConfiguration.get($r2);
        float $f0 = $r2.getResources().getDisplayMetrics().density;
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop($r4);
        this.mMinimumVelocity = (int) (400.0f * $f0);
        this.mMaximumVelocity = $r4.getScaledMaximumFlingVelocity();
        this.mLeftEdge = new EdgeEffectCompat($r2);
        this.mRightEdge = new EdgeEffectCompat($r2);
        this.mFlingDistance = (int) (25.0f * $f0);
        this.mCloseEnough = (int) (LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN * $f0);
        this.mDefaultGutterSize = (int) (16.0f * $f0);
        ViewCompat.setAccessibilityDelegate(this, new MyAccessibilityDelegate());
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        ViewCompat.setOnApplyWindowInsetsListener(this, new C01244());
    }

    protected void onDetachedFromWindow() throws  {
        removeCallbacks(this.mEndScrollRunnable);
        if (!(this.mScroller == null || this.mScroller.isFinished())) {
            this.mScroller.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

    private void setScrollState(int $i0) throws  {
        if (this.mScrollState != $i0) {
            this.mScrollState = $i0;
            if (this.mPageTransformer != null) {
                boolean $z0;
                if ($i0 != 0) {
                    $z0 = true;
                } else {
                    $z0 = false;
                }
                enableLayers($z0);
            }
            dispatchOnScrollStateChanged($i0);
        }
    }

    public void setAdapter(PagerAdapter $r1) throws  {
        if (this.mAdapter != null) {
            this.mAdapter.setViewPagerObserver(null);
            this.mAdapter.startUpdate((ViewGroup) this);
            for (int $i0 = 0; $i0 < this.mItems.size(); $i0++) {
                ItemInfo $r5 = (ItemInfo) this.mItems.get($i0);
                this.mAdapter.destroyItem((ViewGroup) this, $r5.position, $r5.object);
            }
            this.mAdapter.finishUpdate((ViewGroup) this);
            this.mItems.clear();
            removeNonDecorViews();
            this.mCurItem = 0;
            scrollTo(0, 0);
        }
        PagerAdapter $r2 = this.mAdapter;
        this.mAdapter = $r1;
        this.mExpectedAdapterCount = 0;
        if (this.mAdapter != null) {
            if (this.mObserver == null) {
                ViewPager viewPager = this;
                this.mObserver = new PagerObserver();
            }
            this.mAdapter.setViewPagerObserver(this.mObserver);
            this.mPopulatePending = false;
            boolean $z0 = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                PagerAdapter $r6 = this.mAdapter;
                Parcelable $r8 = this.mRestoredAdapterState;
                ClassLoader $r9 = this.mRestoredClassLoader;
                $r6.restoreState($r8, $r9);
                setCurrentItemInternal(this.mRestoredCurItem, false, true);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
                this.mRestoredClassLoader = null;
            } else if ($z0) {
                requestLayout();
            } else {
                populate();
            }
        }
        if (this.mAdapterChangeListener != null && $r2 != $r1) {
            OnAdapterChangeListener onAdapterChangeListener = this.mAdapterChangeListener;
            OnAdapterChangeListener $r10 = onAdapterChangeListener;
            onAdapterChangeListener.onAdapterChanged($r2, $r1);
        }
    }

    private void removeNonDecorViews() throws  {
        int $i0 = 0;
        while ($i0 < getChildCount()) {
            if (!((LayoutParams) getChildAt($i0).getLayoutParams()).isDecor) {
                removeViewAt($i0);
                $i0--;
            }
            $i0++;
        }
    }

    public PagerAdapter getAdapter() throws  {
        return this.mAdapter;
    }

    void setOnAdapterChangeListener(OnAdapterChangeListener $r1) throws  {
        this.mAdapterChangeListener = $r1;
    }

    private int getClientWidth() throws  {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    public void setCurrentItem(int $i0) throws  {
        boolean $z0;
        this.mPopulatePending = false;
        if (this.mFirstLayout) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        setCurrentItemInternal($i0, $z0, false);
    }

    public void setCurrentItem(int $i0, boolean $z0) throws  {
        this.mPopulatePending = false;
        setCurrentItemInternal($i0, $z0, false);
    }

    public int getCurrentItem() throws  {
        return this.mCurItem;
    }

    void setCurrentItemInternal(int $i0, boolean $z0, boolean $z1) throws  {
        setCurrentItemInternal($i0, $z0, $z1, 0);
    }

    void setCurrentItemInternal(int $i2, boolean $z0, boolean $z1, int $i0) throws  {
        boolean $z2 = true;
        if (this.mAdapter == null || this.mAdapter.getCount() <= 0) {
            setScrollingCacheEnabled(false);
        } else if ($z1 || this.mCurItem != $i2 || this.mItems.size() == 0) {
            if ($i2 < 0) {
                $i2 = 0;
            } else if ($i2 >= this.mAdapter.getCount()) {
                $i2 = this.mAdapter.getCount() - 1;
            }
            int $i1 = this.mOffscreenPageLimit;
            if ($i2 > this.mCurItem + $i1 || $i2 < this.mCurItem - $i1) {
                for ($i1 = 0; $i1 < this.mItems.size(); $i1++) {
                    ((ItemInfo) this.mItems.get($i1)).scrolling = true;
                }
            }
            if (this.mCurItem == $i2) {
                $z2 = false;
            }
            if (this.mFirstLayout) {
                this.mCurItem = $i2;
                if ($z2) {
                    dispatchOnPageSelected($i2);
                }
                requestLayout();
                return;
            }
            populate($i2);
            scrollToItem($i2, $z0, $i0, $z2);
        } else {
            setScrollingCacheEnabled(false);
        }
    }

    private void scrollToItem(int $i0, boolean $z0, int $i1, boolean $z1) throws  {
        ItemInfo $r1 = infoForPosition($i0);
        int $i2 = 0;
        if ($r1 != null) {
            $i2 = (int) (((float) getClientWidth()) * Math.max(this.mFirstOffset, Math.min($r1.offset, this.mLastOffset)));
        }
        if ($z0) {
            smoothScrollTo($i2, 0, $i1);
            if ($z1) {
                dispatchOnPageSelected($i0);
                return;
            }
            return;
        }
        if ($z1) {
            dispatchOnPageSelected($i0);
        }
        completeScroll(false);
        scrollTo($i2, 0);
        pageScrolled($i2);
    }

    @Deprecated
    public void setOnPageChangeListener(@Deprecated OnPageChangeListener $r1) throws  {
        this.mOnPageChangeListener = $r1;
    }

    public void addOnPageChangeListener(OnPageChangeListener $r1) throws  {
        if (this.mOnPageChangeListeners == null) {
            this.mOnPageChangeListeners = new ArrayList();
        }
        this.mOnPageChangeListeners.add($r1);
    }

    public void removeOnPageChangeListener(OnPageChangeListener $r1) throws  {
        if (this.mOnPageChangeListeners != null) {
            this.mOnPageChangeListeners.remove($r1);
        }
    }

    public void clearOnPageChangeListeners() throws  {
        if (this.mOnPageChangeListeners != null) {
            this.mOnPageChangeListeners.clear();
        }
    }

    public void setPageTransformer(boolean $z0, PageTransformer $r1) throws  {
        byte $b0 = (byte) 1;
        if (VERSION.SDK_INT >= 11) {
            boolean $z1;
            boolean $z2;
            if ($r1 != null) {
                $z1 = true;
            } else {
                $z1 = false;
            }
            if (this.mPageTransformer != null) {
                $z2 = true;
            } else {
                $z2 = false;
            }
            $z2 = $z1 != $z2;
            this.mPageTransformer = $r1;
            setChildrenDrawingOrderEnabledCompat($z1);
            if ($z1) {
                if ($z0) {
                    $b0 = (byte) 2;
                }
                this.mDrawingOrder = $b0;
            } else {
                this.mDrawingOrder = 0;
            }
            if ($z2) {
                populate();
            }
        }
    }

    void setChildrenDrawingOrderEnabledCompat(boolean $z0) throws  {
        if (VERSION.SDK_INT >= 7) {
            if (this.mSetChildrenDrawingOrderEnabled == null) {
                try {
                    this.mSetChildrenDrawingOrderEnabled = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", new Class[]{Boolean.TYPE});
                } catch (NoSuchMethodException $r7) {
                    Log.e(TAG, "Can't find setChildrenDrawingOrderEnabled", $r7);
                }
            }
            try {
                this.mSetChildrenDrawingOrderEnabled.invoke(this, new Object[]{Boolean.valueOf($z0)});
            } catch (Exception $r8) {
                Log.e(TAG, "Error changing children drawing order", $r8);
            }
        }
    }

    protected int getChildDrawingOrder(int $i0, int $i1) throws  {
        if (this.mDrawingOrder == 2) {
            $i0 = ($i0 - 1) - $i1;
        } else {
            $i0 = $i1;
        }
        return ((LayoutParams) ((View) this.mDrawingOrderedChildren.get($i0)).getLayoutParams()).childIndex;
    }

    OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener $r1) throws  {
        OnPageChangeListener r2 = this.mInternalPageChangeListener;
        this.mInternalPageChangeListener = $r1;
        return r2;
    }

    public int getOffscreenPageLimit() throws  {
        return this.mOffscreenPageLimit;
    }

    public void setOffscreenPageLimit(int $i0) throws  {
        if ($i0 < 1) {
            Log.w(TAG, "Requested offscreen page limit " + $i0 + " too small; defaulting to " + 1);
            $i0 = 1;
        }
        if ($i0 != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = $i0;
            populate();
        }
    }

    public void setPageMargin(int $i0) throws  {
        int $i1 = this.mPageMargin;
        this.mPageMargin = $i0;
        int $i2 = getWidth();
        recomputeScrollPosition($i2, $i2, $i0, $i1);
        requestLayout();
    }

    public int getPageMargin() throws  {
        return this.mPageMargin;
    }

    public void setPageMarginDrawable(Drawable $r1) throws  {
        boolean $z0;
        this.mMarginDrawable = $r1;
        if ($r1 != null) {
            refreshDrawableState();
        }
        if ($r1 == null) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        setWillNotDraw($z0);
        invalidate();
    }

    public void setPageMarginDrawable(@DrawableRes int $i0) throws  {
        setPageMarginDrawable(getContext().getResources().getDrawable($i0));
    }

    protected boolean verifyDrawable(Drawable $r1) throws  {
        return super.verifyDrawable($r1) || $r1 == this.mMarginDrawable;
    }

    protected void drawableStateChanged() throws  {
        super.drawableStateChanged();
        Drawable $r1 = this.mMarginDrawable;
        if ($r1 != null && $r1.isStateful()) {
            $r1.setState(getDrawableState());
        }
    }

    float distanceInfluenceForSnapDuration(float $f0) throws  {
        return (float) Math.sin((double) ((float) (((double) ($f0 - CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR)) * 0.4712389167638204d)));
    }

    void smoothScrollTo(int $i0, int $i1) throws  {
        smoothScrollTo($i0, $i1, 0);
    }

    void smoothScrollTo(int $i0, int $i1, int $i3) throws  {
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        int $i4;
        boolean $z0 = (this.mScroller == null || this.mScroller.isFinished()) ? false : true;
        if ($z0) {
            $i4 = this.mIsScrollStarted ? this.mScroller.getCurrX() : this.mScroller.getStartX();
            this.mScroller.abortAnimation();
            setScrollingCacheEnabled(false);
        } else {
            $i4 = getScrollX();
        }
        int $i5 = getScrollY();
        $i0 -= $i4;
        $i1 -= $i5;
        if ($i0 == 0 && $i1 == 0) {
            completeScroll(false);
            populate();
            setScrollState(0);
            return;
        }
        setScrollingCacheEnabled(true);
        setScrollState(2);
        int $i6 = getClientWidth();
        int $i2 = $i6 / 2;
        float $f1 = (float) $i6;
        $f1 = ((float) $i2) * distanceInfluenceForSnapDuration(Math.min(1.0f, (1.0f * ((float) Math.abs($i0))) / $f1));
        float f = $f1;
        float $f0 = ((float) $i2) + $f1;
        $i3 = Math.abs($i3);
        if ($i3 > 0) {
            $f1 = (float) $i3;
            $i3 = Math.round(1000.0f * Math.abs($f0 / $f1)) * 4;
        } else {
            f = ((float) $i6) * this.mAdapter.getPageWidth(this.mCurItem);
            $f0 = (float) Math.abs($i0);
            int $f2 = this.mPageMargin;
            $i3 = $f2;
            $i3 = (int) ((1.0f + ($f0 / (((float) $f2) + f))) * 100.0f);
        }
        $i3 = Math.min($i3, 600);
        this.mIsScrollStarted = false;
        this.mScroller.startScroll($i4, $i5, $i0, $i1, $i3);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    ItemInfo addNewItem(int $i0, int $i1) throws  {
        ItemInfo $r1 = new ItemInfo();
        $r1.position = $i0;
        $r1.object = this.mAdapter.instantiateItem((ViewGroup) this, $i0);
        $r1.widthFactor = this.mAdapter.getPageWidth($i0);
        if ($i1 < 0 || $i1 >= this.mItems.size()) {
            this.mItems.add($r1);
            return $r1;
        }
        this.mItems.add($i1, $r1);
        return $r1;
    }

    void dataSetChanged() throws  {
        int $i0 = this.mAdapter.getCount();
        this.mExpectedAdapterCount = $i0;
        boolean $z0 = this.mItems.size() < (this.mOffscreenPageLimit * 2) + 1 && this.mItems.size() < $i0;
        int $i2 = this.mCurItem;
        boolean $z1 = false;
        int $i1 = 0;
        while ($i1 < this.mItems.size()) {
            ItemInfo $r4 = (ItemInfo) this.mItems.get($i1);
            int $i3 = this.mAdapter.getItemPosition($r4.object);
            if ($i3 != -1) {
                if ($i3 == -2) {
                    this.mItems.remove($i1);
                    $i1--;
                    if (!$z1) {
                        this.mAdapter.startUpdate((ViewGroup) this);
                        $z1 = true;
                    }
                    this.mAdapter.destroyItem((ViewGroup) this, $r4.position, $r4.object);
                    $z0 = true;
                    if (this.mCurItem == $r4.position) {
                        $i2 = Math.max(0, Math.min(this.mCurItem, $i0 - 1));
                        $z0 = true;
                    }
                } else if ($r4.position != $i3) {
                    if ($r4.position == this.mCurItem) {
                        $i2 = $i3;
                    }
                    $r4.position = $i3;
                    $z0 = true;
                }
            }
            $i1++;
        }
        if ($z1) {
            this.mAdapter.finishUpdate((ViewGroup) this);
        }
        Collections.sort(this.mItems, COMPARATOR);
        if ($z0) {
            $i0 = getChildCount();
            for ($i1 = 0; $i1 < $i0; $i1++) {
                LayoutParams $r8 = (LayoutParams) getChildAt($i1).getLayoutParams();
                if (!$r8.isDecor) {
                    $r8.widthFactor = 0.0f;
                }
            }
            setCurrentItemInternal($i2, false, true);
            requestLayout();
        }
    }

    void populate() throws  {
        populate(this.mCurItem);
    }

    private void sortChildDrawingOrder() throws  {
        if (this.mDrawingOrder != 0) {
            if (this.mDrawingOrderedChildren == null) {
                this.mDrawingOrderedChildren = new ArrayList();
            } else {
                this.mDrawingOrderedChildren.clear();
            }
            int $i0 = getChildCount();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                this.mDrawingOrderedChildren.add(getChildAt($i1));
            }
            Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
        }
    }

    private void calculatePageOffsets(ItemInfo $r1, int $i0, ItemInfo $r2) throws  {
        float $f0;
        int $i1;
        float $f1;
        float $f2;
        int $i2 = this.mAdapter.getCount();
        int $i3 = getClientWidth();
        if ($i3 > 0) {
            $f0 = ((float) this.mPageMargin) / ((float) $i3);
        } else {
            $f0 = 0.0f;
        }
        if ($r2 != null) {
            $i1 = $r2.position;
            if ($i1 < $r1.position) {
                $i3 = 0;
                $f1 = ($r2.offset + $r2.widthFactor) + $f0;
                $i1++;
                while ($i1 <= $r1.position && $i3 < this.mItems.size()) {
                    $r2 = (ItemInfo) this.mItems.get($i3);
                    while ($i1 > $r2.position && $i3 < this.mItems.size() - 1) {
                        $i3++;
                        $r2 = (ItemInfo) this.mItems.get($i3);
                    }
                    while ($i1 < $r2.position) {
                        $f1 += this.mAdapter.getPageWidth($i1) + $f0;
                        $i1++;
                    }
                    $r2.offset = $f1;
                    $f1 += $r2.widthFactor + $f0;
                    $i1++;
                }
            } else if ($i1 > $r1.position) {
                $i3 = this.mItems.size() - 1;
                $f1 = $r2.offset;
                $i1--;
                while ($i1 >= $r1.position && $i3 >= 0) {
                    $r2 = (ItemInfo) this.mItems.get($i3);
                    while ($i1 < $r2.position && $i3 > 0) {
                        $i3--;
                        $r2 = (ItemInfo) this.mItems.get($i3);
                    }
                    while ($i1 > $r2.position) {
                        $f1 -= this.mAdapter.getPageWidth($i1) + $f0;
                        $i1--;
                    }
                    $f1 -= $r2.widthFactor + $f0;
                    $r2.offset = $f1;
                    $i1--;
                }
            }
        }
        $i3 = this.mItems.size();
        $f1 = $r1.offset;
        $i1 = $r1.position - 1;
        this.mFirstOffset = $r1.position == 0 ? $r1.offset : -3.4028235E38f;
        if ($r1.position == $i2 - 1) {
            $f2 = $r1.offset;
            float $f3 = $r1.widthFactor;
            $f2 = ($f2 + $f3) - 1.0f;
        } else {
            $f2 = AutoScrollHelper.NO_MAX;
        }
        this.mLastOffset = $f2;
        int $i4 = $i0 - 1;
        while ($i4 >= 0) {
            $r2 = (ItemInfo) this.mItems.get($i4);
            while (true) {
                int $i5 = $r2.position;
                if ($i1 <= $i5) {
                    break;
                }
                $f1 -= this.mAdapter.getPageWidth($i1) + $f0;
                $i1--;
            }
            $f1 -= $r2.widthFactor + $f0;
            $r2.offset = $f1;
            if ($r2.position == 0) {
                this.mFirstOffset = $f1;
            }
            $i4--;
            $i1--;
        }
        $f1 = ($r1.offset + $r1.widthFactor) + $f0;
        $i1 = $r1.position + 1;
        $i0++;
        while ($i0 < $i3) {
            $r1 = (ItemInfo) this.mItems.get($i0);
            while ($i1 < $r1.position) {
                $f1 += this.mAdapter.getPageWidth($i1) + $f0;
                $i1++;
            }
            if ($r1.position == $i2 - 1) {
                this.mLastOffset = ($r1.widthFactor + $f1) - 1.0f;
            }
            $r1.offset = $f1;
            $f1 += $r1.widthFactor + $f0;
            $i0++;
            $i1++;
        }
        this.mNeedCalculatePageOffsets = false;
    }

    public Parcelable onSaveInstanceState() throws  {
        SavedState $r1 = new SavedState(super.onSaveInstanceState());
        $r1.position = this.mCurItem;
        if (this.mAdapter == null) {
            return $r1;
        }
        $r1.adapterState = this.mAdapter.saveState();
        return $r1;
    }

    public void onRestoreInstanceState(Parcelable $r1) throws  {
        if ($r1 instanceof SavedState) {
            SavedState $r2 = (SavedState) $r1;
            super.onRestoreInstanceState($r2.getSuperState());
            if (this.mAdapter != null) {
                this.mAdapter.restoreState($r2.adapterState, $r2.loader);
                setCurrentItemInternal($r2.position, false, true);
                return;
            }
            this.mRestoredCurItem = $r2.position;
            this.mRestoredAdapterState = $r2.adapterState;
            this.mRestoredClassLoader = $r2.loader;
            return;
        }
        super.onRestoreInstanceState($r1);
    }

    public void addView(View $r1, int $i0, android.view.ViewGroup.LayoutParams $r2) throws  {
        if (!checkLayoutParams($r2)) {
            $r2 = generateLayoutParams($r2);
        }
        LayoutParams $r3 = (LayoutParams) $r2;
        $r3.isDecor |= $r1 instanceof Decor;
        if (!this.mInLayout) {
            super.addView($r1, $i0, $r2);
        } else if ($r3 == null || !$r3.isDecor) {
            $r3.needsMeasure = true;
            addViewInLayout($r1, $i0, $r2);
        } else {
            throw new IllegalStateException("Cannot add pager decor view during layout");
        }
    }

    public void removeView(View $r1) throws  {
        if (this.mInLayout) {
            removeViewInLayout($r1);
        } else {
            super.removeView($r1);
        }
    }

    ItemInfo infoForChild(View $r1) throws  {
        for (int $i0 = 0; $i0 < this.mItems.size(); $i0++) {
            ItemInfo $r4 = (ItemInfo) this.mItems.get($i0);
            if (this.mAdapter.isViewFromObject($r1, $r4.object)) {
                return $r4;
            }
        }
        return null;
    }

    ItemInfo infoForAnyChild(View $r1) throws  {
        while (true) {
            ViewPager $r2 = $r1.getParent();
            if ($r2 == this) {
                return infoForChild($r1);
            }
            if ($r2 != null && ($r2 instanceof View)) {
                $r1 = $r2;
            }
        }
        return null;
    }

    ItemInfo infoForPosition(int $i0) throws  {
        for (int $i1 = 0; $i1 < this.mItems.size(); $i1++) {
            ItemInfo $r3 = (ItemInfo) this.mItems.get($i1);
            if ($r3.position == $i0) {
                return $r3;
            }
        }
        return null;
    }

    protected void onAttachedToWindow() throws  {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        setMeasuredDimension(getDefaultSize(0, $i0), getDefaultSize(0, $i1));
        $i0 = getMeasuredWidth();
        this.mGutterSize = Math.min($i0 / 10, this.mDefaultGutterSize);
        $i0 = ($i0 - getPaddingLeft()) - getPaddingRight();
        $i1 = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        int $i4 = getChildCount();
        for (int $i5 = 0; $i5 < $i4; $i5++) {
            LayoutParams $r3;
            View $r1 = getChildAt($i5);
            if ($r1.getVisibility() != 8) {
                $r3 = (LayoutParams) $r1.getLayoutParams();
                if ($r3 != null && $r3.isDecor) {
                    boolean $z0;
                    boolean $z1;
                    int $i2 = $r3.gravity & 7;
                    int $i3 = $r3.gravity & 112;
                    int $i7 = Integer.MIN_VALUE;
                    int $i6 = Integer.MIN_VALUE;
                    if ($i3 == 48 || $i3 == 80) {
                        $z0 = true;
                    } else {
                        $z0 = false;
                    }
                    if ($i2 == 3 || $i2 == 5) {
                        $z1 = true;
                    } else {
                        $z1 = false;
                    }
                    if ($z0) {
                        $i7 = 1073741824;
                    } else if ($z1) {
                        $i6 = 1073741824;
                    }
                    $i3 = $i0;
                    $i2 = $i1;
                    if ($r3.width != -2) {
                        $i7 = 1073741824;
                        if ($r3.width != -1) {
                            $i3 = $r3.width;
                        }
                    }
                    if ($r3.height != -2) {
                        $i6 = 1073741824;
                        if ($r3.height != -1) {
                            $i2 = $r3.height;
                        }
                    }
                    $r1.measure(MeasureSpec.makeMeasureSpec($i3, $i7), MeasureSpec.makeMeasureSpec($i2, $i6));
                    if ($z0) {
                        $i1 -= $r1.getMeasuredHeight();
                    } else if ($z1) {
                        $i0 -= $r1.getMeasuredWidth();
                    }
                }
            }
        }
        this.mChildWidthMeasureSpec = MeasureSpec.makeMeasureSpec($i0, 1073741824);
        this.mChildHeightMeasureSpec = MeasureSpec.makeMeasureSpec($i1, 1073741824);
        this.mInLayout = true;
        populate();
        this.mInLayout = false;
        $i1 = getChildCount();
        for ($i4 = 0; $i4 < $i1; $i4++) {
            $r1 = getChildAt($i4);
            if ($r1.getVisibility() != 8) {
                $r3 = (LayoutParams) $r1.getLayoutParams();
                if ($r3 == null || !$r3.isDecor) {
                    float $f0 = ((float) $i0) * $r3.widthFactor;
                    float f = $f0;
                    $r1.measure(MeasureSpec.makeMeasureSpec((int) $f0, 1073741824), this.mChildHeightMeasureSpec);
                }
            }
        }
    }

    protected void onSizeChanged(int $i0, int $i1, int $i2, int $i3) throws  {
        super.onSizeChanged($i0, $i1, $i2, $i3);
        if ($i0 != $i2) {
            recomputeScrollPosition($i0, $i2, this.mPageMargin, this.mPageMargin);
        }
    }

    private void recomputeScrollPosition(int $i0, int $i1, int $i2, int $i3) throws  {
        if ($i1 <= 0 || this.mItems.isEmpty()) {
            ItemInfo $r3 = infoForPosition(this.mCurItem);
            $i0 = (int) (((float) (($i0 - getPaddingLeft()) - getPaddingRight())) * ($r3 != null ? Math.min($r3.offset, this.mLastOffset) : 0.0f));
            if ($i0 != getScrollX()) {
                completeScroll(false);
                scrollTo($i0, getScrollY());
            }
        } else if (this.mScroller.isFinished()) {
            scrollTo((int) (((float) ((($i0 - getPaddingLeft()) - getPaddingRight()) + $i2)) * (((float) getScrollX()) / ((float) ((($i1 - getPaddingLeft()) - getPaddingRight()) + $i3)))), getScrollY());
        } else {
            this.mScroller.setFinalX(getCurrentItem() * getClientWidth());
        }
    }

    protected void onLayout(boolean changed, int $i0, int $i1, int $i2, int $i3) throws  {
        int $i11;
        int $i6;
        int $i7 = getChildCount();
        int $i4 = $i2 - $i0;
        $i0 = $i3 - $i1;
        $i1 = getPaddingLeft();
        $i2 = getPaddingTop();
        int $i8 = getPaddingRight();
        int $i9 = getPaddingBottom();
        int $i10 = getScrollX();
        $i3 = 0;
        for ($i11 = 0; $i11 < $i7; $i11++) {
            LayoutParams $r3;
            int $i62;
            View $r1 = getChildAt($i11);
            if ($r1.getVisibility() != 8) {
                $r3 = (LayoutParams) $r1.getLayoutParams();
                if ($r3.isDecor) {
                    int $i5;
                    $i62 = $r3.gravity & 112;
                    switch ($r3.gravity & 7) {
                        case 1:
                            $i5 = Math.max(($i4 - $r1.getMeasuredWidth()) / 2, $i1);
                            break;
                        case 2:
                        case 4:
                            break;
                        case 3:
                            $i5 = $i1;
                            $i1 += $r1.getMeasuredWidth();
                            break;
                        case 5:
                            $i5 = ($i4 - $i8) - $r1.getMeasuredWidth();
                            $i8 += $r1.getMeasuredWidth();
                            break;
                        default:
                            break;
                    }
                    $i5 = $i1;
                    switch ($i62) {
                        case 16:
                            $i62 = Math.max(($i0 - $r1.getMeasuredHeight()) / 2, $i2);
                            break;
                        case 48:
                            $i62 = $i2;
                            $i2 += $r1.getMeasuredHeight();
                            break;
                        case 80:
                            $i62 = ($i0 - $i9) - $r1.getMeasuredHeight();
                            $i9 += $r1.getMeasuredHeight();
                            break;
                        default:
                            $i62 = $i2;
                            break;
                    }
                    $i5 += $i10;
                    $r1.layout($i5, $i62, $r1.getMeasuredWidth() + $i5, $r1.getMeasuredHeight() + $i62);
                    $i3++;
                }
            }
        }
        $i4 = ($i4 - $i1) - $i8;
        for ($i8 = 0; $i8 < $i7; $i8++) {
            $r1 = getChildAt($i8);
            if ($r1.getVisibility() != 8) {
                $r3 = (LayoutParams) $r1.getLayoutParams();
                if (!$r3.isDecor) {
                    ItemInfo $r4 = infoForChild($r1);
                    if ($r4 != null) {
                        float $f0 = ((float) $i4) * $r4.offset;
                        float f = $f0;
                        $i10 = $i1 + ((int) $f0);
                        $i11 = $i2;
                        if ($r3.needsMeasure) {
                            $r3.needsMeasure = false;
                            $f0 = ((float) $i4) * $r3.widthFactor;
                            f = $f0;
                            $r1.measure(MeasureSpec.makeMeasureSpec((int) $f0, 1073741824), MeasureSpec.makeMeasureSpec(($i0 - $i2) - $i9, 1073741824));
                        }
                        $i6 = $r1.getMeasuredHeight() + $i11;
                        $i62 = $i6;
                        $r1.layout($i10, $i11, $r1.getMeasuredWidth() + $i10, $i6);
                    }
                }
            }
        }
        this.mTopPageBounds = $i2;
        $i6 = $i0 - $i9;
        $i0 = $i6;
        this.mBottomPageBounds = $i6;
        this.mDecorChildCount = $i3;
        if (this.mFirstLayout) {
            scrollToItem(this.mCurItem, false, 0, false);
        }
        this.mFirstLayout = false;
    }

    public void computeScroll() throws  {
        this.mIsScrollStarted = true;
        if (this.mScroller.isFinished() || !this.mScroller.computeScrollOffset()) {
            completeScroll(true);
            return;
        }
        int $i0 = getScrollX();
        int $i1 = getScrollY();
        int $i2 = this.mScroller.getCurrX();
        int $i3 = this.mScroller.getCurrY();
        if (!($i0 == $i2 && $i1 == $i3)) {
            scrollTo($i2, $i3);
            if (!pageScrolled($i2)) {
                this.mScroller.abortAnimation();
                scrollTo(0, $i3);
            }
        }
        ViewCompat.postInvalidateOnAnimation(this);
    }

    private boolean pageScrolled(int $i0) throws  {
        if (this.mItems.size() != 0) {
            ItemInfo $r3 = infoForCurrentScrollPosition();
            int $i3 = getClientWidth();
            int $i2 = $i3 + this.mPageMargin;
            float $f0 = ((float) this.mPageMargin) / ((float) $i3);
            int $i1 = $r3.position;
            $f0 = ((((float) $i0) / ((float) $i3)) - $r3.offset) / ($r3.widthFactor + $f0);
            $i0 = (int) (((float) $i2) * $f0);
            this.mCalledSuper = false;
            onPageScrolled($i1, $f0, $i0);
            if (this.mCalledSuper) {
                return true;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        } else if (this.mFirstLayout) {
            return false;
        } else {
            this.mCalledSuper = false;
            onPageScrolled(0, 0.0f, 0);
            if (this.mCalledSuper) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
    }

    @CallSuper
    protected void onPageScrolled(int $i0, float $f0, int $i1) throws  {
        int $i3;
        View $r1;
        if (this.mDecorChildCount > 0) {
            $i3 = getScrollX();
            int $i4 = getPaddingLeft();
            int $i5 = getPaddingRight();
            int $i6 = getWidth();
            int $i7 = getChildCount();
            for (int $i8 = 0; $i8 < $i7; $i8++) {
                $r1 = getChildAt($i8);
                LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
                if ($r3.isDecor) {
                    int $i2;
                    switch ($r3.gravity & 7) {
                        case 1:
                            $i2 = Math.max(($i6 - $r1.getMeasuredWidth()) / 2, $i4);
                            break;
                        case 2:
                        case 4:
                            break;
                        case 3:
                            $i2 = $i4;
                            $i4 += $r1.getWidth();
                            break;
                        case 5:
                            $i2 = ($i6 - $i5) - $r1.getMeasuredWidth();
                            $i5 += $r1.getMeasuredWidth();
                            break;
                        default:
                            break;
                    }
                    $i2 = $i4;
                    $i2 = ($i2 + $i3) - $r1.getLeft();
                    if ($i2 != 0) {
                        $r1.offsetLeftAndRight($i2);
                    }
                }
            }
        }
        dispatchOnPageScrolled($i0, $f0, $i1);
        if (this.mPageTransformer != null) {
            $i0 = getScrollX();
            $i1 = getChildCount();
            for ($i3 = 0; $i3 < $i1; $i3++) {
                $r1 = getChildAt($i3);
                if (!((LayoutParams) $r1.getLayoutParams()).isDecor) {
                    $f0 = ((float) ($r1.getLeft() - $i0)) / ((float) getClientWidth());
                    PageTransformer pageTransformer = this.mPageTransformer;
                    PageTransformer $r4 = pageTransformer;
                    pageTransformer.transformPage($r1, $f0);
                }
            }
        }
        this.mCalledSuper = true;
    }

    private void dispatchOnPageScrolled(int $i0, float $f0, int $i1) throws  {
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrolled($i0, $f0, $i1);
        }
        if (this.mOnPageChangeListeners != null) {
            int $i3 = this.mOnPageChangeListeners.size();
            for (int $i2 = 0; $i2 < $i3; $i2++) {
                OnPageChangeListener $r1 = (OnPageChangeListener) this.mOnPageChangeListeners.get($i2);
                if ($r1 != null) {
                    $r1.onPageScrolled($i0, $f0, $i1);
                }
            }
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageScrolled($i0, $f0, $i1);
        }
    }

    private void dispatchOnPageSelected(int $i0) throws  {
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageSelected($i0);
        }
        if (this.mOnPageChangeListeners != null) {
            int $i2 = this.mOnPageChangeListeners.size();
            for (int $i1 = 0; $i1 < $i2; $i1++) {
                OnPageChangeListener $r1 = (OnPageChangeListener) this.mOnPageChangeListeners.get($i1);
                if ($r1 != null) {
                    $r1.onPageSelected($i0);
                }
            }
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageSelected($i0);
        }
    }

    private void dispatchOnScrollStateChanged(int $i0) throws  {
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrollStateChanged($i0);
        }
        if (this.mOnPageChangeListeners != null) {
            int $i2 = this.mOnPageChangeListeners.size();
            for (int $i1 = 0; $i1 < $i2; $i1++) {
                OnPageChangeListener $r1 = (OnPageChangeListener) this.mOnPageChangeListeners.get($i1);
                if ($r1 != null) {
                    $r1.onPageScrollStateChanged($i0);
                }
            }
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageScrollStateChanged($i0);
        }
    }

    private void completeScroll(boolean $z0) throws  {
        int $i0;
        boolean $z1 = true;
        boolean $z2 = this.mScrollState == 2;
        if ($z2) {
            setScrollingCacheEnabled(false);
            if (this.mScroller.isFinished()) {
                $z1 = false;
            }
            if ($z1) {
                this.mScroller.abortAnimation();
                $i0 = getScrollX();
                int $i1 = getScrollY();
                int $i2 = this.mScroller.getCurrX();
                int $i3 = this.mScroller.getCurrY();
                if (!($i0 == $i2 && $i1 == $i3)) {
                    scrollTo($i2, $i3);
                    if ($i2 != $i0) {
                        pageScrolled($i2);
                    }
                }
            }
        }
        this.mPopulatePending = false;
        for ($i0 = 0; $i0 < this.mItems.size(); $i0++) {
            ItemInfo $r4 = (ItemInfo) this.mItems.get($i0);
            if ($r4.scrolling) {
                $z2 = true;
                $r4.scrolling = false;
            }
        }
        if (!$z2) {
            return;
        }
        if ($z0) {
            ViewCompat.postOnAnimation(this, this.mEndScrollRunnable);
        } else {
            this.mEndScrollRunnable.run();
        }
    }

    private boolean isGutterDrag(float $f0, float $f1) throws  {
        return ($f0 < ((float) this.mGutterSize) && $f1 > 0.0f) || ($f0 > ((float) (getWidth() - this.mGutterSize)) && $f1 < 0.0f);
    }

    private void enableLayers(boolean $z0) throws  {
        int $i0 = getChildCount();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            byte $b2;
            if ($z0) {
                $b2 = (byte) 2;
            } else {
                $b2 = (byte) 0;
            }
            ViewCompat.setLayerType(getChildAt($i1), $b2, null);
        }
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        if (this.mFakeDragging) {
            return true;
        }
        if ($r1.getAction() == 0 && $r1.getEdgeFlags() != 0) {
            return false;
        }
        if (this.mAdapter == null || this.mAdapter.getCount() == 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement($r1);
        boolean $z0 = false;
        float $f0;
        int $i1;
        switch ($r1.getAction() & 255) {
            case 0:
                this.mScroller.abortAnimation();
                this.mPopulatePending = false;
                populate();
                $f0 = $r1.getX();
                this.mInitialMotionX = $f0;
                this.mLastMotionX = $f0;
                $f0 = $r1.getY();
                this.mInitialMotionY = $f0;
                this.mLastMotionY = $f0;
                this.mActivePointerId = MotionEventCompat.getPointerId($r1, 0);
                break;
            case 1:
                if (this.mIsBeingDragged) {
                    VelocityTracker $r3 = this.mVelocityTracker;
                    $r3.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                    $i1 = (int) VelocityTrackerCompat.getXVelocity($r3, this.mActivePointerId);
                    this.mPopulatePending = true;
                    int $i3 = getClientWidth();
                    int $i4 = getScrollX();
                    ItemInfo $r6 = infoForCurrentScrollPosition();
                    int $i0 = this.mPageMargin;
                    $f0 = ((float) $i0) / ((float) $i3);
                    setCurrentItemInternal(determineTargetPage($r6.position, ((((float) $i4) / ((float) $i3)) - $r6.offset) / ($r6.widthFactor + $f0), $i1, (int) (MotionEventCompat.getX($r1, MotionEventCompat.findPointerIndex($r1, this.mActivePointerId)) - this.mInitialMotionX)), true, true, $i1);
                    $z0 = resetTouch();
                    break;
                }
                break;
            case 2:
                if (!this.mIsBeingDragged) {
                    $i1 = MotionEventCompat.findPointerIndex($r1, this.mActivePointerId);
                    if ($i1 == -1) {
                        $z0 = resetTouch();
                        break;
                    }
                    float $f1 = MotionEventCompat.getX($r1, $i1);
                    float $f2 = Math.abs($f1 - this.mLastMotionX);
                    $f0 = MotionEventCompat.getY($r1, $i1);
                    float $f3 = Math.abs($f0 - this.mLastMotionY);
                    if ($f2 > ((float) this.mTouchSlop) && $f2 > $f3) {
                        this.mIsBeingDragged = true;
                        requestParentDisallowInterceptTouchEvent(true);
                        if ($f1 - this.mInitialMotionX > 0.0f) {
                            $f1 = this.mInitialMotionX + ((float) this.mTouchSlop);
                        } else {
                            $f1 = this.mInitialMotionX - ((float) this.mTouchSlop);
                        }
                        this.mLastMotionX = $f1;
                        this.mLastMotionY = $f0;
                        setScrollState(1);
                        setScrollingCacheEnabled(true);
                        ViewParent $r5 = getParent();
                        if ($r5 != null) {
                            $r5.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
                if (this.mIsBeingDragged) {
                    $z0 = false | performDrag(MotionEventCompat.getX($r1, MotionEventCompat.findPointerIndex($r1, this.mActivePointerId)));
                    break;
                }
                break;
            case 3:
                if (this.mIsBeingDragged) {
                    scrollToItem(this.mCurItem, true, 0, false);
                    $z0 = resetTouch();
                    break;
                }
                break;
            case 4:
                break;
            case 5:
                $i1 = MotionEventCompat.getActionIndex($r1);
                this.mLastMotionX = MotionEventCompat.getX($r1, $i1);
                this.mActivePointerId = MotionEventCompat.getPointerId($r1, $i1);
                break;
            case 6:
                onSecondaryPointerUp($r1);
                this.mLastMotionX = MotionEventCompat.getX($r1, MotionEventCompat.findPointerIndex($r1, this.mActivePointerId));
                break;
            default:
                break;
        }
        if ($z0) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        return true;
    }

    private boolean resetTouch() throws  {
        this.mActivePointerId = -1;
        endDrag();
        return this.mLeftEdge.onRelease() | this.mRightEdge.onRelease();
    }

    private void requestParentDisallowInterceptTouchEvent(boolean $z0) throws  {
        ViewParent $r1 = getParent();
        if ($r1 != null) {
            $r1.requestDisallowInterceptTouchEvent($z0);
        }
    }

    private boolean performDrag(float $f0) throws  {
        boolean $z0 = false;
        float $f1 = this.mLastMotionX - $f0;
        this.mLastMotionX = $f0;
        float $f02 = (float) getScrollX();
        $f0 = $f02 + $f1;
        int $i0 = getClientWidth();
        $f1 = ((float) $i0) * this.mFirstOffset;
        float $f2 = ((float) $i0) * this.mLastOffset;
        boolean $z1 = true;
        boolean $z2 = true;
        ItemInfo $r3 = (ItemInfo) this.mItems.get(0);
        ItemInfo $r5 = (ItemInfo) this.mItems.get(this.mItems.size() - 1);
        if ($r3.position != 0) {
            $z1 = false;
            $f1 = $r3.offset * ((float) $i0);
        }
        int $i1 = $r5.position;
        PagerAdapter $r6 = this.mAdapter;
        if ($i1 != $r6.getCount() - 1) {
            $z2 = false;
            $f2 = $r5.offset * ((float) $i0);
        }
        if ($f0 < $f1) {
            if ($z1) {
                $f0 = $f1 - $f0;
                $z0 = this.mLeftEdge.onPull(Math.abs($f0) / ((float) $i0));
            }
            $f0 = $f1;
        } else if ($f0 > $f2) {
            if ($z2) {
                $f0 -= $f2;
                $z0 = this.mRightEdge.onPull(Math.abs($f0) / ((float) $i0));
            }
            $f0 = $f2;
        }
        this.mLastMotionX += $f0 - ((float) ((int) $f0));
        scrollTo((int) $f0, getScrollY());
        pageScrolled((int) $f0);
        return $z0;
    }

    private ItemInfo infoForCurrentScrollPosition() throws  {
        float $f2 = 0.0f;
        int $i0 = getClientWidth();
        float $f3 = $i0 > 0 ? ((float) getScrollX()) / ((float) $i0) : 0.0f;
        if ($i0 > 0) {
            $f2 = ((float) this.mPageMargin) / ((float) $i0);
        }
        int $i1 = -1;
        float $f0 = 0.0f;
        float $f1 = 0.0f;
        boolean $z0 = true;
        ItemInfo $r1 = null;
        $i0 = 0;
        while ($i0 < this.mItems.size()) {
            ItemInfo $r4 = (ItemInfo) this.mItems.get($i0);
            if (!($z0 || $r4.position == $i1 + 1)) {
                $r4 = this.mTempItem;
                $r4.offset = ($f0 + $f1) + $f2;
                $r4.position = $i1 + 1;
                $r4.widthFactor = this.mAdapter.getPageWidth($r4.position);
                $i0--;
            }
            $f0 = $r4.offset;
            $f1 = ($r4.widthFactor + $f0) + $f2;
            if (!$z0 && $f3 < $f0) {
                return $r1;
            }
            if ($f3 < $f1 || $i0 == this.mItems.size() - 1) {
                return $r4;
            }
            $z0 = false;
            $i1 = $r4.position;
            $f1 = $r4.widthFactor;
            $r1 = $r4;
            $i0++;
        }
        return $r1;
    }

    private int determineTargetPage(int $i0, float $f0, int $i1, int $i2) throws  {
        if (Math.abs($i2) <= this.mFlingDistance || Math.abs($i1) <= this.mMinimumVelocity) {
            $i0 = (int) ((((float) $i0) + $f0) + ($i0 >= this.mCurItem ? 0.4f : 0.6f));
        } else if ($i1 <= 0) {
            $i0++;
        }
        if (this.mItems.size() <= 0) {
            return $i0;
        }
        return Math.max(((ItemInfo) this.mItems.get(0)).position, Math.min($i0, ((ItemInfo) this.mItems.get(this.mItems.size() - 1)).position));
    }

    public void draw(Canvas $r1) throws  {
        super.draw($r1);
        boolean $z0 = false;
        int $i0 = ViewCompat.getOverScrollMode(this);
        if ($i0 == 0 || ($i0 == 1 && this.mAdapter != null && this.mAdapter.getCount() > 1)) {
            int $i1;
            int $i2;
            if (!this.mLeftEdge.isFinished()) {
                $i0 = $r1.save();
                $i1 = (getHeight() - getPaddingTop()) - getPaddingBottom();
                $i2 = getWidth();
                $r1.rotate(270.0f);
                $r1.translate((float) ((-$i1) + getPaddingTop()), this.mFirstOffset * ((float) $i2));
                this.mLeftEdge.setSize($i1, $i2);
                $z0 = false | this.mLeftEdge.draw($r1);
                $r1.restoreToCount($i0);
            }
            if (!this.mRightEdge.isFinished()) {
                $i0 = $r1.save();
                $i1 = getWidth();
                $i2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
                $r1.rotate(90.0f);
                $r1.translate((float) (-getPaddingTop()), (-(this.mLastOffset + 1.0f)) * ((float) $i1));
                this.mRightEdge.setSize($i2, $i1);
                $z0 |= this.mRightEdge.draw($r1);
                $r1.restoreToCount($i0);
            }
        } else {
            this.mLeftEdge.finish();
            this.mRightEdge.finish();
        }
        if ($z0) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    protected void onDraw(Canvas $r1) throws  {
        super.onDraw($r1);
        if (this.mPageMargin > 0 && this.mMarginDrawable != null && this.mItems.size() > 0 && this.mAdapter != null) {
            int $i3 = getScrollX();
            int $i4 = getWidth();
            float $f0 = ((float) this.mPageMargin) / ((float) $i4);
            int $i5 = 0;
            ItemInfo $r6 = (ItemInfo) this.mItems.get(0);
            float $f2 = $r6.offset;
            int $i6 = this.mItems.size();
            int $i0 = $r6.position;
            int $i1 = ((ItemInfo) this.mItems.get($i6 - 1)).position;
            while ($i0 < $i1) {
                float $f1;
                float $f22;
                while ($i0 > $r6.position && $i5 < $i6) {
                    $i5++;
                    $r6 = (ItemInfo) this.mItems.get($i5);
                }
                if ($i0 == $r6.position) {
                    $f1 = $r6.offset;
                    $f22 = $r6.widthFactor;
                    $f1 += $f22;
                    $f22 = (float) $i4;
                    $f1 *= $f22;
                    $f22 = $r6.offset + $r6.widthFactor;
                    $f2 = $f22;
                    $f2 = $f22 + $f0;
                } else {
                    float $f3 = this.mAdapter.getPageWidth($i0);
                    $f22 = (float) $i4;
                    $f1 = ($f2 + $f3) * $f22;
                    $f2 += $f3 + $f0;
                }
                int $f32 = this.mPageMargin;
                int $i2 = $f32;
                $f22 = (float) $f32;
                if ($f22 + $f1 > ((float) $i3)) {
                    Drawable $r2 = this.mMarginDrawable;
                    int $i8 = Math.round($f1);
                    $i2 = this.mTopPageBounds;
                    $f32 = this.mPageMargin;
                    $f22 = (float) $f32;
                    $f22 += $f1;
                    $r2.setBounds($i8, $i2, Math.round($f22), this.mBottomPageBounds);
                    this.mMarginDrawable.draw($r1);
                }
                if ($f1 <= ((float) ($i3 + $i4))) {
                    $i0++;
                } else {
                    return;
                }
            }
        }
    }

    public boolean beginFakeDrag() throws  {
        if (this.mIsBeingDragged) {
            return false;
        }
        this.mFakeDragging = true;
        setScrollState(1);
        this.mLastMotionX = 0.0f;
        this.mInitialMotionX = 0.0f;
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            this.mVelocityTracker.clear();
        }
        long $l0 = SystemClock.uptimeMillis();
        MotionEvent $r2 = MotionEvent.obtain($l0, $l0, 0, 0.0f, 0.0f, 0);
        this.mVelocityTracker.addMovement($r2);
        $r2.recycle();
        this.mFakeDragBeginTime = $l0;
        return true;
    }

    public void endFakeDrag() throws  {
        if (this.mFakeDragging) {
            if (this.mAdapter != null) {
                VelocityTracker $r1 = this.mVelocityTracker;
                $r1.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                int $i1 = (int) VelocityTrackerCompat.getXVelocity($r1, this.mActivePointerId);
                this.mPopulatePending = true;
                int $i2 = getClientWidth();
                int $i3 = getScrollX();
                ItemInfo $r4 = infoForCurrentScrollPosition();
                setCurrentItemInternal(determineTargetPage($r4.position, ((((float) $i3) / ((float) $i2)) - $r4.offset) / $r4.widthFactor, $i1, (int) (this.mLastMotionX - this.mInitialMotionX)), true, true, $i1);
            }
            endDrag();
            this.mFakeDragging = false;
            return;
        }
        throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
    }

    public void fakeDragBy(float $f0) throws  {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        } else if (this.mAdapter != null) {
            this.mLastMotionX += $f0;
            $f0 = ((float) getScrollX()) - $f0;
            int $i0 = getClientWidth();
            float $f1 = ((float) $i0) * this.mFirstOffset;
            float $f2 = ((float) $i0) * this.mLastOffset;
            ItemInfo itemInfo = (ItemInfo) this.mItems.get(0);
            ArrayList $r3 = this.mItems;
            ArrayList $r6 = this.mItems;
            ItemInfo $r7 = (ItemInfo) $r3.get($r6.size() - 1);
            if (itemInfo.position != 0) {
                $f1 = itemInfo.offset * ((float) $i0);
            }
            if ($r7.position != this.mAdapter.getCount() - 1) {
                $f2 = $r7.offset * ((float) $i0);
            }
            if ($f0 < $f1) {
                $f0 = $f1;
            } else if ($f0 > $f2) {
                $f0 = $f2;
            }
            this.mLastMotionX += $f0 - ((float) ((int) $f0));
            scrollTo((int) $f0, getScrollY());
            pageScrolled((int) $f0);
            long $l4 = SystemClock.uptimeMillis();
            MotionEvent $r8 = MotionEvent.obtain(this.mFakeDragBeginTime, $l4, 2, this.mLastMotionX, 0.0f, 0);
            VelocityTracker velocityTracker = this.mVelocityTracker;
            VelocityTracker $r9 = velocityTracker;
            velocityTracker.addMovement($r8);
            $r8.recycle();
        }
    }

    public boolean isFakeDragging() throws  {
        return this.mFakeDragging;
    }

    private void onSecondaryPointerUp(MotionEvent $r1) throws  {
        int $i0 = MotionEventCompat.getActionIndex($r1);
        if (MotionEventCompat.getPointerId($r1, $i0) == this.mActivePointerId) {
            byte $b3;
            if ($i0 == 0) {
                $b3 = (byte) 1;
            } else {
                $b3 = (byte) 0;
            }
            this.mLastMotionX = MotionEventCompat.getX($r1, $b3);
            this.mActivePointerId = MotionEventCompat.getPointerId($r1, $b3);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private void endDrag() throws  {
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void setScrollingCacheEnabled(boolean $z0) throws  {
        if (this.mScrollingCacheEnabled != $z0) {
            this.mScrollingCacheEnabled = $z0;
        }
    }

    public boolean canScrollHorizontally(int $i0) throws  {
        boolean $z0 = true;
        if (this.mAdapter == null) {
            return false;
        }
        int $i1 = getClientWidth();
        int $i2 = getScrollX();
        if ($i0 < 0) {
            if ($i2 <= ((int) (((float) $i1) * this.mFirstOffset))) {
                $z0 = false;
            }
            return $z0;
        } else if ($i0 <= 0) {
            return false;
        } else {
            if ($i2 >= ((int) (((float) $i1) * this.mLastOffset))) {
                $z0 = false;
            }
            return $z0;
        }
    }

    protected boolean canScroll(View $r1, boolean $z0, int $i0, int $i1, int $i2) throws  {
        if ($r1 instanceof ViewGroup) {
            ViewGroup $r2 = (ViewGroup) $r1;
            int $i5 = $r1.getScrollX();
            int $i6 = $r1.getScrollY();
            for (int $i7 = $r2.getChildCount() - 1; $i7 >= 0; $i7--) {
                View $r3 = $r2.getChildAt($i7);
                if ($i1 + $i5 >= $r3.getLeft() && $i1 + $i5 < $r3.getRight() && $i2 + $i6 >= $r3.getTop() && $i2 + $i6 < $r3.getBottom()) {
                    if (canScroll($r3, true, $i0, ($i1 + $i5) - $r3.getLeft(), ($i2 + $i6) - $r3.getTop())) {
                        return true;
                    }
                }
            }
        }
        if ($z0) {
            if (ViewCompat.canScrollHorizontally($r1, -$i0)) {
                return true;
            }
        }
        return false;
    }

    public boolean dispatchKeyEvent(KeyEvent $r1) throws  {
        return super.dispatchKeyEvent($r1) || executeKeyEvent($r1);
    }

    public boolean executeKeyEvent(KeyEvent $r1) throws  {
        if ($r1.getAction() != 0) {
            return false;
        }
        switch ($r1.getKeyCode()) {
            case 21:
                return arrowScroll(17);
            case 22:
                return arrowScroll(66);
            case 61:
                if (VERSION.SDK_INT < 11) {
                    return false;
                }
                if (KeyEventCompat.hasNoModifiers($r1)) {
                    return arrowScroll(2);
                }
                return KeyEventCompat.hasModifiers($r1, 1) ? arrowScroll(1) : false;
            default:
                return false;
        }
    }

    private Rect getChildRectInPagerCoordinates(Rect $r2, View $r1) throws  {
        if ($r2 == null) {
            $r2 = new Rect();
        }
        if ($r1 == null) {
            $r2.set(0, 0, 0, 0);
            return $r2;
        }
        $r2.left = $r1.getLeft();
        $r2.right = $r1.getRight();
        $r2.top = $r1.getTop();
        $r2.bottom = $r1.getBottom();
        ViewPager $r3 = $r1.getParent();
        while (($r3 instanceof ViewGroup) && $r3 != this) {
            ViewGroup $r4 = $r3;
            $r2.left += $r4.getLeft();
            $r2.right += $r4.getRight();
            $r2.top += $r4.getTop();
            $r2.bottom += $r4.getBottom();
            $r3 = $r4.getParent();
        }
        return $r2;
    }

    boolean pageLeft() throws  {
        if (this.mCurItem <= 0) {
            return false;
        }
        setCurrentItem(this.mCurItem - 1, true);
        return true;
    }

    boolean pageRight() throws  {
        if (this.mAdapter == null || this.mCurItem >= this.mAdapter.getCount() - 1) {
            return false;
        }
        setCurrentItem(this.mCurItem + 1, true);
        return true;
    }

    public void addFocusables(@Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;II)V"}) ArrayList<View> $r1, @Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;II)V"}) int $i0, @Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;II)V"}) int $i1) throws  {
        int $i2 = $r1.size();
        int $i3 = getDescendantFocusability();
        if ($i3 != 393216) {
            for (int $i4 = 0; $i4 < getChildCount(); $i4++) {
                View $r2 = getChildAt($i4);
                if ($r2.getVisibility() == 0) {
                    ItemInfo $r3 = infoForChild($r2);
                    if ($r3 != null && $r3.position == this.mCurItem) {
                        $r2.addFocusables($r1, $i0, $i1);
                    }
                }
            }
        }
        if (($i3 == 262144 && $i2 != $r1.size()) || !isFocusable()) {
            return;
        }
        if ((($i1 & 1) != 1 || !isInTouchMode() || isFocusableInTouchMode()) && $r1 != null) {
            $r1.add(this);
        }
    }

    public void addTouchables(@Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) ArrayList<View> $r1) throws  {
        for (int $i1 = 0; $i1 < getChildCount(); $i1++) {
            View $r2 = getChildAt($i1);
            if ($r2.getVisibility() == 0) {
                ItemInfo $r3 = infoForChild($r2);
                if ($r3 != null && $r3.position == this.mCurItem) {
                    $r2.addTouchables($r1);
                }
            }
        }
    }

    protected boolean onRequestFocusInDescendants(int $i0, Rect $r1) throws  {
        int $i3;
        byte $b4;
        int $i2 = getChildCount();
        if (($i0 & 2) != 0) {
            $i3 = 0;
            $b4 = (byte) 1;
        } else {
            $i3 = $i2 - 1;
            $b4 = (byte) -1;
            $i2 = -1;
        }
        while ($i3 != $i2) {
            View $r2 = getChildAt($i3);
            if ($r2.getVisibility() == 0) {
                ItemInfo $r3 = infoForChild($r2);
                if ($r3 != null && $r3.position == this.mCurItem && $r2.requestFocus($i0, $r1)) {
                    return true;
                }
            }
            $i3 += $b4;
        }
        return false;
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent $r1) throws  {
        if ($r1.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent($r1);
        }
        int $i0 = getChildCount();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            View $r2 = getChildAt($i1);
            if ($r2.getVisibility() == 0) {
                ItemInfo $r3 = infoForChild($r2);
                if ($r3 != null && $r3.position == this.mCurItem && $r2.dispatchPopulateAccessibilityEvent($r1)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() throws  {
        return new LayoutParams();
    }

    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams p) throws  {
        return generateDefaultLayoutParams();
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
        return ($r1 instanceof LayoutParams) && super.checkLayoutParams($r1);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet $r1) throws  {
        return new LayoutParams(getContext(), $r1);
    }
}
