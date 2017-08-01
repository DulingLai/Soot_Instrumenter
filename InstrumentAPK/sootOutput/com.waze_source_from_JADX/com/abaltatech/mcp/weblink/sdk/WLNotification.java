package com.abaltatech.mcp.weblink.sdk;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import com.abaltatech.mcp.weblink.sdk.WLNotificationManager.NotificationPriority;
import java.util.Date;

public class WLNotification implements Comparable<WLNotification> {
    public PendingIntent contentIntent;
    public PendingIntent deleteIntent;
    public Bitmap largeIcon = null;
    public String largeIconURL = "";
    NotificationPriority notificationPriority;
    public int priority = 0;
    public boolean showProgress = false;
    public String text = "Wow!";
    public int timeoutMilliseconds = WLNotificationManager.DEFAULT_TIMEOUT_MILLISECONDS;
    public long when = new Date().getTime();

    public static class Builder {
        private WLNotification m_notification = new WLNotification();

        public Builder(Context context) throws  {
        }

        public Builder setContentText(String $r1) throws  {
            this.m_notification.text = $r1;
            return this;
        }

        public Builder setLargeIcon(Bitmap $r1) throws  {
            this.m_notification.largeIcon = $r1;
            return this;
        }

        public Builder setLargeIconURL(String $r1) throws  {
            this.m_notification.largeIconURL = $r1;
            return this;
        }

        public Builder setPriority(int $i0) throws  {
            this.m_notification.priority = $i0;
            return this;
        }

        public Builder setShowProgress(boolean $z0) throws  {
            this.m_notification.showProgress = $z0;
            return this;
        }

        public Builder setTimeout(int $i0) throws  {
            this.m_notification.timeoutMilliseconds = $i0;
            return this;
        }

        public Builder setContentIntent(PendingIntent $r1) throws  {
            this.m_notification.contentIntent = $r1;
            return this;
        }

        public Builder setDeleteIntent(PendingIntent $r1) throws  {
            this.m_notification.deleteIntent = $r1;
            return this;
        }

        public WLNotification build() throws  {
            return this.m_notification;
        }
    }

    WLNotification() throws  {
    }

    public int compareTo(WLNotification $r1) throws  {
        if (this == null && $r1 == null) {
            return 0;
        }
        if (this == null) {
            return -1;
        }
        if ($r1 == null) {
            return 1;
        }
        int $i0 = this.notificationPriority.compareTo($r1.notificationPriority);
        int $i1 = $i0;
        if ($i0 == 0) {
            $i1 = this.when < $r1.when ? 1 : this.when > $r1.when ? -1 : 0;
        }
        return $i1;
    }
}
