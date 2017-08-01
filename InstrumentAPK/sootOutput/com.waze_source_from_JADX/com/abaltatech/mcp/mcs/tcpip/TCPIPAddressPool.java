package com.abaltatech.mcp.mcs.tcpip;

import com.abaltatech.mcp.mcs.common.MCSException;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import java.net.InetAddress;

public class TCPIPAddressPool {
    private static final int PoolSize = 6144;
    private static boolean m_dumpInfo = false;
    private static final DataQueueIPAddress m_freeAddresses = new DataQueueIPAddress(PoolSize);
    private static boolean m_isInitialzied = false;

    public static void setDumpInfo(boolean $z0) throws  {
        synchronized (m_freeAddresses) {
            m_dumpInfo = $z0;
        }
    }

    public static boolean getDumpInfo() throws  {
        boolean z0;
        synchronized (m_freeAddresses) {
            z0 = m_dumpInfo;
        }
        return z0;
    }

    private static void init() throws MCSException {
        synchronized (m_freeAddresses) {
            for (int $i0 = 0; $i0 < PoolSize; $i0++) {
                m_freeAddresses.insert(new TCPIPAddress());
            }
            m_isInitialzied = true;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.abaltatech.mcp.mcs.tcpip.TCPIPAddress getAddress() throws com.abaltatech.mcp.mcs.common.MCSException {
        /*
        r0 = m_freeAddresses;
        monitor-enter(r0);
        r1 = m_isInitialzied;	 Catch:{ Throwable -> 0x0045 }
        if (r1 != 0) goto L_0x000a;
    L_0x0007:
        init();	 Catch:{ Throwable -> 0x0045 }
    L_0x000a:
        r2 = m_freeAddresses;	 Catch:{ Throwable -> 0x0045 }
        r3 = r2.size();	 Catch:{ Throwable -> 0x0045 }
        if (r3 <= 0) goto L_0x003c;
    L_0x0012:
        r2 = m_freeAddresses;	 Catch:{ Throwable -> 0x0045 }
        r4 = r2.delete();	 Catch:{ Throwable -> 0x0045 }
        r1 = m_dumpInfo;	 Catch:{ Throwable -> 0x0045 }
        if (r1 == 0) goto L_0x003a;
    L_0x001c:
        r5 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0045 }
        r5.<init>();	 Catch:{ Throwable -> 0x0045 }
        r6 = "";
        r5 = r5.append(r6);	 Catch:{ Throwable -> 0x0045 }
        r2 = m_freeAddresses;	 Catch:{ Throwable -> 0x0045 }
        r3 = r2.size();	 Catch:{ Throwable -> 0x0045 }
        r5 = r5.append(r3);	 Catch:{ Throwable -> 0x0045 }
        r7 = r5.toString();	 Catch:{ Throwable -> 0x0045 }
        r6 = "- FREE ADDRESSES";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r6, r7);	 Catch:{ Throwable -> 0x0045 }
    L_0x003a:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0045 }
        return r4;
    L_0x003c:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0045 }
        r8 = new com.abaltatech.mcp.mcs.common.MCSException;
        r6 = "No free TCPIPAddress object";
        r8.<init>(r6);
        throw r8;
    L_0x0045:
        r9 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0045 }
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.tcpip.TCPIPAddressPool.getAddress():com.abaltatech.mcp.mcs.tcpip.TCPIPAddress");
    }

    public static void freeAddress(TCPIPAddress $r0) throws MCSException {
        if ($r0 != null) {
            synchronized (m_freeAddresses) {
                if (m_isInitialzied) {
                    m_freeAddresses.insert($r0);
                    if (m_dumpInfo) {
                        MCSLogger.log("+ FREE ADDRESSES", "" + m_freeAddresses.size());
                    }
                } else {
                    throw new MCSException("TCPIPAddressPool is not initialized yet - freeing unknown TCPIPAddress");
                }
            }
        }
    }

    public static TCPIPAddress getAddress(byte[] $r0, int $i0) throws MCSException {
        TCPIPAddress $r1 = getAddress();
        $r1.copyFrom($r0, $i0);
        return $r1;
    }

    public static TCPIPAddress getAddress(InetAddress $r0, int $i0) throws MCSException {
        TCPIPAddress $r1 = getAddress();
        $r1.copyFrom($r0, $i0);
        return $r1;
    }

    public static TCPIPAddress copyAddress(TCPIPAddress $r0) throws MCSException {
        TCPIPAddress $r1 = getAddress();
        $r1.copyFrom($r0);
        return $r1;
    }
}
