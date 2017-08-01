package com.google.android.gms.people.identity.internal.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzw implements Creator<PersonImpl> {
    static void zza(PersonImpl $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zzc($r1, 2, $r0.aOx, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zzc($r1, 3, $r0.aMy, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.aOy, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zzc($r1, 5, $r0.aOz, true);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            zzb.zzc($r1, 6, $r0.aOA, true);
        }
        if ($r2.contains(Integer.valueOf(7))) {
            zzb.zzc($r1, 7, $r0.aOB, true);
        }
        if ($r2.contains(Integer.valueOf(8))) {
            zzb.zzc($r1, 8, $r0.aOC, true);
        }
        if ($r2.contains(Integer.valueOf(9))) {
            zzb.zzc($r1, 9, $r0.aMz, true);
        }
        if ($r2.contains(Integer.valueOf(10))) {
            zzb.zza($r1, 10, $r0.aOD, true);
        }
        if ($r2.contains(Integer.valueOf(11))) {
            zzb.zzc($r1, 11, $r0.zzalu, true);
        }
        if ($r2.contains(Integer.valueOf(12))) {
            zzb.zzc($r1, 12, $r0.aOE, true);
        }
        if ($r2.contains(Integer.valueOf(13))) {
            zzb.zza($r1, 13, $r0.zzbgd, true);
        }
        if ($r2.contains(Integer.valueOf(14))) {
            zzb.zzc($r1, 14, $r0.zzbfe, true);
        }
        if ($r2.contains(Integer.valueOf(15))) {
            zzb.zzc($r1, 15, $r0.aOF, true);
        }
        if ($r2.contains(Integer.valueOf(16))) {
            zzb.zza($r1, 16, $r0.zzcuw, true);
        }
        if ($r2.contains(Integer.valueOf(17))) {
            zzb.zza($r1, 17, $r0.aOG, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(18))) {
            zzb.zzc($r1, 18, $r0.aOH, true);
        }
        if ($r2.contains(Integer.valueOf(19))) {
            zzb.zzc($r1, 19, $r0.aOI, true);
        }
        if ($r2.contains(Integer.valueOf(20))) {
            zzb.zza($r1, 20, $r0.aOJ, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(21))) {
            zzb.zzc($r1, 21, $r0.aOK, true);
        }
        if ($r2.contains(Integer.valueOf(22))) {
            zzb.zzc($r1, 22, $r0.aOL, true);
        }
        if ($r2.contains(Integer.valueOf(23))) {
            zzb.zzc($r1, 23, $r0.aOM, true);
        }
        if ($r2.contains(Integer.valueOf(24))) {
            zzb.zzc($r1, 24, $r0.aON, true);
        }
        if ($r2.contains(Integer.valueOf(25))) {
            zzb.zzc($r1, 25, $r0.aOO, true);
        }
        if ($r2.contains(Integer.valueOf(26))) {
            zzb.zzc($r1, 26, $r0.aOP, true);
        }
        if ($r2.contains(Integer.valueOf(27))) {
            zzb.zza($r1, 27, $r0.aOQ, true);
        }
        if ($r2.contains(Integer.valueOf(28))) {
            zzb.zzc($r1, 28, $r0.aOR, true);
        }
        if ($r2.contains(Integer.valueOf(29))) {
            zzb.zzc($r1, 29, $r0.aOS, true);
        }
        if ($r2.contains(Integer.valueOf(30))) {
            zzb.zzc($r1, 30, $r0.aOT, true);
        }
        if ($r2.contains(Integer.valueOf(31))) {
            zzb.zzc($r1, 31, $r0.aOU, true);
        }
        if ($r2.contains(Integer.valueOf(32))) {
            zzb.zza($r1, 32, $r0.aOV, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(33))) {
            zzb.zzc($r1, 33, $r0.aOW, true);
        }
        if ($r2.contains(Integer.valueOf(34))) {
            zzb.zzc($r1, 34, $r0.aOX, true);
        }
        if ($r2.contains(Integer.valueOf(35))) {
            zzb.zzc($r1, 35, $r0.aOY, true);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zztf($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzabx($i0);
    }

    public PersonImpl[] zzabx(int $i0) throws  {
        return new PersonImpl[$i0];
    }

    public com.google.android.gms.people.identity.internal.models.PersonImpl zztf(android.os.Parcel r88) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:10:0x0095 in {6, 7, 9, 11, 13, 14, 16, 18, 25, 28, 31, 34, 37, 40, 43, 46, 49, 52, 55, 58, 61, 64, 67, 70, 73, 76, 79, 82, 85, 88, 91, 94, 97, 100, 103, 106, 111, 113} preds:[]
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
        r87 = this;
        r0 = r88;
        r37 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r38 = new java.util.HashSet;
        r0 = r38;
        r0.<init>();
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
        r54 = 0;
        r55 = 0;
        r56 = 0;
        r57 = 0;
        r58 = 0;
        r59 = 0;
        r60 = 0;
        r61 = 0;
        r62 = 0;
        r63 = 0;
        r64 = 0;
        r65 = 0;
        r66 = 0;
        r67 = 0;
        r68 = 0;
        r69 = 0;
        r70 = 0;
        r71 = 0;
        r72 = 0;
        r73 = 0;
    L_0x0053:
        r0 = r88;
        r74 = r0.dataPosition();
        r0 = r74;
        r1 = r37;
        if (r0 >= r1) goto L_0x04c9;
    L_0x005f:
        r0 = r88;
        r74 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r74;
        r75 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r75) {
            case 1: goto L_0x0077;
            case 2: goto L_0x008f;
            case 3: goto L_0x00b7;
            case 4: goto L_0x00db;
            case 5: goto L_0x00f3;
            case 6: goto L_0x010f;
            case 7: goto L_0x012b;
            case 8: goto L_0x0147;
            case 9: goto L_0x0167;
            case 10: goto L_0x0187;
            case 11: goto L_0x01a3;
            case 12: goto L_0x01c3;
            case 13: goto L_0x01e3;
            case 14: goto L_0x01ff;
            case 15: goto L_0x021f;
            case 16: goto L_0x023f;
            case 17: goto L_0x025b;
            case 18: goto L_0x0281;
            case 19: goto L_0x02a1;
            case 20: goto L_0x02c1;
            case 21: goto L_0x02e7;
            case 22: goto L_0x0307;
            case 23: goto L_0x0327;
            case 24: goto L_0x0347;
            case 25: goto L_0x0367;
            case 26: goto L_0x0387;
            case 27: goto L_0x03a7;
            case 28: goto L_0x03c3;
            case 29: goto L_0x03e3;
            case 30: goto L_0x0403;
            case 31: goto L_0x0423;
            case 32: goto L_0x0443;
            case 33: goto L_0x0469;
            case 34: goto L_0x0489;
            case 35: goto L_0x04a9;
            default: goto L_0x006e;
        };
    L_0x006e:
        goto L_0x006f;
    L_0x006f:
        r0 = r88;
        r1 = r74;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x0053;
    L_0x0077:
        r0 = r88;
        r1 = r74;
        r39 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r77 = 1;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x0053;
    L_0x008f:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.AboutsImpl.CREATOR;
        goto L_0x0099;
    L_0x0092:
        goto L_0x0053;
        goto L_0x0099;
    L_0x0096:
        goto L_0x0053;
    L_0x0099:
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r40 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 2;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x00af;
    L_0x00ac:
        goto L_0x0053;
    L_0x00af:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x0053;
    L_0x00b7:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.AddressesImpl.CREATOR;
        goto L_0x00bd;
    L_0x00ba:
        goto L_0x0053;
    L_0x00bd:
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r41 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        goto L_0x00cb;
    L_0x00c8:
        goto L_0x0053;
    L_0x00cb:
        r77 = 3;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x0096;
    L_0x00db:
        r0 = r88;
        r1 = r74;
        r42 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r77 = 4;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x0092;
    L_0x00f3:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.BirthdaysImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r43 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 5;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x00ac;
    L_0x010f:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.BraggingRightsImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r44 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 6;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x00ba;
    L_0x012b:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.CoverPhotosImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r45 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 7;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x00c8;
    L_0x0147:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.CustomFieldsImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r46 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 8;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x015f;
    L_0x015c:
        goto L_0x0053;
    L_0x015f:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x015c;
    L_0x0167:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.EmailsImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r47 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 9;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x017f;
    L_0x017c:
        goto L_0x0053;
    L_0x017f:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x017c;
    L_0x0187:
        r0 = r88;
        r1 = r74;
        r48 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r77 = 10;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x019b;
    L_0x0198:
        goto L_0x0053;
    L_0x019b:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x0198;
    L_0x01a3:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.EventsImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r49 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 11;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x01bb;
    L_0x01b8:
        goto L_0x0053;
    L_0x01bb:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x01b8;
    L_0x01c3:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.GendersImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r50 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 12;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x01db;
    L_0x01d8:
        goto L_0x0053;
    L_0x01db:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x01d8;
    L_0x01e3:
        r0 = r88;
        r1 = r74;
        r51 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r77 = 13;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x01f7;
    L_0x01f4:
        goto L_0x0053;
    L_0x01f7:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x01f4;
    L_0x01ff:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.ImagesImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r52 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 14;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x0217;
    L_0x0214:
        goto L_0x0053;
    L_0x0217:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x0214;
    L_0x021f:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.InstantMessagingImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r53 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 15;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x0237;
    L_0x0234:
        goto L_0x0053;
    L_0x0237:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x0234;
    L_0x023f:
        r0 = r88;
        r1 = r74;
        r54 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r77 = 16;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x0253;
    L_0x0250:
        goto L_0x0053;
    L_0x0253:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x0250;
    L_0x025b:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.LegacyFieldsImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r79 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r77 = 17;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x027a;
    L_0x0277:
        goto L_0x0053;
    L_0x027a:
        r80 = r79;
        r80 = (com.google.android.gms.people.identity.internal.models.PersonImpl.LegacyFieldsImpl) r80;
        r55 = r80;
        goto L_0x0277;
    L_0x0281:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r56 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 18;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x0299;
    L_0x0296:
        goto L_0x0053;
    L_0x0299:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x0296;
    L_0x02a1:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.MembershipsImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r57 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 19;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x02b9;
    L_0x02b6:
        goto L_0x0053;
    L_0x02b9:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x02b6;
    L_0x02c1:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.PersonMetadataImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r79 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r77 = 20;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x02e0;
    L_0x02dd:
        goto L_0x0053;
    L_0x02e0:
        r81 = r79;
        r81 = (com.google.android.gms.people.identity.internal.models.PersonImpl.PersonMetadataImpl) r81;
        r58 = r81;
        goto L_0x02dd;
    L_0x02e7:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.NamesImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r59 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 21;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x02ff;
    L_0x02fc:
        goto L_0x0053;
    L_0x02ff:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x02fc;
    L_0x0307:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.NicknamesImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r60 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 22;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x031f;
    L_0x031c:
        goto L_0x0053;
    L_0x031f:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x031c;
    L_0x0327:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.OccupationsImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r61 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 23;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x033f;
    L_0x033c:
        goto L_0x0053;
    L_0x033f:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x033c;
    L_0x0347:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.OrganizationsImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r62 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 24;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x035f;
    L_0x035c:
        goto L_0x0053;
    L_0x035f:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x035c;
    L_0x0367:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.PhoneNumbersImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r63 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 25;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x037f;
    L_0x037c:
        goto L_0x0053;
    L_0x037f:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x037c;
    L_0x0387:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.PlacesLivedImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r64 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 26;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x039f;
    L_0x039c:
        goto L_0x0053;
    L_0x039f:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x039c;
    L_0x03a7:
        r0 = r88;
        r1 = r74;
        r65 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r77 = 27;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x03bb;
    L_0x03b8:
        goto L_0x0053;
    L_0x03bb:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x03b8;
    L_0x03c3:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.RelationsImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r66 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 28;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x03db;
    L_0x03d8:
        goto L_0x0053;
    L_0x03db:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x03d8;
    L_0x03e3:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.RelationshipInterestsImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r67 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 29;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x03fb;
    L_0x03f8:
        goto L_0x0053;
    L_0x03fb:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x03f8;
    L_0x0403:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.RelationshipStatusesImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r68 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 30;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x041b;
    L_0x0418:
        goto L_0x0053;
    L_0x041b:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x0418;
    L_0x0423:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.SkillsImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r69 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 31;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x043b;
    L_0x0438:
        goto L_0x0053;
    L_0x043b:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x0438;
    L_0x0443:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.SortKeysImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r79 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r77 = 32;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x0462;
    L_0x045f:
        goto L_0x0053;
    L_0x0462:
        r82 = r79;
        r82 = (com.google.android.gms.people.identity.internal.models.PersonImpl.SortKeysImpl) r82;
        r70 = r82;
        goto L_0x045f;
    L_0x0469:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.TaglinesImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r71 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 33;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x0481;
    L_0x047e:
        goto L_0x0053;
    L_0x0481:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x047e;
    L_0x0489:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.UrlsImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r72 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 34;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x04a1;
    L_0x049e:
        goto L_0x0053;
    L_0x04a1:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x049e;
    L_0x04a9:
        r78 = com.google.android.gms.people.identity.internal.models.PersonImpl.NotesImpl.CREATOR;
        r0 = r88;
        r1 = r74;
        r2 = r78;
        r73 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        r77 = 35;
        r0 = r77;
        r76 = java.lang.Integer.valueOf(r0);
        goto L_0x04c1;
    L_0x04be:
        goto L_0x0053;
    L_0x04c1:
        r0 = r38;
        r1 = r76;
        r0.add(r1);
        goto L_0x04be;
    L_0x04c9:
        r0 = r88;
        r74 = r0.dataPosition();
        r0 = r74;
        r1 = r37;
        if (r0 == r1) goto L_0x0504;
    L_0x04d5:
        r83 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r84 = new java.lang.StringBuilder;
        r77 = 37;
        r0 = r84;
        r1 = r77;
        r0.<init>(r1);
        r85 = "Overread allowed size end=";
        r0 = r84;
        r1 = r85;
        r84 = r0.append(r1);
        r0 = r84;
        r1 = r37;
        r84 = r0.append(r1);
        r0 = r84;
        r42 = r0.toString();
        r0 = r83;
        r1 = r42;
        r2 = r88;
        r0.<init>(r1, r2);
        throw r83;
    L_0x0504:
        r86 = new com.google.android.gms.people.identity.internal.models.PersonImpl;
        r0 = r86;
        r1 = r38;
        r2 = r39;
        r3 = r40;
        r4 = r41;
        r5 = r42;
        r6 = r43;
        r7 = r44;
        r8 = r45;
        r9 = r46;
        r10 = r47;
        r11 = r48;
        r12 = r49;
        r13 = r50;
        r14 = r51;
        r15 = r52;
        r16 = r53;
        r17 = r54;
        r18 = r55;
        r19 = r56;
        r20 = r57;
        r21 = r58;
        r22 = r59;
        r23 = r60;
        r24 = r61;
        r25 = r62;
        r26 = r63;
        r27 = r64;
        r28 = r65;
        r29 = r66;
        r30 = r67;
        r31 = r68;
        r32 = r69;
        r33 = r70;
        r34 = r71;
        r35 = r72;
        r36 = r73;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33, r34, r35, r36);
        return r86;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.people.identity.internal.models.zzw.zztf(android.os.Parcel):com.google.android.gms.people.identity.internal.models.PersonImpl");
    }
}
