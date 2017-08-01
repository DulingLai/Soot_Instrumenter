package com.spotify.sdk.android.authentication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.spotify.sdk.android.authentication.AuthenticationHandler.OnCompleteListener;
import com.spotify.sdk.android.authentication.AuthenticationResponse.Builder;
import com.spotify.sdk.android.authentication.AuthenticationResponse.Type;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationClient {
    static final String ANDROID_SDK = "android-sdk";
    static final String DEFAULT_CAMPAIGN = "android-sdk";
    static final String MARKET_PATH = "details";
    static final String MARKET_SCHEME = "market";
    static final String MARKET_VIEW_PATH = "market://";
    static final String PLAY_STORE_AUTHORITY = "play.google.com";
    static final String PLAY_STORE_PATH = "store/apps/details";
    static final String PLAY_STORE_SCHEME = "https";
    static final String SPOTIFY_ID = "com.spotify.music";
    static final String SPOTIFY_SDK = "spotify-sdk";
    private AuthenticationClientListener mAuthenticationClientListener;
    private List<AuthenticationHandler> mAuthenticationHandlers = new ArrayList();
    private boolean mAuthenticationPending;
    private AuthenticationHandler mCurrentHandler;
    private final Activity mLoginActivity;

    interface AuthenticationClientListener {
        void onClientCancelled() throws ;

        void onClientComplete(AuthenticationResponse authenticationResponse) throws ;
    }

    static final class QueryParams {
        public static final String ID = "id";
        public static final String REFERRER = "referrer";
        public static final String UTM_CAMPAIGN = "utm_campaign";
        public static final String UTM_MEDIUM = "utm_medium";
        public static final String UTM_SOURCE = "utm_source";

        QueryParams() throws  {
        }
    }

    public static void openLoginInBrowser(Activity $r0, AuthenticationRequest $r1) throws  {
        $r0.startActivity(new Intent("android.intent.action.VIEW", $r1.toUri()));
    }

    public static Intent createLoginActivityIntent(Activity $r0, AuthenticationRequest $r1) throws  {
        return LoginActivity.getAuthIntent($r0, $r1);
    }

    public static void openLoginActivity(Activity $r0, int $i0, AuthenticationRequest $r1) throws  {
        $r0.startActivityForResult(createLoginActivityIntent($r0, $r1), $i0);
    }

    public static void stopLoginActivity(Activity $r0, int $i0) throws  {
        $r0.finishActivity($i0);
    }

    public static AuthenticationResponse getResponse(int $i0, Intent $r0) throws  {
        if ($i0 != -1 || LoginActivity.getResponseFromIntent($r0) == null) {
            return new Builder().setType(Type.EMPTY).build();
        }
        return LoginActivity.getResponseFromIntent($r0);
    }

    public static void openDownloadSpotifyActivity(Activity $r0) throws  {
        openDownloadSpotifyActivity($r0, "android-sdk");
    }

    public static void openDownloadSpotifyActivity(Activity $r0, String $r1) throws  {
        Uri.Builder $r3 = new Uri.Builder();
        if (isAvailable($r0, new Intent("android.intent.action.VIEW", Uri.parse(MARKET_VIEW_PATH)))) {
            $r3.scheme(MARKET_SCHEME).appendPath(MARKET_PATH);
        } else {
            $r3.scheme(PLAY_STORE_SCHEME).authority(PLAY_STORE_AUTHORITY).appendEncodedPath(PLAY_STORE_PATH);
        }
        $r3.appendQueryParameter("id", "com.spotify.music");
        Uri.Builder $r2 = new Uri.Builder();
        $r2.appendQueryParameter("utm_source", SPOTIFY_SDK).appendQueryParameter("utm_medium", "android-sdk");
        if (TextUtils.isEmpty($r1)) {
            $r2.appendQueryParameter("utm_campaign", "android-sdk");
        } else {
            $r2.appendQueryParameter("utm_campaign", $r1);
        }
        $r3.appendQueryParameter(QueryParams.REFERRER, $r2.build().getEncodedQuery());
        $r0.startActivity(new Intent("android.intent.action.VIEW", $r3.build()));
    }

    public static boolean isAvailable(Context $r0, Intent $r1) throws  {
        return $r0.getPackageManager().queryIntentActivities($r1, 65536).size() > 0;
    }

    public AuthenticationClient(Activity $r1) throws  {
        this.mLoginActivity = $r1;
        this.mAuthenticationHandlers.add(new SpotifyAuthHandler());
        this.mAuthenticationHandlers.add(new CustomTabAuthHandler());
        this.mAuthenticationHandlers.add(new WebViewAuthHandler());
    }

    void setOnCompleteListener(AuthenticationClientListener $r1) throws  {
        this.mAuthenticationClientListener = $r1;
    }

    void authenticate(AuthenticationRequest $r1) throws  {
        if (!this.mAuthenticationPending) {
            this.mAuthenticationPending = true;
            for (AuthenticationHandler $r5 : this.mAuthenticationHandlers) {
                if (tryAuthenticationHandler($r5, $r1)) {
                    this.mCurrentHandler = $r5;
                    return;
                }
            }
        }
    }

    void cancel() throws  {
        if (this.mAuthenticationPending) {
            this.mAuthenticationPending = false;
            closeAuthenticationHandler(this.mCurrentHandler);
            if (this.mAuthenticationClientListener != null) {
                this.mAuthenticationClientListener.onClientCancelled();
                this.mAuthenticationClientListener = null;
            }
        }
    }

    void complete(AuthenticationResponse $r1) throws  {
        sendComplete(this.mCurrentHandler, $r1);
    }

    private void sendComplete(AuthenticationHandler $r1, AuthenticationResponse $r2) throws  {
        this.mAuthenticationPending = false;
        closeAuthenticationHandler($r1);
        if (this.mAuthenticationClientListener != null) {
            this.mAuthenticationClientListener.onClientComplete($r2);
            this.mAuthenticationClientListener = null;
        }
    }

    private boolean tryAuthenticationHandler(final AuthenticationHandler $r1, AuthenticationRequest $r2) throws  {
        $r1.setOnCompleteListener(new OnCompleteListener() {
            public void onComplete(AuthenticationResponse $r1) throws  {
                AuthenticationClient.this.sendComplete($r1, $r1);
            }

            public void onCancel() throws  {
                AuthenticationClient.this.sendComplete($r1, new Builder().setType(Type.EMPTY).build());
            }

            public void onError(Throwable $r1) throws  {
                AuthenticationClient.this.sendComplete($r1, new Builder().setType(Type.ERROR).setError($r1.getMessage()).build());
            }
        });
        if ($r1.start(this.mLoginActivity, $r2)) {
            return true;
        }
        closeAuthenticationHandler($r1);
        return false;
    }

    private void closeAuthenticationHandler(AuthenticationHandler $r1) throws  {
        if ($r1 != null) {
            $r1.setOnCompleteListener(null);
            $r1.stop();
        }
    }
}
