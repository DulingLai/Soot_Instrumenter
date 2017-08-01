package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zzu {
    protected static final Comparator<byte[]> zzbu = new C08611();
    private List<byte[]> zzbq = new LinkedList();
    private List<byte[]> zzbr = new ArrayList(64);
    private int zzbs = 0;
    private final int zzbt;

    /* compiled from: dalvik_source_com.waze.apk */
    class C08611 implements Comparator<byte[]> {
        C08611() throws  {
        }

        public /* synthetic */ int compare(Object $r1, Object $r2) throws  {
            return zza((byte[]) $r1, (byte[]) $r2);
        }

        public int zza(byte[] $r1, byte[] $r2) throws  {
            return $r1.length - $r2.length;
        }
    }

    public zzu(int $i0) throws  {
        this.zzbt = $i0;
    }

    private synchronized void zzx() throws  {
        while (this.zzbs > this.zzbt) {
            byte[] $r3 = (byte[]) this.zzbq.remove(0);
            this.zzbr.remove($r3);
            this.zzbs -= $r3.length;
        }
    }

    public synchronized void zza(byte[] $r1) throws  {
        if ($r1 != null) {
            if ($r1.length <= this.zzbt) {
                this.zzbq.add($r1);
                int $i0 = Collections.binarySearch(this.zzbr, $r1, zzbu);
                int $i1 = $i0;
                if ($i0 < 0) {
                    $i1 = (-$i0) - 1;
                }
                this.zzbr.add($i1, $r1);
                this.zzbs += $r1.length;
                zzx();
            }
        }
    }

    public synchronized byte[] zzb(int $i0) throws  {
        byte[] $r3;
        for (int $i1 = 0; $i1 < this.zzbr.size(); $i1++) {
            $r3 = (byte[]) this.zzbr.get($i1);
            if ($r3.length >= $i0) {
                this.zzbs -= $r3.length;
                this.zzbr.remove($i1);
                this.zzbq.remove($r3);
                break;
            }
        }
        $r3 = new byte[$i0];
        return $r3;
    }
}
