package com.google.android.gms.people.internal;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.people.model.Circle;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzc extends com.google.android.gms.common.data.zzc implements Circle {
    private final Bundle GV;

    public zzc(DataHolder $r1, int $i0, Bundle $r2) throws  {
        super($r1, $i0);
        this.GV = $r2;
    }

    @Deprecated
    public String getAccountName() throws  {
        return getOwnerAccountName();
    }

    public String getCircleId() throws  {
        return getString("circle_id");
    }

    public String getCircleName() throws  {
        int $i0 = getCircleType();
        if ($i0 != -1) {
            Bundle $r1 = this.GV.getBundle("localized_group_names");
            if ($r1 != null) {
                String $r2 = $r1.getString(String.valueOf($i0));
                if (!TextUtils.isEmpty($r2)) {
                    return $r2;
                }
            }
        }
        return getString("name");
    }

    public int getCircleType() throws  {
        int $i0 = getInteger("type");
        int $i1 = $i0;
        switch ($i0) {
            case -1:
            case 1:
            case 2:
            case 3:
            case 4:
                return $i1;
            case 0:
                break;
            default:
                break;
        }
        return -2;
    }

    public long getLastModifiedTime() throws  {
        return getLong("last_modified");
    }

    public String getOwnerAccountName() throws  {
        return this.GV.getString("account");
    }

    public String getOwnerPlusPageId() throws  {
        return this.GV.getString("pagegaiaid");
    }

    public int getPeopleCount() throws  {
        return getInteger("people_count");
    }

    @Deprecated
    public String getPlusPageGaiaId() throws  {
        return getOwnerPlusPageId();
    }

    public long getRowId() throws  {
        return getLong("_id");
    }

    public String getSortKey() throws  {
        return getString("sort_key");
    }

    public int getVisibility() throws  {
        Bundle $r1 = this.GV.getBundle("circlevisibility");
        return $r1 == null ? 0 : $r1.containsKey(getCircleId()) ? $r1.getInt(getCircleId()) : 0;
    }

    public boolean isEnabledForSharing() throws  {
        return getBoolean("for_sharing");
    }

    public boolean isSyncToContactsEnabled() throws  {
        return getBoolean("sync_to_contacts");
    }
}
