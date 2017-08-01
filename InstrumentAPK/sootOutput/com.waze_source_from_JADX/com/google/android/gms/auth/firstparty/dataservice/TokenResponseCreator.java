package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class TokenResponseCreator implements Creator<TokenResponse> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(TokenResponse $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zza($r1, 2, $r0.accountName, false);
        zzb.zza($r1, 3, $r0.gs, false);
        zzb.zza($r1, 4, $r0.gN, false);
        zzb.zza($r1, 5, $r0.hJ, false);
        zzb.zza($r1, 6, $r0.gu, false);
        zzb.zza($r1, 7, $r0.hK, false);
        zzb.zza($r1, 8, $r0.firstName, false);
        zzb.zza($r1, 9, $r0.lastName, false);
        zzb.zza($r1, 10, $r0.hL);
        zzb.zza($r1, 11, $r0.hM);
        zzb.zza($r1, 12, $r0.hN);
        zzb.zza($r1, 13, $r0.hO);
        zzb.zza($r1, 14, $r0.gv, $i0, false);
        zzb.zzc($r1, 15, $r0.hP, false);
        zzb.zza($r1, 16, $r0.hv, false);
        zzb.zza($r1, 17, $r0.hq, false);
        zzb.zza($r1, 19, $r0.hQ);
        zzb.zzc($r1, 20, $r0.title);
        zzb.zza($r1, 21, $r0.hR, $i0, false);
        zzb.zza($r1, 22, $r0.account, $i0, false);
        zzb.zza($r1, 26, $r0.hS, false);
        zzb.zza($r1, 27, $r0.hT, $i0, false);
        zzb.zza($r1, 28, $r0.hU, false);
        zzb.zza($r1, 29, $r0.hH, false);
        zzb.zzaj($r1, $i1);
    }

    public com.google.android.gms.auth.firstparty.dataservice.TokenResponse createFromParcel(android.os.Parcel r70) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:10:0x0081
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
        r69 = this;
        r0 = r70;
        r26 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
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
        r38 = 0;
        r39 = 0;
        r40 = 0;
        r41 = new java.util.ArrayList;
        r0 = r41;
        r0.<init>();
        r42 = 0;
        r43 = 0;
        r44 = 0;
        r45 = 0;
        r46 = 0;
        r47 = 0;
        r48 = 0;
        r49 = 0;
        r50 = new android.os.Bundle;
        r0 = r50;
        r0.<init>();
        r51 = 0;
    L_0x0042:
        r0 = r70;
        r52 = r0.dataPosition();
        r0 = r52;
        r1 = r26;
        if (r0 >= r1) goto L_0x01c0;
    L_0x004e:
        r0 = r70;
        r52 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r52;
        r53 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r53) {
            case 1: goto L_0x0066;
            case 2: goto L_0x006f;
            case 3: goto L_0x0078;
            case 4: goto L_0x0095;
            case 5: goto L_0x00aa;
            case 6: goto L_0x00bb;
            case 7: goto L_0x00c5;
            case 8: goto L_0x00ce;
            case 9: goto L_0x00d7;
            case 10: goto L_0x00e0;
            case 11: goto L_0x00e9;
            case 12: goto L_0x00f2;
            case 13: goto L_0x00fb;
            case 14: goto L_0x0104;
            case 15: goto L_0x0117;
            case 16: goto L_0x0124;
            case 17: goto L_0x0131;
            case 18: goto L_0x005e;
            case 19: goto L_0x013e;
            case 20: goto L_0x014b;
            case 21: goto L_0x0154;
            case 22: goto L_0x016b;
            case 23: goto L_0x005e;
            case 24: goto L_0x005e;
            case 25: goto L_0x005e;
            case 26: goto L_0x0186;
            case 27: goto L_0x018f;
            case 28: goto L_0x01aa;
            case 29: goto L_0x01b7;
            default: goto L_0x005d;
        };
    L_0x005d:
        goto L_0x005e;
    L_0x005e:
        r0 = r70;
        r1 = r52;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x0042;
    L_0x0066:
        r0 = r70;
        r1 = r52;
        r27 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        goto L_0x0042;
    L_0x006f:
        r0 = r70;
        r1 = r52;
        r28 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0042;
    L_0x0078:
        r0 = r70;
        r1 = r52;
        r29 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0042;
        goto L_0x0095;
        goto L_0x0086;
    L_0x0083:
        goto L_0x0042;
    L_0x0086:
        goto L_0x0042;
        goto L_0x008d;
    L_0x008a:
        goto L_0x0042;
    L_0x008d:
        goto L_0x0091;
    L_0x008e:
        goto L_0x0042;
    L_0x0091:
        goto L_0x0095;
    L_0x0092:
        goto L_0x0042;
    L_0x0095:
        r0 = r70;
        r1 = r52;
        r30 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0042;
        goto L_0x00aa;
    L_0x009f:
        goto L_0x0042;
        goto L_0x00aa;
    L_0x00a3:
        goto L_0x0042;
        goto L_0x00aa;
    L_0x00a7:
        goto L_0x0042;
    L_0x00aa:
        r0 = r70;
        r1 = r52;
        r31 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0042;
        goto L_0x00bb;
    L_0x00b4:
        goto L_0x0042;
        goto L_0x00bb;
    L_0x00b8:
        goto L_0x0042;
    L_0x00bb:
        r0 = r70;
        r1 = r52;
        r32 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0042;
    L_0x00c5:
        r0 = r70;
        r1 = r52;
        r33 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0083;
    L_0x00ce:
        r0 = r70;
        r1 = r52;
        r34 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x008a;
    L_0x00d7:
        r0 = r70;
        r1 = r52;
        r35 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x008e;
    L_0x00e0:
        r0 = r70;
        r1 = r52;
        r36 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x0092;
    L_0x00e9:
        r0 = r70;
        r1 = r52;
        r37 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x0086;
    L_0x00f2:
        r0 = r70;
        r1 = r52;
        r38 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x009f;
    L_0x00fb:
        r0 = r70;
        r1 = r52;
        r39 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x00a3;
    L_0x0104:
        r54 = com.google.android.gms.auth.firstparty.shared.CaptchaChallenge.CREATOR;
        r0 = r70;
        r1 = r52;
        r2 = r54;
        r55 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r56 = r55;
        r56 = (com.google.android.gms.auth.firstparty.shared.CaptchaChallenge) r56;
        r40 = r56;
        goto L_0x00a7;
    L_0x0117:
        r57 = com.google.android.gms.auth.firstparty.shared.ScopeDetail.CREATOR;
        r0 = r70;
        r1 = r52;
        r2 = r57;
        r41 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        goto L_0x00b4;
    L_0x0124:
        r0 = r70;
        r1 = r52;
        r42 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x00b8;
        goto L_0x0131;
    L_0x012e:
        goto L_0x0042;
    L_0x0131:
        r0 = r70;
        r1 = r52;
        r43 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x012e;
        goto L_0x013e;
    L_0x013b:
        goto L_0x0042;
    L_0x013e:
        r0 = r70;
        r1 = r52;
        r44 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x013b;
        goto L_0x014b;
    L_0x0148:
        goto L_0x0042;
    L_0x014b:
        r0 = r70;
        r1 = r52;
        r45 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        goto L_0x0148;
    L_0x0154:
        r58 = com.google.android.gms.auth.firstparty.dataservice.PostSignInData.CREATOR;
        r0 = r70;
        r1 = r52;
        r2 = r58;
        r55 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        goto L_0x0164;
    L_0x0161:
        goto L_0x0042;
    L_0x0164:
        r59 = r55;
        r59 = (com.google.android.gms.auth.firstparty.dataservice.PostSignInData) r59;
        r46 = r59;
        goto L_0x0161;
    L_0x016b:
        r60 = android.accounts.Account.CREATOR;
        r0 = r70;
        r1 = r52;
        r2 = r60;
        r55 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        goto L_0x017b;
    L_0x0178:
        goto L_0x0042;
    L_0x017b:
        r61 = r55;
        r61 = (android.accounts.Account) r61;
        r47 = r61;
        goto L_0x0178;
        goto L_0x0186;
    L_0x0183:
        goto L_0x0042;
    L_0x0186:
        r0 = r70;
        r1 = r52;
        r48 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0183;
    L_0x018f:
        r62 = com.google.android.gms.auth.TokenData.CREATOR;
        r0 = r70;
        r1 = r52;
        r2 = r62;
        r55 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        goto L_0x019f;
    L_0x019c:
        goto L_0x0042;
    L_0x019f:
        r63 = r55;
        r63 = (com.google.android.gms.auth.TokenData) r63;
        r49 = r63;
        goto L_0x019c;
        goto L_0x01aa;
    L_0x01a7:
        goto L_0x0042;
    L_0x01aa:
        r0 = r70;
        r1 = r52;
        r50 = com.google.android.gms.common.internal.safeparcel.zza.zzs(r0, r1);
        goto L_0x01a7;
        goto L_0x01b7;
    L_0x01b4:
        goto L_0x0042;
    L_0x01b7:
        r0 = r70;
        r1 = r52;
        r51 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x01b4;
    L_0x01c0:
        r0 = r70;
        r52 = r0.dataPosition();
        r0 = r52;
        r1 = r26;
        if (r0 == r1) goto L_0x01fb;
    L_0x01cc:
        r64 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r65 = new java.lang.StringBuilder;
        r66 = 37;
        r0 = r65;
        r1 = r66;
        r0.<init>(r1);
        r67 = "Overread allowed size end=";
        r0 = r65;
        r1 = r67;
        r65 = r0.append(r1);
        r0 = r65;
        r1 = r26;
        r65 = r0.append(r1);
        r0 = r65;
        r28 = r0.toString();
        r0 = r64;
        r1 = r28;
        r2 = r70;
        r0.<init>(r1, r2);
        throw r64;
    L_0x01fb:
        r68 = new com.google.android.gms.auth.firstparty.dataservice.TokenResponse;
        r0 = r68;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r4 = r30;
        r5 = r31;
        r6 = r32;
        r7 = r33;
        r8 = r34;
        r9 = r35;
        r10 = r36;
        r11 = r37;
        r12 = r38;
        r13 = r39;
        r14 = r40;
        r15 = r41;
        r16 = r42;
        r17 = r43;
        r18 = r44;
        r19 = r45;
        r20 = r46;
        r21 = r47;
        r22 = r48;
        r23 = r49;
        r24 = r50;
        r25 = r51;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25);
        return r68;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.firstparty.dataservice.TokenResponseCreator.createFromParcel(android.os.Parcel):com.google.android.gms.auth.firstparty.dataservice.TokenResponse");
    }

    public TokenResponse[] newArray(int $i0) throws  {
        return new TokenResponse[$i0];
    }
}
