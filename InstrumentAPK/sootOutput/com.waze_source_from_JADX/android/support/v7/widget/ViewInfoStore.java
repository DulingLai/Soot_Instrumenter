package android.support.v7.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SimplePool;
import android.support.v7.widget.RecyclerView.ItemAnimator.ItemHolderInfo;
import android.support.v7.widget.RecyclerView.ViewHolder;

class ViewInfoStore {
    private static final boolean DEBUG = false;
    @VisibleForTesting
    final ArrayMap<ViewHolder, InfoRecord> mLayoutHolderMap = new ArrayMap();
    @VisibleForTesting
    final LongSparseArray<ViewHolder> mOldChangedHolders = new LongSparseArray();

    interface ProcessCallback {
        void processAppeared(ViewHolder viewHolder, @Nullable ItemHolderInfo itemHolderInfo, ItemHolderInfo itemHolderInfo2) throws ;

        void processDisappeared(ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @Nullable ItemHolderInfo itemHolderInfo2) throws ;

        void processPersistent(ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2) throws ;

        void unused(ViewHolder viewHolder) throws ;
    }

    static class InfoRecord {
        static final int FLAG_APPEAR = 2;
        static final int FLAG_APPEAR_AND_DISAPPEAR = 3;
        static final int FLAG_APPEAR_PRE_AND_POST = 14;
        static final int FLAG_DISAPPEARED = 1;
        static final int FLAG_POST = 8;
        static final int FLAG_PRE = 4;
        static final int FLAG_PRE_AND_POST = 12;
        static Pool<InfoRecord> sPool = new SimplePool(20);
        int flags;
        @Nullable
        ItemHolderInfo postInfo;
        @Nullable
        ItemHolderInfo preInfo;

        private InfoRecord() throws  {
        }

        static InfoRecord obtain() throws  {
            InfoRecord $r2 = (InfoRecord) sPool.acquire();
            return $r2 == null ? new InfoRecord() : $r2;
        }

        static void recycle(InfoRecord $r0) throws  {
            $r0.flags = 0;
            $r0.preInfo = null;
            $r0.postInfo = null;
            sPool.release($r0);
        }

        static void drainCache() throws  {
            do {
            } while (sPool.acquire() != null);
        }
    }

