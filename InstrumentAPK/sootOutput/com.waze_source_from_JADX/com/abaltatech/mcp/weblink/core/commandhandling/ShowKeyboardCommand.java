package com.abaltatech.mcp.weblink.core.commandhandling;

import com.abaltatech.mcp.weblink.core.DataBuffer;

public class ShowKeyboardCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!ShowKeyboardCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 2;
    public static final short ID = (short) 19;
    public static final short KT_EMAIL = (short) 3;
    public static final short KT_NUMBER = (short) 1;
    public static final short KT_PHONE = (short) 2;
    public static final short KT_TEXT = (short) 0;

    public ShowKeyboardCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 19) {
            throw new AssertionError();
        }
    }

    public ShowKeyboardCommand(short $s0) throws  {
        super((short) 19, 2);
        if (isValid()) {
            this.m_binaryCommandContainer.putShort(8, $s0);
        }
    }

    public short getKeyboardType() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 19 && this.m_binaryCommandContainer.getSize() == 10)) {
            return this.m_binaryCommandContainer.getShort(8);
        }
        throw new AssertionError();
    }
}
