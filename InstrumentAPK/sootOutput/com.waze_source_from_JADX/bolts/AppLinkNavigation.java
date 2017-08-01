package bolts;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.util.SparseArray;
import bolts.AppLink.Target;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.AnalyticsEvents;
import dalvik.annotation.Signature;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppLinkNavigation {
    private static final String KEY_NAME_REFERER_APP_LINK = "referer_app_link";
    private static final String KEY_NAME_REFERER_APP_LINK_APP_NAME = "app_name";
    private static final String KEY_NAME_REFERER_APP_LINK_PACKAGE = "package";
    private static final String KEY_NAME_USER_AGENT = "user_agent";
    private static final String KEY_NAME_VERSION = "version";
    private static final String VERSION = "1.0";
    private static AppLinkResolver defaultResolver;
    private final AppLink appLink;
    private final Bundle appLinkData;
    private final Bundle extras;

    public enum NavigationResult {
        FAILED("failed", false),
        WEB(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_WEB, true),
        APP("app", true);
        
        private String code;
        private boolean succeeded;

        public String getCode() throws  {
            return this.code;
        }

        public boolean isSucceeded() throws  {
            return this.succeeded;
        }

        private NavigationResult(@Signature({"(", "Ljava/lang/String;", "Z)V"}) String $r2, @Signature({"(", "Ljava/lang/String;", "Z)V"}) boolean $z0) throws  {
            this.code = $r2;
            this.succeeded = $z0;
        }
    }

    public AppLinkNavigation(AppLink $r1, Bundle $r3, Bundle $r4) throws  {
        if ($r1 == null) {
            throw new IllegalArgumentException("appLink must not be null.");
        }
        if ($r3 == null) {
            $r3 = new Bundle();
        }
        if ($r4 == null) {
            $r4 = new Bundle();
        }
        this.appLink = $r1;
        this.extras = $r3;
        this.appLinkData = $r4;
    }

    public AppLink getAppLink() throws  {
        return this.appLink;
    }

    public Bundle getAppLinkData() throws  {
        return this.appLinkData;
    }

    public Bundle getExtras() throws  {
        return this.extras;
    }

    private Bundle buildAppLinkDataForNavigation(Context $r1) throws  {
        Bundle $r2 = new Bundle();
        Bundle $r3 = new Bundle();
        if ($r1 != null) {
            String $r4 = $r1.getPackageName();
            if ($r4 != null) {
                $r3.putString(KEY_NAME_REFERER_APP_LINK_PACKAGE, $r4);
            }
            ApplicationInfo $r5 = $r1.getApplicationInfo();
            if ($r5 != null) {
                $r4 = $r1.getString($r5.labelRes);
                if ($r4 != null) {
                    $r3.putString("app_name", $r4);
                }
            }
        }
        $r2.putAll(getAppLinkData());
        $r2.putString("target_url", getAppLink().getSourceUrl().toString());
        $r2.putString("version", "1.0");
        $r2.putString(KEY_NAME_USER_AGENT, "Bolts Android 1.2.0");
        $r2.putBundle(KEY_NAME_REFERER_APP_LINK, $r3);
        $r2.putBundle("extras", getExtras());
        return $r2;
    }

    private Object getJSONValue(Object $r1) throws JSONException {
        if ($r1 instanceof Bundle) {
            return getJSONForBundle((Bundle) $r1);
        }
        if ($r1 instanceof CharSequence) {
            return $r1.toString();
        }
        JSONArray $r4;
        if ($r1 instanceof List) {
            $r4 = new JSONArray();
            for (Object $r12 : (List) $r12) {
                $r4.put(getJSONValue($r12));
            }
            return $r4;
        } else if ($r12 instanceof SparseArray) {
            $r4 = new JSONArray();
            SparseArray $r8 = (SparseArray) $r12;
            for ($i0 = 0; $i0 < $r8.size(); $i0++) {
                $r4.put($r8.keyAt($i0), getJSONValue($r8.valueAt($i0)));
            }
            return $r4;
        } else if ($r12 instanceof Character) {
            return $r12.toString();
        } else {
            if ($r12 instanceof Boolean) {
                return $r12;
            }
            if ($r12 instanceof Number) {
                if (($r12 instanceof Double) || ($r12 instanceof Float)) {
                    return Double.valueOf(((Number) $r12).doubleValue());
                }
                return Long.valueOf(((Number) $r12).longValue());
            } else if ($r12 instanceof boolean[]) {
                $r4 = new JSONArray();
                for (boolean $z0 : (boolean[]) $r12) {
                    $r4.put(getJSONValue(Boolean.valueOf($z0)));
                }
                return $r4;
            } else if ($r12 instanceof char[]) {
                $r4 = new JSONArray();
                for (char $c3 : (char[]) $r12) {
                    $r4.put(getJSONValue(Character.valueOf($c3)));
                }
                return $r4;
            } else if ($r12 instanceof CharSequence[]) {
                $r4 = new JSONArray();
                for (CharSequence $r17 : (CharSequence[]) $r12) {
                    $r4.put(getJSONValue($r17));
                }
                return $r4;
            } else if ($r12 instanceof double[]) {
                $r4 = new JSONArray();
                for (double $d0 : (double[]) $r12) {
                    $r4.put(getJSONValue(Double.valueOf($d0)));
                }
                return $r4;
            } else if ($r12 instanceof float[]) {
                $r4 = new JSONArray();
                for (float $f0 : (float[]) $r12) {
                    $r4.put(getJSONValue(Float.valueOf($f0)));
                }
                return $r4;
            } else if ($r12 instanceof int[]) {
                $r4 = new JSONArray();
                for (int $i4 : (int[]) $r12) {
                    $r4.put(getJSONValue(Integer.valueOf($i4)));
                }
                return $r4;
            } else if ($r12 instanceof long[]) {
                $r4 = new JSONArray();
                for (long $l2 : (long[]) $r12) {
                    $r4.put(getJSONValue(Long.valueOf($l2)));
                }
                return $r4;
            } else if ($r12 instanceof short[]) {
                $r4 = new JSONArray();
                for (short $s5 : (short[]) $r12) {
                    $r4.put(getJSONValue(Short.valueOf($s5)));
                }
                return $r4;
            } else if (!($r12 instanceof String[])) {
                return null;
            } else {
                $r4 = new JSONArray();
                for (String $r5 : (String[]) $r12) {
                    $r4.put(getJSONValue($r5));
                }
                return $r4;
            }
        }
    }

    private JSONObject getJSONForBundle(Bundle $r1) throws JSONException {
        JSONObject $r2 = new JSONObject();
        for (String $r6 : $r1.keySet()) {
            $r2.put($r6, getJSONValue($r1.get($r6)));
        }
        return $r2;
    }

    public NavigationResult navigate(Context $r1) throws  {
        Intent $r3;
        PackageManager $r4 = $r1.getPackageManager();
        Bundle $r5 = buildAppLinkDataForNavigation($r1);
        Intent $r6 = null;
        for (Target $r11 : getAppLink().getTargets()) {
            $r3 = new Intent("android.intent.action.VIEW");
            if ($r11.getUrl() != null) {
                $r3.setData($r11.getUrl());
            } else {
                $r3.setData(this.appLink.getSourceUrl());
            }
            $r3.setPackage($r11.getPackageName());
            if ($r11.getClassName() != null) {
                $r3.setClassName($r11.getPackageName(), $r11.getClassName());
            }
            $r3.putExtra("al_applink_data", $r5);
            if ($r4.resolveActivity($r3, 65536) != null) {
                $r6 = $r3;
                break;
            }
        }
        $r3 = null;
        NavigationResult $r16 = NavigationResult.FAILED;
        if ($r6 != null) {
            $r3 = $r6;
            $r16 = NavigationResult.APP;
        } else {
            Uri $r12 = getAppLink().getWebUrl();
            if ($r12 != null) {
                try {
                    JSONObject $r17 = getJSONForBundle($r5);
                    Builder $r18 = $r12.buildUpon();
                    Builder builder = $r18;
                    $r3 = new Intent("android.intent.action.VIEW", builder.appendQueryParameter("al_applink_data", $r17.toString()).build());
                    $r16 = NavigationResult.WEB;
                } catch (Throwable $r2) {
                    sendAppLinkNavigateEventBroadcast($r1, $r6, NavigationResult.FAILED, $r2);
                    throw new RuntimeException($r2);
                }
            }
        }
        sendAppLinkNavigateEventBroadcast($r1, $r3, $r16, null);
        if ($r3 == null) {
            return $r16;
        }
        $r1.startActivity($r3);
        return $r16;
    }

    private void sendAppLinkNavigateEventBroadcast(Context $r1, Intent $r2, NavigationResult $r3, JSONException $r4) throws  {
        HashMap $r5 = new HashMap();
        if ($r4 != null) {
            $r5.put("error", $r4.getLocalizedMessage());
        }
        $r5.put("success", $r3.isSucceeded() ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
        $r5.put("type", $r3.getCode());
        MeasurementEvent.sendBroadcastEvent($r1, MeasurementEvent.APP_LINK_NAVIGATE_OUT_EVENT_NAME, $r2, $r5);
    }

    public static void setDefaultResolver(AppLinkResolver $r0) throws  {
        defaultResolver = $r0;
    }

    public static AppLinkResolver getDefaultResolver() throws  {
        return defaultResolver;
    }

    private static AppLinkResolver getResolver(Context $r0) throws  {
        if (getDefaultResolver() != null) {
            return getDefaultResolver();
        }
        return new WebViewAppLinkResolver($r0);
    }

    public static NavigationResult navigate(Context $r0, AppLink $r1) throws  {
        return new AppLinkNavigation($r1, null, null).navigate($r0);
    }

    public static Task<NavigationResult> navigateInBackground(@Signature({"(", "Landroid/content/Context;", "Landroid/net/Uri;", "Lbolts/AppLinkResolver;", ")", "Lbolts/Task", "<", "Lbolts/AppLinkNavigation$NavigationResult;", ">;"}) final Context $r0, @Signature({"(", "Landroid/content/Context;", "Landroid/net/Uri;", "Lbolts/AppLinkResolver;", ")", "Lbolts/Task", "<", "Lbolts/AppLinkNavigation$NavigationResult;", ">;"}) Uri $r1, @Signature({"(", "Landroid/content/Context;", "Landroid/net/Uri;", "Lbolts/AppLinkResolver;", ")", "Lbolts/Task", "<", "Lbolts/AppLinkNavigation$NavigationResult;", ">;"}) AppLinkResolver $r2) throws  {
        return $r2.getAppLinkFromUrlInBackground($r1).onSuccess(new Continuation<AppLink, NavigationResult>() {
            public NavigationResult then(@Signature({"(", "Lbolts/Task", "<", "Lbolts/AppLink;", ">;)", "Lbolts/AppLinkNavigation$NavigationResult;"}) Task<AppLink> $r1) throws Exception {
                return AppLinkNavigation.navigate($r0, (AppLink) $r1.getResult());
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    public static Task<NavigationResult> navigateInBackground(@Signature({"(", "Landroid/content/Context;", "Ljava/net/URL;", "Lbolts/AppLinkResolver;", ")", "Lbolts/Task", "<", "Lbolts/AppLinkNavigation$NavigationResult;", ">;"}) Context $r0, @Signature({"(", "Landroid/content/Context;", "Ljava/net/URL;", "Lbolts/AppLinkResolver;", ")", "Lbolts/Task", "<", "Lbolts/AppLinkNavigation$NavigationResult;", ">;"}) URL $r1, @Signature({"(", "Landroid/content/Context;", "Ljava/net/URL;", "Lbolts/AppLinkResolver;", ")", "Lbolts/Task", "<", "Lbolts/AppLinkNavigation$NavigationResult;", ">;"}) AppLinkResolver $r2) throws  {
        return navigateInBackground($r0, Uri.parse($r1.toString()), $r2);
    }

    public static Task<NavigationResult> navigateInBackground(@Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "Lbolts/AppLinkResolver;", ")", "Lbolts/Task", "<", "Lbolts/AppLinkNavigation$NavigationResult;", ">;"}) Context $r0, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "Lbolts/AppLinkResolver;", ")", "Lbolts/Task", "<", "Lbolts/AppLinkNavigation$NavigationResult;", ">;"}) String $r1, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "Lbolts/AppLinkResolver;", ")", "Lbolts/Task", "<", "Lbolts/AppLinkNavigation$NavigationResult;", ">;"}) AppLinkResolver $r2) throws  {
        return navigateInBackground($r0, Uri.parse($r1), $r2);
    }

    public static Task<NavigationResult> navigateInBackground(@Signature({"(", "Landroid/content/Context;", "Landroid/net/Uri;", ")", "Lbolts/Task", "<", "Lbolts/AppLinkNavigation$NavigationResult;", ">;"}) Context $r0, @Signature({"(", "Landroid/content/Context;", "Landroid/net/Uri;", ")", "Lbolts/Task", "<", "Lbolts/AppLinkNavigation$NavigationResult;", ">;"}) Uri $r1) throws  {
        return navigateInBackground($r0, $r1, getResolver($r0));
    }

    public static Task<NavigationResult> navigateInBackground(@Signature({"(", "Landroid/content/Context;", "Ljava/net/URL;", ")", "Lbolts/Task", "<", "Lbolts/AppLinkNavigation$NavigationResult;", ">;"}) Context $r0, @Signature({"(", "Landroid/content/Context;", "Ljava/net/URL;", ")", "Lbolts/Task", "<", "Lbolts/AppLinkNavigation$NavigationResult;", ">;"}) URL $r1) throws  {
        return navigateInBackground($r0, $r1, getResolver($r0));
    }

    public static Task<NavigationResult> navigateInBackground(@Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", ")", "Lbolts/Task", "<", "Lbolts/AppLinkNavigation$NavigationResult;", ">;"}) Context $r0, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", ")", "Lbolts/Task", "<", "Lbolts/AppLinkNavigation$NavigationResult;", ">;"}) String $r1) throws  {
        return navigateInBackground($r0, $r1, getResolver($r0));
    }
}
