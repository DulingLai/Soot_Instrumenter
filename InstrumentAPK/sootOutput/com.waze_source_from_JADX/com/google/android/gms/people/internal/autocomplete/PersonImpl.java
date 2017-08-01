package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.people.Autocomplete.Email;
import com.google.android.gms.people.Autocomplete.Name;
import com.google.android.gms.people.Autocomplete.Person;
import com.google.android.gms.people.Autocomplete.PersonMetadata;
import com.google.android.gms.people.Autocomplete.Phone;
import com.google.android.gms.people.Autocomplete.Photo;

/* compiled from: dalvik_source_com.waze.apk */
public class PersonImpl extends AbstractSafeParcelable implements Person {
    public static final Creator<PersonImpl> CREATOR = new zzn();
    final PhoneImpl[] aUA;
    final PhotoImpl[] aUB;
    final PersonMetadataImpl aUx;
    final NameImpl[] aUy;
    final EmailImpl[] aUz;
    final int mVersionCode;

    PersonImpl(int $i0, PersonMetadataImpl $r1, NameImpl[] $r2, EmailImpl[] $r3, PhoneImpl[] $r4, PhotoImpl[] $r5) throws  {
        this.mVersionCode = $i0;
        this.aUx = $r1;
        this.aUy = $r2;
        this.aUz = $r3;
        this.aUA = $r4;
        this.aUB = $r5;
    }

    public Email[] getEmails() throws  {
        return this.aUz;
    }

    public PersonMetadata getMetadata() throws  {
        return this.aUx;
    }

    public Name[] getNames() throws  {
        return this.aUy;
    }

    public Phone[] getPhones() throws  {
        return this.aUA;
    }

    public Photo[] getPhotos() throws  {
        return this.aUB;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzn.zza(this, $r1, $i0);
    }
}
