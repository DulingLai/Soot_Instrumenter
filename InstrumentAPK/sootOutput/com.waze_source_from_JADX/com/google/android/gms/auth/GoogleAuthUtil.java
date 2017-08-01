package com.google.android.gms.auth;

import android.accounts.Account;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public final class GoogleAuthUtil extends GoogleAuthUtilLight {
    public static final String ACCOUNT_ID_SERVICE = "^^_account_id_^^";
    public static final int CHANGE_TYPE_ACCOUNT_ADDED = 1;
    public static final int CHANGE_TYPE_ACCOUNT_REMOVED = 2;
    public static final int CHANGE_TYPE_ACCOUNT_RENAMED_FROM = 3;
    public static final int CHANGE_TYPE_ACCOUNT_RENAMED_TO = 4;
    public static final int DELEGATION_TYPE_CHILD_IMPERSONATION = 1;
    public static final String GOOGLE_ACCOUNT_TYPE = "com.google";
    public static final String KEY_ANDROID_PACKAGE_NAME = GoogleAuthUtilLight.KEY_ANDROID_PACKAGE_NAME;
    public static final String KEY_CALLER_UID = GoogleAuthUtilLight.KEY_CALLER_UID;
    public static final String KEY_CLIENT_PACKAGE_NAME = "clientPackageName";
    public static final String KEY_DELEGATEE_USER_ID = "delegatee_user_id";
    public static final String KEY_DELEGATION_TYPE = "delegation_type";
    public static final String KEY_HANDLE_NOTIFICATION = "handle_notification";
    public static final String KEY_NETWORK_TO_USE = "networkToUse";
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

    private GoogleAuthUtil() throws  {
    }

    public static void clearPassword(Context $r0, Account $r1) throws RemoteException, GoogleAuthException, GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        GoogleAuthUtilLight.clearPassword($r0, $r1);
    }

    public static void clearToken(Context $r0, String $r1) throws GooglePlayServicesAvailabilityException, GoogleAuthException, IOException {
        GoogleAuthUtilLight.clearToken($r0, $r1);
    }

    public static RecoveryReadResponse fetchCurrentRecoveryData(Context $r0, String $r1) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return GoogleAuthUtilLight.fetchCurrentRecoveryData($r0, $r1);
    }

    public static List<AccountChangeEvent> getAccountChangeEvents(@Signature({"(", "Landroid/content/Context;", "I", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/AccountChangeEvent;", ">;"}) Context $r0, @Signature({"(", "Landroid/content/Context;", "I", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/AccountChangeEvent;", ">;"}) int $i0, @Signature({"(", "Landroid/content/Context;", "I", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/AccountChangeEvent;", ">;"}) String $r1) throws GoogleAuthException, IOException {
        return GoogleAuthUtilLight.getAccountChangeEvents($r0, $i0, $r1);
    }

    public static String getAccountId(Context $r0, String $r1) throws GoogleAuthException, IOException {
        return GoogleAuthUtilLight.getAccountId($r0, $r1);
    }

    public static Account[] getAccounts(Context $r0, String $r1) throws RemoteException, GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        return GoogleAuthUtilLight.getAccounts($r0, $r1);
    }

    public static Account[] getAccounts(Context $r0, String $r1, String[] $r2) throws GoogleAuthException, IOException {
        return GoogleAuthUtilLight.getAccounts($r0, $r1, $r2);
    }

    public static RecoveryDecision getRecoveryDetails(Context $r0, String $r1, String $r2, boolean $z0) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return GoogleAuthUtilLight.getRecoveryDetails($r0, $r1, $r2, $z0);
    }

    public static PendingIntent getRecoveryIfSuggested(Context $r0, String $r1, String $r2, boolean $z0) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return GoogleAuthUtilLight.getRecoveryIfSuggested($r0, $r1, $r2, $z0);
    }

    public static String getToken(Context $r0, Account $r1, String $r2) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return GoogleAuthUtilLight.getToken($r0, $r1, $r2);
    }

    public static String getToken(Context $r0, Account $r1, String $r2, Bundle $r3) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return GoogleAuthUtilLight.getToken($r0, $r1, $r2, $r3);
    }

    @Deprecated
    public static String getToken(@Deprecated Context $r0, @Deprecated String $r1, @Deprecated String $r2) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return GoogleAuthUtilLight.getToken($r0, $r1, $r2);
    }

    @Deprecated
    public static String getToken(@Deprecated Context $r0, @Deprecated String $r1, @Deprecated String $r2, @Deprecated Bundle $r3) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return GoogleAuthUtilLight.getToken($r0, $r1, $r2, $r3);
    }

    public static String getTokenWithNotification(Context $r0, Account $r1, String $r2, Bundle $r3) throws IOException, UserRecoverableNotifiedException, GoogleAuthException {
        return zza($r0, $r1, $r2, $r3).getToken();
    }

    public static String getTokenWithNotification(Context $r0, Account $r1, String $r2, Bundle $r4, Intent $r3) throws IOException, UserRecoverableNotifiedException, GoogleAuthException {
        GoogleAuthUtilLight.zzi($r3);
        if ($r4 == null) {
            $r4 = new Bundle();
        }
        $r4.putParcelable("callback_intent", $r3);
        $r4.putBoolean("handle_notification", true);
        return zzb($r0, $r1, $r2, $r4).getToken();
    }

    public static String getTokenWithNotification(Context $r0, Account $r1, String $r2, Bundle $r4, String $r3, Bundle $r5) throws IOException, UserRecoverableNotifiedException, GoogleAuthException {
        zzab.zzi($r3, "Authority cannot be empty or null.");
        if ($r4 == null) {
            $r4 = new Bundle();
        }
        if ($r5 == null) {
            $r5 = new Bundle();
        }
        ContentResolver.validateSyncExtrasBundle($r5);
        $r4.putString("authority", $r3);
        $r4.putBundle("sync_extras", $r5);
        $r4.putBoolean("handle_notification", true);
        return zzb($r0, $r1, $r2, $r4).getToken();
    }

    @Deprecated
    public static String getTokenWithNotification(@Deprecated Context $r0, @Deprecated String $r1, @Deprecated String $r2, @Deprecated Bundle $r3) throws IOException, UserRecoverableNotifiedException, GoogleAuthException {
        return getTokenWithNotification($r0, new Account($r1, "com.google"), $r2, $r3);
    }

    @Deprecated
    public static String getTokenWithNotification(@Deprecated Context $r0, @Deprecated String $r1, @Deprecated String $r2, @Deprecated Bundle $r3, @Deprecated Intent $r4) throws IOException, UserRecoverableNotifiedException, GoogleAuthException {
        return getTokenWithNotification($r0, new Account($r1, "com.google"), $r2, $r3, $r4);
    }

    @Deprecated
    public static String getTokenWithNotification(@Deprecated Context $r0, @Deprecated String $r1, @Deprecated String $r2, @Deprecated Bundle $r3, @Deprecated String $r4, @Deprecated Bundle $r5) throws IOException, UserRecoverableNotifiedException, GoogleAuthException {
        return getTokenWithNotification($r0, new Account($r1, "com.google"), $r2, $r3, $r4, $r5);
    }

    @RequiresPermission("android.permission.MANAGE_ACCOUNTS")
    @Deprecated
    public static void invalidateToken(@Deprecated Context $r0, @Deprecated String $r1) throws  {
        GoogleAuthUtilLight.invalidateToken($r0, $r1);
    }

    @TargetApi(23)
    public static Bundle removeAccount(Context $r0, Account $r1) throws GoogleAuthException, IOException {
        return GoogleAuthUtilLight.removeAccount($r0, $r1);
    }

    public static RecoveryWriteResponse updateRecoveryData(Context $r0, RecoveryWriteRequest $r1) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return GoogleAuthUtilLight.updateRecoveryData($r0, $r1);
    }

    public static TokenData zza(Context $r0, Account $r1, String $r2, Bundle $r4) throws IOException, UserRecoverableNotifiedException, GoogleAuthException {
        if ($r4 == null) {
            $r4 = new Bundle();
        }
        $r4.putBoolean("handle_notification", true);
        return zzb($r0, $r1, $r2, $r4);
    }

    private static TokenData zzb(Context $r0, Account $r1, String $r2, Bundle $r3) throws IOException, GoogleAuthException {
        if ($r3 == null) {
            $r3 = new Bundle();
        }
        try {
            TokenData $r4 = zzc($r0, $r1, $r2, $r3);
            GooglePlayServicesUtilLight.zzbn($r0);
            return $r4;
        } catch (GooglePlayServicesAvailabilityException $r5) {
            GooglePlayServicesUtil.showErrorNotification($r5.getConnectionStatusCode(), $r0);
            throw new UserRecoverableNotifiedException("User intervention required. Notification has been pushed.");
        } catch (UserRecoverableAuthException e) {
            GooglePlayServicesUtilLight.zzbn($r0);
            throw new UserRecoverableNotifiedException("User intervention required. Notification has been pushed.");
        }
    }

    public static TokenData zzc(Context $r0, Account $r1, String $r2, Bundle $r3) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return GoogleAuthUtilLight.zzc($r0, $r1, $r2, $r3);
    }
}
