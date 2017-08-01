package android.support.v7.app;

import android.app.Notification.MediaStyle;
import android.media.session.MediaSession.Token;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;

class NotificationCompatImpl21 {
    NotificationCompatImpl21() throws  {
    }

    public static void addMediaStyle(NotificationBuilderWithBuilderAccessor $r0, int[] $r1, Object $r4) throws  {
        MediaStyle $r2 = new MediaStyle($r0.getBuilder());
        if ($r1 != null) {
            $r2.setShowActionsInCompactView($r1);
        }
        if ($r4 != null) {
            $r2.setMediaSession((Token) $r4);
        }
    }
}
