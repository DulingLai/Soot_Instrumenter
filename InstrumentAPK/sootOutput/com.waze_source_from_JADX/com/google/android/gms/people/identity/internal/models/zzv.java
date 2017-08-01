package com.google.android.gms.people.identity.internal.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.people.identity.internal.models.PersonImpl.OrganizationsImpl;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzv implements Creator<OrganizationsImpl> {
    static void zza(OrganizationsImpl $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.aOZ, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.aPH);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.aPI, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zza($r1, 5, $r0.mDescription, true);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            zzb.zza($r1, 6, $r0.aPJ, true);
        }
        if ($r2.contains(Integer.valueOf(7))) {
            zzb.zza($r1, 7, $r0.aPK, true);
        }
        if ($r2.contains(Integer.valueOf(8))) {
            zzb.zza($r1, 8, $r0.aOw, true);
        }
        if ($r2.contains(Integer.valueOf(9))) {
            zzb.zza($r1, 9, $r0.mName, true);
        }
        if ($r2.contains(Integer.valueOf(10))) {
            zzb.zza($r1, 10, $r0.aPL, true);
        }
        if ($r2.contains(Integer.valueOf(11))) {
            zzb.zza($r1, 11, $r0.aPM, true);
        }
        if ($r2.contains(Integer.valueOf(12))) {
            zzb.zza($r1, 12, $r0.aPN, true);
        }
        if ($r2.contains(Integer.valueOf(13))) {
            zzb.zza($r1, 13, $r0.Sm, true);
        }
        if ($r2.contains(Integer.valueOf(14))) {
            zzb.zza($r1, 14, $r0.zzcft, true);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzte($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzabr($i0);
    }

    public OrganizationsImpl[] zzabr(int $i0) throws  {
        return new OrganizationsImpl[$i0];
    }

    public com.google.android.gms.people.identity.internal.models.PersonImpl.OrganizationsImpl zzte(android.os.Parcel r44) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:10:0x006b
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
        r43 = this;
        r0 = r44;
        r16 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r17 = new java.util.HashSet;
        r0 = r17;
        r0.<init>();
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
        r28 = 0;
        r29 = 0;
        r30 = 0;
        r31 = 0;
    L_0x0029:
        r0 = r44;
        r32 = r0.dataPosition();
        r0 = r32;
        r1 = r16;
        if (r0 >= r1) goto L_0x01df;
    L_0x0035:
        r0 = r44;
        r32 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r32;
        r33 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r33) {
            case 1: goto L_0x004d;
            case 2: goto L_0x0065;
            case 3: goto L_0x009b;
            case 4: goto L_0x00b7;
            case 5: goto L_0x00cf;
            case 6: goto L_0x00ef;
            case 7: goto L_0x0107;
            case 8: goto L_0x011f;
            case 9: goto L_0x0137;
            case 10: goto L_0x0153;
            case 11: goto L_0x016f;
            case 12: goto L_0x018b;
            case 13: goto L_0x01a7;
            case 14: goto L_0x01c3;
            default: goto L_0x0044;
        };
    L_0x0044:
        goto L_0x0045;
    L_0x0045:
        r0 = r44;
        r1 = r32;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x0029;
    L_0x004d:
        r0 = r44;
        r1 = r32;
        r18 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r35 = 1;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x0029;
    L_0x0065:
        r36 = com.google.android.gms.people.identity.internal.models.PersonImpl.MetadataImpl.CREATOR;
        goto L_0x006f;
    L_0x0068:
        goto L_0x0029;
        goto L_0x006f;
    L_0x006c:
        goto L_0x0029;
    L_0x006f:
        r0 = r44;
        r1 = r32;
        r2 = r36;
        r37 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r35 = 2;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        goto L_0x0085;
    L_0x0082:
        goto L_0x0029;
    L_0x0085:
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x0090;
    L_0x008d:
        goto L_0x0029;
    L_0x0090:
        r38 = r37;
        r38 = (com.google.android.gms.people.identity.internal.models.PersonImpl.MetadataImpl) r38;
        r19 = r38;
        goto L_0x0029;
        goto L_0x009b;
    L_0x0098:
        goto L_0x0029;
    L_0x009b:
        r0 = r44;
        r1 = r32;
        r20 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x00a7;
    L_0x00a4:
        goto L_0x0029;
    L_0x00a7:
        r35 = 3;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x006c;
    L_0x00b7:
        r0 = r44;
        r1 = r32;
        r21 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 4;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x0068;
    L_0x00cf:
        r0 = r44;
        r1 = r32;
        r22 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x00db;
    L_0x00d8:
        goto L_0x0098;
    L_0x00db:
        r35 = 5;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x0082;
        goto L_0x00ef;
    L_0x00ec:
        goto L_0x00a4;
    L_0x00ef:
        r0 = r44;
        r1 = r32;
        r23 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 6;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x008d;
    L_0x0107:
        r0 = r44;
        r1 = r32;
        r24 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 7;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x00d8;
    L_0x011f:
        r0 = r44;
        r1 = r32;
        r25 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 8;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x00ec;
    L_0x0137:
        r0 = r44;
        r1 = r32;
        r26 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 9;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        goto L_0x014b;
    L_0x0148:
        goto L_0x0029;
    L_0x014b:
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x0148;
    L_0x0153:
        r0 = r44;
        r1 = r32;
        r27 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 10;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        goto L_0x0167;
    L_0x0164:
        goto L_0x0029;
    L_0x0167:
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x0164;
    L_0x016f:
        r0 = r44;
        r1 = r32;
        r28 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 11;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        goto L_0x0183;
    L_0x0180:
        goto L_0x0029;
    L_0x0183:
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x0180;
    L_0x018b:
        r0 = r44;
        r1 = r32;
        r29 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 12;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        goto L_0x019f;
    L_0x019c:
        goto L_0x0029;
    L_0x019f:
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x019c;
    L_0x01a7:
        r0 = r44;
        r1 = r32;
        r30 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 13;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        goto L_0x01bb;
    L_0x01b8:
        goto L_0x0029;
    L_0x01bb:
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x01b8;
    L_0x01c3:
        r0 = r44;
        r1 = r32;
        r31 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r35 = 14;
        r0 = r35;
        r34 = java.lang.Integer.valueOf(r0);
        goto L_0x01d7;
    L_0x01d4:
        goto L_0x0029;
    L_0x01d7:
        r0 = r17;
        r1 = r34;
        r0.add(r1);
        goto L_0x01d4;
    L_0x01df:
        r0 = r44;
        r32 = r0.dataPosition();
        r0 = r32;
        r1 = r16;
        if (r0 == r1) goto L_0x021a;
    L_0x01eb:
        r39 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r40 = new java.lang.StringBuilder;
        r35 = 37;
        r0 = r40;
        r1 = r35;
        r0.<init>(r1);
        r41 = "Overread allowed size end=";
        r0 = r40;
        r1 = r41;
        r40 = r0.append(r1);
        r0 = r40;
        r1 = r16;
        r40 = r0.append(r1);
        r0 = r40;
        r21 = r0.toString();
        r0 = r39;
        r1 = r21;
        r2 = r44;
        r0.<init>(r1, r2);
        throw r39;
    L_0x021a:
        r42 = new com.google.android.gms.people.identity.internal.models.PersonImpl$OrganizationsImpl;
        r0 = r42;
        r1 = r17;
        r2 = r18;
        r3 = r19;
        r4 = r20;
        r5 = r21;
        r6 = r22;
        r7 = r23;
        r8 = r24;
        r9 = r25;
        r10 = r26;
        r11 = r27;
        r12 = r28;
        r13 = r29;
        r14 = r30;
        r15 = r31;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15);
        return r42;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.people.identity.internal.models.zzv.zzte(android.os.Parcel):com.google.android.gms.people.identity.internal.models.PersonImpl$OrganizationsImpl");
    }
}
