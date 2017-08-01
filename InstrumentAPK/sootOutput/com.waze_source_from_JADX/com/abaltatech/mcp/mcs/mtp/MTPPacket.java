package com.abaltatech.mcp.mcs.mtp;

import com.abaltatech.mcp.mcs.common.IMCSConnectionAddress;
import com.abaltatech.mcp.mcs.tcpip.TCPIPAddress;
import com.abaltatech.mcp.mcs.utils.ByteUtils;
import java.net.Inet4Address;
import java.net.Inet6Address;

public class MTPPacket {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final int ADDR_SCHEMA_IPv4 = 1;
    private static final int ADDR_SCHEMA_IPv6 = 2;
    private static final int ADDR_SCHEMA_NONE = 0;
    public static final int AR_BufferInvalid = -1;
    public static final int AR_MoreDataNeeded = 0;
    private static final int CHECKSUM_PRESENT_BITS_COUNT = 1;
    private static final int CHECKSUM_PRESENT_BIT_OFFSET = 8;
    private static final int IS_COMPRESSED_BITS_COUNT = 1;
    private static final int IS_COMPRESSED_BIT_OFFSET = 9;
    private static final int IS_LAST_BITS_COUNT = 1;
    private static final int IS_LAST_BIT_OFFSET = 0;
    private static final int MESSAGE_TYPE_BITS_COUNT = 4;
    private static final int MESSAGE_TYPE_BIT_OFFSET = 4;
    private static final int MTP_BEGIN_FRAME = 30;
    private static final int MTP_END_FRAME = 3;
    private static final int MTP_OFFSET_BEGIN_FRAME = 0;
    private static final int MTP_OFFSET_FRAME_SIZE = 1;
    private static final int MTP_OFFSET_OPTIONS = 3;
    private static final int MTP_OFFSET_SRC_ADDRESS = 5;
    public static final int MTP_PROTOCOL_TCP = 0;
    public static final int MTP_PROTOCOL_UDP = 1;
    public static final int MaxMTPPacketSize = 16384;
    public static final int PT_CloseListenConn = 3;
    public static final int PT_Data = 0;
    public static final int PT_OpenListenConn = 2;
    public static final int PT_ResolveAddr = 1;
    public static final int PT_StartDGramListen = 4;
    public static final int PT_StopDGramListen = 5;
    public static final int PT_Unknown = -1;
    private static final int SOURCE_PROTOCOL_BITS_COUNT = 3;
    private static final int SOURCE_PROTOCOL_BIT_OFFSET = 1;
    private static int ms_packetID = 0;
    private static boolean ms_useCheckSum = false;
    private IMCSConnectionAddress m_dstAddress;
    private int m_frameOptions;
    private int m_frameSize;
    private boolean m_isValid;
    MTPPacket m_nextPacket;
    private byte[] m_packet;
    private int m_packetID;
    private int m_payloadOffset;
    private int m_payloadSize;
    private IMCSConnectionAddress m_srcAddress;

    private static int CalculateChecksum(byte[] buffer, int offset, int size) throws  {
        return 0;
    }

    public static int GetMaxDataSize() throws  {
        return 16284;
    }

    static {
        boolean $z0;
        if (MTPPacket.class.desiredAssertionStatus()) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        $assertionsDisabled = $z0;
    }

    public MTPPacket() throws  {
        this.m_nextPacket = null;
        this.m_packet = null;
        this.m_isValid = false;
        this.m_payloadSize = 0;
        this.m_payloadOffset = 0;
        this.m_frameSize = 0;
        this.m_frameOptions = 0;
        this.m_srcAddress = null;
        this.m_dstAddress = null;
        this.m_packetID = 0;
        this.m_packet = new byte[16384];
        int $i0 = ms_packetID + 1;
        ms_packetID = $i0;
        this.m_packetID = $i0;
    }

