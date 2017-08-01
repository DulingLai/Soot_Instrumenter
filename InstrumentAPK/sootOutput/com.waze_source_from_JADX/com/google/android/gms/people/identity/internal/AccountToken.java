package com.google.android.gms.people.identity.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public final class AccountToken extends AbstractSafeParcelable {
    public static final Creator<AccountToken> CREATOR = new zza();
    private final String aMp;
    private final String dL;
    private final int mVersionCode;

    AccountToken(int $i0, String $r1, String $r2) throws  {
        this.mVersionCode = $i0;
        this.dL = $r1;
        this.aMp = $r2;
    }

    public AccountToken(String $r1, String $r2) throws  {
        this(1, $r1, $r2);
    }

    public String getAccountName() throws  {
        return this.dL;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zza.zza(this, $r1, $i0);
    }

    public String zzccz() throws  {
        return this.aMp;
    }
}
