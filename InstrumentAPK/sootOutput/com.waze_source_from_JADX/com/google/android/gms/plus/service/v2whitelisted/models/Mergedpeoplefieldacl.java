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
public final class Mergedpeoplefieldacl extends FastSafeParcelableJsonResponse {
    public static final Creator<Mergedpeoplefieldacl> CREATOR = new zzb();
    private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
    final Set<Integer> aOu;
    List<Entries> aZs;
    List<String> aZt;
    final int mVersionCode;

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Entries extends FastSafeParcelableJsonResponse {
        public static final Creator<Entries> CREATOR = new zzc();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        String aZu;
        Scope aZv;
        final int mVersionCode;

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class Scope extends FastSafeParcelableJsonResponse {
            public static final Creator<Scope> CREATOR = new zzd();
            private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
            final Set<Integer> aOu;
            boolean aZw;
            boolean aZx;
            Membership aZy;
            Person aZz;
            final int mVersionCode;

            /* compiled from: dalvik_source_com.waze.apk */
            public static final class Membership extends FastSafeParcelableJsonResponse {
                public static final Creator<Membership> CREATOR = new zze();
                private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
                final Set<Integer> aOu;
                Circle aZA;
                final int mVersionCode;

                /* compiled from: dalvik_source_com.waze.apk */
                public static final class Circle extends FastSafeParcelableJsonResponse {
                    public static final Creator<Circle> CREATOR = new zzf();
                    private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
                    String KN;
                    final Set<Integer> aOu;
                    String aZB;
                    final int mVersionCode;

                    static {
                        aOt.put("circleId", Field.forString("circleId", 2));
                        aOt.put("circleSet", Field.forString("circleSet", 3));
                    }

                    public Circle() throws  {
                        this.mVersionCode = 1;
                        this.aOu = new HashSet();
                    }

                    Circle(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
                        this.aOu = $r1;
                        this.mVersionCode = $i0;
                        this.KN = $r2;
                        this.aZB = $r3;
                    }

                    public boolean equals(Object $r1) throws  {
                        if (!($r1 instanceof Circle)) {
                            return false;
                        }
                        if (this == $r1) {
                            return true;
                        }
                        Circle $r2 = (Circle) $r1;
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
                                return this.KN;
                            case 3:
                                return this.aZB;
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

                    protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
                        int $i0 = $r1.getSafeParcelableFieldId();
                        switch ($i0) {
                            case 2:
                                this.KN = $r3;
                                break;
                            case 3:
                                this.aZB = $r3;
                                break;
                            default:
                                throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
                        }
                        this.aOu.add(Integer.valueOf($i0));
                    }

                    public void writeToParcel(Parcel $r1, int $i0) throws  {
                        zzf.zza(this, $r1, $i0);
                    }
                }

                static {
                    aOt.put("circle", Field.forConcreteType("circle", 2, Circle.class));
                }

                public Membership() throws  {
                    this.mVersionCode = 1;
                    this.aOu = new HashSet();
                }

                Membership(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries$Scope$Membership$Circle;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries$Scope$Membership$Circle;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries$Scope$Membership$Circle;", ")V"}) Circle $r2) throws  {
                    this.aOu = $r1;
                    this.mVersionCode = $i0;
                    this.aZA = $r2;
                }

                public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
                    int $i0 = $r1.getSafeParcelableFieldId();
                    switch ($i0) {
                        case 2:
                            this.aZA = (Circle) $r3;
                            this.aOu.add(Integer.valueOf($i0));
                            return;
                        default:
                            str = String.valueOf($r3.getClass().getCanonicalName());
                            throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
                    }
                }

                public boolean equals(Object $r1) throws  {
                    if (!($r1 instanceof Membership)) {
                        return false;
                    }
                    if (this == $r1) {
                        return true;
                    }
                    Membership $r2 = (Membership) $r1;
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
                            return this.aZA;
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

                public void writeToParcel(Parcel $r1, int $i0) throws  {
                    zze.zza(this, $r1, $i0);
                }
            }

            /* compiled from: dalvik_source_com.waze.apk */
            public static final class Person extends FastSafeParcelableJsonResponse {
                public static final Creator<Person> CREATOR = new zzg();
                private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
                final Set<Integer> aOu;
                String aZC;
                final int mVersionCode;

                static {
                    aOt.put("personId", Field.forString("personId", 2));
                }

                public Person() throws  {
                    this.mVersionCode = 1;
                    this.aOu = new HashSet();
                }

                Person(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", ")V"}) String $r2) throws  {
                    this.aOu = $r1;
                    this.mVersionCode = $i0;
                    this.aZC = $r2;
                }

                public boolean equals(Object $r1) throws  {
                    if (!($r1 instanceof Person)) {
                        return false;
                    }
                    if (this == $r1) {
                        return true;
                    }
                    Person $r2 = (Person) $r1;
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
                            return this.aZC;
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

                protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
                    int $i0 = $r1.getSafeParcelableFieldId();
                    switch ($i0) {
                        case 2:
                            this.aZC = $r3;
                            this.aOu.add(Integer.valueOf($i0));
                            return;
                        default:
                            throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
                    }
                }

                public void writeToParcel(Parcel $r1, int $i0) throws  {
                    zzg.zza(this, $r1, $i0);
                }
            }

