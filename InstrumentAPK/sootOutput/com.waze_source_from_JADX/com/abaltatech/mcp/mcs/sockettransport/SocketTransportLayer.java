package com.abaltatech.mcp.mcs.sockettransport;

import com.abaltatech.mcp.mcs.common.IMCSDataStats;
import com.abaltatech.mcp.mcs.common.MCSDataLayerBase;
import com.abaltatech.mcp.mcs.common.MCSException;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketTransportLayer extends MCSDataLayerBase implements Runnable {
    private static int ms_connID = 0;
    private final String TAG;
    private Socket m_clientSocket = null;
    private final int m_connID;
    private InputStream m_input = null;
    private boolean m_isNotificationSent = false;
    private boolean m_isStopped = false;
    private OutputStream m_output = null;
    private Thread m_thread;

    public void run() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0024 in list [B:38:0x006e]
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
    L_0x0000:
        r1 = 10;	 Catch:{ InterruptedException -> 0x0079 }
        java.lang.Thread.sleep(r1);	 Catch:{ InterruptedException -> 0x0079 }
    L_0x0005:
        r3 = 0;
        r4 = 0;
        monitor-enter(r17);
        r0 = r17;	 Catch:{ IOException -> 0x0063 }
        r5 = r0.m_isStopped;	 Catch:{ IOException -> 0x0063 }
        if (r5 == 0) goto L_0x0010;	 Catch:{ IOException -> 0x0063 }
    L_0x000e:
        monitor-exit(r17);	 Catch:{ IOException -> 0x0063 }
        return;
    L_0x0010:
        r0 = r17;	 Catch:{ IOException -> 0x0063 }
        r6 = r0.m_clientSocket;	 Catch:{ IOException -> 0x0063 }
        r5 = r6.isClosed();	 Catch:{ IOException -> 0x0063 }
        if (r5 != 0) goto L_0x0024;	 Catch:{ IOException -> 0x0063 }
    L_0x001a:
        r0 = r17;	 Catch:{ IOException -> 0x0063 }
        r6 = r0.m_clientSocket;	 Catch:{ IOException -> 0x0063 }
        r5 = r6.isConnected();	 Catch:{ IOException -> 0x0063 }
        if (r5 != 0) goto L_0x004c;
    L_0x0024:
        r4 = 1;
        r0 = r17;
        r7 = r0.TAG;
        r8 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0063 }
        r8.<init>();	 Catch:{ IOException -> 0x0063 }
        r9 = "Closed connection to ";	 Catch:{ IOException -> 0x0063 }
        r8 = r8.append(r9);	 Catch:{ IOException -> 0x0063 }
        r0 = r17;	 Catch:{ IOException -> 0x0063 }
        r6 = r0.m_clientSocket;	 Catch:{ IOException -> 0x0063 }
        r8 = r8.append(r6);	 Catch:{ IOException -> 0x0063 }
        r10 = r8.toString();	 Catch:{ IOException -> 0x0063 }
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r7, r10);	 Catch:{ IOException -> 0x0063 }
    L_0x0043:
        monitor-exit(r17);	 Catch:{ IOException -> 0x0063 }
        if (r4 == 0) goto L_0x0071;
    L_0x0046:
        r0 = r17;
        r0.closeConnection();
        return;
    L_0x004c:
        r0 = r17;	 Catch:{ IOException -> 0x0063 }
        r11 = r0.m_input;	 Catch:{ IOException -> 0x0063 }
        r12 = r11.available();	 Catch:{ IOException -> 0x0063 }
        if (r12 <= 0) goto L_0x0043;	 Catch:{ IOException -> 0x0063 }
    L_0x0056:
        r13 = 1;	 Catch:{ IOException -> 0x0063 }
        r0 = r17;	 Catch:{ IOException -> 0x0063 }
        r0.m_isNotificationSent = r13;	 Catch:{ IOException -> 0x0063 }
        r3 = 1;	 Catch:{ IOException -> 0x0063 }
        r0 = r17;	 Catch:{ IOException -> 0x0063 }
        r5 = r0.m_isNotificationSent;	 Catch:{ IOException -> 0x0063 }
        if (r5 == 0) goto L_0x0043;
    L_0x0062:
        goto L_0x0043;
    L_0x0063:
        r14 = move-exception;
        r0 = r17;	 Catch:{ IOException -> 0x0063 }
        r7 = r0.TAG;	 Catch:{ IOException -> 0x0063 }
        r9 = "run connection exception: ";	 Catch:{ IOException -> 0x0063 }
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r7, r9, r14);	 Catch:{ IOException -> 0x0063 }
        goto L_0x0043;	 Catch:{ IOException -> 0x0063 }
    L_0x006e:
        r15 = move-exception;	 Catch:{ IOException -> 0x0063 }
        monitor-exit(r17);	 Catch:{ IOException -> 0x0063 }
        throw r15;
    L_0x0071:
        if (r3 == 0) goto L_0x0000;
    L_0x0073:
        r0 = r17;
        r0.notifyForData();
        goto L_0x0000;
    L_0x0079:
        r16 = move-exception;
        goto L_0x0005;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.sockettransport.SocketTransportLayer.run():void");
    }

    public SocketTransportLayer(String $r1, int $i0) throws MCSException {
        int $i1 = ms_connID + 1;
        ms_connID = $i1;
        this.m_connID = $i1;
        this.TAG = "===> SOCKET(" + this.m_connID + ")";
        try {
            this.m_clientSocket = new Socket($r1, $i0);
            this.m_clientSocket.setKeepAlive(true);
            this.m_output = this.m_clientSocket.getOutputStream();
            this.m_input = this.m_clientSocket.getInputStream();
            this.m_thread = new Thread(this);
            this.m_thread.setName("SocketTransportLayer Read Thread from Host and Port. " + this.m_connID);
            this.m_thread.start();
            MCSLogger.log(this.TAG, "Established connection to " + this.m_clientSocket);
        } catch (IOException $r2) {
            MCSLogger.log(this.TAG, "Error establishing connection " + $r1 + ":" + $i0);
            throw new MCSException("Error establishing connection " + $r1 + ":" + $i0 + "\n" + $r2.toString());
        }
    }

    public SocketTransportLayer(Socket $r1) throws MCSException {
        int $i0 = ms_connID + 1;
        ms_connID = $i0;
        this.m_connID = $i0;
        this.TAG = "===> SOCKET(" + this.m_connID + ")";
        this.m_clientSocket = $r1;
        try {
            this.m_clientSocket.setKeepAlive(true);
            this.m_output = this.m_clientSocket.getOutputStream();
            this.m_input = this.m_clientSocket.getInputStream();
            this.m_thread = new Thread(this);
            this.m_thread.setName("SocketTransportLayer Read Thread from Client Socket. " + this.m_connID);
            this.m_thread.start();
            MCSLogger.log(this.TAG, "Accepted connection from " + this.m_clientSocket);
        } catch (IOException $r2) {
            MCSLogger.log(this.TAG, "Error opening socket transport layer: " + $r2.toString());
            throw new MCSException("Error opening socket transport layer: " + $r2.toString());
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int readData(byte[] r12, int r13) throws  {
        /*
        r11 = this;
        monitor-enter(r11);
        r0 = 0;
        r11.m_isNotificationSent = r0;	 Catch:{ Throwable -> 0x002b }
        r1 = r11.m_isStopped;	 Catch:{ Throwable -> 0x002b }
        if (r1 != 0) goto L_0x0028;
    L_0x0008:
        r2 = r11.m_input;	 Catch:{ Throwable -> 0x002b }
        if (r2 == 0) goto L_0x0028;
    L_0x000c:
        r2 = r11.m_input;	 Catch:{ Throwable -> 0x002b }
        r3 = r2.available();	 Catch:{ Throwable -> 0x002b }
        if (r3 <= 0) goto L_0x0028;
    L_0x0014:
        r2 = r11.m_input;	 Catch:{ Throwable -> 0x002b }
        r0 = 0;
        r13 = r2.read(r12, r0, r13);	 Catch:{ Throwable -> 0x002b }
        if (r13 <= 0) goto L_0x0026;
    L_0x001d:
        r4 = r11.getDataStats();	 Catch:{ Throwable -> 0x002b }
        if (r4 == 0) goto L_0x0026;
    L_0x0023:
        r4.onDataReceived(r13);	 Catch:{ Throwable -> 0x002b }
    L_0x0026:
        monitor-exit(r11);	 Catch:{ Throwable -> 0x002b }
        return r13;
    L_0x0028:
        monitor-exit(r11);	 Catch:{ Throwable -> 0x002b }
    L_0x0029:
        r0 = 0;
        return r0;
    L_0x002b:
        r5 = move-exception;
        monitor-exit(r11);	 Catch:{ Throwable -> 0x002b }
        throw r5;	 Catch:{ IOException -> 0x002e }
    L_0x002e:
        r6 = move-exception;
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r8 = r11.TAG;
        r7 = r7.append(r8);
        r9 = " EXCEPTION";
        r7 = r7.append(r9);
        r8 = r7.toString();
        r10 = r6.toString();
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r8, r10);
        r11.closeConnection();
        goto L_0x0029;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.sockettransport.SocketTransportLayer.readData(byte[], int):int");
    }

    protected void writeDataInternal(byte[] $r1, int $i0) throws  {
        OutputStream $r3 = this.m_isStopped ? null : this.m_output;
        if ($r3 != null) {
            try {
                $r3.write($r1, 0, $i0);
                IMCSDataStats $r4 = getDataStats();
                if ($r4 != null) {
                    $r4.onDataSent($i0);
                }
            } catch (IOException $r2) {
                MCSLogger.log(this.TAG + "EXCEPTION", $r2.toString());
                closeConnection();
            }
        }
    }

    public void closeConnection() throws  {
        if (!this.m_isStopped) {
            this.m_isStopped = true;
            if (this.m_input != null) {
                try {
                    this.m_input.close();
                } catch (IOException e) {
                }
                this.m_input = null;
            }
            if (this.m_output != null) {
                try {
                    this.m_output.close();
                } catch (IOException e2) {
                }
                this.m_output = null;
            }
            if (this.m_clientSocket != null) {
                try {
                    this.m_clientSocket.close();
                } catch (IOException e3) {
                }
                this.m_clientSocket = null;
            }
        }
        notifyForConnectionClosed();
        clearNotifiables();
    }
}
