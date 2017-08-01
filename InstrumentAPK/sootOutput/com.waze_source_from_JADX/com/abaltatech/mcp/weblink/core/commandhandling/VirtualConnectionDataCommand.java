package com.abaltatech.mcp.weblink.core.commandhandling;

import com.abaltatech.mcp.weblink.core.DataBuffer;
import java.util.Arrays;

public class VirtualConnectionDataCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!VirtualConnectionDataCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 12;
    public static final short ID = (short) 81;

    public VirtualConnectionDataCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 81) {
            throw new AssertionError();
        }
    }

    public VirtualConnectionDataCommand(byte[] $r1, byte[] $r2, byte[] $r3) throws  {
        super((short) 81, (($r1.length + 12) + $r2.length) + $r3.length);
        if (isValid()) {
            int $i1 = $r1.length;
            int $i2 = $r2.length;
            int $i0 = $r3.length;
            this.m_binaryCommandContainer.putInt(8, $i1);
            this.m_binaryCommandContainer.putInt(12, $i2);
            this.m_binaryCommandContainer.putInt(16, $i0);
            if ($i1 > 0) {
                System.arraycopy($r1, 0, this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + 8) + 12, $i1);
            }
            if ($i2 > 0) {
                System.arraycopy($r2, 0, this.m_binaryCommandContainer.getData(), ((this.m_binaryCommandContainer.getPos() + 8) + 12) + $i1, $i2);
            }
            if ($i0 > 0) {
                System.arraycopy($r3, 0, this.m_binaryCommandContainer.getData(), (((this.m_binaryCommandContainer.getPos() + 8) + 12) + $i1) + $i2, $i0);
            }
        }
    }

    public byte[] getFromAddress() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 81 && this.m_binaryCommandContainer.getSize() > 20)) {
            int $i0 = (this.m_binaryCommandContainer.getPos() + 8) + 12;
            return Arrays.copyOfRange(this.m_binaryCommandContainer.getData(), $i0, $i0 + this.m_binaryCommandContainer.getInt(8));
        }
        throw new AssertionError();
    }

    public byte[] getToAddress() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 81 && this.m_binaryCommandContainer.getSize() > 20)) {
            int $i0 = this.m_binaryCommandContainer.getInt(8);
            $i0 = ((this.m_binaryCommandContainer.getPos() + 8) + 12) + $i0;
            return Arrays.copyOfRange(this.m_binaryCommandContainer.getData(), $i0, $i0 + this.m_binaryCommandContainer.getInt(12));
        }
        throw new AssertionError();
    }

    public byte[] getData() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 81 && this.m_binaryCommandContainer.getSize() > 20)) {
            int $i3 = this.m_binaryCommandContainer.getInt(8);
            int $i0 = this.m_binaryCommandContainer.getInt(12);
            $i0 = (((this.m_binaryCommandContainer.getPos() + 8) + 12) + $i3) + $i0;
            return Arrays.copyOfRange(this.m_binaryCommandContainer.getData(), $i0, $i0 + this.m_binaryCommandContainer.getInt(16));
        }
        throw new AssertionError();
    }
}
