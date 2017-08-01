package com.abaltatech.weblink.core.commandhandling;

import com.abaltatech.weblink.core.DataBuffer;

public class SetFpsCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!SetFpsCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 1;
    public static final short ID = (short) 71;

    public SetFpsCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 71) {
            throw new AssertionError();
        }
    }

    public SetFpsCommand(byte $b0) throws  {
        super((short) 71, 1);
        if (isValid()) {
            this.m_binaryCommandContainer.putByte(8, $b0);
        }
    }

    public byte getFps() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 71 && this.m_binaryCommandContainer.getSize() == 9)) {
            return this.m_binaryCommandContainer.getByte(8);
        }
        throw new AssertionError();
    }
}
