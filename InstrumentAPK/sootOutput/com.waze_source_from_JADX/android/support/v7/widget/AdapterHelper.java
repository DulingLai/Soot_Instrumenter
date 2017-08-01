package android.support.v7.widget;

import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SimplePool;
import android.support.v7.widget.RecyclerView.ViewHolder;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class AdapterHelper implements Callback {
    private static final boolean DEBUG = false;
    static final int POSITION_TYPE_INVISIBLE = 0;
    static final int POSITION_TYPE_NEW_OR_LAID_OUT = 1;
    private static final String TAG = "AHT";
    final Callback mCallback;
    final boolean mDisableRecycler;
    private int mExistingUpdateTypes;
    Runnable mOnItemProcessedCallback;
    final OpReorderer mOpReorderer;
    final ArrayList<UpdateOp> mPendingUpdates;
    final ArrayList<UpdateOp> mPostponedList;
    private Pool<UpdateOp> mUpdateOpPool;

    interface Callback {
        ViewHolder findViewHolder(int i) throws ;

        void markViewHoldersUpdated(int i, int i2, Object obj) throws ;

        void offsetPositionsForAdd(int i, int i2) throws ;

        void offsetPositionsForMove(int i, int i2) throws ;

        void offsetPositionsForRemovingInvisible(int i, int i2) throws ;

        void offsetPositionsForRemovingLaidOutOrNewView(int i, int i2) throws ;

        void onDispatchFirstPass(UpdateOp updateOp) throws ;

        void onDispatchSecondPass(UpdateOp updateOp) throws ;
    }

    static class UpdateOp {
        static final int ADD = 1;
        static final int MOVE = 8;
        static final int POOL_SIZE = 30;
        static final int REMOVE = 2;
        static final int UPDATE = 4;
        int cmd;
        int itemCount;
        Object payload;
        int positionStart;

        UpdateOp(int $i0, int $i1, int $i2, Object $r1) throws  {
            this.cmd = $i0;
            this.positionStart = $i1;
            this.itemCount = $i2;
            this.payload = $r1;
        }

        String cmdToString() throws  {
            switch (this.cmd) {
                case 1:
                    return "add";
                case 2:
                    return "rm";
                case 3:
                case 5:
                case 6:
                case 7:
                    break;
                case 4:
                    return "up";
                case 8:
                    return "mv";
                default:
                    break;
            }
            return "??";
        }

        public String toString() throws  {
            return Integer.toHexString(System.identityHashCode(this)) + "[" + cmdToString() + ",s:" + this.positionStart + "c:" + this.itemCount + ",p:" + this.payload + "]";
        }

        public boolean equals(Object $r1) throws  {
            if (this == $r1) {
                return true;
            }
            if ($r1 == null || getClass() != $r1.getClass()) {
                return false;
            }
            UpdateOp $r4 = (UpdateOp) $r1;
            if (this.cmd != $r4.cmd) {
                return false;
            }
            if (this.cmd == 8 && Math.abs(this.itemCount - this.positionStart) == 1 && this.itemCount == $r4.positionStart && this.positionStart == $r4.itemCount) {
                return true;
            }
            if (this.itemCount != $r4.itemCount) {
                return false;
            }
            if (this.positionStart != $r4.positionStart) {
                return false;
            }
            if (this.payload == null) {
                return $r4.payload == null;
            } else {
                if (this.payload.equals($r4.payload)) {
                    return true;
                }
                return false;
            }
        }

        public int hashCode() throws  {
            return (((this.cmd * 31) + this.positionStart) * 31) + this.itemCount;
        }
    }

    AdapterHelper(Callback $r1) throws  {
        this($r1, false);
    }

    AdapterHelper(Callback $r1, boolean $z0) throws  {
        this.mUpdateOpPool = new SimplePool(30);
        this.mPendingUpdates = new ArrayList();
        this.mPostponedList = new ArrayList();
        this.mExistingUpdateTypes = 0;
        this.mCallback = $r1;
        this.mDisableRecycler = $z0;
        this.mOpReorderer = new OpReorderer(this);
    }

    AdapterHelper addUpdateOp(UpdateOp... $r1) throws  {
        Collections.addAll(this.mPendingUpdates, $r1);
        return this;
    }

    void reset() throws  {
        recycleUpdateOpsAndClearList(this.mPendingUpdates);
        recycleUpdateOpsAndClearList(this.mPostponedList);
        this.mExistingUpdateTypes = 0;
    }

    void preProcess() throws  {
        this.mOpReorderer.reorderOps(this.mPendingUpdates);
        int $i0 = this.mPendingUpdates.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            UpdateOp $r4 = (UpdateOp) this.mPendingUpdates.get($i1);
            switch ($r4.cmd) {
                case 1:
                    applyAdd($r4);
                    break;
                case 2:
                    applyRemove($r4);
                    break;
                case 3:
                case 5:
                case 6:
                case 7:
                    break;
                case 4:
                    applyUpdate($r4);
                    break;
                case 8:
                    applyMove($r4);
                    break;
                default:
                    break;
            }
            if (this.mOnItemProcessedCallback != null) {
                this.mOnItemProcessedCallback.run();
            }
        }
        this.mPendingUpdates.clear();
    }

    void consumePostponedUpdates() throws  {
        int $i0 = this.mPostponedList.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            this.mCallback.onDispatchSecondPass((UpdateOp) this.mPostponedList.get($i1));
        }
        recycleUpdateOpsAndClearList(this.mPostponedList);
        this.mExistingUpdateTypes = 0;
    }

    private void applyMove(UpdateOp $r1) throws  {
        postponeAndUpdateViewHolders($r1);
    }

    private void applyRemove(UpdateOp $r1) throws  {
        int $i0 = $r1.positionStart;
        int $i2 = 0;
        int $i1 = $r1.positionStart + $r1.itemCount;
        byte $b4 = (byte) -1;
        int $i3 = $r1.positionStart;
        while ($i3 < $i1) {
            boolean $z0 = false;
            if (this.mCallback.findViewHolder($i3) != null || canFindInPreLayout($i3)) {
                if ($b4 == (byte) 0) {
                    dispatchAndUpdateViewHolders(obtainUpdateOp(2, $i0, $i2, null));
                    $z0 = true;
                }
                $b4 = (byte) 1;
            } else {
                if ($b4 == (byte) 1) {
                    postponeAndUpdateViewHolders(obtainUpdateOp(2, $i0, $i2, null));
                    $z0 = true;
                }
                $b4 = (byte) 0;
            }
            if ($z0) {
                $i3 -= $i2;
                $i1 -= $i2;
                $i2 = 1;
            } else {
                $i2++;
            }
            $i3++;
        }
        if ($i2 != $r1.itemCount) {
            recycleUpdateOp($r1);
            $r1 = obtainUpdateOp(2, $i0, $i2, null);
        }
        if ($b4 == (byte) 0) {
            dispatchAndUpdateViewHolders($r1);
        } else {
            postponeAndUpdateViewHolders($r1);
        }
    }

    private void applyUpdate(UpdateOp $r2) throws  {
        int $i1 = $r2.positionStart;
        int $i2 = 0;
        int $i0 = $r2.positionStart + $r2.itemCount;
        byte $b4 = (byte) -1;
        int $i3 = $r2.positionStart;
        while ($i3 < $i0) {
            if (this.mCallback.findViewHolder($i3) != null || canFindInPreLayout($i3)) {
                if ($b4 == (byte) 0) {
                    dispatchAndUpdateViewHolders(obtainUpdateOp(4, $i1, $i2, $r2.payload));
                    $i2 = 0;
                    $i1 = $i3;
                }
                $b4 = (byte) 1;
            } else {
                if ($b4 == (byte) 1) {
                    postponeAndUpdateViewHolders(obtainUpdateOp(4, $i1, $i2, $r2.payload));
                    $i2 = 0;
                    $i1 = $i3;
                }
                $b4 = (byte) 0;
            }
            $i2++;
            $i3++;
        }
        if ($i2 != $r2.itemCount) {
            Object $r1 = $r2.payload;
            recycleUpdateOp($r2);
            $r2 = obtainUpdateOp(4, $i1, $i2, $r1);
        }
        if ($b4 == (byte) 0) {
            dispatchAndUpdateViewHolders($r2);
        } else {
            postponeAndUpdateViewHolders($r2);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void dispatchAndUpdateViewHolders(android.support.v7.widget.AdapterHelper.UpdateOp r18) throws  {
        /*
        r17 = this;
        r0 = r18;
        r2 = r0.cmd;
        r3 = 1;
        if (r2 == r3) goto L_0x000f;
    L_0x0007:
        r0 = r18;
        r2 = r0.cmd;
        r3 = 8;
        if (r2 != r3) goto L_0x0017;
    L_0x000f:
        r4 = new java.lang.IllegalArgumentException;
        r5 = "should not dispatch add or move for pre layout";
        r4.<init>(r5);
        throw r4;
    L_0x0017:
        r0 = r18;
        r2 = r0.positionStart;
        r0 = r18;
        r6 = r0.cmd;
        r0 = r17;
        r7 = r0.updatePositionWithPostponed(r2, r6);
        r2 = 1;
        r0 = r18;
        r6 = r0.positionStart;
        r0 = r18;
        r8 = r0.cmd;
        switch(r8) {
            case 2: goto L_0x0076;
            case 3: goto L_0x0032;
            case 4: goto L_0x004d;
            default: goto L_0x0031;
        };
    L_0x0031:
        goto L_0x0032;
    L_0x0032:
        r4 = new java.lang.IllegalArgumentException;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r5 = "op should be remove or update.";
        r9 = r9.append(r5);
        r0 = r18;
        r9 = r9.append(r0);
        r10 = r9.toString();
        r4.<init>(r10);
        throw r4;
    L_0x004d:
        r11 = 1;
    L_0x004e:
        r8 = 1;
    L_0x004f:
        r0 = r18;
        r12 = r0.itemCount;
        if (r8 >= r12) goto L_0x00ad;
    L_0x0055:
        r0 = r18;
        r12 = r0.positionStart;
        r13 = r11 * r8;
        r12 = r12 + r13;
        r0 = r18;
        r13 = r0.cmd;
        r0 = r17;
        r12 = r0.updatePositionWithPostponed(r12, r13);
        r14 = 0;
        r0 = r18;
        r13 = r0.cmd;
        switch(r13) {
            case 2: goto L_0x0080;
            case 3: goto L_0x006f;
            case 4: goto L_0x0078;
            default: goto L_0x006e;
        };
    L_0x006e:
        goto L_0x006f;
    L_0x006f:
        if (r14 == 0) goto L_0x0086;
    L_0x0071:
        r2 = r2 + 1;
    L_0x0073:
        r8 = r8 + 1;
        goto L_0x004f;
    L_0x0076:
        r11 = 0;
        goto L_0x004e;
    L_0x0078:
        r13 = r7 + 1;
        if (r12 != r13) goto L_0x007e;
    L_0x007c:
        r14 = 1;
    L_0x007d:
        goto L_0x006f;
    L_0x007e:
        r14 = 0;
        goto L_0x007d;
    L_0x0080:
        if (r12 != r7) goto L_0x0084;
    L_0x0082:
        r14 = 1;
    L_0x0083:
        goto L_0x006f;
    L_0x0084:
        r14 = 0;
        goto L_0x0083;
    L_0x0086:
        r0 = r18;
        r13 = r0.cmd;
        r0 = r18;
        r15 = r0.payload;
        r0 = r17;
        r16 = r0.obtainUpdateOp(r13, r7, r2, r15);
        r0 = r17;
        r1 = r16;
        r0.dispatchFirstPassAndUpdateViewHolders(r1, r6);
        r0 = r17;
        r1 = r16;
        r0.recycleUpdateOp(r1);
        r0 = r18;
        r7 = r0.cmd;
        r3 = 4;
        if (r7 != r3) goto L_0x00aa;
    L_0x00a9:
        r6 = r6 + r2;
    L_0x00aa:
        r7 = r12;
        r2 = 1;
        goto L_0x0073;
    L_0x00ad:
        r0 = r18;
        r15 = r0.payload;
        r0 = r17;
        r1 = r18;
        r0.recycleUpdateOp(r1);
        if (r2 <= 0) goto L_0x00d3;
    L_0x00ba:
        r0 = r18;
        r8 = r0.cmd;
        r0 = r17;
        r18 = r0.obtainUpdateOp(r8, r7, r2, r15);
        r0 = r17;
        r1 = r18;
        r0.dispatchFirstPassAndUpdateViewHolders(r1, r6);
        r0 = r17;
        r1 = r18;
        r0.recycleUpdateOp(r1);
        return;
    L_0x00d3:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.AdapterHelper.dispatchAndUpdateViewHolders(android.support.v7.widget.AdapterHelper$UpdateOp):void");
    }

    void dispatchFirstPassAndUpdateViewHolders(UpdateOp $r1, int $i0) throws  {
        this.mCallback.onDispatchFirstPass($r1);
        switch ($r1.cmd) {
            case 2:
                this.mCallback.offsetPositionsForRemovingInvisible($i0, $r1.itemCount);
                return;
            case 3:
                break;
            case 4:
                this.mCallback.markViewHoldersUpdated($i0, $r1.itemCount, $r1.payload);
                return;
            default:
                break;
        }
        throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
    }

    private int updatePositionWithPostponed(int $i1, int $i0) throws  {
        for (int $i2 = this.mPostponedList.size() - 1; $i2 >= 0; $i2--) {
            UpdateOp $r3 = (UpdateOp) this.mPostponedList.get($i2);
            if ($r3.cmd == 8) {
                int $i3;
                int $i4;
                if ($r3.positionStart < $r3.itemCount) {
                    $i3 = $r3.positionStart;
                    $i4 = $r3.itemCount;
                } else {
                    $i3 = $r3.itemCount;
                    $i4 = $r3.positionStart;
                }
                if ($i1 < $i3 || $i1 > $i4) {
                    if ($i1 < $r3.positionStart) {
                        if ($i0 == 1) {
                            $r3.positionStart++;
                            $r3.itemCount++;
                        } else if ($i0 == 2) {
                            $r3.positionStart--;
                            $r3.itemCount--;
                        }
                    }
                } else if ($i3 == $r3.positionStart) {
                    if ($i0 == 1) {
                        $r3.itemCount++;
                    } else if ($i0 == 2) {
                        $r3.itemCount--;
                    }
                    $i1++;
                } else {
                    if ($i0 == 1) {
                        $r3.positionStart++;
                    } else if ($i0 == 2) {
                        $r3.positionStart--;
                    }
                    $i1--;
                }
            } else if ($r3.positionStart <= $i1) {
                if ($r3.cmd == 1) {
                    $i1 -= $r3.itemCount;
                } else if ($r3.cmd == 2) {
                    $i1 += $r3.itemCount;
                }
            } else if ($i0 == 1) {
                $r3.positionStart++;
            } else if ($i0 == 2) {
                $r3.positionStart--;
            }
        }
        for ($i0 = this.mPostponedList.size() - 1; $i0 >= 0; $i0--) {
            $r3 = (UpdateOp) this.mPostponedList.get($i0);
            if ($r3.cmd == 8) {
                if ($r3.itemCount == $r3.positionStart || $r3.itemCount < 0) {
                    this.mPostponedList.remove($i0);
                    recycleUpdateOp($r3);
                }
            } else if ($r3.itemCount <= 0) {
                this.mPostponedList.remove($i0);
                recycleUpdateOp($r3);
            }
        }
        return $i1;
    }

    private boolean canFindInPreLayout(int $i0) throws  {
        int $i2 = this.mPostponedList.size();
        for (int $i3 = 0; $i3 < $i2; $i3++) {
            UpdateOp $r3 = (UpdateOp) this.mPostponedList.get($i3);
            if ($r3.cmd == 8) {
                if (findPositionOffset($r3.itemCount, $i3 + 1) == $i0) {
                    return true;
                }
            } else if ($r3.cmd == 1) {
                int $i1 = $r3.positionStart + $r3.itemCount;
                for (int $i4 = $r3.positionStart; $i4 < $i1; $i4++) {
                    if (findPositionOffset($i4, $i3 + 1) == $i0) {
                        return true;
                    }
                }
                continue;
            } else {
                continue;
            }
        }
        return false;
    }

    private void applyAdd(UpdateOp $r1) throws  {
        postponeAndUpdateViewHolders($r1);
    }

    private void postponeAndUpdateViewHolders(UpdateOp $r1) throws  {
        this.mPostponedList.add($r1);
        switch ($r1.cmd) {
            case 1:
                this.mCallback.offsetPositionsForAdd($r1.positionStart, $r1.itemCount);
                return;
            case 2:
                this.mCallback.offsetPositionsForRemovingLaidOutOrNewView($r1.positionStart, $r1.itemCount);
                return;
            case 3:
            case 5:
            case 6:
            case 7:
                break;
            case 4:
                this.mCallback.markViewHoldersUpdated($r1.positionStart, $r1.itemCount, $r1.payload);
                return;
            case 8:
                this.mCallback.offsetPositionsForMove($r1.positionStart, $r1.itemCount);
                return;
            default:
                break;
        }
        throw new IllegalArgumentException("Unknown update op type for " + $r1);
    }

    boolean hasPendingUpdates() throws  {
        return this.mPendingUpdates.size() > 0;
    }

    boolean hasAnyUpdateTypes(int $i0) throws  {
        return (this.mExistingUpdateTypes & $i0) != 0;
    }

    int findPositionOffset(int $i0) throws  {
        return findPositionOffset($i0, 0);
    }

    int findPositionOffset(int $i1, int $i0) throws  {
        int $i2 = this.mPostponedList.size();
        while ($i0 < $i2) {
            UpdateOp $r3 = (UpdateOp) this.mPostponedList.get($i0);
            if ($r3.cmd == 8) {
                if ($r3.positionStart == $i1) {
                    $i1 = $r3.itemCount;
                } else {
                    if ($r3.positionStart < $i1) {
                        $i1--;
                    }
                    if ($r3.itemCount <= $i1) {
                        $i1++;
                    }
                }
            } else if ($r3.positionStart > $i1) {
                continue;
            } else if ($r3.cmd == 2) {
                if ($i1 < $r3.positionStart + $r3.itemCount) {
                    return -1;
                }
                $i1 -= $r3.itemCount;
            } else if ($r3.cmd == 1) {
                $i1 += $r3.itemCount;
            }
            $i0++;
        }
        return $i1;
    }

    boolean onItemRangeChanged(int $i0, int $i1, Object $r1) throws  {
        this.mPendingUpdates.add(obtainUpdateOp(4, $i0, $i1, $r1));
        this.mExistingUpdateTypes |= 4;
        return this.mPendingUpdates.size() == 1;
    }

    boolean onItemRangeInserted(int $i0, int $i1) throws  {
        this.mPendingUpdates.add(obtainUpdateOp(1, $i0, $i1, null));
        this.mExistingUpdateTypes |= 1;
        return this.mPendingUpdates.size() == 1;
    }

    boolean onItemRangeRemoved(int $i0, int $i1) throws  {
        this.mPendingUpdates.add(obtainUpdateOp(2, $i0, $i1, null));
        this.mExistingUpdateTypes |= 2;
        return this.mPendingUpdates.size() == 1;
    }

    boolean onItemRangeMoved(int $i0, int $i1, int $i2) throws  {
        boolean $z0 = true;
        if ($i0 == $i1) {
            return false;
        }
        if ($i2 != 1) {
            throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
        }
        this.mPendingUpdates.add(obtainUpdateOp(8, $i0, $i1, null));
        this.mExistingUpdateTypes |= 8;
        if (this.mPendingUpdates.size() != 1) {
            $z0 = false;
        }
        return $z0;
    }

    void consumeUpdatesInOnePass() throws  {
        consumePostponedUpdates();
        int $i0 = this.mPendingUpdates.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            UpdateOp $r3 = (UpdateOp) this.mPendingUpdates.get($i1);
            switch ($r3.cmd) {
                case 1:
                    this.mCallback.onDispatchSecondPass($r3);
                    this.mCallback.offsetPositionsForAdd($r3.positionStart, $r3.itemCount);
                    break;
                case 2:
                    this.mCallback.onDispatchSecondPass($r3);
                    this.mCallback.offsetPositionsForRemovingInvisible($r3.positionStart, $r3.itemCount);
                    break;
                case 3:
                case 5:
                case 6:
                case 7:
                    break;
                case 4:
                    this.mCallback.onDispatchSecondPass($r3);
                    this.mCallback.markViewHoldersUpdated($r3.positionStart, $r3.itemCount, $r3.payload);
                    break;
                case 8:
                    this.mCallback.onDispatchSecondPass($r3);
                    this.mCallback.offsetPositionsForMove($r3.positionStart, $r3.itemCount);
                    break;
                default:
                    break;
            }
            if (this.mOnItemProcessedCallback != null) {
                this.mOnItemProcessedCallback.run();
            }
        }
        recycleUpdateOpsAndClearList(this.mPendingUpdates);
        this.mExistingUpdateTypes = 0;
    }

    public int applyPendingUpdatesToPosition(int $i1) throws  {
        int $i2 = this.mPendingUpdates.size();
        for (int $i3 = 0; $i3 < $i2; $i3++) {
            UpdateOp $r3 = (UpdateOp) this.mPendingUpdates.get($i3);
            switch ($r3.cmd) {
                case 1:
                    if ($r3.positionStart > $i1) {
                        break;
                    }
                    $i1 += $r3.itemCount;
                    break;
                case 2:
                    if ($r3.positionStart <= $i1) {
                        if ($r3.positionStart + $r3.itemCount <= $i1) {
                            $i1 -= $r3.itemCount;
                            break;
                        }
                        return -1;
                    }
                    continue;
                case 8:
                    if ($r3.positionStart != $i1) {
                        if ($r3.positionStart < $i1) {
                            $i1--;
                        }
                        if ($r3.itemCount > $i1) {
                            break;
                        }
                        $i1++;
                        break;
                    }
                    $i1 = $r3.itemCount;
                    break;
                default:
                    break;
            }
        }
        return $i1;
    }

    boolean hasUpdates() throws  {
        return (this.mPostponedList.isEmpty() || this.mPendingUpdates.isEmpty()) ? false : true;
    }

    public UpdateOp obtainUpdateOp(int $i0, int $i1, int $i2, Object $r1) throws  {
        UpdateOp $r4 = (UpdateOp) this.mUpdateOpPool.acquire();
        if ($r4 == null) {
            return new UpdateOp($i0, $i1, $i2, $r1);
        }
        $r4.cmd = $i0;
        $r4.positionStart = $i1;
        $r4.itemCount = $i2;
        $r4.payload = $r1;
        return $r4;
    }

    public void recycleUpdateOp(UpdateOp $r1) throws  {
        if (!this.mDisableRecycler) {
            $r1.payload = null;
            this.mUpdateOpPool.release($r1);
        }
    }

    void recycleUpdateOpsAndClearList(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;)V"}) List<UpdateOp> $r1) throws  {
        int $i0 = $r1.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            recycleUpdateOp((UpdateOp) $r1.get($i1));
        }
        $r1.clear();
    }
}
