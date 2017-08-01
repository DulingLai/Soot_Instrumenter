package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutManager.Properties;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper.ViewDropHandler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import java.util.List;

public class LinearLayoutManager extends LayoutManager implements ViewDropHandler {
    private static final boolean DEBUG = false;
    public static final int HORIZONTAL = 0;
    public static final int INVALID_OFFSET = Integer.MIN_VALUE;
    private static final float MAX_SCROLL_FACTOR = 0.33333334f;
    private static final String TAG = "LinearLayoutManager";
    public static final int VERTICAL = 1;
    final AnchorInfo mAnchorInfo;
    private boolean mLastStackFromEnd;
    private LayoutState mLayoutState;
    int mOrientation;
    OrientationHelper mOrientationHelper;
    SavedState mPendingSavedState;
    int mPendingScrollPosition;
    int mPendingScrollPositionOffset;
    private boolean mRecycleChildrenOnDetach;
    private boolean mReverseLayout;
    boolean mShouldReverseLayout;
    private boolean mSmoothScrollbarEnabled;
    private boolean mStackFromEnd;

    class AnchorInfo {
        int mCoordinate;
        boolean mLayoutFromEnd;
        int mPosition;

        AnchorInfo() throws  {
        }

        void reset() throws  {
            this.mPosition = -1;
            this.mCoordinate = Integer.MIN_VALUE;
            this.mLayoutFromEnd = false;
        }

        void assignCoordinateFromPadding() throws  {
            this.mCoordinate = this.mLayoutFromEnd ? LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() : LinearLayoutManager.this.mOrientationHelper.getStartAfterPadding();
        }

        public String toString() throws  {
            return "AnchorInfo{mPosition=" + this.mPosition + ", mCoordinate=" + this.mCoordinate + ", mLayoutFromEnd=" + this.mLayoutFromEnd + '}';
        }

        private boolean isViewValidAsAnchor(View $r1, State $r2) throws  {
            LayoutParams $r4 = (LayoutParams) $r1.getLayoutParams();
            return !$r4.isItemRemoved() && $r4.getViewLayoutPosition() >= 0 && $r4.getViewLayoutPosition() < $r2.getItemCount();
        }

        public void assignFromViewAndKeepVisibleRect(View $r1) throws  {
            int $i0 = LinearLayoutManager.this.mOrientationHelper.getTotalSpaceChange();
            if ($i0 >= 0) {
                assignFromView($r1);
                return;
            }
            this.mPosition = LinearLayoutManager.this.getPosition($r1);
            int $i2;
            if (this.mLayoutFromEnd) {
                $i2 = (LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() - $i0) - LinearLayoutManager.this.mOrientationHelper.getDecoratedEnd($r1);
                this.mCoordinate = LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() - $i2;
                if ($i2 > 0) {
                    int $i1 = this.mCoordinate - LinearLayoutManager.this.mOrientationHelper.getDecoratedMeasurement($r1);
                    $i0 = LinearLayoutManager.this.mOrientationHelper.getStartAfterPadding();
                    $i0 = $i1 - ($i0 + Math.min(LinearLayoutManager.this.mOrientationHelper.getDecoratedStart($r1) - $i0, 0));
                    if ($i0 < 0) {
                        this.mCoordinate += Math.min($i2, -$i0);
                        return;
                    }
                    return;
                }
                return;
            }
            $i1 = LinearLayoutManager.this.mOrientationHelper.getDecoratedStart($r1);
            $i2 = $i1 - LinearLayoutManager.this.mOrientationHelper.getStartAfterPadding();
            this.mCoordinate = $i1;
            if ($i2 > 0) {
                $i0 = (LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() - Math.min(0, (LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() - $i0) - LinearLayoutManager.this.mOrientationHelper.getDecoratedEnd($r1))) - ($i1 + LinearLayoutManager.this.mOrientationHelper.getDecoratedMeasurement($r1));
                if ($i0 < 0) {
                    this.mCoordinate -= Math.min($i2, -$i0);
                }
            }
        }

        public void assignFromView(View $r1) throws  {
            if (this.mLayoutFromEnd) {
                this.mCoordinate = LinearLayoutManager.this.mOrientationHelper.getDecoratedEnd($r1) + LinearLayoutManager.this.mOrientationHelper.getTotalSpaceChange();
            } else {
                this.mCoordinate = LinearLayoutManager.this.mOrientationHelper.getDecoratedStart($r1);
            }
            this.mPosition = LinearLayoutManager.this.getPosition($r1);
        }
    }

    protected static class LayoutChunkResult {
        public int mConsumed;
        public boolean mFinished;
        public boolean mFocusable;
        public boolean mIgnoreConsumed;

        protected LayoutChunkResult() throws  {
        }

        void resetInternal() throws  {
            this.mConsumed = 0;
            this.mFinished = false;
            this.mIgnoreConsumed = false;
            this.mFocusable = false;
        }
    }

    static class LayoutState {
        static final int INVALID_LAYOUT = Integer.MIN_VALUE;
        static final int ITEM_DIRECTION_HEAD = -1;
        static final int ITEM_DIRECTION_TAIL = 1;
        static final int LAYOUT_END = 1;
        static final int LAYOUT_START = -1;
        static final int SCOLLING_OFFSET_NaN = Integer.MIN_VALUE;
        static final String TAG = "LinearLayoutManager#LayoutState";
        int mAvailable;
        int mCurrentPosition;
        int mExtra = 0;
        boolean mInfinite;
        boolean mIsPreLayout = false;
        int mItemDirection;
        int mLastScrollDelta;
        int mLayoutDirection;
        int mOffset;
        boolean mRecycle = true;
        List<ViewHolder> mScrapList = null;
        int mScrollingOffset;

        LayoutState() throws  {
        }

        boolean hasMore(State $r1) throws  {
            return this.mCurrentPosition >= 0 && this.mCurrentPosition < $r1.getItemCount();
        }

        View next(Recycler $r1) throws  {
            if (this.mScrapList != null) {
                return nextViewFromScrapList();
            }
            View $r3 = $r1.getViewForPosition(this.mCurrentPosition);
            this.mCurrentPosition += this.mItemDirection;
            return $r3;
        }

        private View nextViewFromScrapList() throws  {
            int $i0 = this.mScrapList.size();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                View $r4 = ((ViewHolder) this.mScrapList.get($i1)).itemView;
                LayoutParams $r6 = (LayoutParams) $r4.getLayoutParams();
                if (!$r6.isItemRemoved() && this.mCurrentPosition == $r6.getViewLayoutPosition()) {
                    assignPositionFromScrapList($r4);
                    return $r4;
                }
            }
            return null;
        }

        public void assignPositionFromScrapList() throws  {
            assignPositionFromScrapList(null);
        }

        public void assignPositionFromScrapList(View $r1) throws  {
            $r1 = nextViewInLimitedList($r1);
            if ($r1 == null) {
                this.mCurrentPosition = -1;
            } else {
                this.mCurrentPosition = ((LayoutParams) $r1.getLayoutParams()).getViewLayoutPosition();
            }
        }

        public View nextViewInLimitedList(View $r1) throws  {
            this = this;
            int $i1 = this.mScrapList.size();
            View $r4 = null;
            int $i2 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            for (int $i3 = 0; $i3 < $i1; $i3++) {
                LayoutState $r3 = this;
                this = $r3;
                View $r2 = ((ViewHolder) $r3.mScrapList.get($i3)).itemView;
                LayoutParams $r8 = (LayoutParams) $r2.getLayoutParams();
                if (!($r2 == $r1 || $r8.isItemRemoved())) {
                    int $i0 = ($r8.getViewLayoutPosition() - this.mCurrentPosition) * this.mItemDirection;
                    if ($i0 >= 0 && $i0 < $i2) {
                        $r4 = $r2;
                        $i2 = $i0;
                        if ($i0 == 0) {
                            return $r2;
                        }
                    }
                }
            }
            return $r4;
        }