    public boolean InitPacket(byte[] $r1, int $i0, int $i1) throws  {
        boolean $z0 = false;
        if ($i1 > 5 && $r1[$i0 + 0] == (byte) 30) {
            int $i2 = ByteUtils.ReadWord($r1, $i0 + 1);
            if (!$assertionsDisabled && $i2 > 16384) {
                throw new AssertionError();
            } else if ($i2 <= 16384 && $i2 <= $i1 && $r1[($i0 + $i2) - 1] == (byte) 3) {
                $i1 = ByteUtils.ReadWord($r1, $i0 + 3);
                boolean $z1 = ByteUtils.ReadBits($i1, 8, 1) != 0;
                $z0 = true;
                if ($z1) {
                    if (ByteUtils.ReadWord($r1, ($i0 + 1) - 3) == CalculateChecksum($r1, $i0, $i2 - 3)) {
                        $z0 = true;
                    } else {
                        $z0 = false;
                    }
                }
                if ($z0) {
                    int $i4 = GetAdressSize($r1[$i0 + 5]);
                    int $i5 = GetAdressSize($r1[($i0 + 5) + $i4]);
                    if ($assertionsDisabled || ($i4 > 0 && $i5 > 0)) {
                        if ($i4 <= 0 || $i5 <= 0) {
                            $z0 = false;
                        } else {
                            $z0 = true;
                        }
                        if ($z0) {
                            System.arraycopy($r1, $i0, this.m_packet, 0, $i2);
                            this.m_isValid = true;
                            this.m_frameSize = $i2;
                            this.m_frameOptions = $i1;
                            this.m_srcAddress = CopyAddressFromMTP(this.m_packet, 5);
                            this.m_dstAddress = CopyAddressFromMTP(this.m_packet, $i4 + 5);
                            this.m_payloadOffset = ($i4 + 5) + $i5;
                            this.m_payloadSize = (this.m_frameSize - this.m_payloadOffset) - 1;
                            if ($z1) {
                                this.m_payloadSize -= 2;
                            }
                        }
                    } else {
                        throw new AssertionError();
                    }
                }
            }
        }
        if ($assertionsDisabled || $z0) {
            return $z0;
        }
        throw new AssertionError();
    }

    public void ReleasePacket() throws  {
        this.m_isValid = false;
        this.m_payloadSize = 0;
        this.m_payloadOffset = 0;
        this.m_frameSize = 0;
        this.m_frameOptions = 0;
        this.m_srcAddress = null;
        this.m_dstAddress = null;
        MTPPacketPool.ReleasePacket(this);
    }

    public boolean IsValid() throws  {
        return this.m_isValid;
    }

    public int GetType() throws  {
        return ByteUtils.ReadBits(this.m_frameOptions, 4, 4);
    }

    public boolean IsLastMessage() throws  {
        return ByteUtils.ReadBits(this.m_frameOptions, 0, 1) != 0;
    }

    public boolean IsPayloadCompressed() throws  {
        return ByteUtils.ReadBits(this.m_frameOptions, 9, 1) != 0;
    }

    public int GetSourceProtocol() throws  {
        return ByteUtils.ReadBits(this.m_frameOptions, 1, 3);
    }

    public byte[] GetPacket() throws  {
        return this.m_packet;
    }

    public int GetPacketSize() throws  {
        return this.m_frameSize;
    }

    public int GetPayloadSize() throws  {
        return this.m_payloadSize;
    }

    public int GetPayloadOffset() throws  {
        return this.m_payloadOffset;
    }

    public IMCSConnectionAddress GetFromAddress() throws  {
        return this.m_srcAddress;
    }

    public IMCSConnectionAddress GetToAddress() throws  {
        return this.m_dstAddress;
    }

    public byte[] GetData() throws  {
        if (GetType() != 0) {
            return null;
        }
        byte[] $r1 = new byte[this.m_payloadSize];
        if (this.m_payloadSize <= 0) {
            return $r1;
        }
        System.arraycopy(this.m_packet, this.m_payloadOffset, $r1, 0, this.m_payloadSize);
        return $r1;
    }

    public String GetNameResolution() throws  {
        if (GetType() == 1) {
            return new String(this.m_packet, this.m_payloadOffset, this.m_payloadSize);
        }
        return null;
    }

    public static int AnalyzeBuffer(byte[] $r0, int $i0, int $i1) throws  {
        if (!$assertionsDisabled && ($r0 == null || $r0.length < $i0 + $i1)) {
            throw new AssertionError();
        } else if ($r0 != null) {
            if (!$assertionsDisabled && $r0[$i0 + 0] != (byte) 30) {
                throw new AssertionError();
            } else if ($r0[$i0 + 0] != (byte) 30) {
                return -1;
            } else {
                if ($i1 <= 5) {
                    return 0;
                }
                int $i2 = ByteUtils.ReadWord($r0, $i0 + 1);
                if ($i2 > $i1) {
                    return 0;
                }
                if ($r0[($i0 + $i2) - 1] == (byte) 3) {
                    boolean $z0 = true;
                    if (ByteUtils.ReadBits(ByteUtils.ReadWord($r0, $i0 + 3), 8, 1) != 0) {
                        if (ByteUtils.ReadWord($r0, ($i0 + 1) - 3) == CalculateChecksum($r0, $i0, $i2 - 3)) {
                            $z0 = true;
                        } else {
                            $z0 = false;
                        }
                    }
                    if (!$assertionsDisabled && !$z0) {
                        throw new AssertionError();
                    } else if ($z0) {
                        return $i2;
                    } else {
                        return -1;
                    }
                } else if ($assertionsDisabled) {
                    return -1;
                } else {
                    throw new AssertionError();
                }
            }
        } else if ($assertionsDisabled) {
            return -1;
        } else {
            throw new AssertionError();
        }
    }

