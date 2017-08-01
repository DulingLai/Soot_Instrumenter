package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.auth.firstparty.shared.AccountCredentials;
import com.google.android.gms.auth.firstparty.shared.AppDescription;
import com.google.android.gms.auth.firstparty.shared.CaptchaSolution;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class GoogleAccountSetupRequest extends AbstractSafeParcelable {
    public static final GoogleAccountSetupRequestCreator CREATOR = new GoogleAccountSetupRequestCreator();
    AppDescription callingAppDescription;
    String firstName;
    boolean gK;
    boolean gL;
    AccountCredentials gM;
    String gender;
    CaptchaSolution gq;
    boolean hm;
    boolean hn;
    boolean ho;
    boolean hp;
    String hq;
    String lastName;
    Bundle options;
    String phoneCountryCode;
    String phoneNumber;
    String secondaryEmail;
    final int version;

    public GoogleAccountSetupRequest() throws  {
        this.version = 1;
        this.options = new Bundle();
    }

    GoogleAccountSetupRequest(int $i0, Bundle $r1, boolean $z0, boolean $z1, boolean $z2, String $r2, String $r3, String $r4, String $r5, boolean $z3, boolean $z4, boolean $z5, String $r6, AppDescription $r7, AccountCredentials $r8, CaptchaSolution $r9, String $r10, String $r11) throws  {
        this.version = $i0;
        this.options = $r1;
        this.hm = $z0;
        this.hn = $z1;
        this.ho = $z2;
        this.firstName = $r2;
        this.lastName = $r3;
        this.secondaryEmail = $r4;
        this.gender = $r5;
        this.gK = $z3;
        this.hp = $z4;
        this.gL = $z5;
        this.hq = $r6;
        this.callingAppDescription = $r7;
        this.gM = $r8;
        this.gq = $r9;
        this.phoneNumber = $r10;
        this.phoneCountryCode = $r11;
    }

    public AccountCredentials getAccountCredentials() throws  {
        return this.gM;
    }

    public AppDescription getCallingAppDescription() throws  {
        return this.callingAppDescription;
    }

    public CaptchaSolution getCaptchaSolution() throws  {
        return this.gq;
    }

    public String getFirstName() throws  {
        return this.firstName;
    }

    public String getGender() throws  {
        return this.gender;
    }

    public String getLastName() throws  {
        return this.lastName;
    }

    public Bundle getOptions() throws  {
        return new Bundle(this.options);
    }

    public String getPhoneCountryCode() throws  {
        return this.phoneCountryCode;
    }

    public String getPhoneNumber() throws  {
        return this.phoneNumber;
    }

    public String getRopRevision() throws  {
        return this.hq;
    }

    public String getSecondaryEmail() throws  {
        return this.secondaryEmail;
    }

    public boolean isAddingAccount() throws  {
        return this.hp;
    }

    public boolean isAgreedToMobileTos() throws  {
        return this.ho;
    }

    public boolean isAgreedToPersonalizedContent() throws  {
        return this.hn;
    }

    public boolean isAgreedToWebHistory() throws  {
        return this.hm;
    }

    public boolean isCreatingAccount() throws  {
        return this.gK;
    }

    public boolean isSetupWizardInProgress() throws  {
        return this.gL;
    }

    public GoogleAccountSetupRequest setAccountCredentials(AccountCredentials $r1) throws  {
        this.gM = $r1;
        return this;
    }

    public GoogleAccountSetupRequest setAddingAccount(boolean $z0) throws  {
        this.hp = $z0;
        return this;
    }

    public GoogleAccountSetupRequest setAgreedToMobileTos(boolean $z0) throws  {
        this.ho = $z0;
        return this;
    }

    public GoogleAccountSetupRequest setAgreedToPersonalizedContent(boolean $z0) throws  {
        this.hn = $z0;
        return this;
    }

    public GoogleAccountSetupRequest setAgreedToWebHistory(boolean $z0) throws  {
        this.hm = $z0;
        return this;
    }

    public GoogleAccountSetupRequest setCallingAppDescription(AppDescription $r1) throws  {
        this.callingAppDescription = $r1;
        return this;
    }

    public GoogleAccountSetupRequest setCaptchaSolution(CaptchaSolution $r1) throws  {
        this.gq = $r1;
        return this;
    }

    public GoogleAccountSetupRequest setCreatingAccount(boolean $z0) throws  {
        this.gK = $z0;
        return this;
    }

    public GoogleAccountSetupRequest setFirstName(String $r1) throws  {
        this.firstName = $r1;
        return this;
    }

    public GoogleAccountSetupRequest setGender(String $r1) throws  {
        this.gender = $r1;
        return this;
    }

    public GoogleAccountSetupRequest setLastName(String $r1) throws  {
        this.lastName = $r1;
        return this;
    }

    public GoogleAccountSetupRequest setOptions(Bundle $r1) throws  {
        this.options.clear();
        this.options.putAll($r1);
        return this;
    }

    public GoogleAccountSetupRequest setPhoneCountryCode(String $r1) throws  {
        this.phoneCountryCode = $r1;
        return this;
    }

    public GoogleAccountSetupRequest setPhoneNumber(String $r1) throws  {
        this.phoneNumber = $r1;
        return this;
    }

    public GoogleAccountSetupRequest setRopRevision(String $r1) throws  {
        this.hq = $r1;
        return this;
    }

    public GoogleAccountSetupRequest setSecondaryEmail(String $r1) throws  {
        this.secondaryEmail = $r1;
        return this;
    }

    public GoogleAccountSetupRequest setSetupWizardInProgress(boolean $z0) throws  {
        this.gL = $z0;
        return this;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        GoogleAccountSetupRequestCreator.zza(this, $r1, $i0);
    }
}
