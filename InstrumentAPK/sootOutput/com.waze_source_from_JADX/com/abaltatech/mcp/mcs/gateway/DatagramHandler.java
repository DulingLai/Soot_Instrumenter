package com.abaltatech.mcp.mcs.gateway;

import com.abaltatech.mcp.mcs.common.IDatagramHandlerNotification;
import com.abaltatech.mcp.mcs.common.IDatagramReceiver;
import com.abaltatech.mcp.mcs.common.IMCSConnectionAddress;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import com.abaltatech.mcp.mcs.tcpip.TCPIPAddress;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;

public class DatagramHandler implements IDatagramHandlerNotification {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static int ms_datagramReqID = 0;
    private HashMap<Integer, DatagramConnection> m_datagramHandlerMap = new HashMap();
    private DatagramSocket m_datagramSendSocket = null;
    private int m_maxDatagramSize;

    private class DatagramConnection extends Thread {
        private int m_connID;
        private DatagramSocket m_listenSocket;
        private IDatagramReceiver m_receiver;

        public DatagramConnection(int $i0, DatagramSocket $r2, IDatagramReceiver $r3) throws  {
            this.m_connID = $i0;
            this.m_listenSocket = $r2;
            this.m_receiver = $r3;
            try {
                this.m_listenSocket.setSoTimeout(10);
            } catch (IOException e) {
                DatagramHandler.this.m_datagramSendSocket = null;
            }
        }

        public DatagramSocket GetListenSocket() throws  {
            return this.m_listenSocket;
        }

        public void Terminate() throws  {
            this.m_listenSocket.close();
        }

        public void run() throws  {
            byte[] $r2 = new byte[DatagramHandler.this.m_maxDatagramSize];
            DatagramPacket $r3 = new DatagramPacket($r2, $r2.length);
            while (!this.m_listenSocket.isClosed()) {
                try {
                    this.m_listenSocket.receive($r3);
                    if ($r3.getLength() > 0) {
                        TCPIPAddress $r1 = new TCPIPAddress($r3.getAddress(), $r3.getPort());
                        MCSLogger.log("DatagramHandler: Received " + $r3.getLength() + " bytes datagram from " + $r1.toString());
                        this.m_receiver.OnDatagramReceived(this.m_connID, $r1, $r3.getData(), $r3.getLength());
                    } else {
                        continue;
                    }
                } catch (InterruptedIOException e) {
                } catch (IOException e2) {
                    return;
                }
            }
        }
    }

    static {
        boolean $z0;
        if (DatagramHandler.class.desiredAssertionStatus()) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        $assertionsDisabled = $z0;
    }

