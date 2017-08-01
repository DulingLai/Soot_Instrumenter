package com.google.android.gms.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.zzrm.zzb;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzqq<L> implements zzb<L> {
    private final DataHolder DW;

    protected zzqq(DataHolder $r1) throws  {
        this.DW = $r1;
    }

    protected abstract void zza(@Signature({"(T", "L;", "Lcom/google/android/gms/common/data/DataHolder;", ")V"}) L l, @Signature({"(T", "L;", "Lcom/google/android/gms/common/data/DataHolder;", ")V"}) DataHolder dataHolder) throws ;

    public void zzata() throws  {
        if (this.DW != null) {
            this.DW.close();
        }
    }

    public final void zzy(@Signature({"(T", "L;", ")V"}) L $r1) throws  {
        zza($r1, this.DW);
    }
}
