package com.google.android.gms.internal;

import com.waze.strings.DisplayStrings;
import java.io.IOException;
import java.util.Arrays;

/* compiled from: dalvik_source_com.waze.apk */
final class zzaxb {
    final byte[] cbD;
    final int tag;

    zzaxb(int $i0, byte[] $r1) throws  {
        this.tag = $i0;
        this.cbD = $r1;
    }

    int computeSerializedSize() throws  {
        return (zzawr.zzasg(this.tag) + 0) + this.cbD.length;
    }

    public boolean equals(Object $r1) throws  {
        if ($r1 == this) {
            return true;
        }
        if (!($r1 instanceof zzaxb)) {
            return false;
        }
        zzaxb $r2 = (zzaxb) $r1;
        return this.tag == $r2.tag && Arrays.equals(this.cbD, $r2.cbD);
    }

    public int hashCode() throws  {
        return ((this.tag + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + Arrays.hashCode(this.cbD);
    }

    void writeTo(zzawr $r1) throws IOException {
        $r1.zzasf(this.tag);
        $r1.zzbm(this.cbD);
    }
}
