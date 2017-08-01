package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.people.Autocomplete.ContactGroup;
import com.google.android.gms.people.Autocomplete.ContactGroupId;
import com.google.android.gms.people.Autocomplete.ContactGroupName;
import com.google.android.gms.people.Autocomplete.GroupExtendedData;

/* compiled from: dalvik_source_com.waze.apk */
public class ContactGroupImpl extends AbstractSafeParcelable implements ContactGroup {
    public static final Creator<ContactGroupImpl> CREATOR = new zzd();
    final ContactGroupIdImpl aUp;
    final ContactGroupNameImpl aUq;
    final GroupExtendedDataImpl aUr;
    final int aUs;
    final int mVersionCode;

    ContactGroupImpl(int $i0, ContactGroupIdImpl $r1, ContactGroupNameImpl $r2, GroupExtendedDataImpl $r3, int $i1) throws  {
        this.mVersionCode = $i0;
        this.aUq = $r2;
        this.aUp = $r1;
        this.aUr = $r3;
        this.aUs = $i1;
    }

    public GroupExtendedData getExtendedData() throws  {
        return this.aUr;
    }

    public ContactGroupId getId() throws  {
        return this.aUp;
    }

    public int getMemberCount() throws  {
        return this.aUs;
    }

    public ContactGroupName getName() throws  {
        return this.aUq;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzd.zza(this, $r1, $i0);
    }
}
