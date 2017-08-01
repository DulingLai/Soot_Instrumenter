package com.google.android.gms.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: dalvik_source_com.waze.apk */
public class zzrn {
    private final Set<zzrm<?>> lO = Collections.newSetFromMap(new WeakHashMap());

    public void release() throws  {
        for (zzrm clear : this.lO) {
            clear.clear();
        }
        this.lO.clear();
    }

    public <L> zzrm<L> zzb(@NonNull @Signature({"<", "L:Ljava/lang/Object;", ">(T", "L;", "Landroid/os/Looper;", ")", "Lcom/google/android/gms/internal/zzrm", "<T", "L;", ">;"}) L $r1, @Signature({"<", "L:Ljava/lang/Object;", ">(T", "L;", "Landroid/os/Looper;", ")", "Lcom/google/android/gms/internal/zzrm", "<T", "L;", ">;"}) Looper $r2) throws  {
        zzab.zzb((Object) $r1, (Object) "Listener must not be null");
        zzab.zzb((Object) $r2, (Object) "Looper must not be null");
        zzrm $r4 = new zzrm($r2, $r1);
        this.lO.add($r4);
        return $r4;
    }
}
