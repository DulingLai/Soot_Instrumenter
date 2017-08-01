package android.support.v4.app;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.RemoteViews;

class NotificationCompatHoneycomb {
    NotificationCompatHoneycomb() throws  {
    }

    static Notification add(Context $r0, Notification $r1, CharSequence $r2, CharSequence $r3, CharSequence $r4, RemoteViews $r5, int $i0, PendingIntent $r6, PendingIntent $r7, Bitmap $r8) throws  {
        Builder $r9 = new Builder($r0).setWhen($r1.when).setSmallIcon($r1.icon, $r1.iconLevel).setContent($r1.contentView).setTicker($r1.tickerText, $r5).setSound($r1.sound, $r1.audioStreamType).setVibrate($r1.vibrate).setLights($r1.ledARGB, $r1.ledOnMS, $r1.ledOffMS).setOngoing(($r1.flags & 2) != 0).setOnlyAlertOnce(($r1.flags & 8) != 0).setAutoCancel(($r1.flags & 16) != 0).setDefaults($r1.defaults).setContentTitle($r2).setContentText($r3).setContentInfo($r4).setContentIntent($r6);
        PendingIntent $r62 = $r1.deleteIntent;
        return $r9.setDeleteIntent($r62).setFullScreenIntent($r7, ($r1.flags & 128) != 0).setLargeIcon($r8).setNumber($i0).getNotification();
    }
}
