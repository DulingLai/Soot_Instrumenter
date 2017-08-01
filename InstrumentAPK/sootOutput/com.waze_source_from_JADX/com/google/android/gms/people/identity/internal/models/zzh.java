package com.google.android.gms.people.identity.internal.models;

import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import com.google.android.gms.plus.service.v2whitelisted.models.Person;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzh extends FastJsonResponse {
    private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
    final Set<Integer> aOu = new HashSet();
    List<Person> aOv;

    static {
        aOt.put("items", Field.forConcreteTypeArray("items", 2, Person.class));
    }

    public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) ArrayList<T> $r3) throws  {
        int $i0 = $r1.getSafeParcelableFieldId();
        switch ($i0) {
            case 2:
                this.aOv = $r3;
                this.aOu.add(Integer.valueOf($i0));
                return;
            default:
                str = String.valueOf($r3.getClass().getCanonicalName());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 71).append("Field with id=").append($i0).append(" is not a known array of custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
        }
    }

    public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
        int $i0 = $r1.getSafeParcelableFieldId();
        str = String.valueOf($r3.getClass().getCanonicalName());
        throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof zzh)) {
            return false;
        }
        if (this == $r1) {
            return true;
        }
        zzh $r2 = (zzh) $r1;
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
                return this.aOv;
            default:
                throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
        }
    }

    protected Object getValueObject(String str) throws  {
        return null;
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

    protected boolean isPrimitiveFieldSet(String str) throws  {
        return false;
    }

    protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str2) throws  {
        throw new IllegalArgumentException("Field with id=" + $r1.getSafeParcelableFieldId() + " is not known to be a String.");
    }

    public List<Person> zzcdd() throws  {
        return this.aOv;
    }

    public boolean zzcde() throws  {
        return this.aOu.contains(Integer.valueOf(2));
    }
}
