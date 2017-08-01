package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import com.abaltatech.mcp.mcs.utils.FilenameUtils;
import dalvik.annotation.Signature;

public final class NavUtils {
    private static final NavUtilsImpl IMPL;
    public static final String PARENT_ACTIVITY = "android.support.PARENT_ACTIVITY";
    private static final String TAG = "NavUtils";

    interface NavUtilsImpl {
        Intent getParentActivityIntent(Activity activity) throws ;

        String getParentActivityName(Context context, ActivityInfo activityInfo) throws ;

        void navigateUpTo(Activity activity, Intent intent) throws ;

        boolean shouldUpRecreateTask(Activity activity, Intent intent) throws ;
    }

    static class NavUtilsImplBase implements NavUtilsImpl {
        NavUtilsImplBase() throws  {
        }

        public Intent getParentActivityIntent(Activity $r1) throws  {
            String $r4 = NavUtils.getParentActivityName($r1);
            if ($r4 == null) {
                return null;
            }
            ComponentName $r3 = new ComponentName($r1, $r4);
            try {
                return NavUtils.getParentActivityName($r1, $r3) == null ? IntentCompat.makeMainActivity($r3) : new Intent().setComponent($r3);
            } catch (NameNotFoundException e) {
                Log.e(NavUtils.TAG, "getParentActivityIntent: bad parentActivityName '" + $r4 + "' in manifest");
                return null;
            }
        }

        public boolean shouldUpRecreateTask(Activity $r1, Intent targetIntent) throws  {
            String $r3 = $r1.getIntent().getAction();
            return ($r3 == null || $r3.equals("android.intent.action.MAIN")) ? false : true;
        }

        public void navigateUpTo(Activity $r1, Intent $r2) throws  {
            $r2.addFlags(67108864);
            $r1.startActivity($r2);
            $r1.finish();
        }

        public String getParentActivityName(Context $r1, ActivityInfo $r2) throws  {
            if ($r2.metaData == null) {
                return null;
            }
            String $r4 = $r2.metaData.getString(NavUtils.PARENT_ACTIVITY);
            if ($r4 == null) {
                return null;
            }
            return $r4.charAt(0) == FilenameUtils.EXTENSION_SEPARATOR ? $r1.getPackageName() + $r4 : $r4;
        }
    }

    static class NavUtilsImplJB extends NavUtilsImplBase {
        NavUtilsImplJB() throws  {
        }

        public Intent getParentActivityIntent(Activity $r1) throws  {
            Intent $r2 = NavUtilsJB.getParentActivityIntent($r1);
            if ($r2 == null) {
                return superGetParentActivityIntent($r1);
            }
            return $r2;
        }

        Intent superGetParentActivityIntent(Activity $r1) throws  {
            return super.getParentActivityIntent($r1);
        }

        public boolean shouldUpRecreateTask(Activity $r1, Intent $r2) throws  {
            return NavUtilsJB.shouldUpRecreateTask($r1, $r2);
        }

        public void navigateUpTo(Activity $r1, Intent $r2) throws  {
            NavUtilsJB.navigateUpTo($r1, $r2);
        }

        public String getParentActivityName(Context $r1, ActivityInfo $r2) throws  {
            String $r3 = NavUtilsJB.getParentActivityName($r2);
            if ($r3 == null) {
                return super.getParentActivityName($r1, $r2);
            }
            return $r3;
        }
    }

    static {
        if (VERSION.SDK_INT >= 16) {
            IMPL = new NavUtilsImplJB();
        } else {
            IMPL = new NavUtilsImplBase();
        }
    }

    public static boolean shouldUpRecreateTask(Activity $r0, Intent $r1) throws  {
        return IMPL.shouldUpRecreateTask($r0, $r1);
    }

    public static void navigateUpFromSameTask(Activity $r0) throws  {
        Intent $r2 = getParentActivityIntent($r0);
        if ($r2 == null) {
            throw new IllegalArgumentException("Activity " + $r0.getClass().getSimpleName() + " does not have a parent activity name specified." + " (Did you forget to add the android.support.PARENT_ACTIVITY <meta-data> " + " element in your manifest?)");
        }
        navigateUpTo($r0, $r2);
    }

    public static void navigateUpTo(Activity $r0, Intent $r1) throws  {
        IMPL.navigateUpTo($r0, $r1);
    }

    public static Intent getParentActivityIntent(Activity $r0) throws  {
        return IMPL.getParentActivityIntent($r0);
    }

    public static Intent getParentActivityIntent(@Signature({"(", "Landroid/content/Context;", "Ljava/lang/Class", "<*>;)", "Landroid/content/Intent;"}) Context $r0, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/Class", "<*>;)", "Landroid/content/Intent;"}) Class<?> $r1) throws NameNotFoundException {
        String $r3 = getParentActivityName($r0, new ComponentName($r0, $r1));
        if ($r3 == null) {
            return null;
        }
        ComponentName $r2 = new ComponentName($r0, $r3);
        return getParentActivityName($r0, $r2) == null ? IntentCompat.makeMainActivity($r2) : new Intent().setComponent($r2);
    }

    public static Intent getParentActivityIntent(Context $r0, ComponentName $r1) throws NameNotFoundException {
        String $r3 = getParentActivityName($r0, $r1);
        if ($r3 == null) {
            return null;
        }
        ComponentName $r2 = new ComponentName($r1.getPackageName(), $r3);
        return getParentActivityName($r0, $r2) == null ? IntentCompat.makeMainActivity($r2) : new Intent().setComponent($r2);
    }

    @Nullable
    public static String getParentActivityName(Activity $r0) throws  {
        try {
            return getParentActivityName($r0, $r0.getComponentName());
        } catch (NameNotFoundException $r1) {
            throw new IllegalArgumentException($r1);
        }
    }

    @Nullable
    public static String getParentActivityName(Context $r0, ComponentName $r1) throws NameNotFoundException {
        return IMPL.getParentActivityName($r0, $r0.getPackageManager().getActivityInfo($r1, 128));
    }

    private NavUtils() throws  {
    }
}
