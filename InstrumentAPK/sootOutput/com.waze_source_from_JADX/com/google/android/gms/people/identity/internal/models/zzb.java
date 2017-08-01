package com.google.android.gms.people.identity.internal.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.people.identity.internal.models.PersonImpl.AddressesImpl;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb implements Creator<AddressesImpl> {
    static void zza(AddressesImpl $r0, Parcel $r1, int $i0) throws  {
        int $i1 = com.google.android.gms.common.internal.safeparcel.zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 2, $r0.aOZ, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 3, $r0.aPa, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 4, $r0.aPb, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 5, $r0.aPc, true);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 6, $r0.aPd, true);
        }
        if ($r2.contains(Integer.valueOf(7))) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 7, $r0.aPe, true);
        }
        if ($r2.contains(Integer.valueOf(8))) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 8, $r0.aPf, true);
        }
        if ($r2.contains(Integer.valueOf(9))) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 9, $r0.aPg, true);
        }
        if ($r2.contains(Integer.valueOf(10))) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 10, $r0.aPh, true);
        }
        if ($r2.contains(Integer.valueOf(11))) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 11, $r0.zzcft, true);
        }
        if ($r2.contains(Integer.valueOf(12))) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 12, $r0.mValue, true);
        }
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzsm($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzaay($i0);
    }

    public AddressesImpl[] zzaay(int $i0) throws  {
        return new AddressesImpl[$i0];
    }

    public com.google.android.gms.people.identity.internal.models.PersonImpl.AddressesImpl zzsm(android.os.Parcel r40) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:15:0x0087 in {6, 7, 9, 11, 13, 14, 16, 18, 19, 26, 29, 32, 35, 40, 42} preds:[]
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
        r39 = this;
        r0 = r40;
        r14 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r15 = new java.util.HashSet;
        r15.<init>();
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
        r26 = 0;
        r27 = 0;
    L_0x0023:
        r0 = r40;
        r28 = r0.dataPosition();
        r0 = r28;
        if (r0 >= r14) goto L_0x017c;
    L_0x002d:
        r0 = r40;
        r28 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r28;
        r29 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r29) {
            case 1: goto L_0x0045;
            case 2: goto L_0x005b;
            case 3: goto L_0x008b;
            case 4: goto L_0x00a6;
            case 5: goto L_0x00bc;
            case 6: goto L_0x00d2;
            case 7: goto L_0x00e8;
            case 8: goto L_0x00fe;
            case 9: goto L_0x0114;
            case 10: goto L_0x012e;
            case 11: goto L_0x0148;
            case 12: goto L_0x0162;
            default: goto L_0x003c;
        };
    L_0x003c:
        goto L_0x003d;
    L_0x003d:
        r0 = r40;
        r1 = r28;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x0023;
    L_0x0045:
        r0 = r40;
        r1 = r28;
        r16 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r31 = 1;
        r0 = r31;
        r30 = java.lang.Integer.valueOf(r0);
        r0 = r30;
        r15.add(r0);
        goto L_0x0023;
    L_0x005b:
        r32 = com.google.android.gms.people.identity.internal.models.PersonImpl.MetadataImpl.CREATOR;
        goto L_0x0061;
    L_0x005e:
        goto L_0x0023;
    L_0x0061:
        r0 = r40;
        r1 = r28;
        r2 = r32;
        r33 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        goto L_0x006f;
    L_0x006c:
        goto L_0x0023;
    L_0x006f:
        r31 = 2;
        r0 = r31;
        r30 = java.lang.Integer.valueOf(r0);
        r0 = r30;
        r15.add(r0);
        goto L_0x0080;
    L_0x007d:
        goto L_0x0023;
    L_0x0080:
        r34 = r33;
        r34 = (com.google.android.gms.people.identity.internal.models.PersonImpl.MetadataImpl) r34;
        r17 = r34;
        goto L_0x0023;
        goto L_0x008b;
    L_0x0088:
        goto L_0x0023;
    L_0x008b:
        r0 = r40;
        r1 = r28;
        r18 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0097;
    L_0x0094:
        goto L_0x0023;
    L_0x0097:
        r31 = 3;
        r0 = r31;
        r30 = java.lang.Integer.valueOf(r0);
        r0 = r30;
        r15.add(r0);
        goto L_0x0023;
    L_0x00a6:
        r0 = r40;
        r1 = r28;
        r19 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r31 = 4;
        r0 = r31;
        r30 = java.lang.Integer.valueOf(r0);
        r0 = r30;
        r15.add(r0);
        goto L_0x005e;
    L_0x00bc:
        r0 = r40;
        r1 = r28;
        r20 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r31 = 5;
        r0 = r31;
        r30 = java.lang.Integer.valueOf(r0);
        r0 = r30;
        r15.add(r0);
        goto L_0x006c;
    L_0x00d2:
        r0 = r40;
        r1 = r28;
        r21 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r31 = 6;
        r0 = r31;
        r30 = java.lang.Integer.valueOf(r0);
        r0 = r30;
        r15.add(r0);
        goto L_0x007d;
    L_0x00e8:
        r0 = r40;
        r1 = r28;
        r22 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r31 = 7;
        r0 = r31;
        r30 = java.lang.Integer.valueOf(r0);
        r0 = r30;
        r15.add(r0);
        goto L_0x0088;
    L_0x00fe:
        r0 = r40;
        r1 = r28;
        r23 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r31 = 8;
        r0 = r31;
        r30 = java.lang.Integer.valueOf(r0);
        r0 = r30;
        r15.add(r0);
        goto L_0x0094;
    L_0x0114:
        r0 = r40;
        r1 = r28;
        r24 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r31 = 9;
        r0 = r31;
        r30 = java.lang.Integer.valueOf(r0);
        goto L_0x0128;
    L_0x0125:
        goto L_0x0023;
    L_0x0128:
        r0 = r30;
        r15.add(r0);
        goto L_0x0125;
    L_0x012e:
        r0 = r40;
        r1 = r28;
        r25 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r31 = 10;
        r0 = r31;
        r30 = java.lang.Integer.valueOf(r0);
        goto L_0x0142;
    L_0x013f:
        goto L_0x0023;
    L_0x0142:
        r0 = r30;
        r15.add(r0);
        goto L_0x013f;
    L_0x0148:
        r0 = r40;
        r1 = r28;
        r26 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r31 = 11;
        r0 = r31;
        r30 = java.lang.Integer.valueOf(r0);
        goto L_0x015c;
    L_0x0159:
        goto L_0x0023;
    L_0x015c:
        r0 = r30;
        r15.add(r0);
        goto L_0x0159;
    L_0x0162:
        r0 = r40;
        r1 = r28;
        r27 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r31 = 12;
        r0 = r31;
        r30 = java.lang.Integer.valueOf(r0);
        goto L_0x0176;
    L_0x0173:
        goto L_0x0023;
    L_0x0176:
        r0 = r30;
        r15.add(r0);
        goto L_0x0173;
    L_0x017c:
        r0 = r40;
        r28 = r0.dataPosition();
        r0 = r28;
        if (r0 == r14) goto L_0x01b3;
    L_0x0186:
        r35 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r36 = new java.lang.StringBuilder;
        r31 = 37;
        r0 = r36;
        r1 = r31;
        r0.<init>(r1);
        r37 = "Overread allowed size end=";
        r0 = r36;
        r1 = r37;
        r36 = r0.append(r1);
        r0 = r36;
        r36 = r0.append(r14);
        r0 = r36;
        r18 = r0.toString();
        r0 = r35;
        r1 = r18;
        r2 = r40;
        r0.<init>(r1, r2);
        throw r35;
    L_0x01b3:
        r38 = new com.google.android.gms.people.identity.internal.models.PersonImpl$AddressesImpl;
        r0 = r38;
        r1 = r15;
        r2 = r16;
        r3 = r17;
        r4 = r18;
        r5 = r19;
        r6 = r20;
        r7 = r21;
        r8 = r22;
        r9 = r23;
        r10 = r24;
        r11 = r25;
        r12 = r26;
        r13 = r27;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13);
        return r38;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.people.identity.internal.models.zzb.zzsm(android.os.Parcel):com.google.android.gms.people.identity.internal.models.PersonImpl$AddressesImpl");
    }
}
