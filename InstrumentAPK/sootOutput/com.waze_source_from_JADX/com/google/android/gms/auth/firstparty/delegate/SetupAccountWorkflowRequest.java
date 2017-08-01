package com.google.android.gms.auth.firstparty.delegate;

import android.accounts.AccountAuthenticatorResponse;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import com.google.android.gms.auth.firstparty.shared.AppDescription;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class SetupAccountWorkflowRequest extends AbstractSafeParcelable {
    public static final SetupAccountWorkflowRequestCreator CREATOR = new SetupAccountWorkflowRequestCreator();
    public String accountName;
    public final String accountType;
    public List<String> allowedDomains;
    public AccountAuthenticatorResponse amResponse;
    public final AppDescription callingAppDescription;
    public boolean isCreditCardAllowed;
    public boolean isMultiUser;
    public boolean isSetupWizard;
    public String loginTemplate;
    public Bundle options;
    public String purchaserGaiaEmail;
    public String purchaserName;
    public boolean suppressD2d;
    public boolean useImmersiveMode;
    public final int version;

    public SetupAccountWorkflowRequest(@Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z0, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z1, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) List<String> $r1, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Bundle $r2, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) AppDescription $r3, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z2, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) AccountAuthenticatorResponse $r5, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z3, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z4, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r6, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r7, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r8) throws  {
        this($i0, $z0, $z1, $r1, $r2, $r3, $z2, $r4, $r5, $z3, $z4, $r6, $r7, $r8, null);
    }

    public SetupAccountWorkflowRequest(@Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z0, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z1, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) List<String> $r1, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Bundle $r2, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) AppDescription $r3, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z2, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) AccountAuthenticatorResponse $r5, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z3, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z4, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r6, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r7, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r8, @Signature({"(IZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/google/android/gms/auth/firstparty/shared/AppDescription;", "Z", "Ljava/lang/String;", "Landroid/accounts/AccountAuthenticatorResponse;", "ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r9) throws  {
        this.version = $i0;
        this.isMultiUser = $z0;
        this.isSetupWizard = $z1;
        this.allowedDomains = $r1;
        this.options = $r2;
        this.callingAppDescription = (AppDescription) zzab.zzag($r3);
        this.isCreditCardAllowed = $z2;
        this.accountType = $r4;
        this.amResponse = $r5;
        this.suppressD2d = $z3;
        this.useImmersiveMode = $z4;
        this.purchaserGaiaEmail = $r6;
        this.purchaserName = $r7;
        this.accountName = $r8;
        this.loginTemplate = $r9;
    }

    public SetupAccountWorkflowRequest(AppDescription $r1, String $r2) throws  {
        this.version = 6;
        this.options = new Bundle();
        this.callingAppDescription = $r1;
        this.accountType = $r2;
    }

    @Nullable
    public String getAccountName() throws  {
        return this.accountName;
    }

    public List<String> getAllowedDomains() throws  {
        return this.allowedDomains == null ? null : Collections.unmodifiableList(this.allowedDomains);
    }

    public AccountAuthenticatorResponse getAmResponse() throws  {
        return this.amResponse;
    }

    @Nullable
    public String getLoginTemplate() throws  {
        return this.loginTemplate;
    }

    public Bundle getOptions() throws  {
        return new Bundle(this.options);
    }

    public String getPurchaserGaiaEmail() throws  {
        return this.purchaserGaiaEmail;
    }

    public String getPurchaserName() throws  {
        return this.purchaserName;
    }

    @Deprecated
    public boolean isBackupAccount() throws  {
        return this.isSetupWizard;
    }

    public boolean isCreditCardAllowed() throws  {
        return this.isCreditCardAllowed;
    }

    public boolean isMultiUser() throws  {
        return this.isMultiUser;
    }

    public boolean isSetupWizard() throws  {
        return this.isSetupWizard;
    }

    public SetupAccountWorkflowRequest setAccountName(@Nullable String $r1) throws  {
        this.accountName = $r1;
        return this;
    }

    public SetupAccountWorkflowRequest setAllowedDomains(@Signature({"(", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/auth/firstparty/delegate/SetupAccountWorkflowRequest;"}) Collection<String> $r1) throws  {
        if ($r1 != null) {
            this.allowedDomains = new ArrayList($r1);
            return this;
        }
        this.allowedDomains = null;
        return this;
    }

    public SetupAccountWorkflowRequest setAmResponse(AccountAuthenticatorResponse $r1) throws  {
        this.amResponse = $r1;
        return this;
    }

    @Deprecated
    public SetupAccountWorkflowRequest setBackupAccount(@Deprecated boolean $z0) throws  {
        return setIsSetupWizard($z0);
    }

    public SetupAccountWorkflowRequest setIsCreditCardAllowed(boolean $z0) throws  {
        this.isCreditCardAllowed = $z0;
        return this;
    }

    public SetupAccountWorkflowRequest setIsSetupWizard(boolean $z0) throws  {
        this.isSetupWizard = $z0;
        return this;
    }

    public SetupAccountWorkflowRequest setLoginTemplate(@Nullable String $r1) throws  {
        this.loginTemplate = $r1;
        return this;
    }

    public SetupAccountWorkflowRequest setMultiUser(boolean $z0) throws  {
        this.isMultiUser = $z0;
        return this;
    }

    public SetupAccountWorkflowRequest setOptions(Bundle $r1) throws  {
        this.options.clear();
        if ($r1 == null) {
            return this;
        }
        this.options.putAll($r1);
        return this;
    }

    public SetupAccountWorkflowRequest setPurchaserGaiaEmail(String $r1) throws  {
        this.purchaserGaiaEmail = $r1;
        return this;
    }

    public SetupAccountWorkflowRequest setPurchaserName(String $r1) throws  {
        this.purchaserName = $r1;
        return this;
    }

    public SetupAccountWorkflowRequest setSuppressD2d(boolean $z0) throws  {
        this.suppressD2d = $z0;
        return this;
    }

    public SetupAccountWorkflowRequest setUseImmersiveMode(boolean $z0) throws  {
        this.useImmersiveMode = $z0;
        return this;
    }

    public boolean suppressD2d() throws  {
        return this.suppressD2d;
    }

    public boolean useImmersiveMode() throws  {
        return this.useImmersiveMode;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        SetupAccountWorkflowRequestCreator.zza(this, $r1, $i0);
    }
}
