package com.abaltatech.wlappservices;

import android.util.Log;
import com.abaltatech.mcp.weblink.core.DataBuffer;

public class UnregisterServiceCommand extends WLServiceCommand {
    static final /* synthetic */ boolean $assertionsDisabled = (!UnregisterServiceCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 5;
    public static final short ID = (short) 105;
    private static final String TAG = "UnregisterServiceCommand";
    private boolean m_isValid = false;
    private int m_serviceDescriptorID;
    private String m_serviceName;

    public /* bridge */ /* synthetic */ String getReceiverID() throws  {
        return super.getReceiverID();
    }

    public /* bridge */ /* synthetic */ String getSenderID() throws  {
        return super.getSenderID();
    }

    public UnregisterServiceCommand(DataBuffer $r1) throws  {
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

    public boolean isValid() throws  {
        boolean $z0 = this.m_isValid;
        if ($z0) {
            return $z0;
        }
        $z0 = super.isValid();
        int $i1 = getPayloadOffset();
        $z0 = $z0 && this.m_binaryCommandContainer.getSize() >= $i1 + 5;
        if ($z0) {
            this.m_serviceDescriptorID = this.m_binaryCommandContainer.getInt($i1 + 0);
            $i1 += 4;
            short $s0 = this.m_binaryCommandContainer.getByte($i1) & (short) 255;
            $i1++;
            if ($i1 + $s0 > this.m_binaryCommandContainer.getSize() || $s0 < (short) 0) {
                $z0 = false;
            } else {
                $z0 = true;
            }
            if ($z0) {
                this.m_serviceName = new String(this.m_binaryCommandContainer.getData(), this.m_binaryCommandContainer.getPos() + $i1, $s0);
            }
        }
        this.m_isValid = $z0;
        return $z0;
    }

    public UnregisterServiceCommand(String $r1, String $r2, int $i0, String $r3) throws  {
        super(ID, getNeededLength($r3), $r1, $r2);
        if (super.isValid()) {
            if ($r3 == null) {
                $r1 = "";
            } else {
                $r2 = $r3.trim();
                $r1 = $r2;
                if ($r2.length() > 50) {
                    $r2 = $r2.substring(0, 50);
                    $r1 = $r2;
                    Log.e(TAG, "serviceName is too long, trimming to: " + $r2);
                }
            }
            int $i1 = getPayloadOffset();
            byte[] $r4 = $r1.getBytes();
            this.m_binaryCommandContainer.putInt($i1 + 0, $i0);
            this.m_binaryCommandContainer.putByte($i1 + 4, (byte) $r4.length);
            System.arraycopy($r4, 0, this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + $i1) + 5, $r4.length);
            this.m_serviceName = $r1;
            this.m_serviceDescriptorID = $i0;
        }
    }

    private static int getNeededLength(String $r0) throws  {
        if ($r0 == null) {
            $r0 = "";
        } else {
            String $r2 = $r0.trim();
            $r0 = $r2;
            if ($r2.length() > 50) {
                $r0 = $r2.substring(0, 50);
            }
        }
        return 5 + $r0.getBytes().length;
    }

    public String getServiceName() throws  {
        return this.m_serviceName;
    }

    public int getServiceDescriptorID() throws  {
        return this.m_serviceDescriptorID;
    }
}
