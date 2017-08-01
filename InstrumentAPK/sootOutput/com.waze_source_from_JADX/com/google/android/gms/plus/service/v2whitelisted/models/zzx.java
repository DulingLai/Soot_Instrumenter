package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.ExtendedData.HangoutsExtendedData;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzx implements Creator<HangoutsExtendedData> {
    static void zza(HangoutsExtendedData $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.bab, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.bac, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.bad);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zza($r1, 5, $r0.bae);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            zzb.zza($r1, 6, $r0.baf);
        }
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzwl($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzafo($i0);
    }

    public HangoutsExtendedData[] zzafo(int $i0) throws  {
        return new HangoutsExtendedData[$i0];
    }

    public com.google.android.gms.plus.service.v2whitelisted.models.Person.ExtendedData.HangoutsExtendedData zzwl(android.os.Parcel r25) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:11:0x0063 in {6, 7, 9, 10, 12, 13, 14, 20, 22} preds:[]
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
        r24 = this;
        r8 = 0;
        r9 = 0;
        r0 = r25;
        r10 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r11 = new java.util.HashSet;
        r11.<init>();
        r12 = 0;
        r13 = 0;
        r14 = 0;
        r15 = 0;
    L_0x0011:
        r0 = r25;
        r16 = r0.dataPosition();
        r0 = r16;
        if (r0 >= r10) goto L_0x00c0;
    L_0x001b:
        r0 = r25;
        r16 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r16;
        r17 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r17) {
            case 1: goto L_0x0033;
            case 2: goto L_0x0049;
            case 3: goto L_0x0067;
            case 4: goto L_0x007d;
            case 5: goto L_0x0094;
            case 6: goto L_0x00aa;
            default: goto L_0x002a;
        };
    L_0x002a:
        goto L_0x002b;
    L_0x002b:
        r0 = r25;
        r1 = r16;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x0011;
    L_0x0033:
        r0 = r25;
        r1 = r16;
        r15 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r19 = 1;
        r0 = r19;
        r18 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r11.add(r0);
        goto L_0x0011;
    L_0x0049:
        r0 = r25;
        r1 = r16;
        r14 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r19 = 2;
        r0 = r19;
        r18 = java.lang.Integer.valueOf(r0);
        goto L_0x005d;
    L_0x005a:
        goto L_0x0011;
    L_0x005d:
        r0 = r18;
        r11.add(r0);
        goto L_0x0011;
        goto L_0x0067;
    L_0x0064:
        goto L_0x0011;
    L_0x0067:
        r0 = r25;
        r1 = r16;
        r8 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r19 = 3;
        r0 = r19;
        r18 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r11.add(r0);
        goto L_0x0011;
    L_0x007d:
        r0 = r25;
        r1 = r16;
        r13 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r19 = 4;
        r0 = r19;
        r18 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r11.add(r0);
        goto L_0x0011;
    L_0x0094:
        r0 = r25;
        r1 = r16;
        r12 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r19 = 5;
        r0 = r19;
        r18 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r11.add(r0);
        goto L_0x005a;
    L_0x00aa:
        r0 = r25;
        r1 = r16;
        r9 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r19 = 6;
        r0 = r19;
        r18 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r11.add(r0);
        goto L_0x0064;
    L_0x00c0:
        r0 = r25;
        r16 = r0.dataPosition();
        r0 = r16;
        if (r0 == r10) goto L_0x00f5;
    L_0x00ca:
        r20 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r21 = new java.lang.StringBuilder;
        r19 = 37;
        r0 = r21;
        r1 = r19;
        r0.<init>(r1);
        r22 = "Overread allowed size end=";
        r0 = r21;
        r1 = r22;
        r21 = r0.append(r1);
        r0 = r21;
        r21 = r0.append(r10);
        r0 = r21;
        r8 = r0.toString();
        r0 = r20;
        r1 = r25;
        r0.<init>(r8, r1);
        throw r20;
    L_0x00f5:
        r23 = new com.google.android.gms.plus.service.v2whitelisted.models.Person$ExtendedData$HangoutsExtendedData;
        r0 = r23;
        r1 = r11;
        r2 = r15;
        r3 = r14;
        r4 = r8;
        r5 = r13;
        r6 = r12;
        r7 = r9;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7);
        return r23;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.zzx.zzwl(android.os.Parcel):com.google.android.gms.plus.service.v2whitelisted.models.Person$ExtendedData$HangoutsExtendedData");
    }
}
