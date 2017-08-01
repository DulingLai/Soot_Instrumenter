package com.google.android.gms.common.server;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class FavaDiagnosticsEntity extends AbstractSafeParcelable {
    public static final zza CREATOR = new zza();
    public final String KQ;
    public final int KR;
    final int mVersionCode;

    public FavaDiagnosticsEntity(int $i0, String $r1, int $i1) throws  {
        this.mVersionCode = $i0;
        this.KQ = $r1;
        this.KR = $i1;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zza.zza(this, $r1, $i0);
    }
}
