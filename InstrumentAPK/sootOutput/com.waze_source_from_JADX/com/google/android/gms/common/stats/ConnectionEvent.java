package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: dalvik_source_com.waze.apk */
public final class ConnectionEvent extends StatsEvent {
    public static final Creator<ConnectionEvent> CREATOR = new zza();
    private final long LJ;
    private int LK;
    private final String LL;
    private final String LM;
    private final String LN;
    private final String LO;
    private final String LP;
    private final String LQ;
    private final long LR;
    private final long LS;
    private long LT;
    final int mVersionCode;

    ConnectionEvent(int $i0, long $l1, int $i2, String $r1, String $r2, String $r3, String $r4, String $r5, String $r6, long $l3, long $l4) throws  {
        this.mVersionCode = $i0;
        this.LJ = $l1;
        this.LK = $i2;
        this.LL = $r1;
        this.LM = $r2;
        this.LN = $r3;
        this.LO = $r4;
        this.LT = -1;
        this.LP = $r5;
        this.LQ = $r6;
        this.LR = $l3;
        this.LS = $l4;
    }

    public ConnectionEvent(long $l0, int $i1, String $r1, String $r2, String $r3, String $r4, String $r5, String $r6, long $l2, long $l3) throws  {
        this(1, $l0, $i1, $r1, $r2, $r3, $r4, $r5, $r6, $l2, $l3);
    }

    public long getDurationMillis() throws  {
        return this.LT;
    }

    public int getEventType() throws  {
        return this.LK;
    }

    public long getTimeMillis() throws  {
        return this.LJ;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zza.zza(this, $r1, $i0);
    }

    public String zzaya() throws  {
        return this.LL;
    }

    public String zzayb() throws  {
        return this.LM;
    }

    public String zzayc() throws  {
        return this.LN;
    }

    public String zzayd() throws  {
        return this.LO;
    }

    public String zzaye() throws  {
        return this.LP;
    }

    public String zzayf() throws  {
        return this.LQ;
    }

    public long zzayg() throws  {
        return this.LS;
    }

    public long zzayh() throws  {
        return this.LR;
    }

    public String zzayi() throws  {
        String $r2 = String.valueOf("\t");
        String $r3 = String.valueOf(zzaya());
        String $r4 = String.valueOf(zzayb());
        String $r5 = String.valueOf("\t");
        String $r6 = String.valueOf(zzayc());
        String $r7 = String.valueOf(zzayd());
        String $r8 = String.valueOf("\t");
        String $r9 = this.LP == null ? "" : this.LP;
        String $r10 = String.valueOf("\t");
        return new StringBuilder(((((((((String.valueOf($r2).length() + 22) + String.valueOf($r3).length()) + String.valueOf($r4).length()) + String.valueOf($r5).length()) + String.valueOf($r6).length()) + String.valueOf($r7).length()) + String.valueOf($r8).length()) + String.valueOf($r9).length()) + String.valueOf($r10).length()).append($r2).append($r3).append("/").append($r4).append($r5).append($r6).append("/").append($r7).append($r8).append($r9).append($r10).append(zzayg()).toString();
    }
}
