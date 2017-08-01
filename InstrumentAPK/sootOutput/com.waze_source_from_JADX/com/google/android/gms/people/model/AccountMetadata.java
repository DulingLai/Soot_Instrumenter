package com.google.android.gms.people.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountMetadata extends AbstractSafeParcelable {
    public static final Creator<AccountMetadata> CREATOR = new zza();
    public boolean isPagePeriodicSyncEnabled;
    public boolean isPageTickleSyncEnabled;
    public boolean isPlusEnabled;
    public boolean isSyncEnabled;
    final int mVersionCode;

    public AccountMetadata() throws  {
        this.mVersionCode = 2;
    }

    AccountMetadata(int $i0, boolean $z0, boolean $z1, boolean $z2, boolean $z3) throws  {
        this.mVersionCode = $i0;
        this.isPlusEnabled = $z0;
        this.isSyncEnabled = $z1;
        this.isPagePeriodicSyncEnabled = $z2;
        this.isPageTickleSyncEnabled = $z3;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zza.zza(this, $r1, $i0);
    }
}
