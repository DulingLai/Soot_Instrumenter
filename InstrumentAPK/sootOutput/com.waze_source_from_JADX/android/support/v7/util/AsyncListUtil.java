package android.support.v7.util;

import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.support.v7.util.ThreadUtil.BackgroundCallback;
import android.support.v7.util.ThreadUtil.MainThreadCallback;
import android.support.v7.util.TileList.Tile;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import dalvik.annotation.Signature;

public class AsyncListUtil<T> {
    private static final boolean DEBUG = false;
    private static final String TAG = "AsyncListUtil";
    private boolean mAllowScrollHints;
    private final BackgroundCallback<T> mBackgroundCallback = new C01972();
    final BackgroundCallback<T> mBackgroundProxy;
    final DataCallback<T> mDataCallback;
    int mDisplayedGeneration = 0;
    private int mItemCount = 0;
    private final MainThreadCallback<T> mMainThreadCallback = new C01961();
    final MainThreadCallback<T> mMainThreadProxy;
    private final SparseIntArray mMissingPositions = new SparseIntArray();
    final int[] mPrevRange = new int[2];
    int mRequestedGeneration = this.mDisplayedGeneration;
    private int mScrollHint = 0;
    final Class<T> mTClass;
    final TileList<T> mTileList;
    final int mTileSize;
    final int[] mTmpRange = new int[2];
    final int[] mTmpRangeExtended = new int[2];
    final ViewCallback mViewCallback;

    class C01961 implements MainThreadCallback<T> {
        C01961() throws  {
        }

        public void updateItemCount(int $i0, int $i1) throws  {
            if (isRequestedGeneration($i0)) {
                AsyncListUtil.this.mItemCount = $i1;
                AsyncListUtil.this.mViewCallback.onDataRefresh();
                AsyncListUtil.this.mDisplayedGeneration = AsyncListUtil.this.mRequestedGeneration;
                recycleAllTiles();
                AsyncListUtil.this.mAllowScrollHints = false;
                AsyncListUtil.this.updateRange();
            }
        }

        public void addTile(@Signature({"(I", "Landroid/support/v7/util/TileList$Tile", "<TT;>;)V"}) int $i0, @Signature({"(I", "Landroid/support/v7/util/TileList$Tile", "<TT;>;)V"}) Tile<T> $r1) throws  {
            if (isRequestedGeneration($i0)) {
                Tile $r5 = AsyncListUtil.this.mTileList.addOrReplace($r1);
                if ($r5 != null) {
                    Log.e(AsyncListUtil.TAG, "duplicate tile @" + $r5.mStartPosition);
                    AsyncListUtil.this.mBackgroundProxy.recycleTile($r5);
                }
                $i0 = $r1.mStartPosition + $r1.mItemCount;
                int $i1 = 0;
                while ($i1 < AsyncListUtil.this.mMissingPositions.size()) {
                    int $i2 = AsyncListUtil.this.mMissingPositions.keyAt($i1);
                    if ($r1.mStartPosition > $i2 || $i2 >= $i0) {
                        $i1++;
                    } else {
                        AsyncListUtil.this.mMissingPositions.removeAt($i1);
                        AsyncListUtil.this.mViewCallback.onItemLoaded($i2);
                    }
                }
                return;
            }
            AsyncListUtil.this.mBackgroundProxy.recycleTile($r1);
        }

        public void removeTile(int $i0, int $i1) throws  {
            if (isRequestedGeneration($i0)) {
                Tile $r3 = AsyncListUtil.this.mTileList.removeAtPos($i1);
                if ($r3 == null) {
                    Log.e(AsyncListUtil.TAG, "tile not found @" + $i1);
                    return;
                }
                AsyncListUtil.this.mBackgroundProxy.recycleTile($r3);
            }
        }

        private void recycleAllTiles() throws  {
            for (int $i0 = 0; $i0 < AsyncListUtil.this.mTileList.size(); $i0++) {
                AsyncListUtil.this.mBackgroundProxy.recycleTile(AsyncListUtil.this.mTileList.getAtIndex($i0));
            }
            AsyncListUtil.this.mTileList.clear();
        }

        private boolean isRequestedGeneration(int $i0) throws  {
            return $i0 == AsyncListUtil.this.mRequestedGeneration;
        }
    }

    class C01972 implements BackgroundCallback<T> {
        private int mFirstRequiredTileStart;
        private int mGeneration;
        private int mItemCount;
        private int mLastRequiredTileStart;
        final SparseBooleanArray mLoadedTiles = new SparseBooleanArray();
        private Tile<T> mRecycledRoot;

        C01972() throws  {
        }

        public void refresh(int $i0) throws  {
            this.mGeneration = $i0;
            this.mLoadedTiles.clear();
            this.mItemCount = AsyncListUtil.this.mDataCallback.refreshData();
            AsyncListUtil.this.mMainThreadProxy.updateItemCount(this.mGeneration, this.mItemCount);
        }

