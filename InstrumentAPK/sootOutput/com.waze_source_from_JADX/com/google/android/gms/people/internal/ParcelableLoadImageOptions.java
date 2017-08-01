package com.google.android.gms.people.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.people.Images.LoadImageOptions;

/* compiled from: dalvik_source_com.waze.apk */
public class ParcelableLoadImageOptions extends AbstractSafeParcelable {
    public static final Creator<ParcelableLoadImageOptions> CREATOR = new zzk();
    private final int aLA;
    private final int aLZ;
    private final boolean mUseLargePictureForCp2Images;
    private final int mVersionCode;

    ParcelableLoadImageOptions(int $i0, int $i1, int $i2, boolean $z0) throws  {
        this.mVersionCode = $i0;
        this.aLA = $i1;
        this.aLZ = $i2;
        this.mUseLargePictureForCp2Images = $z0;
    }

    public static ParcelableLoadImageOptions zza(LoadImageOptions $r1) throws  {
        if ($r1 == null) {
            $r1 = LoadImageOptions.DEFAULT;
        }
        return new ParcelableLoadImageOptions(1, $r1.imageSize, $r1.avatarOptions, $r1.useLargePictureForCp2Images);
    }

    public int getImageSize() throws  {
        return this.aLA;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public String toString() throws  {
        return zzaa.zzaf(this).zzh("imageSize", Integer.valueOf(this.aLA)).zzh("avatarOptions", Integer.valueOf(this.aLZ)).zzh("useLargePictureForCp2Images", Boolean.valueOf(this.mUseLargePictureForCp2Images)).toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzk.zza(this, $r1, $i0);
    }

    public int zzcet() throws  {
        return this.aLZ;
    }

    public boolean zzceu() throws  {
        return this.mUseLargePictureForCp2Images;
    }
}
