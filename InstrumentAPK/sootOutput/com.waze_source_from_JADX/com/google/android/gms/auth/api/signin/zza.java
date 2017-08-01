package com.google.android.gms.auth.api.signin;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zza implements Creator<GoogleSignInAccount> {
    static void zza(GoogleSignInAccount $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.versionCode);
        zzb.zza($r1, 2, $r0.getId(), false);
        zzb.zza($r1, 3, $r0.getIdToken(), false);
        zzb.zza($r1, 4, $r0.getEmail(), false);
        zzb.zza($r1, 5, $r0.getDisplayName(), false);
        zzb.zza($r1, 6, $r0.getPhotoUrl(), $i0, false);
        zzb.zza($r1, 7, $r0.getServerAuthCode(), false);
        zzb.zza($r1, 8, $r0.zzaed());
        zzb.zza($r1, 9, $r0.zzaee(), false);
        zzb.zzc($r1, 10, $r0.ei, false);
        zzb.zza($r1, 11, $r0.getGivenName(), false);
        zzb.zza($r1, 12, $r0.getFamilyName(), false);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzaw($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzdc($i0);
    }

    public com.google.android.gms.auth.api.signin.GoogleSignInAccount zzaw(android.os.Parcel r39) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:10:0x005a
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
        r38 = this;
        r0 = r39;
        r14 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r15 = 0;
        r16 = 0;
        r17 = 0;
        r18 = 0;
        r19 = 0;
        r20 = 0;
        r21 = 0;
        r22 = 0;
        r24 = 0;
        r25 = 0;
        r26 = 0;
        r27 = 0;
    L_0x001d:
        r0 = r39;
        r28 = r0.dataPosition();
        r0 = r28;
        if (r0 >= r14) goto L_0x00cd;
    L_0x0027:
        r0 = r39;
        r28 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r28;
        r29 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r29) {
            case 1: goto L_0x003f;
            case 2: goto L_0x0048;
            case 3: goto L_0x0051;
            case 4: goto L_0x0062;
            case 5: goto L_0x0077;
            case 6: goto L_0x0080;
            case 7: goto L_0x0093;
            case 8: goto L_0x009c;
            case 9: goto L_0x00a5;
            case 10: goto L_0x00ae;
            case 11: goto L_0x00bb;
            case 12: goto L_0x00c4;
            default: goto L_0x0036;
        };
    L_0x0036:
        goto L_0x0037;
    L_0x0037:
        r0 = r39;
        r1 = r28;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x001d;
    L_0x003f:
        r0 = r39;
        r1 = r28;
        r15 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        goto L_0x001d;
    L_0x0048:
        r0 = r39;
        r1 = r28;
        r16 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x001d;
    L_0x0051:
        r0 = r39;
        r1 = r28;
        r17 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x001d;
        goto L_0x0062;
    L_0x005b:
        goto L_0x001d;
        goto L_0x0062;
    L_0x005f:
        goto L_0x001d;
    L_0x0062:
        r0 = r39;
        r1 = r28;
        r18 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x001d;
        goto L_0x0077;
    L_0x006c:
        goto L_0x001d;
        goto L_0x0077;
    L_0x0070:
        goto L_0x001d;
        goto L_0x0077;
    L_0x0074:
        goto L_0x001d;
    L_0x0077:
        r0 = r39;
        r1 = r28;
        r19 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x001d;
    L_0x0080:
        r30 = android.net.Uri.CREATOR;
        r0 = r39;
        r1 = r28;
        r2 = r30;
        r31 = com.google.android.gms.common.internal.safeparcel.zza.zza(r0, r1, r2);
        r32 = r31;
        r32 = (android.net.Uri) r32;
        r20 = r32;
        goto L_0x001d;
    L_0x0093:
        r0 = r39;
        r1 = r28;
        r21 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x001d;
    L_0x009c:
        r0 = r39;
        r1 = r28;
        r22 = com.google.android.gms.common.internal.safeparcel.zza.zzi(r0, r1);
        goto L_0x005f;
    L_0x00a5:
        r0 = r39;
        r1 = r28;
        r24 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x005b;
    L_0x00ae:
        r30 = com.google.android.gms.common.api.Scope.CREATOR;
        r0 = r39;
        r1 = r28;
        r2 = r30;
        r25 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1, r2);
        goto L_0x006c;
    L_0x00bb:
        r0 = r39;
        r1 = r28;
        r26 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0070;
    L_0x00c4:
        r0 = r39;
        r1 = r28;
        r27 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0074;
    L_0x00cd:
        r0 = r39;
        r28 = r0.dataPosition();
        r0 = r28;
        if (r0 == r14) goto L_0x0104;
    L_0x00d7:
        r33 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r34 = new java.lang.StringBuilder;
        r35 = 37;
        r0 = r34;
        r1 = r35;
        r0.<init>(r1);
        r36 = "Overread allowed size end=";
        r0 = r34;
        r1 = r36;
        r34 = r0.append(r1);
        r0 = r34;
        r34 = r0.append(r14);
        r0 = r34;
        r16 = r0.toString();
        r0 = r33;
        r1 = r16;
        r2 = r39;
        r0.<init>(r1, r2);
        throw r33;
    L_0x0104:
        r37 = new com.google.android.gms.auth.api.signin.GoogleSignInAccount;
        r0 = r37;
        r1 = r15;
        r2 = r16;
        r3 = r17;
        r4 = r18;
        r5 = r19;
        r6 = r20;
        r7 = r21;
        r8 = r22;
        r10 = r24;
        r11 = r25;
        r12 = r26;
        r13 = r27;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r10, r11, r12, r13);
        return r37;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.signin.zza.zzaw(android.os.Parcel):com.google.android.gms.auth.api.signin.GoogleSignInAccount");
    }

    public GoogleSignInAccount[] zzdc(int $i0) throws  {
        return new GoogleSignInAccount[$i0];
    }
}
