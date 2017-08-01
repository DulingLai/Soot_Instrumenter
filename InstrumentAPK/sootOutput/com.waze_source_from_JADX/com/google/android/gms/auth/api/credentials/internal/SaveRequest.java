package com.google.android.gms.auth.api.credentials.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public final class SaveRequest extends AbstractSafeParcelable {
    public static final Creator<SaveRequest> CREATOR = new zzm();
    private final Credential fb;
    final int mVersionCode;

    SaveRequest(int $i0, Credential $r1) throws  {
        this.mVersionCode = $i0;
        this.fb = $r1;
    }

    public SaveRequest(Credential $r1) throws  {
        this(1, $r1);
    }

    public Credential getCredential() throws  {
        return this.fb;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzm.zza(this, $r1, $i0);
    }
}
