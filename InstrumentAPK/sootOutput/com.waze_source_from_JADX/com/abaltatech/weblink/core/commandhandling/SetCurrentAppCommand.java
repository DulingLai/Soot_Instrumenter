package com.abaltatech.weblink.core.commandhandling;

import com.abaltatech.weblink.core.DataBuffer;

public class SetCurrentAppCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!SetCurrentAppCommand.class.desiredAssertionStatus());
    public static final short ID = (short) 66;

    public SetCurrentAppCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 66) {
            throw new AssertionError();
        }
    }

    public SetCurrentAppCommand(String $r1, String $r2) throws  {
        int $i1;
        byte[] $r3 = null;
        int $i0 = (($r1 != null ? $r1.getBytes().length : 0) + 4) + 4;
        if ($r2 != null) {
            $i1 = $r2.getBytes().length;
        } else {
            $i1 = 0;
        }
        super((short) 66, $i1 + $i0);
        if (isValid()) {
            byte[] $r4;
            if ($r1 == null) {
                $r4 = null;
            } else {
                $r4 = $r1.getBytes();
            }
            if ($r4 == null) {
                $i1 = 0;
            } else {
                $i1 = $r4.length;
            }
            this.m_binaryCommandContainer.putInt(8, $i1);
            if ($i1 > 0) {
                System.arraycopy($r4, 0, this.m_binaryCommandContainer.getData(), 12, $i1);
            }
            if ($r2 != null) {
                $r3 = $r2.getBytes();
            }
            if ($r3 == null) {
                $i0 = 0;
            } else {
                $i0 = $r3.length;
            }
            this.m_binaryCommandContainer.putInt($i1 + 12, $i0);
            if ($i0 > 0) {
                System.arraycopy($r3, 0, this.m_binaryCommandContainer.getData(), ($i1 + 12) + 4, $i0);
            }
        }
    }

    public String getAppID() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 66 && this.m_binaryCommandContainer.getSize() >= 16)) {
            int $i1 = this.m_binaryCommandContainer.getInt(8);
            if ($i1 > 0) {
                return new String(this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + 8) + 4, $i1);
            }
            return null;
        }
        throw new AssertionError();
    }

    public String getAppParams() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 66 && this.m_binaryCommandContainer.getSize() >= 16)) {
            int $i2 = this.m_binaryCommandContainer.getInt(8);
            int $i1 = this.m_binaryCommandContainer.getInt($i2 + 12);
            if ($i1 > 0) {
                return new String(this.m_binaryCommandContainer.getData(), (((this.m_binaryCommandContainer.getPos() + 8) + 4) + $i2) + 4, $i1);
            }
            return null;
        }
        throw new AssertionError();
    }
}
