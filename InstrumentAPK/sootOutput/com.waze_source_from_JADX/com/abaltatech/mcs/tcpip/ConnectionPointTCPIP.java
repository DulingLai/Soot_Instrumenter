package com.abaltatech.mcs.tcpip;

import com.abaltatech.mcs.common.IMCSConnectionAddress;
import com.abaltatech.mcs.common.IMCSDataStats;
import com.abaltatech.mcs.common.MCSDataLayerBase;
import com.abaltatech.mcs.common.MCSException;
import com.abaltatech.mcs.common.MemoryPool;
import com.abaltatech.mcs.logger.MCSLogger;
import com.abaltatech.mcs.utils.DataQueueArr;
import com.abaltatech.mcs.utils.DataQueueInt;

public class ConnectionPointTCPIP extends MCSDataLayerBase implements Runnable {
    private static final int MAX_DATAGRAM_SIZE = 4096;
    private static final int MAX_SEGMENT_SIZE = 4056;
    private static final int MaxQueueSize = 16;
    private static final int STATE_HANDSHAKEN = 3;
    private static final int STATE_LISTEN = 1;
    private static final int STATE_RECEIVED_SYN = 2;
    private static final int STATE_SENT_SYN = 4;
    private static final int TCPWindowSize = 4096;
    private int m_flags;
    private TCPIPAddress m_fromAddress;
    private DataQueueArr m_inDataBuffers = new DataQueueArr(16);
    private DataQueueInt m_inDataSizes = new DataQueueInt(16);
    private boolean m_isClosed = false;
    private boolean m_isStarted = false;
    private int m_maximumSegmentSize;
    private int m_myAcknowledgementNumber;
    private int m_myInitialSequenceNumber;
    private int m_senderAcknowledgementNumber;
    private int m_senderInitialSequenceNumber;
    private TCPSlidingWindow m_slidingWindow;
    private int m_state = 1;
    private Thread m_thread;
    private TCPIPAddress m_toAddress;
    private TCPIPLayer m_writer;

