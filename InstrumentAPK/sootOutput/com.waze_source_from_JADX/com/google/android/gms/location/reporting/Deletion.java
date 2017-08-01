package com.google.android.gms.location.reporting;

import android.accounts.Account;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;

/* compiled from: dalvik_source_com.waze.apk */
public final class Deletion extends AbstractSafeParcelable {
    public static final DeletionCreator CREATOR = new DeletionCreator();
    private final Account f34P;
    private final long ayJ;
    private final long ayK;
    private final long bL;
    private final int mVersionCode;

    Deletion(int $i0, Account $r1, Long $r2, Long $r3, Long $r4) throws  {
        this.mVersionCode = $i0;
        this.f34P = $r1;
        this.ayJ = $r2.longValue();
        this.ayK = $r3.longValue();
        this.bL = $r4.longValue();
    }

    public boolean equals(Object $r2) throws  {
        if (this == $r2) {
            return true;
        }
        if (!($r2 instanceof Deletion)) {
            return false;
        }
        Deletion $r3 = (Deletion) $r2;
        return this.ayJ == $r3.ayJ && this.ayK == $r3.ayK && this.bL == $r3.bL && zzaa.equal(this.f34P, $r3.f34P);
    }

    public Account getAccount() throws  {
        return this.f34P;
    }

    public long getEndTimeMs() throws  {
        return this.ayK;
    }

    public long getStartTimeMs() throws  {
        return this.ayJ;
    }

    public long getTimestampMs() throws  {
        return this.bL;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(this.f34P, Long.valueOf(this.ayJ), Long.valueOf(this.ayK), Long.valueOf(this.bL));
    }

    public String toString() throws  {
        String $r2 = String.valueOf(this.f34P);
        long $l0 = this.ayJ;
        long $l1 = this.ayK;
        return new StringBuilder(String.valueOf($r2).length() + 122).append("Deletion{mAccount=").append($r2).append(", mStartTimeMs=").append($l0).append(", mEndTimeMs=").append($l1).append(", mTimestampMs=").append(this.bL).append("}").toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        DeletionCreator $r2 = CREATOR;
        DeletionCreator.zza(this, $r1, $i0);
    }
}
