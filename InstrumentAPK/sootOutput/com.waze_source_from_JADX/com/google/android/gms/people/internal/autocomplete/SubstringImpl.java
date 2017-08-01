package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.people.Autocomplete.Substring;

/* compiled from: dalvik_source_com.waze.apk */
public class SubstringImpl extends AbstractSafeParcelable implements Substring {
    public static final Creator<SubstringImpl> CREATOR = new zzr();
    final int aUD;
    final int mLength;
    final int mVersionCode;

    SubstringImpl(int $i0, int $i1, int $i2) throws  {
        this.mVersionCode = $i0;
        this.aUD = $i1;
        this.mLength = $i2;
    }

    public int getLength() throws  {
        return this.mLength;
    }

    public int getStartIndex() throws  {
        return this.aUD;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzr.zza(this, $r1, $i0);
    }
}
