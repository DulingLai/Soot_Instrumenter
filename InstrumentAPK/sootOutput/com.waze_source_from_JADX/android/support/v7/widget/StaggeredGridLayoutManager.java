package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutManager.Properties;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class StaggeredGridLayoutManager extends LayoutManager {
    private static final boolean DEBUG = false;
    @Deprecated
    public static final int GAP_HANDLING_LAZY = 1;
    public static final int GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS = 2;
    public static final int GAP_HANDLING_NONE = 0;
    public static final int HORIZONTAL = 0;
    private static final int INVALID_OFFSET = Integer.MIN_VALUE;
    private static final float MAX_SCROLL_FACTOR = 0.33333334f;
    public static final String TAG = "StaggeredGridLayoutManager";
    public static final int VERTICAL = 1;
    private final AnchorInfo mAnchorInfo = new AnchorInfo();
    private final Runnable mCheckForGapsRunnable = new C02701();
    private int mFullSizeSpec;
    private int mGapStrategy = 2;
    private boolean mLaidOutInvalidFullSpan = false;
    private boolean mLastLayoutFromEnd;
    private boolean mLastLayoutRTL;
    @NonNull
    private final LayoutState mLayoutState;
    LazySpanLookup mLazySpanLookup = new LazySpanLookup();
    private int mOrientation;
    private SavedState mPendingSavedState;
    int mPendingScrollPosition = -1;
    int mPendingScrollPositionOffset = Integer.MIN_VALUE;
    @NonNull
    OrientationHelper mPrimaryOrientation;
    private BitSet mRemainingSpans;
    private boolean mReverseLayout = false;
    @NonNull
    OrientationHelper mSecondaryOrientation;
    boolean mShouldReverseLayout = false;
    private int mSizePerSpan;
    private boolean mSmoothScrollbarEnabled = true;
    private int mSpanCount = -1;
    private Span[] mSpans;
    private final Rect mTmpRect = new Rect();

    class C02701 implements Runnable {
        C02701() throws  {
        }

        public void run() throws  {
            StaggeredGridLayoutManager.this.checkForGaps();
        }
    }

    private class AnchorInfo {
        boolean mInvalidateOffsets;
        boolean mLayoutFromEnd;
        int mOffset;
        int mPosition;

        private AnchorInfo() throws  {
        }

        void reset() throws  {
            this.mPosition = -1;
            this.mOffset = Integer.MIN_VALUE;
            this.mLayoutFromEnd = false;
            this.mInvalidateOffsets = false;
        }

        void assignCoordinateFromPadding() throws  {
            this.mOffset = this.mLayoutFromEnd ? StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding() : StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding();
        }

        void assignCoordinateFromPadding(int $i0) throws  {
            if (this.mLayoutFromEnd) {
                this.mOffset = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding() - $i0;
            } else {
                this.mOffset = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding() + $i0;
            }
        }
    }

    public static class LayoutParams extends android.support.v7.widget.RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;
        boolean mFullSpan;
        Span mSpan;

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

        public LayoutParams(android.support.v7.widget.RecyclerView.LayoutParams $r1) throws  {
            super($r1);
        }

        public void setFullSpan(boolean $z0) throws  {
            this.mFullSpan = $z0;
        }

        public boolean isFullSpan() throws  {
            return this.mFullSpan;
        }

        public final int getSpanIndex() throws  {
            return this.mSpan == null ? -1 : this.mSpan.mIndex;
        }
    }

    static class LazySpanLookup {
        private static final int MIN_SIZE = 10;
        int[] mData;
        List<FullSpanItem> mFullSpanItems;

        static class FullSpanItem implements Parcelable {
            public static final Creator<FullSpanItem> CREATOR = new C02721();
            int mGapDir;
            int[] mGapPerSpan;
            boolean mHasUnwantedGapAfter;
            int mPosition;

            static class C02721 implements Creator<FullSpanItem> {
                C02721() throws  {
                }

                public FullSpanItem createFromParcel(Parcel $r1) throws  {
                    return new FullSpanItem($r1);
                }

                public FullSpanItem[] newArray(int $i0) throws  {
                    return new FullSpanItem[$i0];
                }
            }

            public int describeContents() throws  {
                return 0;
            }

            public FullSpanItem(Parcel $r1) throws  {
                boolean $z0 = true;
                this.mPosition = $r1.readInt();
                this.mGapDir = $r1.readInt();
                if ($r1.readInt() != 1) {
                    $z0 = false;
                }
                this.mHasUnwantedGapAfter = $z0;
                int $i0 = $r1.readInt();
                if ($i0 > 0) {
                    this.mGapPerSpan = new int[$i0];
                    $r1.readIntArray(this.mGapPerSpan);
                }
            }

            int getGapForSpan(int $i0) throws  {
                return this.mGapPerSpan == null ? 0 : this.mGapPerSpan[$i0];
            }

            public void writeToParcel(Parcel $r1, int flags) throws  {
                $r1.writeInt(this.mPosition);
                $r1.writeInt(this.mGapDir);
                $r1.writeInt(this.mHasUnwantedGapAfter ? (byte) 1 : (byte) 0);
                if (this.mGapPerSpan == null || this.mGapPerSpan.length <= 0) {
                    $r1.writeInt(0);
                    return;
                }
                $r1.writeInt(this.mGapPerSpan.length);
                $r1.writeIntArray(this.mGapPerSpan);
            }

            public String toString() throws  {
                return "FullSpanItem{mPosition=" + this.mPosition + ", mGapDir=" + this.mGapDir + ", mHasUnwantedGapAfter=" + this.mHasUnwantedGapAfter + ", mGapPerSpan=" + Arrays.toString(this.mGapPerSpan) + '}';
            }
        }

        LazySpanLookup() throws  {
        }

        int forceInvalidateAfter(int $i0) throws  {
            if (this.mFullSpanItems != null) {
                for (int $i1 = this.mFullSpanItems.size() - 1; $i1 >= 0; $i1--) {
                    if (((FullSpanItem) this.mFullSpanItems.get($i1)).mPosition >= $i0) {
                        this.mFullSpanItems.remove($i1);
                    }
                }
            }
            return invalidateAfter($i0);
        }

        int invalidateAfter(int $i0) throws  {
            if (this.mData == null) {
                return -1;
            }
            if ($i0 >= this.mData.length) {
                return -1;
            }
            int $i1 = invalidateFullSpansAfter($i0);
            if ($i1 == -1) {
                Arrays.fill(this.mData, $i0, this.mData.length, -1);
                return this.mData.length;
            }
            Arrays.fill(this.mData, $i0, $i1 + 1, -1);
            return $i1 + 1;
        }

        int getSpan(int $i0) throws  {
            return (this.mData == null || $i0 >= this.mData.length) ? -1 : this.mData[$i0];
        }

        void setSpan(int $i0, Span $r1) throws  {
            ensureSize($i0);
            this.mData[$i0] = $r1.mIndex;
        }

        int sizeForPosition(int $i0) throws  {
            int $i1 = this.mData.length;
            while ($i1 <= $i0) {
                $i1 *= 2;
            }
            return $i1;
        }

        void ensureSize(int $i0) throws  {
            if (this.mData == null) {
                this.mData = new int[(Math.max($i0, 10) + 1)];
                Arrays.fill(this.mData, -1);
            } else if ($i0 >= this.mData.length) {
                int[] $r1 = this.mData;
                this.mData = new int[sizeForPosition($i0)];
                System.arraycopy($r1, 0, this.mData, 0, $r1.length);
                Arrays.fill(this.mData, $r1.length, this.mData.length, -1);
            }
        }

        void clear() throws  {
            if (this.mData != null) {
                Arrays.fill(this.mData, -1);
            }
            this.mFullSpanItems = null;
        }

        void offsetForRemoval(int $i0, int $i1) throws  {
            if (this.mData != null && $i0 < this.mData.length) {
                ensureSize($i0 + $i1);
                System.arraycopy(this.mData, $i0 + $i1, this.mData, $i0, (this.mData.length - $i0) - $i1);
                Arrays.fill(this.mData, this.mData.length - $i1, this.mData.length, -1);
                offsetFullSpansForRemoval($i0, $i1);
            }
        }

        private void offsetFullSpansForRemoval(int $i0, int $i1) throws  {
            if (this.mFullSpanItems != null) {
                int $i2 = $i0 + $i1;
                for (int $i3 = this.mFullSpanItems.size() - 1; $i3 >= 0; $i3--) {
                    FullSpanItem $r3 = (FullSpanItem) this.mFullSpanItems.get($i3);
                    if ($r3.mPosition >= $i0) {
                        if ($r3.mPosition < $i2) {
                            this.mFullSpanItems.remove($i3);
                        } else {
                            $r3.mPosition -= $i1;
                        }
                    }
                }
            }
        }

        void offsetForAddition(int $i0, int $i1) throws  {
            if (this.mData != null && $i0 < this.mData.length) {
                ensureSize($i0 + $i1);
                System.arraycopy(this.mData, $i0, this.mData, $i0 + $i1, (this.mData.length - $i0) - $i1);
                Arrays.fill(this.mData, $i0, $i0 + $i1, -1);
                offsetFullSpansForAddition($i0, $i1);
            }
        }

        private void offsetFullSpansForAddition(int $i0, int $i1) throws  {
            if (this.mFullSpanItems != null) {
                for (int $i2 = this.mFullSpanItems.size() - 1; $i2 >= 0; $i2--) {
                    FullSpanItem $r3 = (FullSpanItem) this.mFullSpanItems.get($i2);
                    if ($r3.mPosition >= $i0) {
                        $r3.mPosition += $i1;
                    }
                }
            }
        }

        private int invalidateFullSpansAfter(int $i0) throws  {
            if (this.mFullSpanItems == null) {
                return -1;
            }
            FullSpanItem $r2 = getFullSpanItem($i0);
            if ($r2 != null) {
                this.mFullSpanItems.remove($r2);
            }
            int $i1 = -1;
            int $i2 = this.mFullSpanItems.size();
            for (int $i3 = 0; $i3 < $i2; $i3++) {
                if (((FullSpanItem) this.mFullSpanItems.get($i3)).mPosition >= $i0) {
                    $i1 = $i3;
                    break;
                }
            }
            if ($i1 == -1) {
                return -1;
            }
            $r2 = (FullSpanItem) this.mFullSpanItems.get($i1);
            this.mFullSpanItems.remove($i1);
            return $r2.mPosition;
        }

        public void addFullSpanItem(FullSpanItem $r1) throws  {
            if (this.mFullSpanItems == null) {
                this.mFullSpanItems = new ArrayList();
            }
            int $i0 = this.mFullSpanItems.size();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                FullSpanItem $r5 = (FullSpanItem) this.mFullSpanItems.get($i1);
                if ($r5.mPosition == $r1.mPosition) {
                    this.mFullSpanItems.remove($i1);
                }
                if ($r5.mPosition >= $r1.mPosition) {
                    this.mFullSpanItems.add($i1, $r1);
                    return;
                }
            }
            this.mFullSpanItems.add($r1);
        }

        public FullSpanItem getFullSpanItem(int $i0) throws  {
            if (this.mFullSpanItems == null) {
                return null;
            }
            for (int $i1 = this.mFullSpanItems.size() - 1; $i1 >= 0; $i1--) {
                FullSpanItem $r2 = (FullSpanItem) this.mFullSpanItems.get($i1);
                if ($r2.mPosition == $i0) {
                    return $r2;
                }
            }
            return null;
        }

        public FullSpanItem getFirstFullSpanItemInRange(int $i0, int $i1, int $i2, boolean $z0) throws  {
            if (this.mFullSpanItems == null) {
                return null;
            }
            int $i3 = this.mFullSpanItems.size();
            for (int $i4 = 0; $i4 < $i3; $i4++) {
                FullSpanItem $r2 = (FullSpanItem) this.mFullSpanItems.get($i4);
                if ($r2.mPosition >= $i1) {
                    return null;
                }
                if ($r2.mPosition >= $i0) {
                    if ($i2 == 0 || $r2.mGapDir == $i2) {
                        return $r2;
                    }
                    if ($z0 && $r2.mHasUnwantedGapAfter) {
                        return $r2;
                    }
                }
            }
            return null;
        }
    }

    public static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new C02731();
        boolean mAnchorLayoutFromEnd;
        int mAnchorPosition;
        List<FullSpanItem> mFullSpanItems;
        boolean mLastLayoutRTL;
        boolean mReverseLayout;
        int[] mSpanLookup;
        int mSpanLookupSize;
        int[] mSpanOffsets;
        int mSpanOffsetsSize;
        int mVisibleAnchorPosition;

        static class C02731 implements Creator<SavedState> {
            C02731() throws  {
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
            boolean $z1;
            boolean $z0 = true;
            this.mAnchorPosition = $r1.readInt();
            this.mVisibleAnchorPosition = $r1.readInt();
            this.mSpanOffsetsSize = $r1.readInt();
            if (this.mSpanOffsetsSize > 0) {
                this.mSpanOffsets = new int[this.mSpanOffsetsSize];
                $r1.readIntArray(this.mSpanOffsets);
            }
            this.mSpanLookupSize = $r1.readInt();
            if (this.mSpanLookupSize > 0) {
                this.mSpanLookup = new int[this.mSpanLookupSize];
                $r1.readIntArray(this.mSpanLookup);
            }
            this.mReverseLayout = $r1.readInt() == 1;
            if ($r1.readInt() == 1) {
                $z1 = true;
            } else {
                $z1 = false;
            }
            this.mAnchorLayoutFromEnd = $z1;
            if ($r1.readInt() != 1) {
                $z0 = false;
            }
            this.mLastLayoutRTL = $z0;
            this.mFullSpanItems = $r1.readArrayList(FullSpanItem.class.getClassLoader());
        }

        public SavedState(SavedState $r1) throws  {
            this.mSpanOffsetsSize = $r1.mSpanOffsetsSize;
            this.mAnchorPosition = $r1.mAnchorPosition;
            this.mVisibleAnchorPosition = $r1.mVisibleAnchorPosition;
            this.mSpanOffsets = $r1.mSpanOffsets;
            this.mSpanLookupSize = $r1.mSpanLookupSize;
            this.mSpanLookup = $r1.mSpanLookup;
            this.mReverseLayout = $r1.mReverseLayout;
            this.mAnchorLayoutFromEnd = $r1.mAnchorLayoutFromEnd;
            this.mLastLayoutRTL = $r1.mLastLayoutRTL;
            this.mFullSpanItems = $r1.mFullSpanItems;
        }

        void invalidateSpanInfo() throws  {
            this.mSpanOffsets = null;
            this.mSpanOffsetsSize = 0;
            this.mSpanLookupSize = 0;
            this.mSpanLookup = null;
            this.mFullSpanItems = null;
        }

        void invalidateAnchorPositionInfo() throws  {
            this.mSpanOffsets = null;
            this.mSpanOffsetsSize = 0;
            this.mAnchorPosition = -1;
            this.mVisibleAnchorPosition = -1;
        }

        public void writeToParcel(Parcel $r1, int flags) throws  {
            byte $b2;
            byte $b1 = (byte) 1;
            $r1.writeInt(this.mAnchorPosition);
            $r1.writeInt(this.mVisibleAnchorPosition);
            $r1.writeInt(this.mSpanOffsetsSize);
            if (this.mSpanOffsetsSize > 0) {
                $r1.writeIntArray(this.mSpanOffsets);
            }
            $r1.writeInt(this.mSpanLookupSize);
            if (this.mSpanLookupSize > 0) {
                $r1.writeIntArray(this.mSpanLookup);
            }
            if (this.mReverseLayout) {
                $b2 = (byte) 1;
            } else {
                $b2 = (byte) 0;
            }
            $r1.writeInt($b2);
            if (this.mAnchorLayoutFromEnd) {
                $b2 = (byte) 1;
            } else {
                $b2 = (byte) 0;
            }
            $r1.writeInt($b2);
            if (!this.mLastLayoutRTL) {
                $b1 = (byte) 0;
            }
            $r1.writeInt($b1);
            $r1.writeList(this.mFullSpanItems);
        }
    }

    class Span {
        static final int INVALID_LINE = Integer.MIN_VALUE;
        int mCachedEnd;
        int mCachedStart;
        int mDeletedSize;
        final int mIndex;
        private ArrayList<View> mViews;

        private Span(int $i0) throws  {
            this.mViews = new ArrayList();
            this.mCachedStart = Integer.MIN_VALUE;
            this.mCachedEnd = Integer.MIN_VALUE;
            this.mDeletedSize = 0;
            this.mIndex = $i0;
        }

        int getStartLine(int $i1) throws  {
            if (this.mCachedStart != Integer.MIN_VALUE) {
                return this.mCachedStart;
            }
            if (this.mViews.size() == 0) {
                return $i1;
            }
            calculateCachedStart();
            return this.mCachedStart;
        }

        void calculateCachedStart() throws  {
            View $r3 = (View) this.mViews.get(0);
            LayoutParams $r4 = getLayoutParams($r3);
            this.mCachedStart = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart($r3);
            if ($r4.mFullSpan) {
                FullSpanItem $r8 = StaggeredGridLayoutManager.this.mLazySpanLookup.getFullSpanItem($r4.getViewLayoutPosition());
                if ($r8 != null && $r8.mGapDir == -1) {
                    this.mCachedStart -= $r8.getGapForSpan(this.mIndex);
                }
            }
        }

        int getStartLine() throws  {
            if (this.mCachedStart != Integer.MIN_VALUE) {
                return this.mCachedStart;
            }
            calculateCachedStart();
            return this.mCachedStart;
        }

        int getEndLine(int $i1) throws  {
            if (this.mCachedEnd != Integer.MIN_VALUE) {
                return this.mCachedEnd;
            }
            if (this.mViews.size() == 0) {
                return $i1;
            }
            calculateCachedEnd();
            return this.mCachedEnd;
        }

        void calculateCachedEnd() throws  {
            View $r4 = (View) this.mViews.get(this.mViews.size() - 1);
            LayoutParams $r5 = getLayoutParams($r4);
            this.mCachedEnd = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd($r4);
            if ($r5.mFullSpan) {
                FullSpanItem $r9 = StaggeredGridLayoutManager.this.mLazySpanLookup.getFullSpanItem($r5.getViewLayoutPosition());
                if ($r9 != null && $r9.mGapDir == 1) {
                    this.mCachedEnd += $r9.getGapForSpan(this.mIndex);
                }
            }
        }

        int getEndLine() throws  {
            if (this.mCachedEnd != Integer.MIN_VALUE) {
                return this.mCachedEnd;
            }
            calculateCachedEnd();
            return this.mCachedEnd;
        }

        void prependToSpan(View $r1) throws  {
            LayoutParams $r2 = getLayoutParams($r1);
            $r2.mSpan = this;
            this.mViews.add(0, $r1);
            this.mCachedStart = Integer.MIN_VALUE;
            if (this.mViews.size() == 1) {
                this.mCachedEnd = Integer.MIN_VALUE;
            }
            if ($r2.isItemRemoved() || $r2.isItemChanged()) {
                this.mDeletedSize += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement($r1);
            }
        }

        void appendToSpan(View $r1) throws  {
            LayoutParams $r2 = getLayoutParams($r1);
            $r2.mSpan = this;
            this.mViews.add($r1);
            this.mCachedEnd = Integer.MIN_VALUE;
            if (this.mViews.size() == 1) {
                this.mCachedStart = Integer.MIN_VALUE;
            }
            if ($r2.isItemRemoved() || $r2.isItemChanged()) {
                this.mDeletedSize += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement($r1);
            }
        }

        void cacheReferenceLineAndClear(boolean $z0, int $i0) throws  {
            int $i1;
            if ($z0) {
                $i1 = getEndLine(Integer.MIN_VALUE);
            } else {
                $i1 = getStartLine(Integer.MIN_VALUE);
            }
            clear();
            if ($i1 != Integer.MIN_VALUE) {
                if ($z0 && $i1 < StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding()) {
                    return;
                }
                if ($z0 || $i1 <= StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding()) {
                    if ($i0 != Integer.MIN_VALUE) {
                        $i1 += $i0;
                    }
                    this.mCachedEnd = $i1;
                    this.mCachedStart = $i1;
                }
            }
        }

        void clear() throws  {
            this.mViews.clear();
            invalidateCache();
            this.mDeletedSize = 0;
        }

        void invalidateCache() throws  {
            this.mCachedStart = Integer.MIN_VALUE;
            this.mCachedEnd = Integer.MIN_VALUE;
        }

        void setLine(int $i0) throws  {
            this.mCachedStart = $i0;
            this.mCachedEnd = $i0;
        }

        void popEnd() throws  {
            int $i0 = this.mViews.size();
            View $r3 = (View) this.mViews.remove($i0 - 1);
            LayoutParams $r4 = getLayoutParams($r3);
            $r4.mSpan = null;
            if ($r4.isItemRemoved() || $r4.isItemChanged()) {
                this.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement($r3);
            }
            if ($i0 == 1) {
                this.mCachedStart = Integer.MIN_VALUE;
            }
            this.mCachedEnd = Integer.MIN_VALUE;
        }

        void popStart() throws  {
            View $r3 = (View) this.mViews.remove(0);
            LayoutParams $r4 = getLayoutParams($r3);
            $r4.mSpan = null;
            if (this.mViews.size() == 0) {
                this.mCachedEnd = Integer.MIN_VALUE;
            }
            if ($r4.isItemRemoved() || $r4.isItemChanged()) {
                this.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement($r3);
            }
            this.mCachedStart = Integer.MIN_VALUE;
        }

        public int getDeletedSize() throws  {
            return this.mDeletedSize;
        }

        LayoutParams getLayoutParams(View $r1) throws  {
            return (LayoutParams) $r1.getLayoutParams();
        }

        void onOffset(int $i0) throws  {
            if (this.mCachedStart != Integer.MIN_VALUE) {
                this.mCachedStart += $i0;
            }
            if (this.mCachedEnd != Integer.MIN_VALUE) {
                this.mCachedEnd += $i0;
            }
        }

        public int findFirstVisibleItemPosition() throws  {
            return StaggeredGridLayoutManager.this.mReverseLayout ? findOneVisibleChild(this.mViews.size() - 1, -1, false) : findOneVisibleChild(0, this.mViews.size(), false);
        }

        public int findFirstCompletelyVisibleItemPosition() throws  {
            return StaggeredGridLayoutManager.this.mReverseLayout ? findOneVisibleChild(this.mViews.size() - 1, -1, true) : findOneVisibleChild(0, this.mViews.size(), true);
        }

        public int findLastVisibleItemPosition() throws  {
            return StaggeredGridLayoutManager.this.mReverseLayout ? findOneVisibleChild(0, this.mViews.size(), false) : findOneVisibleChild(this.mViews.size() - 1, -1, false);
        }

        public int findLastCompletelyVisibleItemPosition() throws  {
            return StaggeredGridLayoutManager.this.mReverseLayout ? findOneVisibleChild(0, this.mViews.size(), true) : findOneVisibleChild(this.mViews.size() - 1, -1, true);
        }

        int findOneVisibleChild(int $i0, int $i1, boolean $z0) throws  {
            byte $b4;
            int $i2 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding();
            int $i3 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding();
            if ($i1 > $i0) {
                $b4 = (byte) 1;
            } else {
                $b4 = (byte) -1;
            }
            while ($i0 != $i1) {
                View $r5 = (View) this.mViews.get($i0);
                int $i5 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart($r5);
                int $i6 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd($r5);
                if ($i5 < $i3 && $i6 > $i2) {
                    if (!$z0) {
                        return StaggeredGridLayoutManager.this.getPosition($r5);
                    }
                    if ($i5 >= $i2 && $i6 <= $i3) {
                        return StaggeredGridLayoutManager.this.getPosition($r5);
                    }
                }
                $i0 += $b4;
            }
            return -1;
        }

        public View getFocusableViewAfter(int $i0, int $i1) throws  {
            View $r1 = null;
            View $r4;
            boolean $z0;
            if ($i1 == -1) {
                $i1 = this.mViews.size();
                for (int $i2 = 0; $i2 < $i1; $i2++) {
                    $r4 = (View) this.mViews.get($i2);
                    if (!$r4.isFocusable()) {
                        return $r1;
                    }
                    if (StaggeredGridLayoutManager.this.getPosition($r4) > $i0) {
                        $z0 = true;
                    } else {
                        $z0 = false;
                    }
                    if ($z0 != StaggeredGridLayoutManager.this.mReverseLayout) {
                        return $r1;
                    }
                    $r1 = $r4;
                }
                return $r1;
            }
            for ($i1 = this.mViews.size() - 1; $i1 >= 0; $i1--) {
                $r4 = (View) this.mViews.get($i1);
                if (!$r4.isFocusable()) {
                    return $r1;
                }
                if (StaggeredGridLayoutManager.this.getPosition($r4) > $i0) {
                    $z0 = true;
                } else {
                    $z0 = false;
                }
                if ($z0 != (!StaggeredGridLayoutManager.this.mReverseLayout)) {
                    return $r1;
                }
                $r1 = $r4;
            }
            return $r1;
        }
    }

    private int fill(android.support.v7.widget.RecyclerView.Recycler r29, android.support.v7.widget.LayoutState r30, android.support.v7.widget.RecyclerView.State r31) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:65:0x01a6
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
        r28 = this;
        r0 = r28;
        r6 = r0.mRemainingSpans;
        r0 = r28;
        r7 = r0.mSpanCount;
        r8 = 0;
        r9 = 1;
        r6.set(r8, r7, r9);
        r0 = r28;
        r10 = r0.mLayoutState;
        r11 = r10.mInfinite;
        if (r11 == 0) goto L_0x01ae;
    L_0x0015:
        r0 = r30;
        r7 = r0.mLayoutDirection;
        r8 = 1;
        if (r7 != r8) goto L_0x01aa;
    L_0x001c:
        r7 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
    L_0x001f:
        r0 = r30;
        r12 = r0.mLayoutDirection;
        r0 = r28;
        r0.updateAllRemainingSpans(r12, r7);
        r0 = r28;
        r11 = r0.mShouldReverseLayout;
        if (r11 == 0) goto L_0x01d1;
    L_0x002e:
        r0 = r28;
        r13 = r0.mPrimaryOrientation;
        r12 = r13.getEndAfterPadding();
    L_0x0036:
        r11 = 0;
    L_0x0037:
        r0 = r30;
        r1 = r31;
        r14 = r0.hasMore(r1);
        if (r14 == 0) goto L_0x034c;
    L_0x0041:
        r0 = r28;
        r10 = r0.mLayoutState;
        r14 = r10.mInfinite;
        if (r14 != 0) goto L_0x0053;
    L_0x0049:
        r0 = r28;
        r6 = r0.mRemainingSpans;
        r14 = r6.isEmpty();
        if (r14 != 0) goto L_0x034c;
    L_0x0053:
        r0 = r30;
        r1 = r29;
        r15 = r0.next(r1);
        r16 = r15.getLayoutParams();
        r18 = r16;
        r18 = (android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams) r18;
        r17 = r18;
        r0 = r17;
        r19 = r0.getViewLayoutPosition();
        r0 = r28;
        r0 = r0.mLazySpanLookup;
        r20 = r0;
        r1 = r19;
        r21 = r0.getSpan(r1);
        r8 = -1;
        r0 = r21;
        if (r0 != r8) goto L_0x01e2;
    L_0x007c:
        r11 = 1;
    L_0x007d:
        if (r11 == 0) goto L_0x01f1;
    L_0x007f:
        r0 = r17;
        r14 = r0.mFullSpan;
        if (r14 == 0) goto L_0x01e8;
    L_0x0085:
        r0 = r28;
        r0 = r0.mSpans;
        r22 = r0;
        r8 = 0;
        r23 = r22[r8];
    L_0x008e:
        r0 = r28;
        r0 = r0.mLazySpanLookup;
        r20 = r0;
        r1 = r19;
        r2 = r23;
        r0.setSpan(r1, r2);
    L_0x009b:
        r0 = r23;
        r1 = r17;
        r1.mSpan = r0;
        r0 = r30;
        r0 = r0.mLayoutDirection;
        r21 = r0;
        r8 = 1;
        r0 = r21;
        if (r0 != r8) goto L_0x0202;
    L_0x00ac:
        r0 = r28;
        r0.addView(r15);
    L_0x00b1:
        r8 = 0;
        r0 = r28;
        r1 = r17;
        r0.measureChildWithDecorationsAndMargin(r15, r1, r8);
        r0 = r30;
        r0 = r0.mLayoutDirection;
        r21 = r0;
        r8 = 1;
        r0 = r21;
        if (r0 != r8) goto L_0x0214;
    L_0x00c4:
        r0 = r17;
        r14 = r0.mFullSpan;
        if (r14 == 0) goto L_0x020d;
    L_0x00ca:
        r0 = r28;
        r21 = r0.getMaxEnd(r12);
    L_0x00d0:
        r0 = r28;
        r13 = r0.mPrimaryOrientation;
        r24 = r13.getDecoratedMeasurement(r15);
        r24 = r21 + r24;
        if (r11 == 0) goto L_0x0100;
    L_0x00dc:
        r0 = r17;
        r14 = r0.mFullSpan;
        if (r14 == 0) goto L_0x0100;
    L_0x00e2:
        r0 = r28;
        r1 = r21;
        r25 = r0.createFullSpanItemFromEnd(r1);
        r8 = -1;
        r0 = r25;
        r0.mGapDir = r8;
        r0 = r19;
        r1 = r25;
        r1.mPosition = r0;
        r0 = r28;
        r0 = r0.mLazySpanLookup;
        r20 = r0;
        r1 = r25;
        r0.addFullSpanItem(r1);
    L_0x0100:
        r0 = r17;
        r14 = r0.mFullSpan;
        if (r14 == 0) goto L_0x0118;
    L_0x0106:
        r0 = r30;
        r0 = r0.mItemDirection;
        r26 = r0;
        r8 = -1;
        r0 = r26;
        if (r0 != r8) goto L_0x0118;
    L_0x0111:
        if (r11 == 0) goto L_0x025c;
    L_0x0113:
        r8 = 1;
        r0 = r28;
        r0.mLaidOutInvalidFullSpan = r8;
    L_0x0118:
        r0 = r28;
        r1 = r17;
        r2 = r30;
        r0.attachViewToSpans(r15, r1, r2);
        r0 = r28;
        r11 = r0.isLayoutRTL();
        if (r11 == 0) goto L_0x02d3;
    L_0x0129:
        r0 = r28;
        r0 = r0.mOrientation;
        r19 = r0;
        r8 = 1;
        r0 = r19;
        if (r0 != r8) goto L_0x02d3;
    L_0x0134:
        r0 = r17;
        r11 = r0.mFullSpan;
        if (r11 == 0) goto L_0x029d;
    L_0x013a:
        r0 = r28;
        r13 = r0.mSecondaryOrientation;
        r19 = r13.getEndAfterPadding();
    L_0x0142:
        r0 = r28;
        r13 = r0.mSecondaryOrientation;
        r26 = r13.getDecoratedMeasurement(r15);
        r26 = r19 - r26;
    L_0x014c:
        r0 = r28;
        r0 = r0.mOrientation;
        r27 = r0;
        r8 = 1;
        r0 = r27;
        if (r0 != r8) goto L_0x0312;
    L_0x0157:
        r0 = r28;
        r1 = r15;
        r2 = r26;
        r3 = r21;
        r4 = r19;
        r5 = r24;
        r0.layoutDecoratedWithMargins(r1, r2, r3, r4, r5);
    L_0x0165:
        r0 = r17;
        r11 = r0.mFullSpan;
        if (r11 == 0) goto L_0x0321;
    L_0x016b:
        r0 = r28;
        r10 = r0.mLayoutState;
        r0 = r10.mLayoutDirection;
        r21 = r0;
        r0 = r28;
        r1 = r21;
        r0.updateAllRemainingSpans(r1, r7);
    L_0x017a:
        r0 = r28;
        r10 = r0.mLayoutState;
        r0 = r28;
        r1 = r29;
        r0.recycle(r1, r10);
        r0 = r28;
        r10 = r0.mLayoutState;
        r11 = r10.mStopInFocusable;
        if (r11 == 0) goto L_0x01a4;
    L_0x018d:
        r11 = r15.isFocusable();
        if (r11 == 0) goto L_0x01a4;
    L_0x0193:
        r0 = r17;
        r11 = r0.mFullSpan;
        if (r11 == 0) goto L_0x0337;
    L_0x0199:
        r0 = r28;
        r6 = r0.mRemainingSpans;
        r6.clear();
        goto L_0x01a4;
    L_0x01a1:
        goto L_0x0037;
    L_0x01a4:
        r11 = 1;
        goto L_0x01a1;
        goto L_0x01aa;
    L_0x01a7:
        goto L_0x001f;
    L_0x01aa:
        r7 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        goto L_0x01a7;
    L_0x01ae:
        r0 = r30;
        r7 = r0.mLayoutDirection;
        r8 = 1;
        if (r7 != r8) goto L_0x01c3;
    L_0x01b5:
        r0 = r30;
        r7 = r0.mEndLine;
        r0 = r30;
        r12 = r0.mAvailable;
        goto L_0x01c1;
    L_0x01be:
        goto L_0x001f;
    L_0x01c1:
        r7 = r7 + r12;
        goto L_0x01be;
    L_0x01c3:
        r0 = r30;
        r7 = r0.mStartLine;
        r0 = r30;
        r12 = r0.mAvailable;
        goto L_0x01cf;
    L_0x01cc:
        goto L_0x001f;
    L_0x01cf:
        r7 = r7 - r12;
        goto L_0x01cc;
    L_0x01d1:
        r0 = r28;
        r13 = r0.mPrimaryOrientation;
        goto L_0x01d9;
    L_0x01d6:
        goto L_0x0036;
    L_0x01d9:
        r12 = r13.getStartAfterPadding();
        goto L_0x01d6;
        goto L_0x01e2;
    L_0x01df:
        goto L_0x007d;
    L_0x01e2:
        r11 = 0;
        goto L_0x01df;
        goto L_0x01e8;
    L_0x01e5:
        goto L_0x008e;
    L_0x01e8:
        r0 = r28;
        r1 = r30;
        r23 = r0.getNextSpan(r1);
        goto L_0x01e5;
    L_0x01f1:
        r0 = r28;
        r0 = r0.mSpans;
        r22 = r0;
        goto L_0x01fb;
    L_0x01f8:
        goto L_0x009b;
    L_0x01fb:
        r23 = r22[r21];
        goto L_0x01f8;
        goto L_0x0202;
    L_0x01ff:
        goto L_0x00b1;
    L_0x0202:
        r8 = 0;
        r0 = r28;
        r0.addView(r15, r8);
        goto L_0x01ff;
        goto L_0x020d;
    L_0x020a:
        goto L_0x00d0;
    L_0x020d:
        r0 = r23;
        r21 = r0.getEndLine(r12);
        goto L_0x020a;
    L_0x0214:
        r0 = r17;
        r14 = r0.mFullSpan;
        if (r14 == 0) goto L_0x0255;
    L_0x021a:
        r0 = r28;
        r24 = r0.getMinStart(r12);
    L_0x0220:
        r0 = r28;
        r13 = r0.mPrimaryOrientation;
        r21 = r13.getDecoratedMeasurement(r15);
        r21 = r24 - r21;
        if (r11 == 0) goto L_0x0100;
    L_0x022c:
        r0 = r17;
        r14 = r0.mFullSpan;
        if (r14 == 0) goto L_0x0100;
    L_0x0232:
        r0 = r28;
        r1 = r24;
        r25 = r0.createFullSpanItemFromStart(r1);
        r8 = 1;
        r0 = r25;
        r0.mGapDir = r8;
        r0 = r19;
        r1 = r25;
        r1.mPosition = r0;
        r0 = r28;
        r0 = r0.mLazySpanLookup;
        r20 = r0;
        goto L_0x024f;
    L_0x024c:
        goto L_0x0100;
    L_0x024f:
        r1 = r25;
        r0.addFullSpanItem(r1);
        goto L_0x024c;
    L_0x0255:
        r0 = r23;
        r24 = r0.getStartLine(r12);
        goto L_0x0220;
    L_0x025c:
        r0 = r30;
        r0 = r0.mLayoutDirection;
        r26 = r0;
        r8 = 1;
        r0 = r26;
        if (r0 != r8) goto L_0x0291;
    L_0x0267:
        r0 = r28;
        r11 = r0.areAllEndsEqual();
        if (r11 != 0) goto L_0x028f;
    L_0x026f:
        r11 = 1;
    L_0x0270:
        if (r11 == 0) goto L_0x0118;
    L_0x0272:
        r0 = r28;
        r0 = r0.mLazySpanLookup;
        r20 = r0;
        r1 = r19;
        r25 = r0.getFullSpanItem(r1);
        if (r25 == 0) goto L_0x0289;
    L_0x0280:
        r8 = 1;
        r0 = r25;
        r0.mHasUnwantedGapAfter = r8;
        goto L_0x0289;
    L_0x0286:
        goto L_0x0118;
    L_0x0289:
        r8 = 1;
        r0 = r28;
        r0.mLaidOutInvalidFullSpan = r8;
        goto L_0x0286;
    L_0x028f:
        r11 = 0;
        goto L_0x0270;
    L_0x0291:
        r0 = r28;
        r11 = r0.areAllStartsEqual();
        if (r11 != 0) goto L_0x029b;
    L_0x0299:
        r11 = 1;
    L_0x029a:
        goto L_0x0270;
    L_0x029b:
        r11 = 0;
        goto L_0x029a;
    L_0x029d:
        r0 = r28;
        r13 = r0.mSecondaryOrientation;
        r19 = r13.getEndAfterPadding();
        r0 = r28;
        r0 = r0.mSpanCount;
        r26 = r0;
        r26 = r26 + -1;
        r0 = r23;
        r0 = r0.mIndex;
        r27 = r0;
        r0 = r26;
        r1 = r27;
        r0 = r0 - r1;
        r26 = r0;
        r0 = r28;
        r0 = r0.mSizePerSpan;
        r27 = r0;
        r0 = r26;
        r1 = r27;
        r0 = r0 * r1;
        r26 = r0;
        goto L_0x02cb;
    L_0x02c8:
        goto L_0x0142;
    L_0x02cb:
        r0 = r19;
        r1 = r26;
        r0 = r0 - r1;
        r19 = r0;
        goto L_0x02c8;
    L_0x02d3:
        r0 = r17;
        r11 = r0.mFullSpan;
        if (r11 == 0) goto L_0x02f0;
    L_0x02d9:
        r0 = r28;
        r13 = r0.mSecondaryOrientation;
        r26 = r13.getStartAfterPadding();
    L_0x02e1:
        r0 = r28;
        r13 = r0.mSecondaryOrientation;
        r19 = r13.getDecoratedMeasurement(r15);
        goto L_0x02ed;
    L_0x02ea:
        goto L_0x014c;
    L_0x02ed:
        r19 = r26 + r19;
        goto L_0x02ea;
    L_0x02f0:
        r0 = r23;
        r0 = r0.mIndex;
        r19 = r0;
        r0 = r28;
        r0 = r0.mSizePerSpan;
        r26 = r0;
        r0 = r19;
        r1 = r26;
        r0 = r0 * r1;
        r19 = r0;
        r0 = r28;
        r13 = r0.mSecondaryOrientation;
        r26 = r13.getStartAfterPadding();
        r26 = r19 + r26;
        goto L_0x02e1;
        goto L_0x0312;
    L_0x030f:
        goto L_0x0165;
    L_0x0312:
        r0 = r28;
        r1 = r15;
        r2 = r21;
        r3 = r26;
        r4 = r24;
        r5 = r19;
        r0.layoutDecoratedWithMargins(r1, r2, r3, r4, r5);
        goto L_0x030f;
    L_0x0321:
        r0 = r28;
        r10 = r0.mLayoutState;
        r0 = r10.mLayoutDirection;
        r21 = r0;
        goto L_0x032d;
    L_0x032a:
        goto L_0x017a;
    L_0x032d:
        r0 = r28;
        r1 = r23;
        r2 = r21;
        r0.updateRemainingSpans(r1, r2, r7);
        goto L_0x032a;
    L_0x0337:
        r0 = r28;
        r6 = r0.mRemainingSpans;
        r0 = r23;
        r0 = r0.mIndex;
        r21 = r0;
        goto L_0x0345;
    L_0x0342:
        goto L_0x01a4;
    L_0x0345:
        r8 = 0;
        r0 = r21;
        r6.set(r0, r8);
        goto L_0x0342;
    L_0x034c:
        if (r11 != 0) goto L_0x0359;
    L_0x034e:
        r0 = r28;
        r10 = r0.mLayoutState;
        r0 = r28;
        r1 = r29;
        r0.recycle(r1, r10);
    L_0x0359:
        r0 = r28;
        r10 = r0.mLayoutState;
        r7 = r10.mLayoutDirection;
        r8 = -1;
        if (r7 != r8) goto L_0x0385;
    L_0x0362:
        r0 = r28;
        r13 = r0.mPrimaryOrientation;
        r7 = r13.getStartAfterPadding();
        r0 = r28;
        r7 = r0.getMinStart(r7);
        r0 = r28;
        r13 = r0.mPrimaryOrientation;
        r12 = r13.getStartAfterPadding();
        r7 = r12 - r7;
    L_0x037a:
        if (r7 <= 0) goto L_0x039d;
    L_0x037c:
        r0 = r30;
        r12 = r0.mAvailable;
        r7 = java.lang.Math.min(r12, r7);
        return r7;
    L_0x0385:
        r0 = r28;
        r13 = r0.mPrimaryOrientation;
        r7 = r13.getEndAfterPadding();
        r0 = r28;
        r7 = r0.getMaxEnd(r7);
        r0 = r28;
        r13 = r0.mPrimaryOrientation;
        r12 = r13.getEndAfterPadding();
        r7 = r7 - r12;
        goto L_0x037a;
    L_0x039d:
        r8 = 0;
        return r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager.fill(android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.LayoutState, android.support.v7.widget.RecyclerView$State):int");
    }

    private void repositionToWrapContentIfNecessary() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:28:0x00f1 in {2, 7, 10, 11, 14, 21, 27, 29, 30, 35, 36} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r22 = this;
        r0 = r22;
        r2 = r0.mSecondaryOrientation;
        r3 = r2.getMode();
        r4 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        if (r3 != r4) goto L_0x000e;
    L_0x000d:
        return;
    L_0x000e:
        r5 = 0;
        r0 = r22;
        r3 = r0.getChildCount();
        r6 = 0;
    L_0x0016:
        if (r6 >= r3) goto L_0x004e;
    L_0x0018:
        r0 = r22;
        r7 = r0.getChildAt(r6);
        r0 = r22;
        r2 = r0.mSecondaryOrientation;
        r8 = r2.getDecoratedMeasurement(r7);
        r9 = (float) r8;
        r10 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1));
        if (r10 >= 0) goto L_0x002e;
    L_0x002b:
        r6 = r6 + 1;
        goto L_0x0016;
    L_0x002e:
        r11 = r7.getLayoutParams();
        r13 = r11;
        r13 = (android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams) r13;
        r12 = r13;
        r14 = r12.isFullSpan();
        if (r14 == 0) goto L_0x0049;
    L_0x003c:
        r15 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r9 = r15 * r9;
        r0 = r22;
        r8 = r0.mSpanCount;
        r0 = (float) r8;
        r16 = r0;
        r9 = r9 / r0;
    L_0x0049:
        r5 = java.lang.Math.max(r5, r9);
        goto L_0x002b;
    L_0x004e:
        r0 = r22;
        r6 = r0.mSizePerSpan;
        r0 = r22;
        r8 = r0.mSpanCount;
        r9 = (float) r8;
        r5 = r9 * r5;
        r8 = java.lang.Math.round(r5);
        r17 = r8;
        r0 = r22;
        r2 = r0.mSecondaryOrientation;
        r18 = r2.getMode();
        r4 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r0 = r18;
        if (r0 != r4) goto L_0x007c;
    L_0x006e:
        r0 = r22;
        r2 = r0.mSecondaryOrientation;
        r17 = r2.getTotalSpace();
        r0 = r17;
        r17 = java.lang.Math.min(r8, r0);
    L_0x007c:
        r0 = r22;
        r1 = r17;
        r0.updateMeasureSpecs(r1);
        r0 = r22;
        r8 = r0.mSizePerSpan;
        if (r8 == r6) goto L_0x014e;
    L_0x0089:
        r8 = 0;
    L_0x008a:
        if (r8 >= r3) goto L_0x014f;
    L_0x008c:
        r0 = r22;
        r7 = r0.getChildAt(r8);
        r11 = r7.getLayoutParams();
        r19 = r11;
        r19 = (android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams) r19;
        r12 = r19;
        r14 = r12.mFullSpan;
        if (r14 == 0) goto L_0x00a3;
    L_0x00a0:
        r8 = r8 + 1;
        goto L_0x008a;
    L_0x00a3:
        r0 = r22;
        r14 = r0.isLayoutRTL();
        if (r14 == 0) goto L_0x010d;
    L_0x00ab:
        r0 = r22;
        r0 = r0.mOrientation;
        r17 = r0;
        r4 = 1;
        r0 = r17;
        if (r0 != r4) goto L_0x010d;
    L_0x00b6:
        r0 = r22;
        r0 = r0.mSpanCount;
        r17 = r0;
        r17 = r17 + -1;
        r0 = r12.mSpan;
        r20 = r0;
        r0 = r0.mIndex;
        r18 = r0;
        r0 = r17;
        r1 = r18;
        r0 = r0 - r1;
        r17 = r0;
        r0 = -r0;
        r17 = r0;
        r0 = r22;
        r0 = r0.mSizePerSpan;
        r18 = r0;
        r0 = r17;
        r1 = r18;
        r0 = r0 * r1;
        r17 = r0;
        r0 = r22;
        r0 = r0.mSpanCount;
        r18 = r0;
        r18 = r18 + -1;
        r0 = r12.mSpan;
        r20 = r0;
        r0 = r0.mIndex;
        r21 = r0;
        goto L_0x00f5;
    L_0x00ee:
        goto L_0x00a0;
        goto L_0x00f5;
    L_0x00f2:
        goto L_0x00a0;
    L_0x00f5:
        r0 = r18;
        r1 = r21;
        r0 = r0 - r1;
        r18 = r0;
        r0 = -r0;
        r18 = r0;
        r0 = r0 * r6;
        r18 = r0;
        r0 = r17;
        r1 = r18;
        r0 = r0 - r1;
        r17 = r0;
        r7.offsetLeftAndRight(r0);
        goto L_0x00a0;
    L_0x010d:
        r0 = r12.mSpan;
        r20 = r0;
        r0 = r0.mIndex;
        r17 = r0;
        r0 = r22;
        r0 = r0.mSizePerSpan;
        r18 = r0;
        r0 = r17;
        r1 = r18;
        r0 = r0 * r1;
        r17 = r0;
        r0 = r12.mSpan;
        r20 = r0;
        r0 = r0.mIndex;
        r18 = r0;
        r0 = r0 * r6;
        r18 = r0;
        r0 = r22;
        r0 = r0.mOrientation;
        r21 = r0;
        r4 = 1;
        r0 = r21;
        if (r0 != r4) goto L_0x0143;
    L_0x0138:
        r0 = r17;
        r1 = r18;
        r0 = r0 - r1;
        r17 = r0;
        r7.offsetLeftAndRight(r0);
        goto L_0x00ee;
    L_0x0143:
        r0 = r17;
        r1 = r18;
        r0 = r0 - r1;
        r17 = r0;
        r7.offsetTopAndBottom(r0);
        goto L_0x00f2;
    L_0x014e:
        return;
    L_0x014f:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager.repositionToWrapContentIfNecessary():void");
    }

    public StaggeredGridLayoutManager(Context $r1, AttributeSet $r2, int $i0, int $i1) throws  {
        boolean $z0 = true;
        Properties $r3 = LayoutManager.getProperties($r1, $r2, $i0, $i1);
        setOrientation($r3.orientation);
        setSpanCount($r3.spanCount);
        setReverseLayout($r3.reverseLayout);
        if (this.mGapStrategy == 0) {
            $z0 = false;
        }
        setAutoMeasureEnabled($z0);
        this.mLayoutState = new LayoutState();
        createOrientationHelpers();
    }

    public StaggeredGridLayoutManager(int $i0, int $i1) throws  {
        boolean $z0 = true;
        this.mOrientation = $i1;
        setSpanCount($i0);
        if (this.mGapStrategy == 0) {
            $z0 = false;
        }
        setAutoMeasureEnabled($z0);
        this.mLayoutState = new LayoutState();
        createOrientationHelpers();
    }

    private void createOrientationHelpers() throws  {
        this.mPrimaryOrientation = OrientationHelper.createOrientationHelper(this, this.mOrientation);
        this.mSecondaryOrientation = OrientationHelper.createOrientationHelper(this, 1 - this.mOrientation);
    }

    private boolean checkForGaps() throws  {
        if (getChildCount() == 0 || this.mGapStrategy == 0 || !isAttachedToWindow()) {
            return false;
        }
        int $i0;
        int $i1;
        if (this.mShouldReverseLayout) {
            $i0 = getLastChildPosition();
            $i1 = getFirstChildPosition();
        } else {
            $i0 = getFirstChildPosition();
            $i1 = getLastChildPosition();
        }
        if ($i0 == 0 && hasGapsToFix() != null) {
            this.mLazySpanLookup.clear();
            requestSimpleAnimationsInNextLayout();
            requestLayout();
            return true;
        } else if (!this.mLaidOutInvalidFullSpan) {
            return false;
        } else {
            byte $b2 = this.mShouldReverseLayout ? (byte) -1 : (byte) 1;
            FullSpanItem $r3 = this.mLazySpanLookup.getFirstFullSpanItemInRange($i0, $i1 + 1, $b2, true);
            if ($r3 == null) {
                this.mLaidOutInvalidFullSpan = false;
                this.mLazySpanLookup.forceInvalidateAfter($i1 + 1);
                return false;
            }
            FullSpanItem $r4 = this.mLazySpanLookup.getFirstFullSpanItemInRange($i0, $r3.mPosition, $b2 * -1, true);
            if ($r4 == null) {
                this.mLazySpanLookup.forceInvalidateAfter($r3.mPosition);
            } else {
                this.mLazySpanLookup.forceInvalidateAfter($r4.mPosition + 1);
            }
            requestSimpleAnimationsInNextLayout();
            requestLayout();
            return true;
        }
    }

    public void onScrollStateChanged(int $i0) throws  {
        if ($i0 == 0) {
            checkForGaps();
        }
    }

    public void onDetachedFromWindow(RecyclerView view, Recycler recycler) throws  {
        removeCallbacks(this.mCheckForGapsRunnable);
        for (int $i0 = 0; $i0 < this.mSpanCount; $i0++) {
            this.mSpans[$i0].clear();
        }
    }

    View hasGapsToFix() throws  {
        int $i1;
        byte $b3;
        int $i0 = getChildCount() - 1;
        BitSet $r1 = new BitSet(this.mSpanCount);
        $r1.set(0, this.mSpanCount, true);
        byte $b2 = (this.mOrientation == 1 && isLayoutRTL()) ? (byte) 1 : (byte) -1;
        if (this.mShouldReverseLayout) {
            $i1 = $i0;
            $i0 = 0 - 1;
        } else {
            $i1 = 0;
            $i0++;
        }
        if ($i1 < $i0) {
            $b3 = (byte) 1;
        } else {
            $b3 = (byte) -1;
        }
        while ($i1 != $i0) {
            View $r2 = getChildAt($i1);
            LayoutParams $r4 = (LayoutParams) $r2.getLayoutParams();
            if ($r1.get($r4.mSpan.mIndex)) {
                if (checkSpanForGap($r4.mSpan)) {
                    return $r2;
                }
                $r1.clear($r4.mSpan.mIndex);
            }
            if (!($r4.mFullSpan || $i1 + $b3 == $i0)) {
                int $i4;
                View $r6 = getChildAt($i1 + $b3);
                boolean $z0 = false;
                OrientationHelper $r7;
                int $i5;
                if (this.mShouldReverseLayout) {
                    $r7 = this.mPrimaryOrientation;
                    $i4 = $r7.getDecoratedEnd($r2);
                    $r7 = this.mPrimaryOrientation;
                    $i5 = $r7.getDecoratedEnd($r6);
                    if ($i4 < $i5) {
                        return $r2;
                    }
                    if ($i4 == $i5) {
                        $z0 = true;
                    }
                } else {
                    $r7 = this.mPrimaryOrientation;
                    $i4 = $r7.getDecoratedStart($r2);
                    $r7 = this.mPrimaryOrientation;
                    $i5 = $r7.getDecoratedStart($r6);
                    if ($i4 > $i5) {
                        return $r2;
                    }
                    if ($i4 == $i5) {
                        $z0 = true;
                    }
                }
                if ($z0) {
                    boolean $z1;
                    LayoutParams $r8 = (LayoutParams) $r6.getLayoutParams();
                    $i4 = $r4.mSpan.mIndex;
                    int $i52 = $r8.mSpan.mIndex;
                    if ($i4 - $i52 < 0) {
                        $z0 = true;
                    } else {
                        $z0 = false;
                    }
                    if ($b2 < (byte) 0) {
                        $z1 = true;
                    } else {
                        $z1 = false;
                    }
                    if ($z0 != $z1) {
                        return $r2;
                    }
                } else {
                    continue;
                }
            }
            $i1 += $b3;
        }
        return null;
    }

    private boolean checkSpanForGap(Span $r1) throws  {
        if (this.mShouldReverseLayout) {
            if ($r1.getEndLine() < this.mPrimaryOrientation.getEndAfterPadding()) {
                if ($r1.getLayoutParams((View) $r1.mViews.get($r1.mViews.size() - 1)).mFullSpan) {
                    return false;
                }
                return true;
            }
        } else if ($r1.getStartLine() > this.mPrimaryOrientation.getStartAfterPadding()) {
            return !$r1.getLayoutParams((View) $r1.mViews.get(0)).mFullSpan;
        }
        return false;
    }

    public void setSpanCount(int $i0) throws  {
        assertNotInLayoutOrScroll(null);
        if ($i0 != this.mSpanCount) {
            invalidateSpanAssignments();
            this.mSpanCount = $i0;
            this.mRemainingSpans = new BitSet(this.mSpanCount);
            this.mSpans = new Span[this.mSpanCount];
            for ($i0 = 0; $i0 < this.mSpanCount; $i0++) {
                this.mSpans[$i0] = new Span($i0);
            }
            requestLayout();
        }
    }

    public void setOrientation(int $i0) throws  {
        if ($i0 == 0 || $i0 == 1) {
            assertNotInLayoutOrScroll(null);
            if ($i0 != this.mOrientation) {
                this.mOrientation = $i0;
                OrientationHelper $r1 = this.mPrimaryOrientation;
                this.mPrimaryOrientation = this.mSecondaryOrientation;
                this.mSecondaryOrientation = $r1;
                requestLayout();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("invalid orientation.");
    }

    public void setReverseLayout(boolean $z0) throws  {
        assertNotInLayoutOrScroll(null);
        if (!(this.mPendingSavedState == null || this.mPendingSavedState.mReverseLayout == $z0)) {
            this.mPendingSavedState.mReverseLayout = $z0;
        }
        this.mReverseLayout = $z0;
        requestLayout();
    }

    public int getGapStrategy() throws  {
        return this.mGapStrategy;
    }

    public void setGapStrategy(int $i0) throws  {
        assertNotInLayoutOrScroll(null);
        if ($i0 != this.mGapStrategy) {
            if ($i0 == 0 || $i0 == 2) {
                this.mGapStrategy = $i0;
                setAutoMeasureEnabled(this.mGapStrategy != 0);
                requestLayout();
                return;
            }
            throw new IllegalArgumentException("invalid gap strategy. Must be GAP_HANDLING_NONE or GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS");
        }
    }

    public void assertNotInLayoutOrScroll(String $r1) throws  {
        if (this.mPendingSavedState == null) {
            super.assertNotInLayoutOrScroll($r1);
        }
    }

    public int getSpanCount() throws  {
        return this.mSpanCount;
    }

    public void invalidateSpanAssignments() throws  {
        this.mLazySpanLookup.clear();
        requestLayout();
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

    boolean isLayoutRTL() throws  {
        return getLayoutDirection() == 1;
    }

    public boolean getReverseLayout() throws  {
        return this.mReverseLayout;
    }

    public void setMeasuredDimension(Rect $r1, int $i0, int $i1) throws  {
        int $i2 = getPaddingLeft() + getPaddingRight();
        int $i3 = getPaddingTop() + getPaddingBottom();
        if (this.mOrientation == 1) {
            $i1 = LayoutManager.chooseSize($i1, $r1.height() + $i3, getMinimumHeight());
            $i0 = LayoutManager.chooseSize($i0, (this.mSizePerSpan * this.mSpanCount) + $i2, getMinimumWidth());
        } else {
            $i0 = LayoutManager.chooseSize($i0, $r1.width() + $i2, getMinimumWidth());
            $i1 = LayoutManager.chooseSize($i1, (this.mSizePerSpan * this.mSpanCount) + $i3, getMinimumHeight());
        }
        setMeasuredDimension($i0, $i1);
    }

    public void onLayoutChildren(Recycler $r1, State $r2) throws  {
        onLayoutChildren($r1, $r2, true);
    }

    private void onLayoutChildren(Recycler $r1, State $r2, boolean $z0) throws  {
        boolean $z1 = true;
        AnchorInfo $r3 = this.mAnchorInfo;
        $r3.reset();
        if (!(this.mPendingSavedState == null && this.mPendingScrollPosition == -1) && $r2.getItemCount() == 0) {
            removeAndRecycleAllViews($r1);
            return;
        }
        int $i0;
        if (this.mPendingSavedState != null) {
            applyPendingSavedState($r3);
        } else {
            resolveShouldLayoutReverse();
            $r3.mLayoutFromEnd = this.mShouldReverseLayout;
        }
        updateAnchorInfoForLayout($r2, $r3);
        if (this.mPendingSavedState == null && !($r3.mLayoutFromEnd == this.mLastLayoutFromEnd && isLayoutRTL() == this.mLastLayoutRTL)) {
            this.mLazySpanLookup.clear();
            $r3.mInvalidateOffsets = true;
        }
        if (getChildCount() > 0 && (this.mPendingSavedState == null || this.mPendingSavedState.mSpanOffsetsSize < 1)) {
            if ($r3.mInvalidateOffsets) {
                for ($i0 = 0; $i0 < this.mSpanCount; $i0++) {
                    this.mSpans[$i0].clear();
                    if ($r3.mOffset != Integer.MIN_VALUE) {
                        this.mSpans[$i0].setLine($r3.mOffset);
                    }
                }
            } else {
                for ($i0 = 0; $i0 < this.mSpanCount; $i0++) {
                    this.mSpans[$i0].cacheReferenceLineAndClear(this.mShouldReverseLayout, $r3.mOffset);
                }
            }
        }
        detachAndScrapAttachedViews($r1);
        this.mLayoutState.mRecycle = false;
        this.mLaidOutInvalidFullSpan = false;
        updateMeasureSpecs(this.mSecondaryOrientation.getTotalSpace());
        updateLayoutState($r3.mPosition, $r2);
        LayoutState $r8;
        LayoutState $r10;
        if ($r3.mLayoutFromEnd) {
            setLayoutStateDirection(-1);
            fill($r1, this.mLayoutState, $r2);
            setLayoutStateDirection(1);
            $r8 = this.mLayoutState;
            $i0 = $r3.mPosition;
            $r10 = this.mLayoutState;
            $r8.mCurrentPosition = $i0 + $r10.mItemDirection;
            fill($r1, this.mLayoutState, $r2);
        } else {
            setLayoutStateDirection(1);
            fill($r1, this.mLayoutState, $r2);
            setLayoutStateDirection(-1);
            $r8 = this.mLayoutState;
            $i0 = $r3.mPosition;
            $r10 = this.mLayoutState;
            $r8.mCurrentPosition = $i0 + $r10.mItemDirection;
            fill($r1, this.mLayoutState, $r2);
        }
        repositionToWrapContentIfNecessary();
        if (getChildCount() > 0) {
            if (this.mShouldReverseLayout) {
                fixEndGap($r1, $r2, true);
                fixStartGap($r1, $r2, false);
            } else {
                fixStartGap($r1, $r2, true);
                fixEndGap($r1, $r2, false);
            }
        }
        boolean $z2 = false;
        if ($z0 && !$r2.isPreLayout()) {
            if (this.mGapStrategy == 0 || getChildCount() <= 0 || (!this.mLaidOutInvalidFullSpan && hasGapsToFix() == null)) {
                $z1 = false;
            }
            if ($z1) {
                removeCallbacks(this.mCheckForGapsRunnable);
                if (checkForGaps()) {
                    $z2 = true;
                }
            }
            this.mPendingScrollPosition = -1;
            this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        }
        boolean z = $r3.mLayoutFromEnd;
        $z0 = z;
        this.mLastLayoutFromEnd = z;
        this.mLastLayoutRTL = isLayoutRTL();
        this.mPendingSavedState = null;
        if ($z2) {
            onLayoutChildren($r1, $r2, false);
        }
    }

    private void applyPendingSavedState(AnchorInfo $r1) throws  {
        if (this.mPendingSavedState.mSpanOffsetsSize > 0) {
            if (this.mPendingSavedState.mSpanOffsetsSize == this.mSpanCount) {
                for (int $i0 = 0; $i0 < this.mSpanCount; $i0++) {
                    this.mSpans[$i0].clear();
                    int $i1 = this.mPendingSavedState.mSpanOffsets[$i0];
                    if ($i1 != Integer.MIN_VALUE) {
                        if (this.mPendingSavedState.mAnchorLayoutFromEnd) {
                            $i1 += this.mPrimaryOrientation.getEndAfterPadding();
                        } else {
                            $i1 += this.mPrimaryOrientation.getStartAfterPadding();
                        }
                    }
                    this.mSpans[$i0].setLine($i1);
                }
            } else {
                this.mPendingSavedState.invalidateSpanInfo();
                this.mPendingSavedState.mAnchorPosition = this.mPendingSavedState.mVisibleAnchorPosition;
            }
        }
        this.mLastLayoutRTL = this.mPendingSavedState.mLastLayoutRTL;
        setReverseLayout(this.mPendingSavedState.mReverseLayout);
        resolveShouldLayoutReverse();
        if (this.mPendingSavedState.mAnchorPosition != -1) {
            this.mPendingScrollPosition = this.mPendingSavedState.mAnchorPosition;
            $r1.mLayoutFromEnd = this.mPendingSavedState.mAnchorLayoutFromEnd;
        } else {
            $r1.mLayoutFromEnd = this.mShouldReverseLayout;
        }
        if (this.mPendingSavedState.mSpanLookupSize > 1) {
            this.mLazySpanLookup.mData = this.mPendingSavedState.mSpanLookup;
            this.mLazySpanLookup.mFullSpanItems = this.mPendingSavedState.mFullSpanItems;
        }
    }

    void updateAnchorInfoForLayout(State $r1, AnchorInfo $r2) throws  {
        if (!updateAnchorFromPendingData($r1, $r2) && !updateAnchorFromChildren($r1, $r2)) {
            $r2.assignCoordinateFromPadding();
            $r2.mPosition = 0;
        }
    }

    private boolean updateAnchorFromChildren(State $r1, AnchorInfo $r2) throws  {
        $r2.mPosition = this.mLastLayoutFromEnd ? findLastReferenceChildPosition($r1.getItemCount()) : findFirstReferenceChildPosition($r1.getItemCount());
        $r2.mOffset = Integer.MIN_VALUE;
        return true;
    }

    boolean updateAnchorFromPendingData(State $r1, AnchorInfo $r2) throws  {
        boolean $z0 = false;
        if ($r1.isPreLayout() || this.mPendingScrollPosition == -1) {
            return false;
        }
        if (this.mPendingScrollPosition < 0 || this.mPendingScrollPosition >= $r1.getItemCount()) {
            this.mPendingScrollPosition = -1;
            this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
            return false;
        } else if (this.mPendingSavedState == null || this.mPendingSavedState.mAnchorPosition == -1 || this.mPendingSavedState.mSpanOffsetsSize < 1) {
            View $r4 = findViewByPosition(this.mPendingScrollPosition);
            if ($r4 != null) {
                $r2.mPosition = this.mShouldReverseLayout ? getLastChildPosition() : getFirstChildPosition();
                if (this.mPendingScrollPositionOffset != Integer.MIN_VALUE) {
                    if ($r2.mLayoutFromEnd) {
                        $r2.mOffset = (this.mPrimaryOrientation.getEndAfterPadding() - this.mPendingScrollPositionOffset) - this.mPrimaryOrientation.getDecoratedEnd($r4);
                        return true;
                    }
                    $r2.mOffset = (this.mPrimaryOrientation.getStartAfterPadding() + this.mPendingScrollPositionOffset) - this.mPrimaryOrientation.getDecoratedStart($r4);
                    return true;
                } else if (this.mPrimaryOrientation.getDecoratedMeasurement($r4) > this.mPrimaryOrientation.getTotalSpace()) {
                    $r2.mOffset = $r2.mLayoutFromEnd ? this.mPrimaryOrientation.getEndAfterPadding() : this.mPrimaryOrientation.getStartAfterPadding();
                    return true;
                } else {
                    int $i0 = this.mPrimaryOrientation.getDecoratedStart($r4) - this.mPrimaryOrientation.getStartAfterPadding();
                    if ($i0 < 0) {
                        $r2.mOffset = -$i0;
                        return true;
                    }
                    $i0 = this.mPrimaryOrientation.getEndAfterPadding() - this.mPrimaryOrientation.getDecoratedEnd($r4);
                    if ($i0 < 0) {
                        $r2.mOffset = $i0;
                        return true;
                    }
                    $r2.mOffset = Integer.MIN_VALUE;
                    return true;
                }
            }
            $r2.mPosition = this.mPendingScrollPosition;
            if (this.mPendingScrollPositionOffset == Integer.MIN_VALUE) {
                if (calculateScrollDirectionForPosition($r2.mPosition) == 1) {
                    $z0 = true;
                }
                $r2.mLayoutFromEnd = $z0;
                $r2.assignCoordinateFromPadding();
            } else {
                $r2.assignCoordinateFromPadding(this.mPendingScrollPositionOffset);
            }
            $r2.mInvalidateOffsets = true;
            return true;
        } else {
            $r2.mOffset = Integer.MIN_VALUE;
            $r2.mPosition = this.mPendingScrollPosition;
            return true;
        }
    }

    void updateMeasureSpecs(int $i0) throws  {
        this.mSizePerSpan = $i0 / this.mSpanCount;
        this.mFullSizeSpec = MeasureSpec.makeMeasureSpec($i0, this.mSecondaryOrientation.getMode());
    }

    public boolean supportsPredictiveItemAnimations() throws  {
        return this.mPendingSavedState == null;
    }

    public int[] findFirstVisibleItemPositions(int[] $r1) throws  {
        if ($r1 == null) {
            $r1 = new int[this.mSpanCount];
        } else if ($r1.length < this.mSpanCount) {
            throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + $r1.length);
        }
        for (int $i0 = 0; $i0 < this.mSpanCount; $i0++) {
            $r1[$i0] = this.mSpans[$i0].findFirstVisibleItemPosition();
        }
        return $r1;
    }

    public int[] findFirstCompletelyVisibleItemPositions(int[] $r1) throws  {
        if ($r1 == null) {
            $r1 = new int[this.mSpanCount];
        } else if ($r1.length < this.mSpanCount) {
            throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + $r1.length);
        }
        for (int $i0 = 0; $i0 < this.mSpanCount; $i0++) {
            $r1[$i0] = this.mSpans[$i0].findFirstCompletelyVisibleItemPosition();
        }
        return $r1;
    }

    public int[] findLastVisibleItemPositions(int[] $r1) throws  {
        if ($r1 == null) {
            $r1 = new int[this.mSpanCount];
        } else if ($r1.length < this.mSpanCount) {
            throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + $r1.length);
        }
        for (int $i0 = 0; $i0 < this.mSpanCount; $i0++) {
            $r1[$i0] = this.mSpans[$i0].findLastVisibleItemPosition();
        }
        return $r1;
    }

    public int[] findLastCompletelyVisibleItemPositions(int[] $r1) throws  {
        if ($r1 == null) {
            $r1 = new int[this.mSpanCount];
        } else if ($r1.length < this.mSpanCount) {
            throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + $r1.length);
        }
        for (int $i0 = 0; $i0 < this.mSpanCount; $i0++) {
            $r1[$i0] = this.mSpans[$i0].findLastCompletelyVisibleItemPosition();
        }
        return $r1;
    }

    public int computeHorizontalScrollOffset(State $r1) throws  {
        return computeScrollOffset($r1);
    }

    private int computeScrollOffset(State $r1) throws  {
        boolean $z0 = false;
        if (getChildCount() == 0) {
            return 0;
        }
        OrientationHelper $r2 = this.mPrimaryOrientation;
        View $r3 = findFirstVisibleItemClosestToStart(!this.mSmoothScrollbarEnabled, true);
        if (!this.mSmoothScrollbarEnabled) {
            $z0 = true;
        }
        return ScrollbarHelper.computeScrollOffset($r1, $r2, $r3, findFirstVisibleItemClosestToEnd($z0, true), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }

    public int computeVerticalScrollOffset(State $r1) throws  {
        return computeScrollOffset($r1);
    }

    public int computeHorizontalScrollExtent(State $r1) throws  {
        return computeScrollExtent($r1);
    }

    private int computeScrollExtent(State $r1) throws  {
        boolean $z0 = false;
        if (getChildCount() == 0) {
            return 0;
        }
        OrientationHelper $r2 = this.mPrimaryOrientation;
        View $r3 = findFirstVisibleItemClosestToStart(!this.mSmoothScrollbarEnabled, true);
        if (!this.mSmoothScrollbarEnabled) {
            $z0 = true;
        }
        return ScrollbarHelper.computeScrollExtent($r1, $r2, $r3, findFirstVisibleItemClosestToEnd($z0, true), this, this.mSmoothScrollbarEnabled);
    }

    public int computeVerticalScrollExtent(State $r1) throws  {
        return computeScrollExtent($r1);
    }

    public int computeHorizontalScrollRange(State $r1) throws  {
        return computeScrollRange($r1);
    }

    private int computeScrollRange(State $r1) throws  {
        boolean $z0 = false;
        if (getChildCount() == 0) {
            return 0;
        }
        OrientationHelper $r2 = this.mPrimaryOrientation;
        View $r3 = findFirstVisibleItemClosestToStart(!this.mSmoothScrollbarEnabled, true);
        if (!this.mSmoothScrollbarEnabled) {
            $z0 = true;
        }
        return ScrollbarHelper.computeScrollRange($r1, $r2, $r3, findFirstVisibleItemClosestToEnd($z0, true), this, this.mSmoothScrollbarEnabled);
    }

    public int computeVerticalScrollRange(State $r1) throws  {
        return computeScrollRange($r1);
    }

    private void measureChildWithDecorationsAndMargin(View $r1, LayoutParams $r2, boolean $z0) throws  {
        if ($r2.mFullSpan) {
            if (this.mOrientation == 1) {
                measureChildWithDecorationsAndMargin($r1, this.mFullSizeSpec, LayoutManager.getChildMeasureSpec(getHeight(), getHeightMode(), 0, $r2.height, true), $z0);
            } else {
                measureChildWithDecorationsAndMargin($r1, LayoutManager.getChildMeasureSpec(getWidth(), getWidthMode(), 0, $r2.width, true), this.mFullSizeSpec, $z0);
            }
        } else if (this.mOrientation == 1) {
            measureChildWithDecorationsAndMargin($r1, LayoutManager.getChildMeasureSpec(this.mSizePerSpan, getWidthMode(), 0, $r2.width, false), LayoutManager.getChildMeasureSpec(getHeight(), getHeightMode(), 0, $r2.height, true), $z0);
        } else {
            measureChildWithDecorationsAndMargin($r1, LayoutManager.getChildMeasureSpec(getWidth(), getWidthMode(), 0, $r2.width, true), LayoutManager.getChildMeasureSpec(this.mSizePerSpan, getHeightMode(), 0, $r2.height, false), $z0);
        }
    }

    private void measureChildWithDecorationsAndMargin(View $r1, int $i0, int $i1, boolean $z0) throws  {
        calculateItemDecorationsForChild($r1, this.mTmpRect);
        LayoutParams $r4 = (LayoutParams) $r1.getLayoutParams();
        $i0 = updateSpecWithExtra($i0, $r4.leftMargin + this.mTmpRect.left, $r4.rightMargin + this.mTmpRect.right);
        $i1 = updateSpecWithExtra($i1, $r4.topMargin + this.mTmpRect.top, $r4.bottomMargin + this.mTmpRect.bottom);
        if ($z0 ? shouldReMeasureChild($r1, $i0, $i1, $r4) : shouldMeasureChild($r1, $i0, $i1, $r4)) {
            $r1.measure($i0, $i1);
        }
    }

    private int updateSpecWithExtra(int $i2, int $i0, int $i1) throws  {
        if ($i0 == 0 && $i1 == 0) {
            return $i2;
        }
        int $i3 = MeasureSpec.getMode($i2);
        return ($i3 == Integer.MIN_VALUE || $i3 == 1073741824) ? MeasureSpec.makeMeasureSpec(Math.max(0, (MeasureSpec.getSize($i2) - $i0) - $i1), $i3) : $i2;
    }

    public void onRestoreInstanceState(Parcelable $r1) throws  {
        if ($r1 instanceof SavedState) {
            this.mPendingSavedState = (SavedState) $r1;
            requestLayout();
        }
    }

    public Parcelable onSaveInstanceState() throws  {
        if (this.mPendingSavedState != null) {
            return new SavedState(this.mPendingSavedState);
        }
        SavedState $r1 = new SavedState();
        $r1.mReverseLayout = this.mReverseLayout;
        $r1.mAnchorLayoutFromEnd = this.mLastLayoutFromEnd;
        $r1.mLastLayoutRTL = this.mLastLayoutRTL;
        if (this.mLazySpanLookup == null || this.mLazySpanLookup.mData == null) {
            $r1.mSpanLookupSize = 0;
        } else {
            $r1.mSpanLookup = this.mLazySpanLookup.mData;
            $r1.mSpanLookupSize = $r1.mSpanLookup.length;
            $r1.mFullSpanItems = this.mLazySpanLookup.mFullSpanItems;
        }
        if (getChildCount() > 0) {
            $r1.mAnchorPosition = this.mLastLayoutFromEnd ? getLastChildPosition() : getFirstChildPosition();
            $r1.mVisibleAnchorPosition = findFirstVisibleItemPositionInt();
            $r1.mSpanOffsetsSize = this.mSpanCount;
            $r1.mSpanOffsets = new int[this.mSpanCount];
            for (int $i0 = 0; $i0 < this.mSpanCount; $i0++) {
                int $i2;
                int $i1;
                if (this.mLastLayoutFromEnd) {
                    $i1 = this.mSpans[$i0].getEndLine(Integer.MIN_VALUE);
                    $i2 = $i1;
                    if ($i1 != Integer.MIN_VALUE) {
                        $i2 = $i1 - this.mPrimaryOrientation.getEndAfterPadding();
                    }
                } else {
                    $i1 = this.mSpans[$i0].getStartLine(Integer.MIN_VALUE);
                    $i2 = $i1;
                    if ($i1 != Integer.MIN_VALUE) {
                        $i2 = $i1 - this.mPrimaryOrientation.getStartAfterPadding();
                    }
                }
                $r1.mSpanOffsets[$i0] = $i2;
            }
            return $r1;
        }
        $r1.mAnchorPosition = -1;
        $r1.mVisibleAnchorPosition = -1;
        $r1.mSpanOffsetsSize = 0;
        return $r1;
    }

    public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View $r3, AccessibilityNodeInfoCompat $r4) throws  {
        android.view.ViewGroup.LayoutParams $r5 = $r3.getLayoutParams();
        if ($r5 instanceof LayoutParams) {
            LayoutParams $r6 = (LayoutParams) $r5;
            if (this.mOrientation == 0) {
                $r4.setCollectionItemInfo(CollectionItemInfoCompat.obtain($r6.getSpanIndex(), $r6.mFullSpan ? this.mSpanCount : 1, -1, -1, $r6.mFullSpan, false));
                return;
            } else {
                $r4.setCollectionItemInfo(CollectionItemInfoCompat.obtain(-1, -1, $r6.getSpanIndex(), $r6.mFullSpan ? this.mSpanCount : 1, $r6.mFullSpan, false));
                return;
            }
        }
        super.onInitializeAccessibilityNodeInfoForItem($r3, $r4);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent $r1) throws  {
        super.onInitializeAccessibilityEvent($r1);
        if (getChildCount() > 0) {
            AccessibilityRecordCompat $r2 = AccessibilityEventCompat.asRecord($r1);
            View $r3 = findFirstVisibleItemClosestToStart(false, true);
            View $r4 = findFirstVisibleItemClosestToEnd(false, true);
            if ($r3 != null && $r4 != null) {
                int $i0 = getPosition($r3);
                int $i1 = getPosition($r4);
                if ($i0 < $i1) {
                    $r2.setFromIndex($i0);
                    $r2.setToIndex($i1);
                    return;
                }
                $r2.setFromIndex($i1);
                $r2.setToIndex($i0);
            }
        }
    }

    int findFirstVisibleItemPositionInt() throws  {
        View $r1 = this.mShouldReverseLayout ? findFirstVisibleItemClosestToEnd(true, true) : findFirstVisibleItemClosestToStart(true, true);
        if ($r1 == null) {
            return -1;
        }
        return getPosition($r1);
    }

    public int getRowCountForAccessibility(Recycler $r1, State $r2) throws  {
        if (this.mOrientation == 0) {
            return this.mSpanCount;
        }
        return super.getRowCountForAccessibility($r1, $r2);
    }

    public int getColumnCountForAccessibility(Recycler $r1, State $r2) throws  {
        if (this.mOrientation == 1) {
            return this.mSpanCount;
        }
        return super.getColumnCountForAccessibility($r1, $r2);
    }

    View findFirstVisibleItemClosestToStart(boolean $z0, boolean $z1) throws  {
        int $i0 = this.mPrimaryOrientation.getStartAfterPadding();
        int $i1 = this.mPrimaryOrientation.getEndAfterPadding();
        int $i2 = getChildCount();
        View $r2 = null;
        for (int $i3 = 0; $i3 < $i2; $i3++) {
            View $r3 = getChildAt($i3);
            int $i4 = this.mPrimaryOrientation.getDecoratedStart($r3);
            if (this.mPrimaryOrientation.getDecoratedEnd($r3) > $i0 && $i4 < $i1) {
                if ($i4 >= $i0 || !$z0) {
                    return $r3;
                }
                if ($z1 && $r2 == null) {
                    $r2 = $r3;
                }
            }
        }
        return $r2;
    }

    View findFirstVisibleItemClosestToEnd(boolean $z0, boolean $z1) throws  {
        int $i0 = this.mPrimaryOrientation.getStartAfterPadding();
        int $i1 = this.mPrimaryOrientation.getEndAfterPadding();
        View $r2 = null;
        for (int $i2 = getChildCount() - 1; $i2 >= 0; $i2--) {
            View $r3 = getChildAt($i2);
            int $i3 = this.mPrimaryOrientation.getDecoratedStart($r3);
            int $i4 = this.mPrimaryOrientation.getDecoratedEnd($r3);
            if ($i4 > $i0 && $i3 < $i1) {
                if ($i4 <= $i1 || !$z0) {
                    return $r3;
                }
                if ($z1 && $r2 == null) {
                    $r2 = $r3;
                }
            }
        }
        return $r2;
    }

    private void fixEndGap(Recycler $r1, State $r2, boolean $z0) throws  {
        int $i1 = getMaxEnd(Integer.MIN_VALUE);
        if ($i1 != Integer.MIN_VALUE) {
            $i1 = this.mPrimaryOrientation.getEndAfterPadding() - $i1;
            if ($i1 > 0) {
                $i1 -= -scrollBy(-$i1, $r1, $r2);
                if ($z0 && $i1 > 0) {
                    this.mPrimaryOrientation.offsetChildren($i1);
                }
            }
        }
    }

    private void fixStartGap(Recycler $r1, State $r2, boolean $z0) throws  {
        int $i0 = getMinStart(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        if ($i0 != ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) {
            $i0 -= this.mPrimaryOrientation.getStartAfterPadding();
            if ($i0 > 0) {
                $i0 -= scrollBy($i0, $r1, $r2);
                if ($z0 && $i0 > 0) {
                    this.mPrimaryOrientation.offsetChildren(-$i0);
                }
            }
        }
    }

    private void updateLayoutState(int $i0, State $r1) throws  {
        boolean $z0 = true;
        this.mLayoutState.mAvailable = 0;
        this.mLayoutState.mCurrentPosition = $i0;
        int $i1 = 0;
        int $i2 = 0;
        if (isSmoothScrolling()) {
            int $i3 = $r1.getTargetScrollPosition();
            if ($i3 != -1) {
                if (this.mShouldReverseLayout == ($i3 < $i0)) {
                    $i2 = this.mPrimaryOrientation.getTotalSpace();
                } else {
                    $i1 = this.mPrimaryOrientation.getTotalSpace();
                }
            }
        }
        if (getClipToPadding()) {
            this.mLayoutState.mStartLine = this.mPrimaryOrientation.getStartAfterPadding() - $i1;
            this.mLayoutState.mEndLine = this.mPrimaryOrientation.getEndAfterPadding() + $i2;
        } else {
            this.mLayoutState.mEndLine = this.mPrimaryOrientation.getEnd() + $i2;
            this.mLayoutState.mStartLine = -$i1;
        }
        this.mLayoutState.mStopInFocusable = false;
        this.mLayoutState.mRecycle = true;
        LayoutState $r2 = this.mLayoutState;
        if (!(this.mPrimaryOrientation.getMode() == 0 && this.mPrimaryOrientation.getEnd() == 0)) {
            $z0 = false;
        }
        $r2.mInfinite = $z0;
    }

    private void setLayoutStateDirection(int $i0) throws  {
        byte $b1 = (byte) 1;
        this.mLayoutState.mLayoutDirection = $i0;
        LayoutState $r1 = this.mLayoutState;
        if (this.mShouldReverseLayout != ($i0 == -1)) {
            $b1 = (byte) -1;
        }
        $r1.mItemDirection = $b1;
    }

    public void offsetChildrenHorizontal(int $i0) throws  {
        super.offsetChildrenHorizontal($i0);
        for (int $i1 = 0; $i1 < this.mSpanCount; $i1++) {
            this.mSpans[$i1].onOffset($i0);
        }
    }

    public void offsetChildrenVertical(int $i0) throws  {
        super.offsetChildrenVertical($i0);
        for (int $i1 = 0; $i1 < this.mSpanCount; $i1++) {
            this.mSpans[$i1].onOffset($i0);
        }
    }

    public void onItemsRemoved(RecyclerView recyclerView, int $i0, int $i1) throws  {
        handleUpdate($i0, $i1, 2);
    }

    public void onItemsAdded(RecyclerView recyclerView, int $i0, int $i1) throws  {
        handleUpdate($i0, $i1, 1);
    }

    public void onItemsChanged(RecyclerView recyclerView) throws  {
        this.mLazySpanLookup.clear();
        requestLayout();
    }

    public void onItemsMoved(RecyclerView recyclerView, int $i0, int $i1, int itemCount) throws  {
        handleUpdate($i0, $i1, 8);
    }

    public void onItemsUpdated(RecyclerView recyclerView, int $i0, int $i1, Object payload) throws  {
        handleUpdate($i0, $i1, 4);
    }

    private void handleUpdate(int $i0, int $i1, int $i2) throws  {
        int $i3;
        int $i5;
        int $i4 = this.mShouldReverseLayout ? getLastChildPosition() : getFirstChildPosition();
        if ($i2 != 8) {
            $i3 = $i0;
            $i5 = $i0 + $i1;
        } else if ($i0 < $i1) {
            $i5 = $i1 + 1;
            $i3 = $i0;
        } else {
            $i5 = $i0 + 1;
            $i3 = $i1;
        }
        this.mLazySpanLookup.invalidateAfter($i3);
        switch ($i2) {
            case 1:
                this.mLazySpanLookup.offsetForAddition($i0, $i1);
                break;
            case 2:
                this.mLazySpanLookup.offsetForRemoval($i0, $i1);
                break;
            case 8:
                this.mLazySpanLookup.offsetForRemoval($i0, 1);
                this.mLazySpanLookup.offsetForAddition($i1, 1);
                break;
            default:
                break;
        }
        if ($i5 > $i4) {
            if ($i3 <= (this.mShouldReverseLayout ? getFirstChildPosition() : getLastChildPosition())) {
                requestLayout();
            }
        }
    }

    private FullSpanItem createFullSpanItemFromEnd(int $i0) throws  {
        FullSpanItem $r1 = new FullSpanItem();
        $r1.mGapPerSpan = new int[this.mSpanCount];
        for (int $i2 = 0; $i2 < this.mSpanCount; $i2++) {
            $r1.mGapPerSpan[$i2] = $i0 - this.mSpans[$i2].getEndLine($i0);
        }
        return $r1;
    }

    private FullSpanItem createFullSpanItemFromStart(int $i0) throws  {
        FullSpanItem $r1 = new FullSpanItem();
        $r1.mGapPerSpan = new int[this.mSpanCount];
        for (int $i2 = 0; $i2 < this.mSpanCount; $i2++) {
            $r1.mGapPerSpan[$i2] = this.mSpans[$i2].getStartLine($i0) - $i0;
        }
        return $r1;
    }

    private void attachViewToSpans(View $r1, LayoutParams $r2, LayoutState $r3) throws  {
        if ($r3.mLayoutDirection == 1) {
            if ($r2.mFullSpan) {
                appendViewToAllSpans($r1);
            } else {
                $r2.mSpan.appendToSpan($r1);
            }
        } else if ($r2.mFullSpan) {
            prependViewToAllSpans($r1);
        } else {
            $r2.mSpan.prependToSpan($r1);
        }
    }

    private void recycle(Recycler $r1, LayoutState $r2) throws  {
        if ($r2.mRecycle && !$r2.mInfinite) {
            if ($r2.mAvailable == 0) {
                if ($r2.mLayoutDirection == -1) {
                    recycleFromEnd($r1, $r2.mEndLine);
                } else {
                    recycleFromStart($r1, $r2.mStartLine);
                }
            } else if ($r2.mLayoutDirection == -1) {
                $i1 = $r2.mStartLine - getMaxStart($r2.mStartLine);
                if ($i1 < 0) {
                    $i0 = $r2.mEndLine;
                } else {
                    $i0 = $r2.mEndLine - Math.min($i1, $r2.mAvailable);
                }
                recycleFromEnd($r1, $i0);
            } else {
                $i1 = getMinEnd($r2.mEndLine) - $r2.mEndLine;
                if ($i1 < 0) {
                    $i0 = $r2.mStartLine;
                } else {
                    $i0 = $r2.mStartLine + Math.min($i1, $r2.mAvailable);
                }
                recycleFromStart($r1, $i0);
            }
        }
    }

    private void appendViewToAllSpans(View $r1) throws  {
        for (int $i0 = this.mSpanCount - 1; $i0 >= 0; $i0--) {
            this.mSpans[$i0].appendToSpan($r1);
        }
    }

    private void prependViewToAllSpans(View $r1) throws  {
        for (int $i0 = this.mSpanCount - 1; $i0 >= 0; $i0--) {
            this.mSpans[$i0].prependToSpan($r1);
        }
    }

    private void layoutDecoratedWithMargins(View $r1, int $i0, int $i1, int $i2, int $i3) throws  {
        LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
        layoutDecorated($r1, $i0 + $r3.leftMargin, $i1 + $r3.topMargin, $i2 - $r3.rightMargin, $i3 - $r3.bottomMargin);
    }

    private void updateAllRemainingSpans(int $i0, int $i1) throws  {
        for (int $i2 = 0; $i2 < this.mSpanCount; $i2++) {
            if (!this.mSpans[$i2].mViews.isEmpty()) {
                updateRemainingSpans(this.mSpans[$i2], $i0, $i1);
            }
        }
    }

    private void updateRemainingSpans(Span $r1, int $i0, int $i1) throws  {
        int $i2 = $r1.getDeletedSize();
        if ($i0 == -1) {
            if ($r1.getStartLine() + $i2 <= $i1) {
                this.mRemainingSpans.set($r1.mIndex, false);
            }
        } else if ($r1.getEndLine() - $i2 >= $i1) {
            this.mRemainingSpans.set($r1.mIndex, false);
        }
    }

    private int getMaxStart(int $i0) throws  {
        int $i1 = this.mSpans[0].getStartLine($i0);
        for (int $i2 = 1; $i2 < this.mSpanCount; $i2++) {
            int $i3 = this.mSpans[$i2].getStartLine($i0);
            if ($i3 > $i1) {
                $i1 = $i3;
            }
        }
        return $i1;
    }

    private int getMinStart(int $i0) throws  {
        int $i1 = this.mSpans[0].getStartLine($i0);
        for (int $i2 = 1; $i2 < this.mSpanCount; $i2++) {
            int $i3 = this.mSpans[$i2].getStartLine($i0);
            if ($i3 < $i1) {
                $i1 = $i3;
            }
        }
        return $i1;
    }

    boolean areAllEndsEqual() throws  {
        int $i0 = this.mSpans[0].getEndLine(Integer.MIN_VALUE);
        for (int $i1 = 1; $i1 < this.mSpanCount; $i1++) {
            if (this.mSpans[$i1].getEndLine(Integer.MIN_VALUE) != $i0) {
                return false;
            }
        }
        return true;
    }

    boolean areAllStartsEqual() throws  {
        int $i0 = this.mSpans[0].getStartLine(Integer.MIN_VALUE);
        for (int $i1 = 1; $i1 < this.mSpanCount; $i1++) {
            if (this.mSpans[$i1].getStartLine(Integer.MIN_VALUE) != $i0) {
                return false;
            }
        }
        return true;
    }

    private int getMaxEnd(int $i0) throws  {
        int $i1 = this.mSpans[0].getEndLine($i0);
        for (int $i2 = 1; $i2 < this.mSpanCount; $i2++) {
            int $i3 = this.mSpans[$i2].getEndLine($i0);
            if ($i3 > $i1) {
                $i1 = $i3;
            }
        }
        return $i1;
    }

    private int getMinEnd(int $i0) throws  {
        int $i1 = this.mSpans[0].getEndLine($i0);
        for (int $i2 = 1; $i2 < this.mSpanCount; $i2++) {
            int $i3 = this.mSpans[$i2].getEndLine($i0);
            if ($i3 < $i1) {
                $i1 = $i3;
            }
        }
        return $i1;
    }

    private void recycleFromStart(Recycler $r1, int $i0) throws  {
        while (getChildCount() > 0) {
            View $r2 = getChildAt(0);
            if (this.mPrimaryOrientation.getDecoratedEnd($r2) <= $i0) {
                LayoutParams $r5 = (LayoutParams) $r2.getLayoutParams();
                if ($r5.mFullSpan) {
                    int $i1 = 0;
                    while ($i1 < this.mSpanCount) {
                        if (this.mSpans[$i1].mViews.size() != 1) {
                            $i1++;
                        } else {
                            return;
                        }
                    }
                    for ($i1 = 0; $i1 < this.mSpanCount; $i1++) {
                        this.mSpans[$i1].popStart();
                    }
                } else if ($r5.mSpan.mViews.size() != 1) {
                    $r5.mSpan.popStart();
                } else {
                    return;
                }
                removeAndRecycleView($r2, $r1);
            } else {
                return;
            }
        }
    }

    private void recycleFromEnd(Recycler $r1, int $i0) throws  {
        int $i1 = getChildCount() - 1;
        while ($i1 >= 0) {
            View $r2 = getChildAt($i1);
            if (this.mPrimaryOrientation.getDecoratedStart($r2) >= $i0) {
                LayoutParams $r5 = (LayoutParams) $r2.getLayoutParams();
                if ($r5.mFullSpan) {
                    int $i2 = 0;
                    while ($i2 < this.mSpanCount) {
                        if (this.mSpans[$i2].mViews.size() != 1) {
                            $i2++;
                        } else {
                            return;
                        }
                    }
                    for ($i2 = 0; $i2 < this.mSpanCount; $i2++) {
                        this.mSpans[$i2].popEnd();
                    }
                } else if ($r5.mSpan.mViews.size() != 1) {
                    $r5.mSpan.popEnd();
                } else {
                    return;
                }
                removeAndRecycleView($r2, $r1);
                $i1--;
            } else {
                return;
            }
        }
    }

    private boolean preferLastSpan(int $i0) throws  {
        boolean $z0;
        if (this.mOrientation == 0) {
            if ($i0 == -1) {
                $z0 = true;
            } else {
                $z0 = false;
            }
            if ($z0 != this.mShouldReverseLayout) {
                return true;
            }
            return false;
        }
        boolean $z1;
        if ($i0 == -1) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        if ($z0 == this.mShouldReverseLayout) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        return $z1 == isLayoutRTL();
    }

    private Span getNextSpan(LayoutState $r1) throws  {
        int $i1;
        int $i0;
        byte $b2;
        if (preferLastSpan($r1.mLayoutDirection)) {
            $i1 = this.mSpanCount - 1;
            $i0 = -1;
            $b2 = (byte) -1;
        } else {
            $i1 = 0;
            $i0 = this.mSpanCount;
            $b2 = (byte) 1;
        }
        Span $r2;
        int $i3;
        int $i4;
        Span $r5;
        int $i5;
        if ($r1.mLayoutDirection == 1) {
            $r2 = null;
            $i3 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            $i4 = this.mPrimaryOrientation.getStartAfterPadding();
            while ($i1 != $i0) {
                $r5 = this.mSpans[$i1];
                $i5 = $r5.getEndLine($i4);
                if ($i5 < $i3) {
                    $r2 = $r5;
                    $i3 = $i5;
                }
                $i1 += $b2;
            }
            return $r2;
        }
        $r2 = null;
        $i3 = Integer.MIN_VALUE;
        $i4 = this.mPrimaryOrientation.getEndAfterPadding();
        while ($i1 != $i0) {
            $r5 = this.mSpans[$i1];
            $i5 = $r5.getStartLine($i4);
            if ($i5 > $i3) {
                $r2 = $r5;
                $i3 = $i5;
            }
            $i1 += $b2;
        }
        return $r2;
    }

    public boolean canScrollVertically() throws  {
        return this.mOrientation == 1;
    }

    public boolean canScrollHorizontally() throws  {
        return this.mOrientation == 0;
    }

    public int scrollHorizontallyBy(int $i0, Recycler $r1, State $r2) throws  {
        return scrollBy($i0, $r1, $r2);
    }

    public int scrollVerticallyBy(int $i0, Recycler $r1, State $r2) throws  {
        return scrollBy($i0, $r1, $r2);
    }

    private int calculateScrollDirectionForPosition(int $i0) throws  {
        byte $b1 = (byte) -1;
        if (getChildCount() == 0) {
            return this.mShouldReverseLayout ? 1 : -1;
        } else {
            if (($i0 < getFirstChildPosition()) == this.mShouldReverseLayout) {
                $b1 = (byte) 1;
            }
            return $b1;
        }
    }

    public void smoothScrollToPosition(RecyclerView $r1, State state, int $i0) throws  {
        C02712 $r3 = new LinearSmoothScroller($r1.getContext()) {
            public PointF computeScrollVectorForPosition(int $i0) throws  {
                $i0 = StaggeredGridLayoutManager.this.calculateScrollDirectionForPosition($i0);
                if ($i0 == 0) {
                    return null;
                }
                if (StaggeredGridLayoutManager.this.mOrientation == 0) {
                    return new PointF((float) $i0, 0.0f);
                }
                return new PointF(0.0f, (float) $i0);
            }
        };
        $r3.setTargetPosition($i0);
        startSmoothScroll($r3);
    }

    public void scrollToPosition(int $i0) throws  {
        if (!(this.mPendingSavedState == null || this.mPendingSavedState.mAnchorPosition == $i0)) {
            this.mPendingSavedState.invalidateAnchorPositionInfo();
        }
        this.mPendingScrollPosition = $i0;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        requestLayout();
    }

    public void scrollToPositionWithOffset(int $i0, int $i1) throws  {
        if (this.mPendingSavedState != null) {
            this.mPendingSavedState.invalidateAnchorPositionInfo();
        }
        this.mPendingScrollPosition = $i0;
        this.mPendingScrollPositionOffset = $i1;
        requestLayout();
    }

    int scrollBy(int $i0, Recycler $r1, State $r2) throws  {
        byte $b1;
        int $i2;
        if ($i0 > 0) {
            $b1 = (byte) 1;
            $i2 = getLastChildPosition();
        } else {
            $b1 = (byte) -1;
            $i2 = getFirstChildPosition();
        }
        this.mLayoutState.mRecycle = true;
        updateLayoutState($i2, $r2);
        setLayoutStateDirection($b1);
        this.mLayoutState.mCurrentPosition = this.mLayoutState.mItemDirection + $i2;
        $i2 = Math.abs($i0);
        this.mLayoutState.mAvailable = $i2;
        int $i3 = fill($r1, this.mLayoutState, $r2);
        if ($i2 >= $i3) {
            if ($i0 < 0) {
                $i0 = -$i3;
            } else {
                $i0 = $i3;
            }
        }
        this.mPrimaryOrientation.offsetChildren(-$i0);
        this.mLastLayoutFromEnd = this.mShouldReverseLayout;
        return $i0;
    }

    private int getLastChildPosition() throws  {
        int $i0 = getChildCount();
        if ($i0 == 0) {
            return 0;
        }
        return getPosition(getChildAt($i0 - 1));
    }

    private int getFirstChildPosition() throws  {
        if (getChildCount() == 0) {
            return 0;
        }
        return getPosition(getChildAt(0));
    }

    private int findFirstReferenceChildPosition(int $i0) throws  {
        int $i1 = getChildCount();
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            int $i3 = getPosition(getChildAt($i2));
            if ($i3 >= 0 && $i3 < $i0) {
                return $i3;
            }
        }
        return 0;
    }

    private int findLastReferenceChildPosition(int $i0) throws  {
        for (int $i1 = getChildCount() - 1; $i1 >= 0; $i1--) {
            int $i2 = getPosition(getChildAt($i1));
            if ($i2 >= 0 && $i2 < $i0) {
                return $i2;
            }
        }
        return 0;
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateDefaultLayoutParams() throws  {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -1);
        }
        return new LayoutParams(-1, -2);
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateLayoutParams(Context $r1, AttributeSet $r2) throws  {
        return new LayoutParams($r1, $r2);
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
        if ($r1 instanceof MarginLayoutParams) {
            return new LayoutParams((MarginLayoutParams) $r1);
        }
        return new LayoutParams($r1);
    }

    public boolean checkLayoutParams(android.support.v7.widget.RecyclerView.LayoutParams $r1) throws  {
        return $r1 instanceof LayoutParams;
    }

    public int getOrientation() throws  {
        return this.mOrientation;
    }

    @Nullable
    public View onFocusSearchFailed(View $r1, int $i0, Recycler $r2, State $r3) throws  {
        if (getChildCount() == 0) {
            return null;
        }
        $r1 = findContainingItemView($r1);
        if ($r1 == null) {
            return null;
        }
        resolveShouldLayoutReverse();
        $i0 = convertFocusDirectionToLayoutDirection($i0);
        if ($i0 == Integer.MIN_VALUE) {
            return null;
        }
        int $i1;
        View $r10;
        LayoutParams $r6 = (LayoutParams) $r1.getLayoutParams();
        boolean $z0 = $r6.mFullSpan;
        Span $r4 = $r6.mSpan;
        if ($i0 == 1) {
            $i1 = getLastChildPosition();
        } else {
            $i1 = getFirstChildPosition();
        }
        updateLayoutState($i1, $r3);
        setLayoutStateDirection($i0);
        this.mLayoutState.mCurrentPosition = this.mLayoutState.mItemDirection + $i1;
        this.mLayoutState.mAvailable = (int) (MAX_SCROLL_FACTOR * ((float) this.mPrimaryOrientation.getTotalSpace()));
        this.mLayoutState.mStopInFocusable = true;
        this.mLayoutState.mRecycle = false;
        fill($r2, this.mLayoutState, $r3);
        boolean z = this.mShouldReverseLayout;
        boolean $z1 = z;
        this.mLastLayoutFromEnd = z;
        if (!$z0) {
            $r10 = $r4.getFocusableViewAfter($i1, $i0);
            if (!($r10 == null || $r10 == $r1)) {
                return $r10;
            }
        }
        int $i2;
        if (!preferLastSpan($i0)) {
            $i2 = 0;
            while (true) {
                int $i3 = this.mSpanCount;
                if ($i2 >= $i3) {
                    break;
                }
                $r10 = this.mSpans[$i2].getFocusableViewAfter($i1, $i0);
                if ($r10 != null && $r10 != $r1) {
                    return $r10;
                }
                $i2++;
            }
        } else {
            for ($i2 = this.mSpanCount - 1; $i2 >= 0; $i2--) {
                $r10 = this.mSpans[$i2].getFocusableViewAfter($i1, $i0);
                if ($r10 != null && $r10 != $r1) {
                    return $r10;
                }
            }
        }
        return null;
    }

    private int convertFocusDirectionToLayoutDirection(int $i0) throws  {
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
}
