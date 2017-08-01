package com.abaltatech.wlappservices;

import com.abaltatech.mcp.weblink.core.DataBuffer;
import com.abaltatech.mcp.weblink.core.commandhandling.Command;

class WLServiceCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!WLServiceCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 2;
    private int m_payloadOffset = 0;
    private String m_receiverID = null;
    private String m_senderID = null;

    public WLServiceCommand(DataBuffer $r1) throws  {
        super($r1);
        boolean $z0 = internalIsValid();
        if (!$assertionsDisabled && !$z0) {
            throw new AssertionError();
        }
    }

    public boolean isValid() throws  {
        return internalIsValid();
    }

    private boolean internalIsValid() throws  {
        boolean $z0 = super.isValid() && this.m_binaryCommandContainer.getSize() > 10;
        if (!$z0) {
            return $z0;
        }
        short $s0 = this.m_binaryCommandContainer.getByte(8) & (short) 255;
        short $s2 = this.m_binaryCommandContainer.getByte(9) & (short) 255;
        if (this.m_binaryCommandContainer.getSize() > ($s0 + 10) + $s2) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        if (!$z0) {
            return $z0;
        }
        String $r2 = new String(this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + 8) + 2, $s0);
        String $r1 = new String(this.m_binaryCommandContainer.getData(), ((this.m_binaryCommandContainer.getPos() + 8) + 2) + $s0, $s2);
        int $i1 = ($s0 + 10) + $s2;
        this.m_senderID = $r2;
        this.m_receiverID = $r1;
        this.m_payloadOffset = $i1;
        return $z0;
    }

    public WLServiceCommand(short $s0, int $i1, String $r1, String $r2) throws  {
        super($s0, getNeededSize($r1, $r2) + $i1);
        byte[] $r3 = $r1.getBytes();
        byte[] $r4 = $r2.getBytes();
        int $i2 = $r3.length;
        $i1 = $r4.length;
        this.m_binaryCommandContainer.putByte(8, (byte) $i2);
        this.m_binaryCommandContainer.putByte(9, (byte) $i1);
        System.arraycopy($r3, 0, this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + 8) + 2, $i2);
        System.arraycopy($r4, 0, this.m_binaryCommandContainer.getData(), ((this.m_binaryCommandContainer.getPos() + 8) + 2) + $i2, $i1);
        this.m_payloadOffset = ($i2 + 10) + $i1;
        this.m_senderID = $r1;
        this.m_receiverID = $r2;
    }

    private static int getNeededSize(String $r0, String $r1) throws  {
        byte[] $r2 = $r0.getBytes();
        if ($r2.length > 255) {
            throw new IllegalArgumentException("callerID is too long");
        } else if ($r2.length < 5) {
            throw new IllegalArgumentException("callerID is too short");
        } else {
            int $i0 = 2 + $r2.length;
            $r2 = $r1.getBytes();
            if ($r2.length > 255) {
                throw new IllegalArgumentException("receiverID is too long");
            } else if ($r2.length >= 5) {
                return $i0 + $r2.length;
            } else {
                throw new IllegalArgumentException("receiverID is too short");
            }
        }
    }

    protected int getPayloadOffset() throws  {
        return this.m_payloadOffset;
    }

    public String getReceiverID() throws  {
        return this.m_receiverID;
    }

    public String getSenderID() throws  {
        return this.m_senderID;
    }
}
