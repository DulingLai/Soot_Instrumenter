package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.SortKeys;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzau implements Creator<SortKeys> {
    static void zza(SortKeys $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zzc($r1, 2, $r0.aZD, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.baq, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.aQc, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zza($r1, 5, $r0.bar, true);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            zzb.zza($r1, 6, $r0.mName, true);
        }
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzxi($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzagl($i0);
    }

    public SortKeys[] zzagl(int $i0) throws  {
        return new SortKeys[$i0];
    }

    public com.google.android.gms.plus.service.v2whitelisted.models.Person.SortKeys zzxi(android.os.Parcel r26) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:13:0x006b in {6, 7, 9, 11, 12, 14, 15, 22, 24} preds:[]
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
        r25 = this;
        r8 = 0;
        r0 = r26;
        r9 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r10 = new java.util.HashSet;
        r10.<init>();
        r11 = 0;
        r12 = 0;
        r13 = 0;
        r14 = 0;
        r15 = 0;
    L_0x0011:
        r0 = r26;
        r16 = r0.dataPosition();
        r0 = r16;
        if (r0 >= r9) goto L_0x00c7;
    L_0x001b:
        r0 = r26;
        r16 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r16;
        r17 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r17) {
            case 1: goto L_0x0033;
            case 2: goto L_0x0049;
            case 3: goto L_0x006f;
            case 4: goto L_0x0085;
            case 5: goto L_0x009b;
            case 6: goto L_0x00b1;
            default: goto L_0x002a;
        };
    L_0x002a:
        goto L_0x002b;
    L_0x002b:
        r0 = r26;
        r1 = r16;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x0011;
    L_0x0033:
        r0 = r26;
        r1 = r16;
        r11 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r19 = 1;
        r0 = r19;
        r18 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r10.add(r0);
        goto L_0x0011;
    L_0x0049:
        r20 = com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeopleaffinities.CREATOR;
        goto L_0x004f;
    L_0x004c:
        goto L_0x0011;
    L_0x004f:
        r0 = r26;
        r1 = r16;
        r2 = r20;
        r15 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        goto L_0x005d;
    L_0x005a:
        goto L_0x0011;
    L_0x005d:
        r19 = 2;
        r0 = r19;
        r18 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r10.add(r0);
        goto L_0x0011;
        goto L_0x006f;
    L_0x006c:
        goto L_0x0011;
    L_0x006f:
        r0 = r26;
        r1 = r16;
        r14 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r19 = 3;
        r0 = r19;
        r18 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r10.add(r0);
        goto L_0x0011;
    L_0x0085:
        r0 = r26;
        r1 = r16;
        r13 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r19 = 4;
        r0 = r19;
        r18 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r10.add(r0);
        goto L_0x004c;
    L_0x009b:
        r0 = r26;
        r1 = r16;
        r12 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r19 = 5;
        r0 = r19;
        r18 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r10.add(r0);
        goto L_0x005a;
    L_0x00b1:
        r0 = r26;
        r1 = r16;
        r8 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r19 = 6;
        r0 = r19;
        r18 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r10.add(r0);
        goto L_0x006c;
    L_0x00c7:
        r0 = r26;
        r16 = r0.dataPosition();
        r0 = r16;
        if (r0 == r9) goto L_0x00fc;
    L_0x00d1:
        r21 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r22 = new java.lang.StringBuilder;
        r19 = 37;
        r0 = r22;
        r1 = r19;
        r0.<init>(r1);
        r23 = "Overread allowed size end=";
        r0 = r22;
        r1 = r23;
        r22 = r0.append(r1);
        r0 = r22;
        r22 = r0.append(r9);
        r0 = r22;
        r8 = r0.toString();
        r0 = r21;
        r1 = r26;
        r0.<init>(r8, r1);
        throw r21;
    L_0x00fc:
        r24 = new com.google.android.gms.plus.service.v2whitelisted.models.Person$SortKeys;
        r0 = r24;
        r1 = r10;
        r2 = r11;
        r3 = r15;
        r4 = r14;
        r5 = r13;
        r6 = r12;
        r7 = r8;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7);
        return r24;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.zzau.zzxi(android.os.Parcel):com.google.android.gms.plus.service.v2whitelisted.models.Person$SortKeys");
    }
}
