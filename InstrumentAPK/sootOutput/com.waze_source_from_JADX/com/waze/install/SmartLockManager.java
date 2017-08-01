package com.waze.install;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.waze.AppService;
import com.waze.ConfigManager;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeData;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.LoginCallback;
import com.waze.mywaze.MyWazeNativeManager.MyWazeDataHandler;
import com.waze.settings.SettingsSmartLockActivity;
import com.waze.strings.DisplayStrings;
import java.util.ArrayList;
import java.util.List;

public class SmartLockManager implements ConnectionCallbacks {
    private static final long MILLISECONDS_IN_DAY = 86400000;
    private static final long PROMPT_COOLDOWN_PERIOD = 90;
    public static final int RC_CREDENTIAL_RESOLUTION_READ = 59103;
    public static final int RC_CREDENTIAL_RESOLUTION_SAVE = 73520;
    private static final String SL_ACCOUNT_TYPE = "http://www.waze.com";
    private static SmartLockManager _instance;
    private GoogleApiClient mCredentialsApiClient;
    private CredentialRequest mCredentialsRequest;
    private List<Runnable> mPostConnectRunnables = new ArrayList();

    public interface SmartLockHasCredentialsListener {
        void onHasCredentialsDetermined(boolean z) throws ;
    }

    class C18263 implements SmartLockHasCredentialsListener {
        C18263() throws  {
        }

        public void onHasCredentialsDetermined(boolean $z0) throws  {
            if (!$z0) {
                SmartLockManager.this.saveCredentials(AppService.getActiveActivity(), "OPT_IN");
            }
        }
    }

    public interface SmartLockEventListener {
        void onSignInFailure() throws ;

        void onSignInSuccess() throws ;
    }

    public static synchronized SmartLockManager getInstance() throws  {
        Class cls = SmartLockManager.class;
        synchronized (cls) {
            try {
                if (_instance == null) {
                    _instance = new SmartLockManager();
                }
                SmartLockManager $r0 = _instance;
                return $r0;
            } finally {
                cls = SmartLockManager.class;
            }
        }
    }

    private SmartLockManager() throws  {
    }

    public void initialize(Context $r1) throws  {
        if (this.mCredentialsApiClient == null) {
            this.mCredentialsApiClient = new Builder($r1).addConnectionCallbacks(this).addApi(Auth.CREDENTIALS_API).build();
            this.mCredentialsRequest = new CredentialRequest.Builder().setPasswordLoginSupported(true).build();
            this.mCredentialsApiClient.connect();
        }
    }

    public void signIn(final Activity $r1, final SmartLockEventListener $r2, final boolean $z0) throws  {
        if (ConfigManager.getInstance().getConfigValueBool(370)) {
            if (this.mCredentialsApiClient == null) {
                initialize($r1.getApplicationContext());
            }
            if (!addToPostConnectIfNotConnected(new Runnable() {
                public void run() throws  {
                    SmartLockManager.this.signIn($r1, $r2, $z0);
                }
            })) {
                Auth.CredentialsApi.request(this.mCredentialsApiClient, this.mCredentialsRequest).setResultCallback(new ResultCallback<CredentialRequestResult>() {
                    public void onResult(@NonNull CredentialRequestResult $r1) throws  {
                        if ($r1.getStatus().isSuccess()) {
                            SmartLockManager.this.onCredentialRetrieved($r1.getCredential(), $r2, $z0);
                        } else {
                            SmartLockManager.this.resolveResult($r1.getStatus(), $r1);
                        }
                    }
                });
            }
        }
    }

    public void checkSmartLockForExistingUser() throws  {
        if (ConfigManager.getInstance().getConfigValueBool(370) && !MyWazeNativeManager.getInstance().isRandomUserNTV()) {
            long $l0 = System.currentTimeMillis() / 86400000;
            if ($l0 - ConfigManager.getInstance().getConfigValueLong(371) >= PROMPT_COOLDOWN_PERIOD) {
                ConfigManager.getInstance().setConfigValueLong(371, $l0);
                determineCredentialsExist(new C18263());
            }
        }
    }

