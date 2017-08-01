package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public final class Mergedpeoplemetadata extends FastSafeParcelableJsonResponse {
    public static final Creator<Mergedpeoplemetadata> CREATOR = new zzh();
    private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
    boolean aMF;
    final Set<Integer> aOu;
    String aPr;
    String aPs;
    String aPt;
    String aPu;
    boolean aPv;
    boolean aPw;
    boolean aPx;
    List<Mergedpeopleaffinities> aZD;
    boolean aZE;
    List<EdgeKeyInfo> aZF;
    Mergedpeoplefieldacl aZG;
    final int mVersionCode;

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class EdgeKeyInfo extends FastSafeParcelableJsonResponse {
        public static final Creator<EdgeKeyInfo> CREATOR = new zzi();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        String aPr;
        String aPt;
        boolean aZH;
        final int mVersionCode;

        static {
            aOt.put("container", Field.forString("container", 2));
            aOt.put("containerId", Field.forString("containerId", 3));
            aOt.put("materialized", Field.forBoolean("materialized", 4));
        }

        public EdgeKeyInfo() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        EdgeKeyInfo(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) boolean $z0) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aPr = $r2;
            this.aPt = $r3;
            this.aZH = $z0;
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof EdgeKeyInfo)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            EdgeKeyInfo $r2 = (EdgeKeyInfo) $r1;
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
                    return this.aPr;
                case 3:
                    return this.aPt;
                case 4:
                    return Boolean.valueOf(this.aZH);
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

        protected void setBooleanInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) boolean $z0) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 4:
                    this.aZH = $z0;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a boolean.");
            }
        }

        protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.aPr = $r3;
                    break;
                case 3:
                    this.aPt = $r3;
                    break;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
            }
            this.aOu.add(Integer.valueOf($i0));
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzi.zza(this, $r1, $i0);
        }
    }

    static {
        aOt.put("affinities", Field.forConcreteTypeArray("affinities", 2, Mergedpeopleaffinities.class));
        aOt.put("container", Field.forString("container", 3));
        aOt.put("containerContactId", Field.forString("containerContactId", 4));
        aOt.put("containerId", Field.forString("containerId", 5));
        aOt.put("container_primary", Field.forBoolean("container_primary", 6));
        aOt.put("edgeKey", Field.forBoolean("edgeKey", 7));
        aOt.put("edgeKeyInfo", Field.forConcreteTypeArray("edgeKeyInfo", 8, EdgeKeyInfo.class));
        aOt.put("fieldAcl", Field.forConcreteType("fieldAcl", 9, Mergedpeoplefieldacl.class));
        aOt.put("primary", Field.forBoolean("primary", 10));
        aOt.put("verified", Field.forBoolean("verified", 11));
        aOt.put("visibility", Field.forString("visibility", 12));
        aOt.put("writeable", Field.forBoolean("writeable", 13));
    }

    public Mergedpeoplemetadata() throws  {
        this.mVersionCode = 1;
        this.aOu = new HashSet();
    }

    Mergedpeoplemetadata(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZ", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata$EdgeKeyInfo;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl;", "ZZ", "Ljava/lang/String;", "Z)V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZ", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata$EdgeKeyInfo;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl;", "ZZ", "Ljava/lang/String;", "Z)V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZ", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata$EdgeKeyInfo;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl;", "ZZ", "Ljava/lang/String;", "Z)V"}) List<Mergedpeopleaffinities> $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZ", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata$EdgeKeyInfo;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl;", "ZZ", "Ljava/lang/String;", "Z)V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZ", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata$EdgeKeyInfo;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl;", "ZZ", "Ljava/lang/String;", "Z)V"}) String $r4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZ", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata$EdgeKeyInfo;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl;", "ZZ", "Ljava/lang/String;", "Z)V"}) String $r5, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZ", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata$EdgeKeyInfo;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl;", "ZZ", "Ljava/lang/String;", "Z)V"}) boolean $z0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZ", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata$EdgeKeyInfo;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl;", "ZZ", "Ljava/lang/String;", "Z)V"}) boolean $z1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZ", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata$EdgeKeyInfo;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl;", "ZZ", "Ljava/lang/String;", "Z)V"}) List<EdgeKeyInfo> $r6, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZ", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata$EdgeKeyInfo;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl;", "ZZ", "Ljava/lang/String;", "Z)V"}) Mergedpeoplefieldacl $r7, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZ", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata$EdgeKeyInfo;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl;", "ZZ", "Ljava/lang/String;", "Z)V"}) boolean $z2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZ", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata$EdgeKeyInfo;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl;", "ZZ", "Ljava/lang/String;", "Z)V"}) boolean $z3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZ", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata$EdgeKeyInfo;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl;", "ZZ", "Ljava/lang/String;", "Z)V"}) String $r8, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZ", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata$EdgeKeyInfo;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl;", "ZZ", "Ljava/lang/String;", "Z)V"}) boolean $z4) throws  {
        this.aOu = $r1;
        this.mVersionCode = $i0;
        this.aZD = $r2;
        this.aPr = $r3;
        this.aPs = $r4;
        this.aPt = $r5;
        this.aZE = $z0;
        this.aPv = $z1;
        this.aZF = $r6;
        this.aZG = $r7;
        this.aMF = $z2;
        this.aPw = $z3;
        this.aPu = $r8;
        this.aPx = $z4;
    }

    public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) ArrayList<T> $r3) throws  {
        int $i0 = $r1.getSafeParcelableFieldId();
        switch ($i0) {
            case 2:
                this.aZD = $r3;
                break;
            case 8:
                this.aZF = $r3;
                break;
            default:
                str = String.valueOf($r3.getClass().getCanonicalName());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 71).append("Field with id=").append($i0).append(" is not a known array of custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
        }
        this.aOu.add(Integer.valueOf($i0));
    }

    public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
        int $i0 = $r1.getSafeParcelableFieldId();
        switch ($i0) {
            case 9:
                this.aZG = (Mergedpeoplefieldacl) $r3;
                this.aOu.add(Integer.valueOf($i0));
                return;
            default:
                str = String.valueOf($r3.getClass().getCanonicalName());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
        }
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof Mergedpeoplemetadata)) {
            return false;
        }
        if (this == $r1) {
            return true;
        }
        Mergedpeoplemetadata $r2 = (Mergedpeoplemetadata) $r1;
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

    public String getContainerId() throws  {
        return this.aPt;
    }

    public HashMap<String, Field<?, ?>> getFieldMappings() throws  {
        return aOt;
    }

    protected Object getFieldValue(Field $r1) throws  {
        switch ($r1.getSafeParcelableFieldId()) {
            case 2:
                return this.aZD;
            case 3:
                return this.aPr;
            case 4:
                return this.aPs;
            case 5:
                return this.aPt;
            case 6:
                return Boolean.valueOf(this.aZE);
            case 7:
                return Boolean.valueOf(this.aPv);
            case 8:
                return this.aZF;
            case 9:
                return this.aZG;
            case 10:
                return Boolean.valueOf(this.aMF);
            case 11:
                return Boolean.valueOf(this.aPw);
            case 12:
                return this.aPu;
            case 13:
                return Boolean.valueOf(this.aPx);
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

    public boolean isPrimary() throws  {
        return this.aMF;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void setBooleanInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) boolean r10) throws  {
        /*
        r7 = this;
        r0 = r8.getSafeParcelableFieldId();
        switch(r0) {
            case 6: goto L_0x0029;
            case 7: goto L_0x0035;
            case 8: goto L_0x0008;
            case 9: goto L_0x0008;
            case 10: goto L_0x0038;
            case 11: goto L_0x003b;
            case 12: goto L_0x0008;
            case 13: goto L_0x003e;
            default: goto L_0x0007;
        };
    L_0x0007:
        goto L_0x0008;
    L_0x0008:
        r1 = new java.lang.IllegalArgumentException;
        r2 = new java.lang.StringBuilder;
        r3 = 55;
        r2.<init>(r3);
        r4 = "Field with id=";
        r2 = r2.append(r4);
        r2 = r2.append(r0);
        r4 = " is not known to be a boolean.";
        r2 = r2.append(r4);
        r9 = r2.toString();
        r1.<init>(r9);
        throw r1;
    L_0x0029:
        r7.aZE = r10;
    L_0x002b:
        r5 = r7.aOu;
        r6 = java.lang.Integer.valueOf(r0);
        r5.add(r6);
        return;
    L_0x0035:
        r7.aPv = r10;
        goto L_0x002b;
    L_0x0038:
        r7.aMF = r10;
        goto L_0x002b;
    L_0x003b:
        r7.aPw = r10;
        goto L_0x002b;
    L_0x003e:
        r7.aPx = r10;
        goto L_0x002b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata.setBooleanInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, boolean):void");
    }

    protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
        int $i0 = $r1.getSafeParcelableFieldId();
        switch ($i0) {
            case 3:
                this.aPr = $r3;
                break;
            case 4:
                this.aPs = $r3;
                break;
            case 5:
                this.aPt = $r3;
                break;
            case 12:
                this.aPu = $r3;
                break;
            default:
                throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
        }
        this.aOu.add(Integer.valueOf($i0));
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzh.zza(this, $r1, $i0);
    }

    public String zzchs() throws  {
        return this.aPr;
    }

    public String zzcht() throws  {
        return this.aPs;
    }
}
