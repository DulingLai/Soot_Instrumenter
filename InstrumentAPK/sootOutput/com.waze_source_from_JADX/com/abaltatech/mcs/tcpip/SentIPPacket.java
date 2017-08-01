package com.abaltatech.mcs.tcpip;

import java.util.Date;

public class SentIPPacket {
    public long AcknowledgmentTime;
    public int End;
    public TCPIPPacket IPPacket;
    public int ResentCount = 0;
    public int Start;

    public SentIPPacket(TCPIPPacket $r1, int $i0) throws  {
        this.IPPacket = $r1;
        this.Start = $r1.getSeqNo();
        this.End = this.Start + $r1.getDataLength();
        this.AcknowledgmentTime = new Date().getTime() + ((long) $i0);
    }
}
