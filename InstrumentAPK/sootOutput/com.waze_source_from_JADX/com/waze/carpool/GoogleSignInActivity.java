package com.waze.carpool;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.waze.AppService;
import com.waze.Logger;
import com.waze.Utils;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.push.Constants;

public class GoogleSignInActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {
    public static final String CARPOOL_SCOPE = "https://www.googleapis.com/auth/carpool";
    public static final String GOOGLE_CONNECT_ACTION = "GOOGLE_CONNECT_ACTION";
    public static final int INTENT_ACTION_SIGN_IN = 1;
    public static final int INTENT_ACTION_SIGN_OUT = 2;
    public static final String INTENT_FAMILY_NAME = "FamilyName";
    public static final String INTENT_GIVEN_NAME = "GivenName";
    public static final String INTENT_IMAGE_URL = "ImageUrl";
    private static final int RC_RESOLUTION = 1002;
    private static final int RC_SIGN_IN = 1001;
    private static final int RC_SIGN_OUT = 1002;
    public static final int RESULT_GOOGLE_PLAY_SERVICES_FAILURE = 1226;
    public static final int RESULT_NETWORK_FAILURE = 1227;
    private static String mAuthorizedAccountName;
    private String GOOGLE_CLIENT_ID = Constants.GOOGLE_NOW_ID;
    private int mConnectAction;
    private GoogleApiClient mGoogleApiClient;
    private Handler mHandler = new C16421();
    private boolean mIntentInProgress = false;
    private boolean mTimeoutOccurred = false;
    private String mToken;
    Runnable timeoutRunnable = new C16432();

    class C16421 extends Handler {
        C16421() throws  {
        }

        public void handleMessage(Message $r1) throws  {
            if ($r1.what != MyWazeNativeManager.UH_GOOGLE_CONNECT) {
                super.handleMessage($r1);
            } else if (GoogleSignInActivity.this.mTimeoutOccurred) {
                Logger.m43w("GoogleSignInActivity: Timeout already occurred so ignoring msg");
            } else {
                GoogleSignInActivity.this.onTokenSet();
            }
        }
    }

    class C16432 implements Runnable {
        C16432() throws  {
        }

        public void run() throws  {
            GoogleSignInActivity.this.mTimeoutOccurred = true;
            MyWazeNativeManager.getInstance().unsetUpdateHandler(MyWazeNativeManager.UH_GOOGLE_CONNECT, GoogleSignInActivity.this.mHandler);
            Logger.m38e("GoogleSignInActivity: Timeout occurred when trying to set token in server");
            GoogleSignInActivity.this.setResult(1227);
            GoogleSignInActivity.this.finish();
        }
    }

    class C16443 implements ResultCallback<Status> {
        C16443() throws  {
        }

