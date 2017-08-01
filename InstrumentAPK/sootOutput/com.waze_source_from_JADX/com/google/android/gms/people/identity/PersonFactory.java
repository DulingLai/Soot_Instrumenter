package com.google.android.gms.people.identity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.people.PeopleConstants.Endpoints;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: dalvik_source_com.waze.apk */
public interface PersonFactory<PersonType> {

    /* compiled from: dalvik_source_com.waze.apk */
    public static class ContactData {
        private final List<RawContactData> aMw;

        public ContactData(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/people/identity/PersonFactory$RawContactData;", ">;)V"}) List<RawContactData> $r1) throws  {
            this.aMw = new ArrayList($r1);
        }

        public ContactData(RawContactData... $r1) throws  {
            this.aMw = Arrays.asList($r1);
        }

        public List<RawContactData> getRawData() throws  {
            return this.aMw;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class ExternalContactData {
        protected final String mAccountType;
        protected final Uri mDataUri;
        protected final String mDetail;
        protected final String mHeader;
        protected final int mIconRes;
        protected final String mMimeType;
        protected final String mResourcePackageName;
        protected final int mTitleRes;

        public ExternalContactData(Uri $r1, String $r2, int $i0, String $r3, String $r4, String $r5, int $i1, String $r6) throws  {
            this.mDataUri = $r1;
            this.mHeader = $r2;
            this.mIconRes = $i0;
            this.mDetail = $r3;
            this.mResourcePackageName = $r4;
            this.mMimeType = $r5;
            this.mTitleRes = $i1;
            this.mAccountType = $r6;
        }

        public String getAccountType() throws  {
            return this.mAccountType;
        }

        public Uri getDataUri() throws  {
            return this.mDataUri;
        }

        public String getDetail() throws  {
            return this.mDetail;
        }

        public String getHeader() throws  {
            return this.mHeader;
        }

        public int getIconRes() throws  {
            return this.mIconRes;
        }

        public String getMimeType() throws  {
            return this.mMimeType;
        }

        public String getResourcePackageName() throws  {
            return this.mResourcePackageName;
        }

        public int getTitleRes() throws  {
            return this.mTitleRes;
        }

        public String toString() throws  {
            StringBuffer $r1 = new StringBuffer();
            $r1.append(ExternalContactData.class.getSimpleName());
            $r1.append("<dataUri=").append(this.mDataUri);
            $r1.append(" header=").append(this.mHeader);
            $r1.append(" detail=").append(this.mDetail);
            $r1.append(" resourcePackageName=").append(this.mResourcePackageName);
            $r1.append(" mimeType=").append(this.mMimeType);
            $r1.append(" titleRes=").append(this.mTitleRes);
            $r1.append(" iconRes=").append(this.mIconRes);
            $r1.append(" accountType=").append(this.mAccountType);
            $r1.append(">");
            return $r1.toString();
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class OfflineDatabaseData {
        protected final VisibleDataBufferRef mRow;

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class AddressData {
            private final String acD;
            private final String zzcft;

            public AddressData(String $r1, String $r2) throws  {
                this.acD = $r1;
                this.zzcft = $r2;
            }

            public String getAddress() throws  {
                return this.acD;
            }

            public String getType() throws  {
                return this.zzcft;
            }
        }

        /* compiled from: dalvik_source_com.waze.apk */
        public static class Circle {
            public static final int UNKNOWN_MEMBER_COUNT = -1;
            private final VisibleDataBufferRef mRow;

            public Circle(VisibleDataBufferRef $r1) throws  {
                this.mRow = $r1;
            }

            public String getId() throws  {
                return this.mRow.getString("circle_id");
            }

            public int getMemberCount() throws  {
                return this.mRow.getInteger("people_count", -1);
            }

            public String getName() throws  {
                return this.mRow.getString("name");
            }
        }

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class EmailData {
            private final String fw;
            private final String zzcft;

            public EmailData(String $r1, String $r2) throws  {
                this.fw = $r1;
                this.zzcft = $r2;
            }

            public String getEmailAddress() throws  {
                return this.fw;
            }

            public String getType() throws  {
                return this.zzcft;
            }
        }

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class PhoneData {
            private final String aMx;
            private final String zzcft;

            public PhoneData(String $r1, String $r2) throws  {
                this.aMx = $r1;
                this.zzcft = $r2;
            }

            public String getPhone() throws  {
                return this.aMx;
            }

            public String getType() throws  {
                return this.zzcft;
            }
        }

        /* compiled from: dalvik_source_com.waze.apk */
        protected static final class VisibleDataBufferRef extends zzc {
            public VisibleDataBufferRef(DataHolder $r1, int $i0) throws  {
                super($r1, $i0);
            }

            public boolean getBoolean(String $r1, boolean $z0) throws  {
                return !hasColumn($r1) ? $z0 : super.getBoolean($r1);
            }

            public int getInteger(String $r1, int $i0) throws  {
                return !hasColumn($r1) ? $i0 : super.getInteger($r1);
            }

            public String getString(String $r1) throws  {
                return getString($r1, null);
            }

            public String getString(String $r1, String $r2) throws  {
                return !hasColumn($r1) ? $r2 : super.getString($r1);
            }
        }

        public OfflineDatabaseData(VisibleDataBufferRef $r1) throws  {
            this.mRow = $r1;
        }

        public static OfflineDatabaseData build(DataHolder $r0, DataHolder $r1, DataHolder $r2, DataHolder $r3, DataHolder $r4, DataHolder $r5, DataHolder $r6, DataHolder $r7, DataHolder $r8, int $i0) throws  {
            VisibleDataBufferRef $r11 = (VisibleDataBufferRef) zzk(findRows($r0, $i0));
            if ($r11 != null) {
                return new zzb($r11, $r1, $r2, $r3, $r8, $i0);
            }
            $r11 = (VisibleDataBufferRef) zzk(findRows($r4, $i0));
            return $r11 != null ? new zza($r11, $r5, $r6, $r7, $i0) : null;
        }

        protected static ArrayList<VisibleDataBufferRef> findRows(@Signature({"(", "Lcom/google/android/gms/common/data/DataHolder;", "I)", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData$VisibleDataBufferRef;", ">;"}) DataHolder $r0, @Signature({"(", "Lcom/google/android/gms/common/data/DataHolder;", "I)", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData$VisibleDataBufferRef;", ">;"}) int $i0) throws  {
            ArrayList $r1 = new ArrayList();
            if ($r0 == null) {
                return $r1;
            }
            for (int $i1 = 0; $i1 < $r0.getCount(); $i1++) {
                if ($i0 == $r0.getInteger("ordinal", $i1, $r0.zzic($i1))) {
                    $r1.add(new VisibleDataBufferRef($r0, $i1));
                } else if (!$r1.isEmpty()) {
                    return $r1;
                }
            }
            return $r1;
        }

        private static <T> T zzk(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/util/ArrayList", "<TT;>;)TT;"}) ArrayList<T> $r0) throws  {
            boolean $z0 = true;
            if ($r0 == null || $r0.isEmpty()) {
                return null;
            }
            if ($r0.size() != 1) {
                $z0 = false;
            }
            zzab.zzbm($z0);
            return $r0.get(0);
        }

        public abstract List<AddressData> getAddresses() throws ;

        public abstract List<Circle> getCircles() throws ;

        public abstract String getCompressedAvatarUrl() throws ;

        public abstract String getDisplayName() throws ;

        public abstract List<EmailData> getEmails() throws ;

        public abstract String getGaiaId() throws ;

        public abstract boolean getNameVerified() throws ;

        protected boolean getPersonBoolean(String $r1, boolean $z0) throws  {
            return this.mRow.getBoolean($r1, $z0);
        }

        protected int getPersonInteger(String $r1, int $i0) throws  {
            return this.mRow.getInteger($r1, $i0);
        }

        protected String getPersonString(String $r1) throws  {
            return getPersonString($r1, null);
        }

        protected String getPersonString(String $r1, String $r2) throws  {
            return this.mRow.getString($r1, $r2);
        }

        public abstract List<PhoneData> getPhones() throws ;

        public abstract int getProfileType() throws ;

        public abstract String getTagline() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class RawContactData {
        private final boolean KL;
        private final String aMC;
        private final String aMD;
        private final String[] aME;
        private final boolean aMF;
        private final ExternalContactData aMG;
        private final int aMH;
        private final String mMimeType;

        public RawContactData(String $r1, String $r2, String $r3, int $i0, String[] $r4, boolean $z0, boolean $z1, ExternalContactData $r5) throws  {
            this.aMC = $r1;
            this.aMD = $r2;
            this.mMimeType = $r3;
            this.aME = $r4;
            this.KL = $z0;
            this.aMF = $z1;
            this.aMG = $r5;
            this.aMH = $i0;
        }

        public String getContactId() throws  {
            return this.aMC;
        }

        public String getData(int $i0) throws  {
            return $i0 < this.aME.length ? this.aME[$i0] : null;
        }

        public ExternalContactData getExternalContactData() throws  {
            return this.aMG;
        }

        public String getMimeType() throws  {
            return this.mMimeType;
        }

        public String getRawContactId() throws  {
            return this.aMD;
        }

        public int getTimesUsed() throws  {
            return this.aMH;
        }

        public boolean isPrimary() throws  {
            return this.aMF;
        }

        public boolean isReadOnly() throws  {
            return this.KL;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class ServiceData {
        public static final int FAILED_RESPONSE_CODE = -1;
        private static final ServiceData aMI = new ServiceData(-1, 0, null, null);
        public final byte[] blob;
        public final int format;
        public final Map<String, String> headers;
        public final int responseCode;

        public ServiceData(@Signature({"(II[B", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) int $i0, @Signature({"(II[B", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) int $i1, @Signature({"(II[B", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) byte[] $r1, @Signature({"(II[B", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Map<String, String> $r2) throws  {
            this.responseCode = $i0;
            this.format = $i1;
            this.blob = $r1;
            this.headers = $r2;
        }

        public static ServiceData zzat(Bundle $r0) throws  {
            if ($r0 == null) {
                return aMI;
            }
            int $i0 = $r0.getInt("get.server_blob.code", -1);
            if ($i0 == -1) {
                return aMI;
            }
            return new ServiceData($i0, $r0.getInt("get.server_blob.format"), $r0.getByteArray("get.server_blob.body"), (HashMap) $r0.getSerializable("get.server_blob.headers"));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zza extends OfflineDatabaseData {
        private final List<PhoneData> aMA;
        private final List<AddressData> aMy;
        private final List<EmailData> aMz;

        public zza(VisibleDataBufferRef $r1, DataHolder $r2, DataHolder $r3, DataHolder $r4, int $i0) throws  {
            super($r1);
            ArrayList $r5 = new ArrayList();
            Iterator $r7 = OfflineDatabaseData.findRows($r2, $i0).iterator();
            while ($r7.hasNext()) {
                $r1 = (VisibleDataBufferRef) $r7.next();
                $r5.add(new AddressData($r1.getString("postal_address"), $r1.getString("type")));
            }
            ArrayList $r6 = new ArrayList();
            $r7 = OfflineDatabaseData.findRows($r3, $i0).iterator();
            while ($r7.hasNext()) {
                $r1 = (VisibleDataBufferRef) $r7.next();
                $r6.add(new EmailData($r1.getString("email"), $r1.getString("type")));
            }
            ArrayList $r12 = new ArrayList();
            $r7 = OfflineDatabaseData.findRows($r4, $i0).iterator();
            while ($r7.hasNext()) {
                $r1 = (VisibleDataBufferRef) $r7.next();
                $r12.add(new PhoneData($r1.getString("phone"), $r1.getString("type")));
            }
            this.aMy = Collections.unmodifiableList($r5);
            this.aMz = Collections.unmodifiableList($r6);
            this.aMA = Collections.unmodifiableList($r12);
        }

        public List<AddressData> getAddresses() throws  {
            return this.aMy;
        }

        public List<Circle> getCircles() throws  {
            return null;
        }

        public String getCompressedAvatarUrl() throws  {
            return null;
        }

        public String getDisplayName() throws  {
            return getPersonString("display_name");
        }

        public List<EmailData> getEmails() throws  {
            return this.aMz;
        }

        public String getGaiaId() throws  {
            return getPersonString(Endpoints.KEY_TARGET_GAIA_ID);
        }

        public boolean getNameVerified() throws  {
            return false;
        }

        public List<PhoneData> getPhones() throws  {
            return this.aMA;
        }

        public int getProfileType() throws  {
            return 0;
        }

        public String getTagline() throws  {
            return null;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zzb extends OfflineDatabaseData {
        private final List<PhoneData> aMA;
        private final List<Circle> aMB;
        private final List<AddressData> aMy;
        private final List<EmailData> aMz;

        public zzb(VisibleDataBufferRef $r1, DataHolder $r2, DataHolder $r3, DataHolder $r4, DataHolder $r5, int $i0) throws  {
            super($r1);
            ArrayList $r6 = new ArrayList();
            Iterator $r8 = OfflineDatabaseData.findRows($r5, $i0).iterator();
            while ($r8.hasNext()) {
                $r6.add(new Circle((VisibleDataBufferRef) $r8.next()));
            }
            ArrayList $r7 = new ArrayList();
            $r8 = OfflineDatabaseData.findRows($r2, $i0).iterator();
            while ($r8.hasNext()) {
                $r1 = (VisibleDataBufferRef) $r8.next();
                $r7.add(new AddressData($r1.getString("postal_address"), $r1.getString("type")));
            }
            ArrayList $r11 = new ArrayList();
            $r8 = OfflineDatabaseData.findRows($r3, $i0).iterator();
            while ($r8.hasNext()) {
                $r1 = (VisibleDataBufferRef) $r8.next();
                $r11.add(new EmailData($r1.getString("email"), $r1.getString("type")));
            }
            ArrayList $r15 = new ArrayList();
            $r8 = OfflineDatabaseData.findRows($r4, $i0).iterator();
            while ($r8.hasNext()) {
                $r1 = (VisibleDataBufferRef) $r8.next();
                $r15.add(new PhoneData($r1.getString("phone"), $r1.getString("type")));
            }
            this.aMB = Collections.unmodifiableList($r6);
            this.aMy = Collections.unmodifiableList($r7);
            this.aMz = Collections.unmodifiableList($r11);
            this.aMA = Collections.unmodifiableList($r15);
        }

        public List<AddressData> getAddresses() throws  {
            return this.aMy;
        }

        public List<Circle> getCircles() throws  {
            return this.aMB;
        }

        public String getCompressedAvatarUrl() throws  {
            return getPersonString("avatar");
        }

        public String getDisplayName() throws  {
            return getPersonString("name");
        }

        public List<EmailData> getEmails() throws  {
            return this.aMz;
        }

        public String getGaiaId() throws  {
            return getPersonString(Endpoints.KEY_TARGET_GAIA_ID);
        }

        public boolean getNameVerified() throws  {
            return getPersonBoolean("name_verified", false);
        }

        public List<PhoneData> getPhones() throws  {
            return this.aMA;
        }

        public int getProfileType() throws  {
            return getPersonInteger("profile_type", -1);
        }

        public String getTagline() throws  {
            return getPersonString("tagline");
        }
    }

    PersonType build(@Signature({"(", "Landroid/content/Context;", "Ljava/lang/Object;", "Lcom/google/android/gms/people/identity/PersonFactory$ServiceData;", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData;", ")TPersonType;"}) Context context, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/Object;", "Lcom/google/android/gms/people/identity/PersonFactory$ServiceData;", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData;", ")TPersonType;"}) Object obj, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/Object;", "Lcom/google/android/gms/people/identity/PersonFactory$ServiceData;", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData;", ")TPersonType;"}) ServiceData serviceData, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/Object;", "Lcom/google/android/gms/people/identity/PersonFactory$ServiceData;", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData;", ")TPersonType;"}) ContactData contactData, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/Object;", "Lcom/google/android/gms/people/identity/PersonFactory$ServiceData;", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData;", ")TPersonType;"}) OfflineDatabaseData offlineDatabaseData) throws ;
}