    public static MTPPacket CreateNameResolutionPacket(IMCSConnectionAddress $r0, IMCSConnectionAddress $r1, String $r2) throws  {
        if (!$assertionsDisabled && $r2 == null) {
            throw new AssertionError();
        } else if ($r2 == null) {
            return null;
        } else {
            byte[] $r4 = $r2.getBytes();
            return CreatePacket($r0, $r1, $r4, 0, $r4.length, true, false, 0, 1);
        }
    }

    public static MTPPacket CreateDataPacket(IMCSConnectionAddress $r0, IMCSConnectionAddress $r1, byte[] $r2, int $i0, int $i1, boolean $z0, boolean $z1, int $i2) throws  {
        if (!$assertionsDisabled && ($r0 == null || $r1 == null)) {
            throw new AssertionError();
        } else if ($r0 == null) {
            return null;
        } else {
            if ($r1 != null) {
                return CreatePacket($r0, $r1, $r2, $i0, $i1, $z0, $z1, $i2, 0);
            }
            return null;
        }
    }

    public static MTPPacket CreateStartListeningPacket(int $i0, IMCSConnectionAddress $r0, int $i1) throws  {
        if ($r0 == null) {
            return null;
        }
        byte[] $r2 = new byte[4];
        ByteUtils.WriteDWord($r2, 0, $i0);
        TCPIPAddress $r1 = new TCPIPAddress($r2, 0);
        ByteUtils.WriteWord($r2, 0, $i1);
        return CreatePacket($r1, $r0, $r2, 0, 2, false, false, 0, 2);
    }

    public static MTPPacket CreateStopListeningPacket(IMCSConnectionAddress $r0) throws  {
        if (!$assertionsDisabled && $r0 == null) {
            throw new AssertionError();
        } else if ($r0 != null) {
            return CreatePacket(null, $r0, null, 0, 0, true, false, 0, 3);
        } else {
            return null;
        }
    }

    public static MTPPacket CreateStartDatagramListeningPacket(int $i0, IMCSConnectionAddress $r0, int $i1) throws  {
        if (!$assertionsDisabled && $r0 == null) {
            throw new AssertionError();
        } else if ($r0 == null) {
            return null;
        } else {
            byte[] $r2 = new byte[4];
            ByteUtils.WriteDWord($r2, 0, $i0);
            TCPIPAddress $r1 = new TCPIPAddress($r2, 0);
            ByteUtils.WriteDWord($r2, 0, $i1);
            return CreatePacket($r1, $r0, $r2, 0, 4, true, false, 1, 4);
        }
    }

    public static MTPPacket CreateStopDatagramListeningPacket(int $i0) throws  {
        byte[] $r1 = new byte[4];
        ByteUtils.WriteDWord($r1, 0, $i0);
        return CreatePacket(new TCPIPAddress($r1, 0), null, $r1, 0, 4, true, false, 1, 5);
    }

    public static MTPPacket ParsePacket(byte[] $r0, int $i0, int $i1) throws  {
        MTPPacket $r1 = MTPPacketPool.GetPacket();
        if (!$assertionsDisabled && $r1 == null) {
            throw new AssertionError();
        } else if ($r1 == null || $r1.InitPacket($r0, $i0, $i1)) {
            return $r1;
        } else {
            $r1.ReleasePacket();
            return null;
        }
    }

    public static void UseCheckSum(boolean $z0) throws  {
        ms_useCheckSum = $z0;
    }

