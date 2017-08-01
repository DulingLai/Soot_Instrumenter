package com.google.android.gms.people.internal;

import java.util.ArrayList;

/* compiled from: dalvik_source_com.waze.apk */
public class zzi {
    private final ArrayList<Object> aQg = new ArrayList();

    private static IndexOutOfBoundsException zzaj(int $i0, int $i1) throws  {
        return new IndexOutOfBoundsException(String.format("Size=%d, requested=%d", new Object[]{Integer.valueOf($i0), Integer.valueOf($i1)}));
    }

    public int get(int $i0, int $i1) throws  {
        Object $r2 = this.aQg.get($i0);
        if ($r2 == null) {
            throw zzaj(0, $i1);
        } else if (!($r2 instanceof Integer)) {
            ArrayList $r1 = (ArrayList) $r2;
            if ($i1 <= $r1.size()) {
                return ((Integer) $r1.get($i1)).intValue();
            }
            throw zzaj($r1.size(), $i1);
        } else if ($i1 <= 0) {
            return ((Integer) $r2).intValue();
        } else {
            throw zzaj(1, $i1);
        }
    }

    public int size() throws  {
        return this.aQg.size();
    }

    public String toString() throws  {
        StringBuilder $r1 = zzq.zzcff();
        for (int $i0 = 0; $i0 < size(); $i0++) {
            if ($i0 > 0) {
                $r1.append(",");
            }
            $r1.append("[");
            int $i1 = zzack($i0);
            for (int $i2 = 0; $i2 < $i1; $i2++) {
                if ($i2 > 0) {
                    $r1.append(",");
                }
                $r1.append(get($i0, $i2));
            }
            $r1.append("]");
        }
        return $r1.toString();
    }

    public void zza(zzv $r1, String $r2) throws  {
        int $i1 = $r1.zzrm($r2);
        if ($i1 != 0) {
            if ($i1 == 1) {
                this.aQg.add(Integer.valueOf($r1.zzac($r2, 0)));
                return;
            }
            ArrayList $r3 = new ArrayList($i1);
            for (int $i0 = 0; $i0 < $i1; $i0++) {
                $r3.add(Integer.valueOf($r1.zzac($r2, $i0)));
            }
            this.aQg.add($r3);
        }
    }

    public int zzack(int $i0) throws  {
        Object $r2 = this.aQg.get($i0);
        return $r2 == null ? 0 : $r2 instanceof Integer ? 1 : ((ArrayList) $r2).size();
    }

    public void zzacl(int $i0) throws  {
        this.aQg.add(Integer.valueOf($i0));
    }

    public void zzcer() throws  {
        this.aQg.add(null);
    }
}
