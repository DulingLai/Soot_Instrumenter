package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.people.Autocomplete.DisplayableField;
import com.google.android.gms.people.Autocomplete.Substring;

/* compiled from: dalvik_source_com.waze.apk */
public class DisplayableFieldImpl extends AbstractSafeParcelable implements DisplayableField {
    public static final Creator<DisplayableFieldImpl> CREATOR = new zzh();
    final SubstringImpl[] aUt;
    final int bG;
    final int mIndex;
    final String mValue;
    final int mVersionCode;

    DisplayableFieldImpl(int $i0, int $i1, int $i2, String $r1, SubstringImpl[] $r2) throws  {
        this.mVersionCode = $i0;
        this.bG = $i1;
        this.mIndex = $i2;
        this.mValue = $r1;
        this.aUt = $r2;
    }

    public int getIndex() throws  {
        return this.mIndex;
    }

    public Substring[] getMatchingSubstrings() throws  {
        return this.aUt;
    }

    public int getType() throws  {
        return this.bG;
    }

    public CharSequence getValue() throws  {
        return this.mValue;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzh.zza(this, $r1, $i0);
    }
}
