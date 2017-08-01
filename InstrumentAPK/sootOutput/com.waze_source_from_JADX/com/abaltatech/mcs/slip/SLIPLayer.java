package com.abaltatech.mcs.slip;

import com.abaltatech.mcp.weblink.core.commandhandling.Command;
import com.abaltatech.mcs.common.IMCSDataLayer;
import com.abaltatech.mcs.common.IMCSDataLayerNotification;
import com.abaltatech.mcs.common.IMCSDataStats;
import com.abaltatech.mcs.common.MCSDataLayerBase;
import com.abaltatech.mcs.common.MCSException;
import com.abaltatech.mcs.common.MemoryPool;
import com.abaltatech.mcs.logger.MCSLogger;
import com.abaltatech.mcs.utils.ByteUtils;

public class SLIPLayer extends MCSDataLayerBase implements IMCSDataLayerNotification, Runnable {
    protected static final int Ended = 3;
    protected static final int Escape = 2;
    protected static final int NotStarted = 0;
    protected static final int Started = 1;
    protected static final int WaitTime = 10;
    protected static final byte cMarkerEnd = (byte) -64;
    protected static final byte cMarkerEsc = (byte) -37;
    protected static final byte cMarkerEscEnd = (byte) -36;
    protected static final byte cMarkerEscEsc = (byte) -35;
    protected static final byte[] m_response = new byte[]{(byte) 67, Command.MAGIC_BYTE2, (byte) 73, (byte) 69, (byte) 78, (byte) 84, (byte) 83, (byte) 69, (byte) 82, (byte) 86, (byte) 69, (byte) 82};
    protected static final int m_responseLen = m_response.length;
    protected int m_counter;
    private boolean m_dumpInfo;
    protected boolean m_hasGarbage;
    protected Buffer m_inBuffer;
    protected Buffer m_inTransportBuffer;
    protected boolean m_isStarted;
    protected Buffer m_outBuffer;
    protected int m_readBufferSize;
    protected int m_state;
    protected Thread m_thread;
    protected IMCSDataLayer m_transporter;

    protected static class Buffer {
        public byte[] Arr;
        public int Pos = 0;

        protected Buffer() throws  {
        }
    }

    public SLIPLayer() throws MCSException {
        this.m_state = 0;
        this.m_inBuffer = new Buffer();
        this.m_inTransportBuffer = new Buffer();
        this.m_outBuffer = new Buffer();
        this.m_hasGarbage = false;
        this.m_counter = 0;
        this.m_dumpInfo = true;
        this.m_inBuffer.Arr = MemoryPool.getMem(MemoryPool.BufferSizeBig, "SLIPLayer");
        this.m_outBuffer.Arr = MemoryPool.getMem(MemoryPool.BufferSizeBig, "SLIPLayer");
        this.m_inTransportBuffer.Arr = MemoryPool.getMem(MemoryPool.BufferSizeBig, "SLIPLayer");
        this.m_readBufferSize = this.m_inTransportBuffer.Arr.length;
    }

    public synchronized void start() throws  {
        if (!this.m_isStarted) {
            this.m_isStarted = true;
            this.m_thread = new Thread(this);
            this.m_thread.start();
        }
    }

    public synchronized boolean isStarted() throws  {
        return this.m_isStarted;
    }

