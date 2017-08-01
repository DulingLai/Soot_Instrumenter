package com.abaltatech.mcp.mcs.mtp;

import com.abaltatech.mcp.mcs.common.IMCSCompressionControl;
import com.abaltatech.mcp.mcs.common.IMCSConnectionAddress;
import com.abaltatech.mcp.mcs.common.IMCSDataLayer;
import com.abaltatech.mcp.mcs.common.IMCSDataStats;
import com.abaltatech.mcp.mcs.common.MCSCompressionControlDefaults;
import com.abaltatech.mcp.mcs.common.MCSDataLayerBase;
import com.abaltatech.mcp.mcs.common.MCSException;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import com.abaltatech.mcp.mcs.tcpip.TCPIPAddress;
import com.abaltatech.mcp.mcs.tcpip.TCPIPAddressPool;
import com.abaltatech.mcp.mcs.utils.DataQueueArr;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;

public class ConnectionPointMTP extends MCSDataLayerBase implements IMCSCompressionControl {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final int MaxQueueSize = 16;
    private static final String TAG = "ConnectionPointMTP";
    private static int ms_connID = 0;
    private static boolean ms_enableCompression = true;
    private boolean m_compressedSource;
    private int m_compressionMode;
    private int m_connID;
    private boolean m_connectionClosed;
    private TCPIPAddress m_fromAddress;
    private DataQueueArr m_inDataBuffers = new DataQueueArr(16);
    private int m_minCompressionSize;
    private MTPLayer m_mtpLayer;
    private boolean m_stopRequested;
    private TCPIPAddress m_toAddress;
    private IMCSDataLayer m_writer;

