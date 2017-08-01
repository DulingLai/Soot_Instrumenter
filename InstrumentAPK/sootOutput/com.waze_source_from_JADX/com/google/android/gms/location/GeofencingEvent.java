package com.google.android.gms.location;

import android.content.Intent;
import android.location.Location;
import com.google.android.gms.location.internal.ParcelableGeofence;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class GeofencingEvent {
    private final int auL;
    private final List<Geofence> auM;
    private final Location auN;
    private final int zzbyc;

    private GeofencingEvent(@Signature({"(II", "Ljava/util/List", "<", "Lcom/google/android/gms/location/Geofence;", ">;", "Landroid/location/Location;", ")V"}) int $i0, @Signature({"(II", "Ljava/util/List", "<", "Lcom/google/android/gms/location/Geofence;", ">;", "Landroid/location/Location;", ")V"}) int $i1, @Signature({"(II", "Ljava/util/List", "<", "Lcom/google/android/gms/location/Geofence;", ">;", "Landroid/location/Location;", ")V"}) List<Geofence> $r1, @Signature({"(II", "Ljava/util/List", "<", "Lcom/google/android/gms/location/Geofence;", ">;", "Landroid/location/Location;", ")V"}) Location $r2) throws  {
        this.zzbyc = $i0;
        this.auL = $i1;
        this.auM = $r1;
        this.auN = $r2;
    }

    public static GeofencingEvent fromIntent(Intent $r0) throws  {
        return $r0 == null ? null : new GeofencingEvent($r0.getIntExtra("gms_error_code", -1), zzab($r0), zzac($r0), (Location) $r0.getParcelableExtra("com.google.android.location.intent.extra.triggering_location"));
    }

    private static int zzab(Intent $r0) throws  {
        int $i0 = $r0.getIntExtra("com.google.android.location.intent.extra.transition", -1);
        return $i0 == -1 ? -1 : ($i0 == 1 || $i0 == 2 || $i0 == 4) ? $i0 : -1;
    }

    private static List<Geofence> zzac(@Signature({"(", "Landroid/content/Intent;", ")", "Ljava/util/List", "<", "Lcom/google/android/gms/location/Geofence;", ">;"}) Intent $r0) throws  {
        ArrayList $r3 = (ArrayList) $r0.getSerializableExtra("com.google.android.location.intent.extra.geofence_list");
        if ($r3 == null) {
            return null;
        }
        ArrayList $r1 = new ArrayList($r3.size());
        Iterator $r4 = $r3.iterator();
        while ($r4.hasNext()) {
            $r1.add(ParcelableGeofence.zzai((byte[]) $r4.next()));
        }
        return $r1;
    }

    public int getErrorCode() throws  {
        return this.zzbyc;
    }

    public int getGeofenceTransition() throws  {
        return this.auL;
    }

    public List<Geofence> getTriggeringGeofences() throws  {
        return this.auM;
    }

    public Location getTriggeringLocation() throws  {
        return this.auN;
    }

    public boolean hasError() throws  {
        return this.zzbyc != -1;
    }
}
