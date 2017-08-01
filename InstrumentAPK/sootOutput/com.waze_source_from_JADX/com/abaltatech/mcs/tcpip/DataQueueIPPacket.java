package com.abaltatech.mcs.tcpip;

import com.abaltatech.mcs.common.MCSException;

public class DataQueueIPPacket {
    private int fp = 0;
    private TCPIPPacket[] f19q;
    private int qMaxSize;
    private int qs = 0;
    private int rp = 0;

    public DataQueueIPPacket(int $i0) throws  {
        this.qMaxSize = $i0;
        this.fp = 0;
        this.rp = 0;
        this.qs = 0;
        this.f19q = new TCPIPPacket[this.qMaxSize];
    }

    public TCPIPPacket delete() throws MCSException {
        if (emptyq()) {
            throw new MCSException("Queue underflow");
        }
        this.qs--;
        this.fp = (this.fp + 1) % this.qMaxSize;
        return this.f19q[this.fp];
    }

    public TCPIPPacket get() throws MCSException {
        if (!emptyq()) {
            return this.f19q[(this.fp + 1) % this.qMaxSize];
        }
        throw new MCSException("DataQueueIPPacket - Queue underflow");
    }

    public void insert(TCPIPPacket $r1) throws MCSException {
        if (fullq()) {
            throw new MCSException("DataQueueIPPacket - Overflow");
        }
        this.qs++;
        this.rp = (this.rp + 1) % this.qMaxSize;
        this.f19q[this.rp] = $r1;
    }

    public boolean contains(TCPIPPacket $r1) throws  {
        if (this.qs == 0) {
            return false;
        }
        if (this.fp < this.rp) {
            return contains($r1, this.fp + 1, this.rp);
        }
        return contains($r1, this.fp + 1, this.qMaxSize + -1) || contains($r1, 0, this.rp);
    }

    private boolean contains(TCPIPPacket $r1, int $i0, int $i1) throws  {
        while ($i0 <= $i1) {
            if (this.f19q[$i0] == $r1) {
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
