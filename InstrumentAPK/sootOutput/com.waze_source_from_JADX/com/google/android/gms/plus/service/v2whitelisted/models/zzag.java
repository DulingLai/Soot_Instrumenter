package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzag implements Creator<Metadata> {
    static void zza(Metadata $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zzc($r1, 2, $r0.aZD, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zzb($r1, 3, $r0.axZ, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zzb($r1, 4, $r0.aPO, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zza($r1, 5, $r0.aPW);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            zzb.zzb($r1, 6, $r0.aMB, true);
        }
        if ($r2.contains(Integer.valueOf(7))) {
            zzb.zzb($r1, 7, $r0.aPP, true);
        }
        if ($r2.contains(Integer.valueOf(8))) {
            zzb.zza($r1, 8, $r0.bah, true);
        }
        if ($r2.contains(Integer.valueOf(9))) {
            zzb.zza($r1, 9, $r0.aPX);
        }
        if ($r2.contains(Integer.valueOf(10))) {
            zzb.zzb($r1, 10, $r0.aPQ, true);
        }
        if ($r2.contains(Integer.valueOf(11))) {
            zzb.zza($r1, 11, $r0.bai, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(12))) {
            zzb.zza($r1, 12, $r0.aPY);
        }
        if ($r2.contains(Integer.valueOf(13))) {
            zzb.zzb($r1, 13, $r0.aPR, true);
        }
        if ($r2.contains(Integer.valueOf(14))) {
            zzb.zza($r1, 14, $r0.baj);
        }
        if ($r2.contains(Integer.valueOf(15))) {
            zzb.zza($r1, 15, $r0.aaX, true);
        }
        if ($r2.contains(Integer.valueOf(16))) {
            zzb.zza($r1, 16, $r0.aPS, true);
        }
        if ($r2.contains(Integer.valueOf(17))) {
            zzb.zzb($r1, 17, $r0.aPT, true);
        }
        if ($r2.contains(Integer.valueOf(18))) {
            zzb.zza($r1, 18, $r0.aPU, true);
        }
        if ($r2.contains(Integer.valueOf(19))) {
            zzb.zza($r1, 19, $r0.bak, $i0, true);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzwu($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzafx($i0);
    }

    public Metadata[] zzafx(int $i0) throws  {
        return new Metadata[$i0];
    }

    public com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata zzwu(android.os.Parcel r57) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:10:0x0075
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r56 = this;
        r0 = r57;
        r22 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r23 = new java.util.HashSet;
        r0 = r23;
        r0.<init>();
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
        r34 = 0;
        r35 = 0;
        r36 = 0;
        r37 = 0;
        r39 = 0;
        r40 = 0;
        r41 = 0;
        r42 = 0;
        r43 = 0;
    L_0x0033:
        r0 = r57;
        r44 = r0.dataPosition();
        r0 = r44;
        r1 = r22;
        if (r0 >= r1) goto L_0x027f;
    L_0x003f:
        r0 = r57;
        r44 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r44;
        r45 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r45) {
            case 1: goto L_0x0057;
            case 2: goto L_0x006f;
            case 3: goto L_0x009b;
            case 4: goto L_0x00bb;
            case 5: goto L_0x00d3;
            case 6: goto L_0x00ef;
            case 7: goto L_0x0107;
            case 8: goto L_0x011f;
            case 9: goto L_0x0137;
            case 10: goto L_0x0153;
            case 11: goto L_0x016f;
            case 12: goto L_0x0195;
            case 13: goto L_0x01b1;
            case 14: goto L_0x01cd;
            case 15: goto L_0x01e9;
            case 16: goto L_0x0205;
            case 17: goto L_0x0221;
            case 18: goto L_0x023d;
            case 19: goto L_0x0259;
            default: goto L_0x004e;
        };
    L_0x004e:
        goto L_0x004f;
    L_0x004f:
        r0 = r57;
        r1 = r44;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x0033;
    L_0x0057:
        r0 = r57;
        r1 = r44;
        r24 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r47 = 1;
        r0 = r47;
        r46 = java.lang.Integer.valueOf(r0);
        r0 = r23;
        r1 = r46;
        r0.add(r1);
        goto L_0x0033;
    L_0x006f:
        r48 = com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeopleaffinities.CREATOR;
        goto L_0x0079;
    L_0x0072:
        goto L_0x0033;
        goto L_0x0079;
    L_0x0076:
        goto L_0x0033;
    L_0x0079:
        r0 = r57;
        r1 = r44;
        r2 = r48;
        r25 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        goto L_0x0087;
    L_0x0084:
        goto L_0x0033;
    L_0x0087:
        r47 = 2;
        r0 = r47;
        r46 = java.lang.Integer.valueOf(r0);
        r0 = r23;
        r1 = r46;
        r0.add(r1);
        goto L_0x0033;
        goto L_0x009b;
    L_0x0098:
        goto L_0x0033;
    L_0x009b:
        r0 = r57;
        r1 = r44;
        r26 = com.google.android.gms.common.internal.safeparcel.zza.zzae(r0, r1);
        goto L_0x00a7;
    L_0x00a4:
        goto L_0x0033;
    L_0x00a7:
        r47 = 3;
        r0 = r47;
        r46 = java.lang.Integer.valueOf(r0);
        goto L_0x00b3;
    L_0x00b0:
        goto L_0x0033;
    L_0x00b3:
        r0 = r23;
        r1 = r46;
        r0.add(r1);
        goto L_0x0076;
    L_0x00bb:
        r0 = r57;
        r1 = r44;
        r27 = com.google.android.gms.common.internal.safeparcel.zza.zzae(r0, r1);
        r47 = 4;
        r0 = r47;
        r46 = java.lang.Integer.valueOf(r0);
        r0 = r23;
        r1 = r46;
        r0.add(r1);
        goto L_0x0072;
    L_0x00d3:
        r0 = r57;
        r1 = r44;
        r28 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r47 = 5;
        r0 = r47;
        r46 = java.lang.Integer.valueOf(r0);
        r0 = r23;
        r1 = r46;
        r0.add(r1);
        goto L_0x0084;
        goto L_0x00ef;
    L_0x00ec:
        goto L_0x00b0;
    L_0x00ef:
        r0 = r57;
        r1 = r44;
        r29 = com.google.android.gms.common.internal.safeparcel.zza.zzae(r0, r1);
        r47 = 6;
        r0 = r47;
        r46 = java.lang.Integer.valueOf(r0);
        r0 = r23;
        r1 = r46;
        r0.add(r1);
        goto L_0x0098;
    L_0x0107:
        r0 = r57;
        r1 = r44;
        r30 = com.google.android.gms.common.internal.safeparcel.zza.zzae(r0, r1);
        r47 = 7;
        r0 = r47;
        r46 = java.lang.Integer.valueOf(r0);
        r0 = r23;
        r1 = r46;
        r0.add(r1);
        goto L_0x00a4;
    L_0x011f:
        r0 = r57;
        r1 = r44;
        r31 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r47 = 8;
        r0 = r47;
        r46 = java.lang.Integer.valueOf(r0);
        r0 = r23;
        r1 = r46;
        r0.add(r1);
        goto L_0x00ec;
    L_0x0137:
        r0 = r57;
        r1 = r44;
        r32 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r47 = 9;
        r0 = r47;
        r46 = java.lang.Integer.valueOf(r0);
        goto L_0x014b;
    L_0x0148:
        goto L_0x0033;
    L_0x014b:
        r0 = r23;
        r1 = r46;
        r0.add(r1);
        goto L_0x0148;
    L_0x0153:
        r0 = r57;
        r1 = r44;
        r33 = com.google.android.gms.common.internal.safeparcel.zza.zzae(r0, r1);
        r47 = 10;
        r0 = r47;
        r46 = java.lang.Integer.valueOf(r0);
        goto L_0x0167;
    L_0x0164:
        goto L_0x0033;
    L_0x0167:
        r0 = r23;
        r1 = r46;
        r0.add(r1);
        goto L_0x0164;
    L_0x016f:
        r48 = com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.CREATOR;
        r0 = r57;
        r1 = r44;
        r2 = r48;
        r49 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r47 = 11;
        r0 = r47;
        r46 = java.lang.Integer.valueOf(r0);
        r0 = r23;
        r1 = r46;
        r0.add(r1);
        goto L_0x018e;
    L_0x018b:
        goto L_0x0033;
    L_0x018e:
        r50 = r49;
        r50 = (com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo) r50;
        r34 = r50;
        goto L_0x018b;
    L_0x0195:
        r0 = r57;
        r1 = r44;
        r35 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r47 = 12;
        r0 = r47;
        r46 = java.lang.Integer.valueOf(r0);
        goto L_0x01a9;
    L_0x01a6:
        goto L_0x0033;
    L_0x01a9:
        r0 = r23;
        r1 = r46;
        r0.add(r1);
        goto L_0x01a6;
    L_0x01b1:
        r0 = r57;
        r1 = r44;
        r36 = com.google.android.gms.common.internal.safeparcel.zza.zzae(r0, r1);
        r47 = 13;
        r0 = r47;
        r46 = java.lang.Integer.valueOf(r0);
        goto L_0x01c5;
    L_0x01c2:
        goto L_0x0033;
    L_0x01c5:
        r0 = r23;
        r1 = r46;
        r0.add(r1);
        goto L_0x01c2;
    L_0x01cd:
        r0 = r57;
        r1 = r44;
        r37 = com.google.android.gms.common.internal.safeparcel.zza.zzi(r0, r1);
        r47 = 14;
        r0 = r47;
        r46 = java.lang.Integer.valueOf(r0);
        goto L_0x01e1;
    L_0x01de:
        goto L_0x0033;
    L_0x01e1:
        r0 = r23;
        r1 = r46;
        r0.add(r1);
        goto L_0x01de;
    L_0x01e9:
        r0 = r57;
        r1 = r44;
        r39 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r47 = 15;
        r0 = r47;
        r46 = java.lang.Integer.valueOf(r0);
        goto L_0x01fd;
    L_0x01fa:
        goto L_0x0033;
    L_0x01fd:
        r0 = r23;
        r1 = r46;
        r0.add(r1);
        goto L_0x01fa;
    L_0x0205:
        r0 = r57;
        r1 = r44;
        r40 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r47 = 16;
        r0 = r47;
        r46 = java.lang.Integer.valueOf(r0);
        goto L_0x0219;
    L_0x0216:
        goto L_0x0033;
    L_0x0219:
        r0 = r23;
        r1 = r46;
        r0.add(r1);
        goto L_0x0216;
    L_0x0221:
        r0 = r57;
        r1 = r44;
        r41 = com.google.android.gms.common.internal.safeparcel.zza.zzae(r0, r1);
        r47 = 17;
        r0 = r47;
        r46 = java.lang.Integer.valueOf(r0);
        goto L_0x0235;
    L_0x0232:
        goto L_0x0033;
    L_0x0235:
        r0 = r23;
        r1 = r46;
        r0.add(r1);
        goto L_0x0232;
    L_0x023d:
        r0 = r57;
        r1 = r44;
        r42 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r47 = 18;
        r0 = r47;
        r46 = java.lang.Integer.valueOf(r0);
        goto L_0x0251;
    L_0x024e:
        goto L_0x0033;
    L_0x0251:
        r0 = r23;
        r1 = r46;
        r0.add(r1);
        goto L_0x024e;
    L_0x0259:
        r48 = com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats.CREATOR;
        r0 = r57;
        r1 = r44;
        r2 = r48;
        r49 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r47 = 19;
        r0 = r47;
        r46 = java.lang.Integer.valueOf(r0);
        r0 = r23;
        r1 = r46;
        r0.add(r1);
        goto L_0x0278;
    L_0x0275:
        goto L_0x0033;
    L_0x0278:
        r51 = r49;
        r51 = (com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats) r51;
        r43 = r51;
        goto L_0x0275;
    L_0x027f:
        r0 = r57;
        r44 = r0.dataPosition();
        r0 = r44;
        r1 = r22;
        if (r0 == r1) goto L_0x02ba;
    L_0x028b:
        r52 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r53 = new java.lang.StringBuilder;
        r47 = 37;
        r0 = r53;
        r1 = r47;
        r0.<init>(r1);
        r54 = "Overread allowed size end=";
        r0 = r53;
        r1 = r54;
        r53 = r0.append(r1);
        r0 = r53;
        r1 = r22;
        r53 = r0.append(r1);
        r0 = r53;
        r31 = r0.toString();
        r0 = r52;
        r1 = r31;
        r2 = r57;
        r0.<init>(r1, r2);
        throw r52;
    L_0x02ba:
        r55 = new com.google.android.gms.plus.service.v2whitelisted.models.Person$Metadata;
        r0 = r55;
        r1 = r23;
        r2 = r24;
        r3 = r25;
        r4 = r26;
        r5 = r27;
        r6 = r28;
        r7 = r29;
        r8 = r30;
        r9 = r31;
        r10 = r32;
        r11 = r33;
        r12 = r34;
        r13 = r35;
        r14 = r36;
        r15 = r37;
        r17 = r39;
        r18 = r40;
        r19 = r41;
        r20 = r42;
        r21 = r43;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r17, r18, r19, r20, r21);
        return r55;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.zzag.zzwu(android.os.Parcel):com.google.android.gms.plus.service.v2whitelisted.models.Person$Metadata");
    }
}
