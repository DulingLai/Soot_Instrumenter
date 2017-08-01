package com.abaltatech.mcs.tcpip;

import com.abaltatech.mcs.common.IMCSDataLayer;
import com.abaltatech.mcs.common.MCSException;
import com.abaltatech.mcs.logger.MCSLogger;
import java.util.Date;

public class TCPSlidingWindow {
    private static final int MaxPacketsToResend = 1;
    private static final int MaxPacketsToSend = 10;
    private static final int MaxPacketsToWait = 1000;
    private static final int MaxResendAttempts = 20;
    private static final int MaxResendBeforeSleep = 4;
    private static final int MaxTimeout = 1000;
    IMCSDataLayer m_connectionPoint;
    private int m_lastAck = 0;
    private int m_sendNextPos;
    private DataQueueSentIPPacket m_sentQ = new DataQueueSentIPPacket(20);
    private int m_startPos;
    private DataQueueIPPacket m_waitQ = new DataQueueIPPacket(1000);
    private int m_wndSize;
    private TCPIPLayer m_writer;

    public TCPSlidingWindow(TCPIPLayer $r1, int $i0, int $i1, IMCSDataLayer $r2) throws  {
        this.m_writer = $r1;
        this.m_wndSize = $i0;
        this.m_startPos = $i1;
        this.m_sendNextPos = this.m_startPos;
        this.m_connectionPoint = $r2;
    }

    private boolean resendIPPackets() throws MCSException {
        int $i0 = this.m_sentQ.size();
        if ($i0 > 0) {
            SentIPPacket $r3 = this.m_sentQ.get();
            long $l1 = new Date().getTime();
            if ($r3.AcknowledgmentTime < $l1) {
                if ($i0 >= 1) {
                    $i0 = 1;
                }
                for (int $i4 = 0; $i4 < $i0; $i4++) {
                    $r3 = this.m_sentQ.get($i4);
                    $r3.AcknowledgmentTime = 1000 + $l1;
                    $r3.ResentCount++;
                    this.m_writer.sendMessage($r3.IPPacket);
                    if ($i4 == 0 && $r3.ResentCount >= 4) {
                        if ($r3.ResentCount % 4 == 0) {
                            try {
                                Thread.sleep(4000);
                            } catch (InterruptedException e) {
                            }
                        }
                        if ($r3.ResentCount > 20) {
                            IMCSDataLayer $r6 = this.m_connectionPoint;
                            $r6.closeConnection();
                            this.m_connectionPoint = null;
                            MCSLogger.log("TCPSlidingWindow", "Closing connection!");
                            return true;
                        }
                    }
                }
                MCSLogger.log("TCPSlidingWindow", "Resent packets: " + $i0);
                return true;
            }
        }
        return false;
    }

    public synchronized void processIPPackets() throws MCSException {
        if (resendIPPackets()) {
        }
        if (this.m_connectionPoint != null) {
            int $i1 = usableWindow();
            int $i0 = this.m_waitQ.size();
            int $i2 = this.m_sentQ.size();
            while ($i1 > 0 && $i2 < 10 && $i0 > 0) {
                TCPIPPacket $r5 = this.m_waitQ.get();
                if ($r5.getDataLength() > $i1) {
                    break;
                }
                SentIPPacket $r1 = new SentIPPacket($r5, 1000);
                this.m_sentQ.insert($r1);
                $i0--;
                $i2++;
                this.m_waitQ.delete();
                this.m_sendNextPos = $r1.End + 1;
                $i1 = usableWindow();
                this.m_writer.sendMessage($r5);
            }
        }
    }

    public synchronized void acknowledgeIPPacket(int $i0, int $i1, int flags) throws  {
        if (!(this.m_sentQ.size() == 0 || this.m_connectionPoint == null)) {
            int i;
            if ($i0 < this.m_startPos || $i0 > this.m_sendNextPos) {
                i = this.m_lastAck;
                $i1 = i;
                if (i != $i0) {
                    MCSLogger.log("Warning", "Invalid ACK");
                }
            } else {
                if ($i0 + $i1 < (this.m_startPos + this.m_wndSize) - 1) {
                    this.m_lastAck = $i0;
                } else {
                    this.m_lastAck = $i0;
                }
                if (this.m_sentQ.size() > 0) {
                    SentIPPacket $r5 = getSentPacket(false);
                    while (true) {
                        i = $r5.End;
                        flags = i;
                        if (i > $i0) {
                            break;
                        }
                        try {
                            this.m_sentQ.delete();
                        } catch (MCSException e) {
                        }
                        this.m_writer.dumpInfo("ACK IP PACKET", $r5.IPPacket.toString());
                        $r5.IPPacket.freeBuffer();
                        try {
                            TCPIPPacketPool.freePacket($r5.IPPacket);
                        } catch (MCSException $r1) {
                            MCSLogger.log("ERROR", $r1.toString());
                        }
                        this.m_startPos = $r5.End + 1;
                        if (this.m_sentQ.size() <= 0) {
                            break;
                        }
                        $r5 = getSentPacket(false);
                    }
                }
                this.m_wndSize = $i1;
            }
        }
    }

    private SentIPPacket getSentPacket(boolean $z0) throws  {
        if (!$z0) {
            return this.m_sentQ.get();
        }
        try {
            return this.m_sentQ.delete();
        } catch (MCSException e) {
            return null;
        }
    }

    public synchronized void addIPPacket(TCPIPPacket $r1) throws MCSException {
        if (this.m_connectionPoint == null) {
            MCSLogger.log("Warning", "Output data IP packet is sent after connection is closed");
            $r1.freeBuffer();
            TCPIPPacketPool.freePacket($r1);
        } else if ($r1.getBuffer() == null) {
            MCSLogger.log("ERROR", "Empty IP Packet buffer");
            TCPIPPacketPool.freePacket($r1);
        } else {
            this.m_waitQ.insert($r1);
        }
    }

    public int usableWindow() throws  {
        return (this.m_wndSize - this.m_sendNextPos) + this.m_startPos;
    }

    public synchronized void close() throws  {
        this.m_connectionPoint = null;
        while (this.m_sentQ.size() > 0) {
            SentIPPacket $r4 = getSentPacket(true);
            $r4.IPPacket.freeBuffer();
            try {
                TCPIPPacketPool.freePacket($r4.IPPacket);
            } catch (MCSException $r2) {
                MCSLogger.log("ERROR", $r2.toString());
            }
        }
        while (this.m_waitQ.size() > 0) {
            try {
                TCPIPPacket $r5 = this.m_waitQ.delete();
                $r5.freeBuffer();
                TCPIPPacketPool.freePacket($r5);
            } catch (MCSException $r1) {
                MCSLogger.log("ERROR", $r1.toString());
            }
        }
    }
}
