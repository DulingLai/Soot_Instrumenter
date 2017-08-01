package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.WorkSource;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class ActivityRecognitionRequest extends AbstractSafeParcelable {
    public static final Creator<ActivityRecognitionRequest> CREATOR = new zza();
    private long aul;
    private boolean aum;
    @Nullable
    private WorkSource aun;
    @Nullable
    private int[] auo;
    @Nullable
    private boolean aup;
    @Nullable
    private String dL;
    @Nullable
    private String mTag;
    private final int mVersionCode;

    ActivityRecognitionRequest(int $i0, long $l1, boolean $z0, @Nullable WorkSource $r1, @Nullable String $r2, @Nullable int[] $r3, boolean $z1, @Nullable String $r4) throws  {
        this.mVersionCode = $i0;
        this.aul = $l1;
        this.aum = $z0;
        this.aun = $r1;
        this.mTag = $r2;
        this.auo = $r3;
        this.aup = $z1;
        this.dL = $r4;
    }

    @Nullable
    public String getAccountName() throws  {
        return this.dL;
    }

    public long getIntervalMillis() throws  {
        return this.aul;
    }

    @Nullable
    public String getTag() throws  {
        return this.mTag;
    }

    int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zza.zza(this, $r1, $i0);
    }

    public boolean zzbrz() throws  {
        return this.aum;
    }

    @Nullable
    public WorkSource zzbsa() throws  {
        return this.aun;
    }

    @Nullable
    public int[] zzbsb() throws  {
        return this.auo;
    }

    public boolean zzbsc() throws  {
        return this.aup;
    }
}
