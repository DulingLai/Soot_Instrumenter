package com.google.android.gms.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.auth.firstparty.shared.Status;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzm;
import com.google.android.gms.common.util.zzr;
import com.google.android.gms.internal.zzbq;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class GoogleAuthUtilLight {
    public static final String ACCOUNT_ID_SERVICE = "^^_account_id_^^";
    public static final int CHANGE_TYPE_ACCOUNT_ADDED = 1;
    public static final int CHANGE_TYPE_ACCOUNT_REMOVED = 2;
    public static final int CHANGE_TYPE_ACCOUNT_RENAMED_FROM = 3;
    public static final int CHANGE_TYPE_ACCOUNT_RENAMED_TO = 4;
    public static final int DELEGATION_TYPE_CHILD_IMPERSONATION = 1;
    public static final String GOOGLE_ACCOUNT_TYPE = "com.google";
    public static final String KEY_ANDROID_PACKAGE_NAME = KEY_ANDROID_PACKAGE_NAME;
    public static final String KEY_CALLER_UID = KEY_CALLER_UID;
    public static final String KEY_CLIENT_PACKAGE_NAME = "clientPackageName";
    public static final String KEY_DELEGATEE_USER_ID = "delegatee_user_id";
    public static final String KEY_DELEGATION_TYPE = "delegation_type";
    public static final String KEY_HANDLE_NOTIFICATION = "handle_notification";
    public static final String KEY_NETWORK_TO_USE = "networkToUse";
    public static final String KEY_REAUTH_PROOF_TOKEN = "reauth_proof_token";
    public static final String KEY_REQUEST_ACTIONS = "request_visible_actions";
    @Deprecated
    public static final String KEY_REQUEST_VISIBLE_ACTIVITIES = "request_visible_actions";
    public static final String KEY_SUPPRESS_PROGRESS_SCREEN = "suppressProgressScreen";
    public static final String KEY_TOKEN_USE_CACHE = "UseCache";
    public static final String OEM_ONLY_KEY_REDIRECT_URI = "oauth2_redirect_uri";
    public static final String OEM_ONLY_KEY_TARGET_ANDROID_ID = "oauth2_target_device_id";
    public static final String OEM_ONLY_KEY_VERIFIER = "oauth2_authcode_verifier";
    public static final String OEM_ONLY_SCOPE_ACCOUNT_BOOTSTRAP = "_account_setup";
    public static final String SIDEWINDER_ACCOUNT_TYPE = "cn.google";
    public static final String STATUS_CAPTCHA_REQUIRED = "CaptchaRequired";
    @Deprecated
    public static final String STATUS_DEVICE_MANAGEMENT = "DeviceManagementRequiredOrSyncDisabled";
    public static final String STATUS_INTERRUPTED = "Interrupted";
    public static final String STATUS_NEEDS_PERMISSION = "NeedPermission";
    public static final String STATUS_NEED_APP = "AppDownloadRequired";
    public static final String STATUS_NETWORK_ERROR = "NetworkError";
    public static final String STATUS_USER_CANCEL = "UserCancel";
    public static final String WORK_ACCOUNT_TYPE = "com.google.work";
    private static final ComponentName dP = new ComponentName("com.google.android.gms", "com.google.android.gms.auth.GetToken");
    private static final ComponentName dQ = new ComponentName("com.google.android.gms", "com.google.android.gms.recovery.RecoveryService");

    /* compiled from: dalvik_source_com.waze.apk */
    private interface zza<T> {
        T exec(@Signature({"(", "Landroid/os/IBinder;", ")TT;"}) IBinder iBinder) throws RemoteException, IOException, GoogleAuthException;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C06461 implements zza<TokenData> {
        final /* synthetic */ Account dR;
        final /* synthetic */ String dS;
        final /* synthetic */ Bundle val$options;

        C06461(Account $r1, String $r2, Bundle $r3) throws  {
            this.dR = $r1;
            this.dS = $r2;
            this.val$options = $r3;
        }

        public /* synthetic */ Object exec(IBinder $r1) throws RemoteException, IOException, GoogleAuthException {
            return zzcb($r1);
        }

        public TokenData zzcb(IBinder $r1) throws RemoteException, IOException, GoogleAuthException {
            Bundle $r5 = (Bundle) GoogleAuthUtilLight.zzr(com.google.android.gms.internal.zzbq.zza.zza($r1).zza(this.dR, this.dS, this.val$options));
            TokenData $r7 = TokenData.zzd($r5, "tokenDetails");
            if ($r7 != null) {
                return $r7;
            }
            String $r4 = $r5.getString(Status.EXTRA_KEY_STATUS);
            Intent $r9 = (Intent) $r5.getParcelable("userRecoveryIntent");
            Status $r10 = Status.fromWireCode($r4);
            if (Status.isUserRecoverableError($r10)) {
                throw new UserRecoverableAuthException($r4, $r9);
            } else if (Status.isRetryableError($r10)) {
                throw new IOException($r4);
            } else {
                throw new GoogleAuthException($r4);
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C06472 implements zza<Void> {
        final /* synthetic */ Bundle bE;
        final /* synthetic */ String dT;

        C06472(String $r1, Bundle $r2) throws  {
            this.dT = $r1;
            this.bE = $r2;
        }

        public /* synthetic */ Object exec(IBinder $r1) throws RemoteException, IOException, GoogleAuthException {
            return zzcc($r1);
        }

        public Void zzcc(IBinder $r1) throws RemoteException, IOException, GoogleAuthException {
            Bundle $r4 = (Bundle) GoogleAuthUtilLight.zzr(com.google.android.gms.internal.zzbq.zza.zza($r1).zza(this.dT, this.bE));
            String $r3 = $r4.getString(Status.EXTRA_KEY_STATUS);
            if ($r4.getBoolean("booleanResult")) {
                return null;
            }
            throw new GoogleAuthException($r3);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C06483 implements zza<RecoveryDecision> {
        final /* synthetic */ Bundle bE;
        final /* synthetic */ String dU;
        final /* synthetic */ String dV;
        final /* synthetic */ boolean dW;

        C06483(String $r1, String $r2, boolean $z0, Bundle $r3) throws  {
            this.dU = $r1;
            this.dV = $r2;
            this.dW = $z0;
            this.bE = $r3;
        }

        public /* synthetic */ Object exec(IBinder $r1) throws RemoteException, IOException, GoogleAuthException {
            return zzcd($r1);
        }

        public RecoveryDecision zzcd(IBinder $r1) throws RemoteException, IOException, GoogleAuthException {
            return (RecoveryDecision) GoogleAuthUtilLight.zzr(com.google.android.gms.internal.zzbr.zza.zzb($r1).zza(this.dU, this.dV, this.dW, this.bE));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C06494 implements zza<RecoveryReadResponse> {
        final /* synthetic */ String dU;
        final /* synthetic */ Context dX;

        C06494(String $r1, Context $r2) throws  {
            this.dU = $r1;
            this.dX = $r2;
        }

        public /* synthetic */ Object exec(IBinder $r1) throws RemoteException, IOException, GoogleAuthException {
            return zzce($r1);
        }

        public RecoveryReadResponse zzce(IBinder $r1) throws RemoteException, IOException, GoogleAuthException {
            return (RecoveryReadResponse) GoogleAuthUtilLight.zzr(com.google.android.gms.internal.zzbr.zza.zzb($r1).zzd(this.dU, this.dX.getPackageName()));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C06505 implements zza<RecoveryWriteResponse> {
        final /* synthetic */ Context dX;
        final /* synthetic */ RecoveryWriteRequest dY;

        C06505(RecoveryWriteRequest $r1, Context $r2) throws  {
            this.dY = $r1;
            this.dX = $r2;
        }

        public /* synthetic */ Object exec(IBinder $r1) throws RemoteException, IOException, GoogleAuthException {
            return zzcf($r1);
        }

        public RecoveryWriteResponse zzcf(IBinder $r1) throws RemoteException, IOException, GoogleAuthException {
            return (RecoveryWriteResponse) GoogleAuthUtilLight.zzr(com.google.android.gms.internal.zzbr.zza.zzb($r1).zza(this.dY, this.dX.getPackageName()));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C06516 implements zza<List<AccountChangeEvent>> {
        final /* synthetic */ String dZ;
        final /* synthetic */ int ea;

        C06516(String $r1, int $i0) throws  {
            this.dZ = $r1;
            this.ea = $i0;
        }

        public /* synthetic */ Object exec(IBinder $r1) throws RemoteException, IOException, GoogleAuthException {
            return zzcg($r1);
        }

        public List<AccountChangeEvent> zzcg(@Signature({"(", "Landroid/os/IBinder;", ")", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/AccountChangeEvent;", ">;"}) IBinder $r1) throws RemoteException, IOException, GoogleAuthException {
            return ((AccountChangeEventsResponse) GoogleAuthUtilLight.zzr(com.google.android.gms.internal.zzbq.zza.zza($r1).getAccountChangeEvents(new AccountChangeEventsRequest().setAccountName(this.dZ).setEventIndex(this.ea)))).getEvents();
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C06527 implements zza<Account[]> {
        final /* synthetic */ String eb;
        final /* synthetic */ String[] ec;

        C06527(String $r1, String[] $r2) throws  {
            this.eb = $r1;
            this.ec = $r2;
        }

        public /* synthetic */ Object exec(IBinder $r1) throws RemoteException, IOException, GoogleAuthException {
            return zzch($r1);
        }

        public Account[] zzch(IBinder $r1) throws RemoteException, IOException, GoogleAuthException {
            zzbq $r2 = com.google.android.gms.internal.zzbq.zza.zza($r1);
            Bundle $r3 = new Bundle();
            $r3.putString("accountType", this.eb);
            $r3.putStringArray("account_features", this.ec);
            Parcelable[] $r7 = ((Bundle) GoogleAuthUtilLight.zzr($r2.zza($r3))).getParcelableArray("accounts");
            Account[] $r8 = new Account[$r7.length];
            for (int $i0 = 0; $i0 < $r7.length; $i0++) {
                $r8[$i0] = (Account) $r7[$i0];
            }
            return $r8;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C06538 implements zza<Bundle> {
        final /* synthetic */ Account dR;

        C06538(Account $r1) throws  {
            this.dR = $r1;
        }

        public /* synthetic */ Object exec(IBinder $r1) throws RemoteException, IOException, GoogleAuthException {
            return zzci($r1);
        }

        public Bundle zzci(IBinder $r1) throws RemoteException, IOException, GoogleAuthException {
            return (Bundle) GoogleAuthUtilLight.zzr(com.google.android.gms.internal.zzbq.zza.zza($r1).zza(this.dR));
        }
    }

    static {
        if (VERSION.SDK_INT < 11) {
        }
        if (VERSION.SDK_INT < 14) {
        }
    }

    GoogleAuthUtilLight() throws  {
    }

    public static void clearPassword(Context $r0, Account $r1) throws RemoteException, GoogleAuthException, GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        zzab.zzag($r1);
        if (zzr.zzazm()) {
            zza($r0, $r1);
        } else {
            AccountManager.get($r0).clearPassword($r1);
        }
    }

    public static void clearToken(Context $r0, String $r1) throws GooglePlayServicesAvailabilityException, GoogleAuthException, IOException {
        zzab.zzgq("Calling this from your main thread can lead to deadlock");
        zzbb($r0);
        Bundle $r2 = new Bundle();
        String $r4 = $r0.getApplicationInfo().packageName;
        $r2.putString("clientPackageName", $r4);
        if (!$r2.containsKey(KEY_ANDROID_PACKAGE_NAME)) {
            $r2.putString(KEY_ANDROID_PACKAGE_NAME, $r4);
        }
        zza($r0, dP, new C06472($r1, $r2));
    }

    public static RecoveryReadResponse fetchCurrentRecoveryData(Context $r0, String $r1) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        zzab.zzgq("Calling this from your main thread can lead to deadlock");
        zzab.zzi($r1, "Email cannot be empty or null.");
        zzbb($r0);
        return (RecoveryReadResponse) zza($r0, dQ, new C06494($r1, $r0));
    }

    public static List<AccountChangeEvent> getAccountChangeEvents(@Signature({"(", "Landroid/content/Context;", "I", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/AccountChangeEvent;", ">;"}) Context $r0, @Signature({"(", "Landroid/content/Context;", "I", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/AccountChangeEvent;", ">;"}) int $i0, @Signature({"(", "Landroid/content/Context;", "I", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/AccountChangeEvent;", ">;"}) String $r1) throws GoogleAuthException, IOException {
        zzab.zzi($r1, "accountName must be provided");
        zzab.zzgq("Calling this from your main thread can lead to deadlock");
        zzbb($r0);
        return (List) zza($r0, dP, new C06516($r1, $i0));
    }

    public static String getAccountId(Context $r0, String $r1) throws GoogleAuthException, IOException {
        zzab.zzi($r1, "accountName must be provided");
        zzab.zzgq("Calling this from your main thread can lead to deadlock");
        zzbb($r0);
        return getToken($r0, $r1, "^^_account_id_^^", new Bundle());
    }

    public static Account[] getAccounts(Context $r0, String $r1) throws RemoteException, GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        zzab.zzgy($r1);
        return zzr.zzazm() ? zzl($r0, $r1) : AccountManager.get($r0).getAccountsByType($r1);
    }

    public static Account[] getAccounts(Context $r0, String $r1, String[] $r2) throws GoogleAuthException, IOException {
        zzab.zzag($r0);
        zzab.zzgy($r1);
        zzbb($r0);
        return (Account[]) zza($r0, dP, new C06527($r1, $r2));
    }

    public static RecoveryDecision getRecoveryDetails(Context $r0, String $r1, String $r2, boolean $z0) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        zzab.zzgq("Calling this from your main thread can lead to deadlock");
        zzab.zzi($r1, "Email cannot be empty or null.");
        zzbb($r0);
        Bundle $r3 = new Bundle();
        $r3.putString(KEY_ANDROID_PACKAGE_NAME, $r0.getPackageName());
        return (RecoveryDecision) zza($r0, dQ, new C06483($r1, $r2, $z0, $r3));
    }

    public static PendingIntent getRecoveryIfSuggested(Context $r0, String $r1, String $r2, boolean $z0) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        RecoveryDecision $r3 = getRecoveryDetails($r0, $r1, $r2, $z0);
        return ($r3.showRecoveryInterstitial && $r3.isRecoveryInterstitialAllowed) ? $r3.recoveryIntent : null;
    }

    public static String getToken(Context $r0, Account $r1, String $r2) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return getToken($r0, $r1, $r2, new Bundle());
    }

    public static String getToken(Context $r0, Account $r1, String $r2, Bundle $r3) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return zzc($r0, $r1, $r2, $r3).getToken();
    }

    @Deprecated
    public static String getToken(@Deprecated Context $r0, @Deprecated String $r1, @Deprecated String $r2) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return getToken($r0, new Account($r1, "com.google"), $r2);
    }

    @Deprecated
    public static String getToken(@Deprecated Context $r0, @Deprecated String $r1, @Deprecated String $r2, @Deprecated Bundle $r3) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return getToken($r0, new Account($r1, "com.google"), $r2, $r3);
    }

    @RequiresPermission("android.permission.MANAGE_ACCOUNTS")
    @Deprecated
    public static void invalidateToken(@Deprecated Context $r0, @Deprecated String $r1) throws  {
        AccountManager.get($r0).invalidateAuthToken("com.google", $r1);
    }

    @TargetApi(23)
    public static Bundle removeAccount(Context $r0, Account $r1) throws GoogleAuthException, IOException {
        zzab.zzag($r0);
        zzab.zzb((Object) $r1, (Object) "Account cannot be null.");
        zzbb($r0);
        return (Bundle) zza($r0, dP, new C06538($r1));
    }

    public static RecoveryWriteResponse updateRecoveryData(Context $r0, RecoveryWriteRequest $r1) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        zzab.zzgq("Calling this from your main thread can lead to deadlock");
        zzbb($r0);
        return (RecoveryWriteResponse) zza($r0, dQ, new C06505($r1, $r0));
    }

    private static <T> T zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Landroid/content/Context;", "Landroid/content/ComponentName;", "Lcom/google/android/gms/auth/GoogleAuthUtilLight$zza", "<TT;>;)TT;"}) Context $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Landroid/content/Context;", "Landroid/content/ComponentName;", "Lcom/google/android/gms/auth/GoogleAuthUtilLight$zza", "<TT;>;)TT;"}) ComponentName $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Landroid/content/Context;", "Landroid/content/ComponentName;", "Lcom/google/android/gms/auth/GoogleAuthUtilLight$zza", "<TT;>;)TT;"}) zza<T> $r2) throws IOException, GoogleAuthException {
        Exception $r7;
        ServiceConnection $r3 = new com.google.android.gms.common.zza();
        zzm $r4 = zzm.zzbz($r0);
        if ($r4.zza($r1, $r3, "GoogleAuthUtil")) {
            try {
                Object $r6 = $r2.exec($r3.zzara());
                $r4.zzb($r1, $r3, "GoogleAuthUtil");
                return $r6;
            } catch (RemoteException e) {
                $r7 = e;
                try {
                    Log.i("GoogleAuthUtil", "Error on service connection.", $r7);
                    throw new IOException("Error on service connection.", $r7);
                } catch (Throwable th) {
                    $r4.zzb($r1, $r3, "GoogleAuthUtil");
                }
            } catch (InterruptedException e2) {
                $r7 = e2;
                Log.i("GoogleAuthUtil", "Error on service connection.", $r7);
                throw new IOException("Error on service connection.", $r7);
            }
        }
        throw new IOException("Could not bind to service.");
    }

    @TargetApi(23)
    private static void zza(Context $r0, Account $r1) throws RemoteException, GoogleAuthException, GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        zzbb($r0);
        ContentProviderClient $r4 = ((Context) zzab.zzag($r0)).getContentResolver().acquireContentProviderClient("com.google.android.gms.auth.accounts");
        if ($r4 == null) {
            Log.w("GoogleAuthUtil", "ContentProviderClient is null. Can't clear password");
            return;
        }
        try {
            Bundle $r5 = new Bundle();
            $r5.putParcelable("clear_password", $r1);
            $r4.call("clear_password", null, $r5);
        } finally {
            $r4.release();
        }
    }

    private static void zzbb(Context $r0) throws GoogleAuthException {
        try {
            GooglePlayServicesUtilLight.zzbb($r0.getApplicationContext());
        } catch (GooglePlayServicesRepairableException $r1) {
            throw new GooglePlayServicesAvailabilityException($r1.getConnectionStatusCode(), $r1.getMessage(), $r1.getIntent());
        } catch (GooglePlayServicesNotAvailableException $r5) {
            throw new GoogleAuthException($r5.getMessage());
        }
    }

    public static TokenData zzc(Context $r0, Account $r1, String $r2, Bundle $r3) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        zzab.zzgq("Calling this from your main thread can lead to deadlock");
        zzab.zzi($r2, "Scope cannot be empty or null.");
        zzab.zzb((Object) $r1, (Object) "Account cannot be null.");
        zzbb($r0);
        Bundle $r4 = $r3 == null ? new Bundle() : new Bundle($r3);
        String $r6 = $r0.getApplicationInfo().packageName;
        $r4.putString("clientPackageName", $r6);
        if (TextUtils.isEmpty($r4.getString(KEY_ANDROID_PACKAGE_NAME))) {
            $r4.putString(KEY_ANDROID_PACKAGE_NAME, $r6);
        }
        $r4.putLong("service_connection_start_time_millis", SystemClock.elapsedRealtime());
        return (TokenData) zza($r0, dP, new C06461($r1, $r2, $r4));
    }

    static void zzi(Intent $r0) throws  {
        if ($r0 == null) {
            throw new IllegalArgumentException("Callback cannot be null.");
        }
        try {
            Intent.parseUri($r0.toUri(1), 1);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Parameter callback contains invalid data. It must be serializable using toUri() and parseUri().");
        }
    }

    @TargetApi(23)
    private static Account[] zzl(Context $r0, String $r1) throws RemoteException, GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        GoogleApiAvailabilityLight.getInstance().zzbm($r0);
        ContentProviderClient $r5 = ((Context) zzab.zzag($r0)).getContentResolver().acquireContentProviderClient("com.google.android.gms.auth.accounts");
        if ($r5 == null) {
            return new Account[0];
        }
        try {
            Parcelable[] $r8 = $r5.call("get_accounts", $r1, new Bundle()).getParcelableArray("accounts");
            Account[] $r6 = new Account[$r8.length];
            for (int $i0 = 0; $i0 < $r8.length; $i0++) {
                $r6[$i0] = (Account) $r8[$i0];
            }
            $r5.release();
            return $r6;
        } catch (Exception $r11) {
            $r1 = "Accounts ContentProvider failed: ";
            String $r13 = String.valueOf($r11.getMessage());
            if ($r13.length() != 0) {
                $r1 = $r1.concat($r13);
            } else {
                String str = new String("Accounts ContentProvider failed: ");
            }
            throw new RemoteException($r1);
        } catch (Throwable th) {
            $r5.release();
        }
    }

    private static <T> T zzr(@Signature({"<T:", "Ljava/lang/Object;", ">(TT;)TT;"}) T $r0) throws IOException {
        if ($r0 != null) {
            return $r0;
        }
        Log.w("GoogleAuthUtil", "Binder call returned null.");
        throw new IOException("Service unavailable.");
    }
}
