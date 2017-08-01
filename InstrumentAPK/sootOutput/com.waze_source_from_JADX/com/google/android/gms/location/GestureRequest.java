package com.google.android.gms.location;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public final class GestureRequest extends AbstractSafeParcelable {
    public static final zzd CREATOR = new zzd();
    public static final int TYPE_BICEP_CURL_COUNTER = 14;
    public static final int TYPE_BICEP_CURL_SEGMENT_DETECTOR = 15;
    public static final int TYPE_CHINUP_COUNTER = 8;
    public static final int TYPE_CHINUP_SEGMENT_DETECTOR = 9;
    public static final int TYPE_DROP_CONTENT_DETECTOR = 18;
    public static final int TYPE_JUMPING_JACK_COUNTER = 10;
    public static final int TYPE_JUMPING_JACK_SEGMENT_DETECTOR = 11;
    public static final int TYPE_LUNGE_COUNTER = 16;
    public static final int TYPE_LUNGE_SEGMENT_DETECTOR = 17;
    public static final int TYPE_PUSHUP_COUNTER = 4;
    public static final int TYPE_PUSHUP_SEGMENT_DETECTOR = 5;
    public static final int TYPE_RECEIVE_CONTENT_DETECTOR = 19;
    public static final int TYPE_SITUP_COUNTER = 2;
    public static final int TYPE_SITUP_SEGMENT_DETECTOR = 3;
    public static final int TYPE_SQUAT_COUNTER = 6;
    public static final int TYPE_SQUAT_SEGMENT_DETECTOR = 7;
    public static final int TYPE_STANDING_CALF_RAISE_COUNTER = 12;
    public static final int TYPE_STANDING_CALF_RAISE_SEGMENT_DETECTOR = 13;
    public static final int TYPE_SWIPE_DETECTOR = 1;
    private static final List<Integer> auV = Collections.unmodifiableList(Arrays.asList(new Integer[]{Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(12), Integer.valueOf(13), Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(17), Integer.valueOf(18), Integer.valueOf(19)}));
    private static final List<Integer> auW = Collections.unmodifiableList(Arrays.asList(new Integer[]{Integer.valueOf(1)}));
    private static final List<Integer> auX = Collections.unmodifiableList(Arrays.asList(new Integer[]{Integer.valueOf(2), Integer.valueOf(4), Integer.valueOf(6), Integer.valueOf(8), Integer.valueOf(10), Integer.valueOf(12), Integer.valueOf(14), Integer.valueOf(16), Integer.valueOf(18), Integer.valueOf(19)}));
    private static final List<Integer> auY = Collections.unmodifiableList(Arrays.asList(new Integer[]{Integer.valueOf(3), Integer.valueOf(5), Integer.valueOf(7), Integer.valueOf(9), Integer.valueOf(11), Integer.valueOf(13), Integer.valueOf(15), Integer.valueOf(17)}));
    private final List<Integer> auZ;
    private final int mVersionCode;

    GestureRequest(@Signature({"(I", "Ljava/util/List", "<", "Ljava/lang/Integer;", ">;)V"}) int $i0, @Signature({"(I", "Ljava/util/List", "<", "Ljava/lang/Integer;", ">;)V"}) List<Integer> $r1) throws  {
        this.mVersionCode = $i0;
        this.auZ = $r1;
    }

    public GestureRequest(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/Integer;", ">;)V"}) List<Integer> $r1) throws  {
        this.mVersionCode = 1;
        this.auZ = zzaf($r1);
    }

    public static GestureRequest create(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/Integer;", ">;)", "Lcom/google/android/gms/location/GestureRequest;"}) List<Integer> $r0) throws  {
        return new GestureRequest($r0);
    }

    private static List<Integer> zzaf(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/Integer;", ">;)", "Ljava/util/List", "<", "Ljava/lang/Integer;", ">;"}) List<Integer> $r0) throws  {
        zzab.zzb((Object) $r0, (Object) "Need to add at least one gesture type");
        zzab.zza($r0.size(), (Object) "Need to add at least one gesture type");
        ArrayList $r1 = new ArrayList();
        for (Integer $r4 : $r0) {
            boolean $z0 = auV.contains($r4);
            String $r5 = String.valueOf($r4);
            zzab.zzb($z0, new StringBuilder(String.valueOf($r5).length() + 25).append("Unsupported gesture type:").append($r5).toString());
            if (!$r1.contains($r4)) {
                $r1.add($r4);
            }
        }
        return $r1;
    }

    public List<Integer> getGestureTypes() throws  {
        return this.auZ;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzd.zza(this, $r1, $i0);
    }
}
