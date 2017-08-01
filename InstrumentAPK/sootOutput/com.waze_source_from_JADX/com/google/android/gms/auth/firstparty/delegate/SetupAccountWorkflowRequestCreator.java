package com.google.android.gms.auth.firstparty.delegate;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class SetupAccountWorkflowRequestCreator implements Creator<SetupAccountWorkflowRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(SetupAccountWorkflowRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zza($r1, 2, $r0.isMultiUser);
        zzb.zza($r1, 3, $r0.isSetupWizard);
        zzb.zzb($r1, 4, $r0.allowedDomains, false);
        zzb.zza($r1, 5, $r0.options, false);
        zzb.zza($r1, 6, $r0.callingAppDescription, $i0, false);
        zzb.zza($r1, 7, $r0.isCreditCardAllowed);
        zzb.zza($r1, 8, $r0.accountType, false);
        zzb.zza($r1, 9, $r0.amResponse, $i0, false);
        zzb.zza($r1, 10, $r0.suppressD2d);
        zzb.zza($r1, 11, $r0.useImmersiveMode);
        zzb.zza($r1, 12, $r0.purchaserGaiaEmail, false);
        zzb.zza($r1, 13, $r0.purchaserName, false);
        zzb.zza($r1, 14, $r0.accountName, false);
        zzb.zza($r1, 15, $r0.loginTemplate, false);
        zzb.zzaj($r1, $i1);
    }

    public com.google.android.gms.auth.firstparty.delegate.SetupAccountWorkflowRequest createFromParcel(android.os.Parcel r45) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:10:0x0068
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
        r44 = this;
        r0 = r45;
        r16 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r17 = 0;
        r18 = 0;
        r19 = 0;
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
        r28 = "null";
        r29 = "null";
        r30 = 0;
        r31 = 0;
    L_0x0029:
        r0 = r45;
        r32 = r0.dataPosition();
        r0 = r32;
        r1 = r16;
        if (r0 >= r1) goto L_0x0110;
    L_0x0035:
        r0 = r45;
        r32 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r32;
        r33 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r33) {
            case 1: goto L_0x004d;
            case 2: goto L_0x0056;
            case 3: goto L_0x005f;
            case 4: goto L_0x0078;
            case 5: goto L_0x0091;
            case 6: goto L_0x00a2;
            case 7: goto L_0x00b5;
            case 8: goto L_0x00be;
            case 9: goto L_0x00c7;
            case 10: goto L_0x00da;
            case 11: goto L_0x00e3;
            case 12: goto L_0x00ec;
            case 13: goto L_0x00f5;
            case 14: goto L_0x00fe;
            case 15: goto L_0x0107;
            default: goto L_0x0044;
        };
    L_0x0044:
        goto L_0x0045;
    L_0x0045:
        r0 = r45;
        r1 = r32;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x0029;
    L_0x004d:
        r0 = r45;
        r1 = r32;
        r17 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        goto L_0x0029;
    L_0x0056:
        r0 = r45;
        r1 = r32;
        r18 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x0029;
    L_0x005f:
        r0 = r45;
        r1 = r32;
        r19 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x0029;
        goto L_0x0078;
        goto L_0x006d;
    L_0x006a:
        goto L_0x0029;
    L_0x006d:
        goto L_0x0029;
        goto L_0x0074;
    L_0x0071:
        goto L_0x0029;
    L_0x0074:
        goto L_0x0078;
    L_0x0075:
        goto L_0x0029;
    L_0x0078:
        r0 = r45;
        r1 = r32;
        r20 = com.google.android.gms.common.internal.safeparcel.zza.zzae(r0, r1);
        goto L_0x0029;
        goto L_0x0091;
    L_0x0082:
        goto L_0x0029;
        goto L_0x0091;
    L_0x0086:
        goto L_0x0029;
        goto L_0x0091;
    L_0x008a:
        goto L_0x0029;
        goto L_0x0091;
    L_0x008e:
        goto L_0x0029;
    L_0x0091:
        r0 = r45;
        r1 = r32;
        r21 = com.google.android.gms.common.internal.safeparcel.zza.zzs(r0, r1);
        goto L_0x00a1;
    L_0x009a:
        goto L_0x0029;
        goto L_0x00a1;
    L_0x009e:
        goto L_0x0029;
    L_0x00a1:
        goto L_0x0029;
    L_0x00a2:
        r34 = com.google.android.gms.auth.firstparty.shared.AppDescription.CREATOR;
        r0 = r45;
        r1 = r32;
        r2 = r34;
        r35 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r36 = r35;
        r36 = (com.google.android.gms.auth.firstparty.shared.AppDescription) r36;
        r22 = r36;
        goto L_0x006a;
    L_0x00b5:
        r0 = r45;
        r1 = r32;
        r23 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x0071;
    L_0x00be:
        r0 = r45;
        r1 = r32;
        r24 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0075;
    L_0x00c7:
        r37 = android.accounts.AccountAuthenticatorResponse.CREATOR;
        r0 = r45;
        r1 = r32;
        r2 = r37;
        r35 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r38 = r35;
        r38 = (android.accounts.AccountAuthenticatorResponse) r38;
        r25 = r38;
        goto L_0x006d;
    L_0x00da:
        r0 = r45;
        r1 = r32;
        r26 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x0082;
    L_0x00e3:
        r0 = r45;
        r1 = r32;
        r27 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        goto L_0x0086;
    L_0x00ec:
        r0 = r45;
        r1 = r32;
        r28 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x008a;
    L_0x00f5:
        r0 = r45;
        r1 = r32;
        r29 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x008e;
    L_0x00fe:
        r0 = r45;
        r1 = r32;
        r30 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x009a;
    L_0x0107:
        r0 = r45;
        r1 = r32;
        r31 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x009e;
    L_0x0110:
        r0 = r45;
        r32 = r0.dataPosition();
        r0 = r32;
        r1 = r16;
        if (r0 == r1) goto L_0x014b;
    L_0x011c:
        r39 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r40 = new java.lang.StringBuilder;
        r41 = 37;
        r0 = r40;
        r1 = r41;
        r0.<init>(r1);
        r42 = "Overread allowed size end=";
        r0 = r40;
        r1 = r42;
        r40 = r0.append(r1);
        r0 = r40;
        r1 = r16;
        r40 = r0.append(r1);
        r0 = r40;
        r24 = r0.toString();
        r0 = r39;
        r1 = r24;
        r2 = r45;
        r0.<init>(r1, r2);
        throw r39;
    L_0x014b:
        r43 = new com.google.android.gms.auth.firstparty.delegate.SetupAccountWorkflowRequest;
        r0 = r43;
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
        return r43;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.firstparty.delegate.SetupAccountWorkflowRequestCreator.createFromParcel(android.os.Parcel):com.google.android.gms.auth.firstparty.delegate.SetupAccountWorkflowRequest");
    }

    public SetupAccountWorkflowRequest[] newArray(int $i0) throws  {
        return new SetupAccountWorkflowRequest[$i0];
    }
}
