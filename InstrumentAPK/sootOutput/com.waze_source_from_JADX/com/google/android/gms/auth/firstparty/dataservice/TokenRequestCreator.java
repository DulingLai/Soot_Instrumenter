package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class TokenRequestCreator implements Creator<TokenRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(TokenRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zza($r1, 2, $r0.hz, false);
        zzb.zza($r1, 3, $r0.accountName, false);
        zzb.zza($r1, 4, $r0.options, false);
        zzb.zza($r1, 5, $r0.hA, $i0, false);
        zzb.zza($r1, 6, $r0.hB, $i0, false);
        zzb.zza($r1, 7, $r0.hp);
        zzb.zza($r1, 8, $r0.gK);
        zzb.zza($r1, 9, $r0.hC, false);
        zzb.zza($r1, 10, $r0.callingAppDescription, $i0, false);
        zzb.zza($r1, 11, $r0.gq, $i0, false);
        zzb.zza($r1, 13, $r0.hD);
        zzb.zza($r1, 14, $r0.hE);
        zzb.zza($r1, 15, $r0.accountType, false);
        zzb.zzc($r1, 16, $r0.hF);
        zzb.zza($r1, 17, $r0.hG, false);
        zzb.zza($r1, 18, $r0.hH, false);
        zzb.zzaj($r1, $i1);
    }

    public com.google.android.gms.auth.firstparty.dataservice.TokenRequest createFromParcel(android.os.Parcel r54) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:10:0x0072
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
        r53 = this;
        r0 = r54;
        r18 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r19 = 0;
        r20 = 0;
        r21 = 0;
        r22 = new android.os.Bundle;
        r0 = r22;
        r0.<init>();
        r23 = 0;
        r24 = 0;
        r25 = 0;
        r26 = 0;
        r27 = com.google.android.gms.auth.firstparty.dataservice.TokenRequest.Consent.UNKNOWN;
        r0 = r27;
        r28 = r0.toString();
        r29 = 0;
        r30 = 0;
        r31 = 0;
        r32 = 1;
        r33 = "com.google";
        r34 = 0;
        r35 = 0;
        r36 = 0;
    L_0x0033:
        r0 = r54;
        r37 = r0.dataPosition();
        r0 = r37;
        r1 = r18;
        if (r0 >= r1) goto L_0x0148;
    L_0x003f:
        r0 = r54;
        r37 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r37;
        r38 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r38) {
            case 1: goto L_0x0057;
            case 2: goto L_0x0060;
            case 3: goto L_0x0069;
            case 4: goto L_0x0082;
            case 5: goto L_0x008b;
            case 6: goto L_0x00b2;
            case 7: goto L_0x00c5;
            case 8: goto L_0x00ce;
            case 9: goto L_0x00d7;
            case 10: goto L_0x00e0;
            case 11: goto L_0x00f3;
            case 12: goto L_0x004f;
            case 13: goto L_0x0106;
            case 14: goto L_0x010f;
            case 15: goto L_0x0118;
            case 16: goto L_0x0125;
            case 17: goto L_0x0132;
            case 18: goto L_0x013f;
            default: goto L_0x004e;
        };
    L_0x004e:
        goto L_0x004f;
    L_0x004f:
        r0 = r54;
        r1 = r37;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x0033;
    L_0x0057:
        r0 = r54;
        r1 = r37;
        r19 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        goto L_0x0033;
    L_0x0060:
        r0 = r54;
        r1 = r37;
        r20 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0033;
    L_0x0069:
        r0 = r54;
        r1 = r37;
        r21 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0033;
        goto L_0x0082;
        goto L_0x007b;
        goto L_0x0078;
    L_0x0075:
        goto L_0x0033;
    L_0x0078:
        goto L_0x0033;
    L_0x007b:
        goto L_0x0033;
        goto L_0x0082;
    L_0x007f:
        goto L_0x0033;
    L_0x0082:
        r0 = r54;
        r1 = r37;
        r22 = com.google.android.gms.common.internal.safeparcel.zza.zzs(r0, r1);
        goto L_0x0033;
    L_0x008b:
        r39 = com.google.android.gms.auth.firstparty.shared.FACLConfig.CREATOR;
        goto L_0x0095;
    L_0x008e:
        goto L_0x0033;
        goto L_0x0095;
    L_0x0092:
        goto L_0x0033;
    L_0x0095:
        r0 = r54;
        r1 = r37;
        r2 = r39;
        r40 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        goto L_0x00ab;
    L_0x00a0:
        goto L_0x0033;
        goto L_0x00ab;
    L_0x00a4:
        goto L_0x0033;
        goto L_0x00ab;
    L_0x00a8:
        goto L_0x0033;
    L_0x00ab:
        r41 = r40;
        r41 = (com.google.android.gms.auth.firstparty.shared.FACLConfig) r41;
        r23 = r41;
        goto L_0x0033;
    L_0x00b2:
        r42 = com.google.android.gms.auth.firstparty.shared.PACLConfig.CREATOR;
        r0 = r54;
        r1 = r37;
        r2 = r42;
        r40 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r43 = r40;
        r43 = (com.google.android.gms.auth.firstparty.shared.PACLConfig) r43;
        r24 = r43;
        goto L_0x0075;
    L_0x00c5:
        r0 = r54;
        r1 = r37;
        r25 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x0078;
    L_0x00ce:
        r0 = r54;
        r1 = r37;
        r26 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x007f;
    L_0x00d7:
        r0 = r54;
        r1 = r37;
        r28 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x007b;
    L_0x00e0:
        r44 = com.google.android.gms.auth.firstparty.shared.AppDescription.CREATOR;
        r0 = r54;
        r1 = r37;
        r2 = r44;
        r40 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r45 = r40;
        r45 = (com.google.android.gms.auth.firstparty.shared.AppDescription) r45;
        r29 = r45;
        goto L_0x008e;
    L_0x00f3:
        r46 = com.google.android.gms.auth.firstparty.shared.CaptchaSolution.CREATOR;
        r0 = r54;
        r1 = r37;
        r2 = r46;
        r40 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r47 = r40;
        r47 = (com.google.android.gms.auth.firstparty.shared.CaptchaSolution) r47;
        r30 = r47;
        goto L_0x0092;
    L_0x0106:
        r0 = r54;
        r1 = r37;
        r31 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x00a0;
    L_0x010f:
        r0 = r54;
        r1 = r37;
        r32 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x00a4;
    L_0x0118:
        r0 = r54;
        r1 = r37;
        r33 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x00a8;
        goto L_0x0125;
    L_0x0122:
        goto L_0x0033;
    L_0x0125:
        r0 = r54;
        r1 = r37;
        r34 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        goto L_0x0122;
        goto L_0x0132;
    L_0x012f:
        goto L_0x0033;
    L_0x0132:
        r0 = r54;
        r1 = r37;
        r35 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x012f;
        goto L_0x013f;
    L_0x013c:
        goto L_0x0033;
    L_0x013f:
        r0 = r54;
        r1 = r37;
        r36 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x013c;
    L_0x0148:
        r0 = r54;
        r37 = r0.dataPosition();
        r0 = r37;
        r1 = r18;
        if (r0 == r1) goto L_0x0183;
    L_0x0154:
        r48 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r49 = new java.lang.StringBuilder;
        r50 = 37;
        r0 = r49;
        r1 = r50;
        r0.<init>(r1);
        r51 = "Overread allowed size end=";
        r0 = r49;
        r1 = r51;
        r49 = r0.append(r1);
        r0 = r49;
        r1 = r18;
        r49 = r0.append(r1);
        r0 = r49;
        r20 = r0.toString();
        r0 = r48;
        r1 = r20;
        r2 = r54;
        r0.<init>(r1, r2);
        throw r48;
    L_0x0183:
        r52 = new com.google.android.gms.auth.firstparty.dataservice.TokenRequest;
        r0 = r52;
        r1 = r19;
        r2 = r20;
        r3 = r21;
        r4 = r22;
        r5 = r23;
        r6 = r24;
        r7 = r25;
        r8 = r26;
        r9 = r28;
        r10 = r29;
        r11 = r30;
        r12 = r31;
        r13 = r32;
        r14 = r33;
        r15 = r34;
        r16 = r35;
        r17 = r36;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17);
        return r52;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.firstparty.dataservice.TokenRequestCreator.createFromParcel(android.os.Parcel):com.google.android.gms.auth.firstparty.dataservice.TokenRequest");
    }

    public TokenRequest[] newArray(int $i0) throws  {
        return new TokenRequest[$i0];
    }
}
