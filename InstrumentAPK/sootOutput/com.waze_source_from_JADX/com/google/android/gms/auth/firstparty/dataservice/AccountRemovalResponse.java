package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.auth.firstparty.shared.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountRemovalResponse extends AbstractSafeParcelable {
    public static final AccountRemovalResponseCreator CREATOR = new AccountRemovalResponseCreator();
    final String gs;
    final int version;

    AccountRemovalResponse(int $i0, String $r1) throws  {
        this.version = $i0;
        this.gs = $r1;
    }

    public AccountRemovalResponse(Status $r1) throws  {
        this.version = 1;
        this.gs = ((Status) zzab.zzag($r1)).getWire();
    }

    public Status getStatus() throws  {
        return Status.fromWireCode(this.gs);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        AccountRemovalResponseCreator.zza(this, $r1, $i0);
    }
}
