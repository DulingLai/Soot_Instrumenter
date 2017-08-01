package android.support.v4.content;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build.VERSION;

public final class IntentCompat {
    public static final String ACTION_EXTERNAL_APPLICATIONS_AVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE";
    public static final String ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE";
    public static final String CATEGORY_LEANBACK_LAUNCHER = "android.intent.category.LEANBACK_LAUNCHER";
    public static final String EXTRA_CHANGED_PACKAGE_LIST = "android.intent.extra.changed_package_list";
    public static final String EXTRA_CHANGED_UID_LIST = "android.intent.extra.changed_uid_list";
    public static final String EXTRA_HTML_TEXT = "android.intent.extra.HTML_TEXT";
    public static final int FLAG_ACTIVITY_CLEAR_TASK = 32768;
    public static final int FLAG_ACTIVITY_TASK_ON_HOME = 16384;
    private static final IntentCompatImpl IMPL;

    interface IntentCompatImpl {
        Intent makeMainActivity(ComponentName componentName) throws ;

        Intent makeMainSelectorActivity(String str, String str2) throws ;

        Intent makeRestartActivityTask(ComponentName componentName) throws ;
    }

    static class IntentCompatImplBase implements IntentCompatImpl {
        IntentCompatImplBase() throws  {
        }

        public Intent makeMainActivity(ComponentName $r1) throws  {
            Intent $r2 = new Intent("android.intent.action.MAIN");
            $r2.setComponent($r1);
            $r2.addCategory("android.intent.category.LAUNCHER");
            return $r2;
        }

        public Intent makeMainSelectorActivity(String $r1, String $r2) throws  {
            Intent $r3 = new Intent($r1);
            $r3.addCategory($r2);
            return $r3;
        }

        public Intent makeRestartActivityTask(ComponentName $r1) throws  {
            Intent $r2 = makeMainActivity($r1);
            $r2.addFlags(268468224);
            return $r2;
        }
    }

    static class IntentCompatImplHC extends IntentCompatImplBase {
        IntentCompatImplHC() throws  {
        }

        public Intent makeMainActivity(ComponentName $r1) throws  {
            return IntentCompatHoneycomb.makeMainActivity($r1);
        }

        public Intent makeRestartActivityTask(ComponentName $r1) throws  {
            return IntentCompatHoneycomb.makeRestartActivityTask($r1);
        }
    }

    static class IntentCompatImplIcsMr1 extends IntentCompatImplHC {
        IntentCompatImplIcsMr1() throws  {
        }

        public Intent makeMainSelectorActivity(String $r1, String $r2) throws  {
            return IntentCompatIcsMr1.makeMainSelectorActivity($r1, $r2);
        }
    }

    static {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 15) {
            IMPL = new IntentCompatImplIcsMr1();
        } else if ($i0 >= 11) {
            IMPL = new IntentCompatImplHC();
        } else {
            IMPL = new IntentCompatImplBase();
        }
    }

    private IntentCompat() throws  {
    }

    public static Intent makeMainActivity(ComponentName $r0) throws  {
        return IMPL.makeMainActivity($r0);
    }

    public static Intent makeMainSelectorActivity(String $r0, String $r1) throws  {
        return IMPL.makeMainSelectorActivity($r0, $r1);
    }

    public static Intent makeRestartActivityTask(ComponentName $r0) throws  {
        return IMPL.makeRestartActivityTask($r0);
    }
}
