package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class SleepSegmentEvent extends AbstractSafeParcelable {
    public static final Creator<SleepSegmentEvent> CREATOR = new zzk();
    public static final int STATUS_MISSING_DATA = 1;
    public static final int STATUS_SUCCESSFUL = 0;
    private final long PO;
    private final int mVersionCode;
    private final int zzbls;
    private final long zzcyx;

    public SleepSegmentEvent(int $i0, long $l1, long $l2, int $i3) throws  {
        boolean $z0 = true;
        zzab.zzb(0 < $l1, (Object) "startTimeMillis must be greater than 0.");
        zzab.zzb(0 < $l2, (Object) "endTimeMillis must be greater than 0.");
        if ($l1 > $l2) {
            $z0 = false;
        }
        zzab.zzb($z0, (Object) "endTimeMillis must be greater than or equal to startTimeMillis");
        this.mVersionCode = $i0;
        this.zzcyx = $l1;
        this.PO = $l2;
        this.zzbls = $i3;
    }

    public SleepSegmentEvent(long $l0, long $l1, int $i2) throws  {
        this(1, $l0, $l1, $i2);
    }

    public static List<SleepSegmentEvent> extractEvents(@Signature({"(", "Landroid/content/Intent;", ")", "Ljava/util/List", "<", "Lcom/google/android/gms/location/SleepSegmentEvent;", ">;"}) Intent $r0) throws  {
        if (!hasEvents($r0)) {
            return Collections.emptyList();
        }
        ArrayList<byte[]> $r4 = (ArrayList) $r0.getSerializableExtra("com.google.android.location.internal.EXTRA_SLEEP_SEGMENT_RESULT");
        ArrayList $r1 = new ArrayList($r4.size());
        for (byte[] zzag : $r4) {
            $r1.add(zzag(zzag));
        }
        return $r1;
    }

    public static boolean hasEvents(Intent $r0) throws  {
        return $r0 == null ? false : $r0.hasExtra("com.google.android.location.internal.EXTRA_SLEEP_SEGMENT_RESULT");
    }

    public static SleepSegmentEvent zzag(byte[] $r0) throws  {
        return $r0 == null ? null : (SleepSegmentEvent) zzc.zza($r0, CREATOR);
    }

    public long getEndTimeMillis() throws  {
        return this.PO;
    }

    public long getStartTimeMillis() throws  {
        return this.zzcyx;
    }

    public int getStatus() throws  {
        return this.zzbls;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public String toString() throws  {
        return String.format("startTimeMillis=%d, endTimeMillis=%d, mStatus=%d]", new Object[]{Long.valueOf(this.zzcyx), Long.valueOf(this.PO), Integer.valueOf(this.zzbls)});
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzk.zza(this, $r1, $i0);
    }
}
