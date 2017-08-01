package com.google.android.gms.plus.internal.model.people;

import android.os.Parcel;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.abaltatech.mcp.weblink.core.WLTypes;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.Person.AgeRange;
import com.google.android.gms.plus.model.people.Person.Cover;
import com.google.android.gms.plus.model.people.Person.Cover.CoverInfo;
import com.google.android.gms.plus.model.people.Person.Cover.CoverPhoto;
import com.google.android.gms.plus.model.people.Person.Image;
import com.google.android.gms.plus.model.people.Person.Name;
import com.google.android.gms.plus.model.people.Person.Organizations;
import com.google.android.gms.plus.model.people.Person.PlacesLived;
import com.google.android.gms.plus.model.people.Person.Urls;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public final class PersonEntity extends FastSafeParcelableJsonResponse implements Person {
    public static final zza CREATOR = new zza();
    private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
    String aBX;
    List<OrganizationsEntity> aON;
    List<PlacesLivedEntity> aOP;
    List<UrlsEntity> aOX;
    final Set<Integer> aOu;
    boolean aPw;
    int aUk;
    String aYV;
    AgeRangeEntity aYW;
    String aYX;
    String aYY;
    int aYZ;
    CoverEntity aZa;
    String aZb;
    ImageEntity aZc;
    boolean aZd;
    NameEntity aZe;
    int aZf;
    int aZg;
    String aZh;
    String cr;
    final int mVersionCode;
    String zzad;
    int zzauv;
    String zzbgd;
    String zzcuw;

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class AgeRangeEntity extends FastSafeParcelableJsonResponse implements AgeRange {
        public static final zzb CREATOR = new zzb();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        int aZi;
        int aZj;
        final int mVersionCode;

        static {
            aOt.put("max", Field.forInteger("max", 2));
            aOt.put("min", Field.forInteger("min", 3));
        }

        public AgeRangeEntity() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        AgeRangeEntity(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;III)V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;III)V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;III)V"}) int $i1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;III)V"}) int $i2) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aZi = $i1;
            this.aZj = $i2;
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof AgeRangeEntity)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            AgeRangeEntity $r2 = (AgeRangeEntity) $r1;
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

        public /* synthetic */ Object freeze() throws  {
            return zzche();
        }

        public HashMap<String, Field<?, ?>> getFieldMappings() throws  {
            return aOt;
        }

        protected Object getFieldValue(Field $r1) throws  {
            switch ($r1.getSafeParcelableFieldId()) {
                case 2:
                    return Integer.valueOf(this.aZi);
                case 3:
                    return Integer.valueOf(this.aZj);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public int getMax() throws  {
            return this.aZi;
        }

        public int getMin() throws  {
            return this.aZj;
        }

        public boolean hasMax() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }

        public boolean hasMin() throws  {
            return this.aOu.contains(Integer.valueOf(3));
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

        public boolean isDataValid() throws  {
            return true;
        }

        protected boolean isFieldSet(Field $r1) throws  {
            return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
        }

        protected void setIntegerInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) int $i0) throws  {
            int $i1 = $r1.getSafeParcelableFieldId();
            switch ($i1) {
                case 2:
                    this.aZi = $i0;
                    break;
                case 3:
                    this.aZj = $i0;
                    break;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i1 + " is not known to be an int.");
            }
            this.aOu.add(Integer.valueOf($i1));
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzb $r2 = CREATOR;
            zzb.zza(this, $r1, $i0);
        }

        public AgeRangeEntity zzche() throws  {
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class CoverEntity extends FastSafeParcelableJsonResponse implements Cover {
        public static final zzc CREATOR = new zzc();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        CoverInfoEntity aZk;
        CoverPhotoEntity aZl;
        int aZm;
        final int mVersionCode;

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class CoverInfoEntity extends FastSafeParcelableJsonResponse implements CoverInfo {
            public static final zzd CREATOR = new zzd();
            private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
            final Set<Integer> aOu;
            int aZn;
            int aZo;
            final int mVersionCode;

            static {
                aOt.put("leftImageOffset", Field.forInteger("leftImageOffset", 2));
                aOt.put("topImageOffset", Field.forInteger("topImageOffset", 3));
            }

            public CoverInfoEntity() throws  {
                this.mVersionCode = 1;
                this.aOu = new HashSet();
            }

            CoverInfoEntity(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;III)V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;III)V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;III)V"}) int $i1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;III)V"}) int $i2) throws  {
                this.aOu = $r1;
                this.mVersionCode = $i0;
                this.aZn = $i1;
                this.aZo = $i2;
            }

            public boolean equals(Object $r1) throws  {
                if (!($r1 instanceof CoverInfoEntity)) {
                    return false;
                }
                if (this == $r1) {
                    return true;
                }
                CoverInfoEntity $r2 = (CoverInfoEntity) $r1;
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

            public /* synthetic */ Object freeze() throws  {
                return zzchg();
            }

            public HashMap<String, Field<?, ?>> getFieldMappings() throws  {
                return aOt;
            }

            protected Object getFieldValue(Field $r1) throws  {
                switch ($r1.getSafeParcelableFieldId()) {
                    case 2:
                        return Integer.valueOf(this.aZn);
                    case 3:
                        return Integer.valueOf(this.aZo);
                    default:
                        throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
                }
            }

            public int getLeftImageOffset() throws  {
                return this.aZn;
            }

            public int getTopImageOffset() throws  {
                return this.aZo;
            }

            public boolean hasLeftImageOffset() throws  {
                return this.aOu.contains(Integer.valueOf(2));
            }

            public boolean hasTopImageOffset() throws  {
                return this.aOu.contains(Integer.valueOf(3));
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

            public boolean isDataValid() throws  {
                return true;
            }

            protected boolean isFieldSet(Field $r1) throws  {
                return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
            }

            protected void setIntegerInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) int $i0) throws  {
                int $i1 = $r1.getSafeParcelableFieldId();
                switch ($i1) {
                    case 2:
                        this.aZn = $i0;
                        break;
                    case 3:
                        this.aZo = $i0;
                        break;
                    default:
                        throw new IllegalArgumentException("Field with id=" + $i1 + " is not known to be an int.");
                }
                this.aOu.add(Integer.valueOf($i1));
            }

            public void writeToParcel(Parcel $r1, int $i0) throws  {
                zzd $r2 = CREATOR;
                zzd.zza(this, $r1, $i0);
            }

            public CoverInfoEntity zzchg() throws  {
                return this;
            }
        }

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class CoverPhotoEntity extends FastSafeParcelableJsonResponse implements CoverPhoto {
            public static final zze CREATOR = new zze();
            private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
            final Set<Integer> aOu;
            final int mVersionCode;
            String zzad;
            int zzaiq;
            int zzair;

            static {
                aOt.put("height", Field.forInteger("height", 2));
                aOt.put("url", Field.forString("url", 3));
                aOt.put("width", Field.forInteger("width", 4));
            }

            public CoverPhotoEntity() throws  {
                this.mVersionCode = 1;
                this.aOu = new HashSet();
            }

            CoverPhotoEntity(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;II", "Ljava/lang/String;", "I)V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;II", "Ljava/lang/String;", "I)V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;II", "Ljava/lang/String;", "I)V"}) int $i1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;II", "Ljava/lang/String;", "I)V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;II", "Ljava/lang/String;", "I)V"}) int $i2) throws  {
                this.aOu = $r1;
                this.mVersionCode = $i0;
                this.zzair = $i1;
                this.zzad = $r2;
                this.zzaiq = $i2;
            }

            public boolean equals(Object $r1) throws  {
                if (!($r1 instanceof CoverPhotoEntity)) {
                    return false;
                }
                if (this == $r1) {
                    return true;
                }
                CoverPhotoEntity $r2 = (CoverPhotoEntity) $r1;
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

            public /* synthetic */ Object freeze() throws  {
                return zzchh();
            }

            public HashMap<String, Field<?, ?>> getFieldMappings() throws  {
                return aOt;
            }

            protected Object getFieldValue(Field $r1) throws  {
                switch ($r1.getSafeParcelableFieldId()) {
                    case 2:
                        return Integer.valueOf(this.zzair);
                    case 3:
                        return this.zzad;
                    case 4:
                        return Integer.valueOf(this.zzaiq);
                    default:
                        throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
                }
            }

            public int getHeight() throws  {
                return this.zzair;
            }

            public String getUrl() throws  {
                return this.zzad;
            }

            public int getWidth() throws  {
                return this.zzaiq;
            }

            public boolean hasHeight() throws  {
                return this.aOu.contains(Integer.valueOf(2));
            }

            public boolean hasUrl() throws  {
                return this.aOu.contains(Integer.valueOf(3));
            }

            public boolean hasWidth() throws  {
                return this.aOu.contains(Integer.valueOf(4));
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

            public boolean isDataValid() throws  {
                return true;
            }

            protected boolean isFieldSet(Field $r1) throws  {
                return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            protected void setIntegerInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) int r10) throws  {
                /*
                r7 = this;
                r0 = r8.getSafeParcelableFieldId();
                switch(r0) {
                    case 2: goto L_0x0029;
                    case 3: goto L_0x0008;
                    case 4: goto L_0x0035;
                    default: goto L_0x0007;
                };
            L_0x0007:
                goto L_0x0008;
            L_0x0008:
                r1 = new java.lang.IllegalArgumentException;
                r2 = new java.lang.StringBuilder;
                r3 = 52;
                r2.<init>(r3);
                r4 = "Field with id=";
                r2 = r2.append(r4);
                r2 = r2.append(r0);
                r4 = " is not known to be an int.";
                r2 = r2.append(r4);
                r9 = r2.toString();
                r1.<init>(r9);
                throw r1;
            L_0x0029:
                r7.zzair = r10;
            L_0x002b:
                r5 = r7.aOu;
                r6 = java.lang.Integer.valueOf(r0);
                r5.add(r6);
                return;
            L_0x0035:
                r7.zzaiq = r10;
                goto L_0x002b;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.internal.model.people.PersonEntity.CoverEntity.CoverPhotoEntity.setIntegerInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, int):void");
            }

            protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
                int $i0 = $r1.getSafeParcelableFieldId();
                switch ($i0) {
                    case 3:
                        this.zzad = $r3;
                        this.aOu.add(Integer.valueOf($i0));
                        return;
                    default:
                        throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
                }
            }

            public void writeToParcel(Parcel $r1, int $i0) throws  {
                zze $r2 = CREATOR;
                zze.zza(this, $r1, $i0);
            }

            public CoverPhotoEntity zzchh() throws  {
                return this;
            }
        }

        static {
            aOt.put("coverInfo", Field.forConcreteType("coverInfo", 2, CoverInfoEntity.class));
            aOt.put("coverPhoto", Field.forConcreteType("coverPhoto", 3, CoverPhotoEntity.class));
            aOt.put("layout", Field.withConverter("layout", 4, new StringToIntConverter().zzj("banner", 0), false));
        }

        public CoverEntity() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        CoverEntity(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity$CoverInfoEntity;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity$CoverPhotoEntity;", "I)V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity$CoverInfoEntity;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity$CoverPhotoEntity;", "I)V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity$CoverInfoEntity;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity$CoverPhotoEntity;", "I)V"}) CoverInfoEntity $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity$CoverInfoEntity;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity$CoverPhotoEntity;", "I)V"}) CoverPhotoEntity $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity$CoverInfoEntity;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity$CoverPhotoEntity;", "I)V"}) int $i1) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aZk = $r2;
            this.aZl = $r3;
            this.aZm = $i1;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.aZk = (CoverInfoEntity) $r3;
                    break;
                case 3:
                    this.aZl = (CoverPhotoEntity) $r3;
                    break;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
            this.aOu.add(Integer.valueOf($i0));
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof CoverEntity)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            CoverEntity $r2 = (CoverEntity) $r1;
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

        public /* synthetic */ Object freeze() throws  {
            return zzchf();
        }

        public CoverInfo getCoverInfo() throws  {
            return this.aZk;
        }

        public CoverPhoto getCoverPhoto() throws  {
            return this.aZl;
        }

        public HashMap<String, Field<?, ?>> getFieldMappings() throws  {
            return aOt;
        }

        protected Object getFieldValue(Field $r1) throws  {
            switch ($r1.getSafeParcelableFieldId()) {
                case 2:
                    return this.aZk;
                case 3:
                    return this.aZl;
                case 4:
                    return Integer.valueOf(this.aZm);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public int getLayout() throws  {
            return this.aZm;
        }

        public boolean hasCoverInfo() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }

        public boolean hasCoverPhoto() throws  {
            return this.aOu.contains(Integer.valueOf(3));
        }

        public boolean hasLayout() throws  {
            return this.aOu.contains(Integer.valueOf(4));
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

        public boolean isDataValid() throws  {
            return true;
        }

        protected boolean isFieldSet(Field $r1) throws  {
            return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
        }

        protected void setIntegerInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) int $i0) throws  {
            int $i1 = $r1.getSafeParcelableFieldId();
            switch ($i1) {
                case 4:
                    this.aZm = $i0;
                    this.aOu.add(Integer.valueOf($i1));
                    return;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i1 + " is not known to be an int.");
            }
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzc $r2 = CREATOR;
            zzc.zza(this, $r1, $i0);
        }

        public CoverEntity zzchf() throws  {
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class ImageEntity extends FastSafeParcelableJsonResponse implements Image {
        public static final zzf CREATOR = new zzf();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        final int mVersionCode;
        String zzad;

        static {
            aOt.put("url", Field.forString("url", 2));
        }

        public ImageEntity() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        public ImageEntity(String $r1) throws  {
            this.aOu = new HashSet();
            this.mVersionCode = 1;
            this.zzad = $r1;
            this.aOu.add(Integer.valueOf(2));
        }

        ImageEntity(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", ")V"}) String $r2) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.zzad = $r2;
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof ImageEntity)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            ImageEntity $r2 = (ImageEntity) $r1;
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

        public /* synthetic */ Object freeze() throws  {
            return zzchi();
        }

        public HashMap<String, Field<?, ?>> getFieldMappings() throws  {
            return aOt;
        }

        protected Object getFieldValue(Field $r1) throws  {
            switch ($r1.getSafeParcelableFieldId()) {
                case 2:
                    return this.zzad;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public String getUrl() throws  {
            return this.zzad;
        }

        public boolean hasUrl() throws  {
            return this.aOu.contains(Integer.valueOf(2));
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

        public boolean isDataValid() throws  {
            return true;
        }

        protected boolean isFieldSet(Field $r1) throws  {
            return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
        }

        protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.zzad = $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
            }
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzf $r2 = CREATOR;
            zzf.zza(this, $r1, $i0);
        }

        public ImageEntity zzchi() throws  {
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class NameEntity extends FastSafeParcelableJsonResponse implements Name {
        public static final zzg CREATOR = new zzg();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        String aPA;
        String aPB;
        String aPC;
        String aPz;
        String fB;
        String fC;
        final int mVersionCode;

        static {
            aOt.put("familyName", Field.forString("familyName", 2));
            aOt.put("formatted", Field.forString("formatted", 3));
            aOt.put("givenName", Field.forString("givenName", 4));
            aOt.put("honorificPrefix", Field.forString("honorificPrefix", 5));
            aOt.put("honorificSuffix", Field.forString("honorificSuffix", 6));
            aOt.put("middleName", Field.forString("middleName", 7));
        }

        public NameEntity() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        NameEntity(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r5, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r6, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r7) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.fC = $r2;
            this.aPz = $r3;
            this.fB = $r4;
            this.aPA = $r5;
            this.aPB = $r6;
            this.aPC = $r7;
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof NameEntity)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            NameEntity $r2 = (NameEntity) $r1;
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

        public /* synthetic */ Object freeze() throws  {
            return zzchj();
        }

        public String getFamilyName() throws  {
            return this.fC;
        }

        public HashMap<String, Field<?, ?>> getFieldMappings() throws  {
            return aOt;
        }

        protected Object getFieldValue(Field $r1) throws  {
            switch ($r1.getSafeParcelableFieldId()) {
                case 2:
                    return this.fC;
                case 3:
                    return this.aPz;
                case 4:
                    return this.fB;
                case 5:
                    return this.aPA;
                case 6:
                    return this.aPB;
                case 7:
                    return this.aPC;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public String getFormatted() throws  {
            return this.aPz;
        }

        public String getGivenName() throws  {
            return this.fB;
        }

        public String getHonorificPrefix() throws  {
            return this.aPA;
        }

        public String getHonorificSuffix() throws  {
            return this.aPB;
        }

        public String getMiddleName() throws  {
            return this.aPC;
        }

        public boolean hasFamilyName() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }

        public boolean hasFormatted() throws  {
            return this.aOu.contains(Integer.valueOf(3));
        }

        public boolean hasGivenName() throws  {
            return this.aOu.contains(Integer.valueOf(4));
        }

        public boolean hasHonorificPrefix() throws  {
            return this.aOu.contains(Integer.valueOf(5));
        }

        public boolean hasHonorificSuffix() throws  {
            return this.aOu.contains(Integer.valueOf(6));
        }

        public boolean hasMiddleName() throws  {
            return this.aOu.contains(Integer.valueOf(7));
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

        public boolean isDataValid() throws  {
            return true;
        }

        protected boolean isFieldSet(Field $r1) throws  {
            return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
        }

        protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.fC = $r3;
                    break;
                case 3:
                    this.aPz = $r3;
                    break;
                case 4:
                    this.fB = $r3;
                    break;
                case 5:
                    this.aPA = $r3;
                    break;
                case 6:
                    this.aPB = $r3;
                    break;
                case 7:
                    this.aPC = $r3;
                    break;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
            }
            this.aOu.add(Integer.valueOf($i0));
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzg $r2 = CREATOR;
            zzg.zza(this, $r1, $i0);
        }

        public NameEntity zzchj() throws  {
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class OrganizationsEntity extends FastSafeParcelableJsonResponse implements Organizations {
        public static final zzh CREATOR = new zzh();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        String Sm;
        boolean aMF;
        final Set<Integer> aOu;
        String aOw;
        String aPI;
        String aPK;
        String aPM;
        int bG;
        String mDescription;
        String mName;
        final int mVersionCode;

        static {
            aOt.put("department", Field.forString("department", 2));
            aOt.put("description", Field.forString("description", 3));
            aOt.put("endDate", Field.forString("endDate", 4));
            aOt.put(WLTypes.VEHICLEDATA_ATTRIBUTE_LOCATION, Field.forString(WLTypes.VEHICLEDATA_ATTRIBUTE_LOCATION, 5));
            aOt.put("name", Field.forString("name", 6));
            aOt.put("primary", Field.forBoolean("primary", 7));
            aOt.put("startDate", Field.forString("startDate", 8));
            aOt.put("title", Field.forString("title", 9));
            aOt.put("type", Field.withConverter("type", 10, new StringToIntConverter().zzj("work", 0).zzj("school", 1), false));
        }

        public OrganizationsEntity() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        OrganizationsEntity(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) String $r4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) String $r5, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) String $r6, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) boolean $z0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) String $r7, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) String $r8, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) int $i1) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aPI = $r2;
            this.mDescription = $r3;
            this.aPK = $r4;
            this.aOw = $r5;
            this.mName = $r6;
            this.aMF = $z0;
            this.aPM = $r7;
            this.Sm = $r8;
            this.bG = $i1;
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof OrganizationsEntity)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            OrganizationsEntity $r2 = (OrganizationsEntity) $r1;
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

        public /* synthetic */ Object freeze() throws  {
            return zzchk();
        }

        public String getDepartment() throws  {
            return this.aPI;
        }

        public String getDescription() throws  {
            return this.mDescription;
        }

        public String getEndDate() throws  {
            return this.aPK;
        }

        public HashMap<String, Field<?, ?>> getFieldMappings() throws  {
            return aOt;
        }

        protected Object getFieldValue(Field $r1) throws  {
            switch ($r1.getSafeParcelableFieldId()) {
                case 2:
                    return this.aPI;
                case 3:
                    return this.mDescription;
                case 4:
                    return this.aPK;
                case 5:
                    return this.aOw;
                case 6:
                    return this.mName;
                case 7:
                    return Boolean.valueOf(this.aMF);
                case 8:
                    return this.aPM;
                case 9:
                    return this.Sm;
                case 10:
                    return Integer.valueOf(this.bG);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public String getLocation() throws  {
            return this.aOw;
        }

        public String getName() throws  {
            return this.mName;
        }

        public String getStartDate() throws  {
            return this.aPM;
        }

        public String getTitle() throws  {
            return this.Sm;
        }

        public int getType() throws  {
            return this.bG;
        }

        public boolean hasDepartment() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }

        public boolean hasDescription() throws  {
            return this.aOu.contains(Integer.valueOf(3));
        }

        public boolean hasEndDate() throws  {
            return this.aOu.contains(Integer.valueOf(4));
        }

        public boolean hasLocation() throws  {
            return this.aOu.contains(Integer.valueOf(5));
        }

        public boolean hasName() throws  {
            return this.aOu.contains(Integer.valueOf(6));
        }

        public boolean hasPrimary() throws  {
            return this.aOu.contains(Integer.valueOf(7));
        }

        public boolean hasStartDate() throws  {
            return this.aOu.contains(Integer.valueOf(8));
        }

        public boolean hasTitle() throws  {
            return this.aOu.contains(Integer.valueOf(9));
        }

        public boolean hasType() throws  {
            return this.aOu.contains(Integer.valueOf(10));
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

        public boolean isDataValid() throws  {
            return true;
        }

        protected boolean isFieldSet(Field $r1) throws  {
            return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
        }

        public boolean isPrimary() throws  {
            return this.aMF;
        }

        protected void setBooleanInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) boolean $z0) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 7:
                    this.aMF = $z0;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a boolean.");
            }
        }

        protected void setIntegerInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) int $i0) throws  {
            int $i1 = $r1.getSafeParcelableFieldId();
            switch ($i1) {
                case 10:
                    this.bG = $i0;
                    this.aOu.add(Integer.valueOf($i1));
                    return;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i1 + " is not known to be an int.");
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected void setStringInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r10) throws  {
            /*
            r7 = this;
            r0 = r8.getSafeParcelableFieldId();
            switch(r0) {
                case 2: goto L_0x0029;
                case 3: goto L_0x0035;
                case 4: goto L_0x0038;
                case 5: goto L_0x003b;
                case 6: goto L_0x003e;
                case 7: goto L_0x0008;
                case 8: goto L_0x0041;
                case 9: goto L_0x0044;
                default: goto L_0x0007;
            };
        L_0x0007:
            goto L_0x0008;
        L_0x0008:
            r1 = new java.lang.IllegalArgumentException;
            r2 = new java.lang.StringBuilder;
            r3 = 54;
            r2.<init>(r3);
            r4 = "Field with id=";
            r2 = r2.append(r4);
            r2 = r2.append(r0);
            r4 = " is not known to be a String.";
            r2 = r2.append(r4);
            r9 = r2.toString();
            r1.<init>(r9);
            throw r1;
        L_0x0029:
            r7.aPI = r10;
        L_0x002b:
            r5 = r7.aOu;
            r6 = java.lang.Integer.valueOf(r0);
            r5.add(r6);
            return;
        L_0x0035:
            r7.mDescription = r10;
            goto L_0x002b;
        L_0x0038:
            r7.aPK = r10;
            goto L_0x002b;
        L_0x003b:
            r7.aOw = r10;
            goto L_0x002b;
        L_0x003e:
            r7.mName = r10;
            goto L_0x002b;
        L_0x0041:
            r7.aPM = r10;
            goto L_0x002b;
        L_0x0044:
            r7.Sm = r10;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.internal.model.people.PersonEntity.OrganizationsEntity.setStringInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.lang.String):void");
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzh $r2 = CREATOR;
            zzh.zza(this, $r1, $i0);
        }

        public OrganizationsEntity zzchk() throws  {
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class PlacesLivedEntity extends FastSafeParcelableJsonResponse implements PlacesLived {
        public static final zzi CREATOR = new zzi();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        boolean aMF;
        final Set<Integer> aOu;
        String mValue;
        final int mVersionCode;

        static {
            aOt.put("primary", Field.forBoolean("primary", 2));
            aOt.put("value", Field.forString("value", 3));
        }

        public PlacesLivedEntity() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        PlacesLivedEntity(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Ljava/lang/String;", ")V"}) boolean $z0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Ljava/lang/String;", ")V"}) String $r2) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aMF = $z0;
            this.mValue = $r2;
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof PlacesLivedEntity)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            PlacesLivedEntity $r2 = (PlacesLivedEntity) $r1;
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

        public /* synthetic */ Object freeze() throws  {
            return zzchl();
        }

        public HashMap<String, Field<?, ?>> getFieldMappings() throws  {
            return aOt;
        }

        protected Object getFieldValue(Field $r1) throws  {
            switch ($r1.getSafeParcelableFieldId()) {
                case 2:
                    return Boolean.valueOf(this.aMF);
                case 3:
                    return this.mValue;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public String getValue() throws  {
            return this.mValue;
        }

        public boolean hasPrimary() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }

        public boolean hasValue() throws  {
            return this.aOu.contains(Integer.valueOf(3));
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

        public boolean isDataValid() throws  {
            return true;
        }

        protected boolean isFieldSet(Field $r1) throws  {
            return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
        }

        public boolean isPrimary() throws  {
            return this.aMF;
        }

        protected void setBooleanInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) boolean $z0) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.aMF = $z0;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a boolean.");
            }
        }

        protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 3:
                    this.mValue = $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
            }
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzi $r2 = CREATOR;
            zzi.zza(this, $r1, $i0);
        }

        public PlacesLivedEntity zzchl() throws  {
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class UrlsEntity extends FastSafeParcelableJsonResponse implements Urls {
        public static final zzj CREATOR = new zzj();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        private final int aZp;
        int bG;
        String mLabel;
        String mValue;
        final int mVersionCode;

        static {
            aOt.put(PlusShare.KEY_CALL_TO_ACTION_LABEL, Field.forString(PlusShare.KEY_CALL_TO_ACTION_LABEL, 5));
            aOt.put("type", Field.withConverter("type", 6, new StringToIntConverter().zzj("home", 0).zzj("work", 1).zzj("blog", 2).zzj(Scopes.PROFILE, 3).zzj(FacebookRequestErrorClassification.KEY_OTHER, 4).zzj("otherProfile", 5).zzj("contributor", 6).zzj("website", 7), false));
            aOt.put("value", Field.forString("value", 4));
        }

        public UrlsEntity() throws  {
            this.aZp = 4;
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        UrlsEntity(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "I", "Ljava/lang/String;", "I)V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "I", "Ljava/lang/String;", "I)V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "I", "Ljava/lang/String;", "I)V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "I", "Ljava/lang/String;", "I)V"}) int $i1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "I", "Ljava/lang/String;", "I)V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "I", "Ljava/lang/String;", "I)V"}) int i) throws  {
            this.aZp = 4;
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.mLabel = $r2;
            this.bG = $i1;
            this.mValue = $r3;
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof UrlsEntity)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            UrlsEntity $r2 = (UrlsEntity) $r1;
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

        public /* synthetic */ Object freeze() throws  {
            return zzchn();
        }

        public HashMap<String, Field<?, ?>> getFieldMappings() throws  {
            return aOt;
        }

        protected Object getFieldValue(Field $r1) throws  {
            switch ($r1.getSafeParcelableFieldId()) {
                case 4:
                    return this.mValue;
                case 5:
                    return this.mLabel;
                case 6:
                    return Integer.valueOf(this.bG);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public String getLabel() throws  {
            return this.mLabel;
        }

        public int getType() throws  {
            return this.bG;
        }

        public String getValue() throws  {
            return this.mValue;
        }

        public boolean hasLabel() throws  {
            return this.aOu.contains(Integer.valueOf(5));
        }

        public boolean hasType() throws  {
            return this.aOu.contains(Integer.valueOf(6));
        }

        public boolean hasValue() throws  {
            return this.aOu.contains(Integer.valueOf(4));
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

        public boolean isDataValid() throws  {
            return true;
        }

        protected boolean isFieldSet(Field $r1) throws  {
            return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
        }

        protected void setIntegerInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) int $i0) throws  {
            int $i1 = $r1.getSafeParcelableFieldId();
            switch ($i1) {
                case 6:
                    this.bG = $i0;
                    this.aOu.add(Integer.valueOf($i1));
                    return;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i1 + " is not known to be an int.");
            }
        }

        protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 4:
                    this.mValue = $r3;
                    break;
                case 5:
                    this.mLabel = $r3;
                    break;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
            }
            this.aOu.add(Integer.valueOf($i0));
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzj $r2 = CREATOR;
            zzj.zza(this, $r1, $i0);
        }

        @Deprecated
        public int zzchm() throws  {
            return 4;
        }

        public UrlsEntity zzchn() throws  {
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zza {
        public static int zzsd(String $r0) throws  {
            if ($r0.equals("person")) {
                return 0;
            }
            if ($r0.equals("page")) {
                return 1;
            }
            String $r2 = "Unknown objectType string: ";
            $r0 = String.valueOf($r0);
            throw new IllegalArgumentException($r0.length() != 0 ? $r2.concat($r0) : new String("Unknown objectType string: "));
        }
    }

    static {
        aOt.put("aboutMe", Field.forString("aboutMe", 2));
        aOt.put("ageRange", Field.forConcreteType("ageRange", 3, AgeRangeEntity.class));
        aOt.put("birthday", Field.forString("birthday", 4));
        aOt.put("braggingRights", Field.forString("braggingRights", 5));
        aOt.put("circledByCount", Field.forInteger("circledByCount", 6));
        aOt.put("cover", Field.forConcreteType("cover", 7, CoverEntity.class));
        aOt.put("currentLocation", Field.forString("currentLocation", 8));
        aOt.put("displayName", Field.forString("displayName", 9));
        aOt.put("gender", Field.withConverter("gender", 12, new StringToIntConverter().zzj("male", 0).zzj("female", 1).zzj(FacebookRequestErrorClassification.KEY_OTHER, 2), false));
        aOt.put("id", Field.forString("id", 14));
        aOt.put("image", Field.forConcreteType("image", 15, ImageEntity.class));
        aOt.put("isPlusUser", Field.forBoolean("isPlusUser", 16));
        aOt.put("language", Field.forString("language", 18));
        aOt.put("name", Field.forConcreteType("name", 19, NameEntity.class));
        aOt.put("nickname", Field.forString("nickname", 20));
        aOt.put("objectType", Field.withConverter("objectType", 21, new StringToIntConverter().zzj("person", 0).zzj("page", 1), false));
        aOt.put("organizations", Field.forConcreteTypeArray("organizations", 22, OrganizationsEntity.class));
        aOt.put("placesLived", Field.forConcreteTypeArray("placesLived", 23, PlacesLivedEntity.class));
        aOt.put("plusOneCount", Field.forInteger("plusOneCount", 24));
        aOt.put("relationshipStatus", Field.withConverter("relationshipStatus", 25, new StringToIntConverter().zzj("single", 0).zzj("in_a_relationship", 1).zzj("engaged", 2).zzj("married", 3).zzj("its_complicated", 4).zzj("open_relationship", 5).zzj("widowed", 6).zzj("in_domestic_partnership", 7).zzj("in_civil_union", 8), false));
        aOt.put("tagline", Field.forString("tagline", 26));
        aOt.put("url", Field.forString("url", 27));
        aOt.put("urls", Field.forConcreteTypeArray("urls", 28, UrlsEntity.class));
        aOt.put("verified", Field.forBoolean("verified", 29));
    }

    public PersonEntity() throws  {
        this.mVersionCode = 1;
        this.aOu = new HashSet();
    }

    public PersonEntity(String $r1, String $r2, ImageEntity $r3, int $i0, String $r4) throws  {
        this.mVersionCode = 1;
        this.aOu = new HashSet();
        this.cr = $r1;
        this.aOu.add(Integer.valueOf(9));
        this.zzbgd = $r2;
        this.aOu.add(Integer.valueOf(14));
        this.aZc = $r3;
        this.aOu.add(Integer.valueOf(15));
        this.aUk = $i0;
        this.aOu.add(Integer.valueOf(21));
        this.zzad = $r4;
        this.aOu.add(Integer.valueOf(27));
    }

    PersonEntity(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) AgeRangeEntity $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) String $r4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) String $r5, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) int $i1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) CoverEntity $r6, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) String $r7, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) String $r8, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) int $i2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) String $r9, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) ImageEntity $r10, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) boolean $z0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) String $r11, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) NameEntity $r12, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) String $r13, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) int $i3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) List<OrganizationsEntity> $r14, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) List<PlacesLivedEntity> $r15, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) int $i4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) int $i5, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) String $r16, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) String $r17, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) List<UrlsEntity> $r18, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$AgeRangeEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$CoverEntity;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$ImageEntity;", "Z", "Ljava/lang/String;", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$NameEntity;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$OrganizationsEntity;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$PlacesLivedEntity;", ">;II", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/internal/model/people/PersonEntity$UrlsEntity;", ">;Z)V"}) boolean $z1) throws  {
        this.aOu = $r1;
        this.mVersionCode = $i0;
        this.aYV = $r2;
        this.aYW = $r3;
        this.aYX = $r4;
        this.aYY = $r5;
        this.aYZ = $i1;
        this.aZa = $r6;
        this.aZb = $r7;
        this.cr = $r8;
        this.zzauv = $i2;
        this.zzbgd = $r9;
        this.aZc = $r10;
        this.aZd = $z0;
        this.zzcuw = $r11;
        this.aZe = $r12;
        this.aBX = $r13;
        this.aUk = $i3;
        this.aON = $r14;
        this.aOP = $r15;
        this.aZf = $i4;
        this.aZg = $i5;
        this.aZh = $r16;
        this.zzad = $r17;
        this.aOX = $r18;
        this.aPw = $z1;
    }

    public static PersonEntity zzar(byte[] $r0) throws  {
        Parcel $r1 = Parcel.obtain();
        $r1.unmarshall($r0, 0, $r0.length);
        $r1.setDataPosition(0);
        PersonEntity $r4 = (PersonEntity) CREATOR.createFromParcel($r1);
        $r1.recycle();
        return $r4;
    }

    public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) ArrayList<T> $r3) throws  {
        int $i0 = $r1.getSafeParcelableFieldId();
        switch ($i0) {
            case 22:
                this.aON = $r3;
                break;
            case 23:
                this.aOP = $r3;
                break;
            case 28:
                this.aOX = $r3;
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
            case 3:
                this.aYW = (AgeRangeEntity) $r3;
                break;
            case 7:
                this.aZa = (CoverEntity) $r3;
                break;
            case 15:
                this.aZc = (ImageEntity) $r3;
                break;
            case 19:
                this.aZe = (NameEntity) $r3;
                break;
            default:
                str = String.valueOf($r3.getClass().getCanonicalName());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
        }
        this.aOu.add(Integer.valueOf($i0));
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof PersonEntity)) {
            return false;
        }
        if (this == $r1) {
            return true;
        }
        PersonEntity $r2 = (PersonEntity) $r1;
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

    public /* synthetic */ Object freeze() throws  {
        return zzchd();
    }

    public String getAboutMe() throws  {
        return this.aYV;
    }

    public AgeRange getAgeRange() throws  {
        return this.aYW;
    }

    public String getBirthday() throws  {
        return this.aYX;
    }

    public String getBraggingRights() throws  {
        return this.aYY;
    }

    public int getCircledByCount() throws  {
        return this.aYZ;
    }

    public Cover getCover() throws  {
        return this.aZa;
    }

    public String getCurrentLocation() throws  {
        return this.aZb;
    }

    public String getDisplayName() throws  {
        return this.cr;
    }

    public HashMap<String, Field<?, ?>> getFieldMappings() throws  {
        return aOt;
    }

    protected Object getFieldValue(Field $r1) throws  {
        switch ($r1.getSafeParcelableFieldId()) {
            case 2:
                return this.aYV;
            case 3:
                return this.aYW;
            case 4:
                return this.aYX;
            case 5:
                return this.aYY;
            case 6:
                return Integer.valueOf(this.aYZ);
            case 7:
                return this.aZa;
            case 8:
                return this.aZb;
            case 9:
                return this.cr;
            case 10:
            case 11:
            case 13:
            case 17:
                break;
            case 12:
                return Integer.valueOf(this.zzauv);
            case 14:
                return this.zzbgd;
            case 15:
                return this.aZc;
            case 16:
                return Boolean.valueOf(this.aZd);
            case 18:
                return this.zzcuw;
            case 19:
                return this.aZe;
            case 20:
                return this.aBX;
            case 21:
                return Integer.valueOf(this.aUk);
            case 22:
                return this.aON;
            case 23:
                return this.aOP;
            case 24:
                return Integer.valueOf(this.aZf);
            case 25:
                return Integer.valueOf(this.aZg);
            case 26:
                return this.aZh;
            case 27:
                return this.zzad;
            case 28:
                return this.aOX;
            case 29:
                return Boolean.valueOf(this.aPw);
            default:
                break;
        }
        throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
    }

    public int getGender() throws  {
        return this.zzauv;
    }

    public String getId() throws  {
        return this.zzbgd;
    }

    public Image getImage() throws  {
        return this.aZc;
    }

    public String getLanguage() throws  {
        return this.zzcuw;
    }

    public Name getName() throws  {
        return this.aZe;
    }

    public String getNickname() throws  {
        return this.aBX;
    }

    public int getObjectType() throws  {
        return this.aUk;
    }

    public List<Organizations> getOrganizations() throws  {
        return (ArrayList) this.aON;
    }

    public List<PlacesLived> getPlacesLived() throws  {
        return (ArrayList) this.aOP;
    }

    public int getPlusOneCount() throws  {
        return this.aZf;
    }

    public int getRelationshipStatus() throws  {
        return this.aZg;
    }

    public String getTagline() throws  {
        return this.aZh;
    }

    public String getUrl() throws  {
        return this.zzad;
    }

    public List<Urls> getUrls() throws  {
        return (ArrayList) this.aOX;
    }

    public boolean hasAboutMe() throws  {
        return this.aOu.contains(Integer.valueOf(2));
    }

    public boolean hasAgeRange() throws  {
        return this.aOu.contains(Integer.valueOf(3));
    }

    public boolean hasBirthday() throws  {
        return this.aOu.contains(Integer.valueOf(4));
    }

    public boolean hasBraggingRights() throws  {
        return this.aOu.contains(Integer.valueOf(5));
    }

    public boolean hasCircledByCount() throws  {
        return this.aOu.contains(Integer.valueOf(6));
    }

    public boolean hasCover() throws  {
        return this.aOu.contains(Integer.valueOf(7));
    }

    public boolean hasCurrentLocation() throws  {
        return this.aOu.contains(Integer.valueOf(8));
    }

    public boolean hasDisplayName() throws  {
        return this.aOu.contains(Integer.valueOf(9));
    }

    public boolean hasGender() throws  {
        return this.aOu.contains(Integer.valueOf(12));
    }

    public boolean hasId() throws  {
        return this.aOu.contains(Integer.valueOf(14));
    }

    public boolean hasImage() throws  {
        return this.aOu.contains(Integer.valueOf(15));
    }

    public boolean hasIsPlusUser() throws  {
        return this.aOu.contains(Integer.valueOf(16));
    }

    public boolean hasLanguage() throws  {
        return this.aOu.contains(Integer.valueOf(18));
    }

    public boolean hasName() throws  {
        return this.aOu.contains(Integer.valueOf(19));
    }

    public boolean hasNickname() throws  {
        return this.aOu.contains(Integer.valueOf(20));
    }

    public boolean hasObjectType() throws  {
        return this.aOu.contains(Integer.valueOf(21));
    }

    public boolean hasOrganizations() throws  {
        return this.aOu.contains(Integer.valueOf(22));
    }

    public boolean hasPlacesLived() throws  {
        return this.aOu.contains(Integer.valueOf(23));
    }

    public boolean hasPlusOneCount() throws  {
        return this.aOu.contains(Integer.valueOf(24));
    }

    public boolean hasRelationshipStatus() throws  {
        return this.aOu.contains(Integer.valueOf(25));
    }

    public boolean hasTagline() throws  {
        return this.aOu.contains(Integer.valueOf(26));
    }

    public boolean hasUrl() throws  {
        return this.aOu.contains(Integer.valueOf(27));
    }

    public boolean hasUrls() throws  {
        return this.aOu.contains(Integer.valueOf(28));
    }

    public boolean hasVerified() throws  {
        return this.aOu.contains(Integer.valueOf(29));
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

    public boolean isDataValid() throws  {
        return true;
    }

    protected boolean isFieldSet(Field $r1) throws  {
        return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
    }

    public boolean isPlusUser() throws  {
        return this.aZd;
    }

    public boolean isVerified() throws  {
        return this.aPw;
    }

    protected void setBooleanInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) boolean $z0) throws  {
        int $i0 = $r1.getSafeParcelableFieldId();
        switch ($i0) {
            case 16:
                this.aZd = $z0;
                break;
            case 29:
                this.aPw = $z0;
                break;
            default:
                throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a boolean.");
        }
        this.aOu.add(Integer.valueOf($i0));
    }

    protected void setIntegerInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) int $i0) throws  {
        int $i1 = $r1.getSafeParcelableFieldId();
        switch ($i1) {
            case 6:
                this.aYZ = $i0;
                break;
            case 12:
                this.zzauv = $i0;
                break;
            case 21:
                this.aUk = $i0;
                break;
            case 24:
                this.aZf = $i0;
                break;
            case 25:
                this.aZg = $i0;
                break;
            default:
                throw new IllegalArgumentException("Field with id=" + $i1 + " is not known to be an int.");
        }
        this.aOu.add(Integer.valueOf($i1));
    }

    protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
        int $i0 = $r1.getSafeParcelableFieldId();
        switch ($i0) {
            case 2:
                this.aYV = $r3;
                break;
            case 4:
                this.aYX = $r3;
                break;
            case 5:
                this.aYY = $r3;
                break;
            case 8:
                this.aZb = $r3;
                break;
            case 9:
                this.cr = $r3;
                break;
            case 14:
                this.zzbgd = $r3;
                break;
            case 18:
                this.zzcuw = $r3;
                break;
            case 20:
                this.aBX = $r3;
                break;
            case 26:
                this.aZh = $r3;
                break;
            case 27:
                this.zzad = $r3;
                break;
            default:
                throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
        }
        this.aOu.add(Integer.valueOf($i0));
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zza $r2 = CREATOR;
        zza.zza(this, $r1, $i0);
    }

    public PersonEntity zzchd() throws  {
        return this;
    }
}
