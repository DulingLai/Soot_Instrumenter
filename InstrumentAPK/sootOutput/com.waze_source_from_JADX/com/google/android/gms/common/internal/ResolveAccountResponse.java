package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzq.zza;

/* compiled from: dalvik_source_com.waze.apk */
public class ResolveAccountResponse extends AbstractSafeParcelable {
    public static final Creator<ResolveAccountResponse> CREATOR = new zzad();
    private ConnectionResult Cf;
    private boolean En;
    IBinder Il;
    private boolean Ki;
    final int mVersionCode;

    ResolveAccountResponse(int $i0, IBinder $r1, ConnectionResult $r2, boolean $z0, boolean $z1) throws  {
        this.mVersionCode = $i0;
        this.Il = $r1;
        this.Cf = $r2;
        this.En = $z0;
        this.Ki = $z1;
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if (!($r1 instanceof ResolveAccountResponse)) {
            return false;
        }
        ResolveAccountResponse $r2 = (ResolveAccountResponse) $r1;
        return this.Cf.equals($r2.Cf) && zzaxd().equals($r2.zzaxd());
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzad.zza(this, $r1, $i0);
    }

    public zzq zzaxd() throws  {
        return zza.zzgr(this.Il);
    }

    public ConnectionResult zzaxe() throws  {
        return this.Cf;
    }

    public boolean zzaxf() throws  {
        return this.En;
    }

    public boolean zzaxg() throws  {
        return this.Ki;
    }
}