    public ConnectionPointTCPIP(TCPIPLayer $r1, IMCSConnectionAddress $r2, IMCSConnectionAddress $r3) throws MCSException {
        this.m_writer = $r1;
        if (($r2 instanceof TCPIPAddress) && ($r3 instanceof TCPIPAddress)) {
            this.m_fromAddress = TCPIPAddressPool.copyAddress((TCPIPAddress) $r2);
            this.m_toAddress = TCPIPAddressPool.copyAddress((TCPIPAddress) $r3);
            if (this.m_fromAddress.getAddress().isAnyLocalAddress() || this.m_toAddress.getAddress().isAnyLocalAddress()) {
                throw new MCSException("Unspecified addresses are not supported yet!");
            } else if (this.m_fromAddress.getPort() == 0 || this.m_toAddress.getPort() == 0) {
                throw new MCSException("Unspecified ports are not supported yet!");
            } else {
                this.m_thread = new Thread(this);
                this.m_thread.start();
                return;
            }
        }
        throw new MCSException("Non-TCPIP connection addresses");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isIPPacketForMe(com.abaltatech.mcs.tcpip.TCPIPPacket r13) throws  {
        /*
        r12 = this;
        monitor-enter(r12);
        r0 = r12.m_isClosed;	 Catch:{ Throwable -> 0x0045 }
        if (r0 == 0) goto L_0x0008;
    L_0x0005:
        monitor-exit(r12);	 Catch:{ Throwable -> 0x0045 }
        r1 = 0;
        return r1;
    L_0x0008:
        monitor-exit(r12);	 Catch:{ Throwable -> 0x0045 }
        r0 = 0;
        r2 = r13.getSourceIPAddress();	 Catch:{ Exception -> 0x003a }
        r3 = r13.getSourcePort();	 Catch:{ Exception -> 0x003a }
        r4 = com.abaltatech.mcs.tcpip.TCPIPAddressPool.getAddress(r2, r3);	 Catch:{ Exception -> 0x003a }
        r2 = r13.getDestIPAddress();	 Catch:{ Exception -> 0x003a }
        r3 = r13.getDestPort();	 Catch:{ Exception -> 0x003a }
        r5 = com.abaltatech.mcs.tcpip.TCPIPAddressPool.getAddress(r2, r3);	 Catch:{ Exception -> 0x003a }
        r6 = r12.m_fromAddress;	 Catch:{ Exception -> 0x003a }
        r7 = r6.equals(r4);	 Catch:{ Exception -> 0x003a }
        if (r7 == 0) goto L_0x0048;
    L_0x002a:
        r6 = r12.m_toAddress;	 Catch:{ Exception -> 0x003a }
        r7 = r6.equals(r5);	 Catch:{ Exception -> 0x003a }
        if (r7 == 0) goto L_0x0048;
    L_0x0032:
        r0 = 1;
    L_0x0033:
        com.abaltatech.mcs.tcpip.TCPIPAddressPool.freeAddress(r4);	 Catch:{ Exception -> 0x003a }
        com.abaltatech.mcs.tcpip.TCPIPAddressPool.freeAddress(r5);	 Catch:{ Exception -> 0x003a }
        return r0;
    L_0x003a:
        r8 = move-exception;
        r9 = r8.toString();
        r10 = "ERROR";
        com.abaltatech.mcs.logger.MCSLogger.log(r10, r9);
        return r0;
    L_0x0045:
        r11 = move-exception;
        monitor-exit(r12);	 Catch:{ Throwable -> 0x0045 }
        throw r11;
    L_0x0048:
        r0 = 0;
        goto L_0x0033;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcs.tcpip.ConnectionPointTCPIP.isIPPacketForMe(com.abaltatech.mcs.tcpip.TCPIPPacket):boolean");
    }

    protected void writeDataInternal(byte[] $r1, int $i0) throws  {
        int $i1 = 0;
        while ($i1 < $i0) {
            try {
                TCPIPPacket $r3 = createPacket($r1, $i0, $i1);
                if (this.m_slidingWindow == null) {
                    this.m_slidingWindow = new TCPSlidingWindow(this.m_writer, $r3.getWindow(), this.m_myInitialSequenceNumber + 1, this);
                }
                this.m_slidingWindow.addIPPacket($r3);
                $i1 += $r3.getDataLength();
            } catch (MCSException $r2) {
                MCSLogger.log("ERROR", $r2.toString());
                return;
            }
        }
        IMCSDataStats $r6 = getDataStats();
        if ($r6 != null) {
            $r6.onDataSent($i0);
        }
    }

    private TCPIPPacket createPacket(byte[] $r1, int $i0, int $i1) throws MCSException {
        $i0 -= $i1;
        if ($i0 > this.m_maximumSegmentSize) {
            $i0 = this.m_maximumSegmentSize;
        }
        TCPIPPacket $r2 = TCPIPPacketPool.getPacket("createPacket");
        $r2.setBuffer(null, 0);
        $r2.init($r1, $i0, $i1, 6, this.m_toAddress.getAddress().getAddress(), this.m_fromAddress.getAddress().getAddress(), this.m_toAddress.getPort(), this.m_fromAddress.getPort());
        int $i12 = this.m_myAcknowledgementNumber;
        $r2.setSeqNo($i12);
        $i12 = this.m_senderAcknowledgementNumber;
        $r2.setAckNo($i12);
        $r2.setWindow(4096);
        $r2.setFlags(24);
        $r2.createIPPacket();
        this.m_myAcknowledgementNumber += $i0;
        return $r2;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int readData(byte[] r14, int r15) throws  {
        /*
        r13 = this;
        monitor-enter(r13);
        r0 = r13.m_isClosed;	 Catch:{ Throwable -> 0x005a }
        if (r0 != 0) goto L_0x000d;
    L_0x0005:
        r1 = r13.m_inDataBuffers;	 Catch:{ Throwable -> 0x005a }
        r0 = r1.emptyq();	 Catch:{ Throwable -> 0x005a }
        if (r0 == 0) goto L_0x0010;
    L_0x000d:
        monitor-exit(r13);	 Catch:{ Throwable -> 0x005a }
        r2 = 0;
        return r2;
    L_0x0010:
        r3 = 0;
        r1 = r13.m_inDataBuffers;	 Catch:{ Throwable -> 0x005a }
        r4 = r1.get();	 Catch:{ MCSException -> 0x0028 }
        r3 = r4;
        r5 = r13.m_inDataSizes;	 Catch:{ MCSException -> 0x0028 }
        r6 = r5.get();	 Catch:{ MCSException -> 0x0028 }
        if (r15 >= r6) goto L_0x003c;
    L_0x0020:
        r7 = new com.abaltatech.mcs.common.MCSException;	 Catch:{ MCSException -> 0x0028 }
        r8 = "Buffer size too small";
        r7.<init>(r8);	 Catch:{ MCSException -> 0x0028 }
        throw r7;	 Catch:{ MCSException -> 0x0028 }
    L_0x0028:
        r7 = move-exception;
        r9 = r7.toString();	 Catch:{ Throwable -> 0x005a }
        r8 = "ERROR";
        com.abaltatech.mcs.logger.MCSLogger.log(r8, r9);	 Catch:{ Throwable -> 0x005a }
        if (r3 == 0) goto L_0x0039;
    L_0x0034:
        r8 = "MTPLayer";
        com.abaltatech.mcs.common.MemoryPool.freeMem(r3, r8);	 Catch:{ Throwable -> 0x005a }
    L_0x0039:
        monitor-exit(r13);	 Catch:{ Throwable -> 0x005a }
        r2 = 0;
        return r2;
    L_0x003c:
        r2 = 0;
        r10 = 0;
        java.lang.System.arraycopy(r4, r2, r14, r10, r6);	 Catch:{ MCSException -> 0x0028 }
        com.abaltatech.mcs.common.MemoryPool.freeMem(r4);	 Catch:{ MCSException -> 0x0028 }
        r3 = 0;
        r1 = r13.m_inDataBuffers;	 Catch:{ MCSException -> 0x0028 }
        r1.delete();	 Catch:{ MCSException -> 0x0028 }
        r5 = r13.m_inDataSizes;	 Catch:{ MCSException -> 0x0028 }
        r5.delete();	 Catch:{ MCSException -> 0x0028 }
        r11 = r13.getDataStats();	 Catch:{ MCSException -> 0x0028 }
        if (r11 == 0) goto L_0x0058;
    L_0x0055:
        r11.onDataReceived(r6);	 Catch:{ MCSException -> 0x0028 }
    L_0x0058:
        monitor-exit(r13);	 Catch:{ Throwable -> 0x005a }
        return r6;
    L_0x005a:
        r12 = move-exception;
        monitor-exit(r13);	 Catch:{ Throwable -> 0x005a }
        throw r12;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcs.tcpip.ConnectionPointTCPIP.readData(byte[], int):int");
    }

    public boolean canHandle(IMCSConnectionAddress $r1, IMCSConnectionAddress $r2) throws  {
        if (this.m_fromAddress == null) {
            return false;
        }
        if (this.m_toAddress == null) {
            return false;
        }
        if ($r1 == null) {
            return false;
        }
        if ($r2 == null) {
            return false;
        }
        if (!($r1 instanceof TCPIPAddress)) {
            return false;
        }
        if (!($r2 instanceof TCPIPAddress)) {
            return false;
        }
        TCPIPAddress $r3 = (TCPIPAddress) $r2;
        if (((TCPIPAddress) $r1).isSubsetOf(this.m_fromAddress)) {
            return $r3.isSubsetOf(this.m_toAddress);
        } else {
            return false;
        }
    }

    public IMCSConnectionAddress getFromAddress() throws  {
        return this.m_fromAddress;
    }

    public IMCSConnectionAddress getToAddress() throws  {
        return this.m_toAddress;
    }

    public void processPacket(TCPIPPacket $r1) throws MCSException {
        this.m_flags = $r1.getFlags();
        switch (this.m_state) {
            case 1:
                try {
                    processModeListen($r1);
                    return;
                } catch (MCSException $r2) {
                    MCSLogger.log("ERROR", $r2.toString());
                }
            case 2:
                processModeReceivedSyn($r1);
                return;
            case 3:
                processModeHandshaken($r1);
                return;
            case 4:
                processModeSentSyn($r1);
                return;
            default:
                return;
        }
        MCSLogger.log("ERROR", $r2.toString());
    }

    private void processModeSentSyn(TCPIPPacket $r1) throws MCSException {
        if ((this.m_flags & 18) == 18) {
            this.m_senderInitialSequenceNumber = $r1.getSeqNo();
            this.m_senderAcknowledgementNumber = this.m_senderInitialSequenceNumber + 1;
            this.m_slidingWindow = new TCPSlidingWindow(this.m_writer, $r1.getWindow(), this.m_myInitialSequenceNumber + 1, this);
            this.m_maximumSegmentSize = $r1.getMSS();
            $r1.reverseAddresses();
            $r1.setFlags(16);
            $r1.setSeqNo(this.m_myInitialSequenceNumber);
            $r1.setAckNo(this.m_senderInitialSequenceNumber + 1);
            $r1.setWindow(4096);
            $r1.setMSS(MAX_SEGMENT_SIZE);
            byte[] $r4 = $r1.getBuffer();
            $r1.setBuffer(null, 0);
            $r1.createIPPacket();
            this.m_writer.sendMessage($r1);
            $r1.freeBuffer();
            $r1.setBuffer($r4, 0);
            this.m_state = 3;
            synchronized (this) {
                this.m_isStarted = true;
            }
        }
    }

    private void processModeListen(TCPIPPacket $r1) throws MCSException {
        if ((this.m_flags & 2) == 2) {
            this.m_senderInitialSequenceNumber = $r1.getSeqNo();
            this.m_senderAcknowledgementNumber = this.m_senderInitialSequenceNumber + 1;
            this.m_myInitialSequenceNumber = 0;
            this.m_myAcknowledgementNumber = this.m_myInitialSequenceNumber + 1;
            this.m_slidingWindow = new TCPSlidingWindow(this.m_writer, $r1.getWindow(), this.m_myInitialSequenceNumber + 1, this);
            this.m_maximumSegmentSize = $r1.getMSS();
            $r1.reverseAddresses();
            $r1.setFlags(18);
            $r1.setSeqNo(this.m_myInitialSequenceNumber);
            $r1.setAckNo(this.m_senderInitialSequenceNumber + 1);
            $r1.setWindow(4096);
            $r1.setMSS(MAX_SEGMENT_SIZE);
            byte[] $r4 = $r1.getBuffer();
            $r1.setBuffer(null, 0);
            $r1.createIPPacket();
            this.m_writer.sendMessage($r1);
            $r1.freeBuffer();
            $r1.setBuffer($r4, 0);
            this.m_state = 2;
        } else if ((this.m_flags & 1) == 1) {
            sendFINPacket($r1);
        } else {
            resetConnection($r1);
        }
    }

    private void sendFINPacket(TCPIPPacket $r1) throws  {
        $r1.reverseAddresses();
        $r1.setFlags(17);
        $r1.setSeqNo(this.m_myAcknowledgementNumber);
        $r1.setAckNo(this.m_senderAcknowledgementNumber);
        $r1.setWindow(4096);
        try {
            $r1.createIPPacket();
            this.m_writer.sendMessage($r1);
        } catch (MCSException $r2) {
            MCSLogger.log("ERROR", $r2.toString());
        }
    }

    private void resetConnection(TCPIPPacket $r1) throws  {
        $r1.reverseAddresses();
        $r1.setFlags(4);
        $r1.setSeqNo(0);
        $r1.setAckNo(0);
        $r1.setWindow(0);
        try {
            $r1.createIPPacket();
            this.m_writer.sendMessage($r1);
        } catch (MCSException $r2) {
            MCSLogger.log("ERROR", $r2.toString());
        }
        this.m_state = 1;
    }

    private void processModeReceivedSyn(TCPIPPacket $r1) throws MCSException {
        if ((this.m_flags & 16) == 16 && $r1.getAckNo() == this.m_myInitialSequenceNumber + 1) {
            this.m_state = 3;
            synchronized (this) {
                this.m_isStarted = true;
            }
        } else if (this.m_flags != 2) {
            resetConnection($r1);
        } else {
            processModeListen($r1);
        }
    }

    private void processModeHandshaken(TCPIPPacket $r1) throws  {
        if ((this.m_flags & 4) == 4) {
            closeConnection($r1);
        } else if ((this.m_flags & 2) != 2) {
            if ((this.m_flags & 16) == 16) {
                this.m_slidingWindow.acknowledgeIPPacket($r1.getAckNo(), $r1.getWindow(), $r1.getFlags());
            }
            if ($r1.getDataLength() > 0) {
                processData($r1);
            }
            if ((this.m_flags & 1) == 1) {
                closeConnection($r1);
            }
        }
    }

    private void processData(TCPIPPacket $r1) throws  {
        if ($r1.getSeqNo() == this.m_senderAcknowledgementNumber) {
            byte[] $r3 = $r1.getBuffer();
            int $i1 = $r1.getDataOffset();
            int $i0 = $r1.getDataLength();
            try {
                byte[] $r4 = MemoryPool.getMem($i0, "ConnectionPointTCPIP");
                System.arraycopy($r3, $i1, $r4, 0, $i0);
                synchronized (this) {
                    this.m_inDataBuffers.insert($r4);
                    this.m_inDataSizes.insert($i0);
                }
                this.m_senderAcknowledgementNumber += $i0;
                sendAcknowledgementPacket($r1);
                notifyForData();
            } catch (MCSException $r2) {
                MCSLogger.log("ERROR", $r2.toString());
            }
        }
    }

    private void sendAcknowledgementPacket(TCPIPPacket $r1) throws MCSException {
        $r1.reverseAddresses();
        $r1.setSeqNo(this.m_myAcknowledgementNumber);
        $r1.setAckNo(this.m_senderAcknowledgementNumber);
        $r1.setWindow(4096);
        $r1.setFlags(16);
        byte[] $r2 = $r1.getBuffer();
        $r1.setBuffer(null, 0);
        $r1.createIPPacket();
        this.m_writer.sendMessage($r1);
        $r1.freeBuffer();
        $r1.setBuffer($r2, 0);
    }

    private void closeConnection(TCPIPPacket $r1) throws  {
        this.m_state = 1;
        sendFINPacket($r1);
        try {
            this.m_writer.closeConnection(this);
        } catch (MCSException $r2) {
            MCSLogger.log("ERROR", $r2.toString());
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() throws  {
        /*
        r9 = this;
    L_0x0000:
        r0 = 10;
        java.lang.Thread.sleep(r0);	 Catch:{ InterruptedException -> 0x002a }
    L_0x0005:
        monitor-enter(r9);
        r2 = r9.m_isClosed;	 Catch:{ Throwable -> 0x0012 }
        if (r2 == 0) goto L_0x000c;
    L_0x000a:
        monitor-exit(r9);	 Catch:{ Throwable -> 0x0012 }
        return;
    L_0x000c:
        r2 = r9.m_isStarted;	 Catch:{ Throwable -> 0x0012 }
        if (r2 != 0) goto L_0x0015;
    L_0x0010:
        monitor-exit(r9);	 Catch:{ Throwable -> 0x0012 }
        goto L_0x0000;
    L_0x0012:
        r3 = move-exception;
        monitor-exit(r9);	 Catch:{ Throwable -> 0x0012 }
        throw r3;
    L_0x0015:
        r4 = r9.m_slidingWindow;
        r4.processIPPackets();	 Catch:{ MCSException -> 0x001c }
    L_0x001a:
        monitor-exit(r9);	 Catch:{ Throwable -> 0x0012 }
        goto L_0x0000;
    L_0x001c:
        r5 = move-exception;
        r6 = r5.toString();	 Catch:{ Throwable -> 0x0012 }
        r7 = "ConnectionPointTCPIP";
        com.abaltatech.mcs.logger.MCSLogger.log(r7, r6);	 Catch:{ Throwable -> 0x0012 }
        r9.closeConnection();	 Catch:{ Throwable -> 0x0012 }
        goto L_0x001a;
    L_0x002a:
        r8 = move-exception;
        goto L_0x0005;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcs.tcpip.ConnectionPointTCPIP.run():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void closeConnection() throws  {
        /*
        r15 = this;
        monitor-enter(r15);
        r0 = r15.m_isClosed;	 Catch:{ Throwable -> 0x0021 }
        if (r0 == 0) goto L_0x0007;
    L_0x0005:
        monitor-exit(r15);	 Catch:{ Throwable -> 0x0021 }
        return;
    L_0x0007:
        r1 = 1;
        r15.m_isClosed = r1;	 Catch:{ Throwable -> 0x0021 }
    L_0x000a:
        r2 = r15.m_inDataBuffers;	 Catch:{ Throwable -> 0x0021 }
        r0 = r2.emptyq();	 Catch:{ Throwable -> 0x0021 }
        if (r0 != 0) goto L_0x0024;
    L_0x0012:
        r2 = r15.m_inDataBuffers;
        r3 = r2.delete();	 Catch:{ MCSException -> 0x001c }
        com.abaltatech.mcs.common.MemoryPool.freeMem(r3);	 Catch:{ MCSException -> 0x001c }
        goto L_0x000a;
    L_0x001c:
        r4 = move-exception;
        r4.printStackTrace();	 Catch:{ Throwable -> 0x0021 }
        goto L_0x000a;
    L_0x0021:
        r5 = move-exception;
        monitor-exit(r15);	 Catch:{ Throwable -> 0x0021 }
        throw r5;
    L_0x0024:
        monitor-exit(r15);	 Catch:{ Throwable -> 0x0021 }
        r15.notifyForConnectionClosed();
        r15.clearNotifiables();
        r6 = r15.m_slidingWindow;
        if (r6 == 0) goto L_0x0034;
    L_0x002f:
        r6 = r15.m_slidingWindow;
        r6.close();	 Catch:{ MCSException -> 0x0077 }
    L_0x0034:
        r8 = "TCPIP connection - closeConnection";
        r7 = com.abaltatech.mcs.tcpip.TCPIPPacketPool.getPacket(r8);	 Catch:{ MCSException -> 0x0077 }
        r9 = r15.m_toAddress;	 Catch:{ MCSException -> 0x0077 }
        r7.setSourceIPAddress(r9);	 Catch:{ MCSException -> 0x0077 }
        r9 = r15.m_fromAddress;	 Catch:{ MCSException -> 0x0077 }
        r7.setDestIPAddress(r9);	 Catch:{ MCSException -> 0x0077 }
        r1 = 1;
        r7.setFlags(r1);	 Catch:{ MCSException -> 0x0077 }
        r10 = r15.m_myAcknowledgementNumber;	 Catch:{ MCSException -> 0x0077 }
        r7.setSeqNo(r10);	 Catch:{ MCSException -> 0x0077 }
        r10 = r15.m_senderAcknowledgementNumber;	 Catch:{ MCSException -> 0x0077 }
        r7.setAckNo(r10);	 Catch:{ MCSException -> 0x0077 }
        r10 = r15.m_senderAcknowledgementNumber;	 Catch:{ MCSException -> 0x0077 }
        r7.setAckNo(r10);	 Catch:{ MCSException -> 0x0077 }
        r1 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r7.setWindow(r1);	 Catch:{ MCSException -> 0x0077 }
        r11 = 0;
        r1 = 0;
        r7.setBuffer(r11, r1);	 Catch:{ MCSException -> 0x0077 }
        r7.createIPPacket();	 Catch:{ MCSException -> 0x0077 }
        r12 = r15.m_writer;	 Catch:{ MCSException -> 0x0077 }
        r12.sendMessage(r7);	 Catch:{ MCSException -> 0x0077 }
        com.abaltatech.mcs.tcpip.TCPIPPacketPool.freePacket(r7);	 Catch:{ MCSException -> 0x0077 }
        r9 = r15.m_fromAddress;	 Catch:{ MCSException -> 0x0077 }
        com.abaltatech.mcs.tcpip.TCPIPAddressPool.freeAddress(r9);	 Catch:{ MCSException -> 0x0077 }
        r9 = r15.m_toAddress;	 Catch:{ MCSException -> 0x0077 }
        com.abaltatech.mcs.tcpip.TCPIPAddressPool.freeAddress(r9);	 Catch:{ MCSException -> 0x0077 }
        return;
    L_0x0077:
        r13 = move-exception;
        r14 = r13.toString();
        r8 = "ERROR";
        com.abaltatech.mcs.logger.MCSLogger.log(r8, r14);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcs.tcpip.ConnectionPointTCPIP.closeConnection():void");
    }

    public void sendConnect() throws  {
        if (this.m_state == 1 || this.m_state == 4) {
            this.m_myInitialSequenceNumber = 0;
            this.m_myAcknowledgementNumber = this.m_myInitialSequenceNumber + 1;
            try {
                TCPIPPacket $r4 = TCPIPPacketPool.getPacket("TCPIPConnectionPoint - sendConnect");
                $r4.setBuffer(null, 0);
                $r4.setSourceIPAddress(this.m_toAddress);
                $r4.setDestIPAddress(this.m_fromAddress);
                $r4.setFlags(2);
                $r4.setSeqNo(this.m_myInitialSequenceNumber);
                $r4.setAckNo(0);
                $r4.setWindow(4096);
                $r4.setMSS(MAX_SEGMENT_SIZE);
                $r4.createIPPacket();
                this.m_writer.sendMessage($r4);
                TCPIPPacketPool.freePacket($r4);
                this.m_state = 4;
                return;
            } catch (MCSException $r1) {
                MCSLogger.log("TCPIPConnectionPoint - sendConnect exception", $r1.toString());
                return;
            }
        }
        MCSLogger.log("TCPIPConnectionPoint sendConnect()", "Invalid internal state: " + this.m_state);
    }

    public boolean isReady() throws  {
        return this.m_state == 3;
    }
}
