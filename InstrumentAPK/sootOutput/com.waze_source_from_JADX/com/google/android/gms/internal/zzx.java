package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.util.Map;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

/* compiled from: dalvik_source_com.waze.apk */
public class zzx {
    public static String zza(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)", "Ljava/lang/String;"}) Map<String, String> $r0) throws  {
        return zzb($r0, "ISO-8859-1");
    }

    public static com.google.android.gms.internal.zzb.zza zzb(com.google.android.gms.internal.zzi r38) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:37:0x00ca in {2, 13, 17, 18, 23, 27, 28, 29, 32, 35, 36, 38, 43, 45, 47, 49, 50, 55, 60, 61} preds:[]
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
        r3 = java.lang.System.currentTimeMillis();
        r0 = r38;
        r5 = r0.headers;
        r6 = 0;
        r8 = 0;
        r10 = 0;
        r13 = "Date";
        r12 = r5.get(r13);
        r15 = r12;
        r15 = (java.lang.String) r15;
        r14 = r15;
        if (r14 == 0) goto L_0x001e;
    L_0x001a:
        r6 = zzg(r14);
    L_0x001e:
        r13 = "Cache-Control";
        r12 = r5.get(r13);
        r16 = r12;
        r16 = (java.lang.String) r16;
        r14 = r16;
        if (r14 == 0) goto L_0x013c;
    L_0x002c:
        r13 = ",";
        r17 = r14.split(r13);
        r18 = 0;
        r19 = 0;
        r10 = 0;
        r20 = 0;
    L_0x003a:
        r0 = r17;
        r0 = r0.length;
        r22 = r0;
        r0 = r18;
        r1 = r22;
        if (r0 >= r1) goto L_0x009e;
    L_0x0045:
        r14 = r17[r18];
        r14 = r14.trim();
        r13 = "no-cache";
        r23 = r14.equals(r13);
        if (r23 != 0) goto L_0x005b;
    L_0x0053:
        r13 = "no-store";
        r23 = r14.equals(r13);
        if (r23 == 0) goto L_0x005e;
    L_0x005b:
        r24 = 0;
        return r24;
    L_0x005e:
        r13 = "max-age=";
        r23 = r14.startsWith(r13);
        if (r23 == 0) goto L_0x0075;
    L_0x0066:
        r25 = 8;	 Catch:{ Exception -> 0x012f }
        r0 = r25;	 Catch:{ Exception -> 0x012f }
        r14 = r14.substring(r0);	 Catch:{ Exception -> 0x012f }
        r20 = java.lang.Long.parseLong(r14);	 Catch:{ Exception -> 0x012f }
    L_0x0072:
        r18 = r18 + 1;
        goto L_0x003a;
    L_0x0075:
        r13 = "stale-while-revalidate=";
        r23 = r14.startsWith(r13);
        if (r23 == 0) goto L_0x008b;
    L_0x007e:
        r25 = 23;	 Catch:{ Exception -> 0x012d }
        r0 = r25;	 Catch:{ Exception -> 0x012d }
        r14 = r14.substring(r0);	 Catch:{ Exception -> 0x012d }
        r10 = java.lang.Long.parseLong(r14);	 Catch:{ Exception -> 0x012d }
        goto L_0x0072;
    L_0x008b:
        r13 = "must-revalidate";
        r23 = r14.equals(r13);
        if (r23 != 0) goto L_0x009b;
    L_0x0093:
        r13 = "proxy-revalidate";
        r23 = r14.equals(r13);
        if (r23 == 0) goto L_0x0072;
    L_0x009b:
        r19 = 1;
        goto L_0x0072;
    L_0x009e:
        r8 = r20;
        r23 = 1;
    L_0x00a2:
        r13 = "Expires";
        r12 = r5.get(r13);
        r26 = r12;
        r26 = (java.lang.String) r26;
        r14 = r26;
        if (r14 == 0) goto L_0x0139;
    L_0x00b0:
        r27 = zzg(r14);
    L_0x00b4:
        r13 = "Last-Modified";
        r12 = r5.get(r13);
        r29 = r12;
        r29 = (java.lang.String) r29;
        r14 = r29;
        if (r14 == 0) goto L_0x0136;
    L_0x00c2:
        r20 = zzg(r14);
        goto L_0x00ce;
    L_0x00c7:
        goto L_0x0072;
        goto L_0x00ce;
    L_0x00cb:
        goto L_0x0072;
    L_0x00ce:
        r13 = "ETag";
        r12 = r5.get(r13);
        r30 = r12;
        r30 = (java.lang.String) r30;
        r14 = r30;
        if (r23 == 0) goto L_0x011e;
    L_0x00dc:
        r31 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r8 = r31 * r8;
        r3 = r3 + r8;
        if (r19 == 0) goto L_0x0118;
    L_0x00e3:
        r10 = r3;
    L_0x00e4:
        r33 = new com.google.android.gms.internal.zzb$zza;
        r0 = r33;
        r0.<init>();
        r0 = r38;
        r0 = r0.data;
        r34 = r0;
        goto L_0x00f5;
    L_0x00f2:
        goto L_0x00a2;
    L_0x00f5:
        r1 = r33;
        r1.data = r0;
        goto L_0x00fd;
    L_0x00fa:
        goto L_0x00b4;
    L_0x00fd:
        r0 = r33;
        r0.zza = r14;
        r0 = r33;
        r0.zze = r3;
        r0 = r33;
        r0.zzd = r10;
        r0 = r33;
        r0.zzb = r6;
        r0 = r20;
        r2 = r33;
        r2.zzc = r0;
        r0 = r33;
        r0.zzf = r5;
        return r33;
    L_0x0118:
        r31 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r10 = r31 * r10;
        r10 = r10 + r3;
        goto L_0x00e4;
    L_0x011e:
        r31 = 0;
        r35 = (r6 > r31 ? 1 : (r6 == r31 ? 0 : -1));
        if (r35 <= 0) goto L_0x0131;
    L_0x0124:
        r35 = (r27 > r6 ? 1 : (r27 == r6 ? 0 : -1));
        if (r35 < 0) goto L_0x0131;
    L_0x0128:
        r10 = r27 - r6;
        r10 = r10 + r3;
        r3 = r10;
        goto L_0x00e4;
    L_0x012d:
        r36 = move-exception;
        goto L_0x00c7;
    L_0x012f:
        r37 = move-exception;
        goto L_0x00cb;
    L_0x0131:
        r10 = 0;
        r3 = 0;
        goto L_0x00e4;
    L_0x0136:
        r20 = 0;
        goto L_0x00ce;
    L_0x0139:
        r27 = 0;
        goto L_0x00fa;
    L_0x013c:
        r19 = 0;
        r23 = 0;
        goto L_0x00f2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzx.zzb(com.google.android.gms.internal.zzi):com.google.android.gms.internal.zzb$zza");
    }

    public static String zzb(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")", "Ljava/lang/String;"}) Map<String, String> $r0, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")", "Ljava/lang/String;"}) String $r1) throws  {
        String $r3 = (String) $r0.get("Content-Type");
        if ($r3 == null) {
            return $r1;
        }
        String[] $r4 = $r3.split(";");
        for (int $i0 = 1; $i0 < $r4.length; $i0++) {
            String[] $r5 = $r4[$i0].trim().split("=");
            if ($r5.length == 2 && $r5[0].equals("charset")) {
                return $r5[1];
            }
        }
        return $r1;
    }

    public static long zzg(String $r0) throws  {
        try {
            return DateUtils.parseDate($r0).getTime();
        } catch (DateParseException e) {
            return 0;
        }
    }
}