    private static MTPPacket CreatePacket(IMCSConnectionAddress $r0, IMCSConnectionAddress $r1, byte[] $r2, int $i0, int $i1, boolean $z0, boolean $z1, int $i2, int $i3) throws  {
        int $i4 = GetAdressSize($r0);
        int $i5 = GetAdressSize($r1);
        if (!$assertionsDisabled && ($i4 <= 0 || $i5 <= 0 || $i1 > GetMaxDataSize())) {
            throw new AssertionError();
        } else if ($i4 <= 0) {
            return null;
        } else {
            if ($i5 <= 0) {
                return null;
            }
            if ($i1 > GetMaxDataSize()) {
                return null;
            }
            MTPPacket $r4 = MTPPacketPool.GetPacket();
            if (!$assertionsDisabled && $r4 == null) {
                throw new AssertionError();
            } else if ($r4 == null) {
                return $r4;
            } else {
                byte $b7;
                if ($z0) {
                    $b7 = (byte) 1;
                } else {
                    $b7 = (byte) 0;
                }
                $i2 = ByteUtils.WriteBits(ByteUtils.WriteBits(ByteUtils.WriteBits(0, 0, 1, $b7), 1, 3, $i2), 4, 4, $i3);
                if (ms_useCheckSum) {
                    $b7 = (byte) 1;
                } else {
                    $b7 = (byte) 0;
                }
                $i2 = ByteUtils.WriteBits($i2, 8, 1, $b7);
                if ($z1) {
                    $b7 = (byte) 1;
                } else {
                    $b7 = (byte) 0;
                }
                $i2 = ByteUtils.WriteBits($i2, 9, 1, $b7);
                $r4.m_isValid = false;
                int $i32 = ($i4 + 5) + $i5;
                $i3 = $i32;
                $r4.m_frameSize = ((ms_useCheckSum ? (byte) 2 : (byte) 0) + ($i32 + $i1)) + 1;
                $r4.m_frameOptions = $i2;
                $r4.m_payloadSize = $i1;
                $i32 = ($i4 + 5) + $i5;
                $i2 = $i32;
                $r4.m_payloadOffset = $i32;
                $r4.m_srcAddress = $r0;
                $r4.m_dstAddress = $r1;
                $r4.m_packet[0] = (byte) 30;
                ByteUtils.WriteWord($r4.m_packet, 1, $r4.m_frameSize);
                ByteUtils.WriteWord($r4.m_packet, 3, $r4.m_frameOptions);
                CopyAddressToMTP($r4.m_packet, 5, $r0);
                CopyAddressToMTP($r4.m_packet, $i4 + 5, $r1);
                if ($i1 > 0) {
                    System.arraycopy($r2, $i0, $r4.m_packet, $r4.m_payloadOffset, $i1);
                }
                if (ms_useCheckSum) {
                    ByteUtils.WriteWord($r4.m_packet, $r4.m_frameSize - 3, CalculateChecksum($r4.m_packet, 0, $r4.m_frameSize - 3));
                }
                $r4.m_packet[$r4.m_frameSize - 1] = (byte) 3;
                $r4.m_isValid = true;
                return $r4;
            }
        }
    }

    private static IMCSConnectionAddress CopyAddressFromMTP(byte[] $r0, int $i0) throws  {
        TCPIPAddress $r1 = null;
        byte[] $r3;
        switch ($r0[$i0]) {
            case (byte) 0:
                break;
            case (byte) 1:
                $r3 = new byte[4];
                System.arraycopy($r0, $i0 + 1, $r3, 0, 4);
                $r1 = new TCPIPAddress($r3, ByteUtils.ReadWord($r0, $i0 + 5));
                break;
            case (byte) 2:
                $r3 = new byte[16];
                System.arraycopy($r0, $i0 + 1, $r3, 0, 16);
                return new TCPIPAddress($r3, ByteUtils.ReadWord($r0, $i0 + 17));
            default:
                if ($assertionsDisabled) {
                    return null;
                }
                throw new AssertionError();
        }
        return $r1;
    }

    private static void CopyAddressToMTP(byte[] $r0, int $i0, IMCSConnectionAddress $r1) throws  {
        if ($r1 == null) {
            $r0[$i0] = (byte) 0;
        } else if (!$assertionsDisabled && !($r1 instanceof TCPIPAddress)) {
            throw new AssertionError();
        } else if ($r1 instanceof TCPIPAddress) {
            TCPIPAddress $r3 = (TCPIPAddress) $r1;
            if ($r3.getAddress() instanceof Inet4Address) {
                $r0[$i0] = (byte) 1;
                System.arraycopy($r3.getAddress().getAddress(), 0, $r0, $i0 + 1, 4);
                ByteUtils.WriteWord($r0, $i0 + 5, $r3.getPort());
            } else if ($r3.getAddress() instanceof Inet6Address) {
                $r0[$i0] = (byte) 2;
                System.arraycopy($r3.getAddress().getAddress(), 0, $r0, $i0 + 1, 16);
                ByteUtils.WriteWord($r0, $i0 + 17, $r3.getPort());
            } else if (!$assertionsDisabled) {
                throw new AssertionError();
            }
        }
    }

    private static int GetAdressSize(byte $b0) throws  {
        switch ($b0) {
            case (byte) 0:
                return 1;
            case (byte) 1:
                return 7;
            case (byte) 2:
                return 19;
            default:
                if ($assertionsDisabled) {
                    return 0;
                }
                throw new AssertionError();
        }
    }

    private static int GetAdressSize(IMCSConnectionAddress $r0) throws  {
        if ($r0 == null) {
            return 1;
        }
        if ($r0 instanceof TCPIPAddress) {
            TCPIPAddress $r1 = (TCPIPAddress) $r0;
            if ($r1.getAddress() instanceof Inet4Address) {
                return 7;
            }
            if ($r1.getAddress() instanceof Inet6Address) {
                return 19;
            }
            if ($assertionsDisabled) {
                return 0;
            }
            throw new AssertionError();
        } else if ($assertionsDisabled) {
            return 0;
        } else {
            throw new AssertionError();
        }
    }
}
