package com.google.android.gms.people.internal.agg;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.people.PeopleConstants.PeopleEmail;
import com.google.android.gms.people.internal.agg.PhoneEmailDecoder.PhoneDecoder;
import com.google.android.gms.people.internal.zzi;
import com.google.android.gms.people.internal.zzm;
import com.google.android.gms.people.internal.zzp;
import com.google.android.gms.people.internal.zzq;
import com.google.android.gms.people.model.AggregatedPerson;
import com.google.android.gms.people.model.AggregatedPersonBuffer;
import com.google.android.gms.people.model.EmailAddress;
import com.google.android.gms.people.model.PhoneNumber;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: dalvik_source_com.waze.apk */
class zza extends AggregatedPersonBuffer {
    private PhoneDecoder aMk;
    private final int aRq;
    private DataHolder aRr;
    private Cursor aRs;
    private zzi aRt;
    private zzi aRu;
    private ArrayList<String> aRv;
    private HashMap<String, String> aRw;
    private zzb aRx;
    private zzb aRy;
    private final boolean aRz;
    private volatile boolean lx;
    private Context mContext;

    /* compiled from: dalvik_source_com.waze.apk */
    private static abstract class zzb {
        private final Resources Kn;
        private final ConcurrentHashMap<Integer, String> aRH = new ConcurrentHashMap();

        public zzb(Resources $r1) throws  {
            this.Kn = $r1;
        }

        public String getLabel(int $i0) throws  {
            if ($i0 == 0) {
                return null;
            }
            String $r1 = (String) this.aRH.get(Integer.valueOf($i0));
            if ($r1 != null) {
                return $r1;
            }
            $r1 = zza(this.Kn, $i0);
            this.aRH.put(Integer.valueOf($i0), $r1);
            return $r1;
        }

