package com.waze;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.TrafficStats;
import android.os.Process;
import android.os.SystemClock;
import android.support.v4.media.session.PlaybackStateCompat;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;

public class SessionTrafficStats {
    private static final String LAST_SESSION_RX = "LAST_SESSION_RX";
    private static final String LAST_SESSION_TX = "LAST_SESSION_TX";
    private static final String LAST_SYSTEM_BOOT_TIME = "LAST_SYSTEM_BOOT_TIME";
    private static final String PREF_NAME = "SessionTrafficStats";
    private long mLastSessionRx = 0;
    private long mLastSessionTx = 0;
    private SharedPreferences mPrefs;
    private long mThisBootTime;

    public SessionTrafficStats(Context $r1) throws  {
        this.mPrefs = $r1.getSharedPreferences(PREF_NAME, 0);
        long $l0 = this.mPrefs.getLong(LAST_SYSTEM_BOOT_TIME, 0);
        this.mThisBootTime = System.currentTimeMillis() - SystemClock.elapsedRealtime();
        if (sortOfEquals(this.mThisBootTime, $l0)) {
            this.mLastSessionTx = this.mPrefs.getLong(LAST_SESSION_TX, 0);
            this.mLastSessionRx = this.mPrefs.getLong(LAST_SESSION_RX, 0);
        }
    }

    private boolean sortOfEquals(long $l0, long $l1) throws  {
        $l0 -= $l1;
        return $l0 > 0 ? $l0 < 100 : $l0 > -100;
    }

    public void endSession() throws  {
        long $l1 = TrafficStats.getUidRxBytes(Process.myUid());
        long $l2 = TrafficStats.getUidTxBytes(Process.myUid());
        if (this.mPrefs.contains(LAST_SYSTEM_BOOT_TIME)) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_DATA_CONSUMPTION, "KB", (int) (((($l1 + $l2) - this.mLastSessionRx) - this.mLastSessionTx) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID));
        }
        Editor $r2 = this.mPrefs.edit();
        Editor editor = $r2;
        editor.putLong(LAST_SYSTEM_BOOT_TIME, this.mThisBootTime);
        $r2.putLong(LAST_SESSION_TX, $l2);
        $r2.putLong(LAST_SESSION_RX, $l1);
        $r2.commit();
    }
}
