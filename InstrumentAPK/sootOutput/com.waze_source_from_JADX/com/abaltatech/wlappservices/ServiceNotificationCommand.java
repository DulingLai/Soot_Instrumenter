package com.abaltatech.wlappservices;

import com.abaltatech.mcp.weblink.core.DataBuffer;

public class ServiceNotificationCommand extends WLServiceCommand {
    static final /* synthetic */ boolean $assertionsDisabled = (!ServiceNotificationCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 16;
    public static final short ID = (short) 103;
    private int m_descriptorID;
    private boolean m_isValid = false;
    private byte[] m_notificationData;
    private int m_notificationID;
    private byte[] m_resourcePath;

    public /* bridge */ /* synthetic */ String getReceiverID() throws  {
        return super.getReceiverID();
    }

    public /* bridge */ /* synthetic */ String getSenderID() throws  {
        return super.getSenderID();
    }

    public ServiceNotificationCommand(DataBuffer $r1) throws  {
        super($r1);
        boolean $z0 = isValid();
        if ($assertionsDisabled || $z0) {
            short $s0 = getCommandID();
            if (!$assertionsDisabled && $s0 != ID) {
                throw new AssertionError();
            }
            return;
        }
        throw new AssertionError();
    }

    public ServiceNotificationCommand(String $r1, String $r2, int $i0, int $i1, byte[] $r3, byte[] $r4) throws  {
        super(ID, getNeededLength($r3.length, $r4.length), $r1, $r2);
        int $i2 = $r4.length;
        int $i3 = $r3.length;
        int $i4 = getPayloadOffset();
        this.m_binaryCommandContainer.putInt($i4 + 0, $i0);
        this.m_binaryCommandContainer.putInt($i4 + 4, $i1);
        this.m_binaryCommandContainer.putInt($i4 + 8, $i2);
        this.m_binaryCommandContainer.putInt($i4 + 12, $i3);
        System.arraycopy($r4, 0, this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + $i4) + 16, $i2);
        System.arraycopy($r3, 0, this.m_binaryCommandContainer.getData(), ((this.m_binaryCommandContainer.getPos() + $i4) + 16) + $i2, $i3);
        this.m_descriptorID = $i0;
        this.m_notificationID = $i1;
        this.m_resourcePath = $r3;
        this.m_notificationData = $r4;
    }

    private static int getNeededLength(int $i0, int $i1) throws  {
        return ($i0 + 16) + $i1;
    }

    public boolean isValid() throws  {
        boolean $z0 = this.m_isValid;
        if ($z0) {
            return $z0;
        }
        $z0 = super.isValid();
        int $i0 = getPayloadOffset();
        $z0 = $z0 && this.m_binaryCommandContainer.getSize() >= $i0 + 16;
        if ($z0) {
            this.m_descriptorID = this.m_binaryCommandContainer.getInt($i0 + 0);
            this.m_notificationID = this.m_binaryCommandContainer.getInt($i0 + 4);
            int $i1 = this.m_binaryCommandContainer.getInt($i0 + 8);
            int $i2 = this.m_binaryCommandContainer.getInt($i0 + 12);
            if (this.m_binaryCommandContainer.getSize() >= (($i0 + 16) + $i1) + $i2) {
                $z0 = true;
            } else {
                $z0 = false;
            }
            $z0 = $z0 && $i1 >= 0 && $i2 >= 0;
            if ($z0) {
                this.m_notificationData = new byte[$i1];
                this.m_resourcePath = new byte[$i2];
                System.arraycopy(this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + $i0) + 16, this.m_notificationData, 0, $i1);
                System.arraycopy(this.m_binaryCommandContainer.getData(), ((this.m_binaryCommandContainer.getPos() + $i0) + 16) + $i1, this.m_resourcePath, 0, $i2);
            }
        }
        this.m_isValid = $z0;
        return $z0;
    }

    public int getDescriptorID() throws  {
        return this.m_descriptorID;
    }

    public int getNotificationID() throws  {
        return this.m_notificationID;
    }

    public byte[] getResourcePath() throws  {
        return this.m_resourcePath;
    }

    public byte[] getNotificationData() throws  {
        return this.m_notificationData;
    }
}
