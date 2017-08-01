package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.abaltatech.mcp.weblink.core.WLTypes;
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
public final class Person extends FastSafeParcelableJsonResponse {
    public static final Creator<Person> CREATOR = new zzj();
    private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
    List<Addresses> aMy;
    List<Emails> aMz;
    List<BraggingRights> aOA;
    List<CoverPhotos> aOB;
    List<CustomFields> aOC;
    String aOD;
    List<Genders> aOE;
    List<InstantMessaging> aOF;
    List<Memberships> aOI;
    List<Names> aOK;
    List<Nicknames> aOL;
    List<Occupations> aOM;
    List<Organizations> aON;
    List<PhoneNumbers> aOO;
    List<PlacesLived> aOP;
    String aOQ;
    List<Relations> aOR;
    List<Skills> aOU;
    List<Taglines> aOW;
    List<Urls> aOX;
    final Set<Integer> aOu;
    List<Abouts> aOx;
    List<Birthdays> aOz;
    List<Calendars> aZI;
    List<ClientData> aZJ;
    ExtendedData aZK;
    List<ExternalIds> aZL;
    List<Interests> aZM;
    List<Languages> aZN;
    LegacyFields aZO;
    Metadata aZP;
    List<OtherKeywords> aZQ;
    List<SipAddress> aZR;
    SortKeys aZS;
    final int mVersionCode;
    List<Events> zzalu;
    List<Images> zzbfe;
    String zzbgd;
    String zzcuw;

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Abouts extends FastSafeParcelableJsonResponse {
        public static final Creator<Abouts> CREATOR = new zzk();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        Mergedpeoplemetadata aZT;
        String mValue;
        final int mVersionCode;
        String zzcft;

        static {
            aOt.put("metadata", Field.forConcreteType("metadata", 2, Mergedpeoplemetadata.class));
            aOt.put("type", Field.forString("type", 3));
            aOt.put("value", Field.forString("value", 4));
        }

        public Abouts() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        Abouts(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aZT = $r2;
            this.zzcft = $r3;
            this.mValue = $r4;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Abouts)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            Abouts $r2 = (Abouts) $r1;
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
                    return this.aZT;
                case 3:
                    return this.zzcft;
                case 4:
                    return this.mValue;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public String getType() throws  {
            return this.zzcft;
        }

        public String getValue() throws  {
            return this.mValue;
        }

        public boolean hasType() throws  {
            return this.aOu.contains(Integer.valueOf(3));
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

        protected boolean isFieldSet(Field $r1) throws  {
            return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
        }

        protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 3:
                    this.zzcft = $r3;
                    break;
                case 4:
                    this.mValue = $r3;
                    break;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
            }
            this.aOu.add(Integer.valueOf($i0));
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzk.zza(this, $r1, $i0);
        }

