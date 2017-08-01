package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.SourceIds;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzai implements Creator<SourceIds> {
    static void zza(SourceIds $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.aPr, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.aPX);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.aOD, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zza($r1, 5, $r0.zzbgd, true);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            zzb.zza($r1, 6, $r0.ban, true);
        }
        if ($r2.contains(Integer.valueOf(7))) {
            zzb.zza($r1, 7, $r0.bao);
        }
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzww($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzafz($i0);
    }

    public SourceIds[] zzafz(int $i0) throws  {
        return new SourceIds[$i0];
    }

    public com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.SourceIds zzww(android.os.Parcel r29) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:13:0x006d in {6, 7, 9, 11, 12, 14, 16, 17, 25, 27} preds:[]
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
        r28 = this;
        r10 = 0;
        r11 = 0;
        r0 = r29;
        r12 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r13 = new java.util.HashSet;
        r13.<init>();
        r14 = 0;
        r16 = 0;
        r17 = 0;
        r18 = 0;
        r19 = 0;
    L_0x0017:
        r0 = r29;
        r20 = r0.dataPosition();
        r0 = r20;
        if (r0 >= r12) goto L_0x00e3;
    L_0x0021:
        r0 = r29;
        r20 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r20;
        r21 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r21) {
            case 1: goto L_0x0039;
            case 2: goto L_0x004f;
            case 3: goto L_0x0071;
            case 4: goto L_0x008b;
            case 5: goto L_0x00a1;
            case 6: goto L_0x00b7;
            case 7: goto L_0x00cd;
            default: goto L_0x0030;
        };
    L_0x0030:
        goto L_0x0031;
    L_0x0031:
        r0 = r29;
        r1 = r20;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x0017;
    L_0x0039:
        r0 = r29;
        r1 = r20;
        r19 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r23 = 1;
        r0 = r23;
        r22 = java.lang.Integer.valueOf(r0);
        r0 = r22;
        r13.add(r0);
        goto L_0x0017;
    L_0x004f:
        r0 = r29;
        r1 = r20;
        r18 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x005b;
    L_0x0058:
        goto L_0x0017;
    L_0x005b:
        r23 = 2;
        r0 = r23;
        r22 = java.lang.Integer.valueOf(r0);
        goto L_0x0067;
    L_0x0064:
        goto L_0x0017;
    L_0x0067:
        r0 = r22;
        r13.add(r0);
        goto L_0x0017;
        goto L_0x0071;
    L_0x006e:
        goto L_0x0017;
    L_0x0071:
        r0 = r29;
        r1 = r20;
        r10 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x007d;
    L_0x007a:
        goto L_0x0017;
    L_0x007d:
        r23 = 3;
        r0 = r23;
        r22 = java.lang.Integer.valueOf(r0);
        r0 = r22;
        r13.add(r0);
        goto L_0x0017;
    L_0x008b:
        r0 = r29;
        r1 = r20;
        r17 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r23 = 4;
        r0 = r23;
        r22 = java.lang.Integer.valueOf(r0);
        r0 = r22;
        r13.add(r0);
        goto L_0x0058;
    L_0x00a1:
        r0 = r29;
        r1 = r20;
        r16 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r23 = 5;
        r0 = r23;
        r22 = java.lang.Integer.valueOf(r0);
        r0 = r22;
        r13.add(r0);
        goto L_0x0064;
    L_0x00b7:
        r0 = r29;
        r1 = r20;
        r11 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r23 = 6;
        r0 = r23;
        r22 = java.lang.Integer.valueOf(r0);
        r0 = r22;
        r13.add(r0);
        goto L_0x006e;
    L_0x00cd:
        r0 = r29;
        r1 = r20;
        r14 = com.google.android.gms.common.internal.safeparcel.zza.zzi(r0, r1);
        r23 = 7;
        r0 = r23;
        r22 = java.lang.Integer.valueOf(r0);
        r0 = r22;
        r13.add(r0);
        goto L_0x007a;
    L_0x00e3:
        r0 = r29;
        r20 = r0.dataPosition();
        r0 = r20;
        if (r0 == r12) goto L_0x0118;
    L_0x00ed:
        r24 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r25 = new java.lang.StringBuilder;
        r23 = 37;
        r0 = r25;
        r1 = r23;
        r0.<init>(r1);
        r26 = "Overread allowed size end=";
        r0 = r25;
        r1 = r26;
        r25 = r0.append(r1);
        r0 = r25;
        r25 = r0.append(r12);
        r0 = r25;
        r11 = r0.toString();
        r0 = r24;
        r1 = r29;
        r0.<init>(r11, r1);
        throw r24;
    L_0x0118:
        r27 = new com.google.android.gms.plus.service.v2whitelisted.models.Person$Metadata$IdentityInfo$SourceIds;
        r0 = r27;
        r1 = r13;
        r2 = r19;
        r3 = r18;
        r4 = r10;
        r5 = r17;
        r6 = r16;
        r7 = r11;
        r8 = r14;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8);
        return r27;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.zzai.zzww(android.os.Parcel):com.google.android.gms.plus.service.v2whitelisted.models.Person$Metadata$IdentityInfo$SourceIds");
    }
}