        public void updateRange(int $i0, int $i1, int $i2, int $i3, int $i4) throws  {
            if ($i0 <= $i1) {
                $i0 = getTileStart($i0);
                $i1 = getTileStart($i1);
                this.mFirstRequiredTileStart = getTileStart($i2);
                this.mLastRequiredTileStart = getTileStart($i3);
                if ($i4 == 1) {
                    requestTiles(this.mFirstRequiredTileStart, $i1, $i4, true);
                    requestTiles(AsyncListUtil.this.mTileSize + $i1, this.mLastRequiredTileStart, $i4, false);
                    return;
                }
                requestTiles($i0, this.mLastRequiredTileStart, $i4, false);
                requestTiles(this.mFirstRequiredTileStart, $i0 - AsyncListUtil.this.mTileSize, $i4, true);
            }
        }

        private int getTileStart(int $i0) throws  {
            return $i0 - ($i0 % AsyncListUtil.this.mTileSize);
        }

        private void requestTiles(int $i0, int $i1, int $i2, boolean $z0) throws  {
            int $i3 = $i0;
            while ($i3 <= $i1) {
                AsyncListUtil.this.mBackgroundProxy.loadTile($z0 ? ($i1 + $i0) - $i3 : $i3, $i2);
                $i3 += AsyncListUtil.this.mTileSize;
            }
        }

        public void loadTile(int $i0, int $i1) throws  {
            if (!isTileLoaded($i0)) {
                Tile $r1 = acquireTile();
                $r1.mStartPosition = $i0;
                $r1.mItemCount = Math.min(AsyncListUtil.this.mTileSize, this.mItemCount - $r1.mStartPosition);
                AsyncListUtil.this.mDataCallback.fillData($r1.mItems, $r1.mStartPosition, $r1.mItemCount);
                flushTileCache($i1);
                addTile($r1);
            }
        }

        public void recycleTile(@Signature({"(", "Landroid/support/v7/util/TileList$Tile", "<TT;>;)V"}) Tile<T> $r1) throws  {
            AsyncListUtil.this.mDataCallback.recycleData($r1.mItems, $r1.mItemCount);
            $r1.mNext = this.mRecycledRoot;
            this.mRecycledRoot = $r1;
        }

        private Tile<T> acquireTile() throws  {
            if (this.mRecycledRoot == null) {
                return new Tile(AsyncListUtil.this.mTClass, AsyncListUtil.this.mTileSize);
            }
            Tile $r1 = this.mRecycledRoot;
            this.mRecycledRoot = this.mRecycledRoot.mNext;
            return $r1;
        }

        private boolean isTileLoaded(int $i0) throws  {
            return this.mLoadedTiles.get($i0);
        }

        private void addTile(@Signature({"(", "Landroid/support/v7/util/TileList$Tile", "<TT;>;)V"}) Tile<T> $r1) throws  {
            this.mLoadedTiles.put($r1.mStartPosition, true);
            AsyncListUtil.this.mMainThreadProxy.addTile(this.mGeneration, $r1);
        }

        private void removeTile(int $i0) throws  {
            this.mLoadedTiles.delete($i0);
            AsyncListUtil.this.mMainThreadProxy.removeTile(this.mGeneration, $i0);
        }

        private void flushTileCache(int $i0) throws  {
            int $i3 = AsyncListUtil.this.mDataCallback.getMaxCachedTiles();
            while (this.mLoadedTiles.size() >= $i3) {
                int $i4 = this.mLoadedTiles.keyAt(0);
                int $i5 = this.mLoadedTiles.keyAt(this.mLoadedTiles.size() - 1);
                int $i2 = this.mFirstRequiredTileStart - $i4;
                int $i1 = $i5 - this.mLastRequiredTileStart;
                if ($i2 > 0 && ($i2 >= $i1 || $i0 == 2)) {
                    removeTile($i4);
                } else if ($i1 <= 0) {
                    return;
                } else {
                    if ($i2 < $i1 || $i0 == 1) {
                        removeTile($i5);
                    } else {
                        return;
                    }
                }
            }
        }

        private void log(String $r1, Object... $r2) throws  {
            Log.d(AsyncListUtil.TAG, "[BKGR] " + String.format($r1, $r2));
        }
    }

    public static abstract class DataCallback<T> {
        @WorkerThread
        public abstract void fillData(@Signature({"([TT;II)V"}) T[] tArr, @Signature({"([TT;II)V"}) int i, @Signature({"([TT;II)V"}) int i2) throws ;

        @WorkerThread
        public int getMaxCachedTiles() throws  {
            return 10;
        }

        @WorkerThread
        public abstract int refreshData() throws ;

        @WorkerThread
        public void recycleData(@Signature({"([TT;I)V"}) T[] tArr, @Signature({"([TT;I)V"}) int itemCount) throws  {
        }
    }

    public static abstract class ViewCallback {
        public static final int HINT_SCROLL_ASC = 2;
        public static final int HINT_SCROLL_DESC = 1;
        public static final int HINT_SCROLL_NONE = 0;

        @UiThread
        public abstract void getItemRangeInto(int[] iArr) throws ;

        @UiThread
        public abstract void onDataRefresh() throws ;

