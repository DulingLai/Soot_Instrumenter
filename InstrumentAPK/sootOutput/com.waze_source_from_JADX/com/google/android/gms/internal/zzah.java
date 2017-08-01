package com.google.android.gms.internal;

import com.facebook.appevents.AppEventsConstants;
import com.waze.strings.DisplayStrings;
import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzah {

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zza extends zzaws<zza> {
        public int level;
        public int zzum;
        public int zzun;

        public zza() throws  {
            zzaa();
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (this.level != 1) {
                $i1 = $i0 + zzawr.zzax(1, this.level);
            }
            if (this.zzum != 0) {
                $i1 += zzawr.zzax(2, this.zzum);
            }
            return this.zzun != 0 ? $i1 + zzawr.zzax(3, this.zzun) : $i1;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zza)) {
                return false;
            }
            zza $r2 = (zza) $r1;
            return this.level == $r2.level ? this.zzum == $r2.zzum ? this.zzun == $r2.zzun ? (this.cbt == null || this.cbt.isEmpty()) ? $r2.cbt == null || $r2.cbt.isEmpty() : this.cbt.equals($r2.cbt) : false : false : false;
        }

        public int hashCode() throws  {
            int $i0 = (((((((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + this.level) * 31) + this.zzum) * 31) + this.zzun) * 31;
            int $i1 = (this.cbt == null || this.cbt.isEmpty()) ? 0 : this.cbt.hashCode();
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzi($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.level != 1) {
                $r1.zzav(1, this.level);
            }
            if (this.zzum != 0) {
                $r1.zzav(2, this.zzum);
            }
            if (this.zzun != 0) {
                $r1.zzav(3, this.zzun);
            }
            super.writeTo($r1);
        }

        public zza zzaa() throws  {
            this.level = 1;
            this.zzum = 0;
            this.zzun = 0;
            this.cbt = null;
            this.cbC = -1;
            return this;
        }

        public zza zzi(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 8:
                        $i0 = $r1.ii();
                        switch ($i0) {
                            case 1:
                            case 2:
                            case 3:
                                this.level = $i0;
                                break;
                            default:
                                continue;
                        }
                    case 16:
                        this.zzum = $r1.ii();
                        continue;
                    case 24:
                        this.zzun = $r1.ii();
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
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzb extends zzaws<zzb> {
        private static volatile zzb[] zzuo;
        public int name;
        public int[] zzup;
        public int zzuq;
        public boolean zzur;
        public boolean zzus;

        public zzb() throws  {
            zzac();
        }

        public static zzb[] zzab() throws  {
            if (zzuo == null) {
                synchronized (zzawx.cbB) {
                    if (zzuo == null) {
                        zzuo = new zzb[0];
                    }
                }
            }
            return zzuo;
        }

        protected int computeSerializedSize() throws  {
            int $i0 = 0;
            int $i1 = super.computeSerializedSize();
            int $i2 = $i1;
            if (this.zzus) {
                $i2 = $i1 + zzawr.zzn(1, this.zzus);
            }
            $i2 = zzawr.zzax(2, this.zzuq) + $i2;
            if (this.zzup == null || this.zzup.length <= 0) {
                $i0 = $i2;
            } else {
                for (int $i3 : this.zzup) {
                    $i0 += zzawr.zzasb($i3);
                }
                $i0 = ($i2 + $i0) + (this.zzup.length * 1);
            }
            if (this.name != 0) {
                $i0 += zzawr.zzax(4, this.name);
            }
            return this.zzur ? $i0 + zzawr.zzn(6, this.zzur) : $i0;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzb)) {
                return false;
            }
            zzb $r2 = (zzb) $r1;
            return zzawx.equals(this.zzup, $r2.zzup) ? this.zzuq == $r2.zzuq ? this.name == $r2.name ? this.zzur == $r2.zzur ? this.zzus == $r2.zzus ? (this.cbt == null || this.cbt.isEmpty()) ? $r2.cbt == null || $r2.cbt.isEmpty() : this.cbt.equals($r2.cbt) : false : false : false : false : false;
        }

        public int hashCode() throws  {
            short $s0 = (short) 1231;
            int $i1 = ((this.zzur ? (short) 1231 : (short) 1237) + ((((((((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + zzawx.hashCode(this.zzup)) * 31) + this.zzuq) * 31) + this.name) * 31)) * 31;
            if (!this.zzus) {
                $s0 = (short) 1237;
            }
            $i1 = ($i1 + $s0) * 31;
            int $i2 = (this.cbt == null || this.cbt.isEmpty()) ? 0 : this.cbt.hashCode();
            return $i2 + $i1;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzj($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.zzus) {
                $r1.zzm(1, this.zzus);
            }
            $r1.zzav(2, this.zzuq);
            if (this.zzup != null && this.zzup.length > 0) {
                for (int $i1 : this.zzup) {
                    $r1.zzav(3, $i1);
                }
            }
            if (this.name != 0) {
                $r1.zzav(4, this.name);
            }
            if (this.zzur) {
                $r1.zzm(6, this.zzur);
            }
            super.writeTo($r1);
        }

        public zzb zzac() throws  {
            this.zzup = zzaxc.cbE;
            this.zzuq = 0;
            this.name = 0;
            this.zzur = false;
            this.zzus = false;
            this.cbt = null;
            this.cbC = -1;
            return this;
        }

        public com.google.android.gms.internal.zzah.zzb zzj(com.google.android.gms.internal.zzawq r10) throws java.io.IOException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:18:0x0049
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
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
            r9 = this;
        L_0x0000:
            r0 = r10.ie();
            switch(r0) {
                case 0: goto L_0x000e;
                case 8: goto L_0x000f;
                case 16: goto L_0x0016;
                case 24: goto L_0x001d;
                case 26: goto L_0x005e;
                case 32: goto L_0x00a1;
                case 48: goto L_0x00a8;
                default: goto L_0x0007;
            };
        L_0x0007:
            goto L_0x0008;
        L_0x0008:
            r1 = super.zza(r10, r0);
            if (r1 != 0) goto L_0x0000;
        L_0x000e:
            return r9;
        L_0x000f:
            r1 = r10.ik();
            r9.zzus = r1;
            goto L_0x0000;
        L_0x0016:
            r0 = r10.ii();
            r9.zzuq = r0;
            goto L_0x0000;
        L_0x001d:
            r3 = 24;
            r2 = com.google.android.gms.internal.zzaxc.zzc(r10, r3);
            r4 = r9.zzup;
            if (r4 != 0) goto L_0x0045;
        L_0x0027:
            r0 = 0;
        L_0x0028:
            r2 = r2 + r0;
            r4 = new int[r2];
            if (r0 == 0) goto L_0x0034;
        L_0x002d:
            r5 = r9.zzup;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r0);
        L_0x0034:
            r2 = r4.length;
            r2 = r2 + -1;
            if (r0 >= r2) goto L_0x0051;
        L_0x0039:
            r2 = r10.ii();
            r4[r0] = r2;
            r10.ie();
            r0 = r0 + 1;
            goto L_0x0034;
        L_0x0045:
            r4 = r9.zzup;
            r0 = r4.length;
            goto L_0x0028;
            goto L_0x0051;
        L_0x004a:
            goto L_0x0000;
            goto L_0x0051;
        L_0x004e:
            goto L_0x0000;
        L_0x0051:
            r2 = r10.ii();
            goto L_0x0059;
        L_0x0056:
            goto L_0x0000;
        L_0x0059:
            r4[r0] = r2;
            r9.zzup = r4;
            goto L_0x0000;
        L_0x005e:
            r0 = r10.in();
            r0 = r10.zzarv(r0);
            r2 = r10.getPosition();
            r7 = 0;
        L_0x006b:
            r8 = r10.is();
            if (r8 <= 0) goto L_0x0077;
        L_0x0071:
            r10.ii();
            r7 = r7 + 1;
            goto L_0x006b;
        L_0x0077:
            r10.zzarx(r2);
            r4 = r9.zzup;
            if (r4 != 0) goto L_0x0097;
        L_0x007e:
            r2 = 0;
        L_0x007f:
            r7 = r7 + r2;
            r4 = new int[r7];
            if (r2 == 0) goto L_0x008b;
        L_0x0084:
            r5 = r9.zzup;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r2);
        L_0x008b:
            r7 = r4.length;
            if (r2 >= r7) goto L_0x009b;
        L_0x008e:
            r7 = r10.ii();
            r4[r2] = r7;
            r2 = r2 + 1;
            goto L_0x008b;
        L_0x0097:
            r4 = r9.zzup;
            r2 = r4.length;
            goto L_0x007f;
        L_0x009b:
            r9.zzup = r4;
            r10.zzarw(r0);
            goto L_0x004a;
        L_0x00a1:
            r0 = r10.ii();
            r9.name = r0;
            goto L_0x004e;
        L_0x00a8:
            r1 = r10.ik();
            r9.zzur = r1;
            goto L_0x0056;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzah.zzb.zzj(com.google.android.gms.internal.zzawq):com.google.android.gms.internal.zzah$zzb");
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzc extends zzaws<zzc> {
        private static volatile zzc[] zzut;
        public String zzca;
        public long zzuu;
        public long zzuv;
        public boolean zzuw;
        public long zzux;

        public zzc() throws  {
            zzae();
        }

        public static zzc[] zzad() throws  {
            if (zzut == null) {
                synchronized (zzawx.cbB) {
                    if (zzut == null) {
                        zzut = new zzc[0];
                    }
                }
            }
            return zzut;
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (!this.zzca.equals("")) {
                $i1 = $i0 + zzawr.zzt(1, this.zzca);
            }
            if (this.zzuu != 0) {
                $i1 += zzawr.zzi(2, this.zzuu);
            }
            if (this.zzuv != 2147483647L) {
                $i1 += zzawr.zzi(3, this.zzuv);
            }
            if (this.zzuw) {
                $i1 += zzawr.zzn(4, this.zzuw);
            }
            return this.zzux != 0 ? $i1 + zzawr.zzi(5, this.zzux) : $i1;
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
            return this.zzuu == $r2.zzuu ? this.zzuv == $r2.zzuv ? this.zzuw == $r2.zzuw ? this.zzux == $r2.zzux ? (this.cbt == null || this.cbt.isEmpty()) ? $r2.cbt == null || $r2.cbt.isEmpty() : this.cbt.equals($r2.cbt) : false : false : false : false;
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((((this.zzuw ? (short) 1231 : (short) 1237) + (((((((this.zzca == null ? 0 : this.zzca.hashCode()) + ((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31)) * 31) + ((int) (this.zzuu ^ (this.zzuu >>> 32)))) * 31) + ((int) (this.zzuv ^ (this.zzuv >>> 32)))) * 31)) * 31) + ((int) (this.zzux ^ (this.zzux >>> 32)))) * 31;
            if (!(this.cbt == null || this.cbt.isEmpty())) {
                $i0 = this.cbt.hashCode();
            }
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzk($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (!this.zzca.equals("")) {
                $r1.zzs(1, this.zzca);
            }
            if (this.zzuu != 0) {
                $r1.zzf(2, this.zzuu);
            }
            if (this.zzuv != 2147483647L) {
                $r1.zzf(3, this.zzuv);
            }
            if (this.zzuw) {
                $r1.zzm(4, this.zzuw);
            }
            if (this.zzux != 0) {
                $r1.zzf(5, this.zzux);
            }
            super.writeTo($r1);
        }

        public zzc zzae() throws  {
            this.zzca = "";
            this.zzuu = 0;
            this.zzuv = 2147483647L;
            this.zzuw = false;
            this.zzux = 0;
            this.cbt = null;
            this.cbC = -1;
            return this;
        }

        public zzc zzk(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 10:
                        this.zzca = $r1.readString();
                        continue;
                    case 16:
                        this.zzuu = $r1.ih();
                        continue;
                    case 24:
                        this.zzuv = $r1.ih();
                        continue;
                    case 32:
                        this.zzuw = $r1.ik();
                        continue;
                    case 40:
                        this.zzux = $r1.ih();
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
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzd extends zzaws<zzd> {
        public com.google.android.gms.internal.zzai.zza[] zzuy;
        public com.google.android.gms.internal.zzai.zza[] zzuz;
        public zzc[] zzva;

        public zzd() throws  {
            zzaf();
        }

        protected int computeSerializedSize() throws  {
            int $i1 = super.computeSerializedSize();
            int $i2 = $i1;
            if (this.zzuy != null && this.zzuy.length > 0) {
                $i2 = $i1;
                for (zzawz $r2 : this.zzuy) {
                    if ($r2 != null) {
                        $i2 += zzawr.zzc(1, $r2);
                    }
                }
            }
            if (this.zzuz != null && this.zzuz.length > 0) {
                for (zzawz $r22 : this.zzuz) {
                    if ($r22 != null) {
                        $i2 += zzawr.zzc(2, $r22);
                    }
                }
            }
            if (this.zzva == null || this.zzva.length <= 0) {
                return $i2;
            }
            for (zzawz $r4 : this.zzva) {
                if ($r4 != null) {
                    $i2 += zzawr.zzc(3, $r4);
                }
            }
            return $i2;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzd)) {
                return false;
            }
            zzd $r2 = (zzd) $r1;
            return zzawx.equals(this.zzuy, $r2.zzuy) ? zzawx.equals(this.zzuz, $r2.zzuz) ? zzawx.equals(this.zzva, $r2.zzva) ? (this.cbt == null || this.cbt.isEmpty()) ? $r2.cbt == null || $r2.cbt.isEmpty() : this.cbt.equals($r2.cbt) : false : false : false;
        }

        public int hashCode() throws  {
            int $i0 = (((((((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + zzawx.hashCode(this.zzuy)) * 31) + zzawx.hashCode(this.zzuz)) * 31) + zzawx.hashCode(this.zzva)) * 31;
            int $i1 = (this.cbt == null || this.cbt.isEmpty()) ? 0 : this.cbt.hashCode();
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzl($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.zzuy != null && this.zzuy.length > 0) {
                for (zzawz $r3 : this.zzuy) {
                    if ($r3 != null) {
                        $r1.zza(1, $r3);
                    }
                }
            }
            if (this.zzuz != null && this.zzuz.length > 0) {
                for (zzawz $r32 : this.zzuz) {
                    if ($r32 != null) {
                        $r1.zza(2, $r32);
                    }
                }
            }
            if (this.zzva != null && this.zzva.length > 0) {
                for (zzawz $r5 : this.zzva) {
                    if ($r5 != null) {
                        $r1.zza(3, $r5);
                    }
                }
            }
            super.writeTo($r1);
        }

        public zzd zzaf() throws  {
            this.zzuy = com.google.android.gms.internal.zzai.zza.zzap();
            this.zzuz = com.google.android.gms.internal.zzai.zza.zzap();
            this.zzva = zzc.zzad();
            this.cbt = null;
            this.cbC = -1;
            return this;
        }

        public zzd zzl(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                int $i1;
                com.google.android.gms.internal.zzai.zza[] $r2;
                switch ($i0) {
                    case 0:
                        break;
                    case 10:
                        $i1 = zzaxc.zzc($r1, 10);
                        $i0 = this.zzuy == null ? 0 : this.zzuy.length;
                        $r2 = new com.google.android.gms.internal.zzai.zza[($i1 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.zzuy, 0, $r2, 0, $i0);
                        }
                        while ($i0 < $r2.length - 1) {
                            $r2[$i0] = new com.google.android.gms.internal.zzai.zza();
                            $r1.zza($r2[$i0]);
                            $r1.ie();
                            $i0++;
                        }
                        $r2[$i0] = new com.google.android.gms.internal.zzai.zza();
                        $r1.zza($r2[$i0]);
                        this.zzuy = $r2;
                        continue;
                    case 18:
                        $i1 = zzaxc.zzc($r1, 18);
                        $i0 = this.zzuz == null ? 0 : this.zzuz.length;
                        $r2 = new com.google.android.gms.internal.zzai.zza[($i1 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.zzuz, 0, $r2, 0, $i0);
                        }
                        while ($i0 < $r2.length - 1) {
                            $r2[$i0] = new com.google.android.gms.internal.zzai.zza();
                            $r1.zza($r2[$i0]);
                            $r1.ie();
                            $i0++;
                        }
                        $r2[$i0] = new com.google.android.gms.internal.zzai.zza();
                        $r1.zza($r2[$i0]);
                        this.zzuz = $r2;
                        continue;
                    case 26:
                        $i1 = zzaxc.zzc($r1, 26);
                        $i0 = this.zzva == null ? 0 : this.zzva.length;
                        zzc[] $r5 = new zzc[($i1 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.zzva, 0, $r5, 0, $i0);
                        }
                        while ($i0 < $r5.length - 1) {
                            $r5[$i0] = new zzc();
                            $r1.zza($r5[$i0]);
                            $r1.ie();
                            $i0++;
                        }
                        $r5[$i0] = new zzc();
                        $r1.zza($r5[$i0]);
                        this.zzva = $r5;
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
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zze extends zzaws<zze> {
        private static volatile zze[] zzvb;
        public int key;
        public int value;

        public zze() throws  {
            zzah();
        }

        public static zze[] zzag() throws  {
            if (zzvb == null) {
                synchronized (zzawx.cbB) {
                    if (zzvb == null) {
                        zzvb = new zze[0];
                    }
                }
            }
            return zzvb;
        }

        protected int computeSerializedSize() throws  {
            return (super.computeSerializedSize() + zzawr.zzax(1, this.key)) + zzawr.zzax(2, this.value);
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zze)) {
                return false;
            }
            zze $r2 = (zze) $r1;
            return this.key == $r2.key ? this.value == $r2.value ? (this.cbt == null || this.cbt.isEmpty()) ? $r2.cbt == null || $r2.cbt.isEmpty() : this.cbt.equals($r2.cbt) : false : false;
        }

        public int hashCode() throws  {
            int $i0 = (((((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + this.key) * 31) + this.value) * 31;
            int $i1 = (this.cbt == null || this.cbt.isEmpty()) ? 0 : this.cbt.hashCode();
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzm($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            $r1.zzav(1, this.key);
            $r1.zzav(2, this.value);
            super.writeTo($r1);
        }

        public zze zzah() throws  {
            this.key = 0;
            this.value = 0;
            this.cbt = null;
            this.cbC = -1;
            return this;
        }

        public zze zzm(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 8:
                        this.key = $r1.ii();
                        continue;
                    case 16:
                        this.value = $r1.ii();
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
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzf extends zzaws<zzf> {
        public String version;
        public String[] zzvc;
        public String[] zzvd;
        public com.google.android.gms.internal.zzai.zza[] zzve;
        public zze[] zzvf;
        public zzb[] zzvg;
        public zzb[] zzvh;
        public zzb[] zzvi;
        public zzg[] zzvj;
        public String zzvk;
        public String zzvl;
        public String zzvm;
        public zza zzvn;
        public float zzvo;
        public boolean zzvp;
        public String[] zzvq;
        public int zzvr;

        public zzf() throws  {
            zzai();
        }

        public static zzf zze(byte[] $r0) throws zzawy {
            return (zzf) zzawz.zza(new zzf(), $r0);
        }

        protected int computeSerializedSize() throws  {
            int $i4;
            int $i2;
            int $i3;
            int $i1 = super.computeSerializedSize();
            if (this.zzvd != null && this.zzvd.length > 0) {
                $i4 = 0;
                $i2 = 0;
                for (String $r2 : this.zzvd) {
                    if ($r2 != null) {
                        $i2++;
                        $i4 += zzawr.zzyu($r2);
                    }
                }
                $i1 = ($i1 + $i4) + ($i2 * 1);
            }
            if (this.zzve != null && this.zzve.length > 0) {
                for (zzawz $r4 : this.zzve) {
                    if ($r4 != null) {
                        $i1 += zzawr.zzc(2, $r4);
                    }
                }
            }
            if (this.zzvf != null && this.zzvf.length > 0) {
                for (zzawz $r6 : this.zzvf) {
                    if ($r6 != null) {
                        $i1 += zzawr.zzc(3, $r6);
                    }
                }
            }
            if (this.zzvg != null && this.zzvg.length > 0) {
                for (zzawz $r8 : this.zzvg) {
                    if ($r8 != null) {
                        $i1 += zzawr.zzc(4, $r8);
                    }
                }
            }
            if (this.zzvh != null && this.zzvh.length > 0) {
                for (zzawz $r82 : this.zzvh) {
                    if ($r82 != null) {
                        $i1 += zzawr.zzc(5, $r82);
                    }
                }
            }
            if (this.zzvi != null && this.zzvi.length > 0) {
                for (zzawz $r822 : this.zzvi) {
                    if ($r822 != null) {
                        $i1 += zzawr.zzc(6, $r822);
                    }
                }
            }
            if (this.zzvj != null) {
                zzg[] $r9 = this.zzvj;
                if ($r9.length > 0) {
                    $i2 = 0;
                    while (true) {
                        $r9 = this.zzvj;
                        if ($i2 >= $r9.length) {
                            break;
                        }
                        zzawz $r10 = this.zzvj[$i2];
                        if ($r10 != null) {
                            $i1 += zzawr.zzc(7, $r10);
                        }
                        $i2++;
                    }
                }
            }
            if (!this.zzvk.equals("")) {
                $i1 += zzawr.zzt(9, this.zzvk);
            }
            if (!this.zzvl.equals("")) {
                $i1 += zzawr.zzt(10, this.zzvl);
            }
            if (!this.zzvm.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                $i1 += zzawr.zzt(12, this.zzvm);
            }
            if (!this.version.equals("")) {
                $i1 += zzawr.zzt(13, this.version);
            }
            if (this.zzvn != null) {
                $i1 += zzawr.zzc(14, this.zzvn);
            }
            float $f0 = this.zzvo;
            if (Float.floatToIntBits($f0) != Float.floatToIntBits(0.0f)) {
                $i1 += zzawr.zzd(15, this.zzvo);
            }
            if (this.zzvq != null && this.zzvq.length > 0) {
                $i4 = 0;
                $i2 = 0;
                for (String $r22 : this.zzvq) {
                    if ($r22 != null) {
                        $i2++;
                        $i4 += zzawr.zzyu($r22);
                    }
                }
                $i1 = ($i1 + $i4) + ($i2 * 2);
            }
            if (this.zzvr != 0) {
                $i1 += zzawr.zzax(17, this.zzvr);
            }
            if (this.zzvp) {
                $i1 += zzawr.zzn(18, this.zzvp);
            }
            if (this.zzvc == null || this.zzvc.length <= 0) {
                return $i1;
            }
            $i3 = 0;
            $i2 = 0;
            for (String $r222 : this.zzvc) {
                if ($r222 != null) {
                    $i2++;
                    $i3 += zzawr.zzyu($r222);
                }
            }
            return ($i1 + $i3) + ($i2 * 2);
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzf)) {
                return false;
            }
            zzf $r2 = (zzf) $r1;
            if (!zzawx.equals(this.zzvc, $r2.zzvc)) {
                return false;
            }
            if (!zzawx.equals(this.zzvd, $r2.zzvd)) {
                return false;
            }
            if (!zzawx.equals(this.zzve, $r2.zzve)) {
                return false;
            }
            if (!zzawx.equals(this.zzvf, $r2.zzvf)) {
                return false;
            }
            if (!zzawx.equals(this.zzvg, $r2.zzvg)) {
                return false;
            }
            if (!zzawx.equals(this.zzvh, $r2.zzvh)) {
                return false;
            }
            if (!zzawx.equals(this.zzvi, $r2.zzvi)) {
                return false;
            }
            if (!zzawx.equals(this.zzvj, $r2.zzvj)) {
                return false;
            }
            if (this.zzvk != null) {
                if (!this.zzvk.equals($r2.zzvk)) {
                    return false;
                }
            } else if ($r2.zzvk != null) {
                return false;
            }
            if (this.zzvl != null) {
                if (!this.zzvl.equals($r2.zzvl)) {
                    return false;
                }
            } else if ($r2.zzvl != null) {
                return false;
            }
            if (this.zzvm != null) {
                if (!this.zzvm.equals($r2.zzvm)) {
                    return false;
                }
            } else if ($r2.zzvm != null) {
                return false;
            }
            if (this.version != null) {
                if (!this.version.equals($r2.version)) {
                    return false;
                }
            } else if ($r2.version != null) {
                return false;
            }
            if (this.zzvn != null) {
                if (!this.zzvn.equals($r2.zzvn)) {
                    return false;
                }
            } else if ($r2.zzvn != null) {
                return false;
            }
            float $f0 = this.zzvo;
            int $i0 = Float.floatToIntBits($f0);
            $f0 = $r2.zzvo;
            if ($i0 != Float.floatToIntBits($f0)) {
                return false;
            }
            boolean $z0 = this.zzvp;
            boolean $z1 = $r2.zzvp;
            if ($z0 != $z1) {
                return false;
            }
            if (!zzawx.equals(this.zzvq, $r2.zzvq)) {
                return false;
            }
            if (this.zzvr != $r2.zzvr) {
                return false;
            }
            zzawv $r15;
            if (this.cbt != null) {
                $r15 = this.cbt;
                if (!$r15.isEmpty()) {
                    return this.cbt.equals($r2.cbt);
                }
            }
            if ($r2.cbt != null) {
                $r15 = $r2.cbt;
                if (!$r15.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((((((this.zzvp ? (short) 1231 : (short) 1237) + (((((this.zzvn == null ? 0 : this.zzvn.hashCode()) + (((this.version == null ? 0 : this.version.hashCode()) + (((this.zzvm == null ? 0 : this.zzvm.hashCode()) + (((this.zzvl == null ? 0 : this.zzvl.hashCode()) + (((this.zzvk == null ? 0 : this.zzvk.hashCode()) + ((((((((((((((((((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + zzawx.hashCode(this.zzvc)) * 31) + zzawx.hashCode(this.zzvd)) * 31) + zzawx.hashCode(this.zzve)) * 31) + zzawx.hashCode(this.zzvf)) * 31) + zzawx.hashCode(this.zzvg)) * 31) + zzawx.hashCode(this.zzvh)) * 31) + zzawx.hashCode(this.zzvi)) * 31) + zzawx.hashCode(this.zzvj)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31) + Float.floatToIntBits(this.zzvo)) * 31)) * 31) + zzawx.hashCode(this.zzvq)) * 31) + this.zzvr) * 31;
            if (!(this.cbt == null || this.cbt.isEmpty())) {
                $i0 = this.cbt.hashCode();
            }
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzn($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.zzvd != null && this.zzvd.length > 0) {
                for (String $r3 : this.zzvd) {
                    if ($r3 != null) {
                        $r1.zzs(1, $r3);
                    }
                }
            }
            if (this.zzve != null && this.zzve.length > 0) {
                for (zzawz $r5 : this.zzve) {
                    if ($r5 != null) {
                        $r1.zza(2, $r5);
                    }
                }
            }
            if (this.zzvf != null && this.zzvf.length > 0) {
                for (zzawz $r7 : this.zzvf) {
                    if ($r7 != null) {
                        $r1.zza(3, $r7);
                    }
                }
            }
            if (this.zzvg != null && this.zzvg.length > 0) {
                for (zzawz $r9 : this.zzvg) {
                    if ($r9 != null) {
                        $r1.zza(4, $r9);
                    }
                }
            }
            if (this.zzvh != null && this.zzvh.length > 0) {
                for (zzawz $r92 : this.zzvh) {
                    if ($r92 != null) {
                        $r1.zza(5, $r92);
                    }
                }
            }
            if (this.zzvi != null && this.zzvi.length > 0) {
                for (zzawz $r922 : this.zzvi) {
                    if ($r922 != null) {
                        $r1.zza(6, $r922);
                    }
                }
            }
            if (this.zzvj != null && this.zzvj.length > 0) {
                for (zzawz $r11 : this.zzvj) {
                    if ($r11 != null) {
                        $r1.zza(7, $r11);
                    }
                }
            }
            if (!this.zzvk.equals("")) {
                $r1.zzs(9, this.zzvk);
            }
            if (!this.zzvl.equals("")) {
                $r1.zzs(10, this.zzvl);
            }
            if (!this.zzvm.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                $r1.zzs(12, this.zzvm);
            }
            if (!this.version.equals("")) {
                $r1.zzs(13, this.version);
            }
            if (this.zzvn != null) {
                $r1.zza(14, this.zzvn);
            }
            float $f0 = this.zzvo;
            if (Float.floatToIntBits($f0) != Float.floatToIntBits(0.0f)) {
                $r1.zzc(15, this.zzvo);
            }
            if (this.zzvq != null && this.zzvq.length > 0) {
                for (String $r32 : this.zzvq) {
                    if ($r32 != null) {
                        $r1.zzs(16, $r32);
                    }
                }
            }
            if (this.zzvr != 0) {
                $r1.zzav(17, this.zzvr);
            }
            if (this.zzvp) {
                $r1.zzm(18, this.zzvp);
            }
            if (this.zzvc != null && this.zzvc.length > 0) {
                for (String $r322 : this.zzvc) {
                    if ($r322 != null) {
                        $r1.zzs(19, $r322);
                    }
                }
            }
            super.writeTo($r1);
        }

        public zzf zzai() throws  {
            this.zzvc = zzaxc.cbJ;
            this.zzvd = zzaxc.cbJ;
            this.zzve = com.google.android.gms.internal.zzai.zza.zzap();
            this.zzvf = zze.zzag();
            this.zzvg = zzb.zzab();
            this.zzvh = zzb.zzab();
            this.zzvi = zzb.zzab();
            this.zzvj = zzg.zzaj();
            this.zzvk = "";
            this.zzvl = "";
            this.zzvm = AppEventsConstants.EVENT_PARAM_VALUE_NO;
            this.version = "";
            this.zzvn = null;
            this.zzvo = 0.0f;
            this.zzvp = false;
            this.zzvq = zzaxc.cbJ;
            this.zzvr = 0;
            this.cbt = null;
            this.cbC = -1;
            return this;
        }

        public zzf zzn(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                int $i1;
                String[] $r2;
                zzb[] $r11;
                Object $r112;
                switch ($i0) {
                    case 0:
                        break;
                    case 10:
                        $i1 = zzaxc.zzc($r1, 10);
                        $i0 = this.zzvd == null ? 0 : this.zzvd.length;
                        $r2 = new String[($i1 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.zzvd, 0, $r2, 0, $i0);
                        }
                        while ($i0 < $r2.length - 1) {
                            $r2[$i0] = $r1.readString();
                            $r1.ie();
                            $i0++;
                        }
                        $r2[$i0] = $r1.readString();
                        this.zzvd = $r2;
                        continue;
                    case 18:
                        $i1 = zzaxc.zzc($r1, 18);
                        $i0 = this.zzve == null ? 0 : this.zzve.length;
                        com.google.android.gms.internal.zzai.zza[] $r5 = new com.google.android.gms.internal.zzai.zza[($i1 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.zzve, 0, $r5, 0, $i0);
                        }
                        while ($i0 < $r5.length - 1) {
                            $r5[$i0] = new com.google.android.gms.internal.zzai.zza();
                            $r1.zza($r5[$i0]);
                            $r1.ie();
                            $i0++;
                        }
                        $r5[$i0] = new com.google.android.gms.internal.zzai.zza();
                        $r1.zza($r5[$i0]);
                        this.zzve = $r5;
                        continue;
                    case 26:
                        $i1 = zzaxc.zzc($r1, 26);
                        $i0 = this.zzvf == null ? 0 : this.zzvf.length;
                        zze[] $r8 = new zze[($i1 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.zzvf, 0, $r8, 0, $i0);
                        }
                        while ($i0 < $r8.length - 1) {
                            $r8[$i0] = new zze();
                            $r1.zza($r8[$i0]);
                            $r1.ie();
                            $i0++;
                        }
                        $r8[$i0] = new zze();
                        $r1.zza($r8[$i0]);
                        this.zzvf = $r8;
                        continue;
                    case 34:
                        $i1 = zzaxc.zzc($r1, 34);
                        if (this.zzvg == null) {
                            $i0 = 0;
                        } else {
                            $r11 = this.zzvg;
                            $i0 = $r11.length;
                        }
                        $r112 = new zzb[($i1 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.zzvg, 0, $r112, 0, $i0);
                        }
                        while ($i0 < $r112.length - 1) {
                            $r112[$i0] = new zzb();
                            $r1.zza($r112[$i0]);
                            $r1.ie();
                            $i0++;
                        }
                        $r112[$i0] = new zzb();
                        $r1.zza($r112[$i0]);
                        this.zzvg = $r112;
                        continue;
                    case 42:
                        $i1 = zzaxc.zzc($r1, 42);
                        if (this.zzvh == null) {
                            $i0 = 0;
                        } else {
                            $r11 = this.zzvh;
                            $i0 = $r11.length;
                        }
                        $r112 = new zzb[($i1 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.zzvh, 0, $r112, 0, $i0);
                        }
                        while ($i0 < $r112.length - 1) {
                            $r112[$i0] = new zzb();
                            $r1.zza($r112[$i0]);
                            $r1.ie();
                            $i0++;
                        }
                        $r112[$i0] = new zzb();
                        $r1.zza($r112[$i0]);
                        this.zzvh = $r112;
                        continue;
                    case 50:
                        $i1 = zzaxc.zzc($r1, 50);
                        if (this.zzvi == null) {
                            $i0 = 0;
                        } else {
                            $r11 = this.zzvi;
                            $i0 = $r11.length;
                        }
                        $r112 = new zzb[($i1 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.zzvi, 0, $r112, 0, $i0);
                        }
                        while ($i0 < $r112.length - 1) {
                            $r112[$i0] = new zzb();
                            $r1.zza($r112[$i0]);
                            $r1.ie();
                            $i0++;
                        }
                        $r112[$i0] = new zzb();
                        $r1.zza($r112[$i0]);
                        this.zzvi = $r112;
                        continue;
                    case 58:
                        $i1 = zzaxc.zzc($r1, 58);
                        if (this.zzvj == null) {
                            $i0 = 0;
                        } else {
                            zzg[] $r14 = this.zzvj;
                            $i0 = $r14.length;
                        }
                        Object $r142 = new zzg[($i1 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.zzvj, 0, $r142, 0, $i0);
                        }
                        while ($i0 < $r142.length - 1) {
                            $r142[$i0] = new zzg();
                            $r1.zza($r142[$i0]);
                            $r1.ie();
                            $i0++;
                        }
                        $r142[$i0] = new zzg();
                        $r1.zza($r142[$i0]);
                        this.zzvj = $r142;
                        continue;
                    case 74:
                        this.zzvk = $r1.readString();
                        continue;
                    case 82:
                        this.zzvl = $r1.readString();
                        continue;
                    case 98:
                        this.zzvm = $r1.readString();
                        continue;
                    case 106:
                        this.version = $r1.readString();
                        continue;
                    case 114:
                        if (this.zzvn == null) {
                            this.zzvn = new zza();
                        }
                        $r1.zza(this.zzvn);
                        continue;
                    case 125:
                        this.zzvo = $r1.readFloat();
                        continue;
                    case 130:
                        $i1 = zzaxc.zzc($r1, 130);
                        $i0 = this.zzvq == null ? 0 : this.zzvq.length;
                        $r2 = new String[($i1 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.zzvq, 0, $r2, 0, $i0);
                        }
                        while ($i0 < $r2.length - 1) {
                            $r2[$i0] = $r1.readString();
                            $r1.ie();
                            $i0++;
                        }
                        $r2[$i0] = $r1.readString();
                        this.zzvq = $r2;
                        continue;
                    case 136:
                        this.zzvr = $r1.ii();
                        continue;
                    case 144:
                        this.zzvp = $r1.ik();
                        continue;
                    case 154:
                        $i1 = zzaxc.zzc($r1, 154);
                        $i0 = this.zzvc == null ? 0 : this.zzvc.length;
                        $r2 = new String[($i1 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.zzvc, 0, $r2, 0, $i0);
                        }
                        while ($i0 < $r2.length - 1) {
                            $r2[$i0] = $r1.readString();
                            $r1.ie();
                            $i0++;
                        }
                        $r2[$i0] = $r1.readString();
                        this.zzvc = $r2;
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
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzg extends zzaws<zzg> {
        private static volatile zzg[] zzvs;
        public int[] zzvt;
        public int[] zzvu;
        public int[] zzvv;
        public int[] zzvw;
        public int[] zzvx;
        public int[] zzvy;
        public int[] zzvz;
        public int[] zzwa;
        public int[] zzwb;
        public int[] zzwc;

        public zzg() throws  {
            zzak();
        }

        public static zzg[] zzaj() throws  {
            if (zzvs == null) {
                synchronized (zzawx.cbB) {
                    if (zzvs == null) {
                        zzvs = new zzg[0];
                    }
                }
            }
            return zzvs;
        }

        protected int computeSerializedSize() throws  {
            int $i3;
            int $i2;
            int $i1 = super.computeSerializedSize();
            if (this.zzvt != null && this.zzvt.length > 0) {
                $i3 = 0;
                for (int $i4 : this.zzvt) {
                    $i3 += zzawr.zzasb($i4);
                }
                $i1 = ($i1 + $i3) + (this.zzvt.length * 1);
            }
            if (this.zzvu != null && this.zzvu.length > 0) {
                $i3 = 0;
                for (int $i42 : this.zzvu) {
                    $i3 += zzawr.zzasb($i42);
                }
                $i1 = ($i1 + $i3) + (this.zzvu.length * 1);
            }
            if (this.zzvv != null && this.zzvv.length > 0) {
                $i3 = 0;
                for (int $i422 : this.zzvv) {
                    $i3 += zzawr.zzasb($i422);
                }
                $i1 = ($i1 + $i3) + (this.zzvv.length * 1);
            }
            if (this.zzvw != null && this.zzvw.length > 0) {
                $i3 = 0;
                for (int $i4222 : this.zzvw) {
                    $i3 += zzawr.zzasb($i4222);
                }
                $i1 = ($i1 + $i3) + (this.zzvw.length * 1);
            }
            if (this.zzvx != null && this.zzvx.length > 0) {
                $i3 = 0;
                for (int $i42222 : this.zzvx) {
                    $i3 += zzawr.zzasb($i42222);
                }
                $i1 = ($i1 + $i3) + (this.zzvx.length * 1);
            }
            if (this.zzvy != null && this.zzvy.length > 0) {
                $i3 = 0;
                for (int $i422222 : this.zzvy) {
                    $i3 += zzawr.zzasb($i422222);
                }
                $i1 = ($i1 + $i3) + (this.zzvy.length * 1);
            }
            if (this.zzvz != null && this.zzvz.length > 0) {
                $i3 = 0;
                for (int $i4222222 : this.zzvz) {
                    $i3 += zzawr.zzasb($i4222222);
                }
                $i1 = ($i1 + $i3) + (this.zzvz.length * 1);
            }
            if (this.zzwa != null && this.zzwa.length > 0) {
                $i3 = 0;
                for (int $i42222222 : this.zzwa) {
                    $i3 += zzawr.zzasb($i42222222);
                }
                $i1 = ($i1 + $i3) + (this.zzwa.length * 1);
            }
            if (this.zzwb != null && this.zzwb.length > 0) {
                $i3 = 0;
                for (int $i422222222 : this.zzwb) {
                    $i3 += zzawr.zzasb($i422222222);
                }
                $i1 = ($i1 + $i3) + (this.zzwb.length * 1);
            }
            if (this.zzwc == null || this.zzwc.length <= 0) {
                return $i1;
            }
            $i2 = 0;
            for (int $i32 : this.zzwc) {
                $i2 += zzawr.zzasb($i32);
            }
            return ($i1 + $i2) + (this.zzwc.length * 1);
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzg)) {
                return false;
            }
            zzg $r2 = (zzg) $r1;
            return zzawx.equals(this.zzvt, $r2.zzvt) ? zzawx.equals(this.zzvu, $r2.zzvu) ? zzawx.equals(this.zzvv, $r2.zzvv) ? zzawx.equals(this.zzvw, $r2.zzvw) ? zzawx.equals(this.zzvx, $r2.zzvx) ? zzawx.equals(this.zzvy, $r2.zzvy) ? zzawx.equals(this.zzvz, $r2.zzvz) ? zzawx.equals(this.zzwa, $r2.zzwa) ? zzawx.equals(this.zzwb, $r2.zzwb) ? zzawx.equals(this.zzwc, $r2.zzwc) ? (this.cbt == null || this.cbt.isEmpty()) ? $r2.cbt == null || $r2.cbt.isEmpty() : this.cbt.equals($r2.cbt) : false : false : false : false : false : false : false : false : false : false;
        }

        public int hashCode() throws  {
            int $i0 = (((((((((((((((((((((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + zzawx.hashCode(this.zzvt)) * 31) + zzawx.hashCode(this.zzvu)) * 31) + zzawx.hashCode(this.zzvv)) * 31) + zzawx.hashCode(this.zzvw)) * 31) + zzawx.hashCode(this.zzvx)) * 31) + zzawx.hashCode(this.zzvy)) * 31) + zzawx.hashCode(this.zzvz)) * 31) + zzawx.hashCode(this.zzwa)) * 31) + zzawx.hashCode(this.zzwb)) * 31) + zzawx.hashCode(this.zzwc)) * 31;
            int $i1 = (this.cbt == null || this.cbt.isEmpty()) ? 0 : this.cbt.hashCode();
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzo($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.zzvt != null && this.zzvt.length > 0) {
                for (int $i2 : this.zzvt) {
                    $r1.zzav(1, $i2);
                }
            }
            if (this.zzvu != null && this.zzvu.length > 0) {
                for (int $i22 : this.zzvu) {
                    $r1.zzav(2, $i22);
                }
            }
            if (this.zzvv != null && this.zzvv.length > 0) {
                for (int $i222 : this.zzvv) {
                    $r1.zzav(3, $i222);
                }
            }
            if (this.zzvw != null && this.zzvw.length > 0) {
                for (int $i2222 : this.zzvw) {
                    $r1.zzav(4, $i2222);
                }
            }
            if (this.zzvx != null && this.zzvx.length > 0) {
                for (int $i22222 : this.zzvx) {
                    $r1.zzav(5, $i22222);
                }
            }
            if (this.zzvy != null && this.zzvy.length > 0) {
                for (int $i222222 : this.zzvy) {
                    $r1.zzav(6, $i222222);
                }
            }
            if (this.zzvz != null && this.zzvz.length > 0) {
                for (int $i2222222 : this.zzvz) {
                    $r1.zzav(7, $i2222222);
                }
            }
            if (this.zzwa != null && this.zzwa.length > 0) {
                for (int $i22222222 : this.zzwa) {
                    $r1.zzav(8, $i22222222);
                }
            }
            if (this.zzwb != null && this.zzwb.length > 0) {
                for (int $i222222222 : this.zzwb) {
                    $r1.zzav(9, $i222222222);
                }
            }
            if (this.zzwc != null && this.zzwc.length > 0) {
                for (int $i1 : this.zzwc) {
                    $r1.zzav(10, $i1);
                }
            }
            super.writeTo($r1);
        }

        public zzg zzak() throws  {
            this.zzvt = zzaxc.cbE;
            this.zzvu = zzaxc.cbE;
            this.zzvv = zzaxc.cbE;
            this.zzvw = zzaxc.cbE;
            this.zzvx = zzaxc.cbE;
            this.zzvy = zzaxc.cbE;
            this.zzvz = zzaxc.cbE;
            this.zzwa = zzaxc.cbE;
            this.zzwb = zzaxc.cbE;
            this.zzwc = zzaxc.cbE;
            this.cbt = null;
            this.cbC = -1;
            return this;
        }

        public com.google.android.gms.internal.zzah.zzg zzo(com.google.android.gms.internal.zzawq r10) throws java.io.IOException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:23:0x0061 in {4, 5, 8, 11, 14, 15, 17, 18, 22, 24, 27, 30, 33, 34, 38, 41, 44, 45, 50, 53, 56, 59, 60, 62, 66, 69, 72, 73, 75, 80, 83, 86, 89, 90, 92, 96, 99, 102, 103, 105, 110, 113, 116, 119, 120, 122, 126, 129, 132, 133, 135, 140, 143, 146, 149, 150, 152, 156, 159, 162, 163, 165, 170, 173, 176, 179, 180, 182, 186, 189, 192, 193, 195, 200, 203, 206, 209, 210, 212, 216, 219, 222, 223, 225, 230, 233, 236, 239, 240, 242, 246, 249, 252, 253, 255, 260, 263, 266, 269, 270, 272, 276, 279, 282, 283, 285, 290, 293, 296, 299, 300, 302} preds:[]
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
            r9 = this;
        L_0x0000:
            r0 = r10.ie();
            switch(r0) {
                case 0: goto L_0x000e;
                case 8: goto L_0x000f;
                case 10: goto L_0x0048;
                case 16: goto L_0x008f;
                case 18: goto L_0x00c4;
                case 24: goto L_0x010b;
                case 26: goto L_0x0144;
                case 32: goto L_0x018b;
                case 34: goto L_0x01c4;
                case 40: goto L_0x020b;
                case 42: goto L_0x0244;
                case 48: goto L_0x028b;
                case 50: goto L_0x02c4;
                case 56: goto L_0x030b;
                case 58: goto L_0x0344;
                case 64: goto L_0x038b;
                case 66: goto L_0x03c4;
                case 72: goto L_0x040b;
                case 74: goto L_0x0444;
                case 80: goto L_0x048b;
                case 82: goto L_0x04c4;
                default: goto L_0x0007;
            };
        L_0x0007:
            goto L_0x0008;
        L_0x0008:
            r1 = super.zza(r10, r0);
            if (r1 != 0) goto L_0x0000;
        L_0x000e:
            return r9;
        L_0x000f:
            r3 = 8;
            r2 = com.google.android.gms.internal.zzaxc.zzc(r10, r3);
            r4 = r9.zzvt;
            if (r4 != 0) goto L_0x0037;
        L_0x0019:
            r0 = 0;
        L_0x001a:
            r2 = r2 + r0;
            r4 = new int[r2];
            if (r0 == 0) goto L_0x0026;
        L_0x001f:
            r5 = r9.zzvt;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r0);
        L_0x0026:
            r2 = r4.length;
            r2 = r2 + -1;
            if (r0 >= r2) goto L_0x003b;
        L_0x002b:
            r2 = r10.ii();
            r4[r0] = r2;
            r10.ie();
            r0 = r0 + 1;
            goto L_0x0026;
        L_0x0037:
            r4 = r9.zzvt;
            r0 = r4.length;
            goto L_0x001a;
        L_0x003b:
            r2 = r10.ii();
            r4[r0] = r2;
            r9.zzvt = r4;
            goto L_0x0047;
        L_0x0044:
            goto L_0x0000;
        L_0x0047:
            goto L_0x0000;
        L_0x0048:
            r0 = r10.in();
            r0 = r10.zzarv(r0);
            r2 = r10.getPosition();
            r7 = 0;
        L_0x0055:
            r8 = r10.is();
            if (r8 <= 0) goto L_0x0065;
        L_0x005b:
            r10.ii();
            r7 = r7 + 1;
            goto L_0x0055;
            goto L_0x0065;
        L_0x0062:
            goto L_0x0000;
        L_0x0065:
            r10.zzarx(r2);
            r4 = r9.zzvt;
            if (r4 != 0) goto L_0x0085;
        L_0x006c:
            r2 = 0;
        L_0x006d:
            r7 = r7 + r2;
            r4 = new int[r7];
            if (r2 == 0) goto L_0x0079;
        L_0x0072:
            r5 = r9.zzvt;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r2);
        L_0x0079:
            r7 = r4.length;
            if (r2 >= r7) goto L_0x0089;
        L_0x007c:
            r7 = r10.ii();
            r4[r2] = r7;
            r2 = r2 + 1;
            goto L_0x0079;
        L_0x0085:
            r4 = r9.zzvt;
            r2 = r4.length;
            goto L_0x006d;
        L_0x0089:
            r9.zzvt = r4;
            r10.zzarw(r0);
            goto L_0x0044;
        L_0x008f:
            r3 = 16;
            r2 = com.google.android.gms.internal.zzaxc.zzc(r10, r3);
            r4 = r9.zzvu;
            if (r4 != 0) goto L_0x00b7;
        L_0x0099:
            r0 = 0;
        L_0x009a:
            r2 = r2 + r0;
            r4 = new int[r2];
            if (r0 == 0) goto L_0x00a6;
        L_0x009f:
            r5 = r9.zzvu;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r0);
        L_0x00a6:
            r2 = r4.length;
            r2 = r2 + -1;
            if (r0 >= r2) goto L_0x00bb;
        L_0x00ab:
            r2 = r10.ii();
            r4[r0] = r2;
            r10.ie();
            r0 = r0 + 1;
            goto L_0x00a6;
        L_0x00b7:
            r4 = r9.zzvu;
            r0 = r4.length;
            goto L_0x009a;
        L_0x00bb:
            r2 = r10.ii();
            r4[r0] = r2;
            r9.zzvu = r4;
            goto L_0x0062;
        L_0x00c4:
            r0 = r10.in();
            r0 = r10.zzarv(r0);
            r2 = r10.getPosition();
            r7 = 0;
        L_0x00d1:
            r8 = r10.is();
            if (r8 <= 0) goto L_0x00dd;
        L_0x00d7:
            r10.ii();
            r7 = r7 + 1;
            goto L_0x00d1;
        L_0x00dd:
            r10.zzarx(r2);
            r4 = r9.zzvu;
            if (r4 != 0) goto L_0x00fd;
        L_0x00e4:
            r2 = 0;
        L_0x00e5:
            r7 = r7 + r2;
            r4 = new int[r7];
            if (r2 == 0) goto L_0x00f1;
        L_0x00ea:
            r5 = r9.zzvu;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r2);
        L_0x00f1:
            r7 = r4.length;
            if (r2 >= r7) goto L_0x0101;
        L_0x00f4:
            r7 = r10.ii();
            r4[r2] = r7;
            r2 = r2 + 1;
            goto L_0x00f1;
        L_0x00fd:
            r4 = r9.zzvu;
            r2 = r4.length;
            goto L_0x00e5;
        L_0x0101:
            r9.zzvu = r4;
            goto L_0x0107;
        L_0x0104:
            goto L_0x0000;
        L_0x0107:
            r10.zzarw(r0);
            goto L_0x0104;
        L_0x010b:
            r3 = 24;
            r2 = com.google.android.gms.internal.zzaxc.zzc(r10, r3);
            r4 = r9.zzvv;
            if (r4 != 0) goto L_0x0133;
        L_0x0115:
            r0 = 0;
        L_0x0116:
            r2 = r2 + r0;
            r4 = new int[r2];
            if (r0 == 0) goto L_0x0122;
        L_0x011b:
            r5 = r9.zzvv;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r0);
        L_0x0122:
            r2 = r4.length;
            r2 = r2 + -1;
            if (r0 >= r2) goto L_0x0137;
        L_0x0127:
            r2 = r10.ii();
            r4[r0] = r2;
            r10.ie();
            r0 = r0 + 1;
            goto L_0x0122;
        L_0x0133:
            r4 = r9.zzvv;
            r0 = r4.length;
            goto L_0x0116;
        L_0x0137:
            r2 = r10.ii();
            r4[r0] = r2;
            goto L_0x0141;
        L_0x013e:
            goto L_0x0000;
        L_0x0141:
            r9.zzvv = r4;
            goto L_0x013e;
        L_0x0144:
            r0 = r10.in();
            r0 = r10.zzarv(r0);
            r2 = r10.getPosition();
            r7 = 0;
        L_0x0151:
            r8 = r10.is();
            if (r8 <= 0) goto L_0x015d;
        L_0x0157:
            r10.ii();
            r7 = r7 + 1;
            goto L_0x0151;
        L_0x015d:
            r10.zzarx(r2);
            r4 = r9.zzvv;
            if (r4 != 0) goto L_0x017d;
        L_0x0164:
            r2 = 0;
        L_0x0165:
            r7 = r7 + r2;
            r4 = new int[r7];
            if (r2 == 0) goto L_0x0171;
        L_0x016a:
            r5 = r9.zzvv;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r2);
        L_0x0171:
            r7 = r4.length;
            if (r2 >= r7) goto L_0x0181;
        L_0x0174:
            r7 = r10.ii();
            r4[r2] = r7;
            r2 = r2 + 1;
            goto L_0x0171;
        L_0x017d:
            r4 = r9.zzvv;
            r2 = r4.length;
            goto L_0x0165;
        L_0x0181:
            r9.zzvv = r4;
            goto L_0x0187;
        L_0x0184:
            goto L_0x0000;
        L_0x0187:
            r10.zzarw(r0);
            goto L_0x0184;
        L_0x018b:
            r3 = 32;
            r2 = com.google.android.gms.internal.zzaxc.zzc(r10, r3);
            r4 = r9.zzvw;
            if (r4 != 0) goto L_0x01b3;
        L_0x0195:
            r0 = 0;
        L_0x0196:
            r2 = r2 + r0;
            r4 = new int[r2];
            if (r0 == 0) goto L_0x01a2;
        L_0x019b:
            r5 = r9.zzvw;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r0);
        L_0x01a2:
            r2 = r4.length;
            r2 = r2 + -1;
            if (r0 >= r2) goto L_0x01b7;
        L_0x01a7:
            r2 = r10.ii();
            r4[r0] = r2;
            r10.ie();
            r0 = r0 + 1;
            goto L_0x01a2;
        L_0x01b3:
            r4 = r9.zzvw;
            r0 = r4.length;
            goto L_0x0196;
        L_0x01b7:
            r2 = r10.ii();
            r4[r0] = r2;
            goto L_0x01c1;
        L_0x01be:
            goto L_0x0000;
        L_0x01c1:
            r9.zzvw = r4;
            goto L_0x01be;
        L_0x01c4:
            r0 = r10.in();
            r0 = r10.zzarv(r0);
            r2 = r10.getPosition();
            r7 = 0;
        L_0x01d1:
            r8 = r10.is();
            if (r8 <= 0) goto L_0x01dd;
        L_0x01d7:
            r10.ii();
            r7 = r7 + 1;
            goto L_0x01d1;
        L_0x01dd:
            r10.zzarx(r2);
            r4 = r9.zzvw;
            if (r4 != 0) goto L_0x01fd;
        L_0x01e4:
            r2 = 0;
        L_0x01e5:
            r7 = r7 + r2;
            r4 = new int[r7];
            if (r2 == 0) goto L_0x01f1;
        L_0x01ea:
            r5 = r9.zzvw;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r2);
        L_0x01f1:
            r7 = r4.length;
            if (r2 >= r7) goto L_0x0201;
        L_0x01f4:
            r7 = r10.ii();
            r4[r2] = r7;
            r2 = r2 + 1;
            goto L_0x01f1;
        L_0x01fd:
            r4 = r9.zzvw;
            r2 = r4.length;
            goto L_0x01e5;
        L_0x0201:
            r9.zzvw = r4;
            goto L_0x0207;
        L_0x0204:
            goto L_0x0000;
        L_0x0207:
            r10.zzarw(r0);
            goto L_0x0204;
        L_0x020b:
            r3 = 40;
            r2 = com.google.android.gms.internal.zzaxc.zzc(r10, r3);
            r4 = r9.zzvx;
            if (r4 != 0) goto L_0x0233;
        L_0x0215:
            r0 = 0;
        L_0x0216:
            r2 = r2 + r0;
            r4 = new int[r2];
            if (r0 == 0) goto L_0x0222;
        L_0x021b:
            r5 = r9.zzvx;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r0);
        L_0x0222:
            r2 = r4.length;
            r2 = r2 + -1;
            if (r0 >= r2) goto L_0x0237;
        L_0x0227:
            r2 = r10.ii();
            r4[r0] = r2;
            r10.ie();
            r0 = r0 + 1;
            goto L_0x0222;
        L_0x0233:
            r4 = r9.zzvx;
            r0 = r4.length;
            goto L_0x0216;
        L_0x0237:
            r2 = r10.ii();
            r4[r0] = r2;
            goto L_0x0241;
        L_0x023e:
            goto L_0x0000;
        L_0x0241:
            r9.zzvx = r4;
            goto L_0x023e;
        L_0x0244:
            r0 = r10.in();
            r0 = r10.zzarv(r0);
            r2 = r10.getPosition();
            r7 = 0;
        L_0x0251:
            r8 = r10.is();
            if (r8 <= 0) goto L_0x025d;
        L_0x0257:
            r10.ii();
            r7 = r7 + 1;
            goto L_0x0251;
        L_0x025d:
            r10.zzarx(r2);
            r4 = r9.zzvx;
            if (r4 != 0) goto L_0x027d;
        L_0x0264:
            r2 = 0;
        L_0x0265:
            r7 = r7 + r2;
            r4 = new int[r7];
            if (r2 == 0) goto L_0x0271;
        L_0x026a:
            r5 = r9.zzvx;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r2);
        L_0x0271:
            r7 = r4.length;
            if (r2 >= r7) goto L_0x0281;
        L_0x0274:
            r7 = r10.ii();
            r4[r2] = r7;
            r2 = r2 + 1;
            goto L_0x0271;
        L_0x027d:
            r4 = r9.zzvx;
            r2 = r4.length;
            goto L_0x0265;
        L_0x0281:
            r9.zzvx = r4;
            goto L_0x0287;
        L_0x0284:
            goto L_0x0000;
        L_0x0287:
            r10.zzarw(r0);
            goto L_0x0284;
        L_0x028b:
            r3 = 48;
            r2 = com.google.android.gms.internal.zzaxc.zzc(r10, r3);
            r4 = r9.zzvy;
            if (r4 != 0) goto L_0x02b3;
        L_0x0295:
            r0 = 0;
        L_0x0296:
            r2 = r2 + r0;
            r4 = new int[r2];
            if (r0 == 0) goto L_0x02a2;
        L_0x029b:
            r5 = r9.zzvy;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r0);
        L_0x02a2:
            r2 = r4.length;
            r2 = r2 + -1;
            if (r0 >= r2) goto L_0x02b7;
        L_0x02a7:
            r2 = r10.ii();
            r4[r0] = r2;
            r10.ie();
            r0 = r0 + 1;
            goto L_0x02a2;
        L_0x02b3:
            r4 = r9.zzvy;
            r0 = r4.length;
            goto L_0x0296;
        L_0x02b7:
            r2 = r10.ii();
            r4[r0] = r2;
            goto L_0x02c1;
        L_0x02be:
            goto L_0x0000;
        L_0x02c1:
            r9.zzvy = r4;
            goto L_0x02be;
        L_0x02c4:
            r0 = r10.in();
            r0 = r10.zzarv(r0);
            r2 = r10.getPosition();
            r7 = 0;
        L_0x02d1:
            r8 = r10.is();
            if (r8 <= 0) goto L_0x02dd;
        L_0x02d7:
            r10.ii();
            r7 = r7 + 1;
            goto L_0x02d1;
        L_0x02dd:
            r10.zzarx(r2);
            r4 = r9.zzvy;
            if (r4 != 0) goto L_0x02fd;
        L_0x02e4:
            r2 = 0;
        L_0x02e5:
            r7 = r7 + r2;
            r4 = new int[r7];
            if (r2 == 0) goto L_0x02f1;
        L_0x02ea:
            r5 = r9.zzvy;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r2);
        L_0x02f1:
            r7 = r4.length;
            if (r2 >= r7) goto L_0x0301;
        L_0x02f4:
            r7 = r10.ii();
            r4[r2] = r7;
            r2 = r2 + 1;
            goto L_0x02f1;
        L_0x02fd:
            r4 = r9.zzvy;
            r2 = r4.length;
            goto L_0x02e5;
        L_0x0301:
            r9.zzvy = r4;
            goto L_0x0307;
        L_0x0304:
            goto L_0x0000;
        L_0x0307:
            r10.zzarw(r0);
            goto L_0x0304;
        L_0x030b:
            r3 = 56;
            r2 = com.google.android.gms.internal.zzaxc.zzc(r10, r3);
            r4 = r9.zzvz;
            if (r4 != 0) goto L_0x0333;
        L_0x0315:
            r0 = 0;
        L_0x0316:
            r2 = r2 + r0;
            r4 = new int[r2];
            if (r0 == 0) goto L_0x0322;
        L_0x031b:
            r5 = r9.zzvz;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r0);
        L_0x0322:
            r2 = r4.length;
            r2 = r2 + -1;
            if (r0 >= r2) goto L_0x0337;
        L_0x0327:
            r2 = r10.ii();
            r4[r0] = r2;
            r10.ie();
            r0 = r0 + 1;
            goto L_0x0322;
        L_0x0333:
            r4 = r9.zzvz;
            r0 = r4.length;
            goto L_0x0316;
        L_0x0337:
            r2 = r10.ii();
            r4[r0] = r2;
            goto L_0x0341;
        L_0x033e:
            goto L_0x0000;
        L_0x0341:
            r9.zzvz = r4;
            goto L_0x033e;
        L_0x0344:
            r0 = r10.in();
            r0 = r10.zzarv(r0);
            r2 = r10.getPosition();
            r7 = 0;
        L_0x0351:
            r8 = r10.is();
            if (r8 <= 0) goto L_0x035d;
        L_0x0357:
            r10.ii();
            r7 = r7 + 1;
            goto L_0x0351;
        L_0x035d:
            r10.zzarx(r2);
            r4 = r9.zzvz;
            if (r4 != 0) goto L_0x037d;
        L_0x0364:
            r2 = 0;
        L_0x0365:
            r7 = r7 + r2;
            r4 = new int[r7];
            if (r2 == 0) goto L_0x0371;
        L_0x036a:
            r5 = r9.zzvz;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r2);
        L_0x0371:
            r7 = r4.length;
            if (r2 >= r7) goto L_0x0381;
        L_0x0374:
            r7 = r10.ii();
            r4[r2] = r7;
            r2 = r2 + 1;
            goto L_0x0371;
        L_0x037d:
            r4 = r9.zzvz;
            r2 = r4.length;
            goto L_0x0365;
        L_0x0381:
            r9.zzvz = r4;
            goto L_0x0387;
        L_0x0384:
            goto L_0x0000;
        L_0x0387:
            r10.zzarw(r0);
            goto L_0x0384;
        L_0x038b:
            r3 = 64;
            r2 = com.google.android.gms.internal.zzaxc.zzc(r10, r3);
            r4 = r9.zzwa;
            if (r4 != 0) goto L_0x03b3;
        L_0x0395:
            r0 = 0;
        L_0x0396:
            r2 = r2 + r0;
            r4 = new int[r2];
            if (r0 == 0) goto L_0x03a2;
        L_0x039b:
            r5 = r9.zzwa;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r0);
        L_0x03a2:
            r2 = r4.length;
            r2 = r2 + -1;
            if (r0 >= r2) goto L_0x03b7;
        L_0x03a7:
            r2 = r10.ii();
            r4[r0] = r2;
            r10.ie();
            r0 = r0 + 1;
            goto L_0x03a2;
        L_0x03b3:
            r4 = r9.zzwa;
            r0 = r4.length;
            goto L_0x0396;
        L_0x03b7:
            r2 = r10.ii();
            r4[r0] = r2;
            goto L_0x03c1;
        L_0x03be:
            goto L_0x0000;
        L_0x03c1:
            r9.zzwa = r4;
            goto L_0x03be;
        L_0x03c4:
            r0 = r10.in();
            r0 = r10.zzarv(r0);
            r2 = r10.getPosition();
            r7 = 0;
        L_0x03d1:
            r8 = r10.is();
            if (r8 <= 0) goto L_0x03dd;
        L_0x03d7:
            r10.ii();
            r7 = r7 + 1;
            goto L_0x03d1;
        L_0x03dd:
            r10.zzarx(r2);
            r4 = r9.zzwa;
            if (r4 != 0) goto L_0x03fd;
        L_0x03e4:
            r2 = 0;
        L_0x03e5:
            r7 = r7 + r2;
            r4 = new int[r7];
            if (r2 == 0) goto L_0x03f1;
        L_0x03ea:
            r5 = r9.zzwa;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r2);
        L_0x03f1:
            r7 = r4.length;
            if (r2 >= r7) goto L_0x0401;
        L_0x03f4:
            r7 = r10.ii();
            r4[r2] = r7;
            r2 = r2 + 1;
            goto L_0x03f1;
        L_0x03fd:
            r4 = r9.zzwa;
            r2 = r4.length;
            goto L_0x03e5;
        L_0x0401:
            r9.zzwa = r4;
            goto L_0x0407;
        L_0x0404:
            goto L_0x0000;
        L_0x0407:
            r10.zzarw(r0);
            goto L_0x0404;
        L_0x040b:
            r3 = 72;
            r2 = com.google.android.gms.internal.zzaxc.zzc(r10, r3);
            r4 = r9.zzwb;
            if (r4 != 0) goto L_0x0433;
        L_0x0415:
            r0 = 0;
        L_0x0416:
            r2 = r2 + r0;
            r4 = new int[r2];
            if (r0 == 0) goto L_0x0422;
        L_0x041b:
            r5 = r9.zzwb;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r0);
        L_0x0422:
            r2 = r4.length;
            r2 = r2 + -1;
            if (r0 >= r2) goto L_0x0437;
        L_0x0427:
            r2 = r10.ii();
            r4[r0] = r2;
            r10.ie();
            r0 = r0 + 1;
            goto L_0x0422;
        L_0x0433:
            r4 = r9.zzwb;
            r0 = r4.length;
            goto L_0x0416;
        L_0x0437:
            r2 = r10.ii();
            r4[r0] = r2;
            goto L_0x0441;
        L_0x043e:
            goto L_0x0000;
        L_0x0441:
            r9.zzwb = r4;
            goto L_0x043e;
        L_0x0444:
            r0 = r10.in();
            r0 = r10.zzarv(r0);
            r2 = r10.getPosition();
            r7 = 0;
        L_0x0451:
            r8 = r10.is();
            if (r8 <= 0) goto L_0x045d;
        L_0x0457:
            r10.ii();
            r7 = r7 + 1;
            goto L_0x0451;
        L_0x045d:
            r10.zzarx(r2);
            r4 = r9.zzwb;
            if (r4 != 0) goto L_0x047d;
        L_0x0464:
            r2 = 0;
        L_0x0465:
            r7 = r7 + r2;
            r4 = new int[r7];
            if (r2 == 0) goto L_0x0471;
        L_0x046a:
            r5 = r9.zzwb;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r2);
        L_0x0471:
            r7 = r4.length;
            if (r2 >= r7) goto L_0x0481;
        L_0x0474:
            r7 = r10.ii();
            r4[r2] = r7;
            r2 = r2 + 1;
            goto L_0x0471;
        L_0x047d:
            r4 = r9.zzwb;
            r2 = r4.length;
            goto L_0x0465;
        L_0x0481:
            r9.zzwb = r4;
            goto L_0x0487;
        L_0x0484:
            goto L_0x0000;
        L_0x0487:
            r10.zzarw(r0);
            goto L_0x0484;
        L_0x048b:
            r3 = 80;
            r2 = com.google.android.gms.internal.zzaxc.zzc(r10, r3);
            r4 = r9.zzwc;
            if (r4 != 0) goto L_0x04b3;
        L_0x0495:
            r0 = 0;
        L_0x0496:
            r2 = r2 + r0;
            r4 = new int[r2];
            if (r0 == 0) goto L_0x04a2;
        L_0x049b:
            r5 = r9.zzwc;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r0);
        L_0x04a2:
            r2 = r4.length;
            r2 = r2 + -1;
            if (r0 >= r2) goto L_0x04b7;
        L_0x04a7:
            r2 = r10.ii();
            r4[r0] = r2;
            r10.ie();
            r0 = r0 + 1;
            goto L_0x04a2;
        L_0x04b3:
            r4 = r9.zzwc;
            r0 = r4.length;
            goto L_0x0496;
        L_0x04b7:
            r2 = r10.ii();
            r4[r0] = r2;
            goto L_0x04c1;
        L_0x04be:
            goto L_0x0000;
        L_0x04c1:
            r9.zzwc = r4;
            goto L_0x04be;
        L_0x04c4:
            r0 = r10.in();
            r0 = r10.zzarv(r0);
            r2 = r10.getPosition();
            r7 = 0;
        L_0x04d1:
            r8 = r10.is();
            if (r8 <= 0) goto L_0x04dd;
        L_0x04d7:
            r10.ii();
            r7 = r7 + 1;
            goto L_0x04d1;
        L_0x04dd:
            r10.zzarx(r2);
            r4 = r9.zzwc;
            if (r4 != 0) goto L_0x04fd;
        L_0x04e4:
            r2 = 0;
        L_0x04e5:
            r7 = r7 + r2;
            r4 = new int[r7];
            if (r2 == 0) goto L_0x04f1;
        L_0x04ea:
            r5 = r9.zzwc;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r2);
        L_0x04f1:
            r7 = r4.length;
            if (r2 >= r7) goto L_0x0501;
        L_0x04f4:
            r7 = r10.ii();
            r4[r2] = r7;
            r2 = r2 + 1;
            goto L_0x04f1;
        L_0x04fd:
            r4 = r9.zzwc;
            r2 = r4.length;
            goto L_0x04e5;
        L_0x0501:
            r9.zzwc = r4;
            goto L_0x0507;
        L_0x0504:
            goto L_0x0000;
        L_0x0507:
            r10.zzarw(r0);
            goto L_0x0504;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzah.zzg.zzo(com.google.android.gms.internal.zzawq):com.google.android.gms.internal.zzah$zzg");
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzh extends zzaws<zzh> {
        public static final zzawt<com.google.android.gms.internal.zzai.zza, zzh> zzwd = zzawt.zza(11, zzh.class, 810);
        private static final zzh[] zzwe = new zzh[0];
        public int[] zzwf;
        public int[] zzwg;
        public int[] zzwh;
        public int zzwi;
        public int[] zzwj;
        public int zzwk;
        public int zzwl;

        public zzh() throws  {
            zzal();
        }

        protected int computeSerializedSize() throws  {
            int $i3;
            int $i2;
            int $i1 = super.computeSerializedSize();
            if (this.zzwf != null && this.zzwf.length > 0) {
                $i3 = 0;
                for (int $i4 : this.zzwf) {
                    $i3 += zzawr.zzasb($i4);
                }
                $i1 = ($i1 + $i3) + (this.zzwf.length * 1);
            }
            if (this.zzwg != null && this.zzwg.length > 0) {
                $i3 = 0;
                for (int $i42 : this.zzwg) {
                    $i3 += zzawr.zzasb($i42);
                }
                $i1 = ($i1 + $i3) + (this.zzwg.length * 1);
            }
            if (this.zzwh != null && this.zzwh.length > 0) {
                $i3 = 0;
                for (int $i422 : this.zzwh) {
                    $i3 += zzawr.zzasb($i422);
                }
                $i1 = ($i1 + $i3) + (this.zzwh.length * 1);
            }
            if (this.zzwi != 0) {
                $i1 += zzawr.zzax(4, this.zzwi);
            }
            if (this.zzwj != null && this.zzwj.length > 0) {
                $i2 = 0;
                for (int $i32 : this.zzwj) {
                    $i2 += zzawr.zzasb($i32);
                }
                $i1 = ($i1 + $i2) + (this.zzwj.length * 1);
            }
            if (this.zzwk != 0) {
                $i1 += zzawr.zzax(6, this.zzwk);
            }
            return this.zzwl != 0 ? $i1 + zzawr.zzax(7, this.zzwl) : $i1;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzh)) {
                return false;
            }
            zzh $r2 = (zzh) $r1;
            return zzawx.equals(this.zzwf, $r2.zzwf) ? zzawx.equals(this.zzwg, $r2.zzwg) ? zzawx.equals(this.zzwh, $r2.zzwh) ? this.zzwi == $r2.zzwi ? zzawx.equals(this.zzwj, $r2.zzwj) ? this.zzwk == $r2.zzwk ? this.zzwl == $r2.zzwl ? (this.cbt == null || this.cbt.isEmpty()) ? $r2.cbt == null || $r2.cbt.isEmpty() : this.cbt.equals($r2.cbt) : false : false : false : false : false : false : false;
        }

        public int hashCode() throws  {
            int $i0 = (((((((((((((((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + zzawx.hashCode(this.zzwf)) * 31) + zzawx.hashCode(this.zzwg)) * 31) + zzawx.hashCode(this.zzwh)) * 31) + this.zzwi) * 31) + zzawx.hashCode(this.zzwj)) * 31) + this.zzwk) * 31) + this.zzwl) * 31;
            int $i1 = (this.cbt == null || this.cbt.isEmpty()) ? 0 : this.cbt.hashCode();
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzp($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.zzwf != null && this.zzwf.length > 0) {
                for (int $i2 : this.zzwf) {
                    $r1.zzav(1, $i2);
                }
            }
            if (this.zzwg != null && this.zzwg.length > 0) {
                for (int $i22 : this.zzwg) {
                    $r1.zzav(2, $i22);
                }
            }
            if (this.zzwh != null && this.zzwh.length > 0) {
                for (int $i222 : this.zzwh) {
                    $r1.zzav(3, $i222);
                }
            }
            if (this.zzwi != 0) {
                $r1.zzav(4, this.zzwi);
            }
            if (this.zzwj != null && this.zzwj.length > 0) {
                for (int $i1 : this.zzwj) {
                    $r1.zzav(5, $i1);
                }
            }
            if (this.zzwk != 0) {
                $r1.zzav(6, this.zzwk);
            }
            if (this.zzwl != 0) {
                $r1.zzav(7, this.zzwl);
            }
            super.writeTo($r1);
        }

        public zzh zzal() throws  {
            this.zzwf = zzaxc.cbE;
            this.zzwg = zzaxc.cbE;
            this.zzwh = zzaxc.cbE;
            this.zzwi = 0;
            this.zzwj = zzaxc.cbE;
            this.zzwk = 0;
            this.zzwl = 0;
            this.cbt = null;
            this.cbC = -1;
            return this;
        }

        public com.google.android.gms.internal.zzah.zzh zzp(com.google.android.gms.internal.zzawq r10) throws java.io.IOException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:23:0x0061 in {4, 5, 8, 11, 14, 15, 17, 18, 22, 24, 27, 30, 33, 34, 38, 41, 44, 45, 50, 53, 56, 59, 60, 62, 66, 69, 72, 73, 75, 80, 83, 86, 89, 90, 92, 95, 99, 102, 105, 106, 108, 113, 116, 119, 122, 123, 125, 128, 131} preds:[]
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
            r9 = this;
        L_0x0000:
            r0 = r10.ie();
            switch(r0) {
                case 0: goto L_0x000e;
                case 8: goto L_0x000f;
                case 10: goto L_0x0048;
                case 16: goto L_0x008f;
                case 18: goto L_0x00c4;
                case 24: goto L_0x010b;
                case 26: goto L_0x0144;
                case 32: goto L_0x018b;
                case 40: goto L_0x0196;
                case 42: goto L_0x01cf;
                case 48: goto L_0x0216;
                case 56: goto L_0x0221;
                default: goto L_0x0007;
            };
        L_0x0007:
            goto L_0x0008;
        L_0x0008:
            r1 = super.zza(r10, r0);
            if (r1 != 0) goto L_0x0000;
        L_0x000e:
            return r9;
        L_0x000f:
            r3 = 8;
            r2 = com.google.android.gms.internal.zzaxc.zzc(r10, r3);
            r4 = r9.zzwf;
            if (r4 != 0) goto L_0x0037;
        L_0x0019:
            r0 = 0;
        L_0x001a:
            r2 = r2 + r0;
            r4 = new int[r2];
            if (r0 == 0) goto L_0x0026;
        L_0x001f:
            r5 = r9.zzwf;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r0);
        L_0x0026:
            r2 = r4.length;
            r2 = r2 + -1;
            if (r0 >= r2) goto L_0x003b;
        L_0x002b:
            r2 = r10.ii();
            r4[r0] = r2;
            r10.ie();
            r0 = r0 + 1;
            goto L_0x0026;
        L_0x0037:
            r4 = r9.zzwf;
            r0 = r4.length;
            goto L_0x001a;
        L_0x003b:
            r2 = r10.ii();
            r4[r0] = r2;
            r9.zzwf = r4;
            goto L_0x0047;
        L_0x0044:
            goto L_0x0000;
        L_0x0047:
            goto L_0x0000;
        L_0x0048:
            r0 = r10.in();
            r0 = r10.zzarv(r0);
            r2 = r10.getPosition();
            r7 = 0;
        L_0x0055:
            r8 = r10.is();
            if (r8 <= 0) goto L_0x0065;
        L_0x005b:
            r10.ii();
            r7 = r7 + 1;
            goto L_0x0055;
            goto L_0x0065;
        L_0x0062:
            goto L_0x0000;
        L_0x0065:
            r10.zzarx(r2);
            r4 = r9.zzwf;
            if (r4 != 0) goto L_0x0085;
        L_0x006c:
            r2 = 0;
        L_0x006d:
            r7 = r7 + r2;
            r4 = new int[r7];
            if (r2 == 0) goto L_0x0079;
        L_0x0072:
            r5 = r9.zzwf;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r2);
        L_0x0079:
            r7 = r4.length;
            if (r2 >= r7) goto L_0x0089;
        L_0x007c:
            r7 = r10.ii();
            r4[r2] = r7;
            r2 = r2 + 1;
            goto L_0x0079;
        L_0x0085:
            r4 = r9.zzwf;
            r2 = r4.length;
            goto L_0x006d;
        L_0x0089:
            r9.zzwf = r4;
            r10.zzarw(r0);
            goto L_0x0044;
        L_0x008f:
            r3 = 16;
            r2 = com.google.android.gms.internal.zzaxc.zzc(r10, r3);
            r4 = r9.zzwg;
            if (r4 != 0) goto L_0x00b7;
        L_0x0099:
            r0 = 0;
        L_0x009a:
            r2 = r2 + r0;
            r4 = new int[r2];
            if (r0 == 0) goto L_0x00a6;
        L_0x009f:
            r5 = r9.zzwg;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r0);
        L_0x00a6:
            r2 = r4.length;
            r2 = r2 + -1;
            if (r0 >= r2) goto L_0x00bb;
        L_0x00ab:
            r2 = r10.ii();
            r4[r0] = r2;
            r10.ie();
            r0 = r0 + 1;
            goto L_0x00a6;
        L_0x00b7:
            r4 = r9.zzwg;
            r0 = r4.length;
            goto L_0x009a;
        L_0x00bb:
            r2 = r10.ii();
            r4[r0] = r2;
            r9.zzwg = r4;
            goto L_0x0062;
        L_0x00c4:
            r0 = r10.in();
            r0 = r10.zzarv(r0);
            r2 = r10.getPosition();
            r7 = 0;
        L_0x00d1:
            r8 = r10.is();
            if (r8 <= 0) goto L_0x00dd;
        L_0x00d7:
            r10.ii();
            r7 = r7 + 1;
            goto L_0x00d1;
        L_0x00dd:
            r10.zzarx(r2);
            r4 = r9.zzwg;
            if (r4 != 0) goto L_0x00fd;
        L_0x00e4:
            r2 = 0;
        L_0x00e5:
            r7 = r7 + r2;
            r4 = new int[r7];
            if (r2 == 0) goto L_0x00f1;
        L_0x00ea:
            r5 = r9.zzwg;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r2);
        L_0x00f1:
            r7 = r4.length;
            if (r2 >= r7) goto L_0x0101;
        L_0x00f4:
            r7 = r10.ii();
            r4[r2] = r7;
            r2 = r2 + 1;
            goto L_0x00f1;
        L_0x00fd:
            r4 = r9.zzwg;
            r2 = r4.length;
            goto L_0x00e5;
        L_0x0101:
            r9.zzwg = r4;
            goto L_0x0107;
        L_0x0104:
            goto L_0x0000;
        L_0x0107:
            r10.zzarw(r0);
            goto L_0x0104;
        L_0x010b:
            r3 = 24;
            r2 = com.google.android.gms.internal.zzaxc.zzc(r10, r3);
            r4 = r9.zzwh;
            if (r4 != 0) goto L_0x0133;
        L_0x0115:
            r0 = 0;
        L_0x0116:
            r2 = r2 + r0;
            r4 = new int[r2];
            if (r0 == 0) goto L_0x0122;
        L_0x011b:
            r5 = r9.zzwh;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r0);
        L_0x0122:
            r2 = r4.length;
            r2 = r2 + -1;
            if (r0 >= r2) goto L_0x0137;
        L_0x0127:
            r2 = r10.ii();
            r4[r0] = r2;
            r10.ie();
            r0 = r0 + 1;
            goto L_0x0122;
        L_0x0133:
            r4 = r9.zzwh;
            r0 = r4.length;
            goto L_0x0116;
        L_0x0137:
            r2 = r10.ii();
            r4[r0] = r2;
            goto L_0x0141;
        L_0x013e:
            goto L_0x0000;
        L_0x0141:
            r9.zzwh = r4;
            goto L_0x013e;
        L_0x0144:
            r0 = r10.in();
            r0 = r10.zzarv(r0);
            r2 = r10.getPosition();
            r7 = 0;
        L_0x0151:
            r8 = r10.is();
            if (r8 <= 0) goto L_0x015d;
        L_0x0157:
            r10.ii();
            r7 = r7 + 1;
            goto L_0x0151;
        L_0x015d:
            r10.zzarx(r2);
            r4 = r9.zzwh;
            if (r4 != 0) goto L_0x017d;
        L_0x0164:
            r2 = 0;
        L_0x0165:
            r7 = r7 + r2;
            r4 = new int[r7];
            if (r2 == 0) goto L_0x0171;
        L_0x016a:
            r5 = r9.zzwh;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r2);
        L_0x0171:
            r7 = r4.length;
            if (r2 >= r7) goto L_0x0181;
        L_0x0174:
            r7 = r10.ii();
            r4[r2] = r7;
            r2 = r2 + 1;
            goto L_0x0171;
        L_0x017d:
            r4 = r9.zzwh;
            r2 = r4.length;
            goto L_0x0165;
        L_0x0181:
            r9.zzwh = r4;
            goto L_0x0187;
        L_0x0184:
            goto L_0x0000;
        L_0x0187:
            r10.zzarw(r0);
            goto L_0x0184;
        L_0x018b:
            r0 = r10.ii();
            goto L_0x0193;
        L_0x0190:
            goto L_0x0000;
        L_0x0193:
            r9.zzwi = r0;
            goto L_0x0190;
        L_0x0196:
            r3 = 40;
            r2 = com.google.android.gms.internal.zzaxc.zzc(r10, r3);
            r4 = r9.zzwj;
            if (r4 != 0) goto L_0x01be;
        L_0x01a0:
            r0 = 0;
        L_0x01a1:
            r2 = r2 + r0;
            r4 = new int[r2];
            if (r0 == 0) goto L_0x01ad;
        L_0x01a6:
            r5 = r9.zzwj;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r0);
        L_0x01ad:
            r2 = r4.length;
            r2 = r2 + -1;
            if (r0 >= r2) goto L_0x01c2;
        L_0x01b2:
            r2 = r10.ii();
            r4[r0] = r2;
            r10.ie();
            r0 = r0 + 1;
            goto L_0x01ad;
        L_0x01be:
            r4 = r9.zzwj;
            r0 = r4.length;
            goto L_0x01a1;
        L_0x01c2:
            r2 = r10.ii();
            r4[r0] = r2;
            goto L_0x01cc;
        L_0x01c9:
            goto L_0x0000;
        L_0x01cc:
            r9.zzwj = r4;
            goto L_0x01c9;
        L_0x01cf:
            r0 = r10.in();
            r0 = r10.zzarv(r0);
            r2 = r10.getPosition();
            r7 = 0;
        L_0x01dc:
            r8 = r10.is();
            if (r8 <= 0) goto L_0x01e8;
        L_0x01e2:
            r10.ii();
            r7 = r7 + 1;
            goto L_0x01dc;
        L_0x01e8:
            r10.zzarx(r2);
            r4 = r9.zzwj;
            if (r4 != 0) goto L_0x0208;
        L_0x01ef:
            r2 = 0;
        L_0x01f0:
            r7 = r7 + r2;
            r4 = new int[r7];
            if (r2 == 0) goto L_0x01fc;
        L_0x01f5:
            r5 = r9.zzwj;
            r3 = 0;
            r6 = 0;
            java.lang.System.arraycopy(r5, r3, r4, r6, r2);
        L_0x01fc:
            r7 = r4.length;
            if (r2 >= r7) goto L_0x020c;
        L_0x01ff:
            r7 = r10.ii();
            r4[r2] = r7;
            r2 = r2 + 1;
            goto L_0x01fc;
        L_0x0208:
            r4 = r9.zzwj;
            r2 = r4.length;
            goto L_0x01f0;
        L_0x020c:
            r9.zzwj = r4;
            goto L_0x0212;
        L_0x020f:
            goto L_0x0000;
        L_0x0212:
            r10.zzarw(r0);
            goto L_0x020f;
        L_0x0216:
            r0 = r10.ii();
            goto L_0x021e;
        L_0x021b:
            goto L_0x0000;
        L_0x021e:
            r9.zzwk = r0;
            goto L_0x021b;
        L_0x0221:
            r0 = r10.ii();
            goto L_0x0229;
        L_0x0226:
            goto L_0x0000;
        L_0x0229:
            r9.zzwl = r0;
            goto L_0x0226;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzah.zzh.zzp(com.google.android.gms.internal.zzawq):com.google.android.gms.internal.zzah$zzh");
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzi extends zzaws<zzi> {
        private static volatile zzi[] zzwm;
        public String name;
        public com.google.android.gms.internal.zzai.zza zzwn;
        public zzd zzwo;

        public zzi() throws  {
            zzan();
        }

        public static zzi[] zzam() throws  {
            if (zzwm == null) {
                synchronized (zzawx.cbB) {
                    if (zzwm == null) {
                        zzwm = new zzi[0];
                    }
                }
            }
            return zzwm;
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (!this.name.equals("")) {
                $i1 = $i0 + zzawr.zzt(1, this.name);
            }
            if (this.zzwn != null) {
                $i1 += zzawr.zzc(2, this.zzwn);
            }
            return this.zzwo != null ? $i1 + zzawr.zzc(3, this.zzwo) : $i1;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzi)) {
                return false;
            }
            zzi $r2 = (zzi) $r1;
            if (this.name == null) {
                if ($r2.name != null) {
                    return false;
                }
            } else if (!this.name.equals($r2.name)) {
                return false;
            }
            if (this.zzwn == null) {
                if ($r2.zzwn != null) {
                    return false;
                }
            } else if (!this.zzwn.equals($r2.zzwn)) {
                return false;
            }
            if (this.zzwo == null) {
                if ($r2.zzwo != null) {
                    return false;
                }
            } else if (!this.zzwo.equals($r2.zzwo)) {
                return false;
            }
            return (this.cbt == null || this.cbt.isEmpty()) ? $r2.cbt == null || $r2.cbt.isEmpty() : this.cbt.equals($r2.cbt);
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.zzwo == null ? 0 : this.zzwo.hashCode()) + (((this.zzwn == null ? 0 : this.zzwn.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + ((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31)) * 31)) * 31)) * 31;
            if (!(this.cbt == null || this.cbt.isEmpty())) {
                $i0 = this.cbt.hashCode();
            }
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzq($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (!this.name.equals("")) {
                $r1.zzs(1, this.name);
            }
            if (this.zzwn != null) {
                $r1.zza(2, this.zzwn);
            }
            if (this.zzwo != null) {
                $r1.zza(3, this.zzwo);
            }
            super.writeTo($r1);
        }

        public zzi zzan() throws  {
            this.name = "";
            this.zzwn = null;
            this.zzwo = null;
            this.cbt = null;
            this.cbC = -1;
            return this;
        }

        public zzi zzq(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 10:
                        this.name = $r1.readString();
                        continue;
                    case 18:
                        if (this.zzwn == null) {
                            this.zzwn = new com.google.android.gms.internal.zzai.zza();
                        }
                        $r1.zza(this.zzwn);
                        continue;
                    case 26:
                        if (this.zzwo == null) {
                            this.zzwo = new zzd();
                        }
                        $r1.zza(this.zzwo);
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
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzj extends zzaws<zzj> {
        public zzi[] zzwp;
        public zzf zzwq;
        public String zzwr;

        public zzj() throws  {
            zzao();
        }

        public static zzj zzf(byte[] $r0) throws zzawy {
            return (zzj) zzawz.zza(new zzj(), $r0);
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            if (this.zzwp != null && this.zzwp.length > 0) {
                for (zzawz $r2 : this.zzwp) {
                    if ($r2 != null) {
                        $i0 += zzawr.zzc(1, $r2);
                    }
                }
            }
            if (this.zzwq != null) {
                $i0 += zzawr.zzc(2, this.zzwq);
            }
            return !this.zzwr.equals("") ? $i0 + zzawr.zzt(3, this.zzwr) : $i0;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzj)) {
                return false;
            }
            zzj $r2 = (zzj) $r1;
            if (!zzawx.equals(this.zzwp, $r2.zzwp)) {
                return false;
            }
            if (this.zzwq == null) {
                if ($r2.zzwq != null) {
                    return false;
                }
            } else if (!this.zzwq.equals($r2.zzwq)) {
                return false;
            }
            if (this.zzwr == null) {
                if ($r2.zzwr != null) {
                    return false;
                }
            } else if (!this.zzwr.equals($r2.zzwr)) {
                return false;
            }
            return (this.cbt == null || this.cbt.isEmpty()) ? $r2.cbt == null || $r2.cbt.isEmpty() : this.cbt.equals($r2.cbt);
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.zzwr == null ? 0 : this.zzwr.hashCode()) + (((this.zzwq == null ? 0 : this.zzwq.hashCode()) + ((((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + zzawx.hashCode(this.zzwp)) * 31)) * 31)) * 31;
            if (!(this.cbt == null || this.cbt.isEmpty())) {
                $i0 = this.cbt.hashCode();
            }
            return $i1 + $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzr($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.zzwp != null && this.zzwp.length > 0) {
                for (zzawz $r3 : this.zzwp) {
                    if ($r3 != null) {
                        $r1.zza(1, $r3);
                    }
                }
            }
            if (this.zzwq != null) {
                $r1.zza(2, this.zzwq);
            }
            if (!this.zzwr.equals("")) {
                $r1.zzs(3, this.zzwr);
            }
            super.writeTo($r1);
        }

        public zzj zzao() throws  {
            this.zzwp = zzi.zzam();
            this.zzwq = null;
            this.zzwr = "";
            this.cbt = null;
            this.cbC = -1;
            return this;
        }

        public zzj zzr(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 10:
                        int $i1 = zzaxc.zzc($r1, 10);
                        $i0 = this.zzwp == null ? 0 : this.zzwp.length;
                        zzi[] $r2 = new zzi[($i1 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.zzwp, 0, $r2, 0, $i0);
                        }
                        while ($i0 < $r2.length - 1) {
                            $r2[$i0] = new zzi();
                            $r1.zza($r2[$i0]);
                            $r1.ie();
                            $i0++;
                        }
                        $r2[$i0] = new zzi();
                        $r1.zza($r2[$i0]);
                        this.zzwp = $r2;
                        continue;
                    case 18:
                        if (this.zzwq == null) {
                            this.zzwq = new zzf();
                        }
                        $r1.zza(this.zzwq);
                        continue;
                    case 26:
                        this.zzwr = $r1.readString();
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
    }
}
