package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.people.Autocomplete.ContactPreferredFields;
import com.google.android.gms.people.Autocomplete.GroupExtendedData;

/* compiled from: dalvik_source_com.waze.apk */
public class GroupExtendedDataImpl extends AbstractSafeParcelable implements GroupExtendedData {
    public static final Creator<GroupExtendedDataImpl> CREATOR = new zzj();
    final ContactPreferredFieldsEntity[] aUu;
    final int mVersionCode;

    GroupExtendedDataImpl(int $i0, ContactPreferredFieldsEntity[] $r1) throws  {
        this.mVersionCode = $i0;
        this.aUu = $r1;
    }

    public ContactPreferredFields[] getContactPreferences() throws  {
        return this.aUu;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzj.zza(this, $r1, $i0);
    }
}
