package com.abaltatech.wlappservices;

import android.util.Log;
import com.abaltatech.mcp.weblink.core.DataBuffer;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

public class RegisterServiceCommand extends WLServiceCommand {
    private static final int CMD_FIXED_SIZE = 6;
    public static final short ID = (short) 104;
    private static final String TAG = "RegisterServiceCommand";
    private boolean m_isValid = false;
    private List<String> m_protocols;
    private int m_serviceDescriptorID;
    private String m_serviceName;

    public /* bridge */ /* synthetic */ String getReceiverID() throws  {
        return super.getReceiverID();
    }

    public /* bridge */ /* synthetic */ String getSenderID() throws  {
        return super.getSenderID();
    }

    public RegisterServiceCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!isValid()) {
            Log.e(TAG, "Not a valid command!");
        }
        short $s0 = getCommandID();
        if ($s0 != ID) {
            Log.e(TAG, "Invalid command ID: " + $s0);
        }
    }

    public RegisterServiceCommand(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i0, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r3, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r4) throws  {
        super(ID, getNeededLength($r3, $r4), $r1, $r2);
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
            List $r42;
            if ($r4 == null) {
                $r42 = r11;
                ArrayList r11 = new ArrayList();
            } else if ($r4.size() > 10) {
                $r42 = $r4.subList(0, 10);
                Log.e(TAG, "Too many protocols, trimming to 10");
            }
            int $i1 = getPayloadOffset();
            byte[] $r5 = $r1.getBytes();
            this.m_binaryCommandContainer.putInt($i1 + 0, $i0);
            this.m_binaryCommandContainer.putByte($i1 + 4, (byte) $r4.size());
            this.m_binaryCommandContainer.putByte($i1 + 5, (byte) $r5.length);
            System.arraycopy($r5, 0, this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + $i1) + 6, $r5.length);
            $i1 = ($i1 + 6) + $r5.length;
            for (String $r22 : $r4) {
                if ($r22 == null) {
                    $r22 = "";
                } else {
                    $r22 = $r22.trim();
                }
                if ($r22.length() > 50) {
                    $r3 = $r22.substring(0, 50);
                    $r22 = $r3;
                    Log.e(TAG, "Protocol name too long, trimming to: " + $r3);
                }
                $r5 = $r22.getBytes();
                this.m_binaryCommandContainer.putByte($i1, (byte) $r5.length);
                $i1++;
                System.arraycopy($r5, 0, this.m_binaryCommandContainer.getData(), this.m_binaryCommandContainer.getPos() + $i1, $r5.length);
                $i1 += $r5.length;
            }
            this.m_serviceDescriptorID = $i0;
            this.m_serviceName = $r1;
            this.m_protocols = $r4;
        }
    }

    private static int getNeededLength(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)I"}) String $r0, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)I"}) List<String> $r1) throws  {
        if ($r0 == null) {
            $r0 = "";
        } else {
            String $r5 = $r0.trim();
            $r0 = $r5;
            if ($r5.length() > 50) {
                $r0 = $r5.substring(0, 50);
            }
        }
        List $r12;
        if ($r1 == null) {
            $r12 = r6;
            ArrayList r6 = new ArrayList();
        } else if ($r1.size() > 10) {
            $r12 = $r1.subList(0, 10);
        }
        int $i0 = 6 + $r0.getBytes().length;
        for (String $r02 : $r1) {
            if ($r02 == null) {
                $r02 = "";
            } else {
                $r02 = $r02.trim();
            }
            if ($r02.length() > 50) {
                $r02 = $r02.substring(0, 50);
            }
            $i0 += $r02.getBytes().length + 1;
        }
        return $i0;
    }

    public boolean isValid() throws  {
        boolean $z0 = this.m_isValid;
        if ($z0) {
            return $z0;
        }
        $z0 = super.isValid();
        int $i1 = getPayloadOffset();
        $z0 = $z0 && this.m_binaryCommandContainer.getSize() >= $i1 + 6;
        if ($z0) {
            this.m_serviceDescriptorID = this.m_binaryCommandContainer.getInt($i1 + 0);
            short $s0 = this.m_binaryCommandContainer.getByte($i1 + 4) & (short) 255;
            $i1 += 5;
            short $s5 = this.m_binaryCommandContainer.getByte($i1) & (short) 255;
            $i1++;
            int $i2 = this.m_binaryCommandContainer.getSize();
            if ($i1 + $s5 > $i2 || $s5 < (short) 0) {
                $z0 = false;
            } else {
                $z0 = true;
            }
            if ($z0) {
                this.m_serviceName = new String(this.m_binaryCommandContainer.getData(), this.m_binaryCommandContainer.getPos() + $i1, $s5);
                $i1 += $s5;
            }
            this.m_protocols = new ArrayList();
            short $i3 = (short) 0;
            while ($z0 && $i3 < $s0) {
                if ($i1 < $i2) {
                    $z0 = true;
                } else {
                    $z0 = false;
                }
                if ($z0) {
                    $s5 = this.m_binaryCommandContainer.getByte($i1) & (short) 255;
                    $i1++;
                    if ($i1 + $s5 <= $i2) {
                        $z0 = true;
                    } else {
                        $z0 = false;
                    }
                    if ($z0) {
                        this.m_protocols.add(new String(this.m_binaryCommandContainer.getData(), this.m_binaryCommandContainer.getPos() + $i1, $s5));
                        $i1 += $s5;
                    }
                }
                $i3++;
            }
        }
        this.m_isValid = $z0;
        return $z0;
    }

    public int getServiceDescriptorID() throws  {
        return this.m_serviceDescriptorID;
    }

    public String getServiceName() throws  {
        return this.m_serviceName;
    }

    public List<String> getProtocols() throws  {
        return this.m_protocols;
    }
}
