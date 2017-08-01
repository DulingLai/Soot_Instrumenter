package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public final class HintRequest extends AbstractSafeParcelable {
    public static final Creator<HintRequest> CREATOR = new zzd();
    private final String[] eN;
    private final CredentialPickerConfig eQ;
    private final boolean eR;
    private final boolean eS;
    final int mVersionCode;

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Builder {
        private String[] eN;
        private CredentialPickerConfig eQ = new com.google.android.gms.auth.api.credentials.CredentialPickerConfig.Builder().build();
        private boolean eR;
        private boolean eS;

        public HintRequest build() throws  {
            if (this.eN == null) {
                this.eN = new String[0];
            }
            if (this.eR || this.eS || this.eN.length != 0) {
                return new HintRequest();
            }
            throw new IllegalStateException("At least one authentication method must be specified");
        }

        public Builder setAccountTypes(String... $r1) throws  {
            if ($r1 == null) {
                $r1 = new String[0];
            }
            this.eN = $r1;
            return this;
        }

        public Builder setEmailAddressIdentifierSupported(boolean $z0) throws  {
            this.eR = $z0;
            return this;
        }

        public Builder setHintPickerConfig(@NonNull CredentialPickerConfig $r1) throws  {
            this.eQ = (CredentialPickerConfig) zzab.zzag($r1);
            return this;
        }

        public Builder setPhoneNumberIdentifierSupported(boolean $z0) throws  {
            this.eS = $z0;
            return this;
        }
    }

    HintRequest(int $i0, CredentialPickerConfig $r1, boolean $z0, boolean $z1, String[] $r2) throws  {
        this.mVersionCode = $i0;
        this.eQ = (CredentialPickerConfig) zzab.zzag($r1);
        this.eR = $z0;
        this.eS = $z1;
        this.eN = (String[]) zzab.zzag($r2);
    }

    private HintRequest(Builder $r1) throws  {
        this(1, $r1.eQ, $r1.eR, $r1.eS, $r1.eN);
    }

    @NonNull
    public String[] getAccountTypes() throws  {
        return this.eN;
    }

    @NonNull
    public CredentialPickerConfig getHintPickerConfig() throws  {
        return this.eQ;
    }

    public boolean isEmailAddressIdentifierSupported() throws  {
        return this.eR;
    }

    public boolean isPhoneNumberIdentifierSupported() throws  {
        return this.eS;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzd.zza(this, $r1, $i0);
    }
}
