package com.google.android.gms.plus.internal.model.people;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.internal.model.people.PersonEntity.NameEntity;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzg implements Creator<NameEntity> {
    static void zza(NameEntity $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.fC, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.aPz, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.fB, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zza($r1, 5, $r0.aPA, true);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            zzb.zza($r1, 6, $r0.aPB, true);
        }
        if ($r2.contains(Integer.valueOf(7))) {
            zzb.zza($r1, 7, $r0.aPC, true);
        }
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzvk($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzaen($i0);
    }

    public NameEntity[] zzaen(int $i0) throws  {
        return new NameEntity[$i0];
    }

    public com.google.android.gms.plus.internal.model.people.PersonEntity.NameEntity zzvk(android.os.Parcel r27) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:13:0x006a in {6, 7, 9, 11, 12, 14, 16, 17, 25, 27} preds:[]
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
        r26 = this;
        r9 = 0;
        r0 = r27;
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
        r0 = r27;
        r18 = r0.dataPosition();
        r0 = r18;
        if (r0 >= r10) goto L_0x00e0;
    L_0x001e:
        r0 = r27;
        r18 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r18;
        r19 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r19) {
            case 1: goto L_0x0036;
            case 2: goto L_0x004c;
            case 3: goto L_0x006e;
            case 4: goto L_0x0088;
            case 5: goto L_0x009e;
            case 6: goto L_0x00b4;
            case 7: goto L_0x00ca;
            default: goto L_0x002d;
        };
    L_0x002d:
        goto L_0x002e;
    L_0x002e:
        r0 = r27;
        r1 = r18;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x0014;
    L_0x0036:
        r0 = r27;
        r1 = r18;
        r12 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r21 = 1;
        r0 = r21;
        r20 = java.lang.Integer.valueOf(r0);
        r0 = r20;
        r11.add(r0);
        goto L_0x0014;
    L_0x004c:
        r0 = r27;
        r1 = r18;
        r17 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0058;
    L_0x0055:
        goto L_0x0014;
    L_0x0058:
        r21 = 2;
        r0 = r21;
        r20 = java.lang.Integer.valueOf(r0);
        goto L_0x0064;
    L_0x0061:
        goto L_0x0014;
    L_0x0064:
        r0 = r20;
        r11.add(r0);
        goto L_0x0014;
        goto L_0x006e;
    L_0x006b:
        goto L_0x0014;
    L_0x006e:
        r0 = r27;
        r1 = r18;
        r16 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x007a;
    L_0x0077:
        goto L_0x0014;
    L_0x007a:
        r21 = 3;
        r0 = r21;
        r20 = java.lang.Integer.valueOf(r0);
        r0 = r20;
        r11.add(r0);
        goto L_0x0014;
    L_0x0088:
        r0 = r27;
        r1 = r18;
        r15 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r21 = 4;
        r0 = r21;
        r20 = java.lang.Integer.valueOf(r0);
        r0 = r20;
        r11.add(r0);
        goto L_0x0055;
    L_0x009e:
        r0 = r27;
        r1 = r18;
        r14 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r21 = 5;
        r0 = r21;
        r20 = java.lang.Integer.valueOf(r0);
        r0 = r20;
        r11.add(r0);
        goto L_0x0061;
    L_0x00b4:
        r0 = r27;
        r1 = r18;
        r13 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r21 = 6;
        r0 = r21;
        r20 = java.lang.Integer.valueOf(r0);
        r0 = r20;
        r11.add(r0);
        goto L_0x006b;
    L_0x00ca:
        r0 = r27;
        r1 = r18;
        r9 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r21 = 7;
        r0 = r21;
        r20 = java.lang.Integer.valueOf(r0);
        r0 = r20;
        r11.add(r0);
        goto L_0x0077;
    L_0x00e0:
        r0 = r27;
        r18 = r0.dataPosition();
        r0 = r18;
        if (r0 == r10) goto L_0x0115;
    L_0x00ea:
        r22 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r23 = new java.lang.StringBuilder;
        r21 = 37;
        r0 = r23;
        r1 = r21;
        r0.<init>(r1);
        r24 = "Overread allowed size end=";
        r0 = r23;
        r1 = r24;
        r23 = r0.append(r1);
        r0 = r23;
        r23 = r0.append(r10);
        r0 = r23;
        r9 = r0.toString();
        r0 = r22;
        r1 = r27;
        r0.<init>(r9, r1);
        throw r22;
    L_0x0115:
        r25 = new com.google.android.gms.plus.internal.model.people.PersonEntity$NameEntity;
        r0 = r25;
        r1 = r11;
        r2 = r12;
        r3 = r17;
        r4 = r16;
        r5 = r15;
        r6 = r14;
        r7 = r13;
        r8 = r9;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8);
        return r25;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.internal.model.people.zzg.zzvk(android.os.Parcel):com.google.android.gms.plus.internal.model.people.PersonEntity$NameEntity");
    }
}
