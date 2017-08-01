package com.google.android.gms.location;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.util.Collections;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class ActivityRecognitionResult extends AbstractSafeParcelable {
    public static final ActivityRecognitionResultCreator CREATOR = new ActivityRecognitionResultCreator();
    List<DetectedActivity> auq;
    long aur;
    long aus;
    int aut;
    Bundle extras;
    private final int mVersionCode;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class ExtrasUtils {
        public static final String BUNDLE_KEY_VEHICLE_PERSONAL_CONFIDENCE = "vehicle_personal_confidence";
        public static final int CONFIDENCE_UNDEFINED = -1;

        private ExtrasUtils() throws  {
        }

        public static int getConfidence(@Nullable Bundle $r0, String $r1) throws  {
            return getConfidence($r0, $r1, -1);
        }

        public static int getConfidence(@Nullable Bundle $r0, String str, int $i0) throws  {
            return $r0 == null ? $i0 : $r0.getInt(BUNDLE_KEY_VEHICLE_PERSONAL_CONFIDENCE, $i0);
        }

        public static boolean isValidConfidence(int $i0) throws  {
            return $i0 <= 100 && $i0 >= 0;
        }

        public static Bundle putConfidenceInExtrasBundle(@Nullable Bundle $r1, String $r0, int $i0) throws  {
            zzab.zzbn(isValidConfidence($i0));
            zzab.zzbn($r0.equals(BUNDLE_KEY_VEHICLE_PERSONAL_CONFIDENCE));
            if ($r1 == null) {
                $r1 = new Bundle();
            }
            $r1.putInt($r0, $i0);
            return $r1;
        }
    }

    public ActivityRecognitionResult(@Signature({"(I", "Ljava/util/List", "<", "Lcom/google/android/gms/location/DetectedActivity;", ">;JJI", "Landroid/os/Bundle;", ")V"}) int $i0, @Signature({"(I", "Ljava/util/List", "<", "Lcom/google/android/gms/location/DetectedActivity;", ">;JJI", "Landroid/os/Bundle;", ")V"}) List<DetectedActivity> $r1, @Signature({"(I", "Ljava/util/List", "<", "Lcom/google/android/gms/location/DetectedActivity;", ">;JJI", "Landroid/os/Bundle;", ")V"}) long $l1, @Signature({"(I", "Ljava/util/List", "<", "Lcom/google/android/gms/location/DetectedActivity;", ">;JJI", "Landroid/os/Bundle;", ")V"}) long $l2, @Signature({"(I", "Ljava/util/List", "<", "Lcom/google/android/gms/location/DetectedActivity;", ">;JJI", "Landroid/os/Bundle;", ")V"}) int $i3, @Signature({"(I", "Ljava/util/List", "<", "Lcom/google/android/gms/location/DetectedActivity;", ">;JJI", "Landroid/os/Bundle;", ")V"}) Bundle $r2) throws  {
        this.mVersionCode = $i0;
        this.auq = $r1;
        this.aur = $l1;
        this.aus = $l2;
        this.aut = $i3;
        this.extras = $r2;
    }

    public ActivityRecognitionResult(DetectedActivity $r1, long $l0, long $l1) throws  {
        this($r1, $l0, $l1, 0, null);
    }

    public ActivityRecognitionResult(DetectedActivity $r1, long $l0, long $l1, int $i2, Bundle $r2) throws  {
        this(Collections.singletonList($r1), $l0, $l1, $i2, $r2);
    }

    public ActivityRecognitionResult(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/DetectedActivity;", ">;JJ)V"}) List<DetectedActivity> $r1, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/DetectedActivity;", ">;JJ)V"}) long $l0, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/DetectedActivity;", ">;JJ)V"}) long $l1) throws  {
        this((List) $r1, $l0, $l1, 0, null);
    }

    public ActivityRecognitionResult(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/DetectedActivity;", ">;JJI", "Landroid/os/Bundle;", ")V"}) List<DetectedActivity> $r1, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/DetectedActivity;", ">;JJI", "Landroid/os/Bundle;", ")V"}) long $l0, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/DetectedActivity;", ">;JJI", "Landroid/os/Bundle;", ")V"}) long $l1, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/DetectedActivity;", ">;JJI", "Landroid/os/Bundle;", ")V"}) int $i2, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/DetectedActivity;", ">;JJI", "Landroid/os/Bundle;", ")V"}) Bundle $r2) throws  {
        boolean $z0 = true;
        boolean $z1 = $r1 != null && $r1.size() > 0;
        zzab.zzb($z1, (Object) "Must have at least 1 detected activity");
        if ($l0 <= 0 || $l1 <= 0) {
            $z0 = false;
        }
        zzab.zzb($z0, (Object) "Must set times");
        this.mVersionCode = 2;
        this.auq = $r1;
        this.aur = $l0;
        this.aus = $l1;
        this.aut = $i2;
        this.extras = $r2;
    }

    public ActivityRecognitionResult(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/DetectedActivity;", ">;JJ", "Landroid/os/Bundle;", ")V"}) List<DetectedActivity> $r1, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/DetectedActivity;", ">;JJ", "Landroid/os/Bundle;", ")V"}) long $l0, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/DetectedActivity;", ">;JJ", "Landroid/os/Bundle;", ")V"}) long $l1, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/DetectedActivity;", ">;JJ", "Landroid/os/Bundle;", ")V"}) Bundle $r2) throws  {
        this((List) $r1, $l0, $l1, 0, $r2);
    }

    public static ActivityRecognitionResult extractResult(Intent $r0) throws  {
        if (!hasResult($r0)) {
            return null;
        }
        Object $r3 = $r0.getExtras().get("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT");
        return $r3 instanceof byte[] ? (ActivityRecognitionResult) zzc.zza((byte[]) $r3, CREATOR) : $r3 instanceof ActivityRecognitionResult ? (ActivityRecognitionResult) $r3 : null;
    }

    public static boolean hasResult(Intent $r0) throws  {
        return $r0 == null ? false : $r0.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT");
    }

    private static boolean zzd(Bundle $r0, Bundle $r1) throws  {
        if ($r0 == null && $r1 == null) {
            return true;
        }
        if (($r0 == null && $r1 != null) || ($r0 != null && $r1 == null)) {
            return false;
        }
        if ($r0.size() != $r1.size()) {
            return false;
        }
        for (String $r5 : $r0.keySet()) {
            if (!$r1.containsKey($r5)) {
                return false;
            }
            if ($r0.get($r5) == null) {
                if ($r1.get($r5) != null) {
                    return false;
                }
            } else if ($r0.get($r5) instanceof Bundle) {
                if (!zzd($r0.getBundle($r5), $r1.getBundle($r5))) {
                    return false;
                }
            } else if (!$r0.get($r5).equals($r1.get($r5))) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null || getClass() != $r1.getClass()) {
            return false;
        }
        ActivityRecognitionResult $r4 = (ActivityRecognitionResult) $r1;
        if (this.aur == $r4.aur && this.aus == $r4.aus && this.aut == $r4.aut && zzaa.equal(this.auq, $r4.auq)) {
            if (zzd(this.extras, $r4.extras)) {
                return true;
            }
        }
        return false;
    }

    public int getActivityConfidence(int $i0) throws  {
        for (DetectedActivity $r4 : this.auq) {
            if ($r4.getType() == $i0) {
                return $r4.getConfidence();
            }
        }
        return 0;
    }

    public long getElapsedRealtimeMillis() throws  {
        return this.aus;
    }

    @Nullable
    public Bundle getExtras() throws  {
        return this.extras == null ? null : (Bundle) this.extras.clone();
    }

    public DetectedActivity getMostProbableActivity() throws  {
        return (DetectedActivity) this.auq.get(0);
    }

    public List<DetectedActivity> getProbableActivities() throws  {
        return this.auq;
    }

    public long getTime() throws  {
        return this.aur;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(Long.valueOf(this.aur), Long.valueOf(this.aus), Integer.valueOf(this.aut), this.auq, this.extras);
    }

    public String toString() throws  {
        String $r2 = String.valueOf(this.auq);
        long $l0 = this.aur;
        return new StringBuilder(String.valueOf($r2).length() + 124).append("ActivityRecognitionResult [probableActivities=").append($r2).append(", timeMillis=").append($l0).append(", elapsedRealtimeMillis=").append(this.aus).append("]").toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        ActivityRecognitionResultCreator.zza(this, $r1, $i0);
    }
}
