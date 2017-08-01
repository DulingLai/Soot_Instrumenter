package com.waze.beacons;

import android.bluetooth.BluetoothDevice;
import com.waze.analytics.AnalyticsEvents;
import java.util.Arrays;

public class BeaconBeatEntry extends BeaconEntry {
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    private int txpower;
    private String uuid;

    public String getUuid() throws  {
        return this.uuid;
    }

    public int getTxpower() throws  {
        return this.txpower;
    }

    public BeaconBeatEntry(BluetoothDevice $r1, byte[] $r2, int $i0, long $l1, long $l2) throws  {
        super($r1, $r2, $i0, $l1, $l2);
        this.uuid = getUuidString($r2);
        this.txpower = decodeTxPower($r2);
    }

    private static String getUuidString(byte[] $r0) throws  {
        if ($r0.length >= 18) {
            return bytesToHex(Arrays.copyOfRange($r0, 2, 18));
        }
        return AnalyticsEvents.ANALYTICS_EVENT_NETWORK_MODE_NA;
    }

    private static String bytesToHex(byte[] $r0) throws  {
        char[] $r1 = new char[($r0.length * 2)];
        for (int $i1 = 0; $i1 < $r0.length; $i1++) {
            short $s0 = $r0[$i1] & (short) 255;
            $r1[$i1 * 2] = hexArray[$s0 >>> (short) 4];
            $r1[($i1 * 2) + 1] = hexArray[$s0 & (short) 15];
        }
        return new String($r1);
    }

    private static int decodeTxPower(byte[] $r0) throws  {
        if ($r0 == null || $r0.length < 1) {
            return 0;
        }
        return $r0[1];
    }
}
