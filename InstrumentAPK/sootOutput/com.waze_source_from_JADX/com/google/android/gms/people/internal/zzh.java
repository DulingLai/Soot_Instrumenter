package com.google.android.gms.people.internal;

import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.google.android.gms.common.internal.zzab;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/* compiled from: dalvik_source_com.waze.apk */
public class zzh {
    private static final Integer[] aQf = new Integer[0];
    private final HashMap<Integer, Object> zzaij = new HashMap();

    private Integer[] zzceq() throws  {
        Integer[] $r4 = (Integer[]) new ArrayList(this.zzaij.keySet()).toArray(aQf);
        Arrays.sort($r4);
        return $r4;
    }

    public String toString() throws  {
        StringBuilder $r2 = zzq.zzcff();
        for (Integer $r1 : zzceq()) {
            if ($r2.length() > 0) {
                $r2.append(",");
            }
            $r2.append($r1);
            $r2.append("=");
            int $i2 = zzack($r1.intValue());
            for (int $i3 = 0; $i3 < $i2; $i3++) {
                if ($i3 > 0) {
                    $r2.append(FileUploadSession.SEPARATOR);
                }
                $r2.append(zzai($r1.intValue(), $i3));
            }
            $r2.append("");
        }
        return $r2.toString();
    }

    public void zza(Integer $r1, String $r2) throws  {
        zzab.zzag($r1);
        Object $r3 = this.zzaij.get($r1);
        if ($r3 == null) {
            this.zzaij.put($r1, $r2);
        } else if ($r3 instanceof String) {
            ArrayList $r5 = new ArrayList(4);
            $r5.add((String) $r3);
            $r5.add($r2);
            this.zzaij.put($r1, $r5);
        } else {
            ((ArrayList) $r3).add($r2);
        }
    }

    public int zzack(int $i0) throws  {
        zzab.zzag(Integer.valueOf($i0));
        Object $r3 = this.zzaij.get(Integer.valueOf($i0));
        return $r3 == null ? 0 : $r3 instanceof String ? 1 : ((ArrayList) $r3).size();
    }

    public String zzai(int $i0, int $i1) throws  {
        zzab.zzag(Integer.valueOf($i0));
        zzab.zzbn($i1 >= 0);
        Object $r3 = this.zzaij.get(Integer.valueOf($i0));
        if ($r3 == null) {
            throw new IndexOutOfBoundsException("Size=0, requested=" + $i1);
        } else if (!($r3 instanceof String)) {
            ArrayList $r7 = (ArrayList) $r3;
            if ($i1 <= $r7.size()) {
                return (String) $r7.get($i1);
            }
            throw new IndexOutOfBoundsException("Size=" + $r7.size() + ", requested=" + $i1);
        } else if ($i1 <= 0) {
            return (String) $r3;
        } else {
            throw new IndexOutOfBoundsException("Size=1, requested=" + $i1);
        }
    }
}
