package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzaws<M extends zzaws<M>> extends zzawz {
    protected zzawv cbt;

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return ix();
    }

    protected int computeSerializedSize() throws  {
        if (this.cbt == null) {
            return 0;
        }
        int $i1 = 0;
        for (int $i0 = 0; $i0 < this.cbt.size(); $i0++) {
            $i1 += this.cbt.zzask($i0).computeSerializedSize();
        }
        return $i1;
    }

    public M ix() throws CloneNotSupportedException {
        zzaws $r2 = (zzaws) super.iy();
        zzawx.zza(this, $r2);
        return $r2;
    }

    public /* synthetic */ zzawz iy() throws CloneNotSupportedException {
        return (zzaws) clone();
    }

    public void writeTo(zzawr $r1) throws IOException {
        if (this.cbt != null) {
            for (int $i0 = 0; $i0 < this.cbt.size(); $i0++) {
                this.cbt.zzask($i0).writeTo($r1);
            }
        }
    }

    public final <T> T zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzawt", "<TM;TT;>;)TT;"}) zzawt<M, T> $r1) throws  {
        if (this.cbt == null) {
            return null;
        }
        zzaww $r3 = this.cbt.zzasj(zzaxc.zzaso($r1.tag));
        return $r3 != null ? $r3.zzb($r1) : null;
    }

    protected final boolean zza(zzawq $r1, int $i0) throws IOException {
        int $i1 = $r1.getPosition();
        if (!$r1.zzart($i0)) {
            return false;
        }
        int $i2 = zzaxc.zzaso($i0);
        zzaxb $r3 = new zzaxb($i0, $r1.zzau($i1, $r1.getPosition() - $i1));
        zzaww $r4 = null;
        if (this.cbt == null) {
            this.cbt = new zzawv();
        } else {
            $r4 = this.cbt.zzasj($i2);
        }
        if ($r4 == null) {
            $r4 = new zzaww();
            this.cbt.zza($i2, $r4);
        }
        $r4.zza($r3);
        return true;
    }
}
