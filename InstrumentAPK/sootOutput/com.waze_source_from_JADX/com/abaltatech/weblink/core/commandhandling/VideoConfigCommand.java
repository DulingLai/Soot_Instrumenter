package com.abaltatech.weblink.core.commandhandling;

import com.abaltatech.weblink.core.DataBuffer;

public class VideoConfigCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!VideoConfigCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 20;
    public static final short ID = (short) 32;

    public VideoConfigCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 32) {
            throw new AssertionError();
        }
    }

    public VideoConfigCommand(int $i0, int $i1, int $i2, int $i3, int $i4, String $r1) throws  {
        super((short) 32, ($r1 != null ? $r1.getBytes().length : 0) + 20);
        if (isValid()) {
            byte[] $r2;
            int $i5;
            if ($r1 == null) {
                $r2 = null;
            } else {
                $r2 = $r1.getBytes();
            }
            if ($r2 == null) {
                $i5 = 0;
            } else {
                $i5 = $r2.length;
            }
            this.m_binaryCommandContainer.putInt(8, $i0);
            this.m_binaryCommandContainer.putInt(12, $i1);
            this.m_binaryCommandContainer.putInt(16, $i2);
            this.m_binaryCommandContainer.putInt(20, $i3);
            this.m_binaryCommandContainer.putInt(24, $i4);
            if ($i5 > 0) {
                System.arraycopy($r2, 0, this.m_binaryCommandContainer.getData(), 28, $i5);
            }
        }
    }

    public int getSourceWidth() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 32 && this.m_binaryCommandContainer.getSize() >= 28)) {
            return this.m_binaryCommandContainer.getInt(8);
        }
        throw new AssertionError();
    }

    public int getSourceHeight() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 32 && this.m_binaryCommandContainer.getSize() >= 28)) {
            return this.m_binaryCommandContainer.getInt(12);
        }
        throw new AssertionError();
    }

    public int getClientWidth() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 32 && this.m_binaryCommandContainer.getSize() >= 28)) {
            return this.m_binaryCommandContainer.getInt(16);
        }
        throw new AssertionError();
    }

    public int getClientHeight() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 32 && this.m_binaryCommandContainer.getSize() >= 28)) {
            return this.m_binaryCommandContainer.getInt(20);
        }
        throw new AssertionError();
    }

    public int getFrameEncoding() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 32 && this.m_binaryCommandContainer.getSize() >= 28)) {
            return this.m_binaryCommandContainer.getInt(24);
        }
        throw new AssertionError();
    }

    public String getEncoderParams() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 32 && this.m_binaryCommandContainer.getSize() >= 28)) {
            int $i0 = this.m_binaryCommandContainer.getSize() - 28;
            if ($i0 > 0) {
                return new String(this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + 8) + 20, $i0);
            }
            return null;
        }
        throw new AssertionError();
    }
}
