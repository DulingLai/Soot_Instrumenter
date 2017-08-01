package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.people.Autocomplete.PersonMetadata;

/* compiled from: dalvik_source_com.waze.apk */
public class PersonMetadataImpl extends AbstractSafeParcelable implements PersonMetadata {
    public static final Creator<PersonMetadataImpl> CREATOR = new zzo();
    final String aPS;
    final int mVersionCode;

    PersonMetadataImpl(int $i0, String $r1) throws  {
        this.mVersionCode = $i0;
        this.aPS = $r1;
    }

    public String getOwnerId() throws  {
        return this.aPS;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzo.zza(this, $r1, $i0);
    }
}
