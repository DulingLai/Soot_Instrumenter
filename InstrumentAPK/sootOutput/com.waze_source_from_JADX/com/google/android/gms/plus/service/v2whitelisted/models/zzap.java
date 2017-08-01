package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.PhoneNumbers;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzap implements Creator<PhoneNumbers> {
    static void zza(PhoneNumbers $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.aPZ, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.aPd, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zza($r1, 5, $r0.aZT, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            zzb.zza($r1, 6, $r0.zzcft, true);
        }
        if ($r2.contains(Integer.valueOf(8))) {
            zzb.zza($r1, 8, $r0.mValue, true);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzxd($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzagg($i0);
    }

    public PhoneNumbers[] zzagg(int $i0) throws  {
        return new PhoneNumbers[$i0];
    }

    public com.google.android.gms.plus.service.v2whitelisted.models.Person.PhoneNumbers zzxd(android.os.Parcel r28) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:13:0x0067 in {5, 6, 7, 9, 11, 12, 14, 15, 22, 24} preds:[]
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
        r27 = this;
        r8 = 0;
        r0 = r28;
        r9 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r10 = new java.util.HashSet;
        r10.<init>();
        r11 = 0;
        r12 = 0;
        r13 = 0;
        r14 = 0;
        r15 = 0;
    L_0x0011:
        r0 = r28;
        r16 = r0.dataPosition();
        r0 = r16;
        if (r0 >= r9) goto L_0x00cd;
    L_0x001b:
        r0 = r28;
        r16 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r16;
        r17 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r17) {
            case 1: goto L_0x0033;
            case 2: goto L_0x0049;
            case 3: goto L_0x002b;
            case 4: goto L_0x006b;
            case 5: goto L_0x0081;
            case 6: goto L_0x00a1;
            case 7: goto L_0x002b;
            case 8: goto L_0x00b7;
            default: goto L_0x002a;
        };
    L_0x002a:
        goto L_0x002b;
    L_0x002b:
        r0 = r28;
        r1 = r16;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x0011;
    L_0x0033:
        r0 = r28;
        r1 = r16;
        r11 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r19 = 1;
        r0 = r19;
        r18 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r10.add(r0);
        goto L_0x0011;
    L_0x0049:
        r0 = r28;
        r1 = r16;
        r15 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0055;
    L_0x0052:
        goto L_0x0011;
    L_0x0055:
        r19 = 2;
        r0 = r19;
        r18 = java.lang.Integer.valueOf(r0);
        goto L_0x0061;
    L_0x005e:
        goto L_0x0011;
    L_0x0061:
        r0 = r18;
        r10.add(r0);
        goto L_0x0011;
        goto L_0x006b;
    L_0x0068:
        goto L_0x0011;
    L_0x006b:
        r0 = r28;
        r1 = r16;
        r14 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r19 = 4;
        r0 = r19;
        r18 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r10.add(r0);
        goto L_0x0011;
    L_0x0081:
        r20 = com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata.CREATOR;
        r0 = r28;
        r1 = r16;
        r2 = r20;
        r21 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r19 = 5;
        r0 = r19;
        r18 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r10.add(r0);
        r22 = r21;
        r22 = (com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata) r22;
        r13 = r22;
        goto L_0x0052;
    L_0x00a1:
        r0 = r28;
        r1 = r16;
        r12 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r19 = 6;
        r0 = r19;
        r18 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r10.add(r0);
        goto L_0x005e;
    L_0x00b7:
        r0 = r28;
        r1 = r16;
        r8 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r19 = 8;
        r0 = r19;
        r18 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r10.add(r0);
        goto L_0x0068;
    L_0x00cd:
        r0 = r28;
        r16 = r0.dataPosition();
        r0 = r16;
        if (r0 == r9) goto L_0x0102;
    L_0x00d7:
        r23 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r24 = new java.lang.StringBuilder;
        r19 = 37;
        r0 = r24;
        r1 = r19;
        r0.<init>(r1);
        r25 = "Overread allowed size end=";
        r0 = r24;
        r1 = r25;
        r24 = r0.append(r1);
        r0 = r24;
        r24 = r0.append(r9);
        r0 = r24;
        r8 = r0.toString();
        r0 = r23;
        r1 = r28;
        r0.<init>(r8, r1);
        throw r23;
    L_0x0102:
        r26 = new com.google.android.gms.plus.service.v2whitelisted.models.Person$PhoneNumbers;
        r0 = r26;
        r1 = r10;
        r2 = r11;
        r3 = r15;
        r4 = r14;
        r5 = r13;
        r6 = r12;
        r7 = r8;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7);
        return r26;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.zzap.zzxd(android.os.Parcel):com.google.android.gms.plus.service.v2whitelisted.models.Person$PhoneNumbers");
    }
}
