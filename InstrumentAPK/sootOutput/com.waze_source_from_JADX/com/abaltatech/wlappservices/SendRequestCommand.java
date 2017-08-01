package com.abaltatech.wlappservices;

import com.abaltatech.mcp.weblink.core.DataBuffer;

public class SendRequestCommand extends WLServiceCommand {
    static final /* synthetic */ boolean $assertionsDisabled = (!SendRequestCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 18;
    public static final short ID = (short) 98;
    private boolean m_allowExecuteInForeground;
    private int m_descriptorID;
    private boolean m_isValid = false;
    private byte[] m_requestBody;
    private int m_requestID;
    private ERequestMethod m_requestMethod;
    private byte[] m_resourcePath;

    public /* bridge */ /* synthetic */ String getReceiverID() throws  {
        return super.getReceiverID();
    }

    public /* bridge */ /* synthetic */ String getSenderID() throws  {
        return super.getSenderID();
    }

    public SendRequestCommand(DataBuffer $r1) throws  {
        super($r1);
        boolean $z0 = isValid();
        if ($assertionsDisabled || $z0) {
            short $s0 = getCommandID();
            if (!$assertionsDisabled && $s0 != (short) 98) {
                throw new AssertionError();
            }
            return;
        }
        throw new AssertionError();
    }

    public SendRequestCommand(String $r1, String $r2, int $i0, int $i1, boolean $z0, byte[] $r3, ERequestMethod $r4, byte[] $r5) throws  {
        byte[] bArr = $r3;
        $r3 = bArr;
        super((short) 98, getNeededLength(bArr.length, $r5.length), $r1, $r2);
        if (super.isValid()) {
            int $i2 = $r3.length;
            int $i3 = $r5;
            int length = $i3.length;
            $r5 = $i3;
            int $i4 = getPayloadOffset();
            this.m_binaryCommandContainer.putInt($i4 + 0, $i0);
            this.m_binaryCommandContainer.putInt($i4 + 4, $i1);
            this.m_binaryCommandContainer.putInt($i4 + 8, $i2);
            this.m_binaryCommandContainer.putInt($i4 + 12, length);
            this.m_binaryCommandContainer.putByte($i4 + 16, (byte) ($z0));
            this.m_binaryCommandContainer.putByte($i4 + 17, $r4.toByte());
            System.arraycopy($r3, 0, this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + $i4) + 18, $i2);
            System.arraycopy($r5, 0, this.m_binaryCommandContainer.getData(), ((this.m_binaryCommandContainer.getPos() + $i4) + 18) + $i2, length);
            this.m_descriptorID = $i0;
            this.m_requestID = $i1;
            this.m_allowExecuteInForeground = $z0;
            this.m_requestBody = $r3;
            this.m_requestMethod = $r4;
            this.m_resourcePath = $r5;
        }
    }

    private static int getNeededLength(int $i0, int $i1) throws  {
        return ($i0 + 18) + $i1;
    }

    public boolean isValid() throws  {
        boolean $z0 = true;
        boolean $z1 = this.m_isValid;
        if ($z1) {
            return $z1;
        }
        $z1 = super.isValid();
        int $i0 = getPayloadOffset();
        $z1 = $z1 && this.m_binaryCommandContainer.getSize() >= $i0 + 18;
        if ($z1) {
            this.m_descriptorID = this.m_binaryCommandContainer.getInt($i0 + 0);
            this.m_requestID = this.m_binaryCommandContainer.getInt($i0 + 4);
            int $i1 = this.m_binaryCommandContainer.getInt($i0 + 8);
            int $i2 = this.m_binaryCommandContainer.getInt($i0 + 12);
            byte $b4 = this.m_binaryCommandContainer.getByte($i0 + 16);
            byte $b5 = this.m_binaryCommandContainer.getByte($i0 + 17);
            if (this.m_binaryCommandContainer.getSize() >= (($i0 + 18) + $i1) + $i2) {
                $z1 = true;
            } else {
                $z1 = false;
            }
            $z1 = $z1 && $i1 >= 0 && $i2 >= 0;
            if ($z1) {
                this.m_requestBody = new byte[$i1];
                this.m_resourcePath = new byte[$i2];
                System.arraycopy(this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + $i0) + 18, this.m_requestBody, 0, $i1);
                System.arraycopy(this.m_binaryCommandContainer.getData(), ((this.m_binaryCommandContainer.getPos() + $i0) + 18) + $i1, this.m_resourcePath, 0, $i2);
                if ($b4 == (byte) 0) {
                    $z0 = false;
                }
                this.m_allowExecuteInForeground = $z0;
                this.m_requestMethod = ERequestMethod.valueOf($b5);
            }
        }
        this.m_isValid = $z1;
        return $z1;
    }

    public int getDescriptorID() throws  {
        return this.m_descriptorID;
    }

    public int getRequestID() throws  {
        return this.m_requestID;
    }

    public boolean getAllowExecuteInForeground() throws  {
        return this.m_allowExecuteInForeground;
    }

    public byte[] getRequestBody() throws  {
        return this.m_requestBody;
    }

    public ERequestMethod getRequestMethod() throws  {
        return this.m_requestMethod;
    }

    public byte[] getResourcePath() throws  {
        return this.m_resourcePath;
    }
}
