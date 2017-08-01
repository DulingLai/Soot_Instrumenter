package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public final class Scope extends AbstractSafeParcelable {
    public static final Creator<Scope> CREATOR = new zzd();
    private final String CP;
    final int mVersionCode;

    Scope(int $i0, String $r1) throws  {
        zzab.zzi($r1, "scopeUri must not be null or empty");
        this.mVersionCode = $i0;
        this.CP = $r1;
    }

    public Scope(String $r1) throws  {
        this(1, $r1);
    }

    public boolean equals(Object $r2) throws  {
        return this == $r2 ? true : !($r2 instanceof Scope) ? false : this.CP.equals(((Scope) $r2).CP);
    }

    public int hashCode() throws  {
        return this.CP.hashCode();
    }

    public String toString() throws  {
        return this.CP;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzd.zza(this, $r1, $i0);
    }

    public String zzasc() throws  {
        return this.CP;
    }
}
