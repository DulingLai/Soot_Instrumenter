package com.abaltatech.mcp.mcs.iostream;

import com.abaltatech.mcp.mcs.common.IMCSDataStats;
import com.abaltatech.mcp.mcs.common.MCSDataLayerBase;
import com.abaltatech.mcp.mcs.common.MemoryPool;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOStreamLayer extends MCSDataLayerBase implements Runnable {
    private int m_available;
    private boolean m_canceled;
    protected InputStream m_input;
    private boolean m_isNotificationSent;
    protected OutputStream m_output;
    private int m_read;
    private byte[] m_readBuffer;
    private ReadThread m_readThread;
    protected Thread m_thread;

    private class ReadThread extends Thread {
        public void run() throws  {
            while (!IOStreamLayer.this.m_canceled) {
                boolean $z0 = false;
                synchronized (this) {
                    boolean $z1 = IOStreamLayer.this.m_input != null && IOStreamLayer.this.m_available == 0;
                }
                if ($z1) {
                    try {
                        IOStreamLayer.this.m_available = IOStreamLayer.this.m_input.read(IOStreamLayer.this.m_readBuffer, 0, MemoryPool.BufferSizeBig);
                    } catch (IOException $r7) {
                        IOStreamLayer.this.onIOException($r7);
                    }
                } else {
                    $z0 = true;
                }
                if ($z0) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }
    }

    public IOStreamLayer() throws  {
        this("IOStreamLayer");
    }

    public IOStreamLayer(String $r1) throws  {
        this.m_input = null;
        this.m_output = null;
        this.m_isNotificationSent = false;
        this.m_readBuffer = null;
        this.m_canceled = false;
        this.m_available = 0;
        this.m_read = 0;
        this.m_readBuffer = new byte[MemoryPool.BufferSizeBig];
        this.m_thread = new Thread(this);
        this.m_thread.start();
        this.m_readThread = new ReadThread();
        this.m_readThread.setName($r1);
        this.m_readThread.start();
    }

    public synchronized void attachStreams(InputStream $r1, OutputStream $r2) throws  {
        this.m_input = $r1;
        this.m_output = $r2;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int readData(byte[] r8, int r9) throws  {
        /*
        r7 = this;
        r0 = r7.getDataStats();
        monitor-enter(r7);
        r1 = 0;
        r7.m_isNotificationSent = r1;	 Catch:{ Throwable -> 0x003c }
        r2 = r7.m_input;	 Catch:{ Throwable -> 0x003c }
        if (r2 == 0) goto L_0x003f;
    L_0x000c:
        r3 = r7.m_available;	 Catch:{ Throwable -> 0x003c }
        r1 = 1;
        if (r3 >= r1) goto L_0x0014;
    L_0x0011:
        monitor-exit(r7);	 Catch:{ Throwable -> 0x003c }
        r1 = 0;
        return r1;
    L_0x0014:
        r3 = r7.m_available;	 Catch:{ Throwable -> 0x003c }
        r4 = r7.m_read;	 Catch:{ Throwable -> 0x003c }
        r3 = r3 - r4;
        if (r3 <= r9) goto L_0x001c;
    L_0x001b:
        r3 = r9;
    L_0x001c:
        r5 = r7.m_readBuffer;	 Catch:{ Throwable -> 0x003c }
        r9 = r7.m_read;	 Catch:{ Throwable -> 0x003c }
        r1 = 0;
        java.lang.System.arraycopy(r5, r9, r8, r1, r3);	 Catch:{ Throwable -> 0x003c }
        r9 = r7.m_read;	 Catch:{ Throwable -> 0x003c }
        r9 = r9 + r3;
        r7.m_read = r9;	 Catch:{ Throwable -> 0x003c }
        if (r0 == 0) goto L_0x002e;
    L_0x002b:
        r0.onDataReceived(r3);	 Catch:{ Throwable -> 0x003c }
    L_0x002e:
        r9 = r7.m_read;	 Catch:{ Throwable -> 0x003c }
        r4 = r7.m_available;	 Catch:{ Throwable -> 0x003c }
        if (r9 < r4) goto L_0x003a;
    L_0x0034:
        r1 = 0;
        r7.m_read = r1;	 Catch:{ Throwable -> 0x003c }
        r1 = 0;
        r7.m_available = r1;	 Catch:{ Throwable -> 0x003c }
    L_0x003a:
        monitor-exit(r7);	 Catch:{ Throwable -> 0x003c }
        return r3;
    L_0x003c:
        r6 = move-exception;
        monitor-exit(r7);	 Catch:{ Throwable -> 0x003c }
        throw r6;
    L_0x003f:
        monitor-exit(r7);	 Catch:{ Throwable -> 0x003c }
        r1 = 0;
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.iostream.IOStreamLayer.readData(byte[], int):int");
    }

    protected void onIOException(IOException $r1) throws  {
        MCSLogger.log("IOStreamLayer EXCEPTION", $r1.toString());
        closeConnection();
    }

    protected void writeDataInternal(byte[] $r1, int $i0) throws  {
        if (this.m_output != null) {
            try {
                IMCSDataStats $r3 = getDataStats();
                this.m_output.write($r1, 0, $i0);
                if ($r3 != null) {
                    $r3.onDataSent($i0);
                }
            } catch (IOException $r2) {
                onIOException($r2);
            }
        }
    }

    public void run() throws  {
        while (!this.m_canceled) {
            try {
                Thread.sleep(10);
                boolean $z0 = false;
                synchronized (this) {
                    if (!(this.m_input == null || this.m_available <= 0 || this.m_isNotificationSent)) {
                        this.m_isNotificationSent = true;
                        $z0 = true;
                    }
                }
                if ($z0) {
                    notifyForData();
                    synchronized (this) {
                        if (this.m_isNotificationSent) {
                            this.m_read = 0;
                            this.m_available = 0;
                            this.m_isNotificationSent = false;
                        }
                    }
                }
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void closeConnection() throws  {
        Thread $r1 = this.m_thread;
        if ($r1 != null) {
            synchronized (this) {
                this.m_canceled = true;
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
            }
            onConnectionClosed();
            this.m_thread = null;
            $r1.interrupt();
            notifyForConnectionClosed();
            clearNotifiables();
        }
    }

    protected void onConnectionClosed() throws  {
    }
}
