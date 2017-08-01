package android.support.v4.util;

public final class CircularIntArray {
    private int mCapacityBitmask;
    private int[] mElements;
    private int mHead;
    private int mTail;

    private void doubleCapacity() throws  {
        int $i0 = this.mElements.length;
        int $i2 = $i0 - this.mHead;
        int $i1 = $i0 << 1;
        if ($i1 < 0) {
            throw new RuntimeException("Max array capacity exceeded");
        }
        int[] $r1 = new int[$i1];
        System.arraycopy(this.mElements, this.mHead, $r1, 0, $i2);
        System.arraycopy(this.mElements, 0, $r1, $i2, this.mHead);
        this.mElements = $r1;
        this.mHead = 0;
        this.mTail = $i0;
        this.mCapacityBitmask = $i1 - 1;
    }

    public CircularIntArray() throws  {
        this(8);
    }

    public CircularIntArray(int $i0) throws  {
        if ($i0 < 1) {
            throw new IllegalArgumentException("capacity must be >= 1");
        } else if ($i0 > 1073741824) {
            throw new IllegalArgumentException("capacity must be <= 2^30");
        } else {
            if (Integer.bitCount($i0) != 1) {
                $i0 = Integer.highestOneBit($i0 - 1) << 1;
            }
            this.mCapacityBitmask = $i0 - 1;
            this.mElements = new int[$i0];
        }
    }

    public void addFirst(int $i0) throws  {
        this.mHead = (this.mHead - 1) & this.mCapacityBitmask;
        this.mElements[this.mHead] = $i0;
        if (this.mHead == this.mTail) {
            doubleCapacity();
        }
    }

    public void addLast(int $i0) throws  {
        this.mElements[this.mTail] = $i0;
        this.mTail = (this.mTail + 1) & this.mCapacityBitmask;
        if (this.mTail == this.mHead) {
            doubleCapacity();
        }
    }

    public int popFirst() throws  {
        if (this.mHead == this.mTail) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int $i0 = this.mElements[this.mHead];
        this.mHead = (this.mHead + 1) & this.mCapacityBitmask;
        return $i0;
    }

    public int popLast() throws  {
        if (this.mHead == this.mTail) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int $i1 = (this.mTail - 1) & this.mCapacityBitmask;
        int $i0 = this.mElements[$i1];
        this.mTail = $i1;
        return $i0;
    }

    public void clear() throws  {
        this.mTail = this.mHead;
    }

    public void removeFromStart(int $i0) throws  {
        if ($i0 > 0) {
            if ($i0 > size()) {
                throw new ArrayIndexOutOfBoundsException();
            }
            this.mHead = (this.mHead + $i0) & this.mCapacityBitmask;
        }
    }

    public void removeFromEnd(int $i0) throws  {
        if ($i0 > 0) {
            if ($i0 > size()) {
                throw new ArrayIndexOutOfBoundsException();
            }
            this.mTail = (this.mTail - $i0) & this.mCapacityBitmask;
        }
    }

    public int getFirst() throws  {
        if (this.mHead != this.mTail) {
            return this.mElements[this.mHead];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getLast() throws  {
        if (this.mHead != this.mTail) {
            return this.mElements[(this.mTail - 1) & this.mCapacityBitmask];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int get(int $i0) throws  {
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
