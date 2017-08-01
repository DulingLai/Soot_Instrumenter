package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzg implements Creator<WakeLockEvent> {
    static void zza(WakeLockEvent $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.getTimeMillis());
        zzb.zza($r1, 4, $r0.zzayl(), false);
        zzb.zzc($r1, 5, $r0.zzayo());
        zzb.zzb($r1, 6, $r0.zzayp(), false);
        zzb.zza($r1, 8, $r0.zzayh());
        zzb.zza($r1, 10, $r0.zzaym(), false);
        zzb.zzc($r1, 11, $r0.getEventType());
        zzb.zza($r1, 12, $r0.zzayf(), false);
        zzb.zza($r1, 13, $r0.zzayr(), false);
        zzb.zzc($r1, 14, $r0.zzayq());
        zzb.zza($r1, 15, $r0.zzays());
        zzb.zza($r1, 16, $r0.zzayt());
        zzb.zza($r1, 17, $r0.zzayn(), false);
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzej($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzjh($i0);
    }

    public com.google.android.gms.common.stats.WakeLockEvent zzej(android.os.Parcel r44) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:10:0x0061
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
        r18 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r19 = 0;
        r20 = 0;
        r22 = 0;
        r23 = 0;
        r24 = 0;
        r25 = 0;
        r26 = 0;
        r27 = 0;
        r29 = 0;
        r30 = 0;
        r31 = 0;
        r32 = 0;
        r33 = 0;
        r35 = 0;
    L_0x0022:
        r0 = r44;
        r36 = r0.dataPosition();
        r0 = r36;
        r1 = r18;
        if (r0 >= r1) goto L_0x00e0;
    L_0x002e:
        r0 = r44;
        r36 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r36;
        r37 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r37) {
            case 1: goto L_0x0046;
            case 2: goto L_0x004f;
            case 3: goto L_0x003e;
            case 4: goto L_0x0058;
            case 5: goto L_0x0071;
            case 6: goto L_0x0086;
            case 7: goto L_0x003e;
            case 8: goto L_0x008f;
            case 9: goto L_0x003e;
            case 10: goto L_0x0098;
            case 11: goto L_0x00a1;
            case 12: goto L_0x00aa;
            case 13: goto L_0x00b3;
            case 14: goto L_0x00bc;
            case 15: goto L_0x00c5;
            case 16: goto L_0x00ce;
            case 17: goto L_0x00d7;
            default: goto L_0x003d;
        };
    L_0x003d:
        goto L_0x003e;
    L_0x003e:
        r0 = r44;
        r1 = r36;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x0022;
    L_0x0046:
        r0 = r44;
        r1 = r36;
        r19 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        goto L_0x0022;
    L_0x004f:
        r0 = r44;
        r1 = r36;
        r20 = com.google.android.gms.common.internal.safeparcel.zza.zzi(r0, r1);
        goto L_0x0022;
    L_0x0058:
        r0 = r44;
        r1 = r36;
        r23 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0022;
        goto L_0x0071;
    L_0x0062:
        goto L_0x0022;
        goto L_0x0069;
    L_0x0066:
        goto L_0x0022;
    L_0x0069:
        goto L_0x006d;
    L_0x006a:
        goto L_0x0022;
    L_0x006d:
        goto L_0x0071;
    L_0x006e:
        goto L_0x0022;
    L_0x0071:
        r0 = r44;
        r1 = r36;
        r24 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        goto L_0x0022;
        goto L_0x0086;
    L_0x007b:
        goto L_0x0022;
        goto L_0x0086;
    L_0x007f:
        goto L_0x0022;
        goto L_0x0086;
    L_0x0083:
        goto L_0x0022;
    L_0x0086:
        r0 = r44;
        r1 = r36;
        r25 = com.google.android.gms.common.internal.safeparcel.zza.zzae(r0, r1);
        goto L_0x0022;
    L_0x008f:
        r0 = r44;
        r1 = r36;
        r27 = com.google.android.gms.common.internal.safeparcel.zza.zzi(r0, r1);
        goto L_0x0022;
    L_0x0098:
        r0 = r44;
        r1 = r36;
        r30 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0022;
    L_0x00a1:
        r0 = r44;
        r1 = r36;
        r22 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        goto L_0x0066;
    L_0x00aa:
        r0 = r44;
        r1 = r36;
        r26 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x006a;
    L_0x00b3:
        r0 = r44;
        r1 = r36;
        r31 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x006e;
    L_0x00bc:
        r0 = r44;
        r1 = r36;
        r29 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        goto L_0x0062;
    L_0x00c5:
        r0 = r44;
        r1 = r36;
        r32 = com.google.android.gms.common.internal.safeparcel.zza.zzl(r0, r1);
        goto L_0x007b;
    L_0x00ce:
        r0 = r44;
        r1 = r36;
        r33 = com.google.android.gms.common.internal.safeparcel.zza.zzi(r0, r1);
        goto L_0x007f;
    L_0x00d7:
        r0 = r44;
        r1 = r36;
        r35 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0083;
    L_0x00e0:
        r0 = r44;
        r36 = r0.dataPosition();
        r0 = r36;
        r1 = r18;
        if (r0 == r1) goto L_0x011b;
    L_0x00ec:
        r38 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r39 = new java.lang.StringBuilder;
        r40 = 37;
        r0 = r39;
        r1 = r40;
        r0.<init>(r1);
        r41 = "Overread allowed size end=";
        r0 = r39;
        r1 = r41;
        r39 = r0.append(r1);
        r0 = r39;
        r1 = r18;
        r39 = r0.append(r1);
        r0 = r39;
        r23 = r0.toString();
        r0 = r38;
        r1 = r23;
        r2 = r44;
        r0.<init>(r1, r2);
        throw r38;
    L_0x011b:
        r42 = new com.google.android.gms.common.stats.WakeLockEvent;
        r0 = r42;
        r1 = r19;
        r2 = r20;
        r4 = r22;
        r5 = r23;
        r6 = r24;
        r7 = r25;
        r8 = r26;
        r9 = r27;
        r11 = r29;
        r12 = r30;
        r13 = r31;
        r14 = r32;
        r15 = r33;
        r17 = r35;
        r0.<init>(r1, r2, r4, r5, r6, r7, r8, r9, r11, r12, r13, r14, r15, r17);
        return r42;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.stats.zzg.zzej(android.os.Parcel):com.google.android.gms.common.stats.WakeLockEvent");
    }

    public WakeLockEvent[] zzjh(int $i0) throws  {
        return new WakeLockEvent[$i0];
    }
}
