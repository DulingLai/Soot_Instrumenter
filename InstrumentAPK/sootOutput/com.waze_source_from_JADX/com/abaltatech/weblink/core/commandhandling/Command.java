package com.abaltatech.weblink.core.commandhandling;

import com.abaltatech.weblink.core.DataBuffer;

public class Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!Command.class.desiredAssertionStatus());
    public static final int HEADER_SIZE = 8;
    protected static final byte MAGIC_BYTE1 = (byte) 87;
    protected static final byte MAGIC_BYTE2 = (byte) 76;
    protected DataBuffer m_binaryCommandContainer;

    public Command(DataBuffer $r1) throws  {
        this.m_binaryCommandContainer = $r1;
    }

    public Command(short $s0, int $i1) throws  {
        try {
            this.m_binaryCommandContainer = new DataBuffer($i1 + 8);
            this.m_binaryCommandContainer.putByte(0, (byte) 87);
            this.m_binaryCommandContainer.putByte(1, (byte) 76);
            this.m_binaryCommandContainer.putShort(2, $s0);
            this.m_binaryCommandContainer.putInt(4, $i1);
        } catch (Exception e) {
            this.m_binaryCommandContainer = null;
        }
    }

    public Command(short $s0, byte[] $r1, int $i1, int $i2) throws  {
        if ($assertionsDisabled || ($i1 >= 0 && $i2 >= 0 && $i1 + $i2 <= $r1.length)) {
            try {
                this.m_binaryCommandContainer = new DataBuffer($i2 + 8);
                this.m_binaryCommandContainer.putByte(0, (byte) 87);
                this.m_binaryCommandContainer.putByte(1, (byte) 76);
                this.m_binaryCommandContainer.putShort(2, $s0);
                this.m_binaryCommandContainer.putInt(4, $i2);
                System.arraycopy($r1, $i1, this.m_binaryCommandContainer.getData(), 8, $i2);
                return;
            } catch (Exception e) {
                this.m_binaryCommandContainer = null;
                return;
            }
        }
        throw new AssertionError();
    }

    public static final boolean isValidCommand(DataBuffer $r0) throws  {
        if ($r0.getSize() < 8) {
            return false;
        }
        return $r0.getByte(0) == (byte) 87 && $r0.getByte(1) == (byte) 76 && $r0.getInt(4) == $r0.getSize() - 8;
    }

    public boolean isValid() throws  {
        return isValidCommand(this.m_binaryCommandContainer);
    }

    public short getCommandID() throws  {
        if (isValid()) {
            return this.m_binaryCommandContainer.getShort(2);
        }
        return (short) -1;
    }

    public DataBuffer getRawCommandData() throws  {
        return this.m_binaryCommandContainer;
    }

    public static int findValidCommand(byte[] $r0, int $i0, int $i1) throws  {
        if ($r0 == null) {
            return -1;
        }
        if ($i1 <= 0) {
            return -1;
        }
        while ($i1 >= 8 && ($r0[$i0] != (byte) 87 || $r0[$i0 + 1] != (byte) 76)) {
            $i0++;
            $i1--;
        }
        return $i1 >= 8 ? $i0 : -1;
    }

    public static short getCommandID(DataBuffer $r0) throws  {
        if ($r0.getSize() >= 8) {
            return $r0.getShort(2);
        }
        return (short) -1;
    }

    public static int getPayloadSize(DataBuffer $r0) throws  {
        if ($r0.getSize() >= 8) {
            return $r0.getInt(4);
        }
        return -1;
    }
}
