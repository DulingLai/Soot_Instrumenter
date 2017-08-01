package com.google.android.gms.people.internal;

import java.util.ArrayList;

/* compiled from: dalvik_source_com.waze.apk */
public class zzu {
    private final ArrayList<Long> aRm;
    private final ArrayList<String> aRn;
    private final String mLabel;

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zza extends zzu {
        public static final zza aRo = new zza();

        public zza() throws  {
            super(null);
        }

        public void zzab(String str, int i) throws  {
        }

        public void zzrl(String str) throws  {
        }
    }

    private zzu(String $r1) throws  {
        this.aRm = new ArrayList();
        this.aRn = new ArrayList();
        this.mLabel = $r1;
        zzrl("");
    }

    public static zzu zzcfi() throws  {
        return zza.aRo;
    }

    public static zzu zzrk(String $r0) throws  {
        return new zzu($r0);
    }

    public synchronized void zzab(String $r1, int $i0) throws  {
        zzrl("");
        long $l1 = ((Long) this.aRm.get(0)).longValue();
        long $l2 = $l1;
        $l1 = ((Long) this.aRm.get(this.aRm.size() - 1)).longValue() - $l1;
        if ($l1 >= ((long) $i0)) {
            StringBuilder $r6 = zzq.zzcff();
            $r6.append(this.mLabel);
            $r6.append(",");
            $r6.append($l1);
            $r6.append("ms: ");
            $i0 = 1;
            while ($i0 < this.aRm.size()) {
                $l1 = ((Long) this.aRm.get($i0)).longValue();
                $r6.append((String) this.aRn.get($i0));
                $r6.append(",");
                $r6.append($l1 - $l2);
                $r6.append("ms ");
                $i0++;
                $l2 = $l1;
            }
            zzp.zzbr($r1, $r6.toString());
        }
    }

    public synchronized void zzrl(String $r1) throws  {
        this.aRm.add(Long.valueOf(System.currentTimeMillis()));
        this.aRn.add($r1);
    }
}
