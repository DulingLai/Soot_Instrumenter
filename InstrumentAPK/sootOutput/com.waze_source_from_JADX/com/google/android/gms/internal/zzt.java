package com.google.android.gms.internal;

import com.google.android.gms.internal.zzb.zza;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.impl.cookie.DateUtils;

/* compiled from: dalvik_source_com.waze.apk */
public class zzt implements zzf {
    protected static final boolean DEBUG = zzs.DEBUG;
    private static int zzbm = 3000;
    private static int zzbn = 4096;
    protected final zzy zzbo;
    protected final zzu zzbp;

    public zzt(zzy $r1) throws  {
        this($r1, new zzu(zzbn));
    }

    public zzt(zzy $r1, zzu $r2) throws  {
        this.zzbo = $r1;
        this.zzbp = $r2;
    }

    protected static Map<String, String> zza(@Signature({"([", "Lorg/apache/http/Header;", ")", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;"}) Header[] $r0) throws  {
        TreeMap $r1 = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        for (int $i0 = 0; $i0 < $r0.length; $i0++) {
            $r1.put($r0[$i0].getName(), $r0[$i0].getValue());
        }
        return $r1;
    }

    private void zza(@Signature({"(J", "Lcom/google/android/gms/internal/zzk", "<*>;[B", "Lorg/apache/http/StatusLine;", ")V"}) long $l0, @Signature({"(J", "Lcom/google/android/gms/internal/zzk", "<*>;[B", "Lorg/apache/http/StatusLine;", ")V"}) zzk<?> $r1, @Signature({"(J", "Lcom/google/android/gms/internal/zzk", "<*>;[B", "Lorg/apache/http/StatusLine;", ")V"}) byte[] $r2, @Signature({"(J", "Lcom/google/android/gms/internal/zzk", "<*>;[B", "Lorg/apache/http/StatusLine;", ")V"}) StatusLine $r3) throws  {
        if (DEBUG || $l0 > ((long) zzbm)) {
            Object[] $r4 = new Object[5];
            $r4[0] = $r1;
            $r4[1] = Long.valueOf($l0);
            $r4[2] = $r2 != null ? Integer.valueOf($r2.length) : "null";
            $r4[3] = Integer.valueOf($r3.getStatusCode());
            $r4[4] = Integer.valueOf($r1.zzt().zzd());
            zzs.zzb("HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]", $r4);
        }
    }

    private static void zza(@Signature({"(", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzk", "<*>;", "Lcom/google/android/gms/internal/zzr;", ")V"}) String $r0, @Signature({"(", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzk", "<*>;", "Lcom/google/android/gms/internal/zzr;", ")V"}) zzk<?> $r1, @Signature({"(", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzk", "<*>;", "Lcom/google/android/gms/internal/zzr;", ")V"}) zzr $r2) throws zzr {
        zzo $r3 = $r1.zzt();
        int $i0 = $r1.zzs();
        try {
            $r3.zza($r2);
            $r1.zzc(String.format("%s-retry [timeout=%s]", new Object[]{$r0, Integer.valueOf($i0)}));
        } catch (zzr $r6) {
            $r1.zzc(String.format("%s-timeout-giveup [timeout=%s]", new Object[]{$r0, Integer.valueOf($i0)}));
            throw $r6;
        }
    }

    private void zza(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/internal/zzb$zza;", ")V"}) Map<String, String> $r1, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/internal/zzb$zza;", ")V"}) zza $r2) throws  {
        if ($r2 != null) {
            if ($r2.zza != null) {
                $r1.put("If-None-Match", $r2.zza);
            }
            if ($r2.zzc > 0) {
                $r1.put("If-Modified-Since", DateUtils.formatDate(new Date($r2.zzc)));
            }
        }
    }

