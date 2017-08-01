package com.abaltatech.weblink.servercore;

import com.abaltatech.mcs.common.IConnectionListener;
import com.abaltatech.mcs.common.IConnectionListenerNotification;
import com.abaltatech.mcs.common.IConnectionReceiver;
import com.abaltatech.mcs.common.IMCSConnectionAddress;
import com.abaltatech.mcs.common.IMCSConnectionClosedNotification;
import com.abaltatech.mcs.common.IMCSDataLayer;
import com.abaltatech.mcs.gateway.ListenConnectionHandler;
import com.abaltatech.mcs.tcpip.TCPIPAddress;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class SocketConnListener implements IConnectionListener, IConnectionReceiver, IMCSConnectionClosedNotification {
    static final /* synthetic */ boolean $assertionsDisabled = (!SocketConnListener.class.desiredAssertionStatus());
    protected InetAddress m_broadcastAddress;
    protected int m_broadcastPort;
    protected Thread m_broadcastThread = null;
    protected int m_connectionsAvail = 0;
    private boolean m_isBroadcastingEnabled;
    protected IMCSConnectionAddress m_listenAddress = null;
    protected ListenConnectionHandler m_listener = null;
    protected IConnectionReceiver m_receiver = null;
    protected String m_serverName;

    class C03921 extends Thread {
        private volatile boolean m_isStopped = false;

        public void run() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x005e in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r17 = this;
            r1 = 0;
            r2 = 0;
            r3 = 0;
            r4 = new java.net.DatagramSocket;
            r0 = r17;
            r5 = com.abaltatech.weblink.servercore.SocketConnListener.this;
            r6 = r5.m_broadcastPort;
            r4.<init>(r6);	 Catch:{ InterruptedException -> 0x0062, Exception -> 0x0060 }
            r7 = 1;	 Catch:{ InterruptedException -> 0x003f }
            r4.setBroadcast(r7);	 Catch:{ InterruptedException -> 0x003f }
        L_0x0012:
            r0 = r17;	 Catch:{ InterruptedException -> 0x003f }
            r8 = r0.isInterrupted();	 Catch:{ InterruptedException -> 0x003f }
            if (r8 != 0) goto L_0x005e;	 Catch:{ Exception -> 0x0052 }
        L_0x001a:
            r0 = r17;	 Catch:{ Exception -> 0x0052 }
            r8 = r0.m_isStopped;	 Catch:{ Exception -> 0x0052 }
            if (r8 != 0) goto L_0x005e;	 Catch:{ Exception -> 0x0052 }
        L_0x0020:
            r0 = r17;	 Catch:{ Exception -> 0x0052 }
            r5 = com.abaltatech.weblink.servercore.SocketConnListener.this;	 Catch:{ Exception -> 0x0052 }
            r9 = r5.m_serverName;	 Catch:{ Exception -> 0x0052 }
            if (r2 == r9) goto L_0x0036;	 Catch:{ InterruptedException -> 0x003f }
        L_0x0028:
            r0 = r17;
            r5 = com.abaltatech.weblink.servercore.SocketConnListener.this;
            r2 = r5.m_serverName;
            r0 = r17;	 Catch:{ InterruptedException -> 0x003f }
            r5 = com.abaltatech.weblink.servercore.SocketConnListener.this;	 Catch:{ InterruptedException -> 0x003f }
            r3 = r5.createBroadcastPacket();	 Catch:{ InterruptedException -> 0x003f }
        L_0x0036:
            r4.send(r3);	 Catch:{ SocketException -> 0x0047 }
        L_0x0039:
            r10 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;	 Catch:{ InterruptedException -> 0x003f }
            java.lang.Thread.sleep(r10);	 Catch:{ InterruptedException -> 0x003f }
            goto L_0x0012;
        L_0x003f:
            r12 = move-exception;
            r1 = r4;
        L_0x0041:
            if (r1 == 0) goto L_0x0064;
        L_0x0043:
            r1.close();
            return;
        L_0x0047:
            r13 = move-exception;
            r9 = r13.toString();	 Catch:{ InterruptedException -> 0x003f }
            r14 = "ServerBroadcasting";	 Catch:{ InterruptedException -> 0x003f }
            com.abaltatech.mcs.logger.MCSLogger.log(r14, r9);	 Catch:{ InterruptedException -> 0x003f }
            goto L_0x0039;
        L_0x0052:
            r15 = move-exception;
            r1 = r4;
        L_0x0054:
            r2 = r15.toString();
            r14 = "ServerBroadcasting";
            com.abaltatech.mcs.logger.MCSLogger.log(r14, r2);
            goto L_0x0041;
        L_0x005e:
            r1 = r4;
            goto L_0x0041;
        L_0x0060:
            r15 = move-exception;
            goto L_0x0054;
        L_0x0062:
            r16 = move-exception;
            goto L_0x0041;
        L_0x0064:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.weblink.servercore.SocketConnListener.1.run():void");
        }

        C03921() throws  {
        }

        public void interrupt() throws  {
            this.m_isStopped = true;
            super.interrupt();
        }
    }

    public SocketConnListener(String $r1, InetAddress $r2, int $i0, boolean $z0) throws  {
        this.m_broadcastAddress = $r2;
        this.m_broadcastPort = $i0;
        this.m_serverName = $r1;
        this.m_isBroadcastingEnabled = $z0;
    }

    public String getServerName() throws  {
        return this.m_serverName;
    }

    public void setServerName(String $r1) throws  {
        this.m_serverName = $r1;
    }

    public synchronized IMCSConnectionAddress StartListening(IMCSConnectionAddress $r1, int $i0, IConnectionReceiver $r2) throws  {
        IMCSConnectionAddress $r3;
        $r3 = null;
        if (this.m_listener == null && $r2 != null) {
            this.m_listener = new ListenConnectionHandler();
            this.m_connectionsAvail = $i0;
            this.m_receiver = $r2;
            $r1 = this.m_listener.StartListening($r1, $i0, this);
            $r3 = $r1;
            if ($r1 != null) {
                this.m_listenAddress = $r1;
                if (this.m_connectionsAvail > 0) {
                    StartNetworkBroadcasting();
                }
            }
        }
        return $r3;
    }

    public synchronized void StopListening(IMCSConnectionAddress listenAddress) throws  {
        if (this.m_listener != null) {
            this.m_listener.StopListening(this.m_listenAddress);
            this.m_listener = null;
            this.m_listenAddress = null;
            this.m_receiver = null;
            this.m_connectionsAvail = 0;
            StopNetworkBroadcasting();
        }
    }

    public void RegisterNotification(IConnectionListenerNotification notification) throws  {
    }

    public void UnregisterNotification(IConnectionListenerNotification notification) throws  {
    }

    public synchronized void OnConnectionEstablished(IMCSConnectionAddress $r1, IMCSConnectionAddress $r2, IMCSDataLayer $r3) throws  {
        if ($assertionsDisabled || this.m_receiver != null) {
            this.m_connectionsAvail--;
            if (this.m_connectionsAvail == 0) {
                StopNetworkBroadcasting();
            }
            $r3.registerCloseNotification(this);
            this.m_receiver.OnConnectionEstablished($r1, $r2, $r3);
        } else {
            throw new AssertionError();
        }
    }

    public void onConnectionClosed(IMCSDataLayer $r1) throws  {
        $r1.unregisterCloseNotification(this);
        this.m_connectionsAvail++;
        StartNetworkBroadcasting();
    }

    private DatagramPacket createBroadcastPacket() throws Exception {
        InetAddress $r2 = this.m_broadcastAddress != null ? this.m_broadcastAddress : InetAddress.getByName("255.255.255.255");
        byte[] $r7 = (this.m_serverName + "," + ((TCPIPAddress) this.m_listenAddress).getPort()).getBytes();
        return new DatagramPacket($r7, $r7.length, $r2, this.m_broadcastPort);
    }

    protected void StartNetworkBroadcasting() throws  {
        if (this.m_isBroadcastingEnabled && this.m_serverName != null && this.m_broadcastPort > 0 && this.m_listenAddress != null && (this.m_listenAddress instanceof TCPIPAddress)) {
            this.m_broadcastThread = new C03921();
            this.m_broadcastThread.start();
        }
    }

    protected void StopNetworkBroadcasting() throws  {
        if (this.m_broadcastThread != null) {
            this.m_broadcastThread.interrupt();
            this.m_broadcastThread = null;
        }
    }
}