        protected abstract String zza(Resources resources, int i) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private class zza implements AggregatedPerson {
        private final int GM;
        final /* synthetic */ zza aRA;
        private boolean aRB;
        private ArrayList<Long> aRC;
        private ArrayList<EmailAddress> aRD;
        private ArrayList<PhoneNumber> aRE;
        private EmailAddress aRF;
        private final boolean aRG;

        public zza(zza $r1, int $i0) throws  {
            this.aRA = $r1;
            this.GM = $i0;
            this.aRG = !TextUtils.isEmpty(getGaiaId());
        }

        private Iterable<EmailAddress> zza(@Signature({"(", "Lcom/google/android/gms/people/model/EmailAddress;", ")", "Ljava/lang/Iterable", "<", "Lcom/google/android/gms/people/model/EmailAddress;", ">;"}) EmailAddress $r1) throws  {
            ArrayList $r2 = new ArrayList(1);
            $r2.add($r1);
            return $r2;
        }

        private String zza(Cursor $r1, zzb $r2) throws  {
            int $i0 = this.aRA.aRs.getInt(4);
            return $i0 == 0 ? $r1.getString(5) : $r2.getLabel($i0);
        }

        private int zzcfl() throws  {
            return this.aRA.aRt.zzack(this.GM);
        }

        private int zzcfm() throws  {
            return this.aRA.aRu.zzack(this.GM);
        }

        private void zzcfn() throws  {
            if (!this.aRB) {
                this.aRB = true;
                int $i0 = zzcfm();
                this.aRC = new ArrayList($i0);
                this.aRD = new ArrayList();
                this.aRE = null;
                if (hasPlusPerson() && this.aRA.aRz) {
                    this.aRE = this.aRA.aMk.decode(zzrn("v_phones"), false);
                }
                if (this.aRE == null) {
                    this.aRE = new ArrayList();
                }
                this.aRF = null;
                String $r4 = getGaiaId();
                for (int $i1 = 0; $i1 < $i0; $i1++) {
                    if (this.aRA.aRs.moveToPosition(this.aRA.aRu.get(this.GM, $i1))) {
                        this.aRC.add(Long.valueOf(this.aRA.aRs.getLong(0)));
                        do {
                            String $r8 = this.aRA.aRs.getString(2);
                            String $r9;
                            if ("vnd.android.cursor.item/email_v2".equals($r8) && this.aRF == null) {
                                $r8 = zza(this.aRA.aRs, this.aRA.aRx);
                                $r9 = this.aRA.aRs.getString(3);
                                if (!TextUtils.isEmpty($r9)) {
                                    zzc com_google_android_gms_people_internal_agg_zzc = new zzc($r8, $r9);
                                    if (!this.aRD.contains(com_google_android_gms_people_internal_agg_zzc)) {
                                        if ($r4 != null) {
                                            if (this.aRA.aRw.containsKey(com_google_android_gms_people_internal_agg_zzc.getValue())) {
                                                if ($r4.equals(this.aRA.aRw.get(com_google_android_gms_people_internal_agg_zzc.getValue()))) {
                                                    this.aRF = com_google_android_gms_people_internal_agg_zzc;
                                                    this.aRD.clear();
                                                }
                                            }
                                        }
                                        this.aRD.add(com_google_android_gms_people_internal_agg_zzc);
                                    }
                                }
                            } else if ("vnd.android.cursor.item/phone_v2".equals($r8)) {
                                $r8 = zza(this.aRA.aRs, this.aRA.aRy);
                                $r9 = this.aRA.aRs.getString(3);
                                if (!TextUtils.isEmpty($r9)) {
                                    zzg com_google_android_gms_people_internal_agg_zzg = new zzg($r8, $r9);
                                    if (!this.aRE.contains(com_google_android_gms_people_internal_agg_zzg)) {
                                        this.aRE.add(com_google_android_gms_people_internal_agg_zzg);
                                    }
                                }
                            }
                        } while (zzb.zzd(this.aRA.aRs));
                    }
                }
            }
        }

        private zza zzcfo() throws  {
            return this.aRA;
        }

        private String zzrn(String $r1) throws  {
            if (!hasPlusPerson()) {
                return null;
            }
            int $i0 = this.aRA.aRt.get(this.GM, 0);
            return this.aRA.aRr.getString($r1, $i0, this.aRA.aRr.zzic($i0));
        }

        private long zzro(String $r1) throws  {
            if (!hasPlusPerson()) {
                return 0;
            }
            int $i0 = this.aRA.aRt.get(this.GM, 0);
            return this.aRA.aRr.getLong($r1, $i0, this.aRA.aRr.zzic($i0));
        }

        private int zzrp(String $r1) throws  {
            if (!hasPlusPerson()) {
                return 0;
            }
            int $i0 = this.aRA.aRt.get(this.GM, 0);
            return this.aRA.aRr.getInteger($r1, $i0, this.aRA.aRr.zzic($i0));
        }

        private double zzrq(String $r1) throws  {
            if (!hasPlusPerson()) {
                return 0.0d;
            }
            int $i0 = this.aRA.aRt.get(this.GM, 0);
            return this.aRA.aRr.getDouble($r1, $i0, this.aRA.aRr.zzic($i0));
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof zza)) {
                return false;
            }
            zza $r2 = (zza) $r1;
            return this.GM == $r2.GM ? zzcfo() == $r2.zzcfo() : false;
        }

        @Deprecated
        public String getAccountName() throws  {
            this.aRA.zzcfk();
            return getOwnerAccountName();
        }

        public double getAffinity1() throws  {
            this.aRA.zzcfk();
            return zzrq(PeopleEmail.AFFINITY_1);
        }

        public double getAffinity2() throws  {
            this.aRA.zzcfk();
            return zzrq(PeopleEmail.AFFINITY_2);
        }

        public double getAffinity3() throws  {
            this.aRA.zzcfk();
            return zzrq(PeopleEmail.AFFINITY_3);
        }

