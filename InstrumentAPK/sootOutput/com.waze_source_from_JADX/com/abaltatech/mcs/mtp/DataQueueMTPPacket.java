package com.abaltatech.mcs.mtp;

import com.abaltatech.mcs.common.MCSException;

public class DataQueueMTPPacket {
    private int fp = 0;
    private MTPPacket[] f17q;
    private int qMaxSize;
    private int qs = 0;
    private int rp = 0;

    public DataQueueMTPPacket(int $i0) throws  {
        this.qMaxSize = $i0;
        this.fp = 0;
        this.rp = 0;
        this.qs = 0;
        this.f17q = new MTPPacket[this.qMaxSize];
    }

    public MTPPacket delete() throws MCSException {
        if (emptyq()) {
            throw new MCSException("DataQueueMTPPacket - Queue underflow");
        }
        this.qs--;
        this.fp = (this.fp + 1) % this.qMaxSize;
        return this.f17q[this.fp];
    }

    public MTPPacket get() throws MCSException {
        if (!emptyq()) {
            return this.f17q[(this.fp + 1) % this.qMaxSize];
        }
        throw new MCSException("DataQueueMTPPacket - Queue underflow");
    }

    public void insert(MTPPacket $r1) throws MCSException {
        if (fullq()) {
            throw new MCSException("DataQueueMTPPacket - Overflow");
        }
        this.qs++;
        this.rp = (this.rp + 1) % this.qMaxSize;
        this.f17q[this.rp] = $r1;
    }

    public boolean contains(MTPPacket $r1) throws  {
        if (this.qs == 0) {
            return false;
        }
        if (this.fp < this.rp) {
            return contains($r1, this.fp + 1, this.rp);
        }
        return contains($r1, this.fp + 1, this.qMaxSize + -1) || contains($r1, 0, this.rp);
    }

    private boolean contains(MTPPacket $r1, int $i0, int $i1) throws  {
        while ($i0 <= $i1) {
            if (this.f17q[$i0] == $r1) {
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
