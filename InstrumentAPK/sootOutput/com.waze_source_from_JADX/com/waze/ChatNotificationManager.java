package com.waze;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationManagerCompat;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.strings.DisplayStrings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChatNotificationManager {
    static final /* synthetic */ boolean $assertionsDisabled = (!ChatNotificationManager.class.desiredAssertionStatus());
    private static final int CHAT_NOTIFICATIONS_ID = 5116;
    private static final String CHAT_URL_BASE = "waze://?message_ride_id=";
    private static ChatNotificationManager mInstance;
    private Handler mHandler = null;
    private Map<String, Set<ChatHandler>> mHandlers = new HashMap();
    private final boolean mOnline;

    class C11121 extends Handler {
        C11121() throws  {
        }

        public void handleMessage(Message $r1) throws  {
            String $r3 = $r1.getData().getString(CarpoolNativeManager.UH_KEY_RIDE_ID, "");
            if ($r3 == null) {
                Logger.m38e("ChatNotificationManager: Received msg type " + $r1.what + " with a null chat id");
                return;
            }
            Set<ChatHandler> $r8 = (Set) ChatNotificationManager.this.mHandlers.get($r3);
            Set<ChatHandler> $r9 = (Set) ChatNotificationManager.this.mHandlers.get("");
            String $r10;
            if ($r1.what == CarpoolNativeManager.UH_CARPOOL_CHAT_MESSAGE) {
                Object obj = null;
                $r10 = $r1.getData().getString(CarpoolNativeManager.UH_KEY_RIDE_MSG, "");
                if ($r9 != null) {
                    for (ChatHandler onChatMessage : $r9) {
                        onChatMessage.onChatMessage($r10);
                    }
                }
                if ($r8 != null && $r8.size() > 0) {
                    Logger.m36d("ChatNotificationManager: received chat msg " + $r10 + "; sending to handlers for " + $r3);
                    for (ChatHandler onChatMessage2 : $r8) {
                        if (onChatMessage2.onChatMessage($r10)) {
                            obj = 1;
                        }
                    }
                }
                if (obj == null) {
                    Logger.m36d("ChatNotificationManager: received chat msg " + $r10 + " but no handler for " + $r3 + "; Send notification");
                    if ($r10 != null) {
                        ChatNotificationManager.this.showNotification($r3, $r10);
                    }
                }
                CarpoolNativeManager.getInstance().updateCarpoolDot(false);
            } else if ($r1.what == CarpoolNativeManager.UH_CARPOOL_MESSAGES_LOADED) {
                if ($r9 != null) {
                    for (ChatHandler onMessagesLoaded : $r9) {
                        onMessagesLoaded.onMessagesLoaded();
                    }
                }
                if ($r8 == null || $r8.size() <= 0) {
                    Logger.m36d("ChatNotificationManager: received msgs loaded notification; but no handler registered for " + $r3);
                } else {
                    Logger.m36d("ChatNotificationManager: received msgs loaded notification; sending to handler for " + $r3);
                    for (ChatHandler onMessagesLoaded2 : $r8) {
                        onMessagesLoaded2.onMessagesLoaded();
                    }
                }
                CarpoolNativeManager.getInstance().updateCarpoolDot(false);
            } else if ($r1.what == CarpoolNativeManager.UH_CARPOOL_CHAT_MESSAGE_SENT_STATUS) {
                Boolean $r15 = Boolean.valueOf($r1.getData().getBoolean("ride_msg_send_status", false));
                if ($r9 != null) {
                    for (ChatHandler $r12 : $r9) {
                        $r12.onMessageSent($r15.booleanValue());
                    }
                }
                if ($r8 == null || $r8.size() <= 0) {
                    Logger.m36d("ChatNotificationManager: received msg sent status; but no handler registered for " + $r3);
                    return;
                }
                Logger.m36d("ChatNotificationManager: received msg sent status; sending to handler for " + $r3);
                for (ChatHandler $r122 : $r8) {
                    $r122.onMessageSent($r15.booleanValue());
                }
            } else if ($r1.what == CarpoolNativeManager.UH_CARPOOL_CHAT_MESSAGE_READ) {
                $r10 = $r1.getData().getString("ride_msg_send_status", null);
                if ($r9 != null) {
                    for (ChatHandler onMessageRead : $r9) {
                        onMessageRead.onMessageRead($r10);
                    }
                }
                if ($r8 == null || $r8.size() <= 0) {
                    Logger.m36d("ChatNotificationManager: received msg read; but no handler registered for " + $r3);
                } else {
                    Logger.m36d("ChatNotificationManager: received msg read; sending to handler for " + $r3);
                    for (ChatHandler onMessageRead2 : $r8) {
                        onMessageRead2.onMessageRead($r10);
                    }
                }
                CarpoolNativeManager.getInstance().updateCarpoolDot(false);
            }
        }
    }

    public interface ChatHandler {
        boolean onChatMessage(String str) throws ;

        void onMessageRead(String str) throws ;

        void onMessageSent(boolean z) throws ;

        void onMessagesLoaded() throws ;
    }

    public static ChatNotificationManager getInstance(boolean $z0) throws  {
        if (mInstance == null) {
            mInstance = new ChatNotificationManager($z0);
        }
        if ($assertionsDisabled || $z0 == mInstance.mOnline) {
            return mInstance;
        }
        throw new AssertionError();
    }

    private Context getContext() throws  {
        if (this.mOnline) {
            return AppService.getAppContext();
        }
        OfflineNativeManager.existingInstance();
        return OfflineNativeManager.getContext();
    }

    private ChatNotificationManager(boolean $z0) throws  {
        this.mOnline = $z0;
        if ($z0) {
            this.mHandler = new C11121();
            CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_CHAT_MESSAGE, this.mHandler);
            CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_MESSAGES_LOADED, this.mHandler);
            CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_CHAT_MESSAGE_SENT_STATUS, this.mHandler);
            CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_CHAT_MESSAGE_READ, this.mHandler);
        }
    }

    public void showNotification(String $r1, String $r2) throws  {
        String $r5;
        PendingIntent $r8 = PendingIntent.getActivity(getContext(), 0, new Intent("android.intent.action.VIEW", Uri.parse(CHAT_URL_BASE + $r1)), 268435456);
        if (this.mOnline) {
            $r5 = NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_WAZE_MESSAGE);
        } else {
            $r5 = OfflineNativeManager.existingInstance().getLanguageString((int) DisplayStrings.DS_WAZE_MESSAGE);
        }
        NotificationManagerCompat.from(getContext()).notify($r1, CHAT_NOTIFICATIONS_ID, new Builder(getContext()).setContentTitle($r5).setContentText($r2).setSmallIcon(C1283R.drawable.notification).setAutoCancel(true).setContentIntent($r8).setColor(getContext().getResources().getColor(C1283R.color.notification_circle_bg)).setDefaults(7).build());
    }

    public void hideNotifications(String $r1) throws  {
        NotificationManagerCompat.from(getContext()).cancel($r1, CHAT_NOTIFICATIONS_ID);
    }

    public void setChatUpdateHandler(String $r1, ChatHandler $r2) throws  {
        Set $r5 = (Set) this.mHandlers.get($r1);
        if ($r5 == null) {
            $r5 = r6;
            HashSet r6 = new HashSet();
            this.mHandlers.put($r1, $r5);
        }
        $r5.add($r2);
    }

    public void unsetChatUpdateHandler(String $r1, ChatHandler $r2) throws  {
        Set $r5 = (Set) this.mHandlers.get($r1);
        if ($r5 != null) {
            $r5.remove($r2);
        }
    }
}
