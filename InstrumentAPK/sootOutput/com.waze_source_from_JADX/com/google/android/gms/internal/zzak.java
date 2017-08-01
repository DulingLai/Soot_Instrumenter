package com.google.android.gms.internal;

import com.google.android.gms.internal.zzae.zzf;
import dalvik.annotation.Signature;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: dalvik_source_com.waze.apk */
final class zzak {
    static boolean zzxe = false;
    private static MessageDigest zzxf = null;
    private static final Object zzxg = new Object();
    private static final Object zzxh = new Object();
    static CountDownLatch zzxi = new CountDownLatch(1);

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zza implements Runnable {
        private zza() throws  {
        }

        public void run() throws  {
            try {
                zzak.zzxf = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
            } finally {
                zzak.zzxi.countDown();
            }
        }
    }

    private static int zza(boolean $z0) throws  {
        return $z0 ? 239 : 255;
    }

    static String zza(com.google.android.gms.internal.zzae.zza $r0, String $r1, boolean $z0) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return zza(zzawz.zzf($r0), $r1, $z0);
    }

    static String zza(String $r0, String $r1, boolean $z0) throws  {
        byte[] $r2 = zzb($r0, $r1, $z0);
        return $r2 != null ? zzaj.zza($r2, true) : Integer.toString(7);
    }

    static String zza(byte[] $r0, String $r1, boolean $z0) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return zzaj.zza($z0 ? zzb($r0, $r1) : zza($r0, $r1), true);
    }

    static Vector<byte[]> zza(@Signature({"([BI)", "Ljava/util/Vector", "<[B>;"}) byte[] $r0, @Signature({"([BI)", "Ljava/util/Vector", "<[B>;"}) int $i0) throws  {
        if ($r0 == null) {
            return null;
        }
        if ($r0.length <= 0) {
            return null;
        }
        int $i1 = (($r0.length + $i0) - 1) / $i0;
        Vector $r2 = new Vector();
        int $i3 = 0;
        while ($i3 < $i1) {
            int $i2 = $i3 * $i0;
            try {
                $r2.add(Arrays.copyOfRange($r0, $i2, $r0.length - $i2 > $i0 ? $i2 + $i0 : $r0.length));
                $i3++;
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
        return $r2;
    }

    static void zza(String $r1, byte[] $r0) throws UnsupportedEncodingException {
        if ($r1.length() > 32) {
            $r1 = $r1.substring(0, 32);
        }
        new zzawp($r1.getBytes("UTF-8")).zzbh($r0);
    }

    static byte[] zza(byte[] $r0, String $r1) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Vector $r4 = zza($r0, 255);
        if ($r4 == null || $r4.size() == 0) {
            return zzb(zzawz.zzf(zzb(4096)), $r1);
        }
        zzf $r2 = new zzf();
        $r2.zzex = new byte[$r4.size()][];
        Iterator $r6 = $r4.iterator();
        int $i0 = 0;
        while ($r6.hasNext()) {
            $r2.zzex[$i0] = zzb((byte[]) $r6.next(), $r1, false);
            $i0++;
        }
        $r2.zzes = zzg($r0);
        return zzawz.zzf($r2);
    }

    static void zzar() throws  {
        synchronized (zzxh) {
            if (!zzxe) {
                zzxe = true;
                new Thread(new zza()).start();
            }
        }
    }

    static MessageDigest zzas() throws  {
        zzar();
        boolean $z0 = false;
        try {
            $z0 = zzxi.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
        return !$z0 ? null : zzxf != null ? zzxf : null;
    }

    static com.google.android.gms.internal.zzae.zza zzb(long $l0) throws  {
        com.google.android.gms.internal.zzae.zza $r0 = new com.google.android.gms.internal.zzae.zza();
        $r0.zzdk = Long.valueOf($l0);
        return $r0;
    }

    static byte[] zzb(java.lang.String r8, java.lang.String r9, boolean r10) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0036 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = new com.google.android.gms.internal.zzae$zzc;
        r0.<init>();
        r1 = r8.length();	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        r2 = 3;	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        if (r1 >= r2) goto L_0x002a;	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
    L_0x000c:
        r4 = "ISO-8859-1";	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        r3 = r8.getBytes(r4);	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
    L_0x0012:
        r0.zzeq = r3;
        if (r10 == 0) goto L_0x0036;	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
    L_0x0016:
        r1 = r9.length();	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        r2 = 3;	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        if (r1 >= r2) goto L_0x0030;	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
    L_0x001d:
        r4 = "ISO-8859-1";	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        r3 = r9.getBytes(r4);	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
    L_0x0023:
        r0.zzer = r3;	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        r3 = com.google.android.gms.internal.zzawz.zzf(r0);	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        return r3;
    L_0x002a:
        r2 = 1;	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        r3 = com.google.android.gms.internal.zzaj.zza(r8, r2);	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        goto L_0x0012;
    L_0x0030:
        r2 = 1;	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        r3 = com.google.android.gms.internal.zzaj.zza(r9, r2);	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        goto L_0x0023;
    L_0x0036:
        if (r9 == 0) goto L_0x003e;	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
    L_0x0038:
        r1 = r9.length();	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        if (r1 != 0) goto L_0x004a;	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
    L_0x003e:
        r2 = 5;	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        r8 = java.lang.Integer.toString(r2);	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        r4 = "ISO-8859-1";	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        r3 = r8.getBytes(r4);	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        goto L_0x0023;
    L_0x004a:
        r4 = "ISO-8859-1";	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        r3 = r9.getBytes(r4);	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        r5 = 0;	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        r2 = 1;	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        r8 = zza(r3, r5, r2);	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        r2 = 1;	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        r3 = com.google.android.gms.internal.zzaj.zza(r8, r2);	 Catch:{ UnsupportedEncodingException -> 0x005f, NoSuchAlgorithmException -> 0x005c }
        goto L_0x0023;
    L_0x005c:
        r6 = move-exception;
    L_0x005d:
        r5 = 0;
        return r5;
    L_0x005f:
        r7 = move-exception;
        goto L_0x005d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzak.zzb(java.lang.String, java.lang.String, boolean):byte[]");
    }

    static byte[] zzb(byte[] $r0, String $r1) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return zzb($r0, $r1, true);
    }

    private static byte[] zzb(byte[] $r1, String $r0, boolean $z0) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] $r3;
        int $i0 = zza($z0);
        if ($r1.length > $i0) {
            $r1 = zzawz.zzf(zzb(4096));
        }
        if ($r1.length < $i0) {
            $r3 = new byte[($i0 - $r1.length)];
            new SecureRandom().nextBytes($r3);
            $r1 = ByteBuffer.allocate($i0 + 1).put((byte) $r1.length).put($r1).put($r3).array();
        } else {
            $r1 = ByteBuffer.allocate($i0 + 1).put((byte) $r1.length).put($r1).array();
        }
        if ($z0) {
            $r1 = ByteBuffer.allocate(256).put(zzg($r1)).put($r1).array();
        }
        $r3 = new byte[256];
        new zzal().zzb($r1, $r3);
        if ($r0 == null || $r0.length() <= 0) {
            return $r3;
        }
        zza($r0, $r3);
        return $r3;
    }

    public static byte[] zzg(byte[] $r0) throws NoSuchAlgorithmException {
        synchronized (zzxg) {
            MessageDigest $r2 = zzas();
            if ($r2 == null) {
                throw new NoSuchAlgorithmException("Cannot compute hash");
            }
            $r2.reset();
            $r2.update($r0);
            $r0 = zzxf.digest();
        }
        return $r0;
    }
}
