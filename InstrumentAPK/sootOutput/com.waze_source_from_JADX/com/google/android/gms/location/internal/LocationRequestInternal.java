package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.location.LocationRequest;
import dalvik.annotation.Signature;
import java.util.Collections;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class LocationRequestInternal extends AbstractSafeParcelable {
    public static final zzm CREATOR = new zzm();
    static final List<ClientIdentity> awd = Collections.emptyList();
    LocationRequest afq;
    boolean aum;
    List<ClientIdentity> awe;
    boolean awf;
    boolean awg;
    @Nullable
    String mTag;
    private final int mVersionCode;

    LocationRequestInternal(@Signature({"(I", "Lcom/google/android/gms/location/LocationRequest;", "Z", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ClientIdentity;", ">;", "Ljava/lang/String;", "ZZ)V"}) int $i0, @Signature({"(I", "Lcom/google/android/gms/location/LocationRequest;", "Z", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ClientIdentity;", ">;", "Ljava/lang/String;", "ZZ)V"}) LocationRequest $r1, @Signature({"(I", "Lcom/google/android/gms/location/LocationRequest;", "Z", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ClientIdentity;", ">;", "Ljava/lang/String;", "ZZ)V"}) boolean $z0, @Signature({"(I", "Lcom/google/android/gms/location/LocationRequest;", "Z", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ClientIdentity;", ">;", "Ljava/lang/String;", "ZZ)V"}) List<ClientIdentity> $r2, @Nullable @Signature({"(I", "Lcom/google/android/gms/location/LocationRequest;", "Z", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ClientIdentity;", ">;", "Ljava/lang/String;", "ZZ)V"}) String $r3, @Signature({"(I", "Lcom/google/android/gms/location/LocationRequest;", "Z", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ClientIdentity;", ">;", "Ljava/lang/String;", "ZZ)V"}) boolean $z1, @Signature({"(I", "Lcom/google/android/gms/location/LocationRequest;", "Z", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ClientIdentity;", ">;", "Ljava/lang/String;", "ZZ)V"}) boolean $z2) throws  {
        this.mVersionCode = $i0;
        this.afq = $r1;
        this.aum = $z0;
        this.awe = $r2;
        this.mTag = $r3;
        this.awf = $z1;
        this.awg = $z2;
    }

    public static LocationRequestInternal zza(@Nullable String $r0, LocationRequest $r1) throws  {
        return new LocationRequestInternal(1, $r1, true, awd, $r0, false, false);
    }

    @Deprecated
    public static LocationRequestInternal zzb(@Deprecated LocationRequest $r0) throws  {
        return zza(null, $r0);
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof LocationRequestInternal)) {
            return false;
        }
        LocationRequestInternal $r2 = (LocationRequestInternal) $r1;
        return zzaa.equal(this.afq, $r2.afq) ? this.aum == $r2.aum ? this.awf == $r2.awf ? zzaa.equal(this.awe, $r2.awe) ? this.awg == $r2.awg : false : false : false : false;
    }

    int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public int hashCode() throws  {
        return this.afq.hashCode();
    }

    public String toString() throws  {
        StringBuilder $r1 = new StringBuilder();
        $r1.append(this.afq.toString());
        if (this.mTag != null) {
            $r1.append(" tag=").append(this.mTag);
        }
        $r1.append(" trigger=").append(this.aum);
        $r1.append(" hideAppOps=").append(this.awf);
        $r1.append(" clients=").append(this.awe);
        $r1.append(" forceCoarseLocation=").append(this.awg);
        return $r1.toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzm.zza(this, $r1, $i0);
    }
}
