package com.google.android.gms.auth.firstparty.dataservice;

import android.accounts.Account;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.gms.auth.AccountChangeEventsRequest;
import com.google.android.gms.auth.AccountChangeEventsResponse;
import com.google.android.gms.auth.firstparty.shared.AccountCredentials;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzm;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class GoogleAccountDataServiceClient implements GoogleAccountDataClient {
    private final Context mContext;

    /* compiled from: dalvik_source_com.waze.apk */
    private interface zza<R> {
        R zzb(@Signature({"(", "Lcom/google/android/gms/auth/firstparty/dataservice/zza;", ")TR;"}) zza com_google_android_gms_auth_firstparty_dataservice_zza) throws RemoteException;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C06868 implements zza<AccountRecoveryData> {
        final /* synthetic */ GoogleAccountDataServiceClient gP;

        C06868(GoogleAccountDataServiceClient $r1) throws  {
            this.gP = $r1;
        }

        public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
            return zzf($r1);
        }

        public AccountRecoveryData zzf(zza $r1) throws RemoteException {
            return $r1.getAccountRecoveryCountryInfo();
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class RuntimeInterruptedException extends RuntimeException {
        public RuntimeInterruptedException(InterruptedException $r1) throws  {
            super($r1);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class RuntimeRemoteException extends RuntimeException {
        private final RemoteException hl;

        public RuntimeRemoteException(RemoteException $r1) throws  {
            super($r1);
            this.hl = $r1;
        }

        public RemoteException getWrappedException() throws  {
            return this.hl;
        }
    }

    public GoogleAccountDataServiceClient(Context $r1) throws  {
        this.mContext = (Context) zzab.zzag($r1);
    }

    private <R> R zza(@Signature({"<R:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/auth/firstparty/dataservice/GoogleAccountDataServiceClient$zza", "<TR;>;)TR;"}) zza<R> $r1) throws  {
        ServiceConnection $r2;
        zzm $r4;
        long $l0 = Binder.clearCallingIdentity();
        try {
            $r2 = new com.google.android.gms.common.zza();
            $r4 = zzm.zzbz(this.mContext);
            if ($r4.zza("com.google.android.gms.auth.DATA_PROXY", $r2, "GoogleAccountDataServiceClient")) {
                Object $r7 = $r1.zzb(com.google.android.gms.auth.firstparty.dataservice.zza.zza.zzcy($r2.zzara()));
                $r4.zzb("com.google.android.gms.auth.DATA_PROXY", $r2, "GoogleAccountDataServiceClient");
                Binder.restoreCallingIdentity($l0);
                return $r7;
            }
            Binder.restoreCallingIdentity($l0);
            return null;
        } catch (InterruptedException $r8) {
            Log.w("GoogleAccountDataServiceClient", "[GoogleAccountDataServiceClient] Interrupted when getting service.", $r8);
            $r4.zzb("com.google.android.gms.auth.DATA_PROXY", $r2, "GoogleAccountDataServiceClient");
            Binder.restoreCallingIdentity($l0);
            return null;
        } catch (RemoteException $r9) {
            Log.w("GoogleAccountDataServiceClient", "[GoogleAccountDataServiceClient] RemoteException when executing call.", $r9);
            $r4.zzb("com.google.android.gms.auth.DATA_PROXY", $r2, "GoogleAccountDataServiceClient");
            Binder.restoreCallingIdentity($l0);
            return null;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity($l0);
        }
    }

    public AccountNameCheckResponse checkAccountName(final AccountNameCheckRequest $r1) throws  {
        return (AccountNameCheckResponse) zza(new zza<AccountNameCheckResponse>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzi($r1);
            }

            public AccountNameCheckResponse zzi(zza $r1) throws RemoteException {
                return $r1.checkAccountName($r1);
            }
        });
    }

    public CheckFactoryResetPolicyComplianceResponse checkFactoryResetPolicyCompliance(final CheckFactoryResetPolicyComplianceRequest $r1) throws  {
        return (CheckFactoryResetPolicyComplianceResponse) zza(new zza<CheckFactoryResetPolicyComplianceResponse>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzn($r1);
            }

            public CheckFactoryResetPolicyComplianceResponse zzn(zza $r1) throws RemoteException {
                return $r1.zza($r1);
            }
        });
    }

    public PasswordCheckResponse checkPassword(final PasswordCheckRequest $r1) throws  {
        return (PasswordCheckResponse) zza(new zza<PasswordCheckResponse>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzr($r1);
            }

            public PasswordCheckResponse zzr(zza $r1) throws RemoteException {
                return $r1.checkPassword($r1);
            }
        });
    }

    public CheckRealNameResponse checkRealName(final CheckRealNameRequest $r1) throws  {
        return (CheckRealNameResponse) zza(new zza<CheckRealNameResponse>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzs($r1);
            }

            public CheckRealNameResponse zzs(zza $r1) throws RemoteException {
                return $r1.checkRealName($r1);
            }
        });
    }

    @Deprecated
    public void clearFactoryResetChallenges() throws  {
        zza(new zza<Void>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            {
                this.gP = $r1;
            }

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzo($r1);
            }

            public Void zzo(zza $r1) throws RemoteException {
                $r1.clearFactoryResetChallenges();
                return null;
            }
        });
    }

    public ClearTokenResponse clearToken(final ClearTokenRequest $r1) throws  {
        zzab.zzb((Object) $r1, (Object) "ClearTokenRequest cannot be null!");
        return (ClearTokenResponse) zza(new zza<ClearTokenResponse>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzd($r1);
            }

            public ClearTokenResponse zzd(zza $r1) throws RemoteException {
                return $r1.clearToken($r1);
            }
        });
    }

    public boolean clearWorkAccountAppWhitelist() throws  {
        return ((Boolean) zza(new zza<Boolean>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            {
                this.gP = $r1;
            }

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzk($r1);
            }

            public Boolean zzk(zza $r1) throws RemoteException {
                return Boolean.valueOf($r1.clearWorkAccountAppWhitelist());
            }
        })).booleanValue();
    }

    public TokenResponse confirmCredentials(final ConfirmCredentialsRequest $r1) throws  {
        return (TokenResponse) zza(new zza<TokenResponse>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzc($r1);
            }

            public TokenResponse zzc(zza $r1) throws RemoteException {
                return $r1.confirmCredentials($r1);
            }
        });
    }

    public TokenResponse createAccount(final GoogleAccountSetupRequest $r1) throws  {
        return (TokenResponse) zza(new zza<TokenResponse>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzc($r1);
            }

            public TokenResponse zzc(zza $r1) throws RemoteException {
                return $r1.createAccount($r1);
            }
        });
    }

    public TokenResponse createPlusProfile(final GoogleAccountSetupRequest $r1) throws  {
        return (TokenResponse) zza(new zza<TokenResponse>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzc($r1);
            }

            public TokenResponse zzc(zza $r1) throws RemoteException {
                return $r1.createPlusProfile($r1);
            }
        });
    }

    public AccountChangeEventsResponse getAccountChangeEvents(final AccountChangeEventsRequest $r1) throws  {
        zzab.zzag($r1);
        return (AccountChangeEventsResponse) zza(new zza<AccountChangeEventsResponse>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzl($r1);
            }

            public AccountChangeEventsResponse zzl(zza $r1) throws RemoteException {
                return $r1.getAccountChangeEvents($r1);
            }
        });
    }

    @Deprecated
    public GoogleAccountData getAccountData(@Deprecated String $r1) throws  {
        return getGoogleAccountData(new Account($r1, "com.google"));
    }

    public Bundle getAccountExportData(final String $r1) throws  {
        return (Bundle) zza(new zza<Bundle>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzj($r1);
            }

            public Bundle zzj(zza $r1) throws RemoteException {
                return $r1.getAccountExportData($r1);
            }
        });
    }

    public String getAccountId(final String $r1) throws  {
        return (String) zza(new zza<String>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzu($r1);
            }

            public String zzu(zza $r1) throws RemoteException {
                return $r1.getAccountId($r1);
            }
        });
    }

    public AccountRecoveryData getAccountRecoveryCountryInfo() throws  {
        return (AccountRecoveryData) zza(new C06868(this));
    }

    public AccountRecoveryData getAccountRecoveryData(final AccountRecoveryDataRequest $r1) throws  {
        return (AccountRecoveryData) zza(new zza<AccountRecoveryData>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzf($r1);
            }

            public AccountRecoveryData zzf(zza $r1) throws RemoteException {
                return $r1.getAccountRecoveryData($r1);
            }
        });
    }

    public AccountRecoveryGuidance getAccountRecoveryGuidance(final AccountRecoveryGuidanceRequest $r1) throws  {
        return (AccountRecoveryGuidance) zza(new zza<AccountRecoveryGuidance>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzg($r1);
            }

            public AccountRecoveryGuidance zzg(zza $r1) throws RemoteException {
                return $r1.getAccountRecoveryGuidance($r1);
            }
        });
    }

    public GetAndAdvanceOtpCounterResponse getAndAdvanceOtpCounter(final String $r1) throws  {
        return (GetAndAdvanceOtpCounterResponse) zza(new zza<GetAndAdvanceOtpCounterResponse>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzq($r1);
            }

            public GetAndAdvanceOtpCounterResponse zzq(zza $r1) throws RemoteException {
                return $r1.getAndAdvanceOtpCounter($r1);
            }
        });
    }

    public GoogleAccountData getGoogleAccountData(final Account $r1) throws  {
        return (GoogleAccountData) zza(new zza<GoogleAccountData>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public GoogleAccountData zza(zza $r1) throws RemoteException {
                return $r1.getGoogleAccountData($r1);
            }

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zza($r1);
            }
        });
    }

    public String getGoogleAccountId(final Account $r1) throws  {
        return (String) zza(new zza<String>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzu($r1);
            }

            public String zzu(zza $r1) throws RemoteException {
                return $r1.getGoogleAccountId($r1);
            }
        });
    }

    public GplusInfoResponse getGplusInfo(final GplusInfoRequest $r1) throws  {
        return (GplusInfoResponse) zza(new zza<GplusInfoResponse>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzt($r1);
            }

            public GplusInfoResponse zzt(zza $r1) throws RemoteException {
                return $r1.getGplusInfo($r1);
            }
        });
    }

    public OtpResponse getOtp(final OtpRequest $r1) throws  {
        return (OtpResponse) zza(new zza<OtpResponse>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzm($r1);
            }

            public OtpResponse zzm(zza $r1) throws RemoteException {
                return $r1.getOtp($r1);
            }
        });
    }

    public TokenResponse getToken(final TokenRequest $r1) throws  {
        zzab.zzb((Object) $r1, (Object) "TokenRequest cannot be null!");
        Bundle $r2 = $r1.getOptions();
        $r2.putLong("gads_service_connection_start_time_millis", SystemClock.elapsedRealtime());
        $r1.setOptions($r2);
        return (TokenResponse) zza(new zza<TokenResponse>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzc($r1);
            }

            public TokenResponse zzc(zza $r1) throws RemoteException {
                return $r1.getToken($r1);
            }
        });
    }

    @Deprecated
    public WebSetupConfig getWebSetupConfig(@Deprecated WebSetupConfigRequest webSetupConfigRequest) throws  {
        throw new UnsupportedOperationException();
    }

    public boolean installAccountFromExportData(final String $r1, final Bundle $r2) throws  {
        return ((Boolean) zza(new zza<Boolean>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzk($r1);
            }

            public Boolean zzk(zza $r1) throws RemoteException {
                return Boolean.valueOf($r1.installAccountFromExportData($r1, $r2));
            }
        })).booleanValue();
    }

    public AccountRemovalResponse removeAccount(final AccountRemovalRequest $r1) throws  {
        return (AccountRemovalResponse) zza(new zza<AccountRemovalResponse>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zze($r1);
            }

            public AccountRemovalResponse zze(zza $r1) throws RemoteException {
                return $r1.removeAccount($r1);
            }
        });
    }

    public boolean setWorkAccountAppWhitelistFingerprint(final String $r1, final String $r2) throws  {
        zzab.zzi($r1, "Package name must not be empty");
        return ((Boolean) zza(new zza<Boolean>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzk($r1);
            }

            public Boolean zzk(zza $r1) throws RemoteException {
                return Boolean.valueOf($r1.setWorkAccountAppWhitelistFingerprint($r1, $r2));
            }
        })).booleanValue();
    }

    public TokenResponse signIn(final AccountSignInRequest $r1) throws  {
        return (TokenResponse) zza(new zza<TokenResponse>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzc($r1);
            }

            public TokenResponse zzc(zza $r1) throws RemoteException {
                return $r1.signIn($r1);
            }
        });
    }

    public AccountRecoveryUpdateResult updateAccountRecoveryData(final AccountRecoveryUpdateRequest $r1) throws  {
        return (AccountRecoveryUpdateResult) zza(new zza<AccountRecoveryUpdateResult>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzh($r1);
            }

            public AccountRecoveryUpdateResult zzh(zza $r1) throws RemoteException {
                return $r1.updateAccountRecoveryData($r1);
            }
        });
    }

    public TokenResponse updateCredentials(final UpdateCredentialsRequest $r1) throws  {
        return (TokenResponse) zza(new zza<TokenResponse>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzc($r1);
            }

            public TokenResponse zzc(zza $r1) throws RemoteException {
                return $r1.updateCredentials($r1);
            }
        });
    }

    public ValidateAccountCredentialsResponse validateAccountCredentials(final AccountCredentials $r1) throws  {
        return (ValidateAccountCredentialsResponse) zza(new zza<ValidateAccountCredentialsResponse>(this) {
            final /* synthetic */ GoogleAccountDataServiceClient gP;

            public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                return zzp($r1);
            }

            public ValidateAccountCredentialsResponse zzp(zza $r1) throws RemoteException {
                return $r1.validateAccountCredentials($r1);
            }
        });
    }
}
