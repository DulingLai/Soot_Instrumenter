package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.IntentCompat;
import android.support.v4.content.SharedPreferencesCompat.EditorCompat;

public class AppLaunchChecker {
    private static final String KEY_STARTED_FROM_LAUNCHER = "startedFromLauncher";
    private static final String SHARED_PREFS_NAME = "android.support.AppLaunchChecker";

    public static boolean hasStartedFromLauncher(Context $r0) throws  {
        return $r0.getSharedPreferences(SHARED_PREFS_NAME, 0).getBoolean(KEY_STARTED_FROM_LAUNCHER, false);
    }

    public static void onActivityCreate(Activity $r0) throws  {
        SharedPreferences $r1 = $r0.getSharedPreferences(SHARED_PREFS_NAME, 0);
        if (!$r1.getBoolean(KEY_STARTED_FROM_LAUNCHER, false)) {
            Intent $r2 = $r0.getIntent();
            if ($r2 != null && "android.intent.action.MAIN".equals($r2.getAction())) {
                if ($r2.hasCategory("android.intent.category.LAUNCHER") || $r2.hasCategory(IntentCompat.CATEGORY_LEANBACK_LAUNCHER)) {
                    EditorCompat.getInstance().apply($r1.edit().putBoolean(KEY_STARTED_FROM_LAUNCHER, true));
                }
            }
        }
    }
}
