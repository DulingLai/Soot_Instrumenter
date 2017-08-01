package com.abaltatech.mcp.mcs.tcpip;

import com.abaltatech.mcp.mcs.common.IMCSConnectionAddress;
import com.abaltatech.mcp.mcs.common.IMCSDataLayer;
import com.abaltatech.mcp.mcs.common.IMCSDataLayerNotification;
import com.abaltatech.mcp.mcs.common.MCSException;
import com.abaltatech.mcp.mcs.common.MCSMultiPointLayerBase;
import com.abaltatech.mcp.mcs.common.MemoryPool;
import com.abaltatech.mcp.mcs.utils.DataQueueArr;

public class TCPIPLayer extends MCSMultiPointLayerBase implements IMCSDataLayerNotification, Runnable {
    private static final int MAX_CONNECTIONS = 1024;
    private int m_bufSize = this.m_inBuffer.length;
    private ConnectionPointTCPIP[] m_connections = new ConnectionPointTCPIP[1024];
    private byte[] m_inBuffer = MemoryPool.getMem(MemoryPool.BufferSizeBig, "TCPIPPacket");
    private DataQueueArr m_outgoingBuffers = new DataQueueArr(32768);
    private Thread m_thread = new Thread(this);

    private void processIncommingData() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x007a in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r17 = this;
        monitor-enter(r17);
        r0 = r17;
        r1 = r0.m_dataLayer;
        r17 = r0;
        if (r1 != 0) goto L_0x000b;
    L_0x0009:
        monitor-exit(r17);
        return;
    L_0x000b:
        r0 = r17;
        r1 = r0.m_dataLayer;
        r17 = r0;
        monitor-exit(r17);
        r0 = r17;
        r2 = r0.m_inBuffer;
        r0 = r17;
        r3 = r0.m_bufSize;
        r3 = r1.readData(r2, r3);
        if (r3 <= 0) goto L_0x00cf;
    L_0x0020:
        r5 = "onDataReceived";	 Catch:{ MCSException -> 0x004f }
        r4 = com.abaltatech.mcp.mcs.tcpip.TCPIPPacketPool.getPacket(r5);	 Catch:{ MCSException -> 0x004f }
        r0 = r17;	 Catch:{ MCSException -> 0x004f }
        r2 = r0.m_inBuffer;	 Catch:{ MCSException -> 0x004f }
        r4.setBuffer(r2, r3);	 Catch:{ MCSException -> 0x004f }
        r6 = r4.isPacketOK();	 Catch:{ MCSException -> 0x004f }
        if (r6 == 0) goto L_0x00b2;	 Catch:{ MCSException -> 0x004f }
    L_0x0033:
        r7 = r4.toString();	 Catch:{ MCSException -> 0x004f }
        r5 = " IN IP PACKET";	 Catch:{ MCSException -> 0x004f }
        r0 = r17;	 Catch:{ MCSException -> 0x004f }
        r0.dumpInfo(r5, r7);	 Catch:{ MCSException -> 0x004f }
        r0 = r17;	 Catch:{ MCSException -> 0x004f }
        r6 = r0.processPacket(r4);	 Catch:{ MCSException -> 0x004f }
        if (r6 == 0) goto L_0x005d;	 Catch:{ MCSException -> 0x004f }
    L_0x0046:
        r8 = 0;	 Catch:{ MCSException -> 0x004f }
        r9 = 0;	 Catch:{ MCSException -> 0x004f }
        r4.setBuffer(r8, r9);	 Catch:{ MCSException -> 0x004f }
        com.abaltatech.mcp.mcs.tcpip.TCPIPPacketPool.freePacket(r4);	 Catch:{ MCSException -> 0x004f }
        return;
    L_0x004f:
        r10 = move-exception;
        r7 = r10.toString();
        r5 = "ERROR";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r5, r7);
        return;
    L_0x005a:
        r11 = move-exception;
        monitor-exit(r17);
        throw r11;
    L_0x005d:
        r3 = r4.getFlags();	 Catch:{ MCSException -> 0x004f }
        r3 = r3 & 2;
        r9 = 2;	 Catch:{ MCSException -> 0x004f }
        if (r3 != r9) goto L_0x00a1;	 Catch:{ MCSException -> 0x004f }
    L_0x0066:
        r3 = r4.getDataLength();	 Catch:{ MCSException -> 0x004f }
        r2 = new byte[r3];
        if (r3 <= 0) goto L_0x007a;	 Catch:{ MCSException -> 0x004f }
    L_0x006e:
        r12 = r4.getBuffer();	 Catch:{ MCSException -> 0x004f }
        r13 = r4.getDataOffset();	 Catch:{ MCSException -> 0x004f }
        r9 = 0;	 Catch:{ MCSException -> 0x004f }
        java.lang.System.arraycopy(r12, r13, r2, r9, r3);	 Catch:{ MCSException -> 0x004f }
    L_0x007a:
        r12 = r4.getSourceIPAddress();	 Catch:{ MCSException -> 0x004f }
        r3 = r4.getSourcePort();	 Catch:{ MCSException -> 0x004f }
        r0 = r17;	 Catch:{ MCSException -> 0x004f }
        r14 = r0.createAddress(r12, r3);	 Catch:{ MCSException -> 0x004f }
        r12 = r4.getDestIPAddress();	 Catch:{ MCSException -> 0x004f }
        r3 = r4.getDestPort();	 Catch:{ MCSException -> 0x004f }
        r0 = r17;	 Catch:{ MCSException -> 0x004f }
        r15 = r0.createAddress(r12, r3);	 Catch:{ MCSException -> 0x004f }
        r0 = r17;	 Catch:{ MCSException -> 0x004f }
        r0.notifyForNewConnection(r14, r15, r2);	 Catch:{ MCSException -> 0x004f }
        com.abaltatech.mcp.mcs.tcpip.TCPIPAddressPool.freeAddress(r14);	 Catch:{ MCSException -> 0x004f }
        com.abaltatech.mcp.mcs.tcpip.TCPIPAddressPool.freeAddress(r15);	 Catch:{ MCSException -> 0x004f }
    L_0x00a1:
        r0 = r17;	 Catch:{ MCSException -> 0x004f }
        r6 = r0.processPacket(r4);	 Catch:{ MCSException -> 0x004f }
        if (r6 == 0) goto L_0x00c6;	 Catch:{ MCSException -> 0x004f }
    L_0x00a9:
        r8 = 0;	 Catch:{ MCSException -> 0x004f }
        r9 = 0;	 Catch:{ MCSException -> 0x004f }
        r4.setBuffer(r8, r9);	 Catch:{ MCSException -> 0x004f }
        com.abaltatech.mcp.mcs.tcpip.TCPIPPacketPool.freePacket(r4);	 Catch:{ MCSException -> 0x004f }
        return;
    L_0x00b2:
        r16 = r4.getProtocolType();	 Catch:{ MCSException -> 0x004f }
        r9 = 1;	 Catch:{ MCSException -> 0x004f }
        r0 = r16;	 Catch:{ MCSException -> 0x004f }
        if (r0 != r9) goto L_0x00c6;	 Catch:{ MCSException -> 0x004f }
    L_0x00bb:
        r7 = r4.toString();	 Catch:{ MCSException -> 0x004f }
        r5 = "WRN IP PACKET";	 Catch:{ MCSException -> 0x004f }
        r0 = r17;	 Catch:{ MCSException -> 0x004f }
        r0.dumpInfo(r5, r7);	 Catch:{ MCSException -> 0x004f }
    L_0x00c6:
        r8 = 0;	 Catch:{ MCSException -> 0x004f }
        r9 = 0;	 Catch:{ MCSException -> 0x004f }
        r4.setBuffer(r8, r9);	 Catch:{ MCSException -> 0x004f }
        com.abaltatech.mcp.mcs.tcpip.TCPIPPacketPool.freePacket(r4);	 Catch:{ MCSException -> 0x004f }
        return;
    L_0x00cf:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.tcpip.TCPIPLayer.processIncommingData():void");
    }

    public TCPIPLayer() throws MCSException {
        this.m_thread.start();
    }

    public void onConnectionClosed(IMCSDataLayer connection) throws  {
        synchronized (this) {
            this.m_dataLayer = null;
            for (int $i0 = 0; $i0 < 1024; $i0++) {
                if (this.m_connections[$i0] != null) {
                    ConnectionPointTCPIP $r2 = this.m_connections[$i0];
                    this.m_connections[$i0] = null;
                    notifyForConnectionClosed($r2, $r2.getFromAddress(), $r2.getToAddress());
                    $r2.closeConnection();
                }
            }
        }
    }

    public void onDataReceived(IMCSDataLayer connection) throws  {
    }

    private TCPIPAddress createAddress(byte[] $r1, int $i0) throws MCSException {
        return TCPIPAddressPool.getAddress($r1, $i0);
    }

    public void closeConnection(IMCSDataLayer $r1) throws MCSException {
        if ($r1 instanceof ConnectionPointTCPIP) {
            ConnectionPointTCPIP $r3 = (ConnectionPointTCPIP) $r1;
            synchronized (this) {
                int $i0 = 0;
                while ($i0 < 1024) {
                    if (this.m_connections[$i0] == $r3) {
                        $r3 = this.m_connections[$i0];
                        this.m_connections[$i0] = null;
                        notifyForConnectionClosed($r3, $r3.getFromAddress(), $r3.getToAddress());
                        $r3.closeConnection();
                    } else {
                        $i0++;
                    }
                }
                throw new MCSException("Invalid connection point in closeConnection() - may be already closed");
            }
            return;
        }
        throw new MCSException("Invalid connection point in closeConnection()");
    }

    public IMCSDataLayer createConnectionPoint(IMCSConnectionAddress $r1, IMCSConnectionAddress $r2) throws MCSException {
        ConnectionPointTCPIP $r3;
        synchronized (this) {
            int $i0 = 1024;
            for (int $i1 = 0; $i1 < 1024; $i1++) {
                if (this.m_connections[$i1] != null) {
                    if (this.m_connections[$i1].canHandle($r1, $r2)) {
                        throw new MCSException("Connection in use");
                    }
                } else if ($i1 < $i0) {
                    $i0 = $i1;
                }
            }
            if ($i0 < 1024) {
                $r3 = new ConnectionPointTCPIP(this, $r1, $r2);
                this.m_connections[$i0] = $r3;
            } else {
                throw new MCSException("Too many connection points");
            }
        }
        return $r3;
    }

    public void sendMessage(TCPIPPacket $r1) throws MCSException {
        if ($r1.getDataLength() == 0) {
            doWriteData($r1);
            return;
        }
        byte[] $r3 = $r1.getBuffer();
        int $i0 = $r1.getBufferLength();
        byte[] $r2 = new byte[$i0];
        System.arraycopy($r3, 0, $r2, 0, $i0);
        synchronized (this.m_outgoingBuffers) {
            this.m_outgoingBuffers.insert($r2);
        }
    }

    public void run() throws  {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            if (this.m_dataLayer != null) {
                byte[] $r2 = null;
                synchronized (this.m_outgoingBuffers) {
                    if (this.m_outgoingBuffers.size() > 0) {
                        try {
                            $r2 = this.m_outgoingBuffers.delete();
                        } catch (MCSException e2) {
                        }
                    }
                }
                if ($r2 != null) {
                    doWriteData($r2, $r2.length, " SIZE: " + $r2.length);
                }
                if (this.m_dataLayer != null) {
                    processIncommingData();
                } else {
                    return;
                }
            }
            return;
        }
    }

    private synchronized boolean processPacket(TCPIPPacket $r1) throws MCSException {
        boolean $z0;
        for (int $i0 = 0; $i0 < 1024; $i0++) {
            if (this.m_connections[$i0] != null) {
                ConnectionPointTCPIP $r2 = this.m_connections[$i0];
                if ($r2.isIPPacketForMe($r1)) {
                    $r2.processPacket($r1);
                    $z0 = true;
                    break;
                }
            }
        }
        $z0 = false;
        return $z0;
    }

    private void doWriteData(TCPIPPacket $r1) throws  {
        doWriteData($r1.getBuffer(), $r1.getBufferLength(), $r1.toString());
        $r1.m_isLocked = false;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void doWriteData(byte[] r5, int r6, java.lang.String r7) throws  {
        /*
        r4 = this;
        monitor-enter(r4);
        r0 = r4.m_dataLayer;	 Catch:{ Throwable -> 0x0017 }
        if (r0 != 0) goto L_0x0007;
    L_0x0005:
        monitor-exit(r4);	 Catch:{ Throwable -> 0x0017 }
        return;
    L_0x0007:
        r1 = r4.m_dumpInfo;	 Catch:{ Throwable -> 0x0017 }
        r0 = r4.m_dataLayer;	 Catch:{ Throwable -> 0x0017 }
        monitor-exit(r4);	 Catch:{ Throwable -> 0x0017 }
        if (r1 == 0) goto L_0x0013;
    L_0x000e:
        r2 = "OUT IP PACKET";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r2, r7);
    L_0x0013:
        r0.writeData(r5, r6);
        return;
    L_0x0017:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ Throwable -> 0x0017 }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.tcpip.TCPIPLayer.doWriteData(byte[], int, java.lang.String):void");
    }
}
