package com.google.android.gms.auth.firstparty.proximity.data;

import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import com.waze.strings.DisplayStrings;
import java.util.Arrays;

/* compiled from: dalvik_source_com.waze.apk */
public class PermitAccess extends AbstractSafeParcelable {
    public static final PermitAccessCreator CREATOR = new PermitAccessCreator();
    public static final String TYPE_AES = "AES";
    public static final String TYPE_AUTHZEN_PUBLIC_KEY = "AUTHZEN_PUBLIC_KEY";
    final boolean ih;
    final boolean ii;
    final boolean ij;
    final byte[] mData;
    final String mName;
    final int mVersion;
    final String zzbgd;
    final String zzcft;

    PermitAccess(int $i0, String $r1, String $r2, byte[] $r3, String $r4, boolean $z0, boolean $z1, boolean $z2) throws  {
        this.mVersion = $i0;
        this.zzbgd = zzab.zzgy($r1);
        this.zzcft = zzab.zzgy($r2);
        this.mData = (byte[]) zzab.zzag($r3);
        if ($r4 == null) {
            $r4 = "";
        }
        this.mName = $r4;
        this.ih = $z0;
        this.ii = $z1;
        this.ij = $z2;
    }

    public PermitAccess(String $r1, String $r2, byte[] $r3, String $r4, boolean $z0, boolean $z1, boolean $z2) throws  {
        this(2, $r1, $r2, $r3, $r4, $z0, $z1, $z2);
    }

    public boolean equals(Object $r1) throws  {
        boolean $z0 = true;
        if ($r1 == null) {
            return false;
        }
        if ($r1 == this) {
            return true;
        }
        if (!($r1 instanceof PermitAccess)) {
            return false;
        }
        PermitAccess $r2 = (PermitAccess) $r1;
        if (!(TextUtils.equals(this.zzbgd, $r2.zzbgd) && TextUtils.equals(this.zzcft, $r2.zzcft) && Arrays.equals(this.mData, $r2.mData) && TextUtils.equals(this.mName, $r2.mName) && this.ih == $r2.ih && this.ii == $r2.ii && this.ij == $r2.ij)) {
            $z0 = false;
        }
        return $z0;
    }

    public byte[] getData() throws  {
        return this.mData;
    }

    public String getId() throws  {
        return this.zzbgd;
    }

    public String getName() throws  {
        return this.mName;
    }

    public String getType() throws  {
        return this.zzcft;
    }

    public int hashCode() throws  {
        byte $b0 = (byte) 1;
        int $i1 = ((this.ii ? (byte) 1 : (byte) 0) + (((this.ih ? (byte) 1 : (byte) 0) + ((((((((this.zzbgd.hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + this.zzcft.hashCode()) * 31) + Arrays.hashCode(this.mData)) * 31) + this.mName.hashCode()) * 31)) * 31)) * 31;
        if (!this.ij) {
            $b0 = (byte) 0;
        }
        return $i1 + $b0;
    }

    public boolean isMobileHotspotSupported() throws  {
        return this.ij;
    }

    public boolean isUnlockKey() throws  {
        return this.ii;
    }

    public boolean isUnlockable() throws  {
        return this.ih;
    }

    public String toString() throws  {
        return String.format("PermitAccess{mVersion=%d, mId='%s', mType='%s', mName='%s',mIsUnlockable=%b, mIsUnlockKey=%b, mIsMobileHotspotSupported=%b}", new Object[]{Integer.valueOf(this.mVersion), this.zzbgd, this.zzcft, this.mName, Boolean.valueOf(this.ih), Boolean.valueOf(this.ii), Boolean.valueOf(this.ij)});
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        PermitAccessCreator.zza(this, $r1, $i0);
    }
}
