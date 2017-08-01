package com.abaltatech.mcs.sockettransport;

import com.abaltatech.mcs.common.MCSDataLayerBase;
import com.abaltatech.mcs.common.MCSException;
import com.abaltatech.mcs.logger.MCSLogger;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SocketChannelTransportLayer extends MCSDataLayerBase implements Runnable {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final String TAG = "SocketChannelTransportLayer";
    private static int ms_connID = 0;
    private SocketChannel m_clientChannel = null;
    private int m_connID;
    private boolean m_dumpInfo = false;
    private SocketAddress m_hostAddress = null;
    private boolean m_isStopped = false;
    private Selector m_selector = null;
    private Thread m_thread;

    public int readData(byte[] r12, int r13) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x004e in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
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
        r11 = this;
        r0 = r11.m_isStopped;
        if (r0 != 0) goto L_0x0008;
    L_0x0004:
        r1 = r11.m_clientChannel;
        if (r1 != 0) goto L_0x000a;
    L_0x0008:
        r2 = 0;
        return r2;
    L_0x000a:
        r1 = r11.m_clientChannel;
        r2 = 0;	 Catch:{ IOException -> 0x0082 }
        r3 = java.nio.ByteBuffer.wrap(r12, r2, r13);	 Catch:{ IOException -> 0x0082 }
        r13 = r1.read(r3);	 Catch:{ IOException -> 0x0082 }
        r2 = -1;
        if (r13 != r2) goto L_0x0050;
    L_0x0018:
        r4 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0082 }
        r4.<init>();	 Catch:{ IOException -> 0x0082 }
        r5 = "SOCKET_CHANNEL(";	 Catch:{ IOException -> 0x0082 }
        r4 = r4.append(r5);	 Catch:{ IOException -> 0x0082 }
        r13 = r11.m_connID;	 Catch:{ IOException -> 0x0082 }
        r4 = r4.append(r13);	 Catch:{ IOException -> 0x0082 }
        r5 = ")";	 Catch:{ IOException -> 0x0082 }
        r4 = r4.append(r5);	 Catch:{ IOException -> 0x0082 }
        r6 = r4.toString();	 Catch:{ IOException -> 0x0082 }
        r4 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0082 }
        r4.<init>();	 Catch:{ IOException -> 0x0082 }
        r5 = "Closed connection to ";	 Catch:{ IOException -> 0x0082 }
        r4 = r4.append(r5);	 Catch:{ IOException -> 0x0082 }
        r7 = r11.m_hostAddress;	 Catch:{ IOException -> 0x0082 }
        r4 = r4.append(r7);	 Catch:{ IOException -> 0x0082 }
        r8 = r4.toString();	 Catch:{ IOException -> 0x0082 }
        com.abaltatech.mcs.logger.MCSLogger.log(r6, r8);	 Catch:{ IOException -> 0x0082 }
        r11.closeConnection();	 Catch:{ IOException -> 0x0082 }
    L_0x004e:
        r2 = 0;
        return r2;
    L_0x0050:
        if (r13 <= 0) goto L_0x004e;	 Catch:{ IOException -> 0x0082 }
    L_0x0052:
        r9 = r11.getDataStats();	 Catch:{ IOException -> 0x0082 }
        if (r9 == 0) goto L_0x005b;	 Catch:{ IOException -> 0x0082 }
    L_0x0058:
        r9.onDataReceived(r13);	 Catch:{ IOException -> 0x0082 }
    L_0x005b:
        r0 = r11.m_dumpInfo;
        if (r0 == 0) goto L_0x0090;
    L_0x005f:
        r4 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0082 }
        r4.<init>();	 Catch:{ IOException -> 0x0082 }
        r5 = "Received ";	 Catch:{ IOException -> 0x0082 }
        r4 = r4.append(r5);	 Catch:{ IOException -> 0x0082 }
        r4 = r4.append(r13);	 Catch:{ IOException -> 0x0082 }
        r5 = " bytes.";	 Catch:{ IOException -> 0x0082 }
        r4 = r4.append(r5);	 Catch:{ IOException -> 0x0082 }
        r6 = r4.toString();	 Catch:{ IOException -> 0x0082 }
        r5 = "SocketChannelTransportLayer";	 Catch:{ IOException -> 0x0082 }
        com.abaltatech.mcs.logger.MCSLogger.log(r5, r6);	 Catch:{ IOException -> 0x0082 }
        r2 = 0;	 Catch:{ IOException -> 0x0082 }
        com.abaltatech.mcs.utils.ByteUtils.dumpBuffer(r12, r2, r13);	 Catch:{ IOException -> 0x0082 }
        return r13;
    L_0x0082:
        r10 = move-exception;
        r6 = r10.toString();
        r5 = "EXCEPTION";
        com.abaltatech.mcs.logger.MCSLogger.log(r5, r6);
        r11.closeConnection();
        goto L_0x004e;
    L_0x0090:
        return r13;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcs.sockettransport.SocketChannelTransportLayer.readData(byte[], int):int");
    }

    protected void writeDataInternal(byte[] r21, int r22) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x008a in list [B:25:0x003e, B:37:0x0067]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
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
        r20 = this;
        r0 = r20;
        r2 = r0.m_isStopped;
        if (r2 != 0) goto L_0x009a;
    L_0x0006:
        r0 = r20;
        r3 = r0.m_clientChannel;
        if (r3 != 0) goto L_0x000d;
    L_0x000c:
        return;
    L_0x000d:
        r0 = r20;
        r2 = r0.m_dumpInfo;
        if (r2 == 0) goto L_0x0013;
    L_0x0013:
        r5 = 0;	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
        r0 = r21;	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
        r1 = r22;	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
        r4 = java.nio.ByteBuffer.wrap(r0, r5, r1);	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
        r0 = r22;
        r6 = (long) r0;
    L_0x001f:
        r9 = 0;
        r8 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1));
        if (r8 <= 0) goto L_0x008a;	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
    L_0x0025:
        r0 = r20;
        r3 = r0.m_clientChannel;
        if (r3 == 0) goto L_0x008a;	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
    L_0x002b:
        r2 = $assertionsDisabled;
        if (r2 != 0) goto L_0x0052;	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
    L_0x002f:
        r11 = r4.remaining();	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
        r12 = (long) r11;
        r8 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1));
        if (r8 == 0) goto L_0x0052;	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
    L_0x0038:
        r14 = new java.lang.AssertionError;	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
        r14.<init>();	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
        throw r14;
    L_0x003e:
        r15 = move-exception;
        r16 = r15.toString();
        r17 = "EXCEPTION";
        r0 = r17;
        r1 = r16;
        com.abaltatech.mcs.logger.MCSLogger.log(r0, r1);
        r0 = r20;
        r0.closeConnection();
        return;
    L_0x0052:
        r11 = r3.write(r4);	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
        r12 = (long) r11;
        r2 = $assertionsDisabled;
        if (r2 != 0) goto L_0x007d;
    L_0x005b:
        r9 = 0;
        r8 = (r12 > r9 ? 1 : (r12 == r9 ? 0 : -1));
        if (r8 >= 0) goto L_0x007d;
    L_0x0061:
        r14 = new java.lang.AssertionError;	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
        r14.<init>();	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
        throw r14;
    L_0x0067:
        r18 = move-exception;
        r0 = r18;
        r16 = r0.toString();
        r17 = "EXCEPTION";
        r0 = r17;
        r1 = r16;
        com.abaltatech.mcs.logger.MCSLogger.log(r0, r1);
        r0 = r20;
        r0.closeConnection();
        return;
    L_0x007d:
        r6 = r6 - r12;
        r9 = 0;
        r8 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1));
        if (r8 <= 0) goto L_0x001f;
    L_0x0084:
        r9 = 10;	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
        java.lang.Thread.sleep(r9);	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
        goto L_0x001f;
    L_0x008a:
        r0 = r20;	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
        r19 = r0.getDataStats();	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
        if (r19 == 0) goto L_0x009b;	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
    L_0x0092:
        r0 = r19;	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
        r1 = r22;	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
        r0.onDataSent(r1);	 Catch:{ IOException -> 0x003e, InterruptedException -> 0x0067 }
        return;
    L_0x009a:
        return;
    L_0x009b:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcs.sockettransport.SocketChannelTransportLayer.writeDataInternal(byte[], int):void");
    }

    static {
        boolean $z0;
        if (SocketChannelTransportLayer.class.desiredAssertionStatus()) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        $assertionsDisabled = $z0;
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
            this.m_thread.setName(TAG);
            this.m_thread.start();
            MCSLogger.log("SOCKET_CHANNEL(" + this.m_connID + ")", "Established connection to " + this.m_hostAddress);
        } catch (IOException $r2) {
            MCSLogger.log("SOCKET_CHANNEL(" + this.m_connID + ")", "Failed to setup socket selector: " + $r2.toString());
        }
    }

    public void run() throws  {
        while (true) {
            try {
                SocketChannelTransportLayer $r2 = $r0;
                Selector selector = $r2.m_selector;
                $r0 = $r2;
                if ($r2.m_isStopped || $r0.m_clientChannel == null || selector == null) {
                    break;
                } else if (selector.select(10) > 0) {
                    Iterator $r7 = selector.selectedKeys().iterator();
                    while ($r7.hasNext()) {
                        SelectionKey $r9 = (SelectionKey) $r7.next();
                        $r7.remove();
                        if ($r9.isValid() && $r9.isReadable() && !$r0.notifyForData()) {
                            Thread.sleep(10);
                        }
                    }
                }
            } catch (Exception $r1) {
                MCSLogger.log(TAG, "EXCEPTION", $r1);
                $r0.closeConnection();
            }
        }
        MCSLogger.log("SOCKET_CHANNEL(" + $r0.m_connID + ") Reading thread exited");
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

    public boolean getDumpInfo() throws  {
        return this.m_dumpInfo;
    }

    public void setDumpInfo(boolean $z0) throws  {
        this.m_dumpInfo = $z0;
    }
}
