package android.support.v4.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;

class NavUtilsJB {
    NavUtilsJB() throws  {
    }

    public static Intent getParentActivityIntent(Activity $r0) throws  {
        return $r0.getParentActivityIntent();
    }

    public static boolean shouldUpRecreateTask(Activity $r0, Intent $r1) throws  {
        return $r0.shouldUpRecreateTask($r1);
    }

    public static void navigateUpTo(Activity $r0, Intent $r1) throws  {
        $r0.navigateUpTo($r1);
    }

    public static String getParentActivityName(ActivityInfo $r0) throws  {
        return $r0.parentActivityName;
    }
}
