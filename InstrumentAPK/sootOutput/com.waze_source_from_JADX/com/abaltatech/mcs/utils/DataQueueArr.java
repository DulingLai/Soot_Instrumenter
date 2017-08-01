package com.abaltatech.mcs.utils;

import com.abaltatech.mcs.common.MCSException;

public class DataQueueArr {
    private int fp = 0;
    private byte[][] f21q;
    private int qMaxSize;
    private int qs = 0;
    private int rp = 0;

    public DataQueueArr(int $i0) throws  {
        this.qMaxSize = $i0;
        this.fp = 0;
        this.rp = 0;
        this.qs = 0;
        this.f21q = new byte[this.qMaxSize][];
    }

    public byte[] delete() throws MCSException {
        if (emptyq()) {
            throw new MCSException("DataQueueArr - Queue underflow");
        }
        this.qs--;
        this.fp = (this.fp + 1) % this.qMaxSize;
        byte[] $r1 = this.f21q[this.fp];
        this.f21q[this.fp] = null;
        return $r1;
    }

    public byte[] get() throws MCSException {
        if (!emptyq()) {
            return this.f21q[(this.fp + 1) % this.qMaxSize];
        }
        throw new MCSException("DataQueueArr - Queue underflow");
    }

    public void insert(byte[] $r1) throws MCSException {
        if (fullq()) {
            throw new MCSException("DataQueueArr - Overflow");
        }
        this.qs++;
        this.rp = (this.rp + 1) % this.qMaxSize;
        this.f21q[this.rp] = $r1;
    }

    public boolean contains(byte[] $r1) throws  {
        if (this.qs == 0) {
            return false;
        }
        if (this.fp < this.rp) {
            return contains($r1, this.fp + 1, this.rp);
        }
        return contains($r1, this.fp + 1, this.qMaxSize + -1) || contains($r1, 0, this.rp);
    }

    private boolean contains(byte[] $r1, int $i0, int $i1) throws  {
        while ($i0 <= $i1) {
            if (this.f21q[$i0] == $r1) {
                return true;
            }
            $i0++;
        }
        return false;
    }

    public boolean emptyq() throws  {
        return this.qs == 0;
    }

    public boolean fullq() throws  {
        return this.qs == this.qMaxSize;
    }

    public int size() throws  {
        return this.qs;
    }
}
