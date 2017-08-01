package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;

class NotificationCompatGingerbread {
    NotificationCompatGingerbread() throws  {
    }

    public static Notification add(Notification $r0, Context $r1, CharSequence $r2, CharSequence $r3, PendingIntent $r4, PendingIntent $r5) throws  {
        $r0.setLatestEventInfo($r1, $r2, $r3, $r4);
        $r0.fullScreenIntent = $r5;
        return $r0;
    }
}
