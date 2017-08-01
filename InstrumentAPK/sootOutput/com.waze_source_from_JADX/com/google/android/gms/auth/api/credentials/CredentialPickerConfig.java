package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public final class CredentialPickerConfig extends AbstractSafeParcelable {
    public static final Creator<CredentialPickerConfig> CREATOR = new zzb();
    private final boolean eK;
    private final boolean eL;
    private final boolean mShowCancelButton;
    final int mVersionCode;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder {
        private boolean eK = false;
        private boolean eL = false;
        private boolean mShowCancelButton = true;

        public CredentialPickerConfig build() throws  {
            return new CredentialPickerConfig();
        }

        public Builder setForNewAccount(boolean $z0) throws  {
            this.eL = $z0;
            return this;
        }

        public Builder setShowAddAccountButton(boolean $z0) throws  {
            this.eK = $z0;
            return this;
        }

        public Builder setShowCancelButton(boolean $z0) throws  {
            this.mShowCancelButton = $z0;
            return this;
        }
    }

    CredentialPickerConfig(int $i0, boolean $z0, boolean $z1, boolean $z2) throws  {
        this.mVersionCode = $i0;
        this.eK = $z0;
        this.mShowCancelButton = $z1;
        this.eL = $z2;
    }

    private CredentialPickerConfig(Builder $r1) throws  {
        this(1, $r1.eK, $r1.mShowCancelButton, $r1.eL);
    }

    public boolean isForNewAccount() throws  {
        return this.eL;
    }

    public boolean shouldShowAddAccountButton() throws  {
        return this.eK;
    }

    public boolean shouldShowCancelButton() throws  {
        return this.mShowCancelButton;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzb.zza(this, $r1, $i0);
    }
}
