package com.abaltatech.mcp.mcs.mtp;

import com.abaltatech.mcp.mcs.common.IConnectionListener;
import com.abaltatech.mcp.mcs.common.IConnectionListenerNotification;
import com.abaltatech.mcp.mcs.common.IConnectionReceiver;
import com.abaltatech.mcp.mcs.common.IDatagramHandler;
import com.abaltatech.mcp.mcs.common.IDatagramHandlerNotification;
import com.abaltatech.mcp.mcs.common.IDatagramReceiver;
import com.abaltatech.mcp.mcs.common.IMCSConnectionAddress;
import com.abaltatech.mcp.mcs.common.IMCSDataLayer;
import com.abaltatech.mcp.mcs.common.IMCSDataLayer.EWriteMode;
import com.abaltatech.mcp.mcs.common.IResolveAddress;
import com.abaltatech.mcp.mcs.common.IResolveAddressCompletedNotification;
import com.abaltatech.mcp.mcs.common.IResolveAddressRequestedNotification;
import com.abaltatech.mcp.mcs.common.MCSException;
import com.abaltatech.mcp.mcs.common.MCSMultiPointLayerBase;
import com.abaltatech.mcp.mcs.common.MemoryPool;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import com.abaltatech.mcp.mcs.tcpip.TCPIPAddress;
import com.abaltatech.mcp.mcs.utils.ByteUtils;
import com.abaltatech.mcp.mcs.utils.NotificationList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MTPLayer extends MCSMultiPointLayerBase implements IConnectionListener, IDatagramHandler, IDatagramReceiver, IResolveAddress {
    static final /* synthetic */ boolean $assertionsDisabled;
    protected static final int FrameBegin = 30;
    protected static final int FrameEnd = 3;
    protected static final int StateEnded = 3;
    protected static final int StateNotStarted = 0;
    protected static final int StatePayload = 2;
    protected static final int StateStarted = 1;
    private static int m_nextReqID = 0;
    protected int m_bufferSize = 0;
    private ConnectionListenerNotificationList m_connectionListenerNotificationList = new ConnectionListenerNotificationList();
    protected ConcurrentLinkedQueue<ConnectionPointMTP> m_connections = new ConcurrentLinkedQueue();
    private DatagramHandlerNotificationList m_datagramHandlerNotificationList = new DatagramHandlerNotificationList();
    private ConcurrentHashMap<Integer, Integer> m_datagramListenMap = new ConcurrentHashMap();
    private ConcurrentHashMap<Integer, IDatagramReceiver> m_datagramReceiverMap = new ConcurrentHashMap();
    protected boolean m_dumpInfo = false;
    protected byte[] m_inBuffer = MemoryPool.getMem(MemoryPool.BufferSizeBig, "MTPPacket");
    private int m_lastNameResolutionRequest = 0;
    private ConcurrentHashMap<IMCSConnectionAddress, IConnectionReceiver> m_listenRecvMap = new ConcurrentHashMap();
    private HashMap<Integer, IMCSConnectionAddress> m_listenReqMap = new HashMap();
    protected int m_readPos = 0;
    private ResolveAddressCompletedNotificationList m_resolveAddressCompletedNotificationList = new ResolveAddressCompletedNotificationList();
    private ResolveAddressRequestedNotificationList m_resolveAddressRequestedNotificationList = new ResolveAddressRequestedNotificationList();

    private class ConnectionListenerNotificationList extends NotificationList {
        private ConnectionListenerNotificationList() throws  {
        }

        public void Register(IConnectionListenerNotification $r1) throws  {
            super.Register($r1);
        }

        public IMCSConnectionAddress NotifyStartListening(IMCSConnectionAddress $r1, int $i0, IConnectionReceiver $r2) throws  {
            IMCSConnectionAddress $r3 = null;
            int $i1 = Start();
            while ($r3 == null) {
                IConnectionListenerNotification $r5 = (IConnectionListenerNotification) GetObject($i1);
                if ($r5 == null) {
                    return $r3;
                }
                $r3 = $r5.OnStartListening($r1, $i0, $r2);
                $i1 = GetNext($i1);
            }
            return $r3;
        }

        public void NotifyStopListening(IMCSConnectionAddress $r1) throws  {
            int $i0 = Start();
            while (true) {
                IConnectionListenerNotification $r3 = (IConnectionListenerNotification) GetObject($i0);
                if ($r3 != null) {
                    $r3.OnStopListening($r1);
                    $i0 = GetNext($i0);
                } else {
                    return;
                }
            }
        }
    }

    private class DatagramHandlerNotificationList extends NotificationList {
        private DatagramHandlerNotificationList() throws  {
        }

        public void Register(IDatagramHandlerNotification $r1) throws  {
            super.Register($r1);
        }

        public int NotifyStartDatagramListening(IMCSConnectionAddress $r1, IDatagramReceiver $r2) throws  {
            int $i0 = Start();
            int $i1 = -1;
            while ($i1 == -1) {
                IDatagramHandlerNotification $r4 = (IDatagramHandlerNotification) GetObject($i0);
                if ($r4 == null) {
                    return $i1;
                }
                $i1 = $r4.OnStartDatagramListening($r1, $r2);
                $i0 = GetNext($i0);
            }
            return $i1;
        }

        public void NotifyStopDatagramListening(int $i0) throws  {
            int $i1 = Start();
            while (true) {
                IDatagramHandlerNotification $r2 = (IDatagramHandlerNotification) GetObject($i1);
                if ($r2 != null) {
                    $r2.OnStopDatagramListening($i0);
                    $i1 = GetNext($i1);
                } else {
                    return;
                }
            }
        }

        public void NotifySendDatagram(int $i0, IMCSConnectionAddress $r1, byte[] $r2, int $i1) throws  {
            int $i2 = Start();
            while (true) {
                IDatagramHandlerNotification $r4 = (IDatagramHandlerNotification) GetObject($i2);
                if ($r4 != null) {
                    $r4.OnSendDatagram($i0, $r1, $r2, $i1);
                    $i2 = GetNext($i2);
                } else {
                    return;
                }
            }
        }
    }

    private class ResolveAddressCompletedNotificationList extends NotificationList {
        private ResolveAddressCompletedNotificationList() throws  {
        }

        public void Register(IResolveAddressCompletedNotification $r1) throws  {
            super.Register($r1);
        }

        public void Notify(int $i0, String $r1, IMCSConnectionAddress $r2) throws  {
            int $i1 = Start();
            while (true) {
                IResolveAddressCompletedNotification $r4 = (IResolveAddressCompletedNotification) GetObject($i1);
                if ($r4 != null) {
                    $r4.OnResolveAddressCompleted($i0, $r1, $r2);
                    $i1 = GetNext($i1);
                } else {
                    return;
                }
            }
        }
    }

    private class ResolveAddressRequestedNotificationList extends NotificationList {
        private ResolveAddressRequestedNotificationList() throws  {
        }

        public void Register(IResolveAddressRequestedNotification $r1) throws  {
            super.Register($r1);
        }

        public boolean Notify(IResolveAddress $r1, int $i0, String $r2) throws  {
            boolean $z0 = false;
            int $i1 = Start();
            while (!$z0) {
                IResolveAddressRequestedNotification $r4 = (IResolveAddressRequestedNotification) GetObject($i1);
                if ($r4 == null) {
                    return $z0;
                }
                $z0 = $r4.OnResolveAddressRequested($r1, $i0, $r2);
                $i1 = GetNext($i1);
            }
            return $z0;
        }
    }

    public int StartDatagramListening(com.abaltatech.mcp.mcs.common.IMCSConnectionAddress r28, com.abaltatech.mcp.mcs.common.IDatagramReceiver r29) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x00b1 in list []
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
        r27 = this;
        r4 = -1;
        r0 = r27;
        r5 = r0.m_dataLayer;
        if (r5 == 0) goto L_0x00b5;
    L_0x0007:
        r6 = 0;
        r7 = m_nextReqID;
        r7 = r7 + 1;
        m_nextReqID = r7;
        r9 = 0;
        r0 = r28;
        r8 = com.abaltatech.mcp.mcs.mtp.MTPPacket.CreateStartDatagramListeningPacket(r7, r0, r9);
        r10 = $assertionsDisabled;
        if (r10 != 0) goto L_0x0021;
    L_0x0019:
        if (r8 != 0) goto L_0x0021;
    L_0x001b:
        r11 = new java.lang.AssertionError;
        r11.<init>();
        throw r11;
    L_0x0021:
        if (r8 == 0) goto L_0x00a5;
    L_0x0023:
        r0 = r27;
        r12 = r0.m_datagramListenMap;
        r13 = java.lang.Integer.valueOf(r7);
        r9 = 0;
        r14 = java.lang.Integer.valueOf(r9);
        r12.put(r13, r14);
        r15 = r8.GetPacket();
        r16 = r8.GetPacketSize();
        r0 = r16;
        r5.writeData(r15, r0);
        r8.ReleasePacket();
        r6 = 1;
    L_0x0044:
        if (r6 == 0) goto L_0x00b7;
    L_0x0046:
        r17 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
    L_0x0048:
        r0 = r27;
        r12 = r0.m_datagramListenMap;
        r13 = java.lang.Integer.valueOf(r7);	 Catch:{ InterruptedException -> 0x00b3 }
        r19 = r12.get(r13);	 Catch:{ InterruptedException -> 0x00b3 }
        r20 = r19;
        r20 = (java.lang.Integer) r20;
        r13 = r20;
        if (r13 == 0) goto L_0x00b1;	 Catch:{ InterruptedException -> 0x00b3 }
    L_0x005c:
        r16 = r13.intValue();	 Catch:{ InterruptedException -> 0x00b3 }
        if (r16 == 0) goto L_0x00b1;
    L_0x0062:
        r6 = 1;
    L_0x0063:
        if (r6 == 0) goto L_0x008a;	 Catch:{ InterruptedException -> 0x00b3 }
    L_0x0065:
        r16 = r13.intValue();	 Catch:{ InterruptedException -> 0x00b3 }
        r4 = r16;
        r0 = r27;	 Catch:{ InterruptedException -> 0x00b3 }
        r12 = r0.m_datagramListenMap;	 Catch:{ InterruptedException -> 0x00b3 }
        r13 = java.lang.Integer.valueOf(r7);	 Catch:{ InterruptedException -> 0x00b3 }
        r12.remove(r13);	 Catch:{ InterruptedException -> 0x00b3 }
        r9 = -1;
        r0 = r16;
        if (r0 == r9) goto L_0x008a;
    L_0x007b:
        r0 = r27;	 Catch:{ InterruptedException -> 0x00b3 }
        r12 = r0.m_datagramReceiverMap;	 Catch:{ InterruptedException -> 0x00b3 }
        r0 = r16;	 Catch:{ InterruptedException -> 0x00b3 }
        r13 = java.lang.Integer.valueOf(r0);	 Catch:{ InterruptedException -> 0x00b3 }
        r0 = r29;	 Catch:{ InterruptedException -> 0x00b3 }
        r12.put(r13, r0);	 Catch:{ InterruptedException -> 0x00b3 }
    L_0x008a:
        if (r6 != 0) goto L_0x0093;	 Catch:{ InterruptedException -> 0x00b3 }
    L_0x008c:
        r21 = 1;	 Catch:{ InterruptedException -> 0x00b3 }
        r0 = r21;	 Catch:{ InterruptedException -> 0x00b3 }
        java.lang.Thread.sleep(r0);	 Catch:{ InterruptedException -> 0x00b3 }
    L_0x0093:
        r21 = 1;
        r0 = r17;
        r2 = r21;
        r0 = r0 - r2;
        r17 = r0;
        if (r6 != 0) goto L_0x00b9;
    L_0x009e:
        r21 = 0;
        r23 = (r17 > r21 ? 1 : (r17 == r21 ? 0 : -1));
        if (r23 > 0) goto L_0x0048;
    L_0x00a4:
        return r4;
    L_0x00a5:
        r24 = "MTP";
        r25 = "Error preparing MTP packet.";
        r0 = r24;
        r1 = r25;
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r0, r1);
        goto L_0x0044;
    L_0x00b1:
        r6 = 0;
        goto L_0x0063;
    L_0x00b3:
        r26 = move-exception;
        return r4;
    L_0x00b5:
        r9 = -1;
        return r9;
    L_0x00b7:
        r9 = -1;
        return r9;
    L_0x00b9:
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.mtp.MTPLayer.StartDatagramListening(com.abaltatech.mcp.mcs.common.IMCSConnectionAddress, com.abaltatech.mcp.mcs.common.IDatagramReceiver):int");
    }

    public void onDataReceived(com.abaltatech.mcp.mcs.common.IMCSDataLayer r20) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x01c4 in list [B:27:0x0089]
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
        r19 = this;
        r2 = com.abaltatech.mcp.mcs.common.MemoryPool.BufferSizeBig;
        r4 = "MTPPacket";	 Catch:{ MCSException -> 0x0089 }
        r3 = com.abaltatech.mcp.mcs.common.MemoryPool.getMem(r2, r4);	 Catch:{ MCSException -> 0x0089 }
    L_0x0008:
        r0 = r19;
        r0 = r0.m_dataLayer;
        r20 = r0;
        if (r20 == 0) goto L_0x01c4;	 Catch:{ MCSException -> 0x0089 }
    L_0x0010:
        r0 = r19;
        r0 = r0.m_dataLayer;
        r20 = r0;
        r2 = r3.length;	 Catch:{ MCSException -> 0x0089 }
        r0 = r20;	 Catch:{ MCSException -> 0x0089 }
        r5 = r0.readData(r3, r2);	 Catch:{ MCSException -> 0x0089 }
        r2 = r5;
        if (r5 <= 0) goto L_0x01c4;	 Catch:{ MCSException -> 0x0089 }
    L_0x0020:
        r6 = new java.lang.StringBuilder;	 Catch:{ MCSException -> 0x0089 }
        r6.<init>();	 Catch:{ MCSException -> 0x0089 }
        r4 = "Received ";	 Catch:{ MCSException -> 0x0089 }
        r6 = r6.append(r4);	 Catch:{ MCSException -> 0x0089 }
        r6 = r6.append(r5);	 Catch:{ MCSException -> 0x0089 }
        r4 = " bytes.";	 Catch:{ MCSException -> 0x0089 }
        r6 = r6.append(r4);	 Catch:{ MCSException -> 0x0089 }
        r7 = r6.toString();	 Catch:{ MCSException -> 0x0089 }
        r0 = r19;	 Catch:{ MCSException -> 0x0089 }
        r0.dumpInfo(r7);	 Catch:{ MCSException -> 0x0089 }
        r5 = 0;
    L_0x003f:
        if (r2 <= 0) goto L_0x0008;
    L_0x0041:
        r0 = r19;
        r8 = r0.m_inBuffer;
        r9 = r8.length;
        r0 = r19;
        r10 = r0.m_bufferSize;
        r9 = r9 - r10;
        if (r9 != 0) goto L_0x0094;	 Catch:{ MCSException -> 0x0089 }
    L_0x004d:
        r6 = new java.lang.StringBuilder;	 Catch:{ MCSException -> 0x0089 }
        r6.<init>();	 Catch:{ MCSException -> 0x0089 }
        r4 = "The MTP buffer is invalid. Discarding ";	 Catch:{ MCSException -> 0x0089 }
        r6 = r6.append(r4);	 Catch:{ MCSException -> 0x0089 }
        r0 = r19;
        r8 = r0.m_inBuffer;
        r9 = r8.length;	 Catch:{ MCSException -> 0x0089 }
        r6 = r6.append(r9);	 Catch:{ MCSException -> 0x0089 }
        r4 = " bytes";	 Catch:{ MCSException -> 0x0089 }
        r6 = r6.append(r4);	 Catch:{ MCSException -> 0x0089 }
        r7 = r6.toString();	 Catch:{ MCSException -> 0x0089 }
        r0 = r19;	 Catch:{ MCSException -> 0x0089 }
        r0.dumpInfo(r7);	 Catch:{ MCSException -> 0x0089 }
        r11 = 0;
        r0 = r19;
        r0.m_readPos = r11;
        r11 = 0;
        r0 = r19;
        r0.m_bufferSize = r11;
        r0 = r19;
        r8 = r0.m_inBuffer;
        r9 = r8.length;
        r12 = $assertionsDisabled;
        if (r12 != 0) goto L_0x0094;	 Catch:{ MCSException -> 0x0089 }
    L_0x0083:
        r13 = new java.lang.AssertionError;	 Catch:{ MCSException -> 0x0089 }
        r13.<init>();	 Catch:{ MCSException -> 0x0089 }
        throw r13;
    L_0x0089:
        r14 = move-exception;
        r7 = r14.toString();
        r4 = "ERROR";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r4, r7);
        return;
    L_0x0094:
        r9 = java.lang.Math.min(r9, r2);	 Catch:{ MCSException -> 0x0089 }
        r0 = r19;
        r8 = r0.m_inBuffer;
        r0 = r19;	 Catch:{ MCSException -> 0x0089 }
        r10 = r0.m_bufferSize;	 Catch:{ MCSException -> 0x0089 }
        java.lang.System.arraycopy(r3, r5, r8, r10, r9);	 Catch:{ MCSException -> 0x0089 }
        r2 = r2 - r9;
        r5 = r5 + r9;
        r0 = r19;
        r10 = r0.m_bufferSize;
        r9 = r10 + r9;
        r0 = r19;
        r0.m_bufferSize = r9;
    L_0x00af:
        r0 = r19;
        r8 = r0.m_inBuffer;
        r0 = r19;
        r9 = r0.m_readPos;
        r0 = r19;
        r10 = r0.m_bufferSize;
        r0 = r19;
        r15 = r0.m_readPos;
        r10 = r10 - r15;	 Catch:{ MCSException -> 0x0089 }
        r9 = com.abaltatech.mcp.mcs.mtp.MTPPacket.AnalyzeBuffer(r8, r9, r10);	 Catch:{ MCSException -> 0x0089 }
        if (r9 <= 0) goto L_0x0192;
    L_0x00c6:
        r0 = r19;
        r8 = r0.m_inBuffer;
        r0 = r19;
        r10 = r0.m_readPos;
        r0 = r19;
        r15 = r0.m_bufferSize;
        r0 = r19;
        r0 = r0.m_readPos;
        r16 = r0;
        r15 = r15 - r0;	 Catch:{ MCSException -> 0x0089 }
        r17 = com.abaltatech.mcp.mcs.mtp.MTPPacket.ParsePacket(r8, r10, r15);	 Catch:{ MCSException -> 0x0089 }
        r0 = r19;
        r10 = r0.m_readPos;
        r10 = r10 + r9;
        r0 = r19;
        r0.m_readPos = r10;
        if (r17 == 0) goto L_0x0167;
    L_0x00e8:
        r6 = new java.lang.StringBuilder;	 Catch:{ MCSException -> 0x0089 }
        r6.<init>();	 Catch:{ MCSException -> 0x0089 }
        r4 = "The MTP packet is received (";	 Catch:{ MCSException -> 0x0089 }
        r6 = r6.append(r4);	 Catch:{ MCSException -> 0x0089 }
        r6 = r6.append(r9);	 Catch:{ MCSException -> 0x0089 }
        r4 = " bytes).";	 Catch:{ MCSException -> 0x0089 }
        r6 = r6.append(r4);	 Catch:{ MCSException -> 0x0089 }
        r7 = r6.toString();	 Catch:{ MCSException -> 0x0089 }
        r0 = r19;	 Catch:{ MCSException -> 0x0089 }
        r0.dumpInfo(r7);	 Catch:{ MCSException -> 0x0089 }
        r0 = r19;	 Catch:{ MCSException -> 0x0089 }
        r1 = r17;	 Catch:{ MCSException -> 0x0089 }
        r0.Process(r1);	 Catch:{ MCSException -> 0x0089 }
        r0 = r17;	 Catch:{ MCSException -> 0x0089 }
        r0.ReleasePacket();	 Catch:{ MCSException -> 0x0089 }
    L_0x0112:
        if (r9 <= 0) goto L_0x011e;
    L_0x0114:
        r0 = r19;
        r9 = r0.m_readPos;
        r0 = r19;
        r10 = r0.m_bufferSize;
        if (r9 < r10) goto L_0x00af;
    L_0x011e:
        r0 = r19;
        r9 = r0.m_readPos;
        if (r9 <= 0) goto L_0x003f;
    L_0x0124:
        r0 = r19;
        r9 = r0.m_readPos;
        r0 = r19;
        r10 = r0.m_bufferSize;
        if (r9 >= r10) goto L_0x01b5;
    L_0x012e:
        r0 = r19;
        r8 = r0.m_inBuffer;
        r0 = r19;
        r9 = r0.m_readPos;
        r0 = r19;
        r0 = r0.m_inBuffer;
        r18 = r0;
        r0 = r19;
        r15 = r0.m_bufferSize;
        r0 = r19;
        r10 = r0.m_readPos;
        r10 = r15 - r10;	 Catch:{ MCSException -> 0x0089 }
        r11 = 0;	 Catch:{ MCSException -> 0x0089 }
        r0 = r18;	 Catch:{ MCSException -> 0x0089 }
        java.lang.System.arraycopy(r8, r9, r0, r11, r10);	 Catch:{ MCSException -> 0x0089 }
        r0 = r19;
        r9 = r0.m_bufferSize;
        r0 = r19;
        r10 = r0.m_readPos;
        goto L_0x0158;
    L_0x0155:
        goto L_0x0112;
    L_0x0158:
        r9 = r9 - r10;
        r0 = r19;
        r0.m_bufferSize = r9;
        goto L_0x0161;
    L_0x015e:
        goto L_0x003f;
    L_0x0161:
        r11 = 0;
        r0 = r19;
        r0.m_readPos = r11;
        goto L_0x015e;
    L_0x0167:
        r12 = $assertionsDisabled;
        if (r12 != 0) goto L_0x0173;
    L_0x016b:
        if (r17 != 0) goto L_0x0173;
    L_0x016d:
        r13 = new java.lang.AssertionError;	 Catch:{ MCSException -> 0x0089 }
        r13.<init>();	 Catch:{ MCSException -> 0x0089 }
        throw r13;
    L_0x0173:
        r6 = new java.lang.StringBuilder;	 Catch:{ MCSException -> 0x0089 }
        r6.<init>();	 Catch:{ MCSException -> 0x0089 }
        r4 = "Invalid MTP packet is received (";	 Catch:{ MCSException -> 0x0089 }
        r6 = r6.append(r4);	 Catch:{ MCSException -> 0x0089 }
        r6 = r6.append(r9);	 Catch:{ MCSException -> 0x0089 }
        r4 = " bytes).";	 Catch:{ MCSException -> 0x0089 }
        r6 = r6.append(r4);	 Catch:{ MCSException -> 0x0089 }
        r7 = r6.toString();	 Catch:{ MCSException -> 0x0089 }
        r0 = r19;	 Catch:{ MCSException -> 0x0089 }
        r0.dumpInfo(r7);	 Catch:{ MCSException -> 0x0089 }
        goto L_0x0112;
    L_0x0192:
        if (r9 != 0) goto L_0x019c;	 Catch:{ MCSException -> 0x0089 }
    L_0x0194:
        r4 = "The MTP packet is incomplete.";	 Catch:{ MCSException -> 0x0089 }
        r0 = r19;	 Catch:{ MCSException -> 0x0089 }
        r0.dumpInfo(r4);	 Catch:{ MCSException -> 0x0089 }
        goto L_0x0155;
    L_0x019c:
        r4 = "The MTP buffer does not contain valid MTP message ... discarding.";	 Catch:{ MCSException -> 0x0089 }
        r0 = r19;	 Catch:{ MCSException -> 0x0089 }
        r0.dumpInfo(r4);	 Catch:{ MCSException -> 0x0089 }
        r0 = r19;
        r10 = r0.m_bufferSize;
        r0 = r19;
        r0.m_readPos = r10;
        r12 = $assertionsDisabled;
        if (r12 != 0) goto L_0x0112;
    L_0x01af:
        r13 = new java.lang.AssertionError;	 Catch:{ MCSException -> 0x0089 }
        r13.<init>();	 Catch:{ MCSException -> 0x0089 }
        throw r13;
    L_0x01b5:
        r11 = 0;
        r0 = r19;
        r0.m_readPos = r11;
        goto L_0x01be;
    L_0x01bb:
        goto L_0x003f;
    L_0x01be:
        r11 = 0;
        r0 = r19;
        r0.m_bufferSize = r11;
        goto L_0x01bb;
    L_0x01c4:
        com.abaltatech.mcp.mcs.common.MemoryPool.freeMem(r3);	 Catch:{ MCSException -> 0x0089 }
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.mtp.MTPLayer.onDataReceived(com.abaltatech.mcp.mcs.common.IMCSDataLayer):void");
    }

    static {
        boolean $z0;
        if (MTPLayer.class.desiredAssertionStatus()) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        $assertionsDisabled = $z0;
    }

    public void attachToLayer(IMCSDataLayer $r1) throws  {
        this.m_bufferSize = 0;
        super.attachToLayer($r1);
        if (this.m_dataLayer != null) {
            this.m_dataLayer.setWriteMode(EWriteMode.eBuffered, 1048576);
        }
    }

    public void closeConnections() throws  {
        while (true) {
            ConnectionPointMTP $r3 = (ConnectionPointMTP) this.m_connections.poll();
            if ($r3 != null) {
                notifyForConnectionClosed($r3, $r3.getFromAddress(), $r3.getToAddress());
                $r3.closeConnection();
            } else {
                return;
            }
        }
    }

    public void closeConnection(IMCSDataLayer $r1) throws MCSException {
        if ($r1 instanceof ConnectionPointMTP) {
            ConnectionPointMTP $r3 = (ConnectionPointMTP) $r1;
            if (this.m_connections.remove($r3)) {
                notifyForConnectionClosed($r3, $r3.getFromAddress(), $r3.getToAddress());
                $r3.closeConnection();
                return;
            }
            throw new MCSException("Invalid connection point in closeConnection() - may be already closed");
        }
        throw new MCSException("Invalid connection point in closeConnection()");
    }

    public IMCSDataLayer createConnectionPoint(IMCSConnectionAddress $r1, IMCSConnectionAddress $r2) throws MCSException {
        ConnectionPointMTP $r3 = new ConnectionPointMTP(this.m_dataLayer, $r1, $r2, this);
        this.m_connections.add($r3);
        return $r3;
    }

    private boolean Process(MTPPacket $r1) throws  {
        switch ($r1.GetType()) {
            case 0:
                dumpInfo("Process DATA packet (last=" + $r1.IsLastMessage() + ")");
                return ProcessData($r1);
            case 1:
                dumpInfo("Process ResolveAddress packet");
                return ProcessResolveAddress($r1);
            case 2:
                dumpInfo("Process OpenListeningConnection packet");
                return ProcessOpenListenConection($r1);
            case 3:
                dumpInfo("Process CloseListeningConnection packet");
                return ProcessCloseListenConection($r1);
            case 4:
                dumpInfo("Process StartDatagramListen packet");
                return ProcessStartDatagramListen($r1);
            case 5:
                dumpInfo("Process StopDatagramListen packet");
                return ProcessStopDatagramListen($r1);
            default:
                dumpInfo("Unsupported message type");
                if ($assertionsDisabled) {
                    return false;
                }
                throw new AssertionError();
        }
    }

    private boolean ProcessData(MTPPacket $r1) throws  {
        switch ($r1.GetSourceProtocol()) {
            case 0:
                return processTCPData($r1);
            case 1:
                return processUDPData($r1);
            default:
                dumpInfo("Invalid addressing schema");
                if ($assertionsDisabled) {
                    return false;
                }
                throw new AssertionError();
        }
    }

    private boolean processTCPData(MTPPacket $r1) throws  {
        boolean $z0 = processPacket($r1);
        if ($z0) {
            return $z0;
        }
        IMCSConnectionAddress $r3 = $r1.GetFromAddress();
        IMCSConnectionAddress $r4 = $r1.GetToAddress();
        int $i0 = $r1.GetPayloadSize();
        if (!$assertionsDisabled && ($r3 == null || $r4 == null)) {
            throw new AssertionError();
        } else if ($r3 == null || $r4 == null) {
            if ($assertionsDisabled) {
                return $z0;
            }
            throw new AssertionError();
        } else if ($r1.IsLastMessage() && $i0 == 0) {
            dumpInfo("Connection to " + $r4.toString() + "  is already closed");
            return true;
        } else {
            IConnectionReceiver $r10 = (IConnectionReceiver) this.m_listenRecvMap.get($r4);
            if ($r10 != null) {
                IMCSDataLayer $r11;
                try {
                    $r11 = createConnectionPoint($r3, $r4);
                } catch (MCSException $r2) {
                    MCSLogger.log("MTP", $r2.getMessage());
                    $r11 = null;
                }
                if (!$assertionsDisabled && $r11 == null) {
                    throw new AssertionError();
                } else if ($r11 == null) {
                    return $z0;
                } else {
                    $r10.OnConnectionEstablished($r3, $r4, $r11, this);
                    return processPacket($r1);
                }
            }
            notifyForNewConnection($r3, $r4, null);
            return processPacket($r1);
        }
    }

    private boolean processUDPData(MTPPacket $r1) throws  {
        IMCSConnectionAddress $r3 = $r1.GetFromAddress();
        IMCSConnectionAddress $r4 = $r1.GetToAddress();
        if ($assertionsDisabled || !($r3 == null || $r4 == null)) {
            if (!($r3 == null || $r4 == null)) {
                TCPIPAddress $r6 = (TCPIPAddress) $r3;
                TCPIPAddress $r7 = (TCPIPAddress) $r4;
                int $i0 = $r1.GetPayloadSize();
                byte[] $r2 = new byte[$i0];
                System.arraycopy($r1.GetPacket(), $r1.GetPayloadOffset(), $r2, 0, $i0);
                int $i1;
                if ($r6.getPort() == 0) {
                    $i1 = ByteUtils.ReadDWord($r6.getAddress().getAddress(), 0);
                    DatagramHandlerNotificationList $r10 = this.m_datagramHandlerNotificationList;
                    $r10.NotifySendDatagram($i1, $r4, $r2, $i0);
                } else {
                    $i1 = ByteUtils.ReadDWord($r7.getAddress().getAddress(), 0);
                    IDatagramReceiver $r14 = (IDatagramReceiver) this.m_datagramReceiverMap.get(Integer.valueOf($i1));
                    if (!$assertionsDisabled && $r14 == null) {
                        throw new AssertionError();
                    } else if ($r14 != null) {
                        $r14.OnDatagramReceived($i1, $r4, $r2, $i0);
                    }
                }
            }
            return true;
        }
        throw new AssertionError();
    }

    protected boolean processPacket(MTPPacket $r1) throws  {
        Iterator $r3 = this.m_connections.iterator();
        while ($r3.hasNext()) {
            ConnectionPointMTP $r5 = (ConnectionPointMTP) $r3.next();
            if ($r5.isMTPPacketForMe($r1)) {
                $r5.processPacket($r1);
                return true;
            }
        }
        return false;
    }

    private boolean ProcessResolveAddress(MTPPacket $r1) throws  {
        String $r2 = $r1.GetNameResolution();
        if ($assertionsDisabled || $r2 != null) {
            if ($r2 != null) {
                IMCSConnectionAddress $r4 = $r1.GetFromAddress();
                IMCSConnectionAddress $r5 = $r1.GetToAddress();
                if (!$assertionsDisabled && $r4 == null && $r5 == null) {
                    throw new AssertionError();
                } else if (!($r4 == null && $r5 == null)) {
                    if ($r5 != null) {
                        this.m_resolveAddressCompletedNotificationList.Notify(((TCPIPAddress) $r5).getPort(), $r2, $r4);
                    } else {
                        boolean $z0 = this.m_resolveAddressRequestedNotificationList.Notify(this, ((TCPIPAddress) $r4).getPort(), $r2);
                        if (!$assertionsDisabled && !$z0) {
                            throw new AssertionError();
                        } else if (!$z0) {
                            SendResolveAddressResponse(((TCPIPAddress) $r4).getPort(), $r2, null);
                        }
                    }
                }
            } else if (!$assertionsDisabled) {
                throw new AssertionError();
            }
            return true;
        }
        throw new AssertionError();
    }

    private boolean ProcessOpenListenConection(MTPPacket $r1) throws  {
        if ($assertionsDisabled || ($r1.GetType() == 2 && $r1.GetFromAddress() != null)) {
            if ($r1.GetType() != 2 || $r1.GetFromAddress() == null) {
                MCSLogger.log("MTP", "Invalid 'Open Listening Connection' request.");
            } else {
                IMCSConnectionAddress $r2 = $r1.GetToAddress();
                byte[] $r7 = ((TCPIPAddress) $r1.GetFromAddress()).getAddress().getAddress();
                int $i1 = $r1.GetPayloadOffset();
                int $i0 = ByteUtils.ReadDWord($r7, 0);
                $i1 = ByteUtils.ReadWord($r1.GetPacket(), $i1);
                if ($i1 != 0) {
                    $r1 = MTPPacket.CreateStartListeningPacket($i0, this.m_connectionListenerNotificationList.NotifyStartListening($r2, $i1, null), 0);
                    if (!$assertionsDisabled && $r1 == null) {
                        throw new AssertionError();
                    } else if ($r1 != null) {
                        this.m_dataLayer.writeData($r1.GetPacket(), $r1.GetPacketSize());
                        $r1.ReleasePacket();
                    }
                } else if (!this.m_listenReqMap.containsKey(Integer.valueOf($i0))) {
                    this.m_listenReqMap.put(Integer.valueOf($i0), $r2);
                } else if ($assertionsDisabled || this.m_listenReqMap.get(Integer.valueOf($i0)) == null) {
                    this.m_listenReqMap.remove(Integer.valueOf($i0));
                } else {
                    throw new AssertionError();
                }
            }
            return true;
        }
        throw new AssertionError();
    }

    private boolean ProcessCloseListenConection(MTPPacket $r1) throws  {
        if ($assertionsDisabled || ($r1.GetType() == 3 && $r1.GetToAddress() != null)) {
            if ($r1.GetType() != 3 || $r1.GetToAddress() == null) {
                MCSLogger.log("MTP", "Invalid 'Close Listening Connection' request.");
            } else {
                this.m_connectionListenerNotificationList.NotifyStopListening($r1.GetToAddress());
            }
            return true;
        }
        throw new AssertionError();
    }

    private boolean ProcessStartDatagramListen(MTPPacket $r1) throws  {
        if ($assertionsDisabled || ($r1.GetType() == 4 && $r1.GetFromAddress() != null)) {
            if ($r1.GetType() == 4 && $r1.GetFromAddress() != null) {
                byte[] $r6 = ((TCPIPAddress) $r1.GetFromAddress()).getAddress().getAddress();
                int $i1 = $r1.GetPayloadOffset();
                int $i0 = ByteUtils.ReadDWord($r6, 0);
                $i1 = ByteUtils.ReadDWord($r1.GetPacket(), $i1);
                if ($i1 == 0) {
                    IMCSConnectionAddress $r2 = $r1.GetToAddress();
                    IMCSConnectionAddress $r7 = $r2;
                    if ($r2 == null) {
                        $r7 = $r4;
                        TCPIPAddress $r4 = new TCPIPAddress();
                    }
                    if (!$assertionsDisabled && $r7 == null) {
                        throw new AssertionError();
                    } else if ($r7 != null) {
                        $r1 = MTPPacket.CreateStartDatagramListeningPacket($i0, $r7, this.m_datagramHandlerNotificationList.NotifyStartDatagramListening($r7, this));
                        if (!$assertionsDisabled && $r1 == null) {
                            throw new AssertionError();
                        } else if ($r1 != null) {
                            this.m_dataLayer.writeData($r1.GetPacket(), $r1.GetPacketSize());
                            $r1.ReleasePacket();
                        }
                    }
                } else {
                    this.m_datagramListenMap.put(Integer.valueOf($i0), Integer.valueOf($i1));
                }
            }
            return true;
        }
        throw new AssertionError();
    }

    private boolean ProcessStopDatagramListen(MTPPacket $r1) throws  {
        if ($assertionsDisabled || ($r1.GetType() == 5 && $r1.GetFromAddress() != null)) {
            if ($r1.GetType() == 5 && $r1.GetFromAddress() != null) {
                int $i0 = ByteUtils.ReadDWord(((TCPIPAddress) $r1.GetFromAddress()).getAddress().getAddress(), 0);
                if ($i0 != -1) {
                    this.m_datagramHandlerNotificationList.NotifyStopDatagramListening($i0);
                }
            }
            return true;
        }
        throw new AssertionError();
    }

    private void dumpInfo(String $r1) throws  {
        if (this.m_dumpInfo) {
            MCSLogger.log(MTPLayer.class.getSimpleName(), $r1);
        }
    }

    public void onConnectionClosed(IMCSDataLayer connection) throws  {
        this.m_dataLayer = null;
        this.m_bufferSize = 0;
        while (true) {
            ConnectionPointMTP $r4 = (ConnectionPointMTP) this.m_connections.poll();
            if ($r4 != null) {
                notifyForConnectionClosed($r4, $r4.getFromAddress(), $r4.getToAddress());
                $r4.closeConnection();
            } else {
                return;
            }
        }
    }

    public void setDumpInfo(boolean $z0) throws  {
        this.m_dumpInfo = $z0;
    }

    public boolean getDumpInfo() throws  {
        return this.m_dumpInfo;
    }

    public int SendResolveAddressRequest(String $r1) throws  {
        IMCSDataLayer $r3 = this.m_dataLayer;
        if (!$assertionsDisabled && $r3 == null) {
            throw new AssertionError();
        } else if ($r3 == null) {
            return -1;
        } else {
            int $i0 = this.m_lastNameResolutionRequest + 1;
            this.m_lastNameResolutionRequest = $i0;
            MTPPacket $r5 = MTPPacket.CreateNameResolutionPacket(new TCPIPAddress($i0), null, $r1);
            if (!$assertionsDisabled && $r5 == null) {
                throw new AssertionError();
            } else if ($r5 != null) {
                MCSLogger.log("MTP", $r5.GetPacketSize() + " bytes sent (name resolution request)");
                $r3.writeData($r5.GetPacket(), $r5.GetPacketSize());
                $r5.ReleasePacket();
                return this.m_lastNameResolutionRequest;
            } else {
                MCSLogger.log("MTP", "Error preparing MTP packet.");
                return -1;
            }
        }
    }

    public boolean SendResolveAddressResponse(int $i0, String $r1, IMCSConnectionAddress $r2) throws  {
        IMCSDataLayer $r3 = this.m_dataLayer;
        if (!$assertionsDisabled && $r3 == null) {
            throw new AssertionError();
        } else if ($r3 == null) {
            return false;
        } else {
            MTPPacket $r6 = MTPPacket.CreateNameResolutionPacket($r2, new TCPIPAddress($i0), $r1);
            if ($i0 == this.m_lastNameResolutionRequest && this.m_lastNameResolutionRequest > 0) {
                this.m_lastNameResolutionRequest--;
            }
            if (!$assertionsDisabled && $r6 == null) {
                throw new AssertionError();
            } else if ($r6 != null) {
                MCSLogger.log("MTP", $r6.GetPacketSize() + " bytes sent (name resolution response)");
                $r3.writeData($r6.GetPacket(), $r6.GetPacketSize());
                $r6.ReleasePacket();
                return true;
            } else {
                MCSLogger.log("MTP", "Error preparing MTP packet.");
                return false;
            }
        }
    }

    public void RegisterNotification(IResolveAddressRequestedNotification $r1) throws  {
        this.m_resolveAddressRequestedNotificationList.Register($r1);
    }

    public void UnregisterNotification(IResolveAddressRequestedNotification $r1) throws  {
        this.m_resolveAddressRequestedNotificationList.Unregister($r1);
    }

    public void RegisterNotification(IResolveAddressCompletedNotification $r1) throws  {
        this.m_resolveAddressCompletedNotificationList.Register($r1);
    }

    public void UnregisterNotification(IResolveAddressCompletedNotification $r1) throws  {
        this.m_resolveAddressCompletedNotificationList.Unregister($r1);
    }

    public void StopDatagramListening(int $i0) throws  {
        IMCSDataLayer $r1 = this.m_dataLayer;
        if (!$assertionsDisabled && $r1 == null) {
            throw new AssertionError();
        } else if ($r1 != null) {
            MTPPacket $r3 = MTPPacket.CreateStopDatagramListeningPacket($i0);
            this.m_datagramReceiverMap.remove(Integer.valueOf($i0));
            if (!$assertionsDisabled && $r3 == null) {
                throw new AssertionError();
            } else if ($r3 != null) {
                $r1.writeData($r3.GetPacket(), $r3.GetPacketSize());
                $r3.ReleasePacket();
            }
        } else {
            MCSLogger.log("MTP", "Error preparing MTP packet.");
        }
    }

    public void SendDatagram(int $i0, IMCSConnectionAddress $r1, byte[] $r2, int $i1) throws  {
        IMCSDataLayer $r3 = this.m_dataLayer;
        if (!$assertionsDisabled && $r3 == null) {
            throw new AssertionError();
        } else if ($r3 != null) {
            TCPIPAddress $r6 = new TCPIPAddress();
            byte[] $r4 = new byte[4];
            ByteUtils.WriteDWord($r4, 0, $i0);
            MTPPacket $r7 = MTPPacket.CreateDataPacket(new TCPIPAddress($r4, 0), $r1, $r2, 0, $i1, true, false, 1);
            if (!$assertionsDisabled && $r7 == null) {
                throw new AssertionError();
            } else if ($r7 != null) {
                $r3.writeData($r7.GetPacket(), $r7.GetPacketSize());
                $r7.ReleasePacket();
            }
        } else {
            MCSLogger.log("MTP", "Error preparing MTP packet.");
        }
    }

    public void RegisterNotification(IDatagramHandlerNotification $r1) throws  {
        this.m_datagramHandlerNotificationList.Register($r1);
    }

    public void UnregisterNotification(IDatagramHandlerNotification $r1) throws  {
        this.m_datagramHandlerNotificationList.Unregister($r1);
    }

    public void OnDatagramReceived(int $i0, IMCSConnectionAddress $r1, byte[] $r2, int $i1) throws  {
        byte[] $r4 = new byte[4];
        ByteUtils.WriteDWord($r4, 0, $i0);
        MTPPacket $r5 = MTPPacket.CreateDataPacket($r1, new TCPIPAddress($r4, 0), $r2, 0, $i1, true, false, 1);
        if (!$assertionsDisabled && $r5 == null) {
            throw new AssertionError();
        } else if ($r5 != null) {
            this.m_dataLayer.writeData($r5.GetPacket(), $r5.GetPacketSize());
            $r5.ReleasePacket();
        } else {
            MCSLogger.log("MTP", "Error preparing MTP packet.");
        }
    }

    public IMCSConnectionAddress StartListening(IMCSConnectionAddress $r1, int $i1, IConnectionReceiver $r2) throws  {
        IMCSConnectionAddress $r4 = null;
        IMCSDataLayer $r3 = this.m_dataLayer;
        if ($r3 == null) {
            return null;
        }
        boolean $z0 = false;
        int $i0 = m_nextReqID + 1;
        m_nextReqID = $i0;
        if ($i1 == 0) {
            $i1 = 1;
        }
        MTPPacket $r5 = MTPPacket.CreateStartListeningPacket($i0, $r1, $i1);
        if ($assertionsDisabled || $r5 != null) {
            if ($r5 != null) {
                $r3.writeData($r5.GetPacket(), $r5.GetPacketSize());
                $z0 = true;
                $r5.ReleasePacket();
            } else {
                MCSLogger.log("MTP", "Error preparing MTP packet.");
            }
            if (!$z0) {
                return null;
            }
            long $l2 = 10000;
            $z0 = false;
            do {
                boolean $z1 = this.m_listenReqMap.containsKey(Integer.valueOf($i0));
                $z0 = $z1;
                if ($z1) {
                    $r1 = (IMCSConnectionAddress) this.m_listenReqMap.get(Integer.valueOf($i0));
                    $r4 = $r1;
                    try {
                        this.m_datagramListenMap.remove(Integer.valueOf($i0));
                        if ($r1 != null) {
                            ConcurrentHashMap concurrentHashMap;
                            ConcurrentHashMap $r11;
                            if (!$assertionsDisabled) {
                                concurrentHashMap = this.m_listenRecvMap;
                                $r11 = concurrentHashMap;
                                if (concurrentHashMap.containsKey($r1)) {
                                    throw new AssertionError();
                                }
                            }
                            concurrentHashMap = this.m_listenRecvMap;
                            $r11 = concurrentHashMap;
                            concurrentHashMap.put($r1, $r2);
                        }
                    } catch (InterruptedException e) {
                    }
                }
                if (!$z1) {
                    Thread.sleep(1);
                    $l2--;
                }
                if ($z1) {
                    break;
                }
            } while ($l2 > 0);
            if ($z0) {
                return $r4;
            }
            this.m_listenReqMap.put(Integer.valueOf($i0), null);
            return $r4;
        }
        throw new AssertionError();
    }

    public void StopListening(IMCSConnectionAddress $r1) throws  {
        IMCSDataLayer $r2 = this.m_dataLayer;
        if ($assertionsDisabled || this.m_listenRecvMap.containsKey($r1)) {
            if (this.m_listenRecvMap.containsKey($r1)) {
                this.m_listenRecvMap.remove($r1);
            }
            if (!$assertionsDisabled && $r2 == null) {
                throw new AssertionError();
            } else if ($r2 != null) {
                MTPPacket $r5 = MTPPacket.CreateStopListeningPacket($r1);
                if (!$assertionsDisabled && $r5 == null) {
                    throw new AssertionError();
                } else if ($r5 != null) {
                    $r2.writeData($r5.GetPacket(), $r5.GetPacketSize());
                    $r5.ReleasePacket();
                    return;
                } else {
                    return;
                }
            } else {
                MCSLogger.log("MTP", "Error preparing MTP packet.");
                return;
            }
        }
        throw new AssertionError();
    }

    public void RegisterNotification(IConnectionListenerNotification $r1) throws  {
        this.m_connectionListenerNotificationList.Register($r1);
    }

    public void UnregisterNotification(IConnectionListenerNotification $r1) throws  {
        this.m_connectionListenerNotificationList.Unregister($r1);
    }

    public void detachFromLayer(IMCSDataLayer dataLayer) throws  {
    }
}
