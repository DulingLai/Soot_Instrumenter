package com.google.android.gms.internal;

import android.os.SystemClock;
import dalvik.annotation.Signature;
import java.io.BufferedInputStream;
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
import java.util.Map.Entry;

/* compiled from: dalvik_source_com.waze.apk */
public class zzv implements zzb {
    private final Map<String, zza> zzbv;
    private long zzbw;
    private final File zzbx;
    private final int zzby;

    /* compiled from: dalvik_source_com.waze.apk */
    static class zza {
        public String zza;
        public long zzb;
        public long zzbz;
        public long zzc;
        public String zzca;
        public long zzd;
        public long zze;
        public Map<String, String> zzf;

        private zza() throws  {
        }

        public zza(String $r1, com.google.android.gms.internal.zzb.zza $r2) throws  {
            this.zzca = $r1;
            this.zzbz = (long) $r2.data.length;
            this.zza = $r2.zza;
            this.zzb = $r2.zzb;
            this.zzc = $r2.zzc;
            this.zzd = $r2.zzd;
            this.zze = $r2.zze;
            this.zzf = $r2.zzf;
        }

        public static zza zzf(InputStream $r0) throws IOException {
            zza $r1 = new zza();
            if (zzv.zzb($r0) != 538247942) {
                throw new IOException();
            }
            $r1.zzca = zzv.zzd($r0);
            $r1.zza = zzv.zzd($r0);
            if ($r1.zza.equals("")) {
                $r1.zza = null;
            }
            $r1.zzb = zzv.zzc($r0);
            $r1.zzc = zzv.zzc($r0);
            $r1.zzd = zzv.zzc($r0);
            $r1.zze = zzv.zzc($r0);
            $r1.zzf = zzv.zze($r0);
            return $r1;
        }

        public boolean zza(OutputStream $r1) throws  {
            try {
                zzv.zza($r1, 538247942);
                zzv.zza($r1, this.zzca);
                zzv.zza($r1, this.zza == null ? "" : this.zza);
                zzv.zza($r1, this.zzb);
                zzv.zza($r1, this.zzc);
                zzv.zza($r1, this.zzd);
                zzv.zza($r1, this.zze);
                zzv.zza(this.zzf, $r1);
                $r1.flush();
                return true;
            } catch (IOException $r4) {
                zzs.zzb("%s", $r4.toString());
                return false;
            }
        }

