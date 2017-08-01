package com.google.android.gms.auth.firstparty.dataservice;

import android.accounts.Account;
import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.auth.firstparty.shared.AppDescription;
import com.google.android.gms.auth.firstparty.shared.CaptchaSolution;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountNameCheckRequest extends AbstractSafeParcelable {
    public static final AccountNameCheckRequestCreator CREATOR = new AccountNameCheckRequestCreator();
    AppDescription callingAppDescription;
    @Deprecated
    String gn;
    String go;
    String gp;
    CaptchaSolution gq;
    Account gr;
    final int version;

    public AccountNameCheckRequest() throws  {
        this.version = 2;
    }

    AccountNameCheckRequest(int $i0, String $r1, String $r2, String $r3, AppDescription $r4, CaptchaSolution $r5, Account $r6) throws  {
        this.version = $i0;
        this.gn = $r1;
        this.go = $r2;
        this.gp = $r3;
        this.callingAppDescription = $r4;
        this.gq = $r5;
        if ($r6 != null || TextUtils.isEmpty($r1)) {
            this.gr = $r6;
        } else {
            this.gr = new Account($r1, "com.google");
        }
    }

    public AccountNameCheckRequest(Account $r1) throws  {
        this.version = 2;
        this.gr = $r1;
    }

    @Deprecated
    public AccountNameCheckRequest(@Deprecated String $r1) throws  {
        this.version = 2;
        this.gn = $r1;
    }

    @Deprecated
    public String getAccountNameToCheck() throws  {
        return this.gn;
    }

    public Account getAccountToCheck() throws  {
        return this.gr;
    }

    public AppDescription getCallingAppDescription() throws  {
        return this.callingAppDescription;
    }

    public CaptchaSolution getCaptchaSolution() throws  {
        return this.gq;
    }

    public String getFirstName() throws  {
        return this.go;
    }

    public String getLastName() throws  {
        return this.gp;
    }

    @Deprecated
    public AccountNameCheckRequest setAccountNameToCheck(@Deprecated String $r1) throws  {
        this.gn = $r1;
        return this;
    }

    public AccountNameCheckRequest setAccountToCheck(Account $r1) throws  {
        this.gr = $r1;
        return this;
    }

    public AccountNameCheckRequest setCallingAppDescription(AppDescription $r1) throws  {
        this.callingAppDescription = $r1;
        return this;
    }

    public AccountNameCheckRequest setCaptchaSolution(CaptchaSolution $r1) throws  {
        this.gq = $r1;
        return this;
    }

    public AccountNameCheckRequest setFirstName(String $r1) throws  {
        this.go = $r1;
        return this;
    }

    public AccountNameCheckRequest setLastName(String $r1) throws  {
        this.gp = $r1;
        return this;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        AccountNameCheckRequestCreator.zza(this, $r1, $i0);
    }
}
