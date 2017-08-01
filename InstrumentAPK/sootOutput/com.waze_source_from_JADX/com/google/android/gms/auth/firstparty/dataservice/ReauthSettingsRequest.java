package com.google.android.gms.auth.firstparty.dataservice;

import android.accounts.Account;
import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class ReauthSettingsRequest extends AbstractSafeParcelable {
    public static final ReauthSettingsRequestCreator CREATOR = new ReauthSettingsRequestCreator();
    public final Account account;
    @Deprecated
    public final String accountName;
    public String callingPackageName;
    public final boolean force;
    final int version;

    ReauthSettingsRequest(int $i0, String $r1, boolean $z0, Account $r2, String $r3) throws  {
        this.version = $i0;
        this.accountName = $r1;
        this.force = $z0;
        if ($r2 != null || TextUtils.isEmpty($r1)) {
            this.account = $r2;
        } else {
            this.account = new Account($r1, "com.google");
        }
        this.callingPackageName = $r3;
    }

    public ReauthSettingsRequest(Account $r1, boolean $z0) throws  {
        this(3, null, $z0, $r1, null);
    }

    @Deprecated
    public ReauthSettingsRequest(@Deprecated String $r1, @Deprecated boolean $z0) throws  {
        this(3, $r1, $z0, null, null);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        ReauthSettingsRequestCreator.zza(this, $r1, $i0);
    }
}
