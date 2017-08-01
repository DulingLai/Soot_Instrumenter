package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.people.Autocomplete.Autocompletion;
import com.google.android.gms.people.Autocomplete.ContactGroup;
import com.google.android.gms.people.Autocomplete.DisplayableField;
import com.google.android.gms.people.Autocomplete.Person;

/* compiled from: dalvik_source_com.waze.apk */
public class AutocompletionImpl extends AbstractSafeParcelable implements Autocompletion {
    public static final Creator<AutocompletionImpl> CREATOR = new zzb();
    final int aUk;
    final PersonImpl aUl;
    final ContactGroupImpl aUm;
    final DisplayableFieldImpl[] aUn;
    final int mVersionCode;

    AutocompletionImpl(int $i0, int $i1, PersonImpl $r1, ContactGroupImpl $r2, DisplayableFieldImpl[] $r3) throws  {
        this.mVersionCode = $i0;
        this.aUk = $i1;
        this.aUn = $r3;
        this.aUl = $r1;
        this.aUm = $r2;
    }

    public ContactGroup getContactGroup() throws  {
        return this.aUm;
    }

    public DisplayableField[] getMatches() throws  {
        return this.aUn;
    }

    public int getObjectType() throws  {
        return this.aUk;
    }

    public Person getPerson() throws  {
        return this.aUl;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzb.zza(this, $r1, $i0);
    }
}
