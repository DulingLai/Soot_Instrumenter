package com.abaltatech.weblink.core.commandhandling;

import com.abaltatech.weblink.core.DataBuffer;

public class WaitIndicatorCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!WaitIndicatorCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 1;
    public static final short ID = (short) 21;

    public WaitIndicatorCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 21) {
            throw new AssertionError();
        }
    }

    public WaitIndicatorCommand(boolean $z0) throws  {
        boolean $z2 = true;
        super((short) 21, 1);
        if (isValid()) {
            DataBuffer $r1 = this.m_binaryCommandContainer;
            if (!$z0) {
                $z2 = false;
            }
            $r1.putByte(8, (byte) $z2);
        }
    }

    public boolean getShowWaitIndicator() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 21 && this.m_binaryCommandContainer.getSize() == 9)) {
            return this.m_binaryCommandContainer.getByte(8) != (byte) 0;
        } else {
            throw new AssertionError();
        }
    }
}
