package com.google.android.gms.common.util;

import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.google.android.gms.common.internal.zzab;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/* compiled from: dalvik_source_com.waze.apk */
public final class IOUtils {

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zza extends ByteArrayOutputStream {
        private zza() throws  {
        }

        void zze(byte[] $r1, int $i0) throws  {
            System.arraycopy(this.buf, 0, $r1, $i0, this.count);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzb {
        private final File file;

        private zzb(File $r1) throws  {
            this.file = (File) zzab.zzag($r1);
        }

        public byte[] zzayz() throws IOException {
            Throwable $r5;
            Closeable $r1;
            try {
                $r1 = new FileInputStream(this.file);
                try {
                    byte[] $r4 = IOUtils.zza((InputStream) $r1, $r1.getChannel().size());
                    IOUtils.closeQuietly($r1);
                    return $r4;
                } catch (Throwable th) {
                    $r5 = th;
                    IOUtils.closeQuietly($r1);
                    throw $r5;
                }
            } catch (Throwable th2) {
                $r5 = th2;
                $r1 = null;
                IOUtils.closeQuietly($r1);
                throw $r5;
            }
        }
    }

    private IOUtils() throws  {
    }

    public static void close(Closeable $r0, String $r1, String $r2) throws  {
        if ($r0 != null) {
            try {
                $r0.close();
            } catch (IOException $r3) {
                Log.d($r1, $r2, $r3);
            }
        }
    }

    public static void closeQuietly(ParcelFileDescriptor $r0) throws  {
        if ($r0 != null) {
            try {
                $r0.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeQuietly(Closeable $r0) throws  {
        if ($r0 != null) {
            try {
                $r0.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeQuietly(ServerSocket $r0) throws  {
        if ($r0 != null) {
            try {
                $r0.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeQuietly(Socket $r0) throws  {
        if ($r0 != null) {
            try {
                $r0.close();
            } catch (IOException e) {
            }
        }
    }

    public static long copyStream(InputStream $r0, OutputStream $r1) throws IOException {
        return copyStream($r0, $r1, false);
    }

    public static long copyStream(InputStream $r0, OutputStream $r1, boolean $z0) throws IOException {
        return copyStream($r0, $r1, $z0, 1024);
    }

    public static long copyStream(java.io.InputStream r8, java.io.OutputStream r9, boolean r10, int r11) throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
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
        r0 = new byte[r11];
        r1 = 0;
    L_0x0004:
        r4 = 0;	 Catch:{ Throwable -> 0x0013 }
        r3 = r8.read(r0, r4, r11);	 Catch:{ Throwable -> 0x0013 }
        r4 = -1;	 Catch:{ Throwable -> 0x0013 }
        if (r3 == r4) goto L_0x001d;	 Catch:{ Throwable -> 0x0013 }
    L_0x000c:
        r5 = (long) r3;	 Catch:{ Throwable -> 0x0013 }
        r1 = r1 + r5;	 Catch:{ Throwable -> 0x0013 }
        r4 = 0;	 Catch:{ Throwable -> 0x0013 }
        r9.write(r0, r4, r3);	 Catch:{ Throwable -> 0x0013 }
        goto L_0x0004;
    L_0x0013:
        r7 = move-exception;
        if (r10 == 0) goto L_0x001c;
    L_0x0016:
        closeQuietly(r8);
        closeQuietly(r9);
    L_0x001c:
        throw r7;
    L_0x001d:
        if (r10 == 0) goto L_0x0026;
    L_0x001f:
        closeQuietly(r8);
        closeQuietly(r9);
        return r1;
    L_0x0026:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.IOUtils.copyStream(java.io.InputStream, java.io.OutputStream, boolean, int):long");
    }

    public static boolean isGzipByteBuffer(byte[] $r0) throws  {
        return $r0.length > 1 && (($r0[0] & (short) 255) | (($r0[1] & (short) 255) << 8)) == 35615;
    }

    public static byte[] readInputStreamFully(InputStream $r0) throws IOException {
        return readInputStreamFully($r0, true);
    }

    public static byte[] readInputStreamFully(InputStream $r0, boolean $z0) throws IOException {
        ByteArrayOutputStream $r2 = new ByteArrayOutputStream();
        copyStream($r0, $r2, $z0);
        return $r2.toByteArray();
    }

    public static byte[] toByteArray(File $r0) throws IOException {
        return zzd($r0).zzayz();
    }

    public static byte[] toByteArray(InputStream $r0) throws IOException {
        OutputStream $r2 = new ByteArrayOutputStream();
        zza($r0, $r2);
        return $r2.toByteArray();
    }

    private static long zza(InputStream $r0, OutputStream $r1) throws IOException {
        zzab.zzag($r0);
        zzab.zzag($r1);
        byte[] $r2 = new byte[4096];
        long $l1 = 0;
        while (true) {
            int $i0 = $r0.read($r2);
            if ($i0 == -1) {
                return $l1;
            }
            $r1.write($r2, 0, $i0);
            $l1 += (long) $i0;
        }
    }

    private static byte[] zza(InputStream $r0, long $l0) throws IOException {
        if ($l0 <= 2147483647L) {
            return $l0 == 0 ? toByteArray($r0) : zzb($r0, (int) $l0);
        } else {
            throw new OutOfMemoryError("file is too large to fit in a byte array: " + $l0 + " bytes");
        }
    }

    static byte[] zzb(InputStream $r0, int $i0) throws IOException {
        byte[] $r1 = new byte[$i0];
        int $i1 = $i0;
        while ($i1 > 0) {
            int $i2 = $i0 - $i1;
            int $i3 = $r0.read($r1, $i2, $i1);
            if ($i3 == -1) {
                return Arrays.copyOf($r1, $i2);
            }
            $i1 -= $i3;
        }
        $i1 = $r0.read();
        if ($i1 == -1) {
            return $r1;
        }
        OutputStream $r2 = new zza();
        $r2.write($i1);
        zza($r0, $r2);
        byte[] $r3 = new byte[($r2.size() + $i0)];
        System.arraycopy($r1, 0, $r3, 0, $i0);
        $r2.zze($r3, $i0);
        return $r3;
    }

    private static zzb zzd(File $r0) throws  {
        return new zzb($r0);
    }
}
