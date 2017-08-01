package com.abaltatech.weblink.core.commandhandling;

import com.abaltatech.weblink.core.DataBuffer;

public class SyncSessionTimeCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!SyncSessionTimeCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 16;
    public static final short ID = (short) 73;

    public SyncSessionTimeCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 73) {
            throw new AssertionError();
        }
    }

    public SyncSessionTimeCommand(long $l0, long $l1) throws  {
        super((short) 73, 16);
        if (isValid()) {
            this.m_binaryCommandContainer.putLong(8, $l0);
            this.m_binaryCommandContainer.putLong(16, $l1);
        }
    }

    public long getCurrentClientTime() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 73 && this.m_binaryCommandContainer.getSize() == 24)) {
            return this.m_binaryCommandContainer.getLong(8);
        }
        throw new AssertionError();
    }

    public long getCurrentServerTime() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 73 && this.m_binaryCommandContainer.getSize() == 24)) {
            return this.m_binaryCommandContainer.getLong(16);
        }
        throw new AssertionError();
    }
}
