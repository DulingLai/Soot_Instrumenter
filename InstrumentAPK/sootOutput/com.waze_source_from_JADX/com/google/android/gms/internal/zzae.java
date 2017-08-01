package com.google.android.gms.internal;

import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzae {

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zza extends zzaws<zza> {
        public String zzcr = null;
        public String zzcs = null;
        public Long zzct = null;
        public Long zzcu = null;
        public Long zzcv = null;
        public Long zzcw = null;
        public Long zzcx = null;
        public Long zzcy = null;
        public Long zzcz = null;
        public Long zzda = null;
        public Long zzdb = null;
        public Long zzdc = null;
        public String zzdd = null;
        public Long zzde = null;
        public Long zzdf = null;
        public Long zzdg = null;
        public Long zzdh = null;
        public Long zzdi = null;
        public Long zzdj = null;
        public Long zzdk = null;
        public Long zzdl = null;
        public Long zzdm = null;
        public String zzdn = null;
        public String zzdo = null;
        public Long zzdp = null;
        public Long zzdq = null;
        public Long zzdr = null;
        public String zzds = null;
        public Long zzdt = null;
        public Long zzdu = null;
        public Long zzdv = null;
        public zzb zzdw;
        public Long zzdx = null;
        public Long zzdy = null;
        public Long zzdz = null;
        public Long zzea = null;
        public Long zzeb = null;
        public Long zzec = null;
        public zza[] zzed = zza.zzy();
        public Long zzee = null;
        public String zzef = null;
        public Integer zzeg = null;
        public Boolean zzeh = null;
        public String zzei = null;
        public Long zzej = null;
        public zze zzek;

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class zza extends zzaws<zza> {
            private static volatile zza[] zzel;
            public Long zzde = null;
            public Long zzdf = null;

            public static zza[] zzy() throws  {
                if (zzel == null) {
                    synchronized (zzawx.cbB) {
                        if (zzel == null) {
                            zzel = new zza[0];
                        }
                    }
                }
                return zzel;
            }

            protected int computeSerializedSize() throws  {
                int $i0 = super.computeSerializedSize();
                int $i1 = $i0;
                if (this.zzde != null) {
                    $i1 = $i0 + zzawr.zzi(1, this.zzde.longValue());
                }
                return this.zzdf != null ? $i1 + zzawr.zzi(2, this.zzdf.longValue()) : $i1;
            }

            public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
                return zzc($r1);
            }

            public void writeTo(zzawr $r1) throws IOException {
                if (this.zzde != null) {
                    $r1.zzf(1, this.zzde.longValue());
                }
                if (this.zzdf != null) {
                    $r1.zzf(2, this.zzdf.longValue());
                }
                super.writeTo($r1);
            }

            public zza zzc(zzawq $r1) throws IOException {
                while (true) {
                    int $i0 = $r1.ie();
                    switch ($i0) {
                        case 0:
                            break;
                        case 8:
                            this.zzde = Long.valueOf($r1.ih());
                            continue;
                        case 16:
                            this.zzdf = Long.valueOf($r1.ih());
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

        public static zza zzc(byte[] $r0) throws zzawy {
            return (zza) zzawz.zza(new zza(), $r0);
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (this.zzcs != null) {
                $i1 = $i0 + zzawr.zzt(1, this.zzcs);
            }
            if (this.zzcr != null) {
                $i1 += zzawr.zzt(2, this.zzcr);
            }
            if (this.zzct != null) {
                $i1 += zzawr.zzi(3, this.zzct.longValue());
            }
            if (this.zzcu != null) {
                $i1 += zzawr.zzi(4, this.zzcu.longValue());
            }
            if (this.zzcv != null) {
                $i1 += zzawr.zzi(5, this.zzcv.longValue());
            }
            if (this.zzcw != null) {
                $i1 += zzawr.zzi(6, this.zzcw.longValue());
            }
            if (this.zzcx != null) {
                $i1 += zzawr.zzi(7, this.zzcx.longValue());
            }
            if (this.zzcy != null) {
                $i1 += zzawr.zzi(8, this.zzcy.longValue());
            }
            if (this.zzcz != null) {
                $i1 += zzawr.zzi(9, this.zzcz.longValue());
            }
            if (this.zzda != null) {
                $i1 += zzawr.zzi(10, this.zzda.longValue());
            }
            if (this.zzdb != null) {
                $i1 += zzawr.zzi(11, this.zzdb.longValue());
            }
            if (this.zzdc != null) {
                $i1 += zzawr.zzi(12, this.zzdc.longValue());
            }
            if (this.zzdd != null) {
                $i1 += zzawr.zzt(13, this.zzdd);
            }
            if (this.zzde != null) {
                $i1 += zzawr.zzi(14, this.zzde.longValue());
            }
            if (this.zzdf != null) {
                $i1 += zzawr.zzi(15, this.zzdf.longValue());
            }
            if (this.zzdg != null) {
                $i1 += zzawr.zzi(16, this.zzdg.longValue());
            }
            if (this.zzdh != null) {
                $i1 += zzawr.zzi(17, this.zzdh.longValue());
            }
            if (this.zzdi != null) {
                $i1 += zzawr.zzi(18, this.zzdi.longValue());
            }
            if (this.zzdj != null) {
                $i1 += zzawr.zzi(19, this.zzdj.longValue());
            }
            if (this.zzdk != null) {
                $i1 += zzawr.zzi(20, this.zzdk.longValue());
            }
            if (this.zzee != null) {
                $i1 += zzawr.zzi(21, this.zzee.longValue());
            }
            if (this.zzdl != null) {
                $i1 += zzawr.zzi(22, this.zzdl.longValue());
            }
            if (this.zzdm != null) {
                $i1 += zzawr.zzi(23, this.zzdm.longValue());
            }
            if (this.zzef != null) {
                $i1 += zzawr.zzt(24, this.zzef);
            }
            if (this.zzej != null) {
                $i1 += zzawr.zzi(25, this.zzej.longValue());
            }
            if (this.zzeg != null) {
                $i1 += zzawr.zzax(26, this.zzeg.intValue());
            }
            if (this.zzdn != null) {
                $i1 += zzawr.zzt(27, this.zzdn);
            }
            if (this.zzeh != null) {
                $i1 += zzawr.zzn(28, this.zzeh.booleanValue());
            }
            if (this.zzdo != null) {
                $i1 += zzawr.zzt(29, this.zzdo);
            }
            if (this.zzei != null) {
                $i1 += zzawr.zzt(30, this.zzei);
            }
            if (this.zzdp != null) {
                $i1 += zzawr.zzi(31, this.zzdp.longValue());
            }
            if (this.zzdq != null) {
                $i1 += zzawr.zzi(32, this.zzdq.longValue());
            }
            if (this.zzdr != null) {
                $i1 += zzawr.zzi(33, this.zzdr.longValue());
            }
            if (this.zzds != null) {
                $i1 += zzawr.zzt(34, this.zzds);
            }
            if (this.zzdt != null) {
                $i1 += zzawr.zzi(35, this.zzdt.longValue());
            }
            if (this.zzdu != null) {
                $i1 += zzawr.zzi(36, this.zzdu.longValue());
            }
            if (this.zzdv != null) {
                $i1 += zzawr.zzi(37, this.zzdv.longValue());
            }
            if (this.zzdw != null) {
                $i1 += zzawr.zzc(38, this.zzdw);
            }
            if (this.zzdx != null) {
                $i1 += zzawr.zzi(39, this.zzdx.longValue());
            }
            if (this.zzdy != null) {
                $i1 += zzawr.zzi(40, this.zzdy.longValue());
            }
            if (this.zzdz != null) {
                $i1 += zzawr.zzi(41, this.zzdz.longValue());
            }
            if (this.zzea != null) {
                $i1 += zzawr.zzi(42, this.zzea.longValue());
            }
            if (this.zzed != null && this.zzed.length > 0) {
                for (zzawz $r7 : this.zzed) {
                    if ($r7 != null) {
                        $i1 += zzawr.zzc(43, $r7);
                    }
                }
            }
            if (this.zzeb != null) {
                $i1 += zzawr.zzi(44, this.zzeb.longValue());
            }
            if (this.zzec != null) {
                $i1 += zzawr.zzi(45, this.zzec.longValue());
            }
            return this.zzek != null ? $i1 + zzawr.zzc(201, this.zzek) : $i1;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzb($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.zzcs != null) {
                $r1.zzs(1, this.zzcs);
            }
            if (this.zzcr != null) {
                $r1.zzs(2, this.zzcr);
            }
            if (this.zzct != null) {
                $r1.zzf(3, this.zzct.longValue());
            }
            if (this.zzcu != null) {
                $r1.zzf(4, this.zzcu.longValue());
            }
            if (this.zzcv != null) {
                $r1.zzf(5, this.zzcv.longValue());
            }
            if (this.zzcw != null) {
                $r1.zzf(6, this.zzcw.longValue());
            }
            if (this.zzcx != null) {
                $r1.zzf(7, this.zzcx.longValue());
            }
            if (this.zzcy != null) {
                $r1.zzf(8, this.zzcy.longValue());
            }
            if (this.zzcz != null) {
                $r1.zzf(9, this.zzcz.longValue());
            }
            if (this.zzda != null) {
                $r1.zzf(10, this.zzda.longValue());
            }
            if (this.zzdb != null) {
                $r1.zzf(11, this.zzdb.longValue());
            }
            if (this.zzdc != null) {
                $r1.zzf(12, this.zzdc.longValue());
            }
            if (this.zzdd != null) {
                $r1.zzs(13, this.zzdd);
            }
            if (this.zzde != null) {
                $r1.zzf(14, this.zzde.longValue());
            }
            if (this.zzdf != null) {
                $r1.zzf(15, this.zzdf.longValue());
            }
            if (this.zzdg != null) {
                $r1.zzf(16, this.zzdg.longValue());
            }
            if (this.zzdh != null) {
                $r1.zzf(17, this.zzdh.longValue());
            }
            if (this.zzdi != null) {
                $r1.zzf(18, this.zzdi.longValue());
            }
            if (this.zzdj != null) {
                $r1.zzf(19, this.zzdj.longValue());
            }
            if (this.zzdk != null) {
                $r1.zzf(20, this.zzdk.longValue());
            }
            if (this.zzee != null) {
                $r1.zzf(21, this.zzee.longValue());
            }
            if (this.zzdl != null) {
                $r1.zzf(22, this.zzdl.longValue());
            }
            if (this.zzdm != null) {
                $r1.zzf(23, this.zzdm.longValue());
            }
            if (this.zzef != null) {
                $r1.zzs(24, this.zzef);
            }
            if (this.zzej != null) {
                $r1.zzf(25, this.zzej.longValue());
            }
            if (this.zzeg != null) {
                $r1.zzav(26, this.zzeg.intValue());
            }
            if (this.zzdn != null) {
                $r1.zzs(27, this.zzdn);
            }
            if (this.zzeh != null) {
                $r1.zzm(28, this.zzeh.booleanValue());
            }
            if (this.zzdo != null) {
                $r1.zzs(29, this.zzdo);
            }
            if (this.zzei != null) {
                $r1.zzs(30, this.zzei);
            }
            if (this.zzdp != null) {
                $r1.zzf(31, this.zzdp.longValue());
            }
            if (this.zzdq != null) {
                $r1.zzf(32, this.zzdq.longValue());
            }
            if (this.zzdr != null) {
                $r1.zzf(33, this.zzdr.longValue());
            }
            if (this.zzds != null) {
                $r1.zzs(34, this.zzds);
            }
            if (this.zzdt != null) {
                $r1.zzf(35, this.zzdt.longValue());
            }
            if (this.zzdu != null) {
                $r1.zzf(36, this.zzdu.longValue());
            }
            if (this.zzdv != null) {
                $r1.zzf(37, this.zzdv.longValue());
            }
            if (this.zzdw != null) {
                $r1.zza(38, this.zzdw);
            }
            if (this.zzdx != null) {
                $r1.zzf(39, this.zzdx.longValue());
            }
            if (this.zzdy != null) {
                $r1.zzf(40, this.zzdy.longValue());
            }
            if (this.zzdz != null) {
                $r1.zzf(41, this.zzdz.longValue());
            }
            if (this.zzea != null) {
                $r1.zzf(42, this.zzea.longValue());
            }
            if (this.zzed != null && this.zzed.length > 0) {
                for (zzawz $r8 : this.zzed) {
                    if ($r8 != null) {
                        $r1.zza(43, $r8);
                    }
                }
            }
            if (this.zzeb != null) {
                $r1.zzf(44, this.zzeb.longValue());
            }
            if (this.zzec != null) {
                $r1.zzf(45, this.zzec.longValue());
            }
            if (this.zzek != null) {
                $r1.zza(201, this.zzek);
            }
            super.writeTo($r1);
        }

        public com.google.android.gms.internal.zzae.zza zzb(com.google.android.gms.internal.zzawq r19) throws java.io.IOException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:12:0x0045
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
            r18 = this;
        L_0x0000:
            r0 = r19;
            r2 = r0.ie();
            switch(r2) {
                case 0: goto L_0x0014;
                case 10: goto L_0x0015;
                case 18: goto L_0x0020;
                case 24: goto L_0x002b;
                case 32: goto L_0x003a;
                case 40: goto L_0x005d;
                case 48: goto L_0x0078;
                case 56: goto L_0x0087;
                case 64: goto L_0x0096;
                case 72: goto L_0x00a5;
                case 80: goto L_0x00b4;
                case 88: goto L_0x00c3;
                case 96: goto L_0x00d2;
                case 106: goto L_0x00e1;
                case 112: goto L_0x00ec;
                case 120: goto L_0x00ff;
                case 128: goto L_0x0112;
                case 136: goto L_0x0125;
                case 144: goto L_0x0138;
                case 152: goto L_0x014b;
                case 160: goto L_0x015e;
                case 168: goto L_0x0171;
                case 176: goto L_0x0184;
                case 184: goto L_0x0197;
                case 194: goto L_0x01aa;
                case 200: goto L_0x01b9;
                case 208: goto L_0x01cc;
                case 218: goto L_0x01e8;
                case 224: goto L_0x01f7;
                case 234: goto L_0x020a;
                case 242: goto L_0x0219;
                case 248: goto L_0x0228;
                case 256: goto L_0x023b;
                case 264: goto L_0x024e;
                case 274: goto L_0x0261;
                case 280: goto L_0x0270;
                case 288: goto L_0x0283;
                case 296: goto L_0x0296;
                case 306: goto L_0x02a9;
                case 312: goto L_0x02c6;
                case 320: goto L_0x02d9;
                case 328: goto L_0x02ec;
                case 336: goto L_0x02ff;
                case 346: goto L_0x0312;
                case 352: goto L_0x036f;
                case 360: goto L_0x0382;
                case 1610: goto L_0x0395;
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
            r0 = r19;
            r4 = r0.readString();
            r0 = r18;
            r0.zzcs = r4;
            goto L_0x0000;
        L_0x0020:
            r0 = r19;
            r4 = r0.readString();
            r0 = r18;
            r0.zzcr = r4;
            goto L_0x0000;
        L_0x002b:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            r0 = r18;
            r0.zzct = r7;
            goto L_0x0000;
        L_0x003a:
            r0 = r19;
            r5 = r0.ih();
            goto L_0x0044;
        L_0x0041:
            goto L_0x0000;
        L_0x0044:
            goto L_0x004c;
            goto L_0x0049;
        L_0x0046:
            goto L_0x0000;
        L_0x0049:
            goto L_0x0000;
        L_0x004c:
            r7 = java.lang.Long.valueOf(r5);
            r0 = r18;
            r0.zzcu = r7;
            goto L_0x0058;
        L_0x0055:
            goto L_0x0000;
        L_0x0058:
            goto L_0x0000;
            goto L_0x005d;
        L_0x005a:
            goto L_0x0000;
        L_0x005d:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x006b;
        L_0x0068:
            goto L_0x0000;
        L_0x006b:
            r0 = r18;
            r0.zzcv = r7;
            goto L_0x0073;
        L_0x0070:
            goto L_0x0000;
        L_0x0073:
            goto L_0x0000;
            goto L_0x0078;
        L_0x0075:
            goto L_0x0000;
        L_0x0078:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            r0 = r18;
            r0.zzcw = r7;
            goto L_0x0041;
        L_0x0087:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            r0 = r18;
            r0.zzcx = r7;
            goto L_0x0046;
        L_0x0096:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            r0 = r18;
            r0.zzcy = r7;
            goto L_0x0049;
        L_0x00a5:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            r0 = r18;
            r0.zzcz = r7;
            goto L_0x0055;
        L_0x00b4:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            r0 = r18;
            r0.zzda = r7;
            goto L_0x005a;
        L_0x00c3:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            r0 = r18;
            r0.zzdb = r7;
            goto L_0x0068;
        L_0x00d2:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            r0 = r18;
            r0.zzdc = r7;
            goto L_0x0070;
        L_0x00e1:
            r0 = r19;
            r4 = r0.readString();
            r0 = r18;
            r0.zzdd = r4;
            goto L_0x0075;
        L_0x00ec:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x00fa;
        L_0x00f7:
            goto L_0x0000;
        L_0x00fa:
            r0 = r18;
            r0.zzde = r7;
            goto L_0x00f7;
        L_0x00ff:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x010d;
        L_0x010a:
            goto L_0x0000;
        L_0x010d:
            r0 = r18;
            r0.zzdf = r7;
            goto L_0x010a;
        L_0x0112:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x0120;
        L_0x011d:
            goto L_0x0000;
        L_0x0120:
            r0 = r18;
            r0.zzdg = r7;
            goto L_0x011d;
        L_0x0125:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x0133;
        L_0x0130:
            goto L_0x0000;
        L_0x0133:
            r0 = r18;
            r0.zzdh = r7;
            goto L_0x0130;
        L_0x0138:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x0146;
        L_0x0143:
            goto L_0x0000;
        L_0x0146:
            r0 = r18;
            r0.zzdi = r7;
            goto L_0x0143;
        L_0x014b:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x0159;
        L_0x0156:
            goto L_0x0000;
        L_0x0159:
            r0 = r18;
            r0.zzdj = r7;
            goto L_0x0156;
        L_0x015e:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x016c;
        L_0x0169:
            goto L_0x0000;
        L_0x016c:
            r0 = r18;
            r0.zzdk = r7;
            goto L_0x0169;
        L_0x0171:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x017f;
        L_0x017c:
            goto L_0x0000;
        L_0x017f:
            r0 = r18;
            r0.zzee = r7;
            goto L_0x017c;
        L_0x0184:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x0192;
        L_0x018f:
            goto L_0x0000;
        L_0x0192:
            r0 = r18;
            r0.zzdl = r7;
            goto L_0x018f;
        L_0x0197:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x01a5;
        L_0x01a2:
            goto L_0x0000;
        L_0x01a5:
            r0 = r18;
            r0.zzdm = r7;
            goto L_0x01a2;
        L_0x01aa:
            r0 = r19;
            r4 = r0.readString();
            goto L_0x01b4;
        L_0x01b1:
            goto L_0x0000;
        L_0x01b4:
            r0 = r18;
            r0.zzef = r4;
            goto L_0x01b1;
        L_0x01b9:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x01c7;
        L_0x01c4:
            goto L_0x0000;
        L_0x01c7:
            r0 = r18;
            r0.zzej = r7;
            goto L_0x01c4;
        L_0x01cc:
            r0 = r19;
            r2 = r0.ii();
            goto L_0x01d6;
        L_0x01d3:
            goto L_0x0000;
        L_0x01d6:
            switch(r2) {
                case 0: goto L_0x01db;
                case 1: goto L_0x01db;
                case 2: goto L_0x01db;
                case 3: goto L_0x01db;
                case 4: goto L_0x01db;
                case 5: goto L_0x01db;
                case 6: goto L_0x01db;
                default: goto L_0x01d9;
            };
        L_0x01d9:
            goto L_0x01da;
        L_0x01da:
            goto L_0x01d3;
        L_0x01db:
            r8 = java.lang.Integer.valueOf(r2);
            goto L_0x01e3;
        L_0x01e0:
            goto L_0x0000;
        L_0x01e3:
            r0 = r18;
            r0.zzeg = r8;
            goto L_0x01e0;
        L_0x01e8:
            r0 = r19;
            r4 = r0.readString();
            goto L_0x01f2;
        L_0x01ef:
            goto L_0x0000;
        L_0x01f2:
            r0 = r18;
            r0.zzdn = r4;
            goto L_0x01ef;
        L_0x01f7:
            r0 = r19;
            r3 = r0.ik();
            r9 = java.lang.Boolean.valueOf(r3);
            goto L_0x0205;
        L_0x0202:
            goto L_0x0000;
        L_0x0205:
            r0 = r18;
            r0.zzeh = r9;
            goto L_0x0202;
        L_0x020a:
            r0 = r19;
            r4 = r0.readString();
            goto L_0x0214;
        L_0x0211:
            goto L_0x0000;
        L_0x0214:
            r0 = r18;
            r0.zzdo = r4;
            goto L_0x0211;
        L_0x0219:
            r0 = r19;
            r4 = r0.readString();
            goto L_0x0223;
        L_0x0220:
            goto L_0x0000;
        L_0x0223:
            r0 = r18;
            r0.zzei = r4;
            goto L_0x0220;
        L_0x0228:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x0236;
        L_0x0233:
            goto L_0x0000;
        L_0x0236:
            r0 = r18;
            r0.zzdp = r7;
            goto L_0x0233;
        L_0x023b:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x0249;
        L_0x0246:
            goto L_0x0000;
        L_0x0249:
            r0 = r18;
            r0.zzdq = r7;
            goto L_0x0246;
        L_0x024e:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x025c;
        L_0x0259:
            goto L_0x0000;
        L_0x025c:
            r0 = r18;
            r0.zzdr = r7;
            goto L_0x0259;
        L_0x0261:
            r0 = r19;
            r4 = r0.readString();
            goto L_0x026b;
        L_0x0268:
            goto L_0x0000;
        L_0x026b:
            r0 = r18;
            r0.zzds = r4;
            goto L_0x0268;
        L_0x0270:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x027e;
        L_0x027b:
            goto L_0x0000;
        L_0x027e:
            r0 = r18;
            r0.zzdt = r7;
            goto L_0x027b;
        L_0x0283:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x0291;
        L_0x028e:
            goto L_0x0000;
        L_0x0291:
            r0 = r18;
            r0.zzdu = r7;
            goto L_0x028e;
        L_0x0296:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x02a4;
        L_0x02a1:
            goto L_0x0000;
        L_0x02a4:
            r0 = r18;
            r0.zzdv = r7;
            goto L_0x02a1;
        L_0x02a9:
            r0 = r18;
            r10 = r0.zzdw;
            if (r10 != 0) goto L_0x02b8;
        L_0x02af:
            r10 = new com.google.android.gms.internal.zzae$zzb;
            r10.<init>();
            r0 = r18;
            r0.zzdw = r10;
        L_0x02b8:
            r0 = r18;
            r10 = r0.zzdw;
            goto L_0x02c0;
        L_0x02bd:
            goto L_0x0000;
        L_0x02c0:
            r0 = r19;
            r0.zza(r10);
            goto L_0x02bd;
        L_0x02c6:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x02d4;
        L_0x02d1:
            goto L_0x0000;
        L_0x02d4:
            r0 = r18;
            r0.zzdx = r7;
            goto L_0x02d1;
        L_0x02d9:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x02e7;
        L_0x02e4:
            goto L_0x0000;
        L_0x02e7:
            r0 = r18;
            r0.zzdy = r7;
            goto L_0x02e4;
        L_0x02ec:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x02fa;
        L_0x02f7:
            goto L_0x0000;
        L_0x02fa:
            r0 = r18;
            r0.zzdz = r7;
            goto L_0x02f7;
        L_0x02ff:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x030d;
        L_0x030a:
            goto L_0x0000;
        L_0x030d:
            r0 = r18;
            r0.zzea = r7;
            goto L_0x030a;
        L_0x0312:
            r12 = 346; // 0x15a float:4.85E-43 double:1.71E-321;
            r0 = r19;
            r11 = com.google.android.gms.internal.zzaxc.zzc(r0, r12);
            r0 = r18;
            r13 = r0.zzed;
            if (r13 != 0) goto L_0x034e;
        L_0x0320:
            r2 = 0;
        L_0x0321:
            r11 = r11 + r2;
            r13 = new com.google.android.gms.internal.zzae.zza.zza[r11];
            if (r2 == 0) goto L_0x032f;
        L_0x0326:
            r0 = r18;
            r14 = r0.zzed;
            r12 = 0;
            r15 = 0;
            java.lang.System.arraycopy(r14, r12, r13, r15, r2);
        L_0x032f:
            r11 = r13.length;
            r11 = r11 + -1;
            if (r2 >= r11) goto L_0x0354;
        L_0x0334:
            r16 = new com.google.android.gms.internal.zzae$zza$zza;
            r0 = r16;
            r0.<init>();
            r13[r2] = r16;
            r16 = r13[r2];
            r0 = r19;
            r1 = r16;
            r0.zza(r1);
            r0 = r19;
            r0.ie();
            r2 = r2 + 1;
            goto L_0x032f;
        L_0x034e:
            r0 = r18;
            r13 = r0.zzed;
            r2 = r13.length;
            goto L_0x0321;
        L_0x0354:
            r16 = new com.google.android.gms.internal.zzae$zza$zza;
            r0 = r16;
            r0.<init>();
            r13[r2] = r16;
            r16 = r13[r2];
            r0 = r19;
            r1 = r16;
            r0.zza(r1);
            goto L_0x036a;
        L_0x0367:
            goto L_0x0000;
        L_0x036a:
            r0 = r18;
            r0.zzed = r13;
            goto L_0x0367;
        L_0x036f:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x037d;
        L_0x037a:
            goto L_0x0000;
        L_0x037d:
            r0 = r18;
            r0.zzeb = r7;
            goto L_0x037a;
        L_0x0382:
            r0 = r19;
            r5 = r0.ih();
            r7 = java.lang.Long.valueOf(r5);
            goto L_0x0390;
        L_0x038d:
            goto L_0x0000;
        L_0x0390:
            r0 = r18;
            r0.zzec = r7;
            goto L_0x038d;
        L_0x0395:
            r0 = r18;
            r0 = r0.zzek;
            r17 = r0;
            if (r17 != 0) goto L_0x03aa;
        L_0x039d:
            r17 = new com.google.android.gms.internal.zzae$zze;
            r0 = r17;
            r0.<init>();
            r0 = r17;
            r1 = r18;
            r1.zzek = r0;
        L_0x03aa:
            r0 = r18;
            r0 = r0.zzek;
            r17 = r0;
            goto L_0x03b4;
        L_0x03b1:
            goto L_0x0000;
        L_0x03b4:
            r0 = r19;
            r1 = r17;
            r0.zza(r1);
            goto L_0x03b1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzae.zza.zzb(com.google.android.gms.internal.zzawq):com.google.android.gms.internal.zzae$zza");
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzb extends zzaws<zzb> {
        public Long zzem = null;
        public Integer zzen = null;
        public Boolean zzeo = null;
        public int[] zzep = zzaxc.cbE;

        protected int computeSerializedSize() throws  {
            int $i1 = super.computeSerializedSize();
            int $i2 = $i1;
            if (this.zzem != null) {
                $i2 = $i1 + zzawr.zzi(1, this.zzem.longValue());
            }
            if (this.zzen != null) {
                $i2 += zzawr.zzax(2, this.zzen.intValue());
            }
            if (this.zzeo != null) {
                $i2 += zzawr.zzn(3, this.zzeo.booleanValue());
            }
            if (this.zzep == null || this.zzep.length <= 0) {
                return $i2;
            }
            $i1 = 0;
            for (int $i4 : this.zzep) {
                $i1 += zzawr.zzasb($i4);
            }
            return ($i2 + $i1) + (this.zzep.length * 1);
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzd($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.zzem != null) {
                $r1.zzf(1, this.zzem.longValue());
            }
            if (this.zzen != null) {
                $r1.zzav(2, this.zzen.intValue());
            }
            if (this.zzeo != null) {
                $r1.zzm(3, this.zzeo.booleanValue());
            }
            if (this.zzep != null && this.zzep.length > 0) {
                for (int $i2 : this.zzep) {
                    $r1.zzav(4, $i2);
                }
            }
            super.writeTo($r1);
        }

        public zzb zzd(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                int $i2;
                int[] $r5;
                switch ($i0) {
                    case 0:
                        break;
                    case 8:
                        this.zzem = Long.valueOf($r1.ih());
                        continue;
                    case 16:
                        this.zzen = Integer.valueOf($r1.ii());
                        continue;
                    case 24:
                        this.zzeo = Boolean.valueOf($r1.ik());
                        continue;
                    case 32:
                        $i2 = zzaxc.zzc($r1, 32);
                        $i0 = this.zzep == null ? 0 : this.zzep.length;
                        $r5 = new int[($i2 + $i0)];
                        if ($i0 != 0) {
                            System.arraycopy(this.zzep, 0, $r5, 0, $i0);
                        }
                        while ($i0 < $r5.length - 1) {
                            $r5[$i0] = $r1.ii();
                            $r1.ie();
                            $i0++;
                        }
                        $r5[$i0] = $r1.ii();
                        this.zzep = $r5;
                        continue;
                    case 34:
                        $i0 = $r1.zzarv($r1.in());
                        $i2 = $r1.getPosition();
                        int $i3 = 0;
                        while ($r1.is() > 0) {
                            $r1.ii();
                            $i3++;
                        }
                        $r1.zzarx($i2);
                        $i2 = this.zzep == null ? 0 : this.zzep.length;
                        $r5 = new int[($i3 + $i2)];
                        if ($i2 != 0) {
                            System.arraycopy(this.zzep, 0, $r5, 0, $i2);
                        }
                        while ($i2 < $r5.length) {
                            $r5[$i2] = $r1.ii();
                            $i2++;
                        }
                        this.zzep = $r5;
                        $r1.zzarw($i0);
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
    public static final class zzc extends zzaws<zzc> {
        public byte[] zzeq = null;
        public byte[] zzer = null;

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (this.zzeq != null) {
                $i1 = $i0 + zzawr.zzb(1, this.zzeq);
            }
            return this.zzer != null ? $i1 + zzawr.zzb(2, this.zzer) : $i1;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zze($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.zzeq != null) {
                $r1.zza(1, this.zzeq);
            }
            if (this.zzer != null) {
                $r1.zza(2, this.zzer);
            }
            super.writeTo($r1);
        }

        public zzc zze(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 10:
                        this.zzeq = $r1.readBytes();
                        continue;
                    case 18:
                        this.zzer = $r1.readBytes();
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
        public byte[] data = null;
        public byte[] zzes = null;
        public byte[] zzet = null;
        public byte[] zzeu = null;

        public static zzd zzd(byte[] $r0) throws zzawy {
            return (zzd) zzawz.zza(new zzd(), $r0);
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (this.data != null) {
                $i1 = $i0 + zzawr.zzb(1, this.data);
            }
            if (this.zzes != null) {
                $i1 += zzawr.zzb(2, this.zzes);
            }
            if (this.zzet != null) {
                $i1 += zzawr.zzb(3, this.zzet);
            }
            return this.zzeu != null ? $i1 + zzawr.zzb(4, this.zzeu) : $i1;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzf($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.data != null) {
                $r1.zza(1, this.data);
            }
            if (this.zzes != null) {
                $r1.zza(2, this.zzes);
            }
            if (this.zzet != null) {
                $r1.zza(3, this.zzet);
            }
            if (this.zzeu != null) {
                $r1.zza(4, this.zzeu);
            }
            super.writeTo($r1);
        }

        public zzd zzf(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 10:
                        this.data = $r1.readBytes();
                        continue;
                    case 18:
                        this.zzes = $r1.readBytes();
                        continue;
                    case 26:
                        this.zzet = $r1.readBytes();
                        continue;
                    case 34:
                        this.zzeu = $r1.readBytes();
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
        public Long zzem = null;
        public String zzev = null;
        public byte[] zzew = null;

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (this.zzem != null) {
                $i1 = $i0 + zzawr.zzi(1, this.zzem.longValue());
            }
            if (this.zzev != null) {
                $i1 += zzawr.zzt(3, this.zzev);
            }
            return this.zzew != null ? $i1 + zzawr.zzb(4, this.zzew) : $i1;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzg($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.zzem != null) {
                $r1.zzf(1, this.zzem.longValue());
            }
            if (this.zzev != null) {
                $r1.zzs(3, this.zzev);
            }
            if (this.zzew != null) {
                $r1.zza(4, this.zzew);
            }
            super.writeTo($r1);
        }

        public zze zzg(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 8:
                        this.zzem = Long.valueOf($r1.ih());
                        continue;
                    case 26:
                        this.zzev = $r1.readString();
                        continue;
                    case 34:
                        this.zzew = $r1.readBytes();
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
        public byte[] zzes = null;
        public byte[][] zzex = zzaxc.cbK;
        public Integer zzey = null;
        public Integer zzez = null;

        protected int computeSerializedSize() throws  {
            int $i0;
            int $i1 = super.computeSerializedSize();
            if (this.zzex == null || this.zzex.length <= 0) {
                $i0 = $i1;
            } else {
                int $i3 = 0;
                int $i2 = 0;
                for (byte[] $r2 : this.zzex) {
                    if ($r2 != null) {
                        $i2++;
                        $i3 += zzawr.zzbl($r2);
                    }
                }
                $i0 = ($i1 + $i3) + ($i2 * 1);
            }
            if (this.zzes != null) {
                $i0 += zzawr.zzb(2, this.zzes);
            }
            if (this.zzey != null) {
                $i0 += zzawr.zzax(3, this.zzey.intValue());
            }
            return this.zzez != null ? $i0 + zzawr.zzax(4, this.zzez.intValue()) : $i0;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzh($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.zzex != null && this.zzex.length > 0) {
                for (byte[] $r3 : this.zzex) {
                    if ($r3 != null) {
                        $r1.zza(1, $r3);
                    }
                }
            }
            if (this.zzes != null) {
                $r1.zza(2, this.zzes);
            }
            if (this.zzey != null) {
                $r1.zzav(3, this.zzey.intValue());
            }
            if (this.zzez != null) {
                $r1.zzav(4, this.zzez.intValue());
            }
            super.writeTo($r1);
        }

        public zzf zzh(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 10:
                        int $i1 = zzaxc.zzc($r1, 10);
                        $i0 = this.zzex == null ? 0 : this.zzex.length;
                        byte[][] $r2 = new byte[($i1 + $i0)][];
                        if ($i0 != 0) {
                            System.arraycopy(this.zzex, 0, $r2, 0, $i0);
                        }
                        while ($i0 < $r2.length - 1) {
                            $r2[$i0] = $r1.readBytes();
                            $r1.ie();
                            $i0++;
                        }
                        $r2[$i0] = $r1.readBytes();
                        this.zzex = $r2;
                        continue;
                    case 18:
                        this.zzes = $r1.readBytes();
                        continue;
                    case 24:
                        $i0 = $r1.ii();
                        switch ($i0) {
                            case 0:
                            case 1:
                                this.zzey = Integer.valueOf($i0);
                                break;
                            default:
                                continue;
                        }
                    case 32:
                        $i0 = $r1.ii();
                        switch ($i0) {
                            case 0:
                            case 1:
                                this.zzez = Integer.valueOf($i0);
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
