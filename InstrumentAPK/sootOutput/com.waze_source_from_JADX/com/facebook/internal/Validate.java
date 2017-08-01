package com.facebook.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Looper;
import android.util.Log;
import com.facebook.FacebookActivity;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.FacebookSdkNotInitializedException;
import dalvik.annotation.Signature;
import java.util.Collection;

public final class Validate {
    private static final String CONTENT_PROVIDER_BASE = "com.facebook.app.FacebookContentProvider";
    private static final String CONTENT_PROVIDER_NOT_FOUND_REASON = "A ContentProvider for this app was not set up in the AndroidManifest.xml, please add %s as a provider to your AndroidManifest.xml file. See https://developers.facebook.com/docs/sharing/android for more info.";
    private static final String FACEBOOK_ACTIVITY_NOT_FOUND_REASON = "FacebookActivity is not declared in the AndroidManifest.xml, please add com.facebook.FacebookActivity to your AndroidManifest.xml file. See https://developers.facebook.com/docs/android/getting-started for more info.";
    private static final String NO_INTERNET_PERMISSION_REASON = "No internet permissions granted for the app, please add <uses-permission android:name=\"android.permission.INTERNET\" /> to your AndroidManifest.xml.";
    private static final String TAG = Validate.class.getName();

    public static void notNull(Object $r0, String $r1) throws  {
        if ($r0 == null) {
            throw new NullPointerException("Argument '" + $r1 + "' cannot be null");
        }
    }

    public static <T> void notEmpty(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/util/Collection", "<TT;>;", "Ljava/lang/String;", ")V"}) Collection<T> $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/util/Collection", "<TT;>;", "Ljava/lang/String;", ")V"}) String $r1) throws  {
        if ($r0.isEmpty()) {
            throw new IllegalArgumentException("Container '" + $r1 + "' cannot be empty");
        }
    }

    public static <T> void containsNoNulls(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/util/Collection", "<TT;>;", "Ljava/lang/String;", ")V"}) Collection<T> $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/util/Collection", "<TT;>;", "Ljava/lang/String;", ")V"}) String $r1) throws  {
        notNull($r0, $r1);
        for (T $r3 : $r0) {
            if ($r3 == null) {
                throw new NullPointerException("Container '" + $r1 + "' cannot contain null values");
            }
        }
    }

    public static void containsNoNullOrEmpty(@Signature({"(", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) Collection<String> $r0, @Signature({"(", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) String $r1) throws  {
        notNull($r0, $r1);
        for (String $r4 : $r0) {
            if ($r4 == null) {
                throw new NullPointerException("Container '" + $r1 + "' cannot contain null values");
            } else if ($r4.length() == 0) {
                throw new IllegalArgumentException("Container '" + $r1 + "' cannot contain empty values");
            }
        }
    }

    public static <T> void notEmptyAndContainsNoNulls(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/util/Collection", "<TT;>;", "Ljava/lang/String;", ")V"}) Collection<T> $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/util/Collection", "<TT;>;", "Ljava/lang/String;", ")V"}) String $r1) throws  {
        containsNoNulls($r0, $r1);
        notEmpty($r0, $r1);
    }

    public static void runningOnUiThread() throws  {
        if (!Looper.getMainLooper().equals(Looper.myLooper())) {
            throw new FacebookException("This method should be called from the UI thread");
        }
    }

    public static void notNullOrEmpty(String $r0, String $r1) throws  {
        if (Utility.isNullOrEmpty($r0)) {
            throw new IllegalArgumentException("Argument '" + $r1 + "' cannot be null or empty");
        }
    }

    public static void oneOf(Object $r0, String $r1, Object... $r2) throws  {
        for (Object $r3 : $r2) {
            if ($r3 != null) {
                if ($r3.equals($r0)) {
                    return;
                }
            } else if ($r0 == null) {
                return;
            }
        }
        throw new IllegalArgumentException("Argument '" + $r1 + "' was not one of the allowed values");
    }

    public static void sdkInitialized() throws  {
        if (!FacebookSdk.isInitialized()) {
            throw new FacebookSdkNotInitializedException("The SDK has not been initialized, make sure to call FacebookSdk.sdkInitialize() first.");
        }
    }

    public static String hasAppID() throws  {
        String $r1 = FacebookSdk.getApplicationId();
        if ($r1 != null) {
            return $r1;
        }
        throw new IllegalStateException("No App ID found, please set the App ID.");
    }

    public static void hasInternetPermissions(Context $r0) throws  {
        hasInternetPermissions($r0, true);
    }

    public static void hasInternetPermissions(Context $r0, boolean $z0) throws  {
        notNull($r0, "context");
        if ($r0.checkCallingOrSelfPermission("android.permission.INTERNET") != -1) {
            return;
        }
        if ($z0) {
            throw new IllegalStateException(NO_INTERNET_PERMISSION_REASON);
        }
        Log.w(TAG, NO_INTERNET_PERMISSION_REASON);
    }

    public static void hasFacebookActivity(Context $r0) throws  {
        hasFacebookActivity($r0, true);
    }

    public static void hasFacebookActivity(Context $r0, boolean $z0) throws  {
        notNull($r0, "context");
        PackageManager $r2 = $r0.getPackageManager();
        ActivityInfo $r3 = null;
        if ($r2 != null) {
            try {
                $r3 = $r2.getActivityInfo(new ComponentName($r0, FacebookActivity.class), 1);
            } catch (NameNotFoundException e) {
            }
        }
        if ($r3 != null) {
            return;
        }
        if ($z0) {
            throw new IllegalStateException(FACEBOOK_ACTIVITY_NOT_FOUND_REASON);
        }
        Log.w(TAG, FACEBOOK_ACTIVITY_NOT_FOUND_REASON);
    }

    public static void hasContentProvider(Context $r0) throws  {
        notNull($r0, "context");
        String $r1 = hasAppID();
        PackageManager $r2 = $r0.getPackageManager();
        if ($r2 != null) {
            if ($r2.resolveContentProvider(CONTENT_PROVIDER_BASE + $r1, 0) == null) {
                throw new IllegalStateException(String.format(CONTENT_PROVIDER_NOT_FOUND_REASON, new Object[]{CONTENT_PROVIDER_BASE + $r1}));
            }
        }
    }
}
