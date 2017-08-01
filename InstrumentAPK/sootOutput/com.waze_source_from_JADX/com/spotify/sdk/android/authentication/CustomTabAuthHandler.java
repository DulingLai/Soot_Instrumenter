package com.spotify.sdk.android.authentication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsIntent.Builder;
import android.text.TextUtils;
import android.util.Log;
import com.spotify.sdk.android.authentication.AuthenticationHandler.OnCompleteListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomTabAuthHandler implements AuthenticationHandler {
    private static final String ACTION_CUSTOM_TABS_CONNECTION = "android.support.customtabs.action.CustomTabsService";
    private static final Set<String> CHROME_PACKAGES = new HashSet(Arrays.asList(new String[]{"com.android.chrome", "com.chrome.beta", "com.chrome.dev", "com.google.android.apps.chrome"}));
    private static final int SPOTIFY_GREEN = Color.rgb(30, 215, 96);
    private static final String TAG = CustomTabAuthHandler.class.getSimpleName();

    public boolean start(Activity $r1, AuthenticationRequest $r2) throws  {
        String $r4 = getChromePackageName($r1);
        if ($r4 == null) {
            return false;
        }
        if (!hasCustomTabRedirectActivity($r1, $r2.getRedirectUri())) {
            return false;
        }
        Builder $r3 = new Builder();
        $r3.setToolbarColor(SPOTIFY_GREEN);
        CustomTabsIntent $r6 = $r3.build();
        $r6.intent.setPackage($r4);
        $r6.launchUrl($r1, $r2.toUri());
        return true;
    }

    public void stop() throws  {
        Log.d(TAG, "stop");
    }

    public void setOnCompleteListener(OnCompleteListener listener) throws  {
    }

    private String getChromePackageName(Context $r1) throws  {
        List<ResolveInfo> $r4 = $r1.getPackageManager().queryIntentServices(new Intent(ACTION_CUSTOM_TABS_CONNECTION), 0);
        if ($r4 != null) {
            for (ResolveInfo $r7 : $r4) {
                if (CHROME_PACKAGES.contains($r7.serviceInfo.packageName)) {
                    return $r7.serviceInfo.packageName;
                }
            }
        }
        return null;
    }

    private boolean hasCustomTabRedirectActivity(Context $r1, String $r2) throws  {
        PackageManager $r6 = $r1.getPackageManager();
        Intent $r5 = new Intent();
        $r5.setAction("android.intent.action.VIEW");
        $r5.addCategory("android.intent.category.DEFAULT");
        $r5.addCategory("android.intent.category.BROWSABLE");
        $r5.setData(Uri.parse($r2));
        List $r8 = $r6.queryIntentActivities($r5, 64);
        if ($r8 == null) {
            return false;
        }
        if ($r8.size() != 1) {
            return false;
        }
        ActivityInfo $r3 = ((ResolveInfo) $r8.get(0)).activityInfo;
        String $r12 = AuthCallbackActivity.class.getName();
        String $r22 = $r3.name;
        if (!$r12.equals($r22)) {
            return false;
        }
        IntentFilter $r4 = ((ResolveInfo) $r8.get(0)).filter;
        $r2 = $r4.getDataScheme(0);
        $r12 = $r4.getDataAuthority(0).getHost();
        if (!TextUtils.isEmpty($r2) || !TextUtils.isEmpty($r12)) {
            return true;
        }
        Log.w("SpotifyAuth", "Please provide valid callback URI for AuthCallbackActivity.\nYou need add @string/com_spotify_sdk_redirect_scheme and @string/com_spotify_sdk_redirect_host to your resources or\nAdd complete definition of AuthCallbackActivity");
        return false;
    }
}
