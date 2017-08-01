package com.google.android.gms.location.reporting;

import android.accounts.Account;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.internal.zzxo;

/* compiled from: dalvik_source_com.waze.apk */
public class UploadRequest extends AbstractSafeParcelable {
    public static final UploadRequestCreator CREATOR = new UploadRequestCreator();
    private final long LT;
    private final Account f38P;
    private final long ayT;
    private final long ayU;
    private final String ayV;
    private final int mVersionCode;
    private final String zzbil;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder {
        private final long LT;
        private final Account f37P;
        private String ayV;
        private long ayW;
        private long ayX;
        private final String zzbil;

        private Builder(Account $r1, String $r2, long $l0) throws  {
            this.ayW = Long.MAX_VALUE;
            this.ayX = Long.MAX_VALUE;
            this.f37P = (Account) zzab.zzb((Object) $r1, (Object) "account");
            this.zzbil = (String) zzab.zzb((Object) $r2, (Object) "reason");
            this.LT = $l0;
        }

        public Builder appSpecificKey(String $r1) throws  {
            this.ayV = $r1;
            return this;
        }

        public UploadRequest build() throws  {
            return new UploadRequest();
        }

        public Builder latencyMillis(long $l0) throws  {
            return movingLatencyMillis($l0).stationaryLatencyMillis($l0);
        }

        public Builder movingLatencyMillis(long $l0) throws  {
            this.ayW = $l0;
            return this;
        }

        public Builder stationaryLatencyMillis(long $l0) throws  {
            this.ayX = $l0;
            return this;
        }
    }

    public UploadRequest(int $i0, Account $r1, String $r2, long $l1, long $l2, long $l3, String $r3) throws  {
        this.mVersionCode = $i0;
        this.f38P = $r1;
        this.zzbil = $r2;
        this.LT = $l1;
        this.ayT = $l2;
        this.ayU = $l3;
        this.ayV = $r3;
    }

    private UploadRequest(Builder $r1) throws  {
        this.mVersionCode = 1;
        this.f38P = $r1.f37P;
        this.zzbil = $r1.zzbil;
        this.LT = $r1.LT;
        this.ayT = $r1.ayW;
        this.ayU = $r1.ayX;
        this.ayV = $r1.ayV;
    }

    public static Builder builder(Account $r0, String $r1, long $l0) throws  {
        return new Builder($r0, $r1, $l0);
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if (!($r1 instanceof UploadRequest)) {
            return false;
        }
        UploadRequest $r2 = (UploadRequest) $r1;
        return this.f38P.equals($r2.f38P) && this.zzbil.equals($r2.zzbil) && zzaa.equal(Long.valueOf(this.LT), Long.valueOf($r2.LT)) && this.ayT == $r2.ayT && this.ayU == $r2.ayU && zzaa.equal(this.ayV, $r2.ayV);
    }

    public Account getAccount() throws  {
        return this.f38P;
    }

    public String getAppSpecificKey() throws  {
        return this.ayV;
    }

    public long getDurationMillis() throws  {
        return this.LT;
    }

    public long getMovingLatencyMillis() throws  {
        return this.ayT;
    }

    public String getReason() throws  {
        return this.zzbil;
    }

    public long getStationaryLatencyMillis() throws  {
        return this.ayU;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(this.f38P, this.zzbil, Long.valueOf(this.LT), Long.valueOf(this.ayT), Long.valueOf(this.ayU), this.ayV);
    }

    public String toString() throws  {
        int i = this.mVersionCode;
        String $r4 = String.valueOf(zzxo.zzh(this.f38P));
        String $r1 = this.zzbil;
        long $l0 = this.LT;
        long $l1 = this.ayT;
        long $l2 = this.ayU;
        String $r2 = this.ayV;
        return new StringBuilder(((String.valueOf($r4).length() + 210) + String.valueOf($r1).length()) + String.valueOf($r2).length()).append("UploadRequest{mVersionCode=").append(i).append(", mAccount=").append($r4).append(", mReason='").append($r1).append("'").append(", mDurationMillis=").append($l0).append(", mMovingLatencyMillis=").append($l1).append(", mStationaryLatencyMillis=").append($l2).append(", mAppSpecificKey='").append($r2).append("'").append("}").toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        UploadRequestCreator $r2 = CREATOR;
        UploadRequestCreator.zza(this, $r1, $i0);
    }
}
