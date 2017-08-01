package com.facebook.internal;

import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.google.android.gms.gcm.nts.GcmScheduler;
import java.util.HashMap;
import java.util.Map.Entry;

public class Logger {
    public static final String LOG_TAG_BASE = "FacebookSDK.";
    private static final HashMap<String, String> stringsToReplace = new HashMap();
    private final LoggingBehavior behavior;
    private StringBuilder contents;
    private int priority = 3;
    private final String tag;

    public static synchronized void registerStringToReplace(String $r0, String $r1) throws  {
        Class cls = Logger.class;
        synchronized (cls) {
            try {
                stringsToReplace.put($r0, $r1);
            } finally {
                cls = Logger.class;
            }
        }
    }

    public static synchronized void registerAccessToken(String $r0) throws  {
        synchronized (Logger.class) {
            try {
                if (!FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.INCLUDE_ACCESS_TOKENS)) {
                    registerStringToReplace($r0, "ACCESS_TOKEN_REMOVED");
                }
            } catch (Throwable th) {
                Class cls = Logger.class;
            }
        }
    }

    public static void log(LoggingBehavior $r0, String $r1, String $r2) throws  {
        log($r0, 3, $r1, $r2);
    }

    public static void log(LoggingBehavior $r0, String $r1, String $r2, Object... $r3) throws  {
        if (FacebookSdk.isLoggingBehaviorEnabled($r0)) {
            log($r0, 3, $r1, String.format($r2, $r3));
        }
    }

    public static void log(LoggingBehavior $r0, int $i0, String $r1, String $r2, Object... $r3) throws  {
        if (FacebookSdk.isLoggingBehaviorEnabled($r0)) {
            log($r0, $i0, $r1, String.format($r2, $r3));
        }
    }

    public static void log(LoggingBehavior $r0, int $i0, String $r1, String $r2) throws  {
        if (FacebookSdk.isLoggingBehaviorEnabled($r0)) {
            $r2 = replaceStrings($r2);
            if (!$r1.startsWith(LOG_TAG_BASE)) {
                $r1 = LOG_TAG_BASE + $r1;
            }
            Log.println($i0, $r1, $r2);
            if ($r0 == LoggingBehavior.DEVELOPER_ERRORS) {
                new Exception().printStackTrace();
            }
        }
    }

    private static synchronized String replaceStrings(String $r0) throws  {
        Class cls = Logger.class;
        synchronized (cls) {
            try {
                for (Entry $r5 : stringsToReplace.entrySet()) {
                    $r0 = $r0.replace((CharSequence) $r5.getKey(), (CharSequence) $r5.getValue());
                }
                return $r0;
            } finally {
                cls = Logger.class;
            }
        }
    }

    public Logger(LoggingBehavior $r1, String $r2) throws  {
        Validate.notNullOrEmpty($r2, GcmScheduler.INTENT_PARAM_TAG);
        this.behavior = $r1;
        this.tag = LOG_TAG_BASE + $r2;
        this.contents = new StringBuilder();
    }

    public int getPriority() throws  {
        return this.priority;
    }

    public void setPriority(int $i0) throws  {
        Validate.oneOf(Integer.valueOf($i0), "value", Integer.valueOf(7), Integer.valueOf(3), Integer.valueOf(6), Integer.valueOf(4), Integer.valueOf(2), Integer.valueOf(5));
        this.priority = $i0;
    }

    public String getContents() throws  {
        return replaceStrings(this.contents.toString());
    }

    public void log() throws  {
        logString(this.contents.toString());
        this.contents = new StringBuilder();
    }

    public void logString(String $r1) throws  {
        log(this.behavior, this.priority, this.tag, $r1);
    }

    public void append(StringBuilder $r1) throws  {
        if (shouldLog()) {
            this.contents.append($r1);
        }
    }

    public void append(String $r1) throws  {
        if (shouldLog()) {
            this.contents.append($r1);
        }
    }

    public void append(String $r1, Object... $r2) throws  {
        if (shouldLog()) {
            this.contents.append(String.format($r1, $r2));
        }
    }

    public void appendKeyValue(String $r1, Object $r2) throws  {
        append("  %s:\t%s\n", $r1, $r2);
    }

    private boolean shouldLog() throws  {
        return FacebookSdk.isLoggingBehaviorEnabled(this.behavior);
    }
}
