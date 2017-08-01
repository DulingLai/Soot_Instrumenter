package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.people.Autocomplete.Photo;

/* compiled from: dalvik_source_com.waze.apk */
public class PhotoImpl extends AbstractSafeParcelable implements Photo {
    public static final Creator<PhotoImpl> CREATOR = new zzq();
    final String aOw;
    final boolean aUC;
    final int mVersionCode;
    final int zzayf;

    PhotoImpl(int $i0, int $i1, String $r1, boolean $z0) throws  {
        this.mVersionCode = $i0;
        this.zzayf = $i1;
        this.aOw = $r1;
        this.aUC = $z0;
    }

    public String getLocation() throws  {
        return this.aOw;
    }

    public int getSource() throws  {
        return this.zzayf;
    }

    public boolean isDefault() throws  {
        return this.aUC;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzq.zza(this, $r1, $i0);
    }
}
