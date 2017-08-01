package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.zzc;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class GestureEvent extends AbstractSafeParcelable {
    public static final zzc CREATOR = new zzc();
    public static final String EXTRA_GESTURE_DETECTED = "com.google.android.location.internal.EXTRA_GESTURE_RESULT";
    private final long auQ;
    private final long auR;
    private final int auS;
    private final boolean auT;
    private final boolean auU;
    private final int bG;
    private final int mVersionCode;

    GestureEvent(int $i0, int $i1, long $l2, long $l3, int $i4, boolean $z0, boolean $z1) throws  {
        this.mVersionCode = $i0;
        this.bG = $i1;
        this.auQ = $l2;
        this.auR = $l3;
        this.auS = $i4;
        this.auT = $z0;
        this.auU = $z1;
    }

    public GestureEvent(int $i0, long $l1, long $l2, int $i3, boolean $z0, boolean $z1) throws  {
        this.mVersionCode = 1;
        this.bG = $i0;
        this.auQ = $l1;
        this.auR = $l2;
        this.auS = $i3;
        this.auT = $z0;
        this.auU = $z1;
    }

    public static GestureEvent create(int $i0, long $l1, long $l2, int $i3, boolean $z0, boolean $z1) throws  {
        return new GestureEvent($i0, $l1, $l2, $i3, $z0, $z1);
    }

    public static List<GestureEvent> extractEvents(@Signature({"(", "Landroid/content/Intent;", ")", "Ljava/util/List", "<", "Lcom/google/android/gms/location/GestureEvent;", ">;"}) Intent $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        ArrayList<byte[]> $r2 = (ArrayList) $r0.getSerializableExtra(EXTRA_GESTURE_DETECTED);
        if ($r2 == null) {
            return null;
        }
        ArrayList $r3 = new ArrayList($r2.size());
        for (byte[] zzaf : $r2) {
            $r3.add(zzaf(zzaf));
        }
        return $r3;
    }

    public static GestureEvent zzaf(byte[] $r0) throws  {
        return $r0 == null ? null : (GestureEvent) zzc.zza($r0, CREATOR);
    }

    public int getCount() throws  {
        return this.auS;
    }

    public long getElapsedRealtimeMillis() throws  {
        return this.auR;
    }

    public long getTimeMillis() throws  {
        return this.auQ;
    }

    public int getType() throws  {
        return this.bG;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public boolean isEnd() throws  {
        return this.auU;
    }

    public boolean isStart() throws  {
        return this.auT;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzc $r2 = CREATOR;
        zzc.zza(this, $r1, $i0);
    }
}
