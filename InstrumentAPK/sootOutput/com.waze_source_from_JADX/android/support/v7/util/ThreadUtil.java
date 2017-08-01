package android.support.v7.util;

import android.support.v7.util.TileList.Tile;
import dalvik.annotation.Signature;

interface ThreadUtil<T> {

    public interface MainThreadCallback<T> {
        void addTile(@Signature({"(I", "Landroid/support/v7/util/TileList$Tile", "<TT;>;)V"}) int i, @Signature({"(I", "Landroid/support/v7/util/TileList$Tile", "<TT;>;)V"}) Tile<T> tile) throws ;

        void removeTile(int i, int i2) throws ;

        void updateItemCount(int i, int i2) throws ;
    }

    public interface BackgroundCallback<T> {
        void loadTile(int i, int i2) throws ;

        void recycleTile(@Signature({"(", "Landroid/support/v7/util/TileList$Tile", "<TT;>;)V"}) Tile<T> tile) throws ;

        void refresh(int i) throws ;

        void updateRange(int i, int i2, int i3, int i4, int i5) throws ;
    }

    BackgroundCallback<T> getBackgroundProxy(@Signature({"(", "Landroid/support/v7/util/ThreadUtil$BackgroundCallback", "<TT;>;)", "Landroid/support/v7/util/ThreadUtil$BackgroundCallback", "<TT;>;"}) BackgroundCallback<T> backgroundCallback) throws ;

    MainThreadCallback<T> getMainThreadProxy(@Signature({"(", "Landroid/support/v7/util/ThreadUtil$MainThreadCallback", "<TT;>;)", "Landroid/support/v7/util/ThreadUtil$MainThreadCallback", "<TT;>;"}) MainThreadCallback<T> mainThreadCallback) throws ;
}
