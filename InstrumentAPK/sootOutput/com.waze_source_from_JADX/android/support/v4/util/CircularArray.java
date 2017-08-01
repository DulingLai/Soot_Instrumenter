package android.support.v4.util;

import dalvik.annotation.Signature;

public final class CircularArray<E> {
    private int mCapacityBitmask;
    private E[] mElements;
    private int mHead;
    private int mTail;

    private void doubleCapacity() throws  {
        int $i0 = this.mElements.length;
        int $i2 = $i0 - this.mHead;
        int $i1 = $i0 << 1;
        if ($i1 < 0) {
            throw new RuntimeException("Max array capacity exceeded");
        }
        Object[] $r1 = new Object[$i1];
        System.arraycopy(this.mElements, this.mHead, $r1, 0, $i2);
        System.arraycopy(this.mElements, 0, $r1, $i2, this.mHead);
        this.mElements = $r1;
        this.mHead = 0;
        this.mTail = $i0;
        this.mCapacityBitmask = $i1 - 1;
    }

    public CircularArray() throws  {
        this(8);
    }

    public CircularArray(int $i0) throws  {
        if ($i0 < 1) {
            throw new IllegalArgumentException("capacity must be >= 1");
        } else if ($i0 > 1073741824) {
            throw new IllegalArgumentException("capacity must be <= 2^30");
        } else {
            if (Integer.bitCount($i0) != 1) {
                $i0 = Integer.highestOneBit($i0 - 1) << 1;
            }
            this.mCapacityBitmask = $i0 - 1;
            this.mElements = new Object[$i0];
        }
    }

    public void addFirst(@Signature({"(TE;)V"}) E $r1) throws  {
        this.mHead = (this.mHead - 1) & this.mCapacityBitmask;
        this.mElements[this.mHead] = $r1;
        if (this.mHead == this.mTail) {
            doubleCapacity();
        }
    }

    public void addLast(@Signature({"(TE;)V"}) E $r1) throws  {
        this.mElements[this.mTail] = $r1;
        this.mTail = (this.mTail + 1) & this.mCapacityBitmask;
        if (this.mTail == this.mHead) {
            doubleCapacity();
        }
    }

    public E popFirst() throws  {
        if (this.mHead == this.mTail) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Object $r1 = this.mElements[this.mHead];
        this.mElements[this.mHead] = null;
        this.mHead = (this.mHead + 1) & this.mCapacityBitmask;
        return $r1;
    }

    public E popLast() throws  {
        if (this.mHead == this.mTail) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int $i0 = (this.mTail - 1) & this.mCapacityBitmask;
        Object $r1 = this.mElements[$i0];
        this.mElements[$i0] = null;
        this.mTail = $i0;
        return $r1;
    }

    public void clear() throws  {
        removeFromStart(size());
    }

    public void removeFromStart(int $i1) throws  {
        if ($i1 > 0) {
            if ($i1 > size()) {
                throw new ArrayIndexOutOfBoundsException();
            }
            int $i0 = this.mElements.length;
            if ($i1 < $i0 - this.mHead) {
                $i0 = this.mHead + $i1;
            }
            for (int $i2 = this.mHead; $i2 < $i0; $i2++) {
                this.mElements[$i2] = null;
            }
            $i0 -= this.mHead;
            $i1 -= $i0;
            this.mHead = (this.mHead + $i0) & this.mCapacityBitmask;
            if ($i1 > 0) {
                for ($i0 = 0; $i0 < $i1; $i0++) {
                    this.mElements[$i0] = null;
                }
                this.mHead = $i1;
            }
        }
    }

    public void removeFromEnd(int $i0) throws  {
        if ($i0 > 0) {
            if ($i0 > size()) {
                throw new ArrayIndexOutOfBoundsException();
            }
            int $i1 = 0;
            if ($i0 < this.mTail) {
                $i1 = this.mTail - $i0;
            }
            for (int $i2 = $i1; $i2 < this.mTail; $i2++) {
                this.mElements[$i2] = null;
            }
            $i1 = this.mTail - $i1;
            $i0 -= $i1;
            this.mTail -= $i1;
            if ($i0 > 0) {
                this.mTail = this.mElements.length;
                $i0 = this.mTail - $i0;
                for ($i1 = $i0; $i1 < this.mTail; $i1++) {
                    this.mElements[$i1] = null;
                }
                this.mTail = $i0;
            }
        }
    }

    public E getFirst() throws  {
        if (this.mHead != this.mTail) {
            return this.mElements[this.mHead];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public E getLast() throws  {
        if (this.mHead != this.mTail) {
            return this.mElements[(this.mTail - 1) & this.mCapacityBitmask];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public E get(@Signature({"(I)TE;"}) int $i0) throws  {
        if ($i0 >= 0 && $i0 < size()) {
            return this.mElements[(this.mHead + $i0) & this.mCapacityBitmask];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int size() throws  {
        return (this.mTail - this.mHead) & this.mCapacityBitmask;
    }

    public boolean isEmpty() throws  {
        return this.mHead == this.mTail;
    }
}
