package com.google.android.gms.plus.internal.model.people;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zza implements Creator<PersonEntity> {
    static void zza(PersonEntity $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.aYV, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.aYW, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.aYX, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zza($r1, 5, $r0.aYY, true);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            zzb.zzc($r1, 6, $r0.aYZ);
        }
        if ($r2.contains(Integer.valueOf(7))) {
            zzb.zza($r1, 7, $r0.aZa, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(8))) {
            zzb.zza($r1, 8, $r0.aZb, true);
        }
        if ($r2.contains(Integer.valueOf(9))) {
            zzb.zza($r1, 9, $r0.cr, true);
        }
        if ($r2.contains(Integer.valueOf(12))) {
            zzb.zzc($r1, 12, $r0.zzauv);
        }
        if ($r2.contains(Integer.valueOf(14))) {
            zzb.zza($r1, 14, $r0.zzbgd, true);
        }
        if ($r2.contains(Integer.valueOf(15))) {
            zzb.zza($r1, 15, $r0.aZc, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(16))) {
            zzb.zza($r1, 16, $r0.aZd);
        }
        if ($r2.contains(Integer.valueOf(18))) {
            zzb.zza($r1, 18, $r0.zzcuw, true);
        }
        if ($r2.contains(Integer.valueOf(19))) {
            zzb.zza($r1, 19, $r0.aZe, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(20))) {
            zzb.zza($r1, 20, $r0.aBX, true);
        }
        if ($r2.contains(Integer.valueOf(21))) {
            zzb.zzc($r1, 21, $r0.aUk);
        }
        if ($r2.contains(Integer.valueOf(22))) {
            zzb.zzc($r1, 22, $r0.aON, true);
        }
        if ($r2.contains(Integer.valueOf(23))) {
            zzb.zzc($r1, 23, $r0.aOP, true);
        }
        if ($r2.contains(Integer.valueOf(24))) {
            zzb.zzc($r1, 24, $r0.aZf);
        }
        if ($r2.contains(Integer.valueOf(25))) {
            zzb.zzc($r1, 25, $r0.aZg);
        }
        if ($r2.contains(Integer.valueOf(26))) {
            zzb.zza($r1, 26, $r0.aZh, true);
        }
        if ($r2.contains(Integer.valueOf(27))) {
            zzb.zza($r1, 27, $r0.zzad, true);
        }
        if ($r2.contains(Integer.valueOf(28))) {
            zzb.zzc($r1, 28, $r0.aOX, true);
        }
        if ($r2.contains(Integer.valueOf(29))) {
            zzb.zza($r1, 29, $r0.aPw);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzve($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzaeh($i0);
    }

    public PersonEntity[] zzaeh(int $i0) throws  {
        return new PersonEntity[$i0];
    }

    public com.google.android.gms.plus.internal.model.people.PersonEntity zzve(android.os.Parcel r75) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:8:0x007b in {5, 6, 7, 9, 11, 13, 14, 16, 18, 25, 28, 31, 34, 37, 40, 43, 46, 49, 52, 55, 58, 61, 64, 67, 70, 73, 76, 81, 83} preds:[]
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
        r74 = this;
        r0 = r75;
        r27 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r28 = new java.util.HashSet;
        r0 = r28;
        r0.<init>();
        r29 = 0;
        r30 = 0;
        r31 = 0;
        r32 = 0;
        r33 = 0;
        r34 = 0;
        r35 = 0;
        r36 = 0;
        r37 = 0;
        r38 = 0;
        r39 = 0;
        r40 = 0;
        r41 = 0;
        r42 = 0;
        r43 = 0;
        r44 = 0;
        r45 = 0;
        r46 = 0;
        r47 = 0;
        r48 = 0;
        r49 = 0;
        r50 = 0;
        r51 = 0;
        r52 = 0;
        r53 = 0;
    L_0x003f:
        r0 = r75;
        r54 = r0.dataPosition();
        r0 = r54;
        r1 = r27;
        if (r0 >= r1) goto L_0x034b;
    L_0x004b:
        r0 = r75;
        r54 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r54;
        r55 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r55) {
            case 1: goto L_0x0063;
            case 2: goto L_0x007f;
            case 3: goto L_0x009f;
            case 4: goto L_0x00c9;
            case 5: goto L_0x00e1;
            case 6: goto L_0x00f9;
            case 7: goto L_0x0111;
            case 8: goto L_0x0133;
            case 9: goto L_0x014f;
            case 10: goto L_0x005b;
            case 11: goto L_0x005b;
            case 12: goto L_0x016b;
            case 13: goto L_0x005b;
            case 14: goto L_0x0187;
            case 15: goto L_0x01a3;
            case 16: goto L_0x01c9;
            case 17: goto L_0x005b;
            case 18: goto L_0x01e5;
            case 19: goto L_0x0201;
            case 20: goto L_0x0227;
            case 21: goto L_0x0243;
            case 22: goto L_0x025f;
            case 23: goto L_0x027f;
            case 24: goto L_0x029f;
            case 25: goto L_0x02bb;
            case 26: goto L_0x02d7;
            case 27: goto L_0x02f3;
            case 28: goto L_0x030f;
            case 29: goto L_0x032f;
            default: goto L_0x005a;
        };
    L_0x005a:
        goto L_0x005b;
    L_0x005b:
        r0 = r75;
        r1 = r54;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x003f;
    L_0x0063:
        r0 = r75;
        r1 = r54;
        r29 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r57 = 1;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x003f;
        goto L_0x007f;
    L_0x007c:
        goto L_0x003f;
    L_0x007f:
        r0 = r75;
        r1 = r54;
        r30 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x008b;
    L_0x0088:
        goto L_0x003f;
    L_0x008b:
        r57 = 2;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        goto L_0x0097;
    L_0x0094:
        goto L_0x003f;
    L_0x0097:
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x003f;
    L_0x009f:
        r58 = com.google.android.gms.plus.internal.model.people.PersonEntity.AgeRangeEntity.CREATOR;
        goto L_0x00a5;
    L_0x00a2:
        goto L_0x003f;
    L_0x00a5:
        r0 = r75;
        r1 = r54;
        r2 = r58;
        r59 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r57 = 3;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        goto L_0x00bb;
    L_0x00b8:
        goto L_0x003f;
    L_0x00bb:
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        r60 = r59;
        r60 = (com.google.android.gms.plus.internal.model.people.PersonEntity.AgeRangeEntity) r60;
        r31 = r60;
        goto L_0x007c;
    L_0x00c9:
        r0 = r75;
        r1 = r54;
        r32 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r57 = 4;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x0088;
    L_0x00e1:
        r0 = r75;
        r1 = r54;
        r33 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r57 = 5;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x0094;
    L_0x00f9:
        r0 = r75;
        r1 = r54;
        r34 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r57 = 6;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x00a2;
    L_0x0111:
        r61 = com.google.android.gms.plus.internal.model.people.PersonEntity.CoverEntity.CREATOR;
        r0 = r75;
        r1 = r54;
        r2 = r61;
        r59 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r57 = 7;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        r62 = r59;
        r62 = (com.google.android.gms.plus.internal.model.people.PersonEntity.CoverEntity) r62;
        r35 = r62;
        goto L_0x00b8;
    L_0x0133:
        r0 = r75;
        r1 = r54;
        r36 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r57 = 8;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        goto L_0x0147;
    L_0x0144:
        goto L_0x003f;
    L_0x0147:
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x0144;
    L_0x014f:
        r0 = r75;
        r1 = r54;
        r37 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r57 = 9;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        goto L_0x0163;
    L_0x0160:
        goto L_0x003f;
    L_0x0163:
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x0160;
    L_0x016b:
        r0 = r75;
        r1 = r54;
        r38 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r57 = 12;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        goto L_0x017f;
    L_0x017c:
        goto L_0x003f;
    L_0x017f:
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x017c;
    L_0x0187:
        r0 = r75;
        r1 = r54;
        r39 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r57 = 14;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        goto L_0x019b;
    L_0x0198:
        goto L_0x003f;
    L_0x019b:
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x0198;
    L_0x01a3:
        r63 = com.google.android.gms.plus.internal.model.people.PersonEntity.ImageEntity.CREATOR;
        r0 = r75;
        r1 = r54;
        r2 = r63;
        r59 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r57 = 15;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x01c2;
    L_0x01bf:
        goto L_0x003f;
    L_0x01c2:
        r64 = r59;
        r64 = (com.google.android.gms.plus.internal.model.people.PersonEntity.ImageEntity) r64;
        r40 = r64;
        goto L_0x01bf;
    L_0x01c9:
        r0 = r75;
        r1 = r54;
        r41 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r57 = 16;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        goto L_0x01dd;
    L_0x01da:
        goto L_0x003f;
    L_0x01dd:
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x01da;
    L_0x01e5:
        r0 = r75;
        r1 = r54;
        r42 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r57 = 18;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        goto L_0x01f9;
    L_0x01f6:
        goto L_0x003f;
    L_0x01f9:
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x01f6;
    L_0x0201:
        r65 = com.google.android.gms.plus.internal.model.people.PersonEntity.NameEntity.CREATOR;
        r0 = r75;
        r1 = r54;
        r2 = r65;
        r59 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r57 = 19;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x0220;
    L_0x021d:
        goto L_0x003f;
    L_0x0220:
        r66 = r59;
        r66 = (com.google.android.gms.plus.internal.model.people.PersonEntity.NameEntity) r66;
        r43 = r66;
        goto L_0x021d;
    L_0x0227:
        r0 = r75;
        r1 = r54;
        r44 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r57 = 20;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        goto L_0x023b;
    L_0x0238:
        goto L_0x003f;
    L_0x023b:
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x0238;
    L_0x0243:
        r0 = r75;
        r1 = r54;
        r45 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r57 = 21;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        goto L_0x0257;
    L_0x0254:
        goto L_0x003f;
    L_0x0257:
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x0254;
    L_0x025f:
        r67 = com.google.android.gms.plus.internal.model.people.PersonEntity.OrganizationsEntity.CREATOR;
        r0 = r75;
        r1 = r54;
        r2 = r67;
        r46 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r57 = 22;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        goto L_0x0277;
    L_0x0274:
        goto L_0x003f;
    L_0x0277:
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x0274;
    L_0x027f:
        r68 = com.google.android.gms.plus.internal.model.people.PersonEntity.PlacesLivedEntity.CREATOR;
        r0 = r75;
        r1 = r54;
        r2 = r68;
        r47 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r57 = 23;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        goto L_0x0297;
    L_0x0294:
        goto L_0x003f;
    L_0x0297:
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x0294;
    L_0x029f:
        r0 = r75;
        r1 = r54;
        r48 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r57 = 24;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        goto L_0x02b3;
    L_0x02b0:
        goto L_0x003f;
    L_0x02b3:
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x02b0;
    L_0x02bb:
        r0 = r75;
        r1 = r54;
        r49 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r57 = 25;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        goto L_0x02cf;
    L_0x02cc:
        goto L_0x003f;
    L_0x02cf:
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x02cc;
    L_0x02d7:
        r0 = r75;
        r1 = r54;
        r50 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r57 = 26;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        goto L_0x02eb;
    L_0x02e8:
        goto L_0x003f;
    L_0x02eb:
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x02e8;
    L_0x02f3:
        r0 = r75;
        r1 = r54;
        r51 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r57 = 27;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        goto L_0x0307;
    L_0x0304:
        goto L_0x003f;
    L_0x0307:
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x0304;
    L_0x030f:
        r69 = com.google.android.gms.plus.internal.model.people.PersonEntity.UrlsEntity.CREATOR;
        r0 = r75;
        r1 = r54;
        r2 = r69;
        r52 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r57 = 28;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        goto L_0x0327;
    L_0x0324:
        goto L_0x003f;
    L_0x0327:
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x0324;
    L_0x032f:
        r0 = r75;
        r1 = r54;
        r53 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r57 = 29;
        r0 = r57;
        r56 = java.lang.Integer.valueOf(r0);
        goto L_0x0343;
    L_0x0340:
        goto L_0x003f;
    L_0x0343:
        r0 = r28;
        r1 = r56;
        r0.add(r1);
        goto L_0x0340;
    L_0x034b:
        r0 = r75;
        r54 = r0.dataPosition();
        r0 = r54;
        r1 = r27;
        if (r0 == r1) goto L_0x0386;
    L_0x0357:
        r70 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r71 = new java.lang.StringBuilder;
        r57 = 37;
        r0 = r71;
        r1 = r57;
        r0.<init>(r1);
        r72 = "Overread allowed size end=";
        r0 = r71;
        r1 = r72;
        r71 = r0.append(r1);
        r0 = r71;
        r1 = r27;
        r71 = r0.append(r1);
        r0 = r71;
        r30 = r0.toString();
        r0 = r70;
        r1 = r30;
        r2 = r75;
        r0.<init>(r1, r2);
        throw r70;
    L_0x0386:
        r73 = new com.google.android.gms.plus.internal.model.people.PersonEntity;
        r0 = r73;
        r1 = r28;
        r2 = r29;
        r3 = r30;
        r4 = r31;
        r5 = r32;
        r6 = r33;
        r7 = r34;
        r8 = r35;
        r9 = r36;
        r10 = r37;
        r11 = r38;
        r12 = r39;
        r13 = r40;
        r14 = r41;
        r15 = r42;
        r16 = r43;
        r17 = r44;
        r18 = r45;
        r19 = r46;
        r20 = r47;
        r21 = r48;
        r22 = r49;
        r23 = r50;
        r24 = r51;
        r25 = r52;
        r26 = r53;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26);
        return r73;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.internal.model.people.zza.zzve(android.os.Parcel):com.google.android.gms.plus.internal.model.people.PersonEntity");
    }
}
