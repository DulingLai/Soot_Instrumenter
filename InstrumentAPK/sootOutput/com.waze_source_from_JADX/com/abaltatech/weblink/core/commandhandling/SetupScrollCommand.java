package com.abaltatech.weblink.core.commandhandling;

import com.abaltatech.weblink.core.DataBuffer;

public class SetupScrollCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!SetupScrollCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 8;
    public static final short ID = (short) 64;

    public SetupScrollCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 64) {
            throw new AssertionError();
        }
    }

    public SetupScrollCommand(int $i0, int $i1) throws  {
        super((short) 64, 8);
        if (isValid()) {
            this.m_binaryCommandContainer.putInt(8, $i1);
            this.m_binaryCommandContainer.putInt(12, $i0);
        }
    }

    public int getDocumentWidth() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 64 && this.m_binaryCommandContainer.getSize() == 16)) {
            return this.m_binaryCommandContainer.getInt(8);
        }
        throw new AssertionError();
    }

    public int getDocumentHeight() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 64 && this.m_binaryCommandContainer.getSize() == 16)) {
            return this.m_binaryCommandContainer.getInt(12);
        }
        throw new AssertionError();
    }
}
