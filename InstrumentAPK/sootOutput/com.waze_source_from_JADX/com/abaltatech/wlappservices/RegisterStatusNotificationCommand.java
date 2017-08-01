package com.abaltatech.wlappservices;

import android.util.Log;
import com.abaltatech.mcp.weblink.core.DataBuffer;

public class RegisterStatusNotificationCommand extends WLServiceCommand {
    private static final int CMD_FIXED_SIZE = 4;
    public static final short ID = (short) 112;
    private boolean m_isValid = false;
    private int m_registerNotificationID = 0;

    public /* bridge */ /* synthetic */ String getReceiverID() throws  {
        return super.getReceiverID();
    }

    public /* bridge */ /* synthetic */ String getSenderID() throws  {
        return super.getSenderID();
    }

    public RegisterStatusNotificationCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!isValid()) {
            Log.e("RegisterStatusNotificationCommand", "Not a valid command!");
        }
        short $s0 = getCommandID();
        if ($s0 != ID) {
            Log.e("RegisterStatusNotificationCommand", "Invalid command ID: " + $s0);
        }
    }

    public RegisterStatusNotificationCommand(String $r1, String $r2, int $i0) throws  {
        super(ID, 4, $r1, $r2);
        if (super.isValid()) {
            this.m_binaryCommandContainer.putInt(getPayloadOffset() + 0, $i0);
            this.m_registerNotificationID = $i0;
        }
    }

    public boolean isValid() throws  {
        boolean $z0 = this.m_isValid;
        if ($z0) {
            return $z0;
        }
        $z0 = super.isValid();
        int $i0 = getPayloadOffset();
        $z0 = $z0 && this.m_binaryCommandContainer.getSize() >= $i0 + 4;
        if ($z0) {
            this.m_registerNotificationID = this.m_binaryCommandContainer.getInt($i0 + 0);
        }
        this.m_isValid = $z0;
        return $z0;
    }

    public int getRegisterNotificationID() throws  {
        return this.m_registerNotificationID;
    }
}
