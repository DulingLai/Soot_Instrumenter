package com.abaltatech.mcp.weblink.sdk.internal;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import com.abaltatech.mcp.mcs.common.IMCSConnectionAddress;
import com.abaltatech.mcp.mcs.tcpip.TCPIPAddress;
import com.abaltatech.mcp.mcs.utils.ByteUtils;
import com.google.android.gms.auth.firstparty.recovery.RecoveryParamConstants;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.Arrays;

public class WLUtils {
    static final /* synthetic */ boolean $assertionsDisabled = (!WLUtils.class.desiredAssertionStatus());
    public static final int AF_INET = 2;
    public static final int AF_INET6 = 23;

    public static String getCurrentProcName(Context $r0) throws  {
        int $i0 = Process.myPid();
        for (RunningAppProcessInfo $r5 : ((ActivityManager) $r0.getSystemService(RecoveryParamConstants.VALUE_ACTIVITY)).getRunningAppProcesses()) {
            if ($r5.pid == $i0) {
                return $r5.processName;
            }
        }
        return "";
    }

    public static IMCSConnectionAddress extractAddress(byte[] $r0) throws  {
        if ($r0.length <= 2) {
            return null;
        }
        switch ($r0[1] + ($r0[0] << 8)) {
            case 2:
                if ($r0.length == 8) {
                    return new TCPIPAddress(Arrays.copyOfRange($r0, 2, 6), ByteUtils.ReadWord($r0, 6));
                }
                return null;
            case 23:
                return $r0.length == 20 ? new TCPIPAddress(Arrays.copyOfRange($r0, 2, 18), ByteUtils.ReadWord($r0, 18)) : null;
            default:
                if ($assertionsDisabled) {
                    return null;
                }
                throw new AssertionError();
        }
    }

    public static byte[] extractData(IMCSConnectionAddress $r0) throws  {
        if (!($r0 instanceof TCPIPAddress)) {
            return null;
        }
        TCPIPAddress $r2 = (TCPIPAddress) $r0;
        byte[] $r1;
        if ($r2.getAddress() instanceof Inet4Address) {
            $r1 = new byte[8];
            ByteUtils.WriteWord($r1, 0, 2);
            System.arraycopy($r2.getAddress().getAddress(), 0, $r1, 2, 4);
            ByteUtils.WriteWord($r1, 6, $r2.getPort());
            return $r1;
        } else if ($r2.getAddress() instanceof Inet6Address) {
            $r1 = new byte[20];
            ByteUtils.WriteWord($r1, 0, 23);
            System.arraycopy($r2.getAddress().getAddress(), 0, $r1, 2, 16);
            ByteUtils.WriteWord($r1, 18, $r2.getPort());
            return $r1;
        } else if ($assertionsDisabled) {
            return null;
        } else {
            throw new AssertionError();
        }
    }
}
