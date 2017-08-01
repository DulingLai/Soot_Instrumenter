package com.google.android.gms.people.internal;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.people.PeopleConstants.Endpoints;
import com.google.android.gms.people.model.AccountMetadata;
import com.google.android.gms.people.model.Owner;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzj extends zzc implements Owner {
    public zzj(DataHolder $r1, int $i0) throws  {
        super($r1, $i0);
    }

    private AccountMetadata zzces() throws  {
        Bundle $r2 = (Bundle) this.DW.zzava().getParcelable("account_metadata");
        if ($r2 == null) {
            return null;
        }
        $r2.setClassLoader(getClass().getClassLoader());
        return (AccountMetadata) $r2.getParcelable(getAccountName());
    }

    public Owner freeze() throws  {
        throw new UnsupportedOperationException("Method not supported for object Owner");
    }

    @Deprecated
    public String getAccountGaiaId() throws  {
        return getAccountId();
    }

    public String getAccountId() throws  {
        return getString(Endpoints.KEY_TARGET_GAIA_ID);
    }

    public String getAccountName() throws  {
        return getString("account_name");
    }

    public String getAvatarUrl() throws  {
        return zzm.aQh.zzqx(getString("avatar"));
    }

    public int getCoverPhotoHeight() throws  {
        return getInteger("cover_photo_height");
    }

    public String getCoverPhotoId() throws  {
        return getString("cover_photo_id");
    }

    public String getCoverPhotoUrl() throws  {
        return zzm.aQh.zzqx(getString("cover_photo_url"));
    }

    public int getCoverPhotoWidth() throws  {
        return getInteger("cover_photo_width");
    }

    public String getDasherDomain() throws  {
        return getString("dasher_domain");
    }

    public String getDisplayName() throws  {
        String $r1 = getString("display_name");
        return TextUtils.isEmpty($r1) ? getAccountName() : $r1;
    }

    public String getFamilyName() throws  {
        String $r1 = getString("family_name");
        return TextUtils.isEmpty($r1) ? "null" : $r1;
    }

    public String getGivenName() throws  {
        String $r1 = getString("given_name");
        return TextUtils.isEmpty($r1) ? "null" : $r1;
    }

    public long getLastSuccessfulSyncFinishTimestamp() throws  {
        return getLong("last_successful_sync_time");
    }

    public long getLastSyncFinishTimestamp() throws  {
        return getLong("last_sync_finish_time");
    }

    public long getLastSyncStartTimestamp() throws  {
        return getLong("last_sync_start_time");
    }

    public int getLastSyncStatus() throws  {
        return getInteger("last_sync_status");
    }

    @Deprecated
    public String getPlusPageGaiaId() throws  {
        return getPlusPageId();
    }

    public String getPlusPageId() throws  {
        return getString("page_gaia_id");
    }

    public long getRowId() throws  {
        return getLong("_id");
    }

    public int isDasherAccount() throws  {
        return getInteger("is_dasher");
    }

    public boolean isDataValid() throws  {
        return !this.DW.isClosed();
    }

    public boolean isPeriodicSyncEnabled() throws  {
        AccountMetadata $r1 = zzces();
        return $r1 == null ? false : isPlusPage() ? $r1.isPagePeriodicSyncEnabled : $r1.isSyncEnabled;
    }

    public boolean isPlusEnabled() throws  {
        if (isPlusPage()) {
            return true;
        }
        AccountMetadata $r1 = zzces();
        return $r1 == null ? false : $r1.isPlusEnabled;
    }

    public boolean isPlusPage() throws  {
        return getPlusPageId() != null;
    }

    public boolean isSyncCirclesToContactsEnabled() throws  {
        return getBoolean("sync_circles_to_contacts");
    }

    public boolean isSyncEnabled() throws  {
        AccountMetadata $r1 = zzces();
        return $r1 == null ? false : isPlusPage() ? $r1.isPageTickleSyncEnabled : $r1.isSyncEnabled;
    }

    public boolean isSyncEvergreenToContactsEnabled() throws  {
        return getBoolean("sync_evergreen_to_contacts");
    }

    public boolean isSyncMeToContactsEnabled() throws  {
        return getBoolean("sync_me_to_contacts");
    }

    @Deprecated
    public boolean isSyncToContactsEnabled() throws  {
        return isSyncCirclesToContactsEnabled();
    }
}
