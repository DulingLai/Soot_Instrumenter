package com.abaltatech.mcp.mcs.utils;

import com.abaltatech.mcp.mcs.common.MCSException;

public class DataQueueInt {
    private int fp = 0;
    private int[] f15q;
    private int qMaxSize;
    private int qs = 0;
    private int rp = 0;

    public DataQueueInt(int $i0) throws  {
        this.qMaxSize = $i0;
        this.fp = 0;
        this.rp = 0;
        this.qs = 0;
        this.f15q = new int[this.qMaxSize];
    }

    public int delete() throws MCSException {
        if (emptyq()) {
            throw new MCSException("DataQueueInt - Queue underflow");
        }
        this.qs--;
        this.fp = (this.fp + 1) % this.qMaxSize;
        return this.f15q[this.fp];
    }

    public int get() throws MCSException {
        if (!emptyq()) {
            return this.f15q[(this.fp + 1) % this.qMaxSize];
        }
        throw new MCSException("DataQueueInt - Queue underflow");
    }

    public void insert(int $i0) throws MCSException {
        if (fullq()) {
            throw new MCSException("DataQueueInt - Overflow");
        }
        this.qs++;
        this.rp = (this.rp + 1) % this.qMaxSize;
        this.f15q[this.rp] = $i0;
    }

    public boolean contains(int $i0) throws  {
        if (this.qs == 0) {
            return false;
        }
        if (this.fp < this.rp) {
            return contains($i0, this.fp + 1, this.rp);
        }
        return contains($i0, this.fp + 1, this.qMaxSize + -1) || contains($i0, 0, this.rp);
    }

    private boolean contains(int $i0, int $i1, int $i2) throws  {
        while ($i1 <= $i2) {
            if (this.f15q[$i1] == $i0) {
                return true;
            }
            $i1++;
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