    public synchronized boolean isStopped() throws  {
        return !this.m_isStarted;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() throws  {
        /*
        r15 = this;
        r0 = r15.getDataStats();
        r1 = 0;
        r15.m_counter = r1;
    L_0x0007:
        monitor-enter(r15);
        r2 = r15.m_inBuffer;	 Catch:{ Throwable -> 0x006d }
        if (r2 != 0) goto L_0x000e;
    L_0x000c:
        monitor-exit(r15);	 Catch:{ Throwable -> 0x006d }
        return;
    L_0x000e:
        r3 = r15.m_counter;	 Catch:{ Throwable -> 0x006d }
        r3 = r3 + 1;
        r15.m_counter = r3;	 Catch:{ Throwable -> 0x006d }
        r3 = r15.m_counter;	 Catch:{ Throwable -> 0x006d }
        r1 = 100;
        if (r3 <= r1) goto L_0x0063;
    L_0x001a:
        r4 = r15.m_hasGarbage;	 Catch:{ Throwable -> 0x006d }
        if (r4 == 0) goto L_0x0038;
    L_0x001e:
        r5 = r15.m_transporter;	 Catch:{ Throwable -> 0x006d }
        r6 = m_response;	 Catch:{ Throwable -> 0x006d }
        r3 = m_responseLen;	 Catch:{ Throwable -> 0x006d }
        r5.writeData(r6, r3);	 Catch:{ Throwable -> 0x006d }
        if (r0 == 0) goto L_0x002e;
    L_0x0029:
        r3 = m_responseLen;	 Catch:{ Throwable -> 0x006d }
        r0.onDataSent(r3);	 Catch:{ Throwable -> 0x006d }
    L_0x002e:
        r1 = 0;
        r15.m_hasGarbage = r1;	 Catch:{ Throwable -> 0x006d }
        r7 = "SLIP Layer";
        r8 = "Sending CLIENTSERVER";
        com.abaltatech.mcs.logger.MCSLogger.log(r7, r8);	 Catch:{ Throwable -> 0x006d }
    L_0x0038:
        r2 = r15.m_inBuffer;	 Catch:{ Throwable -> 0x006d }
        r3 = r2.Pos;	 Catch:{ Throwable -> 0x006d }
        if (r3 <= 0) goto L_0x0060;
    L_0x003e:
        r9 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x006d }
        r9.<init>();	 Catch:{ Throwable -> 0x006d }
        r7 = "SLIPLayer: Discarding garbage data with length ";
        r9 = r9.append(r7);	 Catch:{ Throwable -> 0x006d }
        r2 = r15.m_inBuffer;	 Catch:{ Throwable -> 0x006d }
        r3 = r2.Pos;	 Catch:{ Throwable -> 0x006d }
        r9 = r9.append(r3);	 Catch:{ Throwable -> 0x006d }
        r10 = r9.toString();	 Catch:{ Throwable -> 0x006d }
        r15.log(r10);	 Catch:{ Throwable -> 0x006d }
        r2 = r15.m_inBuffer;	 Catch:{ Throwable -> 0x006d }
        r1 = 0;
        r2.Pos = r1;	 Catch:{ Throwable -> 0x006d }
        r1 = 0;
        r15.m_state = r1;	 Catch:{ Throwable -> 0x006d }
    L_0x0060:
        r1 = 0;
        r15.m_counter = r1;	 Catch:{ Throwable -> 0x006d }
    L_0x0063:
        monitor-exit(r15);	 Catch:{ Throwable -> 0x006d }
        r11 = 10;
        java.lang.Thread.sleep(r11);	 Catch:{ InterruptedException -> 0x0070 }
    L_0x0069:
        r15.isStarted();
        goto L_0x0007;
    L_0x006d:
        r13 = move-exception;
        monitor-exit(r15);	 Catch:{ Throwable -> 0x006d }
        throw r13;
    L_0x0070:
        r14 = move-exception;
        goto L_0x0069;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcs.slip.SLIPLayer.run():void");
    }

    private void parseByte(byte $b0) throws  {
        switch (this.m_state) {
            case 0:
            case 3:
                if (cMarkerEnd == $b0) {
                    this.m_state = 1;
                    return;
                }
                synchronized (this) {
                    this.m_hasGarbage = true;
                }
                log("SLIPLayer: Found garbage data (missing END marker)");
                return;
            case 1:
                synchronized (this) {
                    this.m_hasGarbage = false;
                }
                if (cMarkerEsc == $b0) {
                    this.m_state = 2;
                    return;
                } else if (cMarkerEnd != $b0) {
                    addByteIn($b0);
                    return;
                } else if (this.m_inBuffer.Pos != 0) {
                    this.m_state = 3;
                    return;
                } else {
                    return;
                }
            case 2:
                if (cMarkerEscEsc == $b0) {
                    addByteIn(cMarkerEsc);
                } else if (cMarkerEscEnd == $b0) {
                    addByteIn(cMarkerEnd);
                } else {
                    addByteIn($b0);
                    log("\nInvalid escape secuence: 219, " + Integer.toString(ByteUtils.toUnsignedInteger($b0)));
                }
                this.m_state = 1;
                return;
            default:
                return;
        }
    }

    private synchronized void addByteIn(byte $b0) throws  {
        if (this.m_inBuffer.Pos < this.m_inBuffer.Arr.length) {
            this.m_inBuffer.Arr[this.m_inBuffer.Pos] = $b0;
            Buffer $r2 = this.m_inBuffer;
            $r2.Pos++;
            IMCSDataStats $r1 = getDataStats();
            if ($r1 != null) {
                $r1.onDataReceived(1);
            }
        } else {
            this.m_inBuffer.Pos = 0;
            this.m_state = 0;
            log("ERROR", "Input SLIP message too long");
        }
    }

    private boolean addByteOut(byte $b0) throws  {
        if (this.m_outBuffer.Pos < this.m_outBuffer.Arr.length) {
            this.m_outBuffer.Arr[this.m_outBuffer.Pos] = $b0;
            Buffer $r2 = this.m_outBuffer;
            $r2.Pos++;
            IMCSDataStats $r1 = getDataStats();
            if ($r1 == null) {
                return true;
            }
            $r1.onDataSent(1);
            return true;
        }
        log("ERROR", "Output SLIP message too long");
        return false;
    }

    public synchronized void attachToLayer(IMCSDataLayer $r1) throws  {
        if (this.m_transporter != null) {
            this.m_transporter.unRegisterNotification(this);
        }
        this.m_transporter = $r1;
        if ($r1 != null) {
            $r1.registerNotification(this);
        }
    }

    public void onConnectionClosed(IMCSDataLayer connection) throws  {
        synchronized (this) {
            if (this.m_transporter == null) {
                return;
            }
            this.m_transporter.unRegisterNotification(this);
            this.m_isStarted = false;
            this.m_hasGarbage = false;
            this.m_readBufferSize = 0;
            try {
                MemoryPool.freeMem(this.m_inBuffer.Arr);
                MemoryPool.freeMem(this.m_outBuffer.Arr);
                MemoryPool.freeMem(this.m_inTransportBuffer.Arr);
            } catch (MCSException e) {
            }
            this.m_inBuffer = null;
            this.m_outBuffer = null;
            this.m_inTransportBuffer = null;
            this.m_transporter.closeConnection();
            this.m_transporter = null;
            notifyForConnectionClosed();
            clearNotifiables();
        }
    }

    public void onDataReceived(IMCSDataLayer connection) throws  {
        notifyForData();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int readData(byte[] r13, int r14) throws  {
        /*
        r12 = this;
        monitor-enter(r12);
        r0 = r12.m_inBuffer;	 Catch:{ Throwable -> 0x0029 }
        if (r0 == 0) goto L_0x0009;
    L_0x0005:
        r1 = r12.m_transporter;	 Catch:{ Throwable -> 0x0029 }
        if (r1 != 0) goto L_0x000c;
    L_0x0009:
        monitor-exit(r12);	 Catch:{ Throwable -> 0x0029 }
        r2 = 0;
        return r2;
    L_0x000c:
        r0 = r12.m_inBuffer;	 Catch:{ Throwable -> 0x0029 }
        r1 = r12.m_transporter;	 Catch:{ Throwable -> 0x0029 }
        r3 = r12.m_inTransportBuffer;	 Catch:{ Throwable -> 0x0029 }
        monitor-exit(r12);	 Catch:{ Throwable -> 0x0029 }
    L_0x0013:
        r4 = r12.processInputData();
        if (r4 == 0) goto L_0x003b;
    L_0x0019:
        r5 = r0.Pos;
        if (r14 >= r5) goto L_0x002c;
    L_0x001d:
        r2 = 0;
        r0.Pos = r2;
        r6 = "SLIP Layer";
        r7 = "ERROR: received datagram is larger than requested in readData()";
        r12.log(r6, r7);
        r2 = 0;
        return r2;
    L_0x0029:
        r8 = move-exception;
        monitor-exit(r12);	 Catch:{ Throwable -> 0x0029 }
        throw r8;
    L_0x002c:
        r9 = r0.Arr;
        r14 = r0.Pos;
        r2 = 0;
        r10 = 0;
        java.lang.System.arraycopy(r9, r2, r13, r10, r14);
        r14 = r0.Pos;
        r2 = 0;
        r0.Pos = r2;
        return r14;
    L_0x003b:
        r9 = r3.Arr;
        r5 = r12.m_readBufferSize;
        r5 = r1.readData(r9, r5);
        r3.Pos = r5;
        if (r5 <= 0) goto L_0x004c;
    L_0x0047:
        monitor-enter(r12);
        r2 = 0;
        r12.m_counter = r2;	 Catch:{ Throwable -> 0x0050 }
        monitor-exit(r12);	 Catch:{ Throwable -> 0x0050 }
    L_0x004c:
        if (r5 > 0) goto L_0x0013;
    L_0x004e:
        r2 = 0;
        return r2;
    L_0x0050:
        r11 = move-exception;
        monitor-exit(r12);	 Catch:{ Throwable -> 0x0050 }
        throw r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcs.slip.SLIPLayer.readData(byte[], int):int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean processInputData() throws  {
        /*
        r11 = this;
        monitor-enter(r11);
        r0 = r11.m_inBuffer;	 Catch:{ Throwable -> 0x0038 }
        if (r0 == 0) goto L_0x0009;
    L_0x0005:
        r1 = r11.m_transporter;	 Catch:{ Throwable -> 0x0038 }
        if (r1 != 0) goto L_0x000c;
    L_0x0009:
        monitor-exit(r11);	 Catch:{ Throwable -> 0x0038 }
        r2 = 0;
        return r2;
    L_0x000c:
        r0 = r11.m_inBuffer;	 Catch:{ Throwable -> 0x0038 }
        r3 = r11.m_inTransportBuffer;	 Catch:{ Throwable -> 0x0038 }
        monitor-exit(r11);	 Catch:{ Throwable -> 0x0038 }
        r4 = r3.Pos;
        r5 = r3.Arr;
        r6 = 0;
    L_0x0016:
        if (r6 >= r4) goto L_0x0042;
    L_0x0018:
        r7 = r5[r6];
        r11.parseByte(r7);
        r8 = r11.m_state;
        r2 = 3;
        if (r8 != r2) goto L_0x003f;
    L_0x0022:
        r8 = r0.Pos;
        if (r8 <= 0) goto L_0x003f;
    L_0x0026:
        r4 = r4 - r6;
        r4 = r4 + -1;
        if (r4 <= 0) goto L_0x003b;
    L_0x002b:
        r8 = 0;
    L_0x002c:
        if (r8 >= r4) goto L_0x003b;
    L_0x002e:
        r9 = r6 + 1;
        r9 = r9 + r8;
        r7 = r5[r9];
        r5[r8] = r7;
        r8 = r8 + 1;
        goto L_0x002c;
    L_0x0038:
        r10 = move-exception;
        monitor-exit(r11);	 Catch:{ Throwable -> 0x0038 }
        throw r10;
    L_0x003b:
        r3.Pos = r4;
        r2 = 1;
        return r2;
    L_0x003f:
        r6 = r6 + 1;
        goto L_0x0016;
    L_0x0042:
        r2 = 0;
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcs.slip.SLIPLayer.processInputData():boolean");
    }

    protected void writeDataInternal(byte[] $r1, int $i0) throws  {
        if ($r1 == null || 0 + $i0 > $r1.length) {
            log("ERROR", "Wrong parameters for SLIPLayer.writeData()");
            return;
        }
        this.m_outBuffer.Pos = 0;
        addByteOut(cMarkerEnd);
        $i0 = 0 + $i0;
        if ($i0 > $r1.length) {
            $i0 = $r1.length;
        }
        for (int $i2 = 0; $i2 < $i0; $i2++) {
            byte $b1 = $r1[$i2];
            switch ($b1) {
                case (byte) -64:
                    if (addByteOut(cMarkerEsc) && addByteOut(cMarkerEscEnd)) {
                        break;
                    }
                    log("ERROR", "Too long SLIP message");
                    return;
                case (byte) -37:
                    if (addByteOut(cMarkerEsc) && addByteOut(cMarkerEscEsc)) {
                        break;
                    }
                    log("ERROR", "Too long SLIP message");
                    return;
                    break;
                default:
                    if (addByteOut($b1)) {
                        break;
                    }
                    log("ERROR", "Too long SLIP message");
                    return;
            }
        }
        if (!addByteOut(cMarkerEnd)) {
            log("ERROR", "Too long SLIP message");
        } else if (this.m_transporter != null) {
            this.m_transporter.writeData(this.m_outBuffer.Arr, this.m_outBuffer.Pos);
        }
    }

    public void closeConnection() throws  {
        onConnectionClosed(this.m_transporter);
    }

    public synchronized void setDumpInfo(boolean $z0) throws  {
        this.m_dumpInfo = $z0;
    }

    public synchronized boolean getDumpInfo() throws  {
        return this.m_dumpInfo;
    }

    protected void log(String $r1) throws  {
        if (getDumpInfo()) {
            MCSLogger.log($r1);
        }
    }

    protected void log(String $r1, String $r2) throws  {
        if (getDumpInfo()) {
            MCSLogger.log($r1, $r2);
        }
    }
}
