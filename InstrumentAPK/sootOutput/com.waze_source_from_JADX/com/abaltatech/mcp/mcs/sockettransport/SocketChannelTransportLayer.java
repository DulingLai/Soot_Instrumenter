package com.abaltatech.mcp.mcs.sockettransport;

import com.abaltatech.mcp.mcs.common.IMCSDataStats;
import com.abaltatech.mcp.mcs.common.MCSDataLayerBase;
import com.abaltatech.mcp.mcs.common.MCSException;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SocketChannelTransportLayer extends MCSDataLayerBase implements Runnable {
    static final /* synthetic */ boolean $assertionsDisabled = (!SocketChannelTransportLayer.class.desiredAssertionStatus());
    private static int ms_connID = 0;
    private SocketChannel m_clientChannel = null;
    private int m_connID;
    private SocketAddress m_hostAddress = null;
    private boolean m_isStopped = false;
    private Selector m_selector = null;
    private Thread m_thread;

    protected void writeDataInternal(byte[] r23, int r24) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:26:0x0079 in {4, 10, 13, 16, 20, 22, 25, 27, 36, 39, 49, 50, 51} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r22 = this;
        r0 = r22;
        r3 = r0.m_isStopped;
        if (r3 != 0) goto L_0x00f0;
    L_0x0006:
        r0 = r22;
        r4 = r0.m_clientChannel;
        if (r4 != 0) goto L_0x000d;
    L_0x000c:
        return;
    L_0x000d:
        r6 = 0;	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
        r0 = r23;	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
        r1 = r24;	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
        r5 = java.nio.ByteBuffer.wrap(r0, r6, r1);	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
        r0 = r24;
        r7 = (long) r0;
    L_0x0019:
        r10 = 0;
        r9 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1));
        if (r9 <= 0) goto L_0x00e0;	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
    L_0x001f:
        r0 = r22;
        r4 = r0.m_clientChannel;
        if (r4 == 0) goto L_0x00e0;	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
    L_0x0025:
        r3 = $assertionsDisabled;
        if (r3 != 0) goto L_0x007d;	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
    L_0x0029:
        r12 = r5.remaining();	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
        r13 = (long) r12;
        r9 = (r13 > r7 ? 1 : (r13 == r7 ? 0 : -1));
        if (r9 == 0) goto L_0x007d;	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
    L_0x0032:
        r15 = new java.lang.AssertionError;	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
        r15.<init>();	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
        throw r15;
    L_0x0038:
        r16 = move-exception;
        r17 = new java.lang.StringBuilder;
        r0 = r17;
        r0.<init>();
        r18 = "SOCKET_CHANNEL(";
        r0 = r17;
        r1 = r18;
        r17 = r0.append(r1);
        r0 = r22;
        r0 = r0.m_connID;
        r24 = r0;
        r0 = r17;
        r1 = r24;
        r17 = r0.append(r1);
        r18 = ")";
        r0 = r17;
        r1 = r18;
        r17 = r0.append(r1);
        r0 = r17;
        r19 = r0.toString();
        r18 = "EXCEPTION";
        r0 = r19;
        r1 = r18;
        r2 = r16;
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r0, r1, r2);
        r0 = r22;
        r0.closeConnection();
        return;
        goto L_0x007d;
    L_0x007a:
        goto L_0x0019;
    L_0x007d:
        r12 = r4.write(r5);	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
        r13 = (long) r12;
        r3 = $assertionsDisabled;
        if (r3 != 0) goto L_0x00d3;
    L_0x0086:
        r10 = 0;
        r9 = (r13 > r10 ? 1 : (r13 == r10 ? 0 : -1));
        if (r9 >= 0) goto L_0x00d3;
    L_0x008c:
        r15 = new java.lang.AssertionError;	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
        r15.<init>();	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
        throw r15;
    L_0x0092:
        r20 = move-exception;
        r17 = new java.lang.StringBuilder;
        r0 = r17;
        r0.<init>();
        r18 = "SOCKET_CHANNEL(";
        r0 = r17;
        r1 = r18;
        r17 = r0.append(r1);
        r0 = r22;
        r0 = r0.m_connID;
        r24 = r0;
        r0 = r17;
        r1 = r24;
        r17 = r0.append(r1);
        r18 = ")";
        r0 = r17;
        r1 = r18;
        r17 = r0.append(r1);
        r0 = r17;
        r19 = r0.toString();
        r18 = "EXCEPTION";
        r0 = r19;
        r1 = r18;
        r2 = r20;
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r0, r1, r2);
        r0 = r22;
        r0.closeConnection();
        return;
    L_0x00d3:
        r7 = r7 - r13;
        r10 = 0;
        r9 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1));
        if (r9 <= 0) goto L_0x0019;
    L_0x00da:
        r10 = 10;	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
        java.lang.Thread.sleep(r10);	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
        goto L_0x007a;
    L_0x00e0:
        r0 = r22;	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
        r21 = r0.getDataStats();	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
        if (r21 == 0) goto L_0x00f1;	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
    L_0x00e8:
        r0 = r21;	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
        r1 = r24;	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
        r0.onDataSent(r1);	 Catch:{ IOException -> 0x0038, InterruptedException -> 0x0092 }
        return;
    L_0x00f0:
        return;
    L_0x00f1:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.sockettransport.SocketChannelTransportLayer.writeDataInternal(byte[], int):void");
    }

    public SocketChannelTransportLayer(InetAddress $r1, int $i0) throws MCSException {
        int $i1 = ms_connID + 1;
        ms_connID = $i1;
        this.m_connID = $i1;
        try {
            this.m_hostAddress = new InetSocketAddress($r1, $i0);
            setChannel(SocketChannel.open(this.m_hostAddress));
        } catch (IOException $r2) {
            MCSLogger.log("SOCKET_CHANNEL(" + this.m_connID + ")", "Error establishing connection " + $r1 + ":" + $i0);
            throw new MCSException("Error establishing connection " + $r1 + ":" + $i0 + "\n" + $r2.toString());
        }
    }

    public SocketChannelTransportLayer(String $r1, int $i0) throws MCSException {
        int $i1 = ms_connID + 1;
        ms_connID = $i1;
        this.m_connID = $i1;
        try {
            this.m_hostAddress = new InetSocketAddress($r1, $i0);
            setChannel(SocketChannel.open(this.m_hostAddress));
        } catch (IOException $r2) {
            MCSLogger.log("SOCKET_CHANNEL(" + this.m_connID + ")", "Error establishing connection " + $r1 + ":" + $i0);
            throw new MCSException("Error establishing connection " + $r1 + ":" + $i0 + "\n" + $r2.toString());
        }
    }

    public SocketChannelTransportLayer(SocketChannel $r1) throws MCSException {
        int $i0 = ms_connID + 1;
        ms_connID = $i0;
        this.m_connID = $i0;
        MCSLogger.log("SocketTransportLayer", "Use existing socket");
        this.m_hostAddress = $r1.socket().getRemoteSocketAddress();
        setChannel($r1);
    }

    private void setChannel(SocketChannel $r1) throws MCSException {
        try {
            this.m_selector = Selector.open();
            this.m_clientChannel = $r1;
            this.m_clientChannel.configureBlocking(false);
            this.m_clientChannel.register(this.m_selector, 1);
            this.m_thread = new Thread(this);
            this.m_thread.setName("SocketChannelTransportLayer");
            this.m_thread.start();
            MCSLogger.log("SOCKET_CHANNEL(" + this.m_connID + ")", "Established connection to " + this.m_hostAddress);
        } catch (IOException $r2) {
            MCSLogger.log("SOCKET_CHANNEL(" + this.m_connID + ")", "Failed to setup socket selector: " + $r2.toString());
        }
    }

    public int readData(byte[] $r1, int $i0) throws  {
        if (this.m_isStopped || this.m_clientChannel == null) {
            return 0;
        }
        try {
            $i0 = this.m_clientChannel.read(ByteBuffer.wrap($r1, 0, $i0));
            if ($i0 == -1) {
                MCSLogger.log("SOCKET_CHANNEL(" + this.m_connID + ")", "Closed connection to " + this.m_hostAddress);
                closeConnection();
                return 0;
            }
            IMCSDataStats $r9 = getDataStats();
            if ($r9 == null) {
                return $i0;
            }
            $r9.onDataReceived($i0);
            return $i0;
        } catch (IOException $r2) {
            MCSLogger.log("EXCEPTION", $r2.toString());
            closeConnection();
        }
    }

    public void run() throws  {
        while (true) {
            try {
                Selector $r2 = this.m_selector;
                if (this.m_isStopped || this.m_clientChannel == null || $r2 == null) {
                    break;
                } else if ($r2.select(10) > 0) {
                    Iterator $r7 = $r2.selectedKeys().iterator();
                    while ($r7.hasNext()) {
                        SelectionKey $r9 = (SelectionKey) $r7.next();
                        $r7.remove();
                        if ($r9.isValid() && $r9.isReadable() && !notifyForData()) {
                            Thread.sleep(10);
                        }
                    }
                }
            } catch (Exception $r1) {
                MCSLogger.log("SOCKET_CHANNEL(" + this.m_connID + ")", "EXCEPTION", $r1);
                closeConnection();
            }
        }
        MCSLogger.log("SOCKET_CHANNEL(" + this.m_connID + ")", " Reading thread exited");
    }

    public void closeConnection() throws  {
        if (!this.m_isStopped) {
            this.m_isStopped = true;
            if (this.m_clientChannel != null) {
                try {
                    this.m_clientChannel.close();
                } catch (IOException e) {
                }
                this.m_clientChannel = null;
            }
            if (this.m_selector != null) {
                try {
                    this.m_selector.close();
                } catch (IOException e2) {
                }
                this.m_selector = null;
            }
        }
        notifyForConnectionClosed();
        clearNotifiables();
    }
}
