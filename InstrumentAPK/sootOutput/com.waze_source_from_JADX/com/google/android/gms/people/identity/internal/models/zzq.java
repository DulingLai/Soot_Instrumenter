package com.google.android.gms.people.identity.internal.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.people.identity.internal.models.PersonImpl.MetadataImpl;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzq implements Creator<MetadataImpl> {
    static void zza(MetadataImpl $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.aPr, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.aPs, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.aPt, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zza($r1, 5, $r0.aPu, true);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            zzb.zza($r1, 6, $r0.aPv);
        }
        if ($r2.contains(Integer.valueOf(7))) {
            zzb.zza($r1, 7, $r0.aMF);
        }
        if ($r2.contains(Integer.valueOf(8))) {
            zzb.zza($r1, 8, $r0.aPw);
        }
        if ($r2.contains(Integer.valueOf(9))) {
            zzb.zza($r1, 9, $r0.aPx);
        }
        if ($r2.contains(Integer.valueOf(10))) {
            zzb.zzc($r1, 10, $r0.aPy);
        }
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzsz($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzabm($i0);
    }

    public MetadataImpl[] zzabm(int $i0) throws  {
        return new MetadataImpl[$i0];
    }

    public com.google.android.gms.people.identity.internal.models.PersonImpl.MetadataImpl zzsz(android.os.Parcel r33) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:13:0x0073
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
        r32 = this;
        r12 = 0;
        r13 = 0;
        r0 = r33;
        r14 = com.google.android.gms.common.internal.safeparcel.zza.zzdz(r0);
        r15 = new java.util.HashSet;
        r15.<init>();
        r16 = 0;
        r17 = 0;
        r18 = 0;
        r19 = 0;
        r20 = 0;
        r21 = 0;
        r22 = 0;
        r23 = 0;
    L_0x001d:
        r0 = r33;
        r24 = r0.dataPosition();
        r0 = r24;
        if (r0 >= r14) goto L_0x013b;
    L_0x0027:
        r0 = r33;
        r24 = com.google.android.gms.common.internal.safeparcel.zza.zzdy(r0);
        r0 = r24;
        r25 = com.google.android.gms.common.internal.safeparcel.zza.zziv(r0);
        switch(r25) {
            case 1: goto L_0x003f;
            case 2: goto L_0x0055;
            case 3: goto L_0x0077;
            case 4: goto L_0x0099;
            case 5: goto L_0x00af;
            case 6: goto L_0x00c5;
            case 7: goto L_0x00df;
            case 8: goto L_0x00f5;
            case 9: goto L_0x010b;
            case 10: goto L_0x0121;
            default: goto L_0x0036;
        };
    L_0x0036:
        goto L_0x0037;
    L_0x0037:
        r0 = r33;
        r1 = r24;
        com.google.android.gms.common.internal.safeparcel.zza.zzb(r0, r1);
        goto L_0x001d;
    L_0x003f:
        r0 = r33;
        r1 = r24;
        r23 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r27 = 1;
        r0 = r27;
        r26 = java.lang.Integer.valueOf(r0);
        r0 = r26;
        r15.add(r0);
        goto L_0x001d;
    L_0x0055:
        r0 = r33;
        r1 = r24;
        r22 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0061;
    L_0x005e:
        goto L_0x001d;
    L_0x0061:
        r27 = 2;
        r0 = r27;
        r26 = java.lang.Integer.valueOf(r0);
        goto L_0x006d;
    L_0x006a:
        goto L_0x001d;
    L_0x006d:
        r0 = r26;
        r15.add(r0);
        goto L_0x001d;
        goto L_0x0077;
    L_0x0074:
        goto L_0x001d;
    L_0x0077:
        r0 = r33;
        r1 = r24;
        r21 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        goto L_0x0083;
    L_0x0080:
        goto L_0x001d;
    L_0x0083:
        r27 = 3;
        r0 = r27;
        r26 = java.lang.Integer.valueOf(r0);
        goto L_0x008f;
    L_0x008c:
        goto L_0x001d;
    L_0x008f:
        r0 = r26;
        r15.add(r0);
        goto L_0x001d;
        goto L_0x0099;
    L_0x0096:
        goto L_0x001d;
    L_0x0099:
        r0 = r33;
        r1 = r24;
        r20 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r27 = 4;
        r0 = r27;
        r26 = java.lang.Integer.valueOf(r0);
        r0 = r26;
        r15.add(r0);
        goto L_0x005e;
    L_0x00af:
        r0 = r33;
        r1 = r24;
        r12 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r0, r1);
        r27 = 5;
        r0 = r27;
        r26 = java.lang.Integer.valueOf(r0);
        r0 = r26;
        r15.add(r0);
        goto L_0x006a;
    L_0x00c5:
        r0 = r33;
        r1 = r24;
        r19 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r27 = 6;
        r0 = r27;
        r26 = java.lang.Integer.valueOf(r0);
        r0 = r26;
        r15.add(r0);
        goto L_0x00de;
    L_0x00db:
        goto L_0x0096;
    L_0x00de:
        goto L_0x0074;
    L_0x00df:
        r0 = r33;
        r1 = r24;
        r18 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r27 = 7;
        r0 = r27;
        r26 = java.lang.Integer.valueOf(r0);
        r0 = r26;
        r15.add(r0);
        goto L_0x0080;
    L_0x00f5:
        r0 = r33;
        r1 = r24;
        r17 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r27 = 8;
        r0 = r27;
        r26 = java.lang.Integer.valueOf(r0);
        r0 = r26;
        r15.add(r0);
        goto L_0x008c;
    L_0x010b:
        r0 = r33;
        r1 = r24;
        r16 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r0, r1);
        r27 = 9;
        r0 = r27;
        r26 = java.lang.Integer.valueOf(r0);
        r0 = r26;
        r15.add(r0);
        goto L_0x00db;
    L_0x0121:
        r0 = r33;
        r1 = r24;
        r13 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r0, r1);
        r27 = 10;
        r0 = r27;
        r26 = java.lang.Integer.valueOf(r0);
        goto L_0x0135;
    L_0x0132:
        goto L_0x001d;
    L_0x0135:
        r0 = r26;
        r15.add(r0);
        goto L_0x0132;
    L_0x013b:
        r0 = r33;
        r24 = r0.dataPosition();
        r0 = r24;
        if (r0 == r14) goto L_0x0170;
    L_0x0145:
        r28 = new com.google.android.gms.common.internal.safeparcel.zza$zza;
        r29 = new java.lang.StringBuilder;
        r27 = 37;
        r0 = r29;
        r1 = r27;
        r0.<init>(r1);
        r30 = "Overread allowed size end=";
        r0 = r29;
        r1 = r30;
        r29 = r0.append(r1);
        r0 = r29;
        r29 = r0.append(r14);
        r0 = r29;
        r12 = r0.toString();
        r0 = r28;
        r1 = r33;
        r0.<init>(r12, r1);
        throw r28;
    L_0x0170:
        r31 = new com.google.android.gms.people.identity.internal.models.PersonImpl$MetadataImpl;
        r0 = r31;
        r1 = r15;
        r2 = r23;
        r3 = r22;
        r4 = r21;
        r5 = r20;
        r6 = r12;
        r7 = r19;
        r8 = r18;
        r9 = r17;
        r10 = r16;
        r11 = r13;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11);
        return r31;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.people.identity.internal.models.zzq.zzsz(android.os.Parcel):com.google.android.gms.people.identity.internal.models.PersonImpl$MetadataImpl");
    }
}
