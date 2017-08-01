package com.facebook.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.facebook.FacebookActivity;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.Utility.DialogFeatureConfig;

public class DialogPresenter {

    public interface ParameterProvider {
        Bundle getLegacyParameters() throws ;

        Bundle getParameters() throws ;
    }

    public static void setupAppCallForCannotShowError(AppCall $r0) throws  {
        setupAppCallForValidationError($r0, new FacebookException("Unable to show the provided content via the web or the installed version of the Facebook app. Some dialogs are only supported starting API 14."));
    }

    public static void setupAppCallForValidationError(AppCall $r0, FacebookException $r1) throws  {
        setupAppCallForErrorResult($r0, $r1);
    }

    public static void present(AppCall $r0, Activity $r1) throws  {
        $r1.startActivityForResult($r0.getRequestIntent(), $r0.getRequestCode());
        $r0.setPending();
    }

    public static void present(AppCall $r0, Fragment $r1) throws  {
        $r1.startActivityForResult($r0.getRequestIntent(), $r0.getRequestCode());
        $r0.setPending();
    }

    public static boolean canPresentNativeDialogWithFeature(DialogFeature $r0) throws  {
        return getProtocolVersionForNativeDialog($r0) != -1;
    }

    public static boolean canPresentWebFallbackDialogWithFeature(DialogFeature $r0) throws  {
        return getDialogWebFallbackUri($r0) != null;
    }

    public static void setupAppCallForErrorResult(AppCall $r0, FacebookException $r1) throws  {
        if ($r1 != null) {
            Validate.hasFacebookActivity(FacebookSdk.getApplicationContext());
            Intent $r2 = new Intent();
            $r2.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
            $r2.setAction(FacebookActivity.PASS_THROUGH_CANCEL_ACTION);
            NativeProtocol.setupProtocolRequestIntent($r2, $r0.getCallId().toString(), null, NativeProtocol.getLatestKnownVersion(), NativeProtocol.createBundleForException($r1));
            $r0.setRequestIntent($r2);
        }
    }

    public static void setupAppCallForWebDialog(AppCall $r0, String $r1, Bundle $r2) throws  {
        Validate.hasFacebookActivity(FacebookSdk.getApplicationContext());
        Validate.hasInternetPermissions(FacebookSdk.getApplicationContext());
        Bundle $r3 = new Bundle();
        $r3.putString(NativeProtocol.WEB_DIALOG_ACTION, $r1);
        $r3.putBundle(NativeProtocol.WEB_DIALOG_PARAMS, $r2);
        Intent $r4 = new Intent();
        NativeProtocol.setupProtocolRequestIntent($r4, $r0.getCallId().toString(), $r1, NativeProtocol.getLatestKnownVersion(), $r3);
        $r4.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
        $r4.setAction(FacebookDialogFragment.TAG);
        $r0.setRequestIntent($r4);
    }

    public static void setupAppCallForWebFallbackDialog(AppCall $r0, Bundle $r1, DialogFeature $r2) throws  {
        Validate.hasFacebookActivity(FacebookSdk.getApplicationContext());
        Validate.hasInternetPermissions(FacebookSdk.getApplicationContext());
        String $r5 = $r2.name();
        Uri $r6 = getDialogWebFallbackUri($r2);
        if ($r6 == null) {
            throw new FacebookException("Unable to fetch the Url for the DialogFeature : '" + $r5 + "'");
        }
        $r1 = ServerProtocol.getQueryParamsForPlatformActivityIntentWebFallback($r0.getCallId().toString(), NativeProtocol.getLatestKnownVersion(), $r1);
        if ($r1 == null) {
            throw new FacebookException("Unable to fetch the app's key-hash");
        }
        if ($r6.isRelative()) {
            $r6 = Utility.buildUri(ServerProtocol.getDialogAuthority(), $r6.toString(), $r1);
        } else {
            $r6 = Utility.buildUri($r6.getAuthority(), $r6.getPath(), $r1);
        }
        $r1 = new Bundle();
        $r1.putString("url", $r6.toString());
        $r1.putBoolean(NativeProtocol.WEB_DIALOG_IS_FALLBACK, true);
        Intent $r3 = new Intent();
        NativeProtocol.setupProtocolRequestIntent($r3, $r0.getCallId().toString(), $r2.getAction(), NativeProtocol.getLatestKnownVersion(), $r1);
        $r3.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
        $r3.setAction(FacebookDialogFragment.TAG);
        $r0.setRequestIntent($r3);
    }

    public static void setupAppCallForNativeDialog(AppCall $r0, ParameterProvider $r1, DialogFeature $r2) throws  {
        Context $r3 = FacebookSdk.getApplicationContext();
        String $r4 = $r2.getAction();
        int $i0 = getProtocolVersionForNativeDialog($r2);
        if ($i0 == -1) {
            throw new FacebookException("Cannot present this dialog. This likely means that the Facebook app is not installed.");
        }
        Bundle $r6;
        if (NativeProtocol.isVersionCompatibleWithBucketedIntent($i0)) {
            $r6 = $r1.getParameters();
        } else {
            $r6 = $r1.getLegacyParameters();
        }
        if ($r6 == null) {
            $r6 = new Bundle();
        }
        Intent $r9 = NativeProtocol.createPlatformActivityIntent($r3, $r0.getCallId().toString(), $r4, $i0, $r6);
        if ($r9 == null) {
            throw new FacebookException("Unable to create Intent; this likely means theFacebook app is not installed.");
        }
        $r0.setRequestIntent($r9);
    }

    private static Uri getDialogWebFallbackUri(DialogFeature $r0) throws  {
        String $r1 = $r0.name();
        DialogFeatureConfig $r4 = Utility.getDialogFeatureConfig(FacebookSdk.getApplicationId(), $r0.getAction(), $r1);
        if ($r4 != null) {
            return $r4.getFallbackUrl();
        }
        return null;
    }

    public static int getProtocolVersionForNativeDialog(DialogFeature $r0) throws  {
        String $r1 = FacebookSdk.getApplicationId();
        String $r2 = $r0.getAction();
        return NativeProtocol.getLatestAvailableProtocolVersionForAction($r2, getVersionSpecForFeature($r1, $r2, $r0));
    }

    private static int[] getVersionSpecForFeature(String $r0, String $r1, DialogFeature $r2) throws  {
        DialogFeatureConfig $r4 = Utility.getDialogFeatureConfig($r0, $r1, $r2.name());
        if ($r4 != null) {
            return $r4.getVersionSpec();
        }
        return new int[]{$r2.getMinVersion()};
    }

    public static void logDialogActivity(Context $r0, String $r1, String $r2) throws  {
        AppEventsLogger $r4 = AppEventsLogger.newLogger($r0);
        Bundle $r3 = new Bundle();
        $r3.putString(AnalyticsEvents.PARAMETER_DIALOG_OUTCOME, $r2);
        $r4.logSdkEvent($r1, null, $r3);
    }
}
