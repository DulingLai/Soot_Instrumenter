package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class GoogleAccountSetupRequestCreator implements Creator<GoogleAccountSetupRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(GoogleAccountSetupRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zza($r1, 2, $r0.options, false);
        zzb.zza($r1, 3, $r0.hm);
        zzb.zza($r1, 4, $r0.hn);
        zzb.zza($r1, 5, $r0.ho);
        zzb.zza($r1, 6, $r0.firstName, false);
        zzb.zza($r1, 7, $r0.lastName, false);
        zzb.zza($r1, 8, $r0.secondaryEmail, false);
        zzb.zza($r1, 9, $r0.gender, false);
        zzb.zza($r1, 10, $r0.gK);
        zzb.zza($r1, 11, $r0.hp);
        zzb.zza($r1, 12, $r0.gL);
        zzb.zza($r1, 13, $r0.hq, false);
        zzb.zza($r1, 14, $r0.callingAppDescription, $i0, false);
        zzb.zza($r1, 15, $r0.gM, $i0, false);
        zzb.zza($r1, 16, $r0.gq, $i0, false);
        zzb.zza($r1, 17, $r0.phoneNumber, false);
        zzb.zza($r1, 18, $r0.phoneCountryCode, false);
        zzb.zzaj($r1, $i1);
    }

    public com.google.android.gms.auth.firstparty.dataservice.GoogleAccountSetupRequest createFromParcel(android.os.Parcel r53) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:10:0x006e
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
        r52 = this;
        r0 = r53;
        r19 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r20 = 0;
        r21 = new android.os.Bundle;
        r0 = r21;
        r0.<init>();
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
        r34 = 0;
        r35 = 0;
        r36 = 0;
        r37 = 0;
    L_0x002f:
        r0 = r53;
        r38 = r0.dataPosition();
        r0 = r38;
        r1 = r19;
        if (r0 >= r1) goto L_0x0143;
    L_0x003b:
        r0 = r53;
        r38 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r38;
        r39 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r39) {
            case 1: goto L_0x0053;
            case 2: goto L_0x005c;
            case 3: goto L_0x0065;
            case 4: goto L_0x0082;
            case 5: goto L_0x0097;
            case 6: goto L_0x00a4;
            case 7: goto L_0x00ad;
            case 8: goto L_0x00b6;
            case 9: goto L_0x00bf;
            case 10: goto L_0x00c8;
            case 11: goto L_0x00d1;
            case 12: goto L_0x00da;
            case 13: goto L_0x00e3;
            case 14: goto L_0x00ec;
            case 15: goto L_0x00ff;
            case 16: goto L_0x0112;
            case 17: goto L_0x012d;
            case 18: goto L_0x013a;
            default: goto L_0x004a;
        };
    L_0x004a:
        goto L_0x004b;
    L_0x004b:
        r0 = r53;
        r1 = r38;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x002f;
    L_0x0053:
        r0 = r53;
        r1 = r38;
        r20 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        goto L_0x002f;
    L_0x005c:
        r0 = r53;
        r1 = r38;
        r21 = com.google.android.gms.common.internal.safeparcel.zza.zzs(r0, r1);
        goto L_0x002f;
    L_0x0065:
        r0 = r53;
        r1 = r38;
        r22 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x002f;
        goto L_0x0082;
        goto L_0x0073;
    L_0x0070:
        goto L_0x002f;
    L_0x0073:
        goto L_0x002f;
        goto L_0x007a;
    L_0x0077:
        goto L_0x002f;
    L_0x007a:
        goto L_0x007e;
    L_0x007b:
        goto L_0x002f;
    L_0x007e:
        goto L_0x0082;
    L_0x007f:
        goto L_0x002f;
    L_0x0082:
        r0 = r53;
        r1 = r38;
        r23 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x002f;
        goto L_0x0097;
    L_0x008c:
        goto L_0x002f;
        goto L_0x0097;
    L_0x0090:
        goto L_0x002f;
        goto L_0x0097;
    L_0x0094:
        goto L_0x002f;
    L_0x0097:
        r0 = r53;
        r1 = r38;
        r24 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x002f;
        goto L_0x00a4;
    L_0x00a1:
        goto L_0x002f;
    L_0x00a4:
        r0 = r53;
        r1 = r38;
        r25 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x002f;
    L_0x00ad:
        r0 = r53;
        r1 = r38;
        r26 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0070;
    L_0x00b6:
        r0 = r53;
        r1 = r38;
        r27 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0077;
    L_0x00bf:
        r0 = r53;
        r1 = r38;
        r28 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x007b;
    L_0x00c8:
        r0 = r53;
        r1 = r38;
        r29 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x007f;
    L_0x00d1:
        r0 = r53;
        r1 = r38;
        r30 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x0073;
    L_0x00da:
        r0 = r53;
        r1 = r38;
        r31 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x008c;
    L_0x00e3:
        r0 = r53;
        r1 = r38;
        r32 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0090;
    L_0x00ec:
        r40 = com.google.android.gms.auth.firstparty.shared.AppDescription.CREATOR;
        r0 = r53;
        r1 = r38;
        r2 = r40;
        r41 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r42 = r41;
        r42 = (com.google.android.gms.auth.firstparty.shared.AppDescription) r42;
        r33 = r42;
        goto L_0x0094;
    L_0x00ff:
        r43 = com.google.android.gms.auth.firstparty.shared.AccountCredentials.CREATOR;
        r0 = r53;
        r1 = r38;
        r2 = r43;
        r41 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r44 = r41;
        r44 = (com.google.android.gms.auth.firstparty.shared.AccountCredentials) r44;
        r34 = r44;
        goto L_0x00a1;
    L_0x0112:
        r45 = com.google.android.gms.auth.firstparty.shared.CaptchaSolution.CREATOR;
        r0 = r53;
        r1 = r38;
        r2 = r45;
        r41 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        goto L_0x0122;
    L_0x011f:
        goto L_0x002f;
    L_0x0122:
        r46 = r41;
        r46 = (com.google.android.gms.auth.firstparty.shared.CaptchaSolution) r46;
        r35 = r46;
        goto L_0x011f;
        goto L_0x012d;
    L_0x012a:
        goto L_0x002f;
    L_0x012d:
        r0 = r53;
        r1 = r38;
        r36 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x012a;
        goto L_0x013a;
    L_0x0137:
        goto L_0x002f;
    L_0x013a:
        r0 = r53;
        r1 = r38;
        r37 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0137;
    L_0x0143:
        r0 = r53;
        r38 = r0.dataPosition();
        r0 = r38;
        r1 = r19;
        if (r0 == r1) goto L_0x017e;
    L_0x014f:
        r47 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r48 = new java.lang.StringBuilder;
        r49 = 37;
        r0 = r48;
        r1 = r49;
        r0.<init>(r1);
        r50 = "Overread allowed size end=";
        r0 = r48;
        r1 = r50;
        r48 = r0.append(r1);
        r0 = r48;
        r1 = r19;
        r48 = r0.append(r1);
        r0 = r48;
        r25 = r0.toString();
        r0 = r47;
        r1 = r25;
        r2 = r53;
        r0.<init>(r1, r2);
        throw r47;
    L_0x017e:
        r51 = new com.google.android.gms.auth.firstparty.dataservice.GoogleAccountSetupRequest;
        r0 = r51;
        r1 = r20;
        r2 = r21;
        r3 = r22;
        r4 = r23;
        r5 = r24;
        r6 = r25;
        r7 = r26;
        r8 = r27;
        r9 = r28;
        r10 = r29;
        r11 = r30;
        r12 = r31;
        r13 = r32;
        r14 = r33;
        r15 = r34;
        r16 = r35;
        r17 = r36;
        r18 = r37;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18);
        return r51;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.firstparty.dataservice.GoogleAccountSetupRequestCreator.createFromParcel(android.os.Parcel):com.google.android.gms.auth.firstparty.dataservice.GoogleAccountSetupRequest");
    }

    public GoogleAccountSetupRequest[] newArray(int $i0) throws  {
        return new GoogleAccountSetupRequest[$i0];
    }
}
