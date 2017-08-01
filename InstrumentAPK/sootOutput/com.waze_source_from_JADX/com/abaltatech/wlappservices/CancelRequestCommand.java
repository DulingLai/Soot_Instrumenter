package com.abaltatech.wlappservices;

import com.abaltatech.mcp.weblink.core.DataBuffer;

public class CancelRequestCommand extends WLServiceCommand {
    static final /* synthetic */ boolean $assertionsDisabled = (!CancelRequestCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 4;
    public static final short ID = (short) 100;
    private int m_requestID;

    public /* bridge */ /* synthetic */ String getReceiverID() throws  {
        return super.getReceiverID();
    }

    public /* bridge */ /* synthetic */ String getSenderID() throws  {
        return super.getSenderID();
    }

    public CancelRequestCommand(DataBuffer $r1) throws  {
        super($r1);
        boolean $z0 = isValid();
        if ($assertionsDisabled || $z0) {
            short $s0 = getCommandID();
            if (!$assertionsDisabled && $s0 != (short) 100) {
                throw new AssertionError();
            }
            return;
        }
        throw new AssertionError();
    }

    public CancelRequestCommand(String $r1, String $r2, int $i0) throws  {
        super((short) 100, 4, $r1, $r2);
        if (super.isValid()) {
            this.m_binaryCommandContainer.putInt(getPayloadOffset() + 0, $i0);
            this.m_requestID = $i0;
        }
    }

    public boolean isValid() throws  {
        boolean $z0 = super.isValid();
        if (!$z0) {
            return $z0;
        }
        int $i0 = getPayloadOffset();
        $z0 = $z0 && this.m_binaryCommandContainer.getSize() >= $i0 + 4;
        if (!$z0) {
            return $z0;
        }
        this.m_requestID = this.m_binaryCommandContainer.getInt($i0 + 0);
        return $z0;
    }

    public int getRequestID() throws  {
        return this.m_requestID;
    }
}
