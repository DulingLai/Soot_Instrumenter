package android.support.v4.app;

import android.app.Activity;
import android.content.Intent;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

class ShareCompatICS {
    private static final String HISTORY_FILENAME_PREFIX = ".sharecompat_";

    ShareCompatICS() throws  {
    }

    public static void configureMenuItem(MenuItem $r0, Activity $r1, Intent $r2) throws  {
        ShareActionProvider $r4;
        ActionProvider $r3 = $r0.getActionProvider();
        if ($r3 instanceof ShareActionProvider) {
            $r4 = (ShareActionProvider) $r3;
        } else {
            $r4 = new ShareActionProvider($r1);
        }
        $r4.setShareHistoryFileName(HISTORY_FILENAME_PREFIX + $r1.getClass().getName());
        $r4.setShareIntent($r2);
        $r0.setActionProvider($r4);
    }
}
