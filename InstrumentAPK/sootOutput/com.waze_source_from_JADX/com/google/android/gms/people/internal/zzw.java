package com.google.android.gms.people.internal;

import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/* compiled from: dalvik_source_com.waze.apk */
public class zzw<T> {
    private final HashMap<String, Object> zzaij = new HashMap();

    private String[] zzcfj() throws  {
        String[] $r4 = (String[]) new ArrayList(this.zzaij.keySet()).toArray(zzq.aQW);
        Arrays.sort($r4);
        return $r4;
    }

    public void put(@Signature({"(", "Ljava/lang/String;", "TT;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "TT;)V"}) T $r2) throws  {
        zzab.zzag($r1);
        Object $r3 = this.zzaij.get($r1);
        if ($r3 == null) {
            this.zzaij.put($r1, $r2);
        } else if ($r3 instanceof ArrayList) {
            ((ArrayList) $r3).add($r2);
        } else {
            ArrayList $r5 = new ArrayList(4);
            $r5.add($r3);
            $r5.add($r2);
            this.zzaij.put($r1, $r5);
        }
    }

    public String toString() throws  {
        StringBuilder $r2 = zzq.zzcff();
        for (String $r1 : zzcfj()) {
            if ($r2.length() > 0) {
                $r2.append(",");
            }
            $r2.append($r1);
            $r2.append("=");
            int $i2 = zzrm($r1);
            for (int $i3 = 0; $i3 < $i2; $i3++) {
                if ($i3 > 0) {
                    $r2.append(FileUploadSession.SEPARATOR);
                }
                $r2.append(zzad($r1, $i3));
            }
            $r2.append("");
        }
        return $r2.toString();
    }

    public T zzad(@Signature({"(", "Ljava/lang/String;", "I)TT;"}) String $r1, @Signature({"(", "Ljava/lang/String;", "I)TT;"}) int $i0) throws  {
        zzab.zzag($r1);
        zzab.zzbn($i0 >= 0);
        Object $r3 = this.zzaij.get($r1);
        if ($r3 == null) {
            throw new IndexOutOfBoundsException("Size=0, requested=" + $i0);
        } else if ($r3 instanceof ArrayList) {
            ArrayList $r6 = (ArrayList) $r3;
            if ($i0 <= $r6.size()) {
                return $r6.get($i0);
            }
            throw new IndexOutOfBoundsException("Size=" + $r6.size() + ", requested=" + $i0);
        } else if ($i0 <= 0) {
            return $r3;
        } else {
            throw new IndexOutOfBoundsException("Size=1, requested=" + $i0);
        }
    }

    public int zzrm(String $r1) throws  {
        zzab.zzag($r1);
        Object $r3 = this.zzaij.get($r1);
        return $r3 == null ? 0 : $r3 instanceof ArrayList ? ((ArrayList) $r3).size() : 1;
    }
}
