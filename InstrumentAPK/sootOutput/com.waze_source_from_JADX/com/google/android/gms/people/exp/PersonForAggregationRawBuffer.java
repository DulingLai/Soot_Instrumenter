package com.google.android.gms.people.exp;

import android.text.TextUtils;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.people.PeopleConstants.Endpoints;
import com.google.android.gms.people.PeopleConstants.PeopleEmail;
import com.google.android.gms.people.internal.agg.PhoneEmailDecoder.EmailDecoder;
import com.google.android.gms.people.internal.agg.PhoneEmailDecoder.PhoneDecoder;
import com.google.android.gms.people.internal.zzm;
import com.google.android.gms.people.internal.zzq;
import com.google.android.gms.people.model.EmailAddress;
import com.google.android.gms.people.model.Person;
import com.google.android.gms.people.model.PhoneNumber;

/* compiled from: dalvik_source_com.waze.apk */
public class PersonForAggregationRawBuffer extends zza implements Person {
    private final PhoneDecoder aMk;
    private final EmailDecoder aMl;
    private final boolean aMm;

    public PersonForAggregationRawBuffer(DataHolder $r1, PhoneDecoder $r2, EmailDecoder $r3) throws  {
        super($r1);
        this.aMk = $r2;
        this.aMl = $r3;
        this.aMm = $r1.zzava().getBoolean("emails_with_affinities", false);
    }

    @Deprecated
    public String getAccountName() throws  {
        return getOwnerAccountName();
    }

    public double getAffinity1() throws  {
        return getDouble(PeopleEmail.AFFINITY_1);
    }

    public double getAffinity2() throws  {
        return getDouble(PeopleEmail.AFFINITY_2);
    }

    public double getAffinity3() throws  {
        return getDouble(PeopleEmail.AFFINITY_3);
    }

    public double getAffinity4() throws  {
        return getDouble(PeopleEmail.AFFINITY_4);
    }

    public double getAffinity5() throws  {
        return getDouble(PeopleEmail.AFFINITY_5);
    }

    public String getAvatarUrl() throws  {
        return zzm.aQh.zzqx(getString("avatar"));
    }

    public String[] getBelongingCircleIds() throws  {
        String $r1 = getString("v_circle_ids");
        return TextUtils.isEmpty($r1) ? zzq.aQW : zzq.aQX.split($r1, -1);
    }

    public Iterable<EmailAddress> getEmailAddresses() throws  {
        return this.aMl.decode(getEncodedEmails(), this.aMm);
    }

    public String getEncodedEmails() throws  {
        return getString("v_emails");
    }

    public String getEncodedPhones() throws  {
        return getString("v_phones");
    }

    public String getFamilyName() throws  {
        return getString("family_name");
    }

    public String getGaiaId() throws  {
        return getString(Endpoints.KEY_TARGET_GAIA_ID);
    }

    public String getGivenName() throws  {
        return getString("given_name");
    }

    public int getInViewerDomain() throws  {
        return getInteger("in_viewer_domain");
    }

    public String getInteractionRankSortKey() throws  {
        return getString("sort_key_irank");
    }

    public long getLastModifiedTime() throws  {
        return getLong("last_modified");
    }

    public String getLoggingId1() throws  {
        return getString(PeopleEmail.LOGGING_ID_1);
    }

    public String getLoggingId2() throws  {
        return getString(PeopleEmail.LOGGING_ID_2);
    }

    public String getLoggingId3() throws  {
        return getString(PeopleEmail.LOGGING_ID_3);
    }

    public String getLoggingId4() throws  {
        return getString(PeopleEmail.LOGGING_ID_4);
    }

    public String getLoggingId5() throws  {
        return getString(PeopleEmail.LOGGING_ID_5);
    }

    public String getName() throws  {
        return getString("name");
    }

    public String getNameSortKey() throws  {
        return getString("sort_key");
    }

    public String getOwnerAccountName() throws  {
        return zzava().getString("account");
    }

    public String getOwnerPlusPageId() throws  {
        return zzava().getString("pagegaiaid");
    }

    public String[] getPeopleInCommon() throws  {
        return zzq.zzqz(getString("people_in_common"));
    }

    public Iterable<PhoneNumber> getPhoneNumbers() throws  {
        return this.aMk.decode(getEncodedPhones(), false);
    }

    @Deprecated
    public String getPlusPageGaiaId() throws  {
        return getOwnerPlusPageId();
    }

    public int getProfileType() throws  {
        return getInteger("profile_type");
    }

    public String getQualifiedId() throws  {
        return getString("qualified_id");
    }

    public long getRowId() throws  {
        return getLong("_id");
    }

    public boolean isBlocked() throws  {
        return getInteger("blocked") != 0;
    }

    public boolean isNameVerified() throws  {
        return getInteger("name_verified") != 0;
    }
}