    private android.support.v7.widget.RecyclerView.ItemAnimator.ItemHolderInfo popFromLayoutStep(android.support.v7.widget.RecyclerView.ViewHolder r1, int r2) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v7.widget.ViewInfoStore.popFromLayoutStep(android.support.v7.widget.RecyclerView$ViewHolder, int):android.support.v7.widget.RecyclerView$ItemAnimator$ItemHolderInfo
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
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ViewInfoStore.popFromLayoutStep(android.support.v7.widget.RecyclerView$ViewHolder, int):android.support.v7.widget.RecyclerView$ItemAnimator$ItemHolderInfo");
    }

    ViewInfoStore() throws  {
    }

    void clear() throws  {
        this.mLayoutHolderMap.clear();
        this.mOldChangedHolders.clear();
    }

    void addToPreLayout(ViewHolder $r1, ItemHolderInfo $r2) throws  {
        InfoRecord $r5 = (InfoRecord) this.mLayoutHolderMap.get($r1);
        if ($r5 == null) {
            InfoRecord $r6 = InfoRecord.obtain();
            $r5 = $r6;
            this.mLayoutHolderMap.put($r1, $r6);
        }
        $r5.preInfo = $r2;
        $r5.flags |= 4;
    }

    boolean isDisappearing(ViewHolder $r1) throws  {
        InfoRecord $r4 = (InfoRecord) this.mLayoutHolderMap.get($r1);
        return ($r4 == null || ($r4.flags & 1) == 0) ? false : true;
    }

    @Nullable
    ItemHolderInfo popFromPreLayout(ViewHolder $r1) throws  {
        return popFromLayoutStep($r1, 4);
    }

    @Nullable
    ItemHolderInfo popFromPostLayout(ViewHolder $r1) throws  {
        return popFromLayoutStep($r1, 8);
    }

    void addToOldChangeHolders(long $l0, ViewHolder $r1) throws  {
        this.mOldChangedHolders.put($l0, $r1);
    }

    void addToAppearedInPreLayoutHolders(ViewHolder $r1, ItemHolderInfo $r2) throws  {
        InfoRecord $r5 = (InfoRecord) this.mLayoutHolderMap.get($r1);
        if ($r5 == null) {
            InfoRecord $r6 = InfoRecord.obtain();
            $r5 = $r6;
            this.mLayoutHolderMap.put($r1, $r6);
        }
        $r5.flags |= 2;
        $r5.preInfo = $r2;
    }

    boolean isInPreLayout(ViewHolder $r1) throws  {
        InfoRecord $r4 = (InfoRecord) this.mLayoutHolderMap.get($r1);
        return ($r4 == null || ($r4.flags & 4) == 0) ? false : true;
    }

    ViewHolder getFromOldChangeHolders(long $l0) throws  {
        return (ViewHolder) this.mOldChangedHolders.get($l0);
    }

    void addToPostLayout(ViewHolder $r1, ItemHolderInfo $r2) throws  {
        InfoRecord $r5 = (InfoRecord) this.mLayoutHolderMap.get($r1);
        if ($r5 == null) {
            InfoRecord $r6 = InfoRecord.obtain();
            $r5 = $r6;
            this.mLayoutHolderMap.put($r1, $r6);
        }
        $r5.postInfo = $r2;
        $r5.flags |= 8;
    }

    void addToDisappearedInLayout(ViewHolder $r1) throws  {
        InfoRecord $r4 = (InfoRecord) this.mLayoutHolderMap.get($r1);
        if ($r4 == null) {
            InfoRecord $r5 = InfoRecord.obtain();
            $r4 = $r5;
            this.mLayoutHolderMap.put($r1, $r5);
        }
        $r4.flags |= 1;
    }

    void removeFromDisappearedInLayout(ViewHolder $r1) throws  {
        InfoRecord $r4 = (InfoRecord) this.mLayoutHolderMap.get($r1);
        if ($r4 != null) {
            $r4.flags &= -2;
        }
    }

    void process(ProcessCallback $r1) throws  {
        for (int $i0 = this.mLayoutHolderMap.size() - 1; $i0 >= 0; $i0--) {
            ViewHolder $r4 = (ViewHolder) this.mLayoutHolderMap.keyAt($i0);
            InfoRecord $r5 = (InfoRecord) this.mLayoutHolderMap.removeAt($i0);
            if (($r5.flags & 3) == 3) {
                $r1.unused($r4);
            } else if (($r5.flags & 1) != 0) {
                if ($r5.preInfo == null) {
                    $r1.unused($r4);
                } else {
                    $r1.processDisappeared($r4, $r5.preInfo, $r5.postInfo);
                }
            } else if (($r5.flags & 14) == 14) {
                $r1.processAppeared($r4, $r5.preInfo, $r5.postInfo);
            } else if (($r5.flags & 12) == 12) {
                $r1.processPersistent($r4, $r5.preInfo, $r5.postInfo);
            } else if (($r5.flags & 4) != 0) {
                $r1.processDisappeared($r4, $r5.preInfo, null);
            } else if (($r5.flags & 8) != 0) {
                $r1.processAppeared($r4, $r5.preInfo, $r5.postInfo);
            } else if (($r5.flags & 2) != 0) {
            }
            InfoRecord.recycle($r5);
        }
    }

    void removeViewHolder(ViewHolder $r1) throws  {
        for (int $i0 = this.mOldChangedHolders.size() - 1; $i0 >= 0; $i0--) {
            if ($r1 == this.mOldChangedHolders.valueAt($i0)) {
                this.mOldChangedHolders.removeAt($i0);
                break;
            }
        }
        InfoRecord $r5 = (InfoRecord) this.mLayoutHolderMap.remove($r1);
        if ($r5 != null) {
            InfoRecord.recycle($r5);
        }
    }

    void onDetach() throws  {
        InfoRecord.drainCache();
    }

    public void onViewDetached(ViewHolder $r1) throws  {
        removeFromDisappearedInLayout($r1);
    }
}
