package com.google.android.gms.people.internal;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.people.PeopleConstants.Endpoints;
import com.google.android.gms.people.model.AutocompleteBuffer;
import com.google.android.gms.people.model.AutocompleteEntry;
import com.google.android.gms.people.model.AvatarReference;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb extends zzc implements AutocompleteEntry {
    private final Bundle GV;
    private final AutocompleteBuffer aQe;

    public zzb(AutocompleteBuffer $r1, DataHolder $r2, int $i0, Bundle $r3) throws  {
        super($r2, $i0);
        this.GV = $r3;
        this.aQe = $r1;
    }

    public long getAndroidContactDataId() throws  {
        return getLong("cp2_data_id");
    }

    public long getAndroidContactId() throws  {
        return getLong("cp2_contact_id");
    }

    public int getAutocompleteItemType() throws  {
        return getInteger("item_type");
    }

    public AvatarReference getAvatarReference() throws  {
        String $r1 = getString("avatar_location");
        return TextUtils.isEmpty($r1) ? null : new AvatarReference(getInteger("avatar_source"), $r1);
    }

    public int getDataSource() throws  {
        return getInteger("data_source");
    }

    public String getFocusContactId() throws  {
        return getString("contact_id");
    }

    public String getGaiaId() throws  {
        return getString(Endpoints.KEY_TARGET_GAIA_ID);
    }

    public double getItemAffinity1() throws  {
        return getDouble("item_affinity1");
    }

    public double getItemAffinity2() throws  {
        return getDouble("item_affinity2");
    }

    public double getItemAffinity3() throws  {
        return getDouble("item_affinity3");
    }

    public double getItemAffinity4() throws  {
        return getDouble("item_affinity4");
    }

    public double getItemAffinity5() throws  {
        return getDouble("item_affinity5");
    }

    public long getItemCertificateExpirationMillis() throws  {
        return getLong("item_certificate_expiration_millis");
    }

    public String getItemCertificateStatus() throws  {
        return getString("item_certificate_status");
    }

    public String getItemLoggingId1() throws  {
        return getString("item_logging_id1");
    }

    public String getItemLoggingId2() throws  {
        return getString("item_logging_id2");
    }

    public String getItemLoggingId3() throws  {
        return getString("item_logging_id3");
    }

    public String getItemLoggingId4() throws  {
        return getString("item_logging_id4");
    }

    public String getItemLoggingId5() throws  {
        return getString("item_logging_id5");
    }

    public String getItemValue() throws  {
        return getString("value");
    }

    public String getItemValueCustomLabel() throws  {
        return getString("custom_label");
    }

    public int getItemValueType() throws  {
        return getInteger("value_type");
    }

    public String getOwnerAccountName() throws  {
        return getString("owner_account");
    }

    public String getOwnerPlusPageId() throws  {
        return getString("owner_page_id");
    }

    public String getPeopleV2Id() throws  {
        return getString("people_v2_id");
    }

    public double getPersonAffinity1() throws  {
        return getDouble("person_affinity1");
    }

    public double getPersonAffinity2() throws  {
        return getDouble("person_affinity2");
    }

    public double getPersonAffinity3() throws  {
        return getDouble("person_affinity3");
    }

    public double getPersonAffinity4() throws  {
        return getDouble("person_affinity4");
    }

    public double getPersonAffinity5() throws  {
        return getDouble("person_affinity5");
    }

    public String getPersonDisplayName() throws  {
        return getString("display_name");
    }

    public String getPersonKey() throws  {
        return getString("person_key");
    }

    public String getPersonLoggingId1() throws  {
        return getString("person_logging_id1");
    }

    public String getPersonLoggingId2() throws  {
        return getString("person_logging_id2");
    }

    public String getPersonLoggingId3() throws  {
        return getString("person_logging_id3");
    }

    public String getPersonLoggingId4() throws  {
        return getString("person_logging_id4");
    }

    public String getPersonLoggingId5() throws  {
        return getString("person_logging_id5");
    }

    public double getPrimarySortedAffinity() throws  {
        return getDouble("primary_affinity_sorted");
    }

    public String getPrimarySortedLoggingId() throws  {
        return getString("primary_logging_id_sorted");
    }

    public int getRowIndex() throws  {
        return zzavc();
    }

    public double getSortedItemAffinity() throws  {
        return getDouble("item_affinity_sorted");
    }

    public String getSortedItemLoggingId() throws  {
        return getString("item_logging_id_sorted");
    }

    public double getSortedPersonAffinity() throws  {
        return getDouble("person_affinity_sorted");
    }

    public String getSortedPersonLoggingId() throws  {
        return getString("person_logging_id_sorted");
    }
}