        public double getAffinity4() throws  {
            this.aRA.zzcfk();
            return zzrq(PeopleEmail.AFFINITY_4);
        }

        public double getAffinity5() throws  {
            this.aRA.zzcfk();
            return zzrq(PeopleEmail.AFFINITY_5);
        }

        public String getAvatarUrl() throws  {
            this.aRA.zzcfk();
            return zzm.aQh.zzqx(zzrn("avatar"));
        }

        public String[] getBelongingCircleIds() throws  {
            this.aRA.zzcfk();
            return zzq.zzqz(zzrn("v_circle_ids"));
        }

        public Iterable<Long> getContactIds() throws  {
            this.aRA.zzcfk();
            zzcfn();
            return this.aRC;
        }

        public Iterable<EmailAddress> getEmailAddresses() throws  {
            this.aRA.zzcfk();
            String $r2 = zzq.zzrc(getQualifiedId());
            if (!TextUtils.isEmpty($r2)) {
                return zza(new zzc("", $r2));
            }
            zzcfn();
            if (this.aRG) {
                return this.aRF != null ? zza(this.aRF) : EmailAddress.EMPTY_EMAILS;
            } else {
                if (hasContact()) {
                    return this.aRD;
                }
                if (zzp.zzcfd()) {
                    $r2 = "Row should have a contact: ";
                    String $r6 = String.valueOf(getQualifiedId());
                    zzp.zzal("PeopleAggregator", $r6.length() != 0 ? $r2.concat($r6) : new String("Row should have a contact: "));
                }
                return EmailAddress.EMPTY_EMAILS;
            }
        }

        public String getFamilyName() throws  {
            this.aRA.zzcfk();
            return zzrn("family_name");
        }

        public String getGaiaId() throws  {
            this.aRA.zzcfk();
            return (String) this.aRA.aRv.get(this.GM);
        }

        public String getGivenName() throws  {
            this.aRA.zzcfk();
            return zzrn("given_name");
        }

        public int getInViewerDomain() throws  {
            this.aRA.zzcfk();
            return zzrp("in_viewer_domain");
        }

        public String getInteractionRankSortKey() throws  {
            this.aRA.zzcfk();
            return zzrn("sort_key_irank");
        }

        public long getLastModifiedTime() throws  {
            this.aRA.zzcfk();
            return zzro("last_modified");
        }

        public String getLoggingId1() throws  {
            this.aRA.zzcfk();
            return zzrn(PeopleEmail.LOGGING_ID_1);
        }

        public String getLoggingId2() throws  {
            this.aRA.zzcfk();
            return zzrn(PeopleEmail.LOGGING_ID_2);
        }

        public String getLoggingId3() throws  {
            this.aRA.zzcfk();
            return zzrn(PeopleEmail.LOGGING_ID_3);
        }

        public String getLoggingId4() throws  {
            this.aRA.zzcfk();
            return zzrn(PeopleEmail.LOGGING_ID_4);
        }

        public String getLoggingId5() throws  {
            this.aRA.zzcfk();
            return zzrn(PeopleEmail.LOGGING_ID_5);
        }

        public String getName() throws  {
            this.aRA.zzcfk();
            if (hasPlusPerson()) {
                return zzrn("name");
            }
            this.aRA.aRs.moveToPosition(this.aRA.aRu.get(this.GM, 0));
            return this.aRA.aRs.getString(1);
        }

        public String getNameSortKey() throws  {
            this.aRA.zzcfk();
            return zzrn("sort_key");
        }

        public String getOwnerAccountName() throws  {
            this.aRA.zzcfk();
            return hasPlusPerson() ? this.aRA.aRr.zzava().getString("account") : null;
        }

        public String getOwnerPlusPageId() throws  {
            this.aRA.zzcfk();
            return hasPlusPerson() ? this.aRA.aRr.zzava().getString("pagegaiaid") : null;
        }

