package com.waze.beacons;

import android.bluetooth.BluetoothAdapter;
import android.os.Handler;
import android.util.Log;
import com.waze.AppService;
import com.waze.ConfigManager;
import com.waze.Logger;
import com.waze.NativeManager;
import java.util.LinkedList;
import java.util.List;

public class BeaconManager {
    private static BeaconManager instance;
    private int delay;
    private Handler handler = new Handler();
    private BeaconListener listener;
    private String prefix;
    private List<BeaconEntry> results;

    class C13701 implements Runnable {
        C13701() throws  {
        }

        public void run() throws  {
            BeaconManager.this.listener.start();
        }
    }

    class C13712 implements Runnable {
        C13712() throws  {
        }

        public void run() throws  {
            BeaconManager.this.listener.stop();
        }
    }

    class C13733 implements Runnable {
        C13733() throws  {
        }

        public void run() throws  {
            final int $i0 = BeaconManager.this.api_get_status();
            NativeManager.Post(new Runnable() {
                public void run() throws  {
                    BeaconManager.this.updateStatusNTV($i0);
                }
            });
        }
    }

    class C13754 implements Runnable {
        C13754() throws  {
        }

        public void run() throws  {
            BluetoothAdapter $r1 = BluetoothAdapter.getDefaultAdapter();
            final boolean $z0 = $r1 != null && $r1.enable();
            NativeManager.Post(new Runnable() {
                public void run() throws  {
                    BeaconManager.this.turnedOnNTV($z0);
                }
            });
        }
    }

    class C13765 implements IBeaconListenerCallback {
        C13765() throws  {
        }

        public void onEntry(BeaconEntry $r1) throws  {
            BeaconManager.this.processEntry($r1);
        }
    }

    class C13787 implements Runnable {
        C13787() throws  {
        }

        public void run() throws  {
            BeaconManager.this.dumpEntries();
        }
    }

    private native void addScanResultNTV(long j, String str, String str2, int i, int i2) throws ;

    private native void addTlmResultNTV(long j, String str, int i, float f, float f2, long j2, long j3) throws ;

    private native void initNativeLayerNTV() throws ;

    private native void turnedOnNTV(boolean z) throws ;

    private native void updateStatusNTV(int i) throws ;

    public static BeaconManager getInstance() throws  {
        create();
        return instance;
    }

    public void api_begin(String $r1) throws  {
        this.prefix = $r1;
        AppService.Post(new C13701());
    }

    public void api_stop() throws  {
        AppService.Post(new C13712());
    }

    public int api_get_status() throws  {
        BluetoothAdapter $r1 = BluetoothAdapter.getDefaultAdapter();
        if ($r1 == null) {
            return 2;
        }
        return $r1.isEnabled() ? 0 : 1;
    }

    public void api_get_status_async() throws  {
        AppService.Post(new C13733());
    }

    public void api_turn_on_async() throws  {
        AppService.Post(new C13754());
    }

    public static void create() throws  {
        if (instance == null) {
            instance = new BeaconManager();
        }
    }

    private BeaconManager() throws  {
        init();
    }

    private void init() throws  {
        Log.d(Logger.TAG, "beacons initNativeLayerNTV " + Thread.currentThread().getId());
        initNativeLayerNTV();
        this.delay = ConfigManager.getInstance().getConfigValueInt(394);
        this.listener = new BeaconListener(new C13765());
    }

    private void dumpEntries() throws  {
        final List $r1 = this.results;
        this.results = null;
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                for (BeaconEntry beaconEntry : $r1) {
                    if (beaconEntry instanceof BeaconBeatEntry) {
                        BeaconBeatEntry beaconBeatEntry = (BeaconBeatEntry) beaconEntry;
                        BeaconManager.this.addScanResultNTV(beaconBeatEntry.getTimestamp(), beaconBeatEntry.getAddress(), beaconBeatEntry.getUuid(), beaconBeatEntry.getRssi(), beaconBeatEntry.getTxpower());
                    } else if (beaconEntry instanceof BeaconTlmEntry) {
                        BeaconTlmEntry $r9 = (BeaconTlmEntry) beaconEntry;
                        BeaconManager.this.addTlmResultNTV($r9.getTimestamp(), $r9.getAddress(), $r9.getRssi(), (float) $r9.getVoltage(), (float) $r9.getTemperature(), $r9.getPdu(), $r9.getLifetime());
                    }
                }
            }
        });
    }

    private void processEntry(BeaconEntry $r1) throws  {
        if ((($r1 instanceof BeaconBeatEntry) && ((BeaconBeatEntry) $r1).getUuid().startsWith(this.prefix)) || ($r1 instanceof BeaconTlmEntry)) {
            if (this.results == null) {
                this.results = new LinkedList();
                this.handler.postDelayed(new C13787(), 250);
            }
            this.results.add($r1);
        }
    }
}