        public com.google.android.gms.internal.zzb.zza zzb(byte[] $r1) throws  {
            com.google.android.gms.internal.zzb.zza $r2 = new com.google.android.gms.internal.zzb.zza();
            $r2.data = $r1;
            $r2.zza = this.zza;
            $r2.zzb = this.zzb;
            $r2.zzc = this.zzc;
            $r2.zzd = this.zzd;
            $r2.zze = this.zze;
            $r2.zzf = this.zzf;
            return $r2;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zzb extends FilterInputStream {
        private int zzcb;

        private zzb(InputStream $r1) throws  {
            super($r1);
            this.zzcb = 0;
        }

        public int read() throws IOException {
            int $i0 = super.read();
            if ($i0 == -1) {
                return $i0;
            }
            this.zzcb++;
            return $i0;
        }

        public int read(byte[] $r1, int $i0, int $i1) throws IOException {
            $i0 = super.read($r1, $i0, $i1);
            if ($i0 == -1) {
                return $i0;
            }
            this.zzcb += $i0;
            return $i0;
        }
    }

    public zzv(File $r1) throws  {
        this($r1, 5242880);
    }

    public zzv(File $r1, int $i0) throws  {
        this.zzbv = new LinkedHashMap(16, 0.75f, true);
        this.zzbw = 0;
        this.zzbx = $r1;
        this.zzby = $i0;
    }

    private void removeEntry(String $r1) throws  {
        zza $r4 = (zza) this.zzbv.get($r1);
        if ($r4 != null) {
            this.zzbw -= $r4.zzbz;
            this.zzbv.remove($r1);
        }
    }

    private static int zza(InputStream $r0) throws IOException {
        int $i0 = $r0.read();
        if ($i0 != -1) {
            return $i0;
        }
        throw new EOFException();
    }

    static void zza(OutputStream $r0, int $i0) throws IOException {
        $r0.write(($i0 >> 0) & 255);
        $r0.write(($i0 >> 8) & 255);
        $r0.write(($i0 >> 16) & 255);
        $r0.write(($i0 >> 24) & 255);
    }

    static void zza(OutputStream $r0, long $l0) throws IOException {
        $r0.write((byte) ((int) ($l0 >>> null)));
        $r0.write((byte) ((int) ($l0 >>> 8)));
        $r0.write((byte) ((int) ($l0 >>> 16)));
        $r0.write((byte) ((int) ($l0 >>> 24)));
        $r0.write((byte) ((int) ($l0 >>> 32)));
        $r0.write((byte) ((int) ($l0 >>> 40)));
        $r0.write((byte) ((int) ($l0 >>> 48)));
        $r0.write((byte) ((int) ($l0 >>> 56)));
    }

    static void zza(OutputStream $r0, String $r1) throws IOException {
        byte[] $r2 = $r1.getBytes("UTF-8");
        zza($r0, (long) $r2.length);
        $r0.write($r2, 0, $r2.length);
    }

    private void zza(String $r1, zza $r2) throws  {
        if (this.zzbv.containsKey($r1)) {
            zza $r5 = (zza) this.zzbv.get($r1);
            this.zzbw = ($r2.zzbz - $r5.zzbz) + this.zzbw;
        } else {
            this.zzbw += $r2.zzbz;
        }
        this.zzbv.put($r1, $r2);
    }

    static void zza(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/io/OutputStream;", ")V"}) Map<String, String> $r0, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/io/OutputStream;", ")V"}) OutputStream $r1) throws IOException {
        if ($r0 != null) {
            zza($r1, $r0.size());
            for (Entry $r5 : $r0.entrySet()) {
                zza($r1, (String) $r5.getKey());
                zza($r1, (String) $r5.getValue());
            }
            return;
        }
        zza($r1, 0);
    }

    private static byte[] zza(InputStream $r0, int $i0) throws IOException {
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

    static int zzb(InputStream $r0) throws IOException {
        return ((((zza($r0) << 0) | 0) | (zza($r0) << 8)) | (zza($r0) << 16)) | (zza($r0) << 24);
    }

    static long zzc(InputStream $r0) throws IOException {
        return (((((((0 | ((((long) zza($r0)) & 255) << null)) | ((((long) zza($r0)) & 255) << 8)) | ((((long) zza($r0)) & 255) << 16)) | ((((long) zza($r0)) & 255) << 24)) | ((((long) zza($r0)) & 255) << 32)) | ((((long) zza($r0)) & 255) << 40)) | ((((long) zza($r0)) & 255) << 48)) | ((((long) zza($r0)) & 255) << 56);
    }

    private void zzc(int $i0) throws  {
        if (this.zzbw + ((long) $i0) >= ((long) this.zzby)) {
            Object[] $r1;
            if (zzs.DEBUG) {
                zzs.zza("Pruning old cache entries.", new Object[0]);
            }
            long $l2 = this.zzbw;
            long $l1 = SystemClock.elapsedRealtime();
            Iterator $r4 = this.zzbv.entrySet().iterator();
            int $i3 = 0;
            while ($r4.hasNext()) {
                long j;
                zza $r7 = (zza) ((Entry) $r4.next()).getValue();
                if (zzf($r7.zzca).delete()) {
                    long $l5 = this.zzbw - $r7.zzbz;
                    j = $l5;
                    this.zzbw = $l5;
                } else {
                    $r1 = new Object[2];
                    $r1[0] = $r7.zzca;
                    $r1[1] = zze($r7.zzca);
                    zzs.zzb("Could not delete cache entry for key=%s, filename=%s", $r1);
                }
                $r4.remove();
                $i3++;
                float $f0 = this.zzbw + ((long) $i0);
                j = $f0;
                float $f02 = (float) $f0;
                int $f1 = this.zzby;
                int $i7 = $f1;
                if ($f02 < ((float) $f1) * 0.9f) {
                    break;
                }
            }
            if (zzs.DEBUG) {
                $r1 = new Object[3];
                $r1[0] = Integer.valueOf($i3);
                $r1[1] = Long.valueOf(this.zzbw - $l2);
                $r1[2] = Long.valueOf(SystemClock.elapsedRealtime() - $l1);
                zzs.zza("pruned %d files, %d bytes, %d ms", $r1);
            }
        }
    }

    static String zzd(InputStream $r0) throws IOException {
        return new String(zza($r0, (int) zzc($r0)), "UTF-8");
    }

    private String zze(String $r1) throws  {
        int $i0 = $r1.length() / 2;
        String $r2 = String.valueOf(String.valueOf($r1.substring(0, $i0).hashCode()));
        $r1 = String.valueOf(String.valueOf($r1.substring($i0).hashCode()));
        return $r1.length() != 0 ? $r2.concat($r1) : new String($r2);
    }

    static Map<String, String> zze(@Signature({"(", "Ljava/io/InputStream;", ")", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;"}) InputStream $r0) throws IOException {
        Map $r1;
        int $i0 = zzb($r0);
        if ($i0 == 0) {
            $r1 = Collections.emptyMap();
        } else {
            Object $r12 = r4;
            HashMap r4 = new HashMap($i0);
        }
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r1.put(zzd($r0).intern(), zzd($r0).intern());
        }
        return $r1;
    }

    public synchronized void initialize() throws  {
        Throwable $r11;
        this = this;
        if (this.zzbx.exists()) {
            this = this;
            File[] $r4 = this.zzbx.listFiles();
            if ($r4 != null) {
                for (File $r1 : $r4) {
                    BufferedInputStream $r5 = null;
                    BufferedInputStream $r6;
                    try {
                        $r6 = new BufferedInputStream(new FileInputStream($r1));
                        try {
                            zza $r8 = zza.zzf($r6);
                            $r8.zzbz = $r1.length();
                            zza($r8.zzca, $r8);
                            if ($r6 != null) {
                                try {
                                    $r6.close();
                                } catch (IOException e) {
                                }
                            }
                        } catch (IOException e2) {
                            if ($r1 != null) {
                                try {
                                    $r1.delete();
                                } catch (Throwable $r15) {
                                    $r5 = $r6;
                                    $r11 = $r15;
                                }
                            }
                            if ($r6 != null) {
                                try {
                                    $r6.close();
                                } catch (IOException e3) {
                                }
                            }
                        }
                    } catch (IOException e4) {
                        $r6 = null;
                        if ($r1 != null) {
                            $r1.delete();
                        }
                        if ($r6 != null) {
                            $r6.close();
                        }
                    } catch (Throwable th) {
                        $r11 = th;
                    }
                }
            }
        } else {
            this = this;
            if (!this.zzbx.mkdirs()) {
                Object[] $r2 = new Object[1];
                this = this;
                $r2[0] = this.zzbx.getAbsolutePath();
                zzs.zzc("Unable to create cache dir %s", $r2);
            }
        }
        return;
        if ($r5 != null) {
            try {
                $r5.close();
            } catch (IOException e5) {
            }
        }
        throw $r11;
        throw $r11;
    }

    public synchronized void remove(String $r1) throws  {
        boolean $z0 = zzf($r1).delete();
        removeEntry($r1);
        if (!$z0) {
            zzs.zzb("Could not delete cache entry for key=%s, filename=%s", $r1, zze($r1));
        }
    }

    public synchronized com.google.android.gms.internal.zzb.zza zza(String $r1) throws  {
        com.google.android.gms.internal.zzb.zza $r5;
        zzb $r7;
        IOException $r11;
        Throwable $r15;
        zza $r4 = (zza) this.zzbv.get($r1);
        if ($r4 == null) {
            $r5 = null;
        } else {
            File $r6 = zzf($r1);
            try {
                $r7 = new zzb(new FileInputStream($r6));
                try {
                    zza.zzf($r7);
                    $r5 = $r4.zzb(zza((InputStream) $r7, (int) ($r6.length() - ((long) $r7.zzcb))));
                    if ($r7 != null) {
                        try {
                            $r7.close();
                        } catch (IOException e) {
                            $r5 = null;
                        }
                    }
                } catch (IOException e2) {
                    $r11 = e2;
                    try {
                        zzs.zzb("%s: %s", $r6.getAbsolutePath(), $r11.toString());
                        remove($r1);
                        if ($r7 != null) {
                            try {
                                $r7.close();
                            } catch (IOException e3) {
                                $r5 = null;
                            }
                        }
                        $r5 = null;
                        return $r5;
                    } catch (Throwable th) {
                        $r15 = th;
                        if ($r7 != null) {
                            try {
                                $r7.close();
                            } catch (IOException e4) {
                                $r5 = null;
                            }
                        }
                        throw $r15;
                    }
                }
            } catch (IOException e5) {
                $r11 = e5;
                $r7 = null;
                zzs.zzb("%s: %s", $r6.getAbsolutePath(), $r11.toString());
                remove($r1);
                if ($r7 != null) {
                    $r7.close();
                }
                $r5 = null;
                return $r5;
            } catch (Throwable th2) {
                $r15 = th2;
                $r7 = null;
                if ($r7 != null) {
                    $r7.close();
                }
                throw $r15;
            }
        }
        return $r5;
    }

    public synchronized void zza(String $r1, com.google.android.gms.internal.zzb.zza $r2) throws  {
        zzc($r2.data.length);
        File $r4 = zzf($r1);
        try {
            FileOutputStream $r5 = new FileOutputStream($r4);
            zza $r6 = new zza($r1, $r2);
            if ($r6.zza($r5)) {
                $r5.write($r2.data);
                $r5.close();
                zza($r1, $r6);
            } else {
                $r5.close();
                zzs.zzb("Failed to write header for %s", $r4.getAbsolutePath());
                throw new IOException();
            }
        } catch (IOException e) {
            if (!$r4.delete()) {
                zzs.zzb("Could not clean up file %s", $r4.getAbsolutePath());
            }
        }
    }

    public File zzf(String $r1) throws  {
        return new File(this.zzbx, zze($r1));
    }
}
