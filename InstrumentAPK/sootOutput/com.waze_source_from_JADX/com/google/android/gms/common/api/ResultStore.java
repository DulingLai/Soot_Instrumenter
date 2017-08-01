package com.google.android.gms.common.api;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.google.android.gms.internal.zzrg;
import com.google.android.gms.internal.zzrk;
import com.google.android.gms.internal.zzry;
import dalvik.annotation.Signature;
import java.util.Map;
import java.util.WeakHashMap;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class ResultStore {
    private static final Map<Object, ResultStore> CO = new WeakHashMap();
    private static final Object zzanj = new Object();

    @NonNull
    public static ResultStore getInstance(@NonNull Activity $r0, @NonNull GoogleApiClient $r1) throws  {
        return zza(new zzrg($r0), $r1);
    }

    private static ResultStore zza(FragmentActivity $r0) throws  {
        FragmentManager $r1 = $r0.getSupportFragmentManager();
        try {
            zzry $r3 = (zzry) $r1.findFragmentByTag("GmsResultStoreFragment");
            if ($r3 == null) {
                $r3 = new zzry();
                $r1.beginTransaction().add((Fragment) $r3, "GmsResultStoreFragment").commit();
            }
            return $r3.zzaul();
        } catch (ClassCastException e) {
            throw new IllegalStateException(new StringBuilder(String.valueOf("GmsResultStoreFragment").length() + 42).append("Fragment tag ").append("GmsResultStoreFragment").append(" is reserved for ResultStore.").toString());
        }
    }

    private static ResultStore zza(@NonNull zzrg $r0, @NonNull GoogleApiClient $r1) throws  {
        ResultStore $r5;
        synchronized (zzanj) {
            $r5 = (ResultStore) CO.get($r0.zzauj());
            if ($r5 == null) {
                $r5 = $r0.zzaug() ? zza($r0.zzaui()) : zzs($r0.zzauh());
                CO.put($r0.zzauj(), $r5);
            }
            $r1.zza($r5);
        }
        return $r5;
    }

    @TargetApi(11)
    private static ResultStore zzs(Activity $r0) throws  {
        android.app.FragmentManager $r1 = $r0.getFragmentManager();
        try {
            zzrk $r3 = (zzrk) $r1.findFragmentByTag("GmsResultStoreFragment");
            if ($r3 == null) {
                $r3 = new zzrk();
                $r1.beginTransaction().add($r3, "GmsResultStoreFragment").commit();
            }
            return $r3.zzaul();
        } catch (ClassCastException e) {
            throw new IllegalStateException(new StringBuilder(String.valueOf("GmsResultStoreFragment").length() + 42).append("Fragment tag ").append("GmsResultStoreFragment").append(" is reserved for ResultStore.").toString());
        }
    }

    protected static void zzx(Object $r0) throws  {
        synchronized (zzanj) {
            CO.remove($r0);
        }
    }

    public abstract boolean hasPendingResult(int i) throws ;

    public abstract void remove(int i) throws ;

    public abstract void setResultCallbacks(@Signature({"(I", "Lcom/google/android/gms/common/api/ResultCallbacks", "<*>;)V"}) int i, @NonNull @Signature({"(I", "Lcom/google/android/gms/common/api/ResultCallbacks", "<*>;)V"}) ResultCallbacks<?> resultCallbacks) throws ;

    public <R extends Result> void zza(@Signature({"<R::", "Lcom/google/android/gms/common/api/Result;", ">(I", "Lcom/google/android/gms/common/api/PendingResult", "<TR;>;)V"}) int i, @NonNull @Signature({"<R::", "Lcom/google/android/gms/common/api/Result;", ">(I", "Lcom/google/android/gms/common/api/PendingResult", "<TR;>;)V"}) PendingResult<R> pendingResult) throws  {
        throw new UnsupportedOperationException();
    }
}
