package com.google.android.gms.people.identity.internal.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.people.identity.internal.models.PersonImpl.NamesImpl;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzr implements Creator<NamesImpl> {
    static void zza(NamesImpl $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.aOZ, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.cr, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.fC, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zza($r1, 5, $r0.aPz, true);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            zzb.zza($r1, 6, $r0.fB, true);
        }
        if ($r2.contains(Integer.valueOf(7))) {
            zzb.zza($r1, 7, $r0.aPA, true);
        }
        if ($r2.contains(Integer.valueOf(8))) {
            zzb.zza($r1, 8, $r0.aPB, true);
        }
        if ($r2.contains(Integer.valueOf(9))) {
            zzb.zza($r1, 9, $r0.aPC, true);
        }
        if ($r2.contains(Integer.valueOf(10))) {
            zzb.zza($r1, 10, $r0.aPD, true);
        }
        if ($r2.contains(Integer.valueOf(11))) {
            zzb.zza($r1, 11, $r0.aPE, true);
        }
        if ($r2.contains(Integer.valueOf(12))) {
            zzb.zza($r1, 12, $r0.aPF, true);
        }
        if ($r2.contains(Integer.valueOf(13))) {
            zzb.zza($r1, 13, $r0.aPG, true);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzta($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzabn($i0);
    }

    public NamesImpl[] zzabn(int $i0) throws  {
        return new NamesImpl[$i0];
    }

    public com.google.android.gms.people.identity.internal.models.PersonImpl.NamesImpl zzta(android.os.Parcel r42) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:24:0x00e3 in {6, 7, 9, 11, 13, 15, 16, 18, 20, 30, 33, 36, 39, 42, 47, 49} preds:[]
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
        if (r0 >= r15) goto L_0x01bb;
    L_0x0031:
        r0 = r42;
        r30 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r30;
        r31 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r31) {
            case 1: goto L_0x0049;
            case 2: goto L_0x0061;
            case 3: goto L_0x0093;
            case 4: goto L_0x00b3;
            case 5: goto L_0x00cb;
            case 6: goto L_0x00e7;
            case 7: goto L_0x00ff;
            case 8: goto L_0x0117;
            case 9: goto L_0x012f;
            case 10: goto L_0x014b;
            case 11: goto L_0x0167;
            case 12: goto L_0x0183;
            case 13: goto L_0x019f;
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
        r34 = com.google.android.gms.people.identity.internal.models.PersonImpl.MetadataImpl.CREATOR;
        goto L_0x0067;
    L_0x0064:
        goto L_0x0027;
    L_0x0067:
        r0 = r42;
        r1 = r30;
        r2 = r34;
        r35 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        goto L_0x0075;
    L_0x0072:
        goto L_0x0027;
    L_0x0075:
        r33 = 2;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        goto L_0x0081;
    L_0x007e:
        goto L_0x0027;
    L_0x0081:
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        r36 = r35;
        r36 = (com.google.android.gms.people.identity.internal.models.PersonImpl.MetadataImpl) r36;
        r18 = r36;
        goto L_0x0092;
    L_0x008f:
        goto L_0x0027;
    L_0x0092:
        goto L_0x0027;
    L_0x0093:
        r0 = r42;
        r1 = r30;
        r19 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x009f;
    L_0x009c:
        goto L_0x0027;
    L_0x009f:
        r33 = 3;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        goto L_0x00ab;
    L_0x00a8:
        goto L_0x0027;
    L_0x00ab:
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x0064;
    L_0x00b3:
        r0 = r42;
        r1 = r30;
        r20 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r33 = 4;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x0072;
    L_0x00cb:
        r0 = r42;
        r1 = r30;
        r21 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r33 = 5;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x007e;
        goto L_0x00e7;
    L_0x00e4:
        goto L_0x00a8;
    L_0x00e7:
        r0 = r42;
        r1 = r30;
        r22 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r33 = 6;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x008f;
    L_0x00ff:
        r0 = r42;
        r1 = r30;
        r23 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r33 = 7;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x009c;
    L_0x0117:
        r0 = r42;
        r1 = r30;
        r24 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r33 = 8;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x00e4;
    L_0x012f:
        r0 = r42;
        r1 = r30;
        r25 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r33 = 9;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        goto L_0x0143;
    L_0x0140:
        goto L_0x0027;
    L_0x0143:
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x0140;
    L_0x014b:
        r0 = r42;
        r1 = r30;
        r26 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r33 = 10;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        goto L_0x015f;
    L_0x015c:
        goto L_0x0027;
    L_0x015f:
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x015c;
    L_0x0167:
        r0 = r42;
        r1 = r30;
        r27 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r33 = 11;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        goto L_0x017b;
    L_0x0178:
        goto L_0x0027;
    L_0x017b:
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x0178;
    L_0x0183:
        r0 = r42;
        r1 = r30;
        r28 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r33 = 12;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        goto L_0x0197;
    L_0x0194:
        goto L_0x0027;
    L_0x0197:
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x0194;
    L_0x019f:
        r0 = r42;
        r1 = r30;
        r29 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r33 = 13;
        r0 = r33;
        r32 = java.lang.Integer.valueOf(r0);
        goto L_0x01b3;
    L_0x01b0:
        goto L_0x0027;
    L_0x01b3:
        r0 = r16;
        r1 = r32;
        r0.add(r1);
        goto L_0x01b0;
    L_0x01bb:
        r0 = r42;
        r30 = r0.dataPosition();
        r0 = r30;
        if (r0 == r15) goto L_0x01f2;
    L_0x01c5:
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
    L_0x01f2:
        r40 = new com.google.android.gms.people.identity.internal.models.PersonImpl$NamesImpl;
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.people.identity.internal.models.zzr.zzta(android.os.Parcel):com.google.android.gms.people.identity.internal.models.PersonImpl$NamesImpl");
    }
}
