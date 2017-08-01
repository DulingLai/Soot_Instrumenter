package android.support.v7.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat.Style;
import android.support.v4.media.session.MediaSessionCompat.Token;

public class NotificationCompat extends android.support.v4.app.NotificationCompat {

    public static class Builder extends android.support.v4.app.NotificationCompat.Builder {
        public Builder(Context $r1) throws  {
            super($r1);
        }

        protected BuilderExtender getExtender() throws  {
            if (VERSION.SDK_INT >= 21) {
                return new LollipopExtender();
            }
            if (VERSION.SDK_INT >= 16) {
                return new JellybeanExtender();
            }
            if (VERSION.SDK_INT >= 14) {
                return new IceCreamSandwichExtender();
            }
            return super.getExtender();
        }
    }

    private static class IceCreamSandwichExtender extends BuilderExtender {
        private IceCreamSandwichExtender() throws  {
        }

        public Notification build(android.support.v4.app.NotificationCompat.Builder $r1, NotificationBuilderWithBuilderAccessor $r2) throws  {
            NotificationCompat.addMediaStyleToBuilderIcs($r2, $r1);
            return $r2.build();
        }
    }

    private static class JellybeanExtender extends BuilderExtender {
        private JellybeanExtender() throws  {
        }

        public Notification build(android.support.v4.app.NotificationCompat.Builder $r1, NotificationBuilderWithBuilderAccessor $r2) throws  {
            NotificationCompat.addMediaStyleToBuilderIcs($r2, $r1);
            Notification $r3 = $r2.build();
            NotificationCompat.addBigMediaStyleToBuilderJellybean($r3, $r1);
            return $r3;
        }
    }

    private static class LollipopExtender extends BuilderExtender {
        private LollipopExtender() throws  {
        }

        public Notification build(android.support.v4.app.NotificationCompat.Builder $r1, NotificationBuilderWithBuilderAccessor $r2) throws  {
            NotificationCompat.addMediaStyleToBuilderLollipop($r2, $r1.mStyle);
            return $r2.build();
        }
    }

    public static class MediaStyle extends Style {
        int[] mActionsToShowInCompact = null;
        PendingIntent mCancelButtonIntent;
        boolean mShowCancelButton;
        Token mToken;

        public MediaStyle(android.support.v4.app.NotificationCompat.Builder $r1) throws  {
            setBuilder($r1);
        }

        public MediaStyle setShowActionsInCompactView(int... $r1) throws  {
            this.mActionsToShowInCompact = $r1;
            return this;
        }

        public MediaStyle setMediaSession(Token $r1) throws  {
            this.mToken = $r1;
            return this;
        }

        public MediaStyle setShowCancelButton(boolean $z0) throws  {
            this.mShowCancelButton = $z0;
            return this;
        }

        public MediaStyle setCancelButtonIntent(PendingIntent $r1) throws  {
            this.mCancelButtonIntent = $r1;
            return this;
        }
    }

    private static void addMediaStyleToBuilderLollipop(NotificationBuilderWithBuilderAccessor $r0, Style $r1) throws  {
        if ($r1 instanceof MediaStyle) {
            MediaStyle $r4 = (MediaStyle) $r1;
            NotificationCompatImpl21.addMediaStyle($r0, $r4.mActionsToShowInCompact, $r4.mToken != null ? $r4.mToken.getToken() : null);
        }
    }

    private static void addMediaStyleToBuilderIcs(NotificationBuilderWithBuilderAccessor $r0, android.support.v4.app.NotificationCompat.Builder $r1) throws  {
        if ($r1.mStyle instanceof MediaStyle) {
            MediaStyle mediaStyle = (MediaStyle) $r1.mStyle;
            Context $r2 = $r1.mContext;
            CharSequence $r3 = $r1.mContentTitle;
            CharSequence $r4 = $r1.mContentText;
            CharSequence $r5 = $r1.mContentInfo;
            int $i0 = $r1.mNumber;
            Bitmap $r6 = $r1.mLargeIcon;
            CharSequence $r7 = $r1.mSubText;
            boolean $z0 = $r1.mUseChronometer;
            long $l1 = $r1.mNotification;
            Notification $r12 = $l1;
            NotificationCompatImplBase.overrideContentView($r0, $r2, $r3, $r4, $r5, $i0, $r6, $r7, $z0, $l1.when, $r1.mActions, mediaStyle.mActionsToShowInCompact, mediaStyle.mShowCancelButton, mediaStyle.mCancelButtonIntent);
        }
    }

    private static void addBigMediaStyleToBuilderJellybean(Notification $r0, android.support.v4.app.NotificationCompat.Builder $r1) throws  {
        if ($r1.mStyle instanceof MediaStyle) {
            MediaStyle mediaStyle = (MediaStyle) $r1.mStyle;
            Context $r2 = $r1.mContext;
            CharSequence $r3 = $r1.mContentTitle;
            CharSequence $r4 = $r1.mContentText;
            CharSequence $r5 = $r1.mContentInfo;
            int $i0 = $r1.mNumber;
            Bitmap $r6 = $r1.mLargeIcon;
            CharSequence $r7 = $r1.mSubText;
            boolean $z0 = $r1.mUseChronometer;
            long $l1 = $r1.mNotification;
            Notification $r11 = $l1;
            NotificationCompatImplBase.overrideBigContentView($r0, $r2, $r3, $r4, $r5, $i0, $r6, $r7, $z0, $l1.when, $r1.mActions, mediaStyle.mShowCancelButton, mediaStyle.mCancelButtonIntent);
        }
    }
}
