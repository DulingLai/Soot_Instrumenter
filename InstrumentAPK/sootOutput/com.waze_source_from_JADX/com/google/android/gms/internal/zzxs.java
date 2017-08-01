package com.google.android.gms.internal;

import com.waze.strings.DisplayStrings;
import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzxs {

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zza extends zzawz {
        private static volatile zza[] aHz;
        public Boolean Ad;
        public Boolean aHA;
        public String name;

        public zza() throws  {
            zzcay();
        }

        public static zza[] zzcax() throws  {
            if (aHz == null) {
                synchronized (zzawx.cbB) {
                    if (aHz == null) {
                        aHz = new zza[0];
                    }
                }
            }
            return aHz;
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (this.name != null) {
                $i1 = $i0 + zzawr.zzt(1, this.name);
            }
            if (this.Ad != null) {
                $i1 += zzawr.zzn(2, this.Ad.booleanValue());
            }
            return this.aHA != null ? $i1 + zzawr.zzn(3, this.aHA.booleanValue()) : $i1;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zza)) {
                return false;
            }
            zza $r2 = (zza) $r1;
            if (this.name == null) {
                if ($r2.name != null) {
                    return false;
                }
            } else if (!this.name.equals($r2.name)) {
                return false;
            }
            if (this.Ad == null) {
                if ($r2.Ad != null) {
                    return false;
                }
            } else if (!this.Ad.equals($r2.Ad)) {
                return false;
            }
            return this.aHA == null ? $r2.aHA == null : this.aHA.equals($r2.aHA);
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.Ad == null ? 0 : this.Ad.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + ((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31)) * 31)) * 31;
            if (this.aHA != null) {
                $i0 = this.aHA.hashCode();
            }
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzbn($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.name != null) {
                $r1.zzs(1, this.name);
            }
            if (this.Ad != null) {
                $r1.zzm(2, this.Ad.booleanValue());
            }
            if (this.aHA != null) {
                $r1.zzm(3, this.aHA.booleanValue());
            }
            super.writeTo($r1);
        }

        public zza zzbn(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 10:
                        this.name = $r1.readString();
                        continue;
                    case 16:
                        this.Ad = Boolean.valueOf($r1.ik());
                        continue;
                    case 24:
                        this.aHA = Boolean.valueOf($r1.ik());
                        continue;
                    default:
                        if (!zzaxc.zzb($r1, $i0)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        public zza zzcay() throws  {
            this.name = null;
            this.Ad = null;
            this.aHA = null;
            this.cbC = -1;
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzb extends zzawz {
        public String aCF;
        public Long aHB;
        public Integer aHC;
        public zzc[] aHD;
        public zza[] aHE;
        public com.google.android.gms.internal.zzxr.zza[] aHF;

        public zzb() throws  {
            zzcaz();
        }

        protected int computeSerializedSize() throws  {
            int $i1 = super.computeSerializedSize();
            int $i2 = $i1;
            if (this.aHB != null) {
                $i2 = $i1 + zzawr.zzi(1, this.aHB.longValue());
            }
            if (this.aCF != null) {
                $i2 += zzawr.zzt(2, this.aCF);
            }
            if (this.aHC != null) {
                $i2 += zzawr.zzax(3, this.aHC.intValue());
            }
            if (this.aHD != null && this.aHD.length > 0) {
                for (zzawz $r5 : this.aHD) {
                    if ($r5 != null) {
                        $i2 += zzawr.zzc(4, $r5);
                    }
                }
            }
            if (this.aHE != null && this.aHE.length > 0) {
                for (zzawz $r7 : this.aHE) {
                    if ($r7 != null) {
                        $i2 += zzawr.zzc(5, $r7);
                    }
                }
            }
            if (this.aHF == null || this.aHF.length <= 0) {
                return $i2;
            }
            for (zzawz $r9 : this.aHF) {
                if ($r9 != null) {
                    $i2 += zzawr.zzc(6, $r9);
                }
            }
            return $i2;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzb)) {
                return false;
            }
            zzb $r2 = (zzb) $r1;
            if (this.aHB == null) {
                if ($r2.aHB != null) {
                    return false;
                }
            } else if (!this.aHB.equals($r2.aHB)) {
                return false;
            }
            if (this.aCF == null) {
                if ($r2.aCF != null) {
                    return false;
                }
            } else if (!this.aCF.equals($r2.aCF)) {
                return false;
            }
            if (this.aHC == null) {
                if ($r2.aHC != null) {
                    return false;
                }
            } else if (!this.aHC.equals($r2.aHC)) {
                return false;
            }
            if (!zzawx.equals(this.aHD, $r2.aHD)) {
                return false;
            }
            if (!zzawx.equals(this.aHE, $r2.aHE)) {
                return false;
            }
            return zzawx.equals((Object[]) this.aHF, (Object[]) $r2.aHF);
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.aCF == null ? 0 : this.aCF.hashCode()) + (((this.aHB == null ? 0 : this.aHB.hashCode()) + ((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31)) * 31)) * 31;
            if (this.aHC != null) {
                $i0 = this.aHC.hashCode();
            }
            return (((((($i1 + $i0) * 31) + zzawx.hashCode(this.aHD)) * 31) + zzawx.hashCode(this.aHE)) * 31) + zzawx.hashCode(this.aHF);
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzbo($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.aHB != null) {
                $r1.zzf(1, this.aHB.longValue());
            }
            if (this.aCF != null) {
                $r1.zzs(2, this.aCF);
            }
            if (this.aHC != null) {
                $r1.zzav(3, this.aHC.intValue());
            }
            if (this.aHD != null && this.aHD.length > 0) {
                for (zzawz $r6 : this.aHD) {
                    if ($r6 != null) {
                        $r1.zza(4, $r6);
                    }
                }
            }
            if (this.aHE != null && this.aHE.length > 0) {
                for (zzawz $r8 : this.aHE) {
                    if ($r8 != null) {
                        $r1.zza(5, $r8);
                    }
                }
            }
            if (this.aHF != null && this.aHF.length > 0) {
                for (zzawz $r10 : this.aHF) {
                    if ($r10 != null) {
                        $r1.zza(6, $r10);
                    }
                }
            }
            super.writeTo($r1);
        }

        public zzb zzbo(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                int $i2;
                switch ($i0) {
                    case 0:
                        break;
                    case 8:
                        this.aHB = Long.valueOf($r1.ih());
                        continue;
                    case 18:
                        this.aCF = $r1.readString();
                        continue;
                    case 24:
                        this.aHC = Integer.valueOf($r1.ii());
                        continue;
                    case 34:
                        $i2 = zzaxc.zzc($r1, 34);
                        $i0 = this.aHD == null ? 0 : this.aHD.length;
                        zzc[] $r5 = new zzc[($i2 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.aHD, 0, $r5, 0, $i0);
                        }
                        while ($i0 < $r5.length - 1) {
                            $r5[$i0] = new zzc();
                            $r1.zza($r5[$i0]);
                            $r1.ie();
                            $i0++;
                        }
                        $r5[$i0] = new zzc();
                        $r1.zza($r5[$i0]);
                        this.aHD = $r5;
                        continue;
                    case 42:
                        $i2 = zzaxc.zzc($r1, 42);
                        $i0 = this.aHE == null ? 0 : this.aHE.length;
                        zza[] $r8 = new zza[($i2 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.aHE, 0, $r8, 0, $i0);
                        }
                        while ($i0 < $r8.length - 1) {
                            $r8[$i0] = new zza();
                            $r1.zza($r8[$i0]);
                            $r1.ie();
                            $i0++;
                        }
                        $r8[$i0] = new zza();
                        $r1.zza($r8[$i0]);
                        this.aHE = $r8;
                        continue;
                    case 50:
                        $i2 = zzaxc.zzc($r1, 50);
                        if (this.aHF == null) {
                            $i0 = 0;
                        } else {
                            com.google.android.gms.internal.zzxr.zza[] $r11 = this.aHF;
                            $i0 = $r11.length;
                        }
                        Object $r112 = new com.google.android.gms.internal.zzxr.zza[($i2 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.aHF, 0, $r112, 0, $i0);
                        }
                        while ($i0 < $r112.length - 1) {
                            $r112[$i0] = new com.google.android.gms.internal.zzxr.zza();
                            $r1.zza($r112[$i0]);
                            $r1.ie();
                            $i0++;
                        }
                        $r112[$i0] = new com.google.android.gms.internal.zzxr.zza();
                        $r1.zza($r112[$i0]);
                        this.aHF = $r112;
                        continue;
                    default:
                        if (!zzaxc.zzb($r1, $i0)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        public zzb zzcaz() throws  {
            this.aHB = null;
            this.aCF = null;
            this.aHC = null;
            this.aHD = zzc.zzcba();
            this.aHE = zza.zzcax();
            this.aHF = com.google.android.gms.internal.zzxr.zza.zzcan();
            this.cbC = -1;
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzc extends zzawz {
        private static volatile zzc[] aHG;
        public String value;
        public String zzca;

        public zzc() throws  {
            zzcbb();
        }

        public static zzc[] zzcba() throws  {
            if (aHG == null) {
                synchronized (zzawx.cbB) {
                    if (aHG == null) {
                        aHG = new zzc[0];
                    }
                }
            }
            return aHG;
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (this.zzca != null) {
                $i1 = $i0 + zzawr.zzt(1, this.zzca);
            }
            return this.value != null ? $i1 + zzawr.zzt(2, this.value) : $i1;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzc)) {
                return false;
            }
            zzc $r2 = (zzc) $r1;
            if (this.zzca == null) {
                if ($r2.zzca != null) {
                    return false;
                }
            } else if (!this.zzca.equals($r2.zzca)) {
                return false;
            }
            return this.value == null ? $r2.value == null : this.value.equals($r2.value);
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.zzca == null ? 0 : this.zzca.hashCode()) + ((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31)) * 31;
            if (this.value != null) {
                $i0 = this.value.hashCode();
            }
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzbp($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.zzca != null) {
                $r1.zzs(1, this.zzca);
            }
            if (this.value != null) {
                $r1.zzs(2, this.value);
            }
            super.writeTo($r1);
        }

        public zzc zzbp(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 10:
                        this.zzca = $r1.readString();
                        continue;
                    case 18:
                        this.value = $r1.readString();
                        continue;
                    default:
                        if (!zzaxc.zzb($r1, $i0)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        public zzc zzcbb() throws  {
            this.zzca = null;
            this.value = null;
            this.cbC = -1;
            return this;
        }
    }
}