        public void onResult(@NonNull Status $r1) throws  {
            if ($r1.isSuccess()) {
                Logger.m36d("GoogleSignInActivity: user signed out successfully, delaying sign in...");
            } else {
                Logger.m36d("GoogleSignInActivity:Failed signing out before signing in, reason: " + $r1.getStatusMessage());
            }
            if (GoogleSignInActivity.this.mConnectAction == 2) {
                Logger.m36d("GoogleSignInActivity: signout requested only, exiting...");
                GoogleSignInActivity.this.setResult(-1);
                GoogleSignInActivity.this.finish();
                return;
            }
            Logger.m36d("GoogleSignInActivity: commencing sign in...");
            GoogleSignInActivity.this.actualSignIn();
        }
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        if (!Utils.checkIsGooglePlayServicesAvailable()) {
            Logger.m38e("GoogleSignInActivity: Google play service not available");
            setResult(1226);
            finish();
        }
        this.mGoogleApiClient = new Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions.Builder().requestId().requestProfile().requestEmail().requestServerAuthCode(this.GOOGLE_CLIENT_ID, true).requestIdToken(this.GOOGLE_CLIENT_ID).requestScopes(new Scope(CARPOOL_SCOPE), new Scope[0]).build()).build();
        Intent $r10 = getIntent();
        if ($r10 != null) {
            this.mConnectAction = $r10.getIntExtra(GOOGLE_CONNECT_ACTION, 1);
        }
        if (this.mConnectAction != 1 && this.mConnectAction != 2) {
            Logger.m38e("GoogleApiClient: Unsupported action");
            setResult(1);
            finish();
        }
    }

    protected void onStart() throws  {
        attemptBeginConnect();
        super.onStart();
    }

    void attemptBeginConnect() throws  {
        if (Utils.checkIsGooglePlayServicesAvailable()) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_GOOGLE_CONNECT_STARTED);
            Logger.m36d("GoogleSignInActivity: connecting...");
            this.mGoogleApiClient.connect();
            return;
        }
        setResult(1226);
        finish();
    }

    protected void onStop() throws  {
        Logger.m36d("GoogleSignInActivity: on stop, disconnecting ");
        super.onStop();
        if (this.mGoogleApiClient.isConnected()) {
            this.mGoogleApiClient.disconnect();
        }
    }

    public void onConnected(@Nullable Bundle bundle) throws  {
        Logger.m36d("GoogleSignInActivity: connected to API client, trying to sign out...");
        Auth.GoogleSignInApi.signOut(this.mGoogleApiClient).setResultCallback(new C16443());
    }

    public void onConnectionFailed(ConnectionResult $r1) throws  {
        Logger.m36d("GoogleSignInActivity: connection failed, result: " + $r1.getErrorCode() + "; has resolution: " + $r1.hasResolution());
        if (this.mIntentInProgress || !$r1.hasResolution()) {
            Logger.m38e("GoogleSignInActivity: onConnectionFailed without resolution, error code is " + $r1.getErrorCode());
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_GOOGLE_CONNECT_FAILED, "ERROR", String.valueOf($r1.getErrorCode()));
            return;
        }
        try {
            if ($r1.getErrorCode() != 4) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_GOOGLE_CONNECT_FAILED, "ERROR", String.valueOf($r1.getErrorCode()));
            }
            this.mIntentInProgress = true;
            startIntentSenderForResult($r1.getResolution().getIntentSender(), 1002, null, 0, 0, 0);
        } catch (SendIntentException e) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_GOOGLE_CONNECT_FAILED, "ERROR", String.valueOf($r1.getErrorCode()));
            Logger.m36d("GoogleSignInActivity: connection failed, intent was canceled, trying again to connect ");
            this.mIntentInProgress = false;
            GoogleApiClient $r7 = this.mGoogleApiClient;
            $r7.connect();
        }
    }

    public void onConnectionSuspended(int $i0) throws  {
        Logger.m38e("GoogleSignInActivity: onConnectionSuspended " + $i0);
    }

    public void actualSignIn() throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_GOOGLE_CONNECT_STARTED);
        Logger.m36d("GoogleSignInActivity signing in...");
        startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(this.mGoogleApiClient), 1001);
    }

    public void onTokenSet() throws  {
        Logger.m36d("GoogleSignInActivity on token set, Google account connected ");
        MyWazeNativeManager.getInstance().unsetUpdateHandler(MyWazeNativeManager.UH_GOOGLE_CONNECT, this.mHandler);
        this.mHandler.removeCallbacks(this.timeoutRunnable);
        finish();
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        super.onActivityResult($i0, $i1, $r1);
        if ($i0 == 1002) {
            if ($i1 == -1) {
                Logger.m41i("GoogleSignInActivity: result ok after error resolving ");
                this.mIntentInProgress = false;
                if (this.mGoogleApiClient.isConnecting() || this.mGoogleApiClient.isConnected()) {
                    Logger.m41i("GoogleSignInActivity: already connected ");
                    return;
                }
                Logger.m41i("GoogleSignInActivity: trying again to connect ");
                this.mGoogleApiClient.connect();
            } else if ($i1 == 0) {
                Logger.m41i("GoogleSignInActivity: result cancelled after error resolving ");
                exitOnCancel();
            } else {
                Logger.m41i("GoogleSignInActivity: result " + $i1 + " after error resolving ");
                if (this.mGoogleApiClient.isConnecting() || this.mGoogleApiClient.isConnected()) {
                    Logger.m41i("GoogleSignInActivity: already connected ");
                    return;
                }
                Logger.m41i("GoogleSignInActivity: trying again to connect ");
                this.mGoogleApiClient.connect();
            }
        } else if ($i0 == 1001) {
            handleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent($r1));
        }
    }

    private void handleSignInResult(GoogleSignInResult $r1) throws  {
        int $i0;
        String $r2;
        if ($r1 == null || !$r1.isSuccess()) {
            $i0 = -99;
            $r2 = "Unknown";
            if (!($r1 == null || $r1.getStatus() == null)) {
                $i0 = $r1.getStatus().getStatusCode();
                $r2 = $r1.getStatus().getStatusMessage();
            }
            Logger.m38e("GoogleSignInActivity: Failed signing in, code: " + $i0 + "; reason: " + $r2);
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_GOOGLE_CONNECT_FAILED, "ERROR", $i0);
            setResult(1);
            finish();
            return;
        }
        Logger.m41i("GoogleSignInActivity: google sign in successful");
        GoogleSignInAccount $r5 = $r1.getSignInAccount();
        if ($r5 != null) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_GOOGLE_CONNECT_SUCCESS);
            setAuthorizedAccountName($r5.getEmail());
            this.mToken = $r5.getServerAuthCode();
            Logger.m41i("GoogleSignInActivity: setting google sign in token");
            MyWazeNativeManager.getInstance().setUpdateHandler(MyWazeNativeManager.UH_GOOGLE_CONNECT, this.mHandler);
            MyWazeNativeManager.getInstance().SetGoogleSignIn(true, true, this.mToken, null, null);
            this.mTimeoutOccurred = false;
            this.mHandler.postDelayed(this.timeoutRunnable, 20000);
            Intent intent = new Intent();
            $r2 = $r5.getDisplayName();
            if (!($r2 == null || $r2.isEmpty())) {
                String[] $r9 = $r2.split("\\s+");
                if ($r9.length > 1) {
                    intent = intent;
                    intent.putExtra("FamilyName", $r9[$r9.length - 1]);
                    StringBuilder stringBuilder = new StringBuilder();
                    for ($i0 = 0; $i0 < $r9.length - 1; $i0++) {
                        stringBuilder.append($r9[$i0]);
                        if ($i0 < $r9.length - 2) {
                            stringBuilder.append(" ");
                        }
                    }
                    $r2 = stringBuilder.toString();
                    intent.putExtra("GivenName", $r2);
                } else {
                    intent = intent;
                    intent.putExtra("GivenName", $r9[0]);
                }
            }
            if ($r5.getPhotoUrl() != null) {
                intent = intent;
                intent.putExtra("ImageUrl", $r5.getPhotoUrl().toString());
            }
            setResult(-1, intent);
        }
    }

    private void exitOnCancel() throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_GOOGLE_CONNECT_CANCELED);
        setResult(0);
        finish();
    }

    public static String GetAuthorizedAccountName() throws  {
        if (mAuthorizedAccountName == null) {
            CarpoolUserData $r2 = CarpoolNativeManager.getInstance().getCarpoolProfileNTV();
            if ($r2 != null && $r2.is_driver) {
                mAuthorizedAccountName = $r2.getEmail();
            }
        }
        return mAuthorizedAccountName;
    }

    private void setAuthorizedAccountName(String $r1) throws  {
        mAuthorizedAccountName = $r1;
        SetStoredAuthorizedAccountName($r1);
    }

    public static String GetStoredAuthorizedAccountName(Context $r0) throws  {
        if (mAuthorizedAccountName != null) {
            return mAuthorizedAccountName;
        }
        if ($r0 == null) {
            $r0 = AppService.getAppContext();
        }
        return $r0.getSharedPreferences("com.waze.Keys", 0).getString("AccountName", null);
    }

    public static void SetStoredAuthorizedAccountName(String $r0) throws  {
        Context $r1 = AppService.getAppContext();
        if ($r1 != null) {
            SharedPreferences $r2 = $r1.getSharedPreferences("com.waze.Keys", 0);
            if ($r2 != null) {
                $r2.edit().putString("AccountName", $r0).apply();
                $r2.edit().commit();
            }
        }
    }
}
