package com.google.android.gms.people.identity.internal.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.people.identity.internal.models.PersonImpl.PersonMetadataImpl;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzx implements Creator<PersonMetadataImpl> {
    static void zza(PersonMetadataImpl $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zzb($r1, 2, $r0.axZ, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zzb($r1, 3, $r0.aPO, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zzb($r1, 4, $r0.aMB, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zzb($r1, 5, $r0.aPP, true);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            zzb.zzb($r1, 6, $r0.aPQ, true);
        }
        if ($r2.contains(Integer.valueOf(7))) {
            zzb.zzb($r1, 7, $r0.aPR, true);
        }
        if ($r2.contains(Integer.valueOf(8))) {
            zzb.zza($r1, 8, $r0.aaX, true);
        }
        if ($r2.contains(Integer.valueOf(9))) {
            zzb.zza($r1, 9, $r0.aPS, true);
        }
        if ($r2.contains(Integer.valueOf(10))) {
            zzb.zzb($r1, 10, $r0.aPT, true);
        }
        if ($r2.contains(Integer.valueOf(11))) {
            zzb.zza($r1, 11, $r0.aPU, true);
        }
        if ($r2.contains(Integer.valueOf(12))) {
            zzb.zza($r1, 12, $r0.aPV, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(13))) {
            zzb.zza($r1, 13, $r0.aPW);
        }
        if ($r2.contains(Integer.valueOf(14))) {
            zzb.zza($r1, 14, $r0.aPX);
        }
        if ($r2.contains(Integer.valueOf(15))) {
            zzb.zza($r1, 15, $r0.aPY);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zztg($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzaby($i0);
    }

    public PersonMetadataImpl[] zzaby(int $i0) throws  {
        return new PersonMetadataImpl[$i0];
    }

    public com.google.android.gms.people.identity.internal.models.PersonImpl.PersonMetadataImpl zztg(android.os.Parcel r46) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:8:0x0067
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
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
        r45 = this;
        r0 = r46;
        r17 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r18 = new java.util.HashSet;
        r0 = r18;
        r0.<init>();
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
        r32 = 0;
        r33 = 0;
    L_0x002b:
        r0 = r46;
        r34 = r0.dataPosition();
        r0 = r34;
        r1 = r17;
        if (r0 >= r1) goto L_0x01f1;
    L_0x0037:
        r0 = r46;
        r34 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r34;
        r35 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r35) {
            case 1: goto L_0x004f;
            case 2: goto L_0x006b;
            case 3: goto L_0x008b;
            case 4: goto L_0x00ab;
            case 5: goto L_0x00c3;
            case 6: goto L_0x00db;
            case 7: goto L_0x00f3;
            case 8: goto L_0x010b;
            case 9: goto L_0x0123;
            case 10: goto L_0x013f;
            case 11: goto L_0x015b;
            case 12: goto L_0x0177;
            case 13: goto L_0x019d;
            case 14: goto L_0x01b9;
            case 15: goto L_0x01d5;
            default: goto L_0x0046;
        };
    L_0x0046:
        goto L_0x0047;
    L_0x0047:
        r0 = r46;
        r1 = r34;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x002b;
    L_0x004f:
        r0 = r46;
        r1 = r34;
        r19 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r37 = 1;
        r0 = r37;
        r36 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r1 = r36;
        r0.add(r1);
        goto L_0x002b;
        goto L_0x006b;
    L_0x0068:
        goto L_0x002b;
    L_0x006b:
        r0 = r46;
        r1 = r34;
        r20 = com.google.android.gms.common.internal.safeparcel.zza.zzae(r0, r1);
        r37 = 2;
        r0 = r37;
        r36 = java.lang.Integer.valueOf(r0);
        goto L_0x007f;
    L_0x007c:
        goto L_0x002b;
    L_0x007f:
        r0 = r18;
        r1 = r36;
        r0.add(r1);
        goto L_0x002b;
        goto L_0x008b;
    L_0x0088:
        goto L_0x002b;
    L_0x008b:
        r0 = r46;
        r1 = r34;
        r21 = com.google.android.gms.common.internal.safeparcel.zza.zzae(r0, r1);
        goto L_0x0097;
    L_0x0094:
        goto L_0x002b;
    L_0x0097:
        r37 = 3;
        r0 = r37;
        r36 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r1 = r36;
        r0.add(r1);
        goto L_0x00aa;
    L_0x00a7:
        goto L_0x002b;
    L_0x00aa:
        goto L_0x002b;
    L_0x00ab:
        r0 = r46;
        r1 = r34;
        r22 = com.google.android.gms.common.internal.safeparcel.zza.zzae(r0, r1);
        r37 = 4;
        r0 = r37;
        r36 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r1 = r36;
        r0.add(r1);
        goto L_0x0068;
    L_0x00c3:
        r0 = r46;
        r1 = r34;
        r23 = com.google.android.gms.common.internal.safeparcel.zza.zzae(r0, r1);
        r37 = 5;
        r0 = r37;
        r36 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r1 = r36;
        r0.add(r1);
        goto L_0x007c;
    L_0x00db:
        r0 = r46;
        r1 = r34;
        r24 = com.google.android.gms.common.internal.safeparcel.zza.zzae(r0, r1);
        r37 = 6;
        r0 = r37;
        r36 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r1 = r36;
        r0.add(r1);
        goto L_0x0088;
    L_0x00f3:
        r0 = r46;
        r1 = r34;
        r25 = com.google.android.gms.common.internal.safeparcel.zza.zzae(r0, r1);
        r37 = 7;
        r0 = r37;
        r36 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r1 = r36;
        r0.add(r1);
        goto L_0x0094;
    L_0x010b:
        r0 = r46;
        r1 = r34;
        r26 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r37 = 8;
        r0 = r37;
        r36 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r1 = r36;
        r0.add(r1);
        goto L_0x00a7;
    L_0x0123:
        r0 = r46;
        r1 = r34;
        r27 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r37 = 9;
        r0 = r37;
        r36 = java.lang.Integer.valueOf(r0);
        goto L_0x0137;
    L_0x0134:
        goto L_0x002b;
    L_0x0137:
        r0 = r18;
        r1 = r36;
        r0.add(r1);
        goto L_0x0134;
    L_0x013f:
        r0 = r46;
        r1 = r34;
        r28 = com.google.android.gms.common.internal.safeparcel.zza.zzae(r0, r1);
        r37 = 10;
        r0 = r37;
        r36 = java.lang.Integer.valueOf(r0);
        goto L_0x0153;
    L_0x0150:
        goto L_0x002b;
    L_0x0153:
        r0 = r18;
        r1 = r36;
        r0.add(r1);
        goto L_0x0150;
    L_0x015b:
        r0 = r46;
        r1 = r34;
        r29 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r37 = 11;
        r0 = r37;
        r36 = java.lang.Integer.valueOf(r0);
        goto L_0x016f;
    L_0x016c:
        goto L_0x002b;
    L_0x016f:
        r0 = r18;
        r1 = r36;
        r0.add(r1);
        goto L_0x016c;
    L_0x0177:
        r38 = com.google.android.gms.people.identity.internal.models.PersonImpl.ProfileOwnerStatsImpl.CREATOR;
        r0 = r46;
        r1 = r34;
        r2 = r38;
        r39 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r37 = 12;
        r0 = r37;
        r36 = java.lang.Integer.valueOf(r0);
        r0 = r18;
        r1 = r36;
        r0.add(r1);
        goto L_0x0196;
    L_0x0193:
        goto L_0x002b;
    L_0x0196:
        r40 = r39;
        r40 = (com.google.android.gms.people.identity.internal.models.PersonImpl.ProfileOwnerStatsImpl) r40;
        r30 = r40;
        goto L_0x0193;
    L_0x019d:
        r0 = r46;
        r1 = r34;
        r31 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r37 = 13;
        r0 = r37;
        r36 = java.lang.Integer.valueOf(r0);
        goto L_0x01b1;
    L_0x01ae:
        goto L_0x002b;
    L_0x01b1:
        r0 = r18;
        r1 = r36;
        r0.add(r1);
        goto L_0x01ae;
    L_0x01b9:
        r0 = r46;
        r1 = r34;
        r32 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r37 = 14;
        r0 = r37;
        r36 = java.lang.Integer.valueOf(r0);
        goto L_0x01cd;
    L_0x01ca:
        goto L_0x002b;
    L_0x01cd:
        r0 = r18;
        r1 = r36;
        r0.add(r1);
        goto L_0x01ca;
    L_0x01d5:
        r0 = r46;
        r1 = r34;
        r33 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r37 = 15;
        r0 = r37;
        r36 = java.lang.Integer.valueOf(r0);
        goto L_0x01e9;
    L_0x01e6:
        goto L_0x002b;
    L_0x01e9:
        r0 = r18;
        r1 = r36;
        r0.add(r1);
        goto L_0x01e6;
    L_0x01f1:
        r0 = r46;
        r34 = r0.dataPosition();
        r0 = r34;
        r1 = r17;
        if (r0 == r1) goto L_0x022c;
    L_0x01fd:
        r41 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r42 = new java.lang.StringBuilder;
        r37 = 37;
        r0 = r42;
        r1 = r37;
        r0.<init>(r1);
        r43 = "Overread allowed size end=";
        r0 = r42;
        r1 = r43;
        r42 = r0.append(r1);
        r0 = r42;
        r1 = r17;
        r42 = r0.append(r1);
        r0 = r42;
        r26 = r0.toString();
        r0 = r41;
        r1 = r26;
        r2 = r46;
        r0.<init>(r1, r2);
        throw r41;
    L_0x022c:
        r44 = new com.google.android.gms.people.identity.internal.models.PersonImpl$PersonMetadataImpl;
        r0 = r44;
        r1 = r18;
        r2 = r19;
        r3 = r20;
        r4 = r21;
        r5 = r22;
        r6 = r23;
        r7 = r24;
        r8 = r25;
        r9 = r26;
        r10 = r27;
        r11 = r28;
        r12 = r29;
        r13 = r30;
        r14 = r31;
        r15 = r32;
        r16 = r33;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16);
        return r44;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.people.identity.internal.models.zzx.zztg(android.os.Parcel):com.google.android.gms.people.identity.internal.models.PersonImpl$PersonMetadataImpl");
    }
}
