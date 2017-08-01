package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Names;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzak implements Creator<Names> {
    static void zza(Names $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.cr, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.fC, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.aPz, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zza($r1, 5, $r0.fB, true);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            zzb.zza($r1, 6, $r0.aPA, true);
        }
        if ($r2.contains(Integer.valueOf(7))) {
            zzb.zza($r1, 7, $r0.aPB, true);
        }
        if ($r2.contains(Integer.valueOf(8))) {
            zzb.zza($r1, 8, $r0.aZT, $i0, true);
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
        if ($r2.contains(Integer.valueOf(14))) {
            zzb.zza($r1, 14, $r0.bap, true);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzwy($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzagb($i0);
    }

    public Names[] zzagb(int $i0) throws  {
        return new Names[$i0];
    }

    public com.google.android.gms.plus.service.v2whitelisted.models.Person.Names zzwy(android.os.Parcel r44) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:8:0x0065
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r43 = this;
        r0 = r44;
        r16 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r17 = new java.util.HashSet;
        r0 = r17;
        r0.<init>();
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
        r30 = 0;
        r31 = 0;
    L_0x0029:
        r0 = r44;
        r32 = r0.dataPosition();
        r0 = r32;
        r1 = r16;
        if (r0 >= r1) goto L_0x01d7;
    L_0x0035:
        r0 = r44;
        r32 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r32;
        r33 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r33) {
            case 1: goto L_0x004d;
            case 2: goto L_0x0069;
            case 3: goto L_0x0089;
            case 4: goto L_0x00a9;
            case 5: goto L_0x00c1;
            case 6: goto L_0x00d9;
            case 7: goto L_0x00f5;
            case 8: goto L_0x010d;
            case 9: goto L_0x012f;
            case 10: goto L_0x014b;
            case 11: goto L_0x0167;
            case 12: goto L_0x0183;
            case 13: goto L_0x019f;
            case 14: goto L_0x01bb;
            default: goto L_0x0044;
        };
    L_0x0044:
        goto L_0x0045;
    L_0x0045:
        r0 = r44;
        r1 = r32;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x0029;
    L_0x004d:
        r0 = r44;
        r1 = r32;
        r18 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r35 = 1;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x0029;
        goto L_0x0069;
    L_0x0066:
        goto L_0x0029;
    L_0x0069:
        r0 = r44;
        r1 = r32;
        r19 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 2;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        goto L_0x007d;
    L_0x007a:
        goto L_0x0029;
    L_0x007d:
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x0029;
        goto L_0x0089;
    L_0x0086:
        goto L_0x0029;
    L_0x0089:
        r0 = r44;
        r1 = r32;
        r20 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0095;
    L_0x0092:
        goto L_0x0029;
    L_0x0095:
        r35 = 3;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x0029;
        goto L_0x00a9;
    L_0x00a6:
        goto L_0x0029;
    L_0x00a9:
        r0 = r44;
        r1 = r32;
        r21 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 4;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x0066;
    L_0x00c1:
        r0 = r44;
        r1 = r32;
        r22 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 5;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x007a;
    L_0x00d9:
        r0 = r44;
        r1 = r32;
        r23 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x00e5;
    L_0x00e2:
        goto L_0x00a6;
    L_0x00e5:
        r35 = 6;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x0086;
    L_0x00f5:
        r0 = r44;
        r1 = r32;
        r24 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 7;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x0092;
    L_0x010d:
        r36 = com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata.CREATOR;
        r0 = r44;
        r1 = r32;
        r2 = r36;
        r37 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r35 = 8;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        r38 = r37;
        r38 = (com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata) r38;
        r25 = r38;
        goto L_0x00e2;
    L_0x012f:
        r0 = r44;
        r1 = r32;
        r26 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 9;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        goto L_0x0143;
    L_0x0140:
        goto L_0x0029;
    L_0x0143:
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x0140;
    L_0x014b:
        r0 = r44;
        r1 = r32;
        r27 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 10;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        goto L_0x015f;
    L_0x015c:
        goto L_0x0029;
    L_0x015f:
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x015c;
    L_0x0167:
        r0 = r44;
        r1 = r32;
        r28 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 11;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        goto L_0x017b;
    L_0x0178:
        goto L_0x0029;
    L_0x017b:
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x0178;
    L_0x0183:
        r0 = r44;
        r1 = r32;
        r29 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 12;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        goto L_0x0197;
    L_0x0194:
        goto L_0x0029;
    L_0x0197:
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x0194;
    L_0x019f:
        r0 = r44;
        r1 = r32;
        r30 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 13;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        goto L_0x01b3;
    L_0x01b0:
        goto L_0x0029;
    L_0x01b3:
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x01b0;
    L_0x01bb:
        r0 = r44;
        r1 = r32;
        r31 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 14;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        goto L_0x01cf;
    L_0x01cc:
        goto L_0x0029;
    L_0x01cf:
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x01cc;
    L_0x01d7:
        r0 = r44;
        r32 = r0.dataPosition();
        r0 = r32;
        r1 = r16;
        if (r0 == r1) goto L_0x0212;
    L_0x01e3:
        r39 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r40 = new java.lang.StringBuilder;
        r35 = 37;
        r0 = r40;
        r1 = r35;
        r0.<init>(r1);
        r41 = "Overread allowed size end=";
        r0 = r40;
        r1 = r41;
        r40 = r0.append(r1);
        r0 = r40;
        r1 = r16;
        r40 = r0.append(r1);
        r0 = r40;
        r19 = r0.toString();
        r0 = r39;
        r1 = r19;
        r2 = r44;
        r0.<init>(r1, r2);
        throw r39;
    L_0x0212:
        r42 = new com.google.android.gms.plus.service.v2whitelisted.models.Person$Names;
        r0 = r42;
        r1 = r17;
        r2 = r18;
        r3 = r19;
        r4 = r20;
        r5 = r21;
        r6 = r22;
        r7 = r23;
        r8 = r24;
        r9 = r25;
        r10 = r26;
        r11 = r27;
        r12 = r28;
        r13 = r29;
        r14 = r30;
        r15 = r31;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15);
        return r42;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.zzak.zzwy(android.os.Parcel):com.google.android.gms.plus.service.v2whitelisted.models.Person$Names");
    }
}
