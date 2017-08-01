package com.facebook.applinks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.google.android.gms.auth.firstparty.recovery.RecoveryParamConstants;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppLinkData {
    private static final String APPLINK_BRIDGE_ARGS_KEY = "bridge_args";
    private static final String APPLINK_METHOD_ARGS_KEY = "method_args";
    private static final String APPLINK_VERSION_KEY = "version";
    public static final String ARGUMENTS_NATIVE_CLASS_KEY = "com.facebook.platform.APPLINK_NATIVE_CLASS";
    public static final String ARGUMENTS_NATIVE_URL = "com.facebook.platform.APPLINK_NATIVE_URL";
    public static final String ARGUMENTS_REFERER_DATA_KEY = "referer_data";
    public static final String ARGUMENTS_TAPTIME_KEY = "com.facebook.platform.APPLINK_TAP_TIME_UTC";
    private static final String BRIDGE_ARGS_METHOD_KEY = "method";
    private static final String BUNDLE_AL_APPLINK_DATA_KEY = "al_applink_data";
    static final String BUNDLE_APPLINK_ARGS_KEY = "com.facebook.platform.APPLINK_ARGS";
    private static final String DEFERRED_APP_LINK_ARGS_FIELD = "applink_args";
    private static final String DEFERRED_APP_LINK_CLASS_FIELD = "applink_class";
    private static final String DEFERRED_APP_LINK_CLICK_TIME_FIELD = "click_time";
    private static final String DEFERRED_APP_LINK_EVENT = "DEFERRED_APP_LINK";
    private static final String DEFERRED_APP_LINK_PATH = "%s/activities";
    private static final String DEFERRED_APP_LINK_URL_FIELD = "applink_url";
    private static final String METHOD_ARGS_REF_KEY = "ref";
    private static final String METHOD_ARGS_TARGET_URL_KEY = "target_url";
    private static final String REFERER_DATA_REF_KEY = "fb_ref";
    private static final String TAG = AppLinkData.class.getCanonicalName();
    private Bundle argumentBundle;
    private JSONObject arguments;
    private String ref;
    private Uri targetUri;

    public interface CompletionHandler {
        void onDeferredAppLinkDataFetched(AppLinkData appLinkData) throws ;
    }

    public static void fetchDeferredAppLinkData(Context $r0, CompletionHandler $r1) throws  {
        fetchDeferredAppLinkData($r0, null, $r1);
    }

    public static void fetchDeferredAppLinkData(Context $r0, String $r3, final CompletionHandler $r1) throws  {
        Validate.notNull($r0, "context");
        Validate.notNull($r1, "completionHandler");
        if ($r3 == null) {
            $r3 = Utility.getMetadataApplicationId($r0);
        }
        Validate.notNull($r3, "applicationId");
        $r0 = $r0.getApplicationContext();
        FacebookSdk.getExecutor().execute(new Runnable() {
            public void run() throws  {
                AppLinkData.fetchDeferredAppLinkFromServer($r0, $r3, $r1);
            }
        });
    }

    private static void fetchDeferredAppLinkFromServer(Context $r0, String $r1, CompletionHandler $r2) throws  {
        JSONObject $r3 = new JSONObject();
        try {
            $r3.put("event", DEFERRED_APP_LINK_EVENT);
            Utility.setAppEventAttributionParameters($r3, AttributionIdentifiers.getAttributionIdentifiers($r0), AppEventsLogger.getAnonymousAppDeviceGUID($r0), FacebookSdk.getLimitEventAndDataUsage($r0));
            $r3.put("application_package_name", $r0.getPackageName());
            AppLinkData $r7 = null;
            try {
                $r3 = GraphRequest.newPostRequest(null, String.format(DEFERRED_APP_LINK_PATH, new Object[]{$r1}), $r3, null).executeAndWait().getJSONObject();
                if ($r3 != null) {
                    String $r10 = $r3.optString(DEFERRED_APP_LINK_ARGS_FIELD);
                    long $l0 = $r3.optLong(DEFERRED_APP_LINK_CLICK_TIME_FIELD, -1);
                    String $r5 = $r3.optString(DEFERRED_APP_LINK_CLASS_FIELD);
                    $r1 = $r3.optString(DEFERRED_APP_LINK_URL_FIELD);
                    if (!TextUtils.isEmpty($r10)) {
                        AppLinkData $r11 = createFromJson($r10);
                        $r7 = $r11;
                        if ($l0 != -1) {
                            if ($r11.arguments != null) {
                                try {
                                    $r11.arguments.put(ARGUMENTS_TAPTIME_KEY, $l0);
                                } catch (JSONException e) {
                                    Log.d(TAG, "Unable to put tap time in AppLinkData.arguments");
                                }
                            }
                            if ($r11.argumentBundle != null) {
                                Bundle $r12 = $r11.argumentBundle;
                                Bundle bundle = $r12;
                                bundle.putString(ARGUMENTS_TAPTIME_KEY, Long.toString($l0));
                            }
                        }
                        if ($r5 != null) {
                            if ($r11.arguments != null) {
                                try {
                                    $r11.arguments.put(ARGUMENTS_NATIVE_CLASS_KEY, $r5);
                                } catch (JSONException e2) {
                                    Log.d(TAG, "Unable to put tap time in AppLinkData.arguments");
                                }
                            }
                            if ($r11.argumentBundle != null) {
                                $r11.argumentBundle.putString(ARGUMENTS_NATIVE_CLASS_KEY, $r5);
                            }
                        }
                        if ($r1 != null) {
                            if ($r11.arguments != null) {
                                try {
                                    $r11.arguments.put(ARGUMENTS_NATIVE_URL, $r1);
                                } catch (JSONException e3) {
                                    Log.d(TAG, "Unable to put tap time in AppLinkData.arguments");
                                }
                            }
                            if ($r11.argumentBundle != null) {
                                $r11.argumentBundle.putString(ARGUMENTS_NATIVE_URL, $r1);
                            }
                        }
                    }
                }
            } catch (Exception e4) {
                Utility.logd(TAG, "Unable to fetch deferred applink from server");
            }
            $r2.onDeferredAppLinkDataFetched($r7);
        } catch (Throwable $r13) {
            throw new FacebookException("An error occurred while preparing deferred app link", $r13);
        }
    }

    public static AppLinkData createFromActivity(Activity $r0) throws  {
        Validate.notNull($r0, RecoveryParamConstants.VALUE_ACTIVITY);
        Intent $r1 = $r0.getIntent();
        if ($r1 == null) {
            return null;
        }
        AppLinkData $r3 = createFromAlApplinkData($r1);
        AppLinkData $r2 = $r3;
        if ($r3 == null) {
            $r2 = createFromJson($r1.getStringExtra(BUNDLE_APPLINK_ARGS_KEY));
        }
        return $r2 == null ? createFromUri($r1.getData()) : $r2;
    }

    private static AppLinkData createFromAlApplinkData(Intent $r0) throws  {
        Bundle $r1 = $r0.getBundleExtra(BUNDLE_AL_APPLINK_DATA_KEY);
        if ($r1 == null) {
            return null;
        }
        AppLinkData $r2 = new AppLinkData();
        $r2.targetUri = $r0.getData();
        if ($r2.targetUri == null) {
            String $r4 = $r1.getString(METHOD_ARGS_TARGET_URL_KEY);
            if ($r4 != null) {
                $r2.targetUri = Uri.parse($r4);
            }
        }
        $r2.argumentBundle = $r1;
        $r2.arguments = null;
        $r1 = $r1.getBundle(ARGUMENTS_REFERER_DATA_KEY);
        if ($r1 == null) {
            return $r2;
        }
        $r2.ref = $r1.getString(REFERER_DATA_REF_KEY);
        return $r2;
    }

    private static AppLinkData createFromJson(String $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        try {
            JSONObject $r1 = new JSONObject($r0);
            $r0 = $r1.getString("version");
            if ($r1.getJSONObject("bridge_args").getString(BRIDGE_ARGS_METHOD_KEY).equals("applink") && $r0.equals("2")) {
                AppLinkData $r2 = new AppLinkData();
                $r2.arguments = $r1.getJSONObject("method_args");
                if ($r2.arguments.has(METHOD_ARGS_REF_KEY)) {
                    $r2.ref = $r2.arguments.getString(METHOD_ARGS_REF_KEY);
                } else {
                    if ($r2.arguments.has(ARGUMENTS_REFERER_DATA_KEY)) {
                        $r1 = $r2.arguments.getJSONObject(ARGUMENTS_REFERER_DATA_KEY);
                        if ($r1.has(REFERER_DATA_REF_KEY)) {
                            $r2.ref = $r1.getString(REFERER_DATA_REF_KEY);
                        }
                    }
                }
                if ($r2.arguments.has(METHOD_ARGS_TARGET_URL_KEY)) {
                    $r2.targetUri = Uri.parse($r2.arguments.getString(METHOD_ARGS_TARGET_URL_KEY));
                }
                $r2.argumentBundle = toBundle($r2.arguments);
                return $r2;
            }
        } catch (JSONException $r7) {
            Log.d(TAG, "Unable to parse AppLink JSON", $r7);
        } catch (FacebookException $r8) {
            Log.d(TAG, "Unable to parse AppLink JSON", $r8);
        }
        return null;
    }

    private static AppLinkData createFromUri(Uri $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        AppLinkData $r1 = new AppLinkData();
        $r1.targetUri = $r0;
        return $r1;
    }

    private static Bundle toBundle(JSONObject $r0) throws JSONException {
        Bundle $r2 = new Bundle();
        Iterator $r4 = $r0.keys();
        while ($r4.hasNext()) {
            String $r6 = (String) $r4.next();
            Object $r5 = $r0.get($r6);
            if ($r5 instanceof JSONObject) {
                $r2.putBundle($r6, toBundle((JSONObject) $r5));
            } else if ($r5 instanceof JSONArray) {
                JSONArray $r9 = (JSONArray) $r5;
                if ($r9.length() == 0) {
                    $r2.putStringArray($r6, new String[0]);
                } else {
                    $r5 = $r9.get(0);
                    int $i0;
                    if ($r5 instanceof JSONObject) {
                        Bundle[] $r3 = new Bundle[$r9.length()];
                        for ($i0 = 0; $i0 < $r9.length(); $i0++) {
                            $r3[$i0] = toBundle($r9.getJSONObject($i0));
                        }
                        $r2.putParcelableArray($r6, $r3);
                    } else if ($r5 instanceof JSONArray) {
                        throw new FacebookException("Nested arrays are not supported.");
                    } else {
                        String[] $r1 = new String[$r9.length()];
                        for ($i0 = 0; $i0 < $r9.length(); $i0++) {
                            $r1[$i0] = $r9.get($i0).toString();
                        }
                        $r2.putStringArray($r6, $r1);
                    }
                }
            } else {
                $r2.putString($r6, $r5.toString());
            }
        }
        return $r2;
    }

    private AppLinkData() throws  {
    }

    public Uri getTargetUri() throws  {
        return this.targetUri;
    }

    public String getRef() throws  {
        return this.ref;
    }

    public Bundle getArgumentBundle() throws  {
        return this.argumentBundle;
    }

    public Bundle getRefererData() throws  {
        if (this.argumentBundle != null) {
            return this.argumentBundle.getBundle(ARGUMENTS_REFERER_DATA_KEY);
        }
        return null;
    }
}
