package com.google.android.gms.auth.api.credentials.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public final class DeleteRequest extends AbstractSafeParcelable {
    public static final Creator<DeleteRequest> CREATOR = new zzh();
    private final Credential fb;
    final int mVersionCode;

    DeleteRequest(int $i0, Credential $r1) throws  {
        this.mVersionCode = $i0;
        this.fb = $r1;
    }

    public DeleteRequest(Credential $r1) throws  {
        this(1, $r1);
    }

    public Credential getCredential() throws  {
        return this.fb;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzh.zza(this, $r1, $i0);
    }
}
