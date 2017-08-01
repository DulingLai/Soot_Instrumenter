package android.support.v7.util;

import dalvik.annotation.Signature;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class SortedList<T> {
    private static final int CAPACITY_GROWTH = 10;
    private static final int DELETION = 2;
    private static final int INSERTION = 1;
    public static final int INVALID_POSITION = -1;
    private static final int LOOKUP = 4;
    private static final int MIN_CAPACITY = 10;
    private BatchedCallback mBatchedCallback;
    private Callback mCallback;
    T[] mData;
    private int mMergedSize;
    private T[] mOldData;
    private int mOldDataSize;
    private int mOldDataStart;
    private int mSize;
    private final Class<T> mTClass;

    public static abstract class Callback<T2> implements Comparator<T2> {
        public abstract boolean areContentsTheSame(@Signature({"(TT2;TT2;)Z"}) T2 t2, @Signature({"(TT2;TT2;)Z"}) T2 t22) throws ;

        public abstract boolean areItemsTheSame(@Signature({"(TT2;TT2;)Z"}) T2 t2, @Signature({"(TT2;TT2;)Z"}) T2 t22) throws ;

        public abstract int compare(@Signature({"(TT2;TT2;)I"}) T2 t2, @Signature({"(TT2;TT2;)I"}) T2 t22) throws ;

        public abstract void onChanged(int i, int i2) throws ;

        public abstract void onInserted(int i, int i2) throws ;

        public abstract void onMoved(int i, int i2) throws ;

        public abstract void onRemoved(int i, int i2) throws ;
    }

    public static class BatchedCallback<T2> extends Callback<T2> {
        static final int TYPE_ADD = 1;
        static final int TYPE_CHANGE = 3;
        static final int TYPE_MOVE = 4;
        static final int TYPE_NONE = 0;
        static final int TYPE_REMOVE = 2;
        int mLastEventCount = -1;
        int mLastEventPosition = -1;
        int mLastEventType = 0;
        private final Callback<T2> mWrappedCallback;

        public BatchedCallback(@Signature({"(", "Landroid/support/v7/util/SortedList$Callback", "<TT2;>;)V"}) Callback<T2> $r1) throws  {
            this.mWrappedCallback = $r1;
        }

        public int compare(@Signature({"(TT2;TT2;)I"}) T2 $r1, @Signature({"(TT2;TT2;)I"}) T2 $r2) throws  {
            return this.mWrappedCallback.compare($r1, $r2);
        }

        public void onInserted(int $i0, int $i1) throws  {
            if (this.mLastEventType != 1 || $i0 < this.mLastEventPosition || $i0 > this.mLastEventPosition + this.mLastEventCount) {
                dispatchLastEvent();
                this.mLastEventPosition = $i0;
                this.mLastEventCount = $i1;
                this.mLastEventType = 1;
                return;
            }
            this.mLastEventCount += $i1;
            this.mLastEventPosition = Math.min($i0, this.mLastEventPosition);
        }

        public void onRemoved(int $i0, int $i1) throws  {
            if (this.mLastEventType == 2 && this.mLastEventPosition == $i0) {
                this.mLastEventCount += $i1;
                return;
            }
            dispatchLastEvent();
            this.mLastEventPosition = $i0;
            this.mLastEventCount = $i1;
            this.mLastEventType = 2;
        }

        public void onMoved(int $i0, int $i1) throws  {
            dispatchLastEvent();
            this.mWrappedCallback.onMoved($i0, $i1);
        }

        public void onChanged(int $i0, int $i1) throws  {
            if (this.mLastEventType != 3 || $i0 > this.mLastEventPosition + this.mLastEventCount || $i0 + $i1 < this.mLastEventPosition) {
                dispatchLastEvent();
                this.mLastEventPosition = $i0;
                this.mLastEventCount = $i1;
                this.mLastEventType = 3;
                return;
            }
            int $i2 = this.mLastEventPosition + this.mLastEventCount;
            this.mLastEventPosition = Math.min($i0, this.mLastEventPosition);
            this.mLastEventCount = Math.max($i2, $i0 + $i1) - this.mLastEventPosition;
        }

        public boolean areContentsTheSame(@Signature({"(TT2;TT2;)Z"}) T2 $r1, @Signature({"(TT2;TT2;)Z"}) T2 $r2) throws  {
            return this.mWrappedCallback.areContentsTheSame($r1, $r2);
        }

        public boolean areItemsTheSame(@Signature({"(TT2;TT2;)Z"}) T2 $r1, @Signature({"(TT2;TT2;)Z"}) T2 $r2) throws  {
            return this.mWrappedCallback.areItemsTheSame($r1, $r2);
        }

        public void dispatchLastEvent() throws  {
            if (this.mLastEventType != 0) {
                switch (this.mLastEventType) {
                    case 1:
                        this.mWrappedCallback.onInserted(this.mLastEventPosition, this.mLastEventCount);
                        break;
                    case 2:
                        this.mWrappedCallback.onRemoved(this.mLastEventPosition, this.mLastEventCount);
                        break;
                    case 3:
                        this.mWrappedCallback.onChanged(this.mLastEventPosition, this.mLastEventCount);
                        break;
                    default:
                        break;
                }
                this.mLastEventType = 0;
            }
        }
    }

    public SortedList(@Signature({"(", "Ljava/lang/Class", "<TT;>;", "Landroid/support/v7/util/SortedList$Callback", "<TT;>;)V"}) Class<T> $r1, @Signature({"(", "Ljava/lang/Class", "<TT;>;", "Landroid/support/v7/util/SortedList$Callback", "<TT;>;)V"}) Callback<T> $r2) throws  {
        this($r1, $r2, 10);
    }

    public SortedList(@Signature({"(", "Ljava/lang/Class", "<TT;>;", "Landroid/support/v7/util/SortedList$Callback", "<TT;>;I)V"}) Class<T> $r1, @Signature({"(", "Ljava/lang/Class", "<TT;>;", "Landroid/support/v7/util/SortedList$Callback", "<TT;>;I)V"}) Callback<T> $r2, @Signature({"(", "Ljava/lang/Class", "<TT;>;", "Landroid/support/v7/util/SortedList$Callback", "<TT;>;I)V"}) int $i0) throws  {
        this.mTClass = $r1;
        this.mData = (Object[]) Array.newInstance($r1, $i0);
        this.mCallback = $r2;
        this.mSize = 0;
    }

    public int size() throws  {
        return this.mSize;
    }

    public int add(@Signature({"(TT;)I"}) T $r1) throws  {
        throwIfMerging();
        return add($r1, true);
    }

    public void addAll(@Signature({"([TT;Z)V"}) T[] $r1, @Signature({"([TT;Z)V"}) boolean $z0) throws  {
        throwIfMerging();
        if ($r1.length != 0) {
            if ($z0) {
                addAllInternal($r1);
                return;
            }
            Object[] $r4 = (Object[]) Array.newInstance(this.mTClass, $r1.length);
            System.arraycopy($r1, 0, $r4, 0, $r1.length);
            addAllInternal($r4);
        }
    }

    public void addAll(@Signature({"([TT;)V"}) T... $r1) throws  {
        addAll($r1, false);
    }

    public void addAll(@Signature({"(", "Ljava/util/Collection", "<TT;>;)V"}) Collection<T> $r1) throws  {
        addAll($r1.toArray((Object[]) Array.newInstance(this.mTClass, $r1.size())), true);
    }

    private void addAllInternal(@Signature({"([TT;)V"}) T[] $r1) throws  {
        boolean $z0 = !(this.mCallback instanceof BatchedCallback);
        if ($z0) {
            beginBatchedUpdates();
        }
        this.mOldData = this.mData;
        this.mOldDataStart = 0;
        this.mOldDataSize = this.mSize;
        Arrays.sort($r1, this.mCallback);
        int $i0 = deduplicate($r1);
        if (this.mSize == 0) {
            this.mData = $r1;
            this.mSize = $i0;
            this.mMergedSize = $i0;
            this.mCallback.onInserted(0, $i0);
        } else {
            merge($r1, $i0);
        }
        this.mOldData = null;
        if ($z0) {
            endBatchedUpdates();
        }
    }

    private int deduplicate(@Signature({"([TT;)I"}) T[] $r1) throws  {
        if ($r1.length == 0) {
            throw new IllegalArgumentException("Input array must be non-empty");
        }
        int $i1 = 0;
        int $i2 = 1;
        for (int $i3 = 1; $i3 < $r1.length; $i3++) {
            Object $r2 = $r1[$i3];
            int $i0 = this.mCallback.compare($r1[$i1], $r2);
            if ($i0 > 0) {
                throw new IllegalArgumentException("Input must be sorted in ascending order.");
            }
            if ($i0 == 0) {
                $i0 = findSameItem($r2, $r1, $i1, $i2);
                if ($i0 != -1) {
                    $r1[$i0] = $r2;
                } else {
                    if ($i2 != $i3) {
                        $r1[$i2] = $r2;
                    }
                    $i2++;
                }
            } else {
                if ($i2 != $i3) {
                    $r1[$i2] = $r2;
                }
                $i1 = $i2;
                $i2++;
            }
        }
        return $i2;
    }

    private int findSameItem(@Signature({"(TT;[TT;II)I"}) T $r1, @Signature({"(TT;[TT;II)I"}) T[] $r2, @Signature({"(TT;[TT;II)I"}) int $i0, @Signature({"(TT;[TT;II)I"}) int $i1) throws  {
        while ($i0 < $i1) {
            if (this.mCallback.areItemsTheSame($r2[$i0], $r1)) {
                return $i0;
            }
            $i0++;
        }
        return -1;
    }

    private void merge(@Signature({"([TT;I)V"}) T[] $r1, @Signature({"([TT;I)V"}) int $i0) throws  {
        this.mData = (Object[]) Array.newInstance(this.mTClass, (this.mSize + $i0) + 10);
        this.mMergedSize = 0;
        int $i1 = 0;
        while (true) {
            if (this.mOldDataStart >= this.mOldDataSize && $i1 >= $i0) {
                return;
            }
            if (this.mOldDataStart == this.mOldDataSize) {
                $i0 -= $i1;
                System.arraycopy($r1, $i1, this.mData, this.mMergedSize, $i0);
                this.mMergedSize += $i0;
                this.mSize += $i0;
                this.mCallback.onInserted(this.mMergedSize - $i0, $i0);
                return;
            } else if ($i1 == $i0) {
                $i0 = this.mOldDataSize - this.mOldDataStart;
                System.arraycopy(this.mOldData, this.mOldDataStart, this.mData, this.mMergedSize, $i0);
                this.mMergedSize += $i0;
                return;
            } else {
                Object $r3 = this.mOldData[this.mOldDataStart];
                Object $r2 = $r1[$i1];
                int $i2 = this.mCallback.compare($r3, $r2);
                Object[] $r5;
                if ($i2 > 0) {
                    $r5 = this.mData;
                    $i2 = this.mMergedSize;
                    this.mMergedSize = $i2 + 1;
                    $r5[$i2] = $r2;
                    this.mSize++;
                    $i1++;
                    this.mCallback.onInserted(this.mMergedSize - 1, 1);
                } else if ($i2 == 0 && this.mCallback.areItemsTheSame($r3, $r2)) {
                    $r5 = this.mData;
                    $i2 = this.mMergedSize;
                    this.mMergedSize = $i2 + 1;
                    $r5[$i2] = $r2;
                    $i1++;
                    this.mOldDataStart++;
                    if (!this.mCallback.areContentsTheSame($r3, $r2)) {
                        this.mCallback.onChanged(this.mMergedSize - 1, 1);
                    }
                } else {
                    $r5 = this.mData;
                    $i2 = this.mMergedSize;
                    this.mMergedSize = $i2 + 1;
                    $r5[$i2] = $r3;
                    this.mOldDataStart++;
                }
            }
        }
    }

    private void throwIfMerging() throws  {
        if (this.mOldData != null) {
            throw new IllegalStateException("Cannot call this method from within addAll");
        }
    }

    public void beginBatchedUpdates() throws  {
        throwIfMerging();
        if (!(this.mCallback instanceof BatchedCallback)) {
            if (this.mBatchedCallback == null) {
                this.mBatchedCallback = new BatchedCallback(this.mCallback);
            }
            this.mCallback = this.mBatchedCallback;
        }
    }

    public void endBatchedUpdates() throws  {
        throwIfMerging();
        if (this.mCallback instanceof BatchedCallback) {
            ((BatchedCallback) this.mCallback).dispatchLastEvent();
        }
        if (this.mCallback == this.mBatchedCallback) {
            this.mCallback = this.mBatchedCallback.mWrappedCallback;
        }
    }

    private int add(@Signature({"(TT;Z)I"}) T $r1, @Signature({"(TT;Z)I"}) boolean $z0) throws  {
        int $i0 = findIndexOf($r1, this.mData, 0, this.mSize, 1);
        int $i1 = $i0;
        if ($i0 == -1) {
            $i1 = 0;
        } else if ($i0 < this.mSize) {
            Object $r3 = this.mData[$i0];
            if (this.mCallback.areItemsTheSame($r3, $r1)) {
                if (this.mCallback.areContentsTheSame($r3, $r1)) {
                    this.mData[$i0] = $r1;
                    return $i0;
                }
                this.mData[$i0] = $r1;
                this.mCallback.onChanged($i0, 1);
                return $i0;
            }
        }
        addToData($i1, $r1);
        if ($z0) {
            this.mCallback.onInserted($i1, 1);
        }
        return $i1;
    }

    public boolean remove(@Signature({"(TT;)Z"}) T $r1) throws  {
        throwIfMerging();
        return remove($r1, true);
    }

    public T removeItemAt(@Signature({"(I)TT;"}) int $i0) throws  {
        throwIfMerging();
        Object $r1 = get($i0);
        removeItemAtIndex($i0, true);
        return $r1;
    }

    private boolean remove(@Signature({"(TT;Z)Z"}) T $r1, @Signature({"(TT;Z)Z"}) boolean $z0) throws  {
        int $i0 = findIndexOf($r1, this.mData, 0, this.mSize, 2);
        if ($i0 == -1) {
            return false;
        }
        removeItemAtIndex($i0, $z0);
        return true;
    }

    private void removeItemAtIndex(int $i0, boolean $z0) throws  {
        System.arraycopy(this.mData, $i0 + 1, this.mData, $i0, (this.mSize - $i0) - 1);
        this.mSize--;
        this.mData[this.mSize] = null;
        if ($z0) {
            this.mCallback.onRemoved($i0, 1);
        }
    }

    public void updateItemAt(@Signature({"(ITT;)V"}) int $i0, @Signature({"(ITT;)V"}) T $r1) throws  {
        throwIfMerging();
        T $r2 = get($i0);
        boolean $z0 = $r2 == $r1 || !this.mCallback.areContentsTheSame($r2, $r1);
        if ($r2 == $r1 || this.mCallback.compare($r2, $r1) != 0) {
            if ($z0) {
                this.mCallback.onChanged($i0, 1);
            }
            removeItemAtIndex($i0, false);
            int $i1 = add($r1, false);
            if ($i0 != $i1) {
                this.mCallback.onMoved($i0, $i1);
                return;
            }
            return;
        }
        this.mData[$i0] = $r1;
        if ($z0) {
            this.mCallback.onChanged($i0, 1);
        }
    }

    public void recalculatePositionOfItemAt(int $i0) throws  {
        throwIfMerging();
        Object $r1 = get($i0);
        removeItemAtIndex($i0, false);
        int $i1 = add($r1, false);
        if ($i0 != $i1) {
            this.mCallback.onMoved($i0, $i1);
        }
    }

    public T get(@Signature({"(I)TT;"}) int $i0) throws IndexOutOfBoundsException {
        if ($i0 >= this.mSize || $i0 < 0) {
            throw new IndexOutOfBoundsException("Asked to get item at " + $i0 + " but size is " + this.mSize);
        } else if (this.mOldData == null || $i0 < this.mMergedSize) {
            return this.mData[$i0];
        } else {
            return this.mOldData[($i0 - this.mMergedSize) + this.mOldDataStart];
        }
    }

    public int indexOf(@Signature({"(TT;)I"}) T $r1) throws  {
        if (this.mOldData != null) {
            int $i0 = findIndexOf($r1, this.mData, 0, this.mMergedSize, 4);
            if ($i0 != -1) {
                return $i0;
            }
            $i0 = findIndexOf($r1, this.mOldData, this.mOldDataStart, this.mOldDataSize, 4);
            return $i0 != -1 ? ($i0 - this.mOldDataStart) + this.mMergedSize : -1;
        } else {
            return findIndexOf($r1, this.mData, 0, this.mSize, 4);
        }
    }

    private int findIndexOf(@Signature({"(TT;[TT;III)I"}) T $r1, @Signature({"(TT;[TT;III)I"}) T[] $r2, @Signature({"(TT;[TT;III)I"}) int $i1, @Signature({"(TT;[TT;III)I"}) int $i2, @Signature({"(TT;[TT;III)I"}) int $i0) throws  {
        while ($i1 < $i2) {
            int $i3 = ($i1 + $i2) / 2;
            Object $r3 = $r2[$i3];
            int $i4 = this.mCallback.compare($r3, $r1);
            if ($i4 < 0) {
                $i1 = $i3 + 1;
            } else if ($i4 != 0) {
                $i2 = $i3;
            } else if (this.mCallback.areItemsTheSame($r3, $r1)) {
                return $i3;
            } else {
                $i1 = linearEqualitySearch($r1, $i3, $i1, $i2);
                if ($i0 == 1 && $i1 == -1) {
                    return $i3;
                }
                return $i1;
            }
        }
        if ($i0 != 1) {
            $i1 = -1;
        }
        return $i1;
    }

    private int linearEqualitySearch(@Signature({"(TT;III)I"}) T $r1, @Signature({"(TT;III)I"}) int $i0, @Signature({"(TT;III)I"}) int $i1, @Signature({"(TT;III)I"}) int $i2) throws  {
        int $i3 = $i0 - 1;
        while ($i3 >= $i1) {
            Object $r3 = this.mData[$i3];
            if (this.mCallback.compare($r3, $r1) != 0) {
                break;
            } else if (this.mCallback.areItemsTheSame($r3, $r1)) {
                return $i3;
            } else {
                $i3--;
            }
        }
        $i0++;
        while ($i0 < $i2) {
            $r3 = this.mData[$i0];
            if (this.mCallback.compare($r3, $r1) != 0) {
                break;
            } else if (this.mCallback.areItemsTheSame($r3, $r1)) {
                return $i0;
            } else {
                $i0++;
            }
        }
        return -1;
    }

    private void addToData(@Signature({"(ITT;)V"}) int $i0, @Signature({"(ITT;)V"}) T $r1) throws  {
        if ($i0 > this.mSize) {
            throw new IndexOutOfBoundsException("cannot add item to " + $i0 + " because size is " + this.mSize);
        }
        if (this.mSize == this.mData.length) {
            Object[] $r5 = (Object[]) Array.newInstance(this.mTClass, this.mData.length + 10);
            System.arraycopy(this.mData, 0, $r5, 0, $i0);
            $r5[$i0] = $r1;
            System.arraycopy(this.mData, $i0, $r5, $i0 + 1, this.mSize - $i0);
            this.mData = $r5;
        } else {
            System.arraycopy(this.mData, $i0, this.mData, $i0 + 1, this.mSize - $i0);
            this.mData[$i0] = $r1;
        }
        this.mSize++;
    }

    public void clear() throws  {
        throwIfMerging();
        if (this.mSize != 0) {
            int $i0 = this.mSize;
            Arrays.fill(this.mData, 0, $i0, null);
            this.mSize = 0;
            this.mCallback.onRemoved(0, $i0);
        }
    }
}
