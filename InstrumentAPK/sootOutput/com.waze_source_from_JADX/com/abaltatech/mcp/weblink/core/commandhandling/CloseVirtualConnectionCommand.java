package com.abaltatech.mcp.weblink.core.commandhandling;

import com.abaltatech.mcp.weblink.core.DataBuffer;
import java.util.Arrays;

public class CloseVirtualConnectionCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!CloseVirtualConnectionCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 8;
    public static final short ID = (short) 80;

    public CloseVirtualConnectionCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 80) {
            throw new AssertionError();
        }
    }

    public CloseVirtualConnectionCommand(byte[] $r1, byte[] $r2) throws  {
        super((short) 80, ($r1.length + 8) + $r2.length);
        if (isValid()) {
            this.m_binaryCommandContainer.putInt(8, $r1.length);
            this.m_binaryCommandContainer.putInt(12, $r2.length);
            if ($r1.length > 0) {
                System.arraycopy($r1, 0, this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + 8) + 8, $r1.length);
            }
            if ($r2.length > 0) {
                System.arraycopy($r2, 0, this.m_binaryCommandContainer.getData(), ((this.m_binaryCommandContainer.getPos() + 8) + 8) + $r1.length, $r2.length);
            }
        }
    }

    public byte[] getFromAddress() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 80 && this.m_binaryCommandContainer.getSize() > 16)) {
            int $i0 = (this.m_binaryCommandContainer.getPos() + 8) + 8;
            return Arrays.copyOfRange(this.m_binaryCommandContainer.getData(), $i0, $i0 + this.m_binaryCommandContainer.getInt(8));
        }
        throw new AssertionError();
    }

    public byte[] getToAddress() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 80 && this.m_binaryCommandContainer.getSize() > 16)) {
            int $i0 = this.m_binaryCommandContainer.getInt(8);
            $i0 = ((this.m_binaryCommandContainer.getPos() + 8) + 8) + $i0;
            return Arrays.copyOfRange(this.m_binaryCommandContainer.getData(), $i0, $i0 + this.m_binaryCommandContainer.getInt(12));
        }
        throw new AssertionError();
    }
}
