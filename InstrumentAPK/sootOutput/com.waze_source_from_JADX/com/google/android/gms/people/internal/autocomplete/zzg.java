package com.google.android.gms.people.internal.autocomplete;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.people.Autocomplete.ContactPreferredFields;

/* compiled from: dalvik_source_com.waze.apk */
public class zzg extends zzc implements ContactPreferredFields {
    public static final String[] aki = new String[]{"email", "name"};

    public zzg(DataHolder $r1, int $i0) throws  {
        super($r1, $i0);
    }

    public /* synthetic */ Object freeze() throws  {
        return zzcgd();
    }

    public CharSequence getEmail() throws  {
        return getString("email");
    }

    public CharSequence getName() throws  {
        return getString("name");
    }

    public ContactPreferredFields zzcgd() throws  {
        return new ContactPreferredFieldsEntity(this);
    }
}
