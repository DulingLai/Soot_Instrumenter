package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public final class LocationSettingsRequest extends AbstractSafeParcelable {
    public static final Creator<LocationSettingsRequest> CREATOR = new zzh();
    private final List<LocationRequest> afn;
    private final boolean avl;
    private final boolean avm;
    private final int mVersionCode;

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Builder {
        private boolean avl = false;
        private boolean avm = false;
        private final ArrayList<LocationRequest> avn = new ArrayList();

        public Builder addAllLocationRequests(@Signature({"(", "Ljava/util/Collection", "<", "Lcom/google/android/gms/location/LocationRequest;", ">;)", "Lcom/google/android/gms/location/LocationSettingsRequest$Builder;"}) Collection<LocationRequest> $r1) throws  {
            this.avn.addAll($r1);
            return this;
        }

        public Builder addLocationRequest(LocationRequest $r1) throws  {
            this.avn.add($r1);
            return this;
        }

        public LocationSettingsRequest build() throws  {
            return new LocationSettingsRequest(this.avn, this.avl, this.avm);
        }

        public Builder setAlwaysShow(boolean $z0) throws  {
            this.avl = $z0;
            return this;
        }

        public Builder setNeedBle(boolean $z0) throws  {
            this.avm = $z0;
            return this;
        }
    }

    LocationSettingsRequest(@Signature({"(I", "Ljava/util/List", "<", "Lcom/google/android/gms/location/LocationRequest;", ">;ZZ)V"}) int $i0, @Signature({"(I", "Ljava/util/List", "<", "Lcom/google/android/gms/location/LocationRequest;", ">;ZZ)V"}) List<LocationRequest> $r1, @Signature({"(I", "Ljava/util/List", "<", "Lcom/google/android/gms/location/LocationRequest;", ">;ZZ)V"}) boolean $z0, @Signature({"(I", "Ljava/util/List", "<", "Lcom/google/android/gms/location/LocationRequest;", ">;ZZ)V"}) boolean $z1) throws  {
        this.mVersionCode = $i0;
        this.afn = $r1;
        this.avl = $z0;
        this.avm = $z1;
    }

    private LocationSettingsRequest(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/LocationRequest;", ">;ZZ)V"}) List<LocationRequest> $r1, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/LocationRequest;", ">;ZZ)V"}) boolean $z0, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/LocationRequest;", ">;ZZ)V"}) boolean $z1) throws  {
        this(3, (List) $r1, $z0, $z1);
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzh.zza(this, $r1, $i0);
    }

    public List<LocationRequest> zzbkm() throws  {
        return Collections.unmodifiableList(this.afn);
    }

    public boolean zzbse() throws  {
        return this.avl;
    }

    public boolean zzbsf() throws  {
        return this.avm;
    }
}
