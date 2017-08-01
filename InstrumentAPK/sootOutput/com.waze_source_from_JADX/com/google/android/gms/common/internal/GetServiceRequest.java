package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzq.zza;
import dalvik.annotation.Signature;
import java.util.Collection;

/* compiled from: dalvik_source_com.waze.apk */
public class GetServiceRequest extends AbstractSafeParcelable {
    public static final Creator<GetServiceRequest> CREATOR = new zzj();
    Account JA;
    long JB;
    final int Ju;
    int Jv;
    String Jw;
    IBinder Jx;
    Scope[] Jy;
    Bundle Jz;
    final int version;

    public GetServiceRequest(int $i0) throws  {
        this.version = 3;
        this.Jv = GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        this.Ju = $i0;
    }

    GetServiceRequest(int $i0, int $i1, int $i2, String $r1, IBinder $r2, Scope[] $r3, Bundle $r4, Account $r5, long $l3) throws  {
        this.version = $i0;
        this.Ju = $i1;
        this.Jv = $i2;
        this.Jw = $r1;
        if ($i0 < 2) {
            this.JA = zzgq($r2);
        } else {
            this.Jx = $r2;
            this.JA = $r5;
        }
        this.Jy = $r3;
        this.Jz = $r4;
        this.JB = $l3;
    }

    private Account zzgq(IBinder $r1) throws  {
        return $r1 != null ? zza.zza(zza.zzgr($r1)) : null;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzj.zza(this, $r1, $i0);
    }

    public GetServiceRequest zzb(zzq $r1) throws  {
        if ($r1 == null) {
            return this;
        }
        this.Jx = $r1.asBinder();
        return this;
    }

    public GetServiceRequest zzc(Account $r1) throws  {
        this.JA = $r1;
        return this;
    }

    public GetServiceRequest zzgs(String $r1) throws  {
        this.Jw = $r1;
        return this;
    }

    public GetServiceRequest zzi(@Signature({"(", "Ljava/util/Collection", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)", "Lcom/google/android/gms/common/internal/GetServiceRequest;"}) Collection<Scope> $r1) throws  {
        this.Jy = (Scope[]) $r1.toArray(new Scope[$r1.size()]);
        return this;
    }

    public GetServiceRequest zzs(Bundle $r1) throws  {
        this.Jz = $r1;
        return this;
    }
}
