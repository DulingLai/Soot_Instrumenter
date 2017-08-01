package com.facebook.login;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.FacebookServiceException;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.WebDialog;
import com.facebook.internal.WebDialog.Builder;
import com.facebook.internal.WebDialog.OnCompleteListener;
import com.facebook.login.LoginClient.Request;
import com.facebook.login.LoginClient.Result;
import java.util.Locale;

class WebViewLoginMethodHandler extends LoginMethodHandler {
    public static final Creator<WebViewLoginMethodHandler> CREATOR = new C05582();
    private static final String WEB_VIEW_AUTH_HANDLER_STORE = "com.facebook.login.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY";
    private static final String WEB_VIEW_AUTH_HANDLER_TOKEN_KEY = "TOKEN";
    private String e2e;
    private WebDialog loginDialog;

    static class C05582 implements Creator {
        C05582() throws  {
        }

        public WebViewLoginMethodHandler createFromParcel(Parcel $r1) throws  {
            return new WebViewLoginMethodHandler($r1);
        }

        public WebViewLoginMethodHandler[] newArray(int $i0) throws  {
            return new WebViewLoginMethodHandler[$i0];
        }
    }

    static class AuthDialogBuilder extends Builder {
        private static final String OAUTH_DIALOG = "oauth";
        static final String REDIRECT_URI = "fbconnect://success";
        private String e2e;
        private boolean isRerequest;

        public AuthDialogBuilder(Context $r1, String $r2, Bundle $r3) throws  {
            super($r1, $r2, OAUTH_DIALOG, $r3);
        }

        public AuthDialogBuilder setE2E(String $r1) throws  {
            this.e2e = $r1;
            return this;
        }

        public AuthDialogBuilder setIsRerequest(boolean $z0) throws  {
            this.isRerequest = $z0;
            return this;
        }

        public WebDialog build() throws  {
            Bundle $r1 = getParameters();
            $r1.putString("redirect_uri", "fbconnect://success");
            $r1.putString("client_id", getApplicationId());
            $r1.putString("e2e", this.e2e);
            $r1.putString("response_type", ServerProtocol.DIALOG_RESPONSE_TYPE_TOKEN_AND_SIGNED_REQUEST);
            $r1.putString(ServerProtocol.DIALOG_PARAM_RETURN_SCOPES, ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
            if (this.isRerequest) {
                $r1.putString(ServerProtocol.DIALOG_PARAM_AUTH_TYPE, ServerProtocol.DIALOG_REREQUEST_AUTH_TYPE);
            }
            return new WebDialog(getContext(), OAUTH_DIALOG, $r1, getTheme(), getListener());
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    String getNameForLogging() throws  {
        return "web_view";
    }

    boolean needsInternetPermission() throws  {
        return true;
    }

    WebViewLoginMethodHandler(LoginClient $r1) throws  {
        super($r1);
    }

    void cancel() throws  {
        if (this.loginDialog != null) {
            this.loginDialog.cancel();
            this.loginDialog = null;
        }
    }

    boolean tryAuthorize(Request $r1) throws  {
        String $r6;
        Bundle $r4 = new Bundle();
        if (!Utility.isNullOrEmpty($r1.getPermissions())) {
            $r6 = TextUtils.join(",", $r1.getPermissions());
            $r4.putString("scope", $r6);
            addLoggingExtra("scope", $r6);
        }
        $r4.putString(ServerProtocol.DIALOG_PARAM_DEFAULT_AUDIENCE, $r1.getDefaultAudience().getNativeProtocolAudience());
        AccessToken $r8 = AccessToken.getCurrentAccessToken();
        $r6 = $r8 != null ? $r8.getToken() : null;
        if ($r6 == null || !$r6.equals(loadCookieToken())) {
            Utility.clearFacebookCookies(this.loginClient.getActivity());
            addLoggingExtra("access_token", AppEventsConstants.EVENT_PARAM_VALUE_NO);
        } else {
            $r4.putString("access_token", $r6);
            addLoggingExtra("access_token", AppEventsConstants.EVENT_PARAM_VALUE_YES);
        }
        final Request request = $r1;
        C05571 $r3 = new OnCompleteListener() {
            public void onComplete(Bundle $r1, FacebookException $r2) throws  {
                WebViewLoginMethodHandler.this.onWebDialogComplete(request, $r1, $r2);
            }
        };
        this.e2e = LoginClient.getE2E();
        addLoggingExtra("e2e", this.e2e);
        FragmentActivity $r11 = this.loginClient.getActivity();
        this.loginDialog = new AuthDialogBuilder($r11, $r1.getApplicationId(), $r4).setE2E(this.e2e).setIsRerequest($r1.isRerequest()).setOnCompleteListener($r3).setTheme(FacebookSdk.getWebDialogTheme()).build();
        FacebookDialogFragment facebookDialogFragment = new FacebookDialogFragment();
        facebookDialogFragment.setRetainInstance(true);
        facebookDialogFragment.setDialog(this.loginDialog);
        facebookDialogFragment.show($r11.getSupportFragmentManager(), FacebookDialogFragment.TAG);
        return true;
    }

    void onWebDialogComplete(Request $r1, Bundle $r2, FacebookException $r4) throws  {
        Result $r10;
        if ($r2 != null) {
            if ($r2.containsKey("e2e")) {
                this.e2e = $r2.getString("e2e");
            }
            try {
                AccessToken $r8 = LoginMethodHandler.createAccessTokenFromWebBundle($r1.getPermissions(), $r2, AccessTokenSource.WEB_VIEW, $r1.getApplicationId());
                $r10 = Result.createTokenResult(this.loginClient.getPendingRequest(), $r8);
                CookieSyncManager.createInstance(this.loginClient.getActivity()).sync();
                saveCookieToken($r8.getToken());
            } catch (FacebookException $r3) {
                $r10 = Result.createErrorResult(this.loginClient.getPendingRequest(), null, $r3.getMessage());
            }
        } else if ($r4 instanceof FacebookOperationCanceledException) {
            $r10 = Result.createCancelResult(this.loginClient.getPendingRequest(), "User canceled log in.");
        } else {
            this.e2e = null;
            String $r5 = null;
            String $r13 = $r4.getMessage();
            if ($r4 instanceof FacebookServiceException) {
                FacebookRequestError $r15 = ((FacebookServiceException) $r4).getRequestError();
                Locale locale = Locale.ROOT;
                $r5 = String.format(locale, "%d", new Object[]{Integer.valueOf($r15.getErrorCode())});
                $r13 = $r15.toString();
            }
            $r10 = Result.createErrorResult(this.loginClient.getPendingRequest(), null, $r13, $r5);
        }
        if (!Utility.isNullOrEmpty(this.e2e)) {
            logWebLoginCompleted(this.e2e);
        }
        this.loginClient.completeAndValidate($r10);
    }

    private void saveCookieToken(String $r1) throws  {
        this.loginClient.getActivity().getSharedPreferences(WEB_VIEW_AUTH_HANDLER_STORE, 0).edit().putString(WEB_VIEW_AUTH_HANDLER_TOKEN_KEY, $r1).apply();
    }

    private String loadCookieToken() throws  {
        return this.loginClient.getActivity().getSharedPreferences(WEB_VIEW_AUTH_HANDLER_STORE, 0).getString(WEB_VIEW_AUTH_HANDLER_TOKEN_KEY, "");
    }

    WebViewLoginMethodHandler(Parcel $r1) throws  {
        super($r1);
        this.e2e = $r1.readString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        super.writeToParcel($r1, $i0);
        $r1.writeString(this.e2e);
    }
}
