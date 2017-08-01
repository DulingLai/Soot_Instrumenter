package com.google.android.gms.auth.firstparty.proximity.data;

import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import com.waze.strings.DisplayStrings;
import java.util.Arrays;

/* compiled from: dalvik_source_com.waze.apk */
public class AuthorizationRequest extends AbstractSafeParcelable {
    public static final AuthorizationRequestCreator CREATOR = new AuthorizationRequestCreator();
    final byte[] mData;
    final String mPermitAccessId;
    final String mPermitId;
    final int mVersion;

    AuthorizationRequest(int $i0, String $r1, String $r2, byte[] $r3) throws  {
        this.mVersion = $i0;
        this.mPermitId = zzab.zzgy($r1);
        this.mPermitAccessId = zzab.zzgy($r2);
        this.mData = (byte[]) zzab.zzag($r3);
    }

    public AuthorizationRequest(String $r1, String $r2, byte[] $r3) throws  {
        this(1, $r1, $r2, $r3);
    }

    public boolean equals(Object $r1) throws  {
        boolean $z0 = true;
        if ($r1 == null) {
            return false;
        }
        if ($r1 == this) {
            return true;
        }
        if (!($r1 instanceof AuthorizationRequest)) {
            return false;
        }
        AuthorizationRequest $r2 = (AuthorizationRequest) $r1;
        if (!(TextUtils.equals(this.mPermitId, $r2.mPermitId) && TextUtils.equals(this.mPermitAccessId, $r2.mPermitAccessId) && Arrays.equals(this.mData, $r2.mData))) {
            $z0 = false;
        }
        return $z0;
    }

    public byte[] getData() throws  {
        return this.mData;
    }

    public String getPermitAccessId() throws  {
        return this.mPermitAccessId;
    }

    public String getPermitId() throws  {
        return this.mPermitId;
    }

    public int hashCode() throws  {
        return ((((this.mPermitId.hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + this.mPermitAccessId.hashCode()) * 31) + Arrays.hashCode(this.mData);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        AuthorizationRequestCreator.zza(this, $r1, $i0);
    }
}
