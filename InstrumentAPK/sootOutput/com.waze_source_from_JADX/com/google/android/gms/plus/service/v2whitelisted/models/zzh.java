package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzh implements Creator<Mergedpeoplemetadata> {
    static void zza(Mergedpeoplemetadata $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zzc($r1, 2, $r0.aZD, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.aPr, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.aPs, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zza($r1, 5, $r0.aPt, true);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            zzb.zza($r1, 6, $r0.aZE);
        }
        if ($r2.contains(Integer.valueOf(7))) {
            zzb.zza($r1, 7, $r0.aPv);
        }
        if ($r2.contains(Integer.valueOf(8))) {
            zzb.zzc($r1, 8, $r0.aZF, true);
        }
        if ($r2.contains(Integer.valueOf(9))) {
            zzb.zza($r1, 9, $r0.aZG, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(10))) {
            zzb.zza($r1, 10, $r0.aMF);
        }
        if ($r2.contains(Integer.valueOf(11))) {
            zzb.zza($r1, 11, $r0.aPw);
        }
        if ($r2.contains(Integer.valueOf(12))) {
            zzb.zza($r1, 12, $r0.aPu, true);
        }
        if ($r2.contains(Integer.valueOf(13))) {
            zzb.zza($r1, 13, $r0.aPx);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzvv($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzaey($i0);
    }

    public Mergedpeoplemetadata[] zzaey(int $i0) throws  {
        return new Mergedpeoplemetadata[$i0];
    }

    public com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata zzvv(android.os.Parcel r42) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:13:0x0085 in {6, 7, 9, 11, 12, 14, 16, 18, 19, 26, 29, 32, 35, 38, 43, 45} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r41 = this;
        r0 = r42;
        r15 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r16 = new java.util.HashSet;
        r0 = r16;
        r0.<init>();
        r17 = 0;
        r18 = 0;
        r19 = 0;
        r20 = 0;
        r21 = 0;
        r22 = 0;
        r23 = 0;
        r24 = 0;
        r25 = 0;
        r26 = 0;
        r27 = 0;
        r28 = 0;
        r29 = 0;
    L_0x0027:
        r0 = r42;
        r30 = r0.dataPosition();
        r0 = r30;
        if (r0 >= r15) goto L_0x01bc;
    L_0x0031:
        r0 = r42;
        r30 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r30;
        r31 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r31) {
            case 1: goto L_0x0049;
            case 2: goto L_0x0061;
            case 3: goto L_0x0089;
            case 4: goto L_0x00aa;
            case 5: goto L_0x00c2;
            case 6: goto L_0x00da;
            case 7: goto L_0x00f2;
            case 8: goto L_0x010a;
            case 9: goto L_0x0126;
            case 10: goto L_0x014c;
            case 11: goto L_0x0168;
            case 12: goto L_0x0184;
            case 13: goto L_0x01a0;
            default: goto L_0x0040;
        };
    L_0x0040:
        goto L_0x0041;
    L_0x0041:
        r0 = r42;
        r1 = r30;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x0027;
    L_0x0049:
        r0 = r42;
        r1 = r30;
        r17 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r33 = 1;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x0027;
    L_0x0061:
        r34 = com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeopleaffinities.CREATOR;
        goto L_0x0067;
    L_0x0064:
        goto L_0x0027;
    L_0x0067:
        r0 = r42;
        r1 = r30;
        r2 = r34;
        r18 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        goto L_0x0075;
    L_0x0072:
        goto L_0x0027;
    L_0x0075:
        r33 = 2;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x0027;
        goto L_0x0089;
    L_0x0086:
        goto L_0x0027;
    L_0x0089:
        r0 = r42;
        r1 = r30;
        r19 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0095;
    L_0x0092:
        goto L_0x0027;
    L_0x0095:
        r33 = 3;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x00a8;
    L_0x00a5:
        goto L_0x0027;
    L_0x00a8:
        goto L_0x0027;
    L_0x00aa:
        r0 = r42;
        r1 = r30;
        r20 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r33 = 4;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x0064;
    L_0x00c2:
        r0 = r42;
        r1 = r30;
        r21 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r33 = 5;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x0072;
    L_0x00da:
        r0 = r42;
        r1 = r30;
        r22 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r33 = 6;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x0086;
    L_0x00f2:
        r0 = r42;
        r1 = r30;
        r23 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r33 = 7;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x0092;
    L_0x010a:
        r34 = com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata.EdgeKeyInfo.CREATOR;
        r0 = r42;
        r1 = r30;
        r2 = r34;
        r24 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r33 = 8;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x00a5;
    L_0x0126:
        r34 = com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplefieldacl.CREATOR;
        r0 = r42;
        r1 = r30;
        r2 = r34;
        r35 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r33 = 9;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x0145;
    L_0x0142:
        goto L_0x0027;
    L_0x0145:
        r36 = r35;
        r36 = (com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplefieldacl) r36;
        r25 = r36;
        goto L_0x0142;
    L_0x014c:
        r0 = r42;
        r1 = r30;
        r26 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r33 = 10;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        goto L_0x0160;
    L_0x015d:
        goto L_0x0027;
    L_0x0160:
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x015d;
    L_0x0168:
        r0 = r42;
        r1 = r30;
        r27 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r33 = 11;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        goto L_0x017c;
    L_0x0179:
        goto L_0x0027;
    L_0x017c:
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x0179;
    L_0x0184:
        r0 = r42;
        r1 = r30;
        r28 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r33 = 12;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        goto L_0x0198;
    L_0x0195:
        goto L_0x0027;
    L_0x0198:
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x0195;
    L_0x01a0:
        r0 = r42;
        r1 = r30;
        r29 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r33 = 13;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        goto L_0x01b4;
    L_0x01b1:
        goto L_0x0027;
    L_0x01b4:
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x01b1;
    L_0x01bc:
        r0 = r42;
        r30 = r0.dataPosition();
        r0 = r30;
        if (r0 == r15) goto L_0x01f3;
    L_0x01c6:
        r37 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r38 = new java.lang.StringBuilder;
        r33 = 37;
        r0 = r38;
        r1 = r33;
        r0.<init>(r1);
        r39 = "Overread allowed size end=";
        r0 = r38;
        r1 = r39;
        r38 = r0.append(r1);
        r0 = r38;
        r38 = r0.append(r15);
        r0 = r38;
        r19 = r0.toString();
        r0 = r37;
        r1 = r19;
        r2 = r42;
        r0.<init>(r1, r2);
        throw r37;
    L_0x01f3:
        r40 = new com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata;
        r0 = r40;
        r1 = r16;
        r2 = r17;
        r3 = r18;
        r4 = r19;
        r5 = r20;
        r6 = r21;
        r7 = r22;
        r8 = r23;
        r9 = r24;
        r10 = r25;
        r11 = r26;
        r12 = r27;
        r13 = r28;
        r14 = r29;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14);
        return r40;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.zzh.zzvv(android.os.Parcel):com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata");
    }
}
