package com.google.android.gms.auth.firstparty.dataservice;

import android.accounts.Account;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.auth.AccountChangeEventsRequest;
import com.google.android.gms.auth.AccountChangeEventsResponse;
import com.google.android.gms.auth.firstparty.shared.AccountCredentials;

/* compiled from: dalvik_source_com.waze.apk */
public interface zza extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zza {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zza {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public AccountNameCheckResponse checkAccountName(AccountNameCheckRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(2, $r2, $r3, 0);
                    $r3.readException();
                    AccountNameCheckResponse $r7 = $r3.readInt() != 0 ? (AccountNameCheckResponse) AccountNameCheckResponse.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public PasswordCheckResponse checkPassword(PasswordCheckRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(3, $r2, $r3, 0);
                    $r3.readException();
                    PasswordCheckResponse $r7 = $r3.readInt() != 0 ? (PasswordCheckResponse) PasswordCheckResponse.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public CheckRealNameResponse checkRealName(CheckRealNameRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(4, $r2, $r3, 0);
                    $r3.readException();
                    CheckRealNameResponse $r7 = $r3.readInt() != 0 ? (CheckRealNameResponse) CheckRealNameResponse.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void clearFactoryResetChallenges() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    this.zzahn.transact(29, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public ClearTokenResponse clearToken(ClearTokenRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(19, $r2, $r3, 0);
                    $r3.readException();
                    ClearTokenResponse $r7 = $r3.readInt() != 0 ? (ClearTokenResponse) ClearTokenResponse.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public boolean clearWorkAccountAppWhitelist() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    this.zzahn.transact(35, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $z0 = true;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public TokenResponse confirmCredentials(ConfirmCredentialsRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(10, $r2, $r3, 0);
                    $r3.readException();
                    TokenResponse $r7 = $r3.readInt() != 0 ? (TokenResponse) TokenResponse.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public TokenResponse createAccount(GoogleAccountSetupRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(5, $r2, $r3, 0);
                    $r3.readException();
                    TokenResponse $r7 = $r3.readInt() != 0 ? (TokenResponse) TokenResponse.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public TokenResponse createPlusProfile(GoogleAccountSetupRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(7, $r2, $r3, 0);
                    $r3.readException();
                    TokenResponse $r7 = $r3.readInt() != 0 ? (TokenResponse) TokenResponse.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public AccountChangeEventsResponse getAccountChangeEvents(AccountChangeEventsRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(23, $r2, $r3, 0);
                    $r3.readException();
                    AccountChangeEventsResponse $r7 = $r3.readInt() != 0 ? (AccountChangeEventsResponse) AccountChangeEventsResponse.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public GoogleAccountData getAccountData(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    $r2.writeString($r1);
                    this.zzahn.transact(1, $r2, $r3, 0);
                    $r3.readException();
                    GoogleAccountData $r7 = $r3.readInt() != 0 ? (GoogleAccountData) GoogleAccountData.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public Bundle getAccountExportData(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    $r2.writeString($r1);
                    this.zzahn.transact(16, $r2, $r3, 0);
                    $r3.readException();
                    Bundle $r7 = $r3.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public String getAccountId(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    $r2.writeString($r1);
                    this.zzahn.transact(25, $r2, $r3, 0);
                    $r3.readException();
                    $r1 = $r3.readString();
                    return $r1;
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public AccountRecoveryData getAccountRecoveryCountryInfo() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    this.zzahn.transact(12, $r1, $r2, 0);
                    $r2.readException();
                    AccountRecoveryData $r6 = $r2.readInt() != 0 ? (AccountRecoveryData) AccountRecoveryData.CREATOR.createFromParcel($r2) : null;
                    $r2.recycle();
                    $r1.recycle();
                    return $r6;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public AccountRecoveryData getAccountRecoveryData(AccountRecoveryDataRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(13, $r2, $r3, 0);
                    $r3.readException();
                    AccountRecoveryData $r7 = $r3.readInt() != 0 ? (AccountRecoveryData) AccountRecoveryData.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public AccountRecoveryGuidance getAccountRecoveryGuidance(AccountRecoveryGuidanceRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(15, $r2, $r3, 0);
                    $r3.readException();
                    AccountRecoveryGuidance $r7 = $r3.readInt() != 0 ? (AccountRecoveryGuidance) AccountRecoveryGuidance.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public GetAndAdvanceOtpCounterResponse getAndAdvanceOtpCounter(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    $r2.writeString($r1);
                    this.zzahn.transact(37, $r2, $r3, 0);
                    $r3.readException();
                    GetAndAdvanceOtpCounterResponse $r7 = $r3.readInt() != 0 ? (GetAndAdvanceOtpCounterResponse) GetAndAdvanceOtpCounterResponse.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public GoogleAccountData getGoogleAccountData(Account $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(30, $r2, $r3, 0);
                    $r3.readException();
                    GoogleAccountData $r7 = $r3.readInt() != 0 ? (GoogleAccountData) GoogleAccountData.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public String getGoogleAccountId(Account $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(31, $r2, $r3, 0);
                    $r3.readException();
                    String $r5 = $r3.readString();
                    return $r5;
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public GplusInfoResponse getGplusInfo(GplusInfoRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(6, $r2, $r3, 0);
                    $r3.readException();
                    GplusInfoResponse $r7 = $r3.readInt() != 0 ? (GplusInfoResponse) GplusInfoResponse.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public OtpResponse getOtp(OtpRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(24, $r2, $r3, 0);
                    $r3.readException();
                    OtpResponse $r7 = $r3.readInt() != 0 ? (OtpResponse) OtpResponse.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public TokenResponse getToken(TokenRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(8, $r2, $r3, 0);
                    $r3.readException();
                    TokenResponse $r7 = $r3.readInt() != 0 ? (TokenResponse) TokenResponse.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public WebSetupConfig getWebSetupConfig(WebSetupConfigRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(18, $r2, $r3, 0);
                    $r3.readException();
                    WebSetupConfig $r7 = $r3.readInt() != 0 ? (WebSetupConfig) WebSetupConfig.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public boolean installAccountFromExportData(String $r1, Bundle $r2) throws RemoteException {
                boolean $z0 = true;
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    $r3.writeString($r1);
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.zzahn.transact(17, $r3, $r4, 0);
                    $r4.readException();
                    if ($r4.readInt() == 0) {
                        $z0 = false;
                    }
                    $r4.recycle();
                    $r3.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public AccountRemovalResponse removeAccount(AccountRemovalRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(20, $r2, $r3, 0);
                    $r3.readException();
                    AccountRemovalResponse $r7 = $r3.readInt() != 0 ? (AccountRemovalResponse) AccountRemovalResponse.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public boolean setWorkAccountAppWhitelistFingerprint(String $r1, String $r2) throws RemoteException {
                boolean $z0 = false;
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    $r3.writeString($r1);
                    $r3.writeString($r2);
                    this.zzahn.transact(34, $r3, $r4, 0);
                    $r4.readException();
                    if ($r4.readInt() != 0) {
                        $z0 = true;
                    }
                    $r4.recycle();
                    $r3.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public TokenResponse signIn(AccountSignInRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(9, $r2, $r3, 0);
                    $r3.readException();
                    TokenResponse $r7 = $r3.readInt() != 0 ? (TokenResponse) TokenResponse.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public AccountRecoveryUpdateResult updateAccountRecoveryData(AccountRecoveryUpdateRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(14, $r2, $r3, 0);
                    $r3.readException();
                    AccountRecoveryUpdateResult $r7 = $r3.readInt() != 0 ? (AccountRecoveryUpdateResult) AccountRecoveryUpdateResult.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public TokenResponse updateCredentials(UpdateCredentialsRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(11, $r2, $r3, 0);
                    $r3.readException();
                    TokenResponse $r7 = $r3.readInt() != 0 ? (TokenResponse) TokenResponse.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public ValidateAccountCredentialsResponse validateAccountCredentials(AccountCredentials $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(36, $r2, $r3, 0);
                    $r3.readException();
                    ValidateAccountCredentialsResponse $r7 = $r3.readInt() != 0 ? (ValidateAccountCredentialsResponse) ValidateAccountCredentialsResponse.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public CheckFactoryResetPolicyComplianceResponse zza(CheckFactoryResetPolicyComplianceRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(27, $r2, $r3, 0);
                    $r3.readException();
                    CheckFactoryResetPolicyComplianceResponse $r7 = $r3.readInt() != 0 ? (CheckFactoryResetPolicyComplianceResponse) CheckFactoryResetPolicyComplianceResponse.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }
        }

        public static zza zzcy(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
            return ($r1 == null || !($r1 instanceof zza)) ? new zza($r0) : (zza) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            Parcelable $r3 = null;
            byte $b2 = (byte) 0;
            GoogleAccountData $r5;
            TokenResponse $r14;
            AccountRecoveryData $r21;
            String $r4;
            boolean $z0;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    $r5 = getAccountData($r1.readString());
                    $r2.writeNoException();
                    if ($r5 != null) {
                        $r2.writeInt(1);
                        $r5.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (AccountNameCheckRequest) AccountNameCheckRequest.CREATOR.createFromParcel($r1);
                    }
                    AccountNameCheckResponse $r8 = checkAccountName((AccountNameCheckRequest) $r3);
                    $r2.writeNoException();
                    if ($r8 != null) {
                        $r2.writeInt(1);
                        $r8.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (PasswordCheckRequest) PasswordCheckRequest.CREATOR.createFromParcel($r1);
                    }
                    PasswordCheckResponse $r10 = checkPassword((PasswordCheckRequest) $r3);
                    $r2.writeNoException();
                    if ($r10 != null) {
                        $r2.writeInt(1);
                        $r10.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 4:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (CheckRealNameRequest) CheckRealNameRequest.CREATOR.createFromParcel($r1);
                    }
                    CheckRealNameResponse $r12 = checkRealName((CheckRealNameRequest) $r3);
                    $r2.writeNoException();
                    if ($r12 != null) {
                        $r2.writeInt(1);
                        $r12.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 5:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (GoogleAccountSetupRequest) GoogleAccountSetupRequest.CREATOR.createFromParcel($r1);
                    }
                    $r14 = createAccount((GoogleAccountSetupRequest) $r3);
                    $r2.writeNoException();
                    if ($r14 != null) {
                        $r2.writeInt(1);
                        $r14.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 6:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (GplusInfoRequest) GplusInfoRequest.CREATOR.createFromParcel($r1);
                    }
                    GplusInfoResponse $r16 = getGplusInfo((GplusInfoRequest) $r3);
                    $r2.writeNoException();
                    if ($r16 != null) {
                        $r2.writeInt(1);
                        $r16.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 7:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (GoogleAccountSetupRequest) GoogleAccountSetupRequest.CREATOR.createFromParcel($r1);
                    }
                    $r14 = createPlusProfile((GoogleAccountSetupRequest) $r3);
                    $r2.writeNoException();
                    if ($r14 != null) {
                        $r2.writeInt(1);
                        $r14.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 8:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (TokenRequest) TokenRequest.CREATOR.createFromParcel($r1);
                    }
                    $r14 = getToken((TokenRequest) $r3);
                    $r2.writeNoException();
                    if ($r14 != null) {
                        $r2.writeInt(1);
                        $r14.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 9:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (AccountSignInRequest) AccountSignInRequest.CREATOR.createFromParcel($r1);
                    }
                    $r14 = signIn((AccountSignInRequest) $r3);
                    $r2.writeNoException();
                    if ($r14 != null) {
                        $r2.writeInt(1);
                        $r14.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 10:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (ConfirmCredentialsRequest) ConfirmCredentialsRequest.CREATOR.createFromParcel($r1);
                    }
                    $r14 = confirmCredentials((ConfirmCredentialsRequest) $r3);
                    $r2.writeNoException();
                    if ($r14 != null) {
                        $r2.writeInt(1);
                        $r14.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 11:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (UpdateCredentialsRequest) UpdateCredentialsRequest.CREATOR.createFromParcel($r1);
                    }
                    $r14 = updateCredentials((UpdateCredentialsRequest) $r3);
                    $r2.writeNoException();
                    if ($r14 != null) {
                        $r2.writeInt(1);
                        $r14.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 12:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    $r21 = getAccountRecoveryCountryInfo();
                    $r2.writeNoException();
                    if ($r21 != null) {
                        $r2.writeInt(1);
                        $r21.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 13:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (AccountRecoveryDataRequest) AccountRecoveryDataRequest.CREATOR.createFromParcel($r1);
                    }
                    $r21 = getAccountRecoveryData((AccountRecoveryDataRequest) $r3);
                    $r2.writeNoException();
                    if ($r21 != null) {
                        $r2.writeInt(1);
                        $r21.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 14:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (AccountRecoveryUpdateRequest) AccountRecoveryUpdateRequest.CREATOR.createFromParcel($r1);
                    }
                    AccountRecoveryUpdateResult $r24 = updateAccountRecoveryData((AccountRecoveryUpdateRequest) $r3);
                    $r2.writeNoException();
                    if ($r24 != null) {
                        $r2.writeInt(1);
                        $r24.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 15:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (AccountRecoveryGuidanceRequest) AccountRecoveryGuidanceRequest.CREATOR.createFromParcel($r1);
                    }
                    AccountRecoveryGuidance $r26 = getAccountRecoveryGuidance((AccountRecoveryGuidanceRequest) $r3);
                    $r2.writeNoException();
                    if ($r26 != null) {
                        $r2.writeInt(1);
                        $r26.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 16:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    Bundle $r27 = getAccountExportData($r1.readString());
                    $r2.writeNoException();
                    if ($r27 != null) {
                        $r2.writeInt(1);
                        $r27.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 17:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    $r4 = $r1.readString();
                    if ($r1.readInt() != 0) {
                        $r3 = (Bundle) Bundle.CREATOR.createFromParcel($r1);
                    }
                    $z0 = installAccountFromExportData($r4, (Bundle) $r3);
                    $r2.writeNoException();
                    $r2.writeInt($z0 ? (byte) 1 : (byte) 0);
                    return true;
                case 18:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (WebSetupConfigRequest) WebSetupConfigRequest.CREATOR.createFromParcel($r1);
                    }
                    WebSetupConfig $r30 = getWebSetupConfig((WebSetupConfigRequest) $r3);
                    $r2.writeNoException();
                    if ($r30 != null) {
                        $r2.writeInt(1);
                        $r30.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 19:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (ClearTokenRequest) ClearTokenRequest.CREATOR.createFromParcel($r1);
                    }
                    ClearTokenResponse $r32 = clearToken((ClearTokenRequest) $r3);
                    $r2.writeNoException();
                    if ($r32 != null) {
                        $r2.writeInt(1);
                        $r32.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 20:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (AccountRemovalRequest) AccountRemovalRequest.CREATOR.createFromParcel($r1);
                    }
                    AccountRemovalResponse $r34 = removeAccount((AccountRemovalRequest) $r3);
                    $r2.writeNoException();
                    if ($r34 != null) {
                        $r2.writeInt(1);
                        $r34.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 23:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (AccountChangeEventsRequest) AccountChangeEventsRequest.CREATOR.createFromParcel($r1);
                    }
                    AccountChangeEventsResponse $r35 = getAccountChangeEvents((AccountChangeEventsRequest) $r3);
                    $r2.writeNoException();
                    if ($r35 != null) {
                        $r2.writeInt(1);
                        $r35.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 24:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (OtpRequest) OtpRequest.CREATOR.createFromParcel($r1);
                    }
                    OtpResponse $r37 = getOtp((OtpRequest) $r3);
                    $r2.writeNoException();
                    if ($r37 != null) {
                        $r2.writeInt(1);
                        $r37.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 25:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    $r4 = getAccountId($r1.readString());
                    $r2.writeNoException();
                    $r2.writeString($r4);
                    return true;
                case 27:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (CheckFactoryResetPolicyComplianceRequest) CheckFactoryResetPolicyComplianceRequest.CREATOR.createFromParcel($r1);
                    }
                    CheckFactoryResetPolicyComplianceResponse $r39 = zza((CheckFactoryResetPolicyComplianceRequest) $r3);
                    $r2.writeNoException();
                    if ($r39 != null) {
                        $r2.writeInt(1);
                        $r39.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 29:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    clearFactoryResetChallenges();
                    $r2.writeNoException();
                    return true;
                case 30:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (Account) Account.CREATOR.createFromParcel($r1);
                    }
                    $r5 = getGoogleAccountData((Account) $r3);
                    $r2.writeNoException();
                    if ($r5 != null) {
                        $r2.writeInt(1);
                        $r5.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 31:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (Account) Account.CREATOR.createFromParcel($r1);
                    }
                    $r4 = getGoogleAccountId((Account) $r3);
                    $r2.writeNoException();
                    $r2.writeString($r4);
                    return true;
                case 34:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    $z0 = setWorkAccountAppWhitelistFingerprint($r1.readString(), $r1.readString());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 35:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    $z0 = clearWorkAccountAppWhitelist();
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 36:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    if ($r1.readInt() != 0) {
                        $r3 = (AccountCredentials) AccountCredentials.CREATOR.createFromParcel($r1);
                    }
                    ValidateAccountCredentialsResponse $r42 = validateAccountCredentials((AccountCredentials) $r3);
                    $r2.writeNoException();
                    if ($r42 != null) {
                        $r2.writeInt(1);
                        $r42.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 37:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    GetAndAdvanceOtpCounterResponse $r43 = getAndAdvanceOtpCounter($r1.readString());
                    $r2.writeNoException();
                    if ($r43 != null) {
                        $r2.writeInt(1);
                        $r43.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.auth.firstparty.dataservice.IGoogleAccountDataService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    AccountNameCheckResponse checkAccountName(AccountNameCheckRequest accountNameCheckRequest) throws RemoteException;

    PasswordCheckResponse checkPassword(PasswordCheckRequest passwordCheckRequest) throws RemoteException;

    CheckRealNameResponse checkRealName(CheckRealNameRequest checkRealNameRequest) throws RemoteException;

    void clearFactoryResetChallenges() throws RemoteException;

    ClearTokenResponse clearToken(ClearTokenRequest clearTokenRequest) throws RemoteException;

    boolean clearWorkAccountAppWhitelist() throws RemoteException;

    TokenResponse confirmCredentials(ConfirmCredentialsRequest confirmCredentialsRequest) throws RemoteException;

    TokenResponse createAccount(GoogleAccountSetupRequest googleAccountSetupRequest) throws RemoteException;

    TokenResponse createPlusProfile(GoogleAccountSetupRequest googleAccountSetupRequest) throws RemoteException;

    AccountChangeEventsResponse getAccountChangeEvents(AccountChangeEventsRequest accountChangeEventsRequest) throws RemoteException;

    GoogleAccountData getAccountData(String str) throws RemoteException;

    Bundle getAccountExportData(String str) throws RemoteException;

    String getAccountId(String str) throws RemoteException;

    AccountRecoveryData getAccountRecoveryCountryInfo() throws RemoteException;

    AccountRecoveryData getAccountRecoveryData(AccountRecoveryDataRequest accountRecoveryDataRequest) throws RemoteException;

    AccountRecoveryGuidance getAccountRecoveryGuidance(AccountRecoveryGuidanceRequest accountRecoveryGuidanceRequest) throws RemoteException;

    GetAndAdvanceOtpCounterResponse getAndAdvanceOtpCounter(String str) throws RemoteException;

    GoogleAccountData getGoogleAccountData(Account account) throws RemoteException;

    String getGoogleAccountId(Account account) throws RemoteException;

    GplusInfoResponse getGplusInfo(GplusInfoRequest gplusInfoRequest) throws RemoteException;

    OtpResponse getOtp(OtpRequest otpRequest) throws RemoteException;

    TokenResponse getToken(TokenRequest tokenRequest) throws RemoteException;

    WebSetupConfig getWebSetupConfig(WebSetupConfigRequest webSetupConfigRequest) throws RemoteException;

    boolean installAccountFromExportData(String str, Bundle bundle) throws RemoteException;

    AccountRemovalResponse removeAccount(AccountRemovalRequest accountRemovalRequest) throws RemoteException;

    boolean setWorkAccountAppWhitelistFingerprint(String str, String str2) throws RemoteException;

    TokenResponse signIn(AccountSignInRequest accountSignInRequest) throws RemoteException;

    AccountRecoveryUpdateResult updateAccountRecoveryData(AccountRecoveryUpdateRequest accountRecoveryUpdateRequest) throws RemoteException;

    TokenResponse updateCredentials(UpdateCredentialsRequest updateCredentialsRequest) throws RemoteException;

    ValidateAccountCredentialsResponse validateAccountCredentials(AccountCredentials accountCredentials) throws RemoteException;

    CheckFactoryResetPolicyComplianceResponse zza(CheckFactoryResetPolicyComplianceRequest checkFactoryResetPolicyComplianceRequest) throws RemoteException;
}
