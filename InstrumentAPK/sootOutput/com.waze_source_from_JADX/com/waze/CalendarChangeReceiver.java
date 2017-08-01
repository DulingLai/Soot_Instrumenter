package com.waze;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CalendarChangeReceiver extends BroadcastReceiver {
    public void onReceive(Context $r1, Intent intent) throws  {
        if (NativeManager.IsAppStarted()) {
            if (NativeManager.getInstance().calendarAccessEnabled()) {
                NativeManager.getInstance().setCalendarEventsDirty(true);
                NativeManager.getInstance().updateCalendarEvents();
            }
        } else if (OfflineNativeManager.getInstance($r1).accessToCalendarAllowed()) {
            OfflineNativeManager.getInstance($r1).updateCalendarEvents();
        }
    }
}
