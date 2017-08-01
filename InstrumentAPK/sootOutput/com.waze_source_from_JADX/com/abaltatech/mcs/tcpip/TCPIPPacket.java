package com.abaltatech.mcs.tcpip;

import android.support.v4.internal.view.SupportMenu;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.abaltatech.mcs.common.MCSException;
import com.abaltatech.mcs.common.MemoryPool;
import com.abaltatech.mcs.logger.MCSLogger;
import com.abaltatech.mcs.utils.ByteUtils;

public class TCPIPPacket {
    private static final int DEFAULT_MSS = 536;
    public static final String MEM_TAG = "TCPIPPacket";
    static final int MinIPHeaderLen = 20;
    static final int MinTCPHeaderLen = 20;
    public static final byte PT_TCP = (byte) 1;
    public static final byte PT_UDP = (byte) 2;
    public static final byte PT_Unknown = (byte) 0;
    public static final int TCPProtocolID = 6;
    public static final byte TCP_ACK = (byte) 16;
    public static final byte TCP_FIN = (byte) 1;
    public static final byte TCP_PSH = (byte) 8;
    public static final byte TCP_RST = (byte) 4;
    public static final byte TCP_SYN = (byte) 2;
    public static final byte TCP_URG = (byte) 32;
    static final int UDPHeaderLen = 8;
    public static final int UDPProtocolID = 17;
    private static final String[] cIPProtocolIDs = new String[]{"Reserved", "ICMP", "IGMP", "GGP", "IP-in-IP encapsulation", "5", "TCP", "7", "EGP", "9", "10", "11", "12", "13", "14", "15", "16", "UDP"};
    private int m_ackNo = 0;
    private byte[] m_buffer;
    private int m_dataOffset = 0;
    private byte[] m_destAddress = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0};
    private int m_destPort = 0;
    private int m_flags = 0;
    private int m_ipHeaderLength = 0;
    private int m_ipProtocolID = -1;
    boolean m_isLocked = false;
    private boolean m_isPacketOK = false;
    private int m_mss = 0;
    private int m_packetID = 1;
    private byte m_protocolType = (byte) 0;
    private int m_seqNo = 0;
    private byte[] m_sourceAddress = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0};
    private int m_sourcePort = 0;
    private int m_totalLength = 0;
    private int m_udpLength = 0;
    private int m_window = 0;

    public void setBuffer(byte[] $r1, int $i0) throws  {
        boolean $z0 = false;
        if (this.m_isLocked) {
            MCSLogger.log("ERROR", "setBuffer() called while packet is locked");
        }
        this.m_buffer = $r1;
        this.m_totalLength = $i0;
        this.m_isPacketOK = false;
        this.m_protocolType = (byte) 0;
        if (this.m_totalLength >= 20 && isIPHeaderOK()) {
            if (6 == this.m_ipProtocolID) {
                $z0 = isTCPHeaderOK();
            } else if (17 == this.m_ipProtocolID) {
                $z0 = isUDPHeaderOK();
            }
            this.m_isPacketOK = $z0;
        }
    }

    private boolean isTCPHeaderOK() throws  {
        this.m_mss = 0;
        if (this.m_ipProtocolID != 6) {
            return false;
        }
        this.m_protocolType = (byte) 1;
        if (this.m_totalLength - this.m_ipHeaderLength < 20) {
            return false;
        }
        this.m_dataOffset = (((this.m_buffer[this.m_ipHeaderLength + 12] >> (byte) 4) & (byte) 15) * 4) + this.m_ipHeaderLength;
        if (this.m_dataOffset > this.m_totalLength) {
            return false;
        }
        this.m_sourcePort = ByteUtils.getWord(this.m_buffer, this.m_ipHeaderLength);
        this.m_destPort = ByteUtils.getWord(this.m_buffer, this.m_ipHeaderLength + 2);
        this.m_seqNo = ByteUtils.getInt(this.m_buffer, this.m_ipHeaderLength + 4, 4);
        this.m_ackNo = ByteUtils.getInt(this.m_buffer, this.m_ipHeaderLength + 8, 4);
        this.m_flags = this.m_buffer[this.m_ipHeaderLength + 13] & (byte) 63;
        this.m_window = ByteUtils.getInt(this.m_buffer, this.m_ipHeaderLength + 14, 2);
        int $i1 = 40;
        while ($i1 < this.m_dataOffset) {
            $i1 += parseOption($i1);
        }
        if ($i1 != this.m_dataOffset) {
            return false;
        }
        if (calculateTCPChecksum() != SupportMenu.USER_MASK) {
            System.out.println("");
        }
        return true;
    }

    private int parseOption(int $i0) throws  {
        switch (this.m_buffer[$i0]) {
            case (byte) 0:
            case (byte) 1:
                return 1;
            case (byte) 2:
                if ($i0 + 4 > this.m_dataOffset || this.m_buffer[$i0 + 1] != (byte) 4) {
                    return this.m_totalLength;
                }
                this.m_mss = ByteUtils.getWord(this.m_buffer, $i0 + 2);
                return 4;
            default:
                int $i2 = ByteUtils.toUnsignedInteger(this.m_buffer[$i0 + 1]);
                if ($i2 + $i0 > this.m_dataOffset) {
                    return this.m_totalLength;
                }
                return $i2;
        }
    }

    private int calculateTCPChecksum() throws  {
        int $i0 = this.m_totalLength - this.m_ipHeaderLength;
        return ByteUtils.onesComplement((ByteUtils.sumWords(ByteUtils.sumWords(ByteUtils.sumWords(0, this.m_buffer, this.m_ipHeaderLength, ($i0 + 1) / 2), this.m_sourceAddress, 0, 2), this.m_destAddress, 0, 2) + 6) + $i0);
    }

    private boolean isUDPHeaderOK() throws  {
        if (this.m_ipProtocolID != 17) {
            return false;
        }
        this.m_protocolType = (byte) 2;
        if (this.m_totalLength - this.m_ipHeaderLength < 8) {
            return false;
        }
        this.m_dataOffset = this.m_ipHeaderLength + 8;
        if (this.m_dataOffset > this.m_totalLength) {
            return false;
        }
        this.m_sourcePort = ByteUtils.getWord(this.m_buffer, this.m_ipHeaderLength);
        this.m_destPort = ByteUtils.getWord(this.m_buffer, this.m_ipHeaderLength + 2);
        this.m_udpLength = ByteUtils.getWord(this.m_buffer, this.m_ipHeaderLength + 4);
        if (this.m_udpLength + this.m_ipHeaderLength == this.m_totalLength) {
            return ByteUtils.getWord(this.m_buffer, this.m_ipHeaderLength + 6) + 0 == 0 || SupportMenu.USER_MASK == calcUDPChecksum();
        } else {
            return false;
        }
    }

    private int calcUDPChecksum() throws  {
        int $i0 = (ByteUtils.sumWords(ByteUtils.sumWords(0, this.m_sourceAddress, 0, 2), this.m_destAddress, 0, 2) + 17) + this.m_udpLength;
        for (int $i1 = 0; $i1 <= this.m_totalLength; $i1 += 2) {
            $i0 += ByteUtils.getWord(this.m_buffer, this.m_ipHeaderLength + $i1);
        }
        return ByteUtils.onesComplement($i0);
    }

    private boolean isIPHeaderOK() throws  {
        if ((this.m_buffer[0] >> (byte) 4) != (byte) 4) {
            return false;
        }
        int $i0 = (this.m_buffer[0] & (byte) 15) * 4;
        if ($i0 > this.m_totalLength) {
            return false;
        }
        if ($i0 < 20) {
            return false;
        }
        this.m_ipHeaderLength = $i0;
        this.m_ipProtocolID = this.m_buffer[9];
        if (!(this.m_ipProtocolID == 6 || this.m_ipProtocolID == 17)) {
            printProtocolVersion();
        }
        if (ByteUtils.getWord(this.m_buffer, 2) != this.m_totalLength) {
            return false;
        }
        for ($i0 = 0; $i0 < 4; $i0++) {
            this.m_sourceAddress[$i0] = this.m_buffer[$i0 + 12];
            this.m_destAddress[$i0] = this.m_buffer[$i0 + 16];
        }
        if (SupportMenu.USER_MASK == calculateIPChecksum()) {
            return 6 == this.m_ipProtocolID || 17 == this.m_ipProtocolID;
        } else {
            return false;
        }
    }

    private int calculateIPChecksum() throws  {
        int $i0 = 0;
        for (int $i1 = 0; $i1 < this.m_ipHeaderLength; $i1 += 2) {
            $i0 += ByteUtils.getWord(this.m_buffer, $i1);
        }
        return ByteUtils.onesComplement($i0);
    }

    private void printProtocolVersion() throws  {
        if (this.m_ipProtocolID < 0 || this.m_ipProtocolID >= cIPProtocolIDs.length) {
            MCSLogger.log("IP Protocol Version", "Unknown protocol - " + Integer.toString(this.m_ipProtocolID));
            return;
        }
        MCSLogger.log("IP Protocol Version", cIPProtocolIDs[this.m_ipProtocolID]);
    }

    public boolean isPacketOK() throws  {
        return this.m_isPacketOK;
    }

    public int getFlags() throws  {
        return this.m_flags;
    }

    public byte[] getDestIPAddress() throws  {
        return this.m_destAddress;
    }

    public byte[] getSourceIPAddress() throws  {
        return this.m_sourceAddress;
    }

    public int getDestPort() throws  {
        return this.m_destPort;
    }

    public int getSourcePort() throws  {
        return this.m_sourcePort;
    }

    public byte getProtocolType() throws  {
        return this.m_protocolType;
    }

    public int getSeqNo() throws  {
        return this.m_seqNo;
    }

    public void reverseAddresses() throws  {
        byte[] $r1 = this.m_sourceAddress;
        this.m_sourceAddress = this.m_destAddress;
        this.m_destAddress = $r1;
        int $i0 = this.m_sourcePort;
        this.m_sourcePort = this.m_destPort;
        this.m_destPort = $i0;
    }

    public void setFlags(int $i0) throws  {
        this.m_flags = $i0;
    }

    public void setSeqNo(int $i0) throws  {
        this.m_seqNo = $i0;
    }

    public void setAckNo(int $i0) throws  {
        this.m_ackNo = $i0;
    }

    public void setWindow(int $i0) throws  {
        this.m_window = $i0;
    }

    public void createIPPacket() throws MCSException {
        if (this.m_isLocked) {
            throw new MCSException("TCPIPPacket is locked while createMessage() is called");
        }
        int $i0 = this.m_buffer != null ? this.m_totalLength : getOptionsLength() + 40;
        if (this.m_buffer == null) {
            this.m_buffer = MemoryPool.getMem($i0, "TCPIPPacket");
            this.m_totalLength = $i0;
        } else if (this.m_totalLength == 0) {
            this.m_totalLength = getOptionsLength() + 40;
        }
        createIPHeader($i0);
        createTCPHeader();
        if (this.m_totalLength < this.m_buffer.length) {
            this.m_buffer[this.m_totalLength] = (byte) 0;
        }
        ByteUtils.writeToArray(ByteUtils.bitwiseNot(calculateTCPChecksum()), this.m_buffer, this.m_ipHeaderLength + 16, 2);
        if (calculateTCPChecksum() != SupportMenu.USER_MASK) {
            MCSLogger.log("ERROR while calculating TCP checksum");
        }
    }

    private int getOptionsLength() throws  {
        if (this.m_mss != 0) {
            return 0 + 4;
        }
        return 0;
    }

    private void createTCPHeader() throws  {
        this.m_dataOffset = (this.m_ipHeaderLength + 20) + getOptionsLength();
        ByteUtils.writeToArray(this.m_sourcePort, this.m_buffer, this.m_ipHeaderLength, 2);
        ByteUtils.writeToArray(this.m_destPort, this.m_buffer, this.m_ipHeaderLength + 2, 2);
        ByteUtils.writeToArray(this.m_seqNo, this.m_buffer, this.m_ipHeaderLength + 4, 4);
        ByteUtils.writeToArray(this.m_ackNo, this.m_buffer, this.m_ipHeaderLength + 8, 4);
        this.m_buffer[this.m_ipHeaderLength + 12] = (byte) ((((this.m_dataOffset - 20) / 4) << 4) & 240);
        this.m_buffer[this.m_ipHeaderLength + 13] = (byte) (this.m_flags & 63);
        ByteUtils.writeToArray(this.m_window, this.m_buffer, this.m_ipHeaderLength + 14, 2);
        ByteUtils.writeToArray(0, this.m_buffer, this.m_ipHeaderLength + 16, 2);
        ByteUtils.writeToArray(0, this.m_buffer, this.m_ipHeaderLength + 18, 2);
        if (this.m_mss > 0) {
            this.m_buffer[this.m_ipHeaderLength + 20] = (byte) 2;
            this.m_buffer[this.m_ipHeaderLength + 21] = (byte) 4;
            ByteUtils.writeToArray(this.m_mss, this.m_buffer, this.m_ipHeaderLength + 22, 2);
        }
    }

    private void createIPHeader(int $i0) throws  {
        this.m_ipHeaderLength = 20;
        this.m_buffer[0] = (byte) ((this.m_ipHeaderLength / 4) + 64);
        this.m_buffer[1] = (byte) 0;
        ByteUtils.writeToArray($i0, this.m_buffer, 2, 2);
        ByteUtils.writeToArray(getNextPacketID(), this.m_buffer, 4, 2);
        byte[] $r1 = this.m_buffer;
        this.m_buffer[7] = (byte) 0;
        $r1[6] = (byte) 0;
        this.m_buffer[8] = (byte) 55;
        this.m_buffer[9] = (byte) 6;
        $r1 = this.m_buffer;
        this.m_buffer[11] = (byte) 0;
        $r1[10] = (byte) 0;
        for ($i0 = 0; $i0 < 4; $i0++) {
            this.m_buffer[$i0 + 12] = this.m_sourceAddress[$i0];
            this.m_buffer[$i0 + 16] = this.m_destAddress[$i0];
        }
        ByteUtils.writeToArray(ByteUtils.bitwiseNot(calculateIPChecksum()), this.m_buffer, 10, 2);
        if (calculateIPChecksum() != SupportMenu.USER_MASK) {
            MCSLogger.log("ERROR while recalculating IP Checksum");
        }
    }

    private synchronized int getNextPacketID() throws  {
        this.m_packetID++;
        return this.m_packetID;
    }

    public void freeBuffer() throws  {
        if (this.m_isLocked) {
            MCSLogger.log("ERROR", "freeBuffer() called while packet is locked");
        }
        MemoryPool.freeMem(this.m_buffer, "TCPIPPacket");
        this.m_buffer = null;
    }

    public byte[] getBuffer() throws  {
        return this.m_buffer;
    }

    public int getBufferLength() throws  {
        return this.m_totalLength;
    }

    public int getAckNo() throws  {
        return this.m_ackNo;
    }

    public int getDataLength() throws  {
        return this.m_totalLength - this.m_dataOffset;
    }

    public int getDataOffset() throws  {
        return this.m_dataOffset;
    }

    public int getWindow() throws  {
        return this.m_window;
    }

    public void init(byte[] $r1, int $i0, int $i1, int $i2, byte[] $r2, byte[] $r3, int $i3, int $i4) throws MCSException {
        this.m_totalLength = ($i0 + 20) + 20;
        this.m_mss = 0;
        if (this.m_isLocked) {
            MCSLogger.log("ERROR", "init() called while packet is locked");
        }
        for (int $i5 = 0; $i5 < 4; $i5++) {
            this.m_sourceAddress[$i5] = $r2[$i5];
            this.m_destAddress[$i5] = $r3[$i5];
        }
        this.m_sourcePort = $i3;
        this.m_destPort = $i4;
        this.m_buffer = MemoryPool.getMem(this.m_totalLength, "TCPIPPacket");
        this.m_flags = 0;
        this.m_ipHeaderLength = 20;
        this.m_dataOffset = this.m_ipHeaderLength + 20;
        this.m_ipProtocolID = $i2;
        if ($r1 != null && $i0 > 0) {
            System.arraycopy($r1, $i1, this.m_buffer, this.m_ipHeaderLength + 20, $i0);
        }
    }

    public String toString() throws  {
        int $i0 = this.m_totalLength - this.m_dataOffset;
        String $r2 = (((((((addressToString(this.m_sourceAddress) + ":") + String.valueOf(this.m_sourcePort)) + " -> ") + addressToString(this.m_destAddress)) + ":") + String.valueOf(this.m_destPort)) + " (P" + String.valueOf(this.m_ipProtocolID) + ")") + " TL " + this.m_totalLength;
        String $r4 = $r2;
        if ($i0 > 0) {
            $r4 = ($r2 + " DS " + $i0) + " DO " + this.m_dataOffset;
        }
        return ($r4 + " SeqNo " + this.m_seqNo + " AckNo " + this.m_ackNo) + flagsToString();
    }

    private String flagsToString() throws  {
        String $r1 = " ";
        if ((this.m_flags & 16) == 16) {
            $r1 = " " + " ACK";
        }
        if ((this.m_flags & 1) == 1) {
            $r1 = $r1 + " FIN";
        }
        if ((this.m_flags & 2) == 2) {
            $r1 = $r1 + " SYN";
        }
        if ((this.m_flags & 4) == 4) {
            $r1 = $r1 + " RST";
        }
        if ((this.m_flags & 8) == 8) {
            $r1 = $r1 + " PSH";
        }
        if ((this.m_flags & 32) == 32) {
            return $r1 + " URG";
        }
        return $r1;
    }

    private String addressToString(byte[] $r1) throws  {
        String $r2 = String.valueOf(ByteUtils.toUnsignedInteger($r1[0]));
        for (int $i1 = 1; $i1 < 4; $i1++) {
            $r2 = $r2 + FileUploadSession.SEPARATOR + String.valueOf(ByteUtils.toUnsignedInteger($r1[$i1]));
        }
        return $r2;
    }

    public int getOptionCount() throws  {
        return this.m_dataOffset - 40;
    }

    public int getMSS() throws  {
        return this.m_mss == 0 ? 536 : this.m_mss;
    }

    public void setMSS(int $i0) throws  {
        this.m_mss = $i0;
    }

    public boolean isSameAs(TCPIPPacket $r1) throws  {
        if (this.m_ackNo != $r1.m_ackNo) {
            return false;
        }
        if (this.m_dataOffset != $r1.m_dataOffset) {
            return false;
        }
        if (this.m_destPort != $r1.m_destPort) {
            return false;
        }
        if (this.m_flags != $r1.m_flags) {
            return false;
        }
        if (this.m_ipHeaderLength != $r1.m_ipHeaderLength) {
            return false;
        }
        if (this.m_ipProtocolID != $r1.m_ipProtocolID) {
            return false;
        }
        if (this.m_seqNo != $r1.m_seqNo) {
            return false;
        }
        if (this.m_sourcePort != $r1.m_sourcePort) {
            return false;
        }
        if (this.m_totalLength != $r1.m_totalLength) {
            return false;
        }
        if (this.m_udpLength != $r1.m_udpLength) {
            return false;
        }
        if (this.m_window != $r1.m_window) {
            return false;
        }
        for (int $i0 = 0; $i0 < 4; $i0++) {
            if (this.m_sourceAddress[$i0] != $r1.m_sourceAddress[$i0]) {
                return false;
            }
            if (this.m_destAddress[$i0] != $r1.m_destAddress[$i0]) {
                return false;
            }
        }
        return true;
    }

    public void setSourceIPAddress(TCPIPAddress $r1) throws  {
        System.arraycopy($r1.m_address, 0, this.m_sourceAddress, 0, 4);
        this.m_sourcePort = $r1.getPort();
    }

    public void setDestIPAddress(TCPIPAddress $r1) throws  {
        System.arraycopy($r1.m_address, 0, this.m_destAddress, 0, 4);
        this.m_destPort = $r1.getPort();
    }
}
