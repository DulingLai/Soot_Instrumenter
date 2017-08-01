package com.facebook.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.login.LoginClient.Request;
import dalvik.annotation.Signature;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

abstract class LoginMethodHandler implements Parcelable {
    protected LoginClient loginClient;
    Map<String, String> methodLoggingExtras;

    abstract String getNameForLogging() throws ;

    boolean needsInternetPermission() throws  {
        return false;
    }

    boolean onActivityResult(int requestCode, int resultCode, Intent data) throws  {
        return false;
    }

    abstract boolean tryAuthorize(Request request) throws ;

    LoginMethodHandler(LoginClient $r1) throws  {
        this.loginClient = $r1;
    }

    LoginMethodHandler(Parcel $r1) throws  {
        this.methodLoggingExtras = Utility.readStringMapFromParcel($r1);
    }

    void setLoginClient(LoginClient $r1) throws  {
        if (this.loginClient != null) {
            throw new FacebookException("Can't set LoginClient if it is already set.");
        }
        this.loginClient = $r1;
    }

    void cancel() throws  {
    }

    protected void addLoggingExtra(String $r1, Object $r2) throws  {
        if (this.methodLoggingExtras == null) {
            this.methodLoggingExtras = new HashMap();
        }
        this.methodLoggingExtras.put($r1, $r2 == null ? null : $r2.toString());
    }

    protected void logWebLoginCompleted(String $r1) throws  {
        String $r5 = this.loginClient.getPendingRequest().getApplicationId();
        AppEventsLogger $r7 = AppEventsLogger.newLogger(this.loginClient.getActivity(), $r5);
        Bundle $r2 = new Bundle();
        $r2.putString(AnalyticsEvents.PARAMETER_WEB_LOGIN_E2E, $r1);
        $r2.putLong(AnalyticsEvents.PARAMETER_WEB_LOGIN_SWITCHBACK_TIME, System.currentTimeMillis());
        $r2.putString("app_id", $r5);
        $r7.logSdkEvent(AnalyticsEvents.EVENT_WEB_LOGIN_COMPLETE, null, $r2);
    }

    static AccessToken createAccessTokenFromNativeLogin(Bundle $r0, AccessTokenSource $r1, String $r2) throws  {
        Bundle bundle = $r0;
        Date $r3 = Utility.getBundleLongAsDate(bundle, NativeProtocol.EXTRA_EXPIRES_SECONDS_SINCE_EPOCH, new Date(0));
        ArrayList $r4 = $r0.getStringArrayList(NativeProtocol.EXTRA_PERMISSIONS);
        String $r5 = $r0.getString(NativeProtocol.EXTRA_ACCESS_TOKEN);
        if (Utility.isNullOrEmpty($r5)) {
            return null;
        }
        return new AccessToken($r5, $r2, $r0.getString(NativeProtocol.EXTRA_USER_ID), $r4, null, $r1, $r3, new Date());
    }

    public static AccessToken createAccessTokenFromWebBundle(@Signature({"(", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/facebook/AccessTokenSource;", "Ljava/lang/String;", ")", "Lcom/facebook/AccessToken;"}) Collection<String> $r4, @Signature({"(", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/facebook/AccessTokenSource;", "Ljava/lang/String;", ")", "Lcom/facebook/AccessToken;"}) Bundle $r0, @Signature({"(", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/facebook/AccessTokenSource;", "Ljava/lang/String;", ")", "Lcom/facebook/AccessToken;"}) AccessTokenSource $r1, @Signature({"(", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/facebook/AccessTokenSource;", "Ljava/lang/String;", ")", "Lcom/facebook/AccessToken;"}) String $r2) throws FacebookException {
        ArrayList $r10;
        Bundle bundle = $r0;
        Date $r5 = Utility.getBundleLongAsDate(bundle, "expires_in", new Date());
        String $r6 = $r0.getString("access_token");
        String $r7 = $r0.getString("granted_scopes");
        if (!Utility.isNullOrEmpty($r7)) {
            Collection $r42 = $r10;
            $r10 = new ArrayList(Arrays.asList($r7.split(",")));
        }
        $r7 = $r0.getString("denied_scopes");
        $r10 = null;
        if (!Utility.isNullOrEmpty($r7)) {
            $r10 = r0;
            ArrayList arrayList = new ArrayList(Arrays.asList($r7.split(",")));
        }
        if (Utility.isNullOrEmpty($r6)) {
            return null;
        }
        return new AccessToken($r6, $r2, getUserIDFromSignedRequest($r0.getString("signed_request")), $r42, $r10, $r1, $r5, new Date());
    }

    private static String getUserIDFromSignedRequest(String $r0) throws FacebookException {
        if ($r0 == null || $r0.isEmpty()) {
            throw new FacebookException("Authorization response does not contain the signed_request");
        }
        try {
            String[] $r3 = $r0.split("\\.");
            if ($r3.length == 2) {
                return new JSONObject(new String(Base64.decode($r3[1], 0), "UTF-8")).getString("user_id");
            }
        } catch (UnsupportedEncodingException e) {
        } catch (JSONException e2) {
        }
        throw new FacebookException("Failed to retrieve user_id from signed_request");
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        Utility.writeStringMapToParcel($r1, this.methodLoggingExtras);
    }
}
