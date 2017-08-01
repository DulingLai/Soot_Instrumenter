package com.android.volley.toolbox;

import android.os.SystemClock;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.VolleyLog;
import dalvik.annotation.Signature;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class DiskBasedCache implements Cache {
    private static final int CACHE_MAGIC = 538051844;
    private static final int DEFAULT_DISK_USAGE_BYTES = 5242880;
    private static final float HYSTERESIS_FACTOR = 0.9f;
    private final Map<String, CacheHeader> mEntries;
    private final int mMaxCacheSizeInBytes;
    private final File mRootDirectory;
    private long mTotalSize;

    static class CacheHeader {
        public String etag;
        public String key;
        public Map<String, String> responseHeaders;
        public long serverDate;
        public long size;
        public long softTtl;
        public long ttl;

        private CacheHeader() throws  {
        }

        public CacheHeader(String $r1, Entry $r2) throws  {
            this.key = $r1;
            this.size = (long) $r2.data.length;
            this.etag = $r2.etag;
            this.serverDate = $r2.serverDate;
            this.ttl = $r2.ttl;
            this.softTtl = $r2.softTtl;
            this.responseHeaders = $r2.responseHeaders;
        }

        public static CacheHeader readHeader(InputStream $r0) throws IOException {
            CacheHeader $r1 = new CacheHeader();
            if (DiskBasedCache.readInt($r0) != DiskBasedCache.CACHE_MAGIC) {
                throw new IOException();
            }
            $r1.key = DiskBasedCache.readString($r0);
            $r1.etag = DiskBasedCache.readString($r0);
            if ($r1.etag.equals("")) {
                $r1.etag = null;
            }
            $r1.serverDate = DiskBasedCache.readLong($r0);
            $r1.ttl = DiskBasedCache.readLong($r0);
            $r1.softTtl = DiskBasedCache.readLong($r0);
            $r1.responseHeaders = DiskBasedCache.readStringStringMap($r0);
            return $r1;
        }

        public Entry toCacheEntry(byte[] $r1) throws  {
            Entry $r2 = new Entry();
            $r2.data = $r1;
            $r2.etag = this.etag;
            $r2.serverDate = this.serverDate;
            $r2.ttl = this.ttl;
            $r2.softTtl = this.softTtl;
            $r2.responseHeaders = this.responseHeaders;
            return $r2;
        }

        public boolean writeHeader(OutputStream $r1) throws  {
            try {
                DiskBasedCache.writeInt($r1, DiskBasedCache.CACHE_MAGIC);
                DiskBasedCache.writeString($r1, this.key);
                DiskBasedCache.writeString($r1, this.etag == null ? "" : this.etag);
                DiskBasedCache.writeLong($r1, this.serverDate);
                DiskBasedCache.writeLong($r1, this.ttl);
                DiskBasedCache.writeLong($r1, this.softTtl);
                DiskBasedCache.writeStringStringMap(this.responseHeaders, $r1);
                $r1.flush();
                return true;
            } catch (IOException $r2) {
                VolleyLog.m15d("%s", $r2.toString());
                return false;
            }
        }
    }

    private static class CountingInputStream extends FilterInputStream {
        private int bytesRead;

        private CountingInputStream(InputStream $r1) throws  {
            super($r1);
            this.bytesRead = 0;
        }

        public int read() throws IOException {
            int $i0 = super.read();
            if ($i0 == -1) {
                return $i0;
            }
            this.bytesRead++;
            return $i0;
        }

        public int read(byte[] $r1, int $i0, int $i1) throws IOException {
            $i0 = super.read($r1, $i0, $i1);
            if ($i0 == -1) {
                return $i0;
            }
            this.bytesRead += $i0;
            return $i0;
        }
    }

    public DiskBasedCache(File $r1, int $i0) throws  {
        this.mEntries = new LinkedHashMap(16, 0.75f, true);
        this.mTotalSize = 0;
        this.mRootDirectory = $r1;
        this.mMaxCacheSizeInBytes = $i0;
    }

    public DiskBasedCache(File $r1) throws  {
        this($r1, DEFAULT_DISK_USAGE_BYTES);
    }

    public synchronized void clear() throws  {
        File[] $r2 = this.mRootDirectory.listFiles();
        if ($r2 != null) {
            for (File $r1 : $r2) {
                $r1.delete();
            }
        }
        this.mEntries.clear();
        this.mTotalSize = 0;
        VolleyLog.m15d("Cache cleared.", new Object[0]);
    }

    public synchronized Entry get(String $r1) throws  {
        IOException $r13;
        Throwable $r17;
        Entry $r3 = null;
        synchronized (this) {
            CacheHeader $r6 = (CacheHeader) this.mEntries.get($r1);
            if ($r6 != null) {
                File $r7 = getFileForKey($r1);
                CountingInputStream $r8 = null;
                try {
                    CountingInputStream $r2 = new CountingInputStream(new FileInputStream($r7));
                    try {
                        CacheHeader.readHeader($r2);
                        long $l2 = (long) $r2.bytesRead;
                        Entry $r11 = $r6.toCacheEntry(streamToBytes($r2, (int) ($r7.length() - $l2)));
                        if ($r2 != null) {
                            try {
                                $r2.close();
                            } catch (IOException e) {
                            }
                        }
                        $r3 = $r11;
                    } catch (IOException e2) {
                        $r13 = e2;
                        $r8 = $r2;
                        try {
                            VolleyLog.m15d("%s: %s", $r7.getAbsolutePath(), $r13.toString());
                            remove($r1);
                            if ($r8 != null) {
                                try {
                                    $r8.close();
                                } catch (IOException e3) {
                                }
                            }
                            return $r3;
                        } catch (Throwable th) {
                            $r17 = th;
                            if ($r8 != null) {
                                try {
                                    $r8.close();
                                } catch (IOException e4) {
                                }
                            }
                            throw $r17;
                        }
                    } catch (Throwable th2) {
                        $r17 = th2;
                        $r8 = $r2;
                        if ($r8 != null) {
                            $r8.close();
                        }
                        throw $r17;
                    }
                } catch (IOException e5) {
                    $r13 = e5;
                    VolleyLog.m15d("%s: %s", $r7.getAbsolutePath(), $r13.toString());
                    remove($r1);
                    if ($r8 != null) {
                        $r8.close();
                    }
                    return $r3;
                }
            }
        }
        return $r3;
    }

    public synchronized void initialize() throws  {
        FileInputStream $r6;
        Throwable $r11;
        this = this;
        if (this.mRootDirectory.exists()) {
            this = this;
            File[] $r5 = this.mRootDirectory.listFiles();
            if ($r5 != null) {
                for (File $r1 : $r5) {
                    $r6 = null;
                    try {
                        FileInputStream $r2 = new FileInputStream($r1);
                        try {
                            CacheHeader $r7 = CacheHeader.readHeader($r2);
                            $r7.size = $r1.length();
                            putEntry($r7.key, $r7);
                            if ($r2 != null) {
                                try {
                                    $r2.close();
                                } catch (IOException e) {
                                }
                            }
                        } catch (IOException e2) {
                            $r6 = $r2;
                            if ($r1 != null) {
                                try {
                                    $r1.delete();
                                } catch (Throwable th) {
                                    $r11 = th;
                                }
                            }
                            if ($r6 != null) {
                                try {
                                    $r6.close();
                                } catch (IOException e3) {
                                }
                            }
                        } catch (Throwable th2) {
                            $r11 = th2;
                            $r6 = $r2;
                        }
                    } catch (IOException e4) {
                        if ($r1 != null) {
                            $r1.delete();
                        }
                        if ($r6 != null) {
                            $r6.close();
                        }
                    }
                }
            }
        } else {
            this = this;
            if (!this.mRootDirectory.mkdirs()) {
                Object[] $r3 = new Object[1];
                this = this;
                $r3[0] = this.mRootDirectory.getAbsolutePath();
                VolleyLog.m16e("Unable to create cache dir %s", $r3);
            }
        }
        return;
        if ($r6 != null) {
            try {
                $r6.close();
            } catch (IOException e5) {
            }
        }
        throw $r11;
        throw $r11;
    }

    public synchronized void invalidate(String $r1, boolean $z0) throws  {
        Entry $r3 = get($r1);
        if ($r3 != null) {
            $r3.softTtl = 0;
            if ($z0) {
                $r3.ttl = 0;
            }
            put($r1, $r3);
        }
    }

    public synchronized void put(String $r1, Entry $r2) throws  {
        pruneIfNeeded($r2.data.length);
        File $r6 = getFileForKey($r1);
        try {
            FileOutputStream $r4 = new FileOutputStream($r6);
            CacheHeader $r3 = new CacheHeader($r1, $r2);
            $r3.writeHeader($r4);
            $r4.write($r2.data);
            $r4.close();
            putEntry($r1, $r3);
        } catch (IOException e) {
            if (!$r6.delete()) {
                VolleyLog.m15d("Could not clean up file %s", $r6.getAbsolutePath());
            }
        }
    }

    public synchronized void remove(String $r1) throws  {
        boolean $z0 = getFileForKey($r1).delete();
        removeEntry($r1);
        if (!$z0) {
            VolleyLog.m15d("Could not delete cache entry for key=%s, filename=%s", $r1, getFilenameForKey($r1));
        }
    }

    private String getFilenameForKey(String $r1) throws  {
        int $i0 = $r1.length() / 2;
        return String.valueOf($r1.substring(0, $i0).hashCode()) + String.valueOf($r1.substring($i0).hashCode());
    }

    public File getFileForKey(String $r1) throws  {
        return new File(this.mRootDirectory, getFilenameForKey($r1));
    }

    private void pruneIfNeeded(int $i0) throws  {
        if (this.mTotalSize + ((long) $i0) >= ((long) this.mMaxCacheSizeInBytes)) {
            Object[] $r1;
            if (VolleyLog.DEBUG) {
                VolleyLog.m18v("Pruning old cache entries.", new Object[0]);
            }
            long $l1 = this.mTotalSize;
            int $i3 = 0;
            long $l2 = SystemClock.elapsedRealtime();
            Iterator $r4 = this.mEntries.entrySet().iterator();
            while ($r4.hasNext()) {
                long j;
                CacheHeader $r7 = (CacheHeader) ((Map.Entry) $r4.next()).getValue();
                if (getFileForKey($r7.key).delete()) {
                    long $l5 = this.mTotalSize - $r7.size;
                    j = $l5;
                    this.mTotalSize = $l5;
                } else {
                    $r1 = new Object[2];
                    $r1[0] = $r7.key;
                    $r1[1] = getFilenameForKey($r7.key);
                    VolleyLog.m15d("Could not delete cache entry for key=%s, filename=%s", $r1);
                }
                $r4.remove();
                $i3++;
                float $f0 = this.mTotalSize + ((long) $i0);
                j = $f0;
                float $f02 = (float) $f0;
                int $f1 = this.mMaxCacheSizeInBytes;
                int $i7 = $f1;
                if ($f02 < ((float) $f1) * HYSTERESIS_FACTOR) {
                    break;
                }
            }
            if (VolleyLog.DEBUG) {
                $r1 = new Object[3];
                $r1[0] = Integer.valueOf($i3);
                $r1[1] = Long.valueOf(this.mTotalSize - $l1);
                $r1[2] = Long.valueOf(SystemClock.elapsedRealtime() - $l2);
                VolleyLog.m18v("pruned %d files, %d bytes, %d ms", $r1);
            }
        }
    }

    private void putEntry(String $r1, CacheHeader $r2) throws  {
        if (this.mEntries.containsKey($r1)) {
            this.mTotalSize += $r2.size - ((CacheHeader) this.mEntries.get($r1)).size;
        } else {
            this.mTotalSize += $r2.size;
        }
        this.mEntries.put($r1, $r2);
    }

    private void removeEntry(String $r1) throws  {
        CacheHeader $r4 = (CacheHeader) this.mEntries.get($r1);
        if ($r4 != null) {
            this.mTotalSize -= $r4.size;
            this.mEntries.remove($r1);
        }
    }

    private static byte[] streamToBytes(InputStream $r0, int $i0) throws IOException {
        byte[] $r1 = new byte[$i0];
        int $i1 = 0;
        while ($i1 < $i0) {
            int $i2 = $r0.read($r1, $i1, $i0 - $i1);
            if ($i2 == -1) {
                break;
            }
            $i1 += $i2;
        }
        if ($i1 == $i0) {
            return $r1;
        }
        throw new IOException("Expected " + $i0 + " bytes, read " + $i1 + " bytes");
    }

    private static int read(InputStream $r0) throws IOException {
        int $i0 = $r0.read();
        if ($i0 != -1) {
            return $i0;
        }
        throw new EOFException();
    }

    static void writeInt(OutputStream $r0, int $i0) throws IOException {
        $r0.write(($i0 >> 0) & 255);
        $r0.write(($i0 >> 8) & 255);
        $r0.write(($i0 >> 16) & 255);
        $r0.write(($i0 >> 24) & 255);
    }

    static int readInt(InputStream $r0) throws IOException {
        return (((0 | (read($r0) << 0)) | (read($r0) << 8)) | (read($r0) << 16)) | (read($r0) << 24);
    }

    static void writeLong(OutputStream $r0, long $l0) throws IOException {
        $r0.write((byte) ((int) ($l0 >>> null)));
        $r0.write((byte) ((int) ($l0 >>> 8)));
        $r0.write((byte) ((int) ($l0 >>> 16)));
        $r0.write((byte) ((int) ($l0 >>> 24)));
        $r0.write((byte) ((int) ($l0 >>> 32)));
        $r0.write((byte) ((int) ($l0 >>> 40)));
        $r0.write((byte) ((int) ($l0 >>> 48)));
        $r0.write((byte) ((int) ($l0 >>> 56)));
    }

    static long readLong(InputStream $r0) throws IOException {
        return (((((((0 | ((((long) read($r0)) & 255) << null)) | ((((long) read($r0)) & 255) << 8)) | ((((long) read($r0)) & 255) << 16)) | ((((long) read($r0)) & 255) << 24)) | ((((long) read($r0)) & 255) << 32)) | ((((long) read($r0)) & 255) << 40)) | ((((long) read($r0)) & 255) << 48)) | ((((long) read($r0)) & 255) << 56);
    }

    static void writeString(OutputStream $r0, String $r1) throws IOException {
        byte[] $r2 = $r1.getBytes("UTF-8");
        writeLong($r0, (long) $r2.length);
        $r0.write($r2, 0, $r2.length);
    }

    static String readString(InputStream $r0) throws IOException {
        return new String(streamToBytes($r0, (int) readLong($r0)), "UTF-8");
    }

    static void writeStringStringMap(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/io/OutputStream;", ")V"}) Map<String, String> $r0, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/io/OutputStream;", ")V"}) OutputStream $r1) throws IOException {
        if ($r0 != null) {
            writeInt($r1, $r0.size());
            for (Map.Entry $r5 : $r0.entrySet()) {
                writeString($r1, (String) $r5.getKey());
                writeString($r1, (String) $r5.getValue());
            }
            return;
        }
        writeInt($r1, 0);
    }

    static Map<String, String> readStringStringMap(@Signature({"(", "Ljava/io/InputStream;", ")", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;"}) InputStream $r0) throws IOException {
        Map $r1;
        int $i0 = readInt($r0);
        if ($i0 == 0) {
            $r1 = Collections.emptyMap();
        } else {
            Object $r12 = r4;
            HashMap r4 = new HashMap($i0);
        }
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r1.put(readString($r0).intern(), readString($r0).intern());
        }
        return $r1;
    }
}
