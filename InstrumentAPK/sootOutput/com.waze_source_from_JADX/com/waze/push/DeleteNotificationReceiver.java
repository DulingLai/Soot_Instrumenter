package com.waze.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.waze.analytics.AnalyticsEvents;
import com.waze.utils.NotificationsActionsBuilder;
import com.waze.utils.OfflineStats;

public class DeleteNotificationReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(NotificationsActionsBuilder.EXTRA_NOTIFICATION_TYPE);
        OfflineStats.SendStats(context, AnalyticsEvents.ANALYTICS_EVENTS_PUSH_MESSAGE_DISMISSED, new String[]{"TYPE", type});
    }
}
