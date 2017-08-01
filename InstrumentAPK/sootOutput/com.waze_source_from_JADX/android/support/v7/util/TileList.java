package android.support.v7.util;

import android.util.SparseArray;
import dalvik.annotation.Signature;
import java.lang.reflect.Array;

class TileList<T> {
    Tile<T> mLastAccessedTile;
    final int mTileSize;
    private final SparseArray<Tile<T>> mTiles = new SparseArray(10);

    public static class Tile<T> {
        public int mItemCount;
        public final T[] mItems;
        Tile<T> mNext;
        public int mStartPosition;

        public Tile(@Signature({"(", "Ljava/lang/Class", "<TT;>;I)V"}) Class<T> $r1, @Signature({"(", "Ljava/lang/Class", "<TT;>;I)V"}) int $i0) throws  {
            this.mItems = (Object[]) Array.newInstance($r1, $i0);
        }

        boolean containsPosition(int $i0) throws  {
            return this.mStartPosition <= $i0 && $i0 < this.mStartPosition + this.mItemCount;
        }

        T getByPosition(@Signature({"(I)TT;"}) int $i0) throws  {
            return this.mItems[$i0 - this.mStartPosition];
        }
    }

    public TileList(int $i0) throws  {
        this.mTileSize = $i0;
    }

    public T getItemAt(@Signature({"(I)TT;"}) int $i0) throws  {
        if (this.mLastAccessedTile == null || !this.mLastAccessedTile.containsPosition($i0)) {
            int $i1 = this.mTiles.indexOfKey($i0 - ($i0 % this.mTileSize));
            if ($i1 < 0) {
                return null;
            }
            this.mLastAccessedTile = (Tile) this.mTiles.valueAt($i1);
        }
        return this.mLastAccessedTile.getByPosition($i0);
    }

    public int size() throws  {
        return this.mTiles.size();
    }

    public void clear() throws  {
        this.mTiles.clear();
    }

    public Tile<T> getAtIndex(@Signature({"(I)", "Landroid/support/v7/util/TileList$Tile", "<TT;>;"}) int $i0) throws  {
        return (Tile) this.mTiles.valueAt($i0);
    }

    public Tile<T> addOrReplace(@Signature({"(", "Landroid/support/v7/util/TileList$Tile", "<TT;>;)", "Landroid/support/v7/util/TileList$Tile", "<TT;>;"}) Tile<T> $r1) throws  {
        int $i0 = this.mTiles.indexOfKey($r1.mStartPosition);
        if ($i0 < 0) {
            this.mTiles.put($r1.mStartPosition, $r1);
            return null;
        }
        Tile $r3 = (Tile) this.mTiles.valueAt($i0);
        this.mTiles.setValueAt($i0, $r1);
        if (this.mLastAccessedTile != $r3) {
            return $r3;
        }
        this.mLastAccessedTile = $r1;
        return $r3;
    }

    public Tile<T> removeAtPos(@Signature({"(I)", "Landroid/support/v7/util/TileList$Tile", "<TT;>;"}) int $i0) throws  {
        Tile $r3 = (Tile) this.mTiles.get($i0);
        if (this.mLastAccessedTile == $r3) {
            this.mLastAccessedTile = null;
        }
        this.mTiles.delete($i0);
        return $r3;
    }
}
