package com.google.android.gms.people.identity.internal.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.people.identity.internal.models.PersonImpl.InstantMessagingImpl;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzn implements Creator<InstantMessagingImpl> {
    static void zza(InstantMessagingImpl $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.aOZ, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.aPl, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.aPd, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zza($r1, 5, $r0.aPm, true);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            zzb.zza($r1, 6, $r0.zzcft, true);
        }
        if ($r2.contains(Integer.valueOf(7))) {
            zzb.zza($r1, 7, $r0.mValue, true);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzsw($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzabj($i0);
    }

    public InstantMessagingImpl[] zzabj(int $i0) throws  {
        return new InstantMessagingImpl[$i0];
    }

    public com.google.android.gms.people.identity.internal.models.PersonImpl.InstantMessagingImpl zzsw(android.os.Parcel r30) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:15:0x0078 in {6, 7, 9, 11, 13, 14, 16, 17, 25, 27} preds:[]
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
        r29 = this;
        r9 = 0;
        r0 = r30;
        r10 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r11 = new java.util.HashSet;
        r11.<init>();
        r12 = 0;
        r13 = 0;
        r14 = 0;
        r15 = 0;
        r16 = 0;
        r17 = 0;
    L_0x0014:
        r0 = r30;
        r18 = r0.dataPosition();
        r0 = r18;
        if (r0 >= r10) goto L_0x00ea;
    L_0x001e:
        r0 = r30;
        r18 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r18;
        r19 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r19) {
            case 1: goto L_0x0036;
            case 2: goto L_0x004c;
            case 3: goto L_0x007c;
            case 4: goto L_0x0092;
            case 5: goto L_0x00a8;
            case 6: goto L_0x00be;
            case 7: goto L_0x00d4;
            default: goto L_0x002d;
        };
    L_0x002d:
        goto L_0x002e;
    L_0x002e:
        r0 = r30;
        r1 = r18;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x0014;
    L_0x0036:
        r0 = r30;
        r1 = r18;
        r12 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r21 = 1;
        r0 = r21;
        r20 = java.lang.Integer.valueOf(r0);
        r0 = r20;
        r11.add(r0);
        goto L_0x0014;
    L_0x004c:
        r22 = com.google.android.gms.people.identity.internal.models.PersonImpl.MetadataImpl.CREATOR;
        goto L_0x0052;
    L_0x004f:
        goto L_0x0014;
    L_0x0052:
        r0 = r30;
        r1 = r18;
        r2 = r22;
        r23 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        goto L_0x0060;
    L_0x005d:
        goto L_0x0014;
    L_0x0060:
        r21 = 2;
        r0 = r21;
        r20 = java.lang.Integer.valueOf(r0);
        r0 = r20;
        r11.add(r0);
        goto L_0x0071;
    L_0x006e:
        goto L_0x0014;
    L_0x0071:
        r24 = r23;
        r24 = (com.google.android.gms.people.identity.internal.models.PersonImpl.MetadataImpl) r24;
        r17 = r24;
        goto L_0x0014;
        goto L_0x007c;
    L_0x0079:
        goto L_0x0014;
    L_0x007c:
        r0 = r30;
        r1 = r18;
        r16 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r21 = 3;
        r0 = r21;
        r20 = java.lang.Integer.valueOf(r0);
        r0 = r20;
        r11.add(r0);
        goto L_0x0014;
    L_0x0092:
        r0 = r30;
        r1 = r18;
        r15 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r21 = 4;
        r0 = r21;
        r20 = java.lang.Integer.valueOf(r0);
        r0 = r20;
        r11.add(r0);
        goto L_0x004f;
    L_0x00a8:
        r0 = r30;
        r1 = r18;
        r14 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r21 = 5;
        r0 = r21;
        r20 = java.lang.Integer.valueOf(r0);
        r0 = r20;
        r11.add(r0);
        goto L_0x005d;
    L_0x00be:
        r0 = r30;
        r1 = r18;
        r13 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r21 = 6;
        r0 = r21;
        r20 = java.lang.Integer.valueOf(r0);
        r0 = r20;
        r11.add(r0);
        goto L_0x006e;
    L_0x00d4:
        r0 = r30;
        r1 = r18;
        r9 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r21 = 7;
        r0 = r21;
        r20 = java.lang.Integer.valueOf(r0);
        r0 = r20;
        r11.add(r0);
        goto L_0x0079;
    L_0x00ea:
        r0 = r30;
        r18 = r0.dataPosition();
        r0 = r18;
        if (r0 == r10) goto L_0x011f;
    L_0x00f4:
        r25 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r26 = new java.lang.StringBuilder;
        r21 = 37;
        r0 = r26;
        r1 = r21;
        r0.<init>(r1);
        r27 = "Overread allowed size end=";
        r0 = r26;
        r1 = r27;
        r26 = r0.append(r1);
        r0 = r26;
        r26 = r0.append(r10);
        r0 = r26;
        r9 = r0.toString();
        r0 = r25;
        r1 = r30;
        r0.<init>(r9, r1);
        throw r25;
    L_0x011f:
        r28 = new com.google.android.gms.people.identity.internal.models.PersonImpl$InstantMessagingImpl;
        r0 = r28;
        r1 = r11;
        r2 = r12;
        r3 = r17;
        r4 = r16;
        r5 = r15;
        r6 = r14;
        r7 = r13;
        r8 = r9;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8);
        return r28;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.people.identity.internal.models.zzn.zzsw(android.os.Parcel):com.google.android.gms.people.identity.internal.models.PersonImpl$InstantMessagingImpl");
    }
}