    public DatagramHandler() throws  {
        try {
            this.m_datagramSendSocket = new DatagramSocket();
            this.m_maxDatagramSize = this.m_datagramSendSocket.getSendBufferSize();
            this.m_datagramSendSocket.setBroadcast(true);
        } catch (IOException e) {
            this.m_datagramSendSocket = null;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int OnStartDatagramListening(com.abaltatech.mcp.mcs.common.IMCSConnectionAddress r18, com.abaltatech.mcp.mcs.common.IDatagramReceiver r19) throws  {
        /*
        r17 = this;
        r2 = -1;
        monitor-enter(r17);
        r4 = r18;
        r4 = (com.abaltatech.mcp.mcs.tcpip.TCPIPAddress) r4;	 Catch:{ Throwable -> 0x0054 }
        r3 = r4;
        r5 = new java.net.DatagramSocket;	 Catch:{ Throwable -> 0x0054 }
        r6 = r3.getPort();	 Catch:{ Exception -> 0x0038 }
        r7 = r3.getAddress();	 Catch:{ Exception -> 0x0038 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x0038 }
        r8 = 1;
        r5.setBroadcast(r8);	 Catch:{ Exception -> 0x0038 }
        r6 = ms_datagramReqID;	 Catch:{ Throwable -> 0x0054 }
        r6 = r6 + 1;
        ms_datagramReqID = r6;	 Catch:{ Throwable -> 0x0054 }
        r9 = new com.abaltatech.mcp.mcs.gateway.DatagramHandler$DatagramConnection;
        r0 = r17;
        r1 = r19;
        r9.<init>(r6, r5, r1);	 Catch:{ Exception -> 0x0059, Throwable -> 0x0057 }
        r0 = r17;
        r10 = r0.m_datagramHandlerMap;	 Catch:{ Exception -> 0x0059, Throwable -> 0x0057 }
        r11 = java.lang.Integer.valueOf(r6);	 Catch:{ Exception -> 0x0059, Throwable -> 0x0057 }
        r10.put(r11, r9);	 Catch:{ Exception -> 0x0059, Throwable -> 0x0057 }
        r9.start();	 Catch:{ Exception -> 0x0059, Throwable -> 0x0057 }
        r2 = r6;
    L_0x0036:
        monitor-exit(r17);	 Catch:{ Throwable -> 0x0054 }
        return r2;
    L_0x0038:
        r12 = move-exception;
    L_0x0039:
        r13 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0054 }
        r13.<init>();	 Catch:{ Throwable -> 0x0054 }
        r14 = "DatagramHandler:  Error - ";
        r13 = r13.append(r14);	 Catch:{ Throwable -> 0x0054 }
        r15 = r12.getMessage();	 Catch:{ Throwable -> 0x0054 }
        r13 = r13.append(r15);	 Catch:{ Throwable -> 0x0054 }
        r15 = r13.toString();	 Catch:{ Throwable -> 0x0054 }
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r15);	 Catch:{ Throwable -> 0x0054 }
        goto L_0x0036;
    L_0x0054:
        r16 = move-exception;
    L_0x0055:
        monitor-exit(r17);	 Catch:{ Throwable -> 0x0054 }
        throw r16;
    L_0x0057:
        r16 = move-exception;
        goto L_0x0055;
    L_0x0059:
        r12 = move-exception;
        r2 = r6;
        goto L_0x0039;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.gateway.DatagramHandler.OnStartDatagramListening(com.abaltatech.mcp.mcs.common.IMCSConnectionAddress, com.abaltatech.mcp.mcs.common.IDatagramReceiver):int");
    }

    public void OnStopDatagramListening(int $i0) throws  {
        synchronized (this) {
            DatagramConnection $r4 = (DatagramConnection) this.m_datagramHandlerMap.get(Integer.valueOf($i0));
            if ($r4 != null) {
                this.m_datagramHandlerMap.remove(Integer.valueOf($i0));
            }
        }
        if (!$assertionsDisabled && $r4 == null) {
            throw new AssertionError();
        } else if ($r4 != null) {
            $r4.Terminate();
        }
    }

    public void OnSendDatagram(int $i0, IMCSConnectionAddress $r1, byte[] $r2, int $i1) throws  {
        DatagramSocket $r9;
        synchronized (this) {
            DatagramConnection $r8 = (DatagramConnection) this.m_datagramHandlerMap.get(Integer.valueOf($i0));
        }
        if ($r8 == null) {
            $r9 = this.m_datagramSendSocket;
        } else {
            $r9 = $r8.GetListenSocket();
        }
        if ($r9 != null) {
            if ($i1 > this.m_maxDatagramSize) {
                StringBuilder $r10 = new StringBuilder().append("DatagramHandler:  The datagram data is too large. Requested ").append($i1).append(" bytes, but only ");
                int $i02 = this.m_maxDatagramSize;
                MCSLogger.log($r10.append($i02).append(" bytes allowed.").toString());
                return;
            }
            try {
                TCPIPAddress $r13 = (TCPIPAddress) $r1;
                $r9.send(new DatagramPacket($r2, $i1, $r13.getAddress(), $r13.getPort()));
                MCSLogger.log("DatagramHandler: Send " + $i1 + " bytes datagram to " + $r13.toString());
            } catch (Exception $r3) {
                MCSLogger.log("DatagramHandler:  Error - " + $r3.getMessage());
            }
        }
    }
}
