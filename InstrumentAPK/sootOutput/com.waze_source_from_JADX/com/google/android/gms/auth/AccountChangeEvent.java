package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountChangeEvent extends AbstractSafeParcelable {
    public static final Creator<AccountChangeEvent> CREATOR = new zza();
    final String dL;
    final int dM;
    final int dN;
    final String dO;
    final long mId;
    final int mVersion;

    AccountChangeEvent(int $i0, long $l1, String $r1, int $i2, int $i3, String $r2) throws  {
        this.mVersion = $i0;
        this.mId = $l1;
        this.dL = (String) zzab.zzag($r1);
        this.dM = $i2;
        this.dN = $i3;
        this.dO = $r2;
    }

    public AccountChangeEvent(long $l0, String $r1, int $i1, int $i2, String $r2) throws  {
        this.mVersion = 1;
        this.mId = $l0;
        this.dL = (String) zzab.zzag($r1);
        this.dM = $i1;
        this.dN = $i2;
        this.dO = $r2;
    }

    public boolean equals(Object $r1) throws  {
        if ($r1 == this) {
            return true;
        }
        if (!($r1 instanceof AccountChangeEvent)) {
            return false;
        }
        AccountChangeEvent $r2 = (AccountChangeEvent) $r1;
        return this.mVersion == $r2.mVersion && this.mId == $r2.mId && zzaa.equal(this.dL, $r2.dL) && this.dM == $r2.dM && this.dN == $r2.dN && zzaa.equal(this.dO, $r2.dO);
    }

    public String getAccountName() throws  {
        return this.dL;
    }

    public String getChangeData() throws  {
        return this.dO;
    }

    public int getChangeType() throws  {
        return this.dM;
    }

    public int getEventIndex() throws  {
        return this.dN;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(Integer.valueOf(this.mVersion), Long.valueOf(this.mId), this.dL, Integer.valueOf(this.dM), Integer.valueOf(this.dN), this.dO);
    }

    public String toString() throws  {
        String $r2 = "UNKNOWN";
        switch (this.dM) {
            case 1:
                $r2 = "ADDED";
                break;
            case 2:
                $r2 = "REMOVED";
                break;
            case 3:
                $r2 = "RENAMED_FROM";
                break;
            case 4:
                $r2 = "RENAMED_TO";
                break;
            default:
                break;
        }
        String $r3 = this.dL;
        String $r1 = this.dO;
        return new StringBuilder(((String.valueOf($r3).length() + 91) + String.valueOf($r2).length()) + String.valueOf($r1).length()).append("AccountChangeEvent {accountName = ").append($r3).append(", changeType = ").append($r2).append(", changeData = ").append($r1).append(", eventIndex = ").append(this.dN).append("}").toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zza.zza(this, $r1, $i0);
    }
}
