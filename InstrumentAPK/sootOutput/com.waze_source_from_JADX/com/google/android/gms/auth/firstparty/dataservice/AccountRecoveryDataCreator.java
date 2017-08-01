package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountRecoveryDataCreator implements Creator<AccountRecoveryData> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(AccountRecoveryData $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zza($r1, 2, $r0.guidance, $i0, false);
        zzb.zza($r1, 3, $r0.action, false);
        zzb.zza($r1, 4, $r0.allowedRecoveryOption, false);
        zzb.zza($r1, 5, $r0.accountName, false);
        zzb.zza($r1, 6, $r0.secondaryEmail, false);
        zzb.zza($r1, 7, $r0.phoneNumber, false);
        zzb.zzc($r1, 8, $r0.countries, false);
        zzb.zza($r1, 9, $r0.defaultCountryCode, false);
        zzb.zza($r1, 10, $r0.error, false);
        zzb.zza($r1, 11, $r0.account, $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public com.google.android.gms.auth.firstparty.dataservice.AccountRecoveryData createFromParcel(android.os.Parcel r38) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:9:0x0057
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
        r37 = this;
        r12 = 0;
        r0 = r38;
        r13 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r14 = 0;
        r15 = 0;
        r16 = 0;
        r17 = 0;
        r18 = 0;
        r19 = 0;
        r20 = 0;
        r21 = 0;
        r22 = 0;
        r23 = 0;
    L_0x0019:
        r0 = r38;
        r24 = r0.dataPosition();
        r0 = r24;
        if (r0 >= r13) goto L_0x00c6;
    L_0x0023:
        r0 = r38;
        r24 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r24;
        r25 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r25) {
            case 1: goto L_0x003b;
            case 2: goto L_0x0044;
            case 3: goto L_0x005f;
            case 4: goto L_0x0070;
            case 5: goto L_0x0079;
            case 6: goto L_0x0082;
            case 7: goto L_0x008b;
            case 8: goto L_0x0094;
            case 9: goto L_0x00a1;
            case 10: goto L_0x00aa;
            case 11: goto L_0x00b3;
            default: goto L_0x0032;
        };
    L_0x0032:
        goto L_0x0033;
    L_0x0033:
        r0 = r38;
        r1 = r24;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x0019;
    L_0x003b:
        r0 = r38;
        r1 = r24;
        r14 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        goto L_0x0019;
    L_0x0044:
        r26 = com.google.android.gms.auth.firstparty.dataservice.AccountRecoveryGuidance.CREATOR;
        r0 = r38;
        r1 = r24;
        r2 = r26;
        r27 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r28 = r27;
        r28 = (com.google.android.gms.auth.firstparty.dataservice.AccountRecoveryGuidance) r28;
        r23 = r28;
        goto L_0x0019;
        goto L_0x005f;
    L_0x0058:
        goto L_0x0019;
        goto L_0x005f;
    L_0x005c:
        goto L_0x0019;
    L_0x005f:
        r0 = r38;
        r1 = r24;
        r22 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0019;
        goto L_0x0070;
    L_0x0069:
        goto L_0x0019;
        goto L_0x0070;
    L_0x006d:
        goto L_0x0019;
    L_0x0070:
        r0 = r38;
        r1 = r24;
        r21 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0019;
    L_0x0079:
        r0 = r38;
        r1 = r24;
        r20 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0019;
    L_0x0082:
        r0 = r38;
        r1 = r24;
        r19 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0019;
    L_0x008b:
        r0 = r38;
        r1 = r24;
        r18 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0019;
    L_0x0094:
        r29 = com.google.android.gms.auth.Country.CREATOR;
        r0 = r38;
        r1 = r24;
        r2 = r29;
        r17 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        goto L_0x005c;
    L_0x00a1:
        r0 = r38;
        r1 = r24;
        r16 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0058;
    L_0x00aa:
        r0 = r38;
        r1 = r24;
        r15 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0069;
    L_0x00b3:
        r30 = android.accounts.Account.CREATOR;
        r0 = r38;
        r1 = r24;
        r2 = r30;
        r27 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r31 = r27;
        r31 = (android.accounts.Account) r31;
        r12 = r31;
        goto L_0x006d;
    L_0x00c6:
        r0 = r38;
        r24 = r0.dataPosition();
        r0 = r24;
        if (r0 == r13) goto L_0x00fb;
    L_0x00d0:
        r32 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r33 = new java.lang.StringBuilder;
        r34 = 37;
        r0 = r33;
        r1 = r34;
        r0.<init>(r1);
        r35 = "Overread allowed size end=";
        r0 = r33;
        r1 = r35;
        r33 = r0.append(r1);
        r0 = r33;
        r33 = r0.append(r13);
        r0 = r33;
        r15 = r0.toString();
        r0 = r32;
        r1 = r38;
        r0.<init>(r15, r1);
        throw r32;
    L_0x00fb:
        r36 = new com.google.android.gms.auth.firstparty.dataservice.AccountRecoveryData;
        r0 = r36;
        r1 = r14;
        r2 = r23;
        r3 = r22;
        r4 = r21;
        r5 = r20;
        r6 = r19;
        r7 = r18;
        r8 = r17;
        r9 = r16;
        r10 = r15;
        r11 = r12;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11);
        return r36;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.firstparty.dataservice.AccountRecoveryDataCreator.createFromParcel(android.os.Parcel):com.google.android.gms.auth.firstparty.dataservice.AccountRecoveryData");
    }

    public AccountRecoveryData[] newArray(int $i0) throws  {
        return new AccountRecoveryData[$i0];
    }
}
