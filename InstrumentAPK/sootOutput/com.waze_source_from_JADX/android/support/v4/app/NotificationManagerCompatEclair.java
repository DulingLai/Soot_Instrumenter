package android.support.v4.app;

import android.app.Notification;
import android.app.NotificationManager;

class NotificationManagerCompatEclair {
    NotificationManagerCompatEclair() throws  {
    }

    static void cancelNotification(NotificationManager $r0, String $r1, int $i0) throws  {
        $r0.cancel($r1, $i0);
    }

    public static void postNotification(NotificationManager $r0, String $r1, int $i0, Notification $r2) throws  {
        $r0.notify($r1, $i0, $r2);
    }
}
