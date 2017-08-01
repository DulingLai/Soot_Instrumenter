package com.waze.beacons;

import android.bluetooth.BluetoothDevice;

public class BeaconTlmEntry extends BeaconEntry {
    private long lifetime;
    private long pdu;
    private double temperature;
    private double voltage;

    public double getVoltage() throws  {
        return this.voltage;
    }

    public double getTemperature() throws  {
        return this.temperature;
    }

    public long getPdu() throws  {
        return this.pdu;
    }

    public long getLifetime() throws  {
        return this.lifetime;
    }

    public BeaconTlmEntry(BluetoothDevice $r1, byte[] $r2, int $i0, long $l1, long $l2) throws  {
        super($r1, $r2, $i0, $l1, $l2);
        decodeTLM($r2);
    }

    private boolean decodeTLM(byte[] $r1) throws  {
        if ($r1.length < 14) {
            return false;
        }
        this.voltage = ((double) ((($r1[2] & (short) 255) << 8) | ($r1[3] & (short) 255))) / 1000.0d;
        this.temperature = ((double) ((($r1[4] & (short) 255) << 8) | ($r1[5] & (short) 255))) / 256.0d;
        this.pdu = (long) (((((($r1[6] & (short) 255) << 24) | (($r1[7] & (short) 255) << 16)) | (($r1[8] & (short) 255) << 8)) | ($r1[9] & (short) 255)) & -1);
        this.lifetime = (long) (((((($r1[10] & (short) 255) << 24) | (($r1[11] & (short) 255) << 16)) | (($r1[12] & (short) 255) << 8)) | ($r1[13] & (short) 255)) & -1);
        return true;
    }
}
