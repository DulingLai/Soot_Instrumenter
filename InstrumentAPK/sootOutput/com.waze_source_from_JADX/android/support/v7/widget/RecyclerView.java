package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.os.TraceCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v4.widget.ScrollerCompat;
import android.support.v7.recyclerview.C0195R;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.abaltatech.mcp.mcs.utils.FilenameUtils;
import com.waze.analytics.AnalyticsEvents;
import com.waze.map.CanvasFont;
import com.waze.strings.DisplayStrings;
import dalvik.annotation.Signature;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerView extends ViewGroup implements NestedScrollingChild, ScrollingView {
    static final boolean ALLOW_SIZE_IN_UNSPECIFIED_SPEC;
    private static final boolean DEBUG = false;
    static final boolean DISPATCH_TEMP_DETACH = false;
    private static final boolean FORCE_INVALIDATE_DISPLAY_LIST;
    public static final int HORIZONTAL = 0;
    private static final int INVALID_POINTER = -1;
    public static final int INVALID_TYPE = -1;
    private static final Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = new Class[]{Context.class, AttributeSet.class, Integer.TYPE, Integer.TYPE};
    private static final int MAX_SCROLL_DURATION = 2000;
    private static final int[] NESTED_SCROLLING_ATTRS = new int[]{16843830};
    public static final long NO_ID = -1;
    public static final int NO_POSITION = -1;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "RecyclerView";
    public static final int TOUCH_SLOP_DEFAULT = 0;
    public static final int TOUCH_SLOP_PAGING = 1;
    private static final String TRACE_BIND_VIEW_TAG = "RV OnBindView";
    private static final String TRACE_CREATE_VIEW_TAG = "RV CreateView";
    private static final String TRACE_HANDLE_ADAPTER_UPDATES_TAG = "RV PartialInvalidate";
    private static final String TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG = "RV FullInvalidate";
    private static final String TRACE_ON_LAYOUT_TAG = "RV OnLayout";
    private static final String TRACE_SCROLL_TAG = "RV Scroll";
    public static final int VERTICAL = 1;
    private static final Interpolator sQuinticInterpolator = new C02523();
    private RecyclerViewAccessibilityDelegate mAccessibilityDelegate;
    private final AccessibilityManager mAccessibilityManager;
    private OnItemTouchListener mActiveOnItemTouchListener;
    private Adapter mAdapter;
    AdapterHelper mAdapterHelper;
    private boolean mAdapterUpdateDuringMeasure;
    private EdgeEffectCompat mBottomGlow;
    private ChildDrawingOrderCallback mChildDrawingOrderCallback;
    ChildHelper mChildHelper;
    private boolean mClipToPadding;
    private boolean mDataSetHasChangedAfterLayout;
    private int mEatRequestLayout;
    private int mEatenAccessibilityChangeFlags;
    private boolean mFirstLayoutComplete;
    private boolean mHasFixedSize;
    private boolean mIgnoreMotionEventTillDown;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private boolean mIsAttached;
    ItemAnimator mItemAnimator;
    private ItemAnimatorListener mItemAnimatorListener;
    private Runnable mItemAnimatorRunner;
    private final ArrayList<ItemDecoration> mItemDecorations;
    boolean mItemsAddedOrRemoved;
    boolean mItemsChanged;
    private int mLastTouchX;
    private int mLastTouchY;
    @VisibleForTesting
    LayoutManager mLayout;
    private boolean mLayoutFrozen;
    private int mLayoutOrScrollCounter;
    private boolean mLayoutRequestEaten;
    private EdgeEffectCompat mLeftGlow;
    private final int mMaxFlingVelocity;
    private final int mMinFlingVelocity;
    private final int[] mMinMaxLayoutPositions;
    private final int[] mNestedOffsets;
    private final RecyclerViewDataObserver mObserver;
    private List<OnChildAttachStateChangeListener> mOnChildAttachStateListeners;
    private final ArrayList<OnItemTouchListener> mOnItemTouchListeners;
    private SavedState mPendingSavedState;
    private final boolean mPostUpdatesOnAnimation;
    private boolean mPostedAnimatorRunner;
    final Recycler mRecycler;
    private RecyclerListener mRecyclerListener;
    private EdgeEffectCompat mRightGlow;
    private final int[] mScrollConsumed;
    private float mScrollFactor;
    private OnScrollListener mScrollListener;
    private List<OnScrollListener> mScrollListeners;
    private final int[] mScrollOffset;
    private int mScrollPointerId;
    private int mScrollState;
    private NestedScrollingChildHelper mScrollingChildHelper;
    final State mState;
    private final Rect mTempRect;
    private EdgeEffectCompat mTopGlow;
    private int mTouchSlop;
    private final Runnable mUpdateChildViewsRunnable;
    private VelocityTracker mVelocityTracker;
    private final ViewFlinger mViewFlinger;
    private final ProcessCallback mViewInfoProcessCallback;
    final ViewInfoStore mViewInfoStore;

    public static abstract class ItemAnimator {
        public static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        public static final int FLAG_CHANGED = 2;
        public static final int FLAG_INVALIDATED = 4;
        public static final int FLAG_MOVED = 2048;
        public static final int FLAG_REMOVED = 8;
        private long mAddDuration = 120;
        private long mChangeDuration = 250;
        private ArrayList<ItemAnimatorFinishedListener> mFinishedListeners = new ArrayList();
        private ItemAnimatorListener mListener = null;
        private long mMoveDuration = 250;
        private long mRemoveDuration = 120;

        @Retention(RetentionPolicy.SOURCE)
        public @interface AdapterChanges {
        }

        public interface ItemAnimatorFinishedListener {
            void onAnimationsFinished() throws ;
        }

        interface ItemAnimatorListener {
            void onAnimationFinished(ViewHolder viewHolder) throws ;
        }

        public static class ItemHolderInfo {
            public int bottom;
            public int changeFlags;
            public int left;
            public int right;
            public int top;

            public ItemHolderInfo setFrom(ViewHolder $r1) throws  {
                return setFrom($r1, 0);
            }

            public ItemHolderInfo setFrom(ViewHolder $r1, int flags) throws  {
                View $r2 = $r1.itemView;
                this.left = $r2.getLeft();
                this.top = $r2.getTop();
                this.right = $r2.getRight();
                this.bottom = $r2.getBottom();
                return this;
            }
        }

        public abstract boolean animateAppearance(@NonNull ViewHolder viewHolder, @Nullable ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2) throws ;

        public abstract boolean animateChange(@NonNull ViewHolder viewHolder, @NonNull ViewHolder viewHolder2, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2) throws ;

        public abstract boolean animateDisappearance(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @Nullable ItemHolderInfo itemHolderInfo2) throws ;

        public abstract boolean animatePersistence(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2) throws ;

        public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder) throws  {
            return true;
        }

        public abstract void endAnimation(ViewHolder viewHolder) throws ;

        public abstract void endAnimations() throws ;

        public abstract boolean isRunning() throws ;

        public abstract void runPendingAnimations() throws ;

        public long getMoveDuration() throws  {
            return this.mMoveDuration;
        }

        public void setMoveDuration(long $l0) throws  {
            this.mMoveDuration = $l0;
        }

        public long getAddDuration() throws  {
            return this.mAddDuration;
        }

        public void setAddDuration(long $l0) throws  {
            this.mAddDuration = $l0;
        }

        public long getRemoveDuration() throws  {
            return this.mRemoveDuration;
        }

        public void setRemoveDuration(long $l0) throws  {
            this.mRemoveDuration = $l0;
        }

        public long getChangeDuration() throws  {
            return this.mChangeDuration;
        }

        public void setChangeDuration(long $l0) throws  {
            this.mChangeDuration = $l0;
        }

        void setListener(ItemAnimatorListener $r1) throws  {
            this.mListener = $r1;
        }

        @NonNull
        public ItemHolderInfo recordPreLayoutInformation(@NonNull @Signature({"(", "Landroid/support/v7/widget/RecyclerView$State;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "I", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)", "Landroid/support/v7/widget/RecyclerView$ItemAnimator$ItemHolderInfo;"}) State state, @NonNull @Signature({"(", "Landroid/support/v7/widget/RecyclerView$State;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "I", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)", "Landroid/support/v7/widget/RecyclerView$ItemAnimator$ItemHolderInfo;"}) ViewHolder $r2, @Signature({"(", "Landroid/support/v7/widget/RecyclerView$State;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "I", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)", "Landroid/support/v7/widget/RecyclerView$ItemAnimator$ItemHolderInfo;"}) int changeFlags, @NonNull @Signature({"(", "Landroid/support/v7/widget/RecyclerView$State;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "I", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)", "Landroid/support/v7/widget/RecyclerView$ItemAnimator$ItemHolderInfo;"}) List<Object> list) throws  {
            return obtainHolderInfo().setFrom($r2);
        }

        @NonNull
        public ItemHolderInfo recordPostLayoutInformation(@NonNull State state, @NonNull ViewHolder $r2) throws  {
            return obtainHolderInfo().setFrom($r2);
        }

        static int buildAdapterChangeFlagsForAnimations(ViewHolder $r0) throws  {
            int $i0 = $r0.mFlags & 14;
            if ($r0.isInvalid()) {
                return 4;
            }
            if (($i0 & 4) == 0) {
                int $i1 = $r0.getOldPosition();
                int $i2 = $r0.getAdapterPosition();
                if (!($i1 == -1 || $i2 == -1 || $i1 == $i2)) {
                    $i0 |= 2048;
                }
            }
            return $i0;
        }

        public final void dispatchAnimationFinished(ViewHolder $r1) throws  {
            onAnimationFinished($r1);
            if (this.mListener != null) {
                this.mListener.onAnimationFinished($r1);
            }
        }

        public void onAnimationFinished(ViewHolder viewHolder) throws  {
        }

        public final void dispatchAnimationStarted(ViewHolder $r1) throws  {
            onAnimationStarted($r1);
        }

        public void onAnimationStarted(ViewHolder viewHolder) throws  {
        }

        public final boolean isRunning(ItemAnimatorFinishedListener $r1) throws  {
            boolean $z0 = isRunning();
            if ($r1 == null) {
                return $z0;
            }
            if ($z0) {
                this.mFinishedListeners.add($r1);
                return $z0;
            }
            $r1.onAnimationsFinished();
            return $z0;
        }

        public boolean canReuseUpdatedViewHolder(@NonNull @Signature({"(", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)Z"}) ViewHolder $r1, @NonNull @Signature({"(", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)Z"}) List<Object> list) throws  {
            return canReuseUpdatedViewHolder($r1);
        }

        public final void dispatchAnimationsFinished() throws  {
            int $i0 = this.mFinishedListeners.size();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                ((ItemAnimatorFinishedListener) this.mFinishedListeners.get($i1)).onAnimationsFinished();
            }
            this.mFinishedListeners.clear();
        }

        public ItemHolderInfo obtainHolderInfo() throws  {
            return new ItemHolderInfo();
        }
    }

    public static class LayoutParams extends MarginLayoutParams {
        final Rect mDecorInsets = new Rect();
        boolean mInsetsDirty = true;
        boolean mPendingInvalidate = false;
        ViewHolder mViewHolder;

        public LayoutParams(Context $r1, AttributeSet $r2) throws  {
            super($r1, $r2);
        }

        public LayoutParams(int $i0, int $i1) throws  {
            super($i0, $i1);
        }

        public LayoutParams(MarginLayoutParams $r1) throws  {
            super($r1);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
            super($r1);
        }

        public LayoutParams(LayoutParams $r1) throws  {
            super($r1);
        }

        public boolean viewNeedsUpdate() throws  {
            return this.mViewHolder.needsUpdate();
        }

        public boolean isViewInvalid() throws  {
            return this.mViewHolder.isInvalid();
        }

        public boolean isItemRemoved() throws  {
            return this.mViewHolder.isRemoved();
        }

        public boolean isItemChanged() throws  {
            return this.mViewHolder.isUpdated();
        }

        public int getViewPosition() throws  {
            return this.mViewHolder.getPosition();
        }

        public int getViewLayoutPosition() throws  {
            return this.mViewHolder.getLayoutPosition();
        }

        public int getViewAdapterPosition() throws  {
            return this.mViewHolder.getAdapterPosition();
        }
    }

    public static abstract class LayoutManager {
        private boolean mAutoMeasure = false;
        ChildHelper mChildHelper;
        private int mHeight;
        private int mHeightMode;
        boolean mIsAttachedToWindow = false;
        private boolean mMeasurementCacheEnabled = true;
        RecyclerView mRecyclerView;
        private boolean mRequestedSimpleAnimations = false;
        @Nullable
        SmoothScroller mSmoothScroller;
        private int mWidth;
        private int mWidthMode;

        public static class Properties {
            public int orientation;
            public boolean reverseLayout;
            public int spanCount;
            public boolean stackFromEnd;
        }

        public boolean canScrollHorizontally() throws  {
            return false;
        }

        public boolean canScrollVertically() throws  {
            return false;
        }

        public boolean checkLayoutParams(LayoutParams $r1) throws  {
            return $r1 != null;
        }

        public int computeHorizontalScrollExtent(State state) throws  {
            return 0;
        }

        public int computeHorizontalScrollOffset(State state) throws  {
            return 0;
        }

        public int computeHorizontalScrollRange(State state) throws  {
            return 0;
        }

        public int computeVerticalScrollExtent(State state) throws  {
            return 0;
        }

        public int computeVerticalScrollOffset(State state) throws  {
            return 0;
        }

        public int computeVerticalScrollRange(State state) throws  {
            return 0;
        }

        public abstract LayoutParams generateDefaultLayoutParams() throws ;

        public int getBaseline() throws  {
            return -1;
        }

        public int getSelectionModeForAccessibility(Recycler recycler, State state) throws  {
            return 0;
        }

        public boolean isLayoutHierarchical(Recycler recycler, State state) throws  {
            return false;
        }

        public boolean onAddFocusables(@Signature({"(", "Landroid/support/v7/widget/RecyclerView;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;II)Z"}) RecyclerView recyclerView, @Signature({"(", "Landroid/support/v7/widget/RecyclerView;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;II)Z"}) ArrayList<View> arrayList, @Signature({"(", "Landroid/support/v7/widget/RecyclerView;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;II)Z"}) int direction, @Signature({"(", "Landroid/support/v7/widget/RecyclerView;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;II)Z"}) int focusableMode) throws  {
            return false;
        }

        @Nullable
        public View onFocusSearchFailed(View focused, int direction, Recycler recycler, State state) throws  {
            return null;
        }

        public View onInterceptFocusSearch(View focused, int direction) throws  {
            return null;
        }

        public Parcelable onSaveInstanceState() throws  {
            return null;
        }

        public boolean performAccessibilityActionForItem(Recycler recycler, State state, View view, int action, Bundle args) throws  {
            return false;
        }

        public int scrollHorizontallyBy(int dx, Recycler recycler, State state) throws  {
            return 0;
        }

        public int scrollVerticallyBy(int dy, Recycler recycler, State state) throws  {
            return 0;
        }

        boolean shouldMeasureTwice() throws  {
            return false;
        }

        public boolean supportsPredictiveItemAnimations() throws  {
            return false;
        }

        void setRecyclerView(RecyclerView $r1) throws  {
            if ($r1 == null) {
                this.mRecyclerView = null;
                this.mChildHelper = null;
                this.mWidth = 0;
                this.mHeight = 0;
            } else {
                this.mRecyclerView = $r1;
                this.mChildHelper = $r1.mChildHelper;
                this.mWidth = $r1.getWidth();
                this.mHeight = $r1.getHeight();
            }
            this.mWidthMode = 1073741824;
            this.mHeightMode = 1073741824;
        }

        void setMeasureSpecs(int $i0, int $i1) throws  {
            this.mWidth = MeasureSpec.getSize($i0);
            this.mWidthMode = MeasureSpec.getMode($i0);
            if (this.mWidthMode == 0 && !RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.mWidth = 0;
            }
            this.mHeight = MeasureSpec.getSize($i1);
            this.mHeightMode = MeasureSpec.getMode($i1);
            if (this.mHeightMode == 0 && !RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.mHeight = 0;
            }
        }

        void setMeasuredDimensionFromChildren(int $i0, int $i1) throws  {
            int $i6 = getChildCount();
            if ($i6 == 0) {
                this.mRecyclerView.defaultOnMeasure($i0, $i1);
                return;
            }
            int $i7 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            int $i8 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            int $i9 = Integer.MIN_VALUE;
            int $i10 = Integer.MIN_VALUE;
            for (int $i11 = 0; $i11 < $i6; $i11++) {
                View $r2 = getChildAt($i11);
                LayoutParams $r4 = (LayoutParams) $r2.getLayoutParams();
                int $i3 = getDecoratedLeft($r2);
                int $i4 = $r4.leftMargin;
                $i3 -= $i4;
                int decoratedRight = getDecoratedRight($r2) + $r4.rightMargin;
                int decoratedTop = getDecoratedTop($r2) - $r4.topMargin;
                int decoratedBottom = getDecoratedBottom($r2) + $r4.bottomMargin;
                if ($i3 < $i7) {
                    $i7 = $i3;
                }
                if (decoratedRight > $i9) {
                    $i9 = decoratedRight;
                }
                if (decoratedTop < $i8) {
                    $i8 = decoratedTop;
                }
                if (decoratedBottom > $i10) {
                    $i10 = decoratedBottom;
                }
            }
            this.mRecyclerView.mTempRect.set($i7, $i8, $i9, $i10);
            setMeasuredDimension(this.mRecyclerView.mTempRect, $i0, $i1);
        }

        public void setMeasuredDimension(Rect $r1, int $i0, int $i1) throws  {
            setMeasuredDimension(chooseSize($i0, ($r1.width() + getPaddingLeft()) + getPaddingRight(), getMinimumWidth()), chooseSize($i1, ($r1.height() + getPaddingTop()) + getPaddingBottom(), getMinimumHeight()));
        }

        public void requestLayout() throws  {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.requestLayout();
            }
        }

        public void assertInLayoutOrScroll(String $r1) throws  {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.assertInLayoutOrScroll($r1);
            }
        }

        public static int chooseSize(int $i0, int $i1, int $i2) throws  {
            int $i3 = MeasureSpec.getMode($i0);
            $i0 = MeasureSpec.getSize($i0);
            int $i4 = $i0;
            switch ($i3) {
                case Integer.MIN_VALUE:
                    return Math.min($i0, Math.max($i1, $i2));
                case 1073741824:
                    break;
                default:
                    $i4 = Math.max($i1, $i2);
                    break;
            }
            return $i4;
        }

        public void assertNotInLayoutOrScroll(String $r1) throws  {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.assertNotInLayoutOrScroll($r1);
            }
        }

        public void setAutoMeasureEnabled(boolean $z0) throws  {
            this.mAutoMeasure = $z0;
        }

        public boolean isAutoMeasureEnabled() throws  {
            return this.mAutoMeasure;
        }

        void dispatchAttachedToWindow(RecyclerView $r1) throws  {
            this.mIsAttachedToWindow = true;
            onAttachedToWindow($r1);
        }

        void dispatchDetachedFromWindow(RecyclerView $r1, Recycler $r2) throws  {
            this.mIsAttachedToWindow = false;
            onDetachedFromWindow($r1, $r2);
        }

        public boolean isAttachedToWindow() throws  {
            return this.mIsAttachedToWindow;
        }

        public void postOnAnimation(Runnable $r1) throws  {
            if (this.mRecyclerView != null) {
                ViewCompat.postOnAnimation(this.mRecyclerView, $r1);
            }
        }

        public boolean removeCallbacks(Runnable $r1) throws  {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.removeCallbacks($r1);
            }
            return false;
        }

        @CallSuper
        public void onAttachedToWindow(RecyclerView view) throws  {
        }

        @Deprecated
        public void onDetachedFromWindow(@Deprecated RecyclerView view) throws  {
        }

        @CallSuper
        public void onDetachedFromWindow(RecyclerView $r1, Recycler recycler) throws  {
            onDetachedFromWindow($r1);
        }

        public boolean getClipToPadding() throws  {
            return this.mRecyclerView != null && this.mRecyclerView.mClipToPadding;
        }

        public void onLayoutChildren(Recycler recycler, State state) throws  {
            Log.e(RecyclerView.TAG, "You must override onLayoutChildren(Recycler recycler, State state) ");
        }

        public LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
            if ($r1 instanceof LayoutParams) {
                return new LayoutParams((LayoutParams) $r1);
            }
            if ($r1 instanceof MarginLayoutParams) {
                return new LayoutParams((MarginLayoutParams) $r1);
            }
            return new LayoutParams($r1);
        }

        public LayoutParams generateLayoutParams(Context $r1, AttributeSet $r2) throws  {
            return new LayoutParams($r1, $r2);
        }

        public void scrollToPosition(int position) throws  {
        }

        public void smoothScrollToPosition(RecyclerView recyclerView, State state, int position) throws  {
            Log.e(RecyclerView.TAG, "You must override smoothScrollToPosition to support smooth scrolling");
        }

        public void startSmoothScroll(SmoothScroller $r1) throws  {
            if (!(this.mSmoothScroller == null || $r1 == this.mSmoothScroller || !this.mSmoothScroller.isRunning())) {
                this.mSmoothScroller.stop();
            }
            this.mSmoothScroller = $r1;
            this.mSmoothScroller.start(this.mRecyclerView, this);
        }

        public boolean isSmoothScrolling() throws  {
            return this.mSmoothScroller != null && this.mSmoothScroller.isRunning();
        }

        public int getLayoutDirection() throws  {
            return ViewCompat.getLayoutDirection(this.mRecyclerView);
        }

        public void endAnimation(View $r1) throws  {
            if (this.mRecyclerView.mItemAnimator != null) {
                this.mRecyclerView.mItemAnimator.endAnimation(RecyclerView.getChildViewHolderInt($r1));
            }
        }

        public void addDisappearingView(View $r1) throws  {
            addDisappearingView($r1, -1);
        }

        public void addDisappearingView(View $r1, int $i0) throws  {
            addViewInt($r1, $i0, true);
        }

        public void addView(View $r1) throws  {
            addView($r1, -1);
        }

        public void addView(View $r1, int $i0) throws  {
            addViewInt($r1, $i0, false);
        }

        private void addViewInt(View $r1, int $i0, boolean $z0) throws  {
            ViewHolder $r2 = RecyclerView.getChildViewHolderInt($r1);
            if ($z0 || $r2.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout($r2);
            } else {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout($r2);
            }
            LayoutParams $r6 = (LayoutParams) $r1.getLayoutParams();
            if ($r2.wasReturnedFromScrap() || $r2.isScrap()) {
                if ($r2.isScrap()) {
                    $r2.unScrap();
                } else {
                    $r2.clearReturnedFromScrapFlag();
                }
                this.mChildHelper.attachViewToParent($r1, $i0, $r1.getLayoutParams(), false);
            } else if ($r1.getParent() == this.mRecyclerView) {
                int $i1 = this.mChildHelper.indexOfChild($r1);
                if ($i0 == -1) {
                    $i0 = this.mChildHelper.getChildCount();
                }
                if ($i1 == -1) {
                    throw new IllegalStateException("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:" + this.mRecyclerView.indexOfChild($r1));
                } else if ($i1 != $i0) {
                    LayoutManager layoutManager = this.mRecyclerView.mLayout;
                    this = layoutManager;
                    layoutManager.moveView($i1, $i0);
                }
            } else {
                this.mChildHelper.addView($r1, $i0, false);
                $r6.mInsetsDirty = true;
                if (this.mSmoothScroller != null) {
                    SmoothScroller $r12 = this.mSmoothScroller;
                    if ($r12.isRunning()) {
                        $r12 = this.mSmoothScroller;
                        SmoothScroller $r122 = $r12;
                        $r12.onChildAttachedToWindow($r1);
                    }
                }
            }
            if ($r6.mPendingInvalidate) {
                View $r13 = $r2.itemView;
                $r13.invalidate();
                $r6.mPendingInvalidate = false;
            }
        }

        public void removeView(View $r1) throws  {
            this.mChildHelper.removeView($r1);
        }

        public void removeViewAt(int $i0) throws  {
            if (getChildAt($i0) != null) {
                this.mChildHelper.removeViewAt($i0);
            }
        }

        public void removeAllViews() throws  {
            for (int $i0 = getChildCount() - 1; $i0 >= 0; $i0--) {
                this.mChildHelper.removeViewAt($i0);
            }
        }

        public int getPosition(View $r1) throws  {
            return ((LayoutParams) $r1.getLayoutParams()).getViewLayoutPosition();
        }

        public int getItemViewType(View $r1) throws  {
            return RecyclerView.getChildViewHolderInt($r1).getItemViewType();
        }

        @Nullable
        public View findContainingItemView(View $r1) throws  {
            if (this.mRecyclerView == null) {
                return null;
            }
            $r1 = this.mRecyclerView.findContainingItemView($r1);
            if ($r1 == null) {
                return null;
            }
            return this.mChildHelper.isHidden($r1) ? null : $r1;
        }

        public View findViewByPosition(int $i0) throws  {
            int $i1 = getChildCount();
            for (int $i2 = 0; $i2 < $i1; $i2++) {
                View $r1 = getChildAt($i2);
                ViewHolder $r2 = RecyclerView.getChildViewHolderInt($r1);
                if ($r2 != null && $r2.getLayoutPosition() == $i0 && !$r2.shouldIgnore() && (this.mRecyclerView.mState.isPreLayout() || !$r2.isRemoved())) {
                    return $r1;
                }
            }
            return null;
        }

        public void detachView(View $r1) throws  {
            int $i0 = this.mChildHelper.indexOfChild($r1);
            if ($i0 >= 0) {
                detachViewInternal($i0, $r1);
            }
        }

        public void detachViewAt(int $i0) throws  {
            detachViewInternal($i0, getChildAt($i0));
        }

        private void detachViewInternal(int $i0, View view) throws  {
            this.mChildHelper.detachViewFromParent($i0);
        }

        public void attachView(View $r1, int $i0, LayoutParams $r2) throws  {
            ViewHolder $r3 = RecyclerView.getChildViewHolderInt($r1);
            if ($r3.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout($r3);
            } else {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout($r3);
            }
            this.mChildHelper.attachViewToParent($r1, $i0, $r2, $r3.isRemoved());
        }

        public void attachView(View $r1, int $i0) throws  {
            attachView($r1, $i0, (LayoutParams) $r1.getLayoutParams());
        }

        public void attachView(View $r1) throws  {
            attachView($r1, -1);
        }

        public void removeDetachedView(View $r1) throws  {
            this.mRecyclerView.removeDetachedView($r1, false);
        }

        public void moveView(int $i0, int $i1) throws  {
            View $r2 = getChildAt($i0);
            if ($r2 == null) {
                throw new IllegalArgumentException("Cannot move a child from non-existing index:" + $i0);
            }
            detachViewAt($i0);
            attachView($r2, $i1);
        }

        public void detachAndScrapView(View $r1, Recycler $r2) throws  {
            scrapOrRecycleView($r2, this.mChildHelper.indexOfChild($r1), $r1);
        }

        public void detachAndScrapViewAt(int $i0, Recycler $r1) throws  {
            scrapOrRecycleView($r1, $i0, getChildAt($i0));
        }

        public void removeAndRecycleView(View $r1, Recycler $r2) throws  {
            removeView($r1);
            $r2.recycleView($r1);
        }

        public void removeAndRecycleViewAt(int $i0, Recycler $r1) throws  {
            View $r2 = getChildAt($i0);
            removeViewAt($i0);
            $r1.recycleView($r2);
        }

        public int getChildCount() throws  {
            return this.mChildHelper != null ? this.mChildHelper.getChildCount() : 0;
        }

        public View getChildAt(int $i0) throws  {
            return this.mChildHelper != null ? this.mChildHelper.getChildAt($i0) : null;
        }

        public int getWidthMode() throws  {
            return this.mWidthMode;
        }

        public int getHeightMode() throws  {
            return this.mHeightMode;
        }

        public int getWidth() throws  {
            return this.mWidth;
        }

        public int getHeight() throws  {
            return this.mHeight;
        }

        public int getPaddingLeft() throws  {
            return this.mRecyclerView != null ? this.mRecyclerView.getPaddingLeft() : 0;
        }

        public int getPaddingTop() throws  {
            return this.mRecyclerView != null ? this.mRecyclerView.getPaddingTop() : 0;
        }

        public int getPaddingRight() throws  {
            return this.mRecyclerView != null ? this.mRecyclerView.getPaddingRight() : 0;
        }

        public int getPaddingBottom() throws  {
            return this.mRecyclerView != null ? this.mRecyclerView.getPaddingBottom() : 0;
        }

        public int getPaddingStart() throws  {
            return this.mRecyclerView != null ? ViewCompat.getPaddingStart(this.mRecyclerView) : 0;
        }

        public int getPaddingEnd() throws  {
            return this.mRecyclerView != null ? ViewCompat.getPaddingEnd(this.mRecyclerView) : 0;
        }

        public boolean isFocused() throws  {
            return this.mRecyclerView != null && this.mRecyclerView.isFocused();
        }

        public boolean hasFocus() throws  {
            return this.mRecyclerView != null && this.mRecyclerView.hasFocus();
        }

        public View getFocusedChild() throws  {
            if (this.mRecyclerView == null) {
                return null;
            }
            View $r2 = this.mRecyclerView.getFocusedChild();
            return ($r2 == null || this.mChildHelper.isHidden($r2)) ? null : $r2;
        }

        public int getItemCount() throws  {
            Adapter $r2 = this.mRecyclerView != null ? this.mRecyclerView.getAdapter() : null;
            if ($r2 != null) {
                return $r2.getItemCount();
            }
            return 0;
        }

        public void offsetChildrenHorizontal(int $i0) throws  {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.offsetChildrenHorizontal($i0);
            }
        }

        public void offsetChildrenVertical(int $i0) throws  {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.offsetChildrenVertical($i0);
            }
        }

        public void ignoreView(View $r1) throws  {
            if ($r1.getParent() != this.mRecyclerView || this.mRecyclerView.indexOfChild($r1) == -1) {
                throw new IllegalArgumentException("View should be fully attached to be ignored");
            }
            ViewHolder $r5 = RecyclerView.getChildViewHolderInt($r1);
            $r5.addFlags(128);
            this.mRecyclerView.mViewInfoStore.removeViewHolder($r5);
        }

        public void stopIgnoringView(View $r1) throws  {
            ViewHolder $r2 = RecyclerView.getChildViewHolderInt($r1);
            $r2.stopIgnoring();
            $r2.resetInternal();
            $r2.addFlags(4);
        }

        public void detachAndScrapAttachedViews(Recycler $r1) throws  {
            for (int $i0 = getChildCount() - 1; $i0 >= 0; $i0--) {
                scrapOrRecycleView($r1, $i0, getChildAt($i0));
            }
        }

        private void scrapOrRecycleView(Recycler $r1, int $i0, View $r2) throws  {
            ViewHolder $r3 = RecyclerView.getChildViewHolderInt($r2);
            if (!$r3.shouldIgnore()) {
                if (!$r3.isInvalid() || $r3.isRemoved() || this.mRecyclerView.mAdapter.hasStableIds()) {
                    detachViewAt($i0);
                    $r1.scrapView($r2);
                    this.mRecyclerView.mViewInfoStore.onViewDetached($r3);
                    return;
                }
                removeViewAt($i0);
                $r1.recycleViewHolderInternal($r3);
            }
        }

        void removeAndRecycleScrapInt(Recycler $r1) throws  {
            int $i0 = $r1.getScrapCount();
            for (int $i1 = $i0 - 1; $i1 >= 0; $i1--) {
                View $r2 = $r1.getScrapViewAt($i1);
                ViewHolder $r3 = RecyclerView.getChildViewHolderInt($r2);
                if (!$r3.shouldIgnore()) {
                    $r3.setIsRecyclable(false);
                    if ($r3.isTmpDetached()) {
                        this.mRecyclerView.removeDetachedView($r2, false);
                    }
                    if (this.mRecyclerView.mItemAnimator != null) {
                        this.mRecyclerView.mItemAnimator.endAnimation($r3);
                    }
                    $r3.setIsRecyclable(true);
                    $r1.quickRecycleScrapView($r2);
                }
            }
            $r1.clearScrap();
            if ($i0 > 0) {
                this.mRecyclerView.invalidate();
            }
        }

        public void measureChild(View $r1, int $i0, int $i1) throws  {
            LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
            Rect $r5 = this.mRecyclerView.getItemDecorInsetsForChild($r1);
            int $i2 = $i0 + ($r5.left + $r5.right);
            $i0 = $i1 + ($r5.top + $r5.bottom);
            $i1 = getChildMeasureSpec(getWidth(), getWidthMode(), (getPaddingLeft() + getPaddingRight()) + $i2, $r3.width, canScrollHorizontally());
            $i0 = getChildMeasureSpec(getHeight(), getHeightMode(), (getPaddingTop() + getPaddingBottom()) + $i0, $r3.height, canScrollVertically());
            if (shouldMeasureChild($r1, $i1, $i0, $r3)) {
                $r1.measure($i1, $i0);
            }
        }

        boolean shouldReMeasureChild(View $r1, int $i0, int $i1, LayoutParams $r2) throws  {
            return (this.mMeasurementCacheEnabled && isMeasurementUpToDate($r1.getMeasuredWidth(), $i0, $r2.width) && isMeasurementUpToDate($r1.getMeasuredHeight(), $i1, $r2.height)) ? false : true;
        }

        boolean shouldMeasureChild(View $r1, int $i0, int $i1, LayoutParams $r2) throws  {
            return (!$r1.isLayoutRequested() && this.mMeasurementCacheEnabled && isMeasurementUpToDate($r1.getWidth(), $i0, $r2.width) && isMeasurementUpToDate($r1.getHeight(), $i1, $r2.height)) ? false : true;
        }

        public boolean isMeasurementCacheEnabled() throws  {
            return this.mMeasurementCacheEnabled;
        }

        public void setMeasurementCacheEnabled(boolean $z0) throws  {
            this.mMeasurementCacheEnabled = $z0;
        }

        private static boolean isMeasurementUpToDate(int $i0, int $i1, int $i2) throws  {
            boolean $z0 = true;
            int $i3 = MeasureSpec.getMode($i1);
            $i1 = MeasureSpec.getSize($i1);
            if ($i2 <= 0 || $i0 == $i2) {
                switch ($i3) {
                    case Integer.MIN_VALUE:
                        return $i1 >= $i0;
                    case 0:
                        break;
                    case 1073741824:
                        return $i1 == $i0;
                    default:
                        return false;
                }
            }
            $z0 = false;
            return $z0;
        }

        public void measureChildWithMargins(View $r1, int $i0, int $i1) throws  {
            LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
            Rect $r5 = this.mRecyclerView.getItemDecorInsetsForChild($r1);
            int $i2 = $i0 + ($r5.left + $r5.right);
            $i0 = $i1 + ($r5.top + $r5.bottom);
            $i1 = getChildMeasureSpec(getWidth(), getWidthMode(), (((getPaddingLeft() + getPaddingRight()) + $r3.leftMargin) + $r3.rightMargin) + $i2, $r3.width, canScrollHorizontally());
            $i0 = getChildMeasureSpec(getHeight(), getHeightMode(), (((getPaddingTop() + getPaddingBottom()) + $r3.topMargin) + $r3.bottomMargin) + $i0, $r3.height, canScrollVertically());
            if (shouldMeasureChild($r1, $i1, $i0, $r3)) {
                $r1.measure($i1, $i0);
            }
        }

        @Deprecated
        public static int getChildMeasureSpec(@Deprecated int $i0, @Deprecated int $i1, @Deprecated int $i2, @Deprecated boolean $z0) throws  {
            $i0 = Math.max(0, $i0 - $i1);
            $i1 = 0;
            int $i3 = 0;
            if ($z0) {
                if ($i2 >= 0) {
                    $i1 = $i2;
                    $i3 = 1073741824;
                } else {
                    $i1 = 0;
                    $i3 = 0;
                }
            } else if ($i2 >= 0) {
                $i1 = $i2;
                $i3 = 1073741824;
            } else if ($i2 == -1) {
                $i1 = $i0;
                $i3 = 1073741824;
            } else if ($i2 == -2) {
                $i1 = $i0;
                $i3 = Integer.MIN_VALUE;
            }
            return MeasureSpec.makeMeasureSpec($i1, $i3);
        }

        public static int getChildMeasureSpec(int $i0, int $i1, int $i2, int $i3, boolean $z0) throws  {
            $i0 = Math.max(0, $i0 - $i2);
            $i2 = 0;
            int $i4 = 0;
            if ($z0) {
                if ($i3 >= 0) {
                    $i2 = $i3;
                    $i4 = 1073741824;
                } else if ($i3 == -1) {
                    switch ($i1) {
                        case Integer.MIN_VALUE:
                        case 1073741824:
                            $i2 = $i0;
                            $i4 = $i1;
                            break;
                        case 0:
                            $i2 = 0;
                            $i4 = 0;
                            break;
                        default:
                            break;
                    }
                } else if ($i3 == -2) {
                    $i2 = 0;
                    $i4 = 0;
                }
            } else if ($i3 >= 0) {
                $i2 = $i3;
                $i4 = 1073741824;
            } else if ($i3 == -1) {
                $i2 = $i0;
                $i4 = $i1;
            } else if ($i3 == -2) {
                $i2 = $i0;
                $i4 = ($i1 == Integer.MIN_VALUE || $i1 == 1073741824) ? Integer.MIN_VALUE : 0;
            }
            return MeasureSpec.makeMeasureSpec($i2, $i4);
        }

        public int getDecoratedMeasuredWidth(View $r1) throws  {
            Rect $r2 = ((LayoutParams) $r1.getLayoutParams()).mDecorInsets;
            return ($r1.getMeasuredWidth() + $r2.left) + $r2.right;
        }

        public int getDecoratedMeasuredHeight(View $r1) throws  {
            Rect $r2 = ((LayoutParams) $r1.getLayoutParams()).mDecorInsets;
            return ($r1.getMeasuredHeight() + $r2.top) + $r2.bottom;
        }

        public void layoutDecorated(View $r1, int $i0, int $i1, int $i2, int $i3) throws  {
            Rect $r2 = ((LayoutParams) $r1.getLayoutParams()).mDecorInsets;
            $r1.layout($r2.left + $i0, $r2.top + $i1, $i2 - $r2.right, $i3 - $r2.bottom);
        }

        public int getDecoratedLeft(View $r1) throws  {
            return $r1.getLeft() - getLeftDecorationWidth($r1);
        }

        public int getDecoratedTop(View $r1) throws  {
            return $r1.getTop() - getTopDecorationHeight($r1);
        }

        public int getDecoratedRight(View $r1) throws  {
            return $r1.getRight() + getRightDecorationWidth($r1);
        }

        public int getDecoratedBottom(View $r1) throws  {
            return $r1.getBottom() + getBottomDecorationHeight($r1);
        }

        public void calculateItemDecorationsForChild(View $r1, Rect $r2) throws  {
            if (this.mRecyclerView == null) {
                $r2.set(0, 0, 0, 0);
            } else {
                $r2.set(this.mRecyclerView.getItemDecorInsetsForChild($r1));
            }
        }

        public int getTopDecorationHeight(View $r1) throws  {
            return ((LayoutParams) $r1.getLayoutParams()).mDecorInsets.top;
        }

        public int getBottomDecorationHeight(View $r1) throws  {
            return ((LayoutParams) $r1.getLayoutParams()).mDecorInsets.bottom;
        }

        public int getLeftDecorationWidth(View $r1) throws  {
            return ((LayoutParams) $r1.getLayoutParams()).mDecorInsets.left;
        }

        public int getRightDecorationWidth(View $r1) throws  {
            return ((LayoutParams) $r1.getLayoutParams()).mDecorInsets.right;
        }

        public boolean requestChildRectangleOnScreen(RecyclerView $r1, View $r2, Rect $r3, boolean $z0) throws  {
            int $i6 = getPaddingLeft();
            int $i7 = getPaddingTop();
            int $i5 = getWidth() - getPaddingRight();
            int $i4 = getHeight() - getPaddingBottom();
            int $i1 = ($r2.getLeft() + $r3.left) - $r2.getScrollX();
            int $i3 = ($r2.getTop() + $r3.top) - $r2.getScrollY();
            int $i2 = $i1 + $r3.width();
            int $i0 = $i3 + $r3.height();
            int $i9 = Math.min(0, $i1 - $i6);
            int $i8 = Math.min(0, $i3 - $i7);
            int $i10 = Math.max(0, $i2 - $i5);
            $i4 = Math.max(0, $i0 - $i4);
            if (getLayoutDirection() == 1) {
                if ($i10 != 0) {
                    $i6 = $i10;
                } else {
                    $i6 = Math.max($i9, $i2 - $i5);
                }
            } else if ($i9 != 0) {
                $i6 = $i9;
            } else {
                $i6 = Math.min($i1 - $i6, $i10);
            }
            if ($i8 != 0) {
                $i7 = $i8;
            } else {
                $i7 = Math.min($i3 - $i7, $i4);
            }
            if ($i6 == 0 && $i7 == 0) {
                return false;
            }
            if ($z0) {
                $r1.scrollBy($i6, $i7);
            } else {
                $r1.smoothScrollBy($i6, $i7);
            }
            return true;
        }

        @Deprecated
        public boolean onRequestChildFocus(@Deprecated RecyclerView $r1, @Deprecated View child, @Deprecated View focused) throws  {
            return isSmoothScrolling() || $r1.isComputingLayout();
        }

        public boolean onRequestChildFocus(RecyclerView $r1, State state, View $r3, View $r4) throws  {
            return onRequestChildFocus($r1, $r3, $r4);
        }

        public void onAdapterChanged(Adapter oldAdapter, Adapter newAdapter) throws  {
        }

        public void onItemsChanged(RecyclerView recyclerView) throws  {
        }

        public void onItemsAdded(RecyclerView recyclerView, int positionStart, int itemCount) throws  {
        }

        public void onItemsRemoved(RecyclerView recyclerView, int positionStart, int itemCount) throws  {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int positionStart, int itemCount) throws  {
        }

        public void onItemsUpdated(RecyclerView $r1, int $i0, int $i1, Object payload) throws  {
            onItemsUpdated($r1, $i0, $i1);
        }

        public void onItemsMoved(RecyclerView recyclerView, int from, int to, int itemCount) throws  {
        }

        public void onMeasure(Recycler recycler, State state, int $i0, int $i1) throws  {
            this.mRecyclerView.defaultOnMeasure($i0, $i1);
        }

        public void setMeasuredDimension(int $i0, int $i1) throws  {
            this.mRecyclerView.setMeasuredDimension($i0, $i1);
        }

        public int getMinimumWidth() throws  {
            return ViewCompat.getMinimumWidth(this.mRecyclerView);
        }

        public int getMinimumHeight() throws  {
            return ViewCompat.getMinimumHeight(this.mRecyclerView);
        }

        public void onRestoreInstanceState(Parcelable state) throws  {
        }

        void stopSmoothScroller() throws  {
            if (this.mSmoothScroller != null) {
                this.mSmoothScroller.stop();
            }
        }

        private void onSmoothScrollerStopped(SmoothScroller $r1) throws  {
            if (this.mSmoothScroller == $r1) {
                this.mSmoothScroller = null;
            }
        }

        public void onScrollStateChanged(int state) throws  {
        }

        public void removeAndRecycleAllViews(Recycler $r1) throws  {
            for (int $i0 = getChildCount() - 1; $i0 >= 0; $i0--) {
                if (!RecyclerView.getChildViewHolderInt(getChildAt($i0)).shouldIgnore()) {
                    removeAndRecycleViewAt($i0, $r1);
                }
            }
        }

        void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat $r1) throws  {
            onInitializeAccessibilityNodeInfo(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, $r1);
        }

        public void onInitializeAccessibilityNodeInfo(Recycler $r1, State $r2, AccessibilityNodeInfoCompat $r3) throws  {
            if (ViewCompat.canScrollVertically(this.mRecyclerView, -1) || ViewCompat.canScrollHorizontally(this.mRecyclerView, -1)) {
                $r3.addAction(8192);
                $r3.setScrollable(true);
            }
            if (ViewCompat.canScrollVertically(this.mRecyclerView, 1) || ViewCompat.canScrollHorizontally(this.mRecyclerView, 1)) {
                $r3.addAction(4096);
                $r3.setScrollable(true);
            }
            $r3.setCollectionInfo(CollectionInfoCompat.obtain(getRowCountForAccessibility($r1, $r2), getColumnCountForAccessibility($r1, $r2), isLayoutHierarchical($r1, $r2), getSelectionModeForAccessibility($r1, $r2)));
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent $r1) throws  {
            onInitializeAccessibilityEvent(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, $r1);
        }

        public void onInitializeAccessibilityEvent(Recycler recycler, State state, AccessibilityEvent $r3) throws  {
            boolean $z0 = true;
            AccessibilityRecordCompat $r4 = AccessibilityEventCompat.asRecord($r3);
            if (this.mRecyclerView != null && $r4 != null) {
                if (!(ViewCompat.canScrollVertically(this.mRecyclerView, 1) || ViewCompat.canScrollVertically(this.mRecyclerView, -1) || ViewCompat.canScrollHorizontally(this.mRecyclerView, -1) || ViewCompat.canScrollHorizontally(this.mRecyclerView, 1))) {
                    $z0 = false;
                }
                $r4.setScrollable($z0);
                if (this.mRecyclerView.mAdapter != null) {
                    $r4.setItemCount(this.mRecyclerView.mAdapter.getItemCount());
                }
            }
        }

        void onInitializeAccessibilityNodeInfoForItem(View $r1, AccessibilityNodeInfoCompat $r2) throws  {
            ViewHolder $r3 = RecyclerView.getChildViewHolderInt($r1);
            if ($r3 != null && !$r3.isRemoved() && !this.mChildHelper.isHidden($r3.itemView)) {
                onInitializeAccessibilityNodeInfoForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, $r1, $r2);
            }
        }

        public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View $r3, AccessibilityNodeInfoCompat $r4) throws  {
            int $i1;
            int $i0 = canScrollVertically() ? getPosition($r3) : 0;
            if (canScrollHorizontally()) {
                $i1 = getPosition($r3);
            } else {
                $i1 = 0;
            }
            $r4.setCollectionItemInfo(CollectionItemInfoCompat.obtain($i0, 1, $i1, 1, false, false));
        }

        public void requestSimpleAnimationsInNextLayout() throws  {
            this.mRequestedSimpleAnimations = true;
        }

        public int getRowCountForAccessibility(Recycler recycler, State state) throws  {
            if (this.mRecyclerView == null) {
                return 1;
            }
            if (this.mRecyclerView.mAdapter == null) {
                return 1;
            }
            return canScrollVertically() ? this.mRecyclerView.mAdapter.getItemCount() : 1;
        }

        public int getColumnCountForAccessibility(Recycler recycler, State state) throws  {
            if (this.mRecyclerView == null) {
                return 1;
            }
            if (this.mRecyclerView.mAdapter == null) {
                return 1;
            }
            return canScrollHorizontally() ? this.mRecyclerView.mAdapter.getItemCount() : 1;
        }

        boolean performAccessibilityAction(int $i0, Bundle $r1) throws  {
            return performAccessibilityAction(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, $i0, $r1);
        }

        public boolean performAccessibilityAction(Recycler recycler, State state, int $i0, Bundle args) throws  {
            if (this.mRecyclerView == null) {
                return false;
            }
            int $i1 = 0;
            int $i2 = 0;
            switch ($i0) {
                case 4096:
                    if (ViewCompat.canScrollVertically(this.mRecyclerView, 1)) {
                        $i1 = (getHeight() - getPaddingTop()) - getPaddingBottom();
                    }
                    if (ViewCompat.canScrollHorizontally(this.mRecyclerView, 1)) {
                        $i2 = (getWidth() - getPaddingLeft()) - getPaddingRight();
                        break;
                    }
                    break;
                case 8192:
                    if (ViewCompat.canScrollVertically(this.mRecyclerView, -1)) {
                        $i1 = -((getHeight() - getPaddingTop()) - getPaddingBottom());
                    }
                    if (ViewCompat.canScrollHorizontally(this.mRecyclerView, -1)) {
                        $i2 = -((getWidth() - getPaddingLeft()) - getPaddingRight());
                        break;
                    }
                    break;
                default:
                    break;
            }
            if ($i1 == 0 && $i2 == 0) {
                return false;
            }
            this.mRecyclerView.scrollBy($i2, $i1);
            return true;
        }

        boolean performAccessibilityActionForItem(View $r1, int $i0, Bundle $r2) throws  {
            return performAccessibilityActionForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, $r1, $i0, $r2);
        }

        public static Properties getProperties(Context $r0, AttributeSet $r1, int $i0, int $i1) throws  {
            Properties $r2 = new Properties();
            TypedArray $r4 = $r0.obtainStyledAttributes($r1, C0195R.styleable.RecyclerView, $i0, $i1);
            $r2.orientation = $r4.getInt(C0195R.styleable.RecyclerView_android_orientation, 1);
            $r2.spanCount = $r4.getInt(C0195R.styleable.RecyclerView_spanCount, 1);
            $r2.reverseLayout = $r4.getBoolean(C0195R.styleable.RecyclerView_reverseLayout, false);
            $r2.stackFromEnd = $r4.getBoolean(C0195R.styleable.RecyclerView_stackFromEnd, false);
            $r4.recycle();
            return $r2;
        }

        void setExactMeasureSpecsFrom(RecyclerView $r1) throws  {
            setMeasureSpecs(MeasureSpec.makeMeasureSpec($r1.getWidth(), 1073741824), MeasureSpec.makeMeasureSpec($r1.getHeight(), 1073741824));
        }

        boolean hasFlexibleChildInBothOrientations() throws  {
            int $i0 = getChildCount();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                android.view.ViewGroup.LayoutParams $r2 = getChildAt($i1).getLayoutParams();
                if ($r2.width < 0 && $r2.height < 0) {
                    return true;
                }
            }
            return false;
        }
    }

    public static abstract class SmoothScroller {
        private LayoutManager mLayoutManager;
        private boolean mPendingInitialRun;
        private RecyclerView mRecyclerView;
        private final Action mRecyclingAction = new Action(0, 0);
        private boolean mRunning;
        private int mTargetPosition = -1;
        private View mTargetView;

        public static class Action {
            public static final int UNDEFINED_DURATION = Integer.MIN_VALUE;
            private boolean changed;
            private int consecutiveUpdates;
            private int mDuration;
            private int mDx;
            private int mDy;
            private Interpolator mInterpolator;
            private int mJumpToPosition;

            public Action(int $i0, int $i1) throws  {
                this($i0, $i1, Integer.MIN_VALUE, null);
            }

            public Action(int $i0, int $i1, int $i2) throws  {
                this($i0, $i1, $i2, null);
            }

            public Action(int $i0, int $i1, int $i2, Interpolator $r1) throws  {
                this.mJumpToPosition = -1;
                this.changed = false;
                this.consecutiveUpdates = 0;
                this.mDx = $i0;
                this.mDy = $i1;
                this.mDuration = $i2;
                this.mInterpolator = $r1;
            }

            public void jumpTo(int $i0) throws  {
                this.mJumpToPosition = $i0;
            }

            boolean hasJumpTarget() throws  {
                return this.mJumpToPosition >= 0;
            }

            private void runIfNecessary(RecyclerView $r1) throws  {
                if (this.mJumpToPosition >= 0) {
                    int $i0 = this.mJumpToPosition;
                    this.mJumpToPosition = -1;
                    $r1.jumpToPositionForSmoothScroller($i0);
                    this.changed = false;
                } else if (this.changed) {
                    validate();
                    if (this.mInterpolator != null) {
                        $r1.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration, this.mInterpolator);
                    } else if (this.mDuration == Integer.MIN_VALUE) {
                        $r1.mViewFlinger.smoothScrollBy(this.mDx, this.mDy);
                    } else {
                        $r1.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration);
                    }
                    this.consecutiveUpdates++;
                    if (this.consecutiveUpdates > 10) {
                        Log.e(RecyclerView.TAG, "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
                    }
                    this.changed = false;
                } else {
                    this.consecutiveUpdates = 0;
                }
            }

            private void validate() throws  {
                if (this.mInterpolator != null && this.mDuration < 1) {
                    throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
                } else if (this.mDuration < 1) {
                    throw new IllegalStateException("Scroll duration must be a positive number");
                }
            }

            public int getDx() throws  {
                return this.mDx;
            }

            public void setDx(int $i0) throws  {
                this.changed = true;
                this.mDx = $i0;
            }

            public int getDy() throws  {
                return this.mDy;
            }

            public void setDy(int $i0) throws  {
                this.changed = true;
                this.mDy = $i0;
            }

            public int getDuration() throws  {
                return this.mDuration;
            }

            public void setDuration(int $i0) throws  {
                this.changed = true;
                this.mDuration = $i0;
            }

            public Interpolator getInterpolator() throws  {
                return this.mInterpolator;
            }

            public void setInterpolator(Interpolator $r1) throws  {
                this.changed = true;
                this.mInterpolator = $r1;
            }

            public void update(int $i0, int $i1, int $i2, Interpolator $r1) throws  {
                this.mDx = $i0;
                this.mDy = $i1;
                this.mDuration = $i2;
                this.mInterpolator = $r1;
                this.changed = true;
            }
        }

        protected abstract void onSeekTargetStep(int i, int i2, State state, Action action) throws ;

        protected abstract void onStart() throws ;

        protected abstract void onStop() throws ;

        protected abstract void onTargetFound(View view, State state, Action action) throws ;

        void start(RecyclerView $r1, LayoutManager $r2) throws  {
            this.mRecyclerView = $r1;
            this.mLayoutManager = $r2;
            if (this.mTargetPosition == -1) {
                throw new IllegalArgumentException("Invalid target position");
            }
            this.mRecyclerView.mState.mTargetPosition = this.mTargetPosition;
            this.mRunning = true;
            this.mPendingInitialRun = true;
            this.mTargetView = findViewByPosition(getTargetPosition());
            onStart();
            this.mRecyclerView.mViewFlinger.postOnAnimation();
        }

        public void setTargetPosition(int $i0) throws  {
            this.mTargetPosition = $i0;
        }

        @Nullable
        public LayoutManager getLayoutManager() throws  {
            return this.mLayoutManager;
        }

        protected final void stop() throws  {
            if (this.mRunning) {
                onStop();
                this.mRecyclerView.mState.mTargetPosition = -1;
                this.mTargetView = null;
                this.mTargetPosition = -1;
                this.mPendingInitialRun = false;
                this.mRunning = false;
                this.mLayoutManager.onSmoothScrollerStopped(this);
                this.mLayoutManager = null;
                this.mRecyclerView = null;
            }
        }

        public boolean isPendingInitialRun() throws  {
            return this.mPendingInitialRun;
        }

        public boolean isRunning() throws  {
            return this.mRunning;
        }

        public int getTargetPosition() throws  {
            return this.mTargetPosition;
        }

        private void onAnimation(int $i0, int $i1) throws  {
            RecyclerView $r1 = this.mRecyclerView;
            if (!this.mRunning || this.mTargetPosition == -1 || $r1 == null) {
                stop();
            }
            this.mPendingInitialRun = false;
            if (this.mTargetView != null) {
                if (getChildPosition(this.mTargetView) == this.mTargetPosition) {
                    onTargetFound(this.mTargetView, $r1.mState, this.mRecyclingAction);
                    this.mRecyclingAction.runIfNecessary($r1);
                    stop();
                } else {
                    Log.e(RecyclerView.TAG, "Passed over target position while smooth scrolling.");
                    this.mTargetView = null;
                }
            }
            if (this.mRunning) {
                onSeekTargetStep($i0, $i1, $r1.mState, this.mRecyclingAction);
                boolean $z0 = this.mRecyclingAction.hasJumpTarget();
                this.mRecyclingAction.runIfNecessary($r1);
                if (!$z0) {
                    return;
                }
                if (this.mRunning) {
                    this.mPendingInitialRun = true;
                    $r1.mViewFlinger.postOnAnimation();
                    return;
                }
                stop();
            }
        }

        public int getChildPosition(View $r1) throws  {
            return this.mRecyclerView.getChildLayoutPosition($r1);
        }

        public int getChildCount() throws  {
            return this.mRecyclerView.mLayout.getChildCount();
        }

        public View findViewByPosition(int $i0) throws  {
            return this.mRecyclerView.mLayout.findViewByPosition($i0);
        }

        @Deprecated
        public void instantScrollToPosition(@Deprecated int $i0) throws  {
            this.mRecyclerView.scrollToPosition($i0);
        }

        protected void onChildAttachedToWindow(View $r1) throws  {
            if (getChildPosition($r1) == getTargetPosition()) {
                this.mTargetView = $r1;
            }
        }

        protected void normalize(PointF $r1) throws  {
            double $d0 = Math.sqrt((double) (($r1.x * $r1.x) + ($r1.y * $r1.y)));
            $r1.x = (float) (((double) $r1.x) / $d0);
            $r1.y = (float) (((double) $r1.y) / $d0);
        }
    }

    class C02501 implements Runnable {
        C02501() throws  {
        }

        public void run() throws  {
            if (RecyclerView.this.mFirstLayoutComplete && !RecyclerView.this.isLayoutRequested()) {
                if (RecyclerView.this.mLayoutFrozen) {
                    RecyclerView.this.mLayoutRequestEaten = true;
                } else {
                    RecyclerView.this.consumePendingUpdateOperations();
                }
            }
        }
    }

    class C02512 implements Runnable {
        C02512() throws  {
        }

        public void run() throws  {
            if (RecyclerView.this.mItemAnimator != null) {
                RecyclerView.this.mItemAnimator.runPendingAnimations();
            }
            RecyclerView.this.mPostedAnimatorRunner = false;
        }
    }

    static class C02523 implements Interpolator {
        C02523() throws  {
        }

        public float getInterpolation(float $f0) throws  {
            $f0 -= 1.0f;
            return (((($f0 * $f0) * $f0) * $f0) * $f0) + 1.0f;
        }
    }

    class C02534 implements ProcessCallback {
        C02534() throws  {
        }

        public void processDisappeared(ViewHolder $r1, @NonNull ItemHolderInfo $r2, @Nullable ItemHolderInfo $r3) throws  {
            RecyclerView.this.mRecycler.unscrapView($r1);
            RecyclerView.this.animateDisappearance($r1, $r2, $r3);
        }

        public void processAppeared(ViewHolder $r1, ItemHolderInfo $r2, ItemHolderInfo $r3) throws  {
            RecyclerView.this.animateAppearance($r1, $r2, $r3);
        }

        public void processPersistent(ViewHolder $r1, @NonNull ItemHolderInfo $r2, @NonNull ItemHolderInfo $r3) throws  {
            $r1.setIsRecyclable(false);
            if (RecyclerView.this.mDataSetHasChangedAfterLayout) {
                if (RecyclerView.this.mItemAnimator.animateChange($r1, $r1, $r2, $r3)) {
                    RecyclerView.this.postAnimationRunner();
                }
            } else if (RecyclerView.this.mItemAnimator.animatePersistence($r1, $r2, $r3)) {
                RecyclerView.this.postAnimationRunner();
            }
        }

        public void unused(ViewHolder $r1) throws  {
            RecyclerView.this.mLayout.removeAndRecycleView($r1.itemView, RecyclerView.this.mRecycler);
        }
    }

    class C02545 implements Callback {
        C02545() throws  {
        }

        public int getChildCount() throws  {
            return RecyclerView.this.getChildCount();
        }

        public void addView(View $r1, int $i0) throws  {
            RecyclerView.this.addView($r1, $i0);
            RecyclerView.this.dispatchChildAttached($r1);
        }

        public int indexOfChild(View $r1) throws  {
            return RecyclerView.this.indexOfChild($r1);
        }

        public void removeViewAt(int $i0) throws  {
            View $r1 = RecyclerView.this.getChildAt($i0);
            if ($r1 != null) {
                RecyclerView.this.dispatchChildDetached($r1);
            }
            RecyclerView.this.removeViewAt($i0);
        }

        public View getChildAt(int $i0) throws  {
            return RecyclerView.this.getChildAt($i0);
        }

        public void removeAllViews() throws  {
            int $i0 = getChildCount();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                RecyclerView.this.dispatchChildDetached(getChildAt($i1));
            }
            RecyclerView.this.removeAllViews();
        }

        public ViewHolder getChildViewHolder(View $r1) throws  {
            return RecyclerView.getChildViewHolderInt($r1);
        }

        public void attachViewToParent(View $r1, int $i0, android.view.ViewGroup.LayoutParams $r2) throws  {
            ViewHolder $r3 = RecyclerView.getChildViewHolderInt($r1);
            if ($r3 != null) {
                if ($r3.isTmpDetached() || $r3.shouldIgnore()) {
                    $r3.clearTmpDetachFlag();
                } else {
                    throw new IllegalArgumentException("Called attach on a child which is not detached: " + $r3);
                }
            }
            RecyclerView.this.attachViewToParent($r1, $i0, $r2);
        }

        public void detachViewFromParent(int $i0) throws  {
            View $r1 = getChildAt($i0);
            if ($r1 != null) {
                ViewHolder $r2 = RecyclerView.getChildViewHolderInt($r1);
                if ($r2 != null) {
                    if (!$r2.isTmpDetached() || $r2.shouldIgnore()) {
                        $r2.addFlags(256);
                    } else {
                        throw new IllegalArgumentException("called detach on an already detached child " + $r2);
                    }
                }
            }
            RecyclerView.this.detachViewFromParent($i0);
        }

        public void onEnteredHiddenState(View $r1) throws  {
            ViewHolder $r2 = RecyclerView.getChildViewHolderInt($r1);
            if ($r2 != null) {
                $r2.onEnteredHiddenState();
            }
        }

        public void onLeftHiddenState(View $r1) throws  {
            ViewHolder $r2 = RecyclerView.getChildViewHolderInt($r1);
            if ($r2 != null) {
                $r2.onLeftHiddenState();
            }
        }
    }

    class C02556 implements Callback {
        C02556() throws  {
        }

        public ViewHolder findViewHolder(int $i0) throws  {
            ViewHolder $r2 = RecyclerView.this.findViewHolderForPosition($i0, true);
            if ($r2 == null) {
                return null;
            }
            return RecyclerView.this.mChildHelper.isHidden($r2.itemView) ? null : $r2;
        }

        public void offsetPositionsForRemovingInvisible(int $i0, int $i1) throws  {
            RecyclerView.this.offsetPositionRecordsForRemove($i0, $i1, true);
            RecyclerView.this.mItemsAddedOrRemoved = true;
            State.access$1712(RecyclerView.this.mState, $i1);
        }

        public void offsetPositionsForRemovingLaidOutOrNewView(int $i0, int $i1) throws  {
            RecyclerView.this.offsetPositionRecordsForRemove($i0, $i1, false);
            RecyclerView.this.mItemsAddedOrRemoved = true;
        }

        public void markViewHoldersUpdated(int $i0, int $i1, Object $r1) throws  {
            RecyclerView.this.viewRangeUpdate($i0, $i1, $r1);
            RecyclerView.this.mItemsChanged = true;
        }

        public void onDispatchFirstPass(UpdateOp $r1) throws  {
            dispatchUpdate($r1);
        }

        void dispatchUpdate(UpdateOp $r1) throws  {
            switch ($r1.cmd) {
                case 1:
                    RecyclerView.this.mLayout.onItemsAdded(RecyclerView.this, $r1.positionStart, $r1.itemCount);
                    return;
                case 2:
                    RecyclerView.this.mLayout.onItemsRemoved(RecyclerView.this, $r1.positionStart, $r1.itemCount);
                    return;
                case 3:
                case 5:
                case 6:
                case 7:
                    break;
                case 4:
                    RecyclerView.this.mLayout.onItemsUpdated(RecyclerView.this, $r1.positionStart, $r1.itemCount, $r1.payload);
                    return;
                case 8:
                    RecyclerView.this.mLayout.onItemsMoved(RecyclerView.this, $r1.positionStart, $r1.itemCount, 1);
                    return;
                default:
                    break;
            }
        }

        public void onDispatchSecondPass(UpdateOp $r1) throws  {
            dispatchUpdate($r1);
        }

        public void offsetPositionsForAdd(int $i0, int $i1) throws  {
            RecyclerView.this.offsetPositionRecordsForInsert($i0, $i1);
            RecyclerView.this.mItemsAddedOrRemoved = true;
        }

        public void offsetPositionsForMove(int $i0, int $i1) throws  {
            RecyclerView.this.offsetPositionRecordsForMove($i0, $i1);
            RecyclerView.this.mItemsAddedOrRemoved = true;
        }
    }

    public static abstract class Adapter<VH extends ViewHolder> {
        private boolean mHasStableIds = false;
        private final AdapterDataObservable mObservable = new AdapterDataObservable();

        public abstract int getItemCount() throws ;

        public long getItemId(int position) throws  {
            return -1;
        }

        public int getItemViewType(int position) throws  {
            return 0;
        }

        public abstract void onBindViewHolder(@Signature({"(TVH;I)V"}) VH vh, @Signature({"(TVH;I)V"}) int i) throws ;

        public abstract VH onCreateViewHolder(@Signature({"(", "Landroid/view/ViewGroup;", "I)TVH;"}) ViewGroup viewGroup, @Signature({"(", "Landroid/view/ViewGroup;", "I)TVH;"}) int i) throws ;

        public boolean onFailedToRecycleView(@Signature({"(TVH;)Z"}) VH vh) throws  {
            return false;
        }

        public void onBindViewHolder(@Signature({"(TVH;I", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)V"}) VH $r1, @Signature({"(TVH;I", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)V"}) int $i0, @Signature({"(TVH;I", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)V"}) List<Object> list) throws  {
            onBindViewHolder($r1, $i0);
        }

        public final VH createViewHolder(@Signature({"(", "Landroid/view/ViewGroup;", "I)TVH;"}) ViewGroup $r1, @Signature({"(", "Landroid/view/ViewGroup;", "I)TVH;"}) int $i0) throws  {
            TraceCompat.beginSection(RecyclerView.TRACE_CREATE_VIEW_TAG);
            ViewHolder $r2 = onCreateViewHolder($r1, $i0);
            $r2.mItemViewType = $i0;
            TraceCompat.endSection();
            return $r2;
        }

        public final void bindViewHolder(@Signature({"(TVH;I)V"}) VH $r1, @Signature({"(TVH;I)V"}) int $i0) throws  {
            $r1.mPosition = $i0;
            if (hasStableIds()) {
                $r1.mItemId = getItemId($i0);
            }
            $r1.setFlags(1, DisplayStrings.DS_ONE_YEAR_AGO_UC);
            TraceCompat.beginSection(RecyclerView.TRACE_BIND_VIEW_TAG);
            onBindViewHolder($r1, $i0, $r1.getUnmodifiedPayloads());
            $r1.clearPayload();
            TraceCompat.endSection();
        }

        public void setHasStableIds(boolean $z0) throws  {
            if (hasObservers()) {
                throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
            }
            this.mHasStableIds = $z0;
        }

        public final boolean hasStableIds() throws  {
            return this.mHasStableIds;
        }

        public void onViewRecycled(@Signature({"(TVH;)V"}) VH vh) throws  {
        }

        public void onViewAttachedToWindow(@Signature({"(TVH;)V"}) VH vh) throws  {
        }

        public void onViewDetachedFromWindow(@Signature({"(TVH;)V"}) VH vh) throws  {
        }

        public final boolean hasObservers() throws  {
            return this.mObservable.hasObservers();
        }

        public void registerAdapterDataObserver(AdapterDataObserver $r1) throws  {
            this.mObservable.registerObserver($r1);
        }

        public void unregisterAdapterDataObserver(AdapterDataObserver $r1) throws  {
            this.mObservable.unregisterObserver($r1);
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) throws  {
        }

        public void onDetachedFromRecyclerView(RecyclerView recyclerView) throws  {
        }

        public final void notifyDataSetChanged() throws  {
            this.mObservable.notifyChanged();
        }

        public final void notifyItemChanged(int $i0) throws  {
            this.mObservable.notifyItemRangeChanged($i0, 1);
        }

        public final void notifyItemChanged(int $i0, Object $r1) throws  {
            this.mObservable.notifyItemRangeChanged($i0, 1, $r1);
        }

        public final void notifyItemRangeChanged(int $i0, int $i1) throws  {
            this.mObservable.notifyItemRangeChanged($i0, $i1);
        }

        public final void notifyItemRangeChanged(int $i0, int $i1, Object $r1) throws  {
            this.mObservable.notifyItemRangeChanged($i0, $i1, $r1);
        }

        public final void notifyItemInserted(int $i0) throws  {
            this.mObservable.notifyItemRangeInserted($i0, 1);
        }

        public final void notifyItemMoved(int $i0, int $i1) throws  {
            this.mObservable.notifyItemMoved($i0, $i1);
        }

        public final void notifyItemRangeInserted(int $i0, int $i1) throws  {
            this.mObservable.notifyItemRangeInserted($i0, $i1);
        }

        public final void notifyItemRemoved(int $i0) throws  {
            this.mObservable.notifyItemRangeRemoved($i0, 1);
        }

        public final void notifyItemRangeRemoved(int $i0, int $i1) throws  {
            this.mObservable.notifyItemRangeRemoved($i0, $i1);
        }
    }

    static class AdapterDataObservable extends Observable<AdapterDataObserver> {
        AdapterDataObservable() throws  {
        }

        public boolean hasObservers() throws  {
            return !this.mObservers.isEmpty();
        }

        public void notifyChanged() throws  {
            for (int $i0 = this.mObservers.size() - 1; $i0 >= 0; $i0--) {
                ((AdapterDataObserver) this.mObservers.get($i0)).onChanged();
            }
        }

        public void notifyItemRangeChanged(int $i0, int $i1) throws  {
            notifyItemRangeChanged($i0, $i1, null);
        }

        public void notifyItemRangeChanged(int $i0, int $i1, Object $r1) throws  {
            for (int $i2 = this.mObservers.size() - 1; $i2 >= 0; $i2--) {
                ((AdapterDataObserver) this.mObservers.get($i2)).onItemRangeChanged($i0, $i1, $r1);
            }
        }

        public void notifyItemRangeInserted(int $i0, int $i1) throws  {
            for (int $i2 = this.mObservers.size() - 1; $i2 >= 0; $i2--) {
                ((AdapterDataObserver) this.mObservers.get($i2)).onItemRangeInserted($i0, $i1);
            }
        }

        public void notifyItemRangeRemoved(int $i0, int $i1) throws  {
            for (int $i2 = this.mObservers.size() - 1; $i2 >= 0; $i2--) {
                ((AdapterDataObserver) this.mObservers.get($i2)).onItemRangeRemoved($i0, $i1);
            }
        }

        public void notifyItemMoved(int $i0, int $i1) throws  {
            for (int $i2 = this.mObservers.size() - 1; $i2 >= 0; $i2--) {
                ((AdapterDataObserver) this.mObservers.get($i2)).onItemRangeMoved($i0, $i1, 1);
            }
        }
    }

    public static abstract class AdapterDataObserver {
        public void onChanged() throws  {
        }

        public void onItemRangeChanged(int positionStart, int itemCount) throws  {
        }

        public void onItemRangeChanged(int $i0, int $i1, Object payload) throws  {
            onItemRangeChanged($i0, $i1);
        }

        public void onItemRangeInserted(int positionStart, int itemCount) throws  {
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) throws  {
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) throws  {
        }
    }

    public interface ChildDrawingOrderCallback {
        int onGetChildDrawingOrder(int i, int i2) throws ;
    }

    private class ItemAnimatorRestoreListener implements ItemAnimatorListener {
        private ItemAnimatorRestoreListener() throws  {
        }

        public void onAnimationFinished(ViewHolder $r1) throws  {
            $r1.setIsRecyclable(true);
            if ($r1.mShadowedHolder != null && $r1.mShadowingHolder == null) {
                $r1.mShadowedHolder = null;
            }
            $r1.mShadowingHolder = null;
            if (!$r1.shouldBeKeptAsChild() && !RecyclerView.this.removeAnimatingView($r1.itemView) && $r1.isTmpDetached()) {
                RecyclerView.this.removeDetachedView($r1.itemView, false);
            }
        }
    }

    public static abstract class ItemDecoration {
        public void onDraw(Canvas $r1, RecyclerView $r2, State state) throws  {
            onDraw($r1, $r2);
        }

        @Deprecated
        public void onDraw(@Deprecated Canvas c, @Deprecated RecyclerView parent) throws  {
        }

        public void onDrawOver(Canvas $r1, RecyclerView $r2, State state) throws  {
            onDrawOver($r1, $r2);
        }

        @Deprecated
        public void onDrawOver(@Deprecated Canvas c, @Deprecated RecyclerView parent) throws  {
        }

        @Deprecated
        public void getItemOffsets(@Deprecated Rect $r1, @Deprecated int itemPosition, @Deprecated RecyclerView parent) throws  {
            $r1.set(0, 0, 0, 0);
        }

        public void getItemOffsets(Rect $r1, View $r2, RecyclerView $r3, State state) throws  {
            getItemOffsets($r1, ((LayoutParams) $r2.getLayoutParams()).getViewLayoutPosition(), $r3);
        }
    }

    public interface OnChildAttachStateChangeListener {
        void onChildViewAttachedToWindow(View view) throws ;

        void onChildViewDetachedFromWindow(View view) throws ;
    }

    public interface OnItemTouchListener {
        boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) throws ;

        void onRequestDisallowInterceptTouchEvent(boolean z) throws ;

        void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) throws ;
    }

    public static abstract class OnScrollListener {
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) throws  {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) throws  {
        }
    }

    public static class RecycledViewPool {
        private static final int DEFAULT_MAX_SCRAP = 5;
        private int mAttachCount = 0;
        private SparseIntArray mMaxScrap = new SparseIntArray();
        private SparseArray<ArrayList<ViewHolder>> mScrap = new SparseArray();

        public void clear() throws  {
            this.mScrap.clear();
        }

        public void setMaxRecycledViews(int $i0, int $i1) throws  {
            this.mMaxScrap.put($i0, $i1);
            ArrayList $r4 = (ArrayList) this.mScrap.get($i0);
            if ($r4 != null) {
                while ($r4.size() > $i1) {
                    $r4.remove($r4.size() - 1);
                }
            }
        }

        public ViewHolder getRecycledView(int $i0) throws  {
            ArrayList $r3 = (ArrayList) this.mScrap.get($i0);
            if ($r3 == null || $r3.isEmpty()) {
                return null;
            }
            $i0 = $r3.size() - 1;
            ViewHolder $r4 = (ViewHolder) $r3.get($i0);
            $r3.remove($i0);
            return $r4;
        }

        int size() throws  {
            int $i0 = 0;
            for (int $i1 = 0; $i1 < this.mScrap.size(); $i1++) {
                ArrayList $r3 = (ArrayList) this.mScrap.valueAt($i1);
                if ($r3 != null) {
                    $i0 += $r3.size();
                }
            }
            return $i0;
        }

        public void putRecycledView(ViewHolder $r1) throws  {
            int $i0 = $r1.getItemViewType();
            ArrayList $r2 = getScrapHeapForType($i0);
            if (this.mMaxScrap.get($i0) > $r2.size()) {
                $r1.resetInternal();
                $r2.add($r1);
            }
        }

        void attach(Adapter adapter) throws  {
            this.mAttachCount++;
        }

        void detach() throws  {
            this.mAttachCount--;
        }

        void onAdapterChanged(Adapter $r1, Adapter $r2, boolean $z0) throws  {
            if ($r1 != null) {
                detach();
            }
            if (!$z0 && this.mAttachCount == 0) {
                clear();
            }
            if ($r2 != null) {
                attach($r2);
            }
        }

        private ArrayList<ViewHolder> getScrapHeapForType(@Signature({"(I)", "Ljava/util/ArrayList", "<", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", ">;"}) int $i0) throws  {
            ArrayList $r3 = (ArrayList) this.mScrap.get($i0);
            if ($r3 != null) {
                return $r3;
            }
            $r3 = new ArrayList();
            this.mScrap.put($i0, $r3);
            if (this.mMaxScrap.indexOfKey($i0) >= 0) {
                return $r3;
            }
            this.mMaxScrap.put($i0, 5);
            return $r3;
        }
    }

    public final class Recycler {
        private static final int DEFAULT_CACHE_SIZE = 2;
        final ArrayList<ViewHolder> mAttachedScrap = new ArrayList();
        final ArrayList<ViewHolder> mCachedViews = new ArrayList();
        private ArrayList<ViewHolder> mChangedScrap = null;
        private RecycledViewPool mRecyclerPool;
        private final List<ViewHolder> mUnmodifiableAttachedScrap = Collections.unmodifiableList(this.mAttachedScrap);
        private ViewCacheExtension mViewCacheExtension;
        private int mViewCacheMax = 2;

        android.view.View getViewForPosition(int r37, boolean r38) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:93:0x02d5 in {4, 9, 18, 19, 20, 23, 29, 33, 34, 39, 48, 52, 58, 60, 68, 73, 76, 80, 82, 88, 90, 92, 94, 96} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r36 = this;
            if (r37 < 0) goto L_0x0010;
        L_0x0002:
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r7 = r6.mState;
            r8 = r7.getItemCount();
            r0 = r37;
            if (r0 < r8) goto L_0x004d;
        L_0x0010:
            r9 = new java.lang.IndexOutOfBoundsException;
            r10 = new java.lang.StringBuilder;
            r10.<init>();
            r11 = "Invalid item position ";
            r10 = r10.append(r11);
            r0 = r37;
            r10 = r10.append(r0);
            r11 = "(";
            r10 = r10.append(r11);
            r0 = r37;
            r10 = r10.append(r0);
            r11 = "). Item count:";
            r10 = r10.append(r11);
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r7 = r6.mState;
            r37 = r7.getItemCount();
            r0 = r37;
            r10 = r10.append(r0);
            r12 = r10.toString();
            r9.<init>(r12);
            throw r9;
        L_0x004d:
            r13 = 0;
            r14 = 0;
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r7 = r6.mState;
            r15 = r7.isPreLayout();
            if (r15 == 0) goto L_0x0068;
        L_0x005b:
            r0 = r36;
            r1 = r37;
            r16 = r0.getChangedScrapViewForPosition(r1);
            r14 = r16;
            if (r16 == 0) goto L_0x0123;
        L_0x0067:
            r13 = 1;
        L_0x0068:
            if (r14 != 0) goto L_0x00b9;
        L_0x006a:
            r17 = -1;
            r0 = r36;
            r1 = r37;
            r2 = r17;
            r3 = r38;
            r16 = r0.getScrapViewForPosition(r1, r2, r3);
            r14 = r16;
            if (r16 == 0) goto L_0x00b9;
        L_0x007c:
            r0 = r36;
            r1 = r16;
            r15 = r0.validateViewHolderForOffsetPosition(r1);
            if (r15 != 0) goto L_0x0134;
        L_0x0086:
            if (r38 != 0) goto L_0x00b8;
        L_0x0088:
            r17 = 4;
            r0 = r16;
            r1 = r17;
            r0.addFlags(r1);
            r0 = r16;
            r15 = r0.isScrap();
            if (r15 == 0) goto L_0x0125;
        L_0x0099:
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r0 = r16;
            r0 = r0.itemView;
            r18 = r0;
            r17 = 0;
            r0 = r18;
            r1 = r17;
            r6.removeDetachedView(r0, r1);
            r0 = r16;
            r0.unScrap();
        L_0x00b1:
            r0 = r36;
            r1 = r16;
            r0.recycleViewHolderInternal(r1);
        L_0x00b8:
            r14 = 0;
        L_0x00b9:
            if (r14 != 0) goto L_0x01f9;
        L_0x00bb:
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r0 = r6.mAdapterHelper;
            r19 = r0;
            goto L_0x00c7;
        L_0x00c4:
            goto L_0x0068;
        L_0x00c7:
            r1 = r37;
            r8 = r0.findPositionOffset(r1);
            if (r8 < 0) goto L_0x00e1;
        L_0x00cf:
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r20 = r6.mAdapter;
            r0 = r20;
            r21 = r0.getItemCount();
            r0 = r21;
            if (r8 < r0) goto L_0x0136;
        L_0x00e1:
            r9 = new java.lang.IndexOutOfBoundsException;
            r10 = new java.lang.StringBuilder;
            r10.<init>();
            r11 = "Inconsistency detected. Invalid item position ";
            r10 = r10.append(r11);
            r0 = r37;
            r10 = r10.append(r0);
            r11 = "(offset:";
            r10 = r10.append(r11);
            r10 = r10.append(r8);
            r11 = ").";
            r10 = r10.append(r11);
            r11 = "state:";
            r10 = r10.append(r11);
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r7 = r6.mState;
            r37 = r7.getItemCount();
            r0 = r37;
            r10 = r10.append(r0);
            r12 = r10.toString();
            r9.<init>(r12);
            throw r9;
        L_0x0123:
            r13 = 0;
            goto L_0x00c4;
        L_0x0125:
            r0 = r16;
            r15 = r0.wasReturnedFromScrap();
            if (r15 == 0) goto L_0x00b1;
        L_0x012d:
            r0 = r16;
            r0.clearReturnedFromScrapFlag();
            goto L_0x00b1;
        L_0x0134:
            r13 = 1;
            goto L_0x00b9;
        L_0x0136:
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r20 = r6.mAdapter;
            r0 = r20;
            r21 = r0.getItemViewType(r8);
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r20 = r6.mAdapter;
            r0 = r20;
            r15 = r0.hasStableIds();
            if (r15 == 0) goto L_0x0177;
        L_0x0154:
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r20 = r6.mAdapter;
            r0 = r20;
            r22 = r0.getItemId(r8);
            r0 = r36;
            r1 = r22;
            r3 = r21;
            r4 = r38;
            r16 = r0.getScrapViewForId(r1, r3, r4);
            r14 = r16;
            if (r16 == 0) goto L_0x0177;
        L_0x0172:
            r0 = r16;
            r0.mPosition = r8;
            r13 = 1;
        L_0x0177:
            if (r14 != 0) goto L_0x01bd;
        L_0x0179:
            r0 = r36;
            r0 = r0.mViewCacheExtension;
            r24 = r0;
            if (r24 == 0) goto L_0x01bd;
        L_0x0181:
            r0 = r36;
            r0 = r0.mViewCacheExtension;
            r24 = r0;
            r1 = r36;
            r2 = r37;
            r3 = r21;
            r18 = r0.getViewForPositionAndType(r1, r2, r3);
            if (r18 == 0) goto L_0x01bd;
        L_0x0193:
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r0 = r18;
            r16 = r6.getChildViewHolder(r0);
            r14 = r16;
            if (r16 != 0) goto L_0x01ab;
        L_0x01a1:
            r25 = new java.lang.IllegalArgumentException;
            r11 = "getViewForPositionAndType returned a view which does not have a ViewHolder";
            r0 = r25;
            r0.<init>(r11);
            throw r25;
        L_0x01ab:
            r0 = r16;
            r38 = r0.shouldIgnore();
            if (r38 == 0) goto L_0x01bd;
        L_0x01b3:
            r25 = new java.lang.IllegalArgumentException;
            r11 = "getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view.";
            r0 = r25;
            r0.<init>(r11);
            throw r25;
        L_0x01bd:
            if (r14 != 0) goto L_0x01e3;
        L_0x01bf:
            r0 = r36;
            r26 = r0.getRecycledViewPool();
            r0 = r26;
            r1 = r21;
            r16 = r0.getRecycledView(r1);
            r14 = r16;
            if (r16 == 0) goto L_0x01e3;
        L_0x01d1:
            r0 = r16;
            r0.resetInternal();
            r38 = android.support.v7.widget.RecyclerView.FORCE_INVALIDATE_DISPLAY_LIST;
            if (r38 == 0) goto L_0x01e3;
        L_0x01dc:
            r0 = r36;
            r1 = r16;
            r0.invalidateDisplayListInt(r1);
        L_0x01e3:
            if (r14 != 0) goto L_0x01f9;
        L_0x01e5:
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r20 = r6.mAdapter;
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r0 = r20;
            r1 = r21;
            r14 = r0.createViewHolder(r6, r1);
        L_0x01f9:
            if (r13 == 0) goto L_0x0251;
        L_0x01fb:
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r7 = r6.mState;
            r38 = r7.isPreLayout();
            if (r38 != 0) goto L_0x0251;
        L_0x0207:
            r17 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
            r0 = r17;
            r38 = r14.hasAnyOfTheFlags(r0);
            if (r38 == 0) goto L_0x0251;
        L_0x0211:
            r17 = 0;
            r27 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
            r0 = r17;
            r1 = r27;
            r14.setFlags(r0, r1);
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r7 = r6.mState;
            r38 = r7.mRunSimpleAnimations;
            if (r38 == 0) goto L_0x0251;
        L_0x0228:
            r8 = android.support.v7.widget.RecyclerView.ItemAnimator.buildAdapterChangeFlagsForAnimations(r14);
            r8 = r8 | 4096;
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r0 = r6.mItemAnimator;
            r28 = r0;
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r7 = r6.mState;
            r29 = r14.getUnmodifiedPayloads();
            r0 = r28;
            r1 = r29;
            r30 = r0.recordPreLayoutInformation(r7, r14, r8, r1);
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r0 = r30;
            r6.recordAnimationInfoIfBouncedHiddenView(r14, r0);
        L_0x0251:
            r38 = 0;
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r7 = r6.mState;
            r15 = r7.isPreLayout();
            if (r15 == 0) goto L_0x029f;
        L_0x025f:
            r15 = r14.isBound();
            if (r15 == 0) goto L_0x029f;
        L_0x0265:
            r0 = r37;
            r14.mPreLayoutPosition = r0;
        L_0x0269:
            r0 = r14.itemView;
            r18 = r0;
            r31 = r0.getLayoutParams();
            if (r31 != 0) goto L_0x0300;
        L_0x0273:
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r31 = r6.generateDefaultLayoutParams();
            r33 = r31;
            r33 = (android.support.v7.widget.RecyclerView.LayoutParams) r33;
            r32 = r33;
            r0 = r14.itemView;
            r18 = r0;
            r1 = r32;
            r0.setLayoutParams(r1);
        L_0x028a:
            r0 = r32;
            r0.mViewHolder = r14;
            if (r13 == 0) goto L_0x032d;
        L_0x0290:
            if (r38 == 0) goto L_0x032d;
        L_0x0292:
            r38 = 1;
        L_0x0294:
            r0 = r38;
            r1 = r32;
            r1.mPendingInvalidate = r0;
            r0 = r14.itemView;
            r18 = r0;
            return r18;
        L_0x029f:
            r15 = r14.isBound();
            if (r15 == 0) goto L_0x02b5;
        L_0x02a5:
            r15 = r14.needsUpdate();
            if (r15 != 0) goto L_0x02b5;
        L_0x02ab:
            goto L_0x02af;
        L_0x02ac:
            goto L_0x0269;
        L_0x02af:
            r15 = r14.isInvalid();
            if (r15 == 0) goto L_0x0269;
        L_0x02b5:
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r0 = r6.mAdapterHelper;
            r19 = r0;
            r1 = r37;
            r8 = r0.findPositionOffset(r1);
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r14.mOwnerRecyclerView = r6;
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r20 = r6.mAdapter;
            goto L_0x02d9;
        L_0x02d2:
            goto L_0x028a;
            goto L_0x02d9;
        L_0x02d6:
            goto L_0x028a;
        L_0x02d9:
            r0 = r20;
            r0.bindViewHolder(r14, r8);
            r0 = r14.itemView;
            r18 = r0;
            goto L_0x02e6;
        L_0x02e3:
            goto L_0x0294;
        L_0x02e6:
            r0 = r36;
            r1 = r18;
            r0.attachAccessibilityDelegate(r1);
            r38 = 1;
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r7 = r6.mState;
            r15 = r7.isPreLayout();
            if (r15 == 0) goto L_0x0269;
        L_0x02fb:
            r0 = r37;
            r14.mPreLayoutPosition = r0;
            goto L_0x02ac;
        L_0x0300:
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r0 = r31;
            r15 = r6.checkLayoutParams(r0);
            if (r15 != 0) goto L_0x0326;
        L_0x030c:
            r0 = r36;
            r6 = android.support.v7.widget.RecyclerView.this;
            r0 = r31;
            r31 = r6.generateLayoutParams(r0);
            r34 = r31;
            r34 = (android.support.v7.widget.RecyclerView.LayoutParams) r34;
            r32 = r34;
            r0 = r14.itemView;
            r18 = r0;
            r1 = r32;
            r0.setLayoutParams(r1);
            goto L_0x02d2;
        L_0x0326:
            r35 = r31;
            r35 = (android.support.v7.widget.RecyclerView.LayoutParams) r35;
            r32 = r35;
            goto L_0x02d6;
        L_0x032d:
            r38 = 0;
            goto L_0x02e3;
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.Recycler.getViewForPosition(int, boolean):android.view.View");
        }

        public void clear() throws  {
            this.mAttachedScrap.clear();
            recycleAndClearCachedViews();
        }

        public void setViewCacheSize(int $i0) throws  {
            this.mViewCacheMax = $i0;
            for (int $i1 = this.mCachedViews.size() - 1; $i1 >= 0 && this.mCachedViews.size() > $i0; $i1--) {
                recycleCachedViewAt($i1);
            }
        }

        public List<ViewHolder> getScrapList() throws  {
            return this.mUnmodifiableAttachedScrap;
        }

        boolean validateViewHolderForOffsetPosition(ViewHolder $r1) throws  {
            if ($r1.isRemoved()) {
                return RecyclerView.this.mState.isPreLayout();
            }
            if ($r1.mPosition < 0 || $r1.mPosition >= RecyclerView.this.mAdapter.getItemCount()) {
                throw new IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + $r1);
            } else if (!RecyclerView.this.mState.isPreLayout() && RecyclerView.this.mAdapter.getItemViewType($r1.mPosition) != $r1.getItemViewType()) {
                return false;
            } else {
                if (RecyclerView.this.mAdapter.hasStableIds()) {
                    return $r1.getItemId() == RecyclerView.this.mAdapter.getItemId($r1.mPosition);
                } else {
                    return true;
                }
            }
        }

        public void bindViewToPosition(View $r1, int $i0) throws  {
            boolean $z0 = true;
            ViewHolder $r2 = RecyclerView.getChildViewHolderInt($r1);
            if ($r2 == null) {
                throw new IllegalArgumentException("The view does not have a ViewHolder. You cannot pass arbitrary views to this method, they should be created by the Adapter");
            }
            int $i1 = RecyclerView.this.mAdapterHelper.findPositionOffset($i0);
            if ($i1 < 0 || $i1 >= RecyclerView.this.mAdapter.getItemCount()) {
                throw new IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + $i0 + "(offset:" + $i1 + ")." + "state:" + RecyclerView.this.mState.getItemCount());
            }
            LayoutParams $r12;
            $r2.mOwnerRecyclerView = RecyclerView.this;
            RecyclerView.this.mAdapter.bindViewHolder($r2, $i1);
            attachAccessibilityDelegate($r1);
            if (RecyclerView.this.mState.isPreLayout()) {
                $r2.mPreLayoutPosition = $i0;
            }
            View $r13 = $r2.itemView;
            android.view.ViewGroup.LayoutParams $r11 = $r13.getLayoutParams();
            if ($r11 == null) {
                $r12 = (LayoutParams) RecyclerView.this.generateDefaultLayoutParams();
                $r13 = $r2.itemView;
                $r1 = $r13;
                $r13.setLayoutParams($r12);
            } else if (RecyclerView.this.checkLayoutParams($r11)) {
                $r12 = (LayoutParams) $r11;
            } else {
                android.view.ViewGroup.LayoutParams $r122 = (LayoutParams) RecyclerView.this.generateLayoutParams($r11);
                $r13 = $r2.itemView;
                $r1 = $r13;
                $r13.setLayoutParams($r122);
            }
            $r12.mInsetsDirty = true;
            $r12.mViewHolder = $r2;
            $r13 = $r2.itemView;
            if ($r13.getParent() != null) {
                $z0 = false;
            }
            $r12.mPendingInvalidate = $z0;
        }

        public int convertPreLayoutPositionToPostLayout(int $i0) throws  {
            if ($i0 >= 0 && $i0 < RecyclerView.this.mState.getItemCount()) {
                return !RecyclerView.this.mState.isPreLayout() ? $i0 : RecyclerView.this.mAdapterHelper.findPositionOffset($i0);
            } else {
                throw new IndexOutOfBoundsException("invalid position " + $i0 + ". State " + "item count is " + RecyclerView.this.mState.getItemCount());
            }
        }

        public View getViewForPosition(int $i0) throws  {
            return getViewForPosition($i0, false);
        }

        private void attachAccessibilityDelegate(View $r1) throws  {
            if (RecyclerView.this.isAccessibilityEnabled()) {
                if (ViewCompat.getImportantForAccessibility($r1) == 0) {
                    ViewCompat.setImportantForAccessibility($r1, 1);
                }
                if (!ViewCompat.hasAccessibilityDelegate($r1)) {
                    ViewCompat.setAccessibilityDelegate($r1, RecyclerView.this.mAccessibilityDelegate.getItemDelegate());
                }
            }
        }

        private void invalidateDisplayListInt(ViewHolder $r1) throws  {
            if ($r1.itemView instanceof ViewGroup) {
                invalidateDisplayListInt((ViewGroup) $r1.itemView, false);
            }
        }

        private void invalidateDisplayListInt(ViewGroup $r1, boolean $z0) throws  {
            int $i0;
            for ($i0 = $r1.getChildCount() - 1; $i0 >= 0; $i0--) {
                View $r2 = $r1.getChildAt($i0);
                if ($r2 instanceof ViewGroup) {
                    invalidateDisplayListInt((ViewGroup) $r2, true);
                }
            }
            if (!$z0) {
                return;
            }
            if ($r1.getVisibility() == 4) {
                $r1.setVisibility(0);
                $r1.setVisibility(4);
                return;
            }
            $i0 = $r1.getVisibility();
            $r1.setVisibility(4);
            $r1.setVisibility($i0);
        }

        public void recycleView(View $r1) throws  {
            ViewHolder $r2 = RecyclerView.getChildViewHolderInt($r1);
            if ($r2.isTmpDetached()) {
                RecyclerView.this.removeDetachedView($r1, false);
            }
            if ($r2.isScrap()) {
                $r2.unScrap();
            } else if ($r2.wasReturnedFromScrap()) {
                $r2.clearReturnedFromScrapFlag();
            }
            recycleViewHolderInternal($r2);
        }

        void recycleViewInternal(View $r1) throws  {
            recycleViewHolderInternal(RecyclerView.getChildViewHolderInt($r1));
        }

        void recycleAndClearCachedViews() throws  {
            for (int $i0 = this.mCachedViews.size() - 1; $i0 >= 0; $i0--) {
                recycleCachedViewAt($i0);
            }
            this.mCachedViews.clear();
        }

        void recycleCachedViewAt(int $i0) throws  {
            addViewHolderToRecycledViewPool((ViewHolder) this.mCachedViews.get($i0));
            this.mCachedViews.remove($i0);
        }

        void recycleViewHolderInternal(ViewHolder $r1) throws  {
            boolean $z0 = true;
            if ($r1.isScrap() || $r1.itemView.getParent() != null) {
                StringBuilder $r5 = new StringBuilder().append("Scrapped or attached views may not be recycled. isScrap:").append($r1.isScrap()).append(" isAttached:");
                if ($r1.itemView.getParent() == null) {
                    $z0 = false;
                }
                throw new IllegalArgumentException($r5.append($z0).toString());
            } else if ($r1.isTmpDetached()) {
                throw new IllegalArgumentException("Tmp detached view should be removed from RecyclerView before it can be recycled: " + $r1);
            } else if ($r1.shouldIgnore()) {
                throw new IllegalArgumentException("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle.");
            } else {
                $z0 = $r1.doesTransientStatePreventRecycling();
                boolean $z2 = RecyclerView.this.mAdapter != null && $z0 && RecyclerView.this.mAdapter.onFailedToRecycleView($r1);
                boolean $z1 = false;
                boolean $z3 = false;
                if ($z2 || $r1.isRecyclable()) {
                    if (!$r1.hasAnyOfTheFlags(14)) {
                        int $i0 = this.mCachedViews.size();
                        if ($i0 == this.mViewCacheMax && $i0 > 0) {
                            recycleCachedViewAt(0);
                        }
                        if ($i0 < this.mViewCacheMax) {
                            this.mCachedViews.add($r1);
                            $z1 = true;
                        }
                    }
                    if (!$z1) {
                        addViewHolderToRecycledViewPool($r1);
                        $z3 = true;
                    }
                }
                ViewInfoStore viewInfoStore = RecyclerView.this.mViewInfoStore;
                ViewInfoStore $r10 = viewInfoStore;
                viewInfoStore.removeViewHolder($r1);
                if (!$z1 && !$z3 && $z0) {
                    $r1.mOwnerRecyclerView = null;
                }
            }
        }

        void addViewHolderToRecycledViewPool(ViewHolder $r1) throws  {
            ViewCompat.setAccessibilityDelegate($r1.itemView, null);
            dispatchViewRecycled($r1);
            $r1.mOwnerRecyclerView = null;
            getRecycledViewPool().putRecycledView($r1);
        }

        void quickRecycleScrapView(View $r1) throws  {
            ViewHolder $r2 = RecyclerView.getChildViewHolderInt($r1);
            $r2.mScrapContainer = null;
            $r2.mInChangeScrap = false;
            $r2.clearReturnedFromScrapFlag();
            recycleViewHolderInternal($r2);
        }

        void scrapView(View $r1) throws  {
            ViewHolder $r2 = RecyclerView.getChildViewHolderInt($r1);
            if (!$r2.hasAnyOfTheFlags(12) && $r2.isUpdated() && !RecyclerView.this.canReuseUpdatedViewHolder($r2)) {
                if (this.mChangedScrap == null) {
                    this.mChangedScrap = new ArrayList();
                }
                $r2.setScrapContainer(this, true);
                this.mChangedScrap.add($r2);
            } else if (!$r2.isInvalid() || $r2.isRemoved() || RecyclerView.this.mAdapter.hasStableIds()) {
                $r2.setScrapContainer(this, false);
                this.mAttachedScrap.add($r2);
            } else {
                throw new IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool.");
            }
        }

        void unscrapView(ViewHolder $r1) throws  {
            if ($r1.mInChangeScrap) {
                this.mChangedScrap.remove($r1);
            } else {
                this.mAttachedScrap.remove($r1);
            }
            $r1.mScrapContainer = null;
            $r1.mInChangeScrap = false;
            $r1.clearReturnedFromScrapFlag();
        }

        int getScrapCount() throws  {
            return this.mAttachedScrap.size();
        }

        View getScrapViewAt(int $i0) throws  {
            return ((ViewHolder) this.mAttachedScrap.get($i0)).itemView;
        }

        void clearScrap() throws  {
            this.mAttachedScrap.clear();
            if (this.mChangedScrap != null) {
                this.mChangedScrap.clear();
            }
        }

        ViewHolder getChangedScrapViewForPosition(int $i0) throws  {
            ArrayList $r1 = this.mChangedScrap;
            this = this;
            if ($r1 != null) {
                this = this;
                int $i1 = this.mChangedScrap.size();
                if ($i1 != 0) {
                    Recycler $r12;
                    ViewHolder $r2;
                    int $i2 = 0;
                    while ($i2 < $i1) {
                        $r12 = this;
                        this = $r12;
                        $r2 = (ViewHolder) $r12.mChangedScrap.get($i2);
                        if ($r2.wasReturnedFromScrap() || $r2.getLayoutPosition() != $i0) {
                            $i2++;
                        } else {
                            $r2.addFlags(32);
                            return $r2;
                        }
                    }
                    if (RecyclerView.this.mAdapter.hasStableIds()) {
                        $i0 = RecyclerView.this.mAdapterHelper.findPositionOffset($i0);
                        if ($i0 > 0 && $i0 < RecyclerView.this.mAdapter.getItemCount()) {
                            long $l4 = RecyclerView.this.mAdapter.getItemId($i0);
                            $i0 = 0;
                            while ($i0 < $i1) {
                                $r12 = this;
                                this = $r12;
                                $r2 = (ViewHolder) $r12.mChangedScrap.get($i0);
                                if ($r2.wasReturnedFromScrap() || $r2.getItemId() != $l4) {
                                    $i0++;
                                } else {
                                    $r2.addFlags(32);
                                    return $r2;
                                }
                            }
                        }
                    }
                    return null;
                }
            }
            return null;
        }

        ViewHolder getScrapViewForPosition(int $i0, int $i1, boolean $z0) throws  {
            int $i2 = this.mAttachedScrap.size();
            int $i3 = 0;
            while ($i3 < $i2) {
                ChildHelper childHelper;
                ChildHelper $r8;
                View $r9;
                ViewHolder $r3 = (ViewHolder) this.mAttachedScrap.get($i3);
                if ($r3.wasReturnedFromScrap() || $r3.getLayoutPosition() != $i0 || $r3.isInvalid() || (!RecyclerView.this.mState.mInPreLayout && $r3.isRemoved())) {
                    $i3++;
                } else if ($i1 == -1 || $r3.getItemViewType() == $i1) {
                    $r3.addFlags(32);
                    return $r3;
                } else {
                    Log.e(RecyclerView.TAG, "Scrap view for position " + $i0 + " isn't dirty but has" + " wrong view type! (found " + $r3.getItemViewType() + " but expected " + $i1 + ")");
                    if (!$z0) {
                        childHelper = RecyclerView.this.mChildHelper;
                        $r8 = childHelper;
                        $r9 = childHelper.findHiddenNonRemovedView($i0, $i1);
                        if ($r9 != null) {
                            $r3 = RecyclerView.getChildViewHolderInt($r9);
                            childHelper = RecyclerView.this.mChildHelper;
                            $r8 = childHelper;
                            childHelper.unhide($r9);
                            childHelper = RecyclerView.this.mChildHelper;
                            $r8 = childHelper;
                            $i0 = childHelper.indexOfChild($r9);
                            if ($i0 != -1) {
                                throw new IllegalStateException("layout index should not be -1 after unhiding a view:" + $r3);
                            }
                            childHelper = RecyclerView.this.mChildHelper;
                            $r8 = childHelper;
                            childHelper.detachViewFromParent($i0);
                            scrapView($r9);
                            $r3.addFlags(8224);
                            return $r3;
                        }
                    }
                    $i1 = this.mCachedViews.size();
                    $i2 = 0;
                    while ($i2 < $i1) {
                        $r3 = (ViewHolder) this.mCachedViews.get($i2);
                        if ($r3.isInvalid() || $r3.getLayoutPosition() != $i0) {
                            $i2++;
                        } else if ($z0) {
                            return $r3;
                        } else {
                            this.mCachedViews.remove($i2);
                            return $r3;
                        }
                    }
                    return null;
                }
            }
            if ($z0) {
                childHelper = RecyclerView.this.mChildHelper;
                $r8 = childHelper;
                $r9 = childHelper.findHiddenNonRemovedView($i0, $i1);
                if ($r9 != null) {
                    $r3 = RecyclerView.getChildViewHolderInt($r9);
                    childHelper = RecyclerView.this.mChildHelper;
                    $r8 = childHelper;
                    childHelper.unhide($r9);
                    childHelper = RecyclerView.this.mChildHelper;
                    $r8 = childHelper;
                    $i0 = childHelper.indexOfChild($r9);
                    if ($i0 != -1) {
                        childHelper = RecyclerView.this.mChildHelper;
                        $r8 = childHelper;
                        childHelper.detachViewFromParent($i0);
                        scrapView($r9);
                        $r3.addFlags(8224);
                        return $r3;
                    }
                    throw new IllegalStateException("layout index should not be -1 after unhiding a view:" + $r3);
                }
            }
            $i1 = this.mCachedViews.size();
            $i2 = 0;
            while ($i2 < $i1) {
                $r3 = (ViewHolder) this.mCachedViews.get($i2);
                if ($r3.isInvalid()) {
                }
                $i2++;
            }
            return null;
        }

        ViewHolder getScrapViewForId(long $l0, int $i1, boolean $z0) throws  {
            int $i2;
            Recycler $r1;
            this = this;
            for ($i2 = this.mAttachedScrap.size() - 1; $i2 >= 0; $i2--) {
                $r1 = this;
                this = $r1;
                ViewHolder $r3 = (ViewHolder) $r1.mAttachedScrap.get($i2);
                if ($r3.getItemId() == $l0 && !$r3.wasReturnedFromScrap()) {
                    if ($i1 == $r3.getItemViewType()) {
                        $r3.addFlags(32);
                        if (!$r3.isRemoved() || RecyclerView.this.mState.isPreLayout()) {
                            return $r3;
                        }
                        $r3.setFlags(2, 14);
                        return $r3;
                    } else if (!$z0) {
                        $r1 = this;
                        this = $r1;
                        $r1.mAttachedScrap.remove($i2);
                        RecyclerView.this.removeDetachedView($r3.itemView, false);
                        quickRecycleScrapView($r3.itemView);
                    }
                }
            }
            $r1 = this;
            this = $r1;
            for ($i2 = $r1.mCachedViews.size() - 1; $i2 >= 0; $i2--) {
                $r1 = this;
                this = $r1;
                $r3 = (ViewHolder) $r1.mCachedViews.get($i2);
                if ($r3.getItemId() == $l0) {
                    if ($i1 == $r3.getItemViewType()) {
                        if ($z0) {
                            return $r3;
                        }
                        $r1 = this;
                        this = $r1;
                        $r1.mCachedViews.remove($i2);
                        return $r3;
                    } else if (!$z0) {
                        recycleCachedViewAt($i2);
                    }
                }
            }
            return null;
        }

        void dispatchViewRecycled(ViewHolder $r1) throws  {
            if (RecyclerView.this.mRecyclerListener != null) {
                RecyclerView.this.mRecyclerListener.onViewRecycled($r1);
            }
            if (RecyclerView.this.mAdapter != null) {
                RecyclerView.this.mAdapter.onViewRecycled($r1);
            }
            if (RecyclerView.this.mState != null) {
                RecyclerView.this.mViewInfoStore.removeViewHolder($r1);
            }
        }

        void onAdapterChanged(Adapter $r1, Adapter $r2, boolean $z0) throws  {
            clear();
            getRecycledViewPool().onAdapterChanged($r1, $r2, $z0);
        }

        void offsetPositionRecordsForMove(int $i0, int $i1) throws  {
            byte $b4;
            int $i2;
            int $i3;
            if ($i0 < $i1) {
                $i2 = $i0;
                $i3 = $i1;
                $b4 = (byte) -1;
            } else {
                $i2 = $i1;
                $i3 = $i0;
                $b4 = (byte) 1;
            }
            int $i5 = this.mCachedViews.size();
            for (int $i6 = 0; $i6 < $i5; $i6++) {
                ViewHolder $r3 = (ViewHolder) this.mCachedViews.get($i6);
                if ($r3 != null && $r3.mPosition >= $i2 && $r3.mPosition <= $i3) {
                    if ($r3.mPosition == $i0) {
                        $r3.offsetPosition($i1 - $i0, false);
                    } else {
                        $r3.offsetPosition($b4, false);
                    }
                }
            }
        }

        void offsetPositionRecordsForInsert(int $i0, int $i1) throws  {
            int $i2 = this.mCachedViews.size();
            for (int $i3 = 0; $i3 < $i2; $i3++) {
                ViewHolder $r3 = (ViewHolder) this.mCachedViews.get($i3);
                if ($r3 != null && $r3.mPosition >= $i0) {
                    $r3.offsetPosition($i1, true);
                }
            }
        }

        void offsetPositionRecordsForRemove(int $i0, int $i1, boolean $z0) throws  {
            int $i2 = $i0 + $i1;
            for (int $i3 = this.mCachedViews.size() - 1; $i3 >= 0; $i3--) {
                ViewHolder $r3 = (ViewHolder) this.mCachedViews.get($i3);
                if ($r3 != null) {
                    if ($r3.mPosition >= $i2) {
                        $r3.offsetPosition(-$i1, $z0);
                    } else if ($r3.mPosition >= $i0) {
                        $r3.addFlags(8);
                        recycleCachedViewAt($i3);
                    }
                }
            }
        }

        void setViewCacheExtension(ViewCacheExtension $r1) throws  {
            this.mViewCacheExtension = $r1;
        }

        void setRecycledViewPool(RecycledViewPool $r1) throws  {
            if (this.mRecyclerPool != null) {
                this.mRecyclerPool.detach();
            }
            this.mRecyclerPool = $r1;
            if ($r1 != null) {
                this.mRecyclerPool.attach(RecyclerView.this.getAdapter());
            }
        }

        RecycledViewPool getRecycledViewPool() throws  {
            if (this.mRecyclerPool == null) {
                this.mRecyclerPool = new RecycledViewPool();
            }
            return this.mRecyclerPool;
        }

        void viewRangeUpdate(int $i0, int $i1) throws  {
            $i1 = $i0 + $i1;
            for (int $i2 = this.mCachedViews.size() - 1; $i2 >= 0; $i2--) {
                ViewHolder $r3 = (ViewHolder) this.mCachedViews.get($i2);
                if ($r3 != null) {
                    int $i3 = $r3.getLayoutPosition();
                    if ($i3 >= $i0 && $i3 < $i1) {
                        $r3.addFlags(2);
                        recycleCachedViewAt($i2);
                    }
                }
            }
        }

        void setAdapterPositionsAsUnknown() throws  {
            int $i0 = this.mCachedViews.size();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                ViewHolder $r3 = (ViewHolder) this.mCachedViews.get($i1);
                if ($r3 != null) {
                    $r3.addFlags(512);
                }
            }
        }

        void markKnownViewsInvalid() throws  {
            if (RecyclerView.this.mAdapter == null || !RecyclerView.this.mAdapter.hasStableIds()) {
                recycleAndClearCachedViews();
                return;
            }
            int $i0 = this.mCachedViews.size();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                ViewHolder $r5 = (ViewHolder) this.mCachedViews.get($i1);
                if ($r5 != null) {
                    $r5.addFlags(6);
                    $r5.addChangePayload(null);
                }
            }
        }

        void clearOldPositions() throws  {
            int $i1;
            int $i0 = this.mCachedViews.size();
            for ($i1 = 0; $i1 < $i0; $i1++) {
                ((ViewHolder) this.mCachedViews.get($i1)).clearOldPosition();
            }
            $i0 = this.mAttachedScrap.size();
            for ($i1 = 0; $i1 < $i0; $i1++) {
                ((ViewHolder) this.mAttachedScrap.get($i1)).clearOldPosition();
            }
            if (this.mChangedScrap != null) {
                $i0 = this.mChangedScrap.size();
                for ($i1 = 0; $i1 < $i0; $i1++) {
                    ((ViewHolder) this.mChangedScrap.get($i1)).clearOldPosition();
                }
            }
        }

        void markItemDecorInsetsDirty() throws  {
            int $i0 = this.mCachedViews.size();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                LayoutParams $r6 = (LayoutParams) ((ViewHolder) this.mCachedViews.get($i1)).itemView.getLayoutParams();
                if ($r6 != null) {
                    $r6.mInsetsDirty = true;
                }
            }
        }
    }

    public interface RecyclerListener {
        void onViewRecycled(ViewHolder viewHolder) throws ;
    }

    private class RecyclerViewDataObserver extends AdapterDataObserver {
        private RecyclerViewDataObserver() throws  {
        }

        public void onChanged() throws  {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapter.hasStableIds()) {
                RecyclerView.this.mState.mStructureChanged = true;
                RecyclerView.this.setDataSetChangedAfterLayout();
            } else {
                RecyclerView.this.mState.mStructureChanged = true;
                RecyclerView.this.setDataSetChangedAfterLayout();
            }
            if (!RecyclerView.this.mAdapterHelper.hasPendingUpdates()) {
                RecyclerView.this.requestLayout();
            }
        }

        public void onItemRangeChanged(int $i0, int $i1, Object $r1) throws  {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeChanged($i0, $i1, $r1)) {
                triggerUpdateProcessor();
            }
        }

        public void onItemRangeInserted(int $i0, int $i1) throws  {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeInserted($i0, $i1)) {
                triggerUpdateProcessor();
            }
        }

        public void onItemRangeRemoved(int $i0, int $i1) throws  {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeRemoved($i0, $i1)) {
                triggerUpdateProcessor();
            }
        }

        public void onItemRangeMoved(int $i0, int $i1, int $i2) throws  {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeMoved($i0, $i1, $i2)) {
                triggerUpdateProcessor();
            }
        }

        void triggerUpdateProcessor() throws  {
            if (RecyclerView.this.mPostUpdatesOnAnimation && RecyclerView.this.mHasFixedSize && RecyclerView.this.mIsAttached) {
                ViewCompat.postOnAnimation(RecyclerView.this, RecyclerView.this.mUpdateChildViewsRunnable);
                return;
            }
            RecyclerView.this.mAdapterUpdateDuringMeasure = true;
            RecyclerView.this.requestLayout();
        }
    }

    public static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new C02561();
        Parcelable mLayoutState;

        static class C02561 implements Creator<SavedState> {
            C02561() throws  {
            }

            public SavedState createFromParcel(Parcel $r1) throws  {
                return new SavedState($r1);
            }

            public SavedState[] newArray(int $i0) throws  {
                return new SavedState[$i0];
            }
        }

        SavedState(Parcel $r1) throws  {
            super($r1);
            this.mLayoutState = $r1.readParcelable(LayoutManager.class.getClassLoader());
        }

        SavedState(Parcelable $r1) throws  {
            super($r1);
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            super.writeToParcel($r1, $i0);
            $r1.writeParcelable(this.mLayoutState, 0);
        }

        private void copyFrom(SavedState $r1) throws  {
            this.mLayoutState = $r1.mLayoutState;
        }
    }

    public static class SimpleOnItemTouchListener implements OnItemTouchListener {
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) throws  {
            return false;
        }

        public void onTouchEvent(RecyclerView rv, MotionEvent e) throws  {
        }

        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) throws  {
        }
    }

    public static class State {
        static final int STEP_ANIMATIONS = 4;
        static final int STEP_LAYOUT = 2;
        static final int STEP_START = 1;
        private SparseArray<Object> mData;
        private int mDeletedInvisibleItemCountSincePreviousLayout = 0;
        private boolean mInPreLayout = false;
        private boolean mIsMeasuring = false;
        int mItemCount = 0;
        private int mLayoutStep = 1;
        private int mPreviousLayoutItemCount = 0;
        private boolean mRunPredictiveAnimations = false;
        private boolean mRunSimpleAnimations = false;
        private boolean mStructureChanged = false;
        private int mTargetPosition = -1;
        private boolean mTrackOldChangeHolders = false;

        static /* synthetic */ int access$1712(State $r0, int $i0) throws  {
            $i0 = $r0.mDeletedInvisibleItemCountSincePreviousLayout + $i0;
            $r0.mDeletedInvisibleItemCountSincePreviousLayout = $i0;
            return $i0;
        }

        void assertLayoutStep(int $i0) throws  {
            if ((this.mLayoutStep & $i0) == 0) {
                throw new IllegalStateException("Layout state should be one of " + Integer.toBinaryString($i0) + " but it is " + Integer.toBinaryString(this.mLayoutStep));
            }
        }

        State reset() throws  {
            this.mTargetPosition = -1;
            if (this.mData != null) {
                this.mData.clear();
            }
            this.mItemCount = 0;
            this.mStructureChanged = false;
            this.mIsMeasuring = false;
            return this;
        }

        public boolean isMeasuring() throws  {
            return this.mIsMeasuring;
        }

        public boolean isPreLayout() throws  {
            return this.mInPreLayout;
        }

        public boolean willRunPredictiveAnimations() throws  {
            return this.mRunPredictiveAnimations;
        }

        public boolean willRunSimpleAnimations() throws  {
            return this.mRunSimpleAnimations;
        }

        public void remove(int $i0) throws  {
            if (this.mData != null) {
                this.mData.remove($i0);
            }
        }

        public <T> T get(@Signature({"<T:", "Ljava/lang/Object;", ">(I)TT;"}) int $i0) throws  {
            return this.mData == null ? null : this.mData.get($i0);
        }

        public void put(int $i0, Object $r1) throws  {
            if (this.mData == null) {
                this.mData = new SparseArray();
            }
            this.mData.put($i0, $r1);
        }

        public int getTargetScrollPosition() throws  {
            return this.mTargetPosition;
        }

        public boolean hasTargetScrollPosition() throws  {
            return this.mTargetPosition != -1;
        }

        public boolean didStructureChange() throws  {
            return this.mStructureChanged;
        }

        public int getItemCount() throws  {
            return this.mInPreLayout ? this.mPreviousLayoutItemCount - this.mDeletedInvisibleItemCountSincePreviousLayout : this.mItemCount;
        }

        public String toString() throws  {
            return "State{mTargetPosition=" + this.mTargetPosition + ", mData=" + this.mData + ", mItemCount=" + this.mItemCount + ", mPreviousLayoutItemCount=" + this.mPreviousLayoutItemCount + ", mDeletedInvisibleItemCountSincePreviousLayout=" + this.mDeletedInvisibleItemCountSincePreviousLayout + ", mStructureChanged=" + this.mStructureChanged + ", mInPreLayout=" + this.mInPreLayout + ", mRunSimpleAnimations=" + this.mRunSimpleAnimations + ", mRunPredictiveAnimations=" + this.mRunPredictiveAnimations + '}';
        }
    }

    public static abstract class ViewCacheExtension {
        public abstract View getViewForPositionAndType(Recycler recycler, int i, int i2) throws ;
    }

    private class ViewFlinger implements Runnable {
        private boolean mEatRunOnAnimationRequest = false;
        private Interpolator mInterpolator = RecyclerView.sQuinticInterpolator;
        private int mLastFlingX;
        private int mLastFlingY;
        private boolean mReSchedulePostAnimationCallback = false;
        private ScrollerCompat mScroller;

        public void run() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:64:0x01ac
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r27 = this;
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r3 = r2.mLayout;
            if (r3 != 0) goto L_0x000e;
        L_0x0008:
            r0 = r27;
            r0.stop();
            return;
        L_0x000e:
            r0 = r27;
            r0.disableRunOnAnimationRequests();
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r2.consumePendingUpdateOperations();
            r0 = r27;
            r4 = r0.mScroller;
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r3 = r2.mLayout;
            r5 = r3.mSmoothScroller;
            r6 = r4.computeScrollOffset();
            if (r6 == 0) goto L_0x01d6;
        L_0x002c:
            r7 = r4.getCurrX();
            r8 = r4.getCurrY();
            r0 = r27;
            r9 = r0.mLastFlingX;
            r9 = r7 - r9;
            r0 = r27;
            r10 = r0.mLastFlingY;
            r10 = r8 - r10;
            r11 = 0;
            r12 = 0;
            r0 = r27;
            r0.mLastFlingX = r7;
            r0 = r27;
            r0.mLastFlingY = r8;
            r13 = 0;
            r14 = 0;
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r15 = r2.mAdapter;
            if (r15 == 0) goto L_0x00ec;
        L_0x0056:
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r2.eatRequestLayout();
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r2.onEnterLayoutOrScroll();
            r16 = "RV Scroll";
            r0 = r16;
            android.support.v4.os.TraceCompat.beginSection(r0);
            if (r9 == 0) goto L_0x008e;
        L_0x006d:
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r3 = r2.mLayout;
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r0 = r2.mRecycler;
            r17 = r0;
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r0 = r2.mState;
            r18 = r0;
            r0 = r17;
            r1 = r18;
            r13 = r3.scrollHorizontallyBy(r9, r0, r1);
            r11 = r13;
            r13 = r9 - r13;
        L_0x008e:
            if (r10 == 0) goto L_0x00b1;
        L_0x0090:
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r3 = r2.mLayout;
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r0 = r2.mRecycler;
            r17 = r0;
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r0 = r2.mState;
            r18 = r0;
            r0 = r17;
            r1 = r18;
            r14 = r3.scrollVerticallyBy(r10, r0, r1);
            r12 = r14;
            r14 = r10 - r14;
        L_0x00b1:
            android.support.v4.os.TraceCompat.endSection();
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r2.repositionShadowingViews();
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r2.onExitLayoutOrScroll();
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r19 = 0;
            r0 = r19;
            r2.resumeRequestLayout(r0);
            if (r5 == 0) goto L_0x00ec;
        L_0x00cf:
            r6 = r5.isPendingInitialRun();
            if (r6 != 0) goto L_0x00ec;
        L_0x00d5:
            r6 = r5.isRunning();
            if (r6 == 0) goto L_0x00ec;
        L_0x00db:
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r0 = r2.mState;
            r18 = r0;
            r20 = r0.getItemCount();
            if (r20 != 0) goto L_0x020c;
        L_0x00e9:
            r5.stop();
        L_0x00ec:
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r21 = r2.mItemDecorations;
            r0 = r21;
            r6 = r0.isEmpty();
            if (r6 != 0) goto L_0x0103;
        L_0x00fc:
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r2.invalidate();
        L_0x0103:
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r20 = android.support.v4.view.ViewCompat.getOverScrollMode(r2);
            r19 = 2;
            r0 = r20;
            r1 = r19;
            if (r0 == r1) goto L_0x011a;
        L_0x0113:
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r2.considerReleasingGlowsOnScroll(r9, r10);
        L_0x011a:
            if (r13 != 0) goto L_0x011e;
        L_0x011c:
            if (r14 == 0) goto L_0x016f;
        L_0x011e:
            r22 = r4.getCurrVelocity();
            r0 = r22;
            r0 = (int) r0;
            r23 = r0;
            r20 = 0;
            if (r13 == r7) goto L_0x0132;
        L_0x012b:
            if (r13 >= 0) goto L_0x0245;
        L_0x012d:
            r0 = r23;
            r0 = -r0;
            r20 = r0;
        L_0x0132:
            r24 = 0;
            if (r14 == r8) goto L_0x013d;
        L_0x0136:
            if (r14 >= 0) goto L_0x024d;
        L_0x0138:
            r0 = r23;
            r0 = -r0;
            r24 = r0;
        L_0x013d:
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r23 = android.support.v4.view.ViewCompat.getOverScrollMode(r2);
            r19 = 2;
            r0 = r23;
            r1 = r19;
            if (r0 == r1) goto L_0x0158;
        L_0x014d:
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r0 = r20;
            r1 = r24;
            r2.absorbGlows(r0, r1);
        L_0x0158:
            if (r20 != 0) goto L_0x0162;
        L_0x015a:
            if (r13 == r7) goto L_0x0162;
        L_0x015c:
            r7 = r4.getFinalX();
            if (r7 != 0) goto L_0x016f;
        L_0x0162:
            if (r24 != 0) goto L_0x016c;
        L_0x0164:
            if (r14 == r8) goto L_0x016c;
        L_0x0166:
            r8 = r4.getFinalY();
            if (r8 != 0) goto L_0x016f;
        L_0x016c:
            r4.abortAnimation();
        L_0x016f:
            if (r11 != 0) goto L_0x0173;
        L_0x0171:
            if (r12 == 0) goto L_0x017a;
        L_0x0173:
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r2.dispatchOnScrolled(r11, r12);
        L_0x017a:
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r6 = r2.awakenScrollBars();
            if (r6 != 0) goto L_0x018b;
        L_0x0184:
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r2.invalidate();
        L_0x018b:
            if (r10 == 0) goto L_0x0259;
        L_0x018d:
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r3 = r2.mLayout;
            r6 = r3.canScrollVertically();
            if (r6 == 0) goto L_0x0259;
        L_0x0199:
            if (r12 != r10) goto L_0x0259;
        L_0x019b:
            r6 = 1;
        L_0x019c:
            if (r9 == 0) goto L_0x025b;
        L_0x019e:
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r3 = r2.mLayout;
            r25 = r3.canScrollHorizontally();
            goto L_0x01b0;
        L_0x01a9:
            goto L_0x0132;
            goto L_0x01b0;
        L_0x01ad:
            goto L_0x0132;
        L_0x01b0:
            if (r25 == 0) goto L_0x025b;
        L_0x01b2:
            if (r11 != r9) goto L_0x025b;
        L_0x01b4:
            r25 = 1;
        L_0x01b6:
            if (r9 != 0) goto L_0x01ba;
        L_0x01b8:
            if (r10 == 0) goto L_0x01c2;
        L_0x01ba:
            if (r25 != 0) goto L_0x01c2;
        L_0x01bc:
            goto L_0x01c0;
        L_0x01bd:
            goto L_0x013d;
        L_0x01c0:
            if (r6 == 0) goto L_0x025e;
        L_0x01c2:
            r6 = 1;
        L_0x01c3:
            r25 = r4.isFinished();
            if (r25 != 0) goto L_0x01cb;
        L_0x01c9:
            if (r6 != 0) goto L_0x0260;
        L_0x01cb:
            r0 = r27;
            r2 = android.support.v7.widget.RecyclerView.this;
            r19 = 0;
            r0 = r19;
            r2.setScrollState(r0);
        L_0x01d6:
            if (r5 == 0) goto L_0x01fe;
        L_0x01d8:
            r6 = r5.isPendingInitialRun();
            if (r6 == 0) goto L_0x01e9;
        L_0x01de:
            r19 = 0;
            r26 = 0;
            r0 = r19;
            r1 = r26;
            r5.onAnimation(r0, r1);
        L_0x01e9:
            r0 = r27;
            r6 = r0.mReSchedulePostAnimationCallback;
            goto L_0x01f1;
        L_0x01ee:
            goto L_0x01a9;
        L_0x01f1:
            if (r6 != 0) goto L_0x01fe;
        L_0x01f3:
            goto L_0x01fb;
            goto L_0x01f8;
        L_0x01f5:
            goto L_0x019c;
        L_0x01f8:
            goto L_0x01ad;
        L_0x01fb:
            r5.stop();
        L_0x01fe:
            r0 = r27;
            r0.enableRunOnAnimationRequests();
            goto L_0x020b;
        L_0x0204:
            goto L_0x01bd;
            goto L_0x020b;
        L_0x0208:
            goto L_0x01b6;
        L_0x020b:
            return;
        L_0x020c:
            r24 = r5.getTargetPosition();
            goto L_0x0214;
        L_0x0211:
            goto L_0x01c3;
        L_0x0214:
            r0 = r24;
            r1 = r20;
            if (r0 < r1) goto L_0x0235;
        L_0x021a:
            r20 = r20 + -1;
            r0 = r20;
            r5.setTargetPosition(r0);
            goto L_0x0225;
        L_0x0222:
            goto L_0x01d6;
        L_0x0225:
            r20 = r9 - r13;
            r24 = r10 - r14;
            goto L_0x022d;
        L_0x022a:
            goto L_0x00ec;
        L_0x022d:
            r0 = r20;
            r1 = r24;
            r5.onAnimation(r0, r1);
            goto L_0x022a;
        L_0x0235:
            r20 = r9 - r13;
            r24 = r10 - r14;
            goto L_0x023d;
        L_0x023a:
            goto L_0x00ec;
        L_0x023d:
            r0 = r20;
            r1 = r24;
            r5.onAnimation(r0, r1);
            goto L_0x023a;
        L_0x0245:
            if (r13 <= 0) goto L_0x024a;
        L_0x0247:
            r20 = r23;
            goto L_0x01ee;
        L_0x024a:
            r20 = 0;
            goto L_0x01f8;
        L_0x024d:
            if (r14 <= 0) goto L_0x0256;
        L_0x024f:
            r24 = r23;
            goto L_0x0204;
            goto L_0x0256;
        L_0x0253:
            goto L_0x013d;
        L_0x0256:
            r24 = 0;
            goto L_0x0253;
        L_0x0259:
            r6 = 0;
            goto L_0x01f5;
        L_0x025b:
            r25 = 0;
            goto L_0x0208;
        L_0x025e:
            r6 = 0;
            goto L_0x0211;
        L_0x0260:
            r0 = r27;
            r0.postOnAnimation();
            goto L_0x0222;
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.ViewFlinger.run():void");
        }

        public ViewFlinger() throws  {
            this.mScroller = ScrollerCompat.create(RecyclerView.this.getContext(), RecyclerView.sQuinticInterpolator);
        }

        private void disableRunOnAnimationRequests() throws  {
            this.mReSchedulePostAnimationCallback = false;
            this.mEatRunOnAnimationRequest = true;
        }

        private void enableRunOnAnimationRequests() throws  {
            this.mEatRunOnAnimationRequest = false;
            if (this.mReSchedulePostAnimationCallback) {
                postOnAnimation();
            }
        }

        void postOnAnimation() throws  {
            if (this.mEatRunOnAnimationRequest) {
                this.mReSchedulePostAnimationCallback = true;
                return;
            }
            RecyclerView.this.removeCallbacks(this);
            ViewCompat.postOnAnimation(RecyclerView.this, this);
        }

        public void fling(int $i0, int $i1) throws  {
            RecyclerView.this.setScrollState(2);
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            this.mScroller.fling(0, 0, $i0, $i1, Integer.MIN_VALUE, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, Integer.MIN_VALUE, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
            postOnAnimation();
        }

        public void smoothScrollBy(int $i0, int $i1) throws  {
            smoothScrollBy($i0, $i1, 0, 0);
        }

        public void smoothScrollBy(int $i0, int $i1, int $i2, int $i3) throws  {
            smoothScrollBy($i0, $i1, computeScrollDuration($i0, $i1, $i2, $i3));
        }

        private float distanceInfluenceForSnapDuration(float $f0) throws  {
            return (float) Math.sin((double) ((float) (((double) ($f0 - CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR)) * 0.4712389167638204d)));
        }

        private int computeScrollDuration(int $i0, int $i1, int $i2, int $i3) throws  {
            boolean $z0;
            int $i4 = Math.abs($i0);
            int $i5 = $i4;
            int $i6 = Math.abs($i1);
            if ($i4 > $i6) {
                $z0 = true;
            } else {
                $z0 = false;
            }
            int $i32 = ($i2 * $i2) + ($i3 * $i3);
            $i2 = $i32;
            $i2 = (int) Math.sqrt((double) $i32);
            $i32 = $i1 * $i1;
            $i1 = $i32;
            $i0 = (int) Math.sqrt((double) (($i0 * $i0) + $i32));
            $i3 = $z0 ? RecyclerView.this.getWidth() : RecyclerView.this.getHeight();
            $i1 = $i3 / 2;
            float $f0 = ((float) $i1) + (((float) $i1) * distanceInfluenceForSnapDuration(Math.min(1.0f, (1.0f * ((float) $i0)) / ((float) $i3))));
            if ($i2 > 0) {
                $i0 = Math.round(1000.0f * Math.abs($f0 / ((float) $i2))) * 4;
            } else {
                if (!$z0) {
                    $i5 = $i6;
                }
                $i0 = (int) (((((float) $i5) / ((float) $i3)) + 1.0f) * 300.0f);
            }
            return Math.min($i0, 2000);
        }

        public void smoothScrollBy(int $i0, int $i1, int $i2) throws  {
            smoothScrollBy($i0, $i1, $i2, RecyclerView.sQuinticInterpolator);
        }

        public void smoothScrollBy(int $i0, int $i1, int $i2, Interpolator $r1) throws  {
            if (this.mInterpolator != $r1) {
                this.mInterpolator = $r1;
                this.mScroller = ScrollerCompat.create(RecyclerView.this.getContext(), $r1);
            }
            RecyclerView.this.setScrollState(2);
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            this.mScroller.startScroll(0, 0, $i0, $i1, $i2);
            postOnAnimation();
        }

        public void stop() throws  {
            RecyclerView.this.removeCallbacks(this);
            this.mScroller.abortAnimation();
        }
    }

    public static abstract class ViewHolder {
        static final int FLAG_ADAPTER_FULLUPDATE = 1024;
        static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
        static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 8192;
        static final int FLAG_BOUND = 1;
        static final int FLAG_IGNORE = 128;
        static final int FLAG_INVALID = 4;
        static final int FLAG_MOVED = 2048;
        static final int FLAG_NOT_RECYCLABLE = 16;
        static final int FLAG_REMOVED = 8;
        static final int FLAG_RETURNED_FROM_SCRAP = 32;
        static final int FLAG_TMP_DETACHED = 256;
        static final int FLAG_UPDATE = 2;
        private static final List<Object> FULLUPDATE_PAYLOADS = Collections.EMPTY_LIST;
        public final View itemView;
        private int mFlags;
        private boolean mInChangeScrap = false;
        private int mIsRecyclableCount = 0;
        long mItemId = -1;
        int mItemViewType = -1;
        int mOldPosition = -1;
        RecyclerView mOwnerRecyclerView;
        List<Object> mPayloads = null;
        int mPosition = -1;
        int mPreLayoutPosition = -1;
        private Recycler mScrapContainer = null;
        ViewHolder mShadowedHolder = null;
        ViewHolder mShadowingHolder = null;
        List<Object> mUnmodifiedPayloads = null;
        private int mWasImportantForAccessibilityBeforeHidden = 0;

        void setFlags(int r1, int r2) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v7.widget.RecyclerView.ViewHolder.setFlags(int, int):void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
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
	... 8 more
*/
            /*
            // Can't load method instructions.
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.ViewHolder.setFlags(int, int):void");
        }

        public ViewHolder(View $r1) throws  {
            if ($r1 == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = $r1;
        }

        void flagRemovedAndOffsetPosition(int $i0, int $i1, boolean $z0) throws  {
            addFlags(8);
            offsetPosition($i1, $z0);
            this.mPosition = $i0;
        }

        void offsetPosition(int $i0, boolean $z0) throws  {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
            if (this.mPreLayoutPosition == -1) {
                this.mPreLayoutPosition = this.mPosition;
            }
            if ($z0) {
                this.mPreLayoutPosition += $i0;
            }
            this.mPosition += $i0;
            if (this.itemView.getLayoutParams() != null) {
                ((LayoutParams) this.itemView.getLayoutParams()).mInsetsDirty = true;
            }
        }

        void clearOldPosition() throws  {
            this.mOldPosition = -1;
            this.mPreLayoutPosition = -1;
        }

        void saveOldPosition() throws  {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
        }

        boolean shouldIgnore() throws  {
            return (this.mFlags & 128) != 0;
        }

        @Deprecated
        public final int getPosition() throws  {
            return this.mPreLayoutPosition == -1 ? this.mPosition : this.mPreLayoutPosition;
        }

        public final int getLayoutPosition() throws  {
            return this.mPreLayoutPosition == -1 ? this.mPosition : this.mPreLayoutPosition;
        }

        public final int getAdapterPosition() throws  {
            return this.mOwnerRecyclerView == null ? -1 : this.mOwnerRecyclerView.getAdapterPositionFor(this);
        }

        public final int getOldPosition() throws  {
            return this.mOldPosition;
        }

        public final long getItemId() throws  {
            return this.mItemId;
        }

        public final int getItemViewType() throws  {
            return this.mItemViewType;
        }

        boolean isScrap() throws  {
            return this.mScrapContainer != null;
        }

        void unScrap() throws  {
            this.mScrapContainer.unscrapView(this);
        }

        boolean wasReturnedFromScrap() throws  {
            return (this.mFlags & 32) != 0;
        }

        void clearReturnedFromScrapFlag() throws  {
            this.mFlags &= -33;
        }

        void clearTmpDetachFlag() throws  {
            this.mFlags &= -257;
        }

        void stopIgnoring() throws  {
            this.mFlags &= -129;
        }

        void setScrapContainer(Recycler $r1, boolean $z0) throws  {
            this.mScrapContainer = $r1;
            this.mInChangeScrap = $z0;
        }

        boolean isInvalid() throws  {
            return (this.mFlags & 4) != 0;
        }

        boolean needsUpdate() throws  {
            return (this.mFlags & 2) != 0;
        }

        boolean isBound() throws  {
            return (this.mFlags & 1) != 0;
        }

        boolean isRemoved() throws  {
            return (this.mFlags & 8) != 0;
        }

        boolean hasAnyOfTheFlags(int $i0) throws  {
            return (this.mFlags & $i0) != 0;
        }

        boolean isTmpDetached() throws  {
            return (this.mFlags & 256) != 0;
        }

        boolean isAdapterPositionUnknown() throws  {
            return (this.mFlags & 512) != 0 || isInvalid();
        }

        void addFlags(int $i0) throws  {
            this.mFlags |= $i0;
        }

        void addChangePayload(Object $r1) throws  {
            if ($r1 == null) {
                addFlags(1024);
            } else if ((this.mFlags & 1024) == 0) {
                createPayloadsIfNeeded();
                this.mPayloads.add($r1);
            }
        }

        private void createPayloadsIfNeeded() throws  {
            if (this.mPayloads == null) {
                this.mPayloads = new ArrayList();
                this.mUnmodifiedPayloads = Collections.unmodifiableList(this.mPayloads);
            }
        }

        void clearPayload() throws  {
            if (this.mPayloads != null) {
                this.mPayloads.clear();
            }
            this.mFlags &= -1025;
        }

        List<Object> getUnmodifiedPayloads() throws  {
            if ((this.mFlags & 1024) != 0) {
                return FULLUPDATE_PAYLOADS;
            }
            if (this.mPayloads == null || this.mPayloads.size() == 0) {
                return FULLUPDATE_PAYLOADS;
            }
            return this.mUnmodifiedPayloads;
        }

        void resetInternal() throws  {
            this.mFlags = 0;
            this.mPosition = -1;
            this.mOldPosition = -1;
            this.mItemId = -1;
            this.mPreLayoutPosition = -1;
            this.mIsRecyclableCount = 0;
            this.mShadowedHolder = null;
            this.mShadowingHolder = null;
            clearPayload();
            this.mWasImportantForAccessibilityBeforeHidden = 0;
        }

        private void onEnteredHiddenState() throws  {
            this.mWasImportantForAccessibilityBeforeHidden = ViewCompat.getImportantForAccessibility(this.itemView);
            ViewCompat.setImportantForAccessibility(this.itemView, 4);
        }

        private void onLeftHiddenState() throws  {
            ViewCompat.setImportantForAccessibility(this.itemView, this.mWasImportantForAccessibilityBeforeHidden);
            this.mWasImportantForAccessibilityBeforeHidden = 0;
        }

        public String toString() throws  {
            StringBuilder $r1 = new StringBuilder("ViewHolder{" + Integer.toHexString(hashCode()) + " position=" + this.mPosition + " id=" + this.mItemId + ", oldPos=" + this.mOldPosition + ", pLpos:" + this.mPreLayoutPosition);
            if (isScrap()) {
                $r1.append(" scrap ").append(this.mInChangeScrap ? "[changeScrap]" : "[attachedScrap]");
            }
            if (isInvalid()) {
                $r1.append(" invalid");
            }
            if (!isBound()) {
                $r1.append(" unbound");
            }
            if (needsUpdate()) {
                $r1.append(" update");
            }
            if (isRemoved()) {
                $r1.append(" removed");
            }
            if (shouldIgnore()) {
                $r1.append(" ignored");
            }
            if (isTmpDetached()) {
                $r1.append(" tmpDetached");
            }
            if (!isRecyclable()) {
                $r1.append(" not recyclable(" + this.mIsRecyclableCount + ")");
            }
            if (isAdapterPositionUnknown()) {
                $r1.append(" undefined adapter position");
            }
            if (this.itemView.getParent() == null) {
                $r1.append(" no parent");
            }
            $r1.append("}");
            return $r1.toString();
        }

        public final void setIsRecyclable(boolean $z0) throws  {
            int $i0;
            if ($z0) {
                $i0 = this.mIsRecyclableCount - 1;
            } else {
                $i0 = this.mIsRecyclableCount + 1;
            }
            this.mIsRecyclableCount = $i0;
            if (this.mIsRecyclableCount < 0) {
                this.mIsRecyclableCount = 0;
                Log.e(AnalyticsEvents.ANALYTICS_EVENT_ADS_3RD_PARTY_EVENT_VIEW, "isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this);
            } else if (!$z0 && this.mIsRecyclableCount == 1) {
                this.mFlags |= 16;
            } else if ($z0 && this.mIsRecyclableCount == 0) {
                this.mFlags &= -17;
            }
        }

        public final boolean isRecyclable() throws  {
            return (this.mFlags & 16) == 0 && !ViewCompat.hasTransientState(this.itemView);
        }

        private boolean shouldBeKeptAsChild() throws  {
            return (this.mFlags & 16) != 0;
        }

        private boolean doesTransientStatePreventRecycling() throws  {
            return (this.mFlags & 16) == 0 && ViewCompat.hasTransientState(this.itemView);
        }

        boolean isUpdated() throws  {
            return (this.mFlags & 2) != 0;
        }
    }

    public void draw(android.graphics.Canvas r19) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:68:0x01b6
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
        r18 = this;
        r2 = 1;
        r0 = r18;
        r1 = r19;
        super.draw(r1);
        r0 = r18;
        r3 = r0.mItemDecorations;
        r4 = r3.size();
        r5 = 0;
    L_0x0011:
        if (r5 >= r4) goto L_0x002d;
    L_0x0013:
        r0 = r18;
        r3 = r0.mItemDecorations;
        r6 = r3.get(r5);
        r8 = r6;
        r8 = (android.support.v7.widget.RecyclerView.ItemDecoration) r8;
        r7 = r8;
        r0 = r18;
        r9 = r0.mState;
        r0 = r19;
        r1 = r18;
        r7.onDrawOver(r0, r1, r9);
        r5 = r5 + 1;
        goto L_0x0011;
    L_0x002d:
        r10 = 0;
        r0 = r18;
        r11 = r0.mLeftGlow;
        if (r11 == 0) goto L_0x0080;
    L_0x0034:
        r0 = r18;
        r11 = r0.mLeftGlow;
        r12 = r11.isFinished();
        if (r12 != 0) goto L_0x0080;
    L_0x003e:
        r0 = r19;
        r4 = r0.save();
        r0 = r18;
        r10 = r0.mClipToPadding;
        if (r10 == 0) goto L_0x01ba;
    L_0x004a:
        r0 = r18;
        r5 = r0.getPaddingBottom();
    L_0x0050:
        r13 = 1132920832; // 0x43870000 float:270.0 double:5.597372625E-315;
        r0 = r19;
        r0.rotate(r13);
        r0 = r18;
        r14 = r0.getHeight();
        r14 = -r14;
        r5 = r14 + r5;
        r15 = (float) r5;
        r13 = 0;
        r0 = r19;
        r0.translate(r15, r13);
        r0 = r18;
        r11 = r0.mLeftGlow;
        if (r11 == 0) goto L_0x01c0;
    L_0x006e:
        r0 = r18;
        r11 = r0.mLeftGlow;
        r0 = r19;
        r10 = r11.draw(r0);
        if (r10 == 0) goto L_0x01c0;
    L_0x007a:
        r10 = 1;
    L_0x007b:
        r0 = r19;
        r0.restoreToCount(r4);
    L_0x0080:
        r0 = r18;
        r11 = r0.mTopGlow;
        if (r11 == 0) goto L_0x00cc;
    L_0x0086:
        r0 = r18;
        r11 = r0.mTopGlow;
        r12 = r11.isFinished();
        if (r12 != 0) goto L_0x00cc;
    L_0x0090:
        r0 = r19;
        r4 = r0.save();
        r0 = r18;
        r12 = r0.mClipToPadding;
        if (r12 == 0) goto L_0x00b3;
    L_0x009c:
        r0 = r18;
        r5 = r0.getPaddingLeft();
        r15 = (float) r5;
        r0 = r18;
        r5 = r0.getPaddingTop();
        r0 = (float) r5;
        r16 = r0;
        r0 = r19;
        r1 = r16;
        r0.translate(r15, r1);
    L_0x00b3:
        r0 = r18;
        r11 = r0.mTopGlow;
        if (r11 == 0) goto L_0x01c6;
    L_0x00b9:
        r0 = r18;
        r11 = r0.mTopGlow;
        r0 = r19;
        r12 = r11.draw(r0);
        if (r12 == 0) goto L_0x01c6;
    L_0x00c5:
        r12 = 1;
    L_0x00c6:
        r10 = r10 | r12;
        r0 = r19;
        r0.restoreToCount(r4);
    L_0x00cc:
        r0 = r18;
        r11 = r0.mRightGlow;
        if (r11 == 0) goto L_0x0122;
    L_0x00d2:
        r0 = r18;
        r11 = r0.mRightGlow;
        r12 = r11.isFinished();
        if (r12 != 0) goto L_0x0122;
    L_0x00dc:
        r0 = r19;
        r4 = r0.save();
        r0 = r18;
        r5 = r0.getWidth();
        r0 = r18;
        r12 = r0.mClipToPadding;
        if (r12 == 0) goto L_0x01c8;
    L_0x00ee:
        r0 = r18;
        r14 = r0.getPaddingTop();
    L_0x00f4:
        r13 = 1119092736; // 0x42b40000 float:90.0 double:5.529052754E-315;
        r0 = r19;
        r0.rotate(r13);
        r14 = -r14;
        r15 = (float) r14;
        r5 = -r5;
        r0 = (float) r5;
        r16 = r0;
        r0 = r19;
        r1 = r16;
        r0.translate(r15, r1);
        r0 = r18;
        r11 = r0.mRightGlow;
        if (r11 == 0) goto L_0x01ca;
    L_0x010f:
        r0 = r18;
        r11 = r0.mRightGlow;
        r0 = r19;
        r12 = r11.draw(r0);
        if (r12 == 0) goto L_0x01ca;
    L_0x011b:
        r12 = 1;
    L_0x011c:
        r10 = r10 | r12;
        r0 = r19;
        r0.restoreToCount(r4);
    L_0x0122:
        r0 = r18;
        r11 = r0.mBottomGlow;
        if (r11 == 0) goto L_0x018d;
    L_0x0128:
        r0 = r18;
        r11 = r0.mBottomGlow;
        r12 = r11.isFinished();
        if (r12 != 0) goto L_0x018d;
    L_0x0132:
        r0 = r19;
        r4 = r0.save();
        r13 = 1127481344; // 0x43340000 float:180.0 double:5.570497984E-315;
        r0 = r19;
        r0.rotate(r13);
        r0 = r18;
        r12 = r0.mClipToPadding;
        if (r12 == 0) goto L_0x01cc;
    L_0x0146:
        r0 = r18;
        r5 = r0.getWidth();
        r5 = -r5;
        r0 = r18;
        r14 = r0.getPaddingRight();
        r5 = r5 + r14;
        r15 = (float) r5;
        r0 = r18;
        r5 = r0.getHeight();
        r5 = -r5;
        goto L_0x0160;
    L_0x015d:
        goto L_0x00f4;
    L_0x0160:
        r0 = r18;
        r14 = r0.getPaddingBottom();
        r5 = r5 + r14;
        r0 = (float) r5;
        r16 = r0;
        r0 = r19;
        r1 = r16;
        r0.translate(r15, r1);
    L_0x0171:
        r0 = r18;
        r11 = r0.mBottomGlow;
        goto L_0x0179;
    L_0x0176:
        goto L_0x011c;
    L_0x0179:
        if (r11 == 0) goto L_0x01e6;
    L_0x017b:
        r0 = r18;
        r11 = r0.mBottomGlow;
        r0 = r19;
        r12 = r11.draw(r0);
        if (r12 == 0) goto L_0x01e6;
    L_0x0187:
        r10 = r10 | r2;
        r0 = r19;
        r0.restoreToCount(r4);
    L_0x018d:
        if (r10 != 0) goto L_0x01ae;
    L_0x018f:
        r0 = r18;
        r0 = r0.mItemAnimator;
        r17 = r0;
        if (r17 == 0) goto L_0x01ae;
    L_0x0197:
        r0 = r18;
        r3 = r0.mItemDecorations;
        r4 = r3.size();
        if (r4 <= 0) goto L_0x01ae;
    L_0x01a1:
        r0 = r18;
        r0 = r0.mItemAnimator;
        r17 = r0;
        r2 = r0.isRunning();
        if (r2 == 0) goto L_0x01ae;
    L_0x01ad:
        r10 = 1;
    L_0x01ae:
        if (r10 == 0) goto L_0x01e8;
    L_0x01b0:
        r0 = r18;
        android.support.v4.view.ViewCompat.postInvalidateOnAnimation(r0);
        return;
        goto L_0x01ba;
    L_0x01b7:
        goto L_0x0050;
    L_0x01ba:
        r5 = 0;
        goto L_0x01b7;
        goto L_0x01c0;
    L_0x01bd:
        goto L_0x007b;
    L_0x01c0:
        r10 = 0;
        goto L_0x01bd;
        goto L_0x01c6;
    L_0x01c3:
        goto L_0x00c6;
    L_0x01c6:
        r12 = 0;
        goto L_0x01c3;
    L_0x01c8:
        r14 = 0;
        goto L_0x015d;
    L_0x01ca:
        r12 = 0;
        goto L_0x0176;
    L_0x01cc:
        r0 = r18;
        r5 = r0.getWidth();
        r5 = -r5;
        r15 = (float) r5;
        r0 = r18;
        r5 = r0.getHeight();
        r5 = -r5;
        r0 = (float) r5;
        r16 = r0;
        r0 = r19;
        r1 = r16;
        r0.translate(r15, r1);
        goto L_0x0171;
    L_0x01e6:
        r2 = 0;
        goto L_0x0187;
    L_0x01e8:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.draw(android.graphics.Canvas):void");
    }

    public boolean onInterceptTouchEvent(android.view.MotionEvent r23) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:66:0x01ea
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
        r22 = this;
        r0 = r22;
        r2 = r0.mLayoutFrozen;
        if (r2 == 0) goto L_0x0008;
    L_0x0006:
        r3 = 0;
        return r3;
    L_0x0008:
        r0 = r22;
        r1 = r23;
        r2 = r0.dispatchOnItemTouchIntercept(r1);
        if (r2 == 0) goto L_0x0019;
    L_0x0012:
        r0 = r22;
        r0.cancelTouch();
        r3 = 1;
        return r3;
    L_0x0019:
        r0 = r22;
        r4 = r0.mLayout;
        if (r4 != 0) goto L_0x0021;
    L_0x001f:
        r3 = 0;
        return r3;
    L_0x0021:
        r0 = r22;
        r4 = r0.mLayout;
        r2 = r4.canScrollHorizontally();
        r0 = r22;
        r4 = r0.mLayout;
        r5 = r4.canScrollVertically();
        r0 = r22;
        r6 = r0.mVelocityTracker;
        if (r6 != 0) goto L_0x003f;
    L_0x0037:
        r6 = android.view.VelocityTracker.obtain();
        r0 = r22;
        r0.mVelocityTracker = r6;
    L_0x003f:
        r0 = r22;
        r6 = r0.mVelocityTracker;
        r0 = r23;
        r6.addMovement(r0);
        r0 = r23;
        r7 = android.support.v4.view.MotionEventCompat.getActionMasked(r0);
        r0 = r23;
        r8 = android.support.v4.view.MotionEventCompat.getActionIndex(r0);
        switch(r7) {
            case 0: goto L_0x0061;
            case 1: goto L_0x01f6;
            case 2: goto L_0x0115;
            case 3: goto L_0x020b;
            case 4: goto L_0x0058;
            case 5: goto L_0x00e4;
            case 6: goto L_0x01ee;
            default: goto L_0x0057;
        };
    L_0x0057:
        goto L_0x0058;
    L_0x0058:
        r0 = r22;
        r8 = r0.mScrollState;
        r3 = 1;
        if (r8 != r3) goto L_0x0211;
    L_0x005f:
        r3 = 1;
        return r3;
    L_0x0061:
        r0 = r22;
        r9 = r0.mIgnoreMotionEventTillDown;
        if (r9 == 0) goto L_0x006c;
    L_0x0067:
        r3 = 0;
        r0 = r22;
        r0.mIgnoreMotionEventTillDown = r3;
    L_0x006c:
        r3 = 0;
        r0 = r23;
        r8 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r3);
        r0 = r22;
        r0.mScrollPointerId = r8;
        r0 = r23;
        r10 = r0.getX();
        r11 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r10 = r10 + r11;
        r8 = (int) r10;
        r0 = r22;
        r0.mLastTouchX = r8;
        r0 = r22;
        r0.mInitialTouchX = r8;
        r0 = r23;
        r10 = r0.getY();
        r11 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r10 = r10 + r11;
        r8 = (int) r10;
        r0 = r22;
        r0.mLastTouchY = r8;
        goto L_0x009d;
    L_0x009a:
        goto L_0x0058;
    L_0x009d:
        r0 = r22;
        r0.mInitialTouchY = r8;
        r0 = r22;
        r8 = r0.mScrollState;
        r3 = 2;
        if (r8 != r3) goto L_0x00bc;
    L_0x00a8:
        r0 = r22;
        r12 = r0.getParent();
        r3 = 1;
        r12.requestDisallowInterceptTouchEvent(r3);
        goto L_0x00b6;
    L_0x00b3:
        goto L_0x0058;
    L_0x00b6:
        r3 = 1;
        r0 = r22;
        r0.setScrollState(r3);
    L_0x00bc:
        r0 = r22;
        r13 = r0.mNestedOffsets;
        r0 = r22;
        r14 = r0.mNestedOffsets;
        r3 = 1;
        r15 = 0;
        r14[r3] = r15;
        r3 = 0;
        r15 = 0;
        r13[r3] = r15;
        r16 = 0;
        if (r2 == 0) goto L_0x00d4;
    L_0x00d0:
        r3 = 0;
        r15 = 1;
        r16 = r3 | r15;
    L_0x00d4:
        if (r5 == 0) goto L_0x00dc;
    L_0x00d6:
        r3 = 2;
        r0 = r16;
        r0 = r0 | r3;
        r16 = r0;
    L_0x00dc:
        r0 = r22;
        r1 = r16;
        r0.startNestedScroll(r1);
        goto L_0x009a;
    L_0x00e4:
        r0 = r23;
        r7 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r8);
        r0 = r22;
        r0.mScrollPointerId = r7;
        r0 = r23;
        r10 = android.support.v4.view.MotionEventCompat.getX(r0, r8);
        r11 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r10 = r10 + r11;
        r7 = (int) r10;
        r0 = r22;
        r0.mLastTouchX = r7;
        r0 = r22;
        r0.mInitialTouchX = r7;
        r0 = r23;
        r10 = android.support.v4.view.MotionEventCompat.getY(r0, r8);
        r11 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r10 = r10 + r11;
        r8 = (int) r10;
        r0 = r22;
        r0.mLastTouchY = r8;
        r0 = r22;
        r0.mInitialTouchY = r8;
        goto L_0x00b3;
    L_0x0115:
        r0 = r22;
        r8 = r0.mScrollPointerId;
        r0 = r23;
        r8 = android.support.v4.view.MotionEventCompat.findPointerIndex(r0, r8);
        if (r8 >= 0) goto L_0x0157;
    L_0x0121:
        r17 = new java.lang.StringBuilder;
        r0 = r17;
        r0.<init>();
        r18 = "Error processing scroll; pointer index for id ";
        r0 = r17;
        r1 = r18;
        r17 = r0.append(r1);
        r0 = r22;
        r8 = r0.mScrollPointerId;
        r0 = r17;
        r17 = r0.append(r8);
        r18 = " not found. Did any MotionEvents get skipped?";
        r0 = r17;
        r1 = r18;
        r17 = r0.append(r1);
        r0 = r17;
        r19 = r0.toString();
        r18 = "RecyclerView";
        r0 = r18;
        r1 = r19;
        android.util.Log.e(r0, r1);
        r3 = 0;
        return r3;
    L_0x0157:
        r0 = r23;
        r10 = android.support.v4.view.MotionEventCompat.getX(r0, r8);
        r11 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r10 = r10 + r11;
        r7 = (int) r10;
        r0 = r23;
        r10 = android.support.v4.view.MotionEventCompat.getY(r0, r8);
        r11 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r10 = r10 + r11;
        r8 = (int) r10;
        r0 = r22;
        r0 = r0.mScrollState;
        r20 = r0;
        r3 = 1;
        r0 = r20;
        if (r0 == r3) goto L_0x0058;
    L_0x0178:
        r0 = r22;
        r0 = r0.mInitialTouchX;
        r20 = r0;
        r7 = r7 - r0;
        r0 = r22;
        r0 = r0.mInitialTouchY;
        r20 = r0;
        r8 = r8 - r0;
        r9 = 0;
        if (r2 == 0) goto L_0x01b3;
    L_0x0189:
        r20 = java.lang.Math.abs(r7);
        r0 = r22;
        r0 = r0.mTouchSlop;
        r21 = r0;
        r0 = r20;
        r1 = r21;
        if (r0 <= r1) goto L_0x01b3;
    L_0x0199:
        r0 = r22;
        r0 = r0.mInitialTouchX;
        r20 = r0;
        r0 = r22;
        r0 = r0.mTouchSlop;
        r21 = r0;
        if (r7 >= 0) goto L_0x01e4;
    L_0x01a7:
        r16 = -1;
    L_0x01a9:
        r7 = r16 * r21;
        r0 = r20;
        r7 = r7 + r0;
        r0 = r22;
        r0.mLastTouchX = r7;
        r9 = 1;
    L_0x01b3:
        if (r5 == 0) goto L_0x01d7;
    L_0x01b5:
        r7 = java.lang.Math.abs(r8);
        r0 = r22;
        r0 = r0.mTouchSlop;
        r20 = r0;
        if (r7 <= r0) goto L_0x01d7;
    L_0x01c1:
        r0 = r22;
        r7 = r0.mInitialTouchY;
        r0 = r22;
        r0 = r0.mTouchSlop;
        r20 = r0;
        if (r8 >= 0) goto L_0x01e7;
    L_0x01cd:
        r16 = -1;
    L_0x01cf:
        r8 = r16 * r20;
        r8 = r8 + r7;
        r0 = r22;
        r0.mLastTouchY = r8;
        r9 = 1;
    L_0x01d7:
        if (r9 == 0) goto L_0x0058;
    L_0x01d9:
        goto L_0x01dd;
    L_0x01da:
        goto L_0x0058;
    L_0x01dd:
        r3 = 1;
        r0 = r22;
        r0.setScrollState(r3);
        goto L_0x01da;
    L_0x01e4:
        r16 = 1;
        goto L_0x01a9;
    L_0x01e7:
        r16 = 1;
        goto L_0x01cf;
        goto L_0x01ee;
    L_0x01eb:
        goto L_0x0058;
    L_0x01ee:
        r0 = r22;
        r1 = r23;
        r0.onPointerUp(r1);
        goto L_0x01eb;
    L_0x01f6:
        r0 = r22;
        r6 = r0.mVelocityTracker;
        r6.clear();
        goto L_0x0201;
    L_0x01fe:
        goto L_0x0058;
    L_0x0201:
        r0 = r22;
        r0.stopNestedScroll();
        goto L_0x01fe;
        goto L_0x020b;
    L_0x0208:
        goto L_0x0058;
    L_0x020b:
        r0 = r22;
        r0.cancelTouch();
        goto L_0x0208;
    L_0x0211:
        r3 = 0;
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public boolean onTouchEvent(android.view.MotionEvent r28) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:74:0x02aa
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
        r27 = this;
        r0 = r27;
        r2 = r0.mLayoutFrozen;
        if (r2 != 0) goto L_0x000c;
    L_0x0006:
        r0 = r27;
        r2 = r0.mIgnoreMotionEventTillDown;
        if (r2 == 0) goto L_0x000e;
    L_0x000c:
        r3 = 0;
        return r3;
    L_0x000e:
        r0 = r27;
        r1 = r28;
        r2 = r0.dispatchOnItemTouch(r1);
        if (r2 == 0) goto L_0x001f;
    L_0x0018:
        r0 = r27;
        r0.cancelTouch();
        r3 = 1;
        return r3;
    L_0x001f:
        r0 = r27;
        r4 = r0.mLayout;
        if (r4 != 0) goto L_0x0027;
    L_0x0025:
        r3 = 0;
        return r3;
    L_0x0027:
        r0 = r27;
        r4 = r0.mLayout;
        r5 = r4.canScrollHorizontally();
        r0 = r27;
        r4 = r0.mLayout;
        r6 = r4.canScrollVertically();
        r0 = r27;
        r7 = r0.mVelocityTracker;
        if (r7 != 0) goto L_0x0045;
    L_0x003d:
        r7 = android.view.VelocityTracker.obtain();
        r0 = r27;
        r0.mVelocityTracker = r7;
    L_0x0045:
        r2 = 0;
        r0 = r28;
        r8 = android.view.MotionEvent.obtain(r0);
        r0 = r28;
        r9 = android.support.v4.view.MotionEventCompat.getActionMasked(r0);
        r0 = r28;
        r10 = android.support.v4.view.MotionEventCompat.getActionIndex(r0);
        if (r9 != 0) goto L_0x006a;
    L_0x005a:
        r0 = r27;
        r11 = r0.mNestedOffsets;
        r0 = r27;
        r12 = r0.mNestedOffsets;
        r3 = 1;
        r13 = 0;
        r12[r3] = r13;
        r3 = 0;
        r13 = 0;
        r11[r3] = r13;
    L_0x006a:
        r0 = r27;
        r11 = r0.mNestedOffsets;
        r3 = 0;
        r14 = r11[r3];
        r15 = (float) r14;
        r0 = r27;
        r11 = r0.mNestedOffsets;
        r3 = 1;
        r14 = r11[r3];
        r0 = (float) r14;
        r16 = r0;
        r8.offsetLocation(r15, r0);
        switch(r9) {
            case 0: goto L_0x0091;
            case 1: goto L_0x02b6;
            case 2: goto L_0x0117;
            case 3: goto L_0x031f;
            case 4: goto L_0x0083;
            case 5: goto L_0x00e2;
            case 6: goto L_0x02ae;
            default: goto L_0x0082;
        };
    L_0x0082:
        goto L_0x0083;
    L_0x0083:
        if (r2 != 0) goto L_0x008c;
    L_0x0085:
        r0 = r27;
        r7 = r0.mVelocityTracker;
        r7.addMovement(r8);
    L_0x008c:
        r8.recycle();
        r3 = 1;
        return r3;
    L_0x0091:
        r3 = 0;
        r0 = r28;
        r10 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r3);
        r0 = r27;
        r0.mScrollPointerId = r10;
        r0 = r28;
        r15 = r0.getX();
        r17 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r0 = r17;
        r15 = r15 + r0;
        r10 = (int) r15;
        r0 = r27;
        r0.mLastTouchX = r10;
        r0 = r27;
        r0.mInitialTouchX = r10;
        r0 = r28;
        r15 = r0.getY();
        r17 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r0 = r17;
        r15 = r15 + r0;
        r10 = (int) r15;
        r0 = r27;
        r0.mLastTouchY = r10;
        r0 = r27;
        r0.mInitialTouchY = r10;
        r18 = 0;
        if (r5 == 0) goto L_0x00d2;
    L_0x00ca:
        goto L_0x00ce;
    L_0x00cb:
        goto L_0x0083;
    L_0x00ce:
        r3 = 0;
        r13 = 1;
        r18 = r3 | r13;
    L_0x00d2:
        if (r6 == 0) goto L_0x00da;
    L_0x00d4:
        r3 = 2;
        r0 = r18;
        r0 = r0 | r3;
        r18 = r0;
    L_0x00da:
        r0 = r27;
        r1 = r18;
        r0.startNestedScroll(r1);
        goto L_0x0083;
    L_0x00e2:
        r0 = r28;
        r9 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r10);
        r0 = r27;
        r0.mScrollPointerId = r9;
        r0 = r28;
        r15 = android.support.v4.view.MotionEventCompat.getX(r0, r10);
        r17 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r0 = r17;
        r15 = r15 + r0;
        r9 = (int) r15;
        r0 = r27;
        r0.mLastTouchX = r9;
        r0 = r27;
        r0.mInitialTouchX = r9;
        r0 = r28;
        r15 = android.support.v4.view.MotionEventCompat.getY(r0, r10);
        r17 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r0 = r17;
        r15 = r15 + r0;
        r10 = (int) r15;
        r0 = r27;
        r0.mLastTouchY = r10;
        r0 = r27;
        r0.mInitialTouchY = r10;
        goto L_0x00cb;
    L_0x0117:
        r0 = r27;
        r10 = r0.mScrollPointerId;
        r0 = r28;
        r10 = android.support.v4.view.MotionEventCompat.findPointerIndex(r0, r10);
        if (r10 >= 0) goto L_0x0159;
    L_0x0123:
        r19 = new java.lang.StringBuilder;
        r0 = r19;
        r0.<init>();
        r20 = "Error processing scroll; pointer index for id ";
        r0 = r19;
        r1 = r20;
        r19 = r0.append(r1);
        r0 = r27;
        r10 = r0.mScrollPointerId;
        r0 = r19;
        r19 = r0.append(r10);
        r20 = " not found. Did any MotionEvents get skipped?";
        r0 = r19;
        r1 = r20;
        r19 = r0.append(r1);
        r0 = r19;
        r21 = r0.toString();
        r20 = "RecyclerView";
        r0 = r20;
        r1 = r21;
        android.util.Log.e(r0, r1);
        r3 = 0;
        return r3;
    L_0x0159:
        r0 = r28;
        r15 = android.support.v4.view.MotionEventCompat.getX(r0, r10);
        r17 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r0 = r17;
        r15 = r15 + r0;
        r14 = (int) r15;
        r0 = r28;
        r15 = android.support.v4.view.MotionEventCompat.getY(r0, r10);
        r17 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r0 = r17;
        r15 = r15 + r0;
        r10 = (int) r15;
        r0 = r27;
        r9 = r0.mLastTouchX;
        r9 = r9 - r14;
        r0 = r27;
        r0 = r0.mLastTouchY;
        r22 = r0;
        r0 = r0 - r10;
        r22 = r0;
        r0 = r27;
        r11 = r0.mScrollConsumed;
        r0 = r27;
        r12 = r0.mScrollOffset;
        r0 = r27;
        r1 = r22;
        r23 = r0.dispatchNestedPreScroll(r9, r1, r11, r12);
        if (r23 == 0) goto L_0x01f4;
    L_0x0193:
        r0 = r27;
        r11 = r0.mScrollConsumed;
        r3 = 0;
        r24 = r11[r3];
        r0 = r24;
        r9 = r9 - r0;
        r0 = r27;
        r11 = r0.mScrollConsumed;
        r3 = 1;
        r24 = r11[r3];
        r0 = r22;
        r1 = r24;
        r0 = r0 - r1;
        r22 = r0;
        r0 = r27;
        r11 = r0.mScrollOffset;
        r3 = 0;
        r24 = r11[r3];
        r0 = r24;
        r15 = (float) r0;
        r0 = r27;
        r11 = r0.mScrollOffset;
        r3 = 1;
        r24 = r11[r3];
        r0 = r24;
        r0 = (float) r0;
        r16 = r0;
        r8.offsetLocation(r15, r0);
        r0 = r27;
        r11 = r0.mNestedOffsets;
        r3 = 0;
        r24 = r11[r3];
        r0 = r27;
        r12 = r0.mScrollOffset;
        r3 = 0;
        r25 = r12[r3];
        r0 = r24;
        r1 = r25;
        r0 = r0 + r1;
        r24 = r0;
        r3 = 0;
        r11[r3] = r24;
        r0 = r27;
        r11 = r0.mNestedOffsets;
        r3 = 1;
        r24 = r11[r3];
        r0 = r27;
        r12 = r0.mScrollOffset;
        r3 = 1;
        r25 = r12[r3];
        r0 = r24;
        r1 = r25;
        r0 = r0 + r1;
        r24 = r0;
        r3 = 1;
        r11[r3] = r24;
    L_0x01f4:
        r0 = r27;
        r0 = r0.mScrollState;
        r24 = r0;
        r3 = 1;
        r0 = r24;
        if (r0 == r3) goto L_0x024b;
    L_0x01ff:
        r23 = 0;
        if (r5 == 0) goto L_0x021e;
    L_0x0203:
        r24 = java.lang.Math.abs(r9);
        r0 = r27;
        r0 = r0.mTouchSlop;
        r25 = r0;
        r0 = r24;
        r1 = r25;
        if (r0 <= r1) goto L_0x021e;
    L_0x0213:
        if (r9 <= 0) goto L_0x028f;
    L_0x0215:
        r0 = r27;
        r0 = r0.mTouchSlop;
        r24 = r0;
        r9 = r9 - r0;
    L_0x021c:
        r23 = 1;
    L_0x021e:
        if (r6 == 0) goto L_0x0243;
    L_0x0220:
        r0 = r22;
        r24 = java.lang.Math.abs(r0);
        r0 = r27;
        r0 = r0.mTouchSlop;
        r25 = r0;
        r0 = r24;
        r1 = r25;
        if (r0 <= r1) goto L_0x0243;
    L_0x0232:
        if (r22 <= 0) goto L_0x0297;
    L_0x0234:
        r0 = r27;
        r0 = r0.mTouchSlop;
        r24 = r0;
        r0 = r22;
        r1 = r24;
        r0 = r0 - r1;
        r22 = r0;
    L_0x0241:
        r23 = 1;
    L_0x0243:
        if (r23 == 0) goto L_0x024b;
    L_0x0245:
        r3 = 1;
        r0 = r27;
        r0.setScrollState(r3);
    L_0x024b:
        r0 = r27;
        r0 = r0.mScrollState;
        r24 = r0;
        r3 = 1;
        r0 = r24;
        if (r0 != r3) goto L_0x0083;
    L_0x0256:
        r0 = r27;
        r11 = r0.mScrollOffset;
        r3 = 0;
        r24 = r11[r3];
        r0 = r24;
        r14 = r14 - r0;
        r0 = r27;
        r0.mLastTouchX = r14;
        r0 = r27;
        r11 = r0.mScrollOffset;
        r3 = 1;
        r14 = r11[r3];
        r10 = r10 - r14;
        r0 = r27;
        r0.mLastTouchY = r10;
        if (r5 == 0) goto L_0x02a5;
    L_0x0272:
        if (r6 == 0) goto L_0x02a7;
    L_0x0274:
        r0 = r27;
        r1 = r22;
        r5 = r0.scrollByInternal(r9, r1, r8);
        if (r5 == 0) goto L_0x0083;
    L_0x027e:
        r0 = r27;
        r26 = r0.getParent();
        goto L_0x0288;
    L_0x0285:
        goto L_0x0083;
    L_0x0288:
        r3 = 1;
        r0 = r26;
        r0.requestDisallowInterceptTouchEvent(r3);
        goto L_0x0285;
    L_0x028f:
        r0 = r27;
        r0 = r0.mTouchSlop;
        r24 = r0;
        r9 = r9 + r0;
        goto L_0x021c;
    L_0x0297:
        r0 = r27;
        r0 = r0.mTouchSlop;
        r24 = r0;
        r0 = r22;
        r1 = r24;
        r0 = r0 + r1;
        r22 = r0;
        goto L_0x0241;
    L_0x02a5:
        r9 = 0;
        goto L_0x0272;
    L_0x02a7:
        r22 = 0;
        goto L_0x0274;
        goto L_0x02ae;
    L_0x02ab:
        goto L_0x0083;
    L_0x02ae:
        r0 = r27;
        r1 = r28;
        r0.onPointerUp(r1);
        goto L_0x02ab;
    L_0x02b6:
        r0 = r27;
        r7 = r0.mVelocityTracker;
        r7.addMovement(r8);
        r2 = 1;
        r0 = r27;
        r7 = r0.mVelocityTracker;
        r0 = r27;
        r10 = r0.mMaxFlingVelocity;
        r15 = (float) r10;
        r3 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r7.computeCurrentVelocity(r3, r15);
        if (r5 == 0) goto L_0x0316;
    L_0x02ce:
        r0 = r27;
        r7 = r0.mVelocityTracker;
        r0 = r27;
        r10 = r0.mScrollPointerId;
        r15 = android.support.v4.view.VelocityTrackerCompat.getXVelocity(r7, r10);
        r15 = -r15;
    L_0x02db:
        if (r6 == 0) goto L_0x0318;
    L_0x02dd:
        r0 = r27;
        r7 = r0.mVelocityTracker;
        r0 = r27;
        r10 = r0.mScrollPointerId;
        r16 = android.support.v4.view.VelocityTrackerCompat.getYVelocity(r7, r10);
        r0 = r16;
        r0 = -r0;
        r16 = r0;
    L_0x02ee:
        r17 = 0;
        r18 = (r15 > r17 ? 1 : (r15 == r17 ? 0 : -1));
        if (r18 != 0) goto L_0x02fa;
    L_0x02f4:
        r17 = 0;
        r18 = (r16 > r17 ? 1 : (r16 == r17 ? 0 : -1));
        if (r18 == 0) goto L_0x0306;
    L_0x02fa:
        r10 = (int) r15;
        r0 = r16;
        r9 = (int) r0;
        r0 = r27;
        r5 = r0.fling(r10, r9);
        if (r5 != 0) goto L_0x0310;
    L_0x0306:
        r3 = 0;
        r0 = r27;
        r0.setScrollState(r3);
        goto L_0x0310;
    L_0x030d:
        goto L_0x0083;
    L_0x0310:
        r0 = r27;
        r0.resetTouch();
        goto L_0x030d;
    L_0x0316:
        r15 = 0;
        goto L_0x02db;
    L_0x0318:
        r16 = 0;
        goto L_0x02ee;
        goto L_0x031f;
    L_0x031c:
        goto L_0x0083;
    L_0x031f:
        r0 = r27;
        r0.cancelTouch();
        goto L_0x031c;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    static {
        boolean $z0 = VERSION.SDK_INT == 18 || VERSION.SDK_INT == 19 || VERSION.SDK_INT == 20;
        FORCE_INVALIDATE_DISPLAY_LIST = $z0;
        if (VERSION.SDK_INT >= 23) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        ALLOW_SIZE_IN_UNSPECIFIED_SPEC = $z0;
    }

    public RecyclerView(Context $r1) throws  {
        this($r1, null);
    }

    public RecyclerView(Context $r1, @Nullable AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public RecyclerView(Context $r1, @Nullable AttributeSet $r2, int $i0) throws  {
        boolean z;
        super($r1, $r2, $i0);
        RecyclerView recyclerView = this;
        this.mObserver = new RecyclerViewDataObserver();
        this.mRecycler = new Recycler();
        this.mViewInfoStore = new ViewInfoStore();
        this.mUpdateChildViewsRunnable = new C02501();
        this.mTempRect = new Rect();
        this.mItemDecorations = new ArrayList();
        this.mOnItemTouchListeners = new ArrayList();
        this.mEatRequestLayout = 0;
        this.mDataSetHasChangedAfterLayout = false;
        this.mLayoutOrScrollCounter = 0;
        this.mItemAnimator = new DefaultItemAnimator();
        this.mScrollState = 0;
        this.mScrollPointerId = -1;
        this.mScrollFactor = Float.MIN_VALUE;
        this.mViewFlinger = new ViewFlinger();
        this.mState = new State();
        this.mItemsAddedOrRemoved = false;
        this.mItemsChanged = false;
        RecyclerView recyclerView2 = this;
        this.mItemAnimatorListener = new ItemAnimatorRestoreListener();
        this.mPostedAnimatorRunner = false;
        int[] iArr = new int[2];
        int[] $r13 = iArr;
        this.mMinMaxLayoutPositions = iArr;
        iArr = new int[2];
        $r13 = iArr;
        this.mScrollOffset = iArr;
        iArr = new int[2];
        $r13 = iArr;
        this.mScrollConsumed = iArr;
        iArr = new int[2];
        $r13 = iArr;
        this.mNestedOffsets = iArr;
        this.mItemAnimatorRunner = new C02512();
        this.mViewInfoProcessCallback = new C02534();
        setScrollContainer(true);
        setFocusableInTouchMode(true);
        if (VERSION.SDK_INT >= 16) {
            z = true;
        } else {
            z = false;
        }
        this.mPostUpdatesOnAnimation = z;
        ViewConfiguration $r16 = ViewConfiguration.get($r1);
        this.mTouchSlop = $r16.getScaledTouchSlop();
        this.mMinFlingVelocity = $r16.getScaledMinimumFlingVelocity();
        this.mMaxFlingVelocity = $r16.getScaledMaximumFlingVelocity();
        if (ViewCompat.getOverScrollMode(this) == 2) {
            z = true;
        } else {
            z = false;
        }
        setWillNotDraw(z);
        this.mItemAnimator.setListener(this.mItemAnimatorListener);
        initAdapterManager();
        initChildrenHelper();
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        this.mAccessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        setAccessibilityDelegateCompat(new RecyclerViewAccessibilityDelegate(this));
        z = true;
        if ($r2 != null) {
            TypedArray $r23 = $r1.obtainStyledAttributes($r2, C0195R.styleable.RecyclerView, $i0, 0);
            String $r24 = $r23.getString(C0195R.styleable.RecyclerView_layoutManager);
            $r23.recycle();
            createLayoutManager($r1, $r24, $r2, $i0, 0);
            if (VERSION.SDK_INT >= 21) {
                $r23 = $r1.obtainStyledAttributes($r2, NESTED_SCROLLING_ATTRS, $i0, 0);
                z = $r23.getBoolean(0, true);
                $r23.recycle();
            }
        }
        setNestedScrollingEnabled(z);
    }

    public RecyclerViewAccessibilityDelegate getCompatAccessibilityDelegate() throws  {
        return this.mAccessibilityDelegate;
    }

    public void setAccessibilityDelegateCompat(RecyclerViewAccessibilityDelegate $r1) throws  {
        this.mAccessibilityDelegate = $r1;
        ViewCompat.setAccessibilityDelegate(this, this.mAccessibilityDelegate);
    }

    private void createLayoutManager(Context $r1, String $r5, AttributeSet $r2, int $i0, int $i1) throws  {
        if ($r5 != null) {
            $r5 = $r5.trim();
            if ($r5.length() != 0) {
                $r5 = getFullClassName($r1, $r5);
                try {
                    ClassLoader $r7;
                    Constructor $r10;
                    if (isInEditMode()) {
                        $r7 = getClass().getClassLoader();
                    } else {
                        $r7 = $r1.getClassLoader();
                    }
                    Class $r6 = $r7.loadClass($r5).asSubclass(LayoutManager.class);
                    Object[] $r8 = null;
                    try {
                        $r10 = $r6.getConstructor(LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE);
                        Object[] $r3 = new Object[4];
                        $r3[0] = $r1;
                        $r3[1] = $r2;
                        $r3[2] = Integer.valueOf($i0);
                        $r3[3] = Integer.valueOf($i1);
                        $r8 = $r3;
                    } catch (Throwable $r14) {
                        try {
                            $r10 = $r6.getConstructor(new Class[0]);
                        } catch (Throwable $r4) {
                            $r4.initCause($r14);
                            throw new IllegalStateException($r2.getPositionDescription() + ": Error creating LayoutManager " + $r5, $r4);
                        }
                    }
                    $r10.setAccessible(true);
                    setLayoutManager((LayoutManager) $r10.newInstance($r8));
                } catch (Throwable $r18) {
                    throw new IllegalStateException($r2.getPositionDescription() + ": Unable to find LayoutManager " + $r5, $r18);
                } catch (Throwable $r19) {
                    throw new IllegalStateException($r2.getPositionDescription() + ": Could not instantiate the LayoutManager: " + $r5, $r19);
                } catch (Throwable $r20) {
                    throw new IllegalStateException($r2.getPositionDescription() + ": Could not instantiate the LayoutManager: " + $r5, $r20);
                } catch (Throwable $r21) {
                    throw new IllegalStateException($r2.getPositionDescription() + ": Cannot access non-public constructor " + $r5, $r21);
                } catch (Throwable $r22) {
                    throw new IllegalStateException($r2.getPositionDescription() + ": Class is not a LayoutManager " + $r5, $r22);
                }
            }
        }
    }

    private String getFullClassName(Context $r1, String $r2) throws  {
        if ($r2.charAt(0) == FilenameUtils.EXTENSION_SEPARATOR) {
            return $r1.getPackageName() + $r2;
        }
        return !$r2.contains(FileUploadSession.SEPARATOR) ? RecyclerView.class.getPackage().getName() + FilenameUtils.EXTENSION_SEPARATOR + $r2 : $r2;
    }

    private void initChildrenHelper() throws  {
        this.mChildHelper = new ChildHelper(new C02545());
    }

    void initAdapterManager() throws  {
        this.mAdapterHelper = new AdapterHelper(new C02556());
    }

    public void setHasFixedSize(boolean $z0) throws  {
        this.mHasFixedSize = $z0;
    }

    public boolean hasFixedSize() throws  {
        return this.mHasFixedSize;
    }

    public void setClipToPadding(boolean $z0) throws  {
        if ($z0 != this.mClipToPadding) {
            invalidateGlows();
        }
        this.mClipToPadding = $z0;
        super.setClipToPadding($z0);
        if (this.mFirstLayoutComplete) {
            requestLayout();
        }
    }

    public void setScrollingTouchSlop(int $i0) throws  {
        ViewConfiguration $r2 = ViewConfiguration.get(getContext());
        switch ($i0) {
            case 0:
                break;
            case 1:
                this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop($r2);
                return;
            default:
                Log.w(TAG, "setScrollingTouchSlop(): bad argument constant " + $i0 + "; using default value");
                break;
        }
        this.mTouchSlop = $r2.getScaledTouchSlop();
    }

    public void swapAdapter(Adapter $r1, boolean $z0) throws  {
        setLayoutFrozen(false);
        setAdapterInternal($r1, true, $z0);
        setDataSetChangedAfterLayout();
        requestLayout();
    }

    public void setAdapter(Adapter $r1) throws  {
        setLayoutFrozen(false);
        setAdapterInternal($r1, false, true);
        requestLayout();
    }

    private void setAdapterInternal(Adapter $r1, boolean $z0, boolean $z1) throws  {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterAdapterDataObserver(this.mObserver);
            this.mAdapter.onDetachedFromRecyclerView(this);
        }
        if (!$z0 || $z1) {
            if (this.mItemAnimator != null) {
                this.mItemAnimator.endAnimations();
            }
            if (this.mLayout != null) {
                this.mLayout.removeAndRecycleAllViews(this.mRecycler);
                this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
            }
            this.mRecycler.clear();
        }
        this.mAdapterHelper.reset();
        Adapter $r2 = this.mAdapter;
        this.mAdapter = $r1;
        if ($r1 != null) {
            $r1.registerAdapterDataObserver(this.mObserver);
            $r1.onAttachedToRecyclerView(this);
        }
        if (this.mLayout != null) {
            this.mLayout.onAdapterChanged($r2, this.mAdapter);
        }
        this.mRecycler.onAdapterChanged($r2, this.mAdapter, $z0);
        this.mState.mStructureChanged = true;
        markKnownViewsInvalid();
    }

    public Adapter getAdapter() throws  {
        return this.mAdapter;
    }

    public void setRecyclerListener(RecyclerListener $r1) throws  {
        this.mRecyclerListener = $r1;
    }

    public int getBaseline() throws  {
        if (this.mLayout != null) {
            return this.mLayout.getBaseline();
        }
        return super.getBaseline();
    }

    public void addOnChildAttachStateChangeListener(OnChildAttachStateChangeListener $r1) throws  {
        if (this.mOnChildAttachStateListeners == null) {
            this.mOnChildAttachStateListeners = new ArrayList();
        }
        this.mOnChildAttachStateListeners.add($r1);
    }

    public void removeOnChildAttachStateChangeListener(OnChildAttachStateChangeListener $r1) throws  {
        if (this.mOnChildAttachStateListeners != null) {
            this.mOnChildAttachStateListeners.remove($r1);
        }
    }

    public void clearOnChildAttachStateChangeListeners() throws  {
        if (this.mOnChildAttachStateListeners != null) {
            this.mOnChildAttachStateListeners.clear();
        }
    }

    public void setLayoutManager(LayoutManager $r1) throws  {
        if ($r1 != this.mLayout) {
            stopScroll();
            if (this.mLayout != null) {
                if (this.mIsAttached) {
                    this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
                }
                this.mLayout.setRecyclerView(null);
            }
            this.mRecycler.clear();
            this.mChildHelper.removeAllViewsUnfiltered();
            this.mLayout = $r1;
            if ($r1 != null) {
                if ($r1.mRecyclerView != null) {
                    throw new IllegalArgumentException("LayoutManager " + $r1 + " is already attached to a RecyclerView: " + $r1.mRecyclerView);
                }
                this.mLayout.setRecyclerView(this);
                if (this.mIsAttached) {
                    this.mLayout.dispatchAttachedToWindow(this);
                }
            }
            requestLayout();
        }
    }

    protected Parcelable onSaveInstanceState() throws  {
        SavedState $r1 = new SavedState(super.onSaveInstanceState());
        if (this.mPendingSavedState != null) {
            $r1.copyFrom(this.mPendingSavedState);
            return $r1;
        } else if (this.mLayout != null) {
            $r1.mLayoutState = this.mLayout.onSaveInstanceState();
            return $r1;
        } else {
            $r1.mLayoutState = null;
            return $r1;
        }
    }

    protected void onRestoreInstanceState(Parcelable $r1) throws  {
        if ($r1 instanceof SavedState) {
            this.mPendingSavedState = (SavedState) $r1;
            super.onRestoreInstanceState(this.mPendingSavedState.getSuperState());
            if (this.mLayout != null && this.mPendingSavedState.mLayoutState != null) {
                this.mLayout.onRestoreInstanceState(this.mPendingSavedState.mLayoutState);
                return;
            }
            return;
        }
        super.onRestoreInstanceState($r1);
    }

    protected void dispatchSaveInstanceState(@Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/os/Parcelable;", ">;)V"}) SparseArray<Parcelable> $r1) throws  {
        dispatchFreezeSelfOnly($r1);
    }

    protected void dispatchRestoreInstanceState(@Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/os/Parcelable;", ">;)V"}) SparseArray<Parcelable> $r1) throws  {
        dispatchThawSelfOnly($r1);
    }

    private void addAnimatingView(ViewHolder $r1) throws  {
        View $r2 = $r1.itemView;
        boolean $z0 = $r2.getParent() == this;
        this.mRecycler.unscrapView(getChildViewHolder($r2));
        if ($r1.isTmpDetached()) {
            this.mChildHelper.attachViewToParent($r2, -1, $r2.getLayoutParams(), true);
        } else if ($z0) {
            this.mChildHelper.hide($r2);
        } else {
            this.mChildHelper.addView($r2, true);
        }
    }

    private boolean removeAnimatingView(View $r1) throws  {
        boolean $z1;
        eatRequestLayout();
        boolean $z0 = this.mChildHelper.removeViewIfHidden($r1);
        if ($z0) {
            ViewHolder $r3 = getChildViewHolderInt($r1);
            this.mRecycler.unscrapView($r3);
            this.mRecycler.recycleViewHolderInternal($r3);
        }
        if ($z0) {
            $z1 = false;
        } else {
            $z1 = true;
        }
        resumeRequestLayout($z1);
        return $z0;
    }

    public LayoutManager getLayoutManager() throws  {
        return this.mLayout;
    }

    public RecycledViewPool getRecycledViewPool() throws  {
        return this.mRecycler.getRecycledViewPool();
    }

    public void setRecycledViewPool(RecycledViewPool $r1) throws  {
        this.mRecycler.setRecycledViewPool($r1);
    }

    public void setViewCacheExtension(ViewCacheExtension $r1) throws  {
        this.mRecycler.setViewCacheExtension($r1);
    }

    public void setItemViewCacheSize(int $i0) throws  {
        this.mRecycler.setViewCacheSize($i0);
    }

    public int getScrollState() throws  {
        return this.mScrollState;
    }

    private void setScrollState(int $i0) throws  {
        if ($i0 != this.mScrollState) {
            this.mScrollState = $i0;
            if ($i0 != 2) {
                stopScrollersInternal();
            }
            dispatchOnScrollStateChanged($i0);
        }
    }

    public void addItemDecoration(ItemDecoration $r1, int $i0) throws  {
        if (this.mLayout != null) {
            this.mLayout.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
        }
        if (this.mItemDecorations.isEmpty()) {
            setWillNotDraw(false);
        }
        if ($i0 < 0) {
            this.mItemDecorations.add($r1);
        } else {
            this.mItemDecorations.add($i0, $r1);
        }
        markItemDecorInsetsDirty();
        requestLayout();
    }

    public void addItemDecoration(ItemDecoration $r1) throws  {
        addItemDecoration($r1, -1);
    }

    public void removeItemDecoration(ItemDecoration $r1) throws  {
        if (this.mLayout != null) {
            this.mLayout.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
        }
        this.mItemDecorations.remove($r1);
        if (this.mItemDecorations.isEmpty()) {
            setWillNotDraw(ViewCompat.getOverScrollMode(this) == 2);
        }
        markItemDecorInsetsDirty();
        requestLayout();
    }

    public void setChildDrawingOrderCallback(ChildDrawingOrderCallback $r1) throws  {
        if ($r1 != this.mChildDrawingOrderCallback) {
            this.mChildDrawingOrderCallback = $r1;
            setChildrenDrawingOrderEnabled(this.mChildDrawingOrderCallback != null);
        }
    }

    @Deprecated
    public void setOnScrollListener(@Deprecated OnScrollListener $r1) throws  {
        this.mScrollListener = $r1;
    }

    public void addOnScrollListener(OnScrollListener $r1) throws  {
        if (this.mScrollListeners == null) {
            this.mScrollListeners = new ArrayList();
        }
        this.mScrollListeners.add($r1);
    }

    public void removeOnScrollListener(OnScrollListener $r1) throws  {
        if (this.mScrollListeners != null) {
            this.mScrollListeners.remove($r1);
        }
    }

    public void clearOnScrollListeners() throws  {
        if (this.mScrollListeners != null) {
            this.mScrollListeners.clear();
        }
    }

    public void scrollToPosition(int $i0) throws  {
        if (!this.mLayoutFrozen) {
            stopScroll();
            if (this.mLayout == null) {
                Log.e(TAG, "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
                return;
            }
            this.mLayout.scrollToPosition($i0);
            awakenScrollBars();
        }
    }

    private void jumpToPositionForSmoothScroller(int $i0) throws  {
        if (this.mLayout != null) {
            this.mLayout.scrollToPosition($i0);
            awakenScrollBars();
        }
    }

    public void smoothScrollToPosition(int $i0) throws  {
        if (!this.mLayoutFrozen) {
            if (this.mLayout == null) {
                Log.e(TAG, "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            } else {
                this.mLayout.smoothScrollToPosition(this, this.mState, $i0);
            }
        }
    }

    public void scrollTo(int x, int y) throws  {
        Log.w(TAG, "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    public void scrollBy(int $i0, int $i1) throws  {
        if (this.mLayout == null) {
            Log.e(TAG, "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else if (!this.mLayoutFrozen) {
            boolean $z0 = this.mLayout.canScrollHorizontally();
            boolean $z1 = this.mLayout.canScrollVertically();
            if ($z0 || $z1) {
                if (!$z0) {
                    $i0 = 0;
                }
                if (!$z1) {
                    $i1 = 0;
                }
                scrollByInternal($i0, $i1, null);
            }
        }
    }

    private void consumePendingUpdateOperations() throws  {
        if (!this.mFirstLayoutComplete) {
            return;
        }
        if (this.mDataSetHasChangedAfterLayout) {
            TraceCompat.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
            dispatchLayout();
            TraceCompat.endSection();
        } else if (!this.mAdapterHelper.hasPendingUpdates()) {
        } else {
            if (this.mAdapterHelper.hasAnyUpdateTypes(4) && !this.mAdapterHelper.hasAnyUpdateTypes(11)) {
                TraceCompat.beginSection(TRACE_HANDLE_ADAPTER_UPDATES_TAG);
                eatRequestLayout();
                this.mAdapterHelper.preProcess();
                if (!this.mLayoutRequestEaten) {
                    if (hasUpdatedView()) {
                        dispatchLayout();
                    } else {
                        this.mAdapterHelper.consumePostponedUpdates();
                    }
                }
                resumeRequestLayout(true);
                TraceCompat.endSection();
            } else if (this.mAdapterHelper.hasPendingUpdates()) {
                TraceCompat.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
                dispatchLayout();
                TraceCompat.endSection();
            }
        }
    }

    private boolean hasUpdatedView() throws  {
        int $i0 = this.mChildHelper.getChildCount();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            ViewHolder $r3 = getChildViewHolderInt(this.mChildHelper.getChildAt($i1));
            if ($r3 != null && !$r3.shouldIgnore() && $r3.isUpdated()) {
                return true;
            }
        }
        return false;
    }

    boolean scrollByInternal(int x, int $i1, MotionEvent $r1) throws  {
        int $i2 = 0;
        int $i3 = 0;
        int $i4 = 0;
        int $i5 = 0;
        consumePendingUpdateOperations();
        if (this.mAdapter != null) {
            eatRequestLayout();
            onEnterLayoutOrScroll();
            TraceCompat.beginSection(TRACE_SCROLL_TAG);
            if (x != 0) {
                $i2 = this.mLayout.scrollHorizontallyBy(x, this.mRecycler, this.mState);
                $i4 = $i2;
                $i2 = x - $i2;
            }
            if ($i1 != 0) {
                $i3 = this.mLayout.scrollVerticallyBy($i1, this.mRecycler, this.mState);
                $i5 = $i3;
                $i3 = $i1 - $i3;
            }
            TraceCompat.endSection();
            repositionShadowingViews();
            onExitLayoutOrScroll();
            resumeRequestLayout(false);
        }
        ArrayList $r6 = this.mItemDecorations;
        if (!$r6.isEmpty()) {
            invalidate();
        }
        if (dispatchNestedScroll($i4, $i5, $i2, $i3, this.mScrollOffset)) {
            int $i0 = this.mLastTouchX - this.mScrollOffset[0];
            x = $i0;
            this.mLastTouchX = $i0;
            $i0 = this.mLastTouchY - this.mScrollOffset[1];
            x = $i0;
            this.mLastTouchY = $i0;
            if ($r1 != null) {
                $r1.offsetLocation((float) this.mScrollOffset[0], (float) this.mScrollOffset[1]);
            }
            int[] $r7 = this.mNestedOffsets;
            $r7[0] = $r7[0] + this.mScrollOffset[0];
            $r7 = this.mNestedOffsets;
            $r7[1] = $r7[1] + this.mScrollOffset[1];
        } else if (ViewCompat.getOverScrollMode(this) != 2) {
            if ($r1 != null) {
                pullGlows($r1.getX(), (float) $i2, $r1.getY(), (float) $i3);
            }
            considerReleasingGlowsOnScroll(x, $i1);
        }
        if (!($i4 == 0 && $i5 == 0)) {
            dispatchOnScrolled($i4, $i5);
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        if ($i4 == 0 && $i5 == 0) {
            return false;
        }
        return true;
    }

    public int computeHorizontalScrollOffset() throws  {
        if (this.mLayout == null) {
            return 0;
        }
        return this.mLayout.canScrollHorizontally() ? this.mLayout.computeHorizontalScrollOffset(this.mState) : 0;
    }

    public int computeHorizontalScrollExtent() throws  {
        if (this.mLayout == null) {
            return 0;
        }
        return this.mLayout.canScrollHorizontally() ? this.mLayout.computeHorizontalScrollExtent(this.mState) : 0;
    }

    public int computeHorizontalScrollRange() throws  {
        if (this.mLayout == null) {
            return 0;
        }
        return this.mLayout.canScrollHorizontally() ? this.mLayout.computeHorizontalScrollRange(this.mState) : 0;
    }

    public int computeVerticalScrollOffset() throws  {
        if (this.mLayout == null) {
            return 0;
        }
        return this.mLayout.canScrollVertically() ? this.mLayout.computeVerticalScrollOffset(this.mState) : 0;
    }

    public int computeVerticalScrollExtent() throws  {
        if (this.mLayout == null) {
            return 0;
        }
        return this.mLayout.canScrollVertically() ? this.mLayout.computeVerticalScrollExtent(this.mState) : 0;
    }

    public int computeVerticalScrollRange() throws  {
        if (this.mLayout == null) {
            return 0;
        }
        return this.mLayout.canScrollVertically() ? this.mLayout.computeVerticalScrollRange(this.mState) : 0;
    }

    void eatRequestLayout() throws  {
        this.mEatRequestLayout++;
        if (this.mEatRequestLayout == 1 && !this.mLayoutFrozen) {
            this.mLayoutRequestEaten = false;
        }
    }

    void resumeRequestLayout(boolean $z0) throws  {
        if (this.mEatRequestLayout < 1) {
            this.mEatRequestLayout = 1;
        }
        if (!$z0) {
            this.mLayoutRequestEaten = false;
        }
        if (this.mEatRequestLayout == 1) {
            if (!(!$z0 || !this.mLayoutRequestEaten || this.mLayoutFrozen || this.mLayout == null || this.mAdapter == null)) {
                dispatchLayout();
            }
            if (!this.mLayoutFrozen) {
                this.mLayoutRequestEaten = false;
            }
        }
        this.mEatRequestLayout--;
    }

    public void setLayoutFrozen(boolean $z0) throws  {
        if ($z0 != this.mLayoutFrozen) {
            assertNotInLayoutOrScroll("Do not setLayoutFrozen in layout or scroll");
            if ($z0) {
                long $l0 = SystemClock.uptimeMillis();
                onTouchEvent(MotionEvent.obtain($l0, $l0, 3, 0.0f, 0.0f, 0));
                this.mLayoutFrozen = true;
                this.mIgnoreMotionEventTillDown = true;
                stopScroll();
                return;
            }
            this.mLayoutFrozen = false;
            if (!(!this.mLayoutRequestEaten || this.mLayout == null || this.mAdapter == null)) {
                requestLayout();
            }
            this.mLayoutRequestEaten = false;
        }
    }

    public boolean isLayoutFrozen() throws  {
        return this.mLayoutFrozen;
    }

    public void smoothScrollBy(int $i0, int $i1) throws  {
        if (this.mLayout == null) {
            Log.e(TAG, "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else if (!this.mLayoutFrozen) {
            if (!this.mLayout.canScrollHorizontally()) {
                $i0 = 0;
            }
            if (!this.mLayout.canScrollVertically()) {
                $i1 = 0;
            }
            if ($i0 != 0 || $i1 != 0) {
                this.mViewFlinger.smoothScrollBy($i0, $i1);
            }
        }
    }

    public boolean fling(int $i0, int $i1) throws  {
        if (this.mLayout == null) {
            Log.e(TAG, "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return false;
        } else if (this.mLayoutFrozen) {
            return false;
        } else {
            boolean $z0 = this.mLayout.canScrollHorizontally();
            boolean $z1 = this.mLayout.canScrollVertically();
            if (!$z0 || Math.abs($i0) < this.mMinFlingVelocity) {
                $i0 = 0;
            }
            if (!$z1 || Math.abs($i1) < this.mMinFlingVelocity) {
                $i1 = 0;
            }
            if ($i0 == 0 && $i1 == 0) {
                return false;
            }
            if (dispatchNestedPreFling((float) $i0, (float) $i1)) {
                return false;
            }
            if ($z0 || $z1) {
                $z0 = true;
            } else {
                $z0 = false;
            }
            dispatchNestedFling((float) $i0, (float) $i1, $z0);
            if (!$z0) {
                return false;
            }
            this.mViewFlinger.fling(Math.max(-this.mMaxFlingVelocity, Math.min($i0, this.mMaxFlingVelocity)), Math.max(-this.mMaxFlingVelocity, Math.min($i1, this.mMaxFlingVelocity)));
            return true;
        }
    }

    public void stopScroll() throws  {
        setScrollState(0);
        stopScrollersInternal();
    }

    private void stopScrollersInternal() throws  {
        this.mViewFlinger.stop();
        if (this.mLayout != null) {
            this.mLayout.stopSmoothScroller();
        }
    }

    public int getMinFlingVelocity() throws  {
        return this.mMinFlingVelocity;
    }

    public int getMaxFlingVelocity() throws  {
        return this.mMaxFlingVelocity;
    }

    private void pullGlows(float $f0, float $f1, float $f2, float $f3) throws  {
        boolean $z0 = false;
        if ($f1 < 0.0f) {
            ensureLeftGlow();
            if (this.mLeftGlow.onPull((-$f1) / ((float) getWidth()), 1.0f - ($f2 / ((float) getHeight())))) {
                $z0 = true;
            }
        } else if ($f1 > 0.0f) {
            ensureRightGlow();
            if (this.mRightGlow.onPull($f1 / ((float) getWidth()), $f2 / ((float) getHeight()))) {
                $z0 = true;
            }
        }
        if ($f3 < 0.0f) {
            ensureTopGlow();
            if (this.mTopGlow.onPull((-$f3) / ((float) getHeight()), $f0 / ((float) getWidth()))) {
                $z0 = true;
            }
        } else if ($f3 > 0.0f) {
            ensureBottomGlow();
            if (this.mBottomGlow.onPull($f3 / ((float) getHeight()), 1.0f - ($f0 / ((float) getWidth())))) {
                $z0 = true;
            }
        }
        if ($z0 || $f1 != 0.0f || $f3 != 0.0f) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void releaseGlows() throws  {
        boolean $z0 = false;
        if (this.mLeftGlow != null) {
            $z0 = this.mLeftGlow.onRelease();
        }
        if (this.mTopGlow != null) {
            $z0 |= this.mTopGlow.onRelease();
        }
        if (this.mRightGlow != null) {
            $z0 |= this.mRightGlow.onRelease();
        }
        if (this.mBottomGlow != null) {
            $z0 |= this.mBottomGlow.onRelease();
        }
        if ($z0) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void considerReleasingGlowsOnScroll(int $i0, int $i1) throws  {
        boolean $z0 = false;
        if (!(this.mLeftGlow == null || this.mLeftGlow.isFinished() || $i0 <= 0)) {
            $z0 = this.mLeftGlow.onRelease();
        }
        if (!(this.mRightGlow == null || this.mRightGlow.isFinished() || $i0 >= 0)) {
            $z0 |= this.mRightGlow.onRelease();
        }
        if (!(this.mTopGlow == null || this.mTopGlow.isFinished() || $i1 <= 0)) {
            $z0 |= this.mTopGlow.onRelease();
        }
        if (!(this.mBottomGlow == null || this.mBottomGlow.isFinished() || $i1 >= 0)) {
            $z0 |= this.mBottomGlow.onRelease();
        }
        if ($z0) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    void absorbGlows(int $i0, int $i1) throws  {
        if ($i0 < 0) {
            ensureLeftGlow();
            this.mLeftGlow.onAbsorb(-$i0);
        } else if ($i0 > 0) {
            ensureRightGlow();
            this.mRightGlow.onAbsorb($i0);
        }
        if ($i1 < 0) {
            ensureTopGlow();
            this.mTopGlow.onAbsorb(-$i1);
        } else if ($i1 > 0) {
            ensureBottomGlow();
            this.mBottomGlow.onAbsorb($i1);
        }
        if ($i0 != 0 || $i1 != 0) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    void ensureLeftGlow() throws  {
        if (this.mLeftGlow == null) {
            this.mLeftGlow = new EdgeEffectCompat(getContext());
            if (this.mClipToPadding) {
                this.mLeftGlow.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
            } else {
                this.mLeftGlow.setSize(getMeasuredHeight(), getMeasuredWidth());
            }
        }
    }

    void ensureRightGlow() throws  {
        if (this.mRightGlow == null) {
            this.mRightGlow = new EdgeEffectCompat(getContext());
            if (this.mClipToPadding) {
                this.mRightGlow.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
            } else {
                this.mRightGlow.setSize(getMeasuredHeight(), getMeasuredWidth());
            }
        }
    }

    void ensureTopGlow() throws  {
        if (this.mTopGlow == null) {
            this.mTopGlow = new EdgeEffectCompat(getContext());
            if (this.mClipToPadding) {
                this.mTopGlow.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
            } else {
                this.mTopGlow.setSize(getMeasuredWidth(), getMeasuredHeight());
            }
        }
    }

    void ensureBottomGlow() throws  {
        if (this.mBottomGlow == null) {
            this.mBottomGlow = new EdgeEffectCompat(getContext());
            if (this.mClipToPadding) {
                this.mBottomGlow.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
            } else {
                this.mBottomGlow.setSize(getMeasuredWidth(), getMeasuredHeight());
            }
        }
    }

    void invalidateGlows() throws  {
        this.mBottomGlow = null;
        this.mTopGlow = null;
        this.mRightGlow = null;
        this.mLeftGlow = null;
    }

    public View focusSearch(View $r1, int $i0) throws  {
        View $r5 = this.mLayout.onInterceptFocusSearch($r1, $i0);
        if ($r5 != null) {
            return $r5;
        }
        $r5 = FocusFinder.getInstance().findNextFocus(this, $r1, $i0);
        View $r7 = $r5;
        if (!($r5 != null || this.mAdapter == null || this.mLayout == null || isComputingLayout() || this.mLayoutFrozen)) {
            eatRequestLayout();
            $r7 = this.mLayout.onFocusSearchFailed($r1, $i0, this.mRecycler, this.mState);
            resumeRequestLayout(false);
        }
        if ($r7 != null) {
            return $r7;
        }
        return super.focusSearch($r1, $i0);
    }

    public void requestChildFocus(View $r1, View $r2) throws  {
        boolean $z0 = false;
        if (!(this.mLayout.onRequestChildFocus(this, this.mState, $r1, $r2) || $r2 == null)) {
            Rect $r3;
            this.mTempRect.set(0, 0, $r2.getWidth(), $r2.getHeight());
            android.view.ViewGroup.LayoutParams $r6 = $r2.getLayoutParams();
            if ($r6 instanceof LayoutParams) {
                LayoutParams $r7 = (LayoutParams) $r6;
                if (!$r7.mInsetsDirty) {
                    $r3 = $r7.mDecorInsets;
                    Rect $r8 = this.mTempRect;
                    $r8.left -= $r3.left;
                    $r8 = this.mTempRect;
                    $r8.right += $r3.right;
                    $r8 = this.mTempRect;
                    $r8.top -= $r3.top;
                    $r8 = this.mTempRect;
                    $r8.bottom += $r3.bottom;
                }
            }
            offsetDescendantRectToMyCoords($r2, this.mTempRect);
            offsetRectIntoDescendantCoords($r1, this.mTempRect);
            $r3 = this.mTempRect;
            if (!this.mFirstLayoutComplete) {
                $z0 = true;
            }
            requestChildRectangleOnScreen($r1, $r3, $z0);
        }
        super.requestChildFocus($r1, $r2);
    }

    public boolean requestChildRectangleOnScreen(View $r1, Rect $r2, boolean $z0) throws  {
        return this.mLayout.requestChildRectangleOnScreen(this, $r1, $r2, $z0);
    }

    public void addFocusables(@Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;II)V"}) ArrayList<View> $r1, @Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;II)V"}) int $i0, @Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;II)V"}) int $i1) throws  {
        if (this.mLayout == null || !this.mLayout.onAddFocusables(this, $r1, $i0, $i1)) {
            super.addFocusables($r1, $i0, $i1);
        }
    }

    protected void onAttachedToWindow() throws  {
        super.onAttachedToWindow();
        this.mLayoutOrScrollCounter = 0;
        this.mIsAttached = true;
        this.mFirstLayoutComplete = false;
        if (this.mLayout != null) {
            this.mLayout.dispatchAttachedToWindow(this);
        }
        this.mPostedAnimatorRunner = false;
    }

    protected void onDetachedFromWindow() throws  {
        super.onDetachedFromWindow();
        if (this.mItemAnimator != null) {
            this.mItemAnimator.endAnimations();
        }
        this.mFirstLayoutComplete = false;
        stopScroll();
        this.mIsAttached = false;
        if (this.mLayout != null) {
            this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
        }
        removeCallbacks(this.mItemAnimatorRunner);
        this.mViewInfoStore.onDetach();
    }

    public boolean isAttachedToWindow() throws  {
        return this.mIsAttached;
    }

    void assertInLayoutOrScroll(String $r1) throws  {
        if (!isComputingLayout()) {
            if ($r1 == null) {
                throw new IllegalStateException("Cannot call this method unless RecyclerView is computing a layout or scrolling");
            }
            throw new IllegalStateException($r1);
        }
    }

    void assertNotInLayoutOrScroll(String $r1) throws  {
        if (!isComputingLayout()) {
            return;
        }
        if ($r1 == null) {
            throw new IllegalStateException("Cannot call this method while RecyclerView is computing a layout or scrolling");
        }
        throw new IllegalStateException($r1);
    }

    public void addOnItemTouchListener(OnItemTouchListener $r1) throws  {
        this.mOnItemTouchListeners.add($r1);
    }

    public void removeOnItemTouchListener(OnItemTouchListener $r1) throws  {
        this.mOnItemTouchListeners.remove($r1);
        if (this.mActiveOnItemTouchListener == $r1) {
            this.mActiveOnItemTouchListener = null;
        }
    }

    private boolean dispatchOnItemTouchIntercept(MotionEvent $r1) throws  {
        int $i0 = $r1.getAction();
        if ($i0 == 3 || $i0 == 0) {
            this.mActiveOnItemTouchListener = null;
        }
        int $i1 = this.mOnItemTouchListeners.size();
        int $i2 = 0;
        while ($i2 < $i1) {
            OnItemTouchListener $r4 = (OnItemTouchListener) this.mOnItemTouchListeners.get($i2);
            if (!$r4.onInterceptTouchEvent(this, $r1) || $i0 == 3) {
                $i2++;
            } else {
                this.mActiveOnItemTouchListener = $r4;
                return true;
            }
        }
        return false;
    }

    private boolean dispatchOnItemTouch(MotionEvent $r1) throws  {
        int $i0 = $r1.getAction();
        if (this.mActiveOnItemTouchListener != null) {
            if ($i0 == 0) {
                this.mActiveOnItemTouchListener = null;
            } else {
                this.mActiveOnItemTouchListener.onTouchEvent(this, $r1);
                if ($i0 != 3 && $i0 != 1) {
                    return true;
                }
                this.mActiveOnItemTouchListener = null;
                return true;
            }
        }
        if ($i0 != 0) {
            $i0 = this.mOnItemTouchListeners.size();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                OnItemTouchListener $r2 = (OnItemTouchListener) this.mOnItemTouchListeners.get($i1);
                if ($r2.onInterceptTouchEvent(this, $r1)) {
                    this.mActiveOnItemTouchListener = $r2;
                    return true;
                }
            }
        }
        return false;
    }

    public void requestDisallowInterceptTouchEvent(boolean $z0) throws  {
        int $i0 = this.mOnItemTouchListeners.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            ((OnItemTouchListener) this.mOnItemTouchListeners.get($i1)).onRequestDisallowInterceptTouchEvent($z0);
        }
        super.requestDisallowInterceptTouchEvent($z0);
    }

    private void resetTouch() throws  {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.clear();
        }
        stopNestedScroll();
        releaseGlows();
    }

    private void cancelTouch() throws  {
        resetTouch();
        setScrollState(0);
    }

    private void onPointerUp(MotionEvent $r1) throws  {
        int $i1 = MotionEventCompat.getActionIndex($r1);
        if (MotionEventCompat.getPointerId($r1, $i1) == this.mScrollPointerId) {
            byte $b3;
            if ($i1 == 0) {
                $b3 = (byte) 1;
            } else {
                $b3 = (byte) 0;
            }
            this.mScrollPointerId = MotionEventCompat.getPointerId($r1, $b3);
            int $i0 = (int) (MotionEventCompat.getX($r1, $b3) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            this.mLastTouchX = $i0;
            this.mInitialTouchX = $i0;
            $i0 = (int) (MotionEventCompat.getY($r1, $b3) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            this.mLastTouchY = $i0;
            this.mInitialTouchY = $i0;
        }
    }

    public boolean onGenericMotionEvent(MotionEvent $r1) throws  {
        if (this.mLayout == null) {
            return false;
        }
        if (this.mLayoutFrozen) {
            return false;
        }
        if ((MotionEventCompat.getSource($r1) & 2) == 0) {
            return false;
        }
        if ($r1.getAction() != 8) {
            return false;
        }
        float $f0;
        float $f2;
        if (this.mLayout.canScrollVertically()) {
            $f0 = -MotionEventCompat.getAxisValue($r1, 9);
        } else {
            $f0 = 0.0f;
        }
        if (this.mLayout.canScrollHorizontally()) {
            $f2 = MotionEventCompat.getAxisValue($r1, 10);
        } else {
            $f2 = 0.0f;
        }
        if ($f0 == 0.0f && $f2 == 0.0f) {
            return false;
        }
        float $f1 = getScrollFactor();
        scrollByInternal((int) ($f2 * $f1), (int) ($f0 * $f1), $r1);
        return false;
    }

    private float getScrollFactor() throws  {
        if (this.mScrollFactor == Float.MIN_VALUE) {
            TypedValue $r1 = new TypedValue();
            if (!getContext().getTheme().resolveAttribute(16842829, $r1, true)) {
                return 0.0f;
            }
            this.mScrollFactor = $r1.getDimension(getContext().getResources().getDisplayMetrics());
        }
        return this.mScrollFactor;
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        boolean $z0 = false;
        if (this.mLayout == null) {
            defaultOnMeasure($i0, $i1);
        } else if (this.mLayout.mAutoMeasure) {
            int $i2 = MeasureSpec.getMode($i0);
            int $i3 = MeasureSpec.getMode($i1);
            if ($i2 == 1073741824 && $i3 == 1073741824) {
                $z0 = true;
            }
            this.mLayout.onMeasure(this.mRecycler, this.mState, $i0, $i1);
            if (!$z0 && this.mAdapter != null) {
                if (this.mState.mLayoutStep == 1) {
                    dispatchLayoutStep1();
                }
                this.mLayout.setMeasureSpecs($i0, $i1);
                this.mState.mIsMeasuring = true;
                dispatchLayoutStep2();
                this.mLayout.setMeasuredDimensionFromChildren($i0, $i1);
                if (this.mLayout.shouldMeasureTwice()) {
                    this.mLayout.setMeasureSpecs(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
                    this.mState.mIsMeasuring = true;
                    dispatchLayoutStep2();
                    this.mLayout.setMeasuredDimensionFromChildren($i0, $i1);
                }
            }
        } else if (this.mHasFixedSize) {
            this.mLayout.onMeasure(this.mRecycler, this.mState, $i0, $i1);
        } else {
            if (this.mAdapterUpdateDuringMeasure) {
                eatRequestLayout();
                processAdapterUpdatesAndSetAnimationFlags();
                if (this.mState.mRunPredictiveAnimations) {
                    this.mState.mInPreLayout = true;
                } else {
                    this.mAdapterHelper.consumeUpdatesInOnePass();
                    this.mState.mInPreLayout = false;
                }
                this.mAdapterUpdateDuringMeasure = false;
                resumeRequestLayout(false);
            }
            if (this.mAdapter != null) {
                this.mState.mItemCount = this.mAdapter.getItemCount();
            } else {
                this.mState.mItemCount = 0;
            }
            eatRequestLayout();
            this.mLayout.onMeasure(this.mRecycler, this.mState, $i0, $i1);
            resumeRequestLayout(false);
            this.mState.mInPreLayout = false;
        }
    }

    void defaultOnMeasure(int $i0, int $i1) throws  {
        setMeasuredDimension(LayoutManager.chooseSize($i0, getPaddingLeft() + getPaddingRight(), ViewCompat.getMinimumWidth(this)), LayoutManager.chooseSize($i1, getPaddingTop() + getPaddingBottom(), ViewCompat.getMinimumHeight(this)));
    }

    protected void onSizeChanged(int $i0, int $i1, int $i2, int $i3) throws  {
        super.onSizeChanged($i0, $i1, $i2, $i3);
        if ($i0 != $i2 || $i1 != $i3) {
            invalidateGlows();
        }
    }

    public void setItemAnimator(ItemAnimator $r1) throws  {
        if (this.mItemAnimator != null) {
            this.mItemAnimator.endAnimations();
            this.mItemAnimator.setListener(null);
        }
        this.mItemAnimator = $r1;
        if (this.mItemAnimator != null) {
            this.mItemAnimator.setListener(this.mItemAnimatorListener);
        }
    }

    private void onEnterLayoutOrScroll() throws  {
        this.mLayoutOrScrollCounter++;
    }

    private void onExitLayoutOrScroll() throws  {
        this.mLayoutOrScrollCounter--;
        if (this.mLayoutOrScrollCounter < 1) {
            this.mLayoutOrScrollCounter = 0;
            dispatchContentChangedIfNecessary();
        }
    }

    boolean isAccessibilityEnabled() throws  {
        return this.mAccessibilityManager != null && this.mAccessibilityManager.isEnabled();
    }

    private void dispatchContentChangedIfNecessary() throws  {
        int $i0 = this.mEatenAccessibilityChangeFlags;
        this.mEatenAccessibilityChangeFlags = 0;
        if ($i0 != 0 && isAccessibilityEnabled()) {
            AccessibilityEvent $r1 = AccessibilityEvent.obtain();
            $r1.setEventType(2048);
            AccessibilityEventCompat.setContentChangeTypes($r1, $i0);
            sendAccessibilityEventUnchecked($r1);
        }
    }

    public boolean isComputingLayout() throws  {
        return this.mLayoutOrScrollCounter > 0;
    }

    boolean shouldDeferAccessibilityEvent(AccessibilityEvent $r1) throws  {
        if (!isComputingLayout()) {
            return false;
        }
        int $i0 = 0;
        if ($r1 != null) {
            $i0 = AccessibilityEventCompat.getContentChangeTypes($r1);
        }
        if ($i0 == 0) {
            $i0 = 0;
        }
        this.mEatenAccessibilityChangeFlags |= $i0;
        return true;
    }

    public void sendAccessibilityEventUnchecked(AccessibilityEvent $r1) throws  {
        if (!shouldDeferAccessibilityEvent($r1)) {
            super.sendAccessibilityEventUnchecked($r1);
        }
    }

    public ItemAnimator getItemAnimator() throws  {
        return this.mItemAnimator;
    }

    private void postAnimationRunner() throws  {
        if (!this.mPostedAnimatorRunner && this.mIsAttached) {
            ViewCompat.postOnAnimation(this, this.mItemAnimatorRunner);
            this.mPostedAnimatorRunner = true;
        }
    }

    private boolean predictiveItemAnimationsEnabled() throws  {
        return this.mItemAnimator != null && this.mLayout.supportsPredictiveItemAnimations();
    }

    private void processAdapterUpdatesAndSetAnimationFlags() throws  {
        boolean $z2;
        boolean $z0 = true;
        if (this.mDataSetHasChangedAfterLayout) {
            this.mAdapterHelper.reset();
            markKnownViewsInvalid();
            this.mLayout.onItemsChanged(this);
        }
        if (predictiveItemAnimationsEnabled()) {
            this.mAdapterHelper.preProcess();
        } else {
            this.mAdapterHelper.consumeUpdatesInOnePass();
        }
        boolean $z1;
        if (this.mItemsAddedOrRemoved || this.mItemsChanged) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        State $r3 = this.mState;
        if (!this.mFirstLayoutComplete || this.mItemAnimator == null || (!(this.mDataSetHasChangedAfterLayout || $z1 || this.mLayout.mRequestedSimpleAnimations) || (this.mDataSetHasChangedAfterLayout && !this.mAdapter.hasStableIds()))) {
            $z2 = false;
        } else {
            $z2 = true;
        }
        $r3.mRunSimpleAnimations = $z2;
        $r3 = this.mState;
        if (!(this.mState.mRunSimpleAnimations && $z1 && !this.mDataSetHasChangedAfterLayout && predictiveItemAnimationsEnabled())) {
            $z0 = false;
        }
        $r3.mRunPredictiveAnimations = $z0;
    }

    void dispatchLayout() throws  {
        if (this.mAdapter == null) {
            Log.e(TAG, "No adapter attached; skipping layout");
        } else if (this.mLayout == null) {
            Log.e(TAG, "No layout manager attached; skipping layout");
        } else {
            this.mState.mIsMeasuring = false;
            if (this.mState.mLayoutStep == 1) {
                dispatchLayoutStep1();
                this.mLayout.setExactMeasureSpecsFrom(this);
                dispatchLayoutStep2();
            } else if (!this.mAdapterHelper.hasUpdates() && this.mLayout.getWidth() == getWidth() && this.mLayout.getHeight() == getHeight()) {
                this.mLayout.setExactMeasureSpecsFrom(this);
            } else {
                this.mLayout.setExactMeasureSpecsFrom(this);
                dispatchLayoutStep2();
            }
            dispatchLayoutStep3();
        }
    }

    private void dispatchLayoutStep1() throws  {
        int $i0;
        int $i1;
        ViewHolder $r8;
        this.mState.assertLayoutStep(1);
        this.mState.mIsMeasuring = false;
        eatRequestLayout();
        this.mViewInfoStore.clear();
        onEnterLayoutOrScroll();
        processAdapterUpdatesAndSetAnimationFlags();
        State $r1 = this.mState;
        boolean $z0 = this.mState.mRunSimpleAnimations && this.mItemsChanged;
        $r1.mTrackOldChangeHolders = $z0;
        this.mItemsChanged = false;
        this.mItemsAddedOrRemoved = false;
        this.mState.mInPreLayout = this.mState.mRunPredictiveAnimations;
        this.mState.mItemCount = this.mAdapter.getItemCount();
        findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        if (this.mState.mRunSimpleAnimations) {
            $i0 = this.mChildHelper.getChildCount();
            for ($i1 = 0; $i1 < $i0; $i1++) {
                $r8 = getChildViewHolderInt(this.mChildHelper.getChildAt($i1));
                if (!$r8.shouldIgnore() && (!$r8.isInvalid() || this.mAdapter.hasStableIds())) {
                    this.mViewInfoStore.addToPreLayout($r8, this.mItemAnimator.recordPreLayoutInformation(this.mState, $r8, ItemAnimator.buildAdapterChangeFlagsForAnimations($r8), $r8.getUnmodifiedPayloads()));
                    if (!(!this.mState.mTrackOldChangeHolders || !$r8.isUpdated() || $r8.isRemoved() || $r8.shouldIgnore() || $r8.isInvalid())) {
                        this.mViewInfoStore.addToOldChangeHolders(getChangedHolderKey($r8), $r8);
                    }
                }
            }
        }
        if (this.mState.mRunPredictiveAnimations) {
            saveOldPositions();
            $z0 = this.mState.mStructureChanged;
            this.mState.mStructureChanged = false;
            this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
            this.mState.mStructureChanged = $z0;
            for ($i0 = 0; $i0 < this.mChildHelper.getChildCount(); $i0++) {
                $r8 = getChildViewHolderInt(this.mChildHelper.getChildAt($i0));
                if (!($r8.shouldIgnore() || this.mViewInfoStore.isInPreLayout($r8))) {
                    $i1 = ItemAnimator.buildAdapterChangeFlagsForAnimations($r8);
                    int $i2 = $i1;
                    $z0 = $r8.hasAnyOfTheFlags(8192);
                    if (!$z0) {
                        $i2 = $i1 | 4096;
                    }
                    ItemHolderInfo $r11 = this.mItemAnimator.recordPreLayoutInformation(this.mState, $r8, $i2, $r8.getUnmodifiedPayloads());
                    if ($z0) {
                        recordAnimationInfoIfBouncedHiddenView($r8, $r11);
                    } else {
                        this.mViewInfoStore.addToAppearedInPreLayoutHolders($r8, $r11);
                    }
                }
            }
            clearOldPositions();
        } else {
            clearOldPositions();
        }
        onExitLayoutOrScroll();
        resumeRequestLayout(false);
        this.mState.mLayoutStep = 2;
    }

    private void dispatchLayoutStep2() throws  {
        boolean $z0;
        eatRequestLayout();
        onEnterLayoutOrScroll();
        this.mState.assertLayoutStep(6);
        this.mAdapterHelper.consumeUpdatesInOnePass();
        this.mState.mItemCount = this.mAdapter.getItemCount();
        this.mState.mDeletedInvisibleItemCountSincePreviousLayout = 0;
        this.mState.mInPreLayout = false;
        this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
        this.mState.mStructureChanged = false;
        this.mPendingSavedState = null;
        State $r1 = this.mState;
        if (!this.mState.mRunSimpleAnimations || this.mItemAnimator == null) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        $r1.mRunSimpleAnimations = $z0;
        this.mState.mLayoutStep = 4;
        onExitLayoutOrScroll();
        resumeRequestLayout(false);
    }

    private void dispatchLayoutStep3() throws  {
        ViewInfoStore $r7;
        this.mState.assertLayoutStep(4);
        eatRequestLayout();
        onEnterLayoutOrScroll();
        this.mState.mLayoutStep = 1;
        if (this.mState.mRunSimpleAnimations) {
            for (int $i0 = this.mChildHelper.getChildCount() - 1; $i0 >= 0; $i0--) {
                ViewHolder $r4 = getChildViewHolderInt(this.mChildHelper.getChildAt($i0));
                if (!$r4.shouldIgnore()) {
                    long $l1 = getChangedHolderKey($r4);
                    ItemHolderInfo $r6 = this.mItemAnimator.recordPostLayoutInformation(this.mState, $r4);
                    $r7 = this.mViewInfoStore;
                    ViewHolder $r8 = $r7.getFromOldChangeHolders($l1);
                    ViewInfoStore $r72;
                    if ($r8 == null || $r8.shouldIgnore()) {
                        $r7 = this.mViewInfoStore;
                        $r72 = $r7;
                        $r7.addToPostLayout($r4, $r6);
                    } else {
                        $r7 = this.mViewInfoStore;
                        $r72 = $r7;
                        boolean $z0 = $r7.isDisappearing($r8);
                        $r7 = this.mViewInfoStore;
                        $r72 = $r7;
                        boolean $z1 = $r7.isDisappearing($r4);
                        if ($z0 && $r8 == $r4) {
                            $r7 = this.mViewInfoStore;
                            $r72 = $r7;
                            $r7.addToPostLayout($r4, $r6);
                        } else {
                            $r7 = this.mViewInfoStore;
                            $r72 = $r7;
                            ItemHolderInfo $r9 = $r7.popFromPreLayout($r8);
                            $r7 = this.mViewInfoStore;
                            $r72 = $r7;
                            $r7.addToPostLayout($r4, $r6);
                            $r7 = this.mViewInfoStore;
                            $r6 = $r7.popFromPostLayout($r4);
                            if ($r9 == null) {
                                handleMissingPreInfoForChangeError($l1, $r4, $r8);
                            } else {
                                animateChange($r8, $r4, $r9, $r6, $z0, $z1);
                            }
                        }
                    }
                }
            }
            this.mViewInfoStore.process(this.mViewInfoProcessCallback);
        }
        this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        State $r1 = this.mState;
        State $r13 = this.mState;
        $r1.mPreviousLayoutItemCount = $r13.mItemCount;
        this.mDataSetHasChangedAfterLayout = false;
        this.mState.mRunSimpleAnimations = false;
        this.mState.mRunPredictiveAnimations = false;
        this.mLayout.mRequestedSimpleAnimations = false;
        Recycler $r12 = this.mRecycler;
        if ($r12.mChangedScrap != null) {
            $r12 = this.mRecycler;
            $r12.mChangedScrap.clear();
        }
        onExitLayoutOrScroll();
        resumeRequestLayout(false);
        $r7 = this.mViewInfoStore;
        $r7.clear();
        if (didChildRangeChange(this.mMinMaxLayoutPositions[0], this.mMinMaxLayoutPositions[1])) {
            dispatchOnScrolled(0, 0);
        }
    }

    private void handleMissingPreInfoForChangeError(long $l0, ViewHolder $r1, ViewHolder $r2) throws  {
        int $i1 = this.mChildHelper.getChildCount();
        int $i2 = 0;
        while ($i2 < $i1) {
            ViewHolder $r5 = getChildViewHolderInt(this.mChildHelper.getChildAt($i2));
            if ($r5 == $r1 || getChangedHolderKey($r5) != $l0) {
                $i2++;
            } else if (this.mAdapter == null || !this.mAdapter.hasStableIds()) {
                throw new IllegalStateException("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:" + $r5 + " \n View Holder 2:" + $r1);
            } else {
                throw new IllegalStateException("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:" + $r5 + " \n View Holder 2:" + $r1);
            }
        }
        Log.e(TAG, "Problem while matching changed view holders with the newones. The pre-layout information for the change holder " + $r2 + " cannot be found but it is necessary for " + $r1);
    }

    private void recordAnimationInfoIfBouncedHiddenView(ViewHolder $r1, ItemHolderInfo $r2) throws  {
        $r1.setFlags(0, 8192);
        if (this.mState.mTrackOldChangeHolders && $r1.isUpdated() && !$r1.isRemoved() && !$r1.shouldIgnore()) {
            this.mViewInfoStore.addToOldChangeHolders(getChangedHolderKey($r1), $r1);
        }
        this.mViewInfoStore.addToPreLayout($r1, $r2);
    }

    private void findMinMaxChildLayoutPositions(int[] $r1) throws  {
        int $i0 = this.mChildHelper.getChildCount();
        if ($i0 == 0) {
            $r1[0] = 0;
            $r1[1] = 0;
            return;
        }
        int $i1 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        int $i2 = Integer.MIN_VALUE;
        for (int $i3 = 0; $i3 < $i0; $i3++) {
            ViewHolder $r4 = getChildViewHolderInt(this.mChildHelper.getChildAt($i3));
            if (!$r4.shouldIgnore()) {
                int $i4 = $r4.getLayoutPosition();
                if ($i4 < $i1) {
                    $i1 = $i4;
                }
                if ($i4 > $i2) {
                    $i2 = $i4;
                }
            }
        }
        $r1[0] = $i1;
        $r1[1] = $i2;
    }

    private boolean didChildRangeChange(int $i0, int $i1) throws  {
        if (this.mChildHelper.getChildCount() == 0) {
            return ($i0 == 0 && $i1 == 0) ? false : true;
        } else {
            findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
            return (this.mMinMaxLayoutPositions[0] == $i0 && this.mMinMaxLayoutPositions[1] == $i1) ? false : true;
        }
    }

    protected void removeDetachedView(View $r1, boolean $z0) throws  {
        ViewHolder $r2 = getChildViewHolderInt($r1);
        if ($r2 != null) {
            if ($r2.isTmpDetached()) {
                $r2.clearTmpDetachFlag();
            } else if (!$r2.shouldIgnore()) {
                throw new IllegalArgumentException("Called removeDetachedView with a view which is not flagged as tmp detached." + $r2);
            }
        }
        dispatchChildDetached($r1);
        super.removeDetachedView($r1, $z0);
    }

    long getChangedHolderKey(ViewHolder $r1) throws  {
        return this.mAdapter.hasStableIds() ? $r1.getItemId() : (long) $r1.mPosition;
    }

    private void animateAppearance(@NonNull ViewHolder $r1, @Nullable ItemHolderInfo $r2, @NonNull ItemHolderInfo $r3) throws  {
        $r1.setIsRecyclable(false);
        if (this.mItemAnimator.animateAppearance($r1, $r2, $r3)) {
            postAnimationRunner();
        }
    }

    private void animateDisappearance(@NonNull ViewHolder $r1, @NonNull ItemHolderInfo $r2, @Nullable ItemHolderInfo $r3) throws  {
        addAnimatingView($r1);
        $r1.setIsRecyclable(false);
        if (this.mItemAnimator.animateDisappearance($r1, $r2, $r3)) {
            postAnimationRunner();
        }
    }

    private void animateChange(@NonNull ViewHolder $r1, @NonNull ViewHolder $r2, @NonNull ItemHolderInfo $r3, @NonNull ItemHolderInfo $r4, boolean $z0, boolean $z1) throws  {
        $r1.setIsRecyclable(false);
        if ($z0) {
            addAnimatingView($r1);
        }
        if ($r1 != $r2) {
            if ($z1) {
                addAnimatingView($r2);
            }
            $r1.mShadowedHolder = $r2;
            addAnimatingView($r1);
            this.mRecycler.unscrapView($r1);
            $r2.setIsRecyclable(false);
            $r2.mShadowingHolder = $r1;
        }
        if (this.mItemAnimator.animateChange($r1, $r2, $r3, $r4)) {
            postAnimationRunner();
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) throws  {
        TraceCompat.beginSection(TRACE_ON_LAYOUT_TAG);
        dispatchLayout();
        TraceCompat.endSection();
        this.mFirstLayoutComplete = true;
    }

    public void requestLayout() throws  {
        if (this.mEatRequestLayout != 0 || this.mLayoutFrozen) {
            this.mLayoutRequestEaten = true;
        } else {
            super.requestLayout();
        }
    }

    void markItemDecorInsetsDirty() throws  {
        int $i0 = this.mChildHelper.getUnfilteredChildCount();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            ((LayoutParams) this.mChildHelper.getUnfilteredChildAt($i1).getLayoutParams()).mInsetsDirty = true;
        }
        this.mRecycler.markItemDecorInsetsDirty();
    }

    public void onDraw(Canvas $r1) throws  {
        super.onDraw($r1);
        int $i0 = this.mItemDecorations.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            ((ItemDecoration) this.mItemDecorations.get($i1)).onDraw($r1, this, this.mState);
        }
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
        return ($r1 instanceof LayoutParams) && this.mLayout.checkLayoutParams((LayoutParams) $r1);
    }

    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() throws  {
        if (this.mLayout != null) {
            return this.mLayout.generateDefaultLayoutParams();
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager");
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet $r1) throws  {
        if (this.mLayout != null) {
            return this.mLayout.generateLayoutParams(getContext(), $r1);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager");
    }

    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
        if (this.mLayout != null) {
            return this.mLayout.generateLayoutParams($r1);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager");
    }

    public boolean isAnimating() throws  {
        return this.mItemAnimator != null && this.mItemAnimator.isRunning();
    }

    void saveOldPositions() throws  {
        int $i0 = this.mChildHelper.getUnfilteredChildCount();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            ViewHolder $r3 = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt($i1));
            if (!$r3.shouldIgnore()) {
                $r3.saveOldPosition();
            }
        }
    }

    void clearOldPositions() throws  {
        int $i0 = this.mChildHelper.getUnfilteredChildCount();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            ViewHolder $r3 = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt($i1));
            if (!$r3.shouldIgnore()) {
                $r3.clearOldPosition();
            }
        }
        this.mRecycler.clearOldPositions();
    }

    void offsetPositionRecordsForMove(int $i0, int $i1) throws  {
        byte $b5;
        int $i2 = this.mChildHelper.getUnfilteredChildCount();
        int $i3;
        int $i4;
        if ($i0 < $i1) {
            $i3 = $i0;
            $i4 = $i1;
            $b5 = (byte) -1;
        } else {
            $i3 = $i1;
            $i4 = $i0;
            $b5 = (byte) 1;
        }
        for (int $i6 = 0; $i6 < $i2; $i6++) {
            ViewHolder $r3 = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt($i6));
            if ($r3 != null && $r3.mPosition >= $i3 && $r3.mPosition <= $i4) {
                if ($r3.mPosition == $i0) {
                    $r3.offsetPosition($i1 - $i0, false);
                } else {
                    $r3.offsetPosition($b5, false);
                }
                this.mState.mStructureChanged = true;
            }
        }
        this.mRecycler.offsetPositionRecordsForMove($i0, $i1);
        requestLayout();
    }

    void offsetPositionRecordsForInsert(int $i0, int $i1) throws  {
        int $i2 = this.mChildHelper.getUnfilteredChildCount();
        for (int $i3 = 0; $i3 < $i2; $i3++) {
            ViewHolder $r3 = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt($i3));
            if (!($r3 == null || $r3.shouldIgnore() || $r3.mPosition < $i0)) {
                $r3.offsetPosition($i1, false);
                this.mState.mStructureChanged = true;
            }
        }
        this.mRecycler.offsetPositionRecordsForInsert($i0, $i1);
        requestLayout();
    }

    void offsetPositionRecordsForRemove(int $i0, int $i1, boolean $z0) throws  {
        int $i2 = $i0 + $i1;
        int $i4 = this.mChildHelper.getUnfilteredChildCount();
        for (int $i5 = 0; $i5 < $i4; $i5++) {
            ViewHolder $r3 = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt($i5));
            if (!($r3 == null || $r3.shouldIgnore())) {
                if ($r3.mPosition >= $i2) {
                    $r3.offsetPosition(-$i1, $z0);
                    this.mState.mStructureChanged = true;
                } else if ($r3.mPosition >= $i0) {
                    $r3.flagRemovedAndOffsetPosition($i0 - 1, -$i1, $z0);
                    this.mState.mStructureChanged = true;
                }
            }
        }
        this.mRecycler.offsetPositionRecordsForRemove($i0, $i1, $z0);
        requestLayout();
    }

    void viewRangeUpdate(int $i0, int $i1, Object $r1) throws  {
        int $i3 = this.mChildHelper.getUnfilteredChildCount();
        int $i2 = $i0 + $i1;
        for (int $i4 = 0; $i4 < $i3; $i4++) {
            View $r3 = this.mChildHelper.getUnfilteredChildAt($i4);
            ViewHolder $r4 = getChildViewHolderInt($r3);
            if ($r4 != null && !$r4.shouldIgnore() && $r4.mPosition >= $i0 && $r4.mPosition < $i2) {
                $r4.addFlags(2);
                $r4.addChangePayload($r1);
                ((LayoutParams) $r3.getLayoutParams()).mInsetsDirty = true;
            }
        }
        this.mRecycler.viewRangeUpdate($i0, $i1);
    }

    private boolean canReuseUpdatedViewHolder(ViewHolder $r1) throws  {
        return this.mItemAnimator == null || this.mItemAnimator.canReuseUpdatedViewHolder($r1, $r1.getUnmodifiedPayloads());
    }

    private void setDataSetChangedAfterLayout() throws  {
        if (!this.mDataSetHasChangedAfterLayout) {
            this.mDataSetHasChangedAfterLayout = true;
            int $i0 = this.mChildHelper.getUnfilteredChildCount();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                ViewHolder $r3 = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt($i1));
                if (!($r3 == null || $r3.shouldIgnore())) {
                    $r3.addFlags(512);
                }
            }
            this.mRecycler.setAdapterPositionsAsUnknown();
        }
    }

    void markKnownViewsInvalid() throws  {
        int $i0 = this.mChildHelper.getUnfilteredChildCount();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            ViewHolder $r3 = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt($i1));
            if (!($r3 == null || $r3.shouldIgnore())) {
                $r3.addFlags(6);
            }
        }
        markItemDecorInsetsDirty();
        this.mRecycler.markKnownViewsInvalid();
    }

    public void invalidateItemDecorations() throws  {
        if (this.mItemDecorations.size() != 0) {
            if (this.mLayout != null) {
                this.mLayout.assertNotInLayoutOrScroll("Cannot invalidate item decorations during a scroll or layout");
            }
            markItemDecorInsetsDirty();
            requestLayout();
        }
    }

    public ViewHolder getChildViewHolder(View $r1) throws  {
        Object $r2 = $r1.getParent();
        if ($r2 == null || $r2 == this) {
            return getChildViewHolderInt($r1);
        }
        throw new IllegalArgumentException("View " + $r1 + " is not a direct child of " + this);
    }

    @Nullable
    public View findContainingItemView(View $r1) throws  {
        RecyclerView $r2 = $r1.getParent();
        while ($r2 != null && $r2 != this && ($r2 instanceof View)) {
            $r1 = $r2;
            $r2 = $r1.getParent();
        }
        return $r2 == this ? $r1 : null;
    }

    @Nullable
    public ViewHolder findContainingViewHolder(View $r1) throws  {
        $r1 = findContainingItemView($r1);
        if ($r1 == null) {
            return null;
        }
        return getChildViewHolder($r1);
    }

    static ViewHolder getChildViewHolderInt(View $r0) throws  {
        return $r0 == null ? null : ((LayoutParams) $r0.getLayoutParams()).mViewHolder;
    }

    @Deprecated
    public int getChildPosition(@Deprecated View $r1) throws  {
        return getChildAdapterPosition($r1);
    }

    public int getChildAdapterPosition(View $r1) throws  {
        ViewHolder $r2 = getChildViewHolderInt($r1);
        return $r2 != null ? $r2.getAdapterPosition() : -1;
    }

    public int getChildLayoutPosition(View $r1) throws  {
        ViewHolder $r2 = getChildViewHolderInt($r1);
        return $r2 != null ? $r2.getLayoutPosition() : -1;
    }

    public long getChildItemId(View $r1) throws  {
        if (this.mAdapter == null) {
            return -1;
        }
        if (!this.mAdapter.hasStableIds()) {
            return -1;
        }
        ViewHolder $r3 = getChildViewHolderInt($r1);
        return $r3 != null ? $r3.getItemId() : -1;
    }

    @Deprecated
    public ViewHolder findViewHolderForPosition(@Deprecated int $i0) throws  {
        return findViewHolderForPosition($i0, false);
    }

    public ViewHolder findViewHolderForLayoutPosition(int $i0) throws  {
        return findViewHolderForPosition($i0, false);
    }

    public ViewHolder findViewHolderForAdapterPosition(int $i0) throws  {
        if (this.mDataSetHasChangedAfterLayout) {
            return null;
        }
        int $i1 = this.mChildHelper.getUnfilteredChildCount();
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            ViewHolder $r3 = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt($i2));
            if ($r3 != null && !$r3.isRemoved() && getAdapterPositionFor($r3) == $i0) {
                return $r3;
            }
        }
        return null;
    }

    ViewHolder findViewHolderForPosition(int $i0, boolean $z0) throws  {
        int $i1 = this.mChildHelper.getUnfilteredChildCount();
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            ViewHolder $r3 = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt($i2));
            if (!($r3 == null || $r3.isRemoved())) {
                if ($z0) {
                    if ($r3.mPosition == $i0) {
                        return $r3;
                    }
                } else if ($r3.getLayoutPosition() == $i0) {
                    return $r3;
                }
            }
        }
        return null;
    }

    public ViewHolder findViewHolderForItemId(long $l0) throws  {
        int $i1 = this.mChildHelper.getUnfilteredChildCount();
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            ViewHolder $r3 = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt($i2));
            if ($r3 != null && $r3.getItemId() == $l0) {
                return $r3;
            }
        }
        return null;
    }

    public View findChildViewUnder(float $f0, float $f1) throws  {
        for (int $i0 = this.mChildHelper.getChildCount() - 1; $i0 >= 0; $i0--) {
            View $r2 = this.mChildHelper.getChildAt($i0);
            float $f2 = ViewCompat.getTranslationX($r2);
            float $f3 = ViewCompat.getTranslationY($r2);
            if ($f0 >= ((float) $r2.getLeft()) + $f2 && $f0 <= ((float) $r2.getRight()) + $f2 && $f1 >= ((float) $r2.getTop()) + $f3 && $f1 <= ((float) $r2.getBottom()) + $f3) {
                return $r2;
            }
        }
        return null;
    }

    public boolean drawChild(Canvas $r1, View $r2, long $l0) throws  {
        return super.drawChild($r1, $r2, $l0);
    }

    public void offsetChildrenVertical(int $i0) throws  {
        int $i1 = this.mChildHelper.getChildCount();
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            this.mChildHelper.getChildAt($i2).offsetTopAndBottom($i0);
        }
    }

    public void onChildAttachedToWindow(View child) throws  {
    }

    public void onChildDetachedFromWindow(View child) throws  {
    }

    public void offsetChildrenHorizontal(int $i0) throws  {
        int $i1 = this.mChildHelper.getChildCount();
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            this.mChildHelper.getChildAt($i2).offsetLeftAndRight($i0);
        }
    }

    Rect getItemDecorInsetsForChild(View $r1) throws  {
        LayoutParams $r4 = (LayoutParams) $r1.getLayoutParams();
        if (!$r4.mInsetsDirty) {
            return $r4.mDecorInsets;
        }
        Rect $r5 = $r4.mDecorInsets;
        $r5.set(0, 0, 0, 0);
        int $i0 = this.mItemDecorations.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            this.mTempRect.set(0, 0, 0, 0);
            ((ItemDecoration) this.mItemDecorations.get($i1)).getItemOffsets(this.mTempRect, $r1, this, this.mState);
            int $i2 = $r5.left;
            int $i3 = this.mTempRect;
            Rect $r7 = $i3;
            $i3 = $i2 + $i3.left;
            $i2 = $i3;
            $r5.left = $i3;
            $i2 = $r5.top;
            $i3 = this.mTempRect;
            $r7 = $i3;
            $i3 = $i2 + $i3.top;
            $i2 = $i3;
            $r5.top = $i3;
            $i2 = $r5.right;
            $i3 = this.mTempRect;
            $r7 = $i3;
            $i3 = $i2 + $i3.right;
            $i2 = $i3;
            $r5.right = $i3;
            $i2 = $r5.bottom;
            $i3 = this.mTempRect;
            $r7 = $i3;
            $i3 = $i2 + $i3.bottom;
            $i2 = $i3;
            $r5.bottom = $i3;
        }
        $r4.mInsetsDirty = false;
        return $r5;
    }

    public void onScrolled(int dx, int dy) throws  {
    }

    void dispatchOnScrolled(int $i0, int $i1) throws  {
        int $i2 = getScrollX();
        int $i3 = getScrollY();
        onScrollChanged($i2, $i3, $i2, $i3);
        onScrolled($i0, $i1);
        if (this.mScrollListener != null) {
            this.mScrollListener.onScrolled(this, $i0, $i1);
        }
        if (this.mScrollListeners != null) {
            for ($i2 = this.mScrollListeners.size() - 1; $i2 >= 0; $i2--) {
                ((OnScrollListener) this.mScrollListeners.get($i2)).onScrolled(this, $i0, $i1);
            }
        }
    }

    public void onScrollStateChanged(int state) throws  {
    }

    void dispatchOnScrollStateChanged(int $i0) throws  {
        if (this.mLayout != null) {
            this.mLayout.onScrollStateChanged($i0);
        }
        onScrollStateChanged($i0);
        if (this.mScrollListener != null) {
            this.mScrollListener.onScrollStateChanged(this, $i0);
        }
        if (this.mScrollListeners != null) {
            for (int $i1 = this.mScrollListeners.size() - 1; $i1 >= 0; $i1--) {
                ((OnScrollListener) this.mScrollListeners.get($i1)).onScrollStateChanged(this, $i0);
            }
        }
    }

    public boolean hasPendingAdapterUpdates() throws  {
        return !this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout || this.mAdapterHelper.hasPendingUpdates();
    }

    private void repositionShadowingViews() throws  {
        int $i0 = this.mChildHelper.getChildCount();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            View $r3 = this.mChildHelper.getChildAt($i1);
            ViewHolder $r4 = getChildViewHolder($r3);
            if (!($r4 == null || $r4.mShadowingHolder == null)) {
                View $r1 = $r4.mShadowingHolder.itemView;
                int $i2 = $r3.getLeft();
                int $i3 = $r3.getTop();
                if ($i2 != $r1.getLeft() || $i3 != $r1.getTop()) {
                    $r1.layout($i2, $i3, $r1.getWidth() + $i2, $r1.getHeight() + $i3);
                }
            }
        }
    }

    private void dispatchChildDetached(View $r1) throws  {
        ViewHolder $r2 = getChildViewHolderInt($r1);
        onChildDetachedFromWindow($r1);
        if (!(this.mAdapter == null || $r2 == null)) {
            this.mAdapter.onViewDetachedFromWindow($r2);
        }
        if (this.mOnChildAttachStateListeners != null) {
            for (int $i0 = this.mOnChildAttachStateListeners.size() - 1; $i0 >= 0; $i0--) {
                ((OnChildAttachStateChangeListener) this.mOnChildAttachStateListeners.get($i0)).onChildViewDetachedFromWindow($r1);
            }
        }
    }

    private void dispatchChildAttached(View $r1) throws  {
        ViewHolder $r2 = getChildViewHolderInt($r1);
        onChildAttachedToWindow($r1);
        if (!(this.mAdapter == null || $r2 == null)) {
            this.mAdapter.onViewAttachedToWindow($r2);
        }
        if (this.mOnChildAttachStateListeners != null) {
            for (int $i0 = this.mOnChildAttachStateListeners.size() - 1; $i0 >= 0; $i0--) {
                ((OnChildAttachStateChangeListener) this.mOnChildAttachStateListeners.get($i0)).onChildViewAttachedToWindow($r1);
            }
        }
    }

    private int getAdapterPositionFor(ViewHolder $r1) throws  {
        return ($r1.hasAnyOfTheFlags(DisplayStrings.DS_OTHERS) || !$r1.isBound()) ? -1 : this.mAdapterHelper.applyPendingUpdatesToPosition($r1.mPosition);
    }

    public void setNestedScrollingEnabled(boolean $z0) throws  {
        getScrollingChildHelper().setNestedScrollingEnabled($z0);
    }

    public boolean isNestedScrollingEnabled() throws  {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    public boolean startNestedScroll(int $i0) throws  {
        return getScrollingChildHelper().startNestedScroll($i0);
    }

    public void stopNestedScroll() throws  {
        getScrollingChildHelper().stopNestedScroll();
    }

    public boolean hasNestedScrollingParent() throws  {
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    public boolean dispatchNestedScroll(int $i0, int $i1, int $i2, int $i3, int[] $r1) throws  {
        return getScrollingChildHelper().dispatchNestedScroll($i0, $i1, $i2, $i3, $r1);
    }

    public boolean dispatchNestedPreScroll(int $i0, int $i1, int[] $r1, int[] $r2) throws  {
        return getScrollingChildHelper().dispatchNestedPreScroll($i0, $i1, $r1, $r2);
    }

    public boolean dispatchNestedFling(float $f0, float $f1, boolean $z0) throws  {
        return getScrollingChildHelper().dispatchNestedFling($f0, $f1, $z0);
    }

    public boolean dispatchNestedPreFling(float $f0, float $f1) throws  {
        return getScrollingChildHelper().dispatchNestedPreFling($f0, $f1);
    }

    protected int getChildDrawingOrder(int $i0, int $i1) throws  {
        if (this.mChildDrawingOrderCallback == null) {
            return super.getChildDrawingOrder($i0, $i1);
        }
        return this.mChildDrawingOrderCallback.onGetChildDrawingOrder($i0, $i1);
    }

    private NestedScrollingChildHelper getScrollingChildHelper() throws  {
        if (this.mScrollingChildHelper == null) {
            this.mScrollingChildHelper = new NestedScrollingChildHelper(this);
        }
        return this.mScrollingChildHelper;
    }
}
