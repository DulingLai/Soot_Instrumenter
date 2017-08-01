package com.abaltatech.mcp.mcs.mtp;

public class MTPPacketPool {
    static final /* synthetic */ boolean $assertionsDisabled = (!MTPPacketPool.class.desiredAssertionStatus());
    private static MTPPacket m_freePackets = null;

    public static MTPPacket GetPacket() throws  {
        MTPPacket $r0;
        synchronized (MTPPacketPool.class) {
            try {
                if (m_freePackets != null) {
                    $r0 = m_freePackets;
                    m_freePackets = $r0.m_nextPacket;
                    $r0.m_nextPacket = null;
                } else {
                    $r0 = new MTPPacket();
                }
            } catch (Throwable th) {
                Class cls = MTPPacketPool.class;
            }
        }
        return $r0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void ReleasePacket(com.abaltatech.mcp.mcs.mtp.MTPPacket r5) throws  {
        /*
        r0 = $assertionsDisabled;
        if (r0 != 0) goto L_0x000c;
    L_0x0004:
        if (r5 != 0) goto L_0x000c;
    L_0x0006:
        r1 = new java.lang.AssertionError;
        r1.<init>();
        throw r1;
    L_0x000c:
        if (r5 == 0) goto L_0x0020;
    L_0x000e:
        r2 = com.abaltatech.mcp.mcs.mtp.MTPPacketPool.class;
        monitor-enter(r2);
        r3 = m_freePackets;	 Catch:{ Throwable -> 0x001b }
        r5.m_nextPacket = r3;	 Catch:{ Throwable -> 0x001b }
        m_freePackets = r5;	 Catch:{ Throwable -> 0x001b }
        r2 = com.abaltatech.mcp.mcs.mtp.MTPPacketPool.class;
        monitor-exit(r2);	 Catch:{ Throwable -> 0x001b }
        return;
    L_0x001b:
        r4 = move-exception;
        r2 = com.abaltatech.mcp.mcs.mtp.MTPPacketPool.class;
        monitor-exit(r2);	 Catch:{ Throwable -> 0x001b }
        throw r4;
    L_0x0020:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.mtp.MTPPacketPool.ReleasePacket(com.abaltatech.mcp.mcs.mtp.MTPPacket):void");
    }
}
