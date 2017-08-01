package com.abaltatech.mcs.gateway;

import com.abaltatech.mcs.common.IConnectionListener;
import com.abaltatech.mcs.common.IConnectionListenerNotification;
import com.abaltatech.mcs.common.IConnectionReceiver;
import com.abaltatech.mcs.common.IDatagramHandler;
import com.abaltatech.mcs.common.IDatagramReceiver;
import com.abaltatech.mcs.common.IMCSConnectionAddress;
import com.abaltatech.mcs.common.IMCSDataLayer;
import com.abaltatech.mcs.common.IMCSMultiPointLayer;
import com.abaltatech.mcs.common.IMCSMultiPointLayerNotification;
import com.abaltatech.mcs.common.IResolveAddress;
import com.abaltatech.mcs.common.IResolveAddressRequestedNotification;
import com.abaltatech.mcs.common.MCSException;
import com.abaltatech.mcs.connector.ConnectorLayer;
import com.abaltatech.mcs.logger.MCSLogger;
import com.abaltatech.mcs.sockettransport.SocketChannelTransportLayer;
import com.abaltatech.mcs.tcpip.TCPIPAddress;
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

    public void OnConnectionEstablished(com.abaltatech.mcs.common.IMCSConnectionAddress r19, com.abaltatech.mcs.common.IMCSConnectionAddress r20, com.abaltatech.mcs.common.IMCSDataLayer r21) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0084 in list []
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
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "ListeningConnectionHandler:  Incomming connection accepted from=";
        r3 = r3.append(r4);
        r0 = r19;
        r3 = r3.append(r0);
        r4 = " to=";
        r3 = r3.append(r4);
        r0 = r20;
        r3 = r3.append(r0);
        r5 = r3.toString();
        com.abaltatech.mcs.logger.MCSLogger.log(r5);
        r0 = r18;
        r6 = r0.m_multipointLayer;
        if (r6 == 0) goto L_0x0090;
    L_0x002a:
        r0 = r18;
        r6 = r0.m_multipointLayer;
        r0 = r20;	 Catch:{ MCSException -> 0x005e }
        r1 = r19;	 Catch:{ MCSException -> 0x005e }
        r7 = r6.createConnectionPoint(r0, r1);	 Catch:{ MCSException -> 0x005e }
        if (r7 == 0) goto L_0x0091;
    L_0x0038:
        r8 = new com.abaltatech.mcs.connector.ConnectorLayer;	 Catch:{ MCSException -> 0x005e }
        r0 = r21;	 Catch:{ MCSException -> 0x005e }
        r8.<init>(r7, r0);	 Catch:{ MCSException -> 0x005e }
        if (r8 == 0) goto L_0x0092;
    L_0x0041:
        r0 = r18;	 Catch:{ MCSException -> 0x005e }
        r9 = r0.m_connectors;	 Catch:{ MCSException -> 0x005e }
        r9.put(r7, r8);	 Catch:{ MCSException -> 0x005e }
        r10 = $assertionsDisabled;
        if (r10 != 0) goto L_0x0069;
    L_0x004c:
        r0 = r18;	 Catch:{ MCSException -> 0x005e }
        r9 = r0.m_listenRecvMap;	 Catch:{ MCSException -> 0x005e }
        r0 = r20;	 Catch:{ MCSException -> 0x005e }
        r10 = r9.containsKey(r0);	 Catch:{ MCSException -> 0x005e }
        if (r10 != 0) goto L_0x0069;
    L_0x0058:
        r11 = new java.lang.AssertionError;	 Catch:{ MCSException -> 0x005e }
        r11.<init>();	 Catch:{ MCSException -> 0x005e }
        throw r11;
    L_0x005e:
        r12 = move-exception;
        r5 = r12.toString();
        r4 = "GatewayListener";
        com.abaltatech.mcs.logger.MCSLogger.log(r4, r5);
        return;
    L_0x0069:
        r0 = r18;
        r9 = r0.m_listenRecvMap;
        r0 = r20;	 Catch:{ MCSException -> 0x005e }
        r13 = r9.get(r0);	 Catch:{ MCSException -> 0x005e }
        r15 = r13;
        r15 = (com.abaltatech.mcs.common.IConnectionReceiver) r15;
        r14 = r15;
        if (r14 == 0) goto L_0x0084;	 Catch:{ MCSException -> 0x005e }
    L_0x007b:
        r0 = r19;	 Catch:{ MCSException -> 0x005e }
        r1 = r20;	 Catch:{ MCSException -> 0x005e }
        r2 = r21;	 Catch:{ MCSException -> 0x005e }
        r14.OnConnectionEstablished(r0, r1, r2);	 Catch:{ MCSException -> 0x005e }
    L_0x0084:
        r16 = 0;	 Catch:{ MCSException -> 0x005e }
        r17 = 0;	 Catch:{ MCSException -> 0x005e }
        r0 = r16;	 Catch:{ MCSException -> 0x005e }
        r1 = r17;	 Catch:{ MCSException -> 0x005e }
        r7.writeData(r0, r1);	 Catch:{ MCSException -> 0x005e }
        return;
    L_0x0090:
        return;
    L_0x0091:
        return;
    L_0x0092:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcs.gateway.GatewayLayerBase.OnConnectionEstablished(com.abaltatech.mcs.common.IMCSConnectionAddress, com.abaltatech.mcs.common.IMCSConnectionAddress, com.abaltatech.mcs.common.IMCSDataLayer):void");
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
    public void newConnectionRequested(com.abaltatech.mcs.common.IMCSConnectionAddress r21, com.abaltatech.mcs.common.IMCSConnectionAddress r22, byte[] r23) throws  {
        /*
        r20 = this;
        r0 = r20;
        r4 = r0.m_exceptions;
        monitor-enter(r4);
        r0 = r20;
        r5 = r0.m_exceptions;	 Catch:{ Throwable -> 0x0065 }
        r6 = r5.iterator();	 Catch:{ Throwable -> 0x0065 }
    L_0x000d:
        r7 = r6.hasNext();	 Catch:{ Throwable -> 0x0065 }
        if (r7 == 0) goto L_0x0025;
    L_0x0013:
        r8 = r6.next();	 Catch:{ Throwable -> 0x0065 }
        r10 = r8;
        r10 = (com.abaltatech.mcs.common.IMCSConnectionAddress) r10;	 Catch:{ Throwable -> 0x0065 }
        r9 = r10;
        r0 = r22;
        r7 = r0.isSubsetOf(r9);	 Catch:{ Throwable -> 0x0065 }
        if (r7 == 0) goto L_0x000d;
    L_0x0023:
        monitor-exit(r4);	 Catch:{ Throwable -> 0x0065 }
        return;
    L_0x0025:
        monitor-exit(r4);	 Catch:{ Throwable -> 0x0065 }
        r11 = 0;
        r0 = r20;
        r12 = r0.m_multipointLayer;
        if (r12 == 0) goto L_0x0035;
    L_0x002d:
        r0 = r21;
        r1 = r22;
        r11 = r12.createConnectionPoint(r0, r1);	 Catch:{ MCSException -> 0x0054 }
    L_0x0035:
        if (r11 == 0) goto L_0x0068;
    L_0x0037:
        r0 = r20;
        r1 = r21;
        r2 = r22;
        r3 = r23;
        r13 = r0.createSocket(r1, r2, r3);	 Catch:{ MCSException -> 0x0054 }
        if (r13 == 0) goto L_0x0069;
    L_0x0045:
        r14 = new com.abaltatech.mcs.connector.ConnectorLayer;	 Catch:{ MCSException -> 0x0054 }
        r14.<init>(r11, r13);	 Catch:{ MCSException -> 0x0054 }
        if (r14 == 0) goto L_0x006a;
    L_0x004c:
        r0 = r20;
        r15 = r0.m_connectors;	 Catch:{ MCSException -> 0x0054 }
        r15.put(r11, r14);	 Catch:{ MCSException -> 0x0054 }
        return;
    L_0x0054:
        r16 = move-exception;
        r0 = r16;
        r17 = r0.toString();
        r18 = "GatewayListener";
        r0 = r18;
        r1 = r17;
        com.abaltatech.mcs.logger.MCSLogger.log(r0, r1);
        return;
    L_0x0065:
        r19 = move-exception;
        monitor-exit(r4);	 Catch:{ Throwable -> 0x0065 }
        throw r19;
    L_0x0068:
        return;
    L_0x0069:
        return;
    L_0x006a:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcs.gateway.GatewayLayerBase.newConnectionRequested(com.abaltatech.mcs.common.IMCSConnectionAddress, com.abaltatech.mcs.common.IMCSConnectionAddress, byte[]):void");
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
}
