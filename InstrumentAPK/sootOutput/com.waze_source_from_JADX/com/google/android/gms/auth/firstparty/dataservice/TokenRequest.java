package com.google.android.gms.auth.firstparty.dataservice;

import android.accounts.Account;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.auth.firstparty.shared.AppDescription;
import com.google.android.gms.auth.firstparty.shared.CaptchaSolution;
import com.google.android.gms.auth.firstparty.shared.FACLConfig;
import com.google.android.gms.auth.firstparty.shared.PACLConfig;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public class TokenRequest extends AbstractSafeParcelable {
    public static final TokenRequestCreator CREATOR = new TokenRequestCreator();
    String accountName;
    String accountType;
    AppDescription callingAppDescription;
    boolean gK;
    CaptchaSolution gq;
    FACLConfig hA;
    PACLConfig hB;
    String hC;
    boolean hD;
    boolean hE;
    int hF;
    String hG;
    String hH;
    boolean hp;
    String hz;
    Bundle options;
    final int version;

    /* compiled from: dalvik_source_com.waze.apk */
    public enum Consent {
        UNKNOWN,
        GRANTED,
        REJECTED
    }

    TokenRequest(int $i0, String $r1, String $r2, Bundle $r3, FACLConfig $r4, PACLConfig $r5, boolean $z0, boolean $z1, String $r6, AppDescription $r7, CaptchaSolution $r8, boolean $z2, boolean $z3, String $r9, int $i1, String $r10, String $r11) throws  {
        this.options = new Bundle();
        this.hC = Consent.UNKNOWN.toString();
        this.hD = false;
        this.hE = true;
        this.accountType = "com.google";
        this.hF = 0;
        this.version = $i0;
        this.hz = $r1;
        this.accountName = $r2;
        this.options = $r3;
        this.hA = $r4;
        this.hB = $r5;
        this.hp = $z0;
        this.gK = $z1;
        this.hC = $r6;
        this.callingAppDescription = $r7;
        this.gq = $r8;
        this.hD = $z2;
        this.hE = $z3;
        this.accountType = $r9;
        this.hF = $i1;
        this.hG = $r10;
        this.hH = $r11;
    }

    public TokenRequest(Account $r1, String $r2) throws  {
        this($r1.name, $r1.type, $r2);
    }

    @Deprecated
    public TokenRequest(@Nullable @Deprecated String $r1, @Deprecated String $r2, @Deprecated String $r3) throws  {
        this.options = new Bundle();
        this.hC = Consent.UNKNOWN.toString();
        this.hD = false;
        this.hE = true;
        this.accountType = "com.google";
        this.hF = 0;
        this.version = 4;
        this.accountName = $r1;
        this.accountType = $r2;
        this.hz = $r3;
    }

    public Account getAccount() throws  {
        return !TextUtils.isEmpty(this.accountName) ? new Account(this.accountName, this.accountType) : null;
    }

    public String getAccountName() throws  {
        return this.accountName;
    }

    public String getAccountType() throws  {
        return this.accountType;
    }

    public AppDescription getCallingAppDescription() throws  {
        return this.callingAppDescription;
    }

    public CaptchaSolution getCaptchaSolution() throws  {
        return this.gq;
    }

    public Consent getConsent() throws  {
        return Consent.valueOf(this.hC);
    }

    public FACLConfig getFaclData() throws  {
        return this.hA;
    }

    public Bundle getOptions() throws  {
        return new Bundle(this.options);
    }

    public PACLConfig getPaclData() throws  {
        return this.hB;
    }

    public String getService() throws  {
        return this.hz;
    }

    public boolean isAddingAccount() throws  {
        return this.hp;
    }

    public boolean isCreatingAccount() throws  {
        return this.gK;
    }

    public TokenRequest setAccountName(String $r1) throws  {
        this.accountName = $r1;
        return this;
    }

    public TokenRequest setAddingAccount(boolean $z0) throws  {
        this.hp = $z0;
        return this;
    }

    public TokenRequest setCallingAppDescription(AppDescription $r1) throws  {
        this.callingAppDescription = $r1;
        return this;
    }

    public TokenRequest setCaptchaSolution(CaptchaSolution $r1) throws  {
        this.gq = $r1;
        return this;
    }

    public TokenRequest setConsent(Consent $r1) throws  {
        this.hC = ((Consent) zzab.zzb((Object) $r1, (Object) " Consent cannot be null")).toString();
        return this;
    }

    public TokenRequest setCreatingAccount(boolean $z0) throws  {
        this.gK = $z0;
        return this;
    }

    public TokenRequest setFaclData(FACLConfig $r1) throws  {
        this.hA = $r1;
        return this;
    }

    public TokenRequest setOptions(Bundle $r1) throws  {
        this.options.clear();
        if ($r1 == null) {
            return this;
        }
        this.options.putAll($r1);
        return this;
    }

    public TokenRequest setPaclData(PACLConfig $r1) throws  {
        this.hB = $r1;
        return this;
    }

    public TokenRequest setService(String $r1) throws  {
        this.hz = $r1;
        return this;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        TokenRequestCreator.zza(this, $r1, $i0);
    }
}
