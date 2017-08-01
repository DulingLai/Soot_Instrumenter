package com.abaltatech.mcp.mcs.gateway;

import com.abaltatech.mcp.mcs.common.IConnectionListener;
import com.abaltatech.mcp.mcs.common.IConnectionListenerNotification;
import com.abaltatech.mcp.mcs.common.IConnectionReceiver;
import com.abaltatech.mcp.mcs.common.IDatagramHandler;
import com.abaltatech.mcp.mcs.common.IDatagramReceiver;
import com.abaltatech.mcp.mcs.common.IMCSConnectionAddress;
import com.abaltatech.mcp.mcs.common.IMCSDataLayer;
import com.abaltatech.mcp.mcs.common.IMCSMultiPointLayer;
import com.abaltatech.mcp.mcs.common.IMCSMultiPointLayerNotification;
import com.abaltatech.mcp.mcs.common.IResolveAddress;
import com.abaltatech.mcp.mcs.common.IResolveAddressRequestedNotification;
import com.abaltatech.mcp.mcs.common.MCSException;
import com.abaltatech.mcp.mcs.connector.ConnectorLayer;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import com.abaltatech.mcp.mcs.sockettransport.SocketChannelTransportLayer;
import com.abaltatech.mcp.mcs.tcpip.TCPIPAddress;
import com.waze.strings.DisplayStrings;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class GatewayLayerBase implements IConnectionListenerNotification, IConnectionReceiver, IMCSMultiPointLayerNotification, IResolveAddressRequestedNotification {
    static final /* synthetic */ boolean $assertionsDisabled = (!GatewayLayerBase.class.desiredAssertionStatus());
    static final int SSLPortCount = SSLPorts.length;
    static final int[] SSLPorts = new int[]{443, DisplayStrings.DS_RAW_GPS_IS_ON};
    protected ConcurrentHashMap<IMCSDataLayer, ConnectorLayer> m_connectors = new ConcurrentHashMap();
    protected DatagramHandler m_datagramHandler = new DatagramHandler();
    protected ArrayList<IMCSConnectionAddress> m_exceptions = new ArrayList();
    protected ListenConnectionHandler m_listenConnHandler = new ListenConnectionHandler();
    protected ConcurrentHashMap<IMCSConnectionAddress, IConnectionReceiver> m_listenRecvMap = new ConcurrentHashMap();
    protected IMCSMultiPointLayer m_multipointLayer;

    public void OnConnectionEstablished(com.abaltatech.mcp.mcs.common.IMCSConnectionAddress r19, com.abaltatech.mcp.mcs.common.IMCSConnectionAddress r20, com.abaltatech.mcp.mcs.common.IMCSDataLayer r21, com.abaltatech.mcp.mcs.common.IMCSMultiPointLayer r22) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0080 in list []
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
        r18 = this;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "ListeningConnectionHandler:  Incomming connection accepted from=";
        r4 = r4.append(r5);
        r0 = r19;
        r4 = r4.append(r0);
        r5 = " to=";
        r4 = r4.append(r5);
        r0 = r20;
        r4 = r4.append(r0);
        r6 = r4.toString();
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r6);
        if (r22 == 0) goto L_0x008c;
    L_0x0026:
        r0 = r22;	 Catch:{ MCSException -> 0x0058 }
        r1 = r20;	 Catch:{ MCSException -> 0x0058 }
        r2 = r19;	 Catch:{ MCSException -> 0x0058 }
        r7 = r0.createConnectionPoint(r1, r2);	 Catch:{ MCSException -> 0x0058 }
        if (r7 == 0) goto L_0x008d;
    L_0x0032:
        r8 = new com.abaltatech.mcp.mcs.connector.ConnectorLayer;	 Catch:{ MCSException -> 0x0058 }
        r0 = r21;	 Catch:{ MCSException -> 0x0058 }
        r8.<init>(r7, r0);	 Catch:{ MCSException -> 0x0058 }
        if (r8 == 0) goto L_0x008e;
    L_0x003b:
        r0 = r18;	 Catch:{ MCSException -> 0x0058 }
        r9 = r0.m_connectors;	 Catch:{ MCSException -> 0x0058 }
        r9.put(r7, r8);	 Catch:{ MCSException -> 0x0058 }
        r10 = $assertionsDisabled;
        if (r10 != 0) goto L_0x0063;
    L_0x0046:
        r0 = r18;	 Catch:{ MCSException -> 0x0058 }
        r9 = r0.m_listenRecvMap;	 Catch:{ MCSException -> 0x0058 }
        r0 = r20;	 Catch:{ MCSException -> 0x0058 }
        r10 = r9.containsKey(r0);	 Catch:{ MCSException -> 0x0058 }
        if (r10 != 0) goto L_0x0063;
    L_0x0052:
        r11 = new java.lang.AssertionError;	 Catch:{ MCSException -> 0x0058 }
        r11.<init>();	 Catch:{ MCSException -> 0x0058 }
        throw r11;
    L_0x0058:
        r12 = move-exception;
        r6 = r12.toString();
        r5 = "GatewayListener";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r5, r6);
        return;
    L_0x0063:
        r0 = r18;
        r9 = r0.m_listenRecvMap;
        r0 = r20;	 Catch:{ MCSException -> 0x0058 }
        r13 = r9.get(r0);	 Catch:{ MCSException -> 0x0058 }
        r15 = r13;
        r15 = (com.abaltatech.mcp.mcs.common.IConnectionReceiver) r15;
        r14 = r15;
        if (r14 == 0) goto L_0x0080;	 Catch:{ MCSException -> 0x0058 }
    L_0x0075:
        r0 = r19;	 Catch:{ MCSException -> 0x0058 }
        r1 = r20;	 Catch:{ MCSException -> 0x0058 }
        r2 = r21;	 Catch:{ MCSException -> 0x0058 }
        r3 = r22;	 Catch:{ MCSException -> 0x0058 }
        r14.OnConnectionEstablished(r0, r1, r2, r3);	 Catch:{ MCSException -> 0x0058 }
    L_0x0080:
        r16 = 0;	 Catch:{ MCSException -> 0x0058 }
        r17 = 0;	 Catch:{ MCSException -> 0x0058 }
        r0 = r16;	 Catch:{ MCSException -> 0x0058 }
        r1 = r17;	 Catch:{ MCSException -> 0x0058 }
        r7.writeData(r0, r1);	 Catch:{ MCSException -> 0x0058 }
        return;
    L_0x008c:
        return;
    L_0x008d:
        return;
    L_0x008e:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.gateway.GatewayLayerBase.OnConnectionEstablished(com.abaltatech.mcp.mcs.common.IMCSConnectionAddress, com.abaltatech.mcp.mcs.common.IMCSConnectionAddress, com.abaltatech.mcp.mcs.common.IMCSDataLayer, com.abaltatech.mcp.mcs.common.IMCSMultiPointLayer):void");
    }

    public synchronized void attachToLayer(IMCSMultiPointLayer $r1) throws  {
        if (this.m_multipointLayer != null) {
            if (this.m_multipointLayer instanceof IConnectionListener) {
                ((IConnectionListener) this.m_multipointLayer).UnregisterNotification(this);
            }
            if ((this.m_multipointLayer instanceof IDatagramReceiver) && (this.m_multipointLayer instanceof IDatagramHandler)) {
                ((IDatagramHandler) this.m_multipointLayer).UnregisterNotification(this.m_datagramHandler);
            }
            if (this.m_multipointLayer instanceof IResolveAddress) {
                ((IResolveAddress) this.m_multipointLayer).UnregisterNotification((IResolveAddressRequestedNotification) this);
            }
            this.m_multipointLayer.unRegisterNotification(this);
            this.m_multipointLayer = null;
        }
        this.m_multipointLayer = $r1;
        this.m_listenRecvMap.clear();
        if (this.m_multipointLayer != null) {
            IMCSMultiPointLayer iMCSMultiPointLayer = this.m_multipointLayer;
            $r1 = iMCSMultiPointLayer;
            iMCSMultiPointLayer.registerNotification(this);
            iMCSMultiPointLayer = this.m_multipointLayer;
            if (iMCSMultiPointLayer instanceof IResolveAddress) {
                ((IResolveAddress) this.m_multipointLayer).RegisterNotification((IResolveAddressRequestedNotification) this);
            }
            iMCSMultiPointLayer = this.m_multipointLayer;
            if (iMCSMultiPointLayer instanceof IDatagramReceiver) {
                iMCSMultiPointLayer = this.m_multipointLayer;
                if (iMCSMultiPointLayer instanceof IDatagramHandler) {
                    ((IDatagramHandler) this.m_multipointLayer).RegisterNotification(this.m_datagramHandler);
                }
            }
            iMCSMultiPointLayer = this.m_multipointLayer;
            if (iMCSMultiPointLayer instanceof IConnectionListener) {
                ((IConnectionListener) this.m_multipointLayer).RegisterNotification(this);
            }
        }
    }

    public void addException(IMCSConnectionAddress $r1) throws  {
        synchronized (this.m_exceptions) {
            if (!this.m_exceptions.contains($r1)) {
                this.m_exceptions.add($r1);
            }
        }
    }

    public void removeException(IMCSConnectionAddress $r1) throws  {
        synchronized (this.m_exceptions) {
            this.m_exceptions.remove($r1);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void newConnectionRequested(com.abaltatech.mcp.mcs.common.IMCSMultiPointLayer r23, com.abaltatech.mcp.mcs.common.IMCSConnectionAddress r24, com.abaltatech.mcp.mcs.common.IMCSConnectionAddress r25, byte[] r26) throws  {
        /*
        r22 = this;
        r0 = r25;
        r4 = r0 instanceof com.abaltatech.mcp.mcs.tcpip.TCPIPAddress;
        if (r4 == 0) goto L_0x001c;
    L_0x0006:
        r6 = r25;
        r6 = (com.abaltatech.mcp.mcs.tcpip.TCPIPAddress) r6;
        r5 = r6;
        r7 = r5.getAddress();
        r8 = r7.getHostAddress();
        r9 = "0.0.0.0";
        r4 = r8.equals(r9);
        if (r4 == 0) goto L_0x001c;
    L_0x001b:
        return;
    L_0x001c:
        r0 = r22;
        r10 = r0.m_exceptions;
        monitor-enter(r10);
        r0 = r22;
        r11 = r0.m_exceptions;	 Catch:{ Throwable -> 0x0043 }
        r12 = r11.iterator();	 Catch:{ Throwable -> 0x0043 }
    L_0x0029:
        r4 = r12.hasNext();	 Catch:{ Throwable -> 0x0043 }
        if (r4 == 0) goto L_0x0046;
    L_0x002f:
        r13 = r12.next();	 Catch:{ Throwable -> 0x0043 }
        r15 = r13;
        r15 = (com.abaltatech.mcp.mcs.common.IMCSConnectionAddress) r15;	 Catch:{ Throwable -> 0x0043 }
        r14 = r15;
        r0 = r25;
        r4 = r0.isSubsetOf(r14);	 Catch:{ Throwable -> 0x0043 }
        if (r4 == 0) goto L_0x0029;
    L_0x0041:
        monitor-exit(r10);	 Catch:{ Throwable -> 0x0043 }
        return;
    L_0x0043:
        r16 = move-exception;
        monitor-exit(r10);	 Catch:{ Throwable -> 0x0043 }
        throw r16;
    L_0x0046:
        monitor-exit(r10);	 Catch:{ Throwable -> 0x0043 }
        r17 = 0;
        if (r23 == 0) goto L_0x0055;
    L_0x004b:
        r0 = r23;
        r1 = r24;
        r2 = r25;
        r17 = r0.createConnectionPoint(r1, r2);	 Catch:{ MCSException -> 0x0080 }
    L_0x0055:
        if (r17 == 0) goto L_0x008d;
    L_0x0057:
        r0 = r22;
        r1 = r24;
        r2 = r25;
        r3 = r26;
        r18 = r0.createSocket(r1, r2, r3);	 Catch:{ MCSException -> 0x0080 }
        if (r18 == 0) goto L_0x008e;
    L_0x0065:
        r19 = new com.abaltatech.mcp.mcs.connector.ConnectorLayer;	 Catch:{ MCSException -> 0x0080 }
        r0 = r19;
        r1 = r17;
        r2 = r18;
        r0.<init>(r1, r2);	 Catch:{ MCSException -> 0x0080 }
        if (r19 == 0) goto L_0x008f;
    L_0x0072:
        r0 = r22;
        r0 = r0.m_connectors;	 Catch:{ MCSException -> 0x0080 }
        r20 = r0;
        r1 = r17;
        r2 = r19;
        r0.put(r1, r2);	 Catch:{ MCSException -> 0x0080 }
        return;
    L_0x0080:
        r21 = move-exception;
        r0 = r21;
        r8 = r0.toString();
        r9 = "GatewayListener";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r9, r8);
        return;
    L_0x008d:
        return;
    L_0x008e:
        return;
    L_0x008f:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.gateway.GatewayLayerBase.newConnectionRequested(com.abaltatech.mcp.mcs.common.IMCSMultiPointLayer, com.abaltatech.mcp.mcs.common.IMCSConnectionAddress, com.abaltatech.mcp.mcs.common.IMCSConnectionAddress, byte[]):void");
    }

    private IMCSDataLayer createSocket(IMCSConnectionAddress $r1, IMCSConnectionAddress $r2, byte[] payload) throws MCSException {
        if (($r1 instanceof TCPIPAddress) && ($r2 instanceof TCPIPAddress)) {
            TCPIPAddress $r4 = (TCPIPAddress) $r1;
            TCPIPAddress $r5 = (TCPIPAddress) $r2;
            if (!($r4.getAddress() == null || $r5.getAddress() == null || $r4.getPort() == 0 || $r5.getPort() == 0)) {
                return new SocketChannelTransportLayer($r5.getAddress(), $r5.getPort());
            }
        }
        throw new MCSException("Unsupported address type");
    }

    public void onConnectionClosed(IMCSDataLayer $r1, IMCSConnectionAddress fromAddress, IMCSConnectionAddress toAddress) throws  {
        ConnectorLayer $r6 = (ConnectorLayer) this.m_connectors.remove($r1);
        if (!$assertionsDisabled && $r6 == null) {
            throw new AssertionError();
        }
    }

    public boolean OnResolveAddressRequested(IResolveAddress $r1, int $i0, String $r2) throws  {
        Exception $r11;
        TCPIPAddress $r5 = null;
        if ($assertionsDisabled || $r2 != null) {
            if ($r2 != null) {
                String $r7 = $r2;
                String $r8 = "";
                int $i1 = $r2.lastIndexOf(58);
                if ($i1 != -1) {
                    $r8 = $r2.substring($i1 + 1);
                    $r7 = $r2.substring(0, $i1);
                }
                try {
                    InetAddress $r9 = InetAddress.getByName($r7);
                    try {
                        $i1 = Integer.parseInt($r8);
                    } catch (NumberFormatException e) {
                        $i1 = 80;
                    }
                    TCPIPAddress $r4 = new TCPIPAddress($r9, $i1);
                    try {
                        MCSLogger.log("Gateway:  Resolve address " + $r2 + " -> " + $r4);
                        $r5 = $r4;
                    } catch (Exception e2) {
                        $r11 = e2;
                        $r5 = $r4;
                        MCSLogger.log("Gateway:  Resolve address " + $r2 + " -> address lookup failed: " + $r11.toString());
                        $r1.SendResolveAddressResponse($i0, $r2, $r5);
                        return true;
                    }
                } catch (Exception e3) {
                    $r11 = e3;
                    MCSLogger.log("Gateway:  Resolve address " + $r2 + " -> address lookup failed: " + $r11.toString());
                    $r1.SendResolveAddressResponse($i0, $r2, $r5);
                    return true;
                }
            }
            $r1.SendResolveAddressResponse($i0, $r2, $r5);
            return true;
        }
        throw new AssertionError();
    }

    public IMCSConnectionAddress OnStartListening(IMCSConnectionAddress $r1, int $i0, IConnectionReceiver $r2) throws  {
        $r1 = this.m_listenConnHandler.StartListening($r1, $i0, this);
        if ($r1 == null) {
            return $r1;
        }
        this.m_listenRecvMap.put($r1, $r2);
        return $r1;
    }

    public void OnStopListening(IMCSConnectionAddress $r1) throws  {
        this.m_listenRecvMap.remove($r1);
        this.m_listenConnHandler.StopListening($r1);
    }

    public void OnConnectionFailed() throws  {
    }
}
