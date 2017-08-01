package com.google.android.gms.common.stats;

import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class StatsEvent extends AbstractSafeParcelable {
    public abstract long getDurationMillis() throws ;

    public abstract int getEventType() throws ;

    public abstract long getTimeMillis() throws ;

    public String toString() throws  {
        long $l0 = getTimeMillis();
        String $r2 = String.valueOf("\t");
        int $i1 = getEventType();
        String $r3 = String.valueOf("\t");
        long $l2 = getDurationMillis();
        String $r4 = String.valueOf(zzayi());
        return new StringBuilder(((String.valueOf($r2).length() + 51) + String.valueOf($r3).length()) + String.valueOf($r4).length()).append($l0).append($r2).append($i1).append($r3).append($l2).append($r4).toString();
    }

    public abstract String zzayi() throws ;
}
