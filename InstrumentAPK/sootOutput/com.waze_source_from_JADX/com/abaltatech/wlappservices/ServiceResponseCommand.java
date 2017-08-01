package com.abaltatech.wlappservices;

import com.abaltatech.mcp.weblink.core.DataBuffer;

public class ServiceResponseCommand extends WLServiceCommand {
    static final /* synthetic */ boolean $assertionsDisabled = (!ServiceResponseCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 9;
    public static final short ID = (short) 99;
    private EServiceErrorCode m_code;
    private boolean m_isValid = false;
    private int m_requestID;
    private byte[] m_responseBody;

    public /* bridge */ /* synthetic */ String getReceiverID() throws  {
        return super.getReceiverID();
    }

    public /* bridge */ /* synthetic */ String getSenderID() throws  {
        return super.getSenderID();
    }

    public ServiceResponseCommand(DataBuffer $r1) throws  {
        super($r1);
        boolean $z0 = isValid();
        if ($assertionsDisabled || $z0) {
            short $s0 = getCommandID();
            if (!$assertionsDisabled && $s0 != (short) 99) {
                throw new AssertionError();
            }
            return;
        }
        throw new AssertionError();
    }

    public ServiceResponseCommand(String $r1, String $r2, int $i0, byte[] $r3, EServiceErrorCode $r4) throws  {
        super((short) 99, getNeededLength($r3.length), $r1, $r2);
        if (super.isValid()) {
            int $i1 = $r3.length;
            int $i2 = getPayloadOffset();
            this.m_binaryCommandContainer.putInt($i2 + 0, $i0);
            this.m_binaryCommandContainer.putInt($i2 + 4, $i1);
            this.m_binaryCommandContainer.putByte($i2 + 8, EServiceErrorCode.toByte($r4));
            System.arraycopy($r3, 0, this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + $i2) + 9, $i1);
            this.m_requestID = $i0;
            this.m_responseBody = $r3;
            this.m_code = $r4;
        }
    }

    private static int getNeededLength(int $i0) throws  {
        return $i0 + 9;
    }

    public boolean isValid() throws  {
        boolean $z0 = this.m_isValid;
        if ($z0) {
            return $z0;
        }
        $z0 = super.isValid();
        int $i0 = getPayloadOffset();
        $z0 = $z0 && this.m_binaryCommandContainer.getSize() >= $i0 + 9;
        if ($z0) {
            this.m_requestID = this.m_binaryCommandContainer.getInt($i0 + 0);
            int $i1 = this.m_binaryCommandContainer.getInt($i0 + 4);
            byte $b3 = this.m_binaryCommandContainer.getByte($i0 + 8);
            if (this.m_binaryCommandContainer.getSize() >= ($i0 + 9) + $i1) {
                $z0 = true;
            } else {
                $z0 = false;
            }
            $z0 = $z0 && $i1 >= 0;
            if ($z0) {
                this.m_responseBody = new byte[$i1];
                System.arraycopy(this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + $i0) + 9, this.m_responseBody, 0, $i1);
                this.m_code = EServiceErrorCode.valueOf($b3);
            }
        }
        this.m_isValid = $z0;
        return $z0;
    }

    public int getRequestID() throws  {
        return this.m_requestID;
    }

    public byte[] getResponseBody() throws  {
        return this.m_responseBody;
    }

    public EServiceErrorCode getErrorCode() throws  {
        return this.m_code;
    }
}
