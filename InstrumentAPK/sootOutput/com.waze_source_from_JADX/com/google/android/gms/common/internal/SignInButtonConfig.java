package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class SignInButtonConfig extends AbstractSafeParcelable {
    public static final Creator<SignInButtonConfig> CREATOR = new zzae();
    private final Scope[] BY;
    private final int Kj;
    private final int Kk;
    final int mVersionCode;

    SignInButtonConfig(int $i0, int $i1, int $i2, Scope[] $r1) throws  {
        this.mVersionCode = $i0;
        this.Kj = $i1;
        this.Kk = $i2;
        this.BY = $r1;
    }

    public SignInButtonConfig(int $i0, int $i1, Scope[] $r1) throws  {
        this(1, $i0, $i1, $r1);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzae.zza(this, $r1, $i0);
    }

    public int zzaxh() throws  {
        return this.Kj;
    }

    public int zzaxi() throws  {
        return this.Kk;
    }

    public Scope[] zzaxj() throws  {
        return this.BY;
    }
}
