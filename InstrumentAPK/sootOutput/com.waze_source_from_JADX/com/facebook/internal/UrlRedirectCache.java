package com.facebook.internal;

import android.net.Uri;
import com.facebook.LoggingBehavior;
import com.facebook.internal.FileLruCache.Limits;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

class UrlRedirectCache {
    private static final String REDIRECT_CONTENT_TAG = (TAG + "_Redirect");
    static final String TAG = UrlRedirectCache.class.getSimpleName();
    private static volatile FileLruCache urlRedirectCache;

    UrlRedirectCache() throws  {
    }

    static synchronized FileLruCache getCache() throws IOException {
        Class cls = UrlRedirectCache.class;
        synchronized (cls) {
            try {
                if (urlRedirectCache == null) {
                    urlRedirectCache = new FileLruCache(TAG, new Limits());
                }
                FileLruCache $r2 = urlRedirectCache;
                return $r2;
            } finally {
                cls = UrlRedirectCache.class;
            }
        }
    }

    static Uri getRedirectedUri(Uri $r0) throws  {
        Throwable $r10;
        if ($r0 == null) {
            return null;
        }
        String $r3 = $r0.toString();
        InputStreamReader $r4 = null;
        try {
            FileLruCache $r5 = getCache();
            boolean $z0 = false;
            InputStreamReader $r6 = null;
            while (true) {
                try {
                    InputStream $r8 = $r5.get($r3, REDIRECT_CONTENT_TAG);
                    if ($r8 == null) {
                        break;
                    }
                    $z0 = true;
                    $r4 = new InputStreamReader($r8);
                    char[] $r1 = new char[128];
                    StringBuilder $r2 = new StringBuilder();
                    while (true) {
                        int $i0 = $r4.read($r1, 0, $r1.length);
                        if ($i0 <= 0) {
                            break;
                        }
                        $r2.append($r1, 0, $i0);
                    }
                    Utility.closeQuietly($r4);
                    $r3 = $r2.toString();
                    $r6 = $r4;
                } catch (IOException e) {
                    $r4 = $r6;
                } catch (Throwable th) {
                    $r10 = th;
                    $r4 = $r6;
                }
            }
            if ($z0) {
                $r0 = Uri.parse($r3);
                Utility.closeQuietly($r6);
                return $r0;
            }
            Utility.closeQuietly($r6);
            return null;
        } catch (IOException e2) {
        } catch (Throwable th2) {
            $r10 = th2;
        }
        Utility.closeQuietly($r4);
        throw $r10;
        Utility.closeQuietly($r4);
        return null;
    }

    static void cacheUriRedirect(Uri $r0, Uri $r1) throws  {
        if ($r0 != null && $r1 != null) {
            OutputStream $r3 = null;
            OutputStream $r6;
            try {
                $r6 = getCache().openPutStream($r0.toString(), REDIRECT_CONTENT_TAG);
                $r3 = $r6;
                $r6.write($r1.toString().getBytes());
            } catch (IOException e) {
            } finally {
                Utility.closeQuietly(
/*
Method generation error in method: com.facebook.internal.UrlRedirectCache.cacheUriRedirect(android.net.Uri, android.net.Uri):void
jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x002a: INVOKE  (wrap: java.io.OutputStream
  ?: MERGE  (r0_5 '$r3' java.io.OutputStream) = (r0_4 '$r3' java.io.OutputStream), (r4_0 '$r6' java.io.OutputStream)) com.facebook.internal.Utility.closeQuietly(java.io.Closeable):void type: STATIC in method: com.facebook.internal.UrlRedirectCache.cacheUriRedirect(android.net.Uri, android.net.Uri):void
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:203)
	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:100)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:50)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:297)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:118)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:57)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
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
Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: ?: MERGE  (r0_5 '$r3' java.io.OutputStream) = (r0_4 '$r3' java.io.OutputStream), (r4_0 '$r6' java.io.OutputStream) in method: com.facebook.internal.UrlRedirectCache.cacheUriRedirect(android.net.Uri, android.net.Uri):void
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:101)
	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:679)
	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:649)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:343)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:220)
	... 28 more
Caused by: jadx.core.utils.exceptions.CodegenException: MERGE can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:530)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:514)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:211)
	... 33 more

*/

                static void clearCache() throws  {
                    try {
                        getCache().clearCache();
                    } catch (IOException $r0) {
                        Logger.log(LoggingBehavior.CACHE, 5, TAG, "clearCache failed " + $r0.getMessage());
                    }
                }
            }
