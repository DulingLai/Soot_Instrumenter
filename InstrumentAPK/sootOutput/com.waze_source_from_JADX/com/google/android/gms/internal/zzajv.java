package com.google.android.gms.internal;

import com.google.android.gms.internal.zzah.zzf;
import com.google.android.gms.internal.zzah.zzj;
import com.waze.strings.DisplayStrings;
import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzajv {

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zza extends zzaws<zza> {
        public long brf;
        public zzj brg;
        public zzf zzwq;

        public zza() throws  {
            zzcvj();
        }

        public static zza zzax(byte[] $r0) throws zzawy {
            return (zza) zzawz.zza(new zza(), $r0);
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize() + zzawr.zzi(1, this.brf);
            if (this.zzwq != null) {
                $i0 += zzawr.zzc(2, this.zzwq);
            }
            return this.brg != null ? $i0 + zzawr.zzc(3, this.brg) : $i0;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zza)) {
                return false;
            }
            zza $r2 = (zza) $r1;
            if (this.brf != $r2.brf) {
                return false;
            }
            if (this.zzwq == null) {
                if ($r2.zzwq != null) {
                    return false;
                }
            } else if (!this.zzwq.equals($r2.zzwq)) {
                return false;
            }
            if (this.brg == null) {
                if ($r2.brg != null) {
                    return false;
                }
            } else if (!this.brg.equals($r2.brg)) {
                return false;
            }
            if (this.cbt == null || this.cbt.isEmpty()) {
                return $r2.cbt == null || $r2.cbt.isEmpty();
            } else {
                zzawv $r5 = this.cbt;
                zzawv $r8 = $r2.cbt;
                return $r5.equals($r8);
            }
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.brg == null ? 0 : this.brg.hashCode()) + (((this.zzwq == null ? 0 : this.zzwq.hashCode()) + ((((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + ((int) (this.brf ^ (this.brf >>> 32)))) * 31)) * 31)) * 31;
            if (!(this.cbt == null || this.cbt.isEmpty())) {
                $i0 = this.cbt.hashCode();
            }
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzbx($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            $r1.zzf(1, this.brf);
            if (this.zzwq != null) {
                $r1.zza(2, this.zzwq);
            }
            if (this.brg != null) {
                $r1.zza(3, this.brg);
            }
            super.writeTo($r1);
        }

        public zza zzbx(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 8:
                        this.brf = $r1.ih();
                        continue;
                    case 18:
                        if (this.zzwq == null) {
                            this.zzwq = new zzf();
                        }
                        $r1.zza(this.zzwq);
                        continue;
                    case 26:
                        if (this.brg == null) {
                            this.brg = new zzj();
                        }
                        $r1.zza(this.brg);
                        continue;
                    default:
                        if (!super.zza($r1, $i0)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        public zza zzcvj() throws  {
            this.brf = 0;
            this.zzwq = null;
            this.brg = null;
            this.cbt = null;
            this.cbC = -1;
            return this;
        }
    }
}