        void log() throws  {
            Log.d(TAG, "avail:" + this.mAvailable + ", ind:" + this.mCurrentPosition + ", dir:" + this.mItemDirection + ", offset:" + this.mOffset + ", layoutDir:" + this.mLayoutDirection);
        }
    }

    public static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new C02431();
        boolean mAnchorLayoutFromEnd;
        int mAnchorOffset;
        int mAnchorPosition;

        static class C02431 implements Creator<SavedState> {
            C02431() throws  {
            }

            public SavedState createFromParcel(Parcel $r1) throws  {
                return new SavedState($r1);
            }

            public SavedState[] newArray(int $i0) throws  {
                return new SavedState[$i0];
            }
        }

        public int describeContents() throws  {
            return 0;
        }

        SavedState(Parcel $r1) throws  {
            boolean $z0 = true;
            this.mAnchorPosition = $r1.readInt();
            this.mAnchorOffset = $r1.readInt();
            if ($r1.readInt() != 1) {
                $z0 = false;
            }
            this.mAnchorLayoutFromEnd = $z0;
        }

        public SavedState(SavedState $r1) throws  {
            this.mAnchorPosition = $r1.mAnchorPosition;
            this.mAnchorOffset = $r1.mAnchorOffset;
            this.mAnchorLayoutFromEnd = $r1.mAnchorLayoutFromEnd;
        }

        boolean hasValidAnchor() throws  {
            return this.mAnchorPosition >= 0;
        }

        void invalidateAnchor() throws  {
            this.mAnchorPosition = -1;
        }

        public void writeToParcel(Parcel $r1, int flags) throws  {
            $r1.writeInt(this.mAnchorPosition);
            $r1.writeInt(this.mAnchorOffset);
            $r1.writeInt(this.mAnchorLayoutFromEnd ? (byte) 1 : (byte) 0);
        }
    }

    public void onLayoutChildren(android.support.v7.widget.RecyclerView.Recycler r19, android.support.v7.widget.RecyclerView.State r20) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:52:0x0210
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r18 = this;
        r0 = r18;
        r3 = r0.mPendingSavedState;
        if (r3 != 0) goto L_0x000d;
    L_0x0006:
        r0 = r18;
        r4 = r0.mPendingScrollPosition;
        r5 = -1;
        if (r4 == r5) goto L_0x001d;
    L_0x000d:
        r0 = r20;
        r4 = r0.getItemCount();
        if (r4 != 0) goto L_0x001d;
    L_0x0015:
        r0 = r18;
        r1 = r19;
        r0.removeAndRecycleAllViews(r1);
        return;
    L_0x001d:
        r0 = r18;
        r3 = r0.mPendingSavedState;
        if (r3 == 0) goto L_0x0037;
    L_0x0023:
        r0 = r18;
        r3 = r0.mPendingSavedState;
        r6 = r3.hasValidAnchor();
        if (r6 == 0) goto L_0x0037;
    L_0x002d:
        r0 = r18;
        r3 = r0.mPendingSavedState;
        r4 = r3.mAnchorPosition;
        r0 = r18;
        r0.mPendingScrollPosition = r4;
    L_0x0037:
        r0 = r18;
        r0.ensureLayoutState();
        r0 = r18;
        r7 = r0.mLayoutState;
        r5 = 0;
        r7.mRecycle = r5;
        r0 = r18;
        r0.resolveShouldLayoutReverse();
        r0 = r18;
        r8 = r0.mAnchorInfo;
        r8.reset();
        r0 = r18;
        r8 = r0.mAnchorInfo;
        r0 = r18;
        r6 = r0.mShouldReverseLayout;
        r0 = r18;
        r9 = r0.mStackFromEnd;
        r6 = r6 ^ r9;
        r8.mLayoutFromEnd = r6;
        r0 = r18;
        r8 = r0.mAnchorInfo;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r0.updateAnchorInfoForLayout(r1, r2, r8);
        r0 = r18;
        r1 = r20;
        r4 = r0.getExtraLayoutSpace(r1);
        r0 = r18;
        r7 = r0.mLayoutState;
        r10 = r7.mLastScrollDelta;
        if (r10 < 0) goto L_0x0214;
    L_0x007b:
        r10 = r4;
        r4 = 0;
    L_0x007d:
        r0 = r18;
        r11 = r0.mOrientationHelper;
        r12 = r11.getStartAfterPadding();
        r4 = r4 + r12;
        r0 = r18;
        r11 = r0.mOrientationHelper;
        r12 = r11.getEndPadding();
        r10 = r10 + r12;
        r0 = r20;
        r6 = r0.isPreLayout();
        if (r6 == 0) goto L_0x00d2;
    L_0x0097:
        r0 = r18;
        r12 = r0.mPendingScrollPosition;
        r5 = -1;
        if (r12 == r5) goto L_0x00d2;
    L_0x009e:
        r0 = r18;
        r12 = r0.mPendingScrollPositionOffset;
        r5 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        if (r12 == r5) goto L_0x00d2;
    L_0x00a7:
        r0 = r18;
        r12 = r0.mPendingScrollPosition;
        r0 = r18;
        r13 = r0.findViewByPosition(r12);
        if (r13 == 0) goto L_0x00d2;
    L_0x00b3:
        r0 = r18;
        r6 = r0.mShouldReverseLayout;
        if (r6 == 0) goto L_0x0216;
    L_0x00b9:
        r0 = r18;
        r11 = r0.mOrientationHelper;
        r12 = r11.getEndAfterPadding();
        r0 = r18;
        r11 = r0.mOrientationHelper;
        r14 = r11.getDecoratedEnd(r13);
        r12 = r12 - r14;
        r0 = r18;
        r14 = r0.mPendingScrollPositionOffset;
        r12 = r12 - r14;
    L_0x00cf:
        if (r12 <= 0) goto L_0x0236;
    L_0x00d1:
        r4 = r4 + r12;
    L_0x00d2:
        r0 = r18;
        r8 = r0.mAnchorInfo;
        r6 = r8.mLayoutFromEnd;
        if (r6 == 0) goto L_0x023e;
    L_0x00da:
        r0 = r18;
        r6 = r0.mShouldReverseLayout;
        if (r6 == 0) goto L_0x023c;
    L_0x00e0:
        r15 = 1;
    L_0x00e1:
        r0 = r18;
        r8 = r0.mAnchorInfo;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r0.onAnchorReady(r1, r2, r8, r15);
        r0 = r18;
        r1 = r19;
        r0.detachAndScrapAttachedViews(r1);
        r0 = r18;
        r7 = r0.mLayoutState;
        r0 = r18;
        r6 = r0.resolveIsInfinite();
        r7.mInfinite = r6;
        r0 = r18;
        r7 = r0.mLayoutState;
        r0 = r20;
        r6 = r0.isPreLayout();
        r7.mIsPreLayout = r6;
        r0 = r18;
        r8 = r0.mAnchorInfo;
        r6 = r8.mLayoutFromEnd;
        if (r6 == 0) goto L_0x024c;
    L_0x0115:
        r0 = r18;
        r8 = r0.mAnchorInfo;
        r0 = r18;
        r0.updateLayoutStateToFillStart(r8);
        r0 = r18;
        r7 = r0.mLayoutState;
        r7.mExtra = r4;
        r0 = r18;
        r7 = r0.mLayoutState;
        r5 = 0;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r0.fill(r1, r7, r2, r5);
        r0 = r18;
        r7 = r0.mLayoutState;
        r4 = r7.mOffset;
        r0 = r18;
        r7 = r0.mLayoutState;
        r12 = r7.mCurrentPosition;
        r0 = r18;
        r7 = r0.mLayoutState;
        r14 = r7.mAvailable;
        if (r14 <= 0) goto L_0x014d;
    L_0x0146:
        r0 = r18;
        r7 = r0.mLayoutState;
        r14 = r7.mAvailable;
        r10 = r10 + r14;
    L_0x014d:
        r0 = r18;
        r8 = r0.mAnchorInfo;
        r0 = r18;
        r0.updateLayoutStateToFillEnd(r8);
        r0 = r18;
        r7 = r0.mLayoutState;
        r7.mExtra = r10;
        r0 = r18;
        r7 = r0.mLayoutState;
        r10 = r7.mCurrentPosition;
        r0 = r18;
        r0 = r0.mLayoutState;
        r16 = r0;
        r14 = r0.mItemDirection;
        r10 = r10 + r14;
        r7.mCurrentPosition = r10;
        r0 = r18;
        r7 = r0.mLayoutState;
        r5 = 0;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r0.fill(r1, r7, r2, r5);
        r0 = r18;
        r7 = r0.mLayoutState;
        r10 = r7.mOffset;
        r0 = r18;
        r7 = r0.mLayoutState;
        r14 = r7.mAvailable;
        if (r14 <= 0) goto L_0x01ae;
    L_0x0189:
        r0 = r18;
        r7 = r0.mLayoutState;
        r14 = r7.mAvailable;
        r0 = r18;
        r0.updateLayoutStateToFillStart(r12, r4);
        r0 = r18;
        r7 = r0.mLayoutState;
        r7.mExtra = r14;
        r0 = r18;
        r7 = r0.mLayoutState;
        r5 = 0;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r0.fill(r1, r7, r2, r5);
        r0 = r18;
        r7 = r0.mLayoutState;
        r4 = r7.mOffset;
    L_0x01ae:
        r0 = r18;
        r12 = r0.getChildCount();
        if (r12 <= 0) goto L_0x01db;
    L_0x01b6:
        r0 = r18;
        r6 = r0.mShouldReverseLayout;
        r0 = r18;
        r9 = r0.mStackFromEnd;
        r6 = r6 ^ r9;
        if (r6 == 0) goto L_0x02ea;
    L_0x01c1:
        r5 = 1;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r12 = r0.fixLayoutEndGap(r10, r1, r2, r5);
        r4 = r4 + r12;
        r10 = r10 + r12;
        r5 = 0;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r12 = r0.fixLayoutStartGap(r4, r1, r2, r5);
        r4 = r4 + r12;
        r10 = r10 + r12;
    L_0x01db:
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r0.layoutForPredictiveAnimations(r1, r2, r4, r10);
        r0 = r20;
        r6 = r0.isPreLayout();
        if (r6 != 0) goto L_0x01ff;
    L_0x01ec:
        r5 = -1;
        r0 = r18;
        r0.mPendingScrollPosition = r5;
        r5 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r0 = r18;
        r0.mPendingScrollPositionOffset = r5;
        r0 = r18;
        r11 = r0.mOrientationHelper;
        r11.onLayoutComplete();
    L_0x01ff:
        r0 = r18;
        r6 = r0.mStackFromEnd;
        r0 = r18;
        r0.mLastStackFromEnd = r6;
        r17 = 0;
        r0 = r17;
        r1 = r18;
        r1.mPendingSavedState = r0;
        return;
        goto L_0x0214;
    L_0x0211:
        goto L_0x007d;
    L_0x0214:
        r10 = 0;
        goto L_0x0211;
    L_0x0216:
        r0 = r18;
        r11 = r0.mOrientationHelper;
        r12 = r11.getDecoratedStart(r13);
        r0 = r18;
        r11 = r0.mOrientationHelper;
        r14 = r11.getStartAfterPadding();
        r12 = r12 - r14;
        r0 = r18;
        r14 = r0.mPendingScrollPositionOffset;
        goto L_0x022f;
    L_0x022c:
        goto L_0x00cf;
    L_0x022f:
        r12 = r14 - r12;
        goto L_0x022c;
        goto L_0x0236;
    L_0x0233:
        goto L_0x00d2;
    L_0x0236:
        r10 = r10 - r12;
        goto L_0x0233;
        goto L_0x023c;
    L_0x0239:
        goto L_0x00e1;
    L_0x023c:
        r15 = -1;
        goto L_0x0239;
    L_0x023e:
        r0 = r18;
        r6 = r0.mShouldReverseLayout;
        if (r6 == 0) goto L_0x024a;
    L_0x0244:
        goto L_0x0248;
    L_0x0245:
        goto L_0x00e1;
    L_0x0248:
        r15 = -1;
    L_0x0249:
        goto L_0x0245;
    L_0x024a:
        r15 = 1;
        goto L_0x0249;
    L_0x024c:
        r0 = r18;
        r8 = r0.mAnchorInfo;
        r0 = r18;
        r0.updateLayoutStateToFillEnd(r8);
        r0 = r18;
        r7 = r0.mLayoutState;
        r7.mExtra = r10;
        r0 = r18;
        r7 = r0.mLayoutState;
        r5 = 0;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r0.fill(r1, r7, r2, r5);
        r0 = r18;
        r7 = r0.mLayoutState;
        r10 = r7.mOffset;
        r0 = r18;
        r7 = r0.mLayoutState;
        r12 = r7.mCurrentPosition;
        r0 = r18;
        r7 = r0.mLayoutState;
        r14 = r7.mAvailable;
        if (r14 <= 0) goto L_0x0284;
    L_0x027d:
        r0 = r18;
        r7 = r0.mLayoutState;
        r14 = r7.mAvailable;
        r4 = r4 + r14;
    L_0x0284:
        r0 = r18;
        r8 = r0.mAnchorInfo;
        r0 = r18;
        r0.updateLayoutStateToFillStart(r8);
        r0 = r18;
        r7 = r0.mLayoutState;
        r7.mExtra = r4;
        r0 = r18;
        r7 = r0.mLayoutState;
        r4 = r7.mCurrentPosition;
        r0 = r18;
        r0 = r0.mLayoutState;
        r16 = r0;
        r14 = r0.mItemDirection;
        r4 = r4 + r14;
        r7.mCurrentPosition = r4;
        r0 = r18;
        r7 = r0.mLayoutState;
        r5 = 0;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r0.fill(r1, r7, r2, r5);
        r0 = r18;
        r7 = r0.mLayoutState;
        r4 = r7.mOffset;
        r0 = r18;
        r7 = r0.mLayoutState;
        r14 = r7.mAvailable;
        if (r14 <= 0) goto L_0x01ae;
    L_0x02c0:
        r0 = r18;
        r7 = r0.mLayoutState;
        r14 = r7.mAvailable;
        r0 = r18;
        r0.updateLayoutStateToFillEnd(r12, r10);
        r0 = r18;
        r7 = r0.mLayoutState;
        r7.mExtra = r14;
        r0 = r18;
        r7 = r0.mLayoutState;
        r5 = 0;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r0.fill(r1, r7, r2, r5);
        r0 = r18;
        r7 = r0.mLayoutState;
        goto L_0x02e7;
    L_0x02e4:
        goto L_0x01ae;
    L_0x02e7:
        r10 = r7.mOffset;
        goto L_0x02e4;
    L_0x02ea:
        r5 = 1;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r12 = r0.fixLayoutStartGap(r4, r1, r2, r5);
        r4 = r4 + r12;
        r10 = r10 + r12;
        r5 = 0;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r12 = r0.fixLayoutEndGap(r10, r1, r2, r5);
        r4 = r4 + r12;
        goto L_0x0307;
    L_0x0304:
        goto L_0x01db;
    L_0x0307:
        r10 = r10 + r12;
        goto L_0x0304;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.LinearLayoutManager.onLayoutChildren(android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State):void");
    }

    public LinearLayoutManager(Context $r1) throws  {
        this($r1, 1, false);
    }

    public LinearLayoutManager(Context context, int $i0, boolean $z0) throws  {
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.mStackFromEnd = false;
        this.mSmoothScrollbarEnabled = true;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mPendingSavedState = null;
        this.mAnchorInfo = new AnchorInfo();
        setOrientation($i0);
        setReverseLayout($z0);
        setAutoMeasureEnabled(true);
    }

    public LinearLayoutManager(Context $r1, AttributeSet $r2, int $i0, int $i1) throws  {
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.mStackFromEnd = false;
        this.mSmoothScrollbarEnabled = true;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mPendingSavedState = null;
        this.mAnchorInfo = new AnchorInfo();
        Properties $r3 = LayoutManager.getProperties($r1, $r2, $i0, $i1);
        setOrientation($r3.orientation);
        setReverseLayout($r3.reverseLayout);
        setStackFromEnd($r3.stackFromEnd);
        setAutoMeasureEnabled(true);
    }

    public LayoutParams generateDefaultLayoutParams() throws  {
        return new LayoutParams(-2, -2);
    }

    public boolean getRecycleChildrenOnDetach() throws  {
        return this.mRecycleChildrenOnDetach;
    }

    public void setRecycleChildrenOnDetach(boolean $z0) throws  {
        this.mRecycleChildrenOnDetach = $z0;
    }

    public void onDetachedFromWindow(RecyclerView $r1, Recycler $r2) throws  {
        super.onDetachedFromWindow($r1, $r2);
        if (this.mRecycleChildrenOnDetach) {
            removeAndRecycleAllViews($r2);
            $r2.clear();
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent $r1) throws  {
        super.onInitializeAccessibilityEvent($r1);
        if (getChildCount() > 0) {
            AccessibilityRecordCompat $r2 = AccessibilityEventCompat.asRecord($r1);
            $r2.setFromIndex(findFirstVisibleItemPosition());
            $r2.setToIndex(findLastVisibleItemPosition());
        }
    }

    public Parcelable onSaveInstanceState() throws  {
        if (this.mPendingSavedState != null) {
            return new SavedState(this.mPendingSavedState);
        }
        SavedState $r1 = new SavedState();
        if (getChildCount() > 0) {
            ensureLayoutState();
            boolean $z0 = this.mLastStackFromEnd ^ this.mShouldReverseLayout;
            $r1.mAnchorLayoutFromEnd = $z0;
            View $r3;
            if ($z0) {
                $r3 = getChildClosestToEnd();
                $r1.mAnchorOffset = this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd($r3);
                $r1.mAnchorPosition = getPosition($r3);
                return $r1;
            }
            $r3 = getChildClosestToStart();
            $r1.mAnchorPosition = getPosition($r3);
            $r1.mAnchorOffset = this.mOrientationHelper.getDecoratedStart($r3) - this.mOrientationHelper.getStartAfterPadding();
            return $r1;
        }
        $r1.invalidateAnchor();
        return $r1;
    }

    public void onRestoreInstanceState(Parcelable $r1) throws  {
        if ($r1 instanceof SavedState) {
            this.mPendingSavedState = (SavedState) $r1;
            requestLayout();
        }
    }

    public boolean canScrollHorizontally() throws  {
        return this.mOrientation == 0;
    }

    public boolean canScrollVertically() throws  {
        return this.mOrientation == 1;
    }

    public void setStackFromEnd(boolean $z0) throws  {
        assertNotInLayoutOrScroll(null);
        if (this.mStackFromEnd != $z0) {
            this.mStackFromEnd = $z0;
            requestLayout();
        }
    }

    public boolean getStackFromEnd() throws  {
        return this.mStackFromEnd;
    }

    public int getOrientation() throws  {
        return this.mOrientation;
    }

    public void setOrientation(int $i0) throws  {
        if ($i0 == 0 || $i0 == 1) {
            assertNotInLayoutOrScroll(null);
            if ($i0 != this.mOrientation) {
                this.mOrientation = $i0;
                this.mOrientationHelper = null;
                requestLayout();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("invalid orientation:" + $i0);
    }

    private void resolveShouldLayoutReverse() throws  {
        boolean $z1 = true;
        if (this.mOrientation == 1 || !isLayoutRTL()) {
            this.mShouldReverseLayout = this.mReverseLayout;
            return;
        }
        if (this.mReverseLayout) {
            $z1 = false;
        }
        this.mShouldReverseLayout = $z1;
    }

    public boolean getReverseLayout() throws  {
        return this.mReverseLayout;
    }

    public void setReverseLayout(boolean $z0) throws  {
        assertNotInLayoutOrScroll(null);
        if ($z0 != this.mReverseLayout) {
            this.mReverseLayout = $z0;
            requestLayout();
        }
    }

    public View findViewByPosition(int $i0) throws  {
        int $i2 = getChildCount();
        if ($i2 == 0) {
            return null;
        }
        int $i1 = $i0 - getPosition(getChildAt(0));
        if ($i1 >= 0 && $i1 < $i2) {
            View $r1 = getChildAt($i1);
            if (getPosition($r1) == $i0) {
                return $r1;
            }
        }
        return super.findViewByPosition($i0);
    }

    protected int getExtraLayoutSpace(State $r1) throws  {
        if ($r1.hasTargetScrollPosition()) {
            return this.mOrientationHelper.getTotalSpace();
        }
        return 0;
    }

    public void smoothScrollToPosition(RecyclerView $r1, State state, int $i0) throws  {
        C02421 $r3 = new LinearSmoothScroller($r1.getContext()) {
            public PointF computeScrollVectorForPosition(int $i0) throws  {
                return LinearLayoutManager.this.computeScrollVectorForPosition($i0);
            }
        };
        $r3.setTargetPosition($i0);
        startSmoothScroll($r3);
    }

    public PointF computeScrollVectorForPosition(int $i0) throws  {
        boolean $z0 = false;
        if (getChildCount() == 0) {
            return null;
        }
        byte $b2;
        if ($i0 < getPosition(getChildAt(0))) {
            $z0 = true;
        }
        if ($z0 != this.mShouldReverseLayout) {
            $b2 = (byte) -1;
        } else {
            $b2 = (byte) 1;
        }
        if (this.mOrientation == 0) {
            return new PointF((float) $b2, 0.0f);
        }
        return new PointF(0.0f, (float) $b2);
    }

    void onAnchorReady(Recycler recycler, State state, AnchorInfo anchorInfo, int firstLayoutItemDirection) throws  {
    }

    private void layoutForPredictiveAnimations(Recycler $r1, State $r2, int $i0, int $i1) throws  {
        if ($r2.willRunPredictiveAnimations() && getChildCount() != 0 && !$r2.isPreLayout() && supportsPredictiveItemAnimations()) {
            int $i3 = 0;
            int $i2 = 0;
            List $r3 = $r1.getScrapList();
            int $i4 = $r3.size();
            int $i5 = getPosition(getChildAt(0));
            for (int $i6 = 0; $i6 < $i4; $i6++) {
                ViewHolder $r6 = (ViewHolder) $r3.get($i6);
                if (!$r6.isRemoved()) {
                    boolean $z0;
                    int i;
                    if ($r6.getLayoutPosition() < $i5) {
                        $z0 = true;
                    } else {
                        $z0 = false;
                    }
                    boolean $z1 = this.mShouldReverseLayout;
                    if ($z0 != $z1) {
                        i = -1;
                    } else {
                        i = 1;
                    }
                    if (i == -1) {
                        $i3 += this.mOrientationHelper.getDecoratedMeasurement($r6.itemView);
                    } else {
                        $i2 += this.mOrientationHelper.getDecoratedMeasurement($r6.itemView);
                    }
                }
            }
            LayoutState $r8 = this.mLayoutState;
            $r8.mScrapList = $r3;
            if ($i3 > 0) {
                updateLayoutStateToFillStart(getPosition(getChildClosestToStart()), $i0);
                $r8 = this.mLayoutState;
                $r8.mExtra = $i3;
                this.mLayoutState.mAvailable = 0;
                $r8 = this.mLayoutState;
                $r8.assignPositionFromScrapList();
                fill($r1, this.mLayoutState, $r2, false);
            }
            if ($i2 > 0) {
                updateLayoutStateToFillEnd(getPosition(getChildClosestToEnd()), $i1);
                $r8 = this.mLayoutState;
                $r8.mExtra = $i2;
                this.mLayoutState.mAvailable = 0;
                $r8 = this.mLayoutState;
                $r8.assignPositionFromScrapList();
                fill($r1, this.mLayoutState, $r2, false);
            }
            this.mLayoutState.mScrapList = null;
        }
    }

    private void updateAnchorInfoForLayout(Recycler $r1, State $r2, AnchorInfo $r3) throws  {
        if (!updateAnchorFromPendingData($r2, $r3) && !updateAnchorFromChildren($r1, $r2, $r3)) {
            $r3.assignCoordinateFromPadding();
            $r3.mPosition = this.mStackFromEnd ? $r2.getItemCount() - 1 : 0;
        }
    }

    private boolean updateAnchorFromChildren(Recycler $r1, State $r2, AnchorInfo $r3) throws  {
        if (getChildCount() == 0) {
            return false;
        }
        View $r4 = getFocusedChild();
        if ($r4 != null && $r3.isViewValidAsAnchor($r4, $r2)) {
            $r3.assignFromViewAndKeepVisibleRect($r4);
            return true;
        } else if (this.mLastStackFromEnd != this.mStackFromEnd) {
            return false;
        } else {
            $r4 = $r3.mLayoutFromEnd ? findReferenceChildClosestToEnd($r1, $r2) : findReferenceChildClosestToStart($r1, $r2);
            if ($r4 == null) {
                return false;
            }
            $r3.assignFromView($r4);
            if (!$r2.isPreLayout() && supportsPredictiveItemAnimations()) {
                boolean $z0;
                if (this.mOrientationHelper.getDecoratedStart($r4) >= this.mOrientationHelper.getEndAfterPadding() || this.mOrientationHelper.getDecoratedEnd($r4) < this.mOrientationHelper.getStartAfterPadding()) {
                    $z0 = true;
                } else {
                    $z0 = false;
                }
                if ($z0) {
                    $r3.mCoordinate = $r3.mLayoutFromEnd ? this.mOrientationHelper.getEndAfterPadding() : this.mOrientationHelper.getStartAfterPadding();
                }
            }
            return true;
        }
    }

    private boolean updateAnchorFromPendingData(State $r1, AnchorInfo $r2) throws  {
        boolean $z0 = false;
        if ($r1.isPreLayout() || this.mPendingScrollPosition == -1) {
            return false;
        }
        if (this.mPendingScrollPosition < 0 || this.mPendingScrollPosition >= $r1.getItemCount()) {
            this.mPendingScrollPosition = -1;
            this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
            return false;
        }
        $r2.mPosition = this.mPendingScrollPosition;
        if (this.mPendingSavedState != null && this.mPendingSavedState.hasValidAnchor()) {
            $r2.mLayoutFromEnd = this.mPendingSavedState.mAnchorLayoutFromEnd;
            if ($r2.mLayoutFromEnd) {
                $r2.mCoordinate = this.mOrientationHelper.getEndAfterPadding() - this.mPendingSavedState.mAnchorOffset;
                return true;
            }
            $r2.mCoordinate = this.mOrientationHelper.getStartAfterPadding() + this.mPendingSavedState.mAnchorOffset;
            return true;
        } else if (this.mPendingScrollPositionOffset == Integer.MIN_VALUE) {
            View $r5 = findViewByPosition(this.mPendingScrollPosition);
            if ($r5 == null) {
                if (getChildCount() > 0) {
                    boolean $z1;
                    if (this.mPendingScrollPosition < getPosition(getChildAt(0))) {
                        $z1 = true;
                    } else {
                        $z1 = false;
                    }
                    if ($z1 == this.mShouldReverseLayout) {
                        $z0 = true;
                    }
                    $r2.mLayoutFromEnd = $z0;
                }
                $r2.assignCoordinateFromPadding();
                return true;
            } else if (this.mOrientationHelper.getDecoratedMeasurement($r5) > this.mOrientationHelper.getTotalSpace()) {
                $r2.assignCoordinateFromPadding();
                return true;
            } else if (this.mOrientationHelper.getDecoratedStart($r5) - this.mOrientationHelper.getStartAfterPadding() < 0) {
                $r2.mCoordinate = this.mOrientationHelper.getStartAfterPadding();
                $r2.mLayoutFromEnd = false;
                return true;
            } else if (this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd($r5) < 0) {
                $r2.mCoordinate = this.mOrientationHelper.getEndAfterPadding();
                $r2.mLayoutFromEnd = true;
                return true;
            } else {
                $r2.mCoordinate = $r2.mLayoutFromEnd ? this.mOrientationHelper.getDecoratedEnd($r5) + this.mOrientationHelper.getTotalSpaceChange() : this.mOrientationHelper.getDecoratedStart($r5);
                return true;
            }
        } else {
            $r2.mLayoutFromEnd = this.mShouldReverseLayout;
            if (this.mShouldReverseLayout) {
                $r2.mCoordinate = this.mOrientationHelper.getEndAfterPadding() - this.mPendingScrollPositionOffset;
                return true;
            }
            $r2.mCoordinate = this.mOrientationHelper.getStartAfterPadding() + this.mPendingScrollPositionOffset;
            return true;
        }
    }

    private int fixLayoutEndGap(int $i0, Recycler $r1, State $r2, boolean $z0) throws  {
        int $i1 = this.mOrientationHelper.getEndAfterPadding() - $i0;
        if ($i1 <= 0) {
            return 0;
        }
        $i1 = -scrollBy(-$i1, $r1, $r2);
        $i0 += $i1;
        if ($z0) {
            $i0 = this.mOrientationHelper.getEndAfterPadding() - $i0;
            if ($i0 > 0) {
                this.mOrientationHelper.offsetChildren($i0);
                return $i0 + $i1;
            }
        }
        return $i1;
    }

    private int fixLayoutStartGap(int $i0, Recycler $r1, State $r2, boolean $z0) throws  {
        int $i1 = $i0 - this.mOrientationHelper.getStartAfterPadding();
        if ($i1 <= 0) {
            return 0;
        }
        $i1 = -scrollBy($i1, $r1, $r2);
        $i0 += $i1;
        if ($z0) {
            int $i2 = $i0 - this.mOrientationHelper.getStartAfterPadding();
            if ($i2 > 0) {
                this.mOrientationHelper.offsetChildren(-$i2);
                return $i1 - $i2;
            }
        }
        return $i1;
    }

    private void updateLayoutStateToFillEnd(AnchorInfo $r1) throws  {
        updateLayoutStateToFillEnd($r1.mPosition, $r1.mCoordinate);
    }

    private void updateLayoutStateToFillEnd(int $i0, int $i1) throws  {
        this.mLayoutState.mAvailable = this.mOrientationHelper.getEndAfterPadding() - $i1;
        this.mLayoutState.mItemDirection = this.mShouldReverseLayout ? (byte) -1 : (byte) 1;
        this.mLayoutState.mCurrentPosition = $i0;
        this.mLayoutState.mLayoutDirection = 1;
        this.mLayoutState.mOffset = $i1;
        this.mLayoutState.mScrollingOffset = Integer.MIN_VALUE;
    }

    private void updateLayoutStateToFillStart(AnchorInfo $r1) throws  {
        updateLayoutStateToFillStart($r1.mPosition, $r1.mCoordinate);
    }

    private void updateLayoutStateToFillStart(int $i0, int $i1) throws  {
        this.mLayoutState.mAvailable = $i1 - this.mOrientationHelper.getStartAfterPadding();
        this.mLayoutState.mCurrentPosition = $i0;
        this.mLayoutState.mItemDirection = this.mShouldReverseLayout ? (byte) 1 : (byte) -1;
        this.mLayoutState.mLayoutDirection = -1;
        this.mLayoutState.mOffset = $i1;
        this.mLayoutState.mScrollingOffset = Integer.MIN_VALUE;
    }

    protected boolean isLayoutRTL() throws  {
        return getLayoutDirection() == 1;
    }

    void ensureLayoutState() throws  {
        if (this.mLayoutState == null) {
            this.mLayoutState = createLayoutState();
        }
        if (this.mOrientationHelper == null) {
            this.mOrientationHelper = OrientationHelper.createOrientationHelper(this, this.mOrientation);
        }
    }

    LayoutState createLayoutState() throws  {
        return new LayoutState();
    }

    public void scrollToPosition(int $i0) throws  {
        this.mPendingScrollPosition = $i0;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        if (this.mPendingSavedState != null) {
            this.mPendingSavedState.invalidateAnchor();
        }
        requestLayout();
    }

    public void scrollToPositionWithOffset(int $i0, int $i1) throws  {
        this.mPendingScrollPosition = $i0;
        this.mPendingScrollPositionOffset = $i1;
        if (this.mPendingSavedState != null) {
            this.mPendingSavedState.invalidateAnchor();
        }
        requestLayout();
    }

    public int scrollHorizontallyBy(int $i0, Recycler $r1, State $r2) throws  {
        return this.mOrientation == 1 ? 0 : scrollBy($i0, $r1, $r2);
    }

    public int scrollVerticallyBy(int $i0, Recycler $r1, State $r2) throws  {
        return this.mOrientation == 0 ? 0 : scrollBy($i0, $r1, $r2);
    }

    public int computeHorizontalScrollOffset(State $r1) throws  {
        return computeScrollOffset($r1);
    }

    public int computeVerticalScrollOffset(State $r1) throws  {
        return computeScrollOffset($r1);
    }

    public int computeHorizontalScrollExtent(State $r1) throws  {
        return computeScrollExtent($r1);
    }

    public int computeVerticalScrollExtent(State $r1) throws  {
        return computeScrollExtent($r1);
    }

    public int computeHorizontalScrollRange(State $r1) throws  {
        return computeScrollRange($r1);
    }

    public int computeVerticalScrollRange(State $r1) throws  {
        return computeScrollRange($r1);
    }

    private int computeScrollOffset(State $r1) throws  {
        boolean $z0 = false;
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        OrientationHelper $r2 = this.mOrientationHelper;
        View $r3 = findFirstVisibleChildClosestToStart(!this.mSmoothScrollbarEnabled, true);
        if (!this.mSmoothScrollbarEnabled) {
            $z0 = true;
        }
        return ScrollbarHelper.computeScrollOffset($r1, $r2, $r3, findFirstVisibleChildClosestToEnd($z0, true), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }

    private int computeScrollExtent(State $r1) throws  {
        boolean $z0 = false;
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        OrientationHelper $r2 = this.mOrientationHelper;
        View $r3 = findFirstVisibleChildClosestToStart(!this.mSmoothScrollbarEnabled, true);
        if (!this.mSmoothScrollbarEnabled) {
            $z0 = true;
        }
        return ScrollbarHelper.computeScrollExtent($r1, $r2, $r3, findFirstVisibleChildClosestToEnd($z0, true), this, this.mSmoothScrollbarEnabled);
    }

    private int computeScrollRange(State $r1) throws  {
        boolean $z0 = false;
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        OrientationHelper $r2 = this.mOrientationHelper;
        View $r3 = findFirstVisibleChildClosestToStart(!this.mSmoothScrollbarEnabled, true);
        if (!this.mSmoothScrollbarEnabled) {
            $z0 = true;
        }
        return ScrollbarHelper.computeScrollRange($r1, $r2, $r3, findFirstVisibleChildClosestToEnd($z0, true), this, this.mSmoothScrollbarEnabled);
    }

    public void setSmoothScrollbarEnabled(boolean $z0) throws  {
        this.mSmoothScrollbarEnabled = $z0;
    }

    public boolean isSmoothScrollbarEnabled() throws  {
        return this.mSmoothScrollbarEnabled;
    }

    private void updateLayoutState(int $i0, int $i1, boolean $z0, State $r1) throws  {
        LayoutState $r2;
        byte $b2 = (byte) -1;
        byte $b3 = (byte) 1;
        this.mLayoutState.mInfinite = resolveIsInfinite();
        this.mLayoutState.mExtra = getExtraLayoutSpace($r1);
        this.mLayoutState.mLayoutDirection = $i0;
        View $r4;
        if ($i0 == 1) {
            $r2 = this.mLayoutState;
            $r2.mExtra += this.mOrientationHelper.getEndPadding();
            $r4 = getChildClosestToEnd();
            $r2 = this.mLayoutState;
            if (!this.mShouldReverseLayout) {
                $b2 = (byte) 1;
            }
            $r2.mItemDirection = $b2;
            this.mLayoutState.mCurrentPosition = getPosition($r4) + this.mLayoutState.mItemDirection;
            this.mLayoutState.mOffset = this.mOrientationHelper.getDecoratedEnd($r4);
            $i0 = this.mOrientationHelper.getDecoratedEnd($r4) - this.mOrientationHelper.getEndAfterPadding();
        } else {
            $r4 = getChildClosestToStart();
            $r2 = this.mLayoutState;
            $r2.mExtra += this.mOrientationHelper.getStartAfterPadding();
            $r2 = this.mLayoutState;
            if (!this.mShouldReverseLayout) {
                $b3 = (byte) -1;
            }
            $r2.mItemDirection = $b3;
            this.mLayoutState.mCurrentPosition = getPosition($r4) + this.mLayoutState.mItemDirection;
            this.mLayoutState.mOffset = this.mOrientationHelper.getDecoratedStart($r4);
            $i0 = (-this.mOrientationHelper.getDecoratedStart($r4)) + this.mOrientationHelper.getStartAfterPadding();
        }
        this.mLayoutState.mAvailable = $i1;
        if ($z0) {
            $r2 = this.mLayoutState;
            $r2.mAvailable -= $i0;
        }
        this.mLayoutState.mScrollingOffset = $i0;
    }

    boolean resolveIsInfinite() throws  {
        return this.mOrientationHelper.getMode() == 0 && this.mOrientationHelper.getEnd() == 0;
    }

    int scrollBy(int $i0, Recycler $r1, State $r2) throws  {
        if (getChildCount() == 0) {
            return 0;
        }
        if ($i0 == 0) {
            return 0;
        }
        byte $b3;
        this.mLayoutState.mRecycle = true;
        ensureLayoutState();
        if ($i0 > 0) {
            $b3 = (byte) 1;
        } else {
            $b3 = (byte) -1;
        }
        int $i2 = Math.abs($i0);
        updateLayoutState($b3, $i2, true, $r2);
        int $i1 = this.mLayoutState.mScrollingOffset + fill($r1, this.mLayoutState, $r2, false);
        if ($i1 < 0) {
            return 0;
        }
        if ($i2 > $i1) {
            $i0 = $b3 * $i1;
        }
        this.mOrientationHelper.offsetChildren(-$i0);
        this.mLayoutState.mLastScrollDelta = $i0;
        return $i0;
    }

    public void assertNotInLayoutOrScroll(String $r1) throws  {
        if (this.mPendingSavedState == null) {
            super.assertNotInLayoutOrScroll($r1);
        }
    }

    private void recycleChildren(Recycler $r1, int $i0, int $i1) throws  {
        if ($i0 != $i1) {
            if ($i1 > $i0) {
                for ($i1--; $i1 >= $i0; $i1--) {
                    removeAndRecycleViewAt($i1, $r1);
                }
                return;
            }
            while ($i0 > $i1) {
                removeAndRecycleViewAt($i0, $r1);
                $i0--;
            }
        }
    }

    private void recycleViewsFromStart(Recycler $r1, int $i0) throws  {
        if ($i0 >= 0) {
            int $i1 = getChildCount();
            int $i2;
            if (this.mShouldReverseLayout) {
                for ($i2 = $i1 - 1; $i2 >= 0; $i2--) {
                    if (this.mOrientationHelper.getDecoratedEnd(getChildAt($i2)) > $i0) {
                        recycleChildren($r1, $i1 - 1, $i2);
                        return;
                    }
                }
                return;
            }
            for ($i2 = 0; $i2 < $i1; $i2++) {
                if (this.mOrientationHelper.getDecoratedEnd(getChildAt($i2)) > $i0) {
                    recycleChildren($r1, 0, $i2);
                    return;
                }
            }
        }
    }

    private void recycleViewsFromEnd(Recycler $r1, int $i0) throws  {
        int $i1 = getChildCount();
        if ($i0 >= 0) {
            $i0 = this.mOrientationHelper.getEnd() - $i0;
            int $i2;
            if (this.mShouldReverseLayout) {
                for ($i2 = 0; $i2 < $i1; $i2++) {
                    if (this.mOrientationHelper.getDecoratedStart(getChildAt($i2)) < $i0) {
                        recycleChildren($r1, 0, $i2);
                        return;
                    }
                }
                return;
            }
            for ($i2 = $i1 - 1; $i2 >= 0; $i2--) {
                if (this.mOrientationHelper.getDecoratedStart(getChildAt($i2)) < $i0) {
                    recycleChildren($r1, $i1 - 1, $i2);
                    return;
                }
            }
        }
    }

    private void recycleByLayoutState(Recycler $r1, LayoutState $r2) throws  {
        if ($r2.mRecycle && !$r2.mInfinite) {
            if ($r2.mLayoutDirection == -1) {
                recycleViewsFromEnd($r1, $r2.mScrollingOffset);
            } else {
                recycleViewsFromStart($r1, $r2.mScrollingOffset);
            }
        }
    }

    int fill(Recycler $r1, LayoutState $r2, State $r3, boolean $z0) throws  {
        int $i0 = $r2.mAvailable;
        if ($r2.mScrollingOffset != Integer.MIN_VALUE) {
            if ($r2.mAvailable < 0) {
                $r2.mScrollingOffset += $r2.mAvailable;
            }
            recycleByLayoutState($r1, $r2);
        }
        int $i2 = $r2.mAvailable + $r2.mExtra;
        LayoutChunkResult $r4 = new LayoutChunkResult();
        while (true) {
            if ((!$r2.mInfinite && $i2 <= 0) || !$r2.hasMore($r3)) {
                break;
            }
            $r4.resetInternal();
            layoutChunk($r1, $r3, $r2, $r4);
            if (!$r4.mFinished) {
                $r2.mOffset += $r4.mConsumed * $r2.mLayoutDirection;
                if (!($r4.mIgnoreConsumed && this.mLayoutState.mScrapList == null && $r3.isPreLayout())) {
                    $r2.mAvailable -= $r4.mConsumed;
                    $i2 -= $r4.mConsumed;
                }
                if ($r2.mScrollingOffset != Integer.MIN_VALUE) {
                    $r2.mScrollingOffset += $r4.mConsumed;
                    if ($r2.mAvailable < 0) {
                        $r2.mScrollingOffset += $r2.mAvailable;
                    }
                    recycleByLayoutState($r1, $r2);
                }
                if ($z0 && $r4.mFocusable) {
                    break;
                }
            } else {
                break;
            }
        }
        return $i0 - $r2.mAvailable;
    }

    void layoutChunk(Recycler $r1, State state, LayoutState $r3, LayoutChunkResult $r4) throws  {
        View $r5 = $r3.next($r1);
        if ($r5 == null) {
            $r4.mFinished = true;
            return;
        }
        int $i0;
        int $i2;
        int $i1;
        int i;
        int $i4;
        LayoutParams $r7 = (LayoutParams) $r5.getLayoutParams();
        if ($r3.mScrapList == null) {
            if (this.mShouldReverseLayout == ($r3.mLayoutDirection == -1)) {
                addView($r5);
            } else {
                addView($r5, 0);
            }
        } else {
            if (this.mShouldReverseLayout == ($r3.mLayoutDirection == -1)) {
                addDisappearingView($r5);
            } else {
                addDisappearingView($r5, 0);
            }
        }
        measureChildWithMargins($r5, 0, 0);
        OrientationHelper $r9 = this.mOrientationHelper;
        $r4.mConsumed = $r9.getDecoratedMeasurement($r5);
        if (this.mOrientation == 1) {
            if (isLayoutRTL()) {
                $i0 = getWidth() - getPaddingRight();
                $r9 = this.mOrientationHelper;
                $i2 = $i0 - $r9.getDecoratedMeasurementInOther($r5);
            } else {
                $i1 = getPaddingLeft();
                $i2 = $i1;
                $r9 = this.mOrientationHelper;
                $i0 = $i1 + $r9.getDecoratedMeasurementInOther($r5);
            }
            if ($r3.mLayoutDirection == -1) {
                $i1 = $r3.mOffset;
                i = $r3.mOffset - $r4.mConsumed;
            } else {
                i = $r3.mOffset;
                $i1 = $r3.mOffset;
                $i4 = $r4.mConsumed;
                $i1 += $i4;
            }
        } else {
            $i1 = getPaddingTop();
            i = $i1;
            $r9 = this.mOrientationHelper;
            $i1 += $r9.getDecoratedMeasurementInOther($r5);
            if ($r3.mLayoutDirection == -1) {
                $i0 = $r3.mOffset;
                $i2 = $r3.mOffset - $r4.mConsumed;
            } else {
                $i2 = $r3.mOffset;
                $i0 = $r3.mOffset + $r4.mConsumed;
            }
        }
        $i2 += $r7.leftMargin;
        i += $r7.topMargin;
        $i0 -= $r7.rightMargin;
        $i4 = $r7.bottomMargin;
        layoutDecorated($r5, $i2, i, $i0, $i1 - $i4);
        if ($r7.isItemRemoved() || $r7.isItemChanged()) {
            $r4.mIgnoreConsumed = true;
        }
        $r4.mFocusable = $r5.isFocusable();
    }

    boolean shouldMeasureTwice() throws  {
        return (getHeightMode() == 1073741824 || getWidthMode() == 1073741824 || !hasFlexibleChildInBothOrientations()) ? false : true;
    }

    int convertFocusDirectionToLayoutDirection(int $i0) throws  {
        int $i1 = -1;
        int $i2 = 1;
        int $i3 = Integer.MIN_VALUE;
        switch ($i0) {
            case 1:
                break;
            case 2:
                return 1;
            case 17:
                if (this.mOrientation != 0) {
                    return Integer.MIN_VALUE;
                }
                return -1;
            case 33:
                if (this.mOrientation != 1) {
                    return Integer.MIN_VALUE;
                }
                return -1;
            case 66:
                if (this.mOrientation != 0) {
                    $i2 = Integer.MIN_VALUE;
                }
                return $i2;
            case 130:
                if (this.mOrientation == 1) {
                    $i3 = 1;
                }
                return $i3;
            default:
                $i1 = Integer.MIN_VALUE;
                break;
        }
        return $i1;
    }

    private View getChildClosestToStart() throws  {
        return getChildAt(this.mShouldReverseLayout ? getChildCount() - 1 : 0);
    }

    private View getChildClosestToEnd() throws  {
        return getChildAt(this.mShouldReverseLayout ? 0 : getChildCount() - 1);
    }

    private View findFirstVisibleChildClosestToStart(boolean $z0, boolean $z1) throws  {
        if (this.mShouldReverseLayout) {
            return findOneVisibleChild(getChildCount() - 1, -1, $z0, $z1);
        }
        return findOneVisibleChild(0, getChildCount(), $z0, $z1);
    }

    private View findFirstVisibleChildClosestToEnd(boolean $z0, boolean $z1) throws  {
        if (this.mShouldReverseLayout) {
            return findOneVisibleChild(0, getChildCount(), $z0, $z1);
        }
        return findOneVisibleChild(getChildCount() - 1, -1, $z0, $z1);
    }

    private View findReferenceChildClosestToEnd(Recycler $r1, State $r2) throws  {
        return this.mShouldReverseLayout ? findFirstReferenceChild($r1, $r2) : findLastReferenceChild($r1, $r2);
    }

    private View findReferenceChildClosestToStart(Recycler $r1, State $r2) throws  {
        return this.mShouldReverseLayout ? findLastReferenceChild($r1, $r2) : findFirstReferenceChild($r1, $r2);
    }

    private View findFirstReferenceChild(Recycler $r1, State $r2) throws  {
        return findReferenceChild($r1, $r2, 0, getChildCount(), $r2.getItemCount());
    }

    private View findLastReferenceChild(Recycler $r1, State $r2) throws  {
        return findReferenceChild($r1, $r2, getChildCount() - 1, -1, $r2.getItemCount());
    }

    View findReferenceChild(Recycler recycler, State state, int $i0, int $i1, int $i2) throws  {
        byte $b5;
        ensureLayoutState();
        View $r3 = null;
        View $r4 = null;
        int $i3 = this.mOrientationHelper.getStartAfterPadding();
        int $i4 = this.mOrientationHelper.getEndAfterPadding();
        if ($i1 > $i0) {
            $b5 = (byte) 1;
        } else {
            $b5 = (byte) -1;
        }
        while ($i0 != $i1) {
            View $r6 = getChildAt($i0);
            int $i6 = getPosition($r6);
            if ($i6 >= 0 && $i6 < $i2) {
                if (((LayoutParams) $r6.getLayoutParams()).isItemRemoved()) {
                    if ($r3 == null) {
                        $r3 = $r6;
                    }
                } else if (this.mOrientationHelper.getDecoratedStart($r6) < $i4 && this.mOrientationHelper.getDecoratedEnd($r6) >= $i3) {
                    return $r6;
                } else {
                    if ($r4 == null) {
                        $r4 = $r6;
                    }
                }
            }
            $i0 += $b5;
        }
        if ($r4 == null) {
            $r4 = $r3;
        }
        return $r4;
    }

    public int findFirstVisibleItemPosition() throws  {
        View $r1 = findOneVisibleChild(0, getChildCount(), false, true);
        if ($r1 == null) {
            return -1;
        }
        return getPosition($r1);
    }

    public int findFirstCompletelyVisibleItemPosition() throws  {
        View $r1 = findOneVisibleChild(0, getChildCount(), true, false);
        if ($r1 == null) {
            return -1;
        }
        return getPosition($r1);
    }

    public int findLastVisibleItemPosition() throws  {
        View $r1 = findOneVisibleChild(getChildCount() - 1, -1, false, true);
        if ($r1 == null) {
            return -1;
        }
        return getPosition($r1);
    }

    public int findLastCompletelyVisibleItemPosition() throws  {
        View $r1 = findOneVisibleChild(getChildCount() - 1, -1, true, false);
        if ($r1 == null) {
            return -1;
        }
        return getPosition($r1);
    }

    View findOneVisibleChild(int $i0, int $i1, boolean $z0, boolean $z1) throws  {
        byte $b4;
        ensureLayoutState();
        int $i2 = this.mOrientationHelper.getStartAfterPadding();
        int $i3 = this.mOrientationHelper.getEndAfterPadding();
        if ($i1 > $i0) {
            $b4 = (byte) 1;
        } else {
            $b4 = (byte) -1;
        }
        View $r2 = null;
        while ($i0 != $i1) {
            View $r3 = getChildAt($i0);
            int $i5 = this.mOrientationHelper.getDecoratedStart($r3);
            int $i6 = this.mOrientationHelper.getDecoratedEnd($r3);
            if ($i5 < $i3 && $i6 > $i2) {
                if (!$z0) {
                    return $r3;
                }
                if ($i5 >= $i2 && $i6 <= $i3) {
                    return $r3;
                }
                if ($z1 && $r2 == null) {
                    $r2 = $r3;
                }
            }
            $i0 += $b4;
        }
        return $r2;
    }

    public View onFocusSearchFailed(View focused, int $i0, Recycler $r2, State $r3) throws  {
        resolveShouldLayoutReverse();
        if (getChildCount() == 0) {
            return null;
        }
        $i0 = convertFocusDirectionToLayoutDirection($i0);
        if ($i0 == Integer.MIN_VALUE) {
            return null;
        }
        ensureLayoutState();
        if ($i0 == -1) {
            focused = findReferenceChildClosestToStart($r2, $r3);
        } else {
            focused = findReferenceChildClosestToEnd($r2, $r3);
        }
        if (focused == null) {
            return null;
        }
        View $r4;
        ensureLayoutState();
        updateLayoutState($i0, (int) (MAX_SCROLL_FACTOR * ((float) this.mOrientationHelper.getTotalSpace())), false, $r3);
        this.mLayoutState.mScrollingOffset = Integer.MIN_VALUE;
        this.mLayoutState.mRecycle = false;
        fill($r2, this.mLayoutState, $r3, true);
        if ($i0 == -1) {
            $r4 = getChildClosestToStart();
        } else {
            $r4 = getChildClosestToEnd();
        }
        if ($r4 == focused || !$r4.isFocusable()) {
            return null;
        }
        return $r4;
    }

    private void logChildren() throws  {
        Log.d(TAG, "internal representation of views on the screen");
        for (int $i0 = 0; $i0 < getChildCount(); $i0++) {
            View $r1 = getChildAt($i0);
            Log.d(TAG, "item " + getPosition($r1) + ", coord:" + this.mOrientationHelper.getDecoratedStart($r1));
        }
        Log.d(TAG, "==============");
    }

    void validateChildOrder() throws  {
        boolean $z0 = true;
        Log.d(TAG, "validating child count " + getChildCount());
        if (getChildCount() >= 1) {
            int $i0 = getPosition(getChildAt(0));
            int $i1 = this.mOrientationHelper.getDecoratedStart(getChildAt(0));
            int $i2;
            View $r3;
            int $i3;
            int $i4;
            StringBuilder $r1;
            if (this.mShouldReverseLayout) {
                $i2 = 1;
                while ($i2 < getChildCount()) {
                    $r3 = getChildAt($i2);
                    $i3 = getPosition($r3);
                    $i4 = this.mOrientationHelper.getDecoratedStart($r3);
                    if ($i3 < $i0) {
                        logChildren();
                        $r1 = new StringBuilder().append("detected invalid position. loc invalid? ");
                        if ($i4 >= $i1) {
                            $z0 = false;
                        }
                        throw new RuntimeException($r1.append($z0).toString());
                    } else if ($i4 > $i1) {
                        logChildren();
                        throw new RuntimeException("detected invalid location");
                    } else {
                        $i2++;
                    }
                }
                return;
            }
            $i2 = 1;
            while ($i2 < getChildCount()) {
                $r3 = getChildAt($i2);
                $i3 = getPosition($r3);
                $i4 = this.mOrientationHelper.getDecoratedStart($r3);
                if ($i3 < $i0) {
                    logChildren();
                    $r1 = new StringBuilder().append("detected invalid position. loc invalid? ");
                    if ($i4 >= $i1) {
                        $z0 = false;
                    }
                    throw new RuntimeException($r1.append($z0).toString());
                } else if ($i4 < $i1) {
                    logChildren();
                    throw new RuntimeException("detected invalid location");
                } else {
                    $i2++;
                }
            }
        }
    }

    public boolean supportsPredictiveItemAnimations() throws  {
        return this.mPendingSavedState == null && this.mLastStackFromEnd == this.mStackFromEnd;
    }

    public void prepareForDrop(View $r1, View $r2, int x, int y) throws  {
        byte $b2;
        assertNotInLayoutOrScroll("Cannot drop a view during a scroll or layout calculation");
        ensureLayoutState();
        resolveShouldLayoutReverse();
        y = getPosition($r1);
        x = getPosition($r2);
        if (y < x) {
            $b2 = (byte) 1;
        } else {
            $b2 = (byte) -1;
        }
        if (this.mShouldReverseLayout) {
            if ($b2 == (byte) 1) {
                scrollToPositionWithOffset(x, this.mOrientationHelper.getEndAfterPadding() - (this.mOrientationHelper.getDecoratedStart($r2) + this.mOrientationHelper.getDecoratedMeasurement($r1)));
            } else {
                scrollToPositionWithOffset(x, this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd($r2));
            }
        } else if ($b2 == (byte) -1) {
            scrollToPositionWithOffset(x, this.mOrientationHelper.getDecoratedStart($r2));
        } else {
            scrollToPositionWithOffset(x, this.mOrientationHelper.getDecoratedEnd($r2) - this.mOrientationHelper.getDecoratedMeasurement($r1));
        }
    }
}
