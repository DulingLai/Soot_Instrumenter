package com.abaltatech.weblink.core.commandhandling;

import com.abaltatech.weblink.core.DataBuffer;

public class BrowserCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!BrowserCommand.class.desiredAssertionStatus());
    public static final byte ACT_BACK = (byte) 0;
    public static final byte ACT_FORWARD = (byte) 2;
    public static final byte ACT_GOTO_ADDRESS = (byte) 3;
    public static final byte ACT_HOME = (byte) 1;
    private static final int CMD_FIXED_SIZE = 1;
    public static final short ID = (short) 18;

    public BrowserCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 18) {
            throw new AssertionError();
        }
    }

    public BrowserCommand(byte $b0, String $r1) throws  {
        super((short) 18, ($r1 != null ? $r1.getBytes().length : 0) + 1);
        if (isValid()) {
            byte[] $r2;
            int $i1;
            if ($r1 == null) {
                $r2 = null;
            } else {
                $r2 = $r1.getBytes();
            }
            if ($r2 == null) {
                $i1 = 0;
            } else {
                $i1 = $r2.length;
            }
            this.m_binaryCommandContainer.putByte(8, $b0);
            if ($i1 > 0) {
                System.arraycopy($r2, 0, this.m_binaryCommandContainer.getData(), 9, $i1);
            }
        }
    }

    public byte getAction() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 18 && this.m_binaryCommandContainer.getSize() >= 9)) {
            return this.m_binaryCommandContainer.getByte(8);
        }
        throw new AssertionError();
    }

    public String getParams() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 18 && this.m_binaryCommandContainer.getSize() >= 9)) {
            int $i0 = this.m_binaryCommandContainer.getSize() - 9;
            if ($i0 > 0) {
                return new String(this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + 8) + 1, $i0);
            }
            return null;
        }
        throw new AssertionError();
    }
}
