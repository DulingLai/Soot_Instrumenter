package com.waze;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.waze.analytics.AnalyticsEvents;
import com.waze.utils.OfflineStats;

public class ActionIntent extends BroadcastReceiver {
    public void onReceive(Context $r1, Intent $r2) throws  {
        try {
            ((NotificationManager) $r1.getSystemService("notification")).cancel(2);
            $r1.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
            if ($r2.getData() != null) {
                String $r8 = $r2.getStringExtra("QuestionID");
                String $r9 = $r2.getStringExtra("QuestionType");
                String $r10 = $r2.getStringExtra("Action");
                Context context = $r1;
                OfflineStats.SendStats(context, AnalyticsEvents.ANALYTICS_EVENT_INTERNAL_PUSH_CLICKED, new String[]{"PUSH_TYPE|ID|ACTION", $r9 + "|" + $r8 + "|" + $r10});
                Intent $r3 = new Intent($r1, FreeMapAppActivity.class);
                $r3.setData($r2.getData());
                if (!($r8 == null || $r8.isEmpty())) {
                    $r3.putExtra("QuestionID", $r8);
                }
                $r3.setFlags(268435456);
                $r1.startActivity($r3);
            }
        } catch (Throwable th) {
        }
    }
}