            static {
                aOt.put("allUsers", Field.forBoolean("allUsers", 2));
                aOt.put("domainUsers", Field.forBoolean("domainUsers", 3));
                aOt.put("membership", Field.forConcreteType("membership", 4, Membership.class));
                aOt.put("person", Field.forConcreteType("person", 5, Person.class));
            }

            public Scope() throws  {
                this.mVersionCode = 1;
                this.aOu = new HashSet();
            }

            Scope(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZZ", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries$Scope$Membership;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries$Scope$Person;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZZ", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries$Scope$Membership;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries$Scope$Person;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZZ", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries$Scope$Membership;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries$Scope$Person;", ")V"}) boolean $z0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZZ", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries$Scope$Membership;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries$Scope$Person;", ")V"}) boolean $z1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZZ", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries$Scope$Membership;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries$Scope$Person;", ")V"}) Membership $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZZ", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries$Scope$Membership;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries$Scope$Person;", ")V"}) Person $r3) throws  {
                this.aOu = $r1;
                this.mVersionCode = $i0;
                this.aZw = $z0;
                this.aZx = $z1;
                this.aZy = $r2;
                this.aZz = $r3;
            }

            public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
                int $i0 = $r1.getSafeParcelableFieldId();
                switch ($i0) {
                    case 4:
                        this.aZy = (Membership) $r3;
                        break;
                    case 5:
                        this.aZz = (Person) $r3;
                        break;
                    default:
                        str = String.valueOf($r3.getClass().getCanonicalName());
                        throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
                }
                this.aOu.add(Integer.valueOf($i0));
            }

            public boolean equals(Object $r1) throws  {
                if (!($r1 instanceof Scope)) {
                    return false;
                }
                if (this == $r1) {
                    return true;
                }
                Scope $r2 = (Scope) $r1;
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
                        return Boolean.valueOf(this.aZw);
                    case 3:
                        return Boolean.valueOf(this.aZx);
                    case 4:
                        return this.aZy;
                    case 5:
                        return this.aZz;
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
                    case 2:
                        this.aZw = $z0;
                        break;
                    case 3:
                        this.aZx = $z0;
                        break;
                    default:
                        throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a boolean.");
                }
                this.aOu.add(Integer.valueOf($i0));
            }

            public void writeToParcel(Parcel $r1, int $i0) throws  {
                zzd.zza(this, $r1, $i0);
            }
        }

        static {
            aOt.put("role", Field.forString("role", 2));
            aOt.put("scope", Field.forConcreteType("scope", 3, Scope.class));
        }

        public Entries() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        Entries(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries$Scope;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries$Scope;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries$Scope;", ")V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries$Scope;", ")V"}) Scope $r3) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aZu = $r2;
            this.aZv = $r3;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 3:
                    this.aZv = (Scope) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Entries)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            Entries $r2 = (Entries) $r1;
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
                    return this.aZu;
                case 3:
                    return this.aZv;
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

        protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.aZu = $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
            }
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzc.zza(this, $r1, $i0);
        }
    }

    static {
        aOt.put("entries", Field.forConcreteTypeArray("entries", 2, Entries.class));
        aOt.put("predefinedEntries", Field.forStrings("predefinedEntries", 3));
    }

    public Mergedpeoplefieldacl() throws  {
        this.mVersionCode = 1;
        this.aOu = new HashSet();
    }

    Mergedpeoplefieldacl(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<Entries> $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplefieldacl$Entries;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r3) throws  {
        this.aOu = $r1;
        this.mVersionCode = $i0;
        this.aZs = $r2;
        this.aZt = $r3;
    }

    public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) ArrayList<T> $r3) throws  {
        int $i0 = $r1.getSafeParcelableFieldId();
        switch ($i0) {
            case 2:
                this.aZs = $r3;
                this.aOu.add(Integer.valueOf($i0));
                return;
            default:
                str = String.valueOf($r3.getClass().getCanonicalName());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 71).append("Field with id=").append($i0).append(" is not a known array of custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
        }
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof Mergedpeoplefieldacl)) {
            return false;
        }
        if (this == $r1) {
            return true;
        }
        Mergedpeoplefieldacl $r2 = (Mergedpeoplefieldacl) $r1;
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
                return this.aZs;
            case 3:
                return this.aZt;
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

    protected void setStringsInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) ArrayList<String> $r3) throws  {
        int $i0 = $r1.getSafeParcelableFieldId();
        switch ($i0) {
            case 3:
                this.aZt = $r3;
                this.aOu.add(Integer.valueOf($i0));
                return;
            default:
                throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be an array of String.");
        }
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzb.zza(this, $r1, $i0);
    }
}