    public void processPacket(com.abaltatech.mcp.mcs.mtp.MTPPacket r27) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:48:0x011b in {2, 13, 20, 23, 31, 37, 40, 43, 45, 47, 49, 52, 58, 65, 66, 67} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r26 = this;
        r0 = r27;
        r2 = r0.IsLastMessage();
        if (r2 == 0) goto L_0x000d;
    L_0x0008:
        r3 = 1;
        r0 = r26;
        r0.m_stopRequested = r3;
    L_0x000d:
        r0 = r27;
        r4 = r0.GetPayloadSize();
        if (r4 <= 0) goto L_0x0063;
    L_0x0015:
        r0 = r27;
        r5 = r0.GetData();
        r6 = r5;
        r0 = r27;
        r2 = r0.IsPayloadCompressed();
        if (r2 == 0) goto L_0x0043;
    L_0x0024:
        r7 = new java.io.ByteArrayOutputStream;
        r7.<init>();	 Catch:{ IOException -> 0x011f }
        r8 = new java.util.zip.Inflater;	 Catch:{ IOException -> 0x011f }
        r3 = 0;	 Catch:{ IOException -> 0x011f }
        r8.<init>(r3);	 Catch:{ IOException -> 0x011f }
        r9 = new java.util.zip.InflaterOutputStream;	 Catch:{ IOException -> 0x011f }
        r9.<init>(r7, r8);	 Catch:{ IOException -> 0x011f }
        r4 = r5.length;
        r4 = r4 + -4;	 Catch:{ IOException -> 0x011f }
        r3 = 4;	 Catch:{ IOException -> 0x011f }
        r9.write(r5, r3, r4);	 Catch:{ IOException -> 0x011f }
        r9.close();	 Catch:{ IOException -> 0x011f }
        r5 = r7.toByteArray();	 Catch:{ IOException -> 0x011f }
        r6 = r5;
    L_0x0043:
        if (r6 == 0) goto L_0x0063;
    L_0x0045:
        r0 = r26;
        r10 = r0.m_inDataBuffers;
        monitor-enter(r10);
        r0 = r26;	 Catch:{ Throwable -> 0x0152 }
        r11 = r0.m_inDataBuffers;	 Catch:{ Throwable -> 0x0152 }
        r11.insert(r6);	 Catch:{ Throwable -> 0x0152 }
        r0 = r26;	 Catch:{ Throwable -> 0x0152 }
        r12 = r0.getDataStats();	 Catch:{ Throwable -> 0x0152 }
        if (r12 == 0) goto L_0x005d;	 Catch:{ Throwable -> 0x0152 }
    L_0x0059:
        r4 = r6.length;	 Catch:{ Throwable -> 0x0152 }
        r12.onDataReceived(r4);	 Catch:{ Throwable -> 0x0152 }
    L_0x005d:
        monitor-exit(r10);	 Catch:{ Throwable -> 0x0152 }
        r0 = r26;	 Catch:{ MCSException -> 0x0155 }
        r0.notifyForData();	 Catch:{ MCSException -> 0x0155 }
    L_0x0063:
        r0 = r27;
        r2 = r0.IsLastMessage();
        if (r2 == 0) goto L_0x0171;
    L_0x006b:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = "Connection ";
        r13 = r13.append(r14);
        r0 = r26;
        r4 = r0.m_connID;
        r13 = r13.append(r4);
        r14 = ": Received close connection request";
        r13 = r13.append(r14);
        r15 = r13.toString();
        r14 = "MTP";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r14, r15);
        r0 = r26;
        r0 = r0.m_writer;
        r16 = r0;
        if (r16 == 0) goto L_0x0172;
    L_0x0095:
        r17 = 0;
        r0 = r17;
        r1 = r26;
        r1.m_writer = r0;
        r0 = r26;
        r0 = r0.m_mtpLayer;
        r18 = r0;
        r1 = r26;	 Catch:{ MCSException -> 0x0162 }
        r0.closeConnection(r1);	 Catch:{ MCSException -> 0x0162 }
        r0 = r26;	 Catch:{ MCSException -> 0x0162 }
        r0 = r0.m_fromAddress;	 Catch:{ MCSException -> 0x0162 }
        r19 = r0;	 Catch:{ MCSException -> 0x0162 }
        com.abaltatech.mcp.mcs.tcpip.TCPIPAddressPool.freeAddress(r0);	 Catch:{ MCSException -> 0x0162 }
        r0 = r26;	 Catch:{ MCSException -> 0x0162 }
        r0 = r0.m_toAddress;	 Catch:{ MCSException -> 0x0162 }
        r19 = r0;	 Catch:{ MCSException -> 0x0162 }
        com.abaltatech.mcp.mcs.tcpip.TCPIPAddressPool.freeAddress(r0);	 Catch:{ MCSException -> 0x0162 }
        r17 = 0;
        r0 = r17;
        r1 = r26;
        r1.m_fromAddress = r0;
        r17 = 0;
        r0 = r17;
        r1 = r26;
        r1.m_toAddress = r0;
    L_0x00ca:
        r0 = r26;
        r10 = r0.m_inDataBuffers;
        monitor-enter(r10);
    L_0x00cf:
        r0 = r26;	 Catch:{ Throwable -> 0x00e1 }
        r11 = r0.m_inDataBuffers;	 Catch:{ Throwable -> 0x00e1 }
        r2 = r11.emptyq();	 Catch:{ Throwable -> 0x00e1 }
        if (r2 != 0) goto L_0x016f;	 Catch:{ Throwable -> 0x00e1 }
    L_0x00d9:
        r0 = r26;	 Catch:{ Throwable -> 0x00e1 }
        r11 = r0.m_inDataBuffers;	 Catch:{ Throwable -> 0x00e1 }
        r11.delete();	 Catch:{ Throwable -> 0x00e1 }
        goto L_0x00cf;	 Catch:{ Throwable -> 0x00e1 }
    L_0x00e1:
        r20 = move-exception;	 Catch:{ Throwable -> 0x00e1 }
        goto L_0x00e6;	 Catch:{ Throwable -> 0x00e1 }
    L_0x00e3:
        goto L_0x0063;	 Catch:{ Throwable -> 0x00e1 }
    L_0x00e6:
        monitor-exit(r10);	 Catch:{ Throwable -> 0x00e1 }
        throw r20;	 Catch:{ MCSException -> 0x00e8 }
    L_0x00e8:
        r21 = move-exception;
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = "Connection ";
        r13 = r13.append(r14);
        r0 = r26;
        r4 = r0.m_connID;
        r13 = r13.append(r4);
        r14 = ": ";
        r13 = r13.append(r14);
        r0 = r21;
        r15 = r0.toString();
        r13 = r13.append(r15);
        r15 = r13.toString();
        r14 = "MTP";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r14, r15);
    L_0x0115:
        r0 = r26;
        r0.notifyForConnectionClosed();
        return;
        goto L_0x011f;
    L_0x011c:
        goto L_0x00ca;
    L_0x011f:
        r22 = move-exception;
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = "Connection ";
        r13 = r13.append(r14);
        r0 = r26;
        r4 = r0.m_connID;
        r13 = r13.append(r4);
        r14 = ": ";
        r13 = r13.append(r14);
        r0 = r22;
        r15 = r0.toString();
        r13 = r13.append(r15);
        r15 = r13.toString();
        r14 = "MTP";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r14, r15);
        goto L_0x0150;
    L_0x014d:
        goto L_0x0043;
    L_0x0150:
        r6 = 0;
        goto L_0x014d;
    L_0x0152:
        r23 = move-exception;
        monitor-exit(r10);	 Catch:{ Throwable -> 0x0152 }
        throw r23;	 Catch:{ MCSException -> 0x0155 }
    L_0x0155:
        r24 = move-exception;
        r0 = r24;
        r15 = r0.toString();
        r14 = "ConnectionPointMTP";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r14, r15);
        goto L_0x00e3;
    L_0x0162:
        r25 = move-exception;
        r0 = r25;
        r15 = r0.toString();
        r14 = "ConnectionPointMTP";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r14, r15);
        goto L_0x011c;
    L_0x016f:
        monitor-exit(r10);	 Catch:{ Throwable -> 0x00e1 }
        goto L_0x0115;
    L_0x0171:
        return;
    L_0x0172:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.mtp.ConnectionPointMTP.processPacket(com.abaltatech.mcp.mcs.mtp.MTPPacket):void");
    }

    static {
        boolean $z0;
        if (ConnectionPointMTP.class.desiredAssertionStatus()) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        $assertionsDisabled = $z0;
    }

    public ConnectionPointMTP(IMCSDataLayer $r1, IMCSConnectionAddress $r3, IMCSConnectionAddress $r4, MTPLayer $r2) throws MCSException {
        int $i0 = ms_connID + 1;
        ms_connID = $i0;
        this.m_connID = $i0;
        this.m_stopRequested = false;
        this.m_connectionClosed = false;
        this.m_compressionMode = MCSCompressionControlDefaults.getDefaultCompressionMode();
        this.m_minCompressionSize = MCSCompressionControlDefaults.getDefaultMinimumCompressionSize();
        this.m_compressedSource = false;
        this.m_writer = $r1;
        this.m_mtpLayer = $r2;
        if (($r3 instanceof TCPIPAddress) && ($r4 instanceof TCPIPAddress)) {
            this.m_fromAddress = TCPIPAddressPool.copyAddress((TCPIPAddress) $r3);
            this.m_toAddress = TCPIPAddressPool.copyAddress((TCPIPAddress) $r4);
            return;
        }
        throw new MCSException("Non-TCPIP connection addresses");
    }

    public int getCompressionMode() throws  {
        return this.m_compressionMode;
    }

    public void setCompressionMode(int $i0) throws  {
        this.m_compressionMode = $i0;
    }

    public int getMinCompressionSize() throws  {
        return this.m_minCompressionSize;
    }

    public void setMinCompressionSize(int $i0) throws  {
        this.m_minCompressionSize = $i0;
    }

    private boolean SendData(byte[] $r1, int $i0, int $i1, boolean $z0) throws  {
        byte[] bArr = $r1;
        int $i2 = $i0;
        int i = $i1;
        boolean $z1 = false;
        if ((this.m_compressionMode == 1 || (this.m_compressionMode == 2 && !this.m_compressedSource)) && $i1 >= this.m_minCompressionSize) {
            try {
                ByteArrayOutputStream $r2 = new ByteArrayOutputStream();
                DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream($r2);
                $r2.write($i1 & 255);
                $r2.write(($i1 >> 8) & 255);
                $r2.write(($i1 >> 16) & 255);
                $r2.write(($i1 >> 24) & 255);
                deflaterOutputStream.write($r1, $i0, $i1);
                deflaterOutputStream.close();
                if ($r2.size() < $i1) {
                    bArr = $r2.toByteArray();
                    $i2 = 0;
                    i = $r2.size();
                    $z1 = true;
                } else {
                    this.m_compressedSource = true;
                }
            } catch (IOException $r4) {
                MCSLogger.log("MTP", "Connection " + this.m_connID + ": " + $r4.toString());
            }
        }
        MTPPacket $r8 = MTPPacket.CreateDataPacket(this.m_toAddress, this.m_fromAddress, bArr, $i2, i, $z0, $z1, 0);
        IMCSDataLayer $r9 = this.m_writer;
        if (!$assertionsDisabled && ($r8 == null || $r9 == null)) {
            throw new AssertionError();
        } else if ($r8 == null || $r9 == null) {
            MCSLogger.log("MTP", "Connection " + this.m_connID + ": Error preparing MTP packet.");
            return false;
        } else {
            $r9.writeData($r8.GetPacket(), $r8.GetPacketSize());
            $r8.ReleasePacket();
            IMCSDataStats $r13 = getDataStats();
            if ($r13 == null) {
                return true;
            }
            $r13.onDataSent($i1);
            return true;
        }
    }

    private void SendCloseMtpMessage() throws  {
        SendData(null, 0, 0, true);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void closeConnection() throws  {
        /*
        r15 = this;
        r0 = r15.m_connectionClosed;
        if (r0 != 0) goto L_0x0083;
    L_0x0004:
        r1 = 1;
        r15.m_connectionClosed = r1;
        r2 = r15.m_writer;
        if (r2 == 0) goto L_0x00b3;
    L_0x000b:
        r15.SendCloseMtpMessage();
        r3 = 0;
        r15.m_writer = r3;
        r4 = new java.lang.StringBuilder;
        r4.<init>();	 Catch:{ MCSException -> 0x0087 }
        r5 = "Connection ";
        r4 = r4.append(r5);	 Catch:{ MCSException -> 0x0087 }
        r6 = r15.m_connID;	 Catch:{ MCSException -> 0x0087 }
        r4 = r4.append(r6);	 Catch:{ MCSException -> 0x0087 }
        r5 = " closed";
        r4 = r4.append(r5);	 Catch:{ MCSException -> 0x0087 }
        r7 = r4.toString();	 Catch:{ MCSException -> 0x0087 }
        r5 = "MTP";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r5, r7);	 Catch:{ MCSException -> 0x0087 }
        r8 = r15.m_mtpLayer;	 Catch:{ MCSException -> 0x0087 }
        r8.closeConnection(r15);	 Catch:{ MCSException -> 0x0087 }
        r9 = r15.m_fromAddress;	 Catch:{ MCSException -> 0x0087 }
        com.abaltatech.mcp.mcs.tcpip.TCPIPAddressPool.freeAddress(r9);	 Catch:{ MCSException -> 0x0087 }
        r9 = r15.m_toAddress;	 Catch:{ MCSException -> 0x0087 }
        com.abaltatech.mcp.mcs.tcpip.TCPIPAddressPool.freeAddress(r9);	 Catch:{ MCSException -> 0x0087 }
        r3 = 0;
        r15.m_fromAddress = r3;
        r3 = 0;
        r15.m_toAddress = r3;
    L_0x0046:
        r10 = r15.m_inDataBuffers;
        monitor-enter(r10);
    L_0x0049:
        r11 = r15.m_inDataBuffers;	 Catch:{ Throwable -> 0x0057 }
        r0 = r11.emptyq();	 Catch:{ Throwable -> 0x0057 }
        if (r0 != 0) goto L_0x00b1;
    L_0x0051:
        r11 = r15.m_inDataBuffers;	 Catch:{ Throwable -> 0x0057 }
        r11.delete();	 Catch:{ Throwable -> 0x0057 }
        goto L_0x0049;
    L_0x0057:
        r12 = move-exception;
        monitor-exit(r10);	 Catch:{ Throwable -> 0x0057 }
        throw r12;	 Catch:{ MCSException -> 0x005a }
    L_0x005a:
        r13 = move-exception;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Connection ";
        r4 = r4.append(r5);
        r6 = r15.m_connID;
        r4 = r4.append(r6);
        r5 = ": ";
        r4 = r4.append(r5);
        r7 = r13.toString();
        r4 = r4.append(r7);
        r7 = r4.toString();
        r5 = "MTP";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r5, r7);
    L_0x0083:
        r15.notifyForConnectionClosed();
        return;
    L_0x0087:
        r14 = move-exception;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Connection ";
        r4 = r4.append(r5);
        r6 = r15.m_connID;
        r4 = r4.append(r6);
        r5 = ": ";
        r4 = r4.append(r5);
        r7 = r14.toString();
        r4 = r4.append(r7);
        r7 = r4.toString();
        r5 = "MTP";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r5, r7);
        goto L_0x0046;
    L_0x00b1:
        monitor-exit(r10);	 Catch:{ Throwable -> 0x0057 }
        goto L_0x0083;
    L_0x00b3:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.mtp.ConnectionPointMTP.closeConnection():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int readData(byte[] r13, int r14) throws  {
        /*
        r12 = this;
        r0 = r12.m_inDataBuffers;
        monitor-enter(r0);
        r1 = r12.m_inDataBuffers;	 Catch:{ Throwable -> 0x002e }
        r2 = r1.emptyq();	 Catch:{ Throwable -> 0x002e }
        if (r2 == 0) goto L_0x000e;
    L_0x000b:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x002e }
        r3 = 0;
        return r3;
    L_0x000e:
        r1 = r12.m_inDataBuffers;	 Catch:{ Throwable -> 0x002e }
        r4 = r1.delete();	 Catch:{ Throwable -> 0x002e }
        monitor-exit(r0);	 Catch:{ Throwable -> 0x002e }
        r5 = r4.length;
        if (r14 >= r5) goto L_0x0039;
    L_0x0018:
        r2 = $assertionsDisabled;
        if (r2 != 0) goto L_0x0031;
    L_0x001c:
        r6 = new java.lang.AssertionError;
        r6.<init>();	 Catch:{ MCSException -> 0x0022 }
        throw r6;
    L_0x0022:
        r7 = move-exception;
        r8 = r7.toString();
        r9 = "ERROR";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r9, r8);
        r3 = 0;
        return r3;
    L_0x002e:
        r10 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x002e }
        throw r10;	 Catch:{ MCSException -> 0x0022 }
    L_0x0031:
        r7 = new com.abaltatech.mcp.mcs.common.MCSException;	 Catch:{ MCSException -> 0x0022 }
        r9 = "Buffer size too small";
        r7.<init>(r9);	 Catch:{ MCSException -> 0x0022 }
        throw r7;	 Catch:{ MCSException -> 0x0022 }
    L_0x0039:
        r14 = r4.length;	 Catch:{ MCSException -> 0x0022 }
        r3 = 0;
        r11 = 0;
        java.lang.System.arraycopy(r4, r3, r13, r11, r14);	 Catch:{ MCSException -> 0x0022 }
        r14 = r4.length;
        return r14;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.mtp.ConnectionPointMTP.readData(byte[], int):int");
    }

    protected void writeDataInternal(byte[] $r1, int $i0) throws  {
        if (!this.m_stopRequested) {
            if ($i0 == 0) {
                SendData(null, 0, 0, false);
                return;
            }
            for (int $i1 = 0; $i1 < $i0; $i1 += MTPPacket.GetMaxDataSize()) {
                int $i2 = $i0 - $i1;
                if ($i2 > MTPPacket.GetMaxDataSize()) {
                    $i2 = MTPPacket.GetMaxDataSize();
                }
                if (this.m_writer == null) {
                    MCSLogger.log("MTP", "Connection " + this.m_connID + ": Warning: more data to send to peer but connection is already closed.");
                    return;
                }
                SendData($r1, $i1, $i2, false);
            }
        }
    }

    public IMCSConnectionAddress getFromAddress() throws  {
        return this.m_fromAddress;
    }

    public IMCSConnectionAddress getToAddress() throws  {
        return this.m_toAddress;
    }

    public boolean canHandle(IMCSConnectionAddress $r1, IMCSConnectionAddress $r2) throws  {
        return this.m_fromAddress.isSameAs($r1) && this.m_toAddress.isSameAs($r2);
    }

    public boolean isMTPPacketForMe(MTPPacket $r1) throws  {
        if ($r1 == null) {
            MCSLogger.log(TAG, "Null MTP Packet received!");
            return false;
        }
        IMCSConnectionAddress $r2 = $r1.GetFromAddress();
        IMCSConnectionAddress $r3 = $r1.GetToAddress();
        if ($r2 == null) {
            return false;
        }
        if ($r3 == null) {
            return false;
        }
        if (this.m_connectionClosed) {
            return false;
        }
        if ($r2.isSameAs(this.m_fromAddress)) {
            return $r3.isSameAs(this.m_toAddress);
        } else {
            return false;
        }
    }

    public static boolean isCompressionEnabled() throws  {
        return ms_enableCompression;
    }

    public static void enableCompression(boolean $z0) throws  {
        ms_enableCompression = $z0;
    }
}
