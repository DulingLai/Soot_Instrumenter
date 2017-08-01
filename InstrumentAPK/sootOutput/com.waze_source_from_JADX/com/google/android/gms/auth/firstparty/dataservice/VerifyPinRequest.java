package com.google.android.gms.auth.firstparty.dataservice;

import android.accounts.Account;
import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class VerifyPinRequest extends AbstractSafeParcelable {
    public static final VerifyPinRequestCreator CREATOR = new VerifyPinRequestCreator();
    public final Account account;
    @Deprecated
    public final String accountName;
    public String callingPackageName;
    public final String pin;
    final int version;

    VerifyPinRequest(int $i0, String $r1, String $r2, Account $r3, String $r4) throws  {
        this.version = $i0;
        this.accountName = $r1;
        this.pin = $r2;
        if ($r3 != null || TextUtils.isEmpty($r1)) {
            this.account = $r3;
        } else {
            this.account = new Account($r1, "com.google");
        }
        this.callingPackageName = $r4;
    }

    public VerifyPinRequest(Account $r1, String $r2) throws  {
        this(3, null, $r2, $r1, null);
    }

    @Deprecated
    public VerifyPinRequest(@Deprecated String $r1, @Deprecated String $r2) throws  {
        this(3, $r1, $r2, null, null);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        VerifyPinRequestCreator.zza(this, $r1, $i0);
    }
}
