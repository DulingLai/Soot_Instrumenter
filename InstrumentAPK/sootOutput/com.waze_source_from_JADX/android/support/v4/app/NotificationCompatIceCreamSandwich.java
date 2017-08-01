package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.RemoteViews;

class NotificationCompatIceCreamSandwich {

    public static class Builder implements NotificationBuilderWithBuilderAccessor {
        private android.app.Notification.Builder f2b;

        public Builder(Context $r1, Notification $r2, CharSequence $r3, CharSequence $r4, CharSequence $r5, RemoteViews $r6, int $i0, PendingIntent $r7, PendingIntent $r8, Bitmap $r9, int $i1, int $i2, boolean $z0) throws  {
            android.app.Notification.Builder $r10 = new android.app.Notification.Builder($r1).setWhen($r2.when).setSmallIcon($r2.icon, $r2.iconLevel).setContent($r2.contentView).setTicker($r2.tickerText, $r6).setSound($r2.sound, $r2.audioStreamType).setVibrate($r2.vibrate).setLights($r2.ledARGB, $r2.ledOnMS, $r2.ledOffMS).setOngoing(($r2.flags & 2) != 0).setOnlyAlertOnce(($r2.flags & 8) != 0).setAutoCancel(($r2.flags & 16) != 0).setDefaults($r2.defaults).setContentTitle($r3).setContentText($r4).setContentInfo($r5).setContentIntent($r7);
            PendingIntent $r72 = $r2.deleteIntent;
            this.f2b = $r10.setDeleteIntent($r72).setFullScreenIntent($r8, ($r2.flags & 128) != 0).setLargeIcon($r9).setNumber($i0).setProgress($i1, $i2, $z0);
        }

        public android.app.Notification.Builder getBuilder() throws  {
            return this.f2b;
        }

        public Notification build() throws  {
            return this.f2b.getNotification();
        }
    }

    NotificationCompatIceCreamSandwich() throws  {
    }
}
