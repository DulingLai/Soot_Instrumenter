package com.abaltatech.mcp.weblink.core.commandhandling;

import com.abaltatech.mcp.weblink.core.DataBuffer;

public class ClientFeaturesCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!ClientFeaturesCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 9;
    public static final int HAS_KEYBORD = 1;
    public static final short ID = (short) 75;

    public ClientFeaturesCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 75) {
            throw new AssertionError();
        }
    }

    public ClientFeaturesCommand(int $i0, String $r1) throws  {
        super((short) 75, $r1.length() + 9);
        if (isValid()) {
            this.m_binaryCommandContainer.putInt(8, $i0);
            this.m_binaryCommandContainer.putInt(12, $r1.length());
            byte[] $r3 = $r1.getBytes();
            System.arraycopy($r3, 0, this.m_binaryCommandContainer.getData(), 16, $r3.length);
        }
    }

    public int getFeatures() throws  {
        this.m_binaryCommandContainer.getSize();
        return this.m_binaryCommandContainer.getInt(8);
    }

    public String getFeaturesString() throws  {
        int $i0 = this.m_binaryCommandContainer.getSize();
        if ($i0 <= 17) {
            return "";
        }
        int $i1 = this.m_binaryCommandContainer.getInt(12);
        if (($i1 + 8) + 9 == $i0) {
            return new String(this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + 8) + 8, $i1);
        }
        return "";
    }
}
