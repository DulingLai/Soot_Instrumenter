package com.google.android.gms.internal;

import android.content.ContentResolver;
import android.os.Binder;
import android.util.Log;
import dalvik.annotation.Signature;
import java.util.Collection;
import java.util.HashSet;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzse<T> {
    private static zza GF = null;
    private static int GG = 0;
    private static String READ_PERMISSION = "com.google.android.providers.gsf.permission.READ_GSERVICES";
    private static final Object zzanj = new Object();
    protected final String zzayg;
    protected final T zzayh;
    private T zzcze = null;

    /* compiled from: dalvik_source_com.waze.apk */
    class C08521 extends zzse<Boolean> {
        C08521(String $r1, Boolean $r2) throws  {
            super($r1, $r2);
        }

        protected /* synthetic */ Object zzgf(String $r1) throws  {
            return zzgg($r1);
        }

        protected Boolean zzgg(String str) throws  {
            return zzse.GF.zza(this.zzayg, (Boolean) this.zzayh);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C08532 extends zzse<Long> {
        C08532(String $r1, Long $r2) throws  {
            super($r1, $r2);
        }

        protected /* synthetic */ Object zzgf(String $r1) throws  {
            return zzgh($r1);
        }

        protected Long zzgh(String str) throws  {
            return zzse.GF.getLong(this.zzayg, (Long) this.zzayh);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C08543 extends zzse<Integer> {
        C08543(String $r1, Integer $r2) throws  {
            super($r1, $r2);
        }

        protected /* synthetic */ Object zzgf(String $r1) throws  {
            return zzgi($r1);
        }

        protected Integer zzgi(String str) throws  {
            return zzse.GF.zzb(this.zzayg, (Integer) this.zzayh);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C08554 extends zzse<Float> {
        C08554(String $r1, Float $r2) throws  {
            super($r1, $r2);
        }

        protected /* synthetic */ Object zzgf(String $r1) throws  {
            return zzgj($r1);
        }

        protected Float zzgj(String str) throws  {
            return zzse.GF.zzb(this.zzayg, (Float) this.zzayh);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C08565 extends zzse<String> {
        C08565(String $r1, String $r2) throws  {
            super($r1, $r2);
        }

        protected /* synthetic */ Object zzgf(String $r1) throws  {
            return zzgk($r1);
        }

        protected String zzgk(String str) throws  {
            return zzse.GF.getString(this.zzayg, (String) this.zzayh);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private interface zza {
        Long getLong(String str, Long l) throws ;

        String getString(String str, String str2) throws ;

        Boolean zza(String str, Boolean bool) throws ;

        Float zzb(String str, Float f) throws ;

        Integer zzb(String str, Integer num) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zzb implements zza {
        private static final Collection<zzse<?>> GH = new HashSet();

        private zzb() throws  {
        }

        public Long getLong(String str, Long $r2) throws  {
            return $r2;
        }

        public String getString(String str, String $r2) throws  {
            return $r2;
        }

        public Boolean zza(String str, Boolean $r2) throws  {
            return $r2;
        }

        public Float zzb(String str, Float $r2) throws  {
            return $r2;
        }

        public Integer zzb(String str, Integer $r2) throws  {
            return $r2;
        }
    }

    @Deprecated
    /* compiled from: dalvik_source_com.waze.apk */
    private static class zzc implements zza {
        private <T> T zzg(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "TT;)TT;"}) String str, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "TT;)TT;"}) T t) throws  {
            throw new NullPointerException("This statement would have triggered an Exception: $u-1#2 = interfaceinvoke $u1.<java.util.Map: boolean containsKey(java.lang.Object)>($u3)");
        }

        public Long getLong(String $r1, Long $r2) throws  {
            return (Long) zzg($r1, $r2);
        }

        public String getString(String $r1, String $r2) throws  {
            return (String) zzg($r1, $r2);
        }

        public Boolean zza(String $r1, Boolean $r2) throws  {
            return (Boolean) zzg($r1, $r2);
        }

        public Float zzb(String $r1, Float $r2) throws  {
            return (Float) zzg($r1, $r2);
        }

        public Integer zzb(String $r1, Integer $r2) throws  {
            return (Integer) zzg($r1, $r2);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zzd implements zza {
        private final ContentResolver mContentResolver;

        public zzd(ContentResolver $r1) throws  {
            this.mContentResolver = $r1;
        }

        public Long getLong(String $r1, Long $r2) throws  {
            return Long.valueOf(zzamj.getLong(this.mContentResolver, $r1, $r2.longValue()));
        }

        public String getString(String $r1, String $r2) throws  {
            return zzamj.zza(this.mContentResolver, $r1, $r2);
        }

        public Boolean zza(String $r1, Boolean $r2) throws  {
            return Boolean.valueOf(zzamj.zza(this.mContentResolver, $r1, $r2.booleanValue()));
        }

        public Float zzb(String $r1, Float $r2) throws  {
            $r1 = zzamj.zza(this.mContentResolver, $r1, null);
            if ($r1 == null) {
                return $r2;
            }
            try {
                $r2 = Float.valueOf(Float.parseFloat($r1));
                return $r2;
            } catch (NumberFormatException e) {
                return $r2;
            }
        }

        public Integer zzb(String $r1, Integer $r2) throws  {
            return Integer.valueOf(zzamj.getInt(this.mContentResolver, $r1, $r2.intValue()));
        }
    }

    protected zzse(@Signature({"(", "Ljava/lang/String;", "TT;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "TT;)V"}) T $r2) throws  {
        this.zzayg = $r1;
        this.zzayh = $r2;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void init(android.content.Context r12) throws  {
        /*
        r0 = zzanj;
        monitor-enter(r0);
        r1 = GF;	 Catch:{ Throwable -> 0x0032 }
        if (r1 != 0) goto L_0x0012;
    L_0x0007:
        r2 = new com.google.android.gms.internal.zzse$zzd;	 Catch:{ Throwable -> 0x0032 }
        r3 = r12.getContentResolver();	 Catch:{ Throwable -> 0x0032 }
        r2.<init>(r3);	 Catch:{ Throwable -> 0x0032 }
        GF = r2;	 Catch:{ Throwable -> 0x0032 }
    L_0x0012:
        r4 = GG;	 Catch:{ Throwable -> 0x0032 }
        if (r4 != 0) goto L_0x0025;
    L_0x0016:
        r5 = r12.getPackageManager();	 Catch:{ NameNotFoundException -> 0x0027 }
        r7 = "com.google.android.gms";
        r8 = 0;
        r6 = r5.getApplicationInfo(r7, r8);	 Catch:{ NameNotFoundException -> 0x0027 }
        r4 = r6.uid;	 Catch:{ Throwable -> 0x0032 }
        GG = r4;	 Catch:{ Throwable -> 0x0032 }
    L_0x0025:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0032 }
        return;
    L_0x0027:
        r9 = move-exception;
        r10 = r9.toString();	 Catch:{ Throwable -> 0x0032 }
        r7 = "GservicesValue";
        android.util.Log.e(r7, r10);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x0025;
    L_0x0032:
        r11 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0032 }
        throw r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzse.init(android.content.Context):void");
    }

    public static zzse<Float> zza(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/Float;", ")", "Lcom/google/android/gms/internal/zzse", "<", "Ljava/lang/Float;", ">;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/Float;", ")", "Lcom/google/android/gms/internal/zzse", "<", "Ljava/lang/Float;", ">;"}) Float $r1) throws  {
        return new C08554($r0, $r1);
    }

    public static zzse<Integer> zza(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/Integer;", ")", "Lcom/google/android/gms/internal/zzse", "<", "Ljava/lang/Integer;", ">;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/Integer;", ")", "Lcom/google/android/gms/internal/zzse", "<", "Ljava/lang/Integer;", ">;"}) Integer $r1) throws  {
        return new C08543($r0, $r1);
    }

    public static zzse<Long> zza(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/Long;", ")", "Lcom/google/android/gms/internal/zzse", "<", "Ljava/lang/Long;", ">;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/Long;", ")", "Lcom/google/android/gms/internal/zzse", "<", "Ljava/lang/Long;", ">;"}) Long $r1) throws  {
        return new C08532($r0, $r1);
    }

    public static zzse<String> zzai(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/internal/zzse", "<", "Ljava/lang/String;", ">;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/internal/zzse", "<", "Ljava/lang/String;", ">;"}) String $r1) throws  {
        return new C08565($r0, $r1);
    }

    public static void zzauv() throws  {
        synchronized (zzanj) {
            GF = new zzb();
        }
    }

    private static boolean zzauw() throws  {
        boolean $z0;
        synchronized (zzanj) {
            $z0 = (GF instanceof zzb) || (GF instanceof zzc);
        }
        return $z0;
    }

    public static void zzaux() throws  {
        synchronized (zzanj) {
            if (zzauw()) {
                for (zzse resetOverride : zzb.GH) {
                    resetOverride.resetOverride();
                }
                zzb.GH.clear();
            }
        }
    }

    public static zzse<Boolean> zzn(@Signature({"(", "Ljava/lang/String;", "Z)", "Lcom/google/android/gms/internal/zzse", "<", "Ljava/lang/Boolean;", ">;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "Z)", "Lcom/google/android/gms/internal/zzse", "<", "Ljava/lang/Boolean;", ">;"}) boolean $z0) throws  {
        return new C08521($r0, Boolean.valueOf($z0));
    }

    public final T get() throws  {
        long $l0;
        Object $r1 = this.zzcze;
        if ($r1 != null) {
            return this.zzcze;
        }
        try {
            $r1 = zzgf(this.zzayg);
            return $r1;
        } catch (SecurityException e) {
            $l0 = Binder.clearCallingIdentity();
            $r1 = zzgf(this.zzayg);
            return $r1;
        } finally {
            Binder.restoreCallingIdentity($l0);
        }
    }

    @Deprecated
    public final T getBinderSafe() throws  {
        return get();
    }

    public void override(@Signature({"(TT;)V"}) T $r1) throws  {
        if (!(GF instanceof zzb)) {
            Log.w("GservicesValue", "GservicesValue.override(): test should probably call initForTests() first");
        }
        this.zzcze = $r1;
        synchronized (zzanj) {
            if (zzauw()) {
                zzb.GH.add(this);
            }
        }
    }

    public void resetOverride() throws  {
        this.zzcze = null;
    }

    protected abstract T zzgf(@Signature({"(", "Ljava/lang/String;", ")TT;"}) String str) throws ;
}
