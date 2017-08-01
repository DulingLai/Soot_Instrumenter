package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.people.Autocomplete.ContactPreferredFields;

/* compiled from: dalvik_source_com.waze.apk */
public class ContactPreferredFieldsEntity extends AbstractSafeParcelable implements ContactPreferredFields {
    public static final Creator<ContactPreferredFieldsEntity> CREATOR = new zzf();
    final String fw;
    final String mName;
    final int mVersionCode;

    ContactPreferredFieldsEntity(int $i0, String $r1, String $r2) throws  {
        this.mVersionCode = $i0;
        this.mName = $r2;
        this.fw = $r1;
    }

    public ContactPreferredFieldsEntity(zzg $r1) throws  {
        this(zzc($r1.getEmail()), zzc($r1.getName()));
    }

    public ContactPreferredFieldsEntity(String $r1, String $r2) throws  {
        this(1, $r1, $r2);
    }

    private static String zzc(CharSequence $r0) throws  {
        return $r0 != null ? $r0.toString() : null;
    }

    public /* synthetic */ Object freeze() throws  {
        return zzcgd();
    }

    public CharSequence getEmail() throws  {
        return this.fw;
    }

    public CharSequence getName() throws  {
        return this.mName;
    }

    public boolean isDataValid() throws  {
        return true;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzf.zza(this, $r1, $i0);
    }

    public ContactPreferredFields zzcgd() throws  {
        return this;
    }
}
