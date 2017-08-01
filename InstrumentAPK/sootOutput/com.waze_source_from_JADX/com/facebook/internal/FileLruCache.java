package com.facebook.internal;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.waze.strings.DisplayStrings;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class FileLruCache {
    private static final String HEADER_CACHEKEY_KEY = "key";
    private static final String HEADER_CACHE_CONTENT_TAG_KEY = "tag";
    static final String TAG = FileLruCache.class.getSimpleName();
    private static final AtomicLong bufferIndex = new AtomicLong();
    private final File directory;
    private boolean isTrimInProgress;
    private boolean isTrimPending;
    private AtomicLong lastClearCacheTime = new AtomicLong(0);
    private final Limits limits;
    private final Object lock;
    private final String tag;

    private interface StreamCloseCallback {
        void onClose() throws ;
    }

    class C05293 implements Runnable {
        C05293() throws  {
        }

        public void run() throws  {
            FileLruCache.this.trim();
        }
    }

    private static class BufferFile {
        private static final String FILE_NAME_PREFIX = "buffer";
        private static final FilenameFilter filterExcludeBufferFiles = new C05301();
        private static final FilenameFilter filterExcludeNonBufferFiles = new C05312();

        static class C05301 implements FilenameFilter {
            C05301() throws  {
            }

            public boolean accept(File dir, String $r2) throws  {
                return !$r2.startsWith(BufferFile.FILE_NAME_PREFIX);
            }
        }

        static class C05312 implements FilenameFilter {
            C05312() throws  {
            }

            public boolean accept(File dir, String $r2) throws  {
                return $r2.startsWith(BufferFile.FILE_NAME_PREFIX);
            }
        }

        private BufferFile() throws  {
        }

        static void deleteAll(File $r0) throws  {
            File[] $r2 = $r0.listFiles(excludeNonBufferFiles());
            if ($r2 != null) {
                for (File $r02 : $r2) {
                    $r02.delete();
                }
            }
        }

        static FilenameFilter excludeBufferFiles() throws  {
            return filterExcludeBufferFiles;
        }

        static FilenameFilter excludeNonBufferFiles() throws  {
            return filterExcludeNonBufferFiles;
        }

        static File newFile(File $r0) throws  {
            return new File($r0, FILE_NAME_PREFIX + Long.valueOf(FileLruCache.bufferIndex.incrementAndGet()).toString());
        }
    }

    private static class CloseCallbackOutputStream extends OutputStream {
        final StreamCloseCallback callback;
        final OutputStream innerStream;

        CloseCallbackOutputStream(OutputStream $r1, StreamCloseCallback $r2) throws  {
            this.innerStream = $r1;
            this.callback = $r2;
        }

        public void close() throws IOException {
            try {
                this.innerStream.close();
            } finally {
                this.callback.onClose();
            }
        }

        public void flush() throws IOException {
            this.innerStream.flush();
        }

        public void write(byte[] $r1, int $i0, int $i1) throws IOException {
            this.innerStream.write($r1, $i0, $i1);
        }

        public void write(byte[] $r1) throws IOException {
            this.innerStream.write($r1);
        }

        public void write(int $i0) throws IOException {
            this.innerStream.write($i0);
        }
    }

    private static final class CopyingInputStream extends InputStream {
        final InputStream input;
        final OutputStream output;

        public boolean markSupported() throws  {
            return false;
        }

        CopyingInputStream(InputStream $r1, OutputStream $r2) throws  {
            this.input = $r1;
            this.output = $r2;
        }

        public int available() throws IOException {
            return this.input.available();
        }

        public void close() throws IOException {
            try {
                this.input.close();
            } finally {
                this.output.close();
            }
        }

        public void mark(int readlimit) throws  {
            throw new UnsupportedOperationException();
        }

        public int read(byte[] $r1) throws IOException {
            int $i0 = this.input.read($r1);
            if ($i0 <= 0) {
                return $i0;
            }
            this.output.write($r1, 0, $i0);
            return $i0;
        }

        public int read() throws IOException {
            int $i0 = this.input.read();
            if ($i0 < 0) {
                return $i0;
            }
            this.output.write($i0);
            return $i0;
        }

        public int read(byte[] $r1, int $i0, int $i1) throws IOException {
            $i1 = this.input.read($r1, $i0, $i1);
            if ($i1 <= 0) {
                return $i1;
            }
            this.output.write($r1, $i0, $i1);
            return $i1;
        }

        public synchronized void reset() throws  {
            throw new UnsupportedOperationException();
        }

        public long skip(long $l0) throws IOException {
            byte[] $r1 = new byte[1024];
            long $l2 = 0;
            while ($l2 < $l0) {
                int $i5 = read($r1, 0, (int) Math.min($l0 - $l2, (long) $r1.length));
                if ($i5 < 0) {
                    return $l2;
                }
                $l2 += (long) $i5;
            }
            return $l2;
        }
    }

    public static final class Limits {
        private int byteCount = 1048576;
        private int fileCount = 1024;

        int getByteCount() throws  {
            return this.byteCount;
        }

        int getFileCount() throws  {
            return this.fileCount;
        }

        void setByteCount(int $i0) throws  {
            if ($i0 < 0) {
                throw new InvalidParameterException("Cache byte-count limit must be >= 0");
            }
            this.byteCount = $i0;
        }

        void setFileCount(int $i0) throws  {
            if ($i0 < 0) {
                throw new InvalidParameterException("Cache file count limit must be >= 0");
            }
            this.fileCount = $i0;
        }
    }

    private static final class ModifiedFile implements Comparable<ModifiedFile> {
        private static final int HASH_MULTIPLIER = 37;
        private static final int HASH_SEED = 29;
        private final File file;
        private final long modified;

        ModifiedFile(File $r1) throws  {
            this.file = $r1;
            this.modified = $r1.lastModified();
        }

        File getFile() throws  {
            return this.file;
        }

        long getModified() throws  {
            return this.modified;
        }

        public int compareTo(ModifiedFile $r1) throws  {
            if (getModified() < $r1.getModified()) {
                return -1;
            }
            if (getModified() > $r1.getModified()) {
                return 1;
            }
            return getFile().compareTo($r1.getFile());
        }

        public boolean equals(Object $r1) throws  {
            return ($r1 instanceof ModifiedFile) && compareTo((ModifiedFile) $r1) == 0;
        }

        public int hashCode() throws  {
            return ((this.file.hashCode() + DisplayStrings.DS_FACEBOOK_PRIVACY_BODY3) * 37) + ((int) (this.modified % 2147483647L));
        }
    }

    private static final class StreamHeader {
        private static final int HEADER_VERSION = 0;

        private StreamHeader() throws  {
        }

        static void writeHeader(OutputStream $r0, JSONObject $r1) throws IOException {
            byte[] $r3 = $r1.toString().getBytes();
            $r0.write(0);
            $r0.write(($r3.length >> 16) & 255);
            $r0.write(($r3.length >> 8) & 255);
            $r0.write(($r3.length >> 0) & 255);
            $r0.write($r3);
        }

        static JSONObject readHeader(InputStream $r0) throws IOException {
            if ($r0.read() != 0) {
                return null;
            }
            int $i0;
            int $i1 = 0;
            for ($i0 = 0; $i0 < 3; $i0++) {
                int $i2 = $r0.read();
                if ($i2 == -1) {
                    Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: stream.read returned -1 while reading header size");
                    return null;
                }
                $i1 = ($i1 << 8) + ($i2 & 255);
            }
            byte[] $r2 = new byte[$i1];
            $i0 = 0;
            while ($i0 < $r2.length) {
                $i2 = $r0.read($r2, $i0, $r2.length - $i0);
                if ($i2 < 1) {
                    Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: stream.read stopped at " + Integer.valueOf($i0) + " when expected " + $r2.length);
                    return null;
                }
                $i0 += $i2;
            }
            try {
                Object $r9 = new JSONTokener(new String($r2)).nextValue();
                if ($r9 instanceof JSONObject) {
                    return (JSONObject) $r9;
                }
                Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: expected JSONObject, got " + $r9.getClass().getCanonicalName());
                return null;
            } catch (JSONException $r1) {
                throw new IOException($r1.getMessage());
            }
        }
    }

    private void trim() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x00d0 in list []
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
        r35 = this;
        r0 = r35;
        r3 = r0.lock;
        monitor-enter(r3);
        r4 = 0;
        r0 = r35;
        r0.isTrimPending = r4;
        r4 = 1;
        r0 = r35;
        r0.isTrimInProgress = r4;
        monitor-exit(r3);
        r5 = com.facebook.LoggingBehavior.CACHE;	 Catch:{ Throwable -> 0x011a }
        r6 = TAG;	 Catch:{ Throwable -> 0x011a }
        r7 = "trim started";	 Catch:{ Throwable -> 0x011a }
        com.facebook.internal.Logger.log(r5, r6, r7);	 Catch:{ Throwable -> 0x011a }
        r8 = new java.util.PriorityQueue;	 Catch:{ Throwable -> 0x011a }
        r8.<init>();	 Catch:{ Throwable -> 0x011a }
        r9 = 0;
        r11 = 0;	 Catch:{ Throwable -> 0x011a }
        r0 = r35;	 Catch:{ Throwable -> 0x011a }
        r13 = r0.directory;	 Catch:{ Throwable -> 0x011a }
        r14 = com.facebook.internal.FileLruCache.BufferFile.excludeBufferFiles();	 Catch:{ Throwable -> 0x011a }
        r15 = r13.listFiles(r14);	 Catch:{ Throwable -> 0x011a }
        if (r15 == 0) goto L_0x00aa;	 Catch:{ Throwable -> 0x011a }
    L_0x0031:
        r0 = r15.length;	 Catch:{ Throwable -> 0x011a }
        r16 = r0;	 Catch:{ Throwable -> 0x011a }
        r17 = 0;
    L_0x0036:
        r0 = r17;	 Catch:{ Throwable -> 0x011a }
        r1 = r16;	 Catch:{ Throwable -> 0x011a }
        if (r0 >= r1) goto L_0x00aa;	 Catch:{ Throwable -> 0x011a }
    L_0x003c:
        r13 = r15[r17];	 Catch:{ Throwable -> 0x011a }
        r18 = new com.facebook.internal.FileLruCache$ModifiedFile;	 Catch:{ Throwable -> 0x011a }
        r0 = r18;	 Catch:{ Throwable -> 0x011a }
        r0.<init>(r13);	 Catch:{ Throwable -> 0x011a }
        r0 = r18;	 Catch:{ Throwable -> 0x011a }
        r8.add(r0);	 Catch:{ Throwable -> 0x011a }
        r5 = com.facebook.LoggingBehavior.CACHE;	 Catch:{ Throwable -> 0x011a }
        r6 = TAG;	 Catch:{ Throwable -> 0x011a }
        r19 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x011a }
        r0 = r19;	 Catch:{ Throwable -> 0x011a }
        r0.<init>();	 Catch:{ Throwable -> 0x011a }
        r7 = "  trim considering time=";	 Catch:{ Throwable -> 0x011a }
        r0 = r19;	 Catch:{ Throwable -> 0x011a }
        r19 = r0.append(r7);	 Catch:{ Throwable -> 0x011a }
        r0 = r18;	 Catch:{ Throwable -> 0x011a }
        r20 = r0.getModified();	 Catch:{ Throwable -> 0x011a }
        r0 = r20;	 Catch:{ Throwable -> 0x011a }
        r22 = java.lang.Long.valueOf(r0);	 Catch:{ Throwable -> 0x011a }
        r0 = r19;	 Catch:{ Throwable -> 0x011a }
        r1 = r22;	 Catch:{ Throwable -> 0x011a }
        r19 = r0.append(r1);	 Catch:{ Throwable -> 0x011a }
        r7 = " name=";	 Catch:{ Throwable -> 0x011a }
        r0 = r19;	 Catch:{ Throwable -> 0x011a }
        r19 = r0.append(r7);	 Catch:{ Throwable -> 0x011a }
        r0 = r18;	 Catch:{ Throwable -> 0x011a }
        r23 = r0.getFile();	 Catch:{ Throwable -> 0x011a }
        r0 = r23;	 Catch:{ Throwable -> 0x011a }
        r24 = r0.getName();	 Catch:{ Throwable -> 0x011a }
        r0 = r19;	 Catch:{ Throwable -> 0x011a }
        r1 = r24;	 Catch:{ Throwable -> 0x011a }
        r19 = r0.append(r1);	 Catch:{ Throwable -> 0x011a }
        r0 = r19;	 Catch:{ Throwable -> 0x011a }
        r24 = r0.toString();	 Catch:{ Throwable -> 0x011a }
        r0 = r24;	 Catch:{ Throwable -> 0x011a }
        com.facebook.internal.Logger.log(r5, r6, r0);	 Catch:{ Throwable -> 0x011a }
        r20 = r13.length();	 Catch:{ Throwable -> 0x011a }
        r0 = r20;
        r9 = r9 + r0;
        r25 = 1;
        r0 = r25;
        r11 = r11 + r0;
        r17 = r17 + 1;
        goto L_0x0036;
    L_0x00a7:
        r27 = move-exception;
        monitor-exit(r3);
        throw r27;
    L_0x00aa:
        r0 = r35;	 Catch:{ Throwable -> 0x011a }
        r0 = r0.limits;	 Catch:{ Throwable -> 0x011a }
        r28 = r0;	 Catch:{ Throwable -> 0x011a }
        r16 = r0.getByteCount();	 Catch:{ Throwable -> 0x011a }
        r0 = r16;	 Catch:{ Throwable -> 0x011a }
        r0 = (long) r0;	 Catch:{ Throwable -> 0x011a }
        r20 = r0;	 Catch:{ Throwable -> 0x011a }
        r29 = (r9 > r20 ? 1 : (r9 == r20 ? 0 : -1));
        if (r29 > 0) goto L_0x00d0;	 Catch:{ Throwable -> 0x011a }
    L_0x00bd:
        r0 = r35;	 Catch:{ Throwable -> 0x011a }
        r0 = r0.limits;	 Catch:{ Throwable -> 0x011a }
        r28 = r0;	 Catch:{ Throwable -> 0x011a }
        r16 = r0.getFileCount();	 Catch:{ Throwable -> 0x011a }
        r0 = r16;	 Catch:{ Throwable -> 0x011a }
        r0 = (long) r0;	 Catch:{ Throwable -> 0x011a }
        r20 = r0;	 Catch:{ Throwable -> 0x011a }
        r29 = (r11 > r20 ? 1 : (r11 == r20 ? 0 : -1));
        if (r29 <= 0) goto L_0x0130;	 Catch:{ Throwable -> 0x011a }
    L_0x00d0:
        r3 = r8.remove();	 Catch:{ Throwable -> 0x011a }
        r30 = r3;	 Catch:{ Throwable -> 0x011a }
        r30 = (com.facebook.internal.FileLruCache.ModifiedFile) r30;	 Catch:{ Throwable -> 0x011a }
        r18 = r30;	 Catch:{ Throwable -> 0x011a }
        r0 = r18;	 Catch:{ Throwable -> 0x011a }
        r13 = r0.getFile();	 Catch:{ Throwable -> 0x011a }
        r5 = com.facebook.LoggingBehavior.CACHE;	 Catch:{ Throwable -> 0x011a }
        r6 = TAG;	 Catch:{ Throwable -> 0x011a }
        r19 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x011a }
        r0 = r19;	 Catch:{ Throwable -> 0x011a }
        r0.<init>();	 Catch:{ Throwable -> 0x011a }
        r7 = "  trim removing ";	 Catch:{ Throwable -> 0x011a }
        r0 = r19;	 Catch:{ Throwable -> 0x011a }
        r19 = r0.append(r7);	 Catch:{ Throwable -> 0x011a }
        r24 = r13.getName();	 Catch:{ Throwable -> 0x011a }
        r0 = r19;	 Catch:{ Throwable -> 0x011a }
        r1 = r24;	 Catch:{ Throwable -> 0x011a }
        r19 = r0.append(r1);	 Catch:{ Throwable -> 0x011a }
        r0 = r19;	 Catch:{ Throwable -> 0x011a }
        r24 = r0.toString();	 Catch:{ Throwable -> 0x011a }
        r0 = r24;	 Catch:{ Throwable -> 0x011a }
        com.facebook.internal.Logger.log(r5, r6, r0);	 Catch:{ Throwable -> 0x011a }
        r20 = r13.length();	 Catch:{ Throwable -> 0x011a }
        r0 = r20;
        r9 = r9 - r0;
        r25 = 1;	 Catch:{ Throwable -> 0x011a }
        r0 = r25;	 Catch:{ Throwable -> 0x011a }
        r11 = r11 - r0;	 Catch:{ Throwable -> 0x011a }
        r13.delete();	 Catch:{ Throwable -> 0x011a }
        goto L_0x00aa;
    L_0x011a:
        r31 = move-exception;
        r0 = r35;
        r3 = r0.lock;
        monitor-enter(r3);
        r4 = 0;	 Catch:{ Throwable -> 0x011a }
        r0 = r35;	 Catch:{ Throwable -> 0x011a }
        r0.isTrimInProgress = r4;	 Catch:{ Throwable -> 0x011a }
        r0 = r35;	 Catch:{ Throwable -> 0x011a }
        r0 = r0.lock;	 Catch:{ Throwable -> 0x011a }
        r32 = r0;	 Catch:{ Throwable -> 0x011a }
        r0.notifyAll();	 Catch:{ Throwable -> 0x011a }
        monitor-exit(r3);	 Catch:{ Throwable -> 0x011a }
        throw r31;
    L_0x0130:
        r0 = r35;
        r3 = r0.lock;
        monitor-enter(r3);
        r4 = 0;
        r0 = r35;
        r0.isTrimInProgress = r4;
        r0 = r35;
        r0 = r0.lock;
        r32 = r0;
        r0.notifyAll();
        monitor-exit(r3);
        return;
    L_0x0145:
        r33 = move-exception;
        monitor-exit(r3);
        throw r33;
    L_0x0148:
        r34 = move-exception;
        monitor-exit(r3);	 Catch:{ Throwable -> 0x011a }
        throw r34;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.FileLruCache.trim():void");
    }

    public java.io.InputStream get(java.lang.String r22, java.lang.String r23) throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x005b in list []
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
        r21 = this;
        r3 = new java.io.File;
        r0 = r21;
        r4 = r0.directory;
        r0 = r22;
        r5 = com.facebook.internal.Utility.md5hash(r0);
        r3.<init>(r4, r5);
        r6 = new java.io.FileInputStream;
        r6.<init>(r3);	 Catch:{ IOException -> 0x0026 }
        r7 = new java.io.BufferedInputStream;
        r8 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r7.<init>(r6, r8);
        r9 = com.facebook.internal.FileLruCache.StreamHeader.readHeader(r7);	 Catch:{ Throwable -> 0x00af }
        if (r9 != 0) goto L_0x0029;
    L_0x0021:
        r7.close();
        r10 = 0;
        return r10;
    L_0x0026:
        r11 = move-exception;
        r10 = 0;
        return r10;
    L_0x0029:
        r12 = "key";	 Catch:{ Throwable -> 0x00af }
        r5 = r9.optString(r12);	 Catch:{ Throwable -> 0x00af }
        if (r5 == 0) goto L_0x0039;	 Catch:{ Throwable -> 0x00af }
    L_0x0031:
        r0 = r22;	 Catch:{ Throwable -> 0x00af }
        r13 = r5.equals(r0);	 Catch:{ Throwable -> 0x00af }
        if (r13 != 0) goto L_0x003e;
    L_0x0039:
        r7.close();
        r10 = 0;
        return r10;
    L_0x003e:
        r12 = "tag";	 Catch:{ Throwable -> 0x00af }
        r10 = 0;	 Catch:{ Throwable -> 0x00af }
        r22 = r9.optString(r12, r10);	 Catch:{ Throwable -> 0x00af }
        if (r23 != 0) goto L_0x004a;
    L_0x0048:
        if (r22 != 0) goto L_0x0056;
    L_0x004a:
        if (r23 == 0) goto L_0x005b;	 Catch:{ Throwable -> 0x00af }
    L_0x004c:
        r0 = r23;	 Catch:{ Throwable -> 0x00af }
        r1 = r22;	 Catch:{ Throwable -> 0x00af }
        r13 = r0.equals(r1);	 Catch:{ Throwable -> 0x00af }
        if (r13 != 0) goto L_0x005b;
    L_0x0056:
        r7.close();
        r10 = 0;
        return r10;
    L_0x005b:
        r14 = new java.util.Date;	 Catch:{ Throwable -> 0x00af }
        r14.<init>();	 Catch:{ Throwable -> 0x00af }
        r15 = r14.getTime();	 Catch:{ Throwable -> 0x00af }
        r17 = com.facebook.LoggingBehavior.CACHE;	 Catch:{ Throwable -> 0x00af }
        r22 = TAG;	 Catch:{ Throwable -> 0x00af }
        r18 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00af }
        r0 = r18;	 Catch:{ Throwable -> 0x00af }
        r0.<init>();	 Catch:{ Throwable -> 0x00af }
        r12 = "Setting lastModified to ";	 Catch:{ Throwable -> 0x00af }
        r0 = r18;	 Catch:{ Throwable -> 0x00af }
        r18 = r0.append(r12);	 Catch:{ Throwable -> 0x00af }
        r0 = r15;	 Catch:{ Throwable -> 0x00af }
        r19 = java.lang.Long.valueOf(r0);	 Catch:{ Throwable -> 0x00af }
        r0 = r18;	 Catch:{ Throwable -> 0x00af }
        r1 = r19;	 Catch:{ Throwable -> 0x00af }
        r18 = r0.append(r1);	 Catch:{ Throwable -> 0x00af }
        r12 = " for ";	 Catch:{ Throwable -> 0x00af }
        r0 = r18;	 Catch:{ Throwable -> 0x00af }
        r18 = r0.append(r12);	 Catch:{ Throwable -> 0x00af }
        r23 = r3.getName();	 Catch:{ Throwable -> 0x00af }
        r0 = r18;	 Catch:{ Throwable -> 0x00af }
        r1 = r23;	 Catch:{ Throwable -> 0x00af }
        r18 = r0.append(r1);	 Catch:{ Throwable -> 0x00af }
        r0 = r18;	 Catch:{ Throwable -> 0x00af }
        r23 = r0.toString();	 Catch:{ Throwable -> 0x00af }
        r0 = r17;	 Catch:{ Throwable -> 0x00af }
        r1 = r22;	 Catch:{ Throwable -> 0x00af }
        r2 = r23;	 Catch:{ Throwable -> 0x00af }
        com.facebook.internal.Logger.log(r0, r1, r2);	 Catch:{ Throwable -> 0x00af }
        r0 = r15;	 Catch:{ Throwable -> 0x00af }
        r3.setLastModified(r0);	 Catch:{ Throwable -> 0x00af }
        goto L_0x00ae;
    L_0x00ae:
        return r7;
    L_0x00af:
        r20 = move-exception;
        r7.close();
        throw r20;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.FileLruCache.get(java.lang.String, java.lang.String):java.io.InputStream");
    }

    public FileLruCache(String $r1, Limits $r2) throws  {
        this.tag = $r1;
        this.limits = $r2;
        this.directory = new File(FacebookSdk.getCacheDir(), $r1);
        this.lock = new Object();
        if (this.directory.mkdirs() || this.directory.isDirectory()) {
            BufferFile.deleteAll(this.directory);
        }
    }

    long sizeInBytesForTest() throws  {
        synchronized (this.lock) {
            while (true) {
                if (!this.isTrimPending && !this.isTrimInProgress) {
                    break;
                }
                try {
                    this.lock.wait();
                } catch (InterruptedException e) {
                }
            }
        }
        File[] $r5 = this.directory.listFiles();
        long $l0 = 0;
        if ($r5 == null) {
            return 0;
        }
        for (File $r1 : $r5) {
            $l0 += $r1.length();
        }
        return $l0;
    }

    public InputStream get(String $r1) throws IOException {
        return get($r1, null);
    }

    public OutputStream openPutStream(String $r1) throws IOException {
        return openPutStream($r1, null);
    }

    public OutputStream openPutStream(String $r1, String $r2) throws IOException {
        File $r7 = BufferFile.newFile(this.directory);
        $r7.delete();
        if ($r7.createNewFile()) {
            try {
                FileOutputStream $r10 = new FileOutputStream($r7);
                final long currentTimeMillis = System.currentTimeMillis();
                final File file = $r7;
                final String str = $r1;
                OutputStream bufferedOutputStream = new BufferedOutputStream(new CloseCallbackOutputStream($r10, new StreamCloseCallback() {
                    public void onClose() throws  {
                        if (currentTimeMillis < FileLruCache.this.lastClearCacheTime.get()) {
                            file.delete();
                        } else {
                            FileLruCache.this.renameToTargetAndTrim(str, file);
                        }
                    }
                }), 8192);
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(HEADER_CACHEKEY_KEY, $r1);
                    if (!Utility.isNullOrEmpty($r2)) {
                        jSONObject.put("tag", $r2);
                    }
                    StreamHeader.writeHeader(bufferedOutputStream, jSONObject);
                    return bufferedOutputStream;
                } catch (JSONException $r13) {
                    Logger.log(LoggingBehavior.CACHE, 5, TAG, "Error creating JSON header for cache file: " + $r13);
                    throw new IOException($r13.getMessage());
                } catch (Throwable th) {
                    bufferedOutputStream.close();
                }
            } catch (FileNotFoundException $r11) {
                Logger.log(LoggingBehavior.CACHE, 5, TAG, "Error creating buffer output stream: " + $r11);
                throw new IOException($r11.getMessage());
            }
        }
        throw new IOException("Could not create file at " + $r7.getAbsolutePath());
    }

    public void clearCache() throws  {
        final File[] $r3 = this.directory.listFiles(BufferFile.excludeBufferFiles());
        this.lastClearCacheTime.set(System.currentTimeMillis());
        if ($r3 != null) {
            FacebookSdk.getExecutor().execute(new Runnable() {
                public void run() throws  {
                    for (File $r1 : $r3) {
                        $r1.delete();
                    }
                }
            });
        }
    }

    public String getLocation() throws  {
        return this.directory.getPath();
    }

    private void renameToTargetAndTrim(String $r1, File $r2) throws  {
        if (!$r2.renameTo(new File(this.directory, Utility.md5hash($r1)))) {
            $r2.delete();
        }
        postTrim();
    }

    public InputStream interceptAndPut(String $r1, InputStream $r2) throws IOException {
        return new CopyingInputStream($r2, openPutStream($r1));
    }

    public String toString() throws  {
        return "{FileLruCache: tag:" + this.tag + " file:" + this.directory.getName() + "}";
    }

    private void postTrim() throws  {
        synchronized (this.lock) {
            if (!this.isTrimPending) {
                this.isTrimPending = true;
                FacebookSdk.getExecutor().execute(new C05293());
            }
        }
    }
}
