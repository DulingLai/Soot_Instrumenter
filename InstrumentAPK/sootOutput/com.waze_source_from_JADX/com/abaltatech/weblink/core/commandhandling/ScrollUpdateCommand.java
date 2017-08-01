package com.abaltatech.weblink.core.commandhandling;

import com.abaltatech.weblink.core.DataBuffer;

public class ScrollUpdateCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!ScrollUpdateCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 8;
    public static final short ID = (short) 64;

    public ScrollUpdateCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 64) {
            throw new AssertionError();
        }
    }

    public ScrollUpdateCommand(int $i0, int $i1) throws  {
        super((short) 64, 8);
        if (isValid()) {
            this.m_binaryCommandContainer.putInt(8, $i0);
            this.m_binaryCommandContainer.putInt(12, $i1);
        }
    }

    public int getHorzOffset() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 64 && this.m_binaryCommandContainer.getSize() == 16)) {
            return this.m_binaryCommandContainer.getInt(8);
        }
        throw new AssertionError();
    }

    public int GetVertOffset() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 64 && this.m_binaryCommandContainer.getSize() == 16)) {
            return this.m_binaryCommandContainer.getInt(12);
        }
        throw new AssertionError();
    }
}
