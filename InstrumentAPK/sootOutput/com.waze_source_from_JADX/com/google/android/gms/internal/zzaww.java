package com.google.android.gms.internal;

import com.waze.strings.DisplayStrings;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
class zzaww implements Cloneable {
    private Object aVj;
    private List<zzaxb> cbA = new ArrayList();
    private zzawt<?, ?> cbz;

    zzaww() throws  {
    }

    private byte[] toByteArray() throws IOException {
        byte[] $r1 = new byte[computeSerializedSize()];
        writeTo(zzawr.zzbj($r1));
        return $r1;
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return iA();
    }

    int computeSerializedSize() throws  {
        if (this.aVj != null) {
            return this.cbz.zzdf(this.aVj);
        }
        int $i0 = 0;
        for (zzaxb computeSerializedSize : this.cbA) {
            $i0 = computeSerializedSize.computeSerializedSize() + $i0;
        }
        return $i0;
    }

    public boolean equals(Object $r2) throws  {
        if ($r2 == this) {
            return true;
        }
        if (!($r2 instanceof zzaww)) {
            return false;
        }
        zzaww $r3 = (zzaww) $r2;
        if (this.aVj == null || $r3.aVj == null) {
            if (this.cbA == null || $r3.cbA == null) {
                try {
                    return Arrays.equals(toByteArray(), $r3.toByteArray());
                } catch (Throwable $r23) {
                    throw new IllegalStateException($r23);
                }
            }
            return this.cbA.equals($r3.cbA);
        } else if (this.cbz != $r3.cbz) {
            return false;
        } else {
            if (this.cbz.bTQ.isArray()) {
                Object $r22 = this.aVj;
                if ($r22 instanceof byte[]) {
                    return Arrays.equals((byte[]) this.aVj, (byte[]) $r3.aVj);
                }
                $r22 = this.aVj;
                if ($r22 instanceof int[]) {
                    return Arrays.equals((int[]) this.aVj, (int[]) $r3.aVj);
                }
                $r22 = this.aVj;
                if ($r22 instanceof long[]) {
                    return Arrays.equals((long[]) this.aVj, (long[]) $r3.aVj);
                }
                $r22 = this.aVj;
                if ($r22 instanceof float[]) {
                    return Arrays.equals((float[]) this.aVj, (float[]) $r3.aVj);
                }
                $r22 = this.aVj;
                if ($r22 instanceof double[]) {
                    return Arrays.equals((double[]) this.aVj, (double[]) $r3.aVj);
                }
                $r22 = this.aVj;
                if ($r22 instanceof boolean[]) {
                    return Arrays.equals((boolean[]) this.aVj, (boolean[]) $r3.aVj);
                }
                return Arrays.deepEquals((Object[]) this.aVj, (Object[]) $r3.aVj);
            }
            return this.aVj.equals($r3.aVj);
        }
    }

    public int hashCode() throws  {
        try {
            return Arrays.hashCode(toByteArray()) + DisplayStrings.DS_P2_1F_HOURS_AGO_UC;
        } catch (IOException $r3) {
            throw new IllegalStateException($r3);
        }
    }

    public final zzaww iA() throws  {
        zzaww $r1 = new zzaww();
        $r1.cbz = this.cbz;
        if (this.cbA == null) {
            $r1.cbA = null;
        } else {
            try {
                $r1.cbA.addAll(this.cbA);
            } catch (CloneNotSupportedException $r6) {
                throw new AssertionError($r6);
            }
        }
        if (this.aVj == null) {
            return $r1;
        }
        if (this.aVj instanceof zzawz) {
            $r1.aVj = (zzawz) ((zzawz) this.aVj).clone();
            return $r1;
        } else if (this.aVj instanceof byte[]) {
            $r1.aVj = ((byte[]) this.aVj).clone();
            return $r1;
        } else if (this.aVj instanceof byte[][]) {
            byte[][] $r10 = (byte[][]) this.aVj;
            byte[][] $r11 = $r10.length;
            $i0 = $r11;
            $r11 = new byte[$r11][];
            byte[][] $r112 = $r11;
            $r1.aVj = $r11;
            for ($i0 = 0; $i0 < $r10.length; $i0++) {
                $r112[$i0] = (byte[]) $r10[$i0].clone();
            }
            return $r1;
        } else if (this.aVj instanceof boolean[]) {
            $r1.aVj = ((boolean[]) this.aVj).clone();
            return $r1;
        } else if (this.aVj instanceof int[]) {
            $r1.aVj = ((int[]) this.aVj).clone();
            return $r1;
        } else if (this.aVj instanceof long[]) {
            $r1.aVj = ((long[]) this.aVj).clone();
            return $r1;
        } else if (this.aVj instanceof float[]) {
            $r1.aVj = ((float[]) this.aVj).clone();
            return $r1;
        } else if (this.aVj instanceof double[]) {
            $r1.aVj = ((double[]) this.aVj).clone();
            return $r1;
        } else if (!(this.aVj instanceof zzawz[])) {
            return $r1;
        } else {
            zzawz[] $r17 = (zzawz[]) this.aVj;
            zzawz[] $r18 = $r17.length;
            $i0 = $r18;
            $r18 = new zzawz[$r18];
            zzawz[] $r182 = $r18;
            $r1.aVj = $r18;
            for ($i0 = 0; $i0 < $r17.length; $i0++) {
                $r182[$i0] = (zzawz) $r17[$i0].clone();
            }
            return $r1;
        }
    }

    void writeTo(zzawr $r1) throws IOException {
        if (this.aVj != null) {
            this.cbz.zza(this.aVj, $r1);
            return;
        }
        for (zzaxb writeTo : this.cbA) {
            writeTo.writeTo($r1);
        }
    }

    void zza(zzaxb $r1) throws  {
        this.cbA.add($r1);
    }

    zzaxb zzasm(int $i0) throws  {
        return this.cbA == null ? null : $i0 < this.cbA.size() ? (zzaxb) this.cbA.get($i0) : null;
    }

    <T> T zzb(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzawt", "<*TT;>;)TT;"}) zzawt<?, T> $r1) throws  {
        if (this.aVj == null) {
            this.cbz = $r1;
            this.aVj = $r1.zzbn(this.cbA);
            this.cbA = null;
        } else if (!this.cbz.equals($r1)) {
            throw new IllegalStateException("Tried to getExtension with a different Extension.");
        }
        return this.aVj;
    }
}
