package com.android.volley.toolbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ByteArrayPool {
    protected static final Comparator<byte[]> BUF_COMPARATOR = new C04641();
    private List<byte[]> mBuffersByLastUse = new LinkedList();
    private List<byte[]> mBuffersBySize = new ArrayList(64);
    private int mCurrentSize = 0;
    private final int mSizeLimit;

    static class C04641 implements Comparator<byte[]> {
        C04641() throws  {
        }

        public int compare(byte[] $r1, byte[] $r2) throws  {
            return $r1.length - $r2.length;
        }
    }

    public ByteArrayPool(int $i0) throws  {
        this.mSizeLimit = $i0;
    }

    public synchronized byte[] getBuf(int $i0) throws  {
        byte[] $r3;
        for (int $i1 = 0; $i1 < this.mBuffersBySize.size(); $i1++) {
            $r3 = (byte[]) this.mBuffersBySize.get($i1);
            if ($r3.length >= $i0) {
                this.mCurrentSize -= $r3.length;
                this.mBuffersBySize.remove($i1);
                this.mBuffersByLastUse.remove($r3);
                break;
            }
        }
        $r3 = new byte[$i0];
        return $r3;
    }

    public synchronized void returnBuf(byte[] $r1) throws  {
        if ($r1 != null) {
            if ($r1.length <= this.mSizeLimit) {
                this.mBuffersByLastUse.add($r1);
                int $i0 = Collections.binarySearch(this.mBuffersBySize, $r1, BUF_COMPARATOR);
                int $i1 = $i0;
                if ($i0 < 0) {
                    $i1 = (-$i0) - 1;
                }
                this.mBuffersBySize.add($i1, $r1);
                this.mCurrentSize += $r1.length;
                trim();
            }
        }
    }

    private synchronized void trim() throws  {
        while (this.mCurrentSize > this.mSizeLimit) {
            byte[] $r3 = (byte[]) this.mBuffersByLastUse.remove(0);
            this.mBuffersBySize.remove($r3);
            this.mCurrentSize -= $r3.length;
        }
    }
}
