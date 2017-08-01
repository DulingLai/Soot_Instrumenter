package com.abaltatech.mcp.mcs.tcpip;

import com.abaltatech.mcp.mcs.common.MCSException;

public class DataQueueSentIPPacket {
    private int fp = 0;
    private SentIPPacket[] f13q;
    private int qMaxSize;
    private int qs = 0;
    private int rp = 0;

    public DataQueueSentIPPacket(int $i0) throws  {
        this.qMaxSize = $i0;
        this.fp = 0;
        this.rp = 0;
        this.qs = 0;
        this.f13q = new SentIPPacket[this.qMaxSize];
    }

    public SentIPPacket delete() throws MCSException {
        if (emptyq()) {
            throw new MCSException("DataQueueSentIPPacket - Queue underflow");
        }
        this.qs--;
        this.fp = (this.fp + 1) % this.qMaxSize;
        return this.f13q[this.fp];
    }

    public SentIPPacket get() throws MCSException {
        if (!emptyq()) {
            return this.f13q[(this.fp + 1) % this.qMaxSize];
        }
        throw new MCSException("DataQueueSentIPPacket - Queue underflow");
    }

    public SentIPPacket get(int $i0) throws MCSException {
        if (this.qs > $i0) {
            return this.f13q[((this.fp + 1) + $i0) % this.qMaxSize];
        }
        throw new MCSException("DataQueueSentIPPacket - Queue underflow - index too high");
    }

    public void insert(SentIPPacket $r1) throws MCSException {
        if (fullq()) {
            throw new MCSException("DataQueueSentIPPacket - Overflow");
        }
        this.qs++;
        this.rp = (this.rp + 1) % this.qMaxSize;
        this.f13q[this.rp] = $r1;
    }

    public boolean contains(SentIPPacket $r1) throws  {
        if (this.qs == 0) {
            return false;
        }
        if (this.fp < this.rp) {
            return contains($r1, this.fp + 1, this.rp);
        }
        return contains($r1, this.fp + 1, this.qMaxSize + -1) || contains($r1, 0, this.rp);
    }

    private boolean contains(SentIPPacket $r1, int $i0, int $i1) throws  {
        while ($i0 <= $i1) {
            if (this.f13q[$i0] == $r1) {
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
