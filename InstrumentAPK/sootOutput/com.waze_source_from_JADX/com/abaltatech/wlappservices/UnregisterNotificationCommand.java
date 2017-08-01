package com.abaltatech.wlappservices;

import android.util.Log;
import com.abaltatech.mcp.weblink.core.DataBuffer;

public class UnregisterNotificationCommand extends WLServiceCommand {
    private static final int CMD_FIXED_SIZE = 12;
    public static final short ID = (short) 102;
    private int m_descriptorID;
    private boolean m_isValid = false;
    private int m_notificationID;
    private byte[] m_resourcePath;

    public /* bridge */ /* synthetic */ String getReceiverID() throws  {
        return super.getReceiverID();
    }

    public /* bridge */ /* synthetic */ String getSenderID() throws  {
        return super.getSenderID();
    }

    public UnregisterNotificationCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!isValid()) {
            Log.e("UnregisterNotificationCommand", "Not a valid command!");
        }
        short $s0 = getCommandID();
        if ($s0 != ID) {
            Log.e("UnregisterNotificationCommand", "Invalid command ID: " + $s0);
        }
    }

    public UnregisterNotificationCommand(String $r1, String $r2, int $i0, int $i1, byte[] $r3) throws  {
        super(ID, $r3.length + 12, $r1, $r2);
        if (super.isValid()) {
            int $i2 = $r3.length;
            int $i3 = getPayloadOffset();
            this.m_binaryCommandContainer.putInt($i3 + 0, $i0);
            this.m_binaryCommandContainer.putInt($i3 + 4, $i1);
            this.m_binaryCommandContainer.putInt($i3 + 8, $i2);
            System.arraycopy($r3, 0, this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + $i3) + 12, $i2);
            this.m_descriptorID = $i0;
            this.m_notificationID = $i1;
            this.m_resourcePath = $r3;
        }
    }

    public boolean isValid() throws  {
        boolean $z0 = this.m_isValid;
        if ($z0) {
            return $z0;
        }
        $z0 = super.isValid();
        int $i0 = getPayloadOffset();
        $z0 = $z0 && this.m_binaryCommandContainer.getSize() >= $i0 + 12;
        if ($z0) {
            this.m_descriptorID = this.m_binaryCommandContainer.getInt($i0 + 0);
            this.m_notificationID = this.m_binaryCommandContainer.getInt($i0 + 4);
            int $i1 = this.m_binaryCommandContainer.getInt($i0 + 8);
            if (this.m_binaryCommandContainer.getSize() >= ($i0 + 12) + $i1) {
                $z0 = true;
            } else {
                $z0 = false;
            }
            $z0 = $z0 && $i1 >= 0;
            if ($z0) {
                this.m_resourcePath = new byte[$i1];
                System.arraycopy(this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + $i0) + 12, this.m_resourcePath, 0, $i1);
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
}