        @UiThread
        public abstract void onItemLoaded(int i) throws ;

        @UiThread
        public void extendRangeInto(int[] $r1, int[] $r2, int $i0) throws  {
            int $i4;
            int $i2 = ($r1[1] - $r1[0]) + 1;
            int $i1 = $i2 / 2;
            int $i3 = $r1[0];
            if ($i0 == 1) {
                $i4 = $i2;
            } else {
                $i4 = $i1;
            }
            $r2[0] = $i3 - $i4;
            $i3 = $r1[1];
            if ($i0 != 2) {
                $i2 = $i1;
            }
            $r2[1] = $i3 + $i2;
        }
    }

    private void log(String $r1, Object... $r2) throws  {
        Log.d(TAG, "[MAIN] " + String.format($r1, $r2));
    }

    public AsyncListUtil(@Signature({"(", "Ljava/lang/Class", "<TT;>;I", "Landroid/support/v7/util/AsyncListUtil$DataCallback", "<TT;>;", "Landroid/support/v7/util/AsyncListUtil$ViewCallback;", ")V"}) Class<T> $r1, @Signature({"(", "Ljava/lang/Class", "<TT;>;I", "Landroid/support/v7/util/AsyncListUtil$DataCallback", "<TT;>;", "Landroid/support/v7/util/AsyncListUtil$ViewCallback;", ")V"}) int $i0, @Signature({"(", "Ljava/lang/Class", "<TT;>;I", "Landroid/support/v7/util/AsyncListUtil$DataCallback", "<TT;>;", "Landroid/support/v7/util/AsyncListUtil$ViewCallback;", ")V"}) DataCallback<T> $r2, @Signature({"(", "Ljava/lang/Class", "<TT;>;I", "Landroid/support/v7/util/AsyncListUtil$DataCallback", "<TT;>;", "Landroid/support/v7/util/AsyncListUtil$ViewCallback;", ")V"}) ViewCallback $r3) throws  {
        this.mTClass = $r1;
        this.mTileSize = $i0;
        this.mDataCallback = $r2;
        this.mViewCallback = $r3;
        this.mTileList = new TileList(this.mTileSize);
        MessageThreadUtil $r4 = new MessageThreadUtil();
        this.mMainThreadProxy = $r4.getMainThreadProxy(this.mMainThreadCallback);
        this.mBackgroundProxy = $r4.getBackgroundProxy(this.mBackgroundCallback);
        refresh();
    }

    private boolean isRefreshPending() throws  {
        return this.mRequestedGeneration != this.mDisplayedGeneration;
    }

    public void onRangeChanged() throws  {
        if (!isRefreshPending()) {
            updateRange();
            this.mAllowScrollHints = true;
        }
    }

    public void refresh() throws  {
        this.mMissingPositions.clear();
        BackgroundCallback $r2 = this.mBackgroundProxy;
        int $i0 = this.mRequestedGeneration + 1;
        this.mRequestedGeneration = $i0;
        $r2.refresh($i0);
    }

    public T getItem(@Signature({"(I)TT;"}) int $i0) throws  {
        if ($i0 < 0 || $i0 >= this.mItemCount) {
            throw new IndexOutOfBoundsException($i0 + " is not within 0 and " + this.mItemCount);
        }
        Object $r5 = this.mTileList.getItemAt($i0);
        if ($r5 != null || isRefreshPending()) {
            return $r5;
        }
        this.mMissingPositions.put($i0, 0);
        return $r5;
    }

    public int getItemCount() throws  {
        return this.mItemCount;
    }

    private void updateRange() throws  {
        this.mViewCallback.getItemRangeInto(this.mTmpRange);
        if (this.mTmpRange[0] <= this.mTmpRange[1] && this.mTmpRange[0] >= 0 && this.mTmpRange[1] < this.mItemCount) {
            if (!this.mAllowScrollHints) {
                this.mScrollHint = 0;
            } else if (this.mTmpRange[0] > this.mPrevRange[1] || this.mPrevRange[0] > this.mTmpRange[1]) {
                this.mScrollHint = 0;
            } else if (this.mTmpRange[0] < this.mPrevRange[0]) {
                this.mScrollHint = 1;
            } else if (this.mTmpRange[0] > this.mPrevRange[0]) {
                this.mScrollHint = 2;
            }
            this.mPrevRange[0] = this.mTmpRange[0];
            this.mPrevRange[1] = this.mTmpRange[1];
            this.mViewCallback.extendRangeInto(this.mTmpRange, this.mTmpRangeExtended, this.mScrollHint);
            this.mTmpRangeExtended[0] = Math.min(this.mTmpRange[0], Math.max(this.mTmpRangeExtended[0], 0));
            this.mTmpRangeExtended[1] = Math.max(this.mTmpRange[1], Math.min(this.mTmpRangeExtended[1], this.mItemCount - 1));
            this.mBackgroundProxy.updateRange(this.mTmpRange[0], this.mTmpRange[1], this.mTmpRangeExtended[0], this.mTmpRangeExtended[1], this.mScrollHint);
        }
    }
}
