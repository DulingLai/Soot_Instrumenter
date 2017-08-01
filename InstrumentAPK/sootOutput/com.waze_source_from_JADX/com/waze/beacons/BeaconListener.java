package com.waze.beacons;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.bluetooth.le.ScanSettings.Builder;
import android.os.Build.VERSION;
import android.os.SystemClock;
import java.util.Arrays;

@TargetApi(21)
public class BeaconListener {
    private BluetoothAdapter mBluetoothAdapter;
    private IBeaconListenerCallback mCallback;
    private ScanCallback mScanCallback;
    private LeScanCallback mScanLeCallback;
    private boolean mStarted;
    private long timeOffset;

    class C13681 extends ScanCallback {
        C13681() throws  {
        }

        public void onScanResult(int $i0, ScanResult $r1) throws  {
            super.onScanResult($i0, $r1);
            BeaconListener.this.mCallback.onEntry(BeaconEntry.makeEntry($r1, BeaconListener.this.timeOffset));
        }
    }

    class C13692 implements LeScanCallback {
        C13692() throws  {
        }

        public void onLeScan(BluetoothDevice $r1, int $i0, byte[] $r2) throws  {
            if ($r2 != null) {
                long $l2 = SystemClock.elapsedRealtimeNanos();
                int $i1 = $r2.length;
                if ($i1 > 10 && $r2[9] == (byte) -86 && $r2[10] == (byte) -2) {
                    BeaconListener.this.mCallback.onEntry(BeaconEntry.makeEntry($r1, Arrays.copyOfRange($r2, 11, $i1), $i0, BeaconListener.this.timeOffset, $l2));
                }
            }
        }
    }

    public BeaconListener(IBeaconListenerCallback $r1) throws  {
        this.mCallback = $r1;
        if (VERSION.SDK_INT >= 18) {
            this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (VERSION.SDK_INT >= 21) {
                this.mScanCallback = new C13681();
            } else {
                this.mScanLeCallback = new C13692();
            }
        }
    }

    public void start() throws  {
        this.timeOffset = System.currentTimeMillis() - SystemClock.elapsedRealtime();
        if (this.mBluetoothAdapter != null && !this.mStarted) {
            if (VERSION.SDK_INT >= 21) {
                ScanSettings $r3 = new Builder().setScanMode(2).build();
                BluetoothLeScanner $r4 = this.mBluetoothAdapter.getBluetoothLeScanner();
                if ($r4 != null) {
                    $r4.startScan(null, $r3, this.mScanCallback);
                    this.mStarted = true;
                    return;
                }
                return;
            }
            this.mBluetoothAdapter.startLeScan(this.mScanLeCallback);
            this.mStarted = true;
        }
    }

    public void stop() throws  {
        if (this.mStarted && this.mBluetoothAdapter != null) {
            if (VERSION.SDK_INT >= 21) {
                BluetoothLeScanner $r1 = this.mBluetoothAdapter.getBluetoothLeScanner();
                if ($r1 != null) {
                    $r1.stopScan(this.mScanCallback);
                }
            } else {
                this.mBluetoothAdapter.stopLeScan(this.mScanLeCallback);
            }
            this.mStarted = false;
        }
    }
}
