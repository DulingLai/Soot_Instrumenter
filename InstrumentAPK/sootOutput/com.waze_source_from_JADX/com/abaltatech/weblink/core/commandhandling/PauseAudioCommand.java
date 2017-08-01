package com.abaltatech.weblink.core.commandhandling;

import com.abaltatech.weblink.core.DataBuffer;

public class PauseAudioCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!PauseAudioCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 4;
    public static final short ID = (short) 70;

    public PauseAudioCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 70) {
            throw new AssertionError();
        }
    }

    public PauseAudioCommand(boolean $z0) throws  {
        super((short) 70, 4);
        if (isValid()) {
            this.m_binaryCommandContainer.putInt(8, $z0 ? (byte) 1 : (byte) 0);
        }
    }

    public boolean getPause() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 70 && this.m_binaryCommandContainer.getSize() == 12)) {
            return this.m_binaryCommandContainer.getInt(8) != 0;
        } else {
            throw new AssertionError();
        }
    }
}
