package com.abaltatech.wlappservices;

import android.annotation.SuppressLint;
import com.abaltatech.mcp.weblink.core.DataBuffer;

class FindServiceCommand extends WLServiceCommand {
    static final /* synthetic */ boolean $assertionsDisabled = (!FindServiceCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 5;
    public static final short ID = (short) 96;
    private int m_searchID = 0;
    private String m_serviceName = null;

    @SuppressLint({"Assert"})
    public FindServiceCommand(DataBuffer $r1) throws  {
        boolean $z0 = false;
        super($r1);
        boolean $z1 = isValid();
        if ($assertionsDisabled || $z1) {
            short $s0 = getCommandID();
            if (!$assertionsDisabled && $s0 != (short) 96) {
                throw new AssertionError();
            } else if ($z1 && $s0 == (short) 96) {
                int $i1 = getPayloadOffset();
                $s0 = this.m_binaryCommandContainer.getByte($i1) & (short) 255;
                if (this.m_binaryCommandContainer.getSize() >= ($i1 + 5) + $s0) {
                    $z0 = true;
                }
                if ($z0) {
                    this.m_serviceName = new String(this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + $i1) + 5, $s0);
                    this.m_searchID = this.m_binaryCommandContainer.getInt($i1 + 1);
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
        $z0 = $z0 && this.m_binaryCommandContainer.getSize() > $i1 + 5;
        if (!$z0) {
            return $z0;
        }
        if (this.m_binaryCommandContainer.getSize() >= ($i1 + 5) + (this.m_binaryCommandContainer.getByte($i1) & (short) 255)) {
            return true;
        }
        return false;
    }

    public FindServiceCommand(String $r1, String $r2, String $r3, int $i0) throws  {
        super((short) 96, getNeededLength($r3), $r1, $r2);
        if (super.isValid()) {
            byte[] $r4 = $r3.getBytes();
            int $i1 = $r4.length;
            int $i2 = getPayloadOffset();
            this.m_binaryCommandContainer.putByte($i2, (byte) $i1);
            this.m_binaryCommandContainer.putInt($i2 + 1, $i0);
            System.arraycopy($r4, 0, this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + $i2) + 5, $i1);
            this.m_serviceName = $r3;
            this.m_searchID = $i0;
        }
    }

    private static int getNeededLength(String $r0) throws  {
        byte[] $r1 = $r0.getBytes();
        if ($r1.length > 255) {
            throw new IllegalArgumentException("serviceName is too long");
        } else if ($r1.length >= 5) {
            return 5 + $r1.length;
        } else {
            throw new IllegalArgumentException("serviceName is too short");
        }
    }

    public String getServiceName() throws  {
        return this.m_serviceName;
    }

    public int getSearchID() throws  {
        return this.m_searchID;
    }
}