        public Iterable<PhoneNumber> getPhoneNumbers() throws  {
            this.aRA.zzcfk();
            if (zzq.zzre(getQualifiedId())) {
                return PhoneNumber.EMPTY_PHONES;
            }
            zzcfn();
            return this.aRE;
        }

        @Deprecated
        public String getPlusPageGaiaId() throws  {
            this.aRA.zzcfk();
            return getOwnerPlusPageId();
        }

        public int getProfileType() throws  {
            this.aRA.zzcfk();
            return zzrp("profile_type");
        }

        public String getQualifiedId() throws  {
            this.aRA.zzcfk();
            return zzrn("qualified_id");
        }

        public long getRowId() throws  {
            throw new UnsupportedOperationException();
        }

        public boolean hasContact() throws  {
            this.aRA.zzcfk();
            return zzcfm() > 0;
        }

        public boolean hasPlusPerson() throws  {
            this.aRA.zzcfk();
            return zzcfl() > 0;
        }

        public int hashCode() throws  {
            return (zzcfo().hashCode() * 31) + this.GM;
        }

        public boolean isBlocked() throws  {
            this.aRA.zzcfk();
            return zzrp("blocked") != 0;
        }

        public boolean isNameVerified() throws  {
            this.aRA.zzcfk();
            return zzrp("name_verified") != 0;
        }
    }

    public zza(@Signature({"(", "Lcom/google/android/gms/common/data/DataHolder;", "Landroid/database/Cursor;", "Landroid/content/Context;", "I", "Lcom/google/android/gms/people/internal/zzi;", "Lcom/google/android/gms/people/internal/zzi;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;I", "Landroid/os/Bundle;", "Landroid/os/Bundle;", ")V"}) DataHolder $r1, @Signature({"(", "Lcom/google/android/gms/common/data/DataHolder;", "Landroid/database/Cursor;", "Landroid/content/Context;", "I", "Lcom/google/android/gms/people/internal/zzi;", "Lcom/google/android/gms/people/internal/zzi;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;I", "Landroid/os/Bundle;", "Landroid/os/Bundle;", ")V"}) Cursor $r2, @Signature({"(", "Lcom/google/android/gms/common/data/DataHolder;", "Landroid/database/Cursor;", "Landroid/content/Context;", "I", "Lcom/google/android/gms/people/internal/zzi;", "Lcom/google/android/gms/people/internal/zzi;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;I", "Landroid/os/Bundle;", "Landroid/os/Bundle;", ")V"}) Context $r3, @Signature({"(", "Lcom/google/android/gms/common/data/DataHolder;", "Landroid/database/Cursor;", "Landroid/content/Context;", "I", "Lcom/google/android/gms/people/internal/zzi;", "Lcom/google/android/gms/people/internal/zzi;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;I", "Landroid/os/Bundle;", "Landroid/os/Bundle;", ")V"}) int $i0, @Signature({"(", "Lcom/google/android/gms/common/data/DataHolder;", "Landroid/database/Cursor;", "Landroid/content/Context;", "I", "Lcom/google/android/gms/people/internal/zzi;", "Lcom/google/android/gms/people/internal/zzi;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;I", "Landroid/os/Bundle;", "Landroid/os/Bundle;", ")V"}) zzi $r4, @Signature({"(", "Lcom/google/android/gms/common/data/DataHolder;", "Landroid/database/Cursor;", "Landroid/content/Context;", "I", "Lcom/google/android/gms/people/internal/zzi;", "Lcom/google/android/gms/people/internal/zzi;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;I", "Landroid/os/Bundle;", "Landroid/os/Bundle;", ")V"}) zzi $r5, @Signature({"(", "Lcom/google/android/gms/common/data/DataHolder;", "Landroid/database/Cursor;", "Landroid/content/Context;", "I", "Lcom/google/android/gms/people/internal/zzi;", "Lcom/google/android/gms/people/internal/zzi;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;I", "Landroid/os/Bundle;", "Landroid/os/Bundle;", ")V"}) ArrayList<String> $r6, @Signature({"(", "Lcom/google/android/gms/common/data/DataHolder;", "Landroid/database/Cursor;", "Landroid/content/Context;", "I", "Lcom/google/android/gms/people/internal/zzi;", "Lcom/google/android/gms/people/internal/zzi;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;I", "Landroid/os/Bundle;", "Landroid/os/Bundle;", ")V"}) HashMap<String, String> $r7, @Signature({"(", "Lcom/google/android/gms/common/data/DataHolder;", "Landroid/database/Cursor;", "Landroid/content/Context;", "I", "Lcom/google/android/gms/people/internal/zzi;", "Lcom/google/android/gms/people/internal/zzi;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;I", "Landroid/os/Bundle;", "Landroid/os/Bundle;", ")V"}) int $i1, @Signature({"(", "Lcom/google/android/gms/common/data/DataHolder;", "Landroid/database/Cursor;", "Landroid/content/Context;", "I", "Lcom/google/android/gms/people/internal/zzi;", "Lcom/google/android/gms/people/internal/zzi;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;I", "Landroid/os/Bundle;", "Landroid/os/Bundle;", ")V"}) Bundle bundle, @Signature({"(", "Lcom/google/android/gms/common/data/DataHolder;", "Landroid/database/Cursor;", "Landroid/content/Context;", "I", "Lcom/google/android/gms/people/internal/zzi;", "Lcom/google/android/gms/people/internal/zzi;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;I", "Landroid/os/Bundle;", "Landroid/os/Bundle;", ")V"}) Bundle $r9) throws  {
        boolean $z0 = true;
        super($r1);
        zzab.zzag($r1);
        zzab.zzag($r2);
        zzab.zzag($r7);
        zzab.zzbn($i0 == $r4.size());
        zzab.zzbn($i0 == $r5.size());
        zzab.zzbn($i0 == $r6.size());
        this.aRr = $r1;
        this.aRs = $r2;
        this.aRq = $i0;
        this.aRv = $r6;
        this.mContext = $r3;
        this.aRw = $r7;
        this.aRx = new zzb(this, this.mContext.getResources()) {
            final /* synthetic */ zza aRA;

            protected String zza(Resources $r1, int $i0) throws  {
                return (String) Email.getTypeLabel($r1, $i0, null);
            }
        };
        this.aRy = new zzb(this, this.mContext.getResources()) {
            final /* synthetic */ zza aRA;

            protected String zza(Resources $r1, int $i0) throws  {
                return (String) Phone.getTypeLabel($r1, $i0, null);
            }
        };
        this.aRt = $r4;
        this.aRu = $r5;
        if (($i1 & 1) != 0) {
            zzp.zzao("PeopleAggregator", "PeopleExtraColumnBitmask.EMAILS is not supported in aggregation.  Ignored.");
        }
        if (($i1 & 2) == 0) {
            $z0 = false;
        }
        this.aRz = $z0;
        this.aMk = new PhoneDecoder($r9);
    }

    private void zzcfk() throws  {
        if (this.lx) {
            throw new IllegalStateException("Already released");
        }
    }

    public /* synthetic */ Object get(int $i0) throws  {
        return zzacn($i0);
    }

    public int getCount() throws  {
        zzcfk();
        return this.aRq;
    }

    public void release() throws  {
        if (!this.lx) {
            this.lx = true;
            this.aRr.close();
            this.aRs.close();
            this.aRr = null;
            this.aRs = null;
            this.aRt = null;
            this.aRu = null;
            this.aRv = null;
            this.aRw = null;
            this.mContext = null;
            this.aRx = null;
            this.aRy = null;
            this.aMk = null;
        }
    }

    public AggregatedPerson zzacn(int $i0) throws  {
        zzcfk();
        return new zza(this, $i0);
    }
}
