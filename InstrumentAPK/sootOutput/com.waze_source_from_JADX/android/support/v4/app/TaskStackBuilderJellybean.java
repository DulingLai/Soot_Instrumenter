package android.support.v4.app;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

class TaskStackBuilderJellybean {
    TaskStackBuilderJellybean() throws  {
    }

    public static PendingIntent getActivitiesPendingIntent(Context $r0, int $i0, Intent[] $r1, int $i1, Bundle $r2) throws  {
        return PendingIntent.getActivities($r0, $i0, $r1, $i1, $r2);
    }
}
