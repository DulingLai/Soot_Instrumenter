package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Addresses;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzl implements Creator<Addresses> {
    static void zza(Addresses $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.aPa, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.aPb, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zza($r1, 5, $r0.aZU, true);
        }
        if ($r2.contains(Integer.valueOf(7))) {
            zzb.zza($r1, 7, $r0.aZT, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(8))) {
            zzb.zza($r1, 8, $r0.aPe, true);
        }
        if ($r2.contains(Integer.valueOf(9))) {
            zzb.zza($r1, 9, $r0.aPf, true);
        }
        if ($r2.contains(Integer.valueOf(10))) {
            zzb.zza($r1, 10, $r0.aPg, true);
        }
        if ($r2.contains(Integer.valueOf(11))) {
            zzb.zza($r1, 11, $r0.aPh, true);
        }
        if ($r2.contains(Integer.valueOf(12))) {
            zzb.zza($r1, 12, $r0.zzcft, true);
        }
        if ($r2.contains(Integer.valueOf(13))) {
            zzb.zza($r1, 13, $r0.mValue, true);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzvz($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzafc($i0);
    }

    public Addresses[] zzafc(int $i0) throws  {
        return new Addresses[$i0];
    }

    public com.google.android.gms.plus.service.v2whitelisted.models.Person.Addresses zzvz(android.os.Parcel r38) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:18:0x0094 in {5, 6, 7, 9, 11, 12, 14, 16, 17, 19, 26, 29, 32, 37, 39} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r37 = this;
        r0 = r38;
        r13 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r14 = new java.util.HashSet;
        r14.<init>();
        r15 = 0;
        r16 = 0;
        r17 = 0;
        r18 = 0;
        r19 = 0;
        r20 = 0;
        r21 = 0;
        r22 = 0;
        r23 = 0;
        r24 = 0;
        r25 = 0;
    L_0x0020:
        r0 = r38;
        r26 = r0.dataPosition();
        r0 = r26;
        if (r0 >= r13) goto L_0x015e;
    L_0x002a:
        r0 = r38;
        r26 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r26;
        r27 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r27) {
            case 1: goto L_0x0042;
            case 2: goto L_0x0058;
            case 3: goto L_0x0076;
            case 4: goto L_0x003a;
            case 5: goto L_0x0098;
            case 6: goto L_0x003a;
            case 7: goto L_0x00ae;
            case 8: goto L_0x00ce;
            case 9: goto L_0x00e4;
            case 10: goto L_0x00fa;
            case 11: goto L_0x0110;
            case 12: goto L_0x012a;
            case 13: goto L_0x0144;
            default: goto L_0x0039;
        };
    L_0x0039:
        goto L_0x003a;
    L_0x003a:
        r0 = r38;
        r1 = r26;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x0020;
    L_0x0042:
        r0 = r38;
        r1 = r26;
        r15 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r29 = 1;
        r0 = r29;
        r28 = java.lang.Integer.valueOf(r0);
        r0 = r28;
        r14.add(r0);
        goto L_0x0020;
    L_0x0058:
        r0 = r38;
        r1 = r26;
        r16 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0064;
    L_0x0061:
        goto L_0x0020;
    L_0x0064:
        r29 = 2;
        r0 = r29;
        r28 = java.lang.Integer.valueOf(r0);
        r0 = r28;
        r14.add(r0);
        goto L_0x0075;
    L_0x0072:
        goto L_0x0020;
    L_0x0075:
        goto L_0x0020;
    L_0x0076:
        r0 = r38;
        r1 = r26;
        r17 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0082;
    L_0x007f:
        goto L_0x0020;
    L_0x0082:
        r29 = 3;
        r0 = r29;
        r28 = java.lang.Integer.valueOf(r0);
        goto L_0x008e;
    L_0x008b:
        goto L_0x0020;
    L_0x008e:
        r0 = r28;
        r14.add(r0);
        goto L_0x0020;
        goto L_0x0098;
    L_0x0095:
        goto L_0x0020;
    L_0x0098:
        r0 = r38;
        r1 = r26;
        r18 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r29 = 5;
        r0 = r29;
        r28 = java.lang.Integer.valueOf(r0);
        r0 = r28;
        r14.add(r0);
        goto L_0x0061;
    L_0x00ae:
        r30 = com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata.CREATOR;
        r0 = r38;
        r1 = r26;
        r2 = r30;
        r31 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r29 = 7;
        r0 = r29;
        r28 = java.lang.Integer.valueOf(r0);
        r0 = r28;
        r14.add(r0);
        r32 = r31;
        r32 = (com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata) r32;
        r19 = r32;
        goto L_0x0072;
    L_0x00ce:
        r0 = r38;
        r1 = r26;
        r20 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r29 = 8;
        r0 = r29;
        r28 = java.lang.Integer.valueOf(r0);
        r0 = r28;
        r14.add(r0);
        goto L_0x007f;
    L_0x00e4:
        r0 = r38;
        r1 = r26;
        r21 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r29 = 9;
        r0 = r29;
        r28 = java.lang.Integer.valueOf(r0);
        r0 = r28;
        r14.add(r0);
        goto L_0x008b;
    L_0x00fa:
        r0 = r38;
        r1 = r26;
        r22 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r29 = 10;
        r0 = r29;
        r28 = java.lang.Integer.valueOf(r0);
        r0 = r28;
        r14.add(r0);
        goto L_0x0095;
    L_0x0110:
        r0 = r38;
        r1 = r26;
        r23 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r29 = 11;
        r0 = r29;
        r28 = java.lang.Integer.valueOf(r0);
        goto L_0x0124;
    L_0x0121:
        goto L_0x0020;
    L_0x0124:
        r0 = r28;
        r14.add(r0);
        goto L_0x0121;
    L_0x012a:
        r0 = r38;
        r1 = r26;
        r24 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r29 = 12;
        r0 = r29;
        r28 = java.lang.Integer.valueOf(r0);
        goto L_0x013e;
    L_0x013b:
        goto L_0x0020;
    L_0x013e:
        r0 = r28;
        r14.add(r0);
        goto L_0x013b;
    L_0x0144:
        r0 = r38;
        r1 = r26;
        r25 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r29 = 13;
        r0 = r29;
        r28 = java.lang.Integer.valueOf(r0);
        goto L_0x0158;
    L_0x0155:
        goto L_0x0020;
    L_0x0158:
        r0 = r28;
        r14.add(r0);
        goto L_0x0155;
    L_0x015e:
        r0 = r38;
        r26 = r0.dataPosition();
        r0 = r26;
        if (r0 == r13) goto L_0x0195;
    L_0x0168:
        r33 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r34 = new java.lang.StringBuilder;
        r29 = 37;
        r0 = r34;
        r1 = r29;
        r0.<init>(r1);
        r35 = "Overread allowed size end=";
        r0 = r34;
        r1 = r35;
        r34 = r0.append(r1);
        r0 = r34;
        r34 = r0.append(r13);
        r0 = r34;
        r16 = r0.toString();
        r0 = r33;
        r1 = r16;
        r2 = r38;
        r0.<init>(r1, r2);
        throw r33;
    L_0x0195:
        r36 = new com.google.android.gms.plus.service.v2whitelisted.models.Person$Addresses;
        r0 = r36;
        r1 = r14;
        r2 = r15;
        r3 = r16;
        r4 = r17;
        r5 = r18;
        r6 = r19;
        r7 = r20;
        r8 = r21;
        r9 = r22;
        r10 = r23;
        r11 = r24;
        r12 = r25;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12);
        return r36;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.zzl.zzvz(android.os.Parcel):com.google.android.gms.plus.service.v2whitelisted.models.Person$Addresses");
    }
}
