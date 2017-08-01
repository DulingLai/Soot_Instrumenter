package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse;
import dalvik.annotation.Signature;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public final class Mergedpeopleaffinities extends FastSafeParcelableJsonResponse {
    public static final Creator<Mergedpeopleaffinities> CREATOR = new zza();
    private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
    String Am;
    final Set<Integer> aOu;
    double aZr;
    final int mVersionCode;
    String zzcft;

    static {
        aOt.put("loggingId", Field.forString("loggingId", 2));
        aOt.put("type", Field.forString("type", 3));
        aOt.put("value", Field.forDouble("value", 4));
    }

    public Mergedpeopleaffinities() throws  {
        this.mVersionCode = 1;
        this.aOu = new HashSet();
    }

    Mergedpeopleaffinities(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "D)V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "D)V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "D)V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "D)V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "D)V"}) double $d0) throws  {
        this.aOu = $r1;
        this.mVersionCode = $i0;
        this.Am = $r2;
        this.zzcft = $r3;
        this.aZr = $d0;
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof Mergedpeopleaffinities)) {
            return false;
        }
        if (this == $r1) {
            return true;
        }
        Mergedpeopleaffinities $r2 = (Mergedpeopleaffinities) $r1;
        for (Field $r6 : aOt.values()) {
            if (isFieldSet($r6)) {
                if (!$r2.isFieldSet($r6)) {
                    return false;
                }
                if (!getFieldValue($r6).equals($r2.getFieldValue($r6))) {
                    return false;
                }
            } else if ($r2.isFieldSet($r6)) {
                return false;
            }
        }
        return true;
    }

    public HashMap<String, Field<?, ?>> getFieldMappings() throws  {
        return aOt;
    }

    protected Object getFieldValue(Field $r1) throws  {
        switch ($r1.getSafeParcelableFieldId()) {
            case 2:
                return this.Am;
            case 3:
                return this.zzcft;
            case 4:
                return Double.valueOf(this.aZr);
            default:
                throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
        }
    }

    public int hashCode() throws  {
        int $i0 = 0;
        for (Field $r5 : aOt.values()) {
            if (isFieldSet($r5)) {
                $i0 = getFieldValue($r5).hashCode() + ($i0 + $r5.getSafeParcelableFieldId());
            }
        }
        return $i0;
    }

    protected boolean isFieldSet(Field $r1) throws  {
        return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
    }

    protected void setDoubleInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "D)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "D)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "D)V"}) double $d0) throws  {
        int $i0 = $r1.getSafeParcelableFieldId();
        switch ($i0) {
            case 4:
                this.aZr = $d0;
                this.aOu.add(Integer.valueOf($i0));
                return;
            default:
                throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a double.");
        }
    }

    protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
        int $i0 = $r1.getSafeParcelableFieldId();
        switch ($i0) {
            case 2:
                this.Am = $r3;
                break;
            case 3:
                this.zzcft = $r3;
                break;
            default:
                throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
        }
        this.aOu.add(Integer.valueOf($i0));
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zza.zza(this, $r1, $i0);
    }
}
