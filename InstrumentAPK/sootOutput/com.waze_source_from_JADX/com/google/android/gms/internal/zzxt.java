package com.google.android.gms.internal;

import com.waze.strings.DisplayStrings;
import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzxt {

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zza extends zzawz {
        private static volatile zza[] aHH;
        public Integer aGZ;
        public zzf aHI;
        public zzf aHJ;
        public Boolean aHK;

        public zza() throws  {
            zzcbd();
        }

        public static zza[] zzcbc() throws  {
            if (aHH == null) {
                synchronized (zzawx.cbB) {
                    if (aHH == null) {
                        aHH = new zza[0];
                    }
                }
            }
            return aHH;
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (this.aGZ != null) {
                $i1 = $i0 + zzawr.zzax(1, this.aGZ.intValue());
            }
            if (this.aHI != null) {
                $i1 += zzawr.zzc(2, this.aHI);
            }
            if (this.aHJ != null) {
                $i1 += zzawr.zzc(3, this.aHJ);
            }
            return this.aHK != null ? $i1 + zzawr.zzn(4, this.aHK.booleanValue()) : $i1;
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
            if (this.aHI == null) {
                if ($r2.aHI != null) {
                    return false;
                }
            } else if (!this.aHI.equals($r2.aHI)) {
                return false;
            }
            if (this.aHJ == null) {
                if ($r2.aHJ != null) {
                    return false;
                }
            } else if (!this.aHJ.equals($r2.aHJ)) {
                return false;
            }
            return this.aHK == null ? $r2.aHK == null : this.aHK.equals($r2.aHK);
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.aHJ == null ? 0 : this.aHJ.hashCode()) + (((this.aHI == null ? 0 : this.aHI.hashCode()) + (((this.aGZ == null ? 0 : this.aGZ.hashCode()) + ((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31)) * 31)) * 31)) * 31;
            if (this.aHK != null) {
                $i0 = this.aHK.hashCode();
            }
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzbq($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.aGZ != null) {
                $r1.zzav(1, this.aGZ.intValue());
            }
            if (this.aHI != null) {
                $r1.zza(2, this.aHI);
            }
            if (this.aHJ != null) {
                $r1.zza(3, this.aHJ);
            }
            if (this.aHK != null) {
                $r1.zzm(4, this.aHK.booleanValue());
            }
            super.writeTo($r1);
        }

        public zza zzbq(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 8:
                        this.aGZ = Integer.valueOf($r1.ii());
                        continue;
                    case 18:
                        if (this.aHI == null) {
                            this.aHI = new zzf();
                        }
                        $r1.zza(this.aHI);
                        continue;
                    case 26:
                        if (this.aHJ == null) {
                            this.aHJ = new zzf();
                        }
                        $r1.zza(this.aHJ);
                        continue;
                    case 32:
                        this.aHK = Boolean.valueOf($r1.ik());
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

        public zza zzcbd() throws  {
            this.aGZ = null;
            this.aHI = null;
            this.aHJ = null;
            this.aHK = null;
            this.cbC = -1;
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzb extends zzawz {
        private static volatile zzb[] aHL;
        public zzc[] aHM;
        public Long aHN;
        public Long aHO;
        public Integer count;
        public String name;

        public zzb() throws  {
            zzcbf();
        }

        public static zzb[] zzcbe() throws  {
            if (aHL == null) {
                synchronized (zzawx.cbB) {
                    if (aHL == null) {
                        aHL = new zzb[0];
                    }
                }
            }
            return aHL;
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            if (this.aHM != null && this.aHM.length > 0) {
                for (zzawz $r2 : this.aHM) {
                    if ($r2 != null) {
                        $i0 += zzawr.zzc(1, $r2);
                    }
                }
            }
            if (this.name != null) {
                $i0 += zzawr.zzt(2, this.name);
            }
            if (this.aHN != null) {
                $i0 += zzawr.zzi(3, this.aHN.longValue());
            }
            if (this.aHO != null) {
                $i0 += zzawr.zzi(4, this.aHO.longValue());
            }
            return this.count != null ? $i0 + zzawr.zzax(5, this.count.intValue()) : $i0;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzb)) {
                return false;
            }
            zzb $r2 = (zzb) $r1;
            if (!zzawx.equals(this.aHM, $r2.aHM)) {
                return false;
            }
            if (this.name == null) {
                if ($r2.name != null) {
                    return false;
                }
            } else if (!this.name.equals($r2.name)) {
                return false;
            }
            if (this.aHN == null) {
                if ($r2.aHN != null) {
                    return false;
                }
            } else if (!this.aHN.equals($r2.aHN)) {
                return false;
            }
            if (this.aHO == null) {
                if ($r2.aHO != null) {
                    return false;
                }
            } else if (!this.aHO.equals($r2.aHO)) {
                return false;
            }
            return this.count == null ? $r2.count == null : this.count.equals($r2.count);
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.aHO == null ? 0 : this.aHO.hashCode()) + (((this.aHN == null ? 0 : this.aHN.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + ((((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + zzawx.hashCode(this.aHM)) * 31)) * 31)) * 31)) * 31;
            if (this.count != null) {
                $i0 = this.count.hashCode();
            }
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzbr($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.aHM != null && this.aHM.length > 0) {
                for (zzawz $r3 : this.aHM) {
                    if ($r3 != null) {
                        $r1.zza(1, $r3);
                    }
                }
            }
            if (this.name != null) {
                $r1.zzs(2, this.name);
            }
            if (this.aHN != null) {
                $r1.zzf(3, this.aHN.longValue());
            }
            if (this.aHO != null) {
                $r1.zzf(4, this.aHO.longValue());
            }
            if (this.count != null) {
                $r1.zzav(5, this.count.intValue());
            }
            super.writeTo($r1);
        }

        public zzb zzbr(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 10:
                        int $i1 = zzaxc.zzc($r1, 10);
                        $i0 = this.aHM == null ? 0 : this.aHM.length;
                        zzc[] $r2 = new zzc[($i1 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.aHM, 0, $r2, 0, $i0);
                        }
                        while ($i0 < $r2.length - 1) {
                            $r2[$i0] = new zzc();
                            $r1.zza($r2[$i0]);
                            $r1.ie();
                            $i0++;
                        }
                        $r2[$i0] = new zzc();
                        $r1.zza($r2[$i0]);
                        this.aHM = $r2;
                        continue;
                    case 18:
                        this.name = $r1.readString();
                        continue;
                    case 24:
                        this.aHN = Long.valueOf($r1.ih());
                        continue;
                    case 32:
                        this.aHO = Long.valueOf($r1.ih());
                        continue;
                    case 40:
                        this.count = Integer.valueOf($r1.ii());
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

        public zzb zzcbf() throws  {
            this.aHM = zzc.zzcbg();
            this.name = null;
            this.aHN = null;
            this.aHO = null;
            this.count = null;
            this.cbC = -1;
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzc extends zzawz {
        private static volatile zzc[] aHP;
        public Float aGV;
        public Double aGW;
        public Long aHQ;
        public String name;
        public String stringValue;

        public zzc() throws  {
            zzcbh();
        }

        public static zzc[] zzcbg() throws  {
            if (aHP == null) {
                synchronized (zzawx.cbB) {
                    if (aHP == null) {
                        aHP = new zzc[0];
                    }
                }
            }
            return aHP;
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (this.name != null) {
                $i1 = $i0 + zzawr.zzt(1, this.name);
            }
            if (this.stringValue != null) {
                $i1 += zzawr.zzt(2, this.stringValue);
            }
            if (this.aHQ != null) {
                $i1 += zzawr.zzi(3, this.aHQ.longValue());
            }
            if (this.aGV != null) {
                $i1 += zzawr.zzd(4, this.aGV.floatValue());
            }
            return this.aGW != null ? $i1 + zzawr.zzb(5, this.aGW.doubleValue()) : $i1;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzc)) {
                return false;
            }
            zzc $r2 = (zzc) $r1;
            if (this.name == null) {
                if ($r2.name != null) {
                    return false;
                }
            } else if (!this.name.equals($r2.name)) {
                return false;
            }
            if (this.stringValue == null) {
                if ($r2.stringValue != null) {
                    return false;
                }
            } else if (!this.stringValue.equals($r2.stringValue)) {
                return false;
            }
            if (this.aHQ == null) {
                if ($r2.aHQ != null) {
                    return false;
                }
            } else if (!this.aHQ.equals($r2.aHQ)) {
                return false;
            }
            if (this.aGV == null) {
                if ($r2.aGV != null) {
                    return false;
                }
            } else if (!this.aGV.equals($r2.aGV)) {
                return false;
            }
            return this.aGW == null ? $r2.aGW == null : this.aGW.equals($r2.aGW);
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.aGV == null ? 0 : this.aGV.hashCode()) + (((this.aHQ == null ? 0 : this.aHQ.hashCode()) + (((this.stringValue == null ? 0 : this.stringValue.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + ((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31)) * 31)) * 31)) * 31)) * 31;
            if (this.aGW != null) {
                $i0 = this.aGW.hashCode();
            }
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzbs($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.name != null) {
                $r1.zzs(1, this.name);
            }
            if (this.stringValue != null) {
                $r1.zzs(2, this.stringValue);
            }
            if (this.aHQ != null) {
                $r1.zzf(3, this.aHQ.longValue());
            }
            if (this.aGV != null) {
                $r1.zzc(4, this.aGV.floatValue());
            }
            if (this.aGW != null) {
                $r1.zza(5, this.aGW.doubleValue());
            }
            super.writeTo($r1);
        }

        public zzc zzbs(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 10:
                        this.name = $r1.readString();
                        continue;
                    case 18:
                        this.stringValue = $r1.readString();
                        continue;
                    case 24:
                        this.aHQ = Long.valueOf($r1.ih());
                        continue;
                    case 37:
                        this.aGV = Float.valueOf($r1.readFloat());
                        continue;
                    case 41:
                        this.aGW = Double.valueOf($r1.readDouble());
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

        public zzc zzcbh() throws  {
            this.name = null;
            this.stringValue = null;
            this.aHQ = null;
            this.aGV = null;
            this.aGW = null;
            this.cbC = -1;
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzd extends zzawz {
        public zze[] aHR;

        public zzd() throws  {
            zzcbi();
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (this.aHR == null || this.aHR.length <= 0) {
                return $i0;
            }
            for (zzawz $r2 : this.aHR) {
                if ($r2 != null) {
                    $i1 += zzawr.zzc(1, $r2);
                }
            }
            return $i1;
        }

        public boolean equals(Object $r2) throws  {
            if ($r2 == this) {
                return true;
            }
            if (!($r2 instanceof zzd)) {
                return false;
            }
            return zzawx.equals(this.aHR, ((zzd) $r2).aHR);
        }

        public int hashCode() throws  {
            return ((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + zzawx.hashCode(this.aHR);
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzbt($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.aHR != null && this.aHR.length > 0) {
                for (zzawz $r3 : this.aHR) {
                    if ($r3 != null) {
                        $r1.zza(1, $r3);
                    }
                }
            }
            super.writeTo($r1);
        }

        public zzd zzbt(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 10:
                        int $i1 = zzaxc.zzc($r1, 10);
                        $i0 = this.aHR == null ? 0 : this.aHR.length;
                        zze[] $r2 = new zze[($i1 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.aHR, 0, $r2, 0, $i0);
                        }
                        while ($i0 < $r2.length - 1) {
                            $r2[$i0] = new zze();
                            $r1.zza($r2[$i0]);
                            $r1.ie();
                            $i0++;
                        }
                        $r2[$i0] = new zze();
                        $r1.zza($r2[$i0]);
                        this.aHR = $r2;
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

        public zzd zzcbi() throws  {
            this.aHR = zze.zzcbj();
            this.cbC = -1;
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zze extends zzawz {
        private static volatile zze[] aHS;
        public String aCF;
        public String aCG;
        public String aCJ;
        public String aCN;
        public Integer aHT;
        public zzb[] aHU;
        public zzg[] aHV;
        public Long aHW;
        public Long aHX;
        public Long aHY;
        public Long aHZ;
        public Long aIa;
        public String aIb;
        public String aIc;
        public String aId;
        public Integer aIe;
        public Long aIf;
        public Long aIg;
        public String aIh;
        public Boolean aIi;
        public String aIj;
        public Long aIk;
        public Integer aIl;
        public Boolean aIm;
        public zza[] aIn;
        public Integer aIo;
        public Integer aIp;
        public Integer aIq;
        public String aIr;
        public String atH;
        public String zzcj;
        public String zzcs;

        public zze() throws  {
            zzcbk();
        }

        public static zze[] zzcbj() throws  {
            if (aHS == null) {
                synchronized (zzawx.cbB) {
                    if (aHS == null) {
                        aHS = new zze[0];
                    }
                }
            }
            return aHS;
        }

        protected int computeSerializedSize() throws  {
            int $i0 = 0;
            int $i1 = super.computeSerializedSize();
            int $i2 = $i1;
            if (this.aHT != null) {
                $i2 = $i1 + zzawr.zzax(1, this.aHT.intValue());
            }
            if (this.aHU != null && this.aHU.length > 0) {
                for (zzawz $r3 : this.aHU) {
                    if ($r3 != null) {
                        $i2 += zzawr.zzc(2, $r3);
                    }
                }
            }
            if (this.aHV != null && this.aHV.length > 0) {
                for (zzawz $r5 : this.aHV) {
                    if ($r5 != null) {
                        $i2 += zzawr.zzc(3, $r5);
                    }
                }
            }
            if (this.aHW != null) {
                $i2 += zzawr.zzi(4, this.aHW.longValue());
            }
            if (this.aHX != null) {
                $i2 += zzawr.zzi(5, this.aHX.longValue());
            }
            if (this.aHY != null) {
                $i2 += zzawr.zzi(6, this.aHY.longValue());
            }
            if (this.aIa != null) {
                $i2 += zzawr.zzi(7, this.aIa.longValue());
            }
            if (this.aIb != null) {
                $i2 += zzawr.zzt(8, this.aIb);
            }
            if (this.zzcs != null) {
                $i2 += zzawr.zzt(9, this.zzcs);
            }
            if (this.aIc != null) {
                $i2 += zzawr.zzt(10, this.aIc);
            }
            if (this.aId != null) {
                $i2 += zzawr.zzt(11, this.aId);
            }
            if (this.aIe != null) {
                $i2 += zzawr.zzax(12, this.aIe.intValue());
            }
            if (this.aCG != null) {
                $i2 += zzawr.zzt(13, this.aCG);
            }
            if (this.zzcj != null) {
                $i2 += zzawr.zzt(14, this.zzcj);
            }
            if (this.atH != null) {
                $i2 += zzawr.zzt(16, this.atH);
            }
            if (this.aIf != null) {
                $i2 += zzawr.zzi(17, this.aIf.longValue());
            }
            if (this.aIg != null) {
                $i2 += zzawr.zzi(18, this.aIg.longValue());
            }
            if (this.aIh != null) {
                $i2 += zzawr.zzt(19, this.aIh);
            }
            if (this.aIi != null) {
                $i2 += zzawr.zzn(20, this.aIi.booleanValue());
            }
            if (this.aIj != null) {
                $i2 += zzawr.zzt(21, this.aIj);
            }
            if (this.aIk != null) {
                $i2 += zzawr.zzi(22, this.aIk.longValue());
            }
            if (this.aIl != null) {
                $i2 += zzawr.zzax(23, this.aIl.intValue());
            }
            if (this.aCJ != null) {
                $i2 += zzawr.zzt(24, this.aCJ);
            }
            if (this.aCF != null) {
                $i2 += zzawr.zzt(25, this.aCF);
            }
            if (this.aHZ != null) {
                $i2 += zzawr.zzi(26, this.aHZ.longValue());
            }
            if (this.aIm != null) {
                $i2 += zzawr.zzn(28, this.aIm.booleanValue());
            }
            if (this.aIn != null) {
                zza[] $r9 = this.aIn;
                if ($r9.length > 0) {
                    while (true) {
                        $r9 = this.aIn;
                        if ($i0 >= $r9.length) {
                            break;
                        }
                        zzawz $r10 = this.aIn[$i0];
                        if ($r10 != null) {
                            $i2 += zzawr.zzc(29, $r10);
                        }
                        $i0++;
                    }
                }
            }
            if (this.aCN != null) {
                $i2 += zzawr.zzt(30, this.aCN);
            }
            if (this.aIo != null) {
                $i2 += zzawr.zzax(31, this.aIo.intValue());
            }
            if (this.aIp != null) {
                $i2 += zzawr.zzax(32, this.aIp.intValue());
            }
            if (this.aIq != null) {
                $i2 += zzawr.zzax(33, this.aIq.intValue());
            }
            return this.aIr != null ? $i2 + zzawr.zzt(34, this.aIr) : $i2;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zze)) {
                return false;
            }
            zze $r2 = (zze) $r1;
            if (this.aHT == null) {
                if ($r2.aHT != null) {
                    return false;
                }
            } else if (!this.aHT.equals($r2.aHT)) {
                return false;
            }
            if (!zzawx.equals(this.aHU, $r2.aHU)) {
                return false;
            }
            if (!zzawx.equals(this.aHV, $r2.aHV)) {
                return false;
            }
            if (this.aHW == null) {
                if ($r2.aHW != null) {
                    return false;
                }
            } else if (!this.aHW.equals($r2.aHW)) {
                return false;
            }
            if (this.aHX == null) {
                if ($r2.aHX != null) {
                    return false;
                }
            } else if (!this.aHX.equals($r2.aHX)) {
                return false;
            }
            if (this.aHY == null) {
                if ($r2.aHY != null) {
                    return false;
                }
            } else if (!this.aHY.equals($r2.aHY)) {
                return false;
            }
            if (this.aHZ == null) {
                if ($r2.aHZ != null) {
                    return false;
                }
            } else if (!this.aHZ.equals($r2.aHZ)) {
                return false;
            }
            if (this.aIa == null) {
                if ($r2.aIa != null) {
                    return false;
                }
            } else if (!this.aIa.equals($r2.aIa)) {
                return false;
            }
            if (this.aIb == null) {
                if ($r2.aIb != null) {
                    return false;
                }
            } else if (!this.aIb.equals($r2.aIb)) {
                return false;
            }
            if (this.zzcs == null) {
                if ($r2.zzcs != null) {
                    return false;
                }
            } else if (!this.zzcs.equals($r2.zzcs)) {
                return false;
            }
            if (this.aIc == null) {
                if ($r2.aIc != null) {
                    return false;
                }
            } else if (!this.aIc.equals($r2.aIc)) {
                return false;
            }
            if (this.aId == null) {
                if ($r2.aId != null) {
                    return false;
                }
            } else if (!this.aId.equals($r2.aId)) {
                return false;
            }
            if (this.aIe == null) {
                if ($r2.aIe != null) {
                    return false;
                }
            } else if (!this.aIe.equals($r2.aIe)) {
                return false;
            }
            if (this.aCG == null) {
                if ($r2.aCG != null) {
                    return false;
                }
            } else if (!this.aCG.equals($r2.aCG)) {
                return false;
            }
            if (this.zzcj == null) {
                if ($r2.zzcj != null) {
                    return false;
                }
            } else if (!this.zzcj.equals($r2.zzcj)) {
                return false;
            }
            if (this.atH == null) {
                if ($r2.atH != null) {
                    return false;
                }
            } else if (!this.atH.equals($r2.atH)) {
                return false;
            }
            if (this.aIf == null) {
                if ($r2.aIf != null) {
                    return false;
                }
            } else if (!this.aIf.equals($r2.aIf)) {
                return false;
            }
            if (this.aIg == null) {
                if ($r2.aIg != null) {
                    return false;
                }
            } else if (!this.aIg.equals($r2.aIg)) {
                return false;
            }
            if (this.aIh == null) {
                if ($r2.aIh != null) {
                    return false;
                }
            } else if (!this.aIh.equals($r2.aIh)) {
                return false;
            }
            if (this.aIi != null) {
                if (!this.aIi.equals($r2.aIi)) {
                    return false;
                }
            } else if ($r2.aIi != null) {
                return false;
            }
            if (this.aIj == null) {
                if ($r2.aIj != null) {
                    return false;
                }
            } else if (!this.aIj.equals($r2.aIj)) {
                return false;
            }
            if (this.aIk == null) {
                if ($r2.aIk != null) {
                    return false;
                }
            } else if (!this.aIk.equals($r2.aIk)) {
                return false;
            }
            if (this.aIl == null) {
                if ($r2.aIl != null) {
                    return false;
                }
            } else if (!this.aIl.equals($r2.aIl)) {
                return false;
            }
            if (this.aCJ == null) {
                if ($r2.aCJ != null) {
                    return false;
                }
            } else if (!this.aCJ.equals($r2.aCJ)) {
                return false;
            }
            if (this.aCF == null) {
                if ($r2.aCF != null) {
                    return false;
                }
            } else if (!this.aCF.equals($r2.aCF)) {
                return false;
            }
            if (this.aIm != null) {
                if (!this.aIm.equals($r2.aIm)) {
                    return false;
                }
            } else if ($r2.aIm != null) {
                return false;
            }
            if (!zzawx.equals((Object[]) this.aIn, (Object[]) $r2.aIn)) {
                return false;
            }
            if (this.aCN == null) {
                if ($r2.aCN != null) {
                    return false;
                }
            } else if (!this.aCN.equals($r2.aCN)) {
                return false;
            }
            if (this.aIo == null) {
                if ($r2.aIo != null) {
                    return false;
                }
            } else if (!this.aIo.equals($r2.aIo)) {
                return false;
            }
            if (this.aIp == null) {
                if ($r2.aIp != null) {
                    return false;
                }
            } else if (!this.aIp.equals($r2.aIp)) {
                return false;
            }
            if (this.aIq == null) {
                if ($r2.aIq != null) {
                    return false;
                }
            } else if (!this.aIq.equals($r2.aIq)) {
                return false;
            }
            return this.aIr == null ? $r2.aIr == null : this.aIr.equals($r2.aIr);
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.aIq == null ? 0 : this.aIq.hashCode()) + (((this.aIp == null ? 0 : this.aIp.hashCode()) + (((this.aIo == null ? 0 : this.aIo.hashCode()) + (((this.aCN == null ? 0 : this.aCN.hashCode()) + (((((this.aIm == null ? 0 : this.aIm.hashCode()) + (((this.aCF == null ? 0 : this.aCF.hashCode()) + (((this.aCJ == null ? 0 : this.aCJ.hashCode()) + (((this.aIl == null ? 0 : this.aIl.hashCode()) + (((this.aIk == null ? 0 : this.aIk.hashCode()) + (((this.aIj == null ? 0 : this.aIj.hashCode()) + (((this.aIi == null ? 0 : this.aIi.hashCode()) + (((this.aIh == null ? 0 : this.aIh.hashCode()) + (((this.aIg == null ? 0 : this.aIg.hashCode()) + (((this.aIf == null ? 0 : this.aIf.hashCode()) + (((this.atH == null ? 0 : this.atH.hashCode()) + (((this.zzcj == null ? 0 : this.zzcj.hashCode()) + (((this.aCG == null ? 0 : this.aCG.hashCode()) + (((this.aIe == null ? 0 : this.aIe.hashCode()) + (((this.aId == null ? 0 : this.aId.hashCode()) + (((this.aIc == null ? 0 : this.aIc.hashCode()) + (((this.zzcs == null ? 0 : this.zzcs.hashCode()) + (((this.aIb == null ? 0 : this.aIb.hashCode()) + (((this.aIa == null ? 0 : this.aIa.hashCode()) + (((this.aHZ == null ? 0 : this.aHZ.hashCode()) + (((this.aHY == null ? 0 : this.aHY.hashCode()) + (((this.aHX == null ? 0 : this.aHX.hashCode()) + (((this.aHW == null ? 0 : this.aHW.hashCode()) + (((((((this.aHT == null ? 0 : this.aHT.hashCode()) + ((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31)) * 31) + zzawx.hashCode(this.aHU)) * 31) + zzawx.hashCode(this.aHV)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31) + zzawx.hashCode(this.aIn)) * 31)) * 31)) * 31)) * 31)) * 31;
            if (this.aIr != null) {
                $i0 = this.aIr.hashCode();
            }
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzbu($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            int $i0 = 0;
            if (this.aHT != null) {
                $r1.zzav(1, this.aHT.intValue());
            }
            if (this.aHU != null && this.aHU.length > 0) {
                for (zzawz $r4 : this.aHU) {
                    if ($r4 != null) {
                        $r1.zza(2, $r4);
                    }
                }
            }
            if (this.aHV != null && this.aHV.length > 0) {
                for (zzawz $r6 : this.aHV) {
                    if ($r6 != null) {
                        $r1.zza(3, $r6);
                    }
                }
            }
            if (this.aHW != null) {
                $r1.zzf(4, this.aHW.longValue());
            }
            if (this.aHX != null) {
                $r1.zzf(5, this.aHX.longValue());
            }
            if (this.aHY != null) {
                $r1.zzf(6, this.aHY.longValue());
            }
            if (this.aIa != null) {
                $r1.zzf(7, this.aIa.longValue());
            }
            if (this.aIb != null) {
                $r1.zzs(8, this.aIb);
            }
            if (this.zzcs != null) {
                $r1.zzs(9, this.zzcs);
            }
            if (this.aIc != null) {
                $r1.zzs(10, this.aIc);
            }
            if (this.aId != null) {
                $r1.zzs(11, this.aId);
            }
            if (this.aIe != null) {
                $r1.zzav(12, this.aIe.intValue());
            }
            if (this.aCG != null) {
                $r1.zzs(13, this.aCG);
            }
            if (this.zzcj != null) {
                $r1.zzs(14, this.zzcj);
            }
            if (this.atH != null) {
                $r1.zzs(16, this.atH);
            }
            if (this.aIf != null) {
                $r1.zzf(17, this.aIf.longValue());
            }
            if (this.aIg != null) {
                $r1.zzf(18, this.aIg.longValue());
            }
            if (this.aIh != null) {
                $r1.zzs(19, this.aIh);
            }
            if (this.aIi != null) {
                $r1.zzm(20, this.aIi.booleanValue());
            }
            if (this.aIj != null) {
                $r1.zzs(21, this.aIj);
            }
            if (this.aIk != null) {
                $r1.zzf(22, this.aIk.longValue());
            }
            if (this.aIl != null) {
                $r1.zzav(23, this.aIl.intValue());
            }
            if (this.aCJ != null) {
                $r1.zzs(24, this.aCJ);
            }
            if (this.aCF != null) {
                $r1.zzs(25, this.aCF);
            }
            if (this.aHZ != null) {
                $r1.zzf(26, this.aHZ.longValue());
            }
            if (this.aIm != null) {
                $r1.zzm(28, this.aIm.booleanValue());
            }
            if (this.aIn != null) {
                zza[] $r10 = this.aIn;
                if ($r10.length > 0) {
                    while (true) {
                        $r10 = this.aIn;
                        if ($i0 >= $r10.length) {
                            break;
                        }
                        zzawz $r11 = this.aIn[$i0];
                        if ($r11 != null) {
                            $r1.zza(29, $r11);
                        }
                        $i0++;
                    }
                }
            }
            if (this.aCN != null) {
                $r1.zzs(30, this.aCN);
            }
            if (this.aIo != null) {
                $r1.zzav(31, this.aIo.intValue());
            }
            if (this.aIp != null) {
                $r1.zzav(32, this.aIp.intValue());
            }
            if (this.aIq != null) {
                $r1.zzav(33, this.aIq.intValue());
            }
            if (this.aIr != null) {
                $r1.zzs(34, this.aIr);
            }
            super.writeTo($r1);
        }

        public com.google.android.gms.internal.zzxt.zze zzbu(com.google.android.gms.internal.zzawq r23) throws java.io.IOException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:17:0x0060 in {4, 5, 6, 9, 12, 15, 16, 18, 20, 22, 23, 26, 29, 32, 33, 38, 41, 44, 47, 50, 53, 56, 59, 62, 65, 68, 71, 74, 77, 80, 83, 86, 89, 92, 95, 98, 102, 105, 108, 109, 111, 114, 117, 120, 123, 126} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r22 = this;
        L_0x0000:
            r0 = r23;
            r2 = r0.ie();
            switch(r2) {
                case 0: goto L_0x0012;
                case 8: goto L_0x0013;
                case 18: goto L_0x0022;
                case 26: goto L_0x007f;
                case 32: goto L_0x00d0;
                case 40: goto L_0x00e1;
                case 48: goto L_0x00f2;
                case 56: goto L_0x0107;
                case 66: goto L_0x011c;
                case 74: goto L_0x012d;
                case 82: goto L_0x013e;
                case 90: goto L_0x014f;
                case 96: goto L_0x0160;
                case 106: goto L_0x0173;
                case 114: goto L_0x0184;
                case 130: goto L_0x0195;
                case 136: goto L_0x01a6;
                case 144: goto L_0x01bb;
                case 154: goto L_0x01d0;
                case 160: goto L_0x01e1;
                case 170: goto L_0x01f6;
                case 176: goto L_0x0207;
                case 184: goto L_0x021c;
                case 194: goto L_0x022f;
                case 202: goto L_0x0240;
                case 208: goto L_0x0251;
                case 224: goto L_0x0266;
                case 234: goto L_0x027b;
                case 242: goto L_0x02e8;
                case 248: goto L_0x02f9;
                case 256: goto L_0x030c;
                case 264: goto L_0x031f;
                case 274: goto L_0x0332;
                default: goto L_0x0009;
            };
        L_0x0009:
            goto L_0x000a;
        L_0x000a:
            r0 = r23;
            r3 = com.google.android.gms.internal.zzaxc.zzb(r0, r2);
            if (r3 != 0) goto L_0x0000;
        L_0x0012:
            return r22;
        L_0x0013:
            r0 = r23;
            r2 = r0.ii();
            r4 = java.lang.Integer.valueOf(r2);
            r0 = r22;
            r0.aHT = r4;
            goto L_0x0000;
        L_0x0022:
            r6 = 18;
            r0 = r23;
            r5 = com.google.android.gms.internal.zzaxc.zzc(r0, r6);
            r0 = r22;
            r7 = r0.aHU;
            if (r7 != 0) goto L_0x005a;
        L_0x0030:
            r2 = 0;
        L_0x0031:
            r5 = r5 + r2;
            r7 = new com.google.android.gms.internal.zzxt.zzb[r5];
            if (r2 == 0) goto L_0x003f;
        L_0x0036:
            r0 = r22;
            r8 = r0.aHU;
            r6 = 0;
            r9 = 0;
            java.lang.System.arraycopy(r8, r6, r7, r9, r2);
        L_0x003f:
            r5 = r7.length;
            r5 = r5 + -1;
            if (r2 >= r5) goto L_0x0064;
        L_0x0044:
            r10 = new com.google.android.gms.internal.zzxt$zzb;
            r10.<init>();
            r7[r2] = r10;
            r10 = r7[r2];
            r0 = r23;
            r0.zza(r10);
            r0 = r23;
            r0.ie();
            r2 = r2 + 1;
            goto L_0x003f;
        L_0x005a:
            r0 = r22;
            r7 = r0.aHU;
            r2 = r7.length;
            goto L_0x0031;
            goto L_0x0064;
        L_0x0061:
            goto L_0x0000;
        L_0x0064:
            r10 = new com.google.android.gms.internal.zzxt$zzb;
            r10.<init>();
            r7[r2] = r10;
            goto L_0x006f;
        L_0x006c:
            goto L_0x0000;
        L_0x006f:
            r10 = r7[r2];
            r0 = r23;
            r0.zza(r10);
            goto L_0x007a;
        L_0x0077:
            goto L_0x0000;
        L_0x007a:
            r0 = r22;
            r0.aHU = r7;
            goto L_0x0000;
        L_0x007f:
            r6 = 26;
            r0 = r23;
            r5 = com.google.android.gms.internal.zzaxc.zzc(r0, r6);
            r0 = r22;
            r11 = r0.aHV;
            if (r11 != 0) goto L_0x00b7;
        L_0x008d:
            r2 = 0;
        L_0x008e:
            r5 = r5 + r2;
            r11 = new com.google.android.gms.internal.zzxt.zzg[r5];
            if (r2 == 0) goto L_0x009c;
        L_0x0093:
            r0 = r22;
            r12 = r0.aHV;
            r6 = 0;
            r9 = 0;
            java.lang.System.arraycopy(r12, r6, r11, r9, r2);
        L_0x009c:
            r5 = r11.length;
            r5 = r5 + -1;
            if (r2 >= r5) goto L_0x00bd;
        L_0x00a1:
            r13 = new com.google.android.gms.internal.zzxt$zzg;
            r13.<init>();
            r11[r2] = r13;
            r13 = r11[r2];
            r0 = r23;
            r0.zza(r13);
            r0 = r23;
            r0.ie();
            r2 = r2 + 1;
            goto L_0x009c;
        L_0x00b7:
            r0 = r22;
            r11 = r0.aHV;
            r2 = r11.length;
            goto L_0x008e;
        L_0x00bd:
            r13 = new com.google.android.gms.internal.zzxt$zzg;
            r13.<init>();
            r11[r2] = r13;
            r13 = r11[r2];
            r0 = r23;
            r0.zza(r13);
            r0 = r22;
            r0.aHV = r11;
            goto L_0x0061;
        L_0x00d0:
            r0 = r23;
            r14 = r0.ih();
            r16 = java.lang.Long.valueOf(r14);
            r0 = r16;
            r1 = r22;
            r1.aHW = r0;
            goto L_0x006c;
        L_0x00e1:
            r0 = r23;
            r14 = r0.ih();
            r16 = java.lang.Long.valueOf(r14);
            r0 = r16;
            r1 = r22;
            r1.aHX = r0;
            goto L_0x0077;
        L_0x00f2:
            r0 = r23;
            r14 = r0.ih();
            r16 = java.lang.Long.valueOf(r14);
            goto L_0x0100;
        L_0x00fd:
            goto L_0x0000;
        L_0x0100:
            r0 = r16;
            r1 = r22;
            r1.aHY = r0;
            goto L_0x00fd;
        L_0x0107:
            r0 = r23;
            r14 = r0.ih();
            r16 = java.lang.Long.valueOf(r14);
            goto L_0x0115;
        L_0x0112:
            goto L_0x0000;
        L_0x0115:
            r0 = r16;
            r1 = r22;
            r1.aIa = r0;
            goto L_0x0112;
        L_0x011c:
            r0 = r23;
            r17 = r0.readString();
            goto L_0x0126;
        L_0x0123:
            goto L_0x0000;
        L_0x0126:
            r0 = r17;
            r1 = r22;
            r1.aIb = r0;
            goto L_0x0123;
        L_0x012d:
            r0 = r23;
            r17 = r0.readString();
            goto L_0x0137;
        L_0x0134:
            goto L_0x0000;
        L_0x0137:
            r0 = r17;
            r1 = r22;
            r1.zzcs = r0;
            goto L_0x0134;
        L_0x013e:
            r0 = r23;
            r17 = r0.readString();
            goto L_0x0148;
        L_0x0145:
            goto L_0x0000;
        L_0x0148:
            r0 = r17;
            r1 = r22;
            r1.aIc = r0;
            goto L_0x0145;
        L_0x014f:
            r0 = r23;
            r17 = r0.readString();
            goto L_0x0159;
        L_0x0156:
            goto L_0x0000;
        L_0x0159:
            r0 = r17;
            r1 = r22;
            r1.aId = r0;
            goto L_0x0156;
        L_0x0160:
            r0 = r23;
            r2 = r0.ii();
            r4 = java.lang.Integer.valueOf(r2);
            goto L_0x016e;
        L_0x016b:
            goto L_0x0000;
        L_0x016e:
            r0 = r22;
            r0.aIe = r4;
            goto L_0x016b;
        L_0x0173:
            r0 = r23;
            r17 = r0.readString();
            goto L_0x017d;
        L_0x017a:
            goto L_0x0000;
        L_0x017d:
            r0 = r17;
            r1 = r22;
            r1.aCG = r0;
            goto L_0x017a;
        L_0x0184:
            r0 = r23;
            r17 = r0.readString();
            goto L_0x018e;
        L_0x018b:
            goto L_0x0000;
        L_0x018e:
            r0 = r17;
            r1 = r22;
            r1.zzcj = r0;
            goto L_0x018b;
        L_0x0195:
            r0 = r23;
            r17 = r0.readString();
            goto L_0x019f;
        L_0x019c:
            goto L_0x0000;
        L_0x019f:
            r0 = r17;
            r1 = r22;
            r1.atH = r0;
            goto L_0x019c;
        L_0x01a6:
            r0 = r23;
            r14 = r0.ih();
            r16 = java.lang.Long.valueOf(r14);
            goto L_0x01b4;
        L_0x01b1:
            goto L_0x0000;
        L_0x01b4:
            r0 = r16;
            r1 = r22;
            r1.aIf = r0;
            goto L_0x01b1;
        L_0x01bb:
            r0 = r23;
            r14 = r0.ih();
            r16 = java.lang.Long.valueOf(r14);
            goto L_0x01c9;
        L_0x01c6:
            goto L_0x0000;
        L_0x01c9:
            r0 = r16;
            r1 = r22;
            r1.aIg = r0;
            goto L_0x01c6;
        L_0x01d0:
            r0 = r23;
            r17 = r0.readString();
            goto L_0x01da;
        L_0x01d7:
            goto L_0x0000;
        L_0x01da:
            r0 = r17;
            r1 = r22;
            r1.aIh = r0;
            goto L_0x01d7;
        L_0x01e1:
            r0 = r23;
            r3 = r0.ik();
            r18 = java.lang.Boolean.valueOf(r3);
            goto L_0x01ef;
        L_0x01ec:
            goto L_0x0000;
        L_0x01ef:
            r0 = r18;
            r1 = r22;
            r1.aIi = r0;
            goto L_0x01ec;
        L_0x01f6:
            r0 = r23;
            r17 = r0.readString();
            goto L_0x0200;
        L_0x01fd:
            goto L_0x0000;
        L_0x0200:
            r0 = r17;
            r1 = r22;
            r1.aIj = r0;
            goto L_0x01fd;
        L_0x0207:
            r0 = r23;
            r14 = r0.ih();
            r16 = java.lang.Long.valueOf(r14);
            goto L_0x0215;
        L_0x0212:
            goto L_0x0000;
        L_0x0215:
            r0 = r16;
            r1 = r22;
            r1.aIk = r0;
            goto L_0x0212;
        L_0x021c:
            r0 = r23;
            r2 = r0.ii();
            r4 = java.lang.Integer.valueOf(r2);
            goto L_0x022a;
        L_0x0227:
            goto L_0x0000;
        L_0x022a:
            r0 = r22;
            r0.aIl = r4;
            goto L_0x0227;
        L_0x022f:
            r0 = r23;
            r17 = r0.readString();
            goto L_0x0239;
        L_0x0236:
            goto L_0x0000;
        L_0x0239:
            r0 = r17;
            r1 = r22;
            r1.aCJ = r0;
            goto L_0x0236;
        L_0x0240:
            r0 = r23;
            r17 = r0.readString();
            goto L_0x024a;
        L_0x0247:
            goto L_0x0000;
        L_0x024a:
            r0 = r17;
            r1 = r22;
            r1.aCF = r0;
            goto L_0x0247;
        L_0x0251:
            r0 = r23;
            r14 = r0.ih();
            r16 = java.lang.Long.valueOf(r14);
            goto L_0x025f;
        L_0x025c:
            goto L_0x0000;
        L_0x025f:
            r0 = r16;
            r1 = r22;
            r1.aHZ = r0;
            goto L_0x025c;
        L_0x0266:
            r0 = r23;
            r3 = r0.ik();
            r18 = java.lang.Boolean.valueOf(r3);
            goto L_0x0274;
        L_0x0271:
            goto L_0x0000;
        L_0x0274:
            r0 = r18;
            r1 = r22;
            r1.aIm = r0;
            goto L_0x0271;
        L_0x027b:
            r6 = 234; // 0xea float:3.28E-43 double:1.156E-321;
            r0 = r23;
            r5 = com.google.android.gms.internal.zzaxc.zzc(r0, r6);
            r0 = r22;
            r0 = r0.aIn;
            r19 = r0;
            if (r19 != 0) goto L_0x02c3;
        L_0x028b:
            r2 = 0;
        L_0x028c:
            r5 = r5 + r2;
            r0 = new com.google.android.gms.internal.zzxt.zza[r5];
            r19 = r0;
            if (r2 == 0) goto L_0x02a2;
        L_0x0293:
            r0 = r22;
            r0 = r0.aIn;
            r20 = r0;
            r6 = 0;
            r9 = 0;
            r0 = r20;
            r1 = r19;
            java.lang.System.arraycopy(r0, r6, r1, r9, r2);
        L_0x02a2:
            r0 = r19;
            r5 = r0.length;
            r5 = r5 + -1;
            if (r2 >= r5) goto L_0x02cb;
        L_0x02a9:
            r21 = new com.google.android.gms.internal.zzxt$zza;
            r0 = r21;
            r0.<init>();
            r19[r2] = r21;
            r21 = r19[r2];
            r0 = r23;
            r1 = r21;
            r0.zza(r1);
            r0 = r23;
            r0.ie();
            r2 = r2 + 1;
            goto L_0x02a2;
        L_0x02c3:
            r0 = r22;
            r0 = r0.aIn;
            r19 = r0;
            r2 = r0.length;
            goto L_0x028c;
        L_0x02cb:
            r21 = new com.google.android.gms.internal.zzxt$zza;
            r0 = r21;
            r0.<init>();
            r19[r2] = r21;
            r21 = r19[r2];
            r0 = r23;
            r1 = r21;
            r0.zza(r1);
            goto L_0x02e1;
        L_0x02de:
            goto L_0x0000;
        L_0x02e1:
            r0 = r19;
            r1 = r22;
            r1.aIn = r0;
            goto L_0x02de;
        L_0x02e8:
            r0 = r23;
            r17 = r0.readString();
            goto L_0x02f2;
        L_0x02ef:
            goto L_0x0000;
        L_0x02f2:
            r0 = r17;
            r1 = r22;
            r1.aCN = r0;
            goto L_0x02ef;
        L_0x02f9:
            r0 = r23;
            r2 = r0.ii();
            r4 = java.lang.Integer.valueOf(r2);
            goto L_0x0307;
        L_0x0304:
            goto L_0x0000;
        L_0x0307:
            r0 = r22;
            r0.aIo = r4;
            goto L_0x0304;
        L_0x030c:
            r0 = r23;
            r2 = r0.ii();
            r4 = java.lang.Integer.valueOf(r2);
            goto L_0x031a;
        L_0x0317:
            goto L_0x0000;
        L_0x031a:
            r0 = r22;
            r0.aIp = r4;
            goto L_0x0317;
        L_0x031f:
            r0 = r23;
            r2 = r0.ii();
            r4 = java.lang.Integer.valueOf(r2);
            goto L_0x032d;
        L_0x032a:
            goto L_0x0000;
        L_0x032d:
            r0 = r22;
            r0.aIq = r4;
            goto L_0x032a;
        L_0x0332:
            r0 = r23;
            r17 = r0.readString();
            goto L_0x033c;
        L_0x0339:
            goto L_0x0000;
        L_0x033c:
            r0 = r17;
            r1 = r22;
            r1.aIr = r0;
            goto L_0x0339;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzxt.zze.zzbu(com.google.android.gms.internal.zzawq):com.google.android.gms.internal.zzxt$zze");
        }

        public zze zzcbk() throws  {
            this.aHT = null;
            this.aHU = zzb.zzcbe();
            this.aHV = zzg.zzcbm();
            this.aHW = null;
            this.aHX = null;
            this.aHY = null;
            this.aHZ = null;
            this.aIa = null;
            this.aIb = null;
            this.zzcs = null;
            this.aIc = null;
            this.aId = null;
            this.aIe = null;
            this.aCG = null;
            this.zzcj = null;
            this.atH = null;
            this.aIf = null;
            this.aIg = null;
            this.aIh = null;
            this.aIi = null;
            this.aIj = null;
            this.aIk = null;
            this.aIl = null;
            this.aCJ = null;
            this.aCF = null;
            this.aIm = null;
            this.aIn = zza.zzcbc();
            this.aCN = null;
            this.aIo = null;
            this.aIp = null;
            this.aIq = null;
            this.aIr = null;
            this.cbC = -1;
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzf extends zzawz {
        public long[] aIs;
        public long[] aIt;

        public zzf() throws  {
            zzcbl();
        }

        protected int computeSerializedSize() throws  {
            int $i2;
            int $i1 = super.computeSerializedSize();
            if (this.aIs != null && this.aIs.length > 0) {
                int $i3 = 0;
                for (long $l5 : this.aIs) {
                    $i3 += zzawr.zzdl($l5);
                }
                $i1 = ($i1 + $i3) + (this.aIs.length * 1);
            }
            if (this.aIt == null || this.aIt.length <= 0) {
                return $i1;
            }
            $i2 = 0;
            for (long $l52 : this.aIt) {
                $i2 += zzawr.zzdl($l52);
            }
            return ($i1 + $i2) + (this.aIt.length * 1);
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzf)) {
                return false;
            }
            zzf $r2 = (zzf) $r1;
            return !zzawx.equals(this.aIs, $r2.aIs) ? false : zzawx.equals(this.aIt, $r2.aIt);
        }

        public int hashCode() throws  {
            return ((((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + zzawx.hashCode(this.aIs)) * 31) + zzawx.hashCode(this.aIt);
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzbv($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.aIs != null && this.aIs.length > 0) {
                for (long $l0 : this.aIs) {
                    $r1.zze(1, $l0);
                }
            }
            if (this.aIt != null && this.aIt.length > 0) {
                for (long $l02 : this.aIt) {
                    $r1.zze(2, $l02);
                }
            }
            super.writeTo($r1);
        }

        public com.google.android.gms.internal.zzxt.zzf zzbv(com.google.android.gms.internal.zzawq r12) throws java.io.IOException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:23:0x0061 in {4, 5, 8, 11, 14, 15, 17, 18, 22, 24, 27, 30, 33, 34, 38, 41, 44, 45, 50, 53, 56, 59, 60, 62} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r11 = this;
        L_0x0000:
            r0 = r12.ie();
            switch(r0) {
                case 0: goto L_0x000e;
                case 8: goto L_0x000f;
                case 10: goto L_0x0048;
                case 16: goto L_0x008f;
                case 18: goto L_0x00c4;
                default: goto L_0x0007;
            };
        L_0x0007:
            goto L_0x0008;
        L_0x0008:
            r1 = com.google.android.gms.internal.zzaxc.zzb(r12, r0);
            if (r1 != 0) goto L_0x0000;
        L_0x000e:
            return r11;
        L_0x000f:
            r3 = 8;
            r2 = com.google.android.gms.internal.zzaxc.zzc(r12, r3);
            r4 = r11.aIs;
            if (r4 != 0) goto L_0x0037;
        L_0x0019:
            r0 = 0;
        L_0x001a:
            r2 = r2 + r0;
            r4 = new long[r2];
            if (r0 == 0) goto L_0x0026;
        L_0x001f:
            r5 = r11.aIs;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r0);
        L_0x0026:
            r2 = r4.length;
            r2 = r2 + -1;
            if (r0 >= r2) goto L_0x003b;
        L_0x002b:
            r7 = r12.ig();
            r4[r0] = r7;
            r12.ie();
            r0 = r0 + 1;
            goto L_0x0026;
        L_0x0037:
            r4 = r11.aIs;
            r0 = r4.length;
            goto L_0x001a;
        L_0x003b:
            r7 = r12.ig();
            r4[r0] = r7;
            r11.aIs = r4;
            goto L_0x0047;
        L_0x0044:
            goto L_0x0000;
        L_0x0047:
            goto L_0x0000;
        L_0x0048:
            r0 = r12.in();
            r0 = r12.zzarv(r0);
            r9 = r12.getPosition();
            r2 = 0;
        L_0x0055:
            r10 = r12.is();
            if (r10 <= 0) goto L_0x0065;
        L_0x005b:
            r12.ig();
            r2 = r2 + 1;
            goto L_0x0055;
            goto L_0x0065;
        L_0x0062:
            goto L_0x0000;
        L_0x0065:
            r12.zzarx(r9);
            r4 = r11.aIs;
            if (r4 != 0) goto L_0x0085;
        L_0x006c:
            r9 = 0;
        L_0x006d:
            r2 = r2 + r9;
            r4 = new long[r2];
            if (r9 == 0) goto L_0x0079;
        L_0x0072:
            r5 = r11.aIs;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r9);
        L_0x0079:
            r2 = r4.length;
            if (r9 >= r2) goto L_0x0089;
        L_0x007c:
            r7 = r12.ig();
            r4[r9] = r7;
            r9 = r9 + 1;
            goto L_0x0079;
        L_0x0085:
            r4 = r11.aIs;
            r9 = r4.length;
            goto L_0x006d;
        L_0x0089:
            r11.aIs = r4;
            r12.zzarw(r0);
            goto L_0x0044;
        L_0x008f:
            r3 = 16;
            r2 = com.google.android.gms.internal.zzaxc.zzc(r12, r3);
            r4 = r11.aIt;
            if (r4 != 0) goto L_0x00b7;
        L_0x0099:
            r0 = 0;
        L_0x009a:
            r2 = r2 + r0;
            r4 = new long[r2];
            if (r0 == 0) goto L_0x00a6;
        L_0x009f:
            r5 = r11.aIt;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r0);
        L_0x00a6:
            r2 = r4.length;
            r2 = r2 + -1;
            if (r0 >= r2) goto L_0x00bb;
        L_0x00ab:
            r7 = r12.ig();
            r4[r0] = r7;
            r12.ie();
            r0 = r0 + 1;
            goto L_0x00a6;
        L_0x00b7:
            r4 = r11.aIt;
            r0 = r4.length;
            goto L_0x009a;
        L_0x00bb:
            r7 = r12.ig();
            r4[r0] = r7;
            r11.aIt = r4;
            goto L_0x0062;
        L_0x00c4:
            r0 = r12.in();
            r0 = r12.zzarv(r0);
            r9 = r12.getPosition();
            r2 = 0;
        L_0x00d1:
            r10 = r12.is();
            if (r10 <= 0) goto L_0x00dd;
        L_0x00d7:
            r12.ig();
            r2 = r2 + 1;
            goto L_0x00d1;
        L_0x00dd:
            r12.zzarx(r9);
            r4 = r11.aIt;
            if (r4 != 0) goto L_0x00fd;
        L_0x00e4:
            r9 = 0;
        L_0x00e5:
            r2 = r2 + r9;
            r4 = new long[r2];
            if (r9 == 0) goto L_0x00f1;
        L_0x00ea:
            r5 = r11.aIt;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r9);
        L_0x00f1:
            r2 = r4.length;
            if (r9 >= r2) goto L_0x0101;
        L_0x00f4:
            r7 = r12.ig();
            r4[r9] = r7;
            r9 = r9 + 1;
            goto L_0x00f1;
        L_0x00fd:
            r4 = r11.aIt;
            r9 = r4.length;
            goto L_0x00e5;
        L_0x0101:
            r11.aIt = r4;
            goto L_0x0107;
        L_0x0104:
            goto L_0x0000;
        L_0x0107:
            r12.zzarw(r0);
            goto L_0x0104;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzxt.zzf.zzbv(com.google.android.gms.internal.zzawq):com.google.android.gms.internal.zzxt$zzf");
        }

        public zzf zzcbl() throws  {
            this.aIs = zzaxc.cbF;
            this.aIt = zzaxc.cbF;
            this.cbC = -1;
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzg extends zzawz {
        private static volatile zzg[] aIu;
        public Float aGV;
        public Double aGW;
        public Long aHQ;
        public Long aIv;
        public String name;
        public String stringValue;

        public zzg() throws  {
            zzcbn();
        }

        public static zzg[] zzcbm() throws  {
            if (aIu == null) {
                synchronized (zzawx.cbB) {
                    if (aIu == null) {
                        aIu = new zzg[0];
                    }
                }
            }
            return aIu;
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (this.aIv != null) {
                $i1 = $i0 + zzawr.zzi(1, this.aIv.longValue());
            }
            if (this.name != null) {
                $i1 += zzawr.zzt(2, this.name);
            }
            if (this.stringValue != null) {
                $i1 += zzawr.zzt(3, this.stringValue);
            }
            if (this.aHQ != null) {
                $i1 += zzawr.zzi(4, this.aHQ.longValue());
            }
            if (this.aGV != null) {
                $i1 += zzawr.zzd(5, this.aGV.floatValue());
            }
            return this.aGW != null ? $i1 + zzawr.zzb(6, this.aGW.doubleValue()) : $i1;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzg)) {
                return false;
            }
            zzg $r2 = (zzg) $r1;
            if (this.aIv == null) {
                if ($r2.aIv != null) {
                    return false;
                }
            } else if (!this.aIv.equals($r2.aIv)) {
                return false;
            }
            if (this.name == null) {
                if ($r2.name != null) {
                    return false;
                }
            } else if (!this.name.equals($r2.name)) {
                return false;
            }
            if (this.stringValue == null) {
                if ($r2.stringValue != null) {
                    return false;
                }
            } else if (!this.stringValue.equals($r2.stringValue)) {
                return false;
            }
            if (this.aHQ == null) {
                if ($r2.aHQ != null) {
                    return false;
                }
            } else if (!this.aHQ.equals($r2.aHQ)) {
                return false;
            }
            if (this.aGV == null) {
                if ($r2.aGV != null) {
                    return false;
                }
            } else if (!this.aGV.equals($r2.aGV)) {
                return false;
            }
            return this.aGW == null ? $r2.aGW == null : this.aGW.equals($r2.aGW);
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.aGV == null ? 0 : this.aGV.hashCode()) + (((this.aHQ == null ? 0 : this.aHQ.hashCode()) + (((this.stringValue == null ? 0 : this.stringValue.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + (((this.aIv == null ? 0 : this.aIv.hashCode()) + ((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31)) * 31)) * 31)) * 31)) * 31)) * 31;
            if (this.aGW != null) {
                $i0 = this.aGW.hashCode();
            }
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzbw($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.aIv != null) {
                $r1.zzf(1, this.aIv.longValue());
            }
            if (this.name != null) {
                $r1.zzs(2, this.name);
            }
            if (this.stringValue != null) {
                $r1.zzs(3, this.stringValue);
            }
            if (this.aHQ != null) {
                $r1.zzf(4, this.aHQ.longValue());
            }
            if (this.aGV != null) {
                $r1.zzc(5, this.aGV.floatValue());
            }
            if (this.aGW != null) {
                $r1.zza(6, this.aGW.doubleValue());
            }
            super.writeTo($r1);
        }

        public zzg zzbw(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 8:
                        this.aIv = Long.valueOf($r1.ih());
                        continue;
                    case 18:
                        this.name = $r1.readString();
                        continue;
                    case 26:
                        this.stringValue = $r1.readString();
                        continue;
                    case 32:
                        this.aHQ = Long.valueOf($r1.ih());
                        continue;
                    case 45:
                        this.aGV = Float.valueOf($r1.readFloat());
                        continue;
                    case 49:
                        this.aGW = Double.valueOf($r1.readDouble());
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

        public zzg zzcbn() throws  {
            this.aIv = null;
            this.name = null;
            this.stringValue = null;
            this.aHQ = null;
            this.aGV = null;
            this.aGW = null;
            this.cbC = -1;
            return this;
        }
    }
}
