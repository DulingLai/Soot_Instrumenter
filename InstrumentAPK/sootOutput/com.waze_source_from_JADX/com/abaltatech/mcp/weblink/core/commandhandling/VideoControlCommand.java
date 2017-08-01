package com.abaltatech.mcp.weblink.core.commandhandling;

import com.abaltatech.mcp.weblink.core.DataBuffer;

public class VideoControlCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!VideoControlCommand.class.desiredAssertionStatus());
    public static final int ACTION_START = 0;
    public static final int ACTION_STOP = 1;
    private static final int CMD_FIXED_SIZE = 4;
    public static final short ID = (short) 33;

    public VideoControlCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 33) {
            throw new AssertionError();
        }
    }

    public VideoControlCommand(int $i0) throws  {
        super((short) 33, 4);
        if (isValid()) {
            this.m_binaryCommandContainer.putInt(8, $i0);
        }
    }

    public int getAction() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 33 && this.m_binaryCommandContainer.getSize() >= 12)) {
            return this.m_binaryCommandContainer.getInt(8);
        }
        throw new AssertionError();
    }
}