        public boolean zzcds() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }

        public Mergedpeoplemetadata zzcit() throws  {
            return this.aZT;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Addresses extends FastSafeParcelableJsonResponse {
        public static final Creator<Addresses> CREATOR = new zzl();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        String aPa;
        String aPb;
        String aPe;
        String aPf;
        String aPg;
        String aPh;
        Mergedpeoplemetadata aZT;
        String aZU;
        String mValue;
        final int mVersionCode;
        String zzcft;

        static {
            aOt.put("city", Field.forString("city", 2));
            aOt.put("country", Field.forString("country", 3));
            aOt.put("extendedAddress", Field.forString("extendedAddress", 5));
            aOt.put("metadata", Field.forConcreteType("metadata", 7, Mergedpeoplemetadata.class));
            aOt.put("poBox", Field.forString("poBox", 8));
            aOt.put("postalCode", Field.forString("postalCode", 9));
            aOt.put("region", Field.forString("region", 10));
            aOt.put("streetAddress", Field.forString("streetAddress", 11));
            aOt.put("type", Field.forString("type", 12));
            aOt.put("value", Field.forString("value", 13));
        }

        public Addresses() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        Addresses(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r5, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r6, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r7, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r8, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r9, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r10, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r11) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aPa = $r2;
            this.aPb = $r3;
            this.aZU = $r4;
            this.aZT = $r5;
            this.aPe = $r6;
            this.aPf = $r7;
            this.aPg = $r8;
            this.aPh = $r9;
            this.zzcft = $r10;
            this.mValue = $r11;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 7:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Addresses)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            Addresses $r2 = (Addresses) $r1;
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

        public String getCountry() throws  {
            return this.aPb;
        }

        public HashMap<String, Field<?, ?>> getFieldMappings() throws  {
            return aOt;
        }

        protected Object getFieldValue(Field $r1) throws  {
            switch ($r1.getSafeParcelableFieldId()) {
                case 2:
                    return this.aPa;
                case 3:
                    return this.aPb;
                case 4:
                case 6:
                    break;
                case 5:
                    return this.aZU;
                case 7:
                    return this.aZT;
                case 8:
                    return this.aPe;
                case 9:
                    return this.aPf;
                case 10:
                    return this.aPg;
                case 11:
                    return this.aPh;
                case 12:
                    return this.zzcft;
                case 13:
                    return this.mValue;
                default:
                    break;
            }
            throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
        }

        public String getPostalCode() throws  {
            return this.aPf;
        }

        public String getRegion() throws  {
            return this.aPg;
        }

        public String getStreetAddress() throws  {
            return this.aPh;
        }

        public String getType() throws  {
            return this.zzcft;
        }

        public String getValue() throws  {
            return this.mValue;
        }

        public boolean hasType() throws  {
            return this.aOu.contains(Integer.valueOf(12));
        }

        public boolean hasValue() throws  {
            return this.aOu.contains(Integer.valueOf(13));
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

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected void setStringInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r10) throws  {
            /*
            r7 = this;
            r0 = r8.getSafeParcelableFieldId();
            switch(r0) {
                case 2: goto L_0x0029;
                case 3: goto L_0x0035;
                case 4: goto L_0x0008;
                case 5: goto L_0x0038;
                case 6: goto L_0x0008;
                case 7: goto L_0x0008;
                case 8: goto L_0x003b;
                case 9: goto L_0x003e;
                case 10: goto L_0x0041;
                case 11: goto L_0x0044;
                case 12: goto L_0x0047;
                case 13: goto L_0x004a;
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
            r7.aPa = r10;
        L_0x002b:
            r5 = r7.aOu;
            r6 = java.lang.Integer.valueOf(r0);
            r5.add(r6);
            return;
        L_0x0035:
            r7.aPb = r10;
            goto L_0x002b;
        L_0x0038:
            r7.aZU = r10;
            goto L_0x002b;
        L_0x003b:
            r7.aPe = r10;
            goto L_0x002b;
        L_0x003e:
            r7.aPf = r10;
            goto L_0x002b;
        L_0x0041:
            r7.aPg = r10;
            goto L_0x002b;
        L_0x0044:
            r7.aPh = r10;
            goto L_0x002b;
        L_0x0047:
            r7.zzcft = r10;
            goto L_0x002b;
        L_0x004a:
            r7.mValue = r10;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Addresses.setStringInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.lang.String):void");
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzl.zza(this, $r1, $i0);
        }

        public boolean zzcds() throws  {
            return this.aOu.contains(Integer.valueOf(7));
        }

        public Mergedpeoplemetadata zzcit() throws  {
            return this.aZT;
        }

        public String zzciu() throws  {
            return this.aPa;
        }

        public boolean zzciv() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }

        public boolean zzciw() throws  {
            return this.aOu.contains(Integer.valueOf(3));
        }

        public String zzcix() throws  {
            return this.aPe;
        }

        public boolean zzciy() throws  {
            return this.aOu.contains(Integer.valueOf(8));
        }

        public boolean zzciz() throws  {
            return this.aOu.contains(Integer.valueOf(9));
        }

        public boolean zzcja() throws  {
            return this.aOu.contains(Integer.valueOf(10));
        }

        public boolean zzcjb() throws  {
            return this.aOu.contains(Integer.valueOf(11));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Birthdays extends FastSafeParcelableJsonResponse {
        public static final Creator<Birthdays> CREATOR = new zzm();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        String aPi;
        Mergedpeoplemetadata aZT;
        final int mVersionCode;

        static {
            aOt.put(WLTypes.VEHICLEDATA_ATTRIBUTE_DATE, Field.forString(WLTypes.VEHICLEDATA_ATTRIBUTE_DATE, 2));
            aOt.put("metadata", Field.forConcreteType("metadata", 3, Mergedpeoplemetadata.class));
        }

        public Birthdays() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        Birthdays(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", ")V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", ")V"}) Mergedpeoplemetadata $r3) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aPi = $r2;
            this.aZT = $r3;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 3:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Birthdays)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            Birthdays $r2 = (Birthdays) $r1;
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
                    return this.aPi;
                case 3:
                    return this.aZT;
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
                    this.aPi = $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
            }
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzm.zza(this, $r1, $i0);
        }

        public boolean zzcds() throws  {
            return this.aOu.contains(Integer.valueOf(3));
        }

        public Mergedpeoplemetadata zzcit() throws  {
            return this.aZT;
        }

        public String zzcjc() throws  {
            return this.aPi;
        }

        public boolean zzcjd() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class BraggingRights extends FastSafeParcelableJsonResponse {
        public static final Creator<BraggingRights> CREATOR = new zzn();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        Mergedpeoplemetadata aZT;
        String mValue;
        final int mVersionCode;

        static {
            aOt.put("metadata", Field.forConcreteType("metadata", 2, Mergedpeoplemetadata.class));
            aOt.put("value", Field.forString("value", 3));
        }

        public BraggingRights() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        BraggingRights(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aZT = $r2;
            this.mValue = $r3;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof BraggingRights)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            BraggingRights $r2 = (BraggingRights) $r1;
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
                    return this.aZT;
                case 3:
                    return this.mValue;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public String getValue() throws  {
            return this.mValue;
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

        protected boolean isFieldSet(Field $r1) throws  {
            return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
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
            zzn.zza(this, $r1, $i0);
        }

        public boolean zzcds() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }

        public Mergedpeoplemetadata zzcit() throws  {
            return this.aZT;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Calendars extends FastSafeParcelableJsonResponse {
        public static final Creator<Calendars> CREATOR = new zzo();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        String aPd;
        Mergedpeoplemetadata aZT;
        final int mVersionCode;
        String zzad;
        String zzcft;

        static {
            aOt.put("formattedType", Field.forString("formattedType", 2));
            aOt.put("metadata", Field.forConcreteType("metadata", 3, Mergedpeoplemetadata.class));
            aOt.put("type", Field.forString("type", 4));
            aOt.put("url", Field.forString("url", 5));
        }

        public Calendars() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        Calendars(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r5) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aPd = $r2;
            this.aZT = $r3;
            this.zzcft = $r4;
            this.zzad = $r5;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 3:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Calendars)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            Calendars $r2 = (Calendars) $r1;
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
                    return this.aPd;
                case 3:
                    return this.aZT;
                case 4:
                    return this.zzcft;
                case 5:
                    return this.zzad;
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

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected void setStringInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r10) throws  {
            /*
            r7 = this;
            r0 = r8.getSafeParcelableFieldId();
            switch(r0) {
                case 2: goto L_0x0029;
                case 3: goto L_0x0008;
                case 4: goto L_0x0035;
                case 5: goto L_0x0038;
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
            r7.aPd = r10;
        L_0x002b:
            r5 = r7.aOu;
            r6 = java.lang.Integer.valueOf(r0);
            r5.add(r6);
            return;
        L_0x0035:
            r7.zzcft = r10;
            goto L_0x002b;
        L_0x0038:
            r7.zzad = r10;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Calendars.setStringInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.lang.String):void");
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzo.zza(this, $r1, $i0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class ClientData extends FastSafeParcelableJsonResponse {
        public static final Creator<ClientData> CREATOR = new zzp();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        Mergedpeoplemetadata aZT;
        String jw;
        String mValue;
        final int mVersionCode;
        String zzayg;

        static {
            aOt.put("key", Field.forString("key", 2));
            aOt.put("metadata", Field.forConcreteType("metadata", 3, Mergedpeoplemetadata.class));
            aOt.put("namespace", Field.forString("namespace", 4));
            aOt.put("value", Field.forString("value", 5));
        }

        public ClientData() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        ClientData(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r5) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.zzayg = $r2;
            this.aZT = $r3;
            this.jw = $r4;
            this.mValue = $r5;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 3:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof ClientData)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            ClientData $r2 = (ClientData) $r1;
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
                    return this.zzayg;
                case 3:
                    return this.aZT;
                case 4:
                    return this.jw;
                case 5:
                    return this.mValue;
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

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected void setStringInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r10) throws  {
            /*
            r7 = this;
            r0 = r8.getSafeParcelableFieldId();
            switch(r0) {
                case 2: goto L_0x0029;
                case 3: goto L_0x0008;
                case 4: goto L_0x0035;
                case 5: goto L_0x0038;
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
            r7.zzayg = r10;
        L_0x002b:
            r5 = r7.aOu;
            r6 = java.lang.Integer.valueOf(r0);
            r5.add(r6);
            return;
        L_0x0035:
            r7.jw = r10;
            goto L_0x002b;
        L_0x0038:
            r7.mValue = r10;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.ClientData.setStringInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.lang.String):void");
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzp.zza(this, $r1, $i0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class CoverPhotos extends FastSafeParcelableJsonResponse {
        public static final Creator<CoverPhotos> CREATOR = new zzq();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        boolean aUC;
        Mergedpeoplemetadata aZT;
        final int mVersionCode;
        String zzad;
        int zzaiq;
        int zzair;
        String zzbgd;

        static {
            aOt.put("height", Field.forInteger("height", 2));
            aOt.put("id", Field.forString("id", 3));
            aOt.put("isDefault", Field.forBoolean("isDefault", 5));
            aOt.put("metadata", Field.forConcreteType("metadata", 6, Mergedpeoplemetadata.class));
            aOt.put("url", Field.forString("url", 7));
            aOt.put("width", Field.forInteger("width", 8));
        }

        public CoverPhotos() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        CoverPhotos(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;II", "Ljava/lang/String;", "Z", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "I)V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;II", "Ljava/lang/String;", "Z", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "I)V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;II", "Ljava/lang/String;", "Z", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "I)V"}) int $i1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;II", "Ljava/lang/String;", "Z", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "I)V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;II", "Ljava/lang/String;", "Z", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "I)V"}) boolean $z0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;II", "Ljava/lang/String;", "Z", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "I)V"}) Mergedpeoplemetadata $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;II", "Ljava/lang/String;", "Z", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "I)V"}) String $r4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;II", "Ljava/lang/String;", "Z", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "I)V"}) int $i2) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.zzair = $i1;
            this.zzbgd = $r2;
            this.aUC = $z0;
            this.aZT = $r3;
            this.zzad = $r4;
            this.zzaiq = $i2;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 6:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof CoverPhotos)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            CoverPhotos $r2 = (CoverPhotos) $r1;
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
                    return Integer.valueOf(this.zzair);
                case 3:
                    return this.zzbgd;
                case 4:
                    break;
                case 5:
                    return Boolean.valueOf(this.aUC);
                case 6:
                    return this.aZT;
                case 7:
                    return this.zzad;
                case 8:
                    return Integer.valueOf(this.zzaiq);
                default:
                    break;
            }
            throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
        }

        public int getHeight() throws  {
            return this.zzair;
        }

        public String getId() throws  {
            return this.zzbgd;
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

        public boolean hasId() throws  {
            return this.aOu.contains(Integer.valueOf(3));
        }

        public boolean hasUrl() throws  {
            return this.aOu.contains(Integer.valueOf(7));
        }

        public boolean hasWidth() throws  {
            return this.aOu.contains(Integer.valueOf(8));
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

        public boolean isDefault() throws  {
            return this.aUC;
        }

        protected boolean isFieldSet(Field $r1) throws  {
            return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
        }

        protected void setBooleanInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) boolean $z0) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 5:
                    this.aUC = $z0;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a boolean.");
            }
        }

        protected void setIntegerInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) int $i0) throws  {
            int $i1 = $r1.getSafeParcelableFieldId();
            switch ($i1) {
                case 2:
                    this.zzair = $i0;
                    break;
                case 8:
                    this.zzaiq = $i0;
                    break;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i1 + " is not known to be an int.");
            }
            this.aOu.add(Integer.valueOf($i1));
        }

        protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 3:
                    this.zzbgd = $r3;
                    break;
                case 7:
                    this.zzad = $r3;
                    break;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
            }
            this.aOu.add(Integer.valueOf($i0));
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzq.zza(this, $r1, $i0);
        }

        public boolean zzcje() throws  {
            return this.aOu.contains(Integer.valueOf(5));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class CustomFields extends FastSafeParcelableJsonResponse {
        public static final Creator<CustomFields> CREATOR = new zzr();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        Mergedpeoplemetadata aZT;
        String mValue;
        final int mVersionCode;
        String zzayg;

        static {
            aOt.put("key", Field.forString("key", 2));
            aOt.put("metadata", Field.forConcreteType("metadata", 3, Mergedpeoplemetadata.class));
            aOt.put("value", Field.forString("value", 4));
        }

        public CustomFields() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        CustomFields(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) String $r4) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.zzayg = $r2;
            this.aZT = $r3;
            this.mValue = $r4;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 3:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof CustomFields)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            CustomFields $r2 = (CustomFields) $r1;
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
                    return this.zzayg;
                case 3:
                    return this.aZT;
                case 4:
                    return this.mValue;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public String getKey() throws  {
            return this.zzayg;
        }

        public String getValue() throws  {
            return this.mValue;
        }

        public boolean hasKey() throws  {
            return this.aOu.contains(Integer.valueOf(2));
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

        protected boolean isFieldSet(Field $r1) throws  {
            return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected void setStringInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r10) throws  {
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
            r7.zzayg = r10;
        L_0x002b:
            r5 = r7.aOu;
            r6 = java.lang.Integer.valueOf(r0);
            r5.add(r6);
            return;
        L_0x0035:
            r7.mValue = r10;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.CustomFields.setStringInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.lang.String):void");
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzr.zza(this, $r1, $i0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Emails extends FastSafeParcelableJsonResponse {
        public static final Creator<Emails> CREATOR = new zzs();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        String aPd;
        Mergedpeoplemetadata aZT;
        List<Certificates> aZV;
        String mValue;
        final int mVersionCode;
        String zzcft;

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class Certificates extends FastSafeParcelableJsonResponse {
            public static final Creator<Certificates> CREATOR = new zzt();
            private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
            final Set<Integer> aOu;
            Mergedpeoplemetadata aZT;
            Status aZW;
            final int mVersionCode;

            /* compiled from: dalvik_source_com.waze.apk */
            public static final class Status extends FastSafeParcelableJsonResponse {
                public static final Creator<Status> CREATOR = new zzu();
                private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
                final Set<Integer> aOu;
                String aZX;
                long aZY;
                String mCode;
                final int mVersionCode;

                static {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r0 = new com.google.android.gms.plus.service.v2whitelisted.models.zzu;
                    r0.<init>();
                    CREATOR = r0;
                    r1 = new java.util.HashMap;
                    r1.<init>();
                    aOt = r1;
                    r1 = aOt;
                    r3 = "code";
                    r4 = 2;
                    r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forString(r3, r4);
                    r3 = "code";
                    r1.put(r3, r2);
                    r1 = aOt;
                    r3 = "expiration";
                    r4 = 3;
                    r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forString(r3, r4);
                    r3 = "expiration";
                    r1.put(r3, r2);
                    r1 = aOt;
                    r3 = "expirationSeconds";
                    r4 = 4;
                    r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forLong(r3, r4);
                    r3 = "expirationSeconds";
                    r1.put(r3, r2);
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.Status.<clinit>():void");
                }

                public Status() throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r2 = this;
                    r2.<init>();
                    r0 = 1;
                    r2.mVersionCode = r0;
                    r1 = new java.util.HashSet;
                    r1.<init>();
                    r2.aOu = r1;
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.Status.<init>():void");
                }

                Status(@dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "J)V"}) java.util.Set<java.lang.Integer> r1, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "J)V"}) int r2, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "J)V"}) java.lang.String r3, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "J)V"}) java.lang.String r4, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "J)V"}) long r5) throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r0 = this;
                    r0.<init>();
                    r0.aOu = r1;
                    r0.mVersionCode = r2;
                    r0.mCode = r3;
                    r0.aZX = r4;
                    r0.aZY = r5;
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.Status.<init>(java.util.Set, int, java.lang.String, java.lang.String, long):void");
                }

                public boolean equals(java.lang.Object r11) throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r10 = this;
                    r0 = r11 instanceof com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.Status;
                    if (r0 != 0) goto L_0x0006;
                L_0x0004:
                    r1 = 0;
                    return r1;
                L_0x0006:
                    if (r10 != r11) goto L_0x000a;
                L_0x0008:
                    r1 = 1;
                    return r1;
                L_0x000a:
                    r3 = r11;
                    r3 = (com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.Status) r3;
                    r2 = r3;
                    r4 = aOt;
                    r5 = r4.values();
                    r6 = r5.iterator();
                L_0x0018:
                    r0 = r6.hasNext();
                    if (r0 == 0) goto L_0x004c;
                L_0x001e:
                    r11 = r6.next();
                    r8 = r11;
                    r8 = (com.google.android.gms.common.server.response.FastJsonResponse.Field) r8;
                    r7 = r8;
                    r0 = r10.isFieldSet(r7);
                    if (r0 == 0) goto L_0x0044;
                L_0x002c:
                    r0 = r2.isFieldSet(r7);
                    if (r0 == 0) goto L_0x0042;
                L_0x0032:
                    r11 = r10.getFieldValue(r7);
                    r9 = r2.getFieldValue(r7);
                    r0 = r11.equals(r9);
                    if (r0 != 0) goto L_0x0018;
                L_0x0040:
                    r1 = 0;
                    return r1;
                L_0x0042:
                    r1 = 0;
                    return r1;
                L_0x0044:
                    r0 = r2.isFieldSet(r7);
                    if (r0 == 0) goto L_0x0018;
                L_0x004a:
                    r1 = 0;
                    return r1;
                L_0x004c:
                    r1 = 1;
                    return r1;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.Status.equals(java.lang.Object):boolean");
                }

                public java.util.HashMap<java.lang.String, com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?>> getFieldMappings() throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r1 = this;
                    r0 = aOt;
                    return r0;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.Status.getFieldMappings():java.util.HashMap<java.lang.String, com.google.android.gms.common.server.response.FastJsonResponse$Field<?, ?>>");
                }

                protected java.lang.Object getFieldValue(com.google.android.gms.common.server.response.FastJsonResponse.Field r10) throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r9 = this;
                    r0 = r10.getSafeParcelableFieldId();
                    switch(r0) {
                        case 2: goto L_0x0027;
                        case 3: goto L_0x002a;
                        case 4: goto L_0x002d;
                        default: goto L_0x0007;
                    };
                L_0x0007:
                    goto L_0x0008;
                L_0x0008:
                    r1 = new java.lang.IllegalStateException;
                    r0 = r10.getSafeParcelableFieldId();
                    r2 = new java.lang.StringBuilder;
                    r3 = 38;
                    r2.<init>(r3);
                    r4 = "Unknown safe parcelable id=";
                    r2 = r2.append(r4);
                    r2 = r2.append(r0);
                    r5 = r2.toString();
                    r1.<init>(r5);
                    throw r1;
                L_0x0027:
                    r5 = r9.mCode;
                    return r5;
                L_0x002a:
                    r5 = r9.aZX;
                    return r5;
                L_0x002d:
                    r6 = r9.aZY;
                    r8 = java.lang.Long.valueOf(r6);
                    return r8;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.Status.getFieldValue(com.google.android.gms.common.server.response.FastJsonResponse$Field):java.lang.Object");
                }

                public int hashCode() throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r9 = this;
                    r0 = aOt;
                    r1 = r0.values();
                    r2 = r1.iterator();
                    r3 = 0;
                L_0x000b:
                    r4 = r2.hasNext();
                    if (r4 == 0) goto L_0x002f;
                L_0x0011:
                    r5 = r2.next();
                    r7 = r5;
                    r7 = (com.google.android.gms.common.server.response.FastJsonResponse.Field) r7;
                    r6 = r7;
                    r4 = r9.isFieldSet(r6);
                    if (r4 == 0) goto L_0x0030;
                L_0x001f:
                    r8 = r6.getSafeParcelableFieldId();
                    r3 = r3 + r8;
                    r5 = r9.getFieldValue(r6);
                    r8 = r5.hashCode();
                    r3 = r8 + r3;
                L_0x002e:
                    goto L_0x000b;
                L_0x002f:
                    return r3;
                L_0x0030:
                    goto L_0x002e;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.Status.hashCode():int");
                }

                protected boolean isFieldSet(com.google.android.gms.common.server.response.FastJsonResponse.Field r5) throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r4 = this;
                    r0 = r4.aOu;
                    r1 = r5.getSafeParcelableFieldId();
                    r2 = java.lang.Integer.valueOf(r1);
                    r3 = r0.contains(r2);
                    return r3;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.Status.isFieldSet(com.google.android.gms.common.server.response.FastJsonResponse$Field):boolean");
                }

                protected void setLongInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "J)V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "J)V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "J)V"}) long r10) throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r7 = this;
                    r0 = r8.getSafeParcelableFieldId();
                    switch(r0) {
                        case 4: goto L_0x0029;
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
                    r4 = " is not known to be a long.";
                    r2 = r2.append(r4);
                    r9 = r2.toString();
                    r1.<init>(r9);
                    throw r1;
                L_0x0029:
                    r7.aZY = r10;
                    r5 = r7.aOu;
                    r6 = java.lang.Integer.valueOf(r0);
                    r5.add(r6);
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.Status.setLongInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, long):void");
                }

                protected void setStringInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r10) throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r7 = this;
                    r0 = r8.getSafeParcelableFieldId();
                    switch(r0) {
                        case 2: goto L_0x0029;
                        case 3: goto L_0x0035;
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
                    r7.mCode = r10;
                L_0x002b:
                    r5 = r7.aOu;
                    r6 = java.lang.Integer.valueOf(r0);
                    r5.add(r6);
                    return;
                L_0x0035:
                    r7.aZX = r10;
                    goto L_0x002b;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.Status.setStringInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.lang.String):void");
                }

                public void writeToParcel(android.os.Parcel r1, int r2) throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r0 = this;
                    com.google.android.gms.plus.service.v2whitelisted.models.zzu.zza(r0, r1, r2);
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.Status.writeToParcel(android.os.Parcel, int):void");
                }
            }

            static {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r0 = new com.google.android.gms.plus.service.v2whitelisted.models.zzt;
                r0.<init>();
                CREATOR = r0;
                r1 = new java.util.HashMap;
                r1.<init>();
                aOt = r1;
                r1 = aOt;
                r3 = "metadata";
                r4 = 3;
                r5 = com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata.class;
                r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forConcreteType(r3, r4, r5);
                r3 = "metadata";
                r1.put(r3, r2);
                r1 = aOt;
                r3 = "status";
                r4 = 4;
                r5 = com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.Status.class;
                r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forConcreteType(r3, r4, r5);
                r3 = "status";
                r1.put(r3, r2);
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.<clinit>():void");
            }

            public Certificates() throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r2 = this;
                r2.<init>();
                r0 = 1;
                r2.mVersionCode = r0;
                r1 = new java.util.HashSet;
                r1.<init>();
                r2.aOu = r1;
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.<init>():void");
            }

            Certificates(@dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails$Certificates$Status;", ")V"}) java.util.Set<java.lang.Integer> r1, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails$Certificates$Status;", ")V"}) int r2, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails$Certificates$Status;", ")V"}) com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata r3, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails$Certificates$Status;", ")V"}) com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.Status r4) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r0 = this;
                r0.<init>();
                r0.aOu = r1;
                r0.mVersionCode = r2;
                r0.aZT = r3;
                r0.aZW = r4;
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.<init>(java.util.Set, int, com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata, com.google.android.gms.plus.service.v2whitelisted.models.Person$Emails$Certificates$Status):void");
            }

            public <T extends com.google.android.gms.common.server.response.FastJsonResponse> void addConcreteTypeInternal(@dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r15, @dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) java.lang.String r16, @dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T r17) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r14 = this;
                r1 = r15.getSafeParcelableFieldId();
                switch(r1) {
                    case 3: goto L_0x0051;
                    case 4: goto L_0x0062;
                    default: goto L_0x0007;
                };
            L_0x0007:
                goto L_0x0008;
            L_0x0008:
                r2 = new java.lang.IllegalArgumentException;
                r0 = r17;
                r3 = r0.getClass();
                r16 = r3.getCanonicalName();
                r0 = r16;
                r16 = java.lang.String.valueOf(r0);
                r4 = new java.lang.StringBuilder;
                r0 = r16;
                r5 = java.lang.String.valueOf(r0);
                r6 = r5.length();
                r6 = r6 + 62;
                r4.<init>(r6);
                r7 = "Field with id=";
                r4 = r4.append(r7);
                r4 = r4.append(r1);
                r7 = " is not a known custom type.  Found ";
                r4 = r4.append(r7);
                r0 = r16;
                r4 = r4.append(r0);
                r7 = ".";
                r4 = r4.append(r7);
                r16 = r4.toString();
                r0 = r16;
                r2.<init>(r0);
                throw r2;
            L_0x0051:
                r9 = r17;
                r9 = (com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata) r9;
                r8 = r9;
                r14.aZT = r8;
            L_0x0058:
                r10 = r14.aOu;
                r11 = java.lang.Integer.valueOf(r1);
                r10.add(r11);
                return;
            L_0x0062:
                r13 = r17;
                r13 = (com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.Status) r13;
                r12 = r13;
                r14.aZW = r12;
                goto L_0x0058;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.addConcreteTypeInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, com.google.android.gms.common.server.response.FastJsonResponse):void");
            }

            public boolean equals(java.lang.Object r11) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r10 = this;
                r0 = r11 instanceof com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates;
                if (r0 != 0) goto L_0x0006;
            L_0x0004:
                r1 = 0;
                return r1;
            L_0x0006:
                if (r10 != r11) goto L_0x000a;
            L_0x0008:
                r1 = 1;
                return r1;
            L_0x000a:
                r3 = r11;
                r3 = (com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates) r3;
                r2 = r3;
                r4 = aOt;
                r5 = r4.values();
                r6 = r5.iterator();
            L_0x0018:
                r0 = r6.hasNext();
                if (r0 == 0) goto L_0x004c;
            L_0x001e:
                r11 = r6.next();
                r8 = r11;
                r8 = (com.google.android.gms.common.server.response.FastJsonResponse.Field) r8;
                r7 = r8;
                r0 = r10.isFieldSet(r7);
                if (r0 == 0) goto L_0x0044;
            L_0x002c:
                r0 = r2.isFieldSet(r7);
                if (r0 == 0) goto L_0x0042;
            L_0x0032:
                r11 = r10.getFieldValue(r7);
                r9 = r2.getFieldValue(r7);
                r0 = r11.equals(r9);
                if (r0 != 0) goto L_0x0018;
            L_0x0040:
                r1 = 0;
                return r1;
            L_0x0042:
                r1 = 0;
                return r1;
            L_0x0044:
                r0 = r2.isFieldSet(r7);
                if (r0 == 0) goto L_0x0018;
            L_0x004a:
                r1 = 0;
                return r1;
            L_0x004c:
                r1 = 1;
                return r1;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.equals(java.lang.Object):boolean");
            }

            public java.util.HashMap<java.lang.String, com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?>> getFieldMappings() throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r1 = this;
                r0 = aOt;
                return r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.getFieldMappings():java.util.HashMap<java.lang.String, com.google.android.gms.common.server.response.FastJsonResponse$Field<?, ?>>");
            }

            protected java.lang.Object getFieldValue(com.google.android.gms.common.server.response.FastJsonResponse.Field r9) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r8 = this;
                r0 = r9.getSafeParcelableFieldId();
                switch(r0) {
                    case 3: goto L_0x0027;
                    case 4: goto L_0x002a;
                    default: goto L_0x0007;
                };
            L_0x0007:
                goto L_0x0008;
            L_0x0008:
                r1 = new java.lang.IllegalStateException;
                r0 = r9.getSafeParcelableFieldId();
                r2 = new java.lang.StringBuilder;
                r3 = 38;
                r2.<init>(r3);
                r4 = "Unknown safe parcelable id=";
                r2 = r2.append(r4);
                r2 = r2.append(r0);
                r5 = r2.toString();
                r1.<init>(r5);
                throw r1;
            L_0x0027:
                r6 = r8.aZT;
                return r6;
            L_0x002a:
                r7 = r8.aZW;
                return r7;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.getFieldValue(com.google.android.gms.common.server.response.FastJsonResponse$Field):java.lang.Object");
            }

            public int hashCode() throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r9 = this;
                r0 = aOt;
                r1 = r0.values();
                r2 = r1.iterator();
                r3 = 0;
            L_0x000b:
                r4 = r2.hasNext();
                if (r4 == 0) goto L_0x002f;
            L_0x0011:
                r5 = r2.next();
                r7 = r5;
                r7 = (com.google.android.gms.common.server.response.FastJsonResponse.Field) r7;
                r6 = r7;
                r4 = r9.isFieldSet(r6);
                if (r4 == 0) goto L_0x0030;
            L_0x001f:
                r8 = r6.getSafeParcelableFieldId();
                r3 = r3 + r8;
                r5 = r9.getFieldValue(r6);
                r8 = r5.hashCode();
                r3 = r8 + r3;
            L_0x002e:
                goto L_0x000b;
            L_0x002f:
                return r3;
            L_0x0030:
                goto L_0x002e;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.hashCode():int");
            }

            protected boolean isFieldSet(com.google.android.gms.common.server.response.FastJsonResponse.Field r5) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r4 = this;
                r0 = r4.aOu;
                r1 = r5.getSafeParcelableFieldId();
                r2 = java.lang.Integer.valueOf(r1);
                r3 = r0.contains(r2);
                return r3;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.isFieldSet(com.google.android.gms.common.server.response.FastJsonResponse$Field):boolean");
            }

            public void writeToParcel(android.os.Parcel r1, int r2) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r0 = this;
                com.google.android.gms.plus.service.v2whitelisted.models.zzt.zza(r0, r1, r2);
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.writeToParcel(android.os.Parcel, int):void");
            }
        }

        static {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r0 = new com.google.android.gms.plus.service.v2whitelisted.models.zzs;
            r0.<init>();
            CREATOR = r0;
            r1 = new java.util.HashMap;
            r1.<init>();
            aOt = r1;
            r1 = aOt;
            r3 = "certificates";
            r4 = 2;
            r5 = com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.class;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forConcreteTypeArray(r3, r4, r5);
            r3 = "certificates";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "formattedType";
            r4 = 4;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forString(r3, r4);
            r3 = "formattedType";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "metadata";
            r4 = 5;
            r5 = com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata.class;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forConcreteType(r3, r4, r5);
            r3 = "metadata";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "type";
            r4 = 6;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forString(r3, r4);
            r3 = "type";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "value";
            r4 = 7;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forString(r3, r4);
            r3 = "value";
            r1.put(r3, r2);
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.<clinit>():void");
        }

        public Emails() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r2 = this;
            r2.<init>();
            r0 = 1;
            r2.mVersionCode = r0;
            r1 = new java.util.HashSet;
            r1.<init>();
            r2.aOu = r1;
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.<init>():void");
        }

        Emails(@dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails$Certificates;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.util.Set<java.lang.Integer> r1, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails$Certificates;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int r2, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails$Certificates;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.util.List<com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates> r3, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails$Certificates;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r4, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails$Certificates;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata r5, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails$Certificates;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r6, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails$Certificates;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r7) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r0 = this;
            r0.<init>();
            r0.aOu = r1;
            r0.mVersionCode = r2;
            r0.aZV = r3;
            r0.aPd = r4;
            r0.aZT = r5;
            r0.zzcft = r6;
            r0.mValue = r7;
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.<init>(java.util.Set, int, java.util.List, java.lang.String, com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata, java.lang.String, java.lang.String):void");
        }

        public <T extends com.google.android.gms.common.server.response.FastJsonResponse> void addConcreteTypeArrayInternal(@dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r10, @dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) java.lang.String r11, @dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) java.util.ArrayList<T> r12) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r9 = this;
            r0 = r10.getSafeParcelableFieldId();
            switch(r0) {
                case 2: goto L_0x0047;
                default: goto L_0x0007;
            };
        L_0x0007:
            goto L_0x0008;
        L_0x0008:
            r1 = new java.lang.IllegalArgumentException;
            r2 = r12.getClass();
            r11 = r2.getCanonicalName();
            r11 = java.lang.String.valueOf(r11);
            r3 = new java.lang.StringBuilder;
            r4 = java.lang.String.valueOf(r11);
            r5 = r4.length();
            r5 = r5 + 71;
            r3.<init>(r5);
            r6 = "Field with id=";
            r3 = r3.append(r6);
            r3 = r3.append(r0);
            r6 = " is not a known array of custom type.  Found ";
            r3 = r3.append(r6);
            r3 = r3.append(r11);
            r6 = ".";
            r3 = r3.append(r6);
            r11 = r3.toString();
            r1.<init>(r11);
            throw r1;
        L_0x0047:
            r9.aZV = r12;
            r7 = r9.aOu;
            r8 = java.lang.Integer.valueOf(r0);
            r7.add(r8);
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.addConcreteTypeArrayInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.util.ArrayList):void");
        }

        public <T extends com.google.android.gms.common.server.response.FastJsonResponse> void addConcreteTypeInternal(@dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r12, @dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) java.lang.String r13, @dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T r14) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r11 = this;
            r0 = r12.getSafeParcelableFieldId();
            switch(r0) {
                case 5: goto L_0x0047;
                default: goto L_0x0007;
            };
        L_0x0007:
            goto L_0x0008;
        L_0x0008:
            r1 = new java.lang.IllegalArgumentException;
            r2 = r14.getClass();
            r13 = r2.getCanonicalName();
            r13 = java.lang.String.valueOf(r13);
            r3 = new java.lang.StringBuilder;
            r4 = java.lang.String.valueOf(r13);
            r5 = r4.length();
            r5 = r5 + 62;
            r3.<init>(r5);
            r6 = "Field with id=";
            r3 = r3.append(r6);
            r3 = r3.append(r0);
            r6 = " is not a known custom type.  Found ";
            r3 = r3.append(r6);
            r3 = r3.append(r13);
            r6 = ".";
            r3 = r3.append(r6);
            r13 = r3.toString();
            r1.<init>(r13);
            throw r1;
        L_0x0047:
            r8 = r14;
            r8 = (com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata) r8;
            r7 = r8;
            r11.aZT = r7;
            r9 = r11.aOu;
            r10 = java.lang.Integer.valueOf(r0);
            r9.add(r10);
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.addConcreteTypeInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, com.google.android.gms.common.server.response.FastJsonResponse):void");
        }

        public boolean equals(java.lang.Object r11) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r10 = this;
            r0 = r11 instanceof com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails;
            if (r0 != 0) goto L_0x0006;
        L_0x0004:
            r1 = 0;
            return r1;
        L_0x0006:
            if (r10 != r11) goto L_0x000a;
        L_0x0008:
            r1 = 1;
            return r1;
        L_0x000a:
            r3 = r11;
            r3 = (com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails) r3;
            r2 = r3;
            r4 = aOt;
            r5 = r4.values();
            r6 = r5.iterator();
        L_0x0018:
            r0 = r6.hasNext();
            if (r0 == 0) goto L_0x004c;
        L_0x001e:
            r11 = r6.next();
            r8 = r11;
            r8 = (com.google.android.gms.common.server.response.FastJsonResponse.Field) r8;
            r7 = r8;
            r0 = r10.isFieldSet(r7);
            if (r0 == 0) goto L_0x0044;
        L_0x002c:
            r0 = r2.isFieldSet(r7);
            if (r0 == 0) goto L_0x0042;
        L_0x0032:
            r11 = r10.getFieldValue(r7);
            r9 = r2.getFieldValue(r7);
            r0 = r11.equals(r9);
            if (r0 != 0) goto L_0x0018;
        L_0x0040:
            r1 = 0;
            return r1;
        L_0x0042:
            r1 = 0;
            return r1;
        L_0x0044:
            r0 = r2.isFieldSet(r7);
            if (r0 == 0) goto L_0x0018;
        L_0x004a:
            r1 = 0;
            return r1;
        L_0x004c:
            r1 = 1;
            return r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.equals(java.lang.Object):boolean");
        }

        public java.util.HashMap<java.lang.String, com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?>> getFieldMappings() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = aOt;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.getFieldMappings():java.util.HashMap<java.lang.String, com.google.android.gms.common.server.response.FastJsonResponse$Field<?, ?>>");
        }

        protected java.lang.Object getFieldValue(com.google.android.gms.common.server.response.FastJsonResponse.Field r9) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r8 = this;
            r0 = r9.getSafeParcelableFieldId();
            switch(r0) {
                case 2: goto L_0x0027;
                case 3: goto L_0x0008;
                case 4: goto L_0x002a;
                case 5: goto L_0x002d;
                case 6: goto L_0x0030;
                case 7: goto L_0x0033;
                default: goto L_0x0007;
            };
        L_0x0007:
            goto L_0x0008;
        L_0x0008:
            r1 = new java.lang.IllegalStateException;
            r0 = r9.getSafeParcelableFieldId();
            r2 = new java.lang.StringBuilder;
            r3 = 38;
            r2.<init>(r3);
            r4 = "Unknown safe parcelable id=";
            r2 = r2.append(r4);
            r2 = r2.append(r0);
            r5 = r2.toString();
            r1.<init>(r5);
            throw r1;
        L_0x0027:
            r6 = r8.aZV;
            return r6;
        L_0x002a:
            r5 = r8.aPd;
            return r5;
        L_0x002d:
            r7 = r8.aZT;
            return r7;
        L_0x0030:
            r5 = r8.zzcft;
            return r5;
        L_0x0033:
            r5 = r8.mValue;
            return r5;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.getFieldValue(com.google.android.gms.common.server.response.FastJsonResponse$Field):java.lang.Object");
        }

        public java.lang.String getType() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = r1.zzcft;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.getType():java.lang.String");
        }

        public java.lang.String getValue() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = r1.mValue;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.getValue():java.lang.String");
        }

        public boolean hasType() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r2 = 6;
            r1 = java.lang.Integer.valueOf(r2);
            r3 = r0.contains(r1);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.hasType():boolean");
        }

        public boolean hasValue() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r2 = 7;
            r1 = java.lang.Integer.valueOf(r2);
            r3 = r0.contains(r1);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.hasValue():boolean");
        }

        public int hashCode() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r9 = this;
            r0 = aOt;
            r1 = r0.values();
            r2 = r1.iterator();
            r3 = 0;
        L_0x000b:
            r4 = r2.hasNext();
            if (r4 == 0) goto L_0x002f;
        L_0x0011:
            r5 = r2.next();
            r7 = r5;
            r7 = (com.google.android.gms.common.server.response.FastJsonResponse.Field) r7;
            r6 = r7;
            r4 = r9.isFieldSet(r6);
            if (r4 == 0) goto L_0x0030;
        L_0x001f:
            r8 = r6.getSafeParcelableFieldId();
            r3 = r3 + r8;
            r5 = r9.getFieldValue(r6);
            r8 = r5.hashCode();
            r3 = r8 + r3;
        L_0x002e:
            goto L_0x000b;
        L_0x002f:
            return r3;
        L_0x0030:
            goto L_0x002e;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.hashCode():int");
        }

        protected boolean isFieldSet(com.google.android.gms.common.server.response.FastJsonResponse.Field r5) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r1 = r5.getSafeParcelableFieldId();
            r2 = java.lang.Integer.valueOf(r1);
            r3 = r0.contains(r2);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.isFieldSet(com.google.android.gms.common.server.response.FastJsonResponse$Field):boolean");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected void setStringInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r10) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r7 = this;
            r0 = r8.getSafeParcelableFieldId();
            switch(r0) {
                case 4: goto L_0x0029;
                case 5: goto L_0x0008;
                case 6: goto L_0x0035;
                case 7: goto L_0x0038;
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
            r7.aPd = r10;
        L_0x002b:
            r5 = r7.aOu;
            r6 = java.lang.Integer.valueOf(r0);
            r5.add(r6);
            return;
        L_0x0035:
            r7.zzcft = r10;
            goto L_0x002b;
        L_0x0038:
            r7.mValue = r10;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.setStringInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.lang.String):void");
        }

        public void writeToParcel(android.os.Parcel r1, int r2) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r0 = this;
            com.google.android.gms.plus.service.v2whitelisted.models.zzs.zza(r0, r1, r2);
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.writeToParcel(android.os.Parcel, int):void");
        }

        public boolean zzcds() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r2 = 5;
            r1 = java.lang.Integer.valueOf(r2);
            r3 = r0.contains(r1);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.zzcds():boolean");
        }

        public com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata zzcit() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = r1.aZT;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.zzcit():com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata");
        }

        public java.lang.String zzcjf() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = r1.aPd;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.zzcjf():java.lang.String");
        }

        public boolean zzcjg() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r2 = 4;
            r1 = java.lang.Integer.valueOf(r2);
            r3 = r0.contains(r1);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.zzcjg():boolean");
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Events extends FastSafeParcelableJsonResponse {
        public static final Creator<Events> CREATOR = new zzv();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        String aPd;
        String aPi;
        Mergedpeoplemetadata aZT;
        final int mVersionCode;
        String zzcft;

        static {
            aOt.put(WLTypes.VEHICLEDATA_ATTRIBUTE_DATE, Field.forString(WLTypes.VEHICLEDATA_ATTRIBUTE_DATE, 2));
            aOt.put("formattedType", Field.forString("formattedType", 3));
            aOt.put("metadata", Field.forConcreteType("metadata", 4, Mergedpeoplemetadata.class));
            aOt.put("type", Field.forString("type", 5));
        }

        public Events() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        Events(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) String $r5) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aPi = $r2;
            this.aPd = $r3;
            this.aZT = $r4;
            this.zzcft = $r5;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 4:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Events)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            Events $r2 = (Events) $r1;
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
                    return this.aPi;
                case 3:
                    return this.aPd;
                case 4:
                    return this.aZT;
                case 5:
                    return this.zzcft;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public String getType() throws  {
            return this.zzcft;
        }

        public boolean hasType() throws  {
            return this.aOu.contains(Integer.valueOf(5));
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

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected void setStringInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r10) throws  {
            /*
            r7 = this;
            r0 = r8.getSafeParcelableFieldId();
            switch(r0) {
                case 2: goto L_0x0029;
                case 3: goto L_0x0035;
                case 4: goto L_0x0008;
                case 5: goto L_0x0038;
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
            r7.aPi = r10;
        L_0x002b:
            r5 = r7.aOu;
            r6 = java.lang.Integer.valueOf(r0);
            r5.add(r6);
            return;
        L_0x0035:
            r7.aPd = r10;
            goto L_0x002b;
        L_0x0038:
            r7.zzcft = r10;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Events.setStringInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.lang.String):void");
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzv.zza(this, $r1, $i0);
        }

        public boolean zzcds() throws  {
            return this.aOu.contains(Integer.valueOf(4));
        }

        public Mergedpeoplemetadata zzcit() throws  {
            return this.aZT;
        }

        public String zzcjc() throws  {
            return this.aPi;
        }

        public boolean zzcjd() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }

        public String zzcjf() throws  {
            return this.aPd;
        }

        public boolean zzcjg() throws  {
            return this.aOu.contains(Integer.valueOf(3));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class ExtendedData extends FastSafeParcelableJsonResponse {
        public static final Creator<ExtendedData> CREATOR = new zzw();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        HangoutsExtendedData aZZ;
        List<String> baa;
        final int mVersionCode;

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class HangoutsExtendedData extends FastSafeParcelableJsonResponse {
            public static final Creator<HangoutsExtendedData> CREATOR = new zzx();
            private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
            final Set<Integer> aOu;
            String bab;
            String bac;
            boolean bad;
            boolean bae;
            boolean baf;
            final int mVersionCode;

            static {
                aOt.put("hadPastHangoutState", Field.forString("hadPastHangoutState", 2));
                aOt.put("invitationStatus", Field.forString("invitationStatus", 3));
                aOt.put("isDismissed", Field.forBoolean("isDismissed", 4));
                aOt.put("isFavorite", Field.forBoolean("isFavorite", 5));
                aOt.put("isPinned", Field.forBoolean("isPinned", 6));
            }

            public HangoutsExtendedData() throws  {
                this.mVersionCode = 1;
                this.aOu = new HashSet();
            }

            HangoutsExtendedData(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZ)V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZ)V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZ)V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZ)V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZ)V"}) boolean $z0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZ)V"}) boolean $z1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZ)V"}) boolean $z2) throws  {
                this.aOu = $r1;
                this.mVersionCode = $i0;
                this.bab = $r2;
                this.bac = $r3;
                this.bad = $z0;
                this.bae = $z1;
                this.baf = $z2;
            }

            public boolean equals(Object $r1) throws  {
                if (!($r1 instanceof HangoutsExtendedData)) {
                    return false;
                }
                if (this == $r1) {
                    return true;
                }
                HangoutsExtendedData $r2 = (HangoutsExtendedData) $r1;
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
                        return this.bab;
                    case 3:
                        return this.bac;
                    case 4:
                        return Boolean.valueOf(this.bad);
                    case 5:
                        return Boolean.valueOf(this.bae);
                    case 6:
                        return Boolean.valueOf(this.baf);
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
                        this.bad = $z0;
                        break;
                    case 5:
                        this.bae = $z0;
                        break;
                    case 6:
                        this.baf = $z0;
                        break;
                    default:
                        throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a boolean.");
                }
                this.aOu.add(Integer.valueOf($i0));
            }

            protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
                int $i0 = $r1.getSafeParcelableFieldId();
                switch ($i0) {
                    case 2:
                        this.bab = $r3;
                        break;
                    case 3:
                        this.bac = $r3;
                        break;
                    default:
                        throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
                }
                this.aOu.add(Integer.valueOf($i0));
            }

            public void writeToParcel(Parcel $r1, int $i0) throws  {
                zzx.zza(this, $r1, $i0);
            }
        }

        static {
            aOt.put("hangoutsExtendedData", Field.forConcreteType("hangoutsExtendedData", 2, HangoutsExtendedData.class));
            aOt.put("hd", Field.forStrings("hd", 3));
        }

        public ExtendedData() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        ExtendedData(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData$HangoutsExtendedData;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData$HangoutsExtendedData;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData$HangoutsExtendedData;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) HangoutsExtendedData $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData$HangoutsExtendedData;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r3) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aZZ = $r2;
            this.baa = $r3;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.aZZ = (HangoutsExtendedData) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof ExtendedData)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            ExtendedData $r2 = (ExtendedData) $r1;
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
                    return this.aZZ;
                case 3:
                    return this.baa;
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
                    this.baa = $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be an array of String.");
            }
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzw.zza(this, $r1, $i0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class ExternalIds extends FastSafeParcelableJsonResponse {
        public static final Creator<ExternalIds> CREATOR = new zzy();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        String aPd;
        Mergedpeoplemetadata aZT;
        String mValue;
        final int mVersionCode;
        String zzcft;

        static {
            aOt.put("formattedType", Field.forString("formattedType", 2));
            aOt.put("metadata", Field.forConcreteType("metadata", 3, Mergedpeoplemetadata.class));
            aOt.put("type", Field.forString("type", 4));
            aOt.put("value", Field.forString("value", 5));
        }

        public ExternalIds() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        ExternalIds(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r5) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aPd = $r2;
            this.aZT = $r3;
            this.zzcft = $r4;
            this.mValue = $r5;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 3:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof ExternalIds)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            ExternalIds $r2 = (ExternalIds) $r1;
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
                    return this.aPd;
                case 3:
                    return this.aZT;
                case 4:
                    return this.zzcft;
                case 5:
                    return this.mValue;
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

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected void setStringInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r10) throws  {
            /*
            r7 = this;
            r0 = r8.getSafeParcelableFieldId();
            switch(r0) {
                case 2: goto L_0x0029;
                case 3: goto L_0x0008;
                case 4: goto L_0x0035;
                case 5: goto L_0x0038;
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
            r7.aPd = r10;
        L_0x002b:
            r5 = r7.aOu;
            r6 = java.lang.Integer.valueOf(r0);
            r5.add(r6);
            return;
        L_0x0035:
            r7.zzcft = r10;
            goto L_0x002b;
        L_0x0038:
            r7.mValue = r10;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.ExternalIds.setStringInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.lang.String):void");
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzy.zza(this, $r1, $i0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Genders extends FastSafeParcelableJsonResponse {
        public static final Creator<Genders> CREATOR = new zzz();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        Mergedpeoplemetadata aZT;
        String aiv;
        String mValue;
        final int mVersionCode;

        static {
            aOt.put("formattedValue", Field.forString("formattedValue", 3));
            aOt.put("metadata", Field.forConcreteType("metadata", 4, Mergedpeoplemetadata.class));
            aOt.put("value", Field.forString("value", 5));
        }

        public Genders() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        Genders(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) String $r4) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aiv = $r2;
            this.aZT = $r3;
            this.mValue = $r4;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 4:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Genders)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            Genders $r2 = (Genders) $r1;
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
                case 3:
                    return this.aiv;
                case 4:
                    return this.aZT;
                case 5:
                    return this.mValue;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public String getFormattedValue() throws  {
            return this.aiv;
        }

        public String getValue() throws  {
            return this.mValue;
        }

        public boolean hasValue() throws  {
            return this.aOu.contains(Integer.valueOf(5));
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

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected void setStringInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r10) throws  {
            /*
            r7 = this;
            r0 = r8.getSafeParcelableFieldId();
            switch(r0) {
                case 3: goto L_0x0029;
                case 4: goto L_0x0008;
                case 5: goto L_0x0035;
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
            r7.aiv = r10;
        L_0x002b:
            r5 = r7.aOu;
            r6 = java.lang.Integer.valueOf(r0);
            r5.add(r6);
            return;
        L_0x0035:
            r7.mValue = r10;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Genders.setStringInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.lang.String):void");
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzz.zza(this, $r1, $i0);
        }

        public boolean zzcds() throws  {
            return this.aOu.contains(Integer.valueOf(4));
        }

        public Mergedpeoplemetadata zzcit() throws  {
            return this.aZT;
        }

        public boolean zzcjh() throws  {
            return this.aOu.contains(Integer.valueOf(3));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Images extends FastSafeParcelableJsonResponse {
        public static final Creator<Images> CREATOR = new zzaa();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        boolean aUC;
        Mergedpeoplemetadata aZT;
        String bag;
        final int mVersionCode;
        String zzad;

        static {
            aOt.put("isDefault", Field.forBoolean("isDefault", 2));
            aOt.put("metadata", Field.forConcreteType("metadata", 3, Mergedpeoplemetadata.class));
            aOt.put("photoToken", Field.forString("photoToken", 4));
            aOt.put("url", Field.forString("url", 5));
        }

        public Images() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        Images(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aUC = $z0;
            this.aZT = $r2;
            this.bag = $r3;
            this.zzad = $r4;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 3:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Images)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            Images $r2 = (Images) $r1;
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
                    return Boolean.valueOf(this.aUC);
                case 3:
                    return this.aZT;
                case 4:
                    return this.bag;
                case 5:
                    return this.zzad;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public String getUrl() throws  {
            return this.zzad;
        }

        public boolean hasUrl() throws  {
            return this.aOu.contains(Integer.valueOf(5));
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

        public boolean isDefault() throws  {
            return this.aUC;
        }

        protected boolean isFieldSet(Field $r1) throws  {
            return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
        }

        protected void setBooleanInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) boolean $z0) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.aUC = $z0;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a boolean.");
            }
        }

        protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 4:
                    this.bag = $r3;
                    break;
                case 5:
                    this.zzad = $r3;
                    break;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
            }
            this.aOu.add(Integer.valueOf($i0));
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzaa.zza(this, $r1, $i0);
        }

        public boolean zzcds() throws  {
            return this.aOu.contains(Integer.valueOf(3));
        }

        public Mergedpeoplemetadata zzcit() throws  {
            return this.aZT;
        }

        public boolean zzcje() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class InstantMessaging extends FastSafeParcelableJsonResponse {
        public static final Creator<InstantMessaging> CREATOR = new zzab();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        String aPd;
        String aPl;
        String aPm;
        Mergedpeoplemetadata aZT;
        String mValue;
        final int mVersionCode;
        String zzcft;

        static {
            aOt.put("formattedProtocol", Field.forString("formattedProtocol", 2));
            aOt.put("formattedType", Field.forString("formattedType", 3));
            aOt.put("metadata", Field.forConcreteType("metadata", 4, Mergedpeoplemetadata.class));
            aOt.put("protocol", Field.forString("protocol", 5));
            aOt.put("type", Field.forString("type", 6));
            aOt.put("value", Field.forString("value", 7));
        }

        public InstantMessaging() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        InstantMessaging(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r5, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r6, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r7) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aPl = $r2;
            this.aPd = $r3;
            this.aZT = $r4;
            this.aPm = $r5;
            this.zzcft = $r6;
            this.mValue = $r7;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 4:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof InstantMessaging)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            InstantMessaging $r2 = (InstantMessaging) $r1;
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
                    return this.aPl;
                case 3:
                    return this.aPd;
                case 4:
                    return this.aZT;
                case 5:
                    return this.aPm;
                case 6:
                    return this.zzcft;
                case 7:
                    return this.mValue;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public String getProtocol() throws  {
            return this.aPm;
        }

        public String getType() throws  {
            return this.zzcft;
        }

        public String getValue() throws  {
            return this.mValue;
        }

        public boolean hasType() throws  {
            return this.aOu.contains(Integer.valueOf(6));
        }

        public boolean hasValue() throws  {
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

        protected boolean isFieldSet(Field $r1) throws  {
            return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
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
                case 4: goto L_0x0008;
                case 5: goto L_0x0038;
                case 6: goto L_0x003b;
                case 7: goto L_0x003e;
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
            r7.aPl = r10;
        L_0x002b:
            r5 = r7.aOu;
            r6 = java.lang.Integer.valueOf(r0);
            r5.add(r6);
            return;
        L_0x0035:
            r7.aPd = r10;
            goto L_0x002b;
        L_0x0038:
            r7.aPm = r10;
            goto L_0x002b;
        L_0x003b:
            r7.zzcft = r10;
            goto L_0x002b;
        L_0x003e:
            r7.mValue = r10;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.InstantMessaging.setStringInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.lang.String):void");
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzab.zza(this, $r1, $i0);
        }

        public boolean zzcds() throws  {
            return this.aOu.contains(Integer.valueOf(4));
        }

        public Mergedpeoplemetadata zzcit() throws  {
            return this.aZT;
        }

        public String zzcjf() throws  {
            return this.aPd;
        }

        public boolean zzcjg() throws  {
            return this.aOu.contains(Integer.valueOf(3));
        }

        public String zzcji() throws  {
            return this.aPl;
        }

        public boolean zzcjj() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }

        public boolean zzcjk() throws  {
            return this.aOu.contains(Integer.valueOf(5));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Interests extends FastSafeParcelableJsonResponse {
        public static final Creator<Interests> CREATOR = new zzac();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        Mergedpeoplemetadata aZT;
        String mValue;
        final int mVersionCode;

        static {
            aOt.put("metadata", Field.forConcreteType("metadata", 2, Mergedpeoplemetadata.class));
            aOt.put("value", Field.forString("value", 3));
        }

        public Interests() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        Interests(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aZT = $r2;
            this.mValue = $r3;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Interests)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            Interests $r2 = (Interests) $r1;
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
                    return this.aZT;
                case 3:
                    return this.mValue;
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
                case 3:
                    this.mValue = $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
            }
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzac.zza(this, $r1, $i0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Languages extends FastSafeParcelableJsonResponse {
        public static final Creator<Languages> CREATOR = new zzad();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        Mergedpeoplemetadata aZT;
        String mValue;
        final int mVersionCode;

        static {
            aOt.put("metadata", Field.forConcreteType("metadata", 2, Mergedpeoplemetadata.class));
            aOt.put("value", Field.forString("value", 3));
        }

        public Languages() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        Languages(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aZT = $r2;
            this.mValue = $r3;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Languages)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            Languages $r2 = (Languages) $r1;
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
                    return this.aZT;
                case 3:
                    return this.mValue;
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
                case 3:
                    this.mValue = $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
            }
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzad.zza(this, $r1, $i0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class LegacyFields extends FastSafeParcelableJsonResponse {
        public static final Creator<LegacyFields> CREATOR = new zzae();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        String aPn;
        final int mVersionCode;

        static {
            aOt.put("mobileOwnerId", Field.forString("mobileOwnerId", 2));
        }

        public LegacyFields() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        LegacyFields(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", ")V"}) String $r2) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aPn = $r2;
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof LegacyFields)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            LegacyFields $r2 = (LegacyFields) $r1;
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
                    return this.aPn;
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
                    this.aPn = $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
            }
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzae.zza(this, $r1, $i0);
        }

        public String zzcjl() throws  {
            return this.aPn;
        }

        public boolean zzcjm() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Memberships extends FastSafeParcelableJsonResponse {
        public static final Creator<Memberships> CREATOR = new zzaf();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        String aPo;
        String aPp;
        String aPq;
        Mergedpeoplemetadata aZT;
        final int mVersionCode;

        static {
            aOt.put("circle", Field.forString("circle", 2));
            aOt.put("contactGroup", Field.forString("contactGroup", 3));
            aOt.put("metadata", Field.forConcreteType("metadata", 4, Mergedpeoplemetadata.class));
            aOt.put("systemContactGroup", Field.forString("systemContactGroup", 5));
        }

        public Memberships() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        Memberships(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) String $r5) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aPo = $r2;
            this.aPp = $r3;
            this.aZT = $r4;
            this.aPq = $r5;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 4:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Memberships)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            Memberships $r2 = (Memberships) $r1;
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
                    return this.aPo;
                case 3:
                    return this.aPp;
                case 4:
                    return this.aZT;
                case 5:
                    return this.aPq;
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

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected void setStringInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r10) throws  {
            /*
            r7 = this;
            r0 = r8.getSafeParcelableFieldId();
            switch(r0) {
                case 2: goto L_0x0029;
                case 3: goto L_0x0035;
                case 4: goto L_0x0008;
                case 5: goto L_0x0038;
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
            r7.aPo = r10;
        L_0x002b:
            r5 = r7.aOu;
            r6 = java.lang.Integer.valueOf(r0);
            r5.add(r6);
            return;
        L_0x0035:
            r7.aPp = r10;
            goto L_0x002b;
        L_0x0038:
            r7.aPq = r10;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Memberships.setStringInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.lang.String):void");
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzaf.zza(this, $r1, $i0);
        }

        public boolean zzcds() throws  {
            return this.aOu.contains(Integer.valueOf(4));
        }

        public Mergedpeoplemetadata zzcit() throws  {
            return this.aZT;
        }

        public String zzcjn() throws  {
            return this.aPo;
        }

        public boolean zzcjo() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }

        public String zzcjp() throws  {
            return this.aPp;
        }

        public boolean zzcjq() throws  {
            return this.aOu.contains(Integer.valueOf(3));
        }

        public String zzcjr() throws  {
            return this.aPq;
        }

        public boolean zzcjs() throws  {
            return this.aOu.contains(Integer.valueOf(5));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Metadata extends FastSafeParcelableJsonResponse {
        public static final Creator<Metadata> CREATOR;
        private static final HashMap<String, Field<?, ?>> aOt;
        List<String> aMB;
        final Set<Integer> aOu;
        List<String> aPO;
        List<String> aPP;
        List<String> aPQ;
        List<String> aPR;
        String aPS;
        List<String> aPT;
        String aPU;
        boolean aPW;
        boolean aPX;
        boolean aPY;
        List<Mergedpeopleaffinities> aZD;
        String aaX;
        List<String> axZ;
        String bah;
        IdentityInfo bai;
        long baj;
        ProfileOwnerStats bak;
        final int mVersionCode;

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class IdentityInfo extends FastSafeParcelableJsonResponse {
            public static final Creator<IdentityInfo> CREATOR;
            private static final HashMap<String, Field<?, ?>> aOt;
            final Set<Integer> aOu;
            List<String> bal;
            List<SourceIds> bam;
            final int mVersionCode;

            /* compiled from: dalvik_source_com.waze.apk */
            public static final class SourceIds extends FastSafeParcelableJsonResponse {
                public static final Creator<SourceIds> CREATOR;
                private static final HashMap<String, Field<?, ?>> aOt;
                String aOD;
                final Set<Integer> aOu;
                boolean aPX;
                String aPr;
                String ban;
                long bao;
                final int mVersionCode;
                String zzbgd;

                static {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r0 = new com.google.android.gms.plus.service.v2whitelisted.models.zzai;
                    r0.<init>();
                    CREATOR = r0;
                    r1 = new java.util.HashMap;
                    r1.<init>();
                    aOt = r1;
                    r1 = aOt;
                    r3 = "container";
                    r4 = 2;
                    r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forString(r3, r4);
                    r3 = "container";
                    r1.put(r3, r2);
                    r1 = aOt;
                    r3 = "deleted";
                    r4 = 3;
                    r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forBoolean(r3, r4);
                    r3 = "deleted";
                    r1.put(r3, r2);
                    r1 = aOt;
                    r3 = "etag";
                    r4 = 4;
                    r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forString(r3, r4);
                    r3 = "etag";
                    r1.put(r3, r2);
                    r1 = aOt;
                    r3 = "id";
                    r4 = 5;
                    r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forString(r3, r4);
                    r3 = "id";
                    r1.put(r3, r2);
                    r1 = aOt;
                    r3 = "lastUpdated";
                    r4 = 6;
                    r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forString(r3, r4);
                    r3 = "lastUpdated";
                    r1.put(r3, r2);
                    r1 = aOt;
                    r3 = "lastUpdatedMicros";
                    r4 = 7;
                    r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forLong(r3, r4);
                    r3 = "lastUpdatedMicros";
                    r1.put(r3, r2);
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.SourceIds.<clinit>():void");
                }

                public SourceIds() throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r2 = this;
                    r2.<init>();
                    r0 = 1;
                    r2.mVersionCode = r0;
                    r1 = new java.util.HashSet;
                    r1.<init>();
                    r2.aOu = r1;
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.SourceIds.<init>():void");
                }

                SourceIds(@dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "J)V"}) java.util.Set<java.lang.Integer> r1, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "J)V"}) int r2, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "J)V"}) java.lang.String r3, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "J)V"}) boolean r4, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "J)V"}) java.lang.String r5, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "J)V"}) java.lang.String r6, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "J)V"}) java.lang.String r7, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "J)V"}) long r8) throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r0 = this;
                    r0.<init>();
                    r0.aOu = r1;
                    r0.mVersionCode = r2;
                    r0.aPr = r3;
                    r0.aPX = r4;
                    r0.aOD = r5;
                    r0.zzbgd = r6;
                    r0.ban = r7;
                    r0.bao = r8;
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.SourceIds.<init>(java.util.Set, int, java.lang.String, boolean, java.lang.String, java.lang.String, java.lang.String, long):void");
                }

                public boolean equals(java.lang.Object r11) throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r10 = this;
                    r0 = r11 instanceof com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.SourceIds;
                    if (r0 != 0) goto L_0x0006;
                L_0x0004:
                    r1 = 0;
                    return r1;
                L_0x0006:
                    if (r10 != r11) goto L_0x000a;
                L_0x0008:
                    r1 = 1;
                    return r1;
                L_0x000a:
                    r3 = r11;
                    r3 = (com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.SourceIds) r3;
                    r2 = r3;
                    r4 = aOt;
                    r5 = r4.values();
                    r6 = r5.iterator();
                L_0x0018:
                    r0 = r6.hasNext();
                    if (r0 == 0) goto L_0x004c;
                L_0x001e:
                    r11 = r6.next();
                    r8 = r11;
                    r8 = (com.google.android.gms.common.server.response.FastJsonResponse.Field) r8;
                    r7 = r8;
                    r0 = r10.isFieldSet(r7);
                    if (r0 == 0) goto L_0x0044;
                L_0x002c:
                    r0 = r2.isFieldSet(r7);
                    if (r0 == 0) goto L_0x0042;
                L_0x0032:
                    r11 = r10.getFieldValue(r7);
                    r9 = r2.getFieldValue(r7);
                    r0 = r11.equals(r9);
                    if (r0 != 0) goto L_0x0018;
                L_0x0040:
                    r1 = 0;
                    return r1;
                L_0x0042:
                    r1 = 0;
                    return r1;
                L_0x0044:
                    r0 = r2.isFieldSet(r7);
                    if (r0 == 0) goto L_0x0018;
                L_0x004a:
                    r1 = 0;
                    return r1;
                L_0x004c:
                    r1 = 1;
                    return r1;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.SourceIds.equals(java.lang.Object):boolean");
                }

                public java.util.HashMap<java.lang.String, com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?>> getFieldMappings() throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r1 = this;
                    r0 = aOt;
                    return r0;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.SourceIds.getFieldMappings():java.util.HashMap<java.lang.String, com.google.android.gms.common.server.response.FastJsonResponse$Field<?, ?>>");
                }

                protected java.lang.Object getFieldValue(com.google.android.gms.common.server.response.FastJsonResponse.Field r12) throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r11 = this;
                    r0 = r12.getSafeParcelableFieldId();
                    switch(r0) {
                        case 2: goto L_0x0027;
                        case 3: goto L_0x002a;
                        case 4: goto L_0x0031;
                        case 5: goto L_0x0034;
                        case 6: goto L_0x0037;
                        case 7: goto L_0x003a;
                        default: goto L_0x0007;
                    };
                L_0x0007:
                    goto L_0x0008;
                L_0x0008:
                    r1 = new java.lang.IllegalStateException;
                    r0 = r12.getSafeParcelableFieldId();
                    r2 = new java.lang.StringBuilder;
                    r3 = 38;
                    r2.<init>(r3);
                    r4 = "Unknown safe parcelable id=";
                    r2 = r2.append(r4);
                    r2 = r2.append(r0);
                    r5 = r2.toString();
                    r1.<init>(r5);
                    throw r1;
                L_0x0027:
                    r5 = r11.aPr;
                    return r5;
                L_0x002a:
                    r6 = r11.aPX;
                    r7 = java.lang.Boolean.valueOf(r6);
                    return r7;
                L_0x0031:
                    r5 = r11.aOD;
                    return r5;
                L_0x0034:
                    r5 = r11.zzbgd;
                    return r5;
                L_0x0037:
                    r5 = r11.ban;
                    return r5;
                L_0x003a:
                    r8 = r11.bao;
                    r10 = java.lang.Long.valueOf(r8);
                    return r10;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.SourceIds.getFieldValue(com.google.android.gms.common.server.response.FastJsonResponse$Field):java.lang.Object");
                }

                public int hashCode() throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r9 = this;
                    r0 = aOt;
                    r1 = r0.values();
                    r2 = r1.iterator();
                    r3 = 0;
                L_0x000b:
                    r4 = r2.hasNext();
                    if (r4 == 0) goto L_0x002f;
                L_0x0011:
                    r5 = r2.next();
                    r7 = r5;
                    r7 = (com.google.android.gms.common.server.response.FastJsonResponse.Field) r7;
                    r6 = r7;
                    r4 = r9.isFieldSet(r6);
                    if (r4 == 0) goto L_0x0030;
                L_0x001f:
                    r8 = r6.getSafeParcelableFieldId();
                    r3 = r3 + r8;
                    r5 = r9.getFieldValue(r6);
                    r8 = r5.hashCode();
                    r3 = r8 + r3;
                L_0x002e:
                    goto L_0x000b;
                L_0x002f:
                    return r3;
                L_0x0030:
                    goto L_0x002e;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.SourceIds.hashCode():int");
                }

                protected boolean isFieldSet(com.google.android.gms.common.server.response.FastJsonResponse.Field r5) throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r4 = this;
                    r0 = r4.aOu;
                    r1 = r5.getSafeParcelableFieldId();
                    r2 = java.lang.Integer.valueOf(r1);
                    r3 = r0.contains(r2);
                    return r3;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.SourceIds.isFieldSet(com.google.android.gms.common.server.response.FastJsonResponse$Field):boolean");
                }

                protected void setBooleanInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) boolean r10) throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r7 = this;
                    r0 = r8.getSafeParcelableFieldId();
                    switch(r0) {
                        case 3: goto L_0x0029;
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
                    r7.aPX = r10;
                    r5 = r7.aOu;
                    r6 = java.lang.Integer.valueOf(r0);
                    r5.add(r6);
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.SourceIds.setBooleanInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, boolean):void");
                }

                protected void setLongInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "J)V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "J)V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "J)V"}) long r10) throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r7 = this;
                    r0 = r8.getSafeParcelableFieldId();
                    switch(r0) {
                        case 7: goto L_0x0029;
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
                    r4 = " is not known to be a long.";
                    r2 = r2.append(r4);
                    r9 = r2.toString();
                    r1.<init>(r9);
                    throw r1;
                L_0x0029:
                    r7.bao = r10;
                    r5 = r7.aOu;
                    r6 = java.lang.Integer.valueOf(r0);
                    r5.add(r6);
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.SourceIds.setLongInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, long):void");
                }

                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                protected void setStringInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r10) throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r7 = this;
                    r0 = r8.getSafeParcelableFieldId();
                    switch(r0) {
                        case 2: goto L_0x0029;
                        case 3: goto L_0x0008;
                        case 4: goto L_0x0035;
                        case 5: goto L_0x0038;
                        case 6: goto L_0x003b;
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
                    r7.aPr = r10;
                L_0x002b:
                    r5 = r7.aOu;
                    r6 = java.lang.Integer.valueOf(r0);
                    r5.add(r6);
                    return;
                L_0x0035:
                    r7.aOD = r10;
                    goto L_0x002b;
                L_0x0038:
                    r7.zzbgd = r10;
                    goto L_0x002b;
                L_0x003b:
                    r7.ban = r10;
                    goto L_0x002b;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.SourceIds.setStringInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.lang.String):void");
                }

                public void writeToParcel(android.os.Parcel r1, int r2) throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r0 = this;
                    com.google.android.gms.plus.service.v2whitelisted.models.zzai.zza(r0, r1, r2);
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.SourceIds.writeToParcel(android.os.Parcel, int):void");
                }
            }

            static {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r0 = new com.google.android.gms.plus.service.v2whitelisted.models.zzah;
                r0.<init>();
                CREATOR = r0;
                r1 = new java.util.HashMap;
                r1.<init>();
                aOt = r1;
                r1 = aOt;
                r3 = "originalLookupToken";
                r4 = 2;
                r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forStrings(r3, r4);
                r3 = "originalLookupToken";
                r1.put(r3, r2);
                r1 = aOt;
                r3 = "sourceIds";
                r4 = 3;
                r5 = com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.SourceIds.class;
                r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forConcreteTypeArray(r3, r4, r5);
                r3 = "sourceIds";
                r1.put(r3, r2);
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.<clinit>():void");
            }

            public IdentityInfo() throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r2 = this;
                r2.<init>();
                r0 = 1;
                r2.mVersionCode = r0;
                r1 = new java.util.HashSet;
                r1.<init>();
                r2.aOu = r1;
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.<init>():void");
            }

            IdentityInfo(@dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo$SourceIds;", ">;)V"}) java.util.Set<java.lang.Integer> r1, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo$SourceIds;", ">;)V"}) int r2, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo$SourceIds;", ">;)V"}) java.util.List<java.lang.String> r3, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo$SourceIds;", ">;)V"}) java.util.List<com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.SourceIds> r4) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r0 = this;
                r0.<init>();
                r0.aOu = r1;
                r0.mVersionCode = r2;
                r0.bal = r3;
                r0.bam = r4;
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.<init>(java.util.Set, int, java.util.List, java.util.List):void");
            }

            public <T extends com.google.android.gms.common.server.response.FastJsonResponse> void addConcreteTypeArrayInternal(@dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r10, @dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) java.lang.String r11, @dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) java.util.ArrayList<T> r12) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r9 = this;
                r0 = r10.getSafeParcelableFieldId();
                switch(r0) {
                    case 3: goto L_0x0047;
                    default: goto L_0x0007;
                };
            L_0x0007:
                goto L_0x0008;
            L_0x0008:
                r1 = new java.lang.IllegalArgumentException;
                r2 = r12.getClass();
                r11 = r2.getCanonicalName();
                r11 = java.lang.String.valueOf(r11);
                r3 = new java.lang.StringBuilder;
                r4 = java.lang.String.valueOf(r11);
                r5 = r4.length();
                r5 = r5 + 71;
                r3.<init>(r5);
                r6 = "Field with id=";
                r3 = r3.append(r6);
                r3 = r3.append(r0);
                r6 = " is not a known array of custom type.  Found ";
                r3 = r3.append(r6);
                r3 = r3.append(r11);
                r6 = ".";
                r3 = r3.append(r6);
                r11 = r3.toString();
                r1.<init>(r11);
                throw r1;
            L_0x0047:
                r9.bam = r12;
                r7 = r9.aOu;
                r8 = java.lang.Integer.valueOf(r0);
                r7.add(r8);
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.addConcreteTypeArrayInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.util.ArrayList):void");
            }

            public boolean equals(java.lang.Object r11) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r10 = this;
                r0 = r11 instanceof com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo;
                if (r0 != 0) goto L_0x0006;
            L_0x0004:
                r1 = 0;
                return r1;
            L_0x0006:
                if (r10 != r11) goto L_0x000a;
            L_0x0008:
                r1 = 1;
                return r1;
            L_0x000a:
                r3 = r11;
                r3 = (com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo) r3;
                r2 = r3;
                r4 = aOt;
                r5 = r4.values();
                r6 = r5.iterator();
            L_0x0018:
                r0 = r6.hasNext();
                if (r0 == 0) goto L_0x004c;
            L_0x001e:
                r11 = r6.next();
                r8 = r11;
                r8 = (com.google.android.gms.common.server.response.FastJsonResponse.Field) r8;
                r7 = r8;
                r0 = r10.isFieldSet(r7);
                if (r0 == 0) goto L_0x0044;
            L_0x002c:
                r0 = r2.isFieldSet(r7);
                if (r0 == 0) goto L_0x0042;
            L_0x0032:
                r11 = r10.getFieldValue(r7);
                r9 = r2.getFieldValue(r7);
                r0 = r11.equals(r9);
                if (r0 != 0) goto L_0x0018;
            L_0x0040:
                r1 = 0;
                return r1;
            L_0x0042:
                r1 = 0;
                return r1;
            L_0x0044:
                r0 = r2.isFieldSet(r7);
                if (r0 == 0) goto L_0x0018;
            L_0x004a:
                r1 = 0;
                return r1;
            L_0x004c:
                r1 = 1;
                return r1;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.equals(java.lang.Object):boolean");
            }

            public java.util.HashMap<java.lang.String, com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?>> getFieldMappings() throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r1 = this;
                r0 = aOt;
                return r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.getFieldMappings():java.util.HashMap<java.lang.String, com.google.android.gms.common.server.response.FastJsonResponse$Field<?, ?>>");
            }

            protected java.lang.Object getFieldValue(com.google.android.gms.common.server.response.FastJsonResponse.Field r8) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r7 = this;
                r0 = r8.getSafeParcelableFieldId();
                switch(r0) {
                    case 2: goto L_0x0027;
                    case 3: goto L_0x002a;
                    default: goto L_0x0007;
                };
            L_0x0007:
                goto L_0x0008;
            L_0x0008:
                r1 = new java.lang.IllegalStateException;
                r0 = r8.getSafeParcelableFieldId();
                r2 = new java.lang.StringBuilder;
                r3 = 38;
                r2.<init>(r3);
                r4 = "Unknown safe parcelable id=";
                r2 = r2.append(r4);
                r2 = r2.append(r0);
                r5 = r2.toString();
                r1.<init>(r5);
                throw r1;
            L_0x0027:
                r6 = r7.bal;
                return r6;
            L_0x002a:
                r6 = r7.bam;
                return r6;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.getFieldValue(com.google.android.gms.common.server.response.FastJsonResponse$Field):java.lang.Object");
            }

            public int hashCode() throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r9 = this;
                r0 = aOt;
                r1 = r0.values();
                r2 = r1.iterator();
                r3 = 0;
            L_0x000b:
                r4 = r2.hasNext();
                if (r4 == 0) goto L_0x002f;
            L_0x0011:
                r5 = r2.next();
                r7 = r5;
                r7 = (com.google.android.gms.common.server.response.FastJsonResponse.Field) r7;
                r6 = r7;
                r4 = r9.isFieldSet(r6);
                if (r4 == 0) goto L_0x0030;
            L_0x001f:
                r8 = r6.getSafeParcelableFieldId();
                r3 = r3 + r8;
                r5 = r9.getFieldValue(r6);
                r8 = r5.hashCode();
                r3 = r8 + r3;
            L_0x002e:
                goto L_0x000b;
            L_0x002f:
                return r3;
            L_0x0030:
                goto L_0x002e;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.hashCode():int");
            }

            protected boolean isFieldSet(com.google.android.gms.common.server.response.FastJsonResponse.Field r5) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r4 = this;
                r0 = r4.aOu;
                r1 = r5.getSafeParcelableFieldId();
                r2 = java.lang.Integer.valueOf(r1);
                r3 = r0.contains(r2);
                return r3;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.isFieldSet(com.google.android.gms.common.server.response.FastJsonResponse$Field):boolean");
            }

            protected void setStringsInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) java.util.ArrayList<java.lang.String> r10) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r7 = this;
                r0 = r8.getSafeParcelableFieldId();
                switch(r0) {
                    case 2: goto L_0x0029;
                    default: goto L_0x0007;
                };
            L_0x0007:
                goto L_0x0008;
            L_0x0008:
                r1 = new java.lang.IllegalArgumentException;
                r2 = new java.lang.StringBuilder;
                r3 = 64;
                r2.<init>(r3);
                r4 = "Field with id=";
                r2 = r2.append(r4);
                r2 = r2.append(r0);
                r4 = " is not known to be an array of String.";
                r2 = r2.append(r4);
                r9 = r2.toString();
                r1.<init>(r9);
                throw r1;
            L_0x0029:
                r7.bal = r10;
                r5 = r7.aOu;
                r6 = java.lang.Integer.valueOf(r0);
                r5.add(r6);
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.setStringsInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.util.ArrayList):void");
            }

            public void writeToParcel(android.os.Parcel r1, int r2) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r0 = this;
                com.google.android.gms.plus.service.v2whitelisted.models.zzah.zza(r0, r1, r2);
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.writeToParcel(android.os.Parcel, int):void");
            }
        }

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class ProfileOwnerStats extends FastSafeParcelableJsonResponse {
            public static final Creator<ProfileOwnerStats> CREATOR;
            private static final HashMap<String, Field<?, ?>> aOt;
            final Set<Integer> aOu;
            long aQa;
            long aQb;
            final int mVersionCode;

            static {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r0 = new com.google.android.gms.plus.service.v2whitelisted.models.zzaj;
                r0.<init>();
                CREATOR = r0;
                r1 = new java.util.HashMap;
                r1.<init>();
                aOt = r1;
                r1 = aOt;
                r3 = "incomingAnyCircleCount";
                r4 = 2;
                r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forLong(r3, r4);
                r3 = "incomingAnyCircleCount";
                r1.put(r3, r2);
                r1 = aOt;
                r3 = "viewCount";
                r4 = 3;
                r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forLong(r3, r4);
                r3 = "viewCount";
                r1.put(r3, r2);
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats.<clinit>():void");
            }

            public ProfileOwnerStats() throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r2 = this;
                r2.<init>();
                r0 = 1;
                r2.mVersionCode = r0;
                r1 = new java.util.HashSet;
                r1.<init>();
                r2.aOu = r1;
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats.<init>():void");
            }

            ProfileOwnerStats(@dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IJJ)V"}) java.util.Set<java.lang.Integer> r1, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IJJ)V"}) int r2, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IJJ)V"}) long r3, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IJJ)V"}) long r5) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r0 = this;
                r0.<init>();
                r0.aOu = r1;
                r0.mVersionCode = r2;
                r0.aQa = r3;
                r0.aQb = r5;
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats.<init>(java.util.Set, int, long, long):void");
            }

            public boolean equals(java.lang.Object r11) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r10 = this;
                r0 = r11 instanceof com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats;
                if (r0 != 0) goto L_0x0006;
            L_0x0004:
                r1 = 0;
                return r1;
            L_0x0006:
                if (r10 != r11) goto L_0x000a;
            L_0x0008:
                r1 = 1;
                return r1;
            L_0x000a:
                r3 = r11;
                r3 = (com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats) r3;
                r2 = r3;
                r4 = aOt;
                r5 = r4.values();
                r6 = r5.iterator();
            L_0x0018:
                r0 = r6.hasNext();
                if (r0 == 0) goto L_0x004c;
            L_0x001e:
                r11 = r6.next();
                r8 = r11;
                r8 = (com.google.android.gms.common.server.response.FastJsonResponse.Field) r8;
                r7 = r8;
                r0 = r10.isFieldSet(r7);
                if (r0 == 0) goto L_0x0044;
            L_0x002c:
                r0 = r2.isFieldSet(r7);
                if (r0 == 0) goto L_0x0042;
            L_0x0032:
                r11 = r10.getFieldValue(r7);
                r9 = r2.getFieldValue(r7);
                r0 = r11.equals(r9);
                if (r0 != 0) goto L_0x0018;
            L_0x0040:
                r1 = 0;
                return r1;
            L_0x0042:
                r1 = 0;
                return r1;
            L_0x0044:
                r0 = r2.isFieldSet(r7);
                if (r0 == 0) goto L_0x0018;
            L_0x004a:
                r1 = 0;
                return r1;
            L_0x004c:
                r1 = 1;
                return r1;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats.equals(java.lang.Object):boolean");
            }

            public java.util.HashMap<java.lang.String, com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?>> getFieldMappings() throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r1 = this;
                r0 = aOt;
                return r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats.getFieldMappings():java.util.HashMap<java.lang.String, com.google.android.gms.common.server.response.FastJsonResponse$Field<?, ?>>");
            }

            protected java.lang.Object getFieldValue(com.google.android.gms.common.server.response.FastJsonResponse.Field r10) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r9 = this;
                r0 = r10.getSafeParcelableFieldId();
                switch(r0) {
                    case 2: goto L_0x0027;
                    case 3: goto L_0x002e;
                    default: goto L_0x0007;
                };
            L_0x0007:
                goto L_0x0008;
            L_0x0008:
                r1 = new java.lang.IllegalStateException;
                r0 = r10.getSafeParcelableFieldId();
                r2 = new java.lang.StringBuilder;
                r3 = 38;
                r2.<init>(r3);
                r4 = "Unknown safe parcelable id=";
                r2 = r2.append(r4);
                r2 = r2.append(r0);
                r5 = r2.toString();
                r1.<init>(r5);
                throw r1;
            L_0x0027:
                r6 = r9.aQa;
                r8 = java.lang.Long.valueOf(r6);
                return r8;
            L_0x002e:
                r6 = r9.aQb;
                r8 = java.lang.Long.valueOf(r6);
                return r8;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats.getFieldValue(com.google.android.gms.common.server.response.FastJsonResponse$Field):java.lang.Object");
            }

            public int hashCode() throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r9 = this;
                r0 = aOt;
                r1 = r0.values();
                r2 = r1.iterator();
                r3 = 0;
            L_0x000b:
                r4 = r2.hasNext();
                if (r4 == 0) goto L_0x002f;
            L_0x0011:
                r5 = r2.next();
                r7 = r5;
                r7 = (com.google.android.gms.common.server.response.FastJsonResponse.Field) r7;
                r6 = r7;
                r4 = r9.isFieldSet(r6);
                if (r4 == 0) goto L_0x0030;
            L_0x001f:
                r8 = r6.getSafeParcelableFieldId();
                r3 = r3 + r8;
                r5 = r9.getFieldValue(r6);
                r8 = r5.hashCode();
                r3 = r8 + r3;
            L_0x002e:
                goto L_0x000b;
            L_0x002f:
                return r3;
            L_0x0030:
                goto L_0x002e;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats.hashCode():int");
            }

            protected boolean isFieldSet(com.google.android.gms.common.server.response.FastJsonResponse.Field r5) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r4 = this;
                r0 = r4.aOu;
                r1 = r5.getSafeParcelableFieldId();
                r2 = java.lang.Integer.valueOf(r1);
                r3 = r0.contains(r2);
                return r3;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats.isFieldSet(com.google.android.gms.common.server.response.FastJsonResponse$Field):boolean");
            }

            protected void setLongInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "J)V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "J)V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "J)V"}) long r10) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r7 = this;
                r0 = r8.getSafeParcelableFieldId();
                switch(r0) {
                    case 2: goto L_0x0029;
                    case 3: goto L_0x0035;
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
                r4 = " is not known to be a long.";
                r2 = r2.append(r4);
                r9 = r2.toString();
                r1.<init>(r9);
                throw r1;
            L_0x0029:
                r7.aQa = r10;
            L_0x002b:
                r5 = r7.aOu;
                r6 = java.lang.Integer.valueOf(r0);
                r5.add(r6);
                return;
            L_0x0035:
                r7.aQb = r10;
                goto L_0x002b;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats.setLongInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, long):void");
            }

            public void writeToParcel(android.os.Parcel r1, int r2) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r0 = this;
                com.google.android.gms.plus.service.v2whitelisted.models.zzaj.zza(r0, r1, r2);
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats.writeToParcel(android.os.Parcel, int):void");
            }

            public long zzckq() throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r2 = this;
                r0 = r2.aQa;
                return r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats.zzckq():long");
            }

            public boolean zzckr() throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r4 = this;
                r0 = r4.aOu;
                r2 = 2;
                r1 = java.lang.Integer.valueOf(r2);
                r3 = r0.contains(r1);
                return r3;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats.zzckr():boolean");
            }

            public long zzcks() throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r2 = this;
                r0 = r2.aQb;
                return r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats.zzcks():long");
            }

            public boolean zzckt() throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r4 = this;
                r0 = r4.aOu;
                r2 = 3;
                r1 = java.lang.Integer.valueOf(r2);
                r3 = r0.contains(r1);
                return r3;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats.zzckt():boolean");
            }
        }

        static {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r0 = new com.google.android.gms.plus.service.v2whitelisted.models.zzag;
            r0.<init>();
            CREATOR = r0;
            r1 = new java.util.HashMap;
            r1.<init>();
            aOt = r1;
            r1 = aOt;
            r3 = "affinities";
            r4 = 2;
            r5 = com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeopleaffinities.class;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forConcreteTypeArray(r3, r4, r5);
            r3 = "affinities";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "attributions";
            r4 = 3;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forStrings(r3, r4);
            r3 = "attributions";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "blockTypes";
            r4 = 4;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forStrings(r3, r4);
            r3 = "blockTypes";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "blocked";
            r4 = 5;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forBoolean(r3, r4);
            r3 = "blocked";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "circles";
            r4 = 6;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forStrings(r3, r4);
            r3 = "circles";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "contacts";
            r4 = 7;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forStrings(r3, r4);
            r3 = "contacts";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "customResponseMaskingType";
            r4 = 8;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forString(r3, r4);
            r3 = "customResponseMaskingType";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "deleted";
            r4 = 9;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forBoolean(r3, r4);
            r3 = "deleted";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "groups";
            r4 = 10;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forStrings(r3, r4);
            r3 = "groups";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "identityInfo";
            r4 = 11;
            r5 = com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo.class;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forConcreteType(r3, r4, r5);
            r3 = "identityInfo";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "inViewerDomain";
            r4 = 12;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forBoolean(r3, r4);
            r3 = "inViewerDomain";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "incomingBlockTypes";
            r4 = 13;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forStrings(r3, r4);
            r3 = "incomingBlockTypes";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "lastUpdateTimeMicros";
            r4 = 14;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forLong(r3, r4);
            r3 = "lastUpdateTimeMicros";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "objectType";
            r4 = 15;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forString(r3, r4);
            r3 = "objectType";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "ownerId";
            r4 = 16;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forString(r3, r4);
            r3 = "ownerId";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "ownerUserTypes";
            r4 = 17;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forStrings(r3, r4);
            r3 = "ownerUserTypes";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "plusPageType";
            r4 = 18;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forString(r3, r4);
            r3 = "plusPageType";
            r1.put(r3, r2);
            r1 = aOt;
            r3 = "profileOwnerStats";
            r4 = 19;
            r5 = com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats.class;
            r2 = com.google.android.gms.common.server.response.FastJsonResponse.Field.forConcreteType(r3, r4, r5);
            r3 = "profileOwnerStats";
            r1.put(r3, r2);
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.<clinit>():void");
        }

        public Metadata() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r2 = this;
            r2.<init>();
            r0 = 1;
            r2.mVersionCode = r0;
            r1 = new java.util.HashSet;
            r1.<init>();
            r2.aOu = r1;
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.<init>():void");
        }

        Metadata(@dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) java.util.Set<java.lang.Integer> r3, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) int r4, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) java.util.List<com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeopleaffinities> r5, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) java.util.List<java.lang.String> r6, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) java.util.List<java.lang.String> r7, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) boolean r8, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) java.util.List<java.lang.String> r9, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) java.util.List<java.lang.String> r10, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) java.lang.String r11, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) boolean r12, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) java.util.List<java.lang.String> r13, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo r14, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) boolean r15, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) java.util.List<java.lang.String> r16, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) long r17, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) java.lang.String r19, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) java.lang.String r20, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) java.util.List<java.lang.String> r21, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) java.lang.String r22, @dalvik.annotation.Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$IdentityInfo;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata$ProfileOwnerStats;", ")V"}) com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats r23) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r2 = this;
            r2.<init>();
            r2.aOu = r3;
            r2.mVersionCode = r4;
            r2.aZD = r5;
            r2.axZ = r6;
            r2.aPO = r7;
            r2.aPW = r8;
            r2.aMB = r9;
            r2.aPP = r10;
            r2.bah = r11;
            r2.aPX = r12;
            r2.aPQ = r13;
            r2.bai = r14;
            r2.aPY = r15;
            r0 = r16;
            r2.aPR = r0;
            r0 = r17;
            r2.baj = r0;
            r0 = r19;
            r2.aaX = r0;
            r0 = r20;
            r2.aPS = r0;
            r0 = r21;
            r2.aPT = r0;
            r0 = r22;
            r2.aPU = r0;
            r0 = r23;
            r2.bak = r0;
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.<init>(java.util.Set, int, java.util.List, java.util.List, java.util.List, boolean, java.util.List, java.util.List, java.lang.String, boolean, java.util.List, com.google.android.gms.plus.service.v2whitelisted.models.Person$Metadata$IdentityInfo, boolean, java.util.List, long, java.lang.String, java.lang.String, java.util.List, java.lang.String, com.google.android.gms.plus.service.v2whitelisted.models.Person$Metadata$ProfileOwnerStats):void");
        }

        public <T extends com.google.android.gms.common.server.response.FastJsonResponse> void addConcreteTypeArrayInternal(@dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r10, @dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) java.lang.String r11, @dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) java.util.ArrayList<T> r12) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r9 = this;
            r0 = r10.getSafeParcelableFieldId();
            switch(r0) {
                case 2: goto L_0x0047;
                default: goto L_0x0007;
            };
        L_0x0007:
            goto L_0x0008;
        L_0x0008:
            r1 = new java.lang.IllegalArgumentException;
            r2 = r12.getClass();
            r11 = r2.getCanonicalName();
            r11 = java.lang.String.valueOf(r11);
            r3 = new java.lang.StringBuilder;
            r4 = java.lang.String.valueOf(r11);
            r5 = r4.length();
            r5 = r5 + 71;
            r3.<init>(r5);
            r6 = "Field with id=";
            r3 = r3.append(r6);
            r3 = r3.append(r0);
            r6 = " is not a known array of custom type.  Found ";
            r3 = r3.append(r6);
            r3 = r3.append(r11);
            r6 = ".";
            r3 = r3.append(r6);
            r11 = r3.toString();
            r1.<init>(r11);
            throw r1;
        L_0x0047:
            r9.aZD = r12;
            r7 = r9.aOu;
            r8 = java.lang.Integer.valueOf(r0);
            r7.add(r8);
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.addConcreteTypeArrayInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.util.ArrayList):void");
        }

        public <T extends com.google.android.gms.common.server.response.FastJsonResponse> void addConcreteTypeInternal(@dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r15, @dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) java.lang.String r16, @dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T r17) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r14 = this;
            r1 = r15.getSafeParcelableFieldId();
            switch(r1) {
                case 11: goto L_0x0051;
                case 19: goto L_0x0062;
                default: goto L_0x0007;
            };
        L_0x0007:
            goto L_0x0008;
        L_0x0008:
            r2 = new java.lang.IllegalArgumentException;
            r0 = r17;
            r3 = r0.getClass();
            r16 = r3.getCanonicalName();
            r0 = r16;
            r16 = java.lang.String.valueOf(r0);
            r4 = new java.lang.StringBuilder;
            r0 = r16;
            r5 = java.lang.String.valueOf(r0);
            r6 = r5.length();
            r6 = r6 + 62;
            r4.<init>(r6);
            r7 = "Field with id=";
            r4 = r4.append(r7);
            r4 = r4.append(r1);
            r7 = " is not a known custom type.  Found ";
            r4 = r4.append(r7);
            r0 = r16;
            r4 = r4.append(r0);
            r7 = ".";
            r4 = r4.append(r7);
            r16 = r4.toString();
            r0 = r16;
            r2.<init>(r0);
            throw r2;
        L_0x0051:
            r9 = r17;
            r9 = (com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.IdentityInfo) r9;
            r8 = r9;
            r14.bai = r8;
        L_0x0058:
            r10 = r14.aOu;
            r11 = java.lang.Integer.valueOf(r1);
            r10.add(r11);
            return;
        L_0x0062:
            r13 = r17;
            r13 = (com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats) r13;
            r12 = r13;
            r14.bak = r12;
            goto L_0x0058;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.addConcreteTypeInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, com.google.android.gms.common.server.response.FastJsonResponse):void");
        }

        public boolean equals(java.lang.Object r11) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r10 = this;
            r0 = r11 instanceof com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata;
            if (r0 != 0) goto L_0x0006;
        L_0x0004:
            r1 = 0;
            return r1;
        L_0x0006:
            if (r10 != r11) goto L_0x000a;
        L_0x0008:
            r1 = 1;
            return r1;
        L_0x000a:
            r3 = r11;
            r3 = (com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata) r3;
            r2 = r3;
            r4 = aOt;
            r5 = r4.values();
            r6 = r5.iterator();
        L_0x0018:
            r0 = r6.hasNext();
            if (r0 == 0) goto L_0x004c;
        L_0x001e:
            r11 = r6.next();
            r8 = r11;
            r8 = (com.google.android.gms.common.server.response.FastJsonResponse.Field) r8;
            r7 = r8;
            r0 = r10.isFieldSet(r7);
            if (r0 == 0) goto L_0x0044;
        L_0x002c:
            r0 = r2.isFieldSet(r7);
            if (r0 == 0) goto L_0x0042;
        L_0x0032:
            r11 = r10.getFieldValue(r7);
            r9 = r2.getFieldValue(r7);
            r0 = r11.equals(r9);
            if (r0 != 0) goto L_0x0018;
        L_0x0040:
            r1 = 0;
            return r1;
        L_0x0042:
            r1 = 0;
            return r1;
        L_0x0044:
            r0 = r2.isFieldSet(r7);
            if (r0 == 0) goto L_0x0018;
        L_0x004a:
            r1 = 0;
            return r1;
        L_0x004c:
            r1 = 1;
            return r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.equals(java.lang.Object):boolean");
        }

        public java.util.List<java.lang.String> getCircles() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = r1.aMB;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.getCircles():java.util.List<java.lang.String>");
        }

        public java.util.HashMap<java.lang.String, com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?>> getFieldMappings() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = aOt;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.getFieldMappings():java.util.HashMap<java.lang.String, com.google.android.gms.common.server.response.FastJsonResponse$Field<?, ?>>");
        }

        protected java.lang.Object getFieldValue(com.google.android.gms.common.server.response.FastJsonResponse.Field r15) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r14 = this;
            r0 = r15.getSafeParcelableFieldId();
            switch(r0) {
                case 2: goto L_0x0027;
                case 3: goto L_0x002a;
                case 4: goto L_0x002d;
                case 5: goto L_0x0030;
                case 6: goto L_0x0037;
                case 7: goto L_0x003a;
                case 8: goto L_0x003d;
                case 9: goto L_0x0040;
                case 10: goto L_0x0047;
                case 11: goto L_0x004a;
                case 12: goto L_0x004d;
                case 13: goto L_0x0054;
                case 14: goto L_0x0057;
                case 15: goto L_0x005e;
                case 16: goto L_0x0061;
                case 17: goto L_0x0064;
                case 18: goto L_0x0067;
                case 19: goto L_0x006a;
                default: goto L_0x0007;
            };
        L_0x0007:
            goto L_0x0008;
        L_0x0008:
            r1 = new java.lang.IllegalStateException;
            r0 = r15.getSafeParcelableFieldId();
            r2 = new java.lang.StringBuilder;
            r3 = 38;
            r2.<init>(r3);
            r4 = "Unknown safe parcelable id=";
            r2 = r2.append(r4);
            r2 = r2.append(r0);
            r5 = r2.toString();
            r1.<init>(r5);
            throw r1;
        L_0x0027:
            r6 = r14.aZD;
            return r6;
        L_0x002a:
            r6 = r14.axZ;
            return r6;
        L_0x002d:
            r6 = r14.aPO;
            return r6;
        L_0x0030:
            r7 = r14.aPW;
            r8 = java.lang.Boolean.valueOf(r7);
            return r8;
        L_0x0037:
            r6 = r14.aMB;
            return r6;
        L_0x003a:
            r6 = r14.aPP;
            return r6;
        L_0x003d:
            r5 = r14.bah;
            return r5;
        L_0x0040:
            r7 = r14.aPX;
            r8 = java.lang.Boolean.valueOf(r7);
            return r8;
        L_0x0047:
            r6 = r14.aPQ;
            return r6;
        L_0x004a:
            r9 = r14.bai;
            return r9;
        L_0x004d:
            r7 = r14.aPY;
            r8 = java.lang.Boolean.valueOf(r7);
            return r8;
        L_0x0054:
            r6 = r14.aPR;
            return r6;
        L_0x0057:
            r10 = r14.baj;
            r12 = java.lang.Long.valueOf(r10);
            return r12;
        L_0x005e:
            r5 = r14.aaX;
            return r5;
        L_0x0061:
            r5 = r14.aPS;
            return r5;
        L_0x0064:
            r6 = r14.aPT;
            return r6;
        L_0x0067:
            r5 = r14.aPU;
            return r5;
        L_0x006a:
            r13 = r14.bak;
            return r13;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.getFieldValue(com.google.android.gms.common.server.response.FastJsonResponse$Field):java.lang.Object");
        }

        public java.lang.String getOwnerId() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = r1.aPS;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.getOwnerId():java.lang.String");
        }

        public boolean hasObjectType() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r2 = 15;
            r1 = java.lang.Integer.valueOf(r2);
            r3 = r0.contains(r1);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.hasObjectType():boolean");
        }

        public int hashCode() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r9 = this;
            r0 = aOt;
            r1 = r0.values();
            r2 = r1.iterator();
            r3 = 0;
        L_0x000b:
            r4 = r2.hasNext();
            if (r4 == 0) goto L_0x002f;
        L_0x0011:
            r5 = r2.next();
            r7 = r5;
            r7 = (com.google.android.gms.common.server.response.FastJsonResponse.Field) r7;
            r6 = r7;
            r4 = r9.isFieldSet(r6);
            if (r4 == 0) goto L_0x0030;
        L_0x001f:
            r8 = r6.getSafeParcelableFieldId();
            r3 = r3 + r8;
            r5 = r9.getFieldValue(r6);
            r8 = r5.hashCode();
            r3 = r8 + r3;
        L_0x002e:
            goto L_0x000b;
        L_0x002f:
            return r3;
        L_0x0030:
            goto L_0x002e;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.hashCode():int");
        }

        public boolean isBlocked() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = r1.aPW;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.isBlocked():boolean");
        }

        protected boolean isFieldSet(com.google.android.gms.common.server.response.FastJsonResponse.Field r5) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r1 = r5.getSafeParcelableFieldId();
            r2 = java.lang.Integer.valueOf(r1);
            r3 = r0.contains(r2);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.isFieldSet(com.google.android.gms.common.server.response.FastJsonResponse$Field):boolean");
        }

        protected void setBooleanInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) boolean r10) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r7 = this;
            r0 = r8.getSafeParcelableFieldId();
            switch(r0) {
                case 5: goto L_0x0029;
                case 9: goto L_0x0035;
                case 12: goto L_0x0038;
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
            r7.aPW = r10;
        L_0x002b:
            r5 = r7.aOu;
            r6 = java.lang.Integer.valueOf(r0);
            r5.add(r6);
            return;
        L_0x0035:
            r7.aPX = r10;
            goto L_0x002b;
        L_0x0038:
            r7.aPY = r10;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.setBooleanInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, boolean):void");
        }

        protected void setLongInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "J)V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "J)V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "J)V"}) long r10) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r7 = this;
            r0 = r8.getSafeParcelableFieldId();
            switch(r0) {
                case 14: goto L_0x0029;
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
            r4 = " is not known to be a long.";
            r2 = r2.append(r4);
            r9 = r2.toString();
            r1.<init>(r9);
            throw r1;
        L_0x0029:
            r7.baj = r10;
            r5 = r7.aOu;
            r6 = java.lang.Integer.valueOf(r0);
            r5.add(r6);
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.setLongInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, long):void");
        }

        protected void setStringInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r10) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r7 = this;
            r0 = r8.getSafeParcelableFieldId();
            switch(r0) {
                case 8: goto L_0x0029;
                case 15: goto L_0x0035;
                case 16: goto L_0x0038;
                case 18: goto L_0x003b;
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
            r7.bah = r10;
        L_0x002b:
            r5 = r7.aOu;
            r6 = java.lang.Integer.valueOf(r0);
            r5.add(r6);
            return;
        L_0x0035:
            r7.aaX = r10;
            goto L_0x002b;
        L_0x0038:
            r7.aPS = r10;
            goto L_0x002b;
        L_0x003b:
            r7.aPU = r10;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.setStringInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.lang.String):void");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected void setStringsInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) java.util.ArrayList<java.lang.String> r10) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r7 = this;
            r0 = r8.getSafeParcelableFieldId();
            switch(r0) {
                case 3: goto L_0x0029;
                case 4: goto L_0x0035;
                case 5: goto L_0x0008;
                case 6: goto L_0x0038;
                case 7: goto L_0x003b;
                case 8: goto L_0x0008;
                case 9: goto L_0x0008;
                case 10: goto L_0x003e;
                case 11: goto L_0x0008;
                case 12: goto L_0x0008;
                case 13: goto L_0x0041;
                case 14: goto L_0x0008;
                case 15: goto L_0x0008;
                case 16: goto L_0x0008;
                case 17: goto L_0x0044;
                default: goto L_0x0007;
            };
        L_0x0007:
            goto L_0x0008;
        L_0x0008:
            r1 = new java.lang.IllegalArgumentException;
            r2 = new java.lang.StringBuilder;
            r3 = 64;
            r2.<init>(r3);
            r4 = "Field with id=";
            r2 = r2.append(r4);
            r2 = r2.append(r0);
            r4 = " is not known to be an array of String.";
            r2 = r2.append(r4);
            r9 = r2.toString();
            r1.<init>(r9);
            throw r1;
        L_0x0029:
            r7.axZ = r10;
        L_0x002b:
            r5 = r7.aOu;
            r6 = java.lang.Integer.valueOf(r0);
            r5.add(r6);
            return;
        L_0x0035:
            r7.aPO = r10;
            goto L_0x002b;
        L_0x0038:
            r7.aMB = r10;
            goto L_0x002b;
        L_0x003b:
            r7.aPP = r10;
            goto L_0x002b;
        L_0x003e:
            r7.aPQ = r10;
            goto L_0x002b;
        L_0x0041:
            r7.aPR = r10;
            goto L_0x002b;
        L_0x0044:
            r7.aPT = r10;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.setStringsInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.util.ArrayList):void");
        }

        public void writeToParcel(android.os.Parcel r1, int r2) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r0 = this;
            com.google.android.gms.plus.service.v2whitelisted.models.zzag.zza(r0, r1, r2);
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.writeToParcel(android.os.Parcel, int):void");
        }

        public java.lang.String zzbid() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = r1.aaX;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzbid():java.lang.String");
        }

        public java.util.List<java.lang.String> zzcjt() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = r1.axZ;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzcjt():java.util.List<java.lang.String>");
        }

        public boolean zzcju() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r2 = 3;
            r1 = java.lang.Integer.valueOf(r2);
            r3 = r0.contains(r1);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzcju():boolean");
        }

        public java.util.List<java.lang.String> zzcjv() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = r1.aPO;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzcjv():java.util.List<java.lang.String>");
        }

        public boolean zzcjw() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r2 = 4;
            r1 = java.lang.Integer.valueOf(r2);
            r3 = r0.contains(r1);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzcjw():boolean");
        }

        public boolean zzcjx() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r2 = 5;
            r1 = java.lang.Integer.valueOf(r2);
            r3 = r0.contains(r1);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzcjx():boolean");
        }

        public boolean zzcjy() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r2 = 6;
            r1 = java.lang.Integer.valueOf(r2);
            r3 = r0.contains(r1);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzcjy():boolean");
        }

        public java.util.List<java.lang.String> zzcjz() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = r1.aPP;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzcjz():java.util.List<java.lang.String>");
        }

        public boolean zzcka() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r2 = 7;
            r1 = java.lang.Integer.valueOf(r2);
            r3 = r0.contains(r1);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzcka():boolean");
        }

        public boolean zzckb() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = r1.aPX;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzckb():boolean");
        }

        public boolean zzckc() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r2 = 9;
            r1 = java.lang.Integer.valueOf(r2);
            r3 = r0.contains(r1);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzckc():boolean");
        }

        public java.util.List<java.lang.String> zzckd() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = r1.aPQ;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzckd():java.util.List<java.lang.String>");
        }

        public boolean zzcke() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r2 = 10;
            r1 = java.lang.Integer.valueOf(r2);
            r3 = r0.contains(r1);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzcke():boolean");
        }

        public boolean zzckf() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = r1.aPY;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzckf():boolean");
        }

        public boolean zzckg() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r2 = 12;
            r1 = java.lang.Integer.valueOf(r2);
            r3 = r0.contains(r1);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzckg():boolean");
        }

        public java.util.List<java.lang.String> zzckh() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = r1.aPR;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzckh():java.util.List<java.lang.String>");
        }

        public boolean zzcki() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r2 = 13;
            r1 = java.lang.Integer.valueOf(r2);
            r3 = r0.contains(r1);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzcki():boolean");
        }

        public boolean zzckj() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r2 = 16;
            r1 = java.lang.Integer.valueOf(r2);
            r3 = r0.contains(r1);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzckj():boolean");
        }

        public java.util.List<java.lang.String> zzckk() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = r1.aPT;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzckk():java.util.List<java.lang.String>");
        }

        public boolean zzckl() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r2 = 17;
            r1 = java.lang.Integer.valueOf(r2);
            r3 = r0.contains(r1);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzckl():boolean");
        }

        public java.lang.String zzckm() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = r1.aPU;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzckm():java.lang.String");
        }

        public boolean zzckn() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r2 = 18;
            r1 = java.lang.Integer.valueOf(r2);
            r3 = r0.contains(r1);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzckn():boolean");
        }

        public com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats zzcko() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = r1.bak;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzcko():com.google.android.gms.plus.service.v2whitelisted.models.Person$Metadata$ProfileOwnerStats");
        }

        public boolean zzckp() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.aOu;
            r2 = 19;
            r1 = java.lang.Integer.valueOf(r2);
            r3 = r0.contains(r1);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.zzckp():boolean");
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Names extends FastSafeParcelableJsonResponse {
        public static final Creator<Names> CREATOR = new zzak();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        String aPA;
        String aPB;
        String aPC;
        String aPD;
        String aPE;
        String aPF;
        String aPG;
        String aPz;
        Mergedpeoplemetadata aZT;
        String bap;
        String cr;
        String fB;
        String fC;
        final int mVersionCode;

        static {
            aOt.put("displayName", Field.forString("displayName", 2));
            aOt.put("familyName", Field.forString("familyName", 3));
            aOt.put("formatted", Field.forString("formatted", 4));
            aOt.put("givenName", Field.forString("givenName", 5));
            aOt.put("honorificPrefix", Field.forString("honorificPrefix", 6));
            aOt.put("honorificSuffix", Field.forString("honorificSuffix", 7));
            aOt.put("metadata", Field.forConcreteType("metadata", 8, Mergedpeoplemetadata.class));
            aOt.put("middleName", Field.forString("middleName", 9));
            aOt.put("phoneticFamilyName", Field.forString("phoneticFamilyName", 10));
            aOt.put("phoneticGivenName", Field.forString("phoneticGivenName", 11));
            aOt.put("phoneticHonorificPrefix", Field.forString("phoneticHonorificPrefix", 12));
            aOt.put("phoneticHonorificSuffix", Field.forString("phoneticHonorificSuffix", 13));
            aOt.put("phoneticMiddleName", Field.forString("phoneticMiddleName", 14));
        }

        public Names() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        Names(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r5, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r6, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r7, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r8, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r9, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r10, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r11, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r12, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r13, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r14) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.cr = $r2;
            this.fC = $r3;
            this.aPz = $r4;
            this.fB = $r5;
            this.aPA = $r6;
            this.aPB = $r7;
            this.aZT = $r8;
            this.aPC = $r9;
            this.aPD = $r10;
            this.aPE = $r11;
            this.aPF = $r12;
            this.aPG = $r13;
            this.bap = $r14;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 8:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Names)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            Names $r2 = (Names) $r1;
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

        public String getDisplayName() throws  {
            return this.cr;
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
                    return this.cr;
                case 3:
                    return this.fC;
                case 4:
                    return this.aPz;
                case 5:
                    return this.fB;
                case 6:
                    return this.aPA;
                case 7:
                    return this.aPB;
                case 8:
                    return this.aZT;
                case 9:
                    return this.aPC;
                case 10:
                    return this.aPD;
                case 11:
                    return this.aPE;
                case 12:
                    return this.aPF;
                case 13:
                    return this.aPG;
                case 14:
                    return this.bap;
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

        public boolean hasDisplayName() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }

        public boolean hasFamilyName() throws  {
            return this.aOu.contains(Integer.valueOf(3));
        }

        public boolean hasFormatted() throws  {
            return this.aOu.contains(Integer.valueOf(4));
        }

        public boolean hasGivenName() throws  {
            return this.aOu.contains(Integer.valueOf(5));
        }

        public boolean hasHonorificPrefix() throws  {
            return this.aOu.contains(Integer.valueOf(6));
        }

        public boolean hasHonorificSuffix() throws  {
            return this.aOu.contains(Integer.valueOf(7));
        }

        public boolean hasMiddleName() throws  {
            return this.aOu.contains(Integer.valueOf(9));
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
                case 7: goto L_0x0041;
                case 8: goto L_0x0008;
                case 9: goto L_0x0044;
                case 10: goto L_0x0047;
                case 11: goto L_0x004a;
                case 12: goto L_0x004d;
                case 13: goto L_0x0050;
                case 14: goto L_0x0053;
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
            r7.cr = r10;
        L_0x002b:
            r5 = r7.aOu;
            r6 = java.lang.Integer.valueOf(r0);
            r5.add(r6);
            return;
        L_0x0035:
            r7.fC = r10;
            goto L_0x002b;
        L_0x0038:
            r7.aPz = r10;
            goto L_0x002b;
        L_0x003b:
            r7.fB = r10;
            goto L_0x002b;
        L_0x003e:
            r7.aPA = r10;
            goto L_0x002b;
        L_0x0041:
            r7.aPB = r10;
            goto L_0x002b;
        L_0x0044:
            r7.aPC = r10;
            goto L_0x002b;
        L_0x0047:
            r7.aPD = r10;
            goto L_0x002b;
        L_0x004a:
            r7.aPE = r10;
            goto L_0x002b;
        L_0x004d:
            r7.aPF = r10;
            goto L_0x002b;
        L_0x0050:
            r7.aPG = r10;
            goto L_0x002b;
        L_0x0053:
            r7.bap = r10;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Names.setStringInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.lang.String):void");
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzak.zza(this, $r1, $i0);
        }

        public boolean zzcds() throws  {
            return this.aOu.contains(Integer.valueOf(8));
        }

        public Mergedpeoplemetadata zzcit() throws  {
            return this.aZT;
        }

        public String zzcku() throws  {
            return this.aPD;
        }

        public boolean zzckv() throws  {
            return this.aOu.contains(Integer.valueOf(10));
        }

        public String zzckw() throws  {
            return this.aPE;
        }

        public boolean zzckx() throws  {
            return this.aOu.contains(Integer.valueOf(11));
        }

        public String zzcky() throws  {
            return this.aPF;
        }

        public boolean zzckz() throws  {
            return this.aOu.contains(Integer.valueOf(12));
        }

        public String zzcla() throws  {
            return this.aPG;
        }

        public boolean zzclb() throws  {
            return this.aOu.contains(Integer.valueOf(13));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Nicknames extends FastSafeParcelableJsonResponse {
        public static final Creator<Nicknames> CREATOR = new zzal();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        Mergedpeoplemetadata aZT;
        String mValue;
        final int mVersionCode;
        String zzcft;

        static {
            aOt.put("metadata", Field.forConcreteType("metadata", 2, Mergedpeoplemetadata.class));
            aOt.put("type", Field.forString("type", 3));
            aOt.put("value", Field.forString("value", 4));
        }

        public Nicknames() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        Nicknames(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aZT = $r2;
            this.zzcft = $r3;
            this.mValue = $r4;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Nicknames)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            Nicknames $r2 = (Nicknames) $r1;
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
                    return this.aZT;
                case 3:
                    return this.zzcft;
                case 4:
                    return this.mValue;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public String getType() throws  {
            return this.zzcft;
        }

        public String getValue() throws  {
            return this.mValue;
        }

        public boolean hasType() throws  {
            return this.aOu.contains(Integer.valueOf(3));
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

        protected boolean isFieldSet(Field $r1) throws  {
            return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
        }

        protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 3:
                    this.zzcft = $r3;
                    break;
                case 4:
                    this.mValue = $r3;
                    break;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
            }
            this.aOu.add(Integer.valueOf($i0));
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzal.zza(this, $r1, $i0);
        }

        public boolean zzcds() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }

        public Mergedpeoplemetadata zzcit() throws  {
            return this.aZT;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Occupations extends FastSafeParcelableJsonResponse {
        public static final Creator<Occupations> CREATOR = new zzam();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        Mergedpeoplemetadata aZT;
        String mValue;
        final int mVersionCode;

        static {
            aOt.put("metadata", Field.forConcreteType("metadata", 2, Mergedpeoplemetadata.class));
            aOt.put("value", Field.forString("value", 3));
        }

        public Occupations() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        Occupations(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aZT = $r2;
            this.mValue = $r3;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Occupations)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            Occupations $r2 = (Occupations) $r1;
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
                    return this.aZT;
                case 3:
                    return this.mValue;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public String getValue() throws  {
            return this.mValue;
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

        protected boolean isFieldSet(Field $r1) throws  {
            return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
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
            zzam.zza(this, $r1, $i0);
        }

        public boolean zzcds() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }

        public Mergedpeoplemetadata zzcit() throws  {
            return this.aZT;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Organizations extends FastSafeParcelableJsonResponse {
        public static final Creator<Organizations> CREATOR = new zzan();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        String Sm;
        final Set<Integer> aOu;
        String aOw;
        boolean aPH;
        String aPI;
        String aPJ;
        String aPK;
        String aPL;
        String aPM;
        String aPN;
        Mergedpeoplemetadata aZT;
        String mDescription;
        String mName;
        final int mVersionCode;
        String zzcft;

        static {
            aOt.put("current", Field.forBoolean("current", 2));
            aOt.put("department", Field.forString("department", 3));
            aOt.put("description", Field.forString("description", 4));
            aOt.put("domain", Field.forString("domain", 5));
            aOt.put("endDate", Field.forString("endDate", 6));
            aOt.put(WLTypes.VEHICLEDATA_ATTRIBUTE_LOCATION, Field.forString(WLTypes.VEHICLEDATA_ATTRIBUTE_LOCATION, 8));
            aOt.put("metadata", Field.forConcreteType("metadata", 9, Mergedpeoplemetadata.class));
            aOt.put("name", Field.forString("name", 10));
            aOt.put("phoneticName", Field.forString("phoneticName", 11));
            aOt.put("startDate", Field.forString("startDate", 12));
            aOt.put("symbol", Field.forString("symbol", 14));
            aOt.put("title", Field.forString("title", 15));
            aOt.put("type", Field.forString("type", 16));
        }

        public Organizations() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        Organizations(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r5, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r6, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r7, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r8, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r9, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r10, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r11, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r12, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r13) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aPH = $z0;
            this.aPI = $r2;
            this.mDescription = $r3;
            this.aPJ = $r4;
            this.aPK = $r5;
            this.aOw = $r6;
            this.aZT = $r7;
            this.mName = $r8;
            this.aPL = $r9;
            this.aPM = $r10;
            this.aPN = $r11;
            this.Sm = $r12;
            this.zzcft = $r13;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 9:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Organizations)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            Organizations $r2 = (Organizations) $r1;
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

        public String getDepartment() throws  {
            return this.aPI;
        }

        public String getDescription() throws  {
            return this.mDescription;
        }

        public String getDomain() throws  {
            return this.aPJ;
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
                    return Boolean.valueOf(this.aPH);
                case 3:
                    return this.aPI;
                case 4:
                    return this.mDescription;
                case 5:
                    return this.aPJ;
                case 6:
                    return this.aPK;
                case 7:
                case 13:
                    break;
                case 8:
                    return this.aOw;
                case 9:
                    return this.aZT;
                case 10:
                    return this.mName;
                case 11:
                    return this.aPL;
                case 12:
                    return this.aPM;
                case 14:
                    return this.aPN;
                case 15:
                    return this.Sm;
                case 16:
                    return this.zzcft;
                default:
                    break;
            }
            throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
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

        public String getSymbol() throws  {
            return this.aPN;
        }

        public String getTitle() throws  {
            return this.Sm;
        }

        public String getType() throws  {
            return this.zzcft;
        }

        public boolean hasDepartment() throws  {
            return this.aOu.contains(Integer.valueOf(3));
        }

        public boolean hasDescription() throws  {
            return this.aOu.contains(Integer.valueOf(4));
        }

        public boolean hasEndDate() throws  {
            return this.aOu.contains(Integer.valueOf(6));
        }

        public boolean hasLocation() throws  {
            return this.aOu.contains(Integer.valueOf(8));
        }

        public boolean hasName() throws  {
            return this.aOu.contains(Integer.valueOf(10));
        }

        public boolean hasStartDate() throws  {
            return this.aOu.contains(Integer.valueOf(12));
        }

        public boolean hasTitle() throws  {
            return this.aOu.contains(Integer.valueOf(15));
        }

        public boolean hasType() throws  {
            return this.aOu.contains(Integer.valueOf(16));
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
                    this.aPH = $z0;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a boolean.");
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected void setStringInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r10) throws  {
            /*
            r7 = this;
            r0 = r8.getSafeParcelableFieldId();
            switch(r0) {
                case 3: goto L_0x0029;
                case 4: goto L_0x0035;
                case 5: goto L_0x0038;
                case 6: goto L_0x003b;
                case 7: goto L_0x0008;
                case 8: goto L_0x003e;
                case 9: goto L_0x0008;
                case 10: goto L_0x0041;
                case 11: goto L_0x0044;
                case 12: goto L_0x0047;
                case 13: goto L_0x0008;
                case 14: goto L_0x004a;
                case 15: goto L_0x004d;
                case 16: goto L_0x0050;
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
            r7.aPJ = r10;
            goto L_0x002b;
        L_0x003b:
            r7.aPK = r10;
            goto L_0x002b;
        L_0x003e:
            r7.aOw = r10;
            goto L_0x002b;
        L_0x0041:
            r7.mName = r10;
            goto L_0x002b;
        L_0x0044:
            r7.aPL = r10;
            goto L_0x002b;
        L_0x0047:
            r7.aPM = r10;
            goto L_0x002b;
        L_0x004a:
            r7.aPN = r10;
            goto L_0x002b;
        L_0x004d:
            r7.Sm = r10;
            goto L_0x002b;
        L_0x0050:
            r7.zzcft = r10;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Organizations.setStringInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.lang.String):void");
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzan.zza(this, $r1, $i0);
        }

        public boolean zzcds() throws  {
            return this.aOu.contains(Integer.valueOf(9));
        }

        public Mergedpeoplemetadata zzcit() throws  {
            return this.aZT;
        }

        public boolean zzclc() throws  {
            return this.aPH;
        }

        public boolean zzcld() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }

        public boolean zzcle() throws  {
            return this.aOu.contains(Integer.valueOf(5));
        }

        public String zzclf() throws  {
            return this.aPL;
        }

        public boolean zzclg() throws  {
            return this.aOu.contains(Integer.valueOf(11));
        }

        public boolean zzclh() throws  {
            return this.aOu.contains(Integer.valueOf(14));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class OtherKeywords extends FastSafeParcelableJsonResponse {
        public static final Creator<OtherKeywords> CREATOR = new zzao();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        Mergedpeoplemetadata aZT;
        String mValue;
        final int mVersionCode;
        String zzcft;

        static {
            aOt.put("metadata", Field.forConcreteType("metadata", 2, Mergedpeoplemetadata.class));
            aOt.put("type", Field.forString("type", 3));
            aOt.put("value", Field.forString("value", 4));
        }

        public OtherKeywords() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        OtherKeywords(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aZT = $r2;
            this.zzcft = $r3;
            this.mValue = $r4;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof OtherKeywords)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            OtherKeywords $r2 = (OtherKeywords) $r1;
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
                    return this.aZT;
                case 3:
                    return this.zzcft;
                case 4:
                    return this.mValue;
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
                case 3:
                    this.zzcft = $r3;
                    break;
                case 4:
                    this.mValue = $r3;
                    break;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
            }
            this.aOu.add(Integer.valueOf($i0));
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzao.zza(this, $r1, $i0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class PhoneNumbers extends FastSafeParcelableJsonResponse {
        public static final Creator<PhoneNumbers> CREATOR = new zzap();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        String aPZ;
        String aPd;
        Mergedpeoplemetadata aZT;
        String mValue;
        final int mVersionCode;
        String zzcft;

        static {
            aOt.put("canonicalizedForm", Field.forString("canonicalizedForm", 2));
            aOt.put("formattedType", Field.forString("formattedType", 4));
            aOt.put("metadata", Field.forConcreteType("metadata", 5, Mergedpeoplemetadata.class));
            aOt.put("type", Field.forString("type", 6));
            aOt.put("value", Field.forString("value", 8));
        }

        public PhoneNumbers() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        PhoneNumbers(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r5, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r6) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aPZ = $r2;
            this.aPd = $r3;
            this.aZT = $r4;
            this.zzcft = $r5;
            this.mValue = $r6;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 5:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof PhoneNumbers)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            PhoneNumbers $r2 = (PhoneNumbers) $r1;
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
                    return this.aPZ;
                case 3:
                case 7:
                    break;
                case 4:
                    return this.aPd;
                case 5:
                    return this.aZT;
                case 6:
                    return this.zzcft;
                case 8:
                    return this.mValue;
                default:
                    break;
            }
            throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
        }

        public String getType() throws  {
            return this.zzcft;
        }

        public String getValue() throws  {
            return this.mValue;
        }

        public boolean hasType() throws  {
            return this.aOu.contains(Integer.valueOf(6));
        }

        public boolean hasValue() throws  {
            return this.aOu.contains(Integer.valueOf(8));
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

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected void setStringInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r10) throws  {
            /*
            r7 = this;
            r0 = r8.getSafeParcelableFieldId();
            switch(r0) {
                case 2: goto L_0x0029;
                case 3: goto L_0x0008;
                case 4: goto L_0x0035;
                case 5: goto L_0x0008;
                case 6: goto L_0x0038;
                case 7: goto L_0x0008;
                case 8: goto L_0x003b;
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
            r7.aPZ = r10;
        L_0x002b:
            r5 = r7.aOu;
            r6 = java.lang.Integer.valueOf(r0);
            r5.add(r6);
            return;
        L_0x0035:
            r7.aPd = r10;
            goto L_0x002b;
        L_0x0038:
            r7.zzcft = r10;
            goto L_0x002b;
        L_0x003b:
            r7.mValue = r10;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.PhoneNumbers.setStringInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.lang.String):void");
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzap.zza(this, $r1, $i0);
        }

        public boolean zzcds() throws  {
            return this.aOu.contains(Integer.valueOf(5));
        }

        public Mergedpeoplemetadata zzcit() throws  {
            return this.aZT;
        }

        public String zzcjf() throws  {
            return this.aPd;
        }

        public boolean zzcjg() throws  {
            return this.aOu.contains(Integer.valueOf(4));
        }

        public String zzcli() throws  {
            return this.aPZ;
        }

        public boolean zzclj() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class PlacesLived extends FastSafeParcelableJsonResponse {
        public static final Creator<PlacesLived> CREATOR = new zzaq();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        boolean aPH;
        Mergedpeoplemetadata aZT;
        String mValue;
        final int mVersionCode;

        static {
            aOt.put("current", Field.forBoolean("current", 2));
            aOt.put("metadata", Field.forConcreteType("metadata", 3, Mergedpeoplemetadata.class));
            aOt.put("value", Field.forString("value", 4));
        }

        public PlacesLived() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        PlacesLived(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) boolean $z0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;IZ", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aPH = $z0;
            this.aZT = $r2;
            this.mValue = $r3;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 3:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof PlacesLived)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            PlacesLived $r2 = (PlacesLived) $r1;
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
                    return Boolean.valueOf(this.aPH);
                case 3:
                    return this.aZT;
                case 4:
                    return this.mValue;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public String getValue() throws  {
            return this.mValue;
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

        protected boolean isFieldSet(Field $r1) throws  {
            return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
        }

        protected void setBooleanInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) boolean $z0) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.aPH = $z0;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a boolean.");
            }
        }

        protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 4:
                    this.mValue = $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
            }
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzaq.zza(this, $r1, $i0);
        }

        public boolean zzcds() throws  {
            return this.aOu.contains(Integer.valueOf(3));
        }

        public Mergedpeoplemetadata zzcit() throws  {
            return this.aZT;
        }

        public boolean zzclc() throws  {
            return this.aPH;
        }

        public boolean zzcld() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Relations extends FastSafeParcelableJsonResponse {
        public static final Creator<Relations> CREATOR = new zzar();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        String aPd;
        Mergedpeoplemetadata aZT;
        String mValue;
        final int mVersionCode;
        String zzcft;

        static {
            aOt.put("formattedType", Field.forString("formattedType", 2));
            aOt.put("metadata", Field.forConcreteType("metadata", 3, Mergedpeoplemetadata.class));
            aOt.put("type", Field.forString("type", 4));
            aOt.put("value", Field.forString("value", 5));
        }

        public Relations() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        Relations(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r5) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aPd = $r2;
            this.aZT = $r3;
            this.zzcft = $r4;
            this.mValue = $r5;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 3:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Relations)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            Relations $r2 = (Relations) $r1;
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
                    return this.aPd;
                case 3:
                    return this.aZT;
                case 4:
                    return this.zzcft;
                case 5:
                    return this.mValue;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public String getType() throws  {
            return this.zzcft;
        }

        public String getValue() throws  {
            return this.mValue;
        }

        public boolean hasType() throws  {
            return this.aOu.contains(Integer.valueOf(4));
        }

        public boolean hasValue() throws  {
            return this.aOu.contains(Integer.valueOf(5));
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

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected void setStringInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r10) throws  {
            /*
            r7 = this;
            r0 = r8.getSafeParcelableFieldId();
            switch(r0) {
                case 2: goto L_0x0029;
                case 3: goto L_0x0008;
                case 4: goto L_0x0035;
                case 5: goto L_0x0038;
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
            r7.aPd = r10;
        L_0x002b:
            r5 = r7.aOu;
            r6 = java.lang.Integer.valueOf(r0);
            r5.add(r6);
            return;
        L_0x0035:
            r7.zzcft = r10;
            goto L_0x002b;
        L_0x0038:
            r7.mValue = r10;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Relations.setStringInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.lang.String):void");
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzar.zza(this, $r1, $i0);
        }

        public boolean zzcds() throws  {
            return this.aOu.contains(Integer.valueOf(3));
        }

        public Mergedpeoplemetadata zzcit() throws  {
            return this.aZT;
        }

        public String zzcjf() throws  {
            return this.aPd;
        }

        public boolean zzcjg() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class SipAddress extends FastSafeParcelableJsonResponse {
        public static final Creator<SipAddress> CREATOR = new zzas();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        Mergedpeoplemetadata aZT;
        String mValue;
        final int mVersionCode;
        String zzcft;

        static {
            aOt.put("metadata", Field.forConcreteType("metadata", 2, Mergedpeoplemetadata.class));
            aOt.put("type", Field.forString("type", 3));
            aOt.put("value", Field.forString("value", 4));
        }

        public SipAddress() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        SipAddress(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aZT = $r2;
            this.zzcft = $r3;
            this.mValue = $r4;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof SipAddress)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            SipAddress $r2 = (SipAddress) $r1;
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
                    return this.aZT;
                case 3:
                    return this.zzcft;
                case 4:
                    return this.mValue;
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
                case 3:
                    this.zzcft = $r3;
                    break;
                case 4:
                    this.mValue = $r3;
                    break;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
            }
            this.aOu.add(Integer.valueOf($i0));
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzas.zza(this, $r1, $i0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Skills extends FastSafeParcelableJsonResponse {
        public static final Creator<Skills> CREATOR = new zzat();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        Mergedpeoplemetadata aZT;
        String mValue;
        final int mVersionCode;

        static {
            aOt.put("metadata", Field.forConcreteType("metadata", 2, Mergedpeoplemetadata.class));
            aOt.put("value", Field.forString("value", 3));
        }

        public Skills() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        Skills(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aZT = $r2;
            this.mValue = $r3;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Skills)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            Skills $r2 = (Skills) $r1;
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
                    return this.aZT;
                case 3:
                    return this.mValue;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public String getValue() throws  {
            return this.mValue;
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

        protected boolean isFieldSet(Field $r1) throws  {
            return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
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
            zzat.zza(this, $r1, $i0);
        }

        public boolean zzcds() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }

        public Mergedpeoplemetadata zzcit() throws  {
            return this.aZT;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class SortKeys extends FastSafeParcelableJsonResponse {
        public static final Creator<SortKeys> CREATOR = new zzau();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        String aQc;
        List<Mergedpeopleaffinities> aZD;
        String baq;
        String bar;
        String mName;
        final int mVersionCode;

        static {
            aOt.put("affinities", Field.forConcreteTypeArray("affinities", 2, Mergedpeopleaffinities.class));
            aOt.put("firstName", Field.forString("firstName", 3));
            aOt.put("interactionRank", Field.forString("interactionRank", 4));
            aOt.put("lastName", Field.forString("lastName", 5));
            aOt.put("name", Field.forString("name", 6));
        }

        public SortKeys() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        SortKeys(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) List<Mergedpeopleaffinities> $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r5, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeopleaffinities;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r6) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aZD = $r2;
            this.baq = $r3;
            this.aQc = $r4;
            this.bar = $r5;
            this.mName = $r6;
        }

        public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) ArrayList<T> $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.aZD = $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 71).append("Field with id=").append($i0).append(" is not a known array of custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof SortKeys)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            SortKeys $r2 = (SortKeys) $r1;
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
                    return this.aZD;
                case 3:
                    return this.baq;
                case 4:
                    return this.aQc;
                case 5:
                    return this.bar;
                case 6:
                    return this.mName;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public String getName() throws  {
            return this.mName;
        }

        public boolean hasName() throws  {
            return this.aOu.contains(Integer.valueOf(6));
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
                case 3:
                    this.baq = $r3;
                    break;
                case 4:
                    this.aQc = $r3;
                    break;
                case 5:
                    this.bar = $r3;
                    break;
                case 6:
                    this.mName = $r3;
                    break;
                default:
                    throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
            }
            this.aOu.add(Integer.valueOf($i0));
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzau.zza(this, $r1, $i0);
        }

        public String zzclk() throws  {
            return this.aQc;
        }

        public boolean zzcll() throws  {
            return this.aOu.contains(Integer.valueOf(4));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Taglines extends FastSafeParcelableJsonResponse {
        public static final Creator<Taglines> CREATOR = new zzav();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        Mergedpeoplemetadata aZT;
        String mValue;
        final int mVersionCode;

        static {
            aOt.put("metadata", Field.forConcreteType("metadata", 2, Mergedpeoplemetadata.class));
            aOt.put("value", Field.forString("value", 3));
        }

        public Taglines() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        Taglines(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aZT = $r2;
            this.mValue = $r3;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 2:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Taglines)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            Taglines $r2 = (Taglines) $r1;
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
                    return this.aZT;
                case 3:
                    return this.mValue;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
            }
        }

        public String getValue() throws  {
            return this.mValue;
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

        protected boolean isFieldSet(Field $r1) throws  {
            return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
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
            zzav.zza(this, $r1, $i0);
        }

        public boolean zzcds() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }

        public Mergedpeoplemetadata zzcit() throws  {
            return this.aZT;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Urls extends FastSafeParcelableJsonResponse {
        public static final Creator<Urls> CREATOR = new zzaw();
        private static final HashMap<String, Field<?, ?>> aOt = new HashMap();
        final Set<Integer> aOu;
        String aPd;
        Mergedpeoplemetadata aZT;
        String mValue;
        final int mVersionCode;
        String zzcft;

        static {
            aOt.put("formattedType", Field.forString("formattedType", 2));
            aOt.put("metadata", Field.forConcreteType("metadata", 3, Mergedpeoplemetadata.class));
            aOt.put("type", Field.forString("type", 5));
            aOt.put("value", Field.forString("value", 6));
        }

        public Urls() throws  {
            this.mVersionCode = 1;
            this.aOu = new HashSet();
        }

        Urls(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Mergedpeoplemetadata $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Mergedpeoplemetadata;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r5) throws  {
            this.aOu = $r1;
            this.mVersionCode = $i0;
            this.aPd = $r2;
            this.aZT = $r3;
            this.zzcft = $r4;
            this.mValue = $r5;
        }

        public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
            int $i0 = $r1.getSafeParcelableFieldId();
            switch ($i0) {
                case 3:
                    this.aZT = (Mergedpeoplemetadata) $r3;
                    this.aOu.add(Integer.valueOf($i0));
                    return;
                default:
                    str = String.valueOf($r3.getClass().getCanonicalName());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Urls)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            Urls $r2 = (Urls) $r1;
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
                    return this.aPd;
                case 3:
                    return this.aZT;
                case 4:
                    break;
                case 5:
                    return this.zzcft;
                case 6:
                    return this.mValue;
                default:
                    break;
            }
            throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
        }

        public String getType() throws  {
            return this.zzcft;
        }

        public String getValue() throws  {
            return this.mValue;
        }

        public boolean hasType() throws  {
            return this.aOu.contains(Integer.valueOf(5));
        }

        public boolean hasValue() throws  {
            return this.aOu.contains(Integer.valueOf(6));
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

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected void setStringInternal(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r8, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r9, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) java.lang.String r10) throws  {
            /*
            r7 = this;
            r0 = r8.getSafeParcelableFieldId();
            switch(r0) {
                case 2: goto L_0x0029;
                case 3: goto L_0x0008;
                case 4: goto L_0x0008;
                case 5: goto L_0x0035;
                case 6: goto L_0x0038;
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
            r7.aPd = r10;
        L_0x002b:
            r5 = r7.aOu;
            r6 = java.lang.Integer.valueOf(r0);
            r5.add(r6);
            return;
        L_0x0035:
            r7.zzcft = r10;
            goto L_0x002b;
        L_0x0038:
            r7.mValue = r10;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.Urls.setStringInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.lang.String):void");
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzaw.zza(this, $r1, $i0);
        }

        public boolean zzcds() throws  {
            return this.aOu.contains(Integer.valueOf(3));
        }

        public Mergedpeoplemetadata zzcit() throws  {
            return this.aZT;
        }

        public String zzcjf() throws  {
            return this.aPd;
        }

        public boolean zzcjg() throws  {
            return this.aOu.contains(Integer.valueOf(2));
        }
    }

    static {
        aOt.put("abouts", Field.forConcreteTypeArray("abouts", 2, Abouts.class));
        aOt.put("addresses", Field.forConcreteTypeArray("addresses", 3, Addresses.class));
        aOt.put("birthdays", Field.forConcreteTypeArray("birthdays", 5, Birthdays.class));
        aOt.put("braggingRights", Field.forConcreteTypeArray("braggingRights", 6, BraggingRights.class));
        aOt.put("calendars", Field.forConcreteTypeArray("calendars", 7, Calendars.class));
        aOt.put("clientData", Field.forConcreteTypeArray("clientData", 8, ClientData.class));
        aOt.put("coverPhotos", Field.forConcreteTypeArray("coverPhotos", 9, CoverPhotos.class));
        aOt.put("customFields", Field.forConcreteTypeArray("customFields", 10, CustomFields.class));
        aOt.put("emails", Field.forConcreteTypeArray("emails", 11, Emails.class));
        aOt.put("etag", Field.forString("etag", 12));
        aOt.put("events", Field.forConcreteTypeArray("events", 13, Events.class));
        aOt.put("extendedData", Field.forConcreteType("extendedData", 14, ExtendedData.class));
        aOt.put("externalIds", Field.forConcreteTypeArray("externalIds", 15, ExternalIds.class));
        aOt.put("genders", Field.forConcreteTypeArray("genders", 17, Genders.class));
        aOt.put("id", Field.forString("id", 18));
        aOt.put("images", Field.forConcreteTypeArray("images", 19, Images.class));
        aOt.put("instantMessaging", Field.forConcreteTypeArray("instantMessaging", 21, InstantMessaging.class));
        aOt.put("interests", Field.forConcreteTypeArray("interests", 22, Interests.class));
        aOt.put("language", Field.forString("language", 24));
        aOt.put("languages", Field.forConcreteTypeArray("languages", 25, Languages.class));
        aOt.put("legacyFields", Field.forConcreteType("legacyFields", 26, LegacyFields.class));
        aOt.put("memberships", Field.forConcreteTypeArray("memberships", 28, Memberships.class));
        aOt.put("metadata", Field.forConcreteType("metadata", 29, Metadata.class));
        aOt.put("names", Field.forConcreteTypeArray("names", 30, Names.class));
        aOt.put("nicknames", Field.forConcreteTypeArray("nicknames", 31, Nicknames.class));
        aOt.put("occupations", Field.forConcreteTypeArray("occupations", 32, Occupations.class));
        aOt.put("organizations", Field.forConcreteTypeArray("organizations", 33, Organizations.class));
        aOt.put("otherKeywords", Field.forConcreteTypeArray("otherKeywords", 34, OtherKeywords.class));
        aOt.put("phoneNumbers", Field.forConcreteTypeArray("phoneNumbers", 36, PhoneNumbers.class));
        aOt.put("placesLived", Field.forConcreteTypeArray("placesLived", 38, PlacesLived.class));
        aOt.put("profileUrl", Field.forString("profileUrl", 39));
        aOt.put("relations", Field.forConcreteTypeArray("relations", 40, Relations.class));
        aOt.put("sipAddress", Field.forConcreteTypeArray("sipAddress", 43, SipAddress.class));
        aOt.put("skills", Field.forConcreteTypeArray("skills", 44, Skills.class));
        aOt.put("sortKeys", Field.forConcreteType("sortKeys", 45, SortKeys.class));
        aOt.put("taglines", Field.forConcreteTypeArray("taglines", 46, Taglines.class));
        aOt.put("urls", Field.forConcreteTypeArray("urls", 47, Urls.class));
    }

    public Person() throws  {
        this.mVersionCode = 1;
        this.aOu = new HashSet();
    }

    Person(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<Abouts> $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<Addresses> $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<Birthdays> $r4, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<BraggingRights> $r5, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<Calendars> $r6, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<ClientData> $r7, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<CoverPhotos> $r8, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<CustomFields> $r9, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<Emails> $r10, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) String $r11, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<Events> $r12, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) ExtendedData $r13, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<ExternalIds> $r14, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<Genders> $r15, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) String $r16, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<Images> $r17, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<InstantMessaging> $r18, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<Interests> $r19, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) String $r20, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<Languages> $r21, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) LegacyFields $r22, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<Memberships> $r23, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) Metadata $r24, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<Names> $r25, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<Nicknames> $r26, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<Occupations> $r27, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<Organizations> $r28, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<OtherKeywords> $r29, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<PhoneNumbers> $r30, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<PlacesLived> $r31, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) String $r32, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<Relations> $r33, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<SipAddress> $r34, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<Skills> $r35, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) SortKeys $r36, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<Taglines> $r37, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Abouts;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Addresses;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Birthdays;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$BraggingRights;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Calendars;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ClientData;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CoverPhotos;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$CustomFields;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Emails;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Events;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExtendedData;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$ExternalIds;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Genders;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$InstantMessaging;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Interests;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Languages;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$LegacyFields;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Memberships;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Metadata;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Nicknames;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Occupations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Organizations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$OtherKeywords;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PhoneNumbers;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$PlacesLived;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Relations;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SipAddress;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Skills;", ">;", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$SortKeys;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Taglines;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Urls;", ">;)V"}) List<Urls> $r38) throws  {
        this.aOu = $r1;
        this.mVersionCode = $i0;
        this.aOx = $r2;
        this.aMy = $r3;
        this.aOz = $r4;
        this.aOA = $r5;
        this.aZI = $r6;
        this.aZJ = $r7;
        this.aOB = $r8;
        this.aOC = $r9;
        this.aMz = $r10;
        this.aOD = $r11;
        this.zzalu = $r12;
        this.aZK = $r13;
        this.aZL = $r14;
        this.aOE = $r15;
        this.zzbgd = $r16;
        this.zzbfe = $r17;
        this.aOF = $r18;
        this.aZM = $r19;
        this.zzcuw = $r20;
        this.aZN = $r21;
        this.aZO = $r22;
        this.aOI = $r23;
        this.aZP = $r24;
        this.aOK = $r25;
        this.aOL = $r26;
        this.aOM = $r27;
        this.aON = $r28;
        this.aZQ = $r29;
        this.aOO = $r30;
        this.aOP = $r31;
        this.aOQ = $r32;
        this.aOR = $r33;
        this.aZR = $r34;
        this.aOU = $r35;
        this.aZS = $r36;
        this.aOW = $r37;
        this.aOX = $r38;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T extends com.google.android.gms.common.server.response.FastJsonResponse> void addConcreteTypeArrayInternal(@dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r10, @dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) java.lang.String r11, @dalvik.annotation.Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) java.util.ArrayList<T> r12) throws  {
        /*
        r9 = this;
        r0 = r10.getSafeParcelableFieldId();
        switch(r0) {
            case 2: goto L_0x0047;
            case 3: goto L_0x0053;
            case 4: goto L_0x0008;
            case 5: goto L_0x0056;
            case 6: goto L_0x0059;
            case 7: goto L_0x005c;
            case 8: goto L_0x005f;
            case 9: goto L_0x0062;
            case 10: goto L_0x0065;
            case 11: goto L_0x0068;
            case 12: goto L_0x0008;
            case 13: goto L_0x006b;
            case 14: goto L_0x0008;
            case 15: goto L_0x006e;
            case 16: goto L_0x0008;
            case 17: goto L_0x0071;
            case 18: goto L_0x0008;
            case 19: goto L_0x0074;
            case 20: goto L_0x0008;
            case 21: goto L_0x0077;
            case 22: goto L_0x007a;
            case 23: goto L_0x0008;
            case 24: goto L_0x0008;
            case 25: goto L_0x007d;
            case 26: goto L_0x0008;
            case 27: goto L_0x0008;
            case 28: goto L_0x0080;
            case 29: goto L_0x0008;
            case 30: goto L_0x0083;
            case 31: goto L_0x0086;
            case 32: goto L_0x0089;
            case 33: goto L_0x008c;
            case 34: goto L_0x008f;
            case 35: goto L_0x0008;
            case 36: goto L_0x0092;
            case 37: goto L_0x0008;
            case 38: goto L_0x0095;
            case 39: goto L_0x0008;
            case 40: goto L_0x0098;
            case 41: goto L_0x0008;
            case 42: goto L_0x0008;
            case 43: goto L_0x009b;
            case 44: goto L_0x009e;
            case 45: goto L_0x0008;
            case 46: goto L_0x00a1;
            case 47: goto L_0x00a4;
            default: goto L_0x0007;
        };
    L_0x0007:
        goto L_0x0008;
    L_0x0008:
        r1 = new java.lang.IllegalArgumentException;
        r2 = r12.getClass();
        r11 = r2.getCanonicalName();
        r11 = java.lang.String.valueOf(r11);
        r3 = new java.lang.StringBuilder;
        r4 = java.lang.String.valueOf(r11);
        r5 = r4.length();
        r5 = r5 + 71;
        r3.<init>(r5);
        r6 = "Field with id=";
        r3 = r3.append(r6);
        r3 = r3.append(r0);
        r6 = " is not a known array of custom type.  Found ";
        r3 = r3.append(r6);
        r3 = r3.append(r11);
        r6 = ".";
        r3 = r3.append(r6);
        r11 = r3.toString();
        r1.<init>(r11);
        throw r1;
    L_0x0047:
        r9.aOx = r12;
    L_0x0049:
        r7 = r9.aOu;
        r8 = java.lang.Integer.valueOf(r0);
        r7.add(r8);
        return;
    L_0x0053:
        r9.aMy = r12;
        goto L_0x0049;
    L_0x0056:
        r9.aOz = r12;
        goto L_0x0049;
    L_0x0059:
        r9.aOA = r12;
        goto L_0x0049;
    L_0x005c:
        r9.aZI = r12;
        goto L_0x0049;
    L_0x005f:
        r9.aZJ = r12;
        goto L_0x0049;
    L_0x0062:
        r9.aOB = r12;
        goto L_0x0049;
    L_0x0065:
        r9.aOC = r12;
        goto L_0x0049;
    L_0x0068:
        r9.aMz = r12;
        goto L_0x0049;
    L_0x006b:
        r9.zzalu = r12;
        goto L_0x0049;
    L_0x006e:
        r9.aZL = r12;
        goto L_0x0049;
    L_0x0071:
        r9.aOE = r12;
        goto L_0x0049;
    L_0x0074:
        r9.zzbfe = r12;
        goto L_0x0049;
    L_0x0077:
        r9.aOF = r12;
        goto L_0x0049;
    L_0x007a:
        r9.aZM = r12;
        goto L_0x0049;
    L_0x007d:
        r9.aZN = r12;
        goto L_0x0049;
    L_0x0080:
        r9.aOI = r12;
        goto L_0x0049;
    L_0x0083:
        r9.aOK = r12;
        goto L_0x0049;
    L_0x0086:
        r9.aOL = r12;
        goto L_0x0049;
    L_0x0089:
        r9.aOM = r12;
        goto L_0x0049;
    L_0x008c:
        r9.aON = r12;
        goto L_0x0049;
    L_0x008f:
        r9.aZQ = r12;
        goto L_0x0049;
    L_0x0092:
        r9.aOO = r12;
        goto L_0x0049;
    L_0x0095:
        r9.aOP = r12;
        goto L_0x0049;
    L_0x0098:
        r9.aOR = r12;
        goto L_0x0049;
    L_0x009b:
        r9.aZR = r12;
        goto L_0x0049;
    L_0x009e:
        r9.aOU = r12;
        goto L_0x0049;
    L_0x00a1:
        r9.aOW = r12;
        goto L_0x0049;
    L_0x00a4:
        r9.aOX = r12;
        goto L_0x0049;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.plus.service.v2whitelisted.models.Person.addConcreteTypeArrayInternal(com.google.android.gms.common.server.response.FastJsonResponse$Field, java.lang.String, java.util.ArrayList):void");
    }

    public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
        int $i0 = $r1.getSafeParcelableFieldId();
        switch ($i0) {
            case 14:
                this.aZK = (ExtendedData) $r3;
                break;
            case 26:
                this.aZO = (LegacyFields) $r3;
                break;
            case 29:
                this.aZP = (Metadata) $r3;
                break;
            case 45:
                this.aZS = (SortKeys) $r3;
                break;
            default:
                str = String.valueOf($r3.getClass().getCanonicalName());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 62).append("Field with id=").append($i0).append(" is not a known custom type.  Found ").append(str).append(FileUploadSession.SEPARATOR).toString());
        }
        this.aOu.add(Integer.valueOf($i0));
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

    public List<Abouts> getAbouts() throws  {
        return this.aOx;
    }

    public List<Addresses> getAddresses() throws  {
        return this.aMy;
    }

    public List<Birthdays> getBirthdays() throws  {
        return this.aOz;
    }

    public List<BraggingRights> getBraggingRights() throws  {
        return this.aOA;
    }

    public List<CoverPhotos> getCoverPhotos() throws  {
        return this.aOB;
    }

    public List<CustomFields> getCustomFields() throws  {
        return this.aOC;
    }

    public List<Emails> getEmails() throws  {
        return this.aMz;
    }

    public String getEtag() throws  {
        return this.aOD;
    }

    public List<Events> getEvents() throws  {
        return this.zzalu;
    }

    public HashMap<String, Field<?, ?>> getFieldMappings() throws  {
        return aOt;
    }

    public Object getFieldValue(Field $r1) throws  {
        switch ($r1.getSafeParcelableFieldId()) {
            case 2:
                return this.aOx;
            case 3:
                return this.aMy;
            case 4:
            case 16:
            case 20:
            case 23:
            case 27:
            case 35:
            case 37:
            case 41:
            case 42:
                break;
            case 5:
                return this.aOz;
            case 6:
                return this.aOA;
            case 7:
                return this.aZI;
            case 8:
                return this.aZJ;
            case 9:
                return this.aOB;
            case 10:
                return this.aOC;
            case 11:
                return this.aMz;
            case 12:
                return this.aOD;
            case 13:
                return this.zzalu;
            case 14:
                return this.aZK;
            case 15:
                return this.aZL;
            case 17:
                return this.aOE;
            case 18:
                return this.zzbgd;
            case 19:
                return this.zzbfe;
            case 21:
                return this.aOF;
            case 22:
                return this.aZM;
            case 24:
                return this.zzcuw;
            case 25:
                return this.aZN;
            case 26:
                return this.aZO;
            case 28:
                return this.aOI;
            case 29:
                return this.aZP;
            case 30:
                return this.aOK;
            case 31:
                return this.aOL;
            case 32:
                return this.aOM;
            case 33:
                return this.aON;
            case 34:
                return this.aZQ;
            case 36:
                return this.aOO;
            case 38:
                return this.aOP;
            case 39:
                return this.aOQ;
            case 40:
                return this.aOR;
            case 43:
                return this.aZR;
            case 44:
                return this.aOU;
            case 45:
                return this.aZS;
            case 46:
                return this.aOW;
            case 47:
                return this.aOX;
            default:
                break;
        }
        throw new IllegalStateException("Unknown safe parcelable id=" + $r1.getSafeParcelableFieldId());
    }

    public List<Genders> getGenders() throws  {
        return this.aOE;
    }

    public String getId() throws  {
        return this.zzbgd;
    }

    public List<Images> getImages() throws  {
        return this.zzbfe;
    }

    public List<InstantMessaging> getInstantMessaging() throws  {
        return this.aOF;
    }

    public String getLanguage() throws  {
        return this.zzcuw;
    }

    public List<Memberships> getMemberships() throws  {
        return this.aOI;
    }

    public List<Names> getNames() throws  {
        return this.aOK;
    }

    public List<Nicknames> getNicknames() throws  {
        return this.aOL;
    }

    public List<Occupations> getOccupations() throws  {
        return this.aOM;
    }

    public List<Organizations> getOrganizations() throws  {
        return this.aON;
    }

    public List<PhoneNumbers> getPhoneNumbers() throws  {
        return this.aOO;
    }

    public List<PlacesLived> getPlacesLived() throws  {
        return this.aOP;
    }

    public List<Relations> getRelations() throws  {
        return this.aOR;
    }

    public List<Skills> getSkills() throws  {
        return this.aOU;
    }

    public List<Taglines> getTaglines() throws  {
        return this.aOW;
    }

    public List<Urls> getUrls() throws  {
        return this.aOX;
    }

    public boolean hasBraggingRights() throws  {
        return this.aOu.contains(Integer.valueOf(6));
    }

    public boolean hasId() throws  {
        return this.aOu.contains(Integer.valueOf(18));
    }

    public boolean hasImages() throws  {
        return this.aOu.contains(Integer.valueOf(19));
    }

    public boolean hasLanguage() throws  {
        return this.aOu.contains(Integer.valueOf(24));
    }

    public boolean hasOrganizations() throws  {
        return this.aOu.contains(Integer.valueOf(33));
    }

    public boolean hasPlacesLived() throws  {
        return this.aOu.contains(Integer.valueOf(38));
    }

    public boolean hasUrls() throws  {
        return this.aOu.contains(Integer.valueOf(47));
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

    public boolean isFieldSet(Field $r1) throws  {
        return this.aOu.contains(Integer.valueOf($r1.getSafeParcelableFieldId()));
    }

    protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
        int $i0 = $r1.getSafeParcelableFieldId();
        switch ($i0) {
            case 12:
                this.aOD = $r3;
                break;
            case 18:
                this.zzbgd = $r3;
                break;
            case 24:
                this.zzcuw = $r3;
                break;
            case 39:
                this.aOQ = $r3;
                break;
            default:
                throw new IllegalArgumentException("Field with id=" + $i0 + " is not known to be a String.");
        }
        this.aOu.add(Integer.valueOf($i0));
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzj.zza(this, $r1, $i0);
    }

    public boolean zzcds() throws  {
        return this.aOu.contains(Integer.valueOf(29));
    }

    public boolean zzchu() throws  {
        return this.aOu.contains(Integer.valueOf(2));
    }

    public boolean zzchv() throws  {
        return this.aOu.contains(Integer.valueOf(3));
    }

    public boolean zzchw() throws  {
        return this.aOu.contains(Integer.valueOf(5));
    }

    public boolean zzchx() throws  {
        return this.aOu.contains(Integer.valueOf(9));
    }

    public boolean zzchy() throws  {
        return this.aOu.contains(Integer.valueOf(10));
    }

    public boolean zzchz() throws  {
        return this.aOu.contains(Integer.valueOf(11));
    }

    public boolean zzcia() throws  {
        return this.aOu.contains(Integer.valueOf(12));
    }

    public boolean zzcib() throws  {
        return this.aOu.contains(Integer.valueOf(13));
    }

    public boolean zzcic() throws  {
        return this.aOu.contains(Integer.valueOf(17));
    }

    public boolean zzcid() throws  {
        return this.aOu.contains(Integer.valueOf(21));
    }

    public LegacyFields zzcie() throws  {
        return this.aZO;
    }

    public boolean zzcif() throws  {
        return this.aOu.contains(Integer.valueOf(26));
    }

    public boolean zzcig() throws  {
        return this.aOu.contains(Integer.valueOf(28));
    }

    public Metadata zzcih() throws  {
        return this.aZP;
    }

    public boolean zzcii() throws  {
        return this.aOu.contains(Integer.valueOf(30));
    }

    public boolean zzcij() throws  {
        return this.aOu.contains(Integer.valueOf(31));
    }

    public boolean zzcik() throws  {
        return this.aOu.contains(Integer.valueOf(32));
    }

    public boolean zzcil() throws  {
        return this.aOu.contains(Integer.valueOf(36));
    }

    public String zzcim() throws  {
        return this.aOQ;
    }

    public boolean zzcin() throws  {
        return this.aOu.contains(Integer.valueOf(39));
    }

    public boolean zzcio() throws  {
        return this.aOu.contains(Integer.valueOf(40));
    }

    public boolean zzcip() throws  {
        return this.aOu.contains(Integer.valueOf(44));
    }

    public SortKeys zzciq() throws  {
        return this.aZS;
    }

    public boolean zzcir() throws  {
        return this.aOu.contains(Integer.valueOf(45));
    }

    public boolean zzcis() throws  {
        return this.aOu.contains(Integer.valueOf(46));
    }
}
