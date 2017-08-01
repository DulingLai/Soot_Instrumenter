package android.support.v7.widget;

import dalvik.annotation.Signature;
import java.util.List;

class OpReorderer {
    final Callback mCallback;

    interface Callback {
        UpdateOp obtainUpdateOp(int i, int i2, int i3, Object obj) throws ;

        void recycleUpdateOp(UpdateOp updateOp) throws ;
    }

    public OpReorderer(Callback $r1) throws  {
        this.mCallback = $r1;
    }

    void reorderOps(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;)V"}) List<UpdateOp> $r1) throws  {
        while (true) {
            int $i0 = getLastMoveOutOfOrder($r1);
            if ($i0 != -1) {
                swapMoveOp($r1, $i0, $i0 + 1);
            } else {
                return;
            }
        }
    }

    private void swapMoveOp(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;II)V"}) List<UpdateOp> $r1, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;II)V"}) int $i0, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;II)V"}) int $i1) throws  {
        UpdateOp $r3 = (UpdateOp) $r1.get($i0);
        UpdateOp $r4 = (UpdateOp) $r1.get($i1);
        switch ($r4.cmd) {
            case 1:
                swapMoveAdd($r1, $i0, $r3, $i1, $r4);
                return;
            case 2:
                swapMoveRemove($r1, $i0, $r3, $i1, $r4);
                return;
            case 3:
                break;
            case 4:
                swapMoveUpdate($r1, $i0, $r3, $i1, $r4);
                return;
            default:
                break;
        }
    }

    void swapMoveRemove(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", "I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ")V"}) List<UpdateOp> $r1, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", "I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ")V"}) int $i0, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", "I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ")V"}) UpdateOp $r2, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", "I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ")V"}) int $i1, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", "I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ")V"}) UpdateOp $r3) throws  {
        boolean $z1;
        UpdateOp $r4 = null;
        boolean $z0 = false;
        if ($r2.positionStart < $r2.itemCount) {
            $z1 = false;
            if ($r3.positionStart == $r2.positionStart && $r3.itemCount == $r2.itemCount - $r2.positionStart) {
                $z0 = true;
            }
        } else {
            $z1 = true;
            if ($r3.positionStart == $r2.itemCount + 1 && $r3.itemCount == $r2.positionStart - $r2.itemCount) {
                $z0 = true;
            }
        }
        if ($r2.itemCount < $r3.positionStart) {
            $r3.positionStart--;
        } else if ($r2.itemCount < $r3.positionStart + $r3.itemCount) {
            $r3.itemCount--;
            $r2.cmd = 2;
            $r2.itemCount = 1;
            if ($r3.itemCount == 0) {
                $r1.remove($i1);
                this.mCallback.recycleUpdateOp($r3);
                return;
            }
            return;
        }
        if ($r2.positionStart <= $r3.positionStart) {
            $r3.positionStart++;
        } else if ($r2.positionStart < $r3.positionStart + $r3.itemCount) {
            $r4 = this.mCallback.obtainUpdateOp(2, $r2.positionStart + 1, ($r3.positionStart + $r3.itemCount) - $r2.positionStart, null);
            $r3.itemCount = $r2.positionStart - $r3.positionStart;
        }
        if ($z0) {
            $r1.set($i0, $r3);
            $r1.remove($i1);
            this.mCallback.recycleUpdateOp($r2);
            return;
        }
        if ($z1) {
            if ($r4 != null) {
                if ($r2.positionStart > $r4.positionStart) {
                    $r2.positionStart -= $r4.itemCount;
                }
                if ($r2.itemCount > $r4.positionStart) {
                    $r2.itemCount -= $r4.itemCount;
                }
            }
            if ($r2.positionStart > $r3.positionStart) {
                $r2.positionStart -= $r3.itemCount;
            }
            if ($r2.itemCount > $r3.positionStart) {
                $r2.itemCount -= $r3.itemCount;
            }
        } else {
            if ($r4 != null) {
                if ($r2.positionStart >= $r4.positionStart) {
                    $r2.positionStart -= $r4.itemCount;
                }
                if ($r2.itemCount >= $r4.positionStart) {
                    $r2.itemCount -= $r4.itemCount;
                }
            }
            if ($r2.positionStart >= $r3.positionStart) {
                $r2.positionStart -= $r3.itemCount;
            }
            if ($r2.itemCount >= $r3.positionStart) {
                $r2.itemCount -= $r3.itemCount;
            }
        }
        $r1.set($i0, $r3);
        if ($r2.positionStart != $r2.itemCount) {
            $r1.set($i1, $r2);
        } else {
            $r1.remove($i1);
        }
        if ($r4 != null) {
            $r1.add($i0, $r4);
        }
    }

    private void swapMoveAdd(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", "I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ")V"}) List<UpdateOp> $r1, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", "I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ")V"}) int $i0, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", "I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ")V"}) UpdateOp $r2, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", "I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ")V"}) int $i1, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", "I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ")V"}) UpdateOp $r3) throws  {
        int $i2 = 0;
        if ($r2.itemCount < $r3.positionStart) {
            $i2 = 0 - 1;
        }
        if ($r2.positionStart < $r3.positionStart) {
            $i2++;
        }
        if ($r3.positionStart <= $r2.positionStart) {
            $r2.positionStart += $r3.itemCount;
        }
        if ($r3.positionStart <= $r2.itemCount) {
            $r2.itemCount += $r3.itemCount;
        }
        $r3.positionStart += $i2;
        $r1.set($i0, $r3);
        $r1.set($i1, $r2);
    }

    void swapMoveUpdate(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", "I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ")V"}) List<UpdateOp> $r1, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", "I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ")V"}) int $i0, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", "I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ")V"}) UpdateOp $r2, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", "I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ")V"}) int $i1, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", "I", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ")V"}) UpdateOp $r3) throws  {
        UpdateOp $r5 = null;
        UpdateOp $r6 = null;
        if ($r2.itemCount < $r3.positionStart) {
            $r3.positionStart--;
        } else if ($r2.itemCount < $r3.positionStart + $r3.itemCount) {
            $r3.itemCount--;
            $r5 = this.mCallback.obtainUpdateOp(4, $r2.positionStart, 1, $r3.payload);
        }
        if ($r2.positionStart <= $r3.positionStart) {
            $r3.positionStart++;
        } else if ($r2.positionStart < $r3.positionStart + $r3.itemCount) {
            int $i2 = ($r3.positionStart + $r3.itemCount) - $r2.positionStart;
            $r6 = this.mCallback.obtainUpdateOp(4, $r2.positionStart + 1, $i2, $r3.payload);
            $r3.itemCount -= $i2;
        }
        $r1.set($i1, $r2);
        if ($r3.itemCount > 0) {
            $r1.set($i0, $r3);
        } else {
            $r1.remove($i0);
            this.mCallback.recycleUpdateOp($r3);
        }
        if ($r5 != null) {
            $r1.add($i0, $r5);
        }
        if ($r6 != null) {
            $r1.add($i0, $r6);
        }
    }

    private int getLastMoveOutOfOrder(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/AdapterHelper$UpdateOp;", ">;)I"}) List<UpdateOp> $r1) throws  {
        boolean $z0 = false;
        for (int $i0 = $r1.size() - 1; $i0 >= 0; $i0--) {
            if (((UpdateOp) $r1.get($i0)).cmd != 8) {
                $z0 = true;
            } else if ($z0) {
                return $i0;
            }
        }
        return -1;
    }
}