    public void determineCredentialsExist(final SmartLockHasCredentialsListener $r1) throws  {
        if ($r1 != null) {
            if (!ConfigManager.getInstance().getConfigValueBool(370)) {
                $r1.onHasCredentialsDetermined(false);
            }
            if (!addToPostConnectIfNotConnected(new Runnable() {
                public void run() throws  {
                    SmartLockManager.this.determineCredentialsExist($r1);
                }
            })) {
                Auth.CredentialsApi.disableAutoSignIn(this.mCredentialsApiClient);
                Auth.CredentialsApi.request(this.mCredentialsApiClient, this.mCredentialsRequest).setResultCallback(new ResultCallback<CredentialRequestResult>() {
                    public void onResult(@NonNull CredentialRequestResult $r1) throws  {
                        if ($r1.getStatus().isSuccess() || $r1.getStatus().getStatusCode() == 6) {
                            $r1.onHasCredentialsDetermined(true);
                        } else {
                            $r1.onHasCredentialsDetermined(false);
                        }
                    }
                });
            }
        }
    }

    private void onCredentialRetrieved(Credential $r1, SmartLockEventListener $r2, boolean $z0) throws  {
        String $r3 = $r1.getId();
        String $r4 = $r1.getPassword();
        if ($z0) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SMART_LOCK_INTERNAL_POPUP_SHOWN).send();
            AnalyticsBuilder $r5 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SMART_LOCK_INTERNAL_POPUP_CLICKED);
            final AnalyticsBuilder analyticsBuilder = $r5;
            final String str = $r3;
            final String str2 = $r4;
            final Credential credential = $r1;
            final SmartLockEventListener smartLockEventListener = $r2;
            MsgBox.openConfirmDialogJavaCallback(DisplayStrings.displayString(DisplayStrings.DS_SMART_LOCK_PROMPT_TITLE), DisplayStrings.displayString(DisplayStrings.DS_SMART_LOCK_PROMPT_DESCRIPTION), false, new OnClickListener() {
                public void onClick(DialogInterface dialog, int $i0) throws  {
                    if ($i0 == 1) {
                        analyticsBuilder.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, AnalyticsEvents.ANALYTICS_YES).send();
                        SmartLockManager.this.performLogin(str, str2, credential, smartLockEventListener);
                        return;
                    }
                    analyticsBuilder.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, AnalyticsEvents.ANALYTICS_NO).send();
                }
            }, DisplayStrings.displayString(357), DisplayStrings.displayString(DisplayStrings.DS_NO_THANKS), 0);
            return;
        }
        performLogin($r3, $r4, $r1, $r2);
    }

    private void performLogin(String $r1, String $r2, final Credential $r3, final SmartLockEventListener $r4) throws  {
        MyWazeNativeManager.getInstance().doLogin($r1, $r2, $r1, new LoginCallback() {

            class C18301 implements ResultCallback<Status> {
                C18301() throws  {
                }

                public void onResult(@NonNull Status status) throws  {
                }
            }

            public void onLogin(boolean $z0) throws  {
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_LOGIN_WAZER_RESPONSE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, $z0 ? AnalyticsEvents.ANALYTICS_EVENT_SUCCESS : AnalyticsEvents.ANALYTICS_EVENT_FAILURE, true);
                AnalyticsBuilder $r3 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SMART_LOCK_AUTO_RECOVER);
                if ($z0) {
                    $r3.addParam("INFO", AnalyticsEvents.ANALYTICS_EVENT_SUCCESS).send();
                    NativeManager.getInstance().SetSocialIsFirstTime(false);
                    if ($r4 != null) {
                        $r4.onSignInSuccess();
                        return;
                    }
                    return;
                }
                $r3.addParam("INFO", "FAIL").send();
                Auth.CredentialsApi.delete(SmartLockManager.this.mCredentialsApiClient, $r3).setResultCallback(new C18301());
                if ($r4 != null) {
                    $r4.onSignInFailure();
                }
            }
        });
    }

    private void resolveResult(Status $r1, Activity $r2) throws  {
        if ($r1.getStatusCode() == 6) {
            final Status status = $r1;
            final Activity activity = $r2;
            MsgBox.openConfirmDialogJavaCallback(DisplayStrings.displayString(DisplayStrings.DS_SMART_LOCK_PROMPT_TITLE), DisplayStrings.displayString(DisplayStrings.DS_SMART_LOCK_PROMPT_DESCRIPTION), false, new OnClickListener() {
                public void onClick(DialogInterface dialog, int $i0) throws  {
                    if ($i0 == 1) {
                        try {
                            status.startResolutionForResult(activity, SmartLockManager.RC_CREDENTIAL_RESOLUTION_READ);
                        } catch (SendIntentException e) {
                        }
                    }
                }
            }, DisplayStrings.displayString(357), DisplayStrings.displayString(DisplayStrings.DS_NO_THANKS), 0);
        }
    }

    public void credentialsResolutionActivityResult(int $i0, int $i1, Intent $r1, SmartLockEventListener $r2) throws  {
        if (ConfigManager.getInstance().getConfigValueBool(370)) {
            final int i = $i0;
            final int i2 = $i1;
            final Intent intent = $r1;
            final SmartLockEventListener smartLockEventListener = $r2;
            if (!addToPostConnectIfNotConnected(new Runnable() {
                public void run() throws  {
                    SmartLockManager.this.credentialsResolutionActivityResult(i, i2, intent, smartLockEventListener);
                }
            })) {
                if ($i0 == 59103) {
                    if ($i1 == -1) {
                        onCredentialRetrieved((Credential) $r1.getParcelableExtra(Credential.EXTRA_KEY), $r2, false);
                    }
                } else if ($i0 == 73520) {
                    AnalyticsBuilder $r7 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SMART_LOCK_POPUP_CLICKED);
                    if ($i1 == -1) {
                        $r7.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, AnalyticsEvents.ANALYTICS_YES).send();
                        if (AppService.getActiveActivity() instanceof SettingsSmartLockActivity) {
                            ((SettingsSmartLockActivity) AppService.getActiveActivity()).onSaveCompleted();
                            return;
                        }
                        return;
                    }
                    $r7.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, AnalyticsEvents.ANALYTICS_NO).send();
                }
            }
        }
    }

    public void saveCredentials(ActivityBase $r1, String $r2) throws  {
        saveCredentials($r1, 0, $r2);
    }

    private void saveCredentials(final ActivityBase $r1, final int $i0, final String $r2) throws  {
        if (ConfigManager.getInstance().getConfigValueBool(370)) {
            if (this.mCredentialsApiClient == null) {
                initialize($r1.getApplicationContext());
            }
            if (!addToPostConnectIfNotConnected(new Runnable() {
                public void run() throws  {
                    SmartLockManager.this.saveCredentials($r1, $i0, $r2);
                }
            })) {
                MyWazeNativeManager.getInstance().getMyWazeData(new MyWazeDataHandler() {

                    class C18221 implements Runnable {
                        C18221() throws  {
                        }

                        public void run() throws  {
                            SmartLockManager.this.saveCredentials($r1, $i0 + 1, $r2);
                        }
                    }

                    public void onMyWazeDataReceived(MyWazeData $r1) throws  {
                        String $r3 = $r1.mUserName;
                        if (TextUtils.isEmpty($r1.mUserName) || $r1.mUserName.equals("Wazer")) {
                            $r3 = MyWazeNativeManager.getInstance().getRealUserNameNTV();
                        }
                        if (!TextUtils.isEmpty($r3) && !TextUtils.isEmpty($r1.mPassword)) {
                            SmartLockManager.this.saveCredentialsWithUserDetails($r1, $r3, $r1.mPassword, $r2);
                        } else if ($i0 < 3) {
                            $r1.postDelayed(new C18221(), 5000);
                        }
                    }
                });
            }
        }
    }

    public void deleteExistingCredentials() throws  {
        if (ConfigManager.getInstance().getConfigValueBool(370) && !addToPostConnectIfNotConnected(new Runnable() {
            public void run() throws  {
                SmartLockManager.this.deleteExistingCredentials();
            }
        })) {
            MyWazeNativeManager.getInstance().getMyWazeData(new MyWazeDataHandler() {
                public void onMyWazeDataReceived(MyWazeData $r1) throws  {
                    String $r2 = $r1.mUserName;
                    if (TextUtils.isEmpty($r1.mUserName) || $r1.mUserName.equals("Wazer")) {
                        $r2 = MyWazeNativeManager.getInstance().getRealUserNameNTV();
                    }
                    if (!TextUtils.isEmpty($r2) && !TextUtils.isEmpty($r1.mPassword)) {
                        SmartLockManager.this.deleteExistingCredentials($r2, $r1.mPassword);
                    }
                }
            });
        }
    }

    private void deleteExistingCredentials(String $r1, String $r2) throws  {
        deleteExistingCredentials($r1, $r2, null);
    }

    private void deleteExistingCredentials(final String $r1, final String $r2, final Runnable $r3) throws  {
        if (!addToPostConnectIfNotConnected(new Runnable() {
            public void run() throws  {
                SmartLockManager.this.deleteExistingCredentials($r1, $r2, $r3);
            }
        })) {
            Auth.CredentialsApi.delete(this.mCredentialsApiClient, createCredentials($r1, $r2)).setResultCallback(new ResultCallback<Status>() {
                public void onResult(@NonNull Status $r1) throws  {
                    if ($r1.isSuccess() && $r3 != null) {
                        $r3.run();
                    }
                }
            });
        }
    }

    public void renameExistingCredentials(ActivityBase $r1, String $r2, String $r3, String $r4, String $r5) throws  {
        if (ConfigManager.getInstance().getConfigValueBool(370)) {
            final ActivityBase activityBase = $r1;
            final String str = $r2;
            final String str2 = $r3;
            final String str3 = $r4;
            final String str4 = $r5;
            if (!addToPostConnectIfNotConnected(new Runnable() {
                public void run() throws  {
                    SmartLockManager.this.renameExistingCredentials(activityBase, str, str2, str3, str4);
                }
            })) {
                final String str5 = $r2;
                str = $r4;
                str2 = $r5;
                final ActivityBase activityBase2 = $r1;
                str4 = $r3;
                determineCredentialsExist(new SmartLockHasCredentialsListener() {

                    class C18231 implements Runnable {
                        C18231() throws  {
                        }

                        public void run() throws  {
                            SmartLockManager.this.saveCredentialsWithUserDetails(activityBase2, str4, TextUtils.isEmpty(str2) ? str : str2, "RENAME");
                        }
                    }

                    public void onHasCredentialsDetermined(boolean $z0) throws  {
                        if ($z0) {
                            SmartLockManager.this.deleteExistingCredentials(str5, str, new C18231());
                        }
                    }
                });
            }
        }
    }

    private void saveCredentialsWithUserDetails(final Activity $r1, String $r2, String $r3, String $r4) throws  {
        Credential $r5 = createCredentials($r2, $r3);
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SMART_LOCK_POPUP_SHOWN).addParam("TYPE", $r4).send();
        final long $l0 = System.currentTimeMillis();
        Auth.CredentialsApi.save(this.mCredentialsApiClient, $r5).setResultCallback(new ResultCallback<Status>() {
            public void onResult(@NonNull Status $r1) throws  {
                AnalyticsBuilder $r3 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SMART_LOCK_POPUP_CLICKED);
                if ($r1.isSuccess()) {
                    $r3.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, AnalyticsEvents.ANALYTICS_YES).send();
                    if ($r1 instanceof SettingsSmartLockActivity) {
                        ((SettingsSmartLockActivity) $r1).onSaveCompleted();
                    }
                } else if ($r1.hasResolution()) {
                    try {
                        $r1.startResolutionForResult($r1, SmartLockManager.RC_CREDENTIAL_RESOLUTION_SAVE);
                    } catch (SendIntentException e) {
                        $r3.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, AnalyticsEvents.ANALYTICS_NO).send();
                    }
                } else {
                    $r3.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, AnalyticsEvents.ANALYTICS_NO).send();
                    if (System.currentTimeMillis() - $l0 <= 150 && ($r1 instanceof SettingsSmartLockActivity)) {
                        ((SettingsSmartLockActivity) $r1).onNeverDetected();
                    }
                }
            }
        });
    }

    private Credential createCredentials(String $r1, String $r2) throws  {
        return new Credential.Builder($r1).setPassword($r2).setName(DisplayStrings.displayString(DisplayStrings.DS_SMART_LOCK_ACCOUNT_TITLE)).build();
    }

    public boolean isConnected() throws  {
        return this.mCredentialsApiClient.isConnected();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean addToPostConnectIfNotConnected(java.lang.Runnable r5) throws  {
        /*
        r4 = this;
        monitor-enter(r4);
        r0 = r4.isConnected();	 Catch:{ Throwable -> 0x0012 }
        if (r0 != 0) goto L_0x000f;
    L_0x0007:
        r1 = r4.mPostConnectRunnables;	 Catch:{ Throwable -> 0x0012 }
        r1.add(r5);	 Catch:{ Throwable -> 0x0012 }
        monitor-exit(r4);	 Catch:{ Throwable -> 0x0012 }
        r2 = 1;
        return r2;
    L_0x000f:
        monitor-exit(r4);	 Catch:{ Throwable -> 0x0012 }
        r2 = 0;
        return r2;
    L_0x0012:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ Throwable -> 0x0012 }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.install.SmartLockManager.addToPostConnectIfNotConnected(java.lang.Runnable):boolean");
    }

    public void onConnected(@Nullable Bundle bundle) throws  {
        if (isConnected()) {
            synchronized (this) {
                for (Runnable run : this.mPostConnectRunnables) {
                    run.run();
                }
                this.mPostConnectRunnables.clear();
            }
        }
    }

    public void onConnectionSuspended(int i) throws  {
    }
}
