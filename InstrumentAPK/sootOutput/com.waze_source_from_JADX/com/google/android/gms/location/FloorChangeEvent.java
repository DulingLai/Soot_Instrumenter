package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class FloorChangeEvent extends AbstractSafeParcelable {
    public static final FloorChangeEventCreator CREATOR = new FloorChangeEventCreator();
    public static final int TYPE_ELEVATOR = 2;
    public static final int TYPE_ESCALATOR = 3;
    public static final int TYPE_STAIRS = 1;
    private final long PO;
    private final long auA;
    private final long auB;
    private final float auC;
    private final int auz;
    private final int bG;
    private final int mVersionCode;
    private final long zzcyx;

    public FloorChangeEvent(int $i0, int $i1, int $i2, long $l3, long $l4, long $l5, long $l6, float $f0) throws  {
        zzab.zzb($i2 >= 0, (Object) "confidence must be equal to or greater than 0");
        zzab.zzb($i2 <= 100, (Object) "confidence must be equal to or less than 100");
        zzab.zzb(0 < $l3, (Object) "startTimeMillis must be greater than 0");
        zzab.zzb($l3 <= $l4, (Object) "endTimeMillis must be equal to or greater than startTimeMillis");
        zzab.zzb(0 <= $l5, (Object) "startElapsedRealtimeMillis must be equal to or greater than 0");
        zzab.zzb($l5 <= $l6, (Object) "endElapsedRealtimeMillis must be equal to or greater than startElapsedRealtimeMillis");
        zzab.zzb($l5 < $l3, (Object) "startTimeMillis must be greater than startElapsedRealtimeMillis");
        zzab.zzb($l6 < $l4, (Object) "endTimeMillis must be greater than endElapsedRealtimeMillis");
        this.mVersionCode = $i0;
        this.bG = $i1;
        this.auz = $i2;
        this.zzcyx = $l3;
        this.PO = $l4;
        this.auA = $l5;
        this.auB = $l6;
        this.auC = $f0;
    }

    public FloorChangeEvent(int $i0, int $i1, long $l2, long $l3, long $l4, long $l5, float $f0) throws  {
        this(1, $i0, $i1, $l2, $l3, $l4, $l5, $f0);
    }

    public static List<FloorChangeEvent> extractEvents(@Signature({"(", "Landroid/content/Intent;", ")", "Ljava/util/List", "<", "Lcom/google/android/gms/location/FloorChangeEvent;", ">;"}) Intent $r0) throws  {
        if (!hasEvents($r0)) {
            return Collections.emptyList();
        }
        ArrayList<byte[]> $r4 = (ArrayList) $r0.getSerializableExtra("com.google.android.location.internal.EXTRA_FLOOR_CHANGE_RESULT");
        ArrayList $r1 = new ArrayList($r4.size());
        for (byte[] zzae : $r4) {
            $r1.add(zzae(zzae));
        }
        return $r1;
    }

    public static boolean hasEvents(Intent $r0) throws  {
        return $r0 == null ? false : $r0.hasExtra("com.google.android.location.internal.EXTRA_FLOOR_CHANGE_RESULT");
    }

    public static FloorChangeEvent zzae(byte[] $r0) throws  {
        return $r0 == null ? null : (FloorChangeEvent) zzc.zza($r0, CREATOR);
    }

    public int getConfidence() throws  {
        return this.auz;
    }

    public float getElevationChange() throws  {
        return this.auC;
    }

    public long getEndElapsedRealtimeMillis() throws  {
        return this.auB;
    }

    public long getEndTimeMillis() throws  {
        return this.PO;
    }

    public long getStartElapsedRealtimeMillis() throws  {
        return this.auA;
    }

    public long getStartTimeMillis() throws  {
        return this.zzcyx;
    }

    public int getType() throws  {
        return this.bG;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public String toString() throws  {
        return String.format("FloorChangeEvent [type=%d, confidence=%d, elevationChange=%f, startTimeMillis=%d, endTimeMillis=%d, startElapsedRealtimeMillis=%d, endElapsedRealtimeMillis=%d]", new Object[]{Integer.valueOf(this.bG), Integer.valueOf(this.auz), Float.valueOf(this.auC), Long.valueOf(this.zzcyx), Long.valueOf(this.PO), Long.valueOf(this.auA), Long.valueOf(this.auB)});
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        FloorChangeEventCreator.zza(this, $r1, $i0);
    }
}
