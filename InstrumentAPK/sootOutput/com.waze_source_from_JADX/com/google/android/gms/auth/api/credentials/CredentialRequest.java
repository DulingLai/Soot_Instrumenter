package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public final class CredentialRequest extends AbstractSafeParcelable {
    public static final Creator<CredentialRequest> CREATOR = new zzc();
    private final boolean eM;
    private final String[] eN;
    private final CredentialPickerConfig eO;
    private final CredentialPickerConfig eP;
    final int mVersionCode;

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Builder {
        private boolean eM;
        private String[] eN;
        private CredentialPickerConfig eO;
        private CredentialPickerConfig eP;

        public CredentialRequest build() throws  {
            if (this.eN == null) {
                this.eN = new String[0];
            }
            if (this.eM || this.eN.length != 0) {
                return new CredentialRequest();
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

        public Builder setCredentialHintPickerConfig(CredentialPickerConfig $r1) throws  {
            this.eP = $r1;
            return this;
        }

        public Builder setCredentialPickerConfig(CredentialPickerConfig $r1) throws  {
            this.eO = $r1;
            return this;
        }

        public Builder setPasswordLoginSupported(boolean $z0) throws  {
            this.eM = $z0;
            return this;
        }

        @Deprecated
        public Builder setSupportsPasswordLogin(@Deprecated boolean $z0) throws  {
            return setPasswordLoginSupported($z0);
        }
    }

    CredentialRequest(int $i0, boolean $z0, String[] $r1, CredentialPickerConfig $r2, CredentialPickerConfig $r3) throws  {
        this.mVersionCode = $i0;
        this.eM = $z0;
        this.eN = (String[]) zzab.zzag($r1);
        if ($r2 == null) {
            $r2 = new com.google.android.gms.auth.api.credentials.CredentialPickerConfig.Builder().build();
        }
        this.eO = $r2;
        if ($r3 == null) {
            $r3 = new com.google.android.gms.auth.api.credentials.CredentialPickerConfig.Builder().build();
        }
        this.eP = $r3;
    }

    private CredentialRequest(Builder $r1) throws  {
        this(2, $r1.eM, $r1.eN, $r1.eO, $r1.eP);
    }

    @NonNull
    public String[] getAccountTypes() throws  {
        return this.eN;
    }

    @NonNull
    public CredentialPickerConfig getCredentialHintPickerConfig() throws  {
        return this.eP;
    }

    @NonNull
    public CredentialPickerConfig getCredentialPickerConfig() throws  {
        return this.eO;
    }

    @Deprecated
    public boolean getSupportsPasswordLogin() throws  {
        return isPasswordLoginSupported();
    }

    public boolean isPasswordLoginSupported() throws  {
        return this.eM;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzc.zza(this, $r1, $i0);
    }
}
