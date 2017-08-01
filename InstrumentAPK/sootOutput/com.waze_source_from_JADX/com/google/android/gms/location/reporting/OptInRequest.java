package com.google.android.gms.location.reporting;

import android.accounts.Account;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.internal.zzxo;

/* compiled from: dalvik_source_com.waze.apk */
public class OptInRequest implements SafeParcelable {
    public static final OptInRequestCreator CREATOR = new OptInRequestCreator();
    private final Account f36P;
    private final String mTag;
    private final int mVersionCode;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder {
        private final Account f35P;
        private String mTag;

        private Builder(Account $r1) throws  {
            this.f35P = (Account) zzab.zzb((Object) $r1, (Object) "account");
        }

        public OptInRequest build() throws  {
            return new OptInRequest();
        }

        public Builder tag(String $r1) throws  {
            this.mTag = $r1;
            return this;
        }
    }

    public OptInRequest(int $i0, Account $r1, String $r2) throws  {
        this.mVersionCode = $i0;
        this.f36P = $r1;
        this.mTag = $r2;
    }

    private OptInRequest(Builder $r1) throws  {
        this.mVersionCode = 1;
        this.f36P = $r1.f35P;
        this.mTag = $r1.mTag;
    }

    public static Builder builder(Account $r0) throws  {
        return new Builder($r0);
    }

    public int describeContents() throws  {
        OptInRequestCreator $r1 = CREATOR;
        return 0;
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if (!($r1 instanceof OptInRequest)) {
            return false;
        }
        OptInRequest $r2 = (OptInRequest) $r1;
        return this.f36P.equals($r2.f36P) && zzaa.equal(this.mTag, $r2.mTag);
    }

    public Account getAccount() throws  {
        return this.f36P;
    }

    public String getTag() throws  {
        return this.mTag;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(this.f36P, this.mTag);
    }

    public String toString() throws  {
        int $i0 = this.mVersionCode;
        String $r3 = String.valueOf(zzxo.zzh(this.f36P));
        String $r1 = this.mTag;
        return new StringBuilder((String.valueOf($r3).length() + 58) + String.valueOf($r1).length()).append("UploadRequest{mVersionCode=").append($i0).append(", mAccount=").append($r3).append(", mTag='").append($r1).append("}").toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        OptInRequestCreator $r2 = CREATOR;
        OptInRequestCreator.zza(this, $r1, $i0);
    }
}
