package com.google.android.gms.internal;

import com.waze.strings.DisplayStrings;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zzawt<M extends zzaws<M>, T> {
    protected final Class<T> bTQ;
    protected final boolean cbu;
    public final int tag;
    protected final int type;

    private zzawt(@Signature({"(I", "Ljava/lang/Class", "<TT;>;IZ)V"}) int $i0, @Signature({"(I", "Ljava/lang/Class", "<TT;>;IZ)V"}) Class<T> $r1, @Signature({"(I", "Ljava/lang/Class", "<TT;>;IZ)V"}) int $i1, @Signature({"(I", "Ljava/lang/Class", "<TT;>;IZ)V"}) boolean $z0) throws  {
        this.type = $i0;
        this.bTQ = $r1;
        this.tag = $i1;
        this.cbu = $z0;
    }

    public static <M extends zzaws<M>, T extends zzawz> zzawt<M, T> zza(@Signature({"<M:", "Lcom/google/android/gms/internal/zzaws", "<TM;>;T:", "Lcom/google/android/gms/internal/zzawz;", ">(I", "Ljava/lang/Class", "<TT;>;J)", "Lcom/google/android/gms/internal/zzawt", "<TM;TT;>;"}) int $i0, @Signature({"<M:", "Lcom/google/android/gms/internal/zzaws", "<TM;>;T:", "Lcom/google/android/gms/internal/zzawz;", ">(I", "Ljava/lang/Class", "<TT;>;J)", "Lcom/google/android/gms/internal/zzawt", "<TM;TT;>;"}) Class<T> $r0, @Signature({"<M:", "Lcom/google/android/gms/internal/zzaws", "<TM;>;T:", "Lcom/google/android/gms/internal/zzawz;", ">(I", "Ljava/lang/Class", "<TT;>;J)", "Lcom/google/android/gms/internal/zzawt", "<TM;TT;>;"}) long $l1) throws  {
        return new zzawt($i0, $r0, (int) $l1, false);
    }

    private T zzbo(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzaxb;", ">;)TT;"}) List<zzaxb> $r1) throws  {
        int $i1;
        List $r2 = new ArrayList();
        for ($i1 = 0; $i1 < $r1.size(); $i1++) {
            zzaxb $r4 = (zzaxb) $r1.get($i1);
            if ($r4.cbD.length != 0) {
                zza($r4, $r2);
            }
        }
        $i1 = $r2.size();
        if ($i1 == 0) {
            return null;
        }
        Object $r3 = this.bTQ.cast(Array.newInstance(this.bTQ.getComponentType(), $i1));
        for (int $i0 = 0; $i0 < $i1; $i0++) {
            Array.set($r3, $i0, $r2.get($i0));
        }
        return $r3;
    }

    private T zzbp(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzaxb;", ">;)TT;"}) List<zzaxb> $r1) throws  {
        if ($r1.isEmpty()) {
            return null;
        }
        return this.bTQ.cast(zzct(zzawq.zzbi(((zzaxb) $r1.get($r1.size() - 1)).cbD)));
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if (!($r1 instanceof zzawt)) {
            return false;
        }
        zzawt $r2 = (zzawt) $r1;
        return this.type == $r2.type && this.bTQ == $r2.bTQ && this.tag == $r2.tag && this.cbu == $r2.cbu;
    }

    public int hashCode() throws  {
        return (this.cbu ? (byte) 1 : (byte) 0) + ((((((this.type + DisplayStrings.DS_NO_MUTUAL_FRIENDS) * 31) + this.bTQ.hashCode()) * 31) + this.tag) * 31);
    }

    protected void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzaxb;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)V"}) zzaxb $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzaxb;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)V"}) List<Object> $r2) throws  {
        $r2.add(zzct(zzawq.zzbi($r1.cbD)));
    }

    void zza(Object $r1, zzawr $r2) throws IOException {
        if (this.cbu) {
            zzc($r1, $r2);
        } else {
            zzb($r1, $r2);
        }
    }

    protected void zzb(Object $r2, zzawr $r1) throws  {
        try {
            $r1.zzasf(this.tag);
            switch (this.type) {
                case 10:
                    zzawz $r8 = (zzawz) $r2;
                    int $i0 = zzaxc.zzaso(this.tag);
                    $r1.zzb($r8);
                    $r1.zzaz($i0, 4);
                    return;
                case 11:
                    $r1.zzc((zzawz) $r2);
                    return;
                default:
                    throw new IllegalArgumentException("Unknown type " + this.type);
            }
        } catch (IOException $r6) {
            throw new IllegalStateException($r6);
        }
        throw new IllegalStateException($r6);
    }

    final T zzbn(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzaxb;", ">;)TT;"}) List<zzaxb> $r1) throws  {
        return $r1 == null ? null : this.cbu ? zzbo($r1) : zzbp($r1);
    }

    protected void zzc(Object $r1, zzawr $r2) throws  {
        int $i0 = Array.getLength($r1);
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            Object $r3 = Array.get($r1, $i1);
            if ($r3 != null) {
                zzb($r3, $r2);
            }
        }
    }

    protected Object zzct(zzawq $r1) throws  {
        String $r5;
        Class $r2 = this.cbu ? this.bTQ.getComponentType() : this.bTQ;
        zzawz $r9;
        switch (this.type) {
            case 10:
                $r9 = (zzawz) $r2.newInstance();
                $r1.zza($r9, zzaxc.zzaso(this.tag));
                return $r9;
            case 11:
                $r9 = (zzawz) $r2.newInstance();
                $r1.zza($r9);
                return $r9;
            default:
                try {
                    throw new IllegalArgumentException("Unknown type " + this.type);
                } catch (InstantiationException $r6) {
                    $r5 = String.valueOf($r2);
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf($r5).length() + 33).append("Error creating instance of class ").append($r5).toString(), $r6);
                } catch (IllegalAccessException $r10) {
                    $r5 = String.valueOf($r2);
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf($r5).length() + 33).append("Error creating instance of class ").append($r5).toString(), $r10);
                } catch (IOException $r11) {
                    throw new IllegalArgumentException("Error reading extension field", $r11);
                }
        }
    }

    int zzdf(Object $r1) throws  {
        return this.cbu ? zzdg($r1) : zzdh($r1);
    }

    protected int zzdg(Object $r1) throws  {
        int $i0 = 0;
        int $i1 = Array.getLength($r1);
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            if (Array.get($r1, $i2) != null) {
                $i0 += zzdh(Array.get($r1, $i2));
            }
        }
        return $i0;
    }

    protected int zzdh(Object $r1) throws  {
        int $i0 = zzaxc.zzaso(this.tag);
        switch (this.type) {
            case 10:
                return zzawr.zzb($i0, (zzawz) $r1);
            case 11:
                return zzawr.zzc($i0, (zzawz) $r1);
            default:
                throw new IllegalArgumentException("Unknown type " + this.type);
        }
    }
}
