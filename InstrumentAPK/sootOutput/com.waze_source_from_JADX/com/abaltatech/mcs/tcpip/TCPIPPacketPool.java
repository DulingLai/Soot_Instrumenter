package com.abaltatech.mcs.tcpip;

import com.abaltatech.mcs.common.MCSException;
import com.abaltatech.mcs.logger.MCSLogger;

public class TCPIPPacketPool {
    public static final int PacketCount = 2048;
    private static boolean m_dumpInfo = false;
    private static final DataQueueIPPacket m_freePackets = new DataQueueIPPacket(2048);
    private static boolean m_isInitialzied = false;

    public static void setDumpInfo(boolean $z0) throws  {
        synchronized (m_freePackets) {
            m_dumpInfo = $z0;
        }
    }

    public static boolean getDumpInfo() throws  {
        boolean z0;
        synchronized (m_freePackets) {
            z0 = m_dumpInfo;
        }
        return z0;
    }

    private static void init() throws MCSException {
        synchronized (m_freePackets) {
            for (int $i0 = 0; $i0 < 2048; $i0++) {
                m_freePackets.insert(new TCPIPPacket());
            }
            m_isInitialzied = true;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.abaltatech.mcs.tcpip.TCPIPPacket getPacket(java.lang.String r9) throws com.abaltatech.mcs.common.MCSException {
        /*
        r0 = m_freePackets;
        monitor-enter(r0);
        r1 = m_isInitialzied;	 Catch:{ Throwable -> 0x0054 }
        if (r1 != 0) goto L_0x000a;
    L_0x0007:
        init();	 Catch:{ Throwable -> 0x0054 }
    L_0x000a:
        r2 = m_freePackets;	 Catch:{ Throwable -> 0x0054 }
        r3 = r2.size();	 Catch:{ Throwable -> 0x0054 }
        if (r3 <= 0) goto L_0x004b;
    L_0x0012:
        r2 = m_freePackets;	 Catch:{ Throwable -> 0x0054 }
        r4 = r2.get();	 Catch:{ Throwable -> 0x0054 }
        r2 = m_freePackets;	 Catch:{ Throwable -> 0x0054 }
        r2.delete();	 Catch:{ Throwable -> 0x0054 }
        r1 = m_dumpInfo;	 Catch:{ Throwable -> 0x0054 }
        if (r1 == 0) goto L_0x0049;
    L_0x0021:
        r5 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0054 }
        r5.<init>();	 Catch:{ Throwable -> 0x0054 }
        r6 = "";
        r5 = r5.append(r6);	 Catch:{ Throwable -> 0x0054 }
        r2 = m_freePackets;	 Catch:{ Throwable -> 0x0054 }
        r3 = r2.size();	 Catch:{ Throwable -> 0x0054 }
        r5 = r5.append(r3);	 Catch:{ Throwable -> 0x0054 }
        r6 = "     ";
        r5 = r5.append(r6);	 Catch:{ Throwable -> 0x0054 }
        r5 = r5.append(r9);	 Catch:{ Throwable -> 0x0054 }
        r9 = r5.toString();	 Catch:{ Throwable -> 0x0054 }
        r6 = "- FREE TCPIP PACKETS";
        com.abaltatech.mcs.logger.MCSLogger.log(r6, r9);	 Catch:{ Throwable -> 0x0054 }
    L_0x0049:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0054 }
        return r4;
    L_0x004b:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0054 }
        r7 = new com.abaltatech.mcs.common.MCSException;
        r6 = "No free TCPIPPacket object";
        r7.<init>(r6);
        throw r7;
    L_0x0054:
        r8 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0054 }
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcs.tcpip.TCPIPPacketPool.getPacket(java.lang.String):com.abaltatech.mcs.tcpip.TCPIPPacket");
    }

    public static void freePacket(TCPIPPacket $r0) throws MCSException {
        if ($r0 == null) {
            throw new MCSException("TCPIP Packet is null in freePacket()");
        }
        synchronized (m_freePackets) {
            $r0.freeBuffer();
            $r0.setFlags(0);
            if (!m_isInitialzied) {
                throw new MCSException("TCPIPPacketPool is not initialized yet - freeing unknown TCPIPPacket");
            } else if (m_freePackets.contains($r0)) {
                throw new MCSException("TCPIPPacket freed twice");
            } else {
                m_freePackets.insert($r0);
                if (m_dumpInfo) {
                    MCSLogger.log("+ FREE TCPIP PACKETS", "" + m_freePackets.size() + "     " + $r0.toString());
                }
            }
        }
    }
}
