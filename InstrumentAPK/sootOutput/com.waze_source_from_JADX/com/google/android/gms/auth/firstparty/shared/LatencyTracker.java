package com.google.android.gms.auth.firstparty.shared;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/* compiled from: dalvik_source_com.waze.apk */
public class LatencyTracker extends AbstractSafeParcelable {
    public static final LatencyTrackerCreator CREATOR = new LatencyTrackerCreator();
    final long iG;
    final String mName;
    final int mVersion;
    public final LatencyTracker parent;

    LatencyTracker(int $i0, String $r1, long $l1, LatencyTracker $r2) throws  {
        this.mVersion = $i0;
        this.mName = $r1;
        this.iG = $l1;
        this.parent = $r2;
        log($r1, "constructed");
    }

    public static LatencyTracker create(String $r0) throws  {
        return new LatencyTracker(1, $r0, SystemClock.elapsedRealtime(), null);
    }

    public static LatencyTracker fromBundle(Bundle $r0) throws  {
        return (LatencyTracker) $r0.getParcelable("latency.tracker");
    }

    public static LatencyTracker fromIntent(Intent $r0) throws  {
        return (LatencyTracker) $r0.getParcelableExtra("latency.tracker");
    }

    public LatencyTracker createChild(String $r1) throws  {
        return new LatencyTracker(1, $r1, SystemClock.elapsedRealtime(), this);
    }

    public void log(String $r1, String $r2) throws  {
        if (Log.isLoggable("GLSLogging", 2)) {
            String $r3 = zzac(SystemClock.elapsedRealtime());
            Log.println(2, "GLSLogging", new StringBuilder(((String.valueOf($r1).length() + 4) + String.valueOf($r3).length()) + String.valueOf($r2).length()).append($r1).append(" ").append($r3).append(" > ").append($r2).toString());
        }
    }

    public void toBundle(Bundle $r1) throws  {
        $r1.putParcelable("latency.tracker", this);
    }

    public void toIntent(Intent $r1) throws  {
        $r1.putExtra("latency.tracker", this);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        log(this.mName, "writing to parcel");
        LatencyTrackerCreator.zza(this, $r1, $i0);
    }

    String zzac(long $l0) throws  {
        LinkedList $r2 = new LinkedList();
        while ($r1 != null) {
            long $l2 = $l0 - $r1.iG;
            $l2 -= TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds($l2));
            $r2.addFirst(String.format("[%s, %,d.%03ds]", new Object[]{$r1.mName, Long.valueOf($l1), Long.valueOf($l2)}));
            $r1 = $r1.parent;
        }
        return TextUtils.join(" > ", $r2);
    }
}
