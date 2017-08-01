package com.google.android.gms.auth.firstparty.dataservice;

import android.accounts.Account;
import android.os.Bundle;
import com.google.android.gms.auth.firstparty.shared.AccountCredentials;

/* compiled from: dalvik_source_com.waze.apk */
public interface GoogleAccountDataClient {
    AccountNameCheckResponse checkAccountName(AccountNameCheckRequest accountNameCheckRequest) throws ;

    CheckFactoryResetPolicyComplianceResponse checkFactoryResetPolicyCompliance(CheckFactoryResetPolicyComplianceRequest checkFactoryResetPolicyComplianceRequest) throws ;

    PasswordCheckResponse checkPassword(PasswordCheckRequest passwordCheckRequest) throws ;

    CheckRealNameResponse checkRealName(CheckRealNameRequest checkRealNameRequest) throws ;

    void clearFactoryResetChallenges() throws ;

    ClearTokenResponse clearToken(ClearTokenRequest clearTokenRequest) throws ;

    boolean clearWorkAccountAppWhitelist() throws ;

    TokenResponse confirmCredentials(ConfirmCredentialsRequest confirmCredentialsRequest) throws ;

    TokenResponse createAccount(GoogleAccountSetupRequest googleAccountSetupRequest) throws ;

    TokenResponse createPlusProfile(GoogleAccountSetupRequest googleAccountSetupRequest) throws ;

    @Deprecated
    GoogleAccountData getAccountData(@Deprecated String str) throws ;

    Bundle getAccountExportData(String str) throws ;

    String getAccountId(String str) throws ;

    AccountRecoveryData getAccountRecoveryCountryInfo() throws ;

    AccountRecoveryData getAccountRecoveryData(AccountRecoveryDataRequest accountRecoveryDataRequest) throws ;

    AccountRecoveryGuidance getAccountRecoveryGuidance(AccountRecoveryGuidanceRequest accountRecoveryGuidanceRequest) throws ;

    GetAndAdvanceOtpCounterResponse getAndAdvanceOtpCounter(String str) throws ;

    GoogleAccountData getGoogleAccountData(Account account) throws ;

    String getGoogleAccountId(Account account) throws ;

    GplusInfoResponse getGplusInfo(GplusInfoRequest gplusInfoRequest) throws ;

    OtpResponse getOtp(OtpRequest otpRequest) throws ;

    TokenResponse getToken(TokenRequest tokenRequest) throws ;

    boolean installAccountFromExportData(String str, Bundle bundle) throws ;

    AccountRemovalResponse removeAccount(AccountRemovalRequest accountRemovalRequest) throws ;

    boolean setWorkAccountAppWhitelistFingerprint(String str, String str2) throws ;

    TokenResponse signIn(AccountSignInRequest accountSignInRequest) throws ;

    AccountRecoveryUpdateResult updateAccountRecoveryData(AccountRecoveryUpdateRequest accountRecoveryUpdateRequest) throws ;

    TokenResponse updateCredentials(UpdateCredentialsRequest updateCredentialsRequest) throws ;

    ValidateAccountCredentialsResponse validateAccountCredentials(AccountCredentials accountCredentials) throws ;
}
