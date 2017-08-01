package com.abaltatech.wlappservices;

import android.annotation.SuppressLint;
import com.abaltatech.mcp.weblink.core.DataBuffer;

class FindServiceByProtocolCommand extends WLServiceCommand {
    static final /* synthetic */ boolean $assertionsDisabled = (!FindServiceByProtocolCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 6;
    public static final short ID = (short) 115;
    private String m_protocolName = null;
    private int m_searchID = 0;
    private boolean m_selectDefault = false;

    @SuppressLint({"Assert"})
    public FindServiceByProtocolCommand(DataBuffer $r1) throws  {
        boolean $z0 = true;
        super($r1);
        boolean $z1 = isValid();
        if ($assertionsDisabled || $z1) {
            short $s0 = getCommandID();
            if (!$assertionsDisabled && $s0 != ID) {
                throw new AssertionError();
            } else if ($z1 && $s0 == ID) {
                int $i1 = getPayloadOffset();
                $s0 = this.m_binaryCommandContainer.getByte($i1) & (short) 255;
                if (this.m_binaryCommandContainer.getSize() >= ($i1 + 6) + $s0) {
                    this.m_protocolName = new String(this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + $i1) + 6, $s0);
                    this.m_searchID = this.m_binaryCommandContainer.getInt($i1 + 1);
                    if (this.m_binaryCommandContainer.getInt($i1 + 5) == 0) {
                        $z0 = false;
                    }
                    this.m_selectDefault = $z0;
                    return;
                }
                return;
            } else {
                return;
            }
        }
        throw new AssertionError();
    }

    public boolean isValid() throws  {
        boolean $z0 = super.isValid();
        int $i1 = getPayloadOffset();
        $z0 = $z0 && this.m_binaryCommandContainer.getSize() > $i1 + 6;
        if (!$z0) {
            return $z0;
        }
        if (this.m_binaryCommandContainer.getSize() >= ($i1 + 6) + (this.m_binaryCommandContainer.getByte($i1) & (short) 255)) {
            return true;
        }
        return false;
    }

    public FindServiceByProtocolCommand(String $r1, String $r2, String $r3, int $i0, boolean $z0) throws  {
        super(ID, getNeededLength($r3), $r1, $r2);
        if (super.isValid()) {
            byte[] $r4 = $r3.getBytes();
            int $i1 = $r4.length;
            int $i3 = getPayloadOffset();
            this.m_binaryCommandContainer.putByte($i3, (byte) $i1);
            this.m_binaryCommandContainer.putInt($i3 + 1, $i0);
            this.m_binaryCommandContainer.putInt($i3 + 5, $z0 ? (byte) 1 : (byte) 0);
            System.arraycopy($r4, 0, this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + $i3) + 6, $i1);
            this.m_protocolName = $r3;
            this.m_searchID = $i0;
            this.m_selectDefault = $z0;
        }
    }

    private static int getNeededLength(String $r0) throws  {
        byte[] $r1 = $r0.getBytes();
        if ($r1.length > 255) {
            throw new IllegalArgumentException("serviceName is too long");
        } else if ($r1.length >= 5) {
            return 6 + $r1.length;
        } else {
            throw new IllegalArgumentException("serviceName is too short");
        }
    }

    public String getProtocolName() throws  {
        return this.m_protocolName;
    }

    public int getSearchID() throws  {
        return this.m_searchID;
    }

    public boolean getSelectDefault() throws  {
        return this.m_selectDefault;
    }
}
