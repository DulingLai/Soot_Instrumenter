package com.waze.beacons;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import android.os.ParcelUuid;

@TargetApi(21)
public class BeaconEntry {
    private static final ParcelUuid EDDYSTONE_SERVICE_UUID = ParcelUuid.fromString("0000FEAA-0000-1000-8000-00805F9B34FB");
    private String address;
    private int rssi;
    private long timestamp;

    public int getRssi() throws  {
        return this.rssi;
    }

    public long getTimestamp() throws  {
        return this.timestamp;
    }

    public String getAddress() throws  {
        return this.address;
    }

    private BeaconEntry() throws  {
    }

    protected BeaconEntry(BluetoothDevice $r1, byte[] serviceData, int $i0, long $l1, long $l2) throws  {
        this.rssi = $i0;
        this.timestamp = ($l2 / 1000000) + $l1;
        if ($r1 != null) {
            this.address = $r1.getAddress();
        }
    }

    public static BeaconEntry makeEntry(BluetoothDevice $r0, byte[] $r1, int $i0, long $l1, long $l2) throws  {
        if ($r1 == null) {
            return null;
        }
        if ($r1.length < 1) {
            return null;
        }
        if ($r1[0] == (byte) 0) {
            return new BeaconBeatEntry($r0, $r1, $i0, $l1, $l2);
        }
        return $r1[0] == (byte) 32 ? new BeaconTlmEntry($r0, $r1, $i0, $l1, $l2) : null;
    }

    public static BeaconEntry makeEntry(ScanResult $r0, long $l0) throws  {
        if ($r0 == null || $r0.getScanRecord() == null) {
            return null;
        }
        return makeEntry($r0.getDevice(), $r0.getScanRecord().getServiceData(EDDYSTONE_SERVICE_UUID), $r0.getRssi(), $l0, $r0.getTimestampNanos());
    }
}
