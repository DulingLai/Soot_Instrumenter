package com.facebook.appevents;

import android.os.Bundle;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Logger;
import java.io.Serializable;
import java.util.Locale;

class FacebookTimeSpentData implements Serializable {
    private static final long APP_ACTIVATE_SUPPRESSION_PERIOD_IN_MILLISECONDS = 300000;
    private static final long FIRST_TIME_LOAD_RESUME_TIME = -1;
    private static final long[] INACTIVE_SECONDS_QUANTA = new long[]{APP_ACTIVATE_SUPPRESSION_PERIOD_IN_MILLISECONDS, 900000, 1800000, 3600000, 21600000, 43200000, 86400000, 172800000, 259200000, 604800000, 1209600000, 1814400000, 2419200000L, 5184000000L, 7776000000L, 10368000000L, 12960000000L, 15552000000L, 31536000000L};
    private static final long INTERRUPTION_THRESHOLD_MILLISECONDS = 1000;
    private static final long NUM_MILLISECONDS_IDLE_TO_BE_NEW_SESSION = 60000;
    private static final String TAG = AppEventsLogger.class.getCanonicalName();
    private static final long serialVersionUID = 1;
    private String firstOpenSourceApplication;
    private int interruptionCount;
    private boolean isAppActive;
    private boolean isWarmLaunch;
    private long lastActivateEventLoggedTime;
    private long lastResumeTime;
    private long lastSuspendTime;
    private long millisecondsSpentInSession;

    private static class SerializationProxyV1 implements Serializable {
        private static final long serialVersionUID = 6;
        private final int interruptionCount;
        private final long lastResumeTime;
        private final long lastSuspendTime;
        private final long millisecondsSpentInSession;

        SerializationProxyV1(long $l0, long $l1, long $l2, int $i3) throws  {
            this.lastResumeTime = $l0;
            this.lastSuspendTime = $l1;
            this.millisecondsSpentInSession = $l2;
            this.interruptionCount = $i3;
        }

        private Object readResolve() throws  {
            return new FacebookTimeSpentData(this.lastResumeTime, this.lastSuspendTime, this.millisecondsSpentInSession, this.interruptionCount);
        }
    }

    private static class SerializationProxyV2 implements Serializable {
        private static final long serialVersionUID = 6;
        private final String firstOpenSourceApplication;
        private final int interruptionCount;
        private final long lastResumeTime;
        private final long lastSuspendTime;
        private final long millisecondsSpentInSession;

        SerializationProxyV2(long $l0, long $l1, long $l2, int $i3, String $r1) throws  {
            this.lastResumeTime = $l0;
            this.lastSuspendTime = $l1;
            this.millisecondsSpentInSession = $l2;
            this.interruptionCount = $i3;
            this.firstOpenSourceApplication = $r1;
        }

        private Object readResolve() throws  {
            return new FacebookTimeSpentData(this.lastResumeTime, this.lastSuspendTime, this.millisecondsSpentInSession, this.interruptionCount, this.firstOpenSourceApplication);
        }
    }

    private FacebookTimeSpentData(long $l0, long $l1, long $l2, int $i3) throws  {
        resetSession();
        this.lastResumeTime = $l0;
        this.lastSuspendTime = $l1;
        this.millisecondsSpentInSession = $l2;
        this.interruptionCount = $i3;
    }

    FacebookTimeSpentData() throws  {
        resetSession();
    }

    private FacebookTimeSpentData(long $l0, long $l1, long $l2, int $i3, String $r1) throws  {
        resetSession();
        this.lastResumeTime = $l0;
        this.lastSuspendTime = $l1;
        this.millisecondsSpentInSession = $l2;
        this.interruptionCount = $i3;
        this.firstOpenSourceApplication = $r1;
    }

    private Object writeReplace() throws  {
        return new SerializationProxyV2(this.lastResumeTime, this.lastSuspendTime, this.millisecondsSpentInSession, this.interruptionCount, this.firstOpenSourceApplication);
    }

    void onSuspend(AppEventsLogger logger, long $l0) throws  {
        if (this.isAppActive) {
            long $l1 = $l0 - this.lastResumeTime;
            if ($l1 < 0) {
                Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Clock skew detected");
                $l1 = 0;
            }
            this.millisecondsSpentInSession += $l1;
            this.lastSuspendTime = $l0;
            this.isAppActive = false;
            return;
        }
        Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Suspend for inactive app");
    }

    void onResume(AppEventsLogger $r1, long $l0, String $r2) throws  {
        if (isColdLaunch() || $l0 - this.lastActivateEventLoggedTime > APP_ACTIVATE_SUPPRESSION_PERIOD_IN_MILLISECONDS) {
            Bundle $r3 = new Bundle();
            $r3.putString(AppEventsConstants.EVENT_PARAM_SOURCE_APPLICATION, $r2);
            $r1.logEvent(AppEventsConstants.EVENT_NAME_ACTIVATED_APP, $r3);
            this.lastActivateEventLoggedTime = $l0;
        }
        if (this.isAppActive) {
            Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Resume for active app");
            return;
        }
        long $l1 = wasSuspendedEver() ? $l0 - this.lastSuspendTime : 0;
        if ($l1 < 0) {
            Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Clock skew detected");
            $l1 = 0;
        }
        if ($l1 > NUM_MILLISECONDS_IDLE_TO_BE_NEW_SESSION) {
            logAppDeactivatedEvent($r1, $l1);
        } else if ($l1 > INTERRUPTION_THRESHOLD_MILLISECONDS) {
            this.interruptionCount++;
        }
        if (this.interruptionCount == 0) {
            this.firstOpenSourceApplication = $r2;
        }
        this.lastResumeTime = $l0;
        this.isAppActive = true;
    }

    private void logAppDeactivatedEvent(AppEventsLogger $r1, long $l0) throws  {
        Bundle $r2 = new Bundle();
        $r2.putInt(AppEventsConstants.EVENT_NAME_SESSION_INTERRUPTIONS, this.interruptionCount);
        $r2.putString(AppEventsConstants.EVENT_NAME_TIME_BETWEEN_SESSIONS, String.format(Locale.ROOT, "session_quanta_%d", new Object[]{Integer.valueOf(getQuantaIndex($l0))}));
        $r2.putString(AppEventsConstants.EVENT_PARAM_SOURCE_APPLICATION, this.firstOpenSourceApplication);
        $r1.logEvent(AppEventsConstants.EVENT_NAME_DEACTIVATED_APP, (double) (this.millisecondsSpentInSession / INTERRUPTION_THRESHOLD_MILLISECONDS), $r2);
        resetSession();
    }

    private static int getQuantaIndex(long $l0) throws  {
        int $i2 = 0;
        while ($i2 < INACTIVE_SECONDS_QUANTA.length && INACTIVE_SECONDS_QUANTA[$i2] < $l0) {
            $i2++;
        }
        return $i2;
    }

    private void resetSession() throws  {
        this.isAppActive = false;
        this.lastResumeTime = -1;
        this.lastSuspendTime = -1;
        this.interruptionCount = 0;
        this.millisecondsSpentInSession = 0;
    }

    private boolean wasSuspendedEver() throws  {
        return this.lastSuspendTime != -1;
    }

    private boolean isColdLaunch() throws  {
        boolean $z0 = !this.isWarmLaunch;
        this.isWarmLaunch = true;
        return $z0;
    }
}
