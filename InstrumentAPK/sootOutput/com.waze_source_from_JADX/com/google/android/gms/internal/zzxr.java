package com.google.android.gms.internal;

import com.waze.strings.DisplayStrings;
import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzxr {

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zza extends zzawz {
        private static volatile zza[] aGY;
        public Integer aGZ;
        public zze[] aHa;
        public zzb[] aHb;

        public zza() throws  {
            zzcao();
        }

        public static zza[] zzcan() throws  {
            if (aGY == null) {
                synchronized (zzawx.cbB) {
                    if (aGY == null) {
                        aGY = new zza[0];
                    }
                }
            }
            return aGY;
        }

        protected int computeSerializedSize() throws  {
            int $i1 = super.computeSerializedSize();
            int $i2 = $i1;
            if (this.aGZ != null) {
                $i2 = $i1 + zzawr.zzax(1, this.aGZ.intValue());
            }
            if (this.aHa != null && this.aHa.length > 0) {
                for (zzawz $r3 : this.aHa) {
                    if ($r3 != null) {
                        $i2 += zzawr.zzc(2, $r3);
                    }
                }
            }
            if (this.aHb == null || this.aHb.length <= 0) {
                return $i2;
            }
            for (zzawz $r5 : this.aHb) {
                if ($r5 != null) {
                    $i2 += zzawr.zzc(3, $r5);
                }
            }
            return $i2;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zza)) {
                return false;
            }
            zza $r2 = (zza) $r1;
            if (this.aGZ == null) {
                if ($r2.aGZ != null) {
                    return false;
                }
            } else if (!this.aGZ.equals($r2.aGZ)) {
                return false;
            }
            return !zzawx.equals(this.aHa, $r2.aHa) ? false : zzawx.equals(this.aHb, $r2.aHb);
        }

        public int hashCode() throws  {
            return (((((this.aGZ == null ? 0 : this.aGZ.hashCode()) + ((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31)) * 31) + zzawx.hashCode(this.aHa)) * 31) + zzawx.hashCode(this.aHb);
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzbh($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.aGZ != null) {
                $r1.zzav(1, this.aGZ.intValue());
            }
            if (this.aHa != null && this.aHa.length > 0) {
                for (zzawz $r4 : this.aHa) {
                    if ($r4 != null) {
                        $r1.zza(2, $r4);
                    }
                }
            }
            if (this.aHb != null && this.aHb.length > 0) {
                for (zzawz $r6 : this.aHb) {
                    if ($r6 != null) {
                        $r1.zza(3, $r6);
                    }
                }
            }
            super.writeTo($r1);
        }

        public zza zzbh(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                int $i1;
                switch ($i0) {
                    case 0:
                        break;
                    case 8:
                        this.aGZ = Integer.valueOf($r1.ii());
                        continue;
                    case 18:
                        $i1 = zzaxc.zzc($r1, 18);
                        $i0 = this.aHa == null ? 0 : this.aHa.length;
                        zze[] $r3 = new zze[($i1 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.aHa, 0, $r3, 0, $i0);
                        }
                        while ($i0 < $r3.length - 1) {
                            $r3[$i0] = new zze();
                            $r1.zza($r3[$i0]);
                            $r1.ie();
                            $i0++;
                        }
                        $r3[$i0] = new zze();
                        $r1.zza($r3[$i0]);
                        this.aHa = $r3;
                        continue;
                    case 26:
                        $i1 = zzaxc.zzc($r1, 26);
                        $i0 = this.aHb == null ? 0 : this.aHb.length;
                        zzb[] $r6 = new zzb[($i1 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.aHb, 0, $r6, 0, $i0);
                        }
                        while ($i0 < $r6.length - 1) {
                            $r6[$i0] = new zzb();
                            $r1.zza($r6[$i0]);
                            $r1.ie();
                            $i0++;
                        }
                        $r6[$i0] = new zzb();
                        $r1.zza($r6[$i0]);
                        this.aHb = $r6;
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

        public zza zzcao() throws  {
            this.aGZ = null;
            this.aHa = zze.zzcau();
            this.aHb = zzb.zzcap();
            this.cbC = -1;
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzb extends zzawz {
        private static volatile zzb[] aHc;
        public Integer aHd;
        public String aHe;
        public zzc[] aHf;
        public Boolean aHg;
        public zzd aHh;

        public zzb() throws  {
            zzcaq();
        }

        public static zzb[] zzcap() throws  {
            if (aHc == null) {
                synchronized (zzawx.cbB) {
                    if (aHc == null) {
                        aHc = new zzb[0];
                    }
                }
            }
            return aHc;
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (this.aHd != null) {
                $i1 = $i0 + zzawr.zzax(1, this.aHd.intValue());
            }
            if (this.aHe != null) {
                $i1 += zzawr.zzt(2, this.aHe);
            }
            if (this.aHf != null && this.aHf.length > 0) {
                for (zzawz $r4 : this.aHf) {
                    if ($r4 != null) {
                        $i1 += zzawr.zzc(3, $r4);
                    }
                }
            }
            if (this.aHg != null) {
                $i1 += zzawr.zzn(4, this.aHg.booleanValue());
            }
            return this.aHh != null ? $i1 + zzawr.zzc(5, this.aHh) : $i1;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzb)) {
                return false;
            }
            zzb $r2 = (zzb) $r1;
            if (this.aHd == null) {
                if ($r2.aHd != null) {
                    return false;
                }
            } else if (!this.aHd.equals($r2.aHd)) {
                return false;
            }
            if (this.aHe == null) {
                if ($r2.aHe != null) {
                    return false;
                }
            } else if (!this.aHe.equals($r2.aHe)) {
                return false;
            }
            if (!zzawx.equals(this.aHf, $r2.aHf)) {
                return false;
            }
            if (this.aHg == null) {
                if ($r2.aHg != null) {
                    return false;
                }
            } else if (!this.aHg.equals($r2.aHg)) {
                return false;
            }
            return this.aHh == null ? $r2.aHh == null : this.aHh.equals($r2.aHh);
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.aHg == null ? 0 : this.aHg.hashCode()) + (((((this.aHe == null ? 0 : this.aHe.hashCode()) + (((this.aHd == null ? 0 : this.aHd.hashCode()) + ((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31)) * 31)) * 31) + zzawx.hashCode(this.aHf)) * 31)) * 31;
            if (this.aHh != null) {
                $i0 = this.aHh.hashCode();
            }
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzbi($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.aHd != null) {
                $r1.zzav(1, this.aHd.intValue());
            }
            if (this.aHe != null) {
                $r1.zzs(2, this.aHe);
            }
            if (this.aHf != null && this.aHf.length > 0) {
                for (zzawz $r5 : this.aHf) {
                    if ($r5 != null) {
                        $r1.zza(3, $r5);
                    }
                }
            }
            if (this.aHg != null) {
                $r1.zzm(4, this.aHg.booleanValue());
            }
            if (this.aHh != null) {
                $r1.zza(5, this.aHh);
            }
            super.writeTo($r1);
        }

        public zzb zzbi(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 8:
                        this.aHd = Integer.valueOf($r1.ii());
                        continue;
                    case 18:
                        this.aHe = $r1.readString();
                        continue;
                    case 26:
                        int $i1 = zzaxc.zzc($r1, 26);
                        $i0 = this.aHf == null ? 0 : this.aHf.length;
                        zzc[] $r4 = new zzc[($i1 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.aHf, 0, $r4, 0, $i0);
                        }
                        while ($i0 < $r4.length - 1) {
                            $r4[$i0] = new zzc();
                            $r1.zza($r4[$i0]);
                            $r1.ie();
                            $i0++;
                        }
                        $r4[$i0] = new zzc();
                        $r1.zza($r4[$i0]);
                        this.aHf = $r4;
                        continue;
                    case 32:
                        this.aHg = Boolean.valueOf($r1.ik());
                        continue;
                    case 42:
                        if (this.aHh == null) {
                            this.aHh = new zzd();
                        }
                        $r1.zza(this.aHh);
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

        public zzb zzcaq() throws  {
            this.aHd = null;
            this.aHe = null;
            this.aHf = zzc.zzcar();
            this.aHg = null;
            this.aHh = null;
            this.cbC = -1;
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzc extends zzawz {
        private static volatile zzc[] aHi;
        public zzf aHj;
        public zzd aHk;
        public Boolean aHl;
        public String aHm;

        public zzc() throws  {
            zzcas();
        }

        public static zzc[] zzcar() throws  {
            if (aHi == null) {
                synchronized (zzawx.cbB) {
                    if (aHi == null) {
                        aHi = new zzc[0];
                    }
                }
            }
            return aHi;
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (this.aHj != null) {
                $i1 = $i0 + zzawr.zzc(1, this.aHj);
            }
            if (this.aHk != null) {
                $i1 += zzawr.zzc(2, this.aHk);
            }
            if (this.aHl != null) {
                $i1 += zzawr.zzn(3, this.aHl.booleanValue());
            }
            return this.aHm != null ? $i1 + zzawr.zzt(4, this.aHm) : $i1;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzc)) {
                return false;
            }
            zzc $r2 = (zzc) $r1;
            if (this.aHj == null) {
                if ($r2.aHj != null) {
                    return false;
                }
            } else if (!this.aHj.equals($r2.aHj)) {
                return false;
            }
            if (this.aHk == null) {
                if ($r2.aHk != null) {
                    return false;
                }
            } else if (!this.aHk.equals($r2.aHk)) {
                return false;
            }
            if (this.aHl == null) {
                if ($r2.aHl != null) {
                    return false;
                }
            } else if (!this.aHl.equals($r2.aHl)) {
                return false;
            }
            return this.aHm == null ? $r2.aHm == null : this.aHm.equals($r2.aHm);
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.aHl == null ? 0 : this.aHl.hashCode()) + (((this.aHk == null ? 0 : this.aHk.hashCode()) + (((this.aHj == null ? 0 : this.aHj.hashCode()) + ((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31)) * 31)) * 31)) * 31;
            if (this.aHm != null) {
                $i0 = this.aHm.hashCode();
            }
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzbj($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.aHj != null) {
                $r1.zza(1, this.aHj);
            }
            if (this.aHk != null) {
                $r1.zza(2, this.aHk);
            }
            if (this.aHl != null) {
                $r1.zzm(3, this.aHl.booleanValue());
            }
            if (this.aHm != null) {
                $r1.zzs(4, this.aHm);
            }
            super.writeTo($r1);
        }

        public zzc zzbj(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 10:
                        if (this.aHj == null) {
                            this.aHj = new zzf();
                        }
                        $r1.zza(this.aHj);
                        continue;
                    case 18:
                        if (this.aHk == null) {
                            this.aHk = new zzd();
                        }
                        $r1.zza(this.aHk);
                        continue;
                    case 24:
                        this.aHl = Boolean.valueOf($r1.ik());
                        continue;
                    case 34:
                        this.aHm = $r1.readString();
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

        public zzc zzcas() throws  {
            this.aHj = null;
            this.aHk = null;
            this.aHl = null;
            this.aHm = null;
            this.cbC = -1;
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzd extends zzawz {
        public Integer aHn;
        public Boolean aHo;
        public String aHp;
        public String aHq;
        public String aHr;

        public zzd() throws  {
            zzcat();
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (this.aHn != null) {
                $i1 = $i0 + zzawr.zzax(1, this.aHn.intValue());
            }
            if (this.aHo != null) {
                $i1 += zzawr.zzn(2, this.aHo.booleanValue());
            }
            if (this.aHp != null) {
                $i1 += zzawr.zzt(3, this.aHp);
            }
            if (this.aHq != null) {
                $i1 += zzawr.zzt(4, this.aHq);
            }
            return this.aHr != null ? $i1 + zzawr.zzt(5, this.aHr) : $i1;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzd)) {
                return false;
            }
            zzd $r2 = (zzd) $r1;
            if (this.aHn == null) {
                if ($r2.aHn != null) {
                    return false;
                }
            } else if (!this.aHn.equals($r2.aHn)) {
                return false;
            }
            if (this.aHo == null) {
                if ($r2.aHo != null) {
                    return false;
                }
            } else if (!this.aHo.equals($r2.aHo)) {
                return false;
            }
            if (this.aHp == null) {
                if ($r2.aHp != null) {
                    return false;
                }
            } else if (!this.aHp.equals($r2.aHp)) {
                return false;
            }
            if (this.aHq == null) {
                if ($r2.aHq != null) {
                    return false;
                }
            } else if (!this.aHq.equals($r2.aHq)) {
                return false;
            }
            return this.aHr == null ? $r2.aHr == null : this.aHr.equals($r2.aHr);
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.aHq == null ? 0 : this.aHq.hashCode()) + (((this.aHp == null ? 0 : this.aHp.hashCode()) + (((this.aHo == null ? 0 : this.aHo.hashCode()) + (((this.aHn == null ? 0 : this.aHn.intValue()) + ((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31)) * 31)) * 31)) * 31)) * 31;
            if (this.aHr != null) {
                $i0 = this.aHr.hashCode();
            }
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzbk($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.aHn != null) {
                $r1.zzav(1, this.aHn.intValue());
            }
            if (this.aHo != null) {
                $r1.zzm(2, this.aHo.booleanValue());
            }
            if (this.aHp != null) {
                $r1.zzs(3, this.aHp);
            }
            if (this.aHq != null) {
                $r1.zzs(4, this.aHq);
            }
            if (this.aHr != null) {
                $r1.zzs(5, this.aHr);
            }
            super.writeTo($r1);
        }

        public zzd zzbk(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 8:
                        $i0 = $r1.ii();
                        switch ($i0) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                                this.aHn = Integer.valueOf($i0);
                                break;
                            default:
                                continue;
                        }
                    case 16:
                        this.aHo = Boolean.valueOf($r1.ik());
                        continue;
                    case 26:
                        this.aHp = $r1.readString();
                        continue;
                    case 34:
                        this.aHq = $r1.readString();
                        continue;
                    case 42:
                        this.aHr = $r1.readString();
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

        public zzd zzcat() throws  {
            this.aHo = null;
            this.aHp = null;
            this.aHq = null;
            this.aHr = null;
            this.cbC = -1;
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zze extends zzawz {
        private static volatile zze[] aHs;
        public Integer aHd;
        public String aHt;
        public zzc aHu;

        public zze() throws  {
            zzcav();
        }

        public static zze[] zzcau() throws  {
            if (aHs == null) {
                synchronized (zzawx.cbB) {
                    if (aHs == null) {
                        aHs = new zze[0];
                    }
                }
            }
            return aHs;
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (this.aHd != null) {
                $i1 = $i0 + zzawr.zzax(1, this.aHd.intValue());
            }
            if (this.aHt != null) {
                $i1 += zzawr.zzt(2, this.aHt);
            }
            return this.aHu != null ? $i1 + zzawr.zzc(3, this.aHu) : $i1;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zze)) {
                return false;
            }
            zze $r2 = (zze) $r1;
            if (this.aHd == null) {
                if ($r2.aHd != null) {
                    return false;
                }
            } else if (!this.aHd.equals($r2.aHd)) {
                return false;
            }
            if (this.aHt == null) {
                if ($r2.aHt != null) {
                    return false;
                }
            } else if (!this.aHt.equals($r2.aHt)) {
                return false;
            }
            return this.aHu == null ? $r2.aHu == null : this.aHu.equals($r2.aHu);
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.aHt == null ? 0 : this.aHt.hashCode()) + (((this.aHd == null ? 0 : this.aHd.hashCode()) + ((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31)) * 31)) * 31;
            if (this.aHu != null) {
                $i0 = this.aHu.hashCode();
            }
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzbl($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.aHd != null) {
                $r1.zzav(1, this.aHd.intValue());
            }
            if (this.aHt != null) {
                $r1.zzs(2, this.aHt);
            }
            if (this.aHu != null) {
                $r1.zza(3, this.aHu);
            }
            super.writeTo($r1);
        }

        public zze zzbl(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 8:
                        this.aHd = Integer.valueOf($r1.ii());
                        continue;
                    case 18:
                        this.aHt = $r1.readString();
                        continue;
                    case 26:
                        if (this.aHu == null) {
                            this.aHu = new zzc();
                        }
                        $r1.zza(this.aHu);
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

        public zze zzcav() throws  {
            this.aHd = null;
            this.aHt = null;
            this.aHu = null;
            this.cbC = -1;
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzf extends zzawz {
        public Integer aHv;
        public String aHw;
        public Boolean aHx;
        public String[] aHy;

        public zzf() throws  {
            zzcaw();
        }

        protected int computeSerializedSize() throws  {
            int $i1 = super.computeSerializedSize();
            int $i2 = $i1;
            if (this.aHv != null) {
                $i2 = $i1 + zzawr.zzax(1, this.aHv.intValue());
            }
            if (this.aHw != null) {
                $i2 += zzawr.zzt(2, this.aHw);
            }
            if (this.aHx != null) {
                $i2 += zzawr.zzn(3, this.aHx.booleanValue());
            }
            if (this.aHy == null || this.aHy.length <= 0) {
                return $i2;
            }
            int $i3 = 0;
            $i1 = 0;
            for (String $r2 : this.aHy) {
                if ($r2 != null) {
                    $i1++;
                    $i3 += zzawr.zzyu($r2);
                }
            }
            return ($i2 + $i3) + ($i1 * 1);
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzf)) {
                return false;
            }
            zzf $r2 = (zzf) $r1;
            if (this.aHv == null) {
                if ($r2.aHv != null) {
                    return false;
                }
            } else if (!this.aHv.equals($r2.aHv)) {
                return false;
            }
            if (this.aHw == null) {
                if ($r2.aHw != null) {
                    return false;
                }
            } else if (!this.aHw.equals($r2.aHw)) {
                return false;
            }
            if (this.aHx == null) {
                if ($r2.aHx != null) {
                    return false;
                }
            } else if (!this.aHx.equals($r2.aHx)) {
                return false;
            }
            return zzawx.equals(this.aHy, $r2.aHy);
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.aHw == null ? 0 : this.aHw.hashCode()) + (((this.aHv == null ? 0 : this.aHv.intValue()) + ((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31)) * 31)) * 31;
            if (this.aHx != null) {
                $i0 = this.aHx.hashCode();
            }
            return (($i1 + $i0) * 31) + zzawx.hashCode(this.aHy);
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzbm($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.aHv != null) {
                $r1.zzav(1, this.aHv.intValue());
            }
            if (this.aHw != null) {
                $r1.zzs(2, this.aHw);
            }
            if (this.aHx != null) {
                $r1.zzm(3, this.aHx.booleanValue());
            }
            if (this.aHy != null && this.aHy.length > 0) {
                for (String $r3 : this.aHy) {
                    if ($r3 != null) {
                        $r1.zzs(4, $r3);
                    }
                }
            }
            super.writeTo($r1);
        }

        public zzf zzbm(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 8:
                        $i0 = $r1.ii();
                        switch ($i0) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                                this.aHv = Integer.valueOf($i0);
                                break;
                            default:
                                continue;
                        }
                    case 18:
                        this.aHw = $r1.readString();
                        continue;
                    case 24:
                        this.aHx = Boolean.valueOf($r1.ik());
                        continue;
                    case 34:
                        int $i1 = zzaxc.zzc($r1, 34);
                        $i0 = this.aHy == null ? 0 : this.aHy.length;
                        String[] $r5 = new String[($i1 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.aHy, 0, $r5, 0, $i0);
                        }
                        while ($i0 < $r5.length - 1) {
                            $r5[$i0] = $r1.readString();
                            $r1.ie();
                            $i0++;
                        }
                        $r5[$i0] = $r1.readString();
                        this.aHy = $r5;
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

        public zzf zzcaw() throws  {
            this.aHw = null;
            this.aHx = null;
            this.aHy = zzaxc.cbJ;
            this.cbC = -1;
            return this;
        }
    }
}
