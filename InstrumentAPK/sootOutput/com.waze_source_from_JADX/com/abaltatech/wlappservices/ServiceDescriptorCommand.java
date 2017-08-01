package com.abaltatech.wlappservices;

import android.util.Log;
import com.abaltatech.mcp.weblink.core.DataBuffer;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

class ServiceDescriptorCommand extends WLServiceCommand {
    static final /* synthetic */ boolean $assertionsDisabled = (!ServiceDescriptorCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 18;
    static final short ID = (short) 97;
    public static final int STATUS_END = 3;
    public static final int STATUS_ERROR = 2;
    public static final int STATUS_FOUND = 1;
    private static final String TAG = "ServiceDescriptorCommand";
    private int m_descriptorID = 0;
    private int m_index = 0;
    private boolean m_isValid = false;
    private List<String> m_protocols = null;
    private int m_searchID = 0;
    private int m_searchStatus = 0;
    private String m_serviceName = null;

    public ServiceDescriptorCommand(DataBuffer $r1) throws  {
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
        $z0 = $z0 && this.m_binaryCommandContainer.getSize() >= $i1 + 18;
        if ($z0) {
            this.m_searchID = this.m_binaryCommandContainer.getInt($i1 + 0);
            this.m_searchStatus = this.m_binaryCommandContainer.getInt($i1 + 4);
            this.m_descriptorID = this.m_binaryCommandContainer.getInt($i1 + 8);
            this.m_index = this.m_binaryCommandContainer.getInt($i1 + 12);
            short $s0 = this.m_binaryCommandContainer.getByte($i1 + 16) & (short) 255;
            $i1 += 17;
            short $s5 = this.m_binaryCommandContainer.getByte($i1) & (short) 255;
            $i1++;
            int $i2 = this.m_binaryCommandContainer.getSize();
            $z0 = $i1 + $s5 <= $i2 && $s5 >= (short) 0;
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

    public ServiceDescriptorCommand(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i0, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i2, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i3, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r3, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r4) throws  {
        super(ID, getNeededSize($r3, $r4), $r1, $r2);
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
            int $i5 = getPayloadOffset();
            byte[] $r5 = $r1.getBytes();
            this.m_binaryCommandContainer.putInt($i5 + 0, $i0);
            this.m_binaryCommandContainer.putInt($i5 + 4, $i1);
            this.m_binaryCommandContainer.putInt($i5 + 8, $i2);
            this.m_binaryCommandContainer.putInt($i5 + 12, $i3);
            this.m_binaryCommandContainer.putByte($i5 + 16, (byte) $r4.size());
            this.m_binaryCommandContainer.putByte($i5 + 17, (byte) $r5.length);
            System.arraycopy($r5, 0, this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + $i5) + 18, $r5.length);
            $i5 = ($i5 + 18) + $r5.length;
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
                this.m_binaryCommandContainer.putByte($i5, (byte) $r5.length);
                $i5++;
                System.arraycopy($r5, 0, this.m_binaryCommandContainer.getData(), this.m_binaryCommandContainer.getPos() + $i5, $r5.length);
                $i5 += $r5.length;
            }
            this.m_searchID = $i0;
            this.m_searchStatus = $i1;
            this.m_descriptorID = $i2;
            this.m_serviceName = $r1;
            this.m_protocols = $r4;
            this.m_index = $i3;
        }
    }

    private static int getNeededSize(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)I"}) String $r0, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)I"}) List<String> $r1) throws  {
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
        int $i0 = 18 + $r0.getBytes().length;
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

    public int getSearchID() throws  {
        return this.m_searchID;
    }

    public int getSearchStatus() throws  {
        return this.m_searchStatus;
    }

    public int getDescriptorID() throws  {
        return this.m_descriptorID;
    }

    public String getServiceName() throws  {
        return this.m_serviceName;
    }

    public List<String> getProtocols() throws  {
        return this.m_protocols;
    }

    public int getIndex() throws  {
        return this.m_index;
    }
}
