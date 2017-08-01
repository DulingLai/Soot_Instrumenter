package com.google.android.gms.location.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;

/* compiled from: dalvik_source_com.waze.apk */
public class ClientIdentity extends AbstractSafeParcelable {
    public static final zzc CREATOR = new zzc();
    private final int mVersionCode;
    public final String packageName;
    public final int uid;

    ClientIdentity(int $i0, int $i1, String $r1) throws  {
        this.mVersionCode = $i0;
        this.uid = $i1;
        this.packageName = $r1;
    }

    public boolean equals(Object $r1) throws  {
        if ($r1 == this) {
            return true;
        }
        if ($r1 == null || !($r1 instanceof ClientIdentity)) {
            return false;
        }
        ClientIdentity $r2 = (ClientIdentity) $r1;
        return $r2.uid == this.uid && zzaa.equal($r2.packageName, this.packageName);
    }

    int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public int hashCode() throws  {
        return this.uid;
    }

    public String toString() throws  {
        return String.format("%d:%s", new Object[]{Integer.valueOf(this.uid), this.packageName});
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzc.zza(this, $r1, $i0);
    }
}
