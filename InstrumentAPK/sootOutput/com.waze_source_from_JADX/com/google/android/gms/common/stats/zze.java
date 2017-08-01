package com.google.android.gms.common.stats;

import android.os.SystemClock;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;

/* compiled from: dalvik_source_com.waze.apk */
public class zze {
    private final long Ms;
    private final int Mt;
    private final SimpleArrayMap<String, Long> Mu;

    public zze() throws  {
        this.Ms = 60000;
        this.Mt = 10;
        this.Mu = new SimpleArrayMap(10);
    }

    public zze(int $i0, long $l1) throws  {
        this.Ms = $l1;
        this.Mt = $i0;
        this.Mu = new SimpleArrayMap();
    }

    private void zzh(long $l0, long $l1) throws  {
        for (int $i2 = this.Mu.size() - 1; $i2 >= 0; $i2--) {
            if ($l1 - ((Long) this.Mu.valueAt($i2)).longValue() > $l0) {
                this.Mu.removeAt($i2);
            }
        }
    }

    public Long zzha(String $r1) throws  {
        Long $r6;
        long $l0 = SystemClock.elapsedRealtime();
        long $l1 = this.Ms;
        synchronized (this) {
            while (this.Mu.size() >= this.Mt) {
                zzh($l1, $l0);
                $l1 /= 2;
                String str = "ConnectionTracker";
                Log.w(str, "The max capacity " + this.Mt + " is not enough. Current durationThreshold is: " + $l1);
            }
            $r6 = (Long) this.Mu.put($r1, Long.valueOf($l0));
        }
        return $r6;
    }

    public boolean zzhb(String $r1) throws  {
        boolean $z0;
        synchronized (this) {
            $z0 = this.Mu.remove($r1) != null;
        }
        return $z0;
    }
}
