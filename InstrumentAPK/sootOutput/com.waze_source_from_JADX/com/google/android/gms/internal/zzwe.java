package com.google.android.gms.internal;

import android.os.RemoteException;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzwe<T> {
    private final int zzayf;
    private final String zzayg;
    private final T zzayh;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zza extends zzwe<Boolean> {
        public zza(int $i0, String $r1, Boolean $r2) throws  {
            super($i0, $r1, $r2);
        }

        public /* synthetic */ Object zza(zzwh $r1) throws  {
            return zzb($r1);
        }

        public Boolean zzb(zzwh $r1) throws  {
            try {
                return Boolean.valueOf($r1.getBooleanFlagValue(getKey(), ((Boolean) getDefault()).booleanValue(), getSource()));
            } catch (RemoteException e) {
                return (Boolean) getDefault();
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zzb extends zzwe<Integer> {
        public zzb(int $i0, String $r1, Integer $r2) throws  {
            super($i0, $r1, $r2);
        }

        public /* synthetic */ Object zza(zzwh $r1) throws  {
            return zzc($r1);
        }

        public Integer zzc(zzwh $r1) throws  {
            try {
                return Integer.valueOf($r1.getIntFlagValue(getKey(), ((Integer) getDefault()).intValue(), getSource()));
            } catch (RemoteException e) {
                return (Integer) getDefault();
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zzc extends zzwe<Long> {
        public zzc(int $i0, String $r1, Long $r2) throws  {
            super($i0, $r1, $r2);
        }

        public /* synthetic */ Object zza(zzwh $r1) throws  {
            return zzd($r1);
        }

        public Long zzd(zzwh $r1) throws  {
            try {
                return Long.valueOf($r1.getLongFlagValue(getKey(), ((Long) getDefault()).longValue(), getSource()));
            } catch (RemoteException e) {
                return (Long) getDefault();
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zzd extends zzwe<String> {
        public zzd(int $i0, String $r1, String $r2) throws  {
            super($i0, $r1, $r2);
        }

        public /* synthetic */ Object zza(zzwh $r1) throws  {
            return zze($r1);
        }

        public String zze(zzwh $r1) throws  {
            try {
                return $r1.getStringFlagValue(getKey(), (String) getDefault(), getSource());
            } catch (RemoteException e) {
                return (String) getDefault();
            }
        }
    }

    private zzwe(@Signature({"(I", "Ljava/lang/String;", "TT;)V"}) int $i0, @Signature({"(I", "Ljava/lang/String;", "TT;)V"}) String $r1, @Signature({"(I", "Ljava/lang/String;", "TT;)V"}) T $r2) throws  {
        this.zzayf = $i0;
        this.zzayg = $r1;
        this.zzayh = $r2;
        zzwi.zzblg().zza(this);
    }

    public static zza zzb(int $i0, String $r0, Boolean $r1) throws  {
        return new zza($i0, $r0, $r1);
    }

    public static zzb zzb(int $i0, String $r0, int $i1) throws  {
        return new zzb($i0, $r0, Integer.valueOf($i1));
    }

    public static zzc zzb(int $i0, String $r0, long $l1) throws  {
        return new zzc($i0, $r0, Long.valueOf($l1));
    }

    public static zzd zzc(int $i0, String $r0, String $r1) throws  {
        return new zzd($i0, $r0, $r1);
    }

    public T get() throws  {
        return zzwi.zzblh().zzb(this);
    }

    public T getDefault() throws  {
        return this.zzayh;
    }

    public String getKey() throws  {
        return this.zzayg;
    }

    public int getSource() throws  {
        return this.zzayf;
    }

    protected abstract T zza(@Signature({"(", "Lcom/google/android/gms/internal/zzwh;", ")TT;"}) zzwh com_google_android_gms_internal_zzwh) throws ;
}
