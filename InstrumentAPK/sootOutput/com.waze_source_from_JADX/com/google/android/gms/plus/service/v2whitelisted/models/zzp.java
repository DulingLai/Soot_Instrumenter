package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.ClientData;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzp implements Creator<ClientData> {
    static void zza(ClientData $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.zzayg, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.aZT, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.jw, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zza($r1, 5, $r0.mValue, true);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzwd($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzafg($i0);
    }

    public ClientData[] zzafg(int $i0) throws  {
        return new ClientData[$i0];
    }

    public com.google.android.gms.plus.service.v2whitelisted.models.Person.ClientData zzwd(android.os.Parcel r26) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:9:0x0054 in {6, 7, 8, 10, 11, 12, 17, 19} preds:[]
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
        r25 = this;
        r7 = 0;
        r0 = r26;
        r8 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r9 = new java.util.HashSet;
        r9.<init>();
        r10 = 0;
        r11 = 0;
        r12 = 0;
        r13 = 0;
    L_0x0010:
        r0 = r26;
        r14 = r0.dataPosition();
        if (r14 >= r8) goto L_0x009e;
    L_0x0018:
        r0 = r26;
        r14 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r15 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r14);
        switch(r15) {
            case 1: goto L_0x002c;
            case 2: goto L_0x0040;
            case 3: goto L_0x0058;
            case 4: goto L_0x0076;
            case 5: goto L_0x008a;
            default: goto L_0x0025;
        };
    L_0x0025:
        goto L_0x0026;
    L_0x0026:
        r0 = r26;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r14);
        goto L_0x0010;
    L_0x002c:
        r0 = r26;
        r10 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r14);
        r17 = 1;
        r0 = r17;
        r16 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r9.add(r0);
        goto L_0x0010;
    L_0x0040:
        r0 = r26;
        r13 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r14);
        r17 = 2;
        r0 = r17;
        r16 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r9.add(r0);
        goto L_0x0010;
        goto L_0x0058;
    L_0x0055:
        goto L_0x0010;
    L_0x0058:
        r18 = com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata.CREATOR;
        r0 = r26;
        r1 = r18;
        r19 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r14, r1);
        r17 = 3;
        r0 = r17;
        r16 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r9.add(r0);
        r20 = r19;
        r20 = (com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata) r20;
        r12 = r20;
        goto L_0x0010;
    L_0x0076:
        r0 = r26;
        r11 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r14);
        r17 = 4;
        r0 = r17;
        r16 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r9.add(r0);
        goto L_0x0010;
    L_0x008a:
        r0 = r26;
        r7 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r14);
        r17 = 5;
        r0 = r17;
        r16 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r9.add(r0);
        goto L_0x0055;
    L_0x009e:
        r0 = r26;
        r14 = r0.dataPosition();
        if (r14 == r8) goto L_0x00d1;
    L_0x00a6:
        r21 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r22 = new java.lang.StringBuilder;
        r17 = 37;
        r0 = r22;
        r1 = r17;
        r0.<init>(r1);
        r23 = "Overread allowed size end=";
        r0 = r22;
        r1 = r23;
        r22 = r0.append(r1);
        r0 = r22;
        r22 = r0.append(r8);
        r0 = r22;
        r7 = r0.toString();
        r0 = r21;
        r1 = r26;
        r0.<init>(r7, r1);
        throw r21;
    L_0x00d1:
        r24 = new com.google.android.gms.plus.service.v2whitelisted.models.Person$ClientData;
        r0 = r24;
        r1 = r9;
        r2 = r10;
        r3 = r13;
        r4 = r12;
        r5 = r11;
        r6 = r7;
        r0.<init>(r1, r2, r3, r4, r5, r6);
        return r24;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.zzp.zzwd(android.os.Parcel):com.google.android.gms.plus.service.v2whitelisted.models.Person$ClientData");
    }
}
