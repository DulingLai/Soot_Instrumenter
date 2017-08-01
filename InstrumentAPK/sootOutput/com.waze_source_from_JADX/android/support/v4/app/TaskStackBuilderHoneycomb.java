package android.support.v4.app;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

class TaskStackBuilderHoneycomb {
    TaskStackBuilderHoneycomb() throws  {
    }

    public static PendingIntent getActivitiesPendingIntent(Context $r0, int $i0, Intent[] $r1, int $i1) throws  {
        return PendingIntent.getActivities($r0, $i0, $r1, $i1);
    }
}
