package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.RemoteInputCompatBase.RemoteInput;

public class NotificationCompatBase {

    public static abstract class Action {

        public interface Factory {
            Action build(int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] remoteInputArr) throws ;

            Action[] newArray(int i) throws ;
        }

        public abstract PendingIntent getActionIntent() throws ;

        public abstract Bundle getExtras() throws ;

        public abstract int getIcon() throws ;

        public abstract RemoteInput[] getRemoteInputs() throws ;

        public abstract CharSequence getTitle() throws ;
    }

    public static abstract class UnreadConversation {

        public interface Factory {
            UnreadConversation build(String[] strArr, RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] strArr2, long j) throws ;
        }

        abstract long getLatestTimestamp() throws ;

        abstract String[] getMessages() throws ;

        abstract String getParticipant() throws ;

        abstract String[] getParticipants() throws ;

        abstract PendingIntent getReadPendingIntent() throws ;

        abstract RemoteInput getRemoteInput() throws ;

        abstract PendingIntent getReplyPendingIntent() throws ;
    }

    public static Notification add(Notification $r0, Context $r1, CharSequence $r2, CharSequence $r3, PendingIntent $r4) throws  {
        $r0.setLatestEventInfo($r1, $r2, $r3, $r4);
        return $r0;
    }
}
