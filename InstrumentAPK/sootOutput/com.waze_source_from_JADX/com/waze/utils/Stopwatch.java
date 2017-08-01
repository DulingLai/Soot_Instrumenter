package com.waze.utils;

import android.os.SystemClock;
import com.waze.Logger;

public class Stopwatch {
    public static final String LOG_TAG = "STOPWATCH";
    private volatile long mLastSample = 0;
    private String mName = "Generic";
    private volatile long mStartTime = 0;

    public Stopwatch(String $r1) throws  {
        this.mName = $r1;
    }

    public static Stopwatch create(String $r0) throws  {
        return new Stopwatch($r0);
    }

    public void start() throws  {
        this.mStartTime = _sample();
    }

    public void reset() throws  {
        this.mStartTime = 0;
    }

    public long startDelta(String $r1, boolean $z0) throws  {
        long $l0 = ($z0 ? sample() : _sample()) - this.mStartTime;
        printDelta($r1, $l0, false);
        return $l0;
    }

    public long sampleDelta(String $r1, boolean $z0) throws  {
        long $l0 = ($z0 ? sample() : _sample()) - this.mLastSample;
        printDelta($r1, $l0, true);
        return $l0;
    }

    private void printDelta(String $r1, long $l0, boolean $z0) throws  {
        String $r2;
        if ($z0) {
            $r2 = "SAMPLE; ";
        } else {
            $r2 = "";
        }
        if ($r1 != null) {
            Logger.i_("STOPWATCH " + this.mName, $r2 + "$ " + $r1 + " $. Delta: " + $l0);
        }
    }

    public long sample() throws  {
        this.mLastSample = _sample();
        return this.mLastSample;
    }

    public static long _sample() throws  {
        return SystemClock.elapsedRealtime();
    }
}
