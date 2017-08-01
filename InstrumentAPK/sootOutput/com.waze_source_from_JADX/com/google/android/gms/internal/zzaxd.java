package com.google.android.gms.internal;

import com.waze.strings.DisplayStrings;
import java.io.IOException;
import java.util.Arrays;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzaxd {

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zza extends zzaws<zza> implements Cloneable {
        public String[] cbM;
        public String[] cbN;
        public int[] cbO;
        public long[] cbP;
        public long[] cbQ;

        public zza() throws  {
            iK();
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            return iL();
        }

        protected int computeSerializedSize() throws  {
            int $i4;
            int $i2;
            int $i3;
            int $i1 = super.computeSerializedSize();
            if (this.cbM != null && this.cbM.length > 0) {
                $i4 = 0;
                $i2 = 0;
                for (String $r2 : this.cbM) {
                    if ($r2 != null) {
                        $i2++;
                        $i4 += zzawr.zzyu($r2);
                    }
                }
                $i1 = ($i1 + $i4) + ($i2 * 1);
            }
            if (this.cbN != null && this.cbN.length > 0) {
                $i4 = 0;
                $i2 = 0;
                for (String $r22 : this.cbN) {
                    if ($r22 != null) {
                        $i2++;
                        $i4 += zzawr.zzyu($r22);
                    }
                }
                $i1 = ($i1 + $i4) + ($i2 * 1);
            }
            if (this.cbO != null && this.cbO.length > 0) {
                $i3 = 0;
                for (int $i42 : this.cbO) {
                    $i3 += zzawr.zzasb($i42);
                }
                $i1 = ($i1 + $i3) + (this.cbO.length * 1);
            }
            if (this.cbP != null && this.cbP.length > 0) {
                $i3 = 0;
                for (long $l6 : this.cbP) {
                    $i3 += zzawr.zzdm($l6);
                }
                $i1 = ($i1 + $i3) + (this.cbP.length * 1);
            }
            if (this.cbQ == null || this.cbQ.length <= 0) {
                return $i1;
            }
            $i2 = 0;
            for (long $l62 : this.cbQ) {
                $i2 += zzawr.zzdm($l62);
            }
            return ($i1 + $i2) + (this.cbQ.length * 1);
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zza)) {
                return false;
            }
            zza $r2 = (zza) $r1;
            return zzawx.equals(this.cbM, $r2.cbM) ? zzawx.equals(this.cbN, $r2.cbN) ? zzawx.equals(this.cbO, $r2.cbO) ? zzawx.equals(this.cbP, $r2.cbP) ? zzawx.equals(this.cbQ, $r2.cbQ) ? (this.cbt == null || this.cbt.isEmpty()) ? $r2.cbt == null || $r2.cbt.isEmpty() : this.cbt.equals($r2.cbt) : false : false : false : false : false;
        }

        public int hashCode() throws  {
            int $i0 = (((((((((((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + zzawx.hashCode(this.cbM)) * 31) + zzawx.hashCode(this.cbN)) * 31) + zzawx.hashCode(this.cbO)) * 31) + zzawx.hashCode(this.cbP)) * 31) + zzawx.hashCode(this.cbQ)) * 31;
            int $i1 = (this.cbt == null || this.cbt.isEmpty()) ? 0 : this.cbt.hashCode();
            return $i1 + $i0;
        }

        public zza iK() throws  {
            this.cbM = zzaxc.cbJ;
            this.cbN = zzaxc.cbJ;
            this.cbO = zzaxc.cbE;
            this.cbP = zzaxc.cbF;
            this.cbQ = zzaxc.cbF;
            this.cbt = null;
            this.cbC = -1;
            return this;
        }

        public zza iL() throws  {
            try {
                zza $r2 = (zza) super.ix();
                if (this.cbM != null && this.cbM.length > 0) {
                    $r2.cbM = (String[]) this.cbM.clone();
                }
                if (this.cbN != null && this.cbN.length > 0) {
                    $r2.cbN = (String[]) this.cbN.clone();
                }
                if (this.cbO != null && this.cbO.length > 0) {
                    $r2.cbO = (int[]) this.cbO.clone();
                }
                if (this.cbP != null && this.cbP.length > 0) {
                    $r2.cbP = (long[]) this.cbP.clone();
                }
                if (this.cbQ == null || this.cbQ.length <= 0) {
                    return $r2;
                }
                $r2.cbQ = (long[]) this.cbQ.clone();
                return $r2;
            } catch (CloneNotSupportedException $r7) {
                throw new AssertionError($r7);
            }
        }

        public /* synthetic */ zzaws ix() throws CloneNotSupportedException {
            return (zza) clone();
        }

        public /* synthetic */ zzawz iy() throws CloneNotSupportedException {
            return (zza) clone();
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzcu($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.cbM != null && this.cbM.length > 0) {
                for (String $r3 : this.cbM) {
                    if ($r3 != null) {
                        $r1.zzs(1, $r3);
                    }
                }
            }
            if (this.cbN != null && this.cbN.length > 0) {
                for (String $r32 : this.cbN) {
                    if ($r32 != null) {
                        $r1.zzs(2, $r32);
                    }
                }
            }
            if (this.cbO != null && this.cbO.length > 0) {
                for (int $i3 : this.cbO) {
                    $r1.zzav(3, $i3);
                }
            }
            if (this.cbP != null && this.cbP.length > 0) {
                for (long $l0 : this.cbP) {
                    $r1.zzf(4, $l0);
                }
            }
            if (this.cbQ != null && this.cbQ.length > 0) {
                for (long $l02 : this.cbQ) {
                    $r1.zzf(5, $l02);
                }
            }
            super.writeTo($r1);
        }

        public com.google.android.gms.internal.zzaxd.zza zzcu(com.google.android.gms.internal.zzawq r19) throws java.io.IOException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:16:0x004d in {4, 5, 8, 11, 14, 15, 17, 18, 21, 25, 26, 29, 30, 34, 37, 40, 41, 46, 49, 52, 55, 56, 58, 62, 65, 68, 69, 71, 76, 79, 82, 85, 86, 88, 92, 95, 98, 99, 101, 106, 109, 112, 115, 116, 118} preds:[]
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
            r18 = this;
        L_0x0000:
            r0 = r19;
            r2 = r0.ie();
            switch(r2) {
                case 0: goto L_0x0014;
                case 10: goto L_0x0015;
                case 18: goto L_0x005e;
                case 24: goto L_0x00a7;
                case 26: goto L_0x00ec;
                case 32: goto L_0x014b;
                case 34: goto L_0x0194;
                case 40: goto L_0x01f3;
                case 42: goto L_0x023c;
                default: goto L_0x0009;
            };
        L_0x0009:
            goto L_0x000a;
        L_0x000a:
            r0 = r18;
            r1 = r19;
            r3 = super.zza(r1, r2);
            if (r3 != 0) goto L_0x0000;
        L_0x0014:
            return r18;
        L_0x0015:
            r5 = 10;
            r0 = r19;
            r4 = com.google.android.gms.internal.zzaxc.zzc(r0, r5);
            r0 = r18;
            r6 = r0.cbM;
            if (r6 != 0) goto L_0x0047;
        L_0x0023:
            r2 = 0;
        L_0x0024:
            r4 = r4 + r2;
            r6 = new java.lang.String[r4];
            if (r2 == 0) goto L_0x0032;
        L_0x0029:
            r0 = r18;
            r7 = r0.cbM;
            r5 = 0;
            r8 = 0;
            java.lang.System.arraycopy(r7, r5, r6, r8, r2);
        L_0x0032:
            r4 = r6.length;
            r4 = r4 + -1;
            if (r2 >= r4) goto L_0x0051;
        L_0x0037:
            r0 = r19;
            r9 = r0.readString();
            r6[r2] = r9;
            r0 = r19;
            r0.ie();
            r2 = r2 + 1;
            goto L_0x0032;
        L_0x0047:
            r0 = r18;
            r6 = r0.cbM;
            r2 = r6.length;
            goto L_0x0024;
            goto L_0x0051;
        L_0x004e:
            goto L_0x0000;
        L_0x0051:
            r0 = r19;
            r9 = r0.readString();
            r6[r2] = r9;
            r0 = r18;
            r0.cbM = r6;
            goto L_0x0000;
        L_0x005e:
            r5 = 18;
            r0 = r19;
            r4 = com.google.android.gms.internal.zzaxc.zzc(r0, r5);
            r0 = r18;
            r6 = r0.cbN;
            if (r6 != 0) goto L_0x0094;
        L_0x006c:
            r2 = 0;
        L_0x006d:
            r4 = r4 + r2;
            r6 = new java.lang.String[r4];
            if (r2 == 0) goto L_0x007f;
        L_0x0072:
            goto L_0x0076;
        L_0x0073:
            goto L_0x0000;
        L_0x0076:
            r0 = r18;
            r7 = r0.cbN;
            r5 = 0;
            r8 = 0;
            java.lang.System.arraycopy(r7, r5, r6, r8, r2);
        L_0x007f:
            r4 = r6.length;
            r4 = r4 + -1;
            if (r2 >= r4) goto L_0x009a;
        L_0x0084:
            r0 = r19;
            r9 = r0.readString();
            r6[r2] = r9;
            r0 = r19;
            r0.ie();
            r2 = r2 + 1;
            goto L_0x007f;
        L_0x0094:
            r0 = r18;
            r6 = r0.cbN;
            r2 = r6.length;
            goto L_0x006d;
        L_0x009a:
            r0 = r19;
            r9 = r0.readString();
            r6[r2] = r9;
            r0 = r18;
            r0.cbN = r6;
            goto L_0x004e;
        L_0x00a7:
            r5 = 24;
            r0 = r19;
            r4 = com.google.android.gms.internal.zzaxc.zzc(r0, r5);
            r0 = r18;
            r10 = r0.cbO;
            if (r10 != 0) goto L_0x00d9;
        L_0x00b5:
            r2 = 0;
        L_0x00b6:
            r4 = r4 + r2;
            r10 = new int[r4];
            if (r2 == 0) goto L_0x00c4;
        L_0x00bb:
            r0 = r18;
            r11 = r0.cbO;
            r5 = 0;
            r8 = 0;
            java.lang.System.arraycopy(r11, r5, r10, r8, r2);
        L_0x00c4:
            r4 = r10.length;
            r4 = r4 + -1;
            if (r2 >= r4) goto L_0x00df;
        L_0x00c9:
            r0 = r19;
            r4 = r0.ii();
            r10[r2] = r4;
            r0 = r19;
            r0.ie();
            r2 = r2 + 1;
            goto L_0x00c4;
        L_0x00d9:
            r0 = r18;
            r10 = r0.cbO;
            r2 = r10.length;
            goto L_0x00b6;
        L_0x00df:
            r0 = r19;
            r4 = r0.ii();
            r10[r2] = r4;
            r0 = r18;
            r0.cbO = r10;
            goto L_0x0073;
        L_0x00ec:
            r0 = r19;
            r2 = r0.in();
            r0 = r19;
            r2 = r0.zzarv(r2);
            r0 = r19;
            r4 = r0.getPosition();
            r12 = 0;
        L_0x00ff:
            r0 = r19;
            r13 = r0.is();
            if (r13 <= 0) goto L_0x010f;
        L_0x0107:
            r0 = r19;
            r0.ii();
            r12 = r12 + 1;
            goto L_0x00ff;
        L_0x010f:
            r0 = r19;
            r0.zzarx(r4);
            r0 = r18;
            r10 = r0.cbO;
            if (r10 != 0) goto L_0x0137;
        L_0x011a:
            r4 = 0;
        L_0x011b:
            r12 = r12 + r4;
            r10 = new int[r12];
            if (r4 == 0) goto L_0x0129;
        L_0x0120:
            r0 = r18;
            r11 = r0.cbO;
            r5 = 0;
            r8 = 0;
            java.lang.System.arraycopy(r11, r5, r10, r8, r4);
        L_0x0129:
            r12 = r10.length;
            if (r4 >= r12) goto L_0x013d;
        L_0x012c:
            r0 = r19;
            r12 = r0.ii();
            r10[r4] = r12;
            r4 = r4 + 1;
            goto L_0x0129;
        L_0x0137:
            r0 = r18;
            r10 = r0.cbO;
            r4 = r10.length;
            goto L_0x011b;
        L_0x013d:
            r0 = r18;
            r0.cbO = r10;
            goto L_0x0145;
        L_0x0142:
            goto L_0x0000;
        L_0x0145:
            r0 = r19;
            r0.zzarw(r2);
            goto L_0x0142;
        L_0x014b:
            r5 = 32;
            r0 = r19;
            r4 = com.google.android.gms.internal.zzaxc.zzc(r0, r5);
            r0 = r18;
            r14 = r0.cbP;
            if (r14 != 0) goto L_0x017d;
        L_0x0159:
            r2 = 0;
        L_0x015a:
            r4 = r4 + r2;
            r14 = new long[r4];
            if (r2 == 0) goto L_0x0168;
        L_0x015f:
            r0 = r18;
            r15 = r0.cbP;
            r5 = 0;
            r8 = 0;
            java.lang.System.arraycopy(r15, r5, r14, r8, r2);
        L_0x0168:
            r4 = r14.length;
            r4 = r4 + -1;
            if (r2 >= r4) goto L_0x0183;
        L_0x016d:
            r0 = r19;
            r16 = r0.ih();
            r14[r2] = r16;
            r0 = r19;
            r0.ie();
            r2 = r2 + 1;
            goto L_0x0168;
        L_0x017d:
            r0 = r18;
            r14 = r0.cbP;
            r2 = r14.length;
            goto L_0x015a;
        L_0x0183:
            r0 = r19;
            r16 = r0.ih();
            r14[r2] = r16;
            goto L_0x018f;
        L_0x018c:
            goto L_0x0000;
        L_0x018f:
            r0 = r18;
            r0.cbP = r14;
            goto L_0x018c;
        L_0x0194:
            r0 = r19;
            r2 = r0.in();
            r0 = r19;
            r2 = r0.zzarv(r2);
            r0 = r19;
            r12 = r0.getPosition();
            r4 = 0;
        L_0x01a7:
            r0 = r19;
            r13 = r0.is();
            if (r13 <= 0) goto L_0x01b7;
        L_0x01af:
            r0 = r19;
            r0.ih();
            r4 = r4 + 1;
            goto L_0x01a7;
        L_0x01b7:
            r0 = r19;
            r0.zzarx(r12);
            r0 = r18;
            r14 = r0.cbP;
            if (r14 != 0) goto L_0x01df;
        L_0x01c2:
            r12 = 0;
        L_0x01c3:
            r4 = r4 + r12;
            r14 = new long[r4];
            if (r12 == 0) goto L_0x01d1;
        L_0x01c8:
            r0 = r18;
            r15 = r0.cbP;
            r5 = 0;
            r8 = 0;
            java.lang.System.arraycopy(r15, r5, r14, r8, r12);
        L_0x01d1:
            r4 = r14.length;
            if (r12 >= r4) goto L_0x01e5;
        L_0x01d4:
            r0 = r19;
            r16 = r0.ih();
            r14[r12] = r16;
            r12 = r12 + 1;
            goto L_0x01d1;
        L_0x01df:
            r0 = r18;
            r14 = r0.cbP;
            r12 = r14.length;
            goto L_0x01c3;
        L_0x01e5:
            r0 = r18;
            r0.cbP = r14;
            goto L_0x01ed;
        L_0x01ea:
            goto L_0x0000;
        L_0x01ed:
            r0 = r19;
            r0.zzarw(r2);
            goto L_0x01ea;
        L_0x01f3:
            r5 = 40;
            r0 = r19;
            r4 = com.google.android.gms.internal.zzaxc.zzc(r0, r5);
            r0 = r18;
            r14 = r0.cbQ;
            if (r14 != 0) goto L_0x0225;
        L_0x0201:
            r2 = 0;
        L_0x0202:
            r4 = r4 + r2;
            r14 = new long[r4];
            if (r2 == 0) goto L_0x0210;
        L_0x0207:
            r0 = r18;
            r15 = r0.cbQ;
            r5 = 0;
            r8 = 0;
            java.lang.System.arraycopy(r15, r5, r14, r8, r2);
        L_0x0210:
            r4 = r14.length;
            r4 = r4 + -1;
            if (r2 >= r4) goto L_0x022b;
        L_0x0215:
            r0 = r19;
            r16 = r0.ih();
            r14[r2] = r16;
            r0 = r19;
            r0.ie();
            r2 = r2 + 1;
            goto L_0x0210;
        L_0x0225:
            r0 = r18;
            r14 = r0.cbQ;
            r2 = r14.length;
            goto L_0x0202;
        L_0x022b:
            r0 = r19;
            r16 = r0.ih();
            r14[r2] = r16;
            goto L_0x0237;
        L_0x0234:
            goto L_0x0000;
        L_0x0237:
            r0 = r18;
            r0.cbQ = r14;
            goto L_0x0234;
        L_0x023c:
            r0 = r19;
            r2 = r0.in();
            r0 = r19;
            r2 = r0.zzarv(r2);
            r0 = r19;
            r12 = r0.getPosition();
            r4 = 0;
        L_0x024f:
            r0 = r19;
            r13 = r0.is();
            if (r13 <= 0) goto L_0x025f;
        L_0x0257:
            r0 = r19;
            r0.ih();
            r4 = r4 + 1;
            goto L_0x024f;
        L_0x025f:
            r0 = r19;
            r0.zzarx(r12);
            r0 = r18;
            r14 = r0.cbQ;
            if (r14 != 0) goto L_0x0287;
        L_0x026a:
            r12 = 0;
        L_0x026b:
            r4 = r4 + r12;
            r14 = new long[r4];
            if (r12 == 0) goto L_0x0279;
        L_0x0270:
            r0 = r18;
            r15 = r0.cbQ;
            r5 = 0;
            r8 = 0;
            java.lang.System.arraycopy(r15, r5, r14, r8, r12);
        L_0x0279:
            r4 = r14.length;
            if (r12 >= r4) goto L_0x028d;
        L_0x027c:
            r0 = r19;
            r16 = r0.ih();
            r14[r12] = r16;
            r12 = r12 + 1;
            goto L_0x0279;
        L_0x0287:
            r0 = r18;
            r14 = r0.cbQ;
            r12 = r14.length;
            goto L_0x026b;
        L_0x028d:
            r0 = r18;
            r0.cbQ = r14;
            goto L_0x0295;
        L_0x0292:
            goto L_0x0000;
        L_0x0295:
            r0 = r19;
            r0.zzarw(r2);
            goto L_0x0292;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzaxd.zza.zzcu(com.google.android.gms.internal.zzawq):com.google.android.gms.internal.zzaxd$zza");
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzb extends zzaws<zzb> implements Cloneable {
        public int cbR;
        public String cbS;
        public String version;

        public zzb() throws  {
            iM();
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            return iN();
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (this.cbR != 0) {
                $i1 = $i0 + zzawr.zzax(1, this.cbR);
            }
            if (!this.cbS.equals("")) {
                $i1 += zzawr.zzt(2, this.cbS);
            }
            return !this.version.equals("") ? $i1 + zzawr.zzt(3, this.version) : $i1;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzb)) {
                return false;
            }
            zzb $r2 = (zzb) $r1;
            if (this.cbR != $r2.cbR) {
                return false;
            }
            if (this.cbS == null) {
                if ($r2.cbS != null) {
                    return false;
                }
            } else if (!this.cbS.equals($r2.cbS)) {
                return false;
            }
            if (this.version == null) {
                if ($r2.version != null) {
                    return false;
                }
            } else if (!this.version.equals($r2.version)) {
                return false;
            }
            return (this.cbt == null || this.cbt.isEmpty()) ? $r2.cbt == null || $r2.cbt.isEmpty() : this.cbt.equals($r2.cbt);
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.version == null ? 0 : this.version.hashCode()) + (((this.cbS == null ? 0 : this.cbS.hashCode()) + ((((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + this.cbR) * 31)) * 31)) * 31;
            if (!(this.cbt == null || this.cbt.isEmpty())) {
                $i0 = this.cbt.hashCode();
            }
            return $i1 + $i0;
        }

        public zzb iM() throws  {
            this.cbR = 0;
            this.cbS = "";
            this.version = "";
            this.cbt = null;
            this.cbC = -1;
            return this;
        }

        public zzb iN() throws  {
            try {
                return (zzb) super.ix();
            } catch (CloneNotSupportedException $r3) {
                throw new AssertionError($r3);
            }
        }

        public /* synthetic */ zzaws ix() throws CloneNotSupportedException {
            return (zzb) clone();
        }

        public /* synthetic */ zzawz iy() throws CloneNotSupportedException {
            return (zzb) clone();
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzcv($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.cbR != 0) {
                $r1.zzav(1, this.cbR);
            }
            if (!this.cbS.equals("")) {
                $r1.zzs(2, this.cbS);
            }
            if (!this.version.equals("")) {
                $r1.zzs(3, this.version);
            }
            super.writeTo($r1);
        }

        public zzb zzcv(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 8:
                        this.cbR = $r1.ii();
                        continue;
                    case 18:
                        this.cbS = $r1.readString();
                        continue;
                    case 26:
                        this.version = $r1.readString();
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
    public static final class zzc extends zzaws<zzc> implements Cloneable {
        public byte[] cbT;
        public String cbU;
        public byte[][] cbV;
        public boolean cbW;

        public zzc() throws  {
            iO();
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            return iP();
        }

        protected int computeSerializedSize() throws  {
            int $i1 = super.computeSerializedSize();
            int $i2 = $i1;
            if (!Arrays.equals(this.cbT, zzaxc.cbL)) {
                $i2 = $i1 + zzawr.zzb(1, this.cbT);
            }
            if (this.cbV != null && this.cbV.length > 0) {
                int $i3 = 0;
                $i1 = 0;
                for (byte[] $r1 : this.cbV) {
                    if ($r1 != null) {
                        $i1++;
                        $i3 += zzawr.zzbl($r1);
                    }
                }
                $i2 = ($i2 + $i3) + ($i1 * 1);
            }
            if (this.cbW) {
                $i2 += zzawr.zzn(3, this.cbW);
            }
            return !this.cbU.equals("") ? $i2 + zzawr.zzt(4, this.cbU) : $i2;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzc)) {
                return false;
            }
            zzc $r2 = (zzc) $r1;
            if (!Arrays.equals(this.cbT, $r2.cbT)) {
                return false;
            }
            if (this.cbU == null) {
                if ($r2.cbU != null) {
                    return false;
                }
            } else if (!this.cbU.equals($r2.cbU)) {
                return false;
            }
            return zzawx.zza(this.cbV, $r2.cbV) ? this.cbW == $r2.cbW ? (this.cbt == null || this.cbt.isEmpty()) ? $r2.cbt == null || $r2.cbt.isEmpty() : this.cbt.equals($r2.cbt) : false : false;
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.cbW ? (short) 1231 : (short) 1237) + (((((this.cbU == null ? 0 : this.cbU.hashCode()) + ((((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + Arrays.hashCode(this.cbT)) * 31)) * 31) + zzawx.zzd(this.cbV)) * 31)) * 31;
            if (!(this.cbt == null || this.cbt.isEmpty())) {
                $i0 = this.cbt.hashCode();
            }
            return $i1 + $i0;
        }

        public zzc iO() throws  {
            this.cbT = zzaxc.cbL;
            this.cbU = "";
            this.cbV = zzaxc.cbK;
            this.cbW = false;
            this.cbt = null;
            this.cbC = -1;
            return this;
        }

        public zzc iP() throws  {
            try {
                zzc $r2 = (zzc) super.ix();
                if (this.cbV == null || this.cbV.length <= 0) {
                    return $r2;
                }
                $r2.cbV = (byte[][]) this.cbV.clone();
                return $r2;
            } catch (CloneNotSupportedException $r5) {
                throw new AssertionError($r5);
            }
        }

        public /* synthetic */ zzaws ix() throws CloneNotSupportedException {
            return (zzc) clone();
        }

        public /* synthetic */ zzawz iy() throws CloneNotSupportedException {
            return (zzc) clone();
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzcw($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (!Arrays.equals(this.cbT, zzaxc.cbL)) {
                $r1.zza(1, this.cbT);
            }
            if (this.cbV != null && this.cbV.length > 0) {
                for (byte[] $r2 : this.cbV) {
                    if ($r2 != null) {
                        $r1.zza(2, $r2);
                    }
                }
            }
            if (this.cbW) {
                $r1.zzm(3, this.cbW);
            }
            if (!this.cbU.equals("")) {
                $r1.zzs(4, this.cbU);
            }
            super.writeTo($r1);
        }

        public zzc zzcw(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 10:
                        this.cbT = $r1.readBytes();
                        continue;
                    case 18:
                        int $i1 = zzaxc.zzc($r1, 18);
                        $i0 = this.cbV == null ? 0 : this.cbV.length;
                        byte[][] $r3 = new byte[($i1 + $i0)][];
                        if ($i0 != 0) {
                            System.arraycopy(this.cbV, 0, $r3, 0, $i0);
                        }
                        while ($i0 < $r3.length - 1) {
                            $r3[$i0] = $r1.readBytes();
                            $r1.ie();
                            $i0++;
                        }
                        $r3[$i0] = $r1.readBytes();
                        this.cbV = $r3;
                        continue;
                    case 24:
                        this.cbW = $r1.ik();
                        continue;
                    case 34:
                        this.cbU = $r1.readString();
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
    public static final class zzd extends zzaws<zzd> implements Cloneable {
        public boolean bNa;
        public long cbX;
        public long cbY;
        public long cbZ;
        public zze[] cca;
        public byte[] ccb;
        public zzb ccc;
        public byte[] ccd;
        public String cce;
        public String ccf;
        public zza ccg;
        public String cch;
        public long cci;
        public zzc ccj;
        public byte[] cck;
        public String ccl;
        public int ccm;
        public int[] ccn;
        public long cco;
        public zzf ccp;
        public int eventCode;
        public String tag;
        public int zzahl;

        public zzd() throws  {
            iQ();
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            return iR();
        }

        protected int computeSerializedSize() throws  {
            int $i0 = 0;
            int $i1 = super.computeSerializedSize();
            int $i2 = $i1;
            if (this.cbX != 0) {
                $i2 = $i1 + zzawr.zzi(1, this.cbX);
            }
            if (!this.tag.equals("")) {
                $i2 += zzawr.zzt(2, this.tag);
            }
            if (this.cca != null && this.cca.length > 0) {
                for (zzawz $r3 : this.cca) {
                    if ($r3 != null) {
                        $i2 += zzawr.zzc(3, $r3);
                    }
                }
            }
            if (!Arrays.equals(this.ccb, zzaxc.cbL)) {
                $i2 += zzawr.zzb(4, this.ccb);
            }
            if (!Arrays.equals(this.ccd, zzaxc.cbL)) {
                $i2 += zzawr.zzb(6, this.ccd);
            }
            if (this.ccg != null) {
                $i2 += zzawr.zzc(7, this.ccg);
            }
            if (!this.cce.equals("")) {
                $i2 += zzawr.zzt(8, this.cce);
            }
            if (this.ccc != null) {
                $i2 += zzawr.zzc(9, this.ccc);
            }
            if (this.bNa) {
                $i2 += zzawr.zzn(10, this.bNa);
            }
            if (this.eventCode != 0) {
                $i2 += zzawr.zzax(11, this.eventCode);
            }
            if (this.zzahl != 0) {
                $i2 += zzawr.zzax(12, this.zzahl);
            }
            if (!this.ccf.equals("")) {
                $i2 += zzawr.zzt(13, this.ccf);
            }
            if (!this.cch.equals("")) {
                $i2 += zzawr.zzt(14, this.cch);
            }
            if (this.cci != 180000) {
                $i2 += zzawr.zzk(15, this.cci);
            }
            if (this.ccj != null) {
                $i2 += zzawr.zzc(16, this.ccj);
            }
            if (this.cbY != 0) {
                $i2 += zzawr.zzi(17, this.cbY);
            }
            if (!Arrays.equals(this.cck, zzaxc.cbL)) {
                $i2 += zzawr.zzb(18, this.cck);
            }
            if (this.ccm != 0) {
                $i2 += zzawr.zzax(19, this.ccm);
            }
            if (this.ccn != null) {
                int[] $r9 = this.ccn;
                if ($r9.length > 0) {
                    $i1 = 0;
                    while (true) {
                        $r9 = this.ccn;
                        if ($i0 >= $r9.length) {
                            break;
                        }
                        $i1 += zzawr.zzasb(this.ccn[$i0]);
                        $i0++;
                    }
                    $i0 = $i2 + $i1;
                    $r9 = this.ccn;
                    $i2 = $i0 + ($r9.length * 2);
                }
            }
            if (this.cbZ != 0) {
                $i2 += zzawr.zzi(21, this.cbZ);
            }
            if (this.cco != 0) {
                $i2 += zzawr.zzi(22, this.cco);
            }
            if (this.ccp != null) {
                $i2 += zzawr.zzc(23, this.ccp);
            }
            return !this.ccl.equals("") ? $i2 + zzawr.zzt(24, this.ccl) : $i2;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzd)) {
                return false;
            }
            zzd $r2 = (zzd) $r1;
            if (this.cbX != $r2.cbX) {
                return false;
            }
            if (this.cbY != $r2.cbY) {
                return false;
            }
            if (this.cbZ != $r2.cbZ) {
                return false;
            }
            String $r3;
            String $r15;
            if (this.tag != null) {
                $r3 = this.tag;
                $r15 = $r2.tag;
                if (!$r3.equals($r15)) {
                    return false;
                }
            } else if ($r2.tag != null) {
                return false;
            }
            if (this.eventCode != $r2.eventCode) {
                return false;
            }
            if (this.zzahl != $r2.zzahl) {
                return false;
            }
            if (this.bNa != $r2.bNa) {
                return false;
            }
            Object[] $r4 = this.cca;
            Object[] $r5 = $r2.cca;
            if (!zzawx.equals($r4, $r5)) {
                return false;
            }
            if (!Arrays.equals(this.ccb, $r2.ccb)) {
                return false;
            }
            if (this.ccc != null) {
                if (!this.ccc.equals($r2.ccc)) {
                    return false;
                }
            } else if ($r2.ccc != null) {
                return false;
            }
            if (!Arrays.equals(this.ccd, $r2.ccd)) {
                return false;
            }
            if (this.cce != null) {
                $r3 = this.cce;
                $r15 = $r2.cce;
                if (!$r3.equals($r15)) {
                    return false;
                }
            } else if ($r2.cce != null) {
                return false;
            }
            if (this.ccf != null) {
                $r3 = this.ccf;
                $r15 = $r2.ccf;
                if (!$r3.equals($r15)) {
                    return false;
                }
            } else if ($r2.ccf != null) {
                return false;
            }
            if (this.ccg != null) {
                if (!this.ccg.equals($r2.ccg)) {
                    return false;
                }
            } else if ($r2.ccg != null) {
                return false;
            }
            if (this.cch != null) {
                $r3 = this.cch;
                $r15 = $r2.cch;
                if (!$r3.equals($r15)) {
                    return false;
                }
            } else if ($r2.cch != null) {
                return false;
            }
            if (this.cci != $r2.cci) {
                return false;
            }
            if (this.ccj != null) {
                if (!this.ccj.equals($r2.ccj)) {
                    return false;
                }
            } else if ($r2.ccj != null) {
                return false;
            }
            if (!Arrays.equals(this.cck, $r2.cck)) {
                return false;
            }
            if (this.ccl != null) {
                $r3 = this.ccl;
                $r15 = $r2.ccl;
                if (!$r3.equals($r15)) {
                    return false;
                }
            } else if ($r2.ccl != null) {
                return false;
            }
            if (this.ccm != $r2.ccm) {
                return false;
            }
            if (!zzawx.equals(this.ccn, $r2.ccn)) {
                return false;
            }
            if (this.cco != $r2.cco) {
                return false;
            }
            zzawv $r14;
            if (this.ccp != null) {
                if (!this.ccp.equals($r2.ccp)) {
                    return false;
                }
            } else if ($r2.ccp != null) {
                return false;
            }
            if (this.cbt != null) {
                $r14 = this.cbt;
                if (!$r14.isEmpty()) {
                    return this.cbt.equals($r2.cbt);
                }
            }
            if ($r2.cbt != null) {
                $r14 = $r2.cbt;
                if (!$r14.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:42:0x013f
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
            r21 = this;
            r1 = 0;
            r0 = r21;
            r2 = r0.getClass();
            r3 = r2.getName();
            r4 = r3.hashCode();
            r4 = r4 + 527;
            r4 = r4 * 31;
            r0 = r21;
            r5 = r0.cbX;
            r0 = r21;
            r7 = r0.cbX;
            r9 = 32;
            r7 = r7 >>> r9;
            r5 = r5 ^ r7;
            r10 = (int) r5;
            r4 = r4 + r10;
            r4 = r4 * 31;
            r0 = r21;
            r5 = r0.cbY;
            r0 = r21;
            r7 = r0.cbY;
            r9 = 32;
            r7 = r7 >>> r9;
            r5 = r5 ^ r7;
            r10 = (int) r5;
            r4 = r4 + r10;
            r4 = r4 * 31;
            r0 = r21;
            r5 = r0.cbZ;
            r0 = r21;
            r7 = r0.cbZ;
            r9 = 32;
            r7 = r7 >>> r9;
            r5 = r5 ^ r7;
            r10 = (int) r5;
            r4 = r4 + r10;
            r4 = r4 * 31;
            r0 = r21;
            r3 = r0.tag;
            if (r3 != 0) goto L_0x016d;
        L_0x0049:
            r10 = 0;
        L_0x004a:
            r4 = r10 + r4;
            r4 = r4 * 31;
            r0 = r21;
            r10 = r0.eventCode;
            r4 = r4 + r10;
            r4 = r4 * 31;
            r0 = r21;
            r10 = r0.zzahl;
            r4 = r4 + r10;
            r4 = r4 * 31;
            r0 = r21;
            r11 = r0.bNa;
            if (r11 == 0) goto L_0x017e;
        L_0x0062:
            r12 = 1231; // 0x4cf float:1.725E-42 double:6.08E-321;
        L_0x0064:
            r4 = r12 + r4;
            r4 = r4 * 31;
            r0 = r21;
            r13 = r0.cca;
            r10 = com.google.android.gms.internal.zzawx.hashCode(r13);
            r4 = r4 + r10;
            r4 = r4 * 31;
            r0 = r21;
            r14 = r0.ccb;
            r10 = java.util.Arrays.hashCode(r14);
            r4 = r4 + r10;
            r4 = r4 * 31;
            r0 = r21;
            r15 = r0.ccc;
            if (r15 != 0) goto L_0x0181;
        L_0x0084:
            r10 = 0;
        L_0x0085:
            r4 = r10 + r4;
            r4 = r4 * 31;
            r0 = r21;
            r14 = r0.ccd;
            r10 = java.util.Arrays.hashCode(r14);
            r4 = r4 + r10;
            r4 = r4 * 31;
            r0 = r21;
            r3 = r0.cce;
            if (r3 != 0) goto L_0x018e;
        L_0x009a:
            r10 = 0;
        L_0x009b:
            r4 = r10 + r4;
            r4 = r4 * 31;
            r0 = r21;
            r3 = r0.ccf;
            if (r3 != 0) goto L_0x0197;
        L_0x00a5:
            r10 = 0;
        L_0x00a6:
            r4 = r10 + r4;
            r4 = r4 * 31;
            r0 = r21;
            r0 = r0.ccg;
            r16 = r0;
            if (r16 != 0) goto L_0x01a0;
        L_0x00b2:
            r10 = 0;
        L_0x00b3:
            r4 = r10 + r4;
            r4 = r4 * 31;
            r0 = r21;
            r3 = r0.cch;
            if (r3 != 0) goto L_0x01ab;
        L_0x00bd:
            r10 = 0;
        L_0x00be:
            r4 = r10 + r4;
            r4 = r4 * 31;
            r0 = r21;
            r5 = r0.cci;
            r0 = r21;
            r7 = r0.cci;
            r9 = 32;
            r7 = r7 >>> r9;
            r5 = r5 ^ r7;
            r10 = (int) r5;
            r4 = r4 + r10;
            r4 = r4 * 31;
            r0 = r21;
            r0 = r0.ccj;
            r17 = r0;
            if (r17 != 0) goto L_0x01b4;
        L_0x00da:
            r10 = 0;
        L_0x00db:
            r4 = r10 + r4;
            r4 = r4 * 31;
            r0 = r21;
            r14 = r0.cck;
            r10 = java.util.Arrays.hashCode(r14);
            r4 = r4 + r10;
            r4 = r4 * 31;
            r0 = r21;
            r3 = r0.ccl;
            goto L_0x00f2;
        L_0x00ef:
            goto L_0x0085;
        L_0x00f2:
            if (r3 != 0) goto L_0x01bf;
        L_0x00f4:
            r10 = 0;
        L_0x00f5:
            r4 = r10 + r4;
            r4 = r4 * 31;
            r0 = r21;
            r10 = r0.ccm;
            r4 = r4 + r10;
            r4 = r4 * 31;
            goto L_0x0104;
        L_0x0101:
            goto L_0x009b;
        L_0x0104:
            r0 = r21;
            r0 = r0.ccn;
            r18 = r0;
            goto L_0x010e;
        L_0x010b:
            goto L_0x00a6;
        L_0x010e:
            r10 = com.google.android.gms.internal.zzawx.hashCode(r0);
            r4 = r4 + r10;
            r4 = r4 * 31;
            r0 = r21;
            r5 = r0.cco;
            goto L_0x011d;
        L_0x011a:
            goto L_0x00b3;
        L_0x011d:
            r0 = r21;
            r7 = r0.cco;
            r9 = 32;
            r7 = r7 >>> r9;
            r5 = r5 ^ r7;
            goto L_0x0129;
        L_0x0126:
            goto L_0x00be;
        L_0x0129:
            r10 = (int) r5;
            r4 = r4 + r10;
            r4 = r4 * 31;
            goto L_0x0131;
        L_0x012e:
            goto L_0x00ef;
        L_0x0131:
            r0 = r21;
            r0 = r0.ccp;
            r19 = r0;
            if (r19 != 0) goto L_0x01c8;
        L_0x0139:
            r10 = 0;
        L_0x013a:
            r4 = r10 + r4;
            r4 = r4 * 31;
            goto L_0x0146;
            goto L_0x0143;
        L_0x0140:
            goto L_0x0101;
        L_0x0143:
            goto L_0x00db;
        L_0x0146:
            r0 = r21;
            r0 = r0.cbt;
            r20 = r0;
            goto L_0x0150;
        L_0x014d:
            goto L_0x010b;
        L_0x0150:
            if (r20 == 0) goto L_0x0166;
        L_0x0152:
            r0 = r21;
            r0 = r0.cbt;
            r20 = r0;
            goto L_0x0160;
        L_0x0159:
            goto L_0x00f5;
            goto L_0x0160;
        L_0x015d:
            goto L_0x011a;
        L_0x0160:
            r11 = r0.isEmpty();
            if (r11 == 0) goto L_0x01d3;
        L_0x0166:
            r1 = r4 + r1;
            return r1;
            goto L_0x016d;
        L_0x016a:
            goto L_0x0126;
        L_0x016d:
            r0 = r21;
            r3 = r0.tag;
            goto L_0x0175;
        L_0x0172:
            goto L_0x004a;
        L_0x0175:
            r10 = r3.hashCode();
            goto L_0x0172;
            goto L_0x017e;
        L_0x017b:
            goto L_0x0064;
        L_0x017e:
            r12 = 1237; // 0x4d5 float:1.733E-42 double:6.11E-321;
            goto L_0x017b;
        L_0x0181:
            r0 = r21;
            r15 = r0.ccc;
            goto L_0x0189;
        L_0x0186:
            goto L_0x013a;
        L_0x0189:
            r10 = r15.hashCode();
            goto L_0x012e;
        L_0x018e:
            r0 = r21;
            r3 = r0.cce;
            r10 = r3.hashCode();
            goto L_0x0140;
        L_0x0197:
            r0 = r21;
            r3 = r0.ccf;
            r10 = r3.hashCode();
            goto L_0x014d;
        L_0x01a0:
            r0 = r21;
            r0 = r0.ccg;
            r16 = r0;
            r10 = r0.hashCode();
            goto L_0x015d;
        L_0x01ab:
            r0 = r21;
            r3 = r0.cch;
            r10 = r3.hashCode();
            goto L_0x016a;
        L_0x01b4:
            r0 = r21;
            r0 = r0.ccj;
            r17 = r0;
            r10 = r0.hashCode();
            goto L_0x0143;
        L_0x01bf:
            r0 = r21;
            r3 = r0.ccl;
            r10 = r3.hashCode();
            goto L_0x0159;
        L_0x01c8:
            r0 = r21;
            r0 = r0.ccp;
            r19 = r0;
            r10 = r0.hashCode();
            goto L_0x0186;
        L_0x01d3:
            r0 = r21;
            r0 = r0.cbt;
            r20 = r0;
            r1 = r0.hashCode();
            goto L_0x0166;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzaxd.zzd.hashCode():int");
        }

        public zzd iQ() throws  {
            this.cbX = 0;
            this.cbY = 0;
            this.cbZ = 0;
            this.tag = "";
            this.eventCode = 0;
            this.zzahl = 0;
            this.bNa = false;
            this.cca = zze.iS();
            this.ccb = zzaxc.cbL;
            this.ccc = null;
            this.ccd = zzaxc.cbL;
            this.cce = "";
            this.ccf = "";
            this.ccg = null;
            this.cch = "";
            this.cci = 180000;
            this.ccj = null;
            this.cck = zzaxc.cbL;
            this.ccl = "";
            this.ccm = 0;
            this.ccn = zzaxc.cbE;
            this.cco = 0;
            this.ccp = null;
            this.cbt = null;
            this.cbC = -1;
            return this;
        }

        public zzd iR() throws  {
            try {
                zzd $r3 = (zzd) super.ix();
                if (this.cca != null && this.cca.length > 0) {
                    $r3.cca = new zze[this.cca.length];
                    for (int $i0 = 0; $i0 < this.cca.length; $i0++) {
                        if (this.cca[$i0] != null) {
                            $r3.cca[$i0] = (zze) this.cca[$i0].clone();
                        }
                    }
                }
                if (this.ccc != null) {
                    $r3.ccc = (zzb) this.ccc.clone();
                }
                if (this.ccg != null) {
                    $r3.ccg = (zza) this.ccg.clone();
                }
                if (this.ccj != null) {
                    zzc $r11 = this.ccj;
                    $r3.ccj = (zzc) $r11.clone();
                }
                if (this.ccn != null) {
                    int[] $r12 = this.ccn;
                    if ($r12.length > 0) {
                        $r12 = this.ccn;
                        $r3.ccn = (int[]) $r12.clone();
                    }
                }
                if (this.ccp == null) {
                    return $r3;
                }
                zzf $r13 = this.ccp;
                $r3.ccp = (zzf) $r13.clone();
                return $r3;
            } catch (CloneNotSupportedException $r7) {
                throw new AssertionError($r7);
            }
        }

        public /* synthetic */ zzaws ix() throws CloneNotSupportedException {
            return (zzd) clone();
        }

        public /* synthetic */ zzawz iy() throws CloneNotSupportedException {
            return (zzd) clone();
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzcx($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            int $i0 = 0;
            if (this.cbX != 0) {
                $r1.zzf(1, this.cbX);
            }
            if (!this.tag.equals("")) {
                $r1.zzs(2, this.tag);
            }
            if (this.cca != null && this.cca.length > 0) {
                for (zzawz $r4 : this.cca) {
                    if ($r4 != null) {
                        $r1.zza(3, $r4);
                    }
                }
            }
            if (!Arrays.equals(this.ccb, zzaxc.cbL)) {
                $r1.zza(4, this.ccb);
            }
            if (!Arrays.equals(this.ccd, zzaxc.cbL)) {
                $r1.zza(6, this.ccd);
            }
            if (this.ccg != null) {
                $r1.zza(7, this.ccg);
            }
            if (!this.cce.equals("")) {
                $r1.zzs(8, this.cce);
            }
            if (this.ccc != null) {
                $r1.zza(9, this.ccc);
            }
            if (this.bNa) {
                $r1.zzm(10, this.bNa);
            }
            if (this.eventCode != 0) {
                $r1.zzav(11, this.eventCode);
            }
            if (this.zzahl != 0) {
                $r1.zzav(12, this.zzahl);
            }
            if (!this.ccf.equals("")) {
                $r1.zzs(13, this.ccf);
            }
            if (!this.cch.equals("")) {
                $r1.zzs(14, this.cch);
            }
            if (this.cci != 180000) {
                $r1.zzh(15, this.cci);
            }
            if (this.ccj != null) {
                $r1.zza(16, this.ccj);
            }
            if (this.cbY != 0) {
                $r1.zzf(17, this.cbY);
            }
            if (!Arrays.equals(this.cck, zzaxc.cbL)) {
                $r1.zza(18, this.cck);
            }
            if (this.ccm != 0) {
                $r1.zzav(19, this.ccm);
            }
            if (this.ccn != null) {
                int[] $r10 = this.ccn;
                if ($r10.length > 0) {
                    while (true) {
                        $r10 = this.ccn;
                        if ($i0 >= $r10.length) {
                            break;
                        }
                        $r1.zzav(20, this.ccn[$i0]);
                        $i0++;
                    }
                }
            }
            if (this.cbZ != 0) {
                $r1.zzf(21, this.cbZ);
            }
            if (this.cco != 0) {
                $r1.zzf(22, this.cco);
            }
            if (this.ccp != null) {
                $r1.zza(23, this.ccp);
            }
            if (!this.ccl.equals("")) {
                $r1.zzs(24, this.ccl);
            }
            super.writeTo($r1);
        }

        public com.google.android.gms.internal.zzaxd.zzd zzcx(com.google.android.gms.internal.zzawq r23) throws java.io.IOException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:76:0x0195 in {4, 5, 6, 7, 10, 14, 16, 18, 19, 21, 24, 26, 28, 29, 30, 36, 41, 45, 48, 51, 54, 57, 61, 63, 66, 69, 72, 77, 81, 84, 87, 88, 90, 95, 98, 101, 104, 105, 107, 110, 113, 117, 119, 122} preds:[]
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
                case 0: goto L_0x0014;
                case 8: goto L_0x0015;
                case 18: goto L_0x0020;
                case 26: goto L_0x002b;
                case 34: goto L_0x0098;
                case 50: goto L_0x00a3;
                case 58: goto L_0x00ae;
                case 66: goto L_0x00c7;
                case 74: goto L_0x00d2;
                case 80: goto L_0x00eb;
                case 88: goto L_0x00f6;
                case 96: goto L_0x0105;
                case 106: goto L_0x0114;
                case 114: goto L_0x0123;
                case 120: goto L_0x0132;
                case 130: goto L_0x0141;
                case 136: goto L_0x0168;
                case 146: goto L_0x0177;
                case 152: goto L_0x0186;
                case 160: goto L_0x019e;
                case 162: goto L_0x01f7;
                case 168: goto L_0x026d;
                case 176: goto L_0x027c;
                case 186: goto L_0x028b;
                case 194: goto L_0x02b2;
                default: goto L_0x0009;
            };
        L_0x0009:
            goto L_0x000a;
        L_0x000a:
            r0 = r22;
            r1 = r23;
            r3 = super.zza(r1, r2);
            if (r3 != 0) goto L_0x0000;
        L_0x0014:
            return r22;
        L_0x0015:
            r0 = r23;
            r4 = r0.ih();
            r0 = r22;
            r0.cbX = r4;
            goto L_0x0000;
        L_0x0020:
            r0 = r23;
            r6 = r0.readString();
            r0 = r22;
            r0.tag = r6;
            goto L_0x0000;
        L_0x002b:
            r8 = 26;
            r0 = r23;
            r7 = com.google.android.gms.internal.zzaxc.zzc(r0, r8);
            r0 = r22;
            r9 = r0.cca;
            if (r9 != 0) goto L_0x007f;
        L_0x0039:
            r2 = 0;
        L_0x003a:
            r7 = r7 + r2;
            r9 = new com.google.android.gms.internal.zzaxd.zze[r7];
            if (r2 == 0) goto L_0x0054;
        L_0x003f:
            goto L_0x0043;
        L_0x0040:
            goto L_0x0000;
        L_0x0043:
            goto L_0x0047;
        L_0x0044:
            goto L_0x0000;
        L_0x0047:
            r0 = r22;
            r10 = r0.cca;
            goto L_0x004f;
        L_0x004c:
            goto L_0x0000;
        L_0x004f:
            r8 = 0;
            r11 = 0;
            java.lang.System.arraycopy(r10, r8, r9, r11, r2);
        L_0x0054:
            r7 = r9.length;
            r7 = r7 + -1;
            goto L_0x005b;
        L_0x0058:
            goto L_0x0000;
        L_0x005b:
            if (r2 >= r7) goto L_0x0085;
        L_0x005d:
            r12 = new com.google.android.gms.internal.zzaxd$zze;
            goto L_0x0063;
        L_0x0060:
            goto L_0x0000;
        L_0x0063:
            r12.<init>();
            r9[r2] = r12;
            r12 = r9[r2];
            r0 = r23;
            r0.zza(r12);
            goto L_0x0073;
        L_0x0070:
            goto L_0x0000;
        L_0x0073:
            r0 = r23;
            r0.ie();
            goto L_0x007c;
        L_0x0079:
            goto L_0x0000;
        L_0x007c:
            r2 = r2 + 1;
            goto L_0x0054;
        L_0x007f:
            r0 = r22;
            r9 = r0.cca;
            r2 = r9.length;
            goto L_0x003a;
        L_0x0085:
            r12 = new com.google.android.gms.internal.zzaxd$zze;
            r12.<init>();
            r9[r2] = r12;
            r12 = r9[r2];
            r0 = r23;
            r0.zza(r12);
            r0 = r22;
            r0.cca = r9;
            goto L_0x0040;
        L_0x0098:
            r0 = r23;
            r13 = r0.readBytes();
            r0 = r22;
            r0.ccb = r13;
            goto L_0x0044;
        L_0x00a3:
            r0 = r23;
            r13 = r0.readBytes();
            r0 = r22;
            r0.ccd = r13;
            goto L_0x004c;
        L_0x00ae:
            r0 = r22;
            r14 = r0.ccg;
            if (r14 != 0) goto L_0x00bd;
        L_0x00b4:
            r14 = new com.google.android.gms.internal.zzaxd$zza;
            r14.<init>();
            r0 = r22;
            r0.ccg = r14;
        L_0x00bd:
            r0 = r22;
            r14 = r0.ccg;
            r0 = r23;
            r0.zza(r14);
            goto L_0x0058;
        L_0x00c7:
            r0 = r23;
            r6 = r0.readString();
            r0 = r22;
            r0.cce = r6;
            goto L_0x0060;
        L_0x00d2:
            r0 = r22;
            r15 = r0.ccc;
            if (r15 != 0) goto L_0x00e1;
        L_0x00d8:
            r15 = new com.google.android.gms.internal.zzaxd$zzb;
            r15.<init>();
            r0 = r22;
            r0.ccc = r15;
        L_0x00e1:
            r0 = r22;
            r15 = r0.ccc;
            r0 = r23;
            r0.zza(r15);
            goto L_0x0070;
        L_0x00eb:
            r0 = r23;
            r3 = r0.ik();
            r0 = r22;
            r0.bNa = r3;
            goto L_0x0079;
        L_0x00f6:
            r0 = r23;
            r2 = r0.ii();
            goto L_0x0100;
        L_0x00fd:
            goto L_0x0000;
        L_0x0100:
            r0 = r22;
            r0.eventCode = r2;
            goto L_0x00fd;
        L_0x0105:
            r0 = r23;
            r2 = r0.ii();
            goto L_0x010f;
        L_0x010c:
            goto L_0x0000;
        L_0x010f:
            r0 = r22;
            r0.zzahl = r2;
            goto L_0x010c;
        L_0x0114:
            r0 = r23;
            r6 = r0.readString();
            goto L_0x011e;
        L_0x011b:
            goto L_0x0000;
        L_0x011e:
            r0 = r22;
            r0.ccf = r6;
            goto L_0x011b;
        L_0x0123:
            r0 = r23;
            r6 = r0.readString();
            goto L_0x012d;
        L_0x012a:
            goto L_0x0000;
        L_0x012d:
            r0 = r22;
            r0.cch = r6;
            goto L_0x012a;
        L_0x0132:
            r0 = r23;
            r4 = r0.im();
            goto L_0x013c;
        L_0x0139:
            goto L_0x0000;
        L_0x013c:
            r0 = r22;
            r0.cci = r4;
            goto L_0x0139;
        L_0x0141:
            r0 = r22;
            r0 = r0.ccj;
            r16 = r0;
            if (r16 != 0) goto L_0x0156;
        L_0x0149:
            r16 = new com.google.android.gms.internal.zzaxd$zzc;
            r0 = r16;
            r0.<init>();
            r0 = r16;
            r1 = r22;
            r1.ccj = r0;
        L_0x0156:
            r0 = r22;
            r0 = r0.ccj;
            r16 = r0;
            goto L_0x0160;
        L_0x015d:
            goto L_0x0000;
        L_0x0160:
            r0 = r23;
            r1 = r16;
            r0.zza(r1);
            goto L_0x015d;
        L_0x0168:
            r0 = r23;
            r4 = r0.ih();
            goto L_0x0172;
        L_0x016f:
            goto L_0x0000;
        L_0x0172:
            r0 = r22;
            r0.cbY = r4;
            goto L_0x016f;
        L_0x0177:
            r0 = r23;
            r13 = r0.readBytes();
            goto L_0x0181;
        L_0x017e:
            goto L_0x0000;
        L_0x0181:
            r0 = r22;
            r0.cck = r13;
            goto L_0x017e;
        L_0x0186:
            r0 = r23;
            r2 = r0.ii();
            goto L_0x0190;
        L_0x018d:
            goto L_0x0000;
        L_0x0190:
            switch(r2) {
                case 0: goto L_0x0199;
                case 1: goto L_0x0199;
                case 2: goto L_0x0199;
                default: goto L_0x0193;
            };
        L_0x0193:
            goto L_0x0194;
        L_0x0194:
            goto L_0x018d;
            goto L_0x0199;
        L_0x0196:
            goto L_0x0000;
        L_0x0199:
            r0 = r22;
            r0.ccm = r2;
            goto L_0x0196;
        L_0x019e:
            r8 = 160; // 0xa0 float:2.24E-43 double:7.9E-322;
            r0 = r23;
            r7 = com.google.android.gms.internal.zzaxc.zzc(r0, r8);
            r0 = r22;
            r0 = r0.ccn;
            r17 = r0;
            if (r17 != 0) goto L_0x01dc;
        L_0x01ae:
            r2 = 0;
        L_0x01af:
            r7 = r7 + r2;
            r0 = new int[r7];
            r17 = r0;
            if (r2 == 0) goto L_0x01c5;
        L_0x01b6:
            r0 = r22;
            r0 = r0.ccn;
            r18 = r0;
            r8 = 0;
            r11 = 0;
            r0 = r18;
            r1 = r17;
            java.lang.System.arraycopy(r0, r8, r1, r11, r2);
        L_0x01c5:
            r0 = r17;
            r7 = r0.length;
            r7 = r7 + -1;
            if (r2 >= r7) goto L_0x01e4;
        L_0x01cc:
            r0 = r23;
            r7 = r0.ii();
            r17[r2] = r7;
            r0 = r23;
            r0.ie();
            r2 = r2 + 1;
            goto L_0x01c5;
        L_0x01dc:
            r0 = r22;
            r0 = r0.ccn;
            r17 = r0;
            r2 = r0.length;
            goto L_0x01af;
        L_0x01e4:
            r0 = r23;
            r7 = r0.ii();
            r17[r2] = r7;
            goto L_0x01f0;
        L_0x01ed:
            goto L_0x0000;
        L_0x01f0:
            r0 = r17;
            r1 = r22;
            r1.ccn = r0;
            goto L_0x01ed;
        L_0x01f7:
            r0 = r23;
            r2 = r0.in();
            r0 = r23;
            r2 = r0.zzarv(r2);
            r0 = r23;
            r7 = r0.getPosition();
            r19 = 0;
        L_0x020b:
            r0 = r23;
            r20 = r0.is();
            if (r20 <= 0) goto L_0x021b;
        L_0x0213:
            r0 = r23;
            r0.ii();
            r19 = r19 + 1;
            goto L_0x020b;
        L_0x021b:
            r0 = r23;
            r0.zzarx(r7);
            r0 = r22;
            r0 = r0.ccn;
            r17 = r0;
            if (r17 != 0) goto L_0x0255;
        L_0x0228:
            r7 = 0;
        L_0x0229:
            r0 = r19;
            r0 = r0 + r7;
            r19 = r0;
            r0 = new int[r0];
            r17 = r0;
            if (r7 == 0) goto L_0x0243;
        L_0x0234:
            r0 = r22;
            r0 = r0.ccn;
            r18 = r0;
            r8 = 0;
            r11 = 0;
            r0 = r18;
            r1 = r17;
            java.lang.System.arraycopy(r0, r8, r1, r11, r7);
        L_0x0243:
            r0 = r17;
            r0 = r0.length;
            r19 = r0;
            if (r7 >= r0) goto L_0x025d;
        L_0x024a:
            r0 = r23;
            r19 = r0.ii();
            r17[r7] = r19;
            r7 = r7 + 1;
            goto L_0x0243;
        L_0x0255:
            r0 = r22;
            r0 = r0.ccn;
            r17 = r0;
            r7 = r0.length;
            goto L_0x0229;
        L_0x025d:
            r0 = r17;
            r1 = r22;
            r1.ccn = r0;
            goto L_0x0267;
        L_0x0264:
            goto L_0x0000;
        L_0x0267:
            r0 = r23;
            r0.zzarw(r2);
            goto L_0x0264;
        L_0x026d:
            r0 = r23;
            r4 = r0.ih();
            goto L_0x0277;
        L_0x0274:
            goto L_0x0000;
        L_0x0277:
            r0 = r22;
            r0.cbZ = r4;
            goto L_0x0274;
        L_0x027c:
            r0 = r23;
            r4 = r0.ih();
            goto L_0x0286;
        L_0x0283:
            goto L_0x0000;
        L_0x0286:
            r0 = r22;
            r0.cco = r4;
            goto L_0x0283;
        L_0x028b:
            r0 = r22;
            r0 = r0.ccp;
            r21 = r0;
            if (r21 != 0) goto L_0x02a0;
        L_0x0293:
            r21 = new com.google.android.gms.internal.zzaxd$zzf;
            r0 = r21;
            r0.<init>();
            r0 = r21;
            r1 = r22;
            r1.ccp = r0;
        L_0x02a0:
            r0 = r22;
            r0 = r0.ccp;
            r21 = r0;
            goto L_0x02aa;
        L_0x02a7:
            goto L_0x0000;
        L_0x02aa:
            r0 = r23;
            r1 = r21;
            r0.zza(r1);
            goto L_0x02a7;
        L_0x02b2:
            r0 = r23;
            r6 = r0.readString();
            goto L_0x02bc;
        L_0x02b9:
            goto L_0x0000;
        L_0x02bc:
            r0 = r22;
            r0.ccl = r6;
            goto L_0x02b9;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzaxd.zzd.zzcx(com.google.android.gms.internal.zzawq):com.google.android.gms.internal.zzaxd$zzd");
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zze extends zzaws<zze> implements Cloneable {
        private static volatile zze[] ccq;
        public String value;
        public String zzca;

        public zze() throws  {
            iT();
        }

        public static zze[] iS() throws  {
            if (ccq == null) {
                synchronized (zzawx.cbB) {
                    if (ccq == null) {
                        ccq = new zze[0];
                    }
                }
            }
            return ccq;
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            return iU();
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (!this.zzca.equals("")) {
                $i1 = $i0 + zzawr.zzt(1, this.zzca);
            }
            return !this.value.equals("") ? $i1 + zzawr.zzt(2, this.value) : $i1;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zze)) {
                return false;
            }
            zze $r2 = (zze) $r1;
            if (this.zzca == null) {
                if ($r2.zzca != null) {
                    return false;
                }
            } else if (!this.zzca.equals($r2.zzca)) {
                return false;
            }
            if (this.value == null) {
                if ($r2.value != null) {
                    return false;
                }
            } else if (!this.value.equals($r2.value)) {
                return false;
            }
            return (this.cbt == null || this.cbt.isEmpty()) ? $r2.cbt == null || $r2.cbt.isEmpty() : this.cbt.equals($r2.cbt);
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.value == null ? 0 : this.value.hashCode()) + (((this.zzca == null ? 0 : this.zzca.hashCode()) + ((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31)) * 31)) * 31;
            if (!(this.cbt == null || this.cbt.isEmpty())) {
                $i0 = this.cbt.hashCode();
            }
            return $i1 + $i0;
        }

        public zze iT() throws  {
            this.zzca = "";
            this.value = "";
            this.cbt = null;
            this.cbC = -1;
            return this;
        }

        public zze iU() throws  {
            try {
                return (zze) super.ix();
            } catch (CloneNotSupportedException $r3) {
                throw new AssertionError($r3);
            }
        }

        public /* synthetic */ zzaws ix() throws CloneNotSupportedException {
            return (zze) clone();
        }

        public /* synthetic */ zzawz iy() throws CloneNotSupportedException {
            return (zze) clone();
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzcy($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (!this.zzca.equals("")) {
                $r1.zzs(1, this.zzca);
            }
            if (!this.value.equals("")) {
                $r1.zzs(2, this.value);
            }
            super.writeTo($r1);
        }

        public zze zzcy(zzawq $r1) throws IOException {
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
    public static final class zzf extends zzaws<zzf> implements Cloneable {
        public int networkType;

        public zzf() throws  {
            iV();
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            return iW();
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            return this.networkType != -1 ? $i0 + zzawr.zzax(1, this.networkType) : $i0;
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == this) {
                return true;
            }
            if (!($r1 instanceof zzf)) {
                return false;
            }
            zzf $r2 = (zzf) $r1;
            return this.networkType == $r2.networkType ? (this.cbt == null || this.cbt.isEmpty()) ? $r2.cbt == null || $r2.cbt.isEmpty() : this.cbt.equals($r2.cbt) : false;
        }

        public int hashCode() throws  {
            int $i0 = (((getClass().getName().hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + this.networkType) * 31;
            int $i1 = (this.cbt == null || this.cbt.isEmpty()) ? 0 : this.cbt.hashCode();
            return $i1 + $i0;
        }

        public zzf iV() throws  {
            this.networkType = -1;
            this.cbt = null;
            this.cbC = -1;
            return this;
        }

        public zzf iW() throws  {
            try {
                return (zzf) super.ix();
            } catch (CloneNotSupportedException $r3) {
                throw new AssertionError($r3);
            }
        }

        public /* synthetic */ zzaws ix() throws CloneNotSupportedException {
            return (zzf) clone();
        }

        public /* synthetic */ zzawz iy() throws CloneNotSupportedException {
            return (zzf) clone();
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzcz($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.networkType != -1) {
                $r1.zzav(1, this.networkType);
            }
            super.writeTo($r1);
        }

        public zzf zzcz(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 8:
                        $i0 = $r1.ii();
                        switch ($i0) {
                            case -1:
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                            case 11:
                            case 12:
                            case 13:
                            case 14:
                            case 15:
                            case 16:
                            case 17:
                                this.networkType = $i0;
                                break;
                            default:
                                continue;
                        }
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