    private byte[] zza(HttpEntity $r1) throws IOException, zzp {
        zzaa $r2 = new zzaa(this.zzbp, (int) $r1.getContentLength());
        byte[] $r4 = null;
        byte[] $r8;
        try {
            InputStream $r5 = $r1.getContent();
            if ($r5 == null) {
                throw new zzp();
            }
            $r8 = this.zzbp.zzb(1024);
            $r4 = $r8;
            while (true) {
                int $i1 = $r5.read($r8);
                if ($i1 == -1) {
                    break;
                }
                $r2.write($r8, 0, $i1);
            }
            $r4 = $r2.toByteArray();
            return $r4;
        } finally {
            try {
                $r4 = 
/*
Method generation error in method: com.google.android.gms.internal.zzt.zza(org.apache.http.HttpEntity):byte[]
jadx.core.utils.exceptions.CodegenException: Error generate insn: ?: MERGE  (r6_4 '$r4' byte[]) = (r6_3 '$r4' byte[]), (r10_0 '$r8' byte[]) in method: com.google.android.gms.internal.zzt.zza(org.apache.http.HttpEntity):byte[]
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:203)
	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:100)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:50)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:277)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:297)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:183)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:328)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:265)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:228)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:118)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:83)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:19)
	at jadx.core.ProcessClass.process(ProcessClass.java:43)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.CodegenException: MERGE can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:530)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:514)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:220)
	... 26 more

*/

                public com.google.android.gms.internal.zzi zza(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/internal/zzk", "<*>;)", "Lcom/google/android/gms/internal/zzi;"}) com.google.android.gms.internal.zzk<?> r44) throws com.google.android.gms.internal.zzr {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:60:0x019f in {14, 18, 21, 25, 34, 37, 41, 44, 49, 51, 52, 54, 59, 61, 67, 70, 72, 74} preds:[]
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
                    r43 = this;
                    r7 = android.os.SystemClock.elapsedRealtime();
                L_0x0004:
                    r9 = 0;
                    r10 = java.util.Collections.emptyMap();
                    r11 = new java.util.HashMap;
                    r11.<init>();	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x015e }
                    r0 = r44;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x015e }
                    r12 = r0.zzh();	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x015e }
                    r0 = r43;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x015e }
                    r0.zza(r11, r12);	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x015e }
                    r0 = r43;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x015e }
                    r13 = r0.zzbo;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x015e }
                    r0 = r44;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x015e }
                    r9 = r13.zza(r0, r11);	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x015e }
                    r14 = r9.getStatusLine();	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r15 = r14.getStatusCode();	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r16 = r9.getAllHeaders();	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r0 = r16;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r17 = zza(r0);	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r10 = r17;
                    r18 = 304; // 0x130 float:4.26E-43 double:1.5E-321;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r0 = r18;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    if (r15 != r0) goto L_0x009a;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                L_0x003d:
                    r0 = r44;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r12 = r0.zzh();	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    if (r12 != 0) goto L_0x0066;
                L_0x0045:
                    r19 = new com.google.android.gms.internal.zzi;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r20 = android.os.SystemClock.elapsedRealtime();	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r0 = r20;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r0 = r0 - r7;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r20 = r0;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r18 = 304; // 0x130 float:4.26E-43 double:1.5E-321;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r22 = 0;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r23 = 1;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r0 = r19;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r1 = r18;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r2 = r22;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r3 = r17;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r4 = r23;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r5 = r20;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r0.<init>(r1, r2, r3, r4, r5);	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    return r19;
                L_0x0066:
                    r0 = r12.zzf;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r24 = r0;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r1 = r17;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r0.putAll(r1);	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r19 = new com.google.android.gms.internal.zzi;
                    r0 = r12.data;
                    r25 = r0;
                    goto L_0x0079;
                L_0x0076:
                    goto L_0x0004;
                L_0x0079:
                    r0 = r12.zzf;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r17 = r0;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r20 = android.os.SystemClock.elapsedRealtime();	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r0 = r20;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r0 = r0 - r7;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r20 = r0;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r18 = 304; // 0x130 float:4.26E-43 double:1.5E-321;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r23 = 1;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r0 = r19;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r1 = r18;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r2 = r25;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r3 = r17;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r4 = r23;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r5 = r20;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r0.<init>(r1, r2, r3, r4, r5);	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    return r19;
                L_0x009a:
                    r26 = r9.getEntity();	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    if (r26 == 0) goto L_0x00ea;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                L_0x00a0:
                    r26 = r9.getEntity();	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r0 = r43;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r1 = r26;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                    r25 = r0.zza(r1);	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01f7 }
                L_0x00ac:
                    r20 = android.os.SystemClock.elapsedRealtime();	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r0 = r20;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r0 = r0 - r7;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r20 = r0;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r0 = r43;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r1 = r20;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r3 = r44;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r4 = r25;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r5 = r14;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r0.zza(r1, r3, r4, r5);	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r18 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
                    r0 = r18;
                    if (r15 < r0) goto L_0x00cd;
                L_0x00c7:
                    r18 = 299; // 0x12b float:4.19E-43 double:1.477E-321;
                    r0 = r18;
                    if (r15 <= r0) goto L_0x00f3;
                L_0x00cd:
                    r27 = new java.io.IOException;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r0 = r27;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r0.<init>();	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    throw r27;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                L_0x00d5:
                    r28 = move-exception;
                    r29 = new com.google.android.gms.internal.zzq;
                    r0 = r29;
                    r0.<init>();
                    r30 = "socket";
                    r0 = r30;
                    r1 = r44;
                    r2 = r29;
                    zza(r0, r1, r2);
                    goto L_0x0076;
                L_0x00ea:
                    r18 = 0;
                    r0 = r18;
                    r0 = new byte[r0];
                    r25 = r0;
                    goto L_0x00ac;
                L_0x00f3:
                    r19 = new com.google.android.gms.internal.zzi;
                    r20 = android.os.SystemClock.elapsedRealtime();	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r0 = r20;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r0 = r0 - r7;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r20 = r0;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r18 = 0;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r0 = r19;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r1 = r15;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r2 = r25;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r3 = r17;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r4 = r18;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r5 = r20;	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    r0.<init>(r1, r2, r3, r4, r5);	 Catch:{ SocketTimeoutException -> 0x00d5, ConnectTimeoutException -> 0x010f, MalformedURLException -> 0x0127, IOException -> 0x01fb }
                    return r19;
                L_0x010f:
                    r31 = move-exception;
                    r29 = new com.google.android.gms.internal.zzq;
                    r0 = r29;
                    r0.<init>();
                    goto L_0x011b;
                L_0x0118:
                    goto L_0x0004;
                L_0x011b:
                    r30 = "connection";
                    r0 = r30;
                    r1 = r44;
                    r2 = r29;
                    zza(r0, r1, r2);
                    goto L_0x0118;
                L_0x0127:
                    r32 = move-exception;
                    r33 = new java.lang.RuntimeException;
                    r34 = "Bad URL ";
                    r0 = r44;
                    r35 = r0.getUrl();
                    r0 = r35;
                    r35 = java.lang.String.valueOf(r0);
                    r0 = r35;
                    r15 = r0.length();
                    if (r15 == 0) goto L_0x0152;
                L_0x0140:
                    r0 = r34;
                    r1 = r35;
                    r34 = r0.concat(r1);
                L_0x0148:
                    r0 = r33;
                    r1 = r34;
                    r2 = r32;
                    r0.<init>(r1, r2);
                    throw r33;
                L_0x0152:
                    r34 = new java.lang.String;
                    r30 = "Bad URL ";
                    r0 = r34;
                    r1 = r30;
                    r0.<init>(r1);
                    goto L_0x0148;
                L_0x015e:
                    r36 = move-exception;
                    r25 = 0;
                L_0x0161:
                    if (r9 == 0) goto L_0x01d7;
                L_0x0163:
                    r14 = r9.getStatusLine();
                    r15 = r14.getStatusCode();
                    r18 = 2;
                    r0 = r18;
                    r0 = new java.lang.Object[r0];
                    r37 = r0;
                    r38 = java.lang.Integer.valueOf(r15);
                    r18 = 0;
                    r37[r18] = r38;
                    r0 = r44;
                    r34 = r0.getUrl();
                    r18 = 1;
                    r37[r18] = r34;
                    r30 = "Unexpected response code %d for %s";
                    r0 = r30;
                    r1 = r37;
                    com.google.android.gms.internal.zzs.zzc(r0, r1);
                    if (r25 == 0) goto L_0x01eb;
                L_0x0190:
                    r19 = new com.google.android.gms.internal.zzi;
                    r20 = android.os.SystemClock.elapsedRealtime();
                    r0 = r20;
                    r0 = r0 - r7;
                    r20 = r0;
                    goto L_0x01a3;
                L_0x019c:
                    goto L_0x0161;
                    goto L_0x01a3;
                L_0x01a0:
                    goto L_0x0161;
                L_0x01a3:
                    r18 = 0;
                    r0 = r19;
                    r1 = r15;
                    r2 = r25;
                    r3 = r10;
                    r4 = r18;
                    r5 = r20;
                    r0.<init>(r1, r2, r3, r4, r5);
                    r18 = 401; // 0x191 float:5.62E-43 double:1.98E-321;
                    r0 = r18;
                    if (r15 == r0) goto L_0x01be;
                L_0x01b8:
                    r18 = 403; // 0x193 float:5.65E-43 double:1.99E-321;
                    r0 = r18;
                    if (r15 != r0) goto L_0x01e1;
                L_0x01be:
                    r39 = new com.google.android.gms.internal.zza;
                    r0 = r39;
                    r1 = r19;
                    r0.<init>(r1);
                    goto L_0x01cb;
                L_0x01c8:
                    goto L_0x0004;
                L_0x01cb:
                    r30 = "auth";
                    r0 = r30;
                    r1 = r44;
                    r2 = r39;
                    zza(r0, r1, r2);
                    goto L_0x01c8;
                L_0x01d7:
                    r40 = new com.google.android.gms.internal.zzj;
                    r0 = r40;
                    r1 = r36;
                    r0.<init>(r1);
                    throw r40;
                L_0x01e1:
                    r41 = new com.google.android.gms.internal.zzp;
                    r0 = r41;
                    r1 = r19;
                    r0.<init>(r1);
                    throw r41;
                L_0x01eb:
                    r42 = new com.google.android.gms.internal.zzh;
                    r22 = 0;
                    r0 = r42;
                    r1 = r22;
                    r0.<init>(r1);
                    throw r42;
                L_0x01f7:
                    r36 = move-exception;
                    r25 = 0;
                    goto L_0x019c;
                L_0x01fb:
                    r36 = move-exception;
                    goto L_0x01a0;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzt.zza(com.google.android.gms.internal.zzk):com.google.android.gms.internal.zzi");
                }
            }
