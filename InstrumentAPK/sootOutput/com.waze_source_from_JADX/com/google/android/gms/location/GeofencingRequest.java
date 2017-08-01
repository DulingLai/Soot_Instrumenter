package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.location.internal.ParcelableGeofence;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class GeofencingRequest extends AbstractSafeParcelable {
    public static final Creator<GeofencingRequest> CREATOR = new zzb();
    public static final int INITIAL_TRIGGER_DWELL = 4;
    public static final int INITIAL_TRIGGER_ENTER = 1;
    public static final int INITIAL_TRIGGER_EXIT = 2;
    private final List<ParcelableGeofence> auO;
    private final int auP;
    private final int mVersionCode;

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Builder {
        private final List<ParcelableGeofence> auO = new ArrayList();
        private int auP = 5;

        public static int zzwc(int $i0) throws  {
            return $i0 & 7;
        }

        public Builder addGeofence(Geofence $r1) throws  {
            zzab.zzb((Object) $r1, (Object) "geofence can't be null.");
            zzab.zzb($r1 instanceof ParcelableGeofence, (Object) "Geofence must be created using Geofence.Builder.");
            this.auO.add((ParcelableGeofence) $r1);
            return this;
        }

        public Builder addGeofences(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/Geofence;", ">;)", "Lcom/google/android/gms/location/GeofencingRequest$Builder;"}) List<Geofence> $r1) throws  {
            if ($r1 == null || $r1.isEmpty()) {
                return this;
            }
            for (Geofence $r4 : $r1) {
                if ($r4 != null) {
                    addGeofence($r4);
                }
            }
            return this;
        }

        public GeofencingRequest build() throws  {
            zzab.zzb(!this.auO.isEmpty(), (Object) "No geofence has been added to this request.");
            return new GeofencingRequest(this.auO, this.auP);
        }

        public Builder setInitialTrigger(int $i0) throws  {
            this.auP = zzwc($i0);
            return this;
        }
    }

    GeofencingRequest(@Signature({"(I", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ParcelableGeofence;", ">;I)V"}) int $i0, @Signature({"(I", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ParcelableGeofence;", ">;I)V"}) List<ParcelableGeofence> $r1, @Signature({"(I", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ParcelableGeofence;", ">;I)V"}) int $i1) throws  {
        this.mVersionCode = $i0;
        this.auO = $r1;
        this.auP = $i1;
    }

    private GeofencingRequest(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ParcelableGeofence;", ">;I)V"}) List<ParcelableGeofence> $r1, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ParcelableGeofence;", ">;I)V"}) int $i0) throws  {
        this(1, (List) $r1, $i0);
    }

    public List<Geofence> getGeofences() throws  {
        ArrayList $r1 = new ArrayList();
        $r1.addAll(this.auO);
        return $r1;
    }

    public int getInitialTrigger() throws  {
        return this.auP;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzb.zza(this, $r1, $i0);
    }

    public List<ParcelableGeofence> zzbsd() throws  {
        return this.auO;
    }
}
