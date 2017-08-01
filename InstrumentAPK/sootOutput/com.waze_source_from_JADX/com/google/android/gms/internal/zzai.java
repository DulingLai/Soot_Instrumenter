package com.google.android.gms.internal;

import com.waze.strings.DisplayStrings;
import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzai {

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zza extends zzaws<zza> {
        private static volatile zza[] zzws;
        public int type;
        public String zzwt;
        public zza[] zzwu;
        public zza[] zzwv;
        public zza[] zzww;
        public String zzwx;
        public String zzwy;
        public long zzwz;
        public boolean zzxa;
        public zza[] zzxb;
        public int[] zzxc;
        public boolean zzxd;

        public zza() throws  {
            zzaq();
        }

        public static zza[] zzap() throws  {
            if (zzws == null) {
                synchronized (zzawx.cbB) {
                    if (zzws == null) {
                        zzws = new zza[0];
                    }
                }
            }
            return zzws;
        }

        protected int computeSerializedSize() throws  {
            int $i2;
            int $i1 = super.computeSerializedSize() + zzawr.zzax(1, this.type);
            if (!this.zzwt.equals("")) {
                $i1 += zzawr.zzt(2, this.zzwt);
            }
            if (this.zzwu != null && this.zzwu.length > 0) {
                for (zzawz $r3 : this.zzwu) {
                    if ($r3 != null) {
                        $i1 += zzawr.zzc(3, $r3);
                    }
                }
            }
            if (this.zzwv != null && this.zzwv.length > 0) {
                for (zzawz $r32 : this.zzwv) {
                    if ($r32 != null) {
                        $i1 += zzawr.zzc(4, $r32);
                    }
                }
            }
            if (this.zzww != null && this.zzww.length > 0) {
                for (zzawz $r322 : this.zzww) {
                    if ($r322 != null) {
                        $i1 += zzawr.zzc(5, $r322);
                    }
                }
            }
            if (!this.zzwx.equals("")) {
                $i1 += zzawr.zzt(6, this.zzwx);
            }
            if (!this.zzwy.equals("")) {
                $i1 += zzawr.zzt(7, this.zzwy);
            }
            if (this.zzwz != 0) {
                $i1 += zzawr.zzi(8, this.zzwz);
            }
            if (this.zzxd) {
                $i1 += zzawr.zzn(9, this.zzxd);
            }
            if (this.zzxc != null) {
                int[] $r4 = this.zzxc;
                if ($r4.length > 0) {
                    $i2 = 0;
                    int $i3 = 0;
                    while (true) {
                        int $i6 = this.zzxc;
                        int[] $r42 = $i6;
                        $i6 = $i6.length;
                        if ($i2 >= $i6) {
                            break;
                        }
                        $i3 += zzawr.zzasb(this.zzxc[$i2]);
                        $i2++;
                    }
                    $i1 += $i3;
                    $r4 = this.zzxc;
                    $i1 += $r4.length * 1;
                }
            }
            if (this.zzxb != null && this.zzxb.length > 0) {
                for (zzawz $r3222 : this.zzxb) {
                    if ($r3222 != null) {
                        $i1 += zzawr.zzc(11, $r3222);
                    }
                }
            }
            return this.zzxa ? $i1 + zzawr.zzn(12, this.zzxa) : $i1;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zza)) {
                return false;
            }
            zza $r2 = (zza) $r1;
            if (this.type != $r2.type) {
                return false;
            }
            String $r3;
            String $r9;
            if (this.zzwt != null) {
                $r3 = this.zzwt;
                $r9 = $r2.zzwt;
                if (!$r3.equals($r9)) {
                    return false;
                }
            } else if ($r2.zzwt != null) {
                return false;
            }
            if (!zzawx.equals(this.zzwu, $r2.zzwu)) {
                return false;
            }
            if (!zzawx.equals(this.zzwv, $r2.zzwv)) {
                return false;
            }
            if (!zzawx.equals(this.zzww, $r2.zzww)) {
                return false;
            }
            if (this.zzwx != null) {
                $r3 = this.zzwx;
                $r9 = $r2.zzwx;
                if (!$r3.equals($r9)) {
                    return false;
                }
            } else if ($r2.zzwx != null) {
                return false;
            }
            if (this.zzwy != null) {
                $r3 = this.zzwy;
                $r9 = $r2.zzwy;
                if (!$r3.equals($r9)) {
                    return false;
                }
            } else if ($r2.zzwy != null) {
                return false;
            }
            if (this.zzwz != $r2.zzwz) {
                return false;
            }
            boolean $z0 = this.zzxa;
            boolean $z1 = $r2.zzxa;
            if ($z0 != $z1) {
                return false;
            }
            if (!zzawx.equals(this.zzxb, $r2.zzxb)) {
                return false;
            }
            if (!zzawx.equals(this.zzxc, $r2.zzxc)) {
                return false;
            }
            $z0 = this.zzxd;
            $z1 = $r2.zzxd;
            if ($z0 != $z1) {
                return false;
            }
            zzawv $r8;
            if (this.cbt != null) {
                $r8 = this.cbt;
                if (!$r8.isEmpty()) {
                    return this.cbt.equals($r2.cbt);
                }
            }
            if ($r2.cbt != null) {
                $r8 = $r2.cbt;
                if (!$r8.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() throws  {
            short $s0 = (short) 1231;
            int $i1 = 0;
            int $i2 = ((((((this.zzxa ? (short) 1231 : (short) 1237) + (((((this.zzwy == null ? 0 : this.zzwy.hashCode()) + (((this.zzwx == null ? 0 : this.zzwx.hashCode()) + (((((((((this.zzwt == null ? 0 : this.zzwt.hashCode()) + ((((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + this.type) * 31)) * 31) + zzawx.hashCode(this.zzwu)) * 31) + zzawx.hashCode(this.zzwv)) * 31) + zzawx.hashCode(this.zzww)) * 31)) * 31)) * 31) + ((int) (this.zzwz ^ (this.zzwz >>> 32)))) * 31)) * 31) + zzawx.hashCode(this.zzxb)) * 31) + zzawx.hashCode(this.zzxc)) * 31;
            if (!this.zzxd) {
                $s0 = (short) 1237;
            }
            $i2 = ($i2 + $s0) * 31;
            if (this.cbt != null) {
                zzawv $r5 = this.cbt;
                if (!$r5.isEmpty()) {
                    $r5 = this.cbt;
                    $i1 = $r5.hashCode();
                }
            }
            return $i2 + $i1;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzs($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            int $i1;
            $r1.zzav(1, this.type);
            if (!this.zzwt.equals("")) {
                $r1.zzs(2, this.zzwt);
            }
            if (this.zzwu != null && this.zzwu.length > 0) {
                for (zzawz $r4 : this.zzwu) {
                    if ($r4 != null) {
                        $r1.zza(3, $r4);
                    }
                }
            }
            if (this.zzwv != null && this.zzwv.length > 0) {
                for (zzawz $r42 : this.zzwv) {
                    if ($r42 != null) {
                        $r1.zza(4, $r42);
                    }
                }
            }
            if (this.zzww != null && this.zzww.length > 0) {
                for (zzawz $r422 : this.zzww) {
                    if ($r422 != null) {
                        $r1.zza(5, $r422);
                    }
                }
            }
            if (!this.zzwx.equals("")) {
                $r1.zzs(6, this.zzwx);
            }
            if (!this.zzwy.equals("")) {
                $r1.zzs(7, this.zzwy);
            }
            if (this.zzwz != 0) {
                $r1.zzf(8, this.zzwz);
            }
            if (this.zzxd) {
                $r1.zzm(9, this.zzxd);
            }
            if (this.zzxc != null) {
                int[] $r5 = this.zzxc;
                if ($r5.length > 0) {
                    $i1 = 0;
                    while (true) {
                        $r5 = this.zzxc;
                        if ($i1 >= $r5.length) {
                            break;
                        }
                        $r1.zzav(10, this.zzxc[$i1]);
                        $i1++;
                    }
                }
            }
            if (this.zzxb != null && this.zzxb.length > 0) {
                for (zzawz $r4222 : this.zzxb) {
                    if ($r4222 != null) {
                        $r1.zza(11, $r4222);
                    }
                }
            }
            if (this.zzxa) {
                $r1.zzm(12, this.zzxa);
            }
            super.writeTo($r1);
        }

        public zza zzaq() throws  {
            this.type = 1;
            this.zzwt = "";
            this.zzwu = zzap();
            this.zzwv = zzap();
            this.zzww = zzap();
            this.zzwx = "";
            this.zzwy = "";
            this.zzwz = 0;
            this.zzxa = false;
            this.zzxb = zzap();
            this.zzxc = zzaxc.cbE;
            this.zzxd = false;
            this.cbt = null;
            this.cbC = -1;
            return this;
        }

        public com.google.android.gms.internal.zzai.zza zzs(com.google.android.gms.internal.zzawq r21) throws java.io.IOException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:23:0x006c in {4, 5, 9, 10, 11, 14, 16, 19, 22, 24, 25, 29, 32, 35, 36, 40, 43, 46, 47, 49, 52, 55, 58, 61, 66, 70, 71, 72, 76, 80, 82, 85, 87, 95, 96, 100, 103, 109, 110, 111, 112, 113, 117, 120, 123, 124, 126, 129} preds:[]
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
            r20 = this;
        L_0x0000:
            r0 = r21;
            r3 = r0.ie();
            switch(r3) {
                case 0: goto L_0x0014;
                case 8: goto L_0x0015;
                case 18: goto L_0x0025;
                case 26: goto L_0x0030;
                case 34: goto L_0x0089;
                case 42: goto L_0x00da;
                case 50: goto L_0x012f;
                case 58: goto L_0x013e;
                case 64: goto L_0x014d;
                case 72: goto L_0x015c;
                case 80: goto L_0x016b;
                case 82: goto L_0x01e5;
                case 90: goto L_0x025f;
                case 96: goto L_0x02b4;
                default: goto L_0x0009;
            };
        L_0x0009:
            goto L_0x000a;
        L_0x000a:
            r0 = r20;
            r1 = r21;
            r4 = super.zza(r1, r3);
            if (r4 != 0) goto L_0x0000;
        L_0x0014:
            return r20;
        L_0x0015:
            r0 = r21;
            r3 = r0.ii();
            switch(r3) {
                case 1: goto L_0x0020;
                case 2: goto L_0x0020;
                case 3: goto L_0x0020;
                case 4: goto L_0x0020;
                case 5: goto L_0x0020;
                case 6: goto L_0x0020;
                case 7: goto L_0x0020;
                case 8: goto L_0x0020;
                default: goto L_0x001e;
            };
        L_0x001e:
            goto L_0x001f;
        L_0x001f:
            goto L_0x0000;
        L_0x0020:
            r0 = r20;
            r0.type = r3;
            goto L_0x0000;
        L_0x0025:
            r0 = r21;
            r5 = r0.readString();
            r0 = r20;
            r0.zzwt = r5;
            goto L_0x0000;
        L_0x0030:
            r7 = 26;
            r0 = r21;
            r6 = com.google.android.gms.internal.zzaxc.zzc(r0, r7);
            r0 = r20;
            r8 = r0.zzwu;
            if (r8 != 0) goto L_0x0070;
        L_0x003e:
            r3 = 0;
        L_0x003f:
            r6 = r6 + r3;
            goto L_0x0044;
        L_0x0041:
            goto L_0x0000;
        L_0x0044:
            r8 = new com.google.android.gms.internal.zzai.zza[r6];
            if (r3 == 0) goto L_0x0051;
        L_0x0048:
            r0 = r20;
            r9 = r0.zzwu;
            r7 = 0;
            r10 = 0;
            java.lang.System.arraycopy(r9, r7, r8, r10, r3);
        L_0x0051:
            r6 = r8.length;
            r6 = r6 + -1;
            if (r3 >= r6) goto L_0x0076;
        L_0x0056:
            r11 = new com.google.android.gms.internal.zzai$zza;
            r11.<init>();
            r8[r3] = r11;
            r11 = r8[r3];
            r0 = r21;
            r0.zza(r11);
            r0 = r21;
            r0.ie();
            r3 = r3 + 1;
            goto L_0x0051;
            goto L_0x0070;
        L_0x006d:
            goto L_0x0000;
        L_0x0070:
            r0 = r20;
            r8 = r0.zzwu;
            r3 = r8.length;
            goto L_0x003f;
        L_0x0076:
            r11 = new com.google.android.gms.internal.zzai$zza;
            r11.<init>();
            r8[r3] = r11;
            r11 = r8[r3];
            r0 = r21;
            r0.zza(r11);
            r0 = r20;
            r0.zzwu = r8;
            goto L_0x0041;
        L_0x0089:
            r7 = 34;
            r0 = r21;
            r6 = com.google.android.gms.internal.zzaxc.zzc(r0, r7);
            r0 = r20;
            r8 = r0.zzwv;
            if (r8 != 0) goto L_0x00c1;
        L_0x0097:
            r3 = 0;
        L_0x0098:
            r6 = r6 + r3;
            r8 = new com.google.android.gms.internal.zzai.zza[r6];
            if (r3 == 0) goto L_0x00a6;
        L_0x009d:
            r0 = r20;
            r9 = r0.zzwv;
            r7 = 0;
            r10 = 0;
            java.lang.System.arraycopy(r9, r7, r8, r10, r3);
        L_0x00a6:
            r6 = r8.length;
            r6 = r6 + -1;
            if (r3 >= r6) goto L_0x00c7;
        L_0x00ab:
            r11 = new com.google.android.gms.internal.zzai$zza;
            r11.<init>();
            r8[r3] = r11;
            r11 = r8[r3];
            r0 = r21;
            r0.zza(r11);
            r0 = r21;
            r0.ie();
            r3 = r3 + 1;
            goto L_0x00a6;
        L_0x00c1:
            r0 = r20;
            r8 = r0.zzwv;
            r3 = r8.length;
            goto L_0x0098;
        L_0x00c7:
            r11 = new com.google.android.gms.internal.zzai$zza;
            r11.<init>();
            r8[r3] = r11;
            r11 = r8[r3];
            r0 = r21;
            r0.zza(r11);
            r0 = r20;
            r0.zzwv = r8;
            goto L_0x006d;
        L_0x00da:
            r7 = 42;
            r0 = r21;
            r6 = com.google.android.gms.internal.zzaxc.zzc(r0, r7);
            r0 = r20;
            r8 = r0.zzww;
            if (r8 != 0) goto L_0x0112;
        L_0x00e8:
            r3 = 0;
        L_0x00e9:
            r6 = r6 + r3;
            r8 = new com.google.android.gms.internal.zzai.zza[r6];
            if (r3 == 0) goto L_0x00f7;
        L_0x00ee:
            r0 = r20;
            r9 = r0.zzww;
            r7 = 0;
            r10 = 0;
            java.lang.System.arraycopy(r9, r7, r8, r10, r3);
        L_0x00f7:
            r6 = r8.length;
            r6 = r6 + -1;
            if (r3 >= r6) goto L_0x0118;
        L_0x00fc:
            r11 = new com.google.android.gms.internal.zzai$zza;
            r11.<init>();
            r8[r3] = r11;
            r11 = r8[r3];
            r0 = r21;
            r0.zza(r11);
            r0 = r21;
            r0.ie();
            r3 = r3 + 1;
            goto L_0x00f7;
        L_0x0112:
            r0 = r20;
            r8 = r0.zzww;
            r3 = r8.length;
            goto L_0x00e9;
        L_0x0118:
            r11 = new com.google.android.gms.internal.zzai$zza;
            r11.<init>();
            r8[r3] = r11;
            r11 = r8[r3];
            r0 = r21;
            r0.zza(r11);
            goto L_0x012a;
        L_0x0127:
            goto L_0x0000;
        L_0x012a:
            r0 = r20;
            r0.zzww = r8;
            goto L_0x0127;
        L_0x012f:
            r0 = r21;
            r5 = r0.readString();
            goto L_0x0139;
        L_0x0136:
            goto L_0x0000;
        L_0x0139:
            r0 = r20;
            r0.zzwx = r5;
            goto L_0x0136;
        L_0x013e:
            r0 = r21;
            r5 = r0.readString();
            goto L_0x0148;
        L_0x0145:
            goto L_0x0000;
        L_0x0148:
            r0 = r20;
            r0.zzwy = r5;
            goto L_0x0145;
        L_0x014d:
            r0 = r21;
            r12 = r0.ih();
            goto L_0x0157;
        L_0x0154:
            goto L_0x0000;
        L_0x0157:
            r0 = r20;
            r0.zzwz = r12;
            goto L_0x0154;
        L_0x015c:
            r0 = r21;
            r4 = r0.ik();
            goto L_0x0166;
        L_0x0163:
            goto L_0x0000;
        L_0x0166:
            r0 = r20;
            r0.zzxd = r4;
            goto L_0x0163;
        L_0x016b:
            r7 = 80;
            r0 = r21;
            r3 = com.google.android.gms.internal.zzaxc.zzc(r0, r7);
            r14 = new int[r3];
            r15 = 0;
            r6 = 0;
        L_0x0177:
            if (r15 >= r3) goto L_0x0196;
        L_0x0179:
            if (r15 == 0) goto L_0x0180;
        L_0x017b:
            r0 = r21;
            r0.ie();
        L_0x0180:
            r0 = r21;
            r16 = r0.ii();
            switch(r16) {
                case 1: goto L_0x0191;
                case 2: goto L_0x0191;
                case 3: goto L_0x0191;
                case 4: goto L_0x0191;
                case 5: goto L_0x0191;
                case 6: goto L_0x0191;
                case 7: goto L_0x0191;
                case 8: goto L_0x0191;
                case 9: goto L_0x0191;
                case 10: goto L_0x0191;
                case 11: goto L_0x0191;
                case 12: goto L_0x0191;
                case 13: goto L_0x0191;
                case 14: goto L_0x0191;
                case 15: goto L_0x0191;
                case 16: goto L_0x0191;
                case 17: goto L_0x0191;
                default: goto L_0x0189;
            };
        L_0x0189:
            goto L_0x018a;
        L_0x018a:
            r17 = r6;
        L_0x018c:
            r15 = r15 + 1;
            r6 = r17;
            goto L_0x0177;
        L_0x0191:
            r17 = r6 + 1;
            r14[r6] = r16;
            goto L_0x018c;
        L_0x0196:
            if (r6 == 0) goto L_0x0000;
        L_0x0198:
            r0 = r20;
            r0 = r0.zzxc;
            r18 = r0;
            if (r18 != 0) goto L_0x01af;
        L_0x01a0:
            r17 = 0;
        L_0x01a2:
            if (r17 != 0) goto L_0x01b9;
        L_0x01a4:
            if (r6 != r3) goto L_0x01b9;
        L_0x01a6:
            goto L_0x01aa;
        L_0x01a7:
            goto L_0x0000;
        L_0x01aa:
            r0 = r20;
            r0.zzxc = r14;
            goto L_0x01a7;
        L_0x01af:
            r0 = r20;
            r0 = r0.zzxc;
            r18 = r0;
            r0 = r0.length;
            r17 = r0;
            goto L_0x01a2;
        L_0x01b9:
            r3 = r17 + r6;
            r0 = new int[r3];
            r18 = r0;
            if (r17 == 0) goto L_0x01d2;
        L_0x01c1:
            r0 = r20;
            r0 = r0.zzxc;
            r19 = r0;
            r7 = 0;
            r10 = 0;
            r0 = r19;
            r1 = r18;
            r2 = r17;
            java.lang.System.arraycopy(r0, r7, r1, r10, r2);
        L_0x01d2:
            r7 = 0;
            r0 = r18;
            r1 = r17;
            java.lang.System.arraycopy(r14, r7, r0, r1, r6);
            goto L_0x01de;
        L_0x01db:
            goto L_0x0000;
        L_0x01de:
            r0 = r18;
            r1 = r20;
            r1.zzxc = r0;
            goto L_0x01db;
        L_0x01e5:
            r0 = r21;
            r3 = r0.in();
            r0 = r21;
            r3 = r0.zzarv(r3);
            r0 = r21;
            r6 = r0.getPosition();
            r17 = 0;
        L_0x01f9:
            r0 = r21;
            r15 = r0.is();
            if (r15 <= 0) goto L_0x020f;
        L_0x0201:
            r0 = r21;
            r15 = r0.ii();
            switch(r15) {
                case 1: goto L_0x020c;
                case 2: goto L_0x020c;
                case 3: goto L_0x020c;
                case 4: goto L_0x020c;
                case 5: goto L_0x020c;
                case 6: goto L_0x020c;
                case 7: goto L_0x020c;
                case 8: goto L_0x020c;
                case 9: goto L_0x020c;
                case 10: goto L_0x020c;
                case 11: goto L_0x020c;
                case 12: goto L_0x020c;
                case 13: goto L_0x020c;
                case 14: goto L_0x020c;
                case 15: goto L_0x020c;
                case 16: goto L_0x020c;
                case 17: goto L_0x020c;
                default: goto L_0x020a;
            };
        L_0x020a:
            goto L_0x020b;
        L_0x020b:
            goto L_0x01f9;
        L_0x020c:
            r17 = r17 + 1;
            goto L_0x01f9;
        L_0x020f:
            if (r17 == 0) goto L_0x0259;
        L_0x0211:
            r0 = r21;
            r0.zzarx(r6);
            r0 = r20;
            r14 = r0.zzxc;
            if (r14 != 0) goto L_0x024b;
        L_0x021c:
            r6 = 0;
        L_0x021d:
            r0 = r17;
            r0 = r0 + r6;
            r17 = r0;
            r14 = new int[r0];
            if (r6 == 0) goto L_0x0233;
        L_0x0226:
            r0 = r20;
            r0 = r0.zzxc;
            r18 = r0;
            r7 = 0;
            r10 = 0;
            r0 = r18;
            java.lang.System.arraycopy(r0, r7, r14, r10, r6);
        L_0x0233:
            r0 = r21;
            r17 = r0.is();
            if (r17 <= 0) goto L_0x0251;
        L_0x023b:
            r0 = r21;
            r17 = r0.ii();
            switch(r17) {
                case 1: goto L_0x0246;
                case 2: goto L_0x0246;
                case 3: goto L_0x0246;
                case 4: goto L_0x0246;
                case 5: goto L_0x0246;
                case 6: goto L_0x0246;
                case 7: goto L_0x0246;
                case 8: goto L_0x0246;
                case 9: goto L_0x0246;
                case 10: goto L_0x0246;
                case 11: goto L_0x0246;
                case 12: goto L_0x0246;
                case 13: goto L_0x0246;
                case 14: goto L_0x0246;
                case 15: goto L_0x0246;
                case 16: goto L_0x0246;
                case 17: goto L_0x0246;
                default: goto L_0x0244;
            };
        L_0x0244:
            goto L_0x0245;
        L_0x0245:
            goto L_0x0233;
        L_0x0246:
            r14[r6] = r17;
            r6 = r6 + 1;
            goto L_0x0233;
        L_0x024b:
            r0 = r20;
            r14 = r0.zzxc;
            r6 = r14.length;
            goto L_0x021d;
        L_0x0251:
            r0 = r20;
            r0.zzxc = r14;
            goto L_0x0259;
        L_0x0256:
            goto L_0x0000;
        L_0x0259:
            r0 = r21;
            r0.zzarw(r3);
            goto L_0x0256;
        L_0x025f:
            r7 = 90;
            r0 = r21;
            r6 = com.google.android.gms.internal.zzaxc.zzc(r0, r7);
            r0 = r20;
            r8 = r0.zzxb;
            if (r8 != 0) goto L_0x0297;
        L_0x026d:
            r3 = 0;
        L_0x026e:
            r6 = r6 + r3;
            r8 = new com.google.android.gms.internal.zzai.zza[r6];
            if (r3 == 0) goto L_0x027c;
        L_0x0273:
            r0 = r20;
            r9 = r0.zzxb;
            r7 = 0;
            r10 = 0;
            java.lang.System.arraycopy(r9, r7, r8, r10, r3);
        L_0x027c:
            r6 = r8.length;
            r6 = r6 + -1;
            if (r3 >= r6) goto L_0x029d;
        L_0x0281:
            r11 = new com.google.android.gms.internal.zzai$zza;
            r11.<init>();
            r8[r3] = r11;
            r11 = r8[r3];
            r0 = r21;
            r0.zza(r11);
            r0 = r21;
            r0.ie();
            r3 = r3 + 1;
            goto L_0x027c;
        L_0x0297:
            r0 = r20;
            r8 = r0.zzxb;
            r3 = r8.length;
            goto L_0x026e;
        L_0x029d:
            r11 = new com.google.android.gms.internal.zzai$zza;
            r11.<init>();
            r8[r3] = r11;
            r11 = r8[r3];
            r0 = r21;
            r0.zza(r11);
            goto L_0x02af;
        L_0x02ac:
            goto L_0x0000;
        L_0x02af:
            r0 = r20;
            r0.zzxb = r8;
            goto L_0x02ac;
        L_0x02b4:
            r0 = r21;
            r4 = r0.ik();
            goto L_0x02be;
        L_0x02bb:
            goto L_0x0000;
        L_0x02be:
            r0 = r20;
            r0.zzxa = r4;
            goto L_0x02bb;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzai.zza.zzs(com.google.android.gms.internal.zzawq):com.google.android.gms.internal.zzai$zza");
        }
    }
}
