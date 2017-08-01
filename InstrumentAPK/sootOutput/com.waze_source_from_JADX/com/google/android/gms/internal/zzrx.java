package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import dalvik.annotation.Signature;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzrx extends Fragment implements zzri {
    private static WeakHashMap<FragmentActivity, WeakReference<zzrx>> FN = new WeakHashMap();
    private Map<String, zzrh> FO = new ArrayMap();
    private Bundle kE;
    private int zzbls = 0;

    public static zzrx zzb(FragmentActivity $r0) throws  {
        zzrx $r4;
        WeakReference $r3 = (WeakReference) FN.get($r0);
        if ($r3 != null) {
            $r4 = (zzrx) $r3.get();
            if ($r4 != null) {
                return $r4;
            }
        }
        try {
            $r4 = (zzrx) $r0.getSupportFragmentManager().findFragmentByTag("SupportLifecycleFragmentImpl");
            if ($r4 == null || $r4.isRemoving()) {
                $r4 = new zzrx();
                $r0.getSupportFragmentManager().beginTransaction().add((Fragment) $r4, "SupportLifecycleFragmentImpl").commitAllowingStateLoss();
            }
            FN.put($r0, new WeakReference($r4));
            return $r4;
        } catch (ClassCastException $r8) {
            throw new IllegalStateException("Fragment with tag SupportLifecycleFragmentImpl is not a SupportLifecycleFragmentImpl", $r8);
        }
    }

    private void zzb(final String $r1, @NonNull final zzrh $r2) throws  {
        if (this.zzbls > 0) {
            new Handler(Looper.getMainLooper()).post(new Runnable(this) {
                final /* synthetic */ zzrx Gb;

                public void run() throws  {
                    if (this.Gb.zzbls >= 1) {
                        $r2.onCreate(this.Gb.kE != null ? this.Gb.kE.getBundle($r1) : null);
                    }
                    if (this.Gb.zzbls >= 2) {
                        $r2.onStart();
                    }
                    if (this.Gb.zzbls >= 3) {
                        $r2.onStop();
                    }
                }
            });
        }
    }

    public void dump(String $r1, FileDescriptor $r2, PrintWriter $r3, String[] $r4) throws  {
        super.dump($r1, $r2, $r3, $r4);
        for (zzrh dump : this.FO.values()) {
            dump.dump($r1, $r2, $r3, $r4);
        }
    }

    public void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        super.onActivityResult($i0, $i1, $r1);
        for (zzrh onActivityResult : this.FO.values()) {
            onActivityResult.onActivityResult($i0, $i1, $r1);
        }
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.zzbls = 1;
        this.kE = $r1;
        for (Entry $r6 : this.FO.entrySet()) {
            ((zzrh) $r6.getValue()).onCreate($r1 != null ? $r1.getBundle((String) $r6.getKey()) : null);
        }
    }

    public void onSaveInstanceState(Bundle $r1) throws  {
        super.onSaveInstanceState($r1);
        if ($r1 != null) {
            for (Entry $r7 : this.FO.entrySet()) {
                Bundle $r2 = new Bundle();
                ((zzrh) $r7.getValue()).onSaveInstanceState($r2);
                $r1.putBundle((String) $r7.getKey(), $r2);
            }
        }
    }

    public void onStart() throws  {
        super.onStop();
        this.zzbls = 2;
        for (zzrh onStart : this.FO.values()) {
            onStart.onStart();
        }
    }

    public void onStop() throws  {
        super.onStop();
        this.zzbls = 3;
        for (zzrh onStop : this.FO.values()) {
            onStop.onStop();
        }
    }

    public <T extends zzrh> T zza(@Signature({"<T:", "Lcom/google/android/gms/internal/zzrh;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)TT;"}) String $r1, @Signature({"<T:", "Lcom/google/android/gms/internal/zzrh;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> $r2) throws  {
        return (zzrh) $r2.cast(this.FO.get($r1));
    }

    public void zza(String $r1, @NonNull zzrh $r2) throws  {
        if (this.FO.containsKey($r1)) {
            throw new IllegalArgumentException(new StringBuilder(String.valueOf($r1).length() + 59).append("LifecycleCallback with tag ").append($r1).append(" already added to this fragment.").toString());
        }
        this.FO.put($r1, $r2);
        zzb($r1, $r2);
    }

    public /* synthetic */ Activity zzauk() throws  {
        return zzauo();
    }

    public FragmentActivity zzauo() throws  {
        return getActivity();
    }
}
