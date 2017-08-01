package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzawz {
    protected volatile int cbC = -1;

    public static final <T extends zzawz> T zza(@Signature({"<T:", "Lcom/google/android/gms/internal/zzawz;", ">(TT;[B)TT;"}) T $r0, @Signature({"<T:", "Lcom/google/android/gms/internal/zzawz;", ">(TT;[B)TT;"}) byte[] $r1) throws zzawy {
        return zzb($r0, $r1, 0, $r1.length);
    }

    public static final void zza(zzawz $r0, byte[] $r1, int $i0, int $i1) throws  {
        try {
            zzawr $r3 = zzawr.zzc($r1, $i0, $i1);
            $r0.writeTo($r3);
            $r3.iw();
        } catch (IOException $r4) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", $r4);
        }
    }

    public static final <T extends zzawz> T zzb(@Signature({"<T:", "Lcom/google/android/gms/internal/zzawz;", ">(TT;[BII)TT;"}) T $r0, @Signature({"<T:", "Lcom/google/android/gms/internal/zzawz;", ">(TT;[BII)TT;"}) byte[] $r1, @Signature({"<T:", "Lcom/google/android/gms/internal/zzawz;", ">(TT;[BII)TT;"}) int $i0, @Signature({"<T:", "Lcom/google/android/gms/internal/zzawz;", ">(TT;[BII)TT;"}) int $i1) throws zzawy {
        try {
            zzawq $r2 = zzawq.zzb($r1, $i0, $i1);
            $r0.mergeFrom($r2);
            $r2.zzars(0);
            return $r0;
        } catch (zzawy $r3) {
            throw $r3;
        } catch (IOException e) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
        }
    }

    public static final byte[] zzf(zzawz $r0) throws  {
        byte[] $r1 = new byte[$r0.iJ()];
        zza($r0, $r1, 0, $r1.length);
        return $r1;
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return iy();
    }

    protected int computeSerializedSize() throws  {
        return 0;
    }

    public int iI() throws  {
        if (this.cbC < 0) {
            iJ();
        }
        return this.cbC;
    }

    public int iJ() throws  {
        int $i0 = computeSerializedSize();
        this.cbC = $i0;
        return $i0;
    }

    public zzawz iy() throws CloneNotSupportedException {
        return (zzawz) super.clone();
    }

    public abstract zzawz mergeFrom(zzawq com_google_android_gms_internal_zzawq) throws IOException;

    public String toString() throws  {
        return zzaxa.zzg(this);
    }

    public void writeTo(zzawr com_google_android_gms_internal_zzawr) throws IOException {
    }
}
