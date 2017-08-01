package com.abaltatech.mcp.weblink.core.commandhandling;

import com.abaltatech.mcp.weblink.core.DataBuffer;

public class KeyboardCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!KeyboardCommand.class.desiredAssertionStatus());
    public static final int ACT_KEY_DOWN = 2;
    public static final int ACT_KEY_UP = 1;
    private static final int CMD_FIXED_SIZE = 6;
    public static final short ID = (short) 17;

    public KeyboardCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 17) {
            throw new AssertionError();
        }
    }

    public KeyboardCommand(short $s0, int $i1) throws  {
        super((short) 17, 6);
        if (isValid()) {
            this.m_binaryCommandContainer.putShort(8, $s0);
            this.m_binaryCommandContainer.putInt(10, $i1);
        }
    }

    public short getVirtualKey() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 17 && this.m_binaryCommandContainer.getSize() == 14)) {
            return this.m_binaryCommandContainer.getShort(8);
        }
        throw new AssertionError();
    }

    public int getActionType() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 17 && this.m_binaryCommandContainer.getSize() == 14)) {
            return this.m_binaryCommandContainer.getInt(10);
        }
        throw new AssertionError();
    }
}
