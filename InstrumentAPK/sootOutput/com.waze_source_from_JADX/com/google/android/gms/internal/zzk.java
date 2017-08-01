package com.google.android.gms.internal;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import dalvik.annotation.Signature;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzk<T> implements Comparable<zzk<T>> {
    private final zza zzab;
    private final int zzac;
    private final String zzad;
    private final int zzae;
    private final com.google.android.gms.internal.zzm.zza zzaf;
    private Integer zzag;
    private zzl zzah;
    private boolean zzai;
    private boolean zzaj;
    private boolean zzak;
    private long zzal;
    private zzo zzam;
    private com.google.android.gms.internal.zzb.zza zzan;

    /* compiled from: dalvik_source_com.waze.apk */
    public enum zza {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }

    public zzk(int $i0, String $r1, com.google.android.gms.internal.zzm.zza $r2) throws  {
        this.zzab = zza.zzbi ? new zza() : null;
        this.zzai = true;
        this.zzaj = false;
        this.zzak = false;
        this.zzal = 0;
        this.zzan = null;
        this.zzac = $i0;
        this.zzad = $r1;
        this.zzaf = $r2;
        zza(new zzd());
        this.zzae = zzb($r1);
    }

    private byte[] zza(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")[B"}) Map<String, String> $r1, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")[B"}) String $r2) throws  {
        StringBuilder $r3 = new StringBuilder();
        try {
            for (Entry $r7 : $r1.entrySet()) {
                $r3.append(URLEncoder.encode((String) $r7.getKey(), $r2));
                $r3.append('=');
                $r3.append(URLEncoder.encode((String) $r7.getValue(), $r2));
                $r3.append('&');
            }
            return $r3.toString().getBytes($r2);
        } catch (UnsupportedEncodingException $r9) {
            String $r8 = "Encoding not supported: ";
            $r2 = String.valueOf($r2);
            if ($r2.length() != 0) {
                $r2 = $r8.concat($r2);
            } else {
                String str = new String("Encoding not supported: ");
            }
            throw new RuntimeException($r2, $r9);
        }
    }

    private static int zzb(String $r0) throws  {
        if (!TextUtils.isEmpty($r0)) {
            Uri $r1 = Uri.parse($r0);
            if ($r1 != null) {
                $r0 = $r1.getHost();
                if ($r0 != null) {
                    return $r0.hashCode();
                }
            }
        }
        return 0;
    }

    public /* synthetic */ int compareTo(Object $r1) throws  {
        return zzc((zzk) $r1);
    }

    public Map<String, String> getHeaders() throws zza {
        return Collections.emptyMap();
    }

    public int getMethod() throws  {
        return this.zzac;
    }

    public String getUrl() throws  {
        return this.zzad;
    }

    public boolean isCanceled() throws  {
        return false;
    }

    public String toString() throws  {
        String $r2 = "0x";
        String $r3 = String.valueOf(Integer.toHexString(zzf()));
        $r2 = $r3.length() != 0 ? $r2.concat($r3) : new String("0x");
        $r3 = String.valueOf(getUrl());
        String $r5 = String.valueOf(zzr());
        String $r7 = String.valueOf(this.zzag);
        return new StringBuilder(((((String.valueOf("[ ] ").length() + 3) + String.valueOf($r3).length()) + String.valueOf($r2).length()) + String.valueOf($r5).length()) + String.valueOf($r7).length()).append("[ ] ").append($r3).append(" ").append($r2).append(" ").append($r5).append(" ").append($r7).toString();
    }

    public final zzk<?> zza(@Signature({"(I)", "Lcom/google/android/gms/internal/zzk", "<*>;"}) int $i0) throws  {
        this.zzag = Integer.valueOf($i0);
        return this;
    }

    public zzk<?> zza(@Signature({"(", "Lcom/google/android/gms/internal/zzb$zza;", ")", "Lcom/google/android/gms/internal/zzk", "<*>;"}) com.google.android.gms.internal.zzb.zza $r1) throws  {
        this.zzan = $r1;
        return this;
    }

    public zzk<?> zza(@Signature({"(", "Lcom/google/android/gms/internal/zzl;", ")", "Lcom/google/android/gms/internal/zzk", "<*>;"}) zzl $r1) throws  {
        this.zzah = $r1;
        return this;
    }

    public zzk<?> zza(@Signature({"(", "Lcom/google/android/gms/internal/zzo;", ")", "Lcom/google/android/gms/internal/zzk", "<*>;"}) zzo $r1) throws  {
        this.zzam = $r1;
        return this;
    }

    protected abstract zzm<T> zza(@Signature({"(", "Lcom/google/android/gms/internal/zzi;", ")", "Lcom/google/android/gms/internal/zzm", "<TT;>;"}) zzi com_google_android_gms_internal_zzi) throws ;

    protected abstract void zza(@Signature({"(TT;)V"}) T t) throws ;

    protected zzr zzb(zzr $r1) throws  {
        return $r1;
    }

    public int zzc(@Signature({"(", "Lcom/google/android/gms/internal/zzk", "<TT;>;)I"}) zzk<T> $r1) throws  {
        zza $r2 = zzr();
        zza $r3 = $r1.zzr();
        return $r2 == $r3 ? this.zzag.intValue() - $r1.zzag.intValue() : $r3.ordinal() - $r2.ordinal();
    }

    public void zzc(zzr $r1) throws  {
        if (this.zzaf != null) {
            this.zzaf.zze($r1);
        }
    }

    public void zzc(String $r1) throws  {
        if (zza.zzbi) {
            this.zzab.zza($r1, Thread.currentThread().getId());
        } else if (this.zzal == 0) {
            this.zzal = SystemClock.elapsedRealtime();
        }
    }

    void zzd(String $r1) throws  {
        if (this.zzah != null) {
            this.zzah.zzf(this);
        }
        if (zza.zzbi) {
            final long $l0 = Thread.currentThread().getId();
            if (Looper.myLooper() != Looper.getMainLooper()) {
                final String str = $r1;
                new Handler(Looper.getMainLooper()).post(new Runnable(this) {
                    final /* synthetic */ zzk zzaq;

                    public void run() throws  {
                        this.zzaq.zzab.zza(str, $l0);
                        this.zzaq.zzab.zzd(toString());
                    }
                });
                return;
            }
            this.zzab.zza($r1, $l0);
            this.zzab.zzd(toString());
            return;
        }
        if (SystemClock.elapsedRealtime() - this.zzal >= 3000) {
            zzs.zzb("%d ms: %s", Long.valueOf(SystemClock.elapsedRealtime() - this.zzal), toString());
        }
    }

    public int zzf() throws  {
        return this.zzae;
    }

    public String zzg() throws  {
        return getUrl();
    }

    public com.google.android.gms.internal.zzb.zza zzh() throws  {
        return this.zzan;
    }

    @Deprecated
    protected Map<String, String> zzi() throws zza {
        return zzm();
    }

    @Deprecated
    protected String zzj() throws  {
        return zzn();
    }

    @Deprecated
    public String zzk() throws  {
        return zzo();
    }

    @Deprecated
    public byte[] zzl() throws zza {
        Map $r1 = zzi();
        return ($r1 == null || $r1.size() <= 0) ? null : zza($r1, zzj());
    }

    protected Map<String, String> zzm() throws zza {
        return null;
    }

    protected String zzn() throws  {
        return "UTF-8";
    }

    public String zzo() throws  {
        String $r1 = "application/x-www-form-urlencoded; charset=";
        String $r2 = String.valueOf(zzn());
        return $r2.length() != 0 ? $r1.concat($r2) : new String("application/x-www-form-urlencoded; charset=");
    }

    public byte[] zzp() throws zza {
        Map $r1 = zzm();
        return ($r1 == null || $r1.size() <= 0) ? null : zza($r1, zzn());
    }

    public final boolean zzq() throws  {
        return this.zzai;
    }

    public zza zzr() throws  {
        return zza.NORMAL;
    }

    public final int zzs() throws  {
        return this.zzam.zzc();
    }

    public zzo zzt() throws  {
        return this.zzam;
    }

    public void zzu() throws  {
        this.zzak = true;
    }

    public boolean zzv() throws  {
        return this.zzak;
    }
}
