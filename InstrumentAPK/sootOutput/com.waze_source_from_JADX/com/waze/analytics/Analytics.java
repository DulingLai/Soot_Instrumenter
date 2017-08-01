package com.waze.analytics;

import com.waze.Logger;
import com.waze.NativeManager;

public final class Analytics {
    public static final String ANALYTICS_EVENT_ADS_HIDE_REASON_CLICK = "CLICK";
    public static final String ANALYTICS_EVENT_ADS_HIDE_REASON_CLOSE = "CLOSE";
    public static final String ANALYTICS_EVENT_ADS_HIDE_REASON_COMPLETE = "COMPLETE";
    public static final String ANALYTICS_EVENT_ADS_HIDE_REASON_DRIVE = "DRIVE";
    public static final String ANALYTICS_EVENT_ADS_HIDE_REASON_UNKNOWN = "UNKNOWN";
    public static final String ANALYTICS_EVENT_ADS_HIDE_REASON_VIEW = "VIEW";

    public static void log(String $r0, boolean $z0, boolean $z1) throws  {
        NativeManager.getInstance().logAnalytics($r0, $z0, $z1);
    }

    public static void flush() throws  {
        NativeManager.getInstance().logAnalyticsFlush();
    }

    public static void log(String $r0, String $r1, int $i0) throws  {
        NativeManager.getInstance().logAnalytics($r0, $r1, $i0);
    }

    public static void log(String $r0, String $r1, String $r2) throws  {
        NativeManager.getInstance().logAnalytics($r0, $r1, $r2);
    }

    public static void log(String $r0) throws  {
        NativeManager $r1 = NativeManager.getInstance();
        if ($r1 == null) {
            Logger.m38e("C code not init yet! cannot log analytics event " + $r0);
        } else {
            $r1.logAnalytics($r0, null, null);
        }
    }

    public static void logAdsContext(final String $r0) throws  {
        final NativeManager $r2 = NativeManager.getInstance();
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                $r2.logAnalyticsAdsContextNTV($r0);
            }
        });
    }

    public static void adsContextClear() throws  {
        final NativeManager $r1 = NativeManager.getInstance();
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                $r1.logAnalyticsAdsContextClearNTV();
            }
        });
    }

    public static void adsContextSearchInit(String $r0, int $i0, int $i1, int $i2, boolean $z0, String $r1, String $r2, String $r3, String $r4) throws  {
        final NativeManager instance = NativeManager.getInstance();
        final String str = $r0;
        final int i = $i0;
        final int i2 = $i1;
        final int i3 = $i2;
        final boolean z = $z0;
        final String str2 = $r1;
        final String str3 = $r2;
        final String str4 = $r3;
        final String str5 = $r4;
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                instance.logAnalyticsAdsContextSearchInitNTV(str, i, i2, i3, z, str2, str3, str4, str5);
            }
        });
    }

    public static void adsContextNavigationInit() throws  {
        final NativeManager $r1 = NativeManager.getInstance();
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                $r1.logAnalyticsAdsContextNavigationInitNTV();
            }
        });
    }

    public static void logAdsContextNav(final String $r0) throws  {
        final NativeManager $r2 = NativeManager.getInstance();
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                $r2.logAnalyticsAdsContextNavNTV($r0);
            }
        });
    }

    public static void logAdsSearchEvent(String $r0, String $r1, int $i0, int $i1, int $i2, boolean $z0, String $r2, String $r3, String $r4, String $r5) throws  {
        final NativeManager instance = NativeManager.getInstance();
        final String str = $r0;
        final String str2 = $r1;
        final int i = $i0;
        final int i2 = $i1;
        final int i3 = $i2;
        final boolean z = $z0;
        final String str3 = $r2;
        final String str4 = $r3;
        final String str5 = $r4;
        final String str6 = $r5;
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                instance.logAnalyticsAdsSearchEventNTV(str, str2, i, i2, i3, z, str3, str4, str5, str6);
            }
        });
    }

    public static void logAdsContextDisplayTime(final String $r0) throws  {
        final NativeManager $r2 = NativeManager.getInstance();
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                $r2.logAdsContextDisplayTimeNTV($r0);
            }
        });
    }
}
